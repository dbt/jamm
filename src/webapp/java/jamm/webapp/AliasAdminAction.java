package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;

public class AliasAdminAction extends Action
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String mail = user.getUsername();
        
        MailManager manager =
            new MailManager(Globals.getLdapHost(),
                            Globals.getLdapPort(),
                            Globals.getLdapSearchBase(),
                            user.getDn(),
                            user.getPassword());

        String[] destinations = manager.getAliasDestinations(mail);

        request.setAttribute("mail", mail);
        request.setAttribute("destinations", destinations);

        return (mapping.findForward("alias_admin"));
    }
}
