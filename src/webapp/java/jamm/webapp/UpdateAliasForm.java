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

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;


/**
 * Hold alias update information for UpdateAliasAction.
 *
 * @see jamm.webapp.UpdateAliasAction
 */
public class UpdateAliasForm extends ActionForm
{
    /**
     * Sets the e-mail address
     *
     * @param mail a <code>String</code> containing the mail address
     */
    public void setMail(String mail)
    {
        mMail = mail;
    }

    /**
     * Returns the e-mail address
     *
     * @return a <code>String</code> containing the mail address
     */
    public String getMail()
    {
        return mMail;
    }

    /**
     * Sets the addresses to be delete.
     *
     * @param deleted a <code>String</code> array of addresses
     */
    public void setDeleted(String[] deleted)
    {
        mDeleted = deleted;
    }

    /**
     * Returns the addresses to be deleted.
     *
     * @return a <code>String</code> array of addresses
     */
    public String[] getDeleted()
    {
        return mDeleted;
    }

    /**
     * Sets the addresses to be added.
     *
     * @param added a <code>String</code> of whitespace or comma
     *              seperated addresses
     */
    public void setAdded(String added)
    {
        mAdded = added;
    }

    /**
     * Returns the addresses to be added.
     *
     * @return a <code>String</code> of whitespace or comma seperated
     *         addresses
     */
    public String getAdded()
    {
        return mAdded;
    }

    /**
     * Get the CommonName value.
     *
     * @return the CommonName value.
     */
    public String getCommonName()
    {
        return mCommonName;
    }

    /**
     * Set the CommonName value.
     *
     * @param newCommonName The new CommonName value.
     */
    public void setCommonName(String commonName)
    {
        mCommonName = commonName;
    }

    /**
     * Returns the addresses to add as a List instead of the
     * whitespace or comma seperated string.
     *
     * @return a <code>List</code> containing the addresses
     */
    public List getAddedAddresses()
    {
        StringTokenizer tokenizer = new StringTokenizer(mAdded, " \t\n\r\f,");
        List addresses = new ArrayList();
        while (tokenizer.hasMoreTokens())
        {
            addresses.add(tokenizer.nextToken());
        }
        return addresses;
    }

    /**
     * Resets the form to the default value.  It gets the value for
     * mail from the http request, sets added to an empty string, and
     * deleted to a zero length array.
     *
     * @param mapping an <code>ActionMapping</code> used to select
     *                this instance
     * @param request a <code>HttpServletRequest</code> that is being
     *                processed
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mMail = (String) request.getAttribute("mail");
        mDeleted = new String[0];
        mAdded = "";
    }

    /**
     * Validates the information from the HTML form.  This currently
     * returns an empty ActionErrors object.
     *
     * @param mapping an <code>ActionMapping</code> of possible destinations
     * @param request a <code>HttpServletRequest</code> being processed
     *
     * @return an <code>ActionErrors</code> object
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    /** The mail address of the alias */
    private String mMail;
    /** The addresses to delete from the alias. */
    private String[] mDeleted;
    /** The addresses to add to the alias. */
    private String mAdded;
    /** The common name associated with this alias. */
    private String mCommonName;
}
