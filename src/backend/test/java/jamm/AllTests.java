package jamm;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.extensions.TestSetup;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;
import javax.naming.directory.SearchControls;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite;
        TestSetup wrapper;

        suite = new TestSuite();
        suite.addTest(jamm.ldap.AllTests.suite());
        suite.addTest(jamm.backend.AllTests.suite());
        
                // This wraps all lower test suites around global setup and
        // tear down routines.
        wrapper = new TestSetup(suite)
            {
                public void setUp()
                {
                    try
                    {
                        oneTimeSetUp();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }

                public void tearDown()
                {
                    oneTimeTearDown();
                }
            };
        
        return wrapper;
    }

    private static void oneTimeSetUp()
        throws Exception
    {
        setupLdapData();
    }

    private static void setupLdapData()
        throws Exception
    {
        Hashtable   env;
        DirContext  context;
        DirContext  element;
        BasicAttributes attributes;
        BasicAttribute objectClass;

        // Bind as manager
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, 
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL,
                "ldap://" + LdapConstants.HOST + ":" + LdapConstants.PORT +
                "/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, LdapConstants.MGR_DN);
        env.put(Context.SECURITY_CREDENTIALS, LdapConstants.MGR_PW);
        context = new InitialDirContext(env);

        // Destroy all elements
        destroySubtree(context, "dc=jamm, dc=test");

        // Add root
        attributes = new BasicAttributes();
        objectClass = new BasicAttribute("objectClass");
        objectClass.add("top");
        attributes.put(objectClass);
        element = context.createSubcontext("dc=jamm, dc=test",
                                           attributes);
        element.close();

        // Add manager
        attributes = new BasicAttributes();
        objectClass = new BasicAttribute("objectClass");
        objectClass.add("top");
        objectClass.add("organizationalRole");
        attributes.put(objectClass);
        attributes.put("cn", "Manager");
        element = context.createSubcontext("cn=Manager,dc=jamm, dc=test",
                                           attributes);
        element.close();

        // Add hosting
        attributes = new BasicAttributes();
        objectClass = new BasicAttribute("objectClass");
        objectClass.add("top");
        objectClass.add("organization");
        attributes.put(objectClass);
        attributes.put("o", "hosting");
        element = context.createSubcontext("o=hosting, dc=jamm, dc=test",
                                           attributes);
        element.close();

        // Add domain1.test
        attributes = new BasicAttributes();
        objectClass = new BasicAttribute("objectClass");
        objectClass.add("top");
        objectClass.add("JammVirtualDomain");
        attributes.put(objectClass);
        attributes.put("jvd", "domain1.test");
        attributes.put("postfixTransport", "virtual:");
        element = context.createSubcontext(
            "jvd=domain1.test, o=hosting, dc=jamm, dc=test", attributes);
        element.close();

        // Add acct1@domain1.test
        attributes = new BasicAttributes();
        objectClass = new BasicAttribute("objectClass");
        objectClass.add("top");
        objectClass.add("JammMailAccount");
        attributes.put(objectClass);
        attributes.put("mail", "acct1@domain1.test");
        attributes.put("homeDirectory", "/home/vmail/domains");
        attributes.put("mailbox", "domain1.test/acct1");
        attributes.put("userPassword",
                       "{SSHA}tk3w4vV6xghX4r7P0F1EAeA55jo53sSO");
        element = context.createSubcontext(
            "mail=acct1@domain1.test, jvd=domain1.test, o=hosting, dc=jamm, " +
            "dc=test", attributes);
        element.close();

        // Add acct2@domain1.test
        attributes = new BasicAttributes();
        objectClass = new BasicAttribute("objectClass");
        objectClass.add("top");
        objectClass.add("JammMailAccount");
        attributes.put(objectClass);
        attributes.put("mail", "acct2@domain1.test");
        attributes.put("homeDirectory", "/home/vmail/domains");
        attributes.put("mailbox", "domain1.test/acct2");
        attributes.put("userPassword",
                       "{SSHA}z0pxwHQV6nvrFLMW07ZgOqjoFRPWzoPk");
        element = context.createSubcontext(
            "mail=acct2@domain1.test, jvd=domain1.test, o=hosting, dc=jamm, " +
            "dc=test", attributes);
        element.close();
    }

    private static void destroySubtree(DirContext context, String dn)
        throws NamingException
    {
        SearchControls controls;
        NamingEnumeration results;
        SearchResult element;
        String rdn;
        
        controls = new SearchControls();
        controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        results = context.search(dn, "objectClass=*", controls);

        while (results.hasMore())
        {
            element = (SearchResult) results.next();
            rdn = element.getName();
            if (rdn.equals(""))
            {
                rdn = dn;
            }
            else
            {
                rdn = rdn + "," + dn;
            }

            destroySubtree(context, rdn);
        }

        context.destroySubcontext(dn);
    }
    
    private static void oneTimeTearDown()
    {
    }
}
