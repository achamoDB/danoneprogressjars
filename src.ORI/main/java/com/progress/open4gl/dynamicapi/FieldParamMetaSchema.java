// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

public class FieldParamMetaSchema
{
    public static ResultSetMetaData metaData;
    public static ResultSetMetaData metaDataV2;
    public static ResultSetMetaData metaDataV3;
    
    private FieldParamMetaSchema() {
    }
    
    static {
        (FieldParamMetaSchema.metaData = new ResultSetMetaData(0, 3)).setFieldDesc(1, "dtype", 1, 4);
        FieldParamMetaSchema.metaData.setFieldDesc(2, "extent", 1, 4);
        FieldParamMetaSchema.metaData.setFieldDesc(3, "name", 1, 1);
        (FieldParamMetaSchema.metaDataV2 = new ResultSetMetaData(0, 11)).setFieldDesc(1, "dtype", 1, 4);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(2, "extent", 1, 4);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(3, "name", 1, 1);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(4, "position", 1, 4);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(5, "userOrder", 1, 4);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(6, "flag", 1, 4);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(7, "initialValue", 1, 1);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(8, "caption", 1, 1);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(9, "XMLMapping", 1, 4);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(10, "extendedProps", 1, 1);
        FieldParamMetaSchema.metaDataV2.setFieldDesc(11, "helpStr", 1, 1);
        (FieldParamMetaSchema.metaDataV3 = new ResultSetMetaData(0, 14)).setFieldDesc(1, "dtype", 1, 4);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(2, "extent", 1, 4);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(3, "name", 1, 1);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(4, "position", 1, 4);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(5, "userOrder", 1, 4);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(6, "flag", 1, 4);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(7, "initialValue", 1, 1);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(8, "caption", 1, 1);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(9, "XMLMapping", 1, 4);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(10, "extendedProps", 1, 1);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(11, "helpStr", 1, 1);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(12, "pFormat", 1, 1);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(13, "FieldLabel", 1, 1);
        FieldParamMetaSchema.metaDataV3.setFieldDesc(14, "ValidateString", 1, 1);
    }
}
