package jamm.backend;

import junit.framework.TestCase;
import javax.naming.NamingException;
import javax.naming.AuthenticationException;
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
        String domainDn = "jvd=" + domain + "," + BASE;

        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
        manager.createDomain(domain);

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchOneLevel(BASE, "jvd=" + domain);
        assertTrue("jvd=" + domain + " hasn't been created",
                   mLdap.nextResult());

        mLdap.searchOneLevel(domainDn, "objectClass=*");
        int counter = 0;
        while (mLdap.nextResult())
        {
            counter++;
            if (mLdap.getResultName().equals("cn=postmaster," + domainDn))
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

    public void testCreateAlias()
        throws NamingException, MailManagerException
    {
        MailManager manager;
        String domain = "aliadomainDn.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String aliasName = "alias";
        String aliasDestination = "postmaster";
        manager.createAlias(domain, aliasName, aliasDestination);

        String email = aliasName + "@" + domain;

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchOneLevel(domainDn, "mail=" + email);

        assertTrue("Checking for alias", mLdap.nextResult());
        assertEquals("Checking alias mail", email,
                     mLdap.getResultAttribute("mail"));
        assertEquals("Checking alias maildrop", "postmaster",
                     mLdap.getResultAttribute("maildrop"));
        assertTrue("Checking for no more aliases", !mLdap.nextResult());

        mLdap.close();
    }

    public void testCreateAccount()
        throws NamingException, MailManagerException
    {
        MailManager manager;
        String domain = "accountdomain.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String accountName = "account";
        String accountPassword = "account1pw";
        manager.createAccount(domain, accountName, accountPassword);

        String email = accountName + "@" + domain;

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchOneLevel(domainDn, "mail=" + email);

        assertTrue("Checking for account", mLdap.nextResult());
        assertEquals("Checking account mail", email,
                     mLdap.getResultAttribute("mail"));
        assertEquals("Checking account homeDirectory",
                     "/home/vmail/domains",
                     mLdap.getResultAttribute("homeDirectory"));
        assertEquals("Checking account mailbox",
                     domain + "/" + accountName + "/",
                     mLdap.getResultAttribute("mailbox"));
        assertTrue("Checking for no more account results",
                   !mLdap.nextResult());
        mLdap.close();

        try
        {
            mLdap.simpleBind("mail=" + email + "," + domainDn,
                             accountPassword);
        }
        catch (AuthenticationException e)
        {
            fail("Should bind without an exception");
        }
    }

    private LdapFacade                      mLdap;
    private static final String BASE = "o=hosting,dc=jamm,dc=test";
}
