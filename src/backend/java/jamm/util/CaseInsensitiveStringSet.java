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

import java.util.HashSet;
import java.util.Iterator;

/**
 * A hash set containing strings, ignoring the case, but preserving
 * the case, of strings as they are added.  Thus the following code is
 * valid:
 *
 * <pre>
 *     Set set = new CaseInsensitiveSet();
 *     set.add("NewSet");
 *     set.add("newSet") == false;
 *
 *     set.contains("NewSet") == true;
 *     set.contains("newset") == true;
 *     set.contains("NEWSET") == true;
 * </pre>
 */
public class CaseInsensitiveStringSet extends HashSet
{
    /**
     * Adds a string to this set.
     *
     * @param object Object to add.
     * @return False if this object has already been added.
     */
    public boolean add(Object object)
    {
        String string = (String) object;
        return super.add(new CaseInsensitiveString(string));
    }

    /**
     * Returns true if set contains the string, ignoring the case of
     * the target string.
     *
     * @param object String to check for
     * @return True if the set contais the string
     */
    public boolean contains(Object object)
    {
        String string = (String) object;
        return super.contains(new CaseInsensitiveString(string));
    }

    /**
     * Removes a string from the set, ignore the case.
     *
     * @param object String to remove
     * @return True if the remove succeeded, otherwise false.
     */
    public boolean remove(Object object)
    {
        String string  = (String) object;
        return super.remove(new CaseInsensitiveString(string));
    }

    /**
     * Returns an iterator over the strings in this set.  Each string
     * preserves the case as when they were inserted.
     *
     * @return An iterator for this set.
     */
    public Iterator iterator()
    {
        return new CaseInsensitiveStringIterator(super.iterator());
    }
}
