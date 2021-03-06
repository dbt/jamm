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
 * Extends <code>Exception</code> to provide a root cause exception.
 * This allows exceptions to be rethrown as a different exception, yet
 * keep all the original exception information.
 */
public class ChainedException extends Exception
{
    /**
     * Create a new instance with no message and no root cause.
     */
    public ChainedException()
    {
        super();
    }

    /**
     * Create a new instance with a message, but no root cause.
     *
     * @param message Message of this exception
     */
    public ChainedException(String message)
    {
        super(message);
    }

    /**
     * Create a new instance with a message and a root cause.
     *
     * @param message Message of this exception
     * @param cause Root cause of this exception
     */
    public ChainedException(String message, Throwable cause)
    {
        super(message);
        this.mCause = cause;
    }

    /**
     * Create a new instance with a root cause, but no message.
     *
     * @param cause Root cause of this exception
     */
    public ChainedException(Throwable cause)
    {
        super();
        this.mCause = cause;
    }

    /**
     * Gets the root cause of this exception. <code>null</code> is
     * returned if there is no cause.
     *
     * @return The root cause of this exception.
     */
    public Throwable getCause()
    {
        return mCause;
    }

    /**
     * Prints the full stack trace, including the trace of the cause,
     * to standard error.
     */
    public void printStackTrace()
    {
        super.printStackTrace();
        if (mCause != null)
        {
            System.err.println("Caused by:");
            mCause.printStackTrace();
        }
    }

    /**
     * Prints the full stack trace, including the trace of the cause,
     * to a PrintStream.
     *
     * @param ps PrintStream to print to
     */
    public void printStackTrace(java.io.PrintStream ps)
    {
        super.printStackTrace(ps);
        if (mCause != null)
        {
            ps.println("Caused by:");
            mCause.printStackTrace(ps);
        }
    }

    /**
     * Prints the full stack trace, including the trace of the cause,
     * to a PrintWriter.
     *
     * @param pw PrintWriter to print to
     */
    public void printStackTrace(java.io.PrintWriter pw)
    {
        super.printStackTrace(pw);
        if (mCause != null)
        {
            pw.println("Caused by:");
            mCause.printStackTrace(pw);
        }
    }

    /** The root cause of this exception. */
    private Throwable mCause = null;
}
