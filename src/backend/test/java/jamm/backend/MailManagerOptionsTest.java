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

import jamm.backend.MailManagerOptions;

/**
 * Unit test for the {@link MailManagerOptions} class.
 */
public class MailManagerOptionsTest extends TestCase
{
    /**
     * Creates a new <code>MailManagerOptionsTest</code> instance.
     *
     * @param name test name
     */
    public MailManagerOptionsTest(String name)
    {
        super(name);
    }

    public void testSetVmailHomedir()
    {
        String orig = MailManagerOptions.getVmailHomedir();
        String newv = "Test";

        MailManagerOptions.setVmailHomedir(newv);
        assertEquals("Checking for vmailHomedir",
                     newv, MailManagerOptions.getVmailHomedir());
        MailManagerOptions.setVmailHomedir(orig);
        assertEquals("Checking for vmailHomedir",
                     orig, MailManagerOptions.getVmailHomedir());
        
    }

    public void testGetVmailHomedir()
    {
        assertEquals("Checking for vmailHomedir",
                     "/home/vmail/domains",
                     MailManagerOptions.getVmailHomedir());
    }

    public void testSetUsePasswordExOp()
    {
        boolean orig = MailManagerOptions.isUsePasswordExOp();

        MailManagerOptions.setUsePasswordExOp(true);
        assertTrue("Checking isUsePasswordExOp",
                   MailManagerOptions.isUsePasswordExOp());

        MailManagerOptions.setUsePasswordExOp(false);
        assertTrue("Checking isUsePasswordExOp",
                   !MailManagerOptions.isUsePasswordExOp());

        MailManagerOptions.setUsePasswordExOp(orig);
    }

    public void testIsUsePasswordExOp()
    {
        assertTrue("Checking for isUsePasswordExOp",
                   MailManagerOptions.isUsePasswordExOp());
    }
        
}
