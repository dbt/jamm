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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.GnuParser;

/**
 * This is the application that cleans up after jamm.
 */
public final class JammCleaner
    implements JammCleanerDefines
{
    /**
     * Print out the help message
     *
     * @param opt the options for this application
     */
    private static void printHelp(Options opt)
    {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("JammCleaner [options]" , opt);
    }

    /**
     * Loads up the options object
     *
     * @return a loaded Options object
     */
    private static Options getOptions()
    {
        Option opt = null;
        Options opts = new Options();
        opts.addOption("v", "verbose", NO_ARGS, "verbose");
        opts.addOption("y", "yes", NO_ARGS, "Assume yes for all questions");
        opts.addOption("h", HAS_ARGS, "ldap host  (default: localhost)");
        opts.addOption("p", HAS_ARGS, "ldap port  (default: 389)");
        opts.addOption("w", "password", HAS_ARGS,
                       "password to connect to LDAP with");
        opts.addOption("n", "no-execute", NO_ARGS,
                       "Print what would be executed, but do not execute.");

        opt = new Option("D", "dn", HAS_ARGS, "the dn to bind with");
//        opt.setRequired(true);
        opts.addOption(opt);

        opt = new Option("b", "base", HAS_ARGS, "base dn to search from");
//        opt.setRequired(true);
        opts.addOption(opt);
        opts.addOption("B", "backups", HAS_ARGS, "back up account contents " +
                       "to this directory before deleting");
        opts.addOption("?", "help", NO_ARGS, "prints help message");

        return opts;
    }

    private static void sanityCheck()
    {
        String bindDN = JammCleanerOptions.getBindDn();
        boolean bindDNEmpty = bindDN == null || bindDN.equals("");

        String base = JammCleanerOptions.getBaseDn();
        boolean baseEmpty = base == null || base.equals("");

        if (bindDNEmpty)
        {
            System.err.println("dn must be specified via -D or in " +
                               "jammCleaner.properties");
        }
        
        if (baseEmpty)
        {
            System.err.println("search base dn must be specififed ia -b or" +
                               " in jammCleaner.properties");
        }
        
        if (bindDNEmpty || baseEmpty)
        {
            System.exit(1);
        }
    }

    /**
     * Parse the command line and do what is needed.
     *
     * @param opts The options
     * @param cmd the command line/parsed options
     */
    private static void parseArgs(Options opts, CommandLine cmd)
    {
        if (cmd.hasOption('?'))
        {
            printHelp(opts);
            System.exit(0);
        }
        if (cmd.hasOption('v'))
        {
            JammCleanerOptions.setVerbose(true);
        }
        if (cmd.hasOption('y'))
        {
            JammCleanerOptions.setAssumeYes(true);
        }
        if (cmd.hasOption('h'))
        {
            JammCleanerOptions.setHost(cmd.getOptionValue('h', "localhost"));
        }
        if (cmd.hasOption('p'))
        {
            String value = cmd.getOptionValue('p', "389");
            JammCleanerOptions.setPort(Integer.parseInt(value));
        }
        if (cmd.hasOption('w'))
        {
            JammCleanerOptions.setPassword(cmd.getOptionValue('w'));
        }
        if (cmd.hasOption('D'))
        {
            JammCleanerOptions.setBindDn(cmd.getOptionValue('D'));
        }
        if (cmd.hasOption('b'))
        {
            JammCleanerOptions.setBaseDn(cmd.getOptionValue('b'));
        }
        if (cmd.hasOption('n'))
        {
            JammCleanerOptions.setNonDestructive(true);
        }
        if (cmd.hasOption('B'))
        {
            JammCleanerOptions.setBackupDirectory(cmd.getOptionValue('B'));
        }
    }
    
    /**
     * Load properties from jammCleaner.properties in the classpath.
     * Command line options override this.
     */
    private static void loadProperties()
    {
        // todo finish making jammCleaner more cron friendly.
        ClassLoader classLoader = JammCleaner.class.getClassLoader();
        InputStream is =
            classLoader.getResourceAsStream("jammCleaner.properties");
        if (is == null)
        {
            return;
        }
        
        Properties prop = new Properties();
        try
        {
            prop.load(is);
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
        
        String tmp = prop.getProperty("jammCleaner.ldap.search_base");
        if (tmp != null)
        {
            JammCleanerOptions.setBaseDn(tmp);
        }

        tmp = prop.getProperty("jammCleaner.ldap.bind_dn");
        if (tmp != null)
        {
            JammCleanerOptions.setBindDn(tmp);
        }
        
        tmp = prop.getProperty("jammCleaner.ldap.password");
        if (tmp != null)
        {
            JammCleanerOptions.setPassword(tmp);
        }
        
        tmp = prop.getProperty("jammCleaner.assumeYes");
        if (tmp != null)
        {
            JammCleanerOptions.setAssumeYes(
                Boolean.valueOf(tmp).booleanValue());
        }
        
        tmp = prop.getProperty("jammCleaner.ldap.host");
        if (tmp != null)
        {
            JammCleanerOptions.setHost(tmp);
        }
        
        tmp = prop.getProperty("jammCleaner.ldap.port");
        if (tmp != null)
        {
            JammCleanerOptions.setPort(Integer.parseInt(tmp));
        }
        
        tmp = prop.getProperty("jammCleaner.backup_dir");
        if (tmp != null)
        {
            JammCleanerOptions.setBackupDirectory(tmp);
        }
        
    }
    
    /**
     * Our main method
     *
     * @param argv command line arguments
     * @exception Exception if an error occurs
     */
    public static final void main(String argv[])
    {
        Options opts = getOptions();
        CommandLine cmdl = null;
        Parser parser = new GnuParser();
        
        try
        {
            cmdl = parser.parse(opts, argv);
        }
        catch (MissingOptionException e)
        {
            System.out.println("Missing required options.");
            System.out.println(e.toString());
            printHelp(opts);
            System.exit(1);
        }
        catch (MissingArgumentException e)
        {
            System.out.println("Missing required Arguments.");
            // System.out.println(e.toString());
            printHelp(opts);
            System.exit(1);
        }
        catch (ParseException e)
        {
            System.out.println("Some other command line parsing error");
            printHelp(opts);
            System.exit(1);
        }
        
        // Load properties first
        loadProperties();
        // Allow command line args to override properties file
        parseArgs(opts, cmdl);
        sanityCheck();
        
        // System.out.println(JammCleanerOptions.argDump());

        AccountCleaner ac = new AccountCleaner();
        ac.cleanUp();
        DomainCleaner dc = new DomainCleaner();
        dc.cleanUp();
    }
}
