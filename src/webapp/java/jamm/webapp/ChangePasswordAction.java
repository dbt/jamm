package jamm.webapp;

import jamm.backend.MailManager;

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

public class ChangePasswordAction extends Action
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        ActionErrors errors = new ActionErrors();
        ChangePasswordForm form = (ChangePasswordForm) actionForm;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
   
        if (isCancelled(request))
        {
            return mapping.findForward("home");
        }
        
        MailManager manager =
            new MailManager(Globals.getLdapHost(),
                            Globals.getLdapPort(),
                            Globals.getLdapSearchBase(),
                            user.getDn(),
                            user.getPassword());

        manager.changePassword(form.getMail(), form.getPassword1());

        // Update user object stored in session with the new password,
        // if we changed our own password.
        if (form.getMail().equals(user.getUsername()))
        {
            User newUser = new User(user.getUsername(), user.getDn(),
                                    form.getPassword1());
            session.setAttribute("user", newUser);
        }
        
        return mapping.findForward("home");
    }
}
