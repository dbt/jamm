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
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.MailManagerException;
import jamm.backend.AccountInfo;
import jamm.backend.DomainInfo;

/**
 * It should call MailManager to perform the assigned actions on
 * accounts from a domain.  Currently, it changes actives, prints
 * out/logs the rest of the changes to the checkboxes and then
 * forwards to user_home.
 *
 * @see jamm.webapp.DomainConfigForm
 */
public class DomainAccountAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @see jamm.webapp.DomainConfigForm
     *
     * @param mapping The action mapping of possible destinations
     * @param actionForm a DomainConfigForm with the required info
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
        DomainConfigForm form = (DomainConfigForm) actionForm;
        User user = getUser(request);

        manager = getMailManager(user);
        DomainInfo domainInfo = manager.getDomain(form.getDomain());
        boolean userIsSiteAdmin = user.isUserInRole(User.SITE_ADMIN_ROLE);
        
        accountInfos = new HashMap();
        
        System.out.println("====================================" +
                           "====================================");
        
        System.out.println("Delete: " +
                           Arrays.asList(form.getItemsToDelete()));

        Iterator i;

        if (domainInfo.getCanEditAccounts() || userIsSiteAdmin)
        {
            i = form.getUncheckedActiveItems().iterator();
            modifyActive(false, i);

            i = form.getCheckedActiveItems().iterator();
            modifyActive(true, i);
        }

        if (domainInfo.getCanEditPostmasters() || userIsSiteAdmin)
        {
            i = form.getUncheckedAdminItems().iterator();
            modifyAdministrator(false, i);

            i = form.getCheckedAdminItems().iterator();
            modifyAdministrator(true, i);
        }

        i = accountInfos.values().iterator();
        while (i.hasNext())
        {
            AccountInfo ai = (AccountInfo) i.next();
            manager.modifyAccount(ai);
        }


        return findForward(mapping, "domain_admin", request);
    }

    /**
     * Gets the account out of the accountInfo HashMap.  If the
     * accountInfo isn't containted in there, it looks it up in mail
     * manager.
     *
     * @param account The mail address of the account
     * @return an AccountInfo object
     * @exception MailManagerException if an error occurs
     */
    private AccountInfo getAccount(String account)
        throws MailManagerException
    {
        AccountInfo ai = (AccountInfo) accountInfos.get(account);
        if (ai == null)
        {
            ai = manager.getAccount(account);
            accountInfos.put(account, ai);
        }
        return ai;
    }
            
    /**
     * Modifies the active flag on the account.
     *
     * @param setTo boolean value to set to
     * @param i an iterator filled with AccountInfo
     * @exception MailManagerException if an error occurs
     */
    private void modifyActive(boolean setTo, Iterator i)
        throws MailManagerException
    {
        while (i.hasNext())
        {
            String account = (String) i.next();
            AccountInfo ai = getAccount(account);
            ai.setActive(setTo);
        }
    }

    /**
     * Modifies the administrator flag on the account.
     *
     * @param setTo boolean value to set to
     * @param i an iterator filled with AccountInfo
     * @exception MailManagerException if an error occurs
     */
    private void modifyAdministrator(boolean setTo, Iterator i)
        throws MailManagerException
    {
        while (i.hasNext())
        {
            String account = (String) i.next();
            AccountInfo ai = getAccount(account);
            ai.setAdministrator(setTo);
        }
    }

    /** The account info object */
    private HashMap accountInfos;
    /** The mail manager object */
    private MailManager manager;
}
