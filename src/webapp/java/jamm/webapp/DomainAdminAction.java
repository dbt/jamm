package jamm.webapp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import jamm.backend.MailManager;
import jamm.backend.AccountInfo;
import jamm.backend.AliasInfo;
import jamm.backend.MailAddress;

public class DomainAdminAction extends JammAction
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        User user  = getUser(request);
        MailManager manager = getMailManager(user);
        String domain = request.getParameter("domain");
        if (domain == null)
        {
            domain = MailAddress.hostFromAddress(user.getUsername());
        }
        request.setAttribute("domainName", domain);

        List accounts = manager.getAccounts(domain);
        request.setAttribute("accounts", accounts);

        List activeAccounts = new ArrayList();
        List adminAccounts = new ArrayList();
        Iterator i = accounts.iterator();
        while (i.hasNext())
        {
            AccountInfo account = (AccountInfo) i.next();
            if (account.isActive())
            {
                activeAccounts.add(account.getName());
            }
            if (account.isAdministrator())
            {
                adminAccounts.add(account.getName());
            }
        }

        String[] activeAccountsArray =
            (String []) activeAccounts.toArray(new String[0]);
        String[] adminAccountsArray =
            (String []) adminAccounts.toArray(new String[0]);
        DomainConfigForm dcf = new DomainConfigForm();
        dcf.setOriginalActiveItems(activeAccountsArray);
        dcf.setActiveItems(activeAccountsArray);
        dcf.setOriginalAdminItems(adminAccountsArray);
        dcf.setAdminItems(adminAccountsArray);
        request.setAttribute("domainAccountForm", dcf);

        // Prepare aliases
        List aliases = manager.getAliases(domain);
        request.setAttribute("aliases", aliases);

        List activeAliases = new ArrayList();
        List adminAliases = new ArrayList();
        i = aliases.iterator();
        while (i.hasNext())
        {
            AliasInfo alias = (AliasInfo) i.next();
            if (alias.isActive())
            {
                activeAliases.add(alias.getName());
            }
            if (alias.isAdministrator())
            {
                adminAliases.add(alias.getName());
            }
        }

        String[] activeAliasesArray =
            (String []) activeAliases.toArray(new String[0]);
        String[] adminAliasesArray =
            (String []) adminAliases.toArray(new String[0]);
        dcf = new DomainConfigForm();
        dcf.setOriginalActiveItems(activeAliasesArray);
        dcf.setActiveItems(activeAliasesArray);
        dcf.setOriginalAdminItems(adminAliasesArray);
        dcf.setAdminItems(adminAliasesArray);
        request.setAttribute("domainAliasForm", dcf);

        return (mapping.findForward("domain_admin"));
    }
}
