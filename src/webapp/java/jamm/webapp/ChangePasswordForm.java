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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;


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
