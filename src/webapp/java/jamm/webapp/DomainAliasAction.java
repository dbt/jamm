package jamm.webapp;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;

public class DomainAliasAction extends JammAction
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        DomainConfigForm form = (DomainConfigForm) actionForm;
        User user = getUser(request);
        MailManager manager = getMailManager(user);

        System.out.println("====================================" +
                           "====================================");
        
        System.out.println("Delete: " +
                           Arrays.asList(form.getItemsToDelete()));
        System.out.println("Unchecked active: " +
                           form.getUncheckedActiveItems());
        System.out.println("Checked active: " +
                           form.getCheckedActiveItems());

        System.out.println("Unchecked admin: " +
                           form.getUncheckedAdminItems());
        System.out.println("Checked admin: " +
                           form.getCheckedAdminItems());

        String[] deletions = form.getItemsToDelete();
        for (int i = 0; i < deletions.length; i++)
        {
            manager.deleteAlias(deletions[i]);
        }

        return mapping.findForward("user_home");
    }
}
