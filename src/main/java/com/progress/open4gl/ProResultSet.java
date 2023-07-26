// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.util.GregorianCalendar;
import java.sql.Timestamp;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.ResultSet;

public interface ProResultSet extends ResultSet
{
    byte[] getBytes(final int p0, final int p1) throws ProSQLException;
    
    String getString(final int p0, final int p1) throws ProSQLException;
    
    long getLong(final int p0, final int p1) throws ProSQLException;
    
    int getInt(final int p0, final int p1) throws ProSQLException;
    
    double getDouble(final int p0, final int p1) throws ProSQLException;
    
    BigDecimal getBigDecimal(final int p0, final int p1, final int p2) throws ProSQLException;
    
    boolean getBoolean(final int p0, final int p1) throws ProSQLException;
    
    Date getDate(final int p0, final int p1) throws ProSQLException;
    
    Blob getBlob(final int p0, final int p1) throws ProSQLException;
    
    Clob getClob(final int p0, final int p1) throws ProSQLException;
    
    Timestamp getTimestamp(final int p0, final int p1) throws ProSQLException;
    
    GregorianCalendar getGregorianCalendar(final int p0, final int p1) throws ProSQLException;
    
    Object getObject(final int p0, final int p1) throws ProSQLException;
    
    byte[] getBytes(final String p0, final int p1) throws ProSQLException;
    
    String getString(final String p0, final int p1) throws ProSQLException;
    
    long getLong(final String p0, final int p1) throws ProSQLException;
    
    int getInt(final String p0, final int p1) throws ProSQLException;
    
    double getDouble(final String p0, final int p1) throws ProSQLException;
    
    BigDecimal getBigDecimal(final String p0, final int p1, final int p2) throws ProSQLException;
    
    boolean getBoolean(final String p0, final int p1) throws ProSQLException;
    
    Date getDate(final String p0, final int p1) throws ProSQLException;
    
    Blob getBlob(final String p0, final int p1) throws ProSQLException;
    
    Clob getClob(final String p0, final int p1) throws ProSQLException;
    
    Timestamp getTimestamp(final String p0, final int p1) throws ProSQLException;
    
    GregorianCalendar getGregorianCalendar(final String p0, final int p1) throws ProSQLException;
    
    Object getObject(final String p0, final int p1) throws ProSQLException;
    
    GregorianCalendar getGregorianCalendar(final int p0) throws ProSQLException;
    
    GregorianCalendar getGregorianCalendar(final String p0) throws ProSQLException;
}
