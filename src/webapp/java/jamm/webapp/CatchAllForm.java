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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Bean to hold information for the CatchAllAction.
 *
 * @see jamm.webapp.CatchAllAction
 */
public class CatchAllForm extends ActionForm
{
    /**
     * Gets the value of isActive
     *
     * @return the value of isActive
     */
    public String getIsActive() 
    {
        return this.mIsActive;
    }

    /**
     * Sets the value of isActive
     *
     * @param argIsActive Value to assign to this.isActive
     */
    public void setIsActive(String argIsActive)
    {
        this.mIsActive = argIsActive;
    }

    /**
     * Gets the value of destinations
     *
     * @return the value of destinations
     */
    public String getDestination() 
    {
        return this.mDestination;
    }

    /**
     * Sets the value of destinations
     *
     * @param argDestination Value to assign to this.destinations
     */
    public void setDestination(String argDestination)
    {
        this.mDestination = argDestination;
    }

    /**
     * Sets the value for domain.
     *
     * @param domain The domain we're manupulating the catchall for.
     */
    public void setDomain(String domain)
    {
        mDomain = domain;
    }

    /**
     * Returns the value for domain
     *
     * @return a string containing the domain
     */
    public String getDomain()
    {
        return mDomain;
    }

    /**
     * Resets isActive and destinations to null.
     *
     * @param mapping The mapping used to select this instance.
     * @param request The servlet request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mIsActive = null;
        mDestination = null;
        mDomain = request.getParameter("domain");
    }

    /** Is the catch all on or off? */
    private String mIsActive;
    /** The destination of the catch all */
    private String mDestination;
    /** The domain for the catchall */
    private String mDomain;
}
