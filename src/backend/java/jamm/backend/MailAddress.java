package jamm.backend;

public class MailAddress
{
    public static final String hostFromAddress(String address)
    {
        int separator = address.indexOf("@");
        return address.substring(separator + 1);
    }

    public static final String userFromAddress(String address)
    {
        int separator = address.indexOf("@");
        return address.substring(0, separator);
    }

    public static final String addressFromParts(String user, String host)
    {
        return new StringBuffer(user).append("@").append(host).toString();
    }

    public MailAddress(String address)
    {
        mUser = userFromAddress(address);
        mHost = hostFromAddress(address);
        mAddress = address;
    }

    public MailAddress(String user, String host)
    {
        mUser = user;
        mHost = host;
        mAddress = addressFromParts(user, host);
    }

    public String getUser()
    {
        return mUser;
    }

    public String getHost()
    {
        return mHost;
    }

    public String getAddress()
    {
        return mAddress;
    }

    public String toString()
    {
        return getAddress();
    }

    private String mUser;
    private String mHost;
    private String mAddress;
}
