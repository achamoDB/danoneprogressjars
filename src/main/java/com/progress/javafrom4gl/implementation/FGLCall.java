// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.dynamicapi.ResultSet;
import com.progress.open4gl.dynamicapi.StreamReader;
import com.progress.open4gl.dynamicapi.RequestMetaData;
import com.progress.open4gl.dynamicapi.Util;
import com.progress.open4gl.RunTimeProperties;
import com.progress.javafrom4gl.Log;
import java.io.InputStream;

class FGLCall
{
    private String qualifiedMehtodName;
    private int flagsUnused;
    private int persistencyUnused;
    private int procIdUnused;
    private int numParam;
    private int numSchemas;
    private int fglDebugUnused;
    private int clntVer;
    private String methodName;
    private String interfaceName;
    private boolean getInitialObject;
    private boolean releaseObject;
    private static final int RNC_FROM_OPEN4GL = 8;
    private static final int RNC_FROM_OPEN4GL10 = 32;
    private static final int RNC_FROM_4GL10 = 64;
    
    FGLCall(final InputStream inputStream, final Log log) throws Exception {
        final boolean b = false;
        this.releaseObject = b;
        this.getInitialObject = b;
        final int read = inputStream.read();
        final int streamProtocolVersion = RunTimeProperties.getStreamProtocolVersion();
        if (read != streamProtocolVersion) {
            throw new Exception(Util.getMessageText(7017734119350084680L, String.valueOf(read), String.valueOf(streamProtocolVersion)));
        }
        final byte b2 = (byte)inputStream.read();
        final byte b3 = (byte)inputStream.read();
        if (inputStream.read() != 1) {
            throw new Error("FGLCall: Protocol error 0");
        }
        inputStream.read();
        inputStream.read();
        final ResultSet set = new ResultSet(RequestMetaData.metaData, new StreamReader(inputStream, 9));
        if (!set.next()) {
            throw new Error("FGLCall: Protocol error 1");
        }
        this.qualifiedMehtodName = set.getString(1);
        this.flagsUnused = set.getInt(2);
        this.persistencyUnused = set.getInt(3);
        this.procIdUnused = set.getInt(4);
        this.numParam = set.getInt(5);
        this.numSchemas = set.getInt(6);
        this.fglDebugUnused = set.getInt(7);
        if ((this.flagsUnused & 0x60) != 0x0) {
            this.clntVer = 10;
        }
        else {
            this.clntVer = 9;
        }
        if (this.persistencyUnused != 3) {
            throw new Exception("Persistent 4GL calls are not supported.");
        }
        if (set.next()) {
            throw new Error("FGLCall: Protocol error 2");
        }
        final int lastIndex = this.qualifiedMehtodName.lastIndexOf(46);
        if (lastIndex == -1) {
            this.methodName = this.qualifiedMehtodName;
            this.interfaceName = null;
        }
        else {
            this.methodName = this.qualifiedMehtodName.substring(lastIndex + 1);
            this.interfaceName = this.qualifiedMehtodName.substring(0, lastIndex);
        }
        if (this.methodName == null || this.methodName.length() <= 0) {
            throw new Exception("Illegal method name: " + this.qualifiedMehtodName);
        }
        if (this.interfaceName == null) {
            final String upperCase = this.methodName.toUpperCase();
            if (upperCase.equals("_getInitialObject")) {
                this.getInitialObject = true;
            }
            if (upperCase.equals("_releaseObject")) {
                this.releaseObject = true;
            }
        }
    }
    
    String getMethodName() {
        return this.methodName;
    }
    
    String getInterfaceName() {
        return this.interfaceName;
    }
    
    int getNumParams() {
        return this.numParam;
    }
    
    int getNumSchemas() {
        return this.numSchemas;
    }
    
    boolean isSpecialRelease() {
        return this.releaseObject;
    }
    
    boolean isSpecialGetInit() {
        return this.getInitialObject;
    }
    
    int getClientVersion() {
        return this.clntVer;
    }
}
