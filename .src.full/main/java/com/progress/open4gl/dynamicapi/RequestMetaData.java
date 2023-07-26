// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

public class RequestMetaData
{
    public static ResultSetMetaData metaData;
    
    private RequestMetaData() {
    }
    
    static {
        (RequestMetaData.metaData = new ResultSetMetaData(0, 7)).setFieldDesc(1, "procedure", 1, 1);
        RequestMetaData.metaData.setFieldDesc(2, "flags", 1, 4);
        RequestMetaData.metaData.setFieldDesc(3, "persistency_mode", 1, 4);
        RequestMetaData.metaData.setFieldDesc(4, "procId", 1, 4);
        RequestMetaData.metaData.setFieldDesc(5, "numParam", 1, 4);
        RequestMetaData.metaData.setFieldDesc(6, "numSchemas", 1, 4);
        RequestMetaData.metaData.setFieldDesc(7, "unused", 1, 4);
    }
}
