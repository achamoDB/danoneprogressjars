// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

public class ObjectReference
{
    private String jvmId;
    private long objNum;
    private long refNum;
    private String stringRef;
    private String localStringRef;
    private byte[] byteRef;
    
    ObjectReference(final String jvmId, final long objNum, final long refNum) {
        this.jvmId = jvmId;
        this.objNum = objNum;
        this.refNum = refNum;
        this.getString();
        this.getBytes();
    }
    
    static ObjectReference bytesToReference(final byte[] array) {
        return null;
    }
    
    String getStringRef() {
        return this.stringRef;
    }
    
    String getLocalStringRef() {
        return this.localStringRef;
    }
    
    byte[] getBytesRef() {
        return this.byteRef;
    }
    
    long getObjNum() {
        return this.objNum;
    }
    
    long getrefNum() {
        return this.refNum;
    }
    
    private void getString() {
        this.stringRef = null;
        this.localStringRef = "rrr";
    }
    
    private void getBytes() {
        this.byteRef = null;
    }
}
