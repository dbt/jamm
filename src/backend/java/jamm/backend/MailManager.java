package jamm.backend;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;

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

    private LdapFacade getLdap()
        throws NamingException
    {
        LdapFacade ldap = new LdapFacade(mHost, mPort);
        if (mBindDn.equals("") && mBindPassword.equals(""))
        {
            ldap.anonymousBind();
        }
        else
        {
            ldap.simpleBind(mBindDn, mBindPassword);
        }
        return ldap;
    }
    
    private void closeLdap(LdapFacade ldap)
    {
        if (ldap != null)
        {
            ldap.close();
        }
    }

    public void changePassword(String mail, String newPassword)
        throws MailManagerException
    {
        LdapFacade ldap = null;

        try
        {
            ldap = getLdap();
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
        
        try
        {
            ldap = getLdap();
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
            ldap = getLdap();

            // Get all users in this domain who have postamster
            // privileges.  The results are full DNs.
            String domain = MailAddress.hostFromAddress(mail);
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

    public List getDomains()
        throws MailManagerException
    {
        LdapFacade ldap = null;
        List  domains = new ArrayList();
        try
        {
            ldap = getLdap();

            ldap.searchOneLevel(mBase, "jvd=*");
            while (ldap.nextResult())
            {
                domains.add(ldap.getResultAttribute("jvd"));
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

        Collections.sort(domains, String.CASE_INSENSITIVE_ORDER);
        return domains;
    }

    public String getDnFromMail(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        String foundDn = null;
        try
        {
            ldap = getLdap();
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
            ldap = getLdap();

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
                                         ALIAS_OBJECT_CLASS});
            attributes.put("cn", "postmaster");
            attributes.put("mail",
                           MailAddress.addressFromParts("postmaster", domain));
            attributes.put("maildrop", "postmaster");
            String dn = "cn=postmaster," + domainDn;
            attributes.put("roleOccupant", dn);
            ldap.addElement(dn, attributes);

            // Create the abuse account
            attributes.clear();
            attributes.put("objectClass",
                           new String[] {"top", ALIAS_OBJECT_CLASS});
            String mail = MailAddress.addressFromParts("abuse", domain);
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

    public void createAlias(String domain, String alias,
                            Collection destinations)
        throws MailManagerException
    {
        createAlias(domain, alias,
                    (String []) destinations.toArray(new String[0]));
    }

    public void createAlias(String domain, String alias, String[] destinations)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        String mail = MailAddress.addressFromParts(alias, domain);
        
        try
        {
            ldap = getLdap();

            Map attributes = new HashMap();
            attributes.put("objectClass",
                           new String[] {"top", ALIAS_OBJECT_CLASS});
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

    public void modifyAlias(AliasInfo alias)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        try
        {
            ldap = getLdap();
            ldap.modifyElementAttribute(mailDn(alias.getName()), "maildrop",
                                        alias.getDestinations());
        }
        catch (NamingException e)
        {
            throw new MailManagerException(alias.getName(), e);
        }
        finally
        {
            closeLdap(ldap);
        }
    }

    public boolean isAlias(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        boolean isAlias = false;
        
        try
        {
            ldap = getLdap();
            searchForMail(ldap, mail);
            Set objectClasses =
                ldap.getAllResultAttributeValues("objectClass");
            isAlias = objectClasses.contains(ALIAS_OBJECT_CLASS);
        }
        catch (NamingException e)
        {
            throw new MailManagerException(mail, e);
        }
        finally
        {
            closeLdap(ldap);
        }

        return isAlias;
    }

    public AliasInfo getAlias(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        AliasInfo alias = null;
        try
        {
            ldap = getLdap();
            searchForMail(ldap, mail);
            alias = createAliasInfo(ldap);
        }
        catch (NamingException e)
        {
            throw new MailManagerException(e);
        }
        finally
        {
            closeLdap(ldap);
        }
        return alias;
    }

    public List getAliases(String domain)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        List aliases = new ArrayList();

        try
        {
            ldap = getLdap();
            String domainDn = domainDn(domain);
            ldap.searchOneLevel(domainDn, "objectClass=" + ALIAS_OBJECT_CLASS);

            while (ldap.nextResult())
            {
                String name = ldap.getResultAttribute("mail");
                // Skip "special" accounts
                if (name.startsWith("postmaster@") ||
                    name.startsWith("abuse@"))
                {
                    continue;
                }

                AliasInfo alias = createAliasInfo(ldap);
                aliases.add(alias);
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

        Collections.sort(aliases, new AliasNameComparator());
        return aliases;
    }

    private AliasInfo createAliasInfo(LdapFacade ldap)
        throws NamingException
    {
        String name = ldap.getResultAttribute("mail");

        List destinations =
            new ArrayList(ldap.getAllResultAttributeValues("maildrop"));
        Collections.sort(destinations, String.CASE_INSENSITIVE_ORDER);
        boolean isActive = true;
        boolean isAdmin = false;
        return new AliasInfo(name, destinations, isActive, isAdmin);
    }

    public void deleteAlias(String mail)
        throws MailManagerException
    {
        LdapFacade ldap = null;
        try
        {
            ldap = getLdap();
            searchForMail(ldap, mail);
            ldap.deleteElement(ldap.getResultName());
        }
        catch (NamingException e)
        {
            throw new MailManagerException(mail, e);
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
        String mail = MailAddress.addressFromParts(account, domain);

        try
        {
            ldap = getLdap();
            Map attributes = new HashMap();
            attributes.put("objectClass",
                           new String[] { "top", ACCOUNT_OBJECT_CLASS});
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
            ldap = getLdap();
            String domainDn = domainDn(domain);
            ldap.searchOneLevel(domainDn, "objectClass=" +
                                ACCOUNT_OBJECT_CLASS);

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
            ldap = getLdap();
            Map attributes = new HashMap();
            attributes.put("objectClass",
                           new String[] { "top", ALIAS_OBJECT_CLASS});
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
        String domain = MailAddress.hostFromAddress(mail);
        StringBuffer mailDn = new StringBuffer();
        mailDn.append("mail=").append(mail).append(",");
        mailDn.append(domainDn(domain));
        return mailDn.toString();
    }

    private final String ACCOUNT_OBJECT_CLASS = "JammMailAccount";
    private final String ALIAS_OBJECT_CLASS = "JammMailAlias";

    private String mHost;
    private int mPort;
    private String mBase;
    private String mBindDn;
    private String mBindPassword;
}
