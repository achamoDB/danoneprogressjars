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

public class TableResultSet implements ResultSet
{
    private boolean endList;
    private Integer paramNum;
    private Integer inout;
    private Integer numflds;
    private String m_codePage;
    private String m_bufferName;
    private Integer m_numIndexes;
    private Integer m_flag;
    private String m_errorString;
    private Integer m_originID;
    private String m_prime_ix_cols;
    private String m_sourceStr;
    private String m_namespaceStr;
    private String m_prefixStr;
    
    TableResultSet(final int n, final int n2, final int n3) {
        this(n, n2, n3, null, "OPENCLIENT_TT", 0, 0, null, 0, null, null, null, null);
    }
    
    TableResultSet(final int n, final int n2, final int n3, final String s, final String s2, final int n4, final int n5, final String s3, final int n6) {
        this(n, n2, n3, s, s2, n4, n5, s3, n6, null, null, null, null);
    }
    
    TableResultSet(final int value, final int value2, final int value3, final String codePage, final String bufferName, final int value4, final int value5, final String errorString, final int value6, final String prime_ix_cols, final String sourceStr, final String namespaceStr, final String prefixStr) {
        this.endList = false;
        this.paramNum = new Integer(value);
        this.inout = new Integer(value2);
        this.numflds = new Integer(value3);
        this.m_codePage = codePage;
        if (bufferName != null) {
            this.m_bufferName = bufferName;
        }
        else {
            this.m_bufferName = new String("OPENCLIENT_TT");
        }
        this.m_numIndexes = new Integer(value4);
        this.m_flag = new Integer(value5);
        this.m_errorString = errorString;
        this.m_originID = new Integer(value6);
        this.m_prime_ix_cols = prime_ix_cols;
        this.m_sourceStr = sourceStr;
        this.m_namespaceStr = namespaceStr;
        this.m_prefixStr = prefixStr;
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
                return new Integer(0);
            }
            case 2: {
                return this.paramNum;
            }
            case 3: {
                return this.inout;
            }
            case 4: {
                return this.numflds;
            }
            case 5: {
                return this.m_codePage;
            }
            case 6: {
                return this.m_bufferName;
            }
            case 7: {
                return this.m_numIndexes;
            }
            case 8: {
                return this.m_flag;
            }
            case 9: {
                return this.m_errorString;
            }
            case 10: {
                return this.m_originID;
            }
            case 11: {
                return this.m_prime_ix_cols;
            }
            case 12: {
                return this.m_sourceStr;
            }
            case 13: {
                return this.m_namespaceStr;
            }
            case 14: {
                return this.m_prefixStr;
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
