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
 * This is the application that cleans up after jamm.
 */
public final class JammCleaner
{
    /**
     * Print out the help message
     */
    private static void printHelp()
    {
        System.out.println("Usage:  JammCleaner -b <dn> -D <dn> " +
                           "[other options]");
        System.out.println("    -v    act verbosely");
        System.out.println("    -y    assume \"yes\" for all questions");
        System.out.println("    -h    ldap host   (default: localhost)");
        System.out.println("    -p    ldap port   (default: 389)");
        System.out.println("    -w    password to connect to LDAP with");
        System.out.println("    -D    the DN to bind with");
        System.out.println("    -b    base dn to search from");
    }

    /**
     * Parse the arguements that were passed in on the command line.
     *
     * @param argv The array of arguments
     */
    private static void parseArgs(String argv[])
    {
        for (int i = 0; i < argv.length; i++)
        {
            if (argv[i].startsWith("-"))
            {
                if (argv[i].equals("--help"))
                {
                    printHelp();
                    System.exit(1);
                }
                
                switch (argv[i].charAt(1))
                {
                    case 'v':
                        JammCleanerOptions.setVerbose(true);
                        break;

                    case 'y':
                        JammCleanerOptions.setAssumeYes(true);
                        break;

                    case 'h':
                        if (argv[i].length() > 2)
                        {
                            JammCleanerOptions.setHost(argv[i].substring(2));
                        }
                        else
                        {
                            String nextArg = null;
                            try
                            {
                                nextArg = argv[i + 1];
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                // We could run out of arguments
                            }
                            if ((nextArg != null) &&
                                (!nextArg.startsWith("-")))
                            {
                                JammCleanerOptions.setHost(nextArg);
                                i++;
                            }
                            else
                            {
                                System.err.println("-h must have an argument");
                                System.exit(1);
                            }
                        }
                        break;
                        
                    case 'p':
                        if (argv[i].length() > 2)
                        {
                            JammCleanerOptions.setPort(
                                Integer.parseInt(argv[i].substring(2)));
                        }
                        else
                        {
                            String nextArg = null;
                            try
                            {
                                nextArg = argv[i + 1];
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                // We could run out of arguments
                            }
                            if ((nextArg != null &&
                                 (!nextArg.startsWith("-"))))
                            {
                                JammCleanerOptions.setPort(
                                    Integer.parseInt(nextArg));
                                i++;
                            }
                            else
                            {
                                System.err.println("-p must have an argument");
                                System.exit(1);
                            }
                        }
                        break;

                    // Followed convention from OpenLDAP tools
                    case 'w':
                        if (argv[i].length() > 2)
                        {
                            JammCleanerOptions.setPassword(
                                argv[i].substring(2));
                        }
                        else
                        {
                            String nextArg = null;
                            try
                            {
                                nextArg = argv[i + 1];
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                // Hmm
                            }
                            if ((nextArg != null) &&
                                (!nextArg.startsWith("-")))
                            {
                                JammCleanerOptions.setPassword(nextArg);
                                i++;
                            }
                            else
                            {
                                System.err.println("-w must have an argument");
                                System.exit(1);
                            }
                        }
                        break;

                    // Followed convention from OpenLDAP tools
                    case 'D':
                        if (argv[i].length() > 2)
                        {
                            JammCleanerOptions.setRootDn(argv[i].substring(2));
                        }
                        else
                        {
                            String nextArg = null;
                            try
                            {
                                nextArg = argv[i + 1];
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                // Hmm
                            }
                            if ((nextArg != null) &&
                                (!nextArg.startsWith("-")))
                            {
                                JammCleanerOptions.setRootDn(nextArg);
                                i++;
                            }
                            else
                            {
                                System.err.println("-D must have an argument");
                                System.exit(1);
                            }
                        }
                        break;

                    case 'b':
                        if (argv[i].length() > 2)
                        {
                            JammCleanerOptions.setBaseDn(argv[i].substring(2));
                        }
                        else
                        {
                            String nextArg = null;
                            try
                            {
                                nextArg = argv[i + 1];
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                // Hmm
                            }
                            if ((nextArg != null) &&
                                (!nextArg.startsWith("-")))
                            {
                                JammCleanerOptions.setBaseDn(nextArg);
                                i++;
                            }
                            else
                            {
                                System.err.println("-b must have an argument");
                                System.exit(1);
                            }
                        }
                        break;
                }
            }
        }
    }
    
    /**
     * Our main method
     *
     * @param argv the array of args
     */
    public static final void main(String argv[])
    {
        parseArgs(argv);
        System.out.println(JammCleanerOptions.argDump());
    }
}
