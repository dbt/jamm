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

/**
 * Unit test for the {@link ShaPassword} class.
 */
public class ShaPasswordTest extends TestCase
{
    /**
     * Standard JUnit constructor.
     */
    public ShaPasswordTest(String name)
    {
        super(name);
    }

    /**
     * Tests an SHA hash.
     */
    public void testSha()
    {
        ShaPassword sha;
        String hash;

        sha = new ShaPassword();
        hash = sha.doHash("test");
        assertEquals("Checking SHA hash of \"test\"",
                     "{SHA}qUqP5cyxm6YcTAhz05Hph5gvu9M=", hash);
    }
}
