/*
 * Jamm
 * Copyright (C) 2002 Dave Dribin and Keith Garner
 *  
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jamm.tools;

import jamm.backend.AccountInfo;
import jamm.backend.DomainInfo;
import jamm.backend.MailManager;
import jamm.backend.MailManagerException;
import jamm.util.FileUtils;
import jamm.util.ZipCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * The account cleaner object.  To nuke all accounts for a domain,
 * set the CleanAll property.
 */
public class AccountCleaner
{
    /**
     * Creates a new <code>AccountCleaner</code> instance.
     * This instance cleans all domains.
     */
    public AccountCleaner()
    {
        this(null);
    }

    /**
     * Creates a new <code>AccountCleaner</code> instance.  This
     * instance only cleans the domain specified.
     *
     * @param domain the domain to clean.
     */
    public AccountCleaner(String domain)
    {
        mManager = new MailManager(JammCleanerOptions.getHost(),
                                   JammCleanerOptions.getBaseDn(),
                                   JammCleanerOptions.getBindDn(),
                                   JammCleanerOptions.getPassword());

        mDeadAccounts = new ArrayList();
        mDomain = domain;
        mCleanAllAccounts = CLEAN_ONLY_DELETE;
    }

    /**
     * Creates a new <code>AccountCleaner</code> instance.  That
     * cleans all accounts for this domain.
     *
     * @param domain the domain to clean
     * @param cleanAllAccounts AccountCleaner.CLEAN_ALL or
     *                         AccountCleaner.CLEAN_ONLY_DELETE
     */
    public AccountCleaner(String domain, boolean cleanAllAccounts)
    {
        this(domain);
        mCleanAllAccounts = cleanAllAccounts;
    }

    /**
     * Should we clean all accounts regardless of marking?
     *
     * @param cleanAllAccounts boolean value
     */
    public void setCleanAll(boolean cleanAllAccounts)
    {
        mCleanAllAccounts = cleanAllAccounts;
    }

    /**
     * Get value of marked for cleaning.
     *
     * @return boolean value
     */
    public boolean isCleanAll()
    {
        return mCleanAllAccounts;
    }
    

    /**
     * Actually do the cleanup.
     */
    public void cleanUp()
    {
        try
        {
            List domains;

            if (mDomain == null)
            {
                domains = mManager.getDomains();
            }
            else
            {
                domains = new ArrayList();
                domains.add(mManager.getDomain(mDomain));
            }
            
            Iterator d = domains.iterator();
            while (d.hasNext())
            {
                DomainInfo domain = (DomainInfo) d.next();
                loadAccountListForDomain(domain.getName());
            }

            cleanUpAccounts();
        }
        catch (MailManagerException e)
        {
            LOG.error("Problem purging accounts: ", e);
        }
    }

    /**
     * Performs the actions to erase an account.
     *
     * @param account an account info object
     */
    public void deleteAccount(AccountInfo account)
    {
        boolean nondestruct = JammCleanerOptions.isNonDestructive();

        if (nondestruct)
        {
            LOG.info(account.getName() + " successfully deleted");
        }
        else
        {
            File file = new File(account.getFullPathToMailbox());
            boolean successful = false;
            if (file.exists())
            {
                successful = FileUtils.recursiveDelete(file);
            }
            else
            {
                LOG.info("No filesystem data for " + account.getName() +
                         " exists.  Removing from LDAP anyway.");
                successful = true;
            }

            if (successful)
            {
                boolean ldapsuccess = false;
                try
                {
                    mManager.deleteAccount(account.getName());
                    ldapsuccess = true;
                }
                catch (MailManagerException e)
                {
                    LOG.error("Error: " + account.getName() +
                              "not deleted: ", e);
                }
                    
                if (ldapsuccess && JammCleanerOptions.isVerbose())
                {
                    LOG.info(account.getName() + " successfully deleted");
                }
            }
            else
            {
                LOG.error("Error: " + account.getName() + " not deleted");
            }
        }
    }
    
    /**
     * actually does archiving and nuking
     */
    private void cleanUpAccounts()
    {
        Iterator a = mDeadAccounts.iterator();

        while (a.hasNext())
        {
            AccountInfo account = (AccountInfo) a.next();
            try
            {
                if (JammCleanerOptions.shouldBackup() &&
                    !JammCleanerOptions.isNonDestructive())
                {
                    archiveAccount(account);
                }

                deleteAccount(account);

            }
            catch (IOException e)
            {
                LOG.error("Archiving problem: ", e);
            }
        }
    }

    /**
     * Loads the list of accounts to nuke.
     *
     * @param domain the domain to work on
     * @exception MailManagerException if an error occurs
     */
    private void loadAccountListForDomain(String domain)
        throws MailManagerException
    {
        boolean verbose = JammCleanerOptions.isVerbose();

        List deleteAccts;

        if (mCleanAllAccounts)
        {
            deleteAccts = mManager.getAccounts(domain);
        }
        else
        {
            deleteAccts = mManager.getDeleteMarkedAccounts(domain);
        }

        Iterator a = deleteAccts.iterator();
        while (a.hasNext())
        {
            AccountInfo account = (AccountInfo) a.next();
            if (verbose)
            {
                LOG.info(account.getName() + " is marked for deletion.");
            }
            mDeadAccounts.add(account);
        }
    }

    /**
     * Performs the actions to archive the account in a zip file.
     *
     * @param account an accountinfo object
     * @exception IOException if an error occurs
     */
    private void archiveAccount(AccountInfo account)
        throws IOException
    {
        String backupDir = JammCleanerOptions.getBackupDirectory();
        String email = account.getName();

        int atLoc = email.indexOf('@');
        StringBuffer sb = new StringBuffer();
        sb.append(email.substring(0, atLoc));
        sb.append("AT");
        sb.append(email.substring(atLoc + 1));
        email = sb.toString();

        sb = new StringBuffer(backupDir);
        sb.append(File.separatorChar).append(email).append(".zip");

        if (JammCleanerOptions.isVerbose())
        {
            StringBuffer output = new StringBuffer();
            output.append("Backuping up ").append(account.getName());
            output.append(" to ").append(sb.toString());
            LOG.info(output);
        }

        ZipCreator zc = new ZipCreator(sb.toString());
        zc.open();
        zc.setBaseDirectory(account.getHomeDirectory());
        zc.add(account.getFullPathToMailbox());
        zc.close();
    }
        
    /** Our mail manager */
    private MailManager mManager;
    /** the accounts to remove */
    private List mDeadAccounts;
    /** domain name to clean */
    private String mDomain;
    /** should we clean up all accounts? */
    private boolean mCleanAllAccounts;

    /** Clean all accounts define for readability. */
    public static final boolean CLEAN_ALL = true;
    /** Clean only delete define for readability. */
    public static final boolean CLEAN_ONLY_DELETE = false;

    /** Our Logging object */
    private static final Logger LOG = Logger.getLogger(AccountCleaner.class);
}
