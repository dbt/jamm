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
     * Sets the mail address of the user to change the password for.
     *
     * @param The mail address.
     */
    public void setMail(String mail)
    {
        mMail = mail;
    }

    /**
     * Returns the mail address of the user to change the password
     * for.
     *
     * @return The mail address.
     */
    public String getMail()
    {
        return mMail;
    }

    /**
     * Sets the first password.
     *
     * @param password The first password.
     */
    public void setPassword(String password)
    {
        mPassword = password;
    }

    /**
     * Returns the first password.
     *
     * @return The first password.
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets the second password.
     *
     * @param retypedPassword The second password.
     */
    public void setRetypedPassword(String retypedPassword)
    {
        mRetypedPassword = retypedPassword;
    }

    /**
     * Returns the second password.
     *
     * @return The second password.
     */
    public String getRetypedPassword()
    {
        return mRetypedPassword;
    }

    /**
     * Clears out both passwords.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        clearPasswords();
        mMail = request.getParameter("mail");
    }

    /**
     *
     * @param mapping The action mapping.
     * @param request The servlet request.
     * @return Any errors.
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        if (!PasswordValidator.validatePassword(mPassword, mRetypedPassword,
                                                errors))
        {
            clearPasswords();
        }

        return errors;
    }

    /**
     * Sets both passwords to null.
     */
    private void clearPasswords()
    {
        mPassword = null;
        mRetypedPassword = null;
    }

    /** The mail address. */
    private String mMail;
    
    /** The first password. */
    private String mPassword;

    /** The second password. */
    private String mRetypedPassword;
}
