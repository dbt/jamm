package jamm.backend;

import java.util.Map;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.naming.AuthenticationException;

import jamm.ldap.LdapFacade;
import jamm.ldap.LdapPassword;
import jamm.ldap.PasswordScheme;

public class MailManager
{
    public MailManager(String host, String base, String bindDn,
                       String bindPassword)
    {
        this(host, 389, base, bindDn, bindPassword);
    }

    public MailManager(String host, String base)
    {
        this(host, 389, base, "", "");
    }

    public MailManager(String host, int port, String base, String bindDn,
                       String bindPassword)
    {
        mHost = host;
        mPort = port;
        mBase = base;
        mBindDn = bindDn;
        mBindPassword = bindPassword;
    }

    public MailManager(String host, int port, String base)
    {
        this(host, port, base, "", "");
    }

    public void setBindEntry(String bindDn, String bindPassword)
    {
        mBindDn = bindDn;
        mBindPassword = bindPassword;
    }

    public void changePassword(String newPassword)
        throws MailManagerException
    {
        LdapFacade ldap = null;

        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);
            
            String hashedPassword =
                LdapPassword.hash(PasswordScheme.SSHA_SCHEME, newPassword);
            ldap.modifyElementAttribute(ldap.getName(), "userPassword",
                                        hashedPassword);
            mBindPassword = newPassword;
        }
        catch (NamingException e)
        {
            throw new MailManagerException(e);
        }
        finally
        {
            closeLdap(ldap);
        }
    }

    public boolean authenticate()
        throws MailManagerException
    {
        LdapFacade ldap = null;
        boolean authenticated = false;
        
        ldap = new LdapFacade(mHost, mPort);
        try
        {
            ldap.simpleBind(mBindDn, mBindPassword);
            authenticated = true;
        }
        catch (AuthenticationException e)
        {
            authenticated = false;
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Could not bind", e);
        }
        finally
        {
            closeLdap(ldap);
        }

        return authenticated;
    }

    public String findByMail(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        String foundDn = null;
        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.anonymousBind();
            
            ldap.searchSubtree(mBase, "mail=" + mail);

            if (ldap.nextResult())
            {
                foundDn = ldap.getResultName();
            }
        }
        catch (NamingException e)
        {
            throw new MailManagerException(e);
        }
        finally
        {
            closeLdap(ldap);
        }
        return foundDn;
    }

    public void createDomain(String domain)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);

            // Create the domain
            Map attributes = new HashMap();
            attributes.put("objectClass",
                           new String[] {"top", "JammVirtualDomain"});
            attributes.put("jvd", domain);
            attributes.put("postfixTransport", "virtual:");
            String domainDn = domainDn(domain);
            ldap.addElement(domainDn, attributes);

            // Create the postmaster
            attributes.clear();
            attributes.put("objectClass",
                           new String[] {"top", "organizationalRole",
                                         "JammMailAlias"});
            attributes.put("cn", "postmaster");
            attributes.put("mail", mail(domain, "postmaster"));
            attributes.put("maildrop", "postmaster");
            String dn = "cn=postmaster," + domainDn;
            attributes.put("roleOccupant", dn);
            ldap.addElement(dn, attributes);

            // Create the abuse account
            attributes.clear();
            attributes.put("objectClass",
                           new String[] {"top", "JammMailAlias"});
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
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);

            Map attributes = new HashMap();
            attributes.put("objectClass",
                           new String[] {"top", "JammMailAlias"});
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
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);
            ldap.modifyElementAttribute(mailDn(domain, mail), "maildrop",
                                        newDestination);
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
        String mail = mail(domain, account);

        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);

            Map attributes = new HashMap();
            attributes.put("objectClass",
                           new String[] { "top", "JammMailAccount" });
            attributes.put("homeDirectory", "/home/vmail/domains");
            attributes.put("mail", mail);
            attributes.put("mailbox", domain + "/" + account + "/");
            String hashedPassword =
                LdapPassword.hash(PasswordScheme.SSHA_SCHEME, password);
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
        String catchAll = "@" + domain;

        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);

            Map attributes = new HashMap();
            attributes.put("objectClass",
                           new String[] { "top", "JammMailAlias" });
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
            

    private final String domainDn(String domain)
    {
        StringBuffer domainDn = new StringBuffer();
        domainDn.append("jvd=").append(domain).append(",").append(mBase);
        return domainDn.toString();
    }

    private final String mailDn(String domain, String mail)
    {
        StringBuffer mailDn = new StringBuffer();
        mailDn.append("mail=").append(mail).append(",");
        mailDn.append(domainDn(domain));
        return mailDn.toString();
    }

    private final String mail(String domain, String user)
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
    private int mPort;
    private String mBase;
    private String mBindDn;
    private String mBindPassword;
}
