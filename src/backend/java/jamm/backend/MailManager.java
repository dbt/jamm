package jamm.backend;

import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import jamm.ldap.LdapFacade;
import jamm.ldap.LdapPassword;
import jamm.ldap.PasswordScheme;

public class MailManager
{
    public MailManager(String host, String base, String binddn, String bindpw)
    {
        mHost = host;
        mBase = base;
        mBinddn = binddn;
        mBindpw = bindpw;
    }

    public void createDomain(String domainName)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        BasicAttribute objectClass;
        BasicAttributes attributes;

        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBinddn, mBindpw);

            // Create the domain
            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammVirtualDomain");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("jvd", domainName);
            attributes.put("postfixTransport", "virtual:");
            String domainDn = domainDn(domainName);
            ldap.addElement(domainDn, attributes);

            // Create the postmaster
            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("organizationalRole");
            objectClass.add("JammMailAlias");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("cn", "postmaster");
            attributes.put("mail", "postmaster@" + domainName);
            attributes.put("maildrop", "postmaster");
            String dn = "cn=postmaster," + domainDn;
            attributes.put("roleOccupant", dn);
            ldap.addElement(dn, attributes);

            // Create the abuse account
            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammMailAlias");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            String mail = "abuse@" + domainName;
            attributes.put("mail", mail);
            attributes.put("maildrop", "postmaster");
            dn = "mail=" + mail + "," + domainDn;
            ldap.addElement(dn, attributes);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Count not create domain: " +
                                           domainName, e);
        }
        finally
        {
            closeLdap(ldap);
        }
    }

    public void createAlias(String domain, String alias, String destination)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        BasicAttribute objectClass;
        BasicAttributes attributes;
        String email = alias + "@" + domain;
        
        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBinddn, mBindpw);

            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammMailAlias");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("mail", email);
            attributes.put("maildrop", destination);
            String dn = "mail=" + email + "," + domainDn(domain);
            ldap.addElement(dn, attributes);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Count not create alias " + email,
                                           e);
        }
        finally
        {
            closeLdap(ldap);
        }
    }

    public void createAccount(String domain, String account, String password)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        BasicAttribute objectClass;
        BasicAttributes attributes;
        String email = account + "@" + domain;
        String hashedPassword;

        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBinddn, mBindpw);

            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammMailAccount");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("homeDirectory", "/home/vmail/domains");
            attributes.put("mail", email);
            attributes.put("mailbox", domain + "/" + account + "/");
            hashedPassword = LdapPassword.hash(PasswordScheme.SSHA_SCHEME,
                                               password);
            attributes.put("userPassword", hashedPassword);
            ldap.addElement("mail=" + email + "," + domainDn(domain),
                            attributes);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Could not create account " + email,
                                           e);
        }
        finally
        {
            closeLdap(ldap);
        }
        
    }

    private String domainDn(String domain)
    {
        return "jvd=" + domain + "," + mBase;
    }

    private void closeLdap(LdapFacade ldap)
    {
        if (ldap != null)
        {
            ldap.close();
        }
    }

    private String mHost;
    private String mBase;
    private String mBinddn;
    private String mBindpw;
}
