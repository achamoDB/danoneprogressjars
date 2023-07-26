// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Blob;

public class ProBlob implements Blob
{
    private byte[] byteArray;
    
    public ProBlob(final byte[] byteArray) {
        this.byteArray = null;
        this.byteArray = byteArray;
    }
    
    public long length() throws SQLException {
        return this.byteArray.length;
    }
    
    public byte[] getBytes(final long n, final int n2) throws SQLException {
        final byte[] array = new byte[n2];
        System.arraycopy(this.byteArray, (int)(n - 1L), array, 0, n2);
        return array;
    }
    
    public InputStream getBinaryStream() throws SQLException {
        return new ByteArrayInputStream(this.byteArray);
    }
    
    public long position(final byte[] bytes, final long n) throws SQLException {
        return new String(this.byteArray).indexOf(new String(bytes), (int)n);
    }
    
    public long position(final Blob blob, final long n) throws SQLException {
        return this.position(blob.getBytes(0L, (int)blob.length()), n);
    }
    
    public int setBytes(final long n, final byte[] array) throws SQLException {
        throw new SQLException();
    }
    
    public int setBytes(final long n, final byte[] array, final int n2, final int n3) throws SQLException {
        throw new SQLException();
    }
    
    public OutputStream setBinaryStream(final long n) throws SQLException {
        throw new SQLException();
    }
    
    public void truncate(final long n) throws SQLException {
        throw new SQLException();
    }
}
