package jamm.ldap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cryptix.util.mime.Base64OutputStream;

public abstract class LdapPassword
{
    public static final int CLEAR_SCHEME            = 1;
    public static final int CRYPT_SCHEME            = 2;
    public static final int MD5_SCHEME              = 3;
    public static final int SHA_SCHEME              = 4;
    public static final int SSHA_SCHEME             = 5;

    public static String hash(int scheme, String password)
    {
        LdapPassword    method;

        switch(scheme)
        {
            case CRYPT_SCHEME:
                method = new CryptPassword();
                break;

            case MD5_SCHEME:
                method = new Md5Password();
                break;

            case SHA_SCHEME:
                method = new ShaPassword();
                break;

            case SSHA_SCHEME:
                method = new SaltedShaPassword();
                break;

            case CLEAR_SCHEME:
            default:
                method = new ClearPassword();
        }
        return method.doHash(password);
    }

    public static boolean check(String hashedPassword, String password)
    {
        return false;
    }

    protected abstract String doHash(String password);

    protected abstract boolean doCheck(String hashedPassword, String password);

    protected String encodeBase64(byte[] clearText)
    {
        ByteArrayOutputStream   baos;
        Base64OutputStream      base64;
        String  encoded;

        try
        {
            baos = new ByteArrayOutputStream();
            base64 = new Base64OutputStream(baos);
            base64.write(clearText, 0, clearText.length);
            base64.close();

            // Must trim this string as a CR-LF is appended on the
            // end.
            encoded = new String(baos.toByteArray()).trim();
        }
        catch (IOException e)
        {
            throw new UnsupportedOperationException(e.toString());
        }
        
        return encoded;
    }

    protected final byte[] append(byte[] first, byte[] second)
    {
        byte[] appended;

        appended = new byte[first.length + second.length];
        System.arraycopy(first, 0, appended, 0, first.length);
        System.arraycopy(second, 0, appended, first.length, second.length);

        return appended;
    }
    
    protected final byte[] append(String first, byte[] second)
    {
        return append(first.getBytes(), second);
    }
}
