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

public class DomainAccountAction extends Action
{
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
