// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public interface LogContext
{
    String getLogContextName();
    
    void initEntrytypeNames() throws LogException;
    
    long getEntrytypeBitAt(final int p0);
    
    String getEntrytypeName(final int p0);
    
    String entrytypeNameAt(final int p0) throws LogException;
    
    String getMsgHdr();
    
    void setMsgHdr(final String p0);
    
    String setMsgHdr(final String p0, final Object[] p1);
    
    long getLogEntries();
    
    boolean getLogSubLevelUsed();
    
    byte[] getLogSublevelArray();
    
    String parseEntrytypeString(final String p0, final int p1);
    
    String getErrorStringTag();
    
    void resetLogEntryTypes();
}
