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

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;

public class AliasInfo implements Serializable
{
    public AliasInfo(String name, List destinations, boolean active,
                       boolean administrator)
    {
        mName = name;
        mDestinations = destinations;
        mActive = active;
        mAdministrator = administrator;
    }

    public String getName()
    {
        return mName;
    }

    public void setDestinations(Collection destinations)
    {
        mDestinations = new ArrayList(destinations);
    }

    public void setDestinations(String[] destinations)
    {
        mDestinations = Arrays.asList(destinations);
    }

    public List getDestinations()
    {
        return Collections.unmodifiableList(mDestinations);
    }

    public boolean isActive()
    {
        return mActive;
    }

    public boolean isAdministrator()
    {
        return mAdministrator;
    }

    private String mName;
    private List mDestinations;
    private boolean mActive;
    private boolean mAdministrator;
}
