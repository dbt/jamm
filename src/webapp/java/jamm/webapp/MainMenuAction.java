package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

public class MainMenuAction extends JammAction
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        User user = getUser(request);
        if (user.isUserInRole(User.DOMAIN_ADMIN_ROLE))
        {
            return (mapping.findForward("domain_admin"));
        }
        else
        {
            return (mapping.findForward("account_admin"));
        }
    }
}
