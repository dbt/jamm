package jamm.ldap;

import junit.framework.TestCase;
import java.util.Arrays;

public class Md5PasswordTest extends TestCase
{
    public Md5PasswordTest(String name)
    {
        super(name);
    }

    public void testMd5()
    {
        Md5Password md5;
        byte[] digest;
        String hash;
        byte[] expectedDigest = {
            (byte) 0x09, (byte) 0x8F, (byte) 0x6B, (byte) 0xCD,
            (byte) 0x46, (byte) 0x21, (byte) 0xD3, (byte) 0x73,
            (byte) 0xCA, (byte) 0xDE, (byte) 0x4E, (byte) 0x83,
            (byte) 0x26, (byte) 0x27, (byte) 0xB4, (byte) 0xF6};

        md5 = new Md5Password();
        digest = md5.md5("test");
        assertTrue("Checking MD5 digest of \"test\"",
                   Arrays.equals(expectedDigest, digest));
        
        hash = md5.doHash("test");
        assertEquals("Checking MD5 hash of \"test\"",
                     "{MD5}CY9rzUYh03PK3k6DJie09g==", hash);
    }
}
