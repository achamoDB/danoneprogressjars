// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

class ProcListMetaData
{
    static ResultSetMetaData metaData;
    
    private ProcListMetaData() {
    }
    
    static {
        (ProcListMetaData.metaData = new ResultSetMetaData(0, 1)).setFieldDesc(1, "procId", 1, 4);
    }
}
