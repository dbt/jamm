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

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;

import jamm.backend.MailManager;
import jamm.backend.MailManagerException;
import jamm.backend.AccountInfo;
import jamm.backend.DomainInfo;
import jamm.util.UserQueries;
import jamm.util.FileUtils;

/**
 * The account cleaner object.  To nuke all accounts for a domain,
 * create with a specified domain and set cut off time to be 0.
 */
public class AccountCleaner extends AbstractCleaner
{
    /**
     * Creates a new <code>AccountCleaner</code> instance.
     * This instance cleans all domains.
     */
    public AccountCleaner()
    {
        mManager = new MailManager(JammCleanerOptions.getHost(),
                                   JammCleanerOptions.getBaseDn(),
                                   JammCleanerOptions.getBindDn(),
                                   JammCleanerOptions.getPassword());

        mDeadAccounts = new ArrayList();
        setCutOffTime(5);
        mDomain = null;
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
        setCutOffTime(5);
        mDomain = domain;
    }
    

    /**
     * Actually do the cleanup
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
                domains.add(mDomain);
            }
            
            Iterator d = domains.iterator();
            while (d.hasNext())
            {
                DomainInfo domain = (DomainInfo) d.next();
                loadAccountListForDomain(domain.getName());
            }

            nukeAccounts();
        }
        catch (MailManagerException e)
        {
            System.err.println("Problem purging accounts: " + e);
        }
    }

    /**
     * actually does the nuking
     */
    private void nukeAccounts()
    {
        boolean nondestruct = JammCleanerOptions.isNonDestructive();
        Iterator a = mDeadAccounts.iterator();

        while (a.hasNext())
        {
            AccountInfo account = (AccountInfo) a.next();
            if (nondestruct)
            {
                System.out.println(account.getName() +
                                   " successfully deleted");
            }
            else
            {
                File file = new File(account.getFullPathToMailbox());
                boolean successful = FileUtils.recursiveDelete(file);

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
                        System.out.println("Error: " + account.getName() +
                                           "not deleted: " + e.toString());
                    }
                    
                    if (ldapsuccess && JammCleanerOptions.isVerbose())
                    {
                        System.out.println(account.getName() +
                                           " successfully deleted");
                    }
                }
                else
                {
                    System.out.println("Error: " + account.getName() +
                                       " not deleted");
                }
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

        List inactiveAccts = mManager.getInactiveAccounts(domain);
        Iterator a = inactiveAccts.iterator();
        
        int currentUnixTime = (int) (System.currentTimeMillis() / 1000);
        long cutOffTime = getCutOffTime();

        while (a.hasNext())
        {
            AccountInfo account = (AccountInfo) a.next();
            int timeDelta = currentUnixTime - account.getLastChange();
            if (timeDelta > cutOffTime)
            {
                if (JammCleanerOptions.isAssumeYes())
                {
                    if (verbose)
                    {
                        System.out.println(account.getName() +
                                           "marked for deletion");
                    }
                    mDeadAccounts.add(account);
                }
                else
                {
                    StringBuffer sb = new StringBuffer(account.getName());
                    sb.append(" has been inactive over the cutoff time.\n");
                    sb.append("Would you like to remove its data?");
                    if (UserQueries.askYesNo(sb.toString()))
                    {
                        mDeadAccounts.add(account);
                    }
                }
            }
            else
            {
                if (verbose)
                {
                    System.out.print(account.getName());
                    System.out.println(" hasn't been inactive long enough.  " +
                                       "Ignoring....");
                }
            }
        }
    }

    /** Our mail manager */
    private MailManager mManager;
    /** the accounts to remove */
    private List mDeadAccounts;
    /** domain name to clean */
    private String mDomain;
}
