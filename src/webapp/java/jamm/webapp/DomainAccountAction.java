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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

/**
 * It should call MailManager to perform the assigned actions on
 * accounts from a domain.  Currently, it prints out/logs the changes
 * to the checkboxes and then forwards to user_home.
 *
 * @see jamm.webapp.DomainConfigForm
 */
public class DomainAccountAction extends Action
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

        System.out.println("====================================" +
                           "====================================");
        
        System.out.println("Delete: " +
                           Arrays.asList(form.getItemsToDelete()));
        System.out.println("Unchecked active: " +
                           form.getUncheckedActiveItems());
        System.out.println("Checked active: " +
                           form.getCheckedActiveItems());

        System.out.println("Unchecked admin: " +
                           form.getUncheckedAdminItems());
        System.out.println("Checked admin: " +
                           form.getCheckedAdminItems());

        return mapping.findForward("user_home");
    }
}
