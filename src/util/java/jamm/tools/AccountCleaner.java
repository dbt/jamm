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
 * The account cleaner object
 */
public class AccountCleaner
{
    /**
     * Creates a new <code>AccountCleaner</code> instance.
     */
    public AccountCleaner()
    {
        mManager = new MailManager(JammCleanerOptions.getHost(),
                                   JammCleanerOptions.getBaseDn(),
                                   JammCleanerOptions.getBindDn(),
                                   JammCleanerOptions.getPassword());

        mDeadAccounts = new ArrayList();
        mCutOffTime = 5;
    }

    /**
     * How long should an account be inactive before we nuke it.
     *
     * @param time delta for time in milliseconds
     */
    public void setCutOffTime(long time)
    {
        mCutOffTime = time;
    }

    /**
     * Return the cuttoff time in milliseconds
     *
     * @return a long containing the time
     */
    public long getCutOffTime()
    {
        return mCutOffTime;
    }

    /**
     * Actually do the cleanup
     */
    public void cleanUp()
    {
        try
        {
            List domains = mManager.getDomains();
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
        Iterator a = mDeadAccounts.iterator();

        while (a.hasNext())
        {
            AccountInfo account = (AccountInfo) a.next();
            if (FileUtils.recursiveDelete(
                    new File(account.getFullPathToMailbox())))
            {
                if (JammCleanerOptions.isVerbose())
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

        while (a.hasNext())
        {
            AccountInfo account = (AccountInfo) a.next();
            int timeDelta = currentUnixTime - account.getLastChange();
            if (timeDelta > mCutOffTime)
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

    /** The cutoff time */
    private long mCutOffTime;
    /** Our mail manager */
    private MailManager mManager;
    /** the accounts to remove */
    private List mDeadAccounts;
}
