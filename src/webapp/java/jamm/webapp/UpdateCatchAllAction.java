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

import jamm.backend.MailManager;
import jamm.backend.AliasInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

/**
 * @see jamm.backend.MailManager
 * @see jamm.webapp.UpdateCatchAllForm
 */
public class UpdateCatchAllAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @see jamm.webapp.UpdateCatchAllForm
     *
     * @param mapping The action mapping of possible destinations.
     * @param actionForm a UpdateCatchAllForm with our required information
     * @param request the http request
     * @param response the http response
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
        UpdateCatchAllForm form = (UpdateCatchAllForm) actionForm;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
   
        if (isCancelled(request))
        {
            return findForward(mapping, "domain_admin", request);
        }
        
        String domain = form.getDomain();
        String catchAll = "@" + domain;
        String destination = form.getDestination();

        MailManager manager =
            new MailManager(Globals.getLdapHost(),
                            Globals.getLdapPort(),
                            Globals.getLdapSearchBase(),
                            user.getDn(),
                            user.getPassword());

        AliasInfo ai = manager.getAlias(catchAll);
        boolean catchAllOn = form.isCatchAllOn();

        if (catchAllOn)
        {
            if (ai == null)
            {
                manager.addCatchall(domain, destination);
            }
            else
            {
                String destArray[] = { destination };
                ai.setMailDestinations(destArray);
                manager.modifyAlias(ai);
            }
        }
        else
        {
            if (ai != null)
            {
                manager.deleteAlias(catchAll);
            }
        }

        return findForward(mapping, "domain_admin", request);
    }
}
