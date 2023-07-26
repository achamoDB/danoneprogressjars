// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.util.GregorianCalendar;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Timestamp;
import java.sql.Date;
import java.math.BigDecimal;

public interface SDOResultSet extends ProResultSet
{
    void updateRow() throws ProSQLException;
    
    boolean rowDeleted() throws ProSQLException;
    
    int getFetchSize() throws ProSQLException;
    
    void deleteRow() throws ProSQLException;
    
    void insertRow() throws ProSQLException;
    
    void cancelRowUpdates() throws ProSQLException;
    
    void moveToInsertRow() throws ProSQLException;
    
    void moveToCurrentRow() throws ProSQLException;
    
    boolean previous() throws ProSQLException;
    
    boolean relative(final int p0) throws ProSQLException;
    
    boolean absolute(final int p0) throws ProSQLException;
    
    boolean absolute(final String p0) throws ProSQLException;
    
    void beforeFirst() throws ProSQLException;
    
    boolean first() throws ProSQLException;
    
    void afterLast() throws ProSQLException;
    
    boolean last() throws ProSQLException;
    
    void updateObject(final int p0, final Object p1) throws ProSQLException;
    
    BigDecimal getBigDecimal(final int p0) throws ProSQLException;
    
    BigDecimal getBigDecimal(final String p0) throws ProSQLException;
    
    boolean isBeforeFirst() throws ProSQLException;
    
    boolean isAfterLast() throws ProSQLException;
    
    boolean isFirst() throws ProSQLException;
    
    boolean isLast() throws ProSQLException;
    
    int getRow() throws ProSQLException;
    
    boolean rowUpdated() throws ProSQLException;
    
    boolean rowInserted() throws ProSQLException;
    
    void updateNull(final int p0) throws ProSQLException;
    
    void updateBoolean(final int p0, final boolean p1) throws ProSQLException;
    
    void updateInt(final int p0, final int p1) throws ProSQLException;
    
    void updateLong(final int p0, final long p1) throws ProSQLException;
    
    void updateDouble(final int p0, final double p1) throws ProSQLException;
    
    void updateBigDecimal(final int p0, final BigDecimal p1) throws ProSQLException;
    
    void updateString(final int p0, final String p1) throws ProSQLException;
    
    void updateBytes(final int p0, final byte[] p1) throws ProSQLException;
    
    void updateDate(final int p0, final Date p1) throws ProSQLException;
    
    void updateTimestamp(final int p0, final Timestamp p1) throws ProSQLException;
    
    void updateBlob(final int p0, final Blob p1) throws ProSQLException;
    
    void updateClob(final int p0, final Clob p1) throws ProSQLException;
    
    void updateNull(final String p0) throws ProSQLException;
    
    void updateBoolean(final String p0, final boolean p1) throws ProSQLException;
    
    void updateInt(final String p0, final int p1) throws ProSQLException;
    
    void updateLong(final String p0, final long p1) throws ProSQLException;
    
    void updateDouble(final String p0, final double p1) throws ProSQLException;
    
    void updateBigDecimal(final String p0, final BigDecimal p1) throws ProSQLException;
    
    void updateString(final String p0, final String p1) throws ProSQLException;
    
    void updateBytes(final String p0, final byte[] p1) throws ProSQLException;
    
    void updateDate(final String p0, final Date p1) throws ProSQLException;
    
    void updateTimestamp(final String p0, final Timestamp p1) throws ProSQLException;
    
    void updateBlob(final String p0, final Blob p1) throws ProSQLException;
    
    void updateClob(final String p0, final Clob p1) throws ProSQLException;
    
    void updateObject(final String p0, final Object p1) throws ProSQLException;
    
    void refreshRow() throws ProSQLException;
    
    boolean isAttached();
    
    void detachFromAppObj() throws ProSQLException;
    
    void attachToAppObj(final SDOFactory p0) throws ProSQLException, Open4GLException;
    
    boolean inBatch();
    
    void cancelBatch() throws ProSQLException;
    
    void sendBatch() throws ProSQLException;
    
    void sendBatchAndReOpen() throws ProSQLException;
    
    void sendBatchAndReOpen(final String p0) throws ProSQLException;
    
    void startBatch() throws ProSQLException;
    
    String getQuery() throws ProSQLException;
    
    void reOpenQuery() throws ProSQLException;
    
    void reOpenQuery(final String p0) throws ProSQLException;
    
    String getRowIdentity() throws ProSQLException;
    
    GregorianCalendar getGregorianCalendar(final int p0) throws ProSQLException;
    
    GregorianCalendar getGregorianCalendar(final String p0) throws ProSQLException;
    
    void updateGregorianCalendar(final int p0, final GregorianCalendar p1) throws ProSQLException;
    
    void updateGregorianCalendar(final String p0, final GregorianCalendar p1) throws ProSQLException;
    
    SDOInterface getSDOInterface() throws ProSQLException;
    
    void releaseSDOInterface() throws ProSQLException;
}
