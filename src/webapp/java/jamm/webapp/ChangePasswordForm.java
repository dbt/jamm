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
 *
 * @see jamm.webapp.ChangePasswordAction
 */
public class ChangePasswordForm extends ActionForm
{
    /**
     * Sets the mail address of the user to change the password for.
     *
     * @param mail The mail address.
     */
    public void setMail(String mail)
    {
        mMail = mail;
    }

    /**
     * Returns the mail address of the user to change the password
     * for.
     *
     * @return a string containing the mail address.
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
     * @return a string containing the first password.
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
     * @return a string containing the second password.
     */
    public String getRetypedPassword()
    {
        return mRetypedPassword;
    }

    /**
     * The logical name of the page to forward to after the action is
     * done.
     *
     * @param done Logical forward name
     */
    public void setDone(String done)
    {
        mDone = done;
    }

    /**
     * Returns the logical name of the page to forward to after the
     * action is done.
     *
     * @return Logical forward name.
     */
    public String getDone()
    {
        return mDone;
    }

    public void setClear(String clear)
    {
        mClear = clear;
    }

    public String getClear()
    {
        return mClear;
    }

    public boolean wasClearClicked()
    {
        return (!mClear.equals(""));
    }

    /**
     * Clears out both passwords and sets the Mail to the parameter
     * passed in via the request.
     *
     * @param mapping The mapping used to select this instance.
     * @param request The servlet request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        clearPasswords();
        mMail = request.getParameter("mail");
        mDone = request.getParameter("done");
        mClear = "";
    }

    /**
     * Validates the passwords by calling the validatePassword method
     * of the PasswordValidator.
     *
     * @see jamm.webapp.PasswordValidator#validatePassword
     *
     * @param mapping The mapping used to select this instance.
     * @param request The servlet request.
     * @return Any errors.
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        // If clear password was clicked, then the rest of the fields
        // don't matter.
        if (wasClearClicked())
        {
            return errors;
        }

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

    /** The name of the page to forward to when done with the action. */
    private String mDone;

    private String mClear;
}
