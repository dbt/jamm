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

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
/**
 * Bean which holds the information from an HTML form that calls the
 * AddAliasAction. Also, contains methods to validate the information
 * as well as reset/clear the form.
 *
 * @see jamm.webapp.AddAliasAction
 */
public class AddAliasForm extends ActionForm
{
    /**
     * Creates an AddAliasForm object.
     */
    public AddAliasForm()
    {
        mAddresses = new ArrayList();
    }

    /**
     * Sets the domain of this bean to the domain contained in the string.
     *
     * @param domain a string containing the domain name.
     */
    public void setDomain(String domain)
    {
        mDomain = domain;
    }

    /**
     * Returns the domain contained within this bean.
     *
     * @return a string containtaining the domain name.
     */
    public String getDomain()
    {
        return mDomain;
    }

    /**
     * Sets the name this alias will use.
     *
     * @param name a string containing the alias name
     */
    public void setName(String name)
    {
        mName = name;
    }

    /**
     * Returns the name of the alias this form represents.
     *
     * @return a string containing the alias name.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Set the destinations for this alias.  It takes in a string of
     * whitespace seperated addresses.
     *
     * @param destinations a whitespace seperated list of e-mail addresses.
     */
    public void setDestinations(String destinations)
    {
        mDestinations = destinations;
        StringTokenizer tokenizer = new StringTokenizer(mDestinations,
                                                        " \t\n\r\f,");
        mAddresses.clear();
        while (tokenizer.hasMoreTokens())
        {
            mAddresses.add(tokenizer.nextToken());
        }
    }

    /**
     * Returns what was passed in as the destination string.
     *
     * @return a string containing a whitespace seperated list of e-mail
     *         addresses.
     */
    public String getDestinations()
    {
        return mDestinations;
    }

    /**
     * Returns the destinates as a List.
     *
     * @return a List containing the destination addresses
     */
    public List getDestinationAddresses()
    {
        return mAddresses;
    }

    /**
     * Sets the password associated with this alias.
     *
     * @param password a string containing the password for this alias.
     */
    public void setPassword(String password)
    {
        mPassword = password;
    }

    /**
     * Returns the password set for this alias.
     *
     * @return a string containing the password.
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets the retyped password, used in confirming password.
     *
     * @param retypedPassword a string containing the retyped password.
     */
    public void setRetypedPassword(String retypedPassword)
    {
        mRetypedPassword = retypedPassword;
    }

    /**
     * Returns the retyped password.
     *
     * @return a string containing a retyped password.
     */
    public String getRetypedPassword()
    {
        return mRetypedPassword;
    }

    /**
     * The password is empty if both passwords are null or the empty
     * string.
     *
     * @return returns true if the password is empty, false otherwise
     */
    public boolean isPasswordEmpty()
    {
        return (((mPassword == null) && (mRetypedPassword == null)) ||
                ((mPassword.equals("") && mRetypedPassword.equals(""))));
    }

    /**
     * Resets the form to the default setting.  Clears out all the stored
     * addresses, names, destinaions and passwords.  Sets the domain to
     * that passed in with the request.
     *
     * @param mapping The mapping used to select this instance.
     * @param request The servlet request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mDomain = request.getParameter("domain");
        mName = null;
        mDestinations = null;
        mAddresses.clear();
        clearPasswords();
    }

    /**
     * Validates the destination addresses and password.
     *
     * @param mapping The action mapping.
     * @param request The servlet request.
     * @return Any errors.
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        if (mAddresses.size() == 0)
        {
            errors.add("destinations",
                       new ActionError("alias.error.non_zero_aliases"));
        }

        if ((mName == null) || mName.equals(""))
        {
            errors.add("name",
                       new ActionError("add_alias.error.no_name"));
        }

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
     * Resets the password to null.
     */
    private void clearPasswords()
    {
        mPassword = null;
        mRetypedPassword = null;
    }

    /** The domain */
    private String mDomain;
    /** The alias name */
    private String mName;
    /** The string of destinations */
    private String mDestinations;
    /** The destinations as a List */
    private List mAddresses;
    /** The password */
    private String mPassword;
    /** The retyped password */
    private String mRetypedPassword;
}
