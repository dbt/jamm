package jamm.webapp;

import jamm.backend.MailManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

public class DomainAccountAction extends Action
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        DomainAccountForm form = (DomainAccountForm) actionForm;
        String[] delete = form.getAccountsToDelete();
        System.out.println("Delete length: " + delete.length);
        for (int i = 0; i < delete.length; i++)
        {
            System.out.println("delete[" + i + "]: " + delete[i]);
        }

        String[] activate = form.getAccountsToActivate();
        System.out.println("Activate length: " + activate.length);
        for (int i = 0; i < activate.length; i++)
        {
            System.out.println("activate[" + i + "]: " + activate[i]);
        }

        String[] admin = form.getAccountsToAdmin();
        System.out.println("Admin length: " + admin.length);
        for (int i = 0; i < admin.length; i++)
        {
            System.out.println("admin[" + i + "]: " + admin[i]);
        }

        String[] accounts = form.getOriginalAccounts();
        System.out.println("Accounts length: " + accounts.length);
        for (int i = 0; i < accounts.length; i++)
        {
            System.out.println("accounts[" + i + "]: " + accounts[i]);
        }

        System.out.println("Unchecked: " + form.getUnchecked());
        System.out.println("Checked: " + form.getChecked());

        return mapping.findForward("domain_admin");
    }
}
