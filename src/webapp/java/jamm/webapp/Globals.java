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

public final class Globals
{
    public static void setLdapHost(String ldapHost)
    {
        mLdapHost = ldapHost;
    }

    public static String getLdapHost()
    {
        return mLdapHost;
    }

    public static void setLdapPort(int ldapPort)
    {
        mLdapPort = ldapPort;
    }

    public static int getLdapPort()
    {
        return mLdapPort;
    }

    public static void setLdapSearchBase(String ldapSearchBase)
    {
        mLdapSearchBase = ldapSearchBase;
    }

    public static String getLdapSearchBase()
    {
        return mLdapSearchBase;
    }

    public static void setLdapQueryFilter(String ldapQueryFilter)
    {
        mLdapQueryFormat = new MessageFormat(ldapQueryFilter);
    }

    public static String getLdapQueryFilter(String address)
    {
        Object[] args = {address};

        return mLdapQueryFormat.format(args);
    }

    public static String getRootDn()
    {
        return mRootDn;
    }

    public static void setRootDn(String rootDn)
    {
        mRootDn = rootDn;
    }

    private static String mLdapHost;
    private static int mLdapPort;
    private static String mLdapSearchBase;
    private static MessageFormat mLdapQueryFormat;
    private static String mRootDn;
}
