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

/**
 * Calls the MailManager to add a virtual account based on the information
 * passed in via the AddActionForm.
 *
 * @see jamm.backend.MailManager
 * @see jamm.webapp.AddAccountForm
 */
public class AddAccountAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @see jamm.webapp.AddAccountForm
     *
     * @param mapping The mapping of our possible destinations.
     * @param actionForm An AddAccountForm object with the required
     *                   information.
     * @param request The http request that caused this action.
     * @param response The http response that will be returned from
     *                 this action.
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
            return mapping.findForward("user_home");
        }

        AddAccountForm form = (AddAccountForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);

        manager.createAccount(form.getDomain(), form.getName(),
                              form.getPassword());

        return mapping.findForward("user_home");
    }
}
