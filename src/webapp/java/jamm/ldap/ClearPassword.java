package jamm.ldap;

public class ClearPassword extends LdapPassword
{
    protected String doHash(String password)
    {
        return password;
    }

    protected boolean doCheck(String hashedPassword, String password)
    {
        return hashedPassword.equals(password);
    }
}
