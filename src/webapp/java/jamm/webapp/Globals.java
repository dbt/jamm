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

import java.text.MessageFormat;

/**
 * Bean to make certain objects globally accessable for ease of
 * access.
 */
public final class Globals
{
    /**
     * Sets the host to use to talk to the ldap server.
     *
     * @param ldapHost a string containing the ldap host.
     */
    public static void setLdapHost(String ldapHost)
    {
        mLdapHost = ldapHost;
    }

    /**
     * Returns the ldap host.
     *
     * @return a string returning the ldap host.
     */
    public static String getLdapHost()
    {
        return mLdapHost;
    }

    /**
     * Sets the port to use to talk to the ldap server.
     *
     * @param ldapPort an int representing the ldap port.
     */
    public static void setLdapPort(int ldapPort)
    {
        mLdapPort = ldapPort;
    }

    /**
     * Returns the ldap port.
     *
     * @return an int representing the ldap port.
     */
    public static int getLdapPort()
    {
        return mLdapPort;
    }

    /**
     * Set the search base for our LDAP interaction.
     *
     * @param ldapSearchBase a string containing the search base.
     */
    public static void setLdapSearchBase(String ldapSearchBase)
    {
        mLdapSearchBase = ldapSearchBase;
    }

    /**
     * Returns the ldap search base.
     *
     * @return a string containin the search base.
     */
    public static String getLdapSearchBase()
    {
        return mLdapSearchBase;
    }

    /**
     * Return the rootdn to use when logging in as "root".
     *
     * @return a string containing the root dn
     */
    public static String getRootDn()
    {
        return mRootDn;
    }

    /**
     * Sets the root dn to use when loggin in as "root".
     *
     * @param rootDn a string containing the root dn
     */
    public static void setRootDn(String rootDn)
    {
        mRootDn = rootDn;
    }

    /** The ldap host */
    private static String mLdapHost;
    /** the ldap port */
    private static int mLdapPort;
    /** the search base */
    private static String mLdapSearchBase;
    /** The root dn */
    private static String mRootDn;
}
