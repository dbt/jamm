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

import java.util.Iterator;

/**
 * A wrapper around an iterator of <code>CaseInsensitiveString</code>
 * objects that converts the objects back to normal
 * <code>String</code> objects as the items are retrieved.  This
 * iterator will not work unless every item is a
 * <code>CaseInsensitiveString</code>
 *
 * @see CaseInsensitiveString
 * @see String
 */
public class CaseInsensitiveStringIterator implements Iterator
{
    /**
     * Creates a new iterator from an existing iterator.
     *
     * @param iterator Iterator to wrap.
     */
    public CaseInsensitiveStringIterator(Iterator iterator)
    {
        mIterator = iterator;
    }

    /**
     * Checks if there are more items in this iterator.
     *
     * @return True if there are more items.
     */
    public boolean hasNext()
    {
        return mIterator.hasNext();
    }

    /**
     * Returns the current item, as a <code>String</code>.  The item
     * is really a <code>CaseInsensitiveString</code> object, but gets
     * converted automatically.
     *
     * @return The current item, as a <code>String</code>.
     */
    public Object next()
    {
        CaseInsensitiveString ciString;
        ciString = (CaseInsensitiveString) mIterator.next();
        return ciString.toString();
    }

    /**
     * Removes the current item.
     */
    public void remove()
    {
        mIterator.remove();
    }

    /** The wrapped iterator. */
    private Iterator mIterator;
}
