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

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Useful filesystem utilities
 */
public class FileUtils
{
    /**
     * Recursively deletes a directory and all of its contents
     *
     * @param dir A directory to nuke
     * @return boolean signifying success
     */
    public static boolean recursiveDelete(File dir)
    {
        if (!dir.isDirectory())
        {
            return false;
        }

        File[] contents = dir.listFiles();

        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                // Skip directories if they are symbolic links.
                // Should get around the nuking of courier shared
                // folders.
                if (contents[i].isDirectory() &&
                    isNotSymbolicLink(contents[i]))
                {
                    // Changed logic to fix problem Max Matslofva
                    // has been seeing in lack of deleting.
                    recursiveDelete(contents[i]);
                }
                else
                {
                    boolean successful = contents[i].delete();
                    if (!successful)
                    {
                        return successful;
                    }
                }
            }
        }
        
        return dir.delete();
    }

    
    /**
     * Check to see if a File object is a symbolic link.  This code is
     * borrowed from Ant.  It may have false positives on some
     * platforms.
     *
     * @param file The file object to test
     * @return true is a symbolic link, false otherwise
     */
    public static boolean isNotSymbolicLink(File file)
    {
        boolean result = false;
        try
        {
            result = file.getAbsolutePath().equals(file.getCanonicalPath());
        }
        catch (IOException e)
        {
            // If we have an exception, we couldn't figure out if the
            // file was a symbolic link or not.  To be safe, we'll
            // return false.
            LOG.info("Exception while figuring out symbolic link, " +
                      "returning false", e);
        }
        return result;
    }
    
    private static final Logger LOG = Logger.getLogger(FileUtils.class);
}