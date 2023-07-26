// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.dsm;

import com.progress.common.util.InstallPath;
import com.progress.common.util.PrologFile;
import com.progress.common.util.PromsgsFile;
import com.progress.common.util.JNIHandle;

public class PromsgsCallback
{
    private JNIHandle callbackHandle;
    
    public PromsgsCallback() throws PromsgsCallbackException {
        this.callbackHandle = new JNIHandle();
    }
    
    public void setCallback(final DSMAPI.DSMContextInfo dsmContextInfo, final PromsgsFile promsgsFile, final PrologFile prologFile) throws PromsgsCallbackException {
        this.callbackHandle.setAddr(this.initCallback(PromsgsCallbackException.class, dsmContextInfo.getHandle().getAddr(), promsgsFile.getHandle().getAddr(), prologFile.getHandle().getAddr()));
    }
    
    protected void finalize() throws Throwable {
        this.finiCallback(PromsgsCallbackException.class, this.callbackHandle.getAddr());
        super.finalize();
    }
    
    private native long initCallback(final Class p0, final long p1, final long p2, final long p3) throws PromsgsCallbackException;
    
    private native void finiCallback(final Class p0, final long p1) throws PromsgsCallbackException;
    
    static {
        System.load(new InstallPath().fullyQualifyFile("jni_dsm.dll"));
    }
    
    public static class PromsgsCallbackException extends Exception
    {
        public PromsgsCallbackException() {
        }
        
        public PromsgsCallbackException(final String message) {
            super(message);
        }
    }
}
