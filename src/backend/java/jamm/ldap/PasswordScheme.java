/*
 * Jamm
 * Copyright (C) 2002 Dave Dribin and Keith Garner
 *  
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jamm.ldap;

/**
 * An enumeration of all supported password schemes.
 */
public final class PasswordScheme
{
    /** Keeps track of enumeration values. */
    private static int mCount = 0;

    /** The password is not hashed at all. */
    public static final PasswordScheme CLEAR_SCHEME =
        new PasswordScheme(mCount++);

//    /** The Unix crypt(3) password scheme. */
//    public static final PasswordScheme CRYPT_SCHEME =
//        new PasswordScheme(mCount++);

    /** The MD5 password scheme. */
    public static final PasswordScheme MD5_SCHEME =
        new PasswordScheme(mCount++);

    /** The SHA password scheme. */
    public static final PasswordScheme SHA_SCHEME =
        new PasswordScheme(mCount++);

    /** The SHA + salt password scheme. */
    public static final PasswordScheme SSHA_SCHEME =
        new PasswordScheme(mCount++);

    /**
     * Returns the maximum value of the enumeration.
     *
     * @return The maximum value of the enumeration.
     */
    public static final int max()
    {
        return mCount;
    }

    /**
     * Create a new enumeration instance.
     *
     * @param value Enumeration value.
     */
    private PasswordScheme(int value)
    {
        mValue = value;
    }

    /**
     * This instances enumeration value.
     *
     * @return The enumeration value.
     */
    public int value()
    {
        return mValue;
    }

    /**
     * Two schemes are considered equal if their values match.
     *
     * @param other The other password scheme
     * @return True if the schemes are equal
     */
    public boolean equals(PasswordScheme other)
    {
        return (mValue == other.mValue);
    }

    /** The enumeration value of this instance. */
    private int mValue;
}
