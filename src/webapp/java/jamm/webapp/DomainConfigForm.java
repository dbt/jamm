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
 */
public class DomainConfigForm extends ActionForm
{
    public void setItemsToDelete(String[] itemsToDelete)
    {
        mItemsToDelete = itemsToDelete;
    }

    public String[] getItemsToDelete()
    {
        return mItemsToDelete;
    }

    public void setOriginalActiveItems(String[] originalActiveItems)
    {
        mOriginalActiveItems = originalActiveItems;
    }

    public String[] getOriginalActiveItems()
    {
        return mOriginalActiveItems;
    }

    public void setActiveItems(String[] activeItems)
    {
        mActiveItems = activeItems;
    }

    public String[] getActiveItems()
    {
        return mActiveItems;
    }
    
    public Set getCheckedActiveItems()
    {
        return getDifference(mActiveItems, mOriginalActiveItems);
    }

    public Set getUncheckedActiveItems()
    {
        return getDifference(mOriginalActiveItems, mActiveItems);
    }

    public void setOriginalAdminItems(String[] originalAdminItems)
    {
        mOriginalAdminItems = originalAdminItems;
    }

    public String[] getOriginalAdminItems()
    {
        return mOriginalAdminItems;
    }

    public void setAdminItems(String[] adminItems)
    {
        mAdminItems = adminItems;
    }

    public String[] getAdminItems()
    {
        return mAdminItems;
    }

    public Set getCheckedAdminItems()
    {
        return getDifference(mAdminItems, mOriginalAdminItems);
    }

    public Set getUncheckedAdminItems()
    {
        return getDifference(mOriginalAdminItems, mAdminItems);
    }

    private Set getDifference(String[] list1, String[] list2)
    {
        Set set1 = new HashSet(Arrays.asList(list1));
        Set set2 = new HashSet(Arrays.asList(list2));
        set1.removeAll(set2);
        return set1;
    }

    /**
     *
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

    private String[] mItems;
    private String[] mItemsToDelete;
    private String[] mOriginalActiveItems;
    private String[] mActiveItems;
    private String[] mOriginalAdminItems;
    private String[] mAdminItems;
}
