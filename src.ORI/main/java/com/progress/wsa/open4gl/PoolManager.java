// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl;

import java.util.Hashtable;

public class PoolManager extends Hashtable
{
    public Object newAppObject(final String s) throws Exception {
        if (null == s) {
            throw new NullPointerException("LookupWsaObject detected a Null object UUID");
        }
        return null;
    }
    
    public void storeWsaObject(final String key, final Object value) throws Exception {
        if (null == key) {
            throw new NullPointerException("StoreWsaObject detected a Null object UUID");
        }
        if (null == value) {
            throw new NullPointerException("StoreWsaObject detected a Null WSA object");
        }
        this.put(key, value);
    }
    
    public Object lookupWsaObject(final String key) throws Exception {
        if (null == key) {
            throw new NullPointerException("LookupWsaObject detected a Null object UUID");
        }
        return this.get(key);
    }
    
    public Object createSubObject(final String s, final String s2) throws UnsupportedOperationException {
        if (null == s || null == s2) {
            throw new NullPointerException("CreateSubObject detected a Null object UUID");
        }
        throw new UnsupportedOperationException("Creating sub objects is not yet supported.");
    }
    
    public void releaseWsaObject(final String str) throws NullPointerException {
        if (null == str) {
            throw new NullPointerException("Release detected a Null object UUID");
        }
        try {
            final Object lookupWsaObject = this.lookupWsaObject(str);
            if (null != lookupWsaObject) {
                this.remove(lookupWsaObject);
            }
        }
        catch (Exception ex) {
            System.err.println("Unable to locate for release wsaObjectUUID: " + str);
        }
    }
}
