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

import java.util.Comparator;

/**
 * Compares account by their names, ignoring case.  Note: This is
 * inconsistent with equals().
 */
public class DomainNameComparator implements Comparator
{
    /**
     * Compares two <code>DomainInfo</code> objects by their name,
     * ignoring case.
     *
     * @param o1 First object to compare.  Must be an
     * <code>DomainInfo</code> object
     * @param o2 Second object to compare.  Must be an
     * <code>DomainInfo</code> object
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second
     * @see DomainInfo
     */
    public int compare(Object o1, Object o2)
    {
        DomainInfo domain1 = (DomainInfo) o1;
        DomainInfo domain2 = (DomainInfo) o2;
        return domain1.getName().compareToIgnoreCase(domain2.getName());
    }
}
