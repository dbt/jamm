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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * Bean which holds the information from an HTML form that calls the
 * AddAdminAction.  Also, contains methods to validate the information
 * as well as reset/clear the form.
 *
 * @see jamm.webapp.AddAccountAction
 */
public class AddAccountForm extends ActionForm
{
    /**
     * Sets the domain in this bean to the string passed in.
     *
     * @param domain The name of a domain.
     */
    public void setDomain(String domain)
    {
        mDomain = domain;
    }

    /**
     * Returns the name of the domain stored in the bean.
     *
     * @return a string containing the domain name.
     */
    public String getDomain()
    {
        return mDomain;
    }

    /**
     * Sets the account name in this bean to the string passed in.
     *
     * @param name The name of the account.
     */
    public void setName(String name)
    {
        mName = name;
    }

    /**
     * Returns the name of the account stored in this bean.
     *
     * @return a string containing the account name.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the password stored in this bean to the string passed in.
     *
     * @param password The intended password.
     */
    public void setPassword(String password)
    {
        mPassword = password;
    }

    /**
     * Returns the intended password that is stored in this bean.
     *
     * @return a string containing the password.
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets the "retyped" password in this bean.  Needed as in the HTML form,
     * the passwords aren't echoed.
     *
     * @param retypedPassword a string containing the second password try.
     */
    public void setRetypedPassword(String retypedPassword)
    {
        mRetypedPassword = retypedPassword;
    }

    /**
     * Returns the second attempt at spelling the password correctly.
     *
     * @return a string containing the second password try.
     */
    public String getRetypedPassword()
    {
        return mRetypedPassword;
    }

    /**
     * This resets the form to the default settings.  It sets the
     * domain to the domain name passed in via the reqest and sets all
     * the other parameters to null.
     *
     * @param mapping The mapping of possible destinations.
     * @param request The http request that created this form.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mDomain = request.getParameter("domain");
        mName = null;
        clearPasswords();
    }

    /**
     * Validates the password.  It does this by checking to make sure
     * the password isn't null or an empty string, and then calls a
     * method on PasswordValidator to do the validation.
     *
     * @see jamm.webapp.PasswordValidator#validatePassword
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

    /**
     * Resets the password stored in the bean to null.
     */
    private void clearPasswords()
    {
        mPassword = null;
        mRetypedPassword = null;
    }

    /** The domain. */
    private String mDomain;
    /** The account name. */
    private String mName;
    /** The password. */
    private String mPassword;
    /** The retyped password. */
    private String mRetypedPassword;
}
