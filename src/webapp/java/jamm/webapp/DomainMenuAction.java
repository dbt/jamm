package jamm.webapp;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

public class DomainMenuAction extends Action
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        request.setAttribute("domainName", "example.com");
        
        List accounts = new ArrayList();
        for (int i = 1; i <= 6; i++)
        {
            accounts.add("account" + i);
        }
        request.setAttribute("accounts", accounts);

        List aliases = new ArrayList();
        for (int i = 1; i <= 7; i++)
        {
            AliasDetails alias = new AliasDetails("alias" + i);
            for (int j = 1; j <= 3; j++)
            {
                alias.addDestination("user" + j + "@example.com");
            }

            aliases.add(alias);
        }
        request.setAttribute("aliases", aliases);

        return (mapping.findForward("domain_menu"));
    }
}
