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
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.MailManagerException;
import jamm.backend.AliasInfo;
import jamm.backend.MailAddress;

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
     * @param response The http response that will be returned from
     *                 this action.
     *
     * @return the ActionForward of the next page to show.
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
        MailManager manager = getMailManager(user);

        String mail = request.getParameter("mail");
        if (mail == null)
        {
            mail = user.getUsername();
        }

        Map extraInfo = new HashMap();
        extraInfo.put("mail", mail);
        if (!isAllowedToBeHere(request, extraInfo))
        {
            doAccessError(request, mapping);
            return mapping.findForward("access_error");
        }
        
        ChangePasswordForm cpf = new ChangePasswordForm();
        cpf.setMail(mail);
        request.setAttribute("changePasswordForm", cpf);
        request.setAttribute("mail", mail);

        // Create the bread crumbs
        List breadCrumbs = new ArrayList();
        BreadCrumb breadCrumb;
        if (user.isUserInRole(User.SITE_ADMIN_ROLE))
        {
            breadCrumb = new BreadCrumb(
                findForward(mapping, "site_admin", request).getPath(),
                "Site Admin");
            breadCrumbs.add(breadCrumb);
        }

        if (user.isUserInRole(User.DOMAIN_ADMIN_ROLE))
        {
            String domain = MailAddress.hostFromAddress(mail);
            breadCrumb = new BreadCrumb(
                getDomainAdminForward(mapping, domain).getPath(),
                "Domain Admin");
            breadCrumbs.add(breadCrumb);
        }
        
        if (manager.isAlias(mail))
        {
            breadCrumb = new BreadCrumb(
                getAccountAdminForward(mapping, mail).getPath(),
                "Alias Admin");
            breadCrumbs.add(breadCrumb);
            request.setAttribute("breadCrumbs", breadCrumbs);

            Map passwordParameters = new HashMap();
            passwordParameters.put("mail", mail);
            passwordParameters.put("done", "account_admin");
            request.setAttribute("passwordParameters", passwordParameters);

            AliasInfo alias = manager.getAlias(mail);
            request.setAttribute("alias", alias);
            return (mapping.findForward("alias_admin"));
        }
        else
        {
            breadCrumb = new BreadCrumb(
                getAccountAdminForward(mapping, mail).getPath(),
                "Account Admin");
            breadCrumbs.add(breadCrumb);
            request.setAttribute("breadCrumbs", breadCrumbs);

            Map passwordParameters = new HashMap();
            passwordParameters.put("mail", mail);
            passwordParameters.put("done", "account_admin");
            request.setAttribute("passwordParameters", passwordParameters);

            return (mapping.findForward("view"));
        }
    }

    /**
     * Checks to see if the domain admin is allowed to be here.
     *
     * @param request the request we are servicing
     * @param extraInfo Should contain information for the "mail"
     * @return boolean value signifying if the domain admin can be here.
     * @exception MailManagerException if an error occurs
     */
    protected boolean isDomainAdminAllowed(HttpServletRequest request,
                                           Map extraInfo)
        throws MailManagerException
    {
        User user = getUser(request);
        MailManager manager = getMailManager(user);
        String mail = (String) extraInfo.get("mail");
        String domain = MailAddress.hostFromAddress(mail);

        return manager.isPostmaster(domain, user.getUsername());
    }

    /**
     * Checks to see if the domain admin is allowed to be here.
     *
     * @param request The request we are servicing
     * @param extraInfo Should contain information for the "mail" address
     * @return boolean value signifying if the user can be here.
     * @exception MailManagerException if an error occurs
     */
    protected boolean isUserAllowed(HttpServletRequest request,
                                    Map extraInfo)
        throws MailManagerException
    {
        User user = getUser(request);
        String mail = (String) extraInfo.get("mail");
        
        return user.getUsername().equals(mail);
    }
}
