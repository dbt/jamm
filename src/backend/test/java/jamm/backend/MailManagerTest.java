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
        throws NamingException, MailManagerException
    {
        MailManager manager;

        String domain = "domain3.test";
        String sdomain = "jvd=" + domain + "," + BASE;

        manager = new MailManager("localhost", BASE,
                                  "cn=Manager,dc=jamm,dc=test", "jammtest");
        manager.createDomain(domain);

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchOneLevel(BASE, "jvd=" + domain);
        assertTrue("jvd=" + domain + " hasn't been created",
                   mLdap.nextResult());


        mLdap.searchOneLevel(sdomain, "objectClass=*");
        int counter = 0;
        while (mLdap.nextResult())
        {
            counter++;
            if (mLdap.getResultName().equals("cn=postmaster," + sdomain))
            {
                assertEquals("Checking postmaster cn",
                             mLdap.getResultAttribute("cn"),
                             "postmaster");
                assertEquals("Checking postmaster mail",
                             mLdap.getResultAttribute("mail"),
                             "postmaster@" + domain);
                assertEquals("Checking postmaster maildrop",
                             mLdap.getResultAttribute("maildrop"),
                             "postmaster");
            }
            if (mLdap.getResultName().equals("mail=abuse@" + domain))
            {
                assertEquals("Checking abuse mail",
                             mLdap.getResultAttribute("mail"),
                             "abuse@" + domain);
                assertEquals("Checking abuse maildrop",
                             mLdap.getResultAttribute("maildrop"),
                             "postmaster");
            }
        }
        assertEquals("Checking if we have the right amount of results",
                     counter, 2);
        
        mLdap.close();
    }

    private LdapFacade                      mLdap;
    private static final String BASE = "o=hosting,dc=jamm,dc=test";
}
