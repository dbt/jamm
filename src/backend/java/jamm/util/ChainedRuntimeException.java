/*
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

public class ChainedRuntimeException extends RuntimeException
{
    private Throwable cause = null;

    public ChainedRuntimeException()
    {
        super();
    }

    public ChainedRuntimeException(String message)
    {
        super(message);
    }

    public ChainedRuntimeException(String message, Throwable cause)
    {
        super(message);
        this.cause = cause;
    }

    public ChainedRuntimeException(Throwable cause)
    {
        super();
        this.cause = cause;
    }

    public Throwable getCause()
    {
        return cause;
    }

    public void printStackTrace()
    {
        super.printStackTrace();
        if (cause != null)
        {
            System.err.println("Caused by:");
            cause.printStackTrace();
        }
    }

    public void printStackTrace(java.io.PrintStream ps)
    {
        super.printStackTrace(ps);
        if (cause != null)
        {
            ps.println("Caused by:");
            cause.printStackTrace(ps);
        }
    }

    public void printStackTrace(java.io.PrintWriter pw)
    {
        super.printStackTrace(pw);
        if (cause != null)
        {
            pw.println("Caused by:");
            cause.printStackTrace(pw);
        }
    }
}