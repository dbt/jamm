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
