package jamm.backend;

import junit.framework.TestCase;
import javax.naming.NamingException;
import jamm.ldap.LdapFacade;
import jamm.LdapConstants;

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

        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
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
                             "postmaster",
                             mLdap.getResultAttribute("cn"));
                assertEquals("Checking postmaster mail",
                             "postmaster@" + domain,
                             mLdap.getResultAttribute("mail"));
                assertEquals("Checking postmaster maildrop",
                             "postmaster",
                             mLdap.getResultAttribute("maildrop"));
            }
            if (mLdap.getResultName().equals("mail=abuse@" + domain))
            {
                assertEquals("Checking abuse mail",
                             "abuse@" + domain,
                             mLdap.getResultAttribute("mail"));
                assertEquals("Checking abuse maildrop",
                             "postmaster",
                             mLdap.getResultAttribute("maildrop"));
            }
        }
        assertEquals("Checking if we have the right amount of results",
                     2, counter);
        
        mLdap.close();
    }

    private LdapFacade                      mLdap;
    private static final String BASE = "o=hosting,dc=jamm,dc=test";
}
