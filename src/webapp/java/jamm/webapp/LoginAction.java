package jamm.webapp;

import jamm.backend.MailManager;

import java.util.Set;
import java.util.HashSet;
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
        if (isCancelled(request))
        {
            return mapping.findForward("home");
        }
        
        ActionErrors errors = new ActionErrors();
        LoginForm form = (LoginForm) actionForm;
   
        MailManager manager = new MailManager(Globals.getLdapHost(),
                                              Globals.getLdapPort(),
                                              Globals.getLdapSearchBase());
                                              
        boolean isRoot = false;
        String userDn;
        if (form.getUsername().equals("root"))
        {
            userDn = Globals.getRootDn();
            isRoot = true;
        }
        else
        {
            userDn = manager.getDnFromMail(form.getUsername());
        }
        
        if (userDn == null)
        {
            errors.add(ActionErrors.GLOBAL_ERROR,
                       new ActionError("login.error.invalid_login"));
        }
        else
        {
            manager.setBindEntry(userDn, form.getPassword());
            if (!manager.authenticate())
            {
                errors.add(ActionErrors.GLOBAL_ERROR,
                           new ActionError("login.error.invalid_login"));
            }
        }

        if (!errors.empty())
        {
            saveErrors(request, errors);
            // Clear out password
            form.setPassword(null);
            return new ActionForward(mapping.getInput());
        }

        Set roles = new HashSet();
        if (isRoot)
        {
            roles.add(User.SITE_ADMIN_ROLE);
            roles.add(User.DOMAIN_ADMIN_ROLE);
        }
        else if (manager.isPostmaster(form.getUsername()))
        {
            roles.add(User.DOMAIN_ADMIN_ROLE);
        }

        User user = new User(form.getUsername(), userDn, form.getPassword(),
                             roles);
        HttpSession session = request.getSession();
        session.setAttribute("is_authenticated", "true");
        session.setAttribute("user", user);
        return new RedirectingActionForward(form.getDone());
    }
}
