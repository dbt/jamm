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

import junit.framework.TestCase;

/**
 * Unit test for the {@link MailAddress} class.
 */
public class MailAddressTest extends TestCase
{
    /**
     * Standard JUnit constructor.
     *
     * @param name name of the test
     */
    public MailAddressTest(String name)
    {
        super(name);
    }

    /**
     * Tests getting host name from an address.
     */
    public void testHostFromAddress()
    {
        assertEquals("testing with user@host address",
                     "realtors.org",
                     MailAddress.hostFromAddress(USER_AT_HOST));

        assertNull("testing with user address only",
                   MailAddress.hostFromAddress(USER_ONLY));
    }

    /**
     * Tests getting user from an address.
     */
    public void testUserFromAddress()
    {
        assertEquals("testing with user@host address",
                     "kgarner",
                     MailAddress.userFromAddress(USER_AT_HOST));

        assertEquals("testing with user address only",
                     "root",
                     MailAddress.userFromAddress(USER_ONLY));
    }
    
    /** Sample valid email address */
    private static final String USER_AT_HOST = "kgarner@realtors.org";
    /** Sample valid address that is the user only */
    private static final String USER_ONLY = "root";
}
