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

/**
 * Utility routines for Internet-style "user@example.com" email
 * addresses.
 */
public class MailAddress
{
    /**
     * Returns the host part of a user@host email address.  If it
     * can't find the first '@', it can't figure out the host, so it
     * returns null.
     *
     * @param address Internet-style email address
     * @return The host name, or null
     */
    public static final String hostFromAddress(String address)
    {
        int separator = address.indexOf("@");

        if (separator == -1)
        {
            return null;
        }
        
        return address.substring(separator + 1);
    }

    /**
     * Returns the user part of a user@host email address.  If it can't
     * find the first '@', it assumes the whole string is the user.
     *
     * @param address Internet-style email address
     * @return The user name
     */
    public static final String userFromAddress(String address)
    {
        int separator = address.indexOf("@");

        if (separator == -1)
        {
            return address;
        }
        
        return address.substring(0, separator);
    }

    /**
     * Constructs an Internet-style email address from a user and host
     * name.
     *
     * @param user User name
     * @param host Host name
     * @return Internet-style email address
     */
    public static final String addressFromParts(String user, String host)
    {
        return new StringBuffer(user).append("@").append(host).toString();
    }
}
