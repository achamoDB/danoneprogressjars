// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.net.URL;
import java.util.Calendar;
import java.sql.Array;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Ref;
import java.util.Map;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.Reader;
import java.sql.ResultSetMetaData;
import java.sql.SQLWarning;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import com.progress.open4gl.Open4GLError;
import java.sql.ResultSet;

public class ProcListResultSet implements ResultSet
{
    private Integer procId;
    boolean endList;
    
    ProcListResultSet(final Object o) {
        this.procId = new Integer((int)(long)o);
        this.endList = false;
    }
    
    public boolean next() throws Open4GLError {
        return !this.endList && (this.endList = true);
    }
    
    public boolean wasNull() throws Open4GLError {
        return false;
    }
    
    public Object getObject(final int n) throws Open4GLError {
        switch (n) {
            case 1: {
                return this.procId;
            }
            default: {
                throw new Open4GLError();
            }
        }
    }
    
    public String getString(final int n) throws Open4GLError {
        return null;
    }
    
    public int getInt(final int n) throws Open4GLError {
        return 0;
    }
    
    public double getDouble(final int n) throws Open4GLError {
        return 0.0;
    }
    
    public boolean getBoolean(final int n) throws Open4GLError {
        return false;
    }
    
    public Date getDate(final int n) throws Open4GLError {
        return null;
    }
    
    public void close() throws Open4GLError {
    }
    
    public byte getByte(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public short getShort(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public long getLong(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public float getFloat(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public byte[] getBytes(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public Time getTime(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public Timestamp getTimestamp(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public InputStream getAsciiStream(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public InputStream getUnicodeStream(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public InputStream getBinaryStream(final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public String getString(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public boolean getBoolean(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public byte getByte(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public short getShort(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public int getInt(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public long getLong(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public float getFloat(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public double getDouble(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public BigDecimal getBigDecimal(final String s, final int n) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public BigDecimal getBigDecimal(final int n, final int n2) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public Date getDate(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public Time getTime(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public Timestamp getTimestamp(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public InputStream getAsciiStream(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public InputStream getUnicodeStream(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public InputStream getBinaryStream(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public SQLWarning getWarnings() throws Open4GLError {
        return null;
    }
    
    public void clearWarnings() throws Open4GLError {
    }
    
    public String getCursorName() throws Open4GLError {
        return new String();
    }
    
    public ResultSetMetaData getMetaData() throws Open4GLError {
        throw new Open4GLError();
    }
    
    public Object getObject(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public int findColumn(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public byte[] getBytes(final String s) throws Open4GLError {
        throw new Open4GLError();
    }
    
    public Reader getCharacterStream(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Reader getCharacterStream(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public BigDecimal getBigDecimal(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public BigDecimal getBigDecimal(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public boolean isBeforeFirst() throws SQLException {
        throw new SQLException();
    }
    
    public boolean isAfterLast() throws SQLException {
        throw new SQLException();
    }
    
    public boolean isFirst() throws SQLException {
        throw new SQLException();
    }
    
    public boolean isLast() throws SQLException {
        throw new SQLException();
    }
    
    public void beforeFirst() throws SQLException {
        throw new SQLException();
    }
    
    public void afterLast() throws SQLException {
        throw new SQLException();
    }
    
    public boolean first() throws SQLException {
        throw new SQLException();
    }
    
    public boolean last() throws SQLException {
        throw new SQLException();
    }
    
    public int getRow() throws SQLException {
        throw new SQLException();
    }
    
    public boolean absolute(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public boolean relative(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public boolean previous() throws SQLException {
        throw new SQLException();
    }
    
    public void setFetchDirection(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public int getFetchDirection() throws SQLException {
        throw new SQLException();
    }
    
    public void setFetchSize(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public int getFetchSize() throws SQLException {
        throw new SQLException();
    }
    
    public int getType() throws SQLException {
        throw new SQLException();
    }
    
    public int getConcurrency() throws SQLException {
        throw new SQLException();
    }
    
    public boolean rowUpdated() throws SQLException {
        throw new SQLException();
    }
    
    public boolean rowInserted() throws SQLException {
        throw new SQLException();
    }
    
    public boolean rowDeleted() throws SQLException {
        throw new SQLException();
    }
    
    public void updateNull(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBoolean(final int n, final boolean b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateByte(final int n, final byte b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateShort(final int n, final short n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateInt(final int n, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateLong(final int n, final long n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateFloat(final int n, final float n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDouble(final int n, final double n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBigDecimal(final int n, final BigDecimal bigDecimal) throws SQLException {
        throw new SQLException();
    }
    
    public void updateString(final int n, final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBytes(final int n, final byte[] array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDate(final int n, final Date date) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTime(final int n, final Time time) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTimestamp(final int n, final Timestamp timestamp) throws SQLException {
        throw new SQLException();
    }
    
    public void updateAsciiStream(final int n, final InputStream inputStream, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBinaryStream(final int n, final InputStream inputStream, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateCharacterStream(final int n, final Reader reader, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final int n, final Object o, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final int n, final Object o) throws SQLException {
        throw new SQLException();
    }
    
    public void updateNull(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBoolean(final String s, final boolean b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateByte(final String s, final byte b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateShort(final String s, final short n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateInt(final String s, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateLong(final String s, final long n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateFloat(final String s, final float n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDouble(final String s, final double n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBigDecimal(final String s, final BigDecimal bigDecimal) throws SQLException {
        throw new SQLException();
    }
    
    public void updateString(final String s, final String s2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBytes(final String s, final byte[] array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateDate(final String s, final Date date) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTime(final String s, final Time time) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTimestamp(final String s, final Timestamp timestamp) throws SQLException {
        throw new SQLException();
    }
    
    public void updateAsciiStream(final String s, final InputStream inputStream, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBinaryStream(final String s, final InputStream inputStream, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateCharacterStream(final String s, final Reader reader, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final String s, final Object o, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateObject(final String s, final Object o) throws SQLException {
        throw new SQLException();
    }
    
    public void insertRow() throws SQLException {
        throw new SQLException();
    }
    
    public void updateRow() throws SQLException {
        throw new SQLException();
    }
    
    public void deleteRow() throws SQLException {
        throw new SQLException();
    }
    
    public void refreshRow() throws SQLException {
        throw new SQLException();
    }
    
    public void cancelRowUpdates() throws SQLException {
        throw new SQLException();
    }
    
    public void moveToInsertRow() throws SQLException {
        throw new SQLException();
    }
    
    public void moveToCurrentRow() throws SQLException {
        throw new SQLException();
    }
    
    public Statement getStatement() throws SQLException {
        throw new SQLException();
    }
    
    public Object getObject(final int n, final Map map) throws SQLException {
        throw new SQLException();
    }
    
    public Ref getRef(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Blob getBlob(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Clob getClob(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Array getArray(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Object getObject(final String s, final Map map) throws SQLException {
        throw new SQLException();
    }
    
    public Ref getRef(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Blob getBlob(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Clob getClob(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Array getArray(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Date getDate(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Date getDate(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Time getTime(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Time getTime(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Timestamp getTimestamp(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Timestamp getTimestamp(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public URL getURL(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public URL getURL(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void updateArray(final int n, final Array array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateArray(final String s, final Array array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBlob(final int n, final Blob blob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBlob(final String s, final Blob blob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateClob(final int n, final Clob clob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateClob(final String s, final Clob clob) throws SQLException {
        throw new SQLException();
    }
    
    public void updateRef(final int n, final Ref ref) throws SQLException {
        throw new SQLException();
    }
    
    public void updateRef(final String s, final Ref ref) throws SQLException {
        throw new SQLException();
    }
}
