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

        deadAccounts = new ArrayList();
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
                doDomain(domain.getName());
            }
        }
        catch (MailManagerException e)
        {
            System.err.println("Problem purging accounts: " + e);
        }
    }

    /**
     * Remove the domain and its contents
     *
     * @param domain the domain to work on
     * @exception MailManagerException if an error occurs
     */
    private void doDomain(String domain)
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
            if (verbose)
            {
                System.out.println(account.getName() +
                                   " has been inactive for " + timeDelta);
            }
            if (timeDelta > mCutOffTime)
            {
                String mailDir = account.getFullPathToMailbox();
                if (verbose)
                {
                    System.out.println("Mailbox is " + mailDir);
                }
                File mailDirFile = new File(mailDir);

                File[] dude = mailDirFile.listFiles();

                System.out.println("There are " + dude.length + "files");
                for (int i = 0 ; i < dude.length; i++)
                {
                    System.out.println(dude[i].getName());
                }
                // Nuke files
                // Nuke ldap account
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
    private List deadAccounts;
}
