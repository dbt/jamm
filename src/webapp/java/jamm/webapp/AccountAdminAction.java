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
import jamm.backend.AliasInfo;

/**
 * Forwards a user or an administrator to the correct page for
 * administration, either the alias_admin page or the account_admin
 * page.  If the request contains a "mail=" arguement, that will be used,
 * if not, it will default to the current users information.
 */
public class AccountAdminAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @param mapping Where we get our forwards from.
     * @param actionForm This action does not depend on a form,
     *                   so this should be null.
     * @param request The http request that caused this action.
     * @param request The http response that will be returned from this action.
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        User user = getUser(request);
        String mail = request.getParameter("mail");
        if (mail == null)
        {
            mail = user.getUsername();
        }

        MailManager manager = getMailManager(user);
        if (manager.isAlias(mail))
        {
            AliasInfo alias = manager.getAlias(mail);

            request.setAttribute("mail", mail);
            request.setAttribute("alias", alias);

            return (mapping.findForward("alias_admin"));
        }
        else
        {
            request.setAttribute("mail", mail);
            return (mapping.findForward("account_admin"));
        }
    }
}
