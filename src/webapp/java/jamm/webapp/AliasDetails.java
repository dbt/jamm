package jamm.webapp;

import java.util.List;
import java.util.ArrayList;

public class AliasDetails
{
    public AliasDetails(String alias)
    {
        mAlias = alias;
        mDestinations = new ArrayList();
    }

    public String getAlias()
    {
        return mAlias;
    }

    public List getDestinations()
    {
        return mDestinations;
    }

    public void addDestination(String destination)
    {
        mDestinations.add(destination);
    }

    private String mAlias;
    private List mDestinations;
}
