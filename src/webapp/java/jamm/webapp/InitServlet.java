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
            LdapPassword.setRandomClass(
                getStringProperty(properties, "random_class",
                                  "java.security.SecureRandom"));
            Globals.setRootDn(
                getStringProperty(properties, "ldap.root_dn", ""));
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
        {
            return Integer.parseInt(value);
        }
        else
        {
            return defaultValue;
        }
    }
}
