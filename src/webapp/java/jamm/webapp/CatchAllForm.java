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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

/**
 * Bean to hold information for the CatchAllAction.
 *
 * @see jamm.webapp.CatchAllAction
 */
public class CatchAllForm extends ActionForm
{
    /**
     * Gets the value of the status
     *
     * @return the value of status
     */
    public String getStatus() 
    {
        return this.mStatus;
    }

    /**
     * Sets the value of status
     *
     * @param argStatus Value to assign to this.status
     */
    public void setStatus(String argStatus)
    {
        this.mStatus = argStatus;
    }

    /**
     * Returns true if status == this.ON (case insensitive), false otherwise
     *
     * @return true if on, false if off
     */
    public boolean isCatchAllOn()
    {
        return mStatus.compareToIgnoreCase(ON) == 0;
    }

    /**
     * Sets value of status to this.ON
     */
    public void setCatchAllOn()
    {
        mStatus = ON;
    }

    /**
     * Sets value of status to "off"
     */
    public void setCatchAllOff()
    {
        mStatus = OFF;
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
        mStatus = null;
        mDestination = null;
        mDomain = null;
    }

    /**
     * Validates the data in the form.
     *
     * @param mapping the mapping used to select this instance
     * @param request The servlet request we are processing.
     * @return a collection of action errors
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        
        if (isCatchAllOn() && isFieldBlank(mDestination))
        {
            errors.add("destination",
                new ActionError("catchall.error.no_destination"));
        }

        return errors;
    }

    /**
     * Returns true if the string field passed in is blank.
     *
     * @param field A field to test for blankness
     * @return true if blank, false otherwise
     */
    private boolean isFieldBlank(String field)
    {
        return (field == null || field.equals(""));
    }

    /** Is the catch all on or off? */
    private String mStatus;
    /** The destination of the catch all */
    private String mDestination;
    /** The domain for the catchall */
    private String mDomain;
    /** The value for status to be considered on. */
    public static final String ON = "on";
    /** The default value for status to be considered not on. */
    public static final String OFF = "off";

}
