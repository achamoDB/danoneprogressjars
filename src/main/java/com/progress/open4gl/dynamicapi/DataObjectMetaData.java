// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.SchemaValidationException;
import com.progress.open4gl.ProDataObjectMetaData;
import com.progress.open4gl.ProDataException;

public class DataObjectMetaData extends MetaDataBase
{
    protected ResultSetMetaData m_rsmd;
    protected int m_numFields;
    protected boolean m_noSchemaMarshal;
    private String m_primeUniqueName;
    private String m_codePage;
    protected String m_bufferName;
    protected int m_numIndexes;
    protected int m_flag;
    protected String m_tableErrorString;
    private int m_originID;
    private String m_multiIxCols;
    private String m_sourceStr;
    private String m_XMLNamespace;
    private String m_XMLPrefix;
    
    public DataObjectMetaData(final String bufferName, final int numFields, final boolean b, final int numIndexes, final String multiIxCols, final String xmlNamespace, final String xmlPrefix) {
        super(2);
        this.m_numFields = 0;
        this.m_noSchemaMarshal = false;
        this.m_primeUniqueName = null;
        this.m_codePage = null;
        this.m_bufferName = null;
        this.m_numIndexes = 0;
        this.m_flag = 0;
        this.m_tableErrorString = null;
        this.m_originID = 0;
        this.m_multiIxCols = null;
        this.m_sourceStr = null;
        this.m_XMLNamespace = null;
        this.m_XMLPrefix = null;
        this.m_rsmd = new ResultSetMetaData(0, numFields);
        this.m_numFields = numFields;
        if (b) {
            this.m_flag |= 0x2;
        }
        this.m_numIndexes = numIndexes;
        this.m_multiIxCols = multiIxCols;
        this.m_bufferName = bufferName;
        this.m_XMLNamespace = xmlNamespace;
        this.m_XMLPrefix = xmlPrefix;
    }
    
    protected ResultSetMetaData getResultSetMetaData() {
        return this.m_rsmd;
    }
    
    public void setFieldDesc(final int n, final String s, final int n2, final int n3, final int n4, final int n5) {
        this.m_rsmd.setFieldDesc(n, s, n2, n3, n4, n5);
    }
    
    public void setFieldDesc(final int n, final String s, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.m_rsmd.setFieldDesc(n, s, n2, n3, n4, n5, n6);
    }
    
    protected int getFlag() {
        return this.m_flag;
    }
    
    protected void setFlag(final int flag) {
        this.m_flag = flag;
    }
    
    protected int getColumnCount() {
        return this.m_rsmd.getColumnCount();
    }
    
    protected int getFieldCount() {
        return this.m_rsmd.getFieldCount();
    }
    
    protected int getXMLMapping(final int n) throws ProDataException {
        this.validateFld(n);
        return this.m_rsmd.fields[n].getXMLMapping();
    }
    
    protected int getUserOrder(final int n) throws ProDataException {
        this.validateFld(n);
        return this.m_rsmd.fields[n].getUserOrder();
    }
    
    protected int getProFieldType(final int value) throws ProDataException {
        if (!this.validateFld(value)) {
            throw getProDataException(7665970990714723344L, new Integer(value).toString());
        }
        return this.m_rsmd.fields[value].getType();
    }
    
    protected int getProColumnType(final int n) throws ProDataException {
        this.validateCol(n);
        return this.m_rsmd.fields[this.m_rsmd.columns[n]].getType();
    }
    
    protected int getColumnExtent(final int n) throws ProDataException {
        this.validateCol(n);
        return this.m_rsmd.fields[this.m_rsmd.columns[n]].getExtent();
    }
    
    protected int getFieldExtent(final int value) throws ProDataException {
        if (!this.validateFld(value)) {
            throw getProDataException(7665970990714723344L, new Integer(value).toString());
        }
        return this.m_rsmd.fields[value].getExtent();
    }
    
    protected int getFieldIndex(final String s) throws ProDataException {
        for (int fieldCount = this.m_rsmd.getFieldCount(), i = 0; i < fieldCount; ++i) {
            if (s.compareToIgnoreCase(this.getFieldName(i)) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    protected String getFieldName(final int value) throws ProDataException {
        if (!this.validateFld(value)) {
            throw getProDataException(7665970990714723344L, new Integer(value).toString());
        }
        return this.m_rsmd.fields[value].getName();
    }
    
    protected int getFlag(final int n) throws ProDataException {
        this.validateFld(n);
        return this.m_rsmd.fields[n].getFlag();
    }
    
    protected String getPrimeUniqueName() {
        return this.m_primeUniqueName;
    }
    
    public void setPrimeUniqueName(final String primeUniqueName) {
        this.m_primeUniqueName = primeUniqueName;
    }
    
    protected void setNumIndexes(final int numIndexes) {
        this.m_numIndexes = numIndexes;
    }
    
    protected String getMultiIndexCols() {
        return this.m_multiIxCols;
    }
    
    protected void setMultiIndexCols(final String multiIxCols) {
        this.m_multiIxCols = multiIxCols;
    }
    
    protected int getNumIndexes() {
        return this.m_numIndexes;
    }
    
    private void validateCol(final int n) throws ProDataException {
        if (this.m_rsmd.columns == null) {
            this.m_rsmd.columnToField();
        }
        if (n < 0 || n >= this.m_rsmd.columns.length) {
            throw getProDataException(7665970990714723343L, new Integer(n).toString());
        }
        if (this.m_rsmd.fields[this.m_rsmd.columns[n]] == null) {
            throw getProDataException(7665970990714723343L, new Integer(n).toString());
        }
    }
    
    protected boolean validateFld(final int n) {
        return n >= 0 && n < this.m_rsmd.fields.length && this.m_rsmd.fields[n] != null;
    }
    
    static ProDataException getProDataException(final long n, final String s) {
        return new ProDataException(n, new Object[] { s });
    }
    
    protected static boolean validate(final int i, final ProDataObjectMetaData proDataObjectMetaData, final ProDataObjectMetaData proDataObjectMetaData2) throws SchemaValidationException, ProDataException {
        final Object[] array = { null };
        if (proDataObjectMetaData == proDataObjectMetaData2) {
            return true;
        }
        if (proDataObjectMetaData == null) {
            return false;
        }
        if (proDataObjectMetaData2 == null) {
            return false;
        }
        if (proDataObjectMetaData.getColumnCount() != proDataObjectMetaData2.getColumnCount()) {
            array[0] = "Mismatch column count for parameter number " + i;
            throw new SchemaValidationException(7665970990714723371L, array);
        }
        if (proDataObjectMetaData.getBImageFlag() != proDataObjectMetaData2.getBImageFlag()) {
            array[0] = "Mismatch BEFORE-IMAGE Flag for parameter number " + i;
            throw new SchemaValidationException(7665970990714723371L, array);
        }
        for (int j = 0; j < proDataObjectMetaData.getColumnCount(); ++j) {
            if (proDataObjectMetaData.getProColumnType(j) != proDataObjectMetaData2.getProColumnType(j)) {
                array[0] = "Mismatch type for column number " + j + " in metadata for parameter number " + i;
                throw new SchemaValidationException(7665970990714723371L, array);
            }
        }
        return true;
    }
    
    protected void print(final Tracer tracer) {
        this.m_rsmd.print(tracer);
    }
}
