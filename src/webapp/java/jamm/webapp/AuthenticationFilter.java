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
 * This filter makes sure that people are authenticated before they
 * enter into the private area.  It redirects to "/login.jsp" with
 * the argument of "done=<URL ORIGINALLY SPECIFIED>".
 *
 * @web.filter name="Authentication Filter"
 * @web.filter-mapping url-pattern="/private/*"
 */
public class AuthenticationFilter implements Filter
{
    /**
     * initializes the authentication filter.
     *
     * @param config the FilterConfig object that this Filter should use
     */
    public void init(FilterConfig config)
    {
        mConfig = config;
    }

    /**
     * Cleans up any resources the filter may have taken.
     */
    public void destroy()
    {
        mConfig = null;
    }

    /**
     * Checks to see if AUTHENTICATION_KEY is set in the session. If
     * not it redirects to "/login.jsp?done=<ORIGINAL URL CALL>".  If
     * AUTHENTICATION_KEY is present it passes the request and
     * response to the next level of the FilterChain.
     *
     * @param servletRequest the request we're filtering.
     * @param servletResponse the response of the servlet transaction
     * @param chain the filter chain
     *
     * @exception IOException if an error occurs
     * @exception ServletException if an error occurs
     */
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
                StringBuffer done = new StringBuffer(request.getServletPath());
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

    /** The configuration for the filter. */
    private FilterConfig mConfig;
    /** The static string for use in the session attributes. */
    private static final String AUTHENTICATION_KEY = "is_authenticated";
}
