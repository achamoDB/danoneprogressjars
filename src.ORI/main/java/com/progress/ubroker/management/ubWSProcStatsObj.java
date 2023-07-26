// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

public class ubWSProcStatsObj
{
    private String m_procName;
    private long m_procTime;
    private int m_calls;
    
    private ubWSProcStatsObj() {
        this.m_procName = "";
        this.m_procTime = 0L;
        this.m_calls = 0;
    }
    
    public ubWSProcStatsObj(final String procName, final long procTime) {
        this.m_procName = "";
        this.m_procTime = 0L;
        this.m_calls = 0;
        this.m_procName = procName;
        this.m_procTime = procTime;
    }
    
    public String getProcName() {
        return this.m_procName;
    }
    
    public long getTime() {
        return this.m_procTime;
    }
    
    public void incrTime(final long n) {
        this.m_procTime += n;
    }
    
    public int getNumCalls() {
        return this.m_calls;
    }
    
    public void incrCalls() {
        ++this.m_calls;
    }
}
