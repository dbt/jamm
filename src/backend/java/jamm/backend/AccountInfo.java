package jamm.backend;

import java.io.Serializable;

public class AccountInfo implements Serializable
{
    public AccountInfo(String name, boolean active, boolean administrator)
    {
        mName = name;
        mActive = active;
        mAdministrator = administrator;
    }

    public String getName()
    {
        return mName;
    }

    public boolean isActive()
    {
        return mActive;
    }

    public boolean isAdministrator()
    {
        return mAdministrator;
    }

    private String mName;
    private boolean mActive;
    private boolean mAdministrator;
}
