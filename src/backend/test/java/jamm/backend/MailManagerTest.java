/*
 * Jamm
 * Copyright (C) 2002 Dave Dribin and Keith Garner
 *  
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jamm.backend;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import javax.naming.NamingException;

import junit.framework.TestCase;

import jamm.ldap.LdapFacade;
import jamm.LdapConstants;

/**
 * Unit test for the {@link MailManager} class.
 */
public class MailManagerTest extends TestCase
{
    /**
     * Standard JUnit constructor.
     */
    public MailManagerTest(String name)
    {
        super(name);
    }

    /**
     * Ensures LDAP facade reference is null.
     */
    protected void setUp()
    {
        mLdap = null;
    }

    /**
     * Closes an open LDAP facade.
     */
    protected void tearDown()
    {
        if (mLdap != null)
        {
            mLdap.close();
            mLdap = null;
        }
    }

    /**
     * Tests creating a domain.
     */
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

        assertEquals("Checking editAliases",
                     "TRUE",
                     mLdap.getResultAttribute("editAliases"));
        assertEquals("Checking editAccounts",
                     "TRUE",
                     mLdap.getResultAttribute("editAccounts"));
        assertEquals("Checking editPostmasters",
                     "TRUE",
                     mLdap.getResultAttribute("editPostmasters"));
        assertEquals("Checking editCatchalls",
                     "TRUE",
                     mLdap.getResultAttribute("editCatchalls"));


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

    /**
     * Tests creating an alias.
     */
    public void testCreateAlias()
        throws NamingException, MailManagerException
    {
        MailManager manager;
        String domain;
        String domainDn;
        String aliasName;
        String email;
        Set expectedObjectClass;
        Set objectClass;

        domain = "aliadomainDn.test";
        domainDn = "jvd=" + domain + "," + BASE;
        manager = new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                                  LdapConstants.MGR_PW);
        manager.createDomain(domain);

        aliasName = "alias";
        manager.createAlias(domain, aliasName, new String[] {"postmaster"});

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

    /**
     * Tests modifying an alias.
     */
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
        manager.createAlias(domain, aliasName,
                            new String[] {"mail1@abc.test"});
        AliasInfo alias = manager.getAlias(aliasMail);
        alias.setDestinations(new String[] {"mail2@xyz.test",
                                            "mail3@mmm.test"});
        manager.modifyAlias(alias);

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchSubtree(BASE, "mail=" + aliasMail);
        assertTrue("Checking for a result", mLdap.nextResult());
        Set expectedMaildrops = new HashSet();
        expectedMaildrops.add("mail2@xyz.test");
        expectedMaildrops.add("mail3@mmm.test");
        assertEquals("Checking alias mail", expectedMaildrops,
                     mLdap.getAllResultAttributeValues("maildrop"));
    }

    /**
     * Tests retrieving data for an alias.
     */
    public void testGetAlias()
        throws MailManagerException
    {
        String domain = "get-alias-dest.test";
        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);

        String aliasName = "alias";
        String aliasMail = aliasName + "@" + domain;
        manager.createDomain(domain);
        manager.createAlias(domain, aliasName,
                            new String[] {"mail2@xyz.test", "mail1@abc.test"});

        AliasInfo alias = manager.getAlias(aliasMail);
        List destinations = alias.getDestinations();
        assertEquals("Checking number of destinations", 2,
                     destinations.size());
        String destination = (String) destinations.get(0);
        assertEquals("Checking destination", "mail1@abc.test", destination);
        destination = (String) destinations.get(1);
        assertEquals("Checking destination", "mail2@xyz.test", destination);

        // Test for alias that doesn't exist.  Make sure null is returned
        alias = manager.getAlias("noalias@" + domain);
        assertNull("Checking return null on no alias", alias);
    }

    /**
     * Tests deleting an alias.
     */
    public void testDeleteAlias()
        throws NamingException, MailManagerException
    {
        String domain = "del-alias.test";
        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);

        String aliasName = "alias";
        String aliasMail = aliasName + "@" + domain;
        manager.createDomain(domain);
        
        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchSubtree(BASE, "mail=" + aliasMail);
        assertTrue("Checking for no results", !mLdap.nextResult());

        manager.createAlias(domain, aliasName,
                            new String[] {"mail2@xyz.test", "mail1@abc.test"});
        mLdap.searchSubtree(BASE, "mail=" + aliasMail);
        assertTrue("Checking for a results", mLdap.nextResult());
        assertTrue("Checking for no more results", !mLdap.nextResult());

        manager.deleteAlias(aliasMail);
        mLdap.searchSubtree(BASE, "mail=" + aliasMail);
        assertTrue("Checking alias is deleted", !mLdap.nextResult());
    }

    /**
     * Tests creating an account.
     */
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

    /**
     * Tests authenticating a user.
     */
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

    /**
     * Tests getting DN of an email address.
     */
    public void testGetDnFromMail()
        throws MailManagerException
    {
        String domain = "dn-from-mail.test";
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

        String foundDn = manager.getDnFromMail(mail);
        assertEquals("Checking found DN", accountDn, foundDn);

        String unknownMail = "no_account@" + domain;
        foundDn = manager.getDnFromMail(unknownMail);
        assertNull("Checking DN not found for " + unknownMail, foundDn);
    }

    /**
     * Tests adding a catch-all alias.
     */
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

    /**
     * Tests changing passwords.
     */
    public void testChangePassword()
        throws MailManagerException
    {
        String domain = "chpasswd.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String postmasterMail = "postmaster@" + domain;
        String postmasterDn = "cn=postmaster," + domainDn;
        String postmasterPassword = "pm1";
        manager.changePassword(postmasterMail, postmasterPassword);
        manager.setBindEntry(postmasterDn, postmasterPassword);
        assertTrue("Checking postmatser can authenticate",
                   manager.authenticate());

        String accountName = "account";
        String originalPassword = "account1pw";
        String newPassword1 = "changed pw";
        String newPassword2 = "another pw";
        String mail = accountName + "@" + domain;
        String accountDn = "mail=" + mail + "," + domainDn;

        manager.createAlias(domain, accountName,
                            new String[] {"mail1@abc.com", "mail2@xyz.com"});
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

    /**
     * Testing alias detection.
     */
    public void testIsAlias()
        throws MailManagerException
    {
        String domain = "is-alias.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String accountName = "account";
        String accountPassword = "accountpw";
        manager.createAccount(domain, accountName, accountPassword);
        assertTrue("Checking account is not an alias",
                   !manager.isAlias(accountName + "@" + domain));

        String aliasName = "alias";
        manager.createAlias(domain, aliasName, new String[] {"a@b.c"});
        assertTrue("Checking alias is an alias",
                   manager.isAlias(aliasName + "@" + domain));
    }

    /**
     * Testing postmaster detection.
     */
    public void testIsPostmaster()
        throws MailManagerException
    {
        String domain = "is-pm.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        manager.createDomain(domain);

        String aliasName = "alias";
        manager.createAlias(domain, aliasName, new String[] {"a@b.c"});

        assertTrue("Checking postmaster has postmaster privileges",
                   manager.isPostmaster("postmaster@" + domain));
        assertTrue("Checking alias does NOT have postmaster privileges",
                   !manager.isPostmaster(aliasName + "@" + domain));
    }

    /**
     * Tests getting all account and alias data.
     */
    public void testGetAccountsAndAliases()
        throws MailManagerException
    {
        String domain = "info.test";
        String domainDn = "jvd=" + domain + "," + BASE;

        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        manager.createDomain(domain);
        manager.changePassword("postmaster@" + domain, "pm");

        // Create some accounts
        manager.createAccount(domain, "zzz", "zzz");
        manager.createAccount(domain, "aaa", "aaa");
        manager.createAccount(domain, "MMM", "MMM");

        // Create some aliases
        manager.createAlias(domain, "zzzz", new String[]
            { "z@z.test", "M@z.test", "a@z.test"});
        manager.changePassword("zzzz@" + domain, "zzzz");
        manager.createAlias(domain, "MMMM", new String[]
            { "z@M.test", "M@M.test", "a@cMtest"});
        manager.changePassword("MMMM@" + domain, "MMMM");
        manager.createAlias(domain, "aaaa", new String[]
            { "z@a.test", "M@a.test", "a@a.test"});
        manager.changePassword("aaaa@" + domain, "aaaa");
        manager.createAlias(domain, "xxxx", new String[]
            { "z@x.test", "M@x.test", "a@x.test"});
        manager.changePassword("xxxx@" + domain, "xxxx");
        manager.createAlias(domain, "", new String[]
            { "z@x.test" });

        List accounts = manager.getAccounts(domain);
        assertEquals("Checking number of accounts", 3, accounts.size());
        AccountInfo account = (AccountInfo) accounts.get(0);
        assertEquals("Checking name for account[0]",
                     "aaa@" + domain, account.getName());
        account = (AccountInfo) accounts.get(1);
        assertEquals("Checking name for account[1]",
                     "MMM@" + domain, account.getName());
        account = (AccountInfo) accounts.get(2);
        assertEquals("Checking name for account[2]",
                     "zzz@" + domain, account.getName());

        List aliases = manager.getAliases(domain);
        assertEquals("Checking number of aliases", 4, aliases.size());
    }

    /**
     * Tests getting all domains.
     */
    public void testGetDomains()
        throws MailManagerException
    {
        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);
        List domains = manager.getDomains();
        Iterator i = domains.iterator();
        boolean domainFound = false;
        while (i.hasNext())
        {
            DomainInfo di = (DomainInfo) i.next();

            if (di.getName().equals("domain1.test"))
            {
                domainFound = true;
                assertTrue("Testing editAliases",
                           di.getCanEditAliases());
                assertTrue("Testing editAccounts",
                           di.getCanEditAccounts());
                assertTrue("Testing editPostmasters",
                           di.getCanEditPostmasters());
                assertTrue("Testing editCatchalls",
                           !di.getCanEditCatchalls());
            }
        }
        assertTrue("Checking for domain1.test", domainFound);
    }

    public void testGetDomain()
        throws MailManagerException
    {
        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);

        DomainInfo di = manager.getDomain("domain1.test");

        assertTrue("Testing editAliases",
                   di.getCanEditAliases());
        assertTrue("Testing editAccounts",
                   di.getCanEditAccounts());
        assertTrue("Testing editPostmasters",
                   di.getCanEditPostmasters());
        assertTrue("Testing editCatchalls",
                   !di.getCanEditCatchalls());
    }

    /**
     *
     * @exception NamingException if an error occurs
     * @exception MailManagerException if an error occurs
     */
    public void testModifyDomain()
        throws NamingException, MailManagerException
    {
        String domain = "modify-domain.test";
        MailManager manager =
            new MailManager("localhost", BASE, LdapConstants.MGR_DN,
                            LdapConstants.MGR_PW);

        manager.createDomain(domain);

        DomainInfo di = manager.getDomain(domain);
        di.setCanEditAliases(false);
        di.setCanEditAccounts(false);

        manager.modifyDomain(di);

        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.searchOneLevel(BASE, "jvd=" + domain);
        assertTrue("Checking for result", mLdap.nextResult());

        assertTrue("Checking editAliases",
                   !stringToBoolean(mLdap.getResultAttribute("editAliases")));
        assertTrue("Checking editAccounts",
                   !stringToBoolean(mLdap.getResultAttribute("editAccounts")));
        assertTrue("Checking editPostmasters",
                   stringToBoolean(
                       mLdap.getResultAttribute("editPostmasters")));
        assertTrue("Checking editCatchalls",
                   stringToBoolean(mLdap.getResultAttribute("editCatchalls")));
    }

    private boolean stringToBoolean(String string)
    {
        return Boolean.valueOf(string).booleanValue();
    }

    /** The LDAP facade used for most tests. */
    private LdapFacade mLdap;
    /** The base of the LDAP directory. */
    private static final String BASE = "o=hosting,dc=jamm,dc=test";
}
