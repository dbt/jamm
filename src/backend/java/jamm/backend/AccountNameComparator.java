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
public class AccountNameComparator implements Comparator
{
    /**
     * Compares two <code>AccountInfo</code> objects by their name,
     * ignoring case.
     *
     * @param o1 First object to compare.  Must be an
     * <code>AccountInfo</code> object
     * @param o2 Second object to compare.  Must be an
     * <code>AccountInfo</code> object
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second
     * @see AccountInfo
     */
    public int compare(Object o1, Object o2)
    {
        AccountInfo account1 = (AccountInfo) o1;
        AccountInfo account2 = (AccountInfo) o2;
        return account1.getName().compareToIgnoreCase(account2.getName());
    }
}
