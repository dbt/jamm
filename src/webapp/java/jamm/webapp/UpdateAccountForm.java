/*
 * Jamm
 * Copyright (C) 2003 Dave Dribin and Keith Garner
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
 * @author kgarner
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdateAccountForm extends ActionForm
{
    /**
     * @return
     */
    public String getCommonName()
    {
        return mCommonName;
    }

    /**
     * @return
     */
    public String getMail()
    {
        return mMail;
    }

    /**
     * @param string
     */
    public void setCommonName(String string)
    {
        mCommonName = string;
    }

    /**
     * @param string
     */
    public void setMail(String string)
    {
        mMail = string;
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
        mCommonName = "";
    }

    private String mCommonName;
    private String mMail;
}
