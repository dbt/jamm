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

import java.util.Random;

import cryptix.tools.UnixCrypt;

/**
 * Hashes a password using the Unix crypt(3) algorithm.  This uses the
 * Cryptix Cryptogrpahy Toolkit to do the algorithm, however, the salt
 * must be done here.
 *
 * @see <a href="http://www.cryptix.org/">Cryptix Cryptography Toolkit</a>
 */
public class CryptPassword extends LdapPassword
{
    /**
     * Crypts clear text using a given salt.
     *
     * @param salt Salt to use
     * @param clearText The clear text to crypt
     * @return Crypted clear text
     */
    public String crypt(String salt, String clearText)
    {
        UnixCrypt   crypt;

        crypt = new UnixCrypt(salt);
        return crypt.crypt(clearText);
    }

    /**
     * Creates a Unix compatible salt.  A salt is a two-character
     * string chosen from the set [a-zA-Z0-9./].
     *
     * @return A random salt
     */
    public String getRandomSalt()
    {
        char[] saltList = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '/',
        };
        char[] salt;
        Random random;

        random = createRandom();
        salt = new char[2];
        salt[0] = saltList[ random.nextInt(saltList.length) ];
        salt[1] = saltList[ random.nextInt(saltList.length) ];

        return new String(salt);
    }

    /**
     * Hashes a password using the Unix crypt(3) algorithm, random
     * salt and all.
     *
     * @param password Password to hash
     * @return Crypt version of password
     */
    protected String doHash(String password)
    {
        return "{CRYPT}" + crypt(getRandomSalt(), password);
    }

    /**
     * Not implemented, always returns <code>false</code>.
     *
     * @return <code>false</code>
     */
    protected boolean doCheck(String hashedPasword, String password)
    {
        return false;
    }
}
