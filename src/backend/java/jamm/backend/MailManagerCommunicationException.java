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
 * An exception thrown when one cannot communicate to the backend.
 */
public class MailManagerCommunicationException
    extends MailManagerException
{
    /**
     * Creates a new <code>MailManagerCommunicationException</code> instance.
     *
     */
    public MailManagerCommunicationException()
    {
        super();
    }

    /**
     * Creates a new <code>MailManagerCommunicationException</code> instance.
     *
     * @param message Descriptive message to go with the exception.
     */
    public MailManagerCommunicationException(String message)
    {
        super(message);
    }

    /**
     * Creates a new <code>MailManagerCommunicationException</code> instance.
     *
     * @param message Descriptive message to go with the exception.
     * @param cause The throwable that caused this exception.
     */
    public MailManagerCommunicationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Creates a new <code>MailManagerCommunicationException</code> instance.
     *
     * @param cause The throwable that caused this exception.
     */
    public MailManagerCommunicationException(Throwable cause)
    {
        super(cause);
    }
}
