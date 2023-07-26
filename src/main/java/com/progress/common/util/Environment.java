// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class Environment
{
    public native void setEnvironmentValue(final String p0);
    
    public native String getEnvironmentValue(final String p0);
    
    public native String expandPropertyValue(final String p0);
    
    public native int query_PID(final int p0, final boolean p1);
    
    public native int getCurrent_PID(final int p0);
    
    public void setEnvironmentValueJNI(final String environmentValue) {
        this.setEnvironmentValue(environmentValue);
    }
    
    public String getEnvironmentValueJNI(final String s) {
        return this.getEnvironmentValue(s);
    }
    
    public String expandPropertyValueJNI(final String s) {
        return this.expandPropertyValue(s);
    }
    
    public int query_PID_JNI(final int n, final boolean b) {
        return this.query_PID(n, b);
    }
    
    public int getCurrent_PID_JNI(final int n) {
        return this.getCurrent_PID(n);
    }
    
    static {
        System.load(new InstallPath().fullyQualifyFile("environ.dll"));
    }
}
