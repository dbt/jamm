package jamm.ldap;

import java.security.SecureRandom;

public class SaltedShaPassword extends ShaPassword
{
    protected String doHash(String password)
    {
        byte[] randomSalt;
        byte[] digest;

        randomSalt = getRandomSalt();
        digest = sha(append(password, randomSalt));

        return "{SSHA}" + encodeBase64(append(digest, randomSalt));
    }

    protected boolean doCheck(String hashedPassword, String password)
    {
        return false;
    }

    private static byte[] getRandomSalt()
    {
        SecureRandom random;
        byte[] salt;

        random = new SecureRandom();
        salt = new byte[4];
        random.nextBytes(salt);
        return salt;
    }
}
