// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

public class ArrayParameterMetaData
{
    private ResultSetMetaData metaData;
    
    public ArrayParameterMetaData(final int n) {
        (this.metaData = new ResultSetMetaData(0, 2 + n)).setFieldDesc(1, "proType", 1, 4);
        this.metaData.setFieldDesc(2, "extentValue", 1, 4);
        for (int i = 1; i <= n; ++i) {
            this.metaData.setFieldDesc(i + 2, "value" + new Integer(i).toString(), 1, 0);
        }
    }
    
    ResultSetMetaData getMetaData() {
        return this.metaData;
    }
}
