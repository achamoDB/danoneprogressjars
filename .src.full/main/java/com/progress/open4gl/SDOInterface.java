// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public interface SDOInterface
{
    void serverSendRows(final Integer p0, final String p1, final boolean p2, final int p3, final IntHolder p4, final ResultSetHolder p5) throws Open4GLException;
    
    void serverCommit(final ResultSetHolder p0, final StringHolder p1, final StringHolder p2) throws Open4GLException;
    
    void initializeObject() throws Open4GLException;
    
    boolean setQueryWhere(final String p0) throws Open4GLException;
    
    String getQueryWhere() throws Open4GLException;
    
    boolean setQuerySort(final String p0) throws Open4GLException;
    
    boolean openQuery() throws Open4GLException;
    
    boolean closeQuery() throws Open4GLException;
    
    String columnProps(final String p0, final String p1) throws Open4GLException;
    
    String fetchMessages() throws Open4GLException;
    
    void _release() throws Open4GLException;
    
    String getUpdatableColumns() throws Open4GLException;
    
    boolean addQueryWhere(final String p0, final String p1, final String p2) throws Open4GLException, RunTime4GLException, SystemErrorException;
    
    boolean assignQuerySelection(final String p0, final String p1, final String p2) throws Open4GLException, RunTime4GLException, SystemErrorException;
    
    String getTables() throws Open4GLException, RunTime4GLException, SystemErrorException;
    
    String getObjectVersion() throws Open4GLException, RunTime4GLException, SystemErrorException;
    
    void batchServices(final String p0, final StringHolder p1) throws Open4GLException;
}
