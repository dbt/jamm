package jamm.webapp;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class AddAliasForm extends ActionForm
{
    public AddAliasForm()
    {
        mAddresses = new ArrayList();
    }

    public void setDomain(String domain)
    {
        mDomain = domain;
    }

    public String getDomain()
    {
        return mDomain;
    }
    
    public void setName(String name)
    {
        mName = name;
    }

    public String getName()
    {
        return mName;
    }

    public void setDestinations(String destinations)
    {
        mDestinations = destinations;
        StringTokenizer tokenizer = new StringTokenizer(mDestinations,
                                                        " \t\n\r\f,");
        mAddresses.clear();
        while (tokenizer.hasMoreTokens())
        {
            mAddresses.add(tokenizer.nextToken());
        }
    }

    public String getDestinations()
    {
        return mDestinations;
    }
    
    public List getDestinationAddresses()
    {
        return mAddresses;
    }

    public void setPassword(String password)
    {
        mPassword = password;
    }

    public String getPassword()
    {
        return mPassword;
    }

    public void setRetypedPassword(String retypedPassword)
    {
        mRetypedPassword = retypedPassword;
    }

    public String getRetypedPassword()
    {
        return mRetypedPassword;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mDomain = request.getParameter("domain");
        mName = null;
        mDestinations = null;
        mAddresses.clear();
        clearPasswords();
    }

    /**
     * Both passwords must match and must pass certain "bad password"
     * smoke tests.  For now, only the length of the password is
     * considered, but more elaborate tests such as dictionary tests
     * could be performed.
     *
     * @param mapping The action mapping.
     * @param request The servlet request.
     * @return Any errors.
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        if (mAddresses.size() == 0)
        {
            errors.add("destinations",
                       new ActionError(
                           "Must specify at least one destination"));
        }

        if ((mPassword != null) && (!mPassword.equals(mRetypedPassword)))
        {
            errors.add("password",
                       new ActionError("change_password.error.no_match"));
            clearPasswords();
        }
        else if ((mPassword == null) ||
                 (mPassword.length() < MINIMUM_LENGTH))
        {
            errors.add("password",
                       new ActionError("change_password.error.too_short"));
            clearPasswords();
        }

        return errors;
    }

    private void clearPasswords()
    {
        mPassword = null;
        mRetypedPassword = null;
    }
    
    /** The minimum length of the password. */
    private static final int MINIMUM_LENGTH = 5;

    private String mDomain;
    private String mName;
    private String mDestinations;
    private List mAddresses;
    private String mPassword;
    private String mRetypedPassword;
}
