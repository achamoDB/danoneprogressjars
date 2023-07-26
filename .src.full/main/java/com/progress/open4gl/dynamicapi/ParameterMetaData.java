// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

public class ParameterMetaData
{
    public static ResultSetMetaData metaData;
    
    private ParameterMetaData() {
    }
    
    static {
        (ParameterMetaData.metaData = new ResultSetMetaData(0, 6)).setFieldDesc(1, "proType", 1, 4);
        ParameterMetaData.metaData.setFieldDesc(2, "value", 1, 0);
        ParameterMetaData.metaData.setFieldDesc(3, "inOut_mode", 1, 4);
        ParameterMetaData.metaData.setFieldDesc(4, "isExtent", 1, 3);
        ParameterMetaData.metaData.setFieldDesc(5, "extentValue", 1, 4);
        ParameterMetaData.metaData.setFieldDesc(6, "writeXmlBeforeImage", 1, 3);
    }
}
