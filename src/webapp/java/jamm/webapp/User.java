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

import java.util.Set;
import java.util.Collections;
import java.io.Serializable;

/**
 * An immutable bean to hold user information.
 */
public class User implements Serializable
{
    /**
     * Constructs an "empty" user.  An "empty" user contains no real
     * information, just dummy data.
     */
    public User()
    {
        this("", "", "", Collections.EMPTY_SET);
    }

    /**
     * Constructs a new user.
     *
     * @param username The username.
     * @param dn The distinguished name.
     * @param password The password.
     * @param roles a <code>Set</code> containing the roles the user is in
     */
    public User(String username, String dn, String password, Set roles)
    {
        mUsername = username;
        mDn = dn;
        mPassword = password;
        mRoles = roles;
    }

    /**
     * Checks to see if this user is an "empty" user.
     *
     * @return true when user is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return (mUsername.equals(""));
    }

    /**
     * Returns this user's username.
     *
     * @return A username
     */
    public String getUsername()
    {
        return mUsername;
    }

    /**
     * Returns this users's LDAP distinguished name.
     *
     * @return An LDAP DN
     */
    public String getDn()
    {
        return mDn;
    }

    /**
     * Returns this user's password.
     *
     * @return A password
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets this user's password.
     *
     * @param password New password
     */
    public void setPassword(String password)
    {
        mPassword = password;
    }

    /**
     * Verifies if user is in a role.
     *
     * @param role Role to check for.
     * @return <code>true</code> if user is in the role.
     */
    public boolean isUserInRole(String role)
    {
        return mRoles.contains(role);
    }

    /** String for domain administrator. */
    public static final String DOMAIN_ADMIN_ROLE = "Domain Administrator";

    /** String for site administrator. */
    public static final String SITE_ADMIN_ROLE = "Site Administrator";

    /**  The username. */
    private String mUsername;

    /** The distinguished name. */
    private String mDn;

    /** The password. */
    private String mPassword;

    /** The roles a user is in */
    private Set mRoles;
}
