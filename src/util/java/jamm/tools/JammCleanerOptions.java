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

package jamm.tools;

/**
 * The options that any part of JammCleaner could need access to.
 */
public final class JammCleanerOptions
{
    /**
     * Gets the value of verbose
     *
     * @return the value of verbose
     */
    public static boolean isVerbose() 
    {
        return JammCleanerOptions.mVerbose;
    }

    /**
     * Sets the value of verbose
     *
     * @param verbose Value to assign to this.verbose
     */
    public static void setVerbose(boolean verbose)
    {
        JammCleanerOptions.mVerbose = verbose;
    }

    /**
     * Gets the value of assumeYes
     *
     * @return the value of assumeYes
     */
    public static boolean isAssumeYes() 
    {
        return JammCleanerOptions.mAssumeYes;
    }

    /**
     * Sets the value of assumeYes
     *
     * @param assumeYes Value to assign to this.assumeYes
     */
    public static void setAssumeYes(boolean assumeYes)
    {
        JammCleanerOptions.mAssumeYes = assumeYes;
    }

    /**
     * Gets the value of rootDn
     *
     * @return the value of rootDn
     */
    public static String getRootDn() 
    {
        return JammCleanerOptions.mRootDn;
    }

    /**
     * Sets the value of rootDn
     *
     * @param rootDn Value to assign to this.rootDn
     */
    public static void setRootDn(String rootDn)
    {
        JammCleanerOptions.mRootDn = rootDn;
    }

    /**
     * Gets the value of password
     *
     * @return the value of password
     */
    public static String getPassword() 
    {
        return JammCleanerOptions.mPassword;
    }

    /**
     * Sets the value of password
     *
     * @param password Value to assign to this.password
     */
    public static void setPassword(String password)
    {
        JammCleanerOptions.mPassword = password;
    }

    /**
     * Gets the value of host
     *
     * @return the value of host
     */
    public static String getHost() 
    {
        return JammCleanerOptions.mHost;
    }

    /**
     * Sets the value of host
     *
     * @param host Value to assign to this.host
     */
    public static void setHost(String host)
    {
        JammCleanerOptions.mHost = host;
    }

    /**
     * Gets the value of port
     *
     * @return the value of port
     */
    public static int getPort() 
    {
        return JammCleanerOptions.mPort;
    }

    /**
     * Sets the value of port
     *
     * @param port Value to assign to this.port
     */
    public static void setPort(int port)
    {
        JammCleanerOptions.mPort = port;
    }

    public static String argDump()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("verbose: ").append(mVerbose).append("\n");
        sb.append("assume yes: ").append(mAssumeYes).append("\n");
        sb.append("host: ").append(mHost).append("\n");
        sb.append("port: ").append(mPort).append("\n");

        return sb.toString();
    }
        
    private static boolean mVerbose = false;
    private static boolean mAssumeYes = false;
    private static String mRootDn = null;
    private static String mPassword = null;
    private static String mHost = "localhost";
    private static int mPort = 389;
}
