package jamm.ldap;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite;

        suite = new TestSuite();
        suite.addTestSuite(CryptPasswordTest.class);
        suite.addTestSuite(Md5PasswordTest.class);
        suite.addTestSuite(ShaPasswordTest.class);
        return suite;
    }
}
