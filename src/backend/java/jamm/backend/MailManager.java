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
        mBindDn = binddn;
        mBindPassword = bindpw;
    }

    public void createDomain(String domain)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        BasicAttribute objectClass;
        BasicAttributes attributes;

        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBindDn, mBindPassword);

            // Create the domain
            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammVirtualDomain");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("jvd", domain);
            attributes.put("postfixTransport", "virtual:");
            String domainDn = domainDn(domain);
            ldap.addElement(domainDn, attributes);

            // Create the postmaster
            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("organizationalRole");
            objectClass.add("JammMailAlias");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("cn", "postmaster");
            attributes.put("mail", mail(domain, "postmaster"));
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
            String mail = mail(domain, "abuse");
            attributes.put("mail", mail);
            attributes.put("maildrop", "postmaster");
            ldap.addElement(mailDn(domain, mail), attributes);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Count not create domain: " +
                                           domain, e);
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
        String mail = mail(domain, alias);
        
        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBindDn, mBindPassword);

            BasicAttribute objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammMailAlias");
            BasicAttributes attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("mail", mail);
            attributes.put("maildrop", destination);
            ldap.addElement(mailDn(domain, mail), attributes);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Count not create alias: " + mail,
                                           e);
        }
        finally
        {
            closeLdap(ldap);
        }
    }

    public void modifyAlias(String domain, String alias, String newDestination)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        String mail = mail(domain, alias);
        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBindDn, mBindPassword);
            String dn = mailDn(domain, mail);
            ldap.modifyElementAttribute(dn, "maildrop", newDestination);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Could not modify alias" + mail, e);
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
        String mail = mail(domain, account);
        String hashedPassword;

        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBindDn, mBindPassword);

            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammMailAccount");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("homeDirectory", "/home/vmail/domains");
            attributes.put("mail", mail);
            attributes.put("mailbox", domain + "/" + account + "/");
            hashedPassword = LdapPassword.hash(PasswordScheme.SSHA_SCHEME,
                                               password);
            attributes.put("userPassword", hashedPassword);
            ldap.addElement(mailDn(domain, mail), attributes);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Could not create account: " + mail,
                                           e);
        }
        finally
        {
            closeLdap(ldap);
        }
        
    }

    public void addCatchall(String domain, String destination)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        BasicAttribute objectClass;
        BasicAttributes attributes;
        String catchAll = "@" + domain;

        try
        {
            ldap = new LdapFacade(mHost);
            ldap.simpleBind(mBindDn, mBindPassword);

            objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("JammMailAlias");
            attributes = new BasicAttributes();
            attributes.put(objectClass);
            attributes.put("mail", catchAll);
            attributes.put("maildrop", destination);
            ldap.addElement(mailDn(domain, catchAll), attributes);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Could not create catchall @" +
                                           domain, e);
        }
        finally
        {
            closeLdap(ldap);
        }
    }
            

    private String domainDn(String domain)
    {
        StringBuffer domainDn = new StringBuffer();
        domainDn.append("jvd=").append(domain).append(",").append(mBase);
        return domainDn.toString();
    }

    private String mailDn(String domain, String mail)
    {
        StringBuffer mailDn = new StringBuffer();
        mailDn.append("mail=").append(mail).append(",");
        mailDn.append(domainDn(domain));
        return mailDn.toString();
    }

    private static final String mail(String domain, String user)
    {
        StringBuffer mail = new StringBuffer();
        mail.append(user).append("@").append(domain);
        return mail.toString();
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
    private String mBindDn;
    private String mBindPassword;
}
