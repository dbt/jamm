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
 * Bean which holds the information from an HTML form that calls the
 * AddDomainAction. Also, contains methods to validate the information
 * as well as reset/clear the form.
 *
 * @see jamm.webapp.AddDomainAction
 */
public class AddDomainForm extends ActionForm
{
    /**
     * Sets the domain.
     *
     * @param domain a string containing the domain name.
     */
    public void setDomain(String domain)
    {
        mDomain = domain;
    }

    /**
     * Returns the domain name.
     *
     * @return a string containing the domain name.
     */
    public String getDomain()
    {
        return mDomain;
    }

    /**
     * Sets the password.
     *
     * @param password a string containing the password.
     */
    public void setPassword(String password)
    {
        mPassword = password;
    }

    /**
     * Returns the password.
     *
     * @return a string containing the password.
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets the retyped password.
     *
     * @param retypedPassword a string containing the retyped password
     */
    public void setRetypedPassword(String retypedPassword)
    {
        mRetypedPassword = retypedPassword;
    }

    /**
     * Returns the retyped password.
     *
     * @return a string containing the retyped password.
     */
    public String getRetypedPassword()
    {
        return mRetypedPassword;
    }

    /**
     * The password is empty if both passwords are null or the empty
     * string.
     *
     * @return true when emtpy, false otherwise
     */
    public boolean isPasswordEmpty()
    {
        return (((mPassword == null) && (mRetypedPassword == null)) ||
                ((mPassword.equals("") && mRetypedPassword.equals(""))));
    }

    /**
     * Resets the form.  Sets the domain name to null and clears the
     * passwords.
     * 
     * @param mapping The mapping of possible destinations.
     * @param request The http request that created this form.
     */
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

    /**
     * Clears both passwords by setting them to null.
     */
    private void clearPasswords()
    {
        mPassword = null;
        mRetypedPassword = null;
    }

    /** The domain name */
    private String mDomain;
    /** The password */
    private String mPassword;
    /** The retyped password */
    private String mRetypedPassword;
}
