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

import jamm.backend.DomainInfo;
import jamm.backend.MailManager;
import jamm.backend.MailManagerException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Cleans up domains
 */
public class DomainCleaner
{
    /**
     * Creates a new <code>DomainCleander</code> instance.
     */
    public DomainCleaner()
    {
        mManager = new MailManager(JammCleanerOptions.getHost(),
                                   JammCleanerOptions.getBaseDn(),
                                   JammCleanerOptions.getBindDn(),
                                   JammCleanerOptions.getPassword());

        mDeadDomains = new ArrayList();
    }

    /**
     * Clean it up
     */
    public void cleanUp()
    {
        try
        {
            List domains = mManager.getDeleteMarkedDomains();
            Iterator i = domains.iterator();
            while (i.hasNext())
            {
                DomainInfo domain = (DomainInfo) i.next();

                AccountCleaner ac =
                    new AccountCleaner(domain.getName(),
                                       AccountCleaner.CLEAN_ALL);
                ac.cleanUp();
                
                // We assume all the accounts were cleaned up,
                // so we just nuke the domain
                if (JammCleanerOptions.isVerbose() ||
                        JammCleanerOptions.isNonDestructive())
                {
                    LOG.info("Removing domain " + domain.getName());
                }
                if (!JammCleanerOptions.isNonDestructive())
                {
                    mManager.deleteDomain(domain.getName());
                }
            }
        }
        catch (MailManagerException e)
        {
            LOG.error("Problem purging domains: ", e);
        }
    }

    /** the domains to clean up */
    private List mDeadDomains;
    /** the mail manager */
    private MailManager mManager;
    
    private static final Logger LOG = Logger.getLogger(DomainCleaner.class);
}
