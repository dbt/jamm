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

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException
    {
        if (request instanceof HttpServletRequest)
        {
            HttpServletRequest hreq = (HttpServletRequest) request;
            HttpServletResponse hresp = (HttpServletResponse) response;
            HttpSession session = hreq.getSession();

            if ((session == null) ||
                (session.getAttribute(AUTHENTICATION_KEY) == null))
            {
                hresp.sendRedirect(hreq.getContextPath() + "/login.jsp?done=" +
                                   hreq.getRequestURL().toString());
                return;
            }
        }

        // User is authenticated
        chain.doFilter(request, response);
    }

    private void validateUser(HttpServletRequest request,
                              HttpServletResponse response)
        throws IOException, ServletException
    {
        
    }

    private FilterConfig mConfig;
    private static final String AUTHENTICATION_KEY = "is_authenticated";
}
