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

import java.util.Arrays;
import java.util.Iterator;
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

        System.out.println("====================================" +
                           "====================================");

        System.out.println("AllowEditAliases: " +
                           Arrays.asList(form.getAllowEditAliases()));
        System.out.println("Unchecked editAliases: " +
                           form.getUncheckedEditAliases());
        System.out.println("Checked editAliases: " +
                           form.getCheckedEditAliases());

        HashMap domainInfos = new HashMap();

        Iterator i = form.getUncheckedEditAliases().iterator();
        modifyCapability(EDIT_ALIASES, manager, domainInfos, i, false);
        i = form.getCheckedEditAliases().iterator();
        modifyCapability(EDIT_ALIASES, manager, domainInfos, i, true);
                                   
        i = form.getUncheckedEditAccounts().iterator();
        modifyCapability(EDIT_ACCOUNTS, manager, domainInfos, i, false);
        i = form.getCheckedEditAccounts().iterator();
        modifyCapability(EDIT_ACCOUNTS, manager, domainInfos, i, true);

        i = form.getUncheckedEditPostmasters().iterator();
        modifyCapability(EDIT_POSTMASTERS, manager, domainInfos, i, false);
        i = form.getCheckedEditPostmasters().iterator();
        modifyCapability(EDIT_POSTMASTERS, manager, domainInfos, i, true);

        i = form.getUncheckedEditCatchalls().iterator();
        modifyCapability(EDIT_CATCHALLS, manager, domainInfos, i, false);
        i = form.getCheckedEditCatchalls().iterator();
        modifyCapability(EDIT_CATCHALLS, manager, domainInfos, i, true);

        i = domainInfos.values().iterator();
        while (i.hasNext())
        {
            DomainInfo di = (DomainInfo) i.next();
            manager.modifyDomain(di);
        }

        return mapping.findForward("user_home");
    }

    /**
     * Worker class that modifies the domain info
     *
     * @param capability an <code>int</code> value
     * @param manager a <code>MailManager</code> value
     * @param domainInfos a <code>HashMap</code> value
     * @param i an <code>Iterator</code> value
     * @param value a <code>boolean</code> value
     * @exception Exception if an error occurs
     */
    private void modifyCapability(int capability, MailManager manager,
                                  HashMap domainInfos, Iterator i,
                                  boolean value)
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
                    di.setCanEditAliases(value);
                    break;

                case EDIT_ACCOUNTS:
                    di.setCanEditAccounts(value);
                    break;

                case EDIT_POSTMASTERS:
                    di.setCanEditPostmasters(value);
                    break;

                case EDIT_CATCHALLS:
                    di.setCanEditCatchalls(value);
                    break;

                default:
                    throw new Exception("The should never be here");
            }
        }
    }

    private static final int EDIT_ALIASES = 0;
    private static final int EDIT_ACCOUNTS = 1;
    private static final int EDIT_POSTMASTERS = 2;
    private static final int EDIT_CATCHALLS = 3;
}
