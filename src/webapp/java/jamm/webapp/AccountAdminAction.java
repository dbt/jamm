package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;

public class AccountAdminAction extends JammAction
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        User user = getUser(request);
        String mail = request.getParameter("mail");
        if (mail == null)
        {
            mail = user.getUsername();
        }

        MailManager manager = getMailManager(user);
        if (manager.isAlias(mail))
        {
            String[] destinations = manager.getAliasDestinations(mail);

            request.setAttribute("mail", mail);
            request.setAttribute("destinations", destinations);

            return (mapping.findForward("alias_admin"));
        }
        else
        {
            request.setAttribute("mail", mail);
            return (mapping.findForward("account_admin"));
        }
    }
}
