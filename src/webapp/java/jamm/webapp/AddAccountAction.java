package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;

public class AddAccountAction extends JammAction
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        if (isCancelled(request))
        {
            return mapping.findForward("user_home");
        }

        AddAccountForm form = (AddAccountForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);

        manager.createAccount(form.getDomain(), form.getName(),
                              form.getPassword());

        return mapping.findForward("user_home");
    }
}
