package jamm.webapp;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;


/**
 * Holds both new passwords the user typed.  This bean makes sure that
 * the passwords match and that the passwords don't suck.  For now, it
 * just checks the length of the password.
 */
public class ChangePasswordForm extends ActionForm
{
    /**
     * Sets the first password.
     *
     * @param password1 The first password.
     */
    public void setPassword1(String password1)
    {
        mPassword1 = password1;
    }

    /**
     * Returns the first password.
     *
     * @return The first password.
     */
    public String getPassword1()
    {
        return mPassword1;
    }

    /**
     * Sets the second password.
     *
     * @param password2 The second password.
     */
    public void setPassword2(String password2)
    {
        mPassword2 = password2;
    }

    /**
     * Returns the second password.
     *
     * @return The second password.
     */
    public String getPassword2()
    {
        return mPassword2;
    }

    /**
     * Clears out both passwords.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
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
        if ((mPassword1 != null) && (!mPassword1.equals(mPassword2)))
        {
            errors.add("password1",
                       new ActionError("change_password.error.no_match"));
            clearPasswords();
        }
        else if ((mPassword1 == null) ||
                 (mPassword1.length() < MINIMUM_LENGTH))
        {
            errors.add("password1",
                       new ActionError("change_password.error.too_short"));
            clearPasswords();
        }

        return errors;
    }

    /**
     * Sets both passwords to null.
     */
    private void clearPasswords()
    {
        mPassword1 = null;
        mPassword2 = null;
    }

    /** The first password. */
    private String mPassword1;

    /** The second password. */
    private String mPassword2;

    /** The minimum length of the password. */
    private static final int MINIMUM_LENGTH = 5;
}
