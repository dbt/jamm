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
     * Returns the newly unchecked edit account items from the form.
     *
     * @return a Set of the newly unchecked edit account items.
     */
    public Set getUncheckedEditPostmasters()
    {
        return getDifference(mOriginalAllowEditPostmasters,
                             mAllowEditPostmasters);
    }
    /**
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
}
