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
 * Hold alias update information
 */
public class UpdateAliasForm extends ActionForm
{
    public void setMail(String mail)
    {
        mMail = mail;
    }

    public String getMail()
    {
        return mMail;
    }

    public void setDeleted(String[] deleted)
    {
        mDeleted = deleted;
    }

    public String[] getDeleted()
    {
        return mDeleted;
    }

    public void setAdded(String added)
    {
        mAdded = added;
    }

    public String getAdded()
    {
        return mAdded;
    }

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
     *
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mMail = (String) request.getAttribute("mail");
        mDeleted = new String[0];
        mAdded = "";
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    private String mMail;
    private String[] mDeleted;
    private String mAdded;
}
