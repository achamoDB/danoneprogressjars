// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

class ErrorMetaData
{
    static ResultSetMetaData metaData;
    
    private ErrorMetaData() {
    }
    
    static {
        (ErrorMetaData.metaData = new ResultSetMetaData(0, 6)).setFieldDesc(1, "condition", 1, 4);
        ErrorMetaData.metaData.setFieldDesc(2, "errorNum", 1, 4);
        ErrorMetaData.metaData.setFieldDesc(3, "flags", 1, 4);
        ErrorMetaData.metaData.setFieldDesc(4, "unused", 1, 4);
        ErrorMetaData.metaData.setFieldDesc(5, "errorString", 1, 1);
        ErrorMetaData.metaData.setFieldDesc(6, "returnString", 1, 1);
    }
}
