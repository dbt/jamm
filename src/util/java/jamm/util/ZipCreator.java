package jamm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.CRC32;

public class ZipCreator
{
    public ZipCreator(String filename)
    {
        this(new File(filename));
    }

    public ZipCreator(File file)
    {
        mFile = file;
    }

    public void open()
    {
        try
        {
            OutputStream os = new FileOutputStream(mFile);
            mZos = new ZipOutputStream(os);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Can't open: " + e);
        }
    }

    public void add(String name)
        throws IOException
    {
        add(new File(name));
    }

    public void add(File file)
        throws IOException
    {
        if(file.isDirectory())
        {
            addDirectory(file);
        }
        else
        {
            addFile(file);
        }
    }

    /*
    private String getZipName(File file)
    {
        String parent = aFile.getParent();
        String zipName;

        if (parent == null || parent.equals("null") || parent.equals("."))
        {
            zipName = aFile.getName();
        }
        else
        {
            zipName = parent + aFile.separator + aFile.getName();
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
    */

    private void addFile(File aFile)
        throws IOException
    {
        String parent = aFile.getParent();
        String zipName;
        if (parent == null || parent.equals("null") || parent.equals("."))
        {
            zipName = aFile.getName();
        }
        else
        {
            zipName = parent + aFile.separator + aFile.getName();
        }

        // Strip off leading '/'
        if (zipName.charAt(0) == '/')
        {
            System.err.println("Stripping off leading '/'");
            zipName = zipName.substring(1);
        }
        ZipEntry ze = new ZipEntry(zipName);
        ze.setTime(aFile.lastModified());

        System.out.println("Adding " + zipName);

        // Don't compress stuff so small it makes it bigger!
        if (aFile.length() < 51)
        {
            mZos.setMethod(mZos.STORED);
            ze.setSize(aFile.length());
            ze.setCompressedSize(aFile.length());
            ze.setCrc(calcCRC32(aFile));
        }
        else
        {
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
        

    private void addDirectory(File dir)
        throws IOException
    {
        if (!dir.isDirectory())
        {
            return;
        }

        // the following is test code
        String parent = dir.getParent();
        String zipName;
        if (parent == null || parent.equals("null") || parent.equals("."))
        {
            zipName = dir.getName() + dir.separator;
        }
        else
        {
            zipName = parent + dir.separator + dir.getName() + dir.separator;
        }

        // Strip off leading '/'
        if (zipName.charAt(0) == '/')
        {
            System.err.println("Stripping off leading '/'");
            zipName = zipName.substring(1);
        }
        ZipEntry ze = new ZipEntry(zipName);
        ze.setTime(dir.lastModified());
        ze.setSize(0);
        ze.setCompressedSize(0);
        ze.setCrc(0);
        System.out.println("Adding " + zipName);

        mZos.setMethod(mZos.STORED);
        mZos.putNextEntry(ze);
        
        // end test code

        
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

    public void close()
        throws IOException
    {
        mZos.close();
    }

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
            zc.addDirectory(new File(args[0]));
        }
        zc.close();
    }

    private File mFile;
    private ZipOutputStream mZos;
}
