package jamm.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.RedirectingActionForward;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

public class LoginAction extends Action
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        ActionErrors errors = new ActionErrors();
        LoginForm form = (LoginForm) actionForm;
   
        return mapping.findForward("home");
    }
}
