// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.util.Date;
import java.util.Map;
import java.util.Hashtable;

public class RuntimeStats
{
    public static final String[] DEFAULT_COUNTER_NAMES;
    private long m_startup;
    private long m_startRecording;
    private int[] m_counters;
    private String[] m_counterNames;
    private int m_maxIndex;
    private String m_xmlLableName;
    private RuntimeStats m_externalStats;
    
    public RuntimeStats() {
        this.m_startup = System.currentTimeMillis();
        this.m_startRecording = this.m_startup;
        this.m_counters = null;
        this.m_counterNames = null;
        this.m_maxIndex = 0;
        this.m_xmlLableName = "runtime-stats";
        this.m_externalStats = null;
        this.m_counterNames = RuntimeStats.DEFAULT_COUNTER_NAMES;
        this.m_maxIndex = this.m_counterNames.length - 1;
        this.m_counters = new int[this.m_counterNames.length];
    }
    
    public RuntimeStats(final String xmlLableName, final String[] counterNames) {
        this.m_startup = System.currentTimeMillis();
        this.m_startRecording = this.m_startup;
        this.m_counters = null;
        this.m_counterNames = null;
        this.m_maxIndex = 0;
        this.m_xmlLableName = "runtime-stats";
        this.m_externalStats = null;
        if (null != xmlLableName && 0 < xmlLableName.length()) {
            this.m_xmlLableName = xmlLableName;
        }
        if (null != counterNames && 0 < counterNames.length) {
            this.m_counterNames = counterNames;
            this.m_maxIndex = this.m_counterNames.length - 1;
            this.m_counters = new int[this.m_counterNames.length];
        }
    }
    
    public long creationTime() {
        return this.m_startup;
    }
    
    public long lastResetTime() {
        return this.m_startRecording;
    }
    
    public int numberOfCounters() {
        return this.m_counters.length;
    }
    
    public int getCounter(final int n) throws IndexOutOfBoundsException {
        return this.m_counters[n];
    }
    
    public void incrementCounter(final int n) throws IndexOutOfBoundsException {
        synchronized (this.m_counters) {
            final int[] counters = this.m_counters;
            ++counters[n];
        }
    }
    
    public void decrementCounter(final int n) throws IndexOutOfBoundsException {
        synchronized (this.m_counters) {
            final int[] counters = this.m_counters;
            --counters[n];
        }
    }
    
    public void reset() {
        synchronized (this.m_counters) {
            for (int i = 0; i < this.m_counters.length; ++i) {
                this.m_counters[i] = 0;
            }
            this.m_startRecording = System.currentTimeMillis();
        }
    }
    
    public void setExternalStatistics(final RuntimeStats externalStats) {
        this.m_externalStats = externalStats;
    }
    
    public Hashtable getStatistics() {
        final Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();
        final int[] array = new int[this.m_counters.length];
        if (null != this.m_externalStats) {
            hashtable.putAll(this.m_externalStats.getStatistics());
        }
        synchronized (this.m_counters) {
            for (int i = 0; i < this.m_counters.length; ++i) {
                array[i] = this.m_counters[i];
            }
        }
        hashtable.put("startup", (Integer)new Date(this.m_startup).toString());
        hashtable.put("lastReset", (Integer)new Date(this.m_startRecording).toString());
        for (int j = 0; j < this.m_counters.length; ++j) {
            hashtable.put(this.m_counterNames[j], new Integer(array[j]));
        }
        return hashtable;
    }
    
    static {
        DEFAULT_COUNTER_NAMES = new String[] { "undefined" };
    }
}
