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

package jamm.util;

import junit.framework.TestCase;

/**
 * Unit test for the {@link CaseInsensitiveString} class.
 */
public class CaseInsensitiveStringTest extends TestCase
{
    /**
     * Standard JUnit constructor.
     *
     * @param name Name.
     */
    public CaseInsensitiveStringTest(String name)
    {
        super(name);
    }

    /**
     * Tests all the <code>equals()</code> methods.
     */
    public void testEquals()
    {
        CaseInsensitiveString string1 = new CaseInsensitiveString("apple");
        assertTrue("Checking String equals", string1.equals("ApPlE"));

        CaseInsensitiveString string2 = new CaseInsensitiveString("ApPlE");
        assertTrue("Checking CIString equals", string1.equals(string2));

        Object o1 = string1;
        assertTrue("Checking Object equals String", o1.equals("ApPlE"));
        assertTrue("Checking Object equals CIString", o1.equals(string2));

        Object o2 = string2;
        assertTrue("Checking Object equals Object", o1.equals(o2));
    }

    /**
     * Tests the <code>toString()</code> method.
     */
    public void testToString()
    {
        CaseInsensitiveString string1 = new CaseInsensitiveString("apple");
        assertEquals("Checking toString", "apple", string1.toString());

        CaseInsensitiveString string2 = new CaseInsensitiveString("ApPlE");
        assertEquals("Checking toString", "ApPlE", string2.toString());
    }

    /**
     * Tests the <code>hashCode()</code> method.
     */
    public void testHashCode()
    {
        CaseInsensitiveString string1 = new CaseInsensitiveString("apple");
        CaseInsensitiveString string2 = new CaseInsensitiveString("ApPlE");

        assertEquals("Checking hashCode", string1.hashCode(),
                     string2.hashCode());
    }

    /**
     * Tests all the <code>testCompareTo()</code> methods.
     */
    public void testCompareTo()
    {
        CaseInsensitiveString ciString1 = new CaseInsensitiveString("apple");
        assertTrue("Checking CIString compareTo String > 0",
                   ciString1.compareTo("aaa") > 0);
        assertTrue("Checking CIString compareTo String == 0",
                   ciString1.compareTo("ApPlE") == 0);
        assertTrue("Checking CIString compareTo String < 0",
                   ciString1.compareTo("banana") < 0);

        CaseInsensitiveString ciString2 = new CaseInsensitiveString("aaa");
        CaseInsensitiveString ciString3 = new CaseInsensitiveString("ApPlE");
        CaseInsensitiveString ciString4 = new CaseInsensitiveString("banana");
        assertTrue("Checking CIString compareTo CIString > 0",
                   ciString1.compareTo(ciString2) > 0);
        assertTrue("Checking CIString compareTo CIString == 0",
                   ciString1.compareTo(ciString3) == 0);
        assertTrue("Checking CIString compareTo CIString < 0",
                   ciString1.compareTo(ciString4) < 0);

        Comparable c1 = ciString1;
        assertTrue("Checking Comparable compareTo String > 0",
                   c1.compareTo("aaa") > 0);
        assertTrue("Checking Comparable compareTo String == 0",
                   c1.compareTo("ApPlE") == 0);
        assertTrue("Checking Comparable compareTo String < 0",
                   c1.compareTo("banana") < 0);

        Comparable c2 = ciString2;
        Comparable c3 = ciString3;
        Comparable c4 = ciString4;
        assertTrue("Checking Comparable compareTo Comparable(CIString) > 0",
                   c1.compareTo(c2) > 0);
        assertTrue("Checking Comparable compareTo Comparable(CIString) == 0",
                   c1.compareTo(c3) == 0);
        assertTrue("Checking Comparable compareTo Comparable(CIString) < 0",
                   c1.compareTo(c4) < 0);
    }
}
