
import jamm.ldap.LdapFacade;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

public class DirectoryTest
{
    public static void main(String argv[])
    {
        LdapFacade ldap;

        ldap = null;
        try
        {
            ldap = new LdapFacade("localhost");

            ldap.simpleBind("cn=Manager, dc=dribin, dc=net", argv[0]);

            Attributes attrs = new BasicAttributes(true);

            // attrs.put("dn", "uid=afx,ou=accounts,dc=dribin,dc=net");

//             Attribute objectclass = new BasicAttribute("objectclass");
//             objectclass.add("CourierMailAccount");
//             objectclass.add("CourierMailAlias");
//             attrs.put(objectclass);
//             attrs.put("uid", "afx");
//             attrs.put("cn", "Aphex Twin");
//             attrs.put("mail", "afx@dribin.net");
//             attrs.put("homeDirectory", "/home/vmail");
//             attrs.put("uidNumber", "101");
//             attrs.put("gidNumber", "101");
//             attrs.put("maildrop", "dave@dribin.net");

            attrs.put("objectclass", "CourierMailAlias");
            attrs.put("mail", "afx@dribin.net");
            attrs.put("maildrop", "dave@dribin.net");

            ldap.addElement(
                "mail=afx@dribin.net,ou=dribin.net,ou=email,dc=dribin,dc=net",
                attrs);

            ldap.searchSubtree("ou=email,dc=dribin,dc=net",
                               "mail=*@dribin.net");

            while (ldap.nextResult())
            {
                System.out.println("dn: " + ldap.getResultName());
                printAttributes(ldap.getAllResultAttributes());
                System.out.println();
            }

            ldap.deleteElement(
                "mail=afx@dribin.net,ou=dribin.net,ou=email,dc=dribin,dc=net");
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (ldap != null)
                ldap.close();
        }
    }

    private static void printAttributes(NamingEnumeration attributes)
        throws NamingException
    {
        Attribute attribute;
        
        while (attributes.hasMore())
        {
            attribute = (Attribute) attributes.next();
            System.out.println(attribute);
        }
    }
}
