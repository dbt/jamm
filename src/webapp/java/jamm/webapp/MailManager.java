package jamm.webapp;

import jamm.ldap.LdapFacade;

import javax.naming.NamingException;

public class MailManager
{
    public String[] getDomains()
        throws NamingException
    {
        LdapFacade ldap;
        String[] domains;

        ldap = null;
        domains = new String[0];

        try
        {
            ldap = new LdapFacade("localhost");
            ldap.anonymousBind();
            ldap.searchSubtree("ou=email,dc=dribin,dc=net",
                               "uidNumber=101");
            while (ldap.nextResult())
            {
                System.out.println(ldap.getResultName());
                // System.out.println(ldap.getAttribute("mail"));
            }
        }
        finally
        {
            closeLdap(ldap);
        }

        return domains;
    }

    public void check()
        throws NamingException
    {
        LdapFacade ldap;

        ldap = null;
        try
        {
            ldap = new LdapFacade("localhost");
            ldap.simpleBind(
                "cn=Manager, ou=pocuindustry.com, ou=email, dc=dribin, dc=net",
                "pocuindustry");
            System.out.println("cn: " +
                               ldap.getAttribute("cn"));
            ldap.close();
            ldap.simpleBind(
                "mail=andy@pocuindustry.com, ou=pocuindustry.com, ou=email, dc=dribin, dc=net",
                "andy1");
            System.out.println("homeDirectory: " +
                               ldap.getAttribute("homeDirectory"));
            
        }
        finally
        {
            closeLdap(ldap);
        }
    }

    private void closeLdap(LdapFacade ldap)
    {
        if (ldap != null)
            ldap.close();
    }

    public static void main(String args[])
    {
        MailManager manager;
        String[] domains;

        try
        {
            manager = new MailManager();
            domains = manager.getDomains();
            manager.check();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
