package jamm.backend;

import java.util.Comparator;

/**
 * Note: This is inconsistent with equals()
 */
public class AccountNameComparator implements Comparator
{
    public int compare(Object o1, Object o2)
    {
        AccountInfo account1 = (AccountInfo) o1;
        AccountInfo account2 = (AccountInfo) o2;
        return account1.getName().compareToIgnoreCase(account2.getName());
    }
}
