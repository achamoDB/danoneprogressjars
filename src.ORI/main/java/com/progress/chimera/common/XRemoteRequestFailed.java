// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import java.rmi.RemoteException;

public class XRemoteRequestFailed extends RemoteException
{
    Throwable exception;
    String bundle;
    String bundleKey;
    String excpFileName;
    int excpNumber;
    
    public XRemoteRequestFailed(final String bundle, final String bundleKey) {
        this.exception = null;
        this.bundle = null;
        this.bundleKey = null;
        this.excpFileName = null;
        this.excpNumber = 0;
        this.bundle = bundle;
        this.bundleKey = bundleKey;
    }
    
    public XRemoteRequestFailed(final Throwable exception) {
        this.exception = null;
        this.bundle = null;
        this.bundleKey = null;
        this.excpFileName = null;
        this.excpNumber = 0;
        this.exception = exception;
    }
    
    public XRemoteRequestFailed(final Throwable exception, final int excpNumber) {
        this.exception = null;
        this.bundle = null;
        this.bundleKey = null;
        this.excpFileName = null;
        this.excpNumber = 0;
        this.exception = exception;
        this.excpNumber = excpNumber;
    }
    
    public XRemoteRequestFailed(final Throwable exception, final int excpNumber, final String original) {
        this.exception = null;
        this.bundle = null;
        this.bundleKey = null;
        this.excpFileName = null;
        this.excpNumber = 0;
        this.exception = exception;
        this.excpNumber = excpNumber;
        this.excpFileName = new String(original);
    }
    
    public int getExcpNumber() {
        return this.excpNumber;
    }
}
