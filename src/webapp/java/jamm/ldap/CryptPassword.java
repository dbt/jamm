package jamm.ldap;

import java.util.Random;

import cryptix.tools.UnixCrypt;

public class CryptPassword extends LdapPassword
{
    public CryptPassword()
    {
        super();
        mRandomClass = "java.security.SecureRandom";
    }

    public String crypt(String salt, String clearText)
    {
        UnixCrypt   crypt;

        crypt = new UnixCrypt(salt);
        return crypt.crypt(clearText);
    }

    public String getRandomSalt()
    {
        char[] saltList = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '/',
        };
        char[] salt;
        Random random;

        random = createRandom();
        salt = new char[2];
        salt[0] = saltList[ random.nextInt(saltList.length) ];
        salt[1] = saltList[ random.nextInt(saltList.length) ];

        return new String(salt);
    }

    protected String doHash(String password)
    {
        return "{CRYPT}" + crypt(getRandomSalt(), password);
    }

    protected boolean doCheck(String hashedPasword, String password)
    {
        return false;
    }

    public void setRandomClass(String randomClass)
    {
        mRandomClass = randomClass;
    }

    private Random createRandom()
    {
        Random  random;

        try
        {
            random = (Random) Class.forName(mRandomClass).newInstance();
        }
        catch (ClassNotFoundException e)
        {
            random = new Random();
        }
        catch (InstantiationException e)
        {
            random = new Random();
        }
        catch (IllegalAccessException e)
        {
            random = new Random();
        }

        return random;
    }

    private String mRandomClass;
}
