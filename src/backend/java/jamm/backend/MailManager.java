package jamm.backend;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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

    public void changePassword(String mail, String newPassword)
        throws MailManagerException
    {
        LdapFacade ldap = null;

        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);
            
            searchForMail(ldap, mail);

            String foundDn = ldap.getResultName();
            String hashedPassword =
                LdapPassword.hash(PasswordScheme.SSHA_SCHEME, newPassword);
            ldap.modifyElementAttribute(foundDn, "userPassword",
                                        hashedPassword);

            if (foundDn.equals(mBindDn))
            {
                mBindPassword = newPassword;
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

    public boolean isPostmaster(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        boolean isPostmaster = false;
        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);

            // Get all users in this domain who have postamster
            // privileges.  The results are full DNs.
            String domain = domainFromMail(mail);
            String postmasterMail = "postmaster@" + domain;
            searchForMail(ldap, postmasterMail);
            Set postmasters =
                ldap.getAllResultAttributeValues("roleOccupant");

            // Get DN for user in question and see if they are a
            // postmaster.
            searchForMail(ldap, mail);
            String mailDn = ldap.getResultName();
            isPostmaster = postmasters.contains(mailDn);
        }
        catch (NamingException e)
        {
            throw new MailManagerException(e);
        }
        finally
        {
            closeLdap(ldap);
        }

        return isPostmaster;
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
            
            searchForMail(ldap, mail);
            foundDn = ldap.getResultName();
        }
        catch (NamingException e)
        {
            throw new MailManagerException(e);
        }
        catch (MailNotFoundException e)
        {
            // This is not an exceptional case for this method
            foundDn = null;
        }
        finally
        {
            closeLdap(ldap);
        }
        return foundDn;
    }

    private void searchForMail(LdapFacade ldap, String mail)
        throws NamingException, MailNotFoundException
    {
        ldap.searchSubtree(mBase, "mail=" + mail);

        if (!ldap.nextResult())
        {
            throw new MailNotFoundException(mail);
        }
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

    public void createAlias(String domain, String alias, String[] destinations)
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
            attributes.put("maildrop", destinations);
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

    public void modifyAlias(String domain, String alias,
                            String[] newDestinations)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        String mail = mail(domain, alias);
        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);
            ldap.modifyElementAttribute(mailDn(domain, mail), "maildrop",
                                        newDestinations);
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

    public void modifyAlias(String mail, Collection newDestinations)
        throws MailManagerException
    {
        String domain = domainFromMail(mail);
        String alias = userFromMail(mail);
        String[] destinationsArray = new String[0];
        destinationsArray =
            (String[]) newDestinations.toArray(destinationsArray);
        modifyAlias(domain, alias, destinationsArray);
    }

    public boolean isAlias(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        boolean isAlias = false;
        
        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);

            searchForMail(ldap, mail);
            Set objectClasses =
                ldap.getAllResultAttributeValues("objectClass");
            isAlias = objectClasses.contains("JammMailAlias");
        }
        catch (NamingException e)
        {
            throw new MailManagerException(e);
        }
        finally
        {
            closeLdap(ldap);
        }

        return isAlias;
    }

    public String[] getAliasDestinations(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        String[] destinations = new String[0];
        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);

            searchForMail(ldap, mail);

            Set maildrops = ldap.getAllResultAttributeValues("maildrop");
            destinations = (String[]) maildrops.toArray(destinations);
            Arrays.sort(destinations);
        }
        catch (NamingException e)
        {
            throw new MailManagerException("Could not modify alias" + mail, e);
        }
        finally
        {
            closeLdap(ldap);
        }
        return destinations;
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

    public List getAccounts(String domain)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        List accounts = new ArrayList();

        try
        {
            ldap = new LdapFacade(mHost, mPort);
            ldap.simpleBind(mBindDn, mBindPassword);
            String domainDn = domainDn(domain);
            ldap.searchOneLevel(domainDn, "objectClass=JammMailAccount");

            while (ldap.nextResult())
            {
                String name = ldap.getResultAttribute("mail");
                boolean isActive = true;
                boolean isAdmin = false;
                AccountInfo account = new AccountInfo(name, isActive, isAdmin);
                accounts.add(account);
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

        Collections.sort(accounts, new AccountNameComparator());
        return accounts;
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

    private final String mailDn(String mail)
    {
        String domain = domainFromMail(mail);
        StringBuffer mailDn = new StringBuffer();
        mailDn.append("mail=").append(mail).append(",");
        mailDn.append(domainDn(domain));
        return mailDn.toString();
    }

    private final String domainFromMail(String mail)
    {
        int domainIndex = mail.indexOf("@");
        return mail.substring(domainIndex + 1);
    }

    private final String userFromMail(String mail)
    {
        int domainIndex = mail.indexOf("@");
        return mail.substring(0, domainIndex);
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
