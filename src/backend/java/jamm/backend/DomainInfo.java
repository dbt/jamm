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
     * @param canEditAliases a <code>boolean</code> value
     * @param canEditAccounts a <code>boolean</code> value
     * @param canEditPostmasters a <code>boolean</code> value
     * @param canEditCatchalls a <code>boolean</code> value
     */
    public DomainInfo(String name, boolean canEditAliases,
                      boolean canEditAccounts, boolean canEditPostmasters,
                      boolean canEditCatchalls)
    {
        mName = name;
        mCanEditAliases = canEditAliases;
        mCanEditAccounts = canEditAccounts;
        mCanEditPostmasters = canEditPostmasters;
        mCanEditCatchalls = canEditCatchalls;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return this.mName;
    }

    /**
     * Sets the value of name
     *
     * @param argName Value to assign to this.name
     */
    public void setName(String argName){
        this.mName = argName;
    }

    /**
     * Gets the value of canEditAliases
     *
     * @return the value of canEditAliases
     */
    public boolean getCanEditAliases() {
        return this.mCanEditAliases;
    }

    /**
     * Sets the value of canEditAliases
     *
     * @param argCanEditAliases Value to assign to this.canEditAliases
     */
    public void setCanEditAliases(boolean argCanEditAliases){
        this.mCanEditAliases = argCanEditAliases;
    }

    /**
     * Gets the value of canEditAccounts
     *
     * @return the value of canEditAccounts
     */
    public boolean getCanEditAccounts() {
        return this.mCanEditAccounts;
    }

    /**
     * Sets the value of canEditAccounts
     *
     * @param argCanEditAccounts Value to assign to this.canEditAccounts
     */
    public void setCanEditAccounts(boolean argCanEditAccounts){
        this.mCanEditAccounts = argCanEditAccounts;
    }

    /**
     * Gets the value of canEditPostmasters
     *
     * @return the value of canEditPostmasters
     */
    public boolean getCanEditPostmasters() {
        return this.mCanEditPostmasters;
    }

    /**
     * Sets the value of canEditPostmasters
     *
     * @param argCanEditPostmasters Value to assign to this.canEditPostmasters
     */
    public void setCanEditPostmasters(boolean argCanEditPostmasters){
        this.mCanEditPostmasters = argCanEditPostmasters;
    }

    /**
     * Gets the value of canEditCatchalls
     *
     * @return the value of canEditCatchalls
     */
    public boolean getCanEditCatchalls() {
        return this.mCanEditCatchalls;
    }

    /**
     * Sets the value of canEditCatchalls
     *
     * @param argCanEditCatchalls Value to assign to this.canEditCatchalls
     */
    public void setCanEditCatchalls(boolean argCanEditCatchalls){
        this.mCanEditCatchalls = argCanEditCatchalls;
    }

    private String mName;
    private boolean mCanEditAliases;
    private boolean mCanEditAccounts;
    private boolean mCanEditPostmasters;
    private boolean mCanEditCatchalls;
}
