package jamm.ldap;

import junit.framework.TestCase;

public class CryptPasswordTest extends TestCase
{
    public CryptPasswordTest(String name)
    {
        super(name);
    }

    public void testCrypt()
    {
        CryptPassword   crypt;
        String hash;
        String ldapHash;
        String password;

        crypt = new CryptPassword();
        hash = crypt.crypt("cl", "test");
        assertEquals("Checking hash of \"test\"", "clKAOxsMt8tC6", hash);

        ldapHash = crypt.doHash("test");
        assertTrue("Checking {crypt} prefix", ldapHash.startsWith("{CRYPT}"));
    }
}
