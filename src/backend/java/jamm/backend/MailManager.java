package jamm.backend;

import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import jamm.ldap.LdapFacade;

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
            String domainDn = "jvd=" + domainName + "," + mBase;
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
