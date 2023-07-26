// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

public class ubASProcStatsObj
{
    private String m_procName;
    private String m_procParent;
    private String m_procType;
    private int m_procSuccess;
    private int m_procError;
    private int m_procStop;
    private int m_procQuit;
    private int m_procDebug;
    private int m_procCalls;
    private long m_procTime;
    
    private ubASProcStatsObj() {
        this.m_procName = "";
        this.m_procParent = "";
        this.m_procType = "";
        this.m_procSuccess = 0;
        this.m_procError = 0;
        this.m_procStop = 0;
        this.m_procQuit = 0;
        this.m_procDebug = 0;
        this.m_procCalls = 0;
        this.m_procTime = 0L;
    }
    
    public ubASProcStatsObj(final String procName, final String procParent, final String procType, final int procSuccess, final int procError, final int procStop, final int procQuit, final int procDebug, final long procTime, final int procCalls) {
        this.m_procName = "";
        this.m_procParent = "";
        this.m_procType = "";
        this.m_procSuccess = 0;
        this.m_procError = 0;
        this.m_procStop = 0;
        this.m_procQuit = 0;
        this.m_procDebug = 0;
        this.m_procCalls = 0;
        this.m_procTime = 0L;
        this.m_procName = procName;
        this.m_procParent = procParent;
        this.m_procType = procType;
        this.m_procSuccess = procSuccess;
        this.m_procError = procError;
        this.m_procStop = procStop;
        this.m_procQuit = procQuit;
        this.m_procDebug = procDebug;
        this.m_procTime = procTime;
        this.m_procCalls = procCalls;
    }
    
    public String getProcName() {
        return this.m_procName;
    }
    
    public String getProcParent() {
        return this.m_procParent;
    }
    
    public String getProcType() {
        return this.m_procType;
    }
    
    public int getNumSuccess() {
        return this.m_procSuccess;
    }
    
    public void incrNumSuccess(final int n) {
        this.m_procSuccess += n;
    }
    
    public int getNumError() {
        return this.m_procError;
    }
    
    public void incrNumError(final int n) {
        this.m_procError += n;
    }
    
    public int getNumQuit() {
        return this.m_procQuit;
    }
    
    public void incrNumQuit(final int n) {
        this.m_procQuit += n;
    }
    
    public int getNumStop() {
        return this.m_procStop;
    }
    
    public void incrNumStop(final int n) {
        this.m_procStop += n;
    }
    
    public int getNumDebug() {
        return this.m_procDebug;
    }
    
    public void incrNumDebug(final int n) {
        this.m_procDebug += n;
    }
    
    public long getTime() {
        return this.m_procTime;
    }
    
    public void incrTime(final long n) {
        this.m_procTime += n;
    }
    
    public int getNumCalls() {
        return this.m_procCalls;
    }
    
    public void incrNumCalls(final int n) {
        this.m_procCalls += n;
    }
}
