package jamm.ldap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Password extends LdapPassword
{
    public byte[] md5(String clearText)
    {
        MessageDigest   md;
        byte[] digest;

        try
        {
            md = MessageDigest.getInstance("MD5");
            md.update(clearText.getBytes());
            digest = md.digest();
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new UnsupportedOperationException(e.toString());
        }

        return digest;
    }

    protected String doHash(String password)
    {
        byte[] digest;

        digest = md5(password);
        return "{MD5}" + encodeBase64(digest);
    }

    protected boolean doCheck(String hashedpassword, String password)
    {
        return false;
    }
}
