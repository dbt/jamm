package jamm.backend;

import java.util.Comparator;

/**
 * Note: This is inconsistent with equals()
 */
public class AliasNameComparator implements Comparator
{
    public int compare(Object o1, Object o2)
    {
        AliasInfo alias1 = (AliasInfo) o1;
        AliasInfo alias2 = (AliasInfo) o2;
        return alias1.getName().compareToIgnoreCase(alias2.getName());
    }
}
