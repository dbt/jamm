package jamm.webapp;

import java.text.MessageFormat;

public final class Globals
{
    public static void setLdapHost(String ldapHost)
    {
        mLdapHost = ldapHost;
    }

    public static String getLdapHost()
    {
        return mLdapHost;
    }

    public static void setLdapPort(int ldapPort)
    {
        mLdapPort = ldapPort;
    }

    public static int getLdapPort()
    {
        return mLdapPort;
    }

    public static void setLdapSearchBase(String ldapSearchBase)
    {
        mLdapSearchBase = ldapSearchBase;
    }

    public static String getLdapSearchBase()
    {
        return mLdapSearchBase;
    }

    public static void setLdapQueryFilter(String ldapQueryFilter)
    {
        mLdapQueryFormat = new MessageFormat(ldapQueryFilter);
    }

    public static String getLdapQueryFilter(String address)
    {
        Object[] args = {address};

        return mLdapQueryFormat.format(args);
    }

    public static String getRootDn()
    {
        return mRootDn;
    }

    public static void setRootDn(String rootDn)
    {
        mRootDn = rootDn;
    }

    private static String mLdapHost;
    private static int mLdapPort;
    private static String mLdapSearchBase;
    private static MessageFormat mLdapQueryFormat;
    private static String mRootDn;
}
