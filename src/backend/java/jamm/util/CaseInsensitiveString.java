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

package jamm.util;

/**
 * A case insensitive, yet case presverving, variant of
 * <code>String</code>.  This means that comparison and hash code
 * operations are case insensitive, but the string, with its original
 * case, may be retrieved.  Also, a <code>String</code> may be used in
 * many places to achieve easier usability.  Thus the following holds
 * true:
 *
 * <pre>
 *     CaseInsensitiveString ciString1 = new CaseInsensitiveString("apple");
 *     CaseInsensitiveString ciString2 = new CaseInsensitiveString("ApPlE");
 *
 *     ciString1.equals("aPpLe") == true;
 *     ciString1.equals(ciString2) == true;
 *
 *     ciString1.compareTo("aPpLe") == 0;
 *     ciString1.compareTo(cistring2) == 0;
 *
 *     ciString1.hashCode().equals(ciString2.hashCode()) == true;
 * </pre>
 *
 * @see String
 */
public class CaseInsensitiveString implements Comparable
{
    /**
     * Creates an empty case insensitive string.
     */
    public CaseInsensitiveString()
    {
        mString = "";
    }

    /**
     * Creates a case insensitive string from a normal string,
     * preserving the case.
     *
     * @param string A normal string.
     */
    public CaseInsensitiveString(String string)
    {
        mString = string;
    }

    /**
     * Compares this object to a <code>CaseInsensitiveString</code> or
     * <code>String</code>.  This calls either {@link
     * #compareTo(CaseInsensitiveString)} or {@link
     * #compareTo(String)}.
     *
     * @param object Object to be compared
     * @return A case insensitive comparison
     * @throws ClassCastException if the argument is not a
     * <code>CaseInsensitiveString</code> or <code>String</code>
     */
    public int compareTo(Object object)
        throws ClassCastException
    {
        if (object instanceof String)
        {
            String string = (String) object;
            return compareTo(string);
        }
        else if (object instanceof CaseInsensitiveString)
        {
            CaseInsensitiveString ciString = (CaseInsensitiveString) object;
            return compareTo(ciString);
        }
        else
        {
            throw new ClassCastException(
                "Not a String or CaseInsensitiveString");
        }
    }

    /**
     * Compares this object to a normal string, ignoring the case of
     * the normal string.
     *
     * @param string String to be compared
     * @return A case insensitive comparison
     */
    public int compareTo(String string)
    {
        return mString.compareToIgnoreCase(string);
    }

    /**
     * Compares this object to a case insensitive string.
     *
     * @param ciString Case insensitive string to be compared
     * @return A case insensitive comparison
     */
    public int compareTo(CaseInsensitiveString ciString)
    {
        return mString.compareToIgnoreCase(ciString.mString);
    }

    /**
     * Compares this object to a <code>CaseInsensitiveString</code> or
     * <code>String</code>.  This calls either {@link
     * #equals(CaseInsensitiveString)} or {@link
     * #equals(String)}.
     *
     * @param object Object to be compared
     * @return True if the strings are equal, ignoring case
     */
    public boolean equals(Object object)
    {
        if (object instanceof String)
        {
            String string = (String) object;
            return equals(string);
        }
        else if (object instanceof CaseInsensitiveString)
        {
            CaseInsensitiveString ciString = (CaseInsensitiveString) object;
            return equals(ciString);
        }
        else
        {
            return false;
        }
    }

    /**
     * Compares this object to a normal string, ignoring the case of
     * the normal string.
     *
     * @param string String to be compared
     * @return True if the strings are equals, ignoring case
     */
    public boolean equals(String string)
    {
        return mString.equalsIgnoreCase(string);
    }

    /**
     * Compares this object to a case insensitive string.
     *
     * @param ciString Case insensitive string to be compared
     * @return True if the strings are equals, ignoring case
     */
    public boolean equals(CaseInsensitiveString ciString)
    {
        return mString.equalsIgnoreCase(ciString.mString);
    }

    /**
     * Returns the original string with the case intact.
     *
     * @return The original string with the case intact
     */
    public String toString()
    {
        return mString;
    }

    /**
     * Returns the hash code of this string.  The string is converted
     * to lower case before calculating the hash code.
     *
     * @return The hash code of this string.
     */
    public int hashCode()
    {
        return mString.toUpperCase().toLowerCase().hashCode();
    }

    /** The original string. */
    private String mString;
}
