package jamm.ldap;

public final class PasswordScheme
{
    private static int mCount = 0;

    public static final PasswordScheme CLEAR_SCHEME =
        new PasswordScheme(mCount++);

    public static final PasswordScheme CRYPT_SCHEME =
        new PasswordScheme(mCount++);

    public static final PasswordScheme MD5_SCHEME =
        new PasswordScheme(mCount++);

    public static final PasswordScheme SHA_SCHEME =
        new PasswordScheme(mCount++);

    public static final PasswordScheme SSHA_SCHEME =
        new PasswordScheme(mCount++);

    public static final int max()
    {
        return mCount;
    }

    private PasswordScheme(int value)
    {
        mValue = value;
    }

    public int value()
    {
        return mValue;
    }

    public boolean equals(PasswordScheme other)
    {
        return (mValue == other.mValue);
    }

    private int mValue;
}
