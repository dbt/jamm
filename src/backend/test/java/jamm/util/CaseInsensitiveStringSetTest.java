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

import java.util.Set;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 * Tests the {@link CaseInsensitiveStringSet} class.
 */
public class CaseInsensitiveStringSetTest extends TestCase
{
    /**
     * Standard JUnit constructor.
     *
     * @param name Name.
     */
    public CaseInsensitiveStringSetTest(String name)
    {
        super(name);
    }

    /**
     * Creates a default set.
     */
    protected void setUp()
    {
        mSet = new CaseInsensitiveStringSet();
    }

    /**
     * Tests adding items to the set.
     */
    public void testAdd()
    {
        assertTrue("Checking add succeeds", mSet.add("apple"));
        assertTrue("Checking add fails", !mSet.add("APPLE"));
    }

    /**
     * tests removing items from the set.
     */
    public void testRemove()
    {
        mSet.add("MANGO");
        mSet.add("apple");
        mSet.add("Orange");

        assertTrue("Checking remove succeeds", mSet.remove("APPLE"));
        assertTrue("Checking remove succeeds", mSet.remove("mango"));
        assertTrue("Checking remove succeeds", mSet.remove("orange"));
        assertTrue("Checking remove fails", !mSet.remove("apple"));
    }

    /**
     * Tests checking for items.
     */
    public void testContains()
    {
        mSet.add("string");
        assertTrue("Checking lower case", mSet.contains("string"));
        assertTrue("Checking upper case", mSet.contains("STRING"));
        assertTrue("Checking mixed case", mSet.contains("StRiNg"));
    }

    /**
     * Tests the iterator by converting to an array.
     */
    public void testToArray()
    {
        mSet.add("MANGO");
        mSet.add("apple");
        mSet.add("Orange");
        mSet.add("APPLE");

        String[] fruits = (String[]) mSet.toArray(new String[0]);
        assertEquals("Checking array length", 3, fruits.length);
        Arrays.sort(fruits, String.CASE_INSENSITIVE_ORDER);
        assertEquals("Checking element 0", "apple", fruits[0]);
        assertEquals("Checking element 1", "MANGO", fruits[1]);
        assertEquals("Checking element 2", "Orange", fruits[2]);
    }

    /** The default set. */
    private Set mSet;
}
