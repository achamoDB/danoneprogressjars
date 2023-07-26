// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.util.Locale;
import com.progress.common.message.IProMessage;

public class PromsgsFile implements IProMessage
{
    private static JNIHandle prmHandle;
    private static long hdl;
    
    public PromsgsFile(final String s) throws PromsgsFileIOException {
        this.openFile(s, null);
    }
    
    public PromsgsFile(final Locale locale) throws PromsgsFileIOException {
        this.openFile(null, locale);
    }
    
    public PromsgsFile() throws PromsgsFileIOException {
        this.openFile(null, null);
    }
    
    public JNIHandle getHandle() {
        return PromsgsFile.prmHandle;
    }
    
    public String getMessage(final long n) throws PromsgsFileIOException {
        return this.juPrmLookup(PromsgsFileIOException.class, PromsgsFile.prmHandle.getAddr(), (int)n);
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
    }
    
    public void openFile(final String s, final Locale locale) throws PromsgsFileIOException {
        if (PromsgsFile.hdl == 0L) {
            PromsgsFile.hdl = this.juPrmOpen(PromsgsFileIOException.class, s, this.getProLocale(locale));
        }
        if (PromsgsFile.prmHandle == null) {
            (PromsgsFile.prmHandle = new JNIHandle()).setAddr(PromsgsFile.hdl);
        }
    }
    
    private String getProLocale(final Locale locale) throws PromsgsFileIOException {
        if (locale == null) {
            return null;
        }
        if (locale.equals(Locale.US)) {
            return "ame";
        }
        if (locale.equals(Locale.TRADITIONAL_CHINESE)) {
            return "tch";
        }
        throw new PromsgsFileIOException("unsupported locale");
    }
    
    private native long juPrmOpen(final Class p0, final String p1, final String p2) throws PromsgsFileIOException;
    
    private native String juPrmLookup(final Class p0, final long p1, final int p2) throws PromsgsFileIOException;
    
    private native void juPrmClose(final Class p0, final long p1) throws PromsgsFileIOException;
    
    static {
        PromsgsFile.prmHandle = null;
        PromsgsFile.hdl = 0L;
        final InstallPath installPath = new InstallPath();
        if (System.getProperty("os.name").startsWith("Windows")) {
            System.load(installPath.fullyQualifyFile("jutil.dll"));
        }
        System.load(installPath.fullyQualifyFile("jni_util.dll"));
    }
    
    public static class PromsgsFileIOException extends ProMessageException
    {
        public PromsgsFileIOException() {
            super("");
        }
        
        public PromsgsFileIOException(final String s) {
            super(s);
        }
    }
}
