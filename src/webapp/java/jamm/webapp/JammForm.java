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

package jamm.webapp;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.struts.action.ActionForm;

/**
 * Provides utility functions that man Jamm Forms may need.
 */
public abstract class JammForm extends ActionForm
{
    /**
     * Returns the items in list1 that are not in list 2.
     *
     * @param list1 a String array
     * @param list2 a String array
     *
     * @return a Set of the items in list1 not in list2
     */
    protected Set getDifference(String[] list1, String[] list2)
    {
        Set set1 = new HashSet(Arrays.asList(list1));
        Set set2 = new HashSet(Arrays.asList(list2));
        set1.removeAll(set2);
        return set1;
    }
}
