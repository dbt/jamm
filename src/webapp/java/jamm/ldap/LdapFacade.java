package jamm.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;
import javax.naming.directory.SearchControls;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

public class LdapFacade
{
    public LdapFacade(String host)
    {
        this(host, 389);
    }

    public LdapFacade(String host, int port)
    {
        mHost = host;
        mPort = port;
        mContext = null;
        mAttributes = null;

        mEnvironment = new Hashtable();
        mEnvironment.put(Context.INITIAL_CONTEXT_FACTORY, 
                         "com.sun.jndi.ldap.LdapCtxFactory");
        mEnvironment.put(Context.PROVIDER_URL,
                "ldap://" + mHost + ":" + mPort + "/");
    }

    /**
     * Bind anonymously.
     */
    public void anonymousBind()
        throws NamingException
    {
        mEnvironment.put(Context.SECURITY_AUTHENTICATION, "none");
        bind();
        mAttributes = null;
    }

    /**
     * Bind to a DN using simple authentication.
     */
    public void simpleBind(String dn, String password)
        throws NamingException
    {
        mEnvironment.put(Context.SECURITY_AUTHENTICATION, "simple");
        mEnvironment.put(Context.SECURITY_PRINCIPAL, dn);
        mEnvironment.put(Context.SECURITY_CREDENTIALS, password);
        bind();
        mAttributes = mContext.getAttributes(dn);
    }

    private void bind()
        throws NamingException
    {
        mContext = new InitialDirContext(mEnvironment);
        resetSearch();
    }

    public String getName()
        throws NamingException
    {
        return (String)
            mContext.getEnvironment().get(Context.SECURITY_PRINCIPAL);
    }

    public String getAttribute(String name)
        throws NamingException
    {
        return (String) mAttributes.get(name).get();
    }

    public void setReturningAttributes(String[] attributes)
    {
        mControls.setReturningAttributes(attributes);
    }

    public void replaceModifiedAttributes(Attributes attributes)
        throws NamingException
    {
        mContext.modifyAttributes(getName(), DirContext.REPLACE_ATTRIBUTE,
                                  attributes);
    }

    public void addElement(String distinguishedName, Attributes attributes)
        throws NamingException
    {
        DirContext subcontext;

        subcontext = mContext.createSubcontext(distinguishedName, attributes);
        subcontext.close();
    }

    public void deleteElement(String distinguishedName)
        throws NamingException
    {
        mContext.destroySubcontext(distinguishedName);
    }

    public void resetSearch()
    {
        mControls = new SearchControls();
        mSearchBase = "";
        mResults = null;
        mCurrentResultElement = null;
        mCurrentResultAttributes = null;
    }

    public void searchOneLevel(String base, String filter)
        throws NamingException
    {
        mSearchBase = base;
        mControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        mResults = mContext.search(mSearchBase, filter, mControls);
    }

    public void searchSubtree(String base, String filter)
        throws NamingException
    {
        mSearchBase = base;
        mControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        mResults = mContext.search(mSearchBase, filter, mControls);
    }

    public boolean nextResult()
        throws NamingException
    {
        boolean hasMore;

        hasMore = mResults.hasMore();
        if (hasMore)
        {
            mCurrentResultElement = (SearchResult) mResults.next();
            mCurrentResultAttributes = mCurrentResultElement.getAttributes();
        }
        
        return hasMore;
    }

    public String getResultAttribute(String name)
        throws NamingException
    {
        return (String) mCurrentResultAttributes.get(name).get();
    }

    public NamingEnumeration getAllResultAttributes()
        throws NamingException
    {
        return mCurrentResultAttributes.getAll();
    }

    public String getResultName()
        throws NamingException
    {
        String relativeDn;

        relativeDn = mCurrentResultElement.getName();
        if (relativeDn.equals(""))
            return mSearchBase;
        else
            return  relativeDn + "," + mSearchBase;
    }

    public void close()
    {
        try
        {
            if (mContext != null)
            {
                resetSearch();
                mContext.close();
                mContext = null;
            }
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
    }

    private String mHost;
    private int mPort;

    private Hashtable mEnvironment;
    private DirContext mContext;
    private Attributes mAttributes;

    private SearchControls mControls;
    private NamingEnumeration mResults;
    private SearchResult mCurrentResultElement;
    private Attributes mCurrentResultAttributes;
    private String mSearchBase;
}
