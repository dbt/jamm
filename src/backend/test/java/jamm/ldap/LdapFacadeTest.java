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

package jamm.ldap;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import javax.naming.NamingException;
import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.InvalidNameException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import junit.framework.TestCase;

import jamm.LdapConstants;
import jamm.util.CaseInsensitiveStringSet;

/**
 * Unit test for the {@link LdapFacade} class.
 */
public class LdapFacadeTest extends TestCase
{
    /**
     * Standard JUnit constructor.
     * @param name test name
     */
    public LdapFacadeTest(String name)
    {
        super(name);
    }

    /**
     * Ensures LDAP facade is null.
     */
    protected void setUp()
    {
    }

    /**
     * Closes the LDAP facade, if it is open.
     */
    protected void tearDown()
    {
    }

    /**
     * Tests an anonymous bind.
     * @throws NamingException on error
     */
    public void testAnonymousBind()
        throws NamingException
    {
        LdapFacade ldap = new LdapFacade("localhost");
        ldap.anonymousBind();
        assertNull("Checking name is null", ldap.getName());
        ldap.close();
    }

    /**
     * Tests a simple bind.
     * @throws NamingException on error
     */
    public void testSimpleBind()
        throws NamingException
    {
        LdapFacade ldap = new LdapFacade("localhost");

        ldap.simpleBind(LdapConstants.MGR_DN, LdapConstants.MGR_PW);
        assertEquals("Checking manager name", LdapConstants.MGR_DN,
                     ldap.getName());
        ldap.close();

        ldap.simpleBind(LdapConstants.ACCT1_DN, LdapConstants.ACCT1_PW);
        assertEquals("Checking account 1 name", LdapConstants.ACCT1_DN,
                     ldap.getName());
        ldap.close();

        ldap.simpleBind(ACCT2_DN, ACCT2_PW);
        assertEquals("Checking account 2 name", ACCT2_DN, ldap.getName());
        ldap.close();

        try
        {
            // Try with invalid password
            ldap.simpleBind(LdapConstants.ACCT1_DN, "badpw");
            fail("Should have thrown AuthenticationException");
        }
        catch (AuthenticationException e)
        {
            // Expected
        }

        try
        {
            // Try with invalid password
            ldap.simpleBind(LdapConstants.MGR_DN, "badpw");
            fail("Should have thrown an authentication exception");
        }
        catch (AuthenticationException e)
        {
            // Expected in JDK 1.3.1
        }
        catch (AuthenticationNotSupportedException e)
        {
            // Expected in JDK 1.4.0
        }

        try
        {
            // Try with invalid DN
            ldap.simpleBind("baddn", LdapConstants.ACCT1_PW);
            fail("Should have thrown InvalidNameException");
        }
        catch (InvalidNameException e)
        {
            // Expected
        }
    }

    /**
     * Tests getting element attributes.
     * @throws NamingException on error
     */
    public void testGetElementAttributes()
        throws NamingException
    {
        LdapFacade ldap = new LdapFacade("localhost");

        ldap.simpleBind(LdapConstants.ACCT1_DN, LdapConstants.ACCT1_PW);
        assertEquals("Checking account1 DN", LdapConstants.ACCT1_DN,
                     ldap.getName());
        assertEquals("Checking mail", "acct1@domain1.test",
                     ldap.getAttribute("mail"));
        assertEquals("Checking homeDirectory", "/home/vmail/domains",
                     ldap.getAttribute("homeDirectory"));
        assertEquals("Checking mailbox", "domain1.test/acct1",
                     ldap.getAttribute("mailbox"));
        assertNull("Checking description",
                   ldap.getAttribute("description"));
        // The password is stored as a binary object, so this test
        // ensures that the facade converts it to a string.
        assertEquals("Checking password", LdapConstants.ACCT1_PW_HASHED,
                     ldap.getAttribute("userPassword"));

        Set expectedObjectClass = new CaseInsensitiveStringSet();
        expectedObjectClass.add("top");
        expectedObjectClass.add("JammMailAccount");

        Set objectClass = ldap.getAllAttributeValues("objectClass");
        assertEquals("Checking multi-value objectClass", expectedObjectClass,
                     objectClass);

        assertTrue("Checking lower case object class",
                   objectClass.contains("jammMailAccount"));
        assertTrue("Checking upper case object class",
                   objectClass.contains("JammMailAccount"));

        Set noAttribute = ldap.getAllAttributeValues("noAttribute");
        assertEquals("Checking noAttribute has no values",
                     Collections.EMPTY_SET, noAttribute);
        
        ldap.close();
    }

    /**
     * Tests searching one leve.
     * @throws NamingException on error
     */
    public void testSearchOneLevel()
        throws NamingException
    {
        Set expectedResults;
        Set results;
        
        LdapFacade ldap = new LdapFacade("localhost");

        // There should only be these domains at this level
        expectedResults = new HashSet();
        expectedResults.add(DOMAIN1_DN);

        results = new HashSet();

        ldap.anonymousBind();
        ldap.searchOneLevel("o=hosting,dc=jamm,dc=test",
                             "objectclass=top");

        while (ldap.nextResult())
        {
            results.add(ldap.getResultName());
        }

        assertEquals("Checking results of one level search, " +
                     "(objectclass=top) ",
                     expectedResults, results);
        
        ldap.close();
    }

    /**
     * Tests searching a subtree.
     * @throws NamingException on error
     */
    public void testSearchSubtree()
        throws NamingException
    {
        Set expectedResults;
        Set results;
        
        LdapFacade ldap = new LdapFacade("localhost");

        expectedResults = new HashSet();
        expectedResults.add(DOMAIN1_DN);
        expectedResults.add(POSTMASTER_DN);
        expectedResults.add(ALIAS1_DN);
        expectedResults.add(LdapConstants.ACCT1_DN);
        expectedResults.add(ACCT2_DN);

        results = new HashSet();

        ldap.anonymousBind();
        ldap.searchSubtree(DOMAIN1_DN, "objectClass=*");

        while (ldap.nextResult())
        {
            results.add(ldap.getResultName());
        }

        assertEquals("Checking results of subtree search, " +
                     "(objectClass=jammMailAccount)",
                     expectedResults, results);
        
        ldap.close();
    }

    /**
     * Tests getting attributes from a searched result.
     * @throws NamingException on error
     */
    public void testGetResultAttributes()
        throws NamingException
    {
        LdapFacade ldap = new LdapFacade("localhost");

        ldap.simpleBind(LdapConstants.MGR_DN, LdapConstants.MGR_PW);
        ldap.searchSubtree("o=hosting,dc=jamm,dc=test",
                            "mail=acct1@domain1.test");
        assertTrue("Checking for results", ldap.nextResult());
        assertEquals("Checking account1 DN", LdapConstants.ACCT1_DN,
                     ldap.getResultName());
        assertEquals("Checking mail", "acct1@domain1.test",
                     ldap.getResultAttribute("mail"));
        assertEquals("Checking homeDirectory", "/home/vmail/domains",
                     ldap.getResultAttribute("homeDirectory"));
        assertEquals("Checking mailbox", "domain1.test/acct1",
                     ldap.getResultAttribute("mailbox"));
        assertNull("Checking description",
                   ldap.getAttribute("description"));
        // The password is stored as a binary object, so this test
        // ensures that the facade converts it to a string.
        assertEquals("Checking password", LdapConstants.ACCT1_PW_HASHED,
                     ldap.getResultAttribute("userPassword"));

        Set expectedObjectClass = new CaseInsensitiveStringSet();
        expectedObjectClass.add("top");
        expectedObjectClass.add("JammMailAccount");

        Set objectClass = ldap.getAllResultAttributeValues("objectClass");
        assertEquals("Checking multi-value objectClass", expectedObjectClass,
                     objectClass);

        assertTrue("Checking lower case object class",
                   objectClass.contains("jammMailAccount"));
        assertTrue("Checking upper case object class",
                   objectClass.contains("JammMailAccount"));

        Set description = ldap.getAllResultAttributeValues("description");
        assertEquals("Checking description has no values",
                     Collections.EMPTY_SET, description);
        assertTrue("Checking for no more results", !ldap.nextResult());
        
        ldap.close();
    }

    /**
     * Tests adding, modyfying, and deleting elements.
     * @throws NamingException on error
     */
    public void testAddModifyDeleteElement()
        throws NamingException
    {
        BasicAttribute  objectClass;
        BasicAttributes attributes;
        String  ouName;
        String  dn;

        ouName = "my_ou";
        dn = "ou=" + ouName + ",dc=jamm,dc=test";
        LdapFacade ldap = new LdapFacade("localhost");
        ldap.simpleBind(MGR_DN, MGR_PW);
        
        // This element should not exist
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should not exist",
                   !ldap.nextResult());

        // Create a new element
        attributes = new BasicAttributes();
        objectClass = new BasicAttribute("objectClass");
        objectClass.add("top");
        objectClass.add("organizationalUnit");
        attributes.put(objectClass);
        attributes.put("ou", ouName);
        attributes.put("description", "my description");
        ldap.addElement(dn, attributes);

        // See if the element exists and check the values
        ldap.resetSearch();
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should exist",
                   ldap.nextResult());

        Set expectedObjectClass = new HashSet();
        expectedObjectClass.add("top");
        expectedObjectClass.add("organizationalUnit");
        assertEquals("Checking objectClass", expectedObjectClass,
                     ldap.getAllResultAttributeValues("objectClass"));
        assertEquals("Checking ou", ouName, ldap.getResultAttribute("ou"));
        assertEquals("Checking description", "my description",
                     ldap.getResultAttribute("description"));

        // Modify the description
        ldap.modifyElementAttribute(dn, "description", "new description");
        ldap.resetSearch();
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("Checking for results", ldap.nextResult());
        assertEquals("Checking description", "new description",
                     ldap.getResultAttribute("description"));

        // Delete element
        ldap.deleteElement(dn);

        // This element should not exist
        ldap.resetSearch();
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should not exist",
                   !ldap.nextResult());
        
        ldap.close();
    }

    /**
     * Tests adding elements using a Map for attributes.
     * @throws NamingException on error
     */
    public void testAddUsingMap()
        throws NamingException
    {
        String ouName = "ou_map";
        String dn = "ou=" + ouName + ",dc=jamm,dc=test";
        LdapFacade ldap = new LdapFacade("localhost");
        ldap.simpleBind(MGR_DN, MGR_PW);
        
        // This element should not exist
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should not exist",
                   !ldap.nextResult());

        // Create a new element
        Map attributes = new HashMap();
        attributes.put("objectClass", new String[] { "top",
                                                     "organizationalUnit"});
        Set telephoneNumbers = new HashSet();
        telephoneNumbers.add("555-1234");
        telephoneNumbers.add("555-6789");
        attributes.put("telephoneNumber", telephoneNumbers);
        attributes.put("ou", ouName);
        attributes.put("description", "my description");
        ldap.addElement(dn, attributes);

        // See if the element exists and check the values
        ldap.resetSearch();
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should exist",
                   ldap.nextResult());

        Set expectedObjectClass = new HashSet();
        expectedObjectClass.add("top");
        expectedObjectClass.add("organizationalUnit");
        assertEquals("Checking objectClass", expectedObjectClass,
                     ldap.getAllResultAttributeValues("objectClass"));
        assertEquals("Checking ou", ouName, ldap.getResultAttribute("ou"));
        assertEquals("Checking description", "my description",
                     ldap.getResultAttribute("description"));

        Set expectedTelephoneNumbers = new HashSet();
        expectedTelephoneNumbers.add("555-1234");
        expectedTelephoneNumbers.add("555-6789");
        assertEquals("Checking telephoneNumber", expectedTelephoneNumbers,
                     ldap.getAllResultAttributeValues("telephoneNumber"));
        
        ldap.close();
    }

    /**
     * Tests changePassword
     * @throws NamingException on error
     */
    public void testChangePassword()
        throws NamingException
    {
        String ouName = "change_password";
        String dn = "ou=" + ouName + ",dc=jamm,dc=test";
        LdapFacade ldap = new LdapFacade("localhost");
        ldap.simpleBind(MGR_DN, MGR_PW);

        // This element should not exist
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should not exist",
                   !ldap.nextResult());

        Map attributes = new HashMap();
        attributes.put("objectClass", new String[] { "top",
                                                     "organizationalUnit"});
        attributes.put("ou", ouName);
        // "warez" == ssh value below
        attributes.put("userPassword",
                       "{SSHA}ezzH8G5cwAx+WxOp6qy72kvIV+QGuOzC");

        ldap.addElement(dn, attributes);

        ldap.resetSearch();
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should exist",
                   ldap.nextResult());

        ldap.close();

        ldap = new LdapFacade("localhost");
        ldap.simpleBind(dn, "warez");
        assertEquals("Checking manager name", dn,
                     ldap.getName());

        ldap.changePassword(dn, "testPassword");
        ldap.close();

        ldap = new LdapFacade("localhost");
        ldap.simpleBind(dn, "testPassword");
        assertEquals("Checking manager name", dn,
                     ldap.getName());
        ldap.close();
    }
        
    /**
     * Tests modyfing a multi-value attribute.
     * @throws NamingException on error
     */
    public void testModifyMultiValue()
        throws NamingException
    {
        String ouName = "mod_multi";
        String dn = "ou=" + ouName + ",dc=jamm,dc=test";
        LdapFacade ldap = new LdapFacade("localhost");
        ldap.simpleBind(MGR_DN, MGR_PW);
        
        // This element should not exist
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should not exist",
                   !ldap.nextResult());

        // Create a new element
        Map attributes = new HashMap();
        attributes.put("objectClass", new String[] { "top",
                                                     "organizationalUnit"});
        Set telephoneNumbers = new HashSet();
        telephoneNumbers.add("555-1234");
        telephoneNumbers.add("555-6789");
        attributes.put("telephoneNumber", telephoneNumbers);
        attributes.put("ou", ouName);
        attributes.put("description", "my description");
        ldap.addElement(dn, attributes);

        // Modify using Map
        telephoneNumbers.clear();
        telephoneNumbers.add("555-0666");
        telephoneNumbers.add("555-2222");
        telephoneNumbers.add("555-6759");
        ldap.modifyElementAttribute(dn, "telephoneNumber", telephoneNumbers);

        // See if the element exists and check the values
        ldap.resetSearch();
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should exist",
                   ldap.nextResult());

        Set expectedTelephoneNumbers = new HashSet();
        expectedTelephoneNumbers.add("555-0666");
        expectedTelephoneNumbers.add("555-2222");
        expectedTelephoneNumbers.add("555-6759");
        assertEquals("Checking telephoneNumber after Map modify",
                     expectedTelephoneNumbers,
                     ldap.getAllResultAttributeValues("telephoneNumber"));

        // Modify using String[]
        ldap.modifyElementAttribute(dn, "telephoneNumber",
                                     new String[] {"555-1111"});

        // Check the values
        ldap.resetSearch();
        ldap.searchOneLevel("dc=jamm,dc=test", "ou=" + ouName);
        assertTrue("ou=" + ouName + " should exist",
                   ldap.nextResult());

        expectedTelephoneNumbers.clear();
        expectedTelephoneNumbers.add("555-1111");
        assertEquals("Checking telephoneNumber after String[] modify",
                     expectedTelephoneNumbers,
                     ldap.getAllResultAttributeValues("telephoneNumber"));
        
        ldap.close();
    }

    /** mgr dn to use */
    private static final String MGR_DN = "cn=Manager,dc=jamm,dc=test";
    /** mgr password to use */
    private static final String MGR_PW = "jammtest";

    /** domain1 dn to use */
    private static final String DOMAIN1_DN =
        "jvd=domain1.test,o=hosting,dc=jamm,dc=test";

    /** postmaster dn */
    private static final String PM1_DN =
        "cn=postmaster,jvd=domain1.test,o=hosting,dc=jamm,dc=test";
    /** postmaster password */
    private static final String PM1_PW = "postmaster1pw";

    /** abuse dn */
    private static final String ABUSE1_DN =
        "mail=abuse@domain1.test,jvd=domain2.test,o=hosting,dc=jamm,dc=test";

    /** postmaster dn */
    private static final String POSTMASTER_DN =
        "cn=postmaster,jvd=domain1.test,o=hosting,dc=jamm,dc=test";

    /** alias dn */        
    private static final String ALIAS1_DN =
        "mail=alias1@domain1.test,jvd=domain1.test,o=hosting,dc=jamm,dc=test";

    /** acct2 dn */
    private static final String ACCT2_DN =
        "mail=acct2@domain1.test,jvd=domain1.test,o=hosting,dc=jamm,dc=test";
    /** acct2 pw */
    private static final String ACCT2_PW = "acct2pw";

    /** domain dn */
    private static final String DOMAIN2_DN =
        "jvd=domain2.test,o=hosting,dc=jamm,dc=test";

    /** pm2 dn */
    private static final String PM2_DN =
        "cn=postmaster,jvd=domain2.test,o=hosting,dc=jamm,dc=test";
    /** pm2 pw */
    private static final String PM2_PW = "postmaster2pw";
    
    /** abuse dn */
    private static final String ABUSE2_DN =
        "mail=abuse@domain2.test,jvd=domain2.test,o=hosting,dc=jamm,dc=test";

    /** acct3 dn */
    private static final String ACCT3_DN =
        "mail=acct3@domain2.test,jvd=domain2.test,o=hosting,dc=jamm,dc=test";
    /** acct3 pw */
    private static final String ACCT3_PW = "acct3pw";

    /** acct4 dn */
    private static final String ACCT4_DN =
        "mail=acct4@domain2.test,jvd=domain2.test,o=hosting,dc=jamm,dc=test";
    /** acct4 pw */
    private static final String ACCT4_PW = "acct4pw";
}
