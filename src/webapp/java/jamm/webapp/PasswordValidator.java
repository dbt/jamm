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

package jamm.webapp;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * Utility class that provides static methods for validating passwords
 * and their "goodness."
 */
public class PasswordValidator
{
    /**
     * The same as validatePassword(String, ActionErrors), except that
     * the two passwords must match before checking the validity.
     *
     * @param password a <code>String</code> containing the password
     * @param retypedPassword a <code>String</code> containing the
     *                        retyped password
     * @param errors an <code>ActionErrors</code> object that errors
     *               can be added to.
     *
     * @return true when a password is good, false when not
     */
    public static boolean validatePassword(String password,
                                           String retypedPassword,
                                           ActionErrors errors)
    {
        if ((password != null) && (password.equals(retypedPassword)))
        {
            return validatePassword(password, errors);
        }

        errors.add("password",
                   new ActionError("password.error.do_not_match"));
        return false;
    }

    /**
     * Both passwords must match and must pass certain "bad password"
     * smoke tests.  For now, only the length of the password is
     * considered, but more elaborate tests such as dictionary tests
     * could be performed.
     *
     * @param password a <code>String</code> containing the password
     * @param errors an <code>ActionErrors</code> object that errors
     *               can be added to.
     *
     * @return true when good, false when bad
     */
    public static boolean validatePassword(String password,
                                           ActionErrors errors)
    {
        if ((password == null) || (password.length() < MINIMUM_LENGTH))
        {
            errors.add("password",
                       new ActionError("password.error.too_short"));
            return false;
        }

        return true;
    }

    /** Minimum length that a password should be. */
    private static final int MINIMUM_LENGTH = 5;
}
