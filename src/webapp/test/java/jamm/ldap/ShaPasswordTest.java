package jamm.ldap;

import junit.framework.TestCase;
import java.util.Arrays;

public class ShaPasswordTest extends TestCase
{
    public ShaPasswordTest(String name)
    {
        super(name);
    }

    public void testSha()
    {
        ShaPassword sha;
        String hash;

        sha = new ShaPassword();
        hash = sha.doHash("test");
        assertEquals("Checking SHA hash of \"test\"",
                     "{SHA}qUqP5cyxm6YcTAhz05Hph5gvu9M=", hash);
    }
}
