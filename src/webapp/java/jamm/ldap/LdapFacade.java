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
        resetModify();
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

    public void resetModify()
    {
        mModifyAttributes = new BasicAttributes();
    }

    /**
     * Sets an attribute to be modified.  This will not take effect
     * until replaceModifiedAttributes() is called.
     */
    public void modifyAttribute(String name, String value)
        throws NamingException
    {
        mModifyAttributes.put(name, value);
    }

    public void replaceModifiedAttributes()
        throws NamingException
    {
        mContext.modifyAttributes(getName(), DirContext.REPLACE_ATTRIBUTE,
                                  mModifyAttributes);
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

    public boolean hasMoreResults()
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

    public String getResultName()
        throws NamingException
    {
        return mCurrentResultElement.getName() + "," + mSearchBase;
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

    private Attributes mModifyAttributes;

    private SearchControls mControls;
    private NamingEnumeration mResults;
    private SearchResult mCurrentResultElement;
    private Attributes mCurrentResultAttributes;
    private String mSearchBase;
}
