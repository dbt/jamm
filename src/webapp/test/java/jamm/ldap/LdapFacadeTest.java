package jamm.ldap;

import java.util.Set;
import java.util.HashSet;

import javax.naming.NamingException;
import javax.naming.AuthenticationException;
import javax.naming.InvalidNameException;

import junit.framework.TestCase;

public class LdapFacadeTest extends TestCase
{
    public LdapFacadeTest(String name)
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

    public void testAnonymousBind()
        throws NamingException
    {
        mLdap = new LdapFacade("localhost");
        mLdap.anonymousBind();
        mLdap.close();
    }

    public void testSimpleBind()
        throws NamingException
    {
        mLdap = new LdapFacade("localhost");

        mLdap.simpleBind(MGR_DN, MGR_PW);
        mLdap.close();

        mLdap.simpleBind(MGR1_DN, MGR1_PW);
        mLdap.close();

        mLdap.simpleBind(MGR2_DN, MGR2_PW);
        mLdap.close();

        mLdap.simpleBind(ACCT1_DN, ACCT1_PW);
        mLdap.close();

        mLdap.simpleBind(ACCT3_DN, ACCT3_PW);
        mLdap.close();

        try
        {
            mLdap.simpleBind(MGR_DN, "badpw");
        }
        catch (AuthenticationException e)
        {
            // Expected
        }

        try
        {
            mLdap.simpleBind("baddn", MGR_PW);
        }
        catch (InvalidNameException e)
        {
            // Expected
        }
    }

    public void testGetElementInfo()
        throws NamingException
    {
        mLdap = new LdapFacade("localhost");

        mLdap.simpleBind(ACCT1_DN, ACCT1_PW);
        assertEquals("Checking account1 DN", ACCT1_DN, mLdap.getName());
        assertEquals("Checking mail", "acct1@domain1.net",
                     mLdap.getAttribute("mail"));
        assertEquals("Checking homeDirectory", "/home/vmail/domains",
                     mLdap.getAttribute("homeDirectory"));
        assertEquals("Checking uidNumber", "101",
                     mLdap.getAttribute("uidNumber"));
        assertEquals("Checking gidNumber", "101",
                     mLdap.getAttribute("gidNumber"));
        assertEquals("Checking mailbox", "domain1.net/acct1",
                     mLdap.getAttribute("mailbox"));
    }

    public void testSearchOneLeve()
        throws NamingException
    {
        Set expectedResults;
        Set results;
        
        mLdap = new LdapFacade("localhost");

        expectedResults = new HashSet();
        expectedResults.add(DOMAIN1_DN);
        expectedResults.add(DOMAIN2_DN);

        results = new HashSet();

        mLdap.anonymousBind();
        mLdap.searchOneLevel("ou=email,dc=jamm,dc=local",
                             "objectclass=top");

        while (mLdap.nextResult())
        {
            results.add(mLdap.getResultName());
        }

        assertEquals("Checking results of one level search, " +
                     "(objectclass=top) ",
                     expectedResults, results);
    }

    public void testSearchSubtree()
        throws NamingException
    {
        Set expectedResults;
        Set results;
        
        mLdap = new LdapFacade("localhost");

        expectedResults = new HashSet();
        expectedResults.add("ou=email,dc=jamm,dc=local");
        expectedResults.add(DOMAIN1_DN);
        expectedResults.add(ACCT1_DN);
        expectedResults.add(ACCT2_DN);
        expectedResults.add(DOMAIN2_DN);
        expectedResults.add(ACCT3_DN);

        results = new HashSet();

        mLdap.anonymousBind();
        mLdap.searchSubtree("ou=email,dc=jamm,dc=local",
                            "objectclass=top");

        while (mLdap.nextResult())
        {
            results.add(mLdap.getResultName());
        }

        assertEquals("Checking results of subtree search, " +
                     "(objectclass=CourierMailAccount)",
                     expectedResults, results);
    }

    private LdapFacade mLdap;

    private static final String MGR_DN = "cn=Manager,dc=jamm,dc=local";
    private static final String MGR_PW = "jammtest";

    private static final String DOMAIN1_DN =
        "ou=domain1.net,ou=email,dc=jamm,dc=local";

    private static final String MGR1_DN =
        "cn=Manager,ou=domain1.net,ou=email,dc=jamm,dc=local";
    private static final String MGR1_PW = "mgr1pw";

    private static final String ACCT1_DN =
        "mail=acct1@domain1.net,ou=domain1.net,ou=email,dc=jamm,dc=local";
    private static final String ACCT1_PW = "acct1pw";

    private static final String ACCT2_DN =
        "mail=acct2@domain1.net,ou=domain1.net,ou=email,dc=jamm,dc=local";

    private static final String DOMAIN2_DN =
        "ou=domain2.com,ou=email,dc=jamm,dc=local";

    private static final String MGR2_DN =
        "cn=Manager,ou=domain2.com,ou=email,dc=jamm,dc=local";
    private static final String MGR2_PW = "mgr2pw";
    
    private static final String ACCT3_DN =
        "mail=acct3@domain2.com,ou=domain2.com,ou=email,dc=jamm,dc=local";
    private static final String ACCT3_PW = "acct3pw";
}
