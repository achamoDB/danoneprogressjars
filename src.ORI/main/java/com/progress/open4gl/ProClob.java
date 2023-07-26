// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.io.Writer;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Reader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Clob;

public class ProClob implements Clob
{
    private String stringData;
    
    public ProClob(final String stringData) {
        this.stringData = null;
        this.stringData = stringData;
    }
    
    public long length() throws SQLException {
        return this.stringData.length();
    }
    
    public InputStream getAsciiStream() throws SQLException {
        return new ByteArrayInputStream(this.stringData.getBytes());
    }
    
    public Reader getCharacterStream() throws SQLException {
        return new StringReader(this.stringData);
    }
    
    public String getSubString(final long n, final int n2) throws SQLException {
        if (n2 > this.stringData.length()) {
            throw new SQLException("Clob contains only " + this.stringData.length() + " characters (asking for " + n2 + ").");
        }
        return this.stringData.substring((int)n - 1, n2);
    }
    
    public long position(final String str, final long n) throws SQLException {
        return this.stringData.indexOf(str, (int)n);
    }
    
    public long position(final Clob clob, final long n) throws SQLException {
        return this.position(clob.getSubString(0L, (int)clob.length()), (int)n);
    }
    
    public OutputStream setAsciiStream(final long n) throws SQLException {
        throw new SQLException();
    }
    
    public Writer setCharacterStream(final long n) throws SQLException {
        throw new SQLException();
    }
    
    public int setString(final long n, final String s) throws SQLException {
        throw new SQLException();
    }
    
    public int setString(final long n, final String s, final int n2, final int n3) throws SQLException {
        throw new SQLException();
    }
    
    public void truncate(final long n) throws SQLException {
        throw new SQLException();
    }
}
