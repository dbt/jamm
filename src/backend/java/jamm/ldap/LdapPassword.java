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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import java.util.Map;
import java.util.HashMap;

import cryptix.util.mime.Base64OutputStream;

public abstract class LdapPassword
{
    public static String hash(PasswordScheme scheme, String password)
    {
        LdapPassword    method;

        if (mSchemeLookup == null)
        {
            initSchemeLookup();
        }
        
        method = (LdapPassword) mSchemeLookup.get(scheme);
        if (method == null)
        {
            method = new ClearPassword();
        }

        return method.doHash(password);
    }

    private static void initSchemeLookup()
    {
        mSchemeLookup = new HashMap();
        mSchemeLookup.put(PasswordScheme.CRYPT_SCHEME,
                          new CryptPassword());
        mSchemeLookup.put(PasswordScheme.MD5_SCHEME,
                          new Md5Password());
        mSchemeLookup.put(PasswordScheme.SHA_SCHEME,
                          new ShaPassword());
        mSchemeLookup.put(PasswordScheme.SSHA_SCHEME,
                          new SaltedShaPassword());
    }

    public static boolean check(String hashedPassword, String password)
    {
        return false;
    }

    public static void setRandomClass(String randomClass)
    {
        mRandomClass = randomClass;
    }

    protected static Random createRandom()
    {
        Random  random;

        try
        {
            random = (Random) Class.forName(mRandomClass).newInstance();
        }
        catch (ClassNotFoundException e)
        {
            random = new Random();
        }
        catch (InstantiationException e)
        {
            random = new Random();
        }
        catch (IllegalAccessException e)
        {
            random = new Random();
        }

        return random;
    }

    protected abstract String doHash(String password);

    protected abstract boolean doCheck(String hashedPassword, String password);

    protected String encodeBase64(byte[] clearText)
    {
        ByteArrayOutputStream   baos;
        Base64OutputStream      base64;
        String  encoded;

        try
        {
            baos = new ByteArrayOutputStream();
            base64 = new Base64OutputStream(baos);
            base64.write(clearText, 0, clearText.length);
            base64.close();

            // Must trim this string as a CR-LF is appended on the
            // end.
            encoded = new String(baos.toByteArray()).trim();
        }
        catch (IOException e)
        {
            throw new UnsupportedOperationException(e.toString());
        }
        
        return encoded;
    }

    protected final byte[] append(byte[] first, byte[] second)
    {
        byte[] appended;

        appended = new byte[first.length + second.length];
        System.arraycopy(first, 0, appended, 0, first.length);
        System.arraycopy(second, 0, appended, first.length, second.length);

        return appended;
    }
    
    protected final byte[] append(String first, byte[] second)
    {
        return append(first.getBytes(), second);
    }

    private static Map mSchemeLookup;
    private static String mRandomClass = "java.security.SecureRandom";
}
