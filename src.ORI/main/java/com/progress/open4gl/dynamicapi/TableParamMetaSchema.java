// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

public class TableParamMetaSchema
{
    public static ResultSetMetaData metaData;
    public static ResultSetMetaData metaDataV2;
    public static ResultSetMetaData metaDataV3;
    public static ResultSetMetaData metaDataV4;
    public static ResultSetMetaData dgMetaData;
    public static ResultSetMetaData dgRelationData;
    
    private TableParamMetaSchema() {
    }
    
    static {
        (TableParamMetaSchema.metaData = new ResultSetMetaData(0, 4)).setFieldDesc(1, "uniqid", 1, 4);
        TableParamMetaSchema.metaData.setFieldDesc(2, "paramNum", 1, 4);
        TableParamMetaSchema.metaData.setFieldDesc(3, "inout", 1, 4);
        TableParamMetaSchema.metaData.setFieldDesc(4, "numflds", 1, 4);
        (TableParamMetaSchema.metaDataV2 = new ResultSetMetaData(0, 14)).setFieldDesc(1, "uniqid", 1, 4);
        TableParamMetaSchema.metaDataV2.setFieldDesc(2, "paramNum", 1, 4);
        TableParamMetaSchema.metaDataV2.setFieldDesc(3, "inout", 1, 4);
        TableParamMetaSchema.metaDataV2.setFieldDesc(4, "numflds", 1, 4);
        TableParamMetaSchema.metaDataV2.setFieldDesc(5, "codepage", 1, 1);
        TableParamMetaSchema.metaDataV2.setFieldDesc(6, "bufferName", 1, 1);
        TableParamMetaSchema.metaDataV2.setFieldDesc(7, "numIndexes", 1, 4);
        TableParamMetaSchema.metaDataV2.setFieldDesc(8, "flag", 1, 4);
        TableParamMetaSchema.metaDataV2.setFieldDesc(9, "errorString", 1, 1);
        TableParamMetaSchema.metaDataV2.setFieldDesc(10, "originID", 1, 4);
        TableParamMetaSchema.metaDataV2.setFieldDesc(11, "primeIxCols", 1, 1);
        TableParamMetaSchema.metaDataV2.setFieldDesc(12, "sourceStr", 1, 1);
        TableParamMetaSchema.metaDataV2.setFieldDesc(13, "namespace", 1, 1);
        TableParamMetaSchema.metaDataV2.setFieldDesc(14, "prefix", 1, 1);
        (TableParamMetaSchema.metaDataV3 = new ResultSetMetaData(0, 15)).setFieldDesc(1, "uniqid", 1, 4);
        TableParamMetaSchema.metaDataV3.setFieldDesc(2, "paramNum", 1, 4);
        TableParamMetaSchema.metaDataV3.setFieldDesc(3, "inout", 1, 4);
        TableParamMetaSchema.metaDataV3.setFieldDesc(4, "numflds", 1, 4);
        TableParamMetaSchema.metaDataV3.setFieldDesc(5, "codepage", 1, 1);
        TableParamMetaSchema.metaDataV3.setFieldDesc(6, "bufferName", 1, 1);
        TableParamMetaSchema.metaDataV3.setFieldDesc(7, "numIndexes", 1, 4);
        TableParamMetaSchema.metaDataV3.setFieldDesc(8, "flag", 1, 4);
        TableParamMetaSchema.metaDataV3.setFieldDesc(9, "errorString", 1, 1);
        TableParamMetaSchema.metaDataV3.setFieldDesc(10, "originID", 1, 4);
        TableParamMetaSchema.metaDataV3.setFieldDesc(11, "primeIxCols", 1, 1);
        TableParamMetaSchema.metaDataV3.setFieldDesc(12, "sourceStr", 1, 1);
        TableParamMetaSchema.metaDataV3.setFieldDesc(13, "namespace", 1, 1);
        TableParamMetaSchema.metaDataV3.setFieldDesc(14, "prefix", 1, 1);
        TableParamMetaSchema.metaDataV3.setFieldDesc(15, "template", 1, 1);
        (TableParamMetaSchema.metaDataV4 = new ResultSetMetaData(0, 10)).setFieldDesc(1, "uniqid", 1, 4);
        TableParamMetaSchema.metaDataV4.setFieldDesc(2, "paramNum", 1, 4);
        TableParamMetaSchema.metaDataV4.setFieldDesc(3, "inout", 1, 4);
        TableParamMetaSchema.metaDataV4.setFieldDesc(4, "numflds", 1, 4);
        TableParamMetaSchema.metaDataV4.setFieldDesc(5, "codepage", 1, 1);
        TableParamMetaSchema.metaDataV4.setFieldDesc(6, "bufferName", 1, 1);
        TableParamMetaSchema.metaDataV4.setFieldDesc(7, "numIndexes", 1, 4);
        TableParamMetaSchema.metaDataV4.setFieldDesc(8, "flag", 1, 4);
        TableParamMetaSchema.metaDataV4.setFieldDesc(9, "errorString", 1, 1);
        TableParamMetaSchema.metaDataV4.setFieldDesc(10, "originID", 1, 4);
        (TableParamMetaSchema.dgMetaData = new ResultSetMetaData(0, 10)).setFieldDesc(1, "codepage", 1, 1);
        TableParamMetaSchema.dgMetaData.setFieldDesc(2, "dataset_name", 1, 1);
        TableParamMetaSchema.dgMetaData.setFieldDesc(3, "Parmnum", 1, 4);
        TableParamMetaSchema.dgMetaData.setFieldDesc(4, "inout", 1, 4);
        TableParamMetaSchema.dgMetaData.setFieldDesc(5, "num_buf_mems", 1, 4);
        TableParamMetaSchema.dgMetaData.setFieldDesc(6, "numlinks", 1, 4);
        TableParamMetaSchema.dgMetaData.setFieldDesc(7, "flag", 1, 4);
        TableParamMetaSchema.dgMetaData.setFieldDesc(8, "namespace", 1, 1);
        TableParamMetaSchema.dgMetaData.setFieldDesc(9, "prefix", 1, 1);
        TableParamMetaSchema.dgMetaData.setFieldDesc(10, "extendedprops", 1, 1);
        (TableParamMetaSchema.dgRelationData = new ResultSetMetaData(0, 7)).setFieldDesc(1, "child_ix", 1, 4);
        TableParamMetaSchema.dgRelationData.setFieldDesc(2, "parent_ix", 1, 4);
        TableParamMetaSchema.dgRelationData.setFieldDesc(3, "flag", 1, 4);
        TableParamMetaSchema.dgRelationData.setFieldDesc(4, "numpairs", 1, 4);
        TableParamMetaSchema.dgRelationData.setFieldDesc(5, "pairslist", 1, 1);
        TableParamMetaSchema.dgRelationData.setFieldDesc(6, "linkname", 1, 1);
        TableParamMetaSchema.dgRelationData.setFieldDesc(7, "extendedprops", 1, 1);
    }
}
