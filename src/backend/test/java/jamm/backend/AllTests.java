package jamm.backend;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite;

        suite = new TestSuite();
        suite.addTestSuite(MailManagerTest.class);
        suite.addTestSuite(MailAddressTest.class);
        return suite;
    }
}
