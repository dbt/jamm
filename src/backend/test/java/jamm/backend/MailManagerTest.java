package jamm.backend;

import junit.framework.TestCase;
import javax.naming.NamingException;
import jamm.ldap.LdapFacade;

public class MailManagerTest extends TestCase
{
    public MailManagerTest(String name)
    {
        super(name);
    }

    protected void setUp()
    {
        mLdap = null;
    }

    protected void tearDown()
    {
        if (mLdap != null)
        {
            mLdap.close();
            mLdap = null;
        }
    }

    public void testCreateDomain()
        throws NamingException
    {
        MailManager manager;

        manager = new MailManager();
        manager.createDomain("domain1.test");

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.close();
    }

    private LdapFacade                      mLdap;
}
