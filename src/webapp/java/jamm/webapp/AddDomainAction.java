package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.MailAddress;

public class AddDomainAction extends JammAction
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

        AddDomainForm form = (AddDomainForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);

        manager.createDomain(form.getDomain());
        if (!form.isPasswordEmpty())
        {
            String mail = MailAddress.addressFromParts("postmaster",
                                                       form.getDomain());
            manager.changePassword(mail, form.getPassword());
        }

        return mapping.findForward("user_home");
    }
}
