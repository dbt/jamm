
import java.util.Hashtable;
import java.util.Random;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.SearchResult;
import javax.naming.directory.SearchControls;

import cryptix.tools.UnixCrypt;

public class DirectoryTest
{
    public static void main(String argv[])
    {
	try {
	    String name = "mail=elvis@dribin.net";
	    Hashtable env = new Hashtable();
	
	    env.put(Context.INITIAL_CONTEXT_FACTORY,
		    "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL,
		    "ldap://localhost:389/ou=accounts,dc=dribin,dc=net");

	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL,
		    "cn=Manager, dc=dribin, dc=net");
		    // "uid=elvis, ou=accounts, dc=dribin, dc=net");
	    env.put(Context.SECURITY_CREDENTIALS, argv[0]);

	    DirContext ctx = new InitialDirContext(env);
	
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

//             DirContext afx = ctx.createSubcontext(
//                 "uid=afx,ou=dribin.net", attrs);
//             afx.close();

            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            ctls.setReturningAttributes(new String[0]);
            String filter = "mail=*@dribin.net";
            
            NamingEnumeration answer = ctx.search("", filter, ctls);
            printSearchEnumeration(answer);

            ctx.close();
	}
	catch (NamingException e) {
	    e.printStackTrace();
	}
    }

    public static void printSearchEnumeration(NamingEnumeration enum) {
	try {
	    while (enum.hasMore()) {
		SearchResult sr = (SearchResult)enum.next();
		System.out.println(">>>" + sr.getName());
		printAttrs(sr.getAttributes());
	    }
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }

    public static void printAttrs(Attributes attrs) {
	if (attrs == null) {
	    System.out.println("No attributes");
	} else {
	    /* Print each attribute */
	    try {
		for (NamingEnumeration ae = attrs.getAll();
		     ae.hasMore();) {
		    Attribute attr = (Attribute)ae.next();
		    System.out.println("attribute: " + attr.getID());

		    /* print each value */
		    for (NamingEnumeration e = attr.getAll();
			 e.hasMore();
			 System.out.println("value: " + e.next()))
			;
		}
	    } catch (NamingException e) {
		e.printStackTrace();
	    }
	}
    }

}
