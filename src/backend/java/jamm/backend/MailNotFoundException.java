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
 * A element with the specified mail address could not be found.
 */
public class MailNotFoundException
    extends MailManagerException
{
    /**
     * Create a new instance with the specified mail address, but no
     * root cause.
     *
     * @param message Message of this exception
     */
    public MailNotFoundException(String mail)
    {
        super(mail);
    }

    /**
     * Create a new instance with the specified mail address and root
     * cause.
     *
     * @param message Message of this exception
     * @param cause Root cause of this exception
     */
    public MailNotFoundException(String mail, Throwable cause)
    {
        super(mail, cause);
    }
}
