/*
 * Created on Aug 13, 2004
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
    
    private static final Logger LOG =
        Logger.getLogger(JammExceptionHandler.class);
    private static final String EXCEPTION_STACKTRACE = "exception.stacktrace";
}
