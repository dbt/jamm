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

package jamm.backend;

public class MailAddress
{
    /**
     * returns the host part of a user@host e-mail address.  If it can't
     * find the first '@', it can't figure out the host, so it returns null.
     */
    public static final String hostFromAddress(String address)
    {
        int separator = address.indexOf("@");

        if (separator == -1)
            return null;
        
        return address.substring(separator + 1);
    }

    /**
     * returns the user part of a user@host e-mail address.  If it can't
     * find the first '@', it assumes the whole string is the user.
     */
    public static final String userFromAddress(String address)
    {
        int separator = address.indexOf("@");

        if (separator == -1)
            return address;
        
        return address.substring(0, separator);
    }

    public static final String addressFromParts(String user, String host)
    {
        return new StringBuffer(user).append("@").append(host).toString();
    }

    public MailAddress(String address)
    {
        mUser = userFromAddress(address);
        mHost = hostFromAddress(address);
        mAddress = address;
    }

    public MailAddress(String user, String host)
    {
        mUser = user;
        mHost = host;
        mAddress = addressFromParts(user, host);
    }

    public String getUser()
    {
        return mUser;
    }

    public String getHost()
    {
        return mHost;
    }

    public String getAddress()
    {
        return mAddress;
    }

    public String toString()
    {
        return getAddress();
    }

    private String mUser;
    private String mHost;
    private String mAddress;
}
