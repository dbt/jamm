package jamm.webapp;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;


/**
 * Holds the check boxes for the account list of the domain admin
 * form.  This is a list of check boxes for Delete, Action, and Admin.
 */
public class DomainAccountForm extends ActionForm
{
    public void setAccountsToDelete(String[] accountsToDelete)
    {
        mAccountsToDelete = accountsToDelete;
    }

    public String[] getAccountsToDelete()
    {
        return mAccountsToDelete;
    }

    public void setAccountsToActivate(String[] accountsToActivate)
    {
        mAccountsToActivate = accountsToActivate;
    }

    public String[] getAccountsToActivate()
    {
        return mAccountsToActivate;
    }

    public void setAccountsToAdmin(String[] accountsToAdmin)
    {
        mAccountsToAdmin = accountsToAdmin;
    }

    public String[] getAccountsToAdmin()
    {
        return mAccountsToAdmin;
    }

    public String[] getOriginalAccounts()
    {
        return mAccounts;
    }

    public void setOriginalAccounts(String[] accounts)
    {
        mAccounts = accounts;
    }

    public Set getUnchecked()
    {
        Set original = new HashSet(Arrays.asList(mAccounts));
        Set changed = new HashSet(Arrays.asList(mAccountsToActivate));
        original.removeAll(changed);
        return original;
    }

    public Set getChecked()
    {
        Set original = new HashSet(Arrays.asList(mAccounts));
        Set changed = new HashSet(Arrays.asList(mAccountsToActivate));
        changed.removeAll(original);
        return changed;
    }

    /**
     *
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mAccounts = new String[0];
        mAccountsToDelete = new String[0];
        mAccountsToActivate = new String[0];
        mAccountsToAdmin = new String[0];
    }

    private String[] mAccountsToDelete;
    private String[] mAccountsToActivate;
    private String[] mAccountsToAdmin;
    private String[] mAccounts;
}
