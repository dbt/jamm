
package jamm.webapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Performs all necessary one-time initializations for the web
 * application.
 *
 * @web:servlet name="init-servlet" load-on-startup="1"
 */
public class InitServlet extends HttpServlet
{
    public void init(ServletConfig config)
        throws ServletException
    {
        loadProperties(config);
    }

    private void loadProperties(ServletConfig config)
        throws ServletException
    {
        String path;
        Properties properties;
        
        try
        {
            path = config.getServletContext().getRealPath("WEB-INF/" +
                                                          "jamm.properties");
            properties = new Properties();
            properties.load(new FileInputStream(path));

            Globals.setLdapHost(
                getStringProperty(properties, "ldap.host", "localhost"));
            Globals.setLdapPort(
                getIntProperty(properties, "ldap.port", 389));
            Globals.setLdapSearchBase(
                getStringProperty(properties, "ldap.search_base", ""));
            Globals.setLdapQueryFilter(
                getStringProperty(properties, "ldap.query_filter", ""));
        }
        catch (IOException e)
        {
            throw new ServletException(e);
        }
        
    }

    private String getStringProperty(Properties props,
                                     String property, String defaultValue)
    {
        return props.getProperty("jamm." + property, defaultValue).trim();
    }

    private int getIntProperty(Properties props,
                               String property, int defaultValue)
    {
        String value;

        value  = props.getProperty("jamm." + property);
        if (value != null)
            return Integer.parseInt(value);
        else
            return defaultValue;
    }
}
