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
 * A bean that represents domain information.
 */
public class DomainInfo implements Serializable
{

    /**
     * Creates a new <code>DomainInfo</code> instance with specified data.
     *
     * @param name a <code>String</code> value
     * @param canEditAccounts a <code>boolean</code> value
     * @param canEditPostmasters a <code>boolean</code> value
     * @param active is domain active
     * @param lastChange time of last change in unix time.
     */
    public DomainInfo(String name, boolean canEditAccounts,
                      boolean canEditPostmasters, boolean active,
                      int lastChange)
    {
        mName = name;
        mCanEditAccounts = canEditAccounts;
        mCanEditPostmasters = canEditPostmasters;
        mActive = active;
        mLastChange = lastChange;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return this.mName;
    }

    /**
     * Sets the value of name
     *
     * @param argName Value to assign to this.name
     */
    public void setName(String argName)
    {
        this.mName = argName;
    }

    /**
     * Gets the value of canEditAccounts
     *
     * @return the value of canEditAccounts
     */
    public boolean getCanEditAccounts()
    {
        return this.mCanEditAccounts;
    }

    /**
     * Sets the value of canEditAccounts
     *
     * @param argCanEditAccounts Value to assign to this.canEditAccounts
     */
    public void setCanEditAccounts(boolean argCanEditAccounts)
    {
        this.mCanEditAccounts = argCanEditAccounts;
    }

    /**
     * Gets the value of canEditPostmasters
     *
     * @return the value of canEditPostmasters
     */
    public boolean getCanEditPostmasters()
    {
        return this.mCanEditPostmasters;
    }

    /**
     * Sets the value of canEditPostmasters
     *
     * @param argCanEditPostmasters Value to assign to this.canEditPostmasters
     */
    public void setCanEditPostmasters(boolean argCanEditPostmasters)
    {
        this.mCanEditPostmasters = argCanEditPostmasters;
    }

    /**
     * Returns wether the domain is active or not.
     *
     * @return true if active, false if not.
     */
    public boolean getActive()
    {
        return mActive;
    }

    /**
     * Set domain active or not.
     *
     * @param active true if active, false if not.
     */
    public void setActive(boolean active)
    {
        mActive = active;
    }

    /**
     * returns the last change time of this domain's info
     *
     * @return an int containing unixtime
     */
    public int getLastChange()
    {
        return mLastChange;
    }

    /**
     * Set whether this domain should be marked for deletion.
     *
     * @param delete boolean value
     */
    public void setDelete(boolean delete)
    {
        mDelete = delete;
    }

    /**
     * Is this domain marked for deletion?
     *
     * @return boolean value
     */
    public boolean getDelete()
    {
        return mDelete;
    }

    /** The domain name */
    private String mName;
    /** Can domain admin edit accounts */
    private boolean mCanEditAccounts;
    /** Can domain admin edit postmasters */
    private boolean mCanEditPostmasters;
    /** is this domain active? */
    private boolean mActive;
    /** time of last change */
    private int mLastChange;
    /* True if this domain should be deleted. */
    private boolean mDelete;

}
