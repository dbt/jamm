package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.MailAddress;

public class AddAliasAction extends JammAction
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

        AddAliasForm form = (AddAliasForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);
        String mail = MailAddress.addressFromParts(form.getName(),
                                                   form.getDomain());

        manager.createAlias(form.getDomain(), form.getName(),
                            form.getDestinationAddresses());
        
        if (!form.isPasswordEmpty())
        {
            manager.changePassword(mail, form.getPassword());
        }

        return mapping.findForward("user_home");
    }
}
