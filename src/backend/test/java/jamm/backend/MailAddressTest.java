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

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import javax.naming.NamingException;

import junit.framework.TestCase;

import jamm.backend.MailAddress;

public class MailAddressTest extends TestCase
{
    public MailAddressTest(String name)
    {
        super(name);
    }

    public void testHostFromAddress()
    {
        assertEquals("testing with user@host address",
                     "realtors.org",
                     MailAddress.hostFromAddress(mUserAtHost));

        assertNull("testing with user address only",
                   MailAddress.hostFromAddress(mUserOnly));
    }

    public void testUserFromAddress()
    {
        assertEquals("testing with user@host address",
                     "kgarner",
                     MailAddress.userFromAddress(mUserAtHost));

        assertEquals("testing with user address only",
                     "root",
                     MailAddress.userFromAddress(mUserOnly));
    }
    
    String mUserAtHost = "kgarner@realtors.org";
    String mUserOnly = "root";
}
