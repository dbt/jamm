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
import java.util.HashSet;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * Holds the check boxes for the account list of the domain admin
 * form.  This is a list of check boxes for Delete, Action, and Admin.
 * Used by both DomainAccountAction and DomainAliasAction.
 *
 * @see jamm.webapp.DomainAccountAction
 * @see jamm.webapp.DomainAliasAction
 */
public class DomainConfigForm extends ActionForm
{
    /**
     * Sets the items to delete.
     *
     * @param itemsToDelete an array of strings containing the items
     *                      to delete.
     */
    public void setItemsToDelete(String[] itemsToDelete)
    {
        mItemsToDelete = itemsToDelete;
    }

    /**
     * Returns the items to delete.
     *
     * @return a string array containing the items to delete.
     */
    public String[] getItemsToDelete()
    {
        return mItemsToDelete;
    }

    /**
     * The original list of active items.
     *
     * @param originalActiveItems an array of strings containing the
     *                            originally active items.
     */
    public void setOriginalActiveItems(String[] originalActiveItems)
    {
        mOriginalActiveItems = originalActiveItems;
    }

    /**
     * Returns the original list of active items.
     *
     * @return a string array containing the originally active items.
     */
    public String[] getOriginalActiveItems()
    {
        return mOriginalActiveItems;
    }

    /**
     * Sets the active items.
     *
     * @param activeItems an array of strings containing the active items.
     */
    public void setActiveItems(String[] activeItems)
    {
        mActiveItems = activeItems;
    }

    /**
     * Returns the active items.
     *
     * @return a string array of the active items.
     */
    public String[] getActiveItems()
    {
        return mActiveItems;
    }

    /**
     * Returns the newly checked active items from the form.
     *
     * @return a Set of the newly checked active items.
     */
    public Set getCheckedActiveItems()
    {
        return getDifference(mActiveItems, mOriginalActiveItems);
    }

    /**
     * Returns the newly unchecked active items from the form.
     *
     * @return a Set of the newly unchecked active items.
     */
    public Set getUncheckedActiveItems()
    {
        return getDifference(mOriginalActiveItems, mActiveItems);
    }

    /**
     * Set the originally checked admin items.
     *
     * @param originalAdminItems a string array of the checked items.
     */
    public void setOriginalAdminItems(String[] originalAdminItems)
    {
        mOriginalAdminItems = originalAdminItems;
    }

    /**
     * Returns the originally checked admin items.
     *
     * @return a string array of the originally unchecked items.
     */
    public String[] getOriginalAdminItems()
    {
        return mOriginalAdminItems;
    }

    /**
     * Sets the list of checked admin items.
     *
     * @param adminItems a string array of the checked admin items.
     */
    public void setAdminItems(String[] adminItems)
    {
        mAdminItems = adminItems;
    }

    /**
     * Returns the list of checked admin items.
     *
     * @return a string array of the checked admin items.
     */
    public String[] getAdminItems()
    {
        return mAdminItems;
    }

    /**
     * Returns the newly checked admin items.
     *
     * @return a Set of the newly checked admin items.
     */
    public Set getCheckedAdminItems()
    {
        return getDifference(mAdminItems, mOriginalAdminItems);
    }

    /**
     * Returns the newly unchecked items.
     *
     * @return a Set of the newly unchecked admin items.
     */
    public Set getUncheckedAdminItems()
    {
        return getDifference(mOriginalAdminItems, mAdminItems);
    }

    /**
     * Returns the items in list1 that are not in list 2.
     *
     * @param list1 a String array
     * @param list2 a String array
     *
     * @return a Set of the items in list1 not in list2
     */
    private Set getDifference(String[] list1, String[] list2)
    {
        Set set1 = new HashSet(Arrays.asList(list1));
        Set set2 = new HashSet(Arrays.asList(list2));
        set1.removeAll(set2);
        return set1;
    }

    /**
     * Resets the form to the default values.  In this case, all of
     * the lists become empty.
     *
     * @param mapping The mapping used to select this instance.
     * @param request The servlet request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mItems = new String[0];
        mItemsToDelete = new String[0];
        mOriginalActiveItems = new String[0];
        mActiveItems = new String[0];
        mOriginalAdminItems = new String[0];
        mAdminItems = new String[0];
    }

    /** The list of items. */
    private String[] mItems;
    /** The checked items to delete */
    private String[] mItemsToDelete;
    /** The original list of active items */
    private String[] mOriginalActiveItems;
    /** The checked active items */
    private String[] mActiveItems;
    /** The original list of admin items */
    private String[] mOriginalAdminItems;
    /** The checked list of admin items. */
    private String[] mAdminItems;
}
P
