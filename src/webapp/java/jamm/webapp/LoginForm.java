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

/**
 * Bean to hold the data for the LoginAction.
 *
 * @see jamm.webapp.LoginAction
 */
public class LoginForm extends ActionForm
{
    /**
     * Sets the username
     *
     * @param username a string containing the username
     */
    public void setUsername(String username)
    {
        mUsername = username;
    }

    /**
     * Returns the username
     *
     * @return a string containing the username
     */
    public String getUsername()
    {
        return mUsername;
    }

    /**
     * Sets the password
     *
     * @param password a string containing the password
     */
    public void setPassword(String password)
    {
        mPassword = password;
    }

    /**
     * Returns the password.
     *
     * @return a string containing the password
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets the location to go do after authentication is done.
     *
     * @param done a string containing the "done" location.
     */
    public void setDone(String done)
    {
        mDone = done;
    }

    /**
     * Returns the location to go do after authentication is done.
     *
     * @return a string containing the "done" location
     */
    public String getDone()
    {
        return mDone;
    }

    /**
     * Resets the form to its default state.  In this case, sets all
     * the variables to null.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mUsername = null;
        mPassword = null;
        mDone = null;
    }

    private String mUsername;
    private String mPassword;
    private String mDone;
}
