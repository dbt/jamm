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

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * Override Struts's ExceptionHandler to log the exception via log4j as well
 * as print the stracktrace to a string and stick it in the requst.  With this,
 * error pages can put the stack trace in as a HTML comment if they so desire.
 * 
 * @author kgarner
 */
public class JammExceptionHandler extends ExceptionHandler
{
    public ActionForward execute(Exception exception, ExceptionConfig exConfig,
                                 ActionMapping mapping, ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse rename)
        throws ServletException
    {
        LOG.error("Caught Error", exception);
        
        // Give the stack trace to the page
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        pw.close();
        request.setAttribute(EXCEPTION_STACKTRACE, sw.toString());
        
        return super.execute(exception, exConfig, mapping, actionForm, request,
                             rename);
    }
    
    /** Our logging facility. */
    private static final Logger LOG =
        Logger.getLogger(JammExceptionHandler.class);
    /** The name of the KEY when placed into the request. */
    public static final String EXCEPTION_STACKTRACE = "exception.stacktrace";
}
