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

import java.io.Serializable;

public class RenderDomainInfo
    implements Serializable
{
    public boolean getCanEditAccounts()
    {
        return mCanEditAccounts;
    }
    
    public void setCanEditAccounts(boolean value)
    {
        mCanEditAccounts = value;
    }

    public boolean getCanEditPostmasters()
    {
        return mCanEditPostmasters;
    }

    public void setCanEditPostmasters(boolean value)
    {
        mCanEditPostmasters = value;
    }

    private boolean mCanEditAccounts;
    private boolean mCanEditPostmasters;
}
