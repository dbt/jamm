package jamm.backend;

import java.util.Set;
import java.util.HashSet;
import javax.naming.NamingException;

import junit.framework.TestCase;

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
        Set expectedObjectClass;
        Set objectClass;

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

                expectedObjectClass = new HashSet();
                expectedObjectClass.add("top");
                expectedObjectClass.add("organizationalRole");
                expectedObjectClass.add("JammMailAlias");
                objectClass = mLdap.getAllResultAttributeValues("objectClass");
                assertEquals("Checking postmaster objectClass",
                             expectedObjectClass, objectClass);
            }
            if (mLdap.getResultName().equals("mail=abuse@" + domain))
            {
                assertEquals("Checking abuse mail",
                             "abuse@" + domain,
                             mLdap.getResultAttribute("mail"));
                assertEquals("Checking abuse maildrop",
                             "postmaster",
                             mLdap.getResultAttribute("maildrop"));
                expectedObjectClass = new HashSet();
                expectedObjectClass.add("top");
                expectedObjectClass.add("JammMailAlias");
                objectClass = mLdap.getAllResultAttributeValues("objectClass");
                assertEquals("Checking abuse objectClass",
                             expectedObjectClass, objectClass);
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
        String domain;
        String domainDn;
        String aliasName;
        String aliasDestination;
        String email;
        Set expectedObjectClass;
        Set objectClass;

        domain = "aliadomainDn.test";
        domainDn = "jvd=" + domain + "," + BASE;
        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
        manager.createDomain(domain);

        aliasName = "alias";
        aliasDestination = "postmaster";
        manager.createAlias(domain, aliasName, aliasDestination);

        email = aliasName + "@" + domain;

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchOneLevel(domainDn, "mail=" + email);

        assertTrue("Checking for alias", mLdap.nextResult());
        assertEquals("Checking alias mail", email,
                     mLdap.getResultAttribute("mail"));
        assertEquals("Checking alias maildrop", "postmaster",
                     mLdap.getResultAttribute("maildrop"));
        assertTrue("Checking for no more aliases", !mLdap.nextResult());

        expectedObjectClass = new HashSet();
        expectedObjectClass.add("top");
        expectedObjectClass.add("JammMailAlias");
        objectClass = mLdap.getAllResultAttributeValues("objectClass");
        assertEquals("Checking alias object classes", expectedObjectClass,
                     objectClass);

        mLdap.close();
    }

    public void testModifyAlias()
        throws NamingException, MailManagerException
    {
        String domain = "modify-alias.test";
        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);

        String aliasName = "alias";
        String aliasMail = aliasName + "@" + domain;
        manager.createDomain(domain);
        manager.createAlias(domain, aliasName, "mail1@abc.test");
        manager.modifyAlias(domain, aliasName, "mail2@xyz.test");

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchSubtree(BASE, "mail=" + aliasMail);
        assertTrue("Checking for a result", mLdap.nextResult());
        assertEquals("Checking alias mail", "mail2@xyz.test",
                     mLdap.getResultAttribute("maildrop"));
    }

    public void testCreateAccount()
        throws NamingException, MailManagerException
    {
        MailManager manager;
        String domain = "accountdomain.test";
        String domainDn = "jvd=" + domain + "," + BASE;
        String accountName;
        String accountPassword;
        String email;
        Set expectedObjectClass;
        Set objectClass;

        domain = "accountdomain.test";
        domainDn = "jvd=" + domain + "," + BASE;
        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
        manager.createDomain(domain);

        accountName = "account";
        accountPassword = "account1pw";
        manager.createAccount(domain, accountName, accountPassword);

        email = accountName + "@" + domain;

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

        expectedObjectClass = new HashSet();
        expectedObjectClass.add("top");
        expectedObjectClass.add("JammMailAccount");
        objectClass = mLdap.getAllResultAttributeValues("objectClass");
        assertEquals("Checking alias objectClass", expectedObjectClass,
                     objectClass);

        mLdap.close();

        // Make sure we can bind as this new user
        mLdap.simpleBind("mail=" + email + "," + domainDn,
                         accountPassword);
        mLdap.close();
    }

    public void testAuthenticate()
        throws MailManagerException
    {
        String domain = "authenticatedomain.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String accountName = "account";
        String accountPassword = "account1pw";
        manager.createAccount(domain, accountName, accountPassword);

        String mail = accountName + "@" + domain;
        String accountDn = "mail=" + mail + "," + domainDn;

        // Create a manager against the new account
        manager.setBindEntry(accountDn, accountPassword);
        assertTrue("Checking authentication of " + accountDn,
                   manager.authenticate());

        manager.setBindEntry(accountDn, "bad password");
        assertTrue("Checking non-authentication of " + accountDn,
                   !manager.authenticate());

        manager.setBindEntry(accountDn, "");
        assertTrue("Checking non-authentication of " + accountDn,
                   !manager.authenticate());
    }

    public void testFindByMail()
        throws MailManagerException
    {
        String domain = "find-by-mail-domain.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String accountName = "account";
        String accountPassword = "account1pw";
        manager.createAccount(domain, accountName, accountPassword);

        String mail = accountName + "@" + domain;
        String accountDn = "mail=" + mail + "," + domainDn;

        String foundDn = manager.findByMail(mail);
        assertEquals("Checking found DN", accountDn, foundDn);

        String unknownMail = "no_account@" + domain;
        foundDn = manager.findByMail(unknownMail);
        assertNull("Checking DN not found for " + unknownMail, foundDn);
    }

    public void testAddCatchall()
        throws NamingException, MailManagerException
    {
        MailManager manager;
        String domain = "catchalldomain.test";
        String domainDn = "jvd=" + domain + "," + BASE;
        Set expectedObjectClass;
        Set objectClass;

        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
        manager.createDomain(domain);

        manager.addCatchall(domain, "postmaster");

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchOneLevel(domainDn, "mail=@" + domain);

        assertTrue("Checking for catchall", mLdap.nextResult());
        assertEquals("Checking catchall mail", "@" + domain,
                     mLdap.getResultAttribute("mail"));
        assertEquals("Checking catchall maildrop", "postmaster",
                     mLdap.getResultAttribute("maildrop"));

        objectClass = mLdap.getAllResultAttributeValues("objectClass");
        expectedObjectClass = new HashSet();
        expectedObjectClass.add("top");
        expectedObjectClass.add("JammMailAlias");
        assertEquals("Checking alias objectClass", expectedObjectClass,
                     objectClass);
        
        assertTrue("Checking for lack of additional catchall",
                   !mLdap.nextResult());

        mLdap.close();
    }

    public void testChangePassword()
        throws MailManagerException
    {
        String domain = "change-password.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String accountName = "account";
        String originalPassword = "account1pw";
        String newPassword1 = "changed pw";
        String newPassword2 = "another pw";
        String mail = accountName + "@" + domain;
        String accountDn = "mail=" + mail + "," + domainDn;

        manager.createAccount(domain, accountName, originalPassword);
        manager.changePassword(mail, newPassword1);

        manager.setBindEntry(accountDn, newPassword1);
        assertTrue("Checking authentication using new password 1",
                   manager.authenticate());
        manager.changePassword(mail, newPassword2);
        assertTrue("Checking authentication using new password 2",
                   manager.authenticate());
        manager.setBindEntry(accountDn, originalPassword);
        assertTrue("Checking non-authentication using original password",
                   !manager.authenticate());
        manager.setBindEntry(accountDn, newPassword1);
        assertTrue("Checking non-authentication using new password 1",
                   !manager.authenticate());
        manager.setBindEntry(accountDn, newPassword2);
        assertTrue("Double checking new password 2",
                   manager.authenticate());
    }

    private LdapFacade                      mLdap;
    private static final String BASE = "o=hosting,dc=jamm,dc=test";
}
