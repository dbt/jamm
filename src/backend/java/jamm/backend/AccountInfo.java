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
n */

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
     * @param administrator True if this account has administrator
     * priveleges.
     * @param homeDirectory The home directory of the accounts
     * @param mailbox The mailbox within the home directory
     * @param lastChange time of last change
     */
    public AccountInfo(String name, boolean active, boolean administrator,
                       String homeDirectory, String mailbox, int lastChange)
    {
        mName = name;
        mActive = active;
        mAdministrator = administrator;
        mHomeDirectory = homeDirectory;
        mMailbox = mailbox;
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
     * Sets the value of active.
     *
     * @param value boolean value representing activeness
     */
    public void setActive(boolean value)
    {
        mActive = value;
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

    /**
     * Sets the value of administrator.
     *
     * @param value boolean value to set administrator to.
     */
    public void setAdministrator(boolean value)
    {
        mAdministrator = value;
    }

    /**
     * Gets the value of homeDirectory
     *
     * @return the value of homeDirectory
     */
    public String getHomeDirectory() 
    {
        return this.mHomeDirectory;
    }

    /**
     * Gets the value of mailbox
     *
     * @return the value of mailbox
     */
    public String getMailbox() 
    {
        return this.mMailbox;
    }

    /**
     * Gets the value of lastChange in unix time
     *
     * @return the value of lastChange in unix time
     */
    public int getLastChange()
    {
        return this.lastChange;
    }

    /** Account name. */
    private String mName;
    /** True if this account active. */
    private boolean mActive;
    /** True if this account is an adiministrator. */
    private boolean mAdministrator;
    /** Home Directory */
    private String mHomeDirectory;
    /** mailbox */
    private String mMailbox;
    /** time of last change */
    private int lastChange;
}
