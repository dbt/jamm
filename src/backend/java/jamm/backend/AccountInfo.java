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

/**
 * A bean that represents account information.
 */
public class AccountInfo implements Serializable
{
    /**
     * Create an account with the specified name, active, and boolean
     * flags.
     *
     * @param name Account name
     * @param active True if this account is active.
     * @param amdministrator True if this account has administrator
     * priveleges.
     */
    public AccountInfo(String name, boolean active, boolean administrator)
    {
        mName = name;
        mActive = active;
        mAdministrator = administrator;
    }

    /**
     * Returns this account's name.
     *
     * @return Accout name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Returns true if this account is active.
     *
     * @return True if this acount is active
     */
    public boolean isActive()
    {
        return mActive;
    }

    /**
     * Returns true if this account is an administrator.
     *
     * @return rue if this account is an administrator
     */
    public boolean isAdministrator()
    {
        return mAdministrator;
    }

    /** Account name. */
    private String mName;
    /** True if this account active. */
    private boolean mActive;
    /** True if this account is an adiministrator. */
    private boolean mAdministrator;
}
