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
