/*
 * Jamm
 * Copyright (C) 2003 Dave Dribin and Keith Garner
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

import jamm.backend.AccountInfo;
import jamm.backend.MailManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
* This action updates an alias entry with the changes as specified by the
* information in the UpdateAccountForm. Right now this is limited to
* CommonName.
* 
* @see jamm.webapp.UpdateAccountForm
* @see jamm.backend.MailManager
*/
public class UpdateAccountAction extends JammAction
{
    /**
     * Performs the action.
     *
     * @param mapping an <code>ActionMapping</code> of possible destinations
     * @param actionForm an <code>UpdateAliasForm</code> with our
     *                   required information.
     * @param request a <code>HttpServletRequest</code>
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
        UpdateAccountForm form = (UpdateAccountForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);

        String mail = form.getMail();
        AccountInfo account = manager.getAccount(mail);

        account.setCommonName(form.getCommonName());
        
        manager.modifyAccount(account);

        return findForward(mapping, "account_admin", request);
    }
}
