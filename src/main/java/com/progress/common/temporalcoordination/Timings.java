// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.temporalcoordination;

public class Timings
{
    static final int defaultRegistryTimeoutConst = 300000;
    static final int defaultPingTimeoutConst = 300000;
    static final int defaultRetryWaitConst = 5000;
    public static int defaultRegistryTimeout;
    public static int defaultPingTimeout;
    public static int defaultRetryWait;
    
    public void setRegistryTimeout(final int defaultRegistryTimeout) {
        Timings.defaultRegistryTimeout = defaultRegistryTimeout;
    }
    
    public static int getRegistryTimeout() {
        if (Timings.defaultRegistryTimeout > 0) {
            return Timings.defaultRegistryTimeout;
        }
        final String property = System.getProperty("RegistryTimeout");
        try {
            Timings.defaultRegistryTimeout = Integer.parseInt(property);
        }
        catch (Throwable t) {
            Timings.defaultRegistryTimeout = 300000;
        }
        return Timings.defaultRegistryTimeout;
    }
    
    public void setPingTimeout(final int defaultPingTimeout) {
        Timings.defaultPingTimeout = defaultPingTimeout;
    }
    
    public static int getPingTimeout() {
        if (Timings.defaultPingTimeout > 0) {
            return Timings.defaultPingTimeout;
        }
        final String property = System.getProperty("PingTimeout");
        try {
            Timings.defaultPingTimeout = Integer.parseInt(property);
        }
        catch (Throwable t) {
            Timings.defaultPingTimeout = 300000;
        }
        return Timings.defaultPingTimeout;
    }
    
    public void setRetryWait(final int defaultRetryWait) {
        Timings.defaultRetryWait = defaultRetryWait;
    }
    
    public static int getRetryWait() {
        if (Timings.defaultRetryWait > 0) {
            return Timings.defaultRetryWait;
        }
        final String property = System.getProperty("RetryWait");
        try {
            Timings.defaultRetryWait = Integer.parseInt(property);
        }
        catch (Throwable t) {
            Timings.defaultRetryWait = 5000;
        }
        return Timings.defaultRetryWait;
    }
    
    static {
        Timings.defaultRegistryTimeout = -1;
        Timings.defaultPingTimeout = -1;
        Timings.defaultRetryWait = -1;
    }
}
