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

/**
 * A bean that represents alias information.
 */
public class AliasInfo implements Serializable
{
    /**
     * Create an alias with the specified name, destniations, active,
     * and boolean flags.
     *
     * @param name Account name
     * @param destinations List of destinations.
     * @param active True if this account is active.
     * @param administrator True if this account has administrator
     * priveleges.
     */
    public AliasInfo(String name, List destinations, boolean active,
                     boolean administrator)
    {
        mName = name;
        mDestinations = destinations;
        mActive = active;
        mAdministrator = administrator;
    }

    /**
     * Returns this alias' name.
     *
     * @return This alias' name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the destinations to the specified collection, removing
     * previous destinations.
     *
     * @param destinations New destinations
     */
    public void setDestinations(Collection destinations)
    {
        mDestinations = new ArrayList(destinations);
    }

    /**
     * Sets the destinations to the specified array, removing previous
     * destinations.
     *
     * @param destinations New destinations
     */
    public void setDestinations(String[] destinations)
    {
        mDestinations = Arrays.asList(destinations);
    }

    /**
     * Returns a list of destinatinos.
     *
     * @return An unmodifialbe list of destinations as strings.
     */
    public List getDestinations()
    {
        return Collections.unmodifiableList(mDestinations);
    }

    /**
     * Returns true if this alias is active.
     *
     * @return True if this alias is active
     */
    public boolean isActive()
    {
        return mActive;
    }

    /**
     * Sets active to the value passed in.
     *
     * @param value boolean value to set active to.
     */
    public void setActive(boolean value)
    {
        mActive = value;
    }

    /**
     * Returns true if this alias is an administrator.
     *
     * @return True if this alias is an administrator.
     */
    public boolean isAdministrator()
    {
        return mAdministrator;
    }

    /**
     * Sets administator to boolean value passed in.
     *
     * @param value boolean value for administrator
     */
    public void setAdministrator(boolean value)
    {
        mAdministrator = value;
    }
    
    /** The alias name. */
    private String mName;
    /** The destinations. */
    private List mDestinations;
    /** True if active. */
    private boolean mActive;
    /** True if an administrator. */
    private boolean mAdministrator;
}
