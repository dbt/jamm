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
import java.util.Iterator;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.DomainInfo;

/**
 * Calls MailManager to loads the information the site_admin page
 * needs and then forwards to the site_admin page.  Currently, the
 * list of domains is loaded and saved in the request attribute
 * <code>domains</code>.  Prepopulates the SiteConfigForm with data.
 *
 * @see jamm.backend.MailManager
 * @see jamm.webapp.SiteConfigForm
 */
public class SiteAdminAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @param mapping <code>ActionMapping</code> of possible locations.
     * @param actionForm <code>ActionForm</code>, ignored in this action
     * @param request a <code>HttpServletRequest</code> that caused the action
     * @param response a <code>HttpServletResponse</code>
     *
     * @return an <code>ActionForward</code> value
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
        List domains = manager.getDomains();
        request.setAttribute("domains", domains);

        // Create the bread crumbs
        List breadCrumbs = new ArrayList();
        BreadCrumb breadCrumb;
        breadCrumb = new BreadCrumb(
            findForward(mapping, "site_admin", request).getPath(),
            "Site Admin");
        breadCrumbs.add(breadCrumb);
        request.setAttribute("breadCrumbs", breadCrumbs);

        SiteConfigForm siteConfigForm = new SiteConfigForm();

        List domainNames = new ArrayList();
        List allowEditAccounts = new ArrayList();
        List allowEditPostmasters = new ArrayList();
        List active = new ArrayList();

        Iterator i = domains.iterator();
        while (i.hasNext())
        {
            DomainInfo di = (DomainInfo) i.next();
            String name = di.getName();
            
            domainNames.add(name);

            if (di.getCanEditAccounts())
            {
                allowEditAccounts.add(name);
            }
            if (di.getCanEditPostmasters())
            {
                allowEditPostmasters.add(name);
            }
            if (di.getActive())
            {
                active.add(name);
            }
        }
            
        siteConfigForm.setDomains(
            (String []) domainNames.toArray(new String[0]));

        String[] editAccountsArray =
            (String []) allowEditAccounts.toArray(new String[0]);
        siteConfigForm.setAllowEditAccounts(editAccountsArray);
        siteConfigForm.setOriginalAllowEditAccounts(editAccountsArray);

        String[] editPostmastersArray =
            (String []) allowEditPostmasters.toArray(new String[0]);
        siteConfigForm.setAllowEditPostmasters(editPostmastersArray);
        siteConfigForm.setOriginalAllowEditPostmasters(editPostmastersArray);

        String[] activeArray =
            (String []) active.toArray(new String[0]);
        siteConfigForm.setActive(activeArray);
        siteConfigForm.setOriginalActive(activeArray);

        request.setAttribute("siteConfigForm", siteConfigForm);

        return (mapping.findForward("view"));
    }
}
