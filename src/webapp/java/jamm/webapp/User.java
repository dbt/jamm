package jamm.webapp;

import java.util.Set;
import java.util.Collections;
import java.io.Serializable;

/**
 * An immutable bean to hold user information.
 */
public class User implements Serializable
{
    /**
     * Constructs an "empty" user.  An "empty" user contains no real
     * information, just dummy data.
     */
    public User()
    {
        this("", "", "", Collections.EMPTY_SET);
    }

    /**
     * Constructs a new user.
     *
     * @param username The username.
     * @param dn The distinguished name.
     * @param password The password.
     */
    public User(String username, String dn, String password, Set roles)
    {
        mUsername = username;
        mDn = dn;
        mPassword = password;
        mRoles = roles;
    }

    /**
     * Checks to see if this user is an "empty" user.
     */
    public boolean isEmpty()
    {
        return (mUsername.equals(""));
    }

    /**
     * Returns this user's username.
     *
     * @return A username
     */
    public String getUsername()
    {
        return mUsername;
    }

    /**
     * Returns this users's LDAP distinguished name.
     *
     * @return An LDAP DN
     */
    public String getDn()
    {
        return mDn;
    }

    /**
     * Returns this user's password.
     *
     * @return A password
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets this user's password.
     *
     * @param password New password
     */
    public void setPassword(String password)
    {
        mPassword = password;
    }

    /**
     * Verifies if user is in a role.
     *
     * @param role Role to check for.
     * @return <code>true</code> if user is in the role.
     */
    public boolean isUserInRole(String role)
    {
        return mRoles.contains(role);
    }

    /** String for domain administrator. */
    public static final String DOMAIN_ADMIN_ROLE = "Domain Administrator";

    /** String for site administrator. */
    public static final String SITE_ADMIN_ROLE = "Site Administrator";

    /**  The username. */
    private String mUsername;

    /** The distinguished name. */
    private String mDn;

    /** The password. */
    private String mPassword;

    private Set mRoles;
}
