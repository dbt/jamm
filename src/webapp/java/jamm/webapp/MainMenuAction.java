package jamm.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

public class MainMenuAction extends Action
{
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        if (isDomainAdmin(request))
        {
            return (mapping.findForward("domain_menu"));
        }
        else if (isAccountAdmin(request))
        {
            return (mapping.findForward("account_admin"));
        }
        else if (isAliasAdmin(request))
        {
            return (mapping.findForward("alias_admin"));
        }
        else
        {
            return (mapping.findForward("alias_admin"));
        }
    }

    private boolean isDomainAdmin(HttpServletRequest request)
    {
        String type = request.getParameter("t");
        return (type == null);
    }

    private boolean isAccountAdmin(HttpServletRequest request)
    {
        String type = request.getParameter("t");
        return type.equals("a");
    }

    private boolean isAliasAdmin(HttpServletRequest request)
    {
        String type = request.getParameter("t");
        return type.equals("f");
    }
}
