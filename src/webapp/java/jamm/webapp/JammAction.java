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
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;

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
     */
    protected MailManager getMailManager(User user)
    {
        return new MailManager(Globals.getLdapHost(),
                               Globals.getLdapPort(),
                               Globals.getLdapSearchBase(),
                               user.getDn(),
                               user.getPassword());
    }
}
