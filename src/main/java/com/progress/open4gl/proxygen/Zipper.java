// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipException;
import com.progress.open4gl.Open4GLException;
import java.util.zip.ZipFile;
import com.progress.message.pgMsg;

public class Zipper implements pgMsg
{
    public static String extractFromZip(final String s, final String name, final String s2) throws Open4GLException {
        String s3 = null;
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(s);
        }
        catch (ZipException ex) {
            throw new Open4GLException(8099442454849133674L, new Object[] { s, ex.getMessage() });
        }
        catch (IOException ex4) {
            throw new Open4GLException(8099442454849133675L, new Object[] { s });
        }
        final ZipEntry entry = zipFile.getEntry(name);
        if (entry == null) {
            throw new Open4GLException(8099442454849133677L, new Object[] { name, s });
        }
        byte[] array;
        try {
            final InputStream inputStream = zipFile.getInputStream(entry);
            final int available = inputStream.available();
            array = new byte[available];
            for (int i = inputStream.read(array); i < available; i += inputStream.read(array, i, available - i)) {}
        }
        catch (ZipException ex2) {
            throw new Open4GLException(8099442454849133674L, new Object[] { s, ex2.getMessage() });
        }
        catch (IOException ex3) {
            throw new Open4GLException(8099442454849133677L, new Object[] { name, s + "; " + ex3.getMessage() });
        }
        try {
            if (s2 != null) {
                final FileOutputStream fileOutputStream = new FileOutputStream(PGAppObj.getAbsFilename(null, s2));
                fileOutputStream.write(array);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            else {
                s3 = new String(array);
            }
        }
        catch (IOException ex5) {
            throw new Open4GLException(8099442454849135814L, new Object[] { s2 });
        }
        finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            }
            catch (IOException ex6) {}
        }
        return s3;
    }
}
