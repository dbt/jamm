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
 * Calls MailManager to create a domain based on the information
 * provided in the actionForm.  It will also set the postmaster
 * password if a password was passed in via the form.
 *
 * @see jamm.backend.MailManager
 * @see jamm.webapp.AddDomainForm
 * 
 * @struts.action scope="request" validate="true" name="addDomainForm"
 *                path="/private/add_domain" input="/private/add_domain.jsp"
 *                roles="Site Administrator"
 */
public class AddDomainAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @see jamm.webapp.AddDomainForm
     *
     * @param mapping The action mapping of possible destinations.
     * @param actionForm an AddDomainForm object holding the required info.
     * @param request The http request that caused this action to happen.
     * @param response The http response to this action.
     *
     * @return an <code>ActionForward</code>
     *
     * @exception Exception if an error occurs
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        if (isCancelled(request))
        {
            return mapping.findForward("site_admin");
        }

        AddDomainForm form = (AddDomainForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);

        manager.createDomain(form.getDomain());
        if (!form.isPasswordEmpty())
        {
            String mail = MailAddress.addressFromParts("postmaster",
                                                       form.getDomain());
            manager.changePassword(mail, form.getPassword());
        }

        return mapping.findForward("site_admin");
    }
}
