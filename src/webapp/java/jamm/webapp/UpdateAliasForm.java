package jamm.webapp;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;


/**
 * Hold alias update information
 */
public class UpdateAliasForm extends ActionForm
{
    public void setDeleted(String[] deleted)
    {
        mDeleted = deleted;
    }

    public String[] getDeleted()
    {
        return mDeleted;
    }

    public void setAdded(String added)
    {
        mAdded = added;
    }

    public String getAdded()
    {
        return mAdded;
    }

    /**
     *
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mDeleted = new String[0];
        mAdded = "";
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    private String[] mDeleted;
    private String mAdded;

}
