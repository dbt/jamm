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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipCreator is a facade type class to add in the creation of a zip file.
 * Example code:
 * <pre>
 *      ZipCreator zc = new ZipCreator("/tmp/dude.test.zip");
 *      zc.open();
 *      zc.add("./build.xml");
 *      zc.add("NEWS");
 *      zc.add("README");
 *      zc.add("/usr/src/linux-2.4.19.tar.bz2");
 *      zc.close();
 * </pre>
 */
public class ZipCreator
{
    /**
     * Creates a new <code>ZipCreator</code> instance.
     *
     * @param filename The file name of the new zip file.
     */
    public ZipCreator(String filename)
    {
        this(new File(filename));
    }

    /**
     * Creates a new <code>ZipCreator</code> instance.
     *
     * @param file a File object for the new zip file.
     */
    public ZipCreator(File file)
    {
        mFile = file;
    }

    /**
     * Open the new zip file for writing.
     *
     * @exception FileNotFoundException if an error occurs
     */
    public void open()
        throws FileNotFoundException
    {
            OutputStream os = new FileOutputStream(mFile);
            mZos = new ZipOutputStream(os);
    }

    /**
     * Add a file or directory to the zip file.  Note: directory adds
     * will be recursive.
     *
     * @param name The name of the file or directory to be added.
     * @exception IOException if an error occurs
     */
    public void add(String name)
        throws IOException
    {
        add(new File(name));
    }

    /**
     * Add a file or directory to the zip file.  Note: directory adds
     * will be recursive.
     *
     * @param file the File object representing the file or directory to add.
     * @exception IOException if an error occurs
     */
    public void add(File file)
        throws IOException
    {
        if (file.isDirectory())
        {
            addDirectory(file);
        }
        else
        {
            addFile(file);
        }
    }

    /**
     * Helper method to get a filename in Zip that is clean and sane.
     *
     * @param file the File object to make a ZipName for.
     * @return a String containing the name.
     */
    private String getZipName(File file)
    {
        String parent = file.getParent();
        String zipName;

        if (parent == null || parent.equals("null") || parent.equals("."))
        {
            zipName = file.getName();
        }
        else
        {
            zipName = parent + file.separator + file.getName();
        }

        // Strip off leading '/'
        if (zipName.charAt(0) == '/')
        {
            System.err.println("Stripping off leading '/'");
            zipName = zipName.substring(1);
        }

        // If the file is a directory, tack on an ending '/'
        if (file.isDirectory())
        {
            zipName = zipName + '/';
        }

        return zipName;
    }

    /**
     * Adds a file to the zip file.
     *
     * @param aFile the file to add to the zip file.
     * @exception IOException if an error occurs
     */
    private void addFile(File aFile)
        throws IOException
    {
        if (aFile.isDirectory())
        {
            throw new IOException(aFile.getName() + " is a directory.");
        }
        
        String zipName = getZipName(aFile);
        ZipEntry ze = new ZipEntry(zipName);
        ze.setTime(aFile.lastModified());

        System.out.println("Adding " + zipName);

        // Don't compress really small stuff
        if (aFile.length() < 51)
        {
            // If we just store it, we need to calculate some of the data
            mZos.setMethod(mZos.STORED);
            ze.setSize(aFile.length());
            ze.setCompressedSize(aFile.length());
            ze.setCrc(calcCRC32(aFile));
        }
        else
        {
            // If we are deflating, java does it for us
            mZos.setMethod(mZos.DEFLATED);
        }
        
        mZos.putNextEntry(ze);
        byte[] buf = new byte[2048];
        int read;
        InputStream is = new FileInputStream(aFile);
        while ((read = is.read(buf, 0, buf.length)) > -1)
        {
            mZos.write(buf, 0, read);
        }
        is.close();
    }

    /**
     * Helper method to calculate the crc32 on a file
     *
     * @param file the file to get the crc on
     * @return a long of the crc result
     * @exception IOException if an error occurs
     */
    private long calcCRC32(File file)
        throws IOException
    {
        CRC32 crc = new CRC32();
        byte[] buf = new byte[2048];
        int read;
        InputStream is = new FileInputStream(file);
        while ((read = is.read(buf, 0, buf.length)) > -1)
        {
            crc.update(buf, 0, read);
        }
        is.close();
        return crc.getValue();
    }
        

    /**
     * This function is recursive.  It will add the entire directory tree
     * to the zip file.
     *
     * @param dir the directory to add to the zip file
     * @exception IOException if an error occurs
     */
    private void addDirectory(File dir)
        throws IOException
    {
        if (!dir.isDirectory())
        {
            throw new IOException(dir.getName() + " is not a directory.");
        }

        String zipName = getZipName(dir);
        ZipEntry ze = new ZipEntry(zipName);
        ze.setTime(dir.lastModified());
        ze.setSize(0);
        ze.setCompressedSize(0);
        ze.setCrc(0);

        System.out.println("Adding " + zipName);

        mZos.setMethod(mZos.STORED);
        mZos.putNextEntry(ze);
        
        File[] contents = dir.listFiles();
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (contents[i].isDirectory())
                {
                    addDirectory(contents[i]);
                }
                else
                {
                    addFile(contents[i]);
                }
            }
        }
    }

    /**
     * Close the zip file.
     *
     * @exception IOException if an error occurs
     */
    public void close()
        throws IOException
    {
        mZos.close();
    }

    /**
     * main method for testing
     *
     * @param args the arguments
     * @exception Exception if an error occurs
     */
    public static void main(String args[])
        throws Exception
    {
        ZipCreator zc = new ZipCreator("/tmp/dude.test.zip");
        zc.open();
        if (args.length < 1)
        {
            zc.add("./build.xml");
            zc.add("NEWS");
            zc.add("README");
            zc.add("/usr/src/linux-2.4.19.tar.bz2");
            zc.add("build/jamm-0.9.3.war");
            zc.add("doc");
        }
        else
        {
            zc.add(args[0]);
        }
        zc.close();
    }

    /** A File object representing the zip file */
    private File mFile;
    /** the zip output stream we're writing to */
    private ZipOutputStream mZos;
}
