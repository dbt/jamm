package jamm.webapp;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class AddDomainForm extends ActionForm
{
    public void setDomain(String domain)
    {
        mDomain = domain;
    }

    public String getDomain()
    {
        return mDomain;
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

    /**
     * The password is empty if both passwords are null or the empty
     * string.
     */
    public boolean isPasswordEmpty()
    {
        return (((mPassword == null) && (mRetypedPassword == null)) ||
                ((mPassword.equals("") && mRetypedPassword.equals(""))));
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mDomain = null;
        clearPasswords();
    }

    /**
     * Both passwords must match and must pass certain "bad password"
     * smoke tests.  For now, only the length of the password is
     * considered, but more elaborate tests such as dictionary tests
     * could be performed.
     *
     * @param mapping The action mapping.
     * @param request The servlet request.
     * @return Any errors.
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        if (!isPasswordEmpty())
        {
            if (!PasswordValidator.validatePassword(mPassword,
                                                    mRetypedPassword, errors))
            {
                clearPasswords();
            }
        }

        return errors;
    }

    private void clearPasswords()
    {
        mPassword = null;
        mRetypedPassword = null;
    }

    private String mDomain;
    private String mPassword;
    private String mRetypedPassword;
}
