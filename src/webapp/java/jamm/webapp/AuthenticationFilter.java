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

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @web:filter name="Authentication Filter"
 * @web:filter-mapping url-pattern="/private/*"
 */
public class AuthenticationFilter implements Filter
{
    public void init(FilterConfig config)
        throws ServletException
    {
        mConfig = config;
    }

    public void destroy()
    {
        mConfig = null;
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain)
        throws IOException, ServletException
    {
        if (servletRequest instanceof HttpServletRequest)
        {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response =
                (HttpServletResponse) servletResponse;
            HttpSession session = request.getSession();

            if ((session == null) ||
                (session.getAttribute(AUTHENTICATION_KEY) == null))
            {
                StringBuffer done = request.getRequestURL();
                String query = request.getQueryString();
                if (query != null)
                {
                    done.append("?").append(query);
                }
                response.sendRedirect(request.getContextPath() +
                                      "/login.jsp?done=" + done.toString());
                return;
            }
        }

        // User is authenticated
        chain.doFilter(servletRequest, servletResponse);
    }

    private FilterConfig mConfig;
    private static final String AUTHENTICATION_KEY = "is_authenticated";
}
