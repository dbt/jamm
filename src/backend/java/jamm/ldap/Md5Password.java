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

package jamm.ldap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashes a password using the MD5 algorithm.
 */
public class Md5Password extends LdapPassword
{
    /**
     * Generates the raw 128-bit MD5 hash of the clear text.  Many
     * applications use the Base-64 encoded version of the MD5.
     *
     * @return A 16-byte array representing the MD5 hash of the clear
     * text.
     */
    public byte[] md5(String clearText)
    {
        MessageDigest   md;
        byte[] digest;

        try
        {
            md = MessageDigest.getInstance("MD5");
            md.update(clearText.getBytes());
            digest = md.digest();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new UnsupportedOperationException(e.toString());
        }

        return digest;
    }

    /**
     * Creates a hashed MD5 password in standard LDAP format.
     *
     * @param password Clear text password
     * @return MD5-hashed LDAP password
     */
    protected String doHash(String password)
    {
        return "{MD5}" + encodeBase64(md5(password));
    }

    /**
     * Not imlemented yet.
     *
     * @return Always returns <code>false</code>
     */
    protected boolean doCheck(String hashedpassword, String password)
    {
        return false;
    }
}
