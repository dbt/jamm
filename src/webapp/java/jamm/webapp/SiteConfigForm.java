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
     * Sets the domains that allow aliases editing.
     *
     * @param allowEditAliases a <code>String</code> array with the domains
     *                    that allow alias editing
     */
    public void setAllowEditAliases(String[] allowEditAliases)
    {
        mAllowEditAliases = allowEditAliases;
    }

    /**
     * Returns the domains that allow alias editing.
     *
     * @return a <code>String</code> array with the domains that allow
     *         alias editing
     */
    public String[] getAllowEditAliases()
    {
        return mAllowEditAliases;
    }

    /**
     * The original domains that allowed alias editing
     *
     * @param originalAllowEditAliases a <code>String</code> array with domains
     */
    public void setOriginalAllowEditAliases(String[] originalAllowEditAliases)
    {
        mOriginalAllowEditAliases = originalAllowEditAliases;
    }

    /**
     * Returns the original domains that allow alias editing.
     *
     * @return a <code>String</code> array with domains.
     */
    public String[] getOriginalAllowEditAliases()
    {
        return mOriginalAllowEditAliases;
    }

    /**
     * Returns the newly checked edit alias items from the form.
     *
     * @return a Set of the newly checked edit alias items.
     */
    public Set getCheckedEditAliases()
    {
        return getDifference(mAllowEditAliases, mOriginalAllowEditAliases);
    }

    /**
     * Returns the newly unchecked edit alias items from the form.
     *
     * @return a Set of the newly unchecked edit alias items.
     */
    public Set getUncheckedEditAliases()
    {
        return getDifference(mOriginalAllowEditAliases, mAllowEditAliases);
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
     * Gets the value of allowEditCatchalls
     *
     * @return the value of allowEditCatchalls
     */
    public String[] getAllowEditCatchalls()
    {
        return this.mAllowEditCatchalls;
    }

    /**
     * Sets the value of mAllowEditCatchalls
     *
     * @param allowEditCatchalls Value to assign to this.mAllowEditCatchalls
     */
    public void setAllowEditCatchalls(String[] allowEditCatchalls)
    {
        this.mAllowEditCatchalls = allowEditCatchalls;
    }

    /**
     * Gets the value of mOriginalAllowEditCatchalls
     *
     * @return the value of mOriginalAllowEditCatchalls
     */
    public String[] getOriginalAllowEditCatchalls()
    {
        return this.mOriginalAllowEditCatchalls;
    }

    /**
     * Sets the value of mOriginalAllowEditCatchalls
     *
     * @param originalAllowEditCatchalls Value to assign to
     *                                   this.mOriginalAllowEditCatchalls
     */
    public void setOriginalAllowEditCatchalls(
        String[] originalAllowEditCatchalls)
    {
        this.mOriginalAllowEditCatchalls = originalAllowEditCatchalls;
    }

    /**
     * Returns the newly checked edit Catchalls items from the form.
     *
     * @return a Set of the newly checked edit catchalls items.
     */
    public Set getCheckedEditCatchalls()
    {
        return getDifference(mAllowEditCatchalls,
                             mOriginalAllowEditCatchalls);
    }

    /**
     * Returns the newly unchecked edit account items from the form.
     *
     * @return a Set of the newly unchecked edit account items.
     */
    public Set getUncheckedEditCatchalls()
    {
        return getDifference(mOriginalAllowEditCatchalls,
                             mAllowEditCatchalls);
    }

    /**
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mAllowEditAliases = new String[0];
        mOriginalAllowEditAliases = new String[0];
        mAllowEditAccounts = new String[0];
        mOriginalAllowEditAccounts = new String[0];
        mAllowEditPostmasters = new String[0];
        mOriginalAllowEditPostmasters = new String[0];
        mAllowEditCatchalls = new String[0];
        mOriginalAllowEditCatchalls = new String[0];
    }

    /** the domains */
    private String[] mDomains;
    /** domains that allow alias editing */
    private String[] mAllowEditAliases;
    /** original domains that allow alias editing */
    private String[] mOriginalAllowEditAliases;
    /** domains that allow account editing */
    private String[] mAllowEditAccounts;
    /** original domains that allow account editing */
    private String[] mOriginalAllowEditAccounts;
    /** domains that allow postmaster editing */
    private String[] mAllowEditPostmasters;
    /** original domains that allow postmaster editing */
    private String[] mOriginalAllowEditPostmasters;
    /** domains that allow catchall editing */
    private String[] mAllowEditCatchalls;
    /** original domains that allow catchall editing */
    private String[] mOriginalAllowEditCatchalls;
}
