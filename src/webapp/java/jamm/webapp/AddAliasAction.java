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
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.MailAddress;

/**
 * Calls the MailManager to add a virtual alias based on the
 * information passed in via the AddAliasForm.
 *
 * @see jamm.backend.MailManager
 * @see jamm.webapp.AddAliasForm
 */
public class AddAliasAction extends JammAction
{
    /**
     * Perform the action.
     *
     * @see jamm.webapp.AddAliasForm
     *
     * @param mapping The action mapping of possible destinations.
     * @param actionForm an AddAliasForm object holding the required info.
     * @param request The http request that caused this action to happen.
     * @param response The http response to this action.
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        if (isCancelled(request))
        {
            return mapping.findForward("user_home");
        }

        AddAliasForm form = (AddAliasForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);
        String mail = MailAddress.addressFromParts(form.getName(),
                                                   form.getDomain());

        manager.createAlias(form.getDomain(), form.getName(),
                            form.getDestinationAddresses());

        if (!form.isPasswordEmpty())
        {
            manager.changePassword(mail, form.getPassword());
        }

        return mapping.findForward("user_home");
    }
}
