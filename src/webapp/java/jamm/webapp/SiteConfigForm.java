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

package jamm.webapp;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;


/**
 * Holds the check boxes for the domain list of the site admin
 * form.
 *
 * @see jamm.webapp.SiteConfigAction
 */
public class SiteConfigForm extends JammForm
{
    /**
     * Returns the domains.
     *
     * @return a <code>String</code> array with the domains
     */
    public String[] getDomains()
    {
        return mDomains;
    }
    
    /**
     * Sets the domains
     *
     * @param domains a <code>String</code> array with the domains
     */
    public void setDomains(String[] domains)
    {
        mDomains = domains;
    }
    
    /**
     * Set domains that allow account editing.
     *
     * @param allowEditAccounts a <code>String</code> array with domains
     */
    public void setAllowEditAccounts(String[] allowEditAccounts)
    {
        mAllowEditAccounts = allowEditAccounts;
    }

    /**
     * Returns the domains that allow account editing.
     *
     * @return a <code>String[]</code> value
     */
    public String[] getAllowEditAccounts()
    {
        return mAllowEditAccounts;
    }

    /**
     * Gets the value of originalAllowEditAccounts
     *
     * @return the value of originalAllowEditAccounts
     */
    public String[] getOriginalAllowEditAccounts()
    {
        return this.mOriginalAllowEditAccounts;
    }

    /**
     * Sets the value of mOriginalAllowEditAccounts
     *
     * @param originalAllowEditAccounts Value to assign to
     *                                  this.mOriginalAllowEditAccounts
     */
    public void setOriginalAllowEditAccounts(
        String[] originalAllowEditAccounts)
    {
        this.mOriginalAllowEditAccounts = originalAllowEditAccounts;
    }

    /**
     * Returns the newly checked edit accounts items from the form.
     *
     * @return a Set of the newly checked edit accounts items.
     */
    public Set getCheckedEditAccounts()
    {
        return getDifference(mAllowEditAccounts, mOriginalAllowEditAccounts);
    }

    /**
     * Returns the newly unchecked edit account items from the form.
     *
     * @return a Set of the newly unchecked edit account items.
     */
    public Set getUncheckedEditAccounts()
    {
        return getDifference(mOriginalAllowEditAccounts, mAllowEditAccounts);
    }

    /**
     * Gets the value of allowEditPostmasters
     *
     * @return the value of allowEditPostmasters
     */
    public String[] getAllowEditPostmasters()
    {
        return this.mAllowEditPostmasters;
    }

    /**
     * Sets the value of mAllowEditPostmasters
     *
     * @param allowEditPostmasters Value to assign to
     *                             this.mAllowEditPostmasters
     */
    public void setAllowEditPostmasters(String[] allowEditPostmasters)
    {
        this.mAllowEditPostmasters = allowEditPostmasters;
    }

    /**
     * Gets the value of originalAllowEditPostmasters
     *
     * @return the value of originalAllowEditPostmasters
     */
    public String[] getOriginalAllowEditPostmasters()
    {
        return this.mOriginalAllowEditPostmasters;
    }

    /**
     * Sets the value of mOriginalAllowEditPostmasters
     *
     * @param originalAllowEditPostmasters Value to assign to
     *                                     this.mOriginalAllowEditPostmasters
     */
    public void setOriginalAllowEditPostmasters(
        String[] originalAllowEditPostmasters)
    {
        this.mOriginalAllowEditPostmasters = originalAllowEditPostmasters;
    }

    /**
     * Returns the newly checked edit Postmasters items from the form.
     *
     * @return a Set of the newly checked edit postmasters items.
     */
    public Set getCheckedEditPostmasters()
    {
        return getDifference(mAllowEditPostmasters,
                             mOriginalAllowEditPostmasters);
    }

    /**
     * Returns the newly unchecked edit postmaster items from the form.
     *
     * @return a Set of the newly unchecked edit postmaster items.
     */
    public Set getUncheckedEditPostmasters()
    {
        return getDifference(mOriginalAllowEditPostmasters,
                             mAllowEditPostmasters);
    }

    /**
     * Sets the list of domains that were originall active.
     *
     * @param active a string array of the originally active domains
     */
    public void setOriginalActive(String[] active)
    {
        mOriginalActive = active;
    }

    /**
     * Returns the list of originally active domains.
     *
     * @return a string array of domains
     */
    public String[] getOriginalActive()
    {
        return mOriginalActive;
    }

    /**
     * Sets the list of active domains
     *
     * @param active a string array of domains
     */
    public void setActive(String[] active)
    {
        mActive = active;
    }

    /**
     * Returns the list of originally active domains.
     *
     * @return a string array of domains
     */
    public String[] getActive()
    {
        return mActive;
    }

    /**
     * Returns the newly checked active items from the form.
     *
     * @return a Set of the newly checked active items.
     */
    public Set getCheckedActive()
    {
        return getDifference(mActive, mOriginalActive);
    }

    /**
     * Returns the newly unchecked active items from the form.
     *
     * @return a Set of the newly unchecked account items.
     */
    public Set getUncheckedActive()
    {
        return getDifference(mOriginalActive, mActive);
    }

    /**
     * Get the items to delete
     *
     * @return string array of domains to delete
     */
    public String[] getDelete()
    {
        return mDelete;
    }

    /**
     * Sets the items to delete
     *
     * @param delete list of strings to delete
     */
    public void setDelete(String[] delete)
    {
        mDelete = delete;
    }

    /**
     * Get the original list of items to delete
     *
     * @return String aray of domain names
     */
    public String[] getOriginalDelete()
    {
        return mOriginalDelete;
    }

    /**
     * set the original list of items to delete
     *
     * @param originalDelete a string array of domain names
     */
    public void setOriginalDelete(String[] originalDelete)
    {
        mOriginalDelete = originalDelete;
    }
    
    /**
     * returns the newly checked items to delete
     *
     * @return a set of Strings
     */
    public Set getCheckedDelete()
    {
        return getDifference(mDelete, mOriginalDelete);
    }

    /**
     * Returns the newly unchecked items to delete
     *
     * @return a set of strings
     */
    public Set getUncheckedDelete()
    {
        return getDifference(mOriginalDelete, mDelete);
    }

    /**
     * Resets the accounts, postmasters, and active domains to empty
     * arrays.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mAllowEditAccounts = new String[0];
        mOriginalAllowEditAccounts = new String[0];
        mAllowEditPostmasters = new String[0];
        mOriginalAllowEditPostmasters = new String[0];
        mActive = new String[0];
        mOriginalActive = new String[0];
        mDelete = new String[0];
        mOriginalDelete = new String[0];
    }

    /** the domains */
    private String[] mDomains;
    /** domains that allow account editing */
    private String[] mAllowEditAccounts;
    /** original domains that allow account editing */
    private String[] mOriginalAllowEditAccounts;
    /** domains that allow postmaster editing */
    private String[] mAllowEditPostmasters;
    /** original domains that allow postmaster editing */
    private String[] mOriginalAllowEditPostmasters;
    /** original list of active domains */
    private String[] mOriginalActive;
    /** List of active domains */
    private String[] mActive;
    /** List of domains marked for deletion */
    private String[] mDelete;
    /** original list of domains marked for deletion */
    private String[] mOriginalDelete;
}
