package jamm.webapp;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class AddAccountForm extends ActionForm
{
    public void setDomain(String domain)
    {
        mDomain = domain;
    }

    public String getDomain()
    {
        return mDomain;
    }
    
    public void setName(String name)
    {
        mName = name;
    }

    public String getName()
    {
        return mName;
    }

    public void setPassword(String password)
    {
        mPassword = password;
    }

    public String getPassword()
    {
        return mPassword;
    }

    public void setRetypedPassword(String retypedPassword)
    {
        mRetypedPassword = retypedPassword;
    }

    public String getRetypedPassword()
    {
        return mRetypedPassword;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mDomain = request.getParameter("domain");
        mName = null;
        clearPasswords();
    }

    /**
     * Validates the password.
     *
     * @param mapping The action mapping.
     * @param request The servlet request.
     * @return Any errors.
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        if ((mName == null) || mName.equals(""))
        {
            errors.add("name",
                       new ActionError("add_account.error.no_name"));
        }

        if (!PasswordValidator.validatePassword(mPassword, mRetypedPassword,
                                                errors))
        {
            clearPasswords();
        }

        return errors;
    }

    private void clearPasswords()
    {
        mPassword = null;
        mRetypedPassword = null;
    }
    
    private String mDomain;
    private String mName;
    private String mPassword;
    private String mRetypedPassword;
}
