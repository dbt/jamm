package jamm.backend;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;

public class AliasInfo implements Serializable
{
    public AliasInfo(String name, List destinations, boolean active,
                       boolean administrator)
    {
        mName = name;
        mDestinations = destinations;
        mActive = active;
        mAdministrator = administrator;
    }

    public String getName()
    {
        return mName;
    }

    public void setDestinations(Collection destinations)
    {
        mDestinations = new ArrayList(destinations);
    }

    public void setDestinations(String[] destinations)
    {
        mDestinations = Arrays.asList(destinations);
    }

    public List getDestinations()
    {
        return Collections.unmodifiableList(mDestinations);
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
    private List mDestinations;
    private boolean mActive;
    private boolean mAdministrator;
}
