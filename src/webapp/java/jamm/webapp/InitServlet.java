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

import jamm.ldap.LdapPassword;

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
    /**
     * Initializes the servlet and prepares it for service.
     *
     * @param config The config from the servlet container.
     *
     * @throws ServletException when the configuration fails.
     */
    public void init(ServletConfig config)
        throws ServletException
    {
        loadProperties(config);
    }

    /**
     * Loads the jamm.properties file and uses it to initalize the
     * Globals object.
     *
     * @see jamm.webapp.Globals
     *
     * @param config The configuration from the servlet container.
     *
     * @throws ServletException when their is an IOException loading
     *                          the properties file
     */
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
            LdapPassword.setRandomClass(
                getStringProperty(properties, "random_class",
                                  "java.security.SecureRandom"));
            Globals.setRootDn(
                getStringProperty(properties, "ldap.root_dn", ""));
            Globals.setPasswordUseExOp(
                getBooleanProperty(properties, "password.exop", true));
        }
        catch (IOException e)
        {
            throw new ServletException(e);
        }
        
    }

    /**
     * Returns the property as a string taken from the Properties object.
     *
     * @param props properties object containing the data
     * @param property the property name to return
     * @param defaultValue the default value to use if the property
     *                     doesn't exist in props
     *
     * @return a string containing the property's value
     */
    private String getStringProperty(Properties props,
                                     String property, String defaultValue)
    {
        return props.getProperty("jamm." + property, defaultValue).trim();
    }

    /**
     * Returns the property as an integer taken from the Properties object.
     *
     * @param props properties object containing the data
     * @param property the property name to return
     * @param defaultValue the default value to use if the property
     *                     doesn't exist in props
     *
     * @return a int with the property's value
     */
    private int getIntProperty(Properties props,
                               String property, int defaultValue)
    {
        String value;

        value  = props.getProperty("jamm." + property);
        if (value != null)
        {
            return Integer.parseInt(value);
        }
        else
        {
            return defaultValue;
        }
    }

    private boolean getBooleanProperty(Properties props, String property,
                                       boolean defaultValue)
    {
        String value;

        value = props.getProperty("jamm." + property);

        System.out.println("value: " + value);
        if (value == null)
        {
            return defaultValue;
        }
        else
        {
            return Boolean.valueOf(value).booleanValue();
        }
    }
}
