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

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.DomainInfo;

/**
 *
 * @see jamm.backend.MailManager
 */
public class SiteConfigAction extends JammAction
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
        SiteConfigForm form = (SiteConfigForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);

        HashMap domainInfos = new HashMap();

        Iterator i = form.getUncheckedEditAliases().iterator();
        modifyCapability(EDIT_ALIASES, false, manager, domainInfos, i);
        i = form.getCheckedEditAliases().iterator();
        modifyCapability(EDIT_ALIASES, true, manager, domainInfos, i);
                                   
        i = form.getUncheckedEditAccounts().iterator();
        modifyCapability(EDIT_ACCOUNTS, false, manager, domainInfos, i);
        i = form.getCheckedEditAccounts().iterator();
        modifyCapability(EDIT_ACCOUNTS, true, manager, domainInfos, i);

        i = form.getUncheckedEditPostmasters().iterator();
        modifyCapability(EDIT_POSTMASTERS, false, manager, domainInfos, i);
        i = form.getCheckedEditPostmasters().iterator();
        modifyCapability(EDIT_POSTMASTERS, true, manager, domainInfos, i);

        i = form.getUncheckedEditCatchalls().iterator();
        modifyCapability(EDIT_CATCHALLS, false, manager, domainInfos, i);
        i = form.getCheckedEditCatchalls().iterator();
        modifyCapability(EDIT_CATCHALLS, true, manager, domainInfos, i);

        i = domainInfos.values().iterator();
        while (i.hasNext())
        {
            DomainInfo di = (DomainInfo) i.next();
            manager.modifyDomain(di);
        }

        return mapping.findForward("user_home");
    }

    /**
     * Worker method that modifies the DomainInfo objects setting the
     * capability to the value passed in.  Iterator is an iterator of
     * strings of domain names, domainInfos is a HashMap of the
     * domains we've edited, and manager is where to get the
     * DomainInfo of domains we haven't touched yet.
     *
     * @param capability one of the EDIT_* variables defined in this class.
     * @param setTo what to set the capability to
     * @param manager a MailManager
     * @param domainInfos Map of DomainInfo objects
     * @param i iterator containing strings of domain names
     *
     * @exception Exception if an error occurs
     */
    private void modifyCapability(int capability, boolean setTo,
                                  MailManager manager, Map domainInfos,
                                  Iterator i)
        throws Exception
    {
        while (i.hasNext())
        {
            String domain = (String) i.next();
            DomainInfo di = (DomainInfo) domainInfos.get(domain);
            if (di == null)
            {
                di = manager.getDomain(domain);
                domainInfos.put(domain, di);
            }

            switch(capability)
            {
                case EDIT_ALIASES:
                    di.setCanEditAliases(setTo);
                    break;

                case EDIT_ACCOUNTS:
                    di.setCanEditAccounts(setTo);
                    break;

                case EDIT_POSTMASTERS:
                    di.setCanEditPostmasters(setTo);
                    break;

                case EDIT_CATCHALLS:
                    di.setCanEditCatchalls(setTo);
                    break;

                default:
                    throw new Exception("The should never be here");
            }
        }
    }

    /** a final meaning "call setCanEditAliases" */
    private static final int EDIT_ALIASES = 0;
    /** a final meaning "call setCanEditAccounts" */
    private static final int EDIT_ACCOUNTS = 1;
    /** a final meaning "call setCanEditPostmasters" */
    private static final int EDIT_POSTMASTERS = 2;
    /** a final meaning "call setCanEditCatchalls" */
    private static final int EDIT_CATCHALLS = 3;
}
