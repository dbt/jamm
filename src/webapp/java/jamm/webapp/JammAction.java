package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;

import jamm.backend.MailManager;

public abstract class JammAction extends Action
{
    protected User getUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }
            
    protected MailManager getMailManager(User user)
    {
        return new MailManager(Globals.getLdapHost(),
                               Globals.getLdapPort(),
                               Globals.getLdapSearchBase(),
                               user.getDn(),
                               user.getPassword());
    }
}
