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
