// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

public class PrologFile
{
    private JNIHandle logHandle;
    
    public PrologFile(final String s, final boolean b) throws PrologFileIOException {
        this.openFile(s, b);
    }
    
    public PrologFile(final String s) throws PrologFileIOException {
        this.openFile(s, false);
    }
    
    public JNIHandle getHandle() {
        return this.logHandle;
    }
    
    public void write(final String s) throws PrologFileIOException {
        this.writeFile(null, s, false);
    }
    
    public void write(final String s, final String s2) throws PrologFileIOException {
        this.writeFile(s, s2, false);
    }
    
    public void writeDate() throws PrologFileIOException {
        this.writeFile(null, null, true);
    }
    
    protected void finalize() throws Throwable {
        this.juLogClose(PrologFileIOException.class, this.logHandle.getAddr());
        super.finalize();
    }
    
    public void openFile(final String s, final boolean b) throws PrologFileIOException {
        (this.logHandle = new JNIHandle()).setAddr(this.juLogOpen(PrologFileIOException.class, s, b));
    }
    
    public void writeFile(final String s, final String s2, final boolean b) throws PrologFileIOException {
        this.juLogWrite(PrologFileIOException.class, this.logHandle.getAddr(), s, s2, b);
    }
    
    private native long juLogOpen(final Class p0, final String p1, final boolean p2) throws PrologFileIOException;
    
    private native void juLogWrite(final Class p0, final long p1, final String p2, final String p3, final boolean p4) throws PrologFileIOException;
    
    private native void juLogClose(final Class p0, final long p1) throws PrologFileIOException;
    
    static {
        System.load(new InstallPath().fullyQualifyFile("jni_util.dll"));
    }
    
    public static class PrologFileIOException extends Exception
    {
        public PrologFileIOException() {
        }
        
        public PrologFileIOException(final String message) {
            super(message);
        }
    }
}
