package jamm.ldap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaPassword extends LdapPassword
{
    public byte[] sha(String clearText)
    {
        return sha(clearText.getBytes());
    }

    public byte[] sha(byte[] clearText)
    {
        MessageDigest   md;
        byte[] digest;

        try
        {
            md = MessageDigest.getInstance("SHA");
            md.update(clearText);
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
        return "{SHA}" + encodeBase64(sha(password));
    }

    protected boolean doCheck(String hashedpassword, String password)
    {
        return false;
    }
}
