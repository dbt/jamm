package jamm.webapp;

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
        this("", "");
    }

    /**
     * Constructs a new user.
     *
     * @param username The username
     * @param password The password
     */
    public User(String username, String password)
    {
        mUsername = username;
        mPassword = password;
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
     * Returns this user's password.
     *
     * @return A password
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**  The username. */
    private String mUsername;

    /** The password. */
    private String mPassword;

}
