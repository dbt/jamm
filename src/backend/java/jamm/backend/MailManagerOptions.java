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

package jamm.backend;

/**
 * Options that apply to all MailManager instances.
 */
public final class MailManagerOptions
{
    /**
     * Gets the value of vmailHomedir
     *
     * @return the value of vmailHomedir
     */
    public static String getVmailHomedir() 
    {
        return MailManagerOptions.sVmailHomedir;
    }

    /**
     * Sets the value of vmailHomedir
     *
     * @param vmailHomedir Value to assign to this.vmailHomedir
     */
    public static void setVmailHomedir(String vmailHomedir)
    {
        MailManagerOptions.sVmailHomedir = vmailHomedir;
    }

    /**
     * Gets the value of usePasswordExOp
     *
     * @return the value of usePasswordExOp
     */
    public static boolean isUsePasswordExOp() 
    {
        return MailManagerOptions.sUsePasswordExOp;
    }

    /**
     * Sets the value of usePasswordExOp
     *
     * @param usePasswordExOp Value to assign to this.usePasswordExOp
     */
    public static void setUsePasswordExOp(boolean usePasswordExOp)
    {
        MailManagerOptions.sUsePasswordExOp = usePasswordExOp;
    }

    /** where does the vmail stuff live? */
    private static String sVmailHomedir;
    /** Should the password ExOp be used? */
    private static boolean sUsePasswordExOp;
}
