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

package jamm;

/**
 * Constants used during unit testing.
 */
public class LdapConstants
{
    /** LDAP server: <code>localhost</code> */
    public static final String HOST = "localhost";

    /** LDAP port: 389 */
    public static final int PORT = 389;

    /** Root DN: <code>cn=Manager,dc=jamm,dc=test</code> */
    public static final String MGR_DN = "cn=Manager,dc=jamm,dc=test";

    /** Root password: <code>jammtest</code> */
    public static final String MGR_PW = "jammtest";

    /**
     * DN for account 1: <code>mail=acct1@domain1.test,
     * jvd=domain1.test, o=hosting, dc=jamm, dc=test</code>
     */
    public static final String ACCT1_DN =
        "mail=acct1@domain1.test,jvd=domain1.test,o=hosting,dc=jamm,dc=test";

    /** Passowrd for account 1: <code>acct1pw</code> */
    public static final String ACCT1_PW = "acct1pw";

    /**
     * Hashed password for account 1:
     * <code>{SSHA}tk3w4vV6xghX4r7P0F1EAeA55jo53sSO</code>
     */
    public static final String ACCT1_PW_HASHED =
        "{SSHA}tk3w4vV6xghX4r7P0F1EAeA55jo53sSO";
}
