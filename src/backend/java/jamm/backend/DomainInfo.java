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

import org.apache.commons.lang.builder.ToStringBuilder;

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
     * @param delete is this marked for deletion
     * @param aliasCount the number of aliases in this domain
     * @param accountCount the number of accounts in this domain
     * @param lastChange time of last change in unix time.
     */
    public DomainInfo(String name, boolean canEditAccounts,
                      boolean canEditPostmasters, boolean active,
                      boolean delete, int aliasCount, int accountCount,
                      int lastChange)
    {
        mName = name;
        mCanEditAccounts = canEditAccounts;
        mCanEditPostmasters = canEditPostmasters;
        mActive = active;
        mLastChange = lastChange;
        mDelete = delete;
        mAliasCount = aliasCount;
        mAccountCount = accountCount;
    }
    
    /**
     * Creates a new <code>DomainInfo</code> instance with specified data.
     *
     * @param name a <code>String</code> value
     * @param canEditAccounts a <code>boolean</code> value
     * @param canEditPostmasters a <code>boolean</code> value
     * @param active is domain active
     * @param delete is this marked for deletion
     * @param lastChange time of last change in unix time.
     */
    public DomainInfo(String name, boolean canEditAccounts,
                      boolean canEditPostmasters, boolean active,
                      boolean delete, int lastChange)
    {
        this(name, canEditAccounts, canEditPostmasters, active, delete,
             0, 0, lastChange);
    }

    /**
     * Returns the number of accounts in this domain.
     * 
     * @return an int with the count
     */
    public int getAccountCount()
    {
        return mAccountCount;
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
     * Returns the number of aliases in this domain.
     * 
     * @return an int with the count
     */
    public int getAliasCount()
    {
        return mAliasCount;
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
     * Gets the value of canEditPostmasters
     *
     * @return the value of canEditPostmasters
     */
    public boolean getCanEditPostmasters()
    {
        return this.mCanEditPostmasters;
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
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return this.mName;
    }

    /**
     * Sets the account count for this domain.
     * 
     * @param accountCount a count of accounts
     */
    public void setAccountCount(int accountCount)
    {
        mAccountCount = accountCount;
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
     * Sets the alias count for this domain.
     * 
     * @param aliasCount a count of aliases
     */
    public void setAliasCount(int aliasCount)
    {
        mAliasCount = aliasCount;
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
     * Sets the value of canEditPostmasters
     *
     * @param argCanEditPostmasters Value to assign to this.canEditPostmasters
     */
    public void setCanEditPostmasters(boolean argCanEditPostmasters)
    {
        this.mCanEditPostmasters = argCanEditPostmasters;
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
     * Sets the value of name
     *
     * @param argName Value to assign to this.name
     */
    public void setName(String argName)
    {
        this.mName = argName;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this)
            .append("name", mName)
            .append("active", mActive)
            .append("canEditAccounts", mCanEditAccounts)
            .append("canEditPostmasters", mCanEditPostmasters)
            .append("delete", mDelete)
            .append("lastChange", mLastChange)
            .append("aliasCount", mAliasCount)
            .append("accountCount", mAccountCount)
            .toString();
    }

    /** Account count */
    private int mAccountCount;
    /** is this domain active? */
    private boolean mActive;
    /** Alias count */
    private int mAliasCount;
    /** Can domain admin edit accounts */
    private boolean mCanEditAccounts;
    /** Can domain admin edit postmasters */
    private boolean mCanEditPostmasters;
    /** True if this domain should be deleted. */
    private boolean mDelete;
    /** time of last change */
    private int mLastChange;
    /** The domain name */
    private String mName;

    /** Static final for helping make setActive more readable */
    public static final boolean ACTIVE = true;
    /** Static final for helping make setDelete more readable */
    public static final boolean DELETE = true;
    /** Static final for helping make setDelete more readable */
    public static final boolean NO_DELETE = false;
    /** Static final for helping make setActive more readable */
    public static final boolean NOT_ACTIVE = false;
}
