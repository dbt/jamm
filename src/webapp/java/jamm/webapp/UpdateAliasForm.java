package jamm.webapp;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;


/**
 * Hold alias update information
 */
public class UpdateAliasForm extends ActionForm
{
    public void setMail(String mail)
    {
        mMail = mail;
    }

    public String getMail()
    {
        return mMail;
    }

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

    public List getAddedAddresses()
    {
        StringTokenizer tokenizer = new StringTokenizer(mAdded, " \t\n\r\f,");
        List addresses = new ArrayList();
        while (tokenizer.hasMoreTokens())
        {
            addresses.add(tokenizer.nextToken());
        }
        return addresses;
    }

    /**
     *
     */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mMail = (String) request.getAttribute("mail");
        mDeleted = new String[0];
        mAdded = "";
    }

    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    private String mMail;
    private String[] mDeleted;
    private String mAdded;
}
