package jamm.webapp;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LoginForm extends ActionForm
{
    public void setUsername(String username)
    {
        mUsername = username;
    }

    public String getUsername()
    {
        return mUsername;
    }

    public void setPassword(String password)
    {
        mPassword = password;
    }

    public String getPassword()
    {
        return mPassword;
    }

    public void setDone(String done)
    {
        mDone = done;
    }

    public String getDone()
    {
        return mDone;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        mUsername = null;
        mPassword = null;
        mDone = null;
    }

    protected String mUsername;
    protected String mPassword;
    protected String mDone;
}
