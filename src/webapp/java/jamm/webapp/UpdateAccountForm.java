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

import org.apache.struts.action.ActionForm;

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

    private String mCommonName;
    private String mMail;
}
