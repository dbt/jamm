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

public class SaltedShaPassword extends ShaPassword
{
    protected String doHash(String password)
    {
        byte[] randomSalt;
        byte[] digest;

        randomSalt = getRandomSalt();
        digest = sha(append(password, randomSalt));

        return "{SSHA}" + encodeBase64(append(digest, randomSalt));
    }

    protected boolean doCheck(String hashedPassword, String password)
    {
        return false;
    }

    private static byte[] getRandomSalt()
    {
        Random random;
        byte[] salt;

        random = createRandom();
        salt = new byte[4];
        random.nextBytes(salt);
        return salt;
    }
}
