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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jamm.backend.MailManager;

/**
 * Class provides utility functions that all/most of the Actions in
 * Jamm use.  Actions in Jamm should extend this class and use these
 * functions when needed.
 */
public abstract class JammAction extends Action
{
    /**
     * Gets the User object from the request/session and returns it.
     *
     * @param request the http request causing this action.
     *
     * @return the logged in User
     */
    protected User getUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }

    /**
     * Creates a MailManager object for the User.
     *
     * @param user the User to create a MailManager for.
     *
     * @return an initialized <code>MailManager</code>
     */
    protected MailManager getMailManager(User user)
    {
        return new MailManager(Globals.getLdapHost(),
                               Globals.getLdapPort(),
                               Globals.getLdapSearchBase(),
                               user.getDn(),
                               user.getPassword());
    }

    /**
     * Finds the forward of the specified name in the specified, but
     * adds the required parameters for the forward.  This is due to
     * the fact that a Struts <code>ActionForward</code> does not have
     * any mechanism itself for adding parameters to a forward.
     *
     * @param mapping Mapping to use when finding the forward.
     * @param name Name of the forward to find.
     * @param request Will be used to grab parameters for certain actions.
     * @return An instance to the specified forward.
     */
    protected ActionForward findForward(ActionMapping mapping, String name,
                                        HttpServletRequest request)
    {
        ActionForward forward = mapping.findForward(name);
        if (name.equals("domain_admin"))
        {
            String domain = request.getParameter("domain");
            if (domain != null)
            {
                return addParameter(forward, "domain", domain);
            }
        }
        else if (name.equals("account_admin"))
        {
            String mail = request.getParameter("mail");
            if (mail != null)
            {
                return addParameter(forward, "mail", mail);
            }
        }

        return forward;
    }

    /**
     * Adds multiple parameters to the specified forward.  Each entry
     * in the map is added as "key=value".
     *
     * @param forward The forward to add parameters to
     * @param parameters The parameters to add
     * @return A new forward with parameters.
     */
    protected ActionForward addParameters(ActionForward forward,
                                          Map parameters)
    {
        if (!parameters.isEmpty())
        {
            StringBuffer path = new StringBuffer();
            path.append(forward.getPath()).append("?");
            Iterator entries = parameters.entrySet().iterator();
            while (entries.hasNext())
            {
                Entry entry = (Entry) entries.next();
                path.append(entry.getKey()).append("=");
                path.append(entry.getValue());
            }
            return new ActionForward(path.toString(), forward.getRedirect());
        }
        else
        {
            return forward;
        }
    }

    /**
     * Adds a single parameter to the specified forward.
     *
     * @param forward The forward to add a parameter to
     * @param parameterName The name of the parameter to add
     * @param parameterValue The value of the parameter to add
     * @return A new forward with a parameter.
     */
    protected ActionForward addParameter(ActionForward forward,
                                         String parameterName,
                                         String parameterValue)
    {
        StringBuffer path = new StringBuffer();
        path.append(forward.getPath()).append("?");
        path.append(parameterName).append("=");
        path.append(parameterValue);
        return new ActionForward(path.toString(), forward.getRedirect());
    }
}
