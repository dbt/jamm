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

/**
 * Directs the user to the appropriate menu depending on their level
 * of access.  User.SITE_ADMIN_ROLE is directed to the site_admin
 * forward.  User.DOMAIN_ADMIN_ROLE is directed to the domain_admin
 * forward.  User.SITE_ADMIN_ROLE takes precendence over
 * User.DOMAIN_ADMIN_ROLE.  Everyone else is directed to the
 * account_admin forward.
 *
 * @see jamm.webapp.User
 * 
 * @struts.action scope="request" validate="false" path="/private/index"
 * @struts.action-forward name="account_admin" path="/private/account_admin.do"
 * @struts.action-forward name="domain_admin" path="/private/domain_admin.do"
 * @struts.action-forward name="site_admin" path="/private/site_admin.do"
 */
public class MainMenuAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @param mapping an <code>ActionMapping</code> of possible locations.
     * @param actionForm an <code>ActionForm</code>, ignored in this action
     * @param request a <code>HttpServletRequest</code>
     * @param response a <code>HttpServletResponse</code>
     *
     * @return an <code>ActionForward</code> of the next destination.
     *
     * @exception Exception if an error occurs
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        User user = getUser(request);
        if (user.isUserInRole(User.SITE_ADMIN_ROLE))
        {
            return (mapping.findForward("site_admin"));
        }
        else if (user.isUserInRole(User.DOMAIN_ADMIN_ROLE))
        {
            return (mapping.findForward("domain_admin"));
        }
        else
        {
            return (mapping.findForward("account_admin"));
        }
    }
}
