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

import junit.framework.TestCase;
import java.util.Arrays;

public class Md5PasswordTest extends TestCase
{
    public Md5PasswordTest(String name)
    {
        super(name);
    }

    public void testMd5()
    {
        Md5Password md5;
        byte[] digest;
        String hash;
        byte[] expectedDigest = {
            (byte) 0x09, (byte) 0x8F, (byte) 0x6B, (byte) 0xCD,
            (byte) 0x46, (byte) 0x21, (byte) 0xD3, (byte) 0x73,
            (byte) 0xCA, (byte) 0xDE, (byte) 0x4E, (byte) 0x83,
            (byte) 0x26, (byte) 0x27, (byte) 0xB4, (byte) 0xF6};

        md5 = new Md5Password();
        digest = md5.md5("test");
        assertTrue("Checking MD5 digest of \"test\"",
                   Arrays.equals(expectedDigest, digest));
        
        hash = md5.doHash("test");
        assertEquals("Checking MD5 hash of \"test\"",
                     "{MD5}CY9rzUYh03PK3k6DJie09g==", hash);
    }
}
