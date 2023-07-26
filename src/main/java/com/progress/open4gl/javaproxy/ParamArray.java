// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import com.progress.open4gl.dynamicapi.DataGraphMetaData;
import com.progress.open4gl.ProDataGraphMetaData;
import com.progress.open4gl.ProDataGraph;
import com.progress.open4gl.ProResultSetMetaDataImpl;
import java.sql.ResultSet;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Handle;
import com.progress.open4gl.Memptr;
import com.progress.open4gl.Rowid;
import java.util.GregorianCalendar;
import java.math.BigDecimal;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.dynamicapi.MetaSchema;
import com.progress.open4gl.dynamicapi.ParameterSet;

public class ParamArray
{
    private ParameterSet m_params;
    private MetaSchema m_metaSchema;
    
    public ParamArray(final int n) {
        this.m_params = null;
        this.m_metaSchema = null;
        this.m_params = new ParameterSet(n);
    }
    
    MetaSchema getMetaSchema() {
        return this.m_metaSchema;
    }
    
    ParameterSet getParameterSet() {
        return this.m_params;
    }
    
    public void clear() {
        this.m_params.cleanUp();
        this.m_metaSchema = null;
    }
    
    public void addLogical(final int n, final boolean value, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, new Boolean(value), n2, 3, false, 0);
    }
    
    public void addLogicalArray(final int n, final boolean[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 3, true, n3);
    }
    
    public void addInteger(final int n, final int value, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, new Integer(value), n2, 4, false, 0);
    }
    
    public void addIntegerArray(final int n, final int[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 4, true, n3);
    }
    
    public void addInt64(final int n, final long value, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, new Long(value), n2, 41, false, 0);
    }
    
    public void addInt64Array(final int n, final long[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 41, true, n3);
    }
    
    public void addRecid(final int n, final long value, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, new Long(value), n2, 7, false, 0);
    }
    
    public void addRecidArray(final int n, final long[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 7, true, n3);
    }
    
    public void addLogical(final int n, final Boolean b, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, b, n2, 3, false, 0);
    }
    
    public void addLogicalArray(final int n, final Boolean[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 3, true, n3);
    }
    
    public void addInteger(final int n, final Integer n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, n2, n3, 4, false, 0);
    }
    
    public void addIntegerArray(final int n, final Integer[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 4, true, n3);
    }
    
    public void addInt64(final int n, final Long n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, n2, n3, 41, false, 0);
    }
    
    public void addInt64Array(final int n, final Long[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 41, true, n3);
    }
    
    public void addRecid(final int n, final Long n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, n2, n3, 7, false, 0);
    }
    
    public void addRecidArray(final int n, final Long[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 7, true, n3);
    }
    
    public void addDecimal(final int n, final BigDecimal bigDecimal, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, bigDecimal, n2, 5, false, 0);
    }
    
    public void addDecimalArray(final int n, final BigDecimal[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 5, true, n3);
    }
    
    public void addCharacter(final int n, final String s, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, s, n2, 1, false, 0);
    }
    
    public void addCharacterArray(final int n, final String[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 1, true, n3);
    }
    
    public void addLongchar(final int n, final String s, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, s, n2, 39, false, 0);
    }
    
    public void addLongcharArray(final int n, final String[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 39, true, n3);
    }
    
    public void addDate(final int n, final GregorianCalendar gregorianCalendar, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, gregorianCalendar, n2, 2, false, 0);
    }
    
    public void addDateArray(final int n, final GregorianCalendar[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 2, true, n3);
    }
    
    public void addDatetime(final int n, final GregorianCalendar gregorianCalendar, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, gregorianCalendar, n2, 34, false, 0);
    }
    
    public void addDatetimeArray(final int n, final GregorianCalendar[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 34, true, n3);
    }
    
    public void addDatetimeTZ(final int n, final GregorianCalendar gregorianCalendar, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, gregorianCalendar, n2, 40, false, 0);
    }
    
    public void addDatetimeTZArray(final int n, final GregorianCalendar[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 40, true, n3);
    }
    
    public void addRaw(final int n, final byte[] array, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 8, false, 0);
    }
    
    public void addRawArray(final int n, final byte[][] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 8, true, n3);
    }
    
    public void addRowid(final int n, final Rowid rowid, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, rowid, n2, 13, false, 0);
    }
    
    public void addRowidArray(final int n, final Rowid[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 13, true, n3);
    }
    
    public void addMemptr(final int n, final Memptr memptr, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, memptr, n2, 11, false, 0);
    }
    
    public void addMemptrArray(final int n, final Memptr[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 11, true, n3);
    }
    
    public void addHandle(final int n, final Handle handle, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, handle, n2, 10, false, 0);
    }
    
    public void addHandleArray(final int n, final Handle[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 10, true, n3);
    }
    
    public void addCOMHandle(final int n, final COMHandle comHandle, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, comHandle, n2, 14, false, 0);
    }
    
    public void addCOMHandleArray(final int n, final COMHandle[] array, final int n2, final int n3) throws Open4GLException {
        this.m_params.setParameter(n + 1, array, n2, 14, true, n3);
    }
    
    public void addTable(final int n, final ResultSet set, final int n2, final ProResultSetMetaDataImpl proResultSetMetaDataImpl) throws Open4GLException {
        this.m_params.setParameter(n + 1, set, n2, 15, false, 0);
        this.addSchema(proResultSetMetaDataImpl, n + 1, n2);
    }
    
    public void addTableHandle(final int n, final ResultSet set, final int n2, final ProResultSetMetaDataImpl proResultSetMetaDataImpl) throws Open4GLException {
        this.m_params.setParameter(n + 1, set, n2, 17, false, 0);
        this.addSchema(proResultSetMetaDataImpl, n + 1, n2);
    }
    
    public void addTable(final int n, final ProDataGraph proDataGraph, final int n2, final ProDataGraphMetaData proDataGraphMetaData) throws Open4GLException {
        this.m_params.setParameter(n + 1, proDataGraph, n2, 36, false, 0);
        this.addSchema(proDataGraphMetaData, n + 1, n2, true);
        this.m_params.setIsMappedTable(n + 1, true);
        this.m_params.setParamNum(proDataGraphMetaData, n + 1);
        this.m_params.setInOut(proDataGraphMetaData, n2);
    }
    
    public void addTableHandle(final int n, final ProDataGraph proDataGraph, final int n2, ProDataGraphMetaData proDataGraphMetaData) throws Open4GLException {
        if (n2 == 2 && proDataGraphMetaData == null) {
            proDataGraphMetaData = new ProDataGraphMetaData(0);
        }
        this.m_params.setParameter(n + 1, proDataGraph, n2, 37, false, 0);
        this.addSchema(proDataGraphMetaData, n + 1, n2, true);
        this.m_params.setIsMappedTable(n + 1, true);
        this.m_params.setParamNum(proDataGraphMetaData, n + 1);
        this.m_params.setInOut(proDataGraphMetaData, n2);
    }
    
    public void addDataset(final int n, final ProDataGraph proDataGraph, final int n2, final ProDataGraphMetaData proDataGraphMetaData) throws Open4GLException {
        this.m_params.setParameter(n + 1, proDataGraph, n2, 36, false, 0);
        this.addSchema(proDataGraphMetaData, n + 1, n2, false);
        this.m_params.setParamNum(proDataGraphMetaData, n + 1);
        this.m_params.setInOut(proDataGraphMetaData, n2);
    }
    
    public void addDataset(final int n, final String s, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, s, n2, 36, false, 0, true, false, false);
    }
    
    public void addDatasetHandle(final int n, final ProDataGraph proDataGraph, final int n2, ProDataGraphMetaData proDataGraphMetaData) throws Open4GLException {
        if (n2 == 2 && proDataGraphMetaData == null) {
            proDataGraphMetaData = new ProDataGraphMetaData(0);
        }
        this.m_params.setParameter(n + 1, proDataGraph, n2, 37, false, 0);
        this.addSchema(proDataGraphMetaData, n + 1, n2, false);
        this.m_params.setParamNum(proDataGraphMetaData, n + 1);
        this.m_params.setInOut(proDataGraphMetaData, n2);
    }
    
    public void addDatasetHandle(final int n, final String s, final int n2) throws Open4GLException {
        this.m_params.setParameter(n + 1, s, n2, 37, false, 0, true, false, false);
    }
    
    public Object getOutputParameter(final int n) throws Open4GLException {
        return this.m_params.getOutputParameter(n + 1);
    }
    
    public void addParameter(final int n, final Object o, final int n2, final int n3, final int n4, final ProResultSetMetaDataImpl proResultSetMetaDataImpl) throws Open4GLException {
        boolean b = false;
        if (n4 > 1) {
            b = true;
        }
        this.m_params.setParameter(n + 1, o, n2, n3, b, n4);
        this.addSchema(proResultSetMetaDataImpl, n + 1, n2);
    }
    
    public void addParameter(final int n, final Object o, final int n2, final int n3, final int n4, final ProDataGraphMetaData proDataGraphMetaData) throws Open4GLException {
        boolean b = false;
        if (n4 > 1) {
            b = true;
        }
        if (n3 == 17) {
            this.addTableHandle(n, (ProDataGraph)o, n2, proDataGraphMetaData);
        }
        else if (n3 == 15) {
            this.addTable(n, (ProDataGraph)o, n2, proDataGraphMetaData);
        }
        else if (n3 == 37) {
            this.addDatasetHandle(n, (ProDataGraph)o, n2, proDataGraphMetaData);
        }
        else if (n3 == 36) {
            this.addDatasetHandle(n, (ProDataGraph)o, n2, proDataGraphMetaData);
        }
        else {
            this.m_params.setParameter(n + 1, o, n2, n3, b, n4);
        }
    }
    
    public void setReturnType(final int n) throws Open4GLException {
        switch (n) {
            case 1: {
                this.m_params.setStringFunction();
                break;
            }
            case 2: {
                this.m_params.setDateFunction();
                break;
            }
            case 3: {
                this.m_params.setBooleanFunction();
                break;
            }
            case 4: {
                this.m_params.setIntegerFunction();
                break;
            }
            case 41: {
                this.m_params.setInt64Function();
                break;
            }
            case 5: {
                this.m_params.setDecimalFunction();
                break;
            }
            case 7: {
                this.m_params.setLongFunction();
                break;
            }
            case 8: {
                this.m_params.setByteFunction();
                break;
            }
            case 10: {
                this.m_params.setHandleFunction();
                break;
            }
            case 13: {
                this.m_params.setRowidFunction();
                break;
            }
            case 14: {
                this.m_params.setCOMHandleFunction();
                break;
            }
            case 34: {
                this.m_params.setDateTimeFunction();
                break;
            }
            case 40: {
                this.m_params.setDateTimeTzFunction();
                break;
            }
            default: {
                throw new Open4GLException(7665970990714728633L, null);
            }
        }
    }
    
    public Object getReturnValue() {
        return this.m_params.getFunctionReturnValue();
    }
    
    public void setIsReturnExtent(final boolean isFuncReturnExtent) {
        this.m_params.setIsFuncReturnExtent(isFuncReturnExtent);
    }
    
    public void setIsReturnUnknown(final boolean isReturnUnknown) {
        this.m_params.setIsReturnUnknown(isReturnUnknown);
    }
    
    public String getProcReturnString() {
        return (String)this.m_params.getProcedureReturnValue();
    }
    
    void addSchema(final ProResultSetMetaDataImpl proResultSetMetaDataImpl, final int n, final int n2) {
        if (proResultSetMetaDataImpl != null) {
            if (this.m_metaSchema == null) {
                this.m_metaSchema = new MetaSchema();
            }
            this.m_metaSchema.addResultSetSchema(proResultSetMetaDataImpl, n, n2);
        }
    }
    
    void addSchema(final ProDataGraphMetaData proDataGraphMetaData, final int n, final int n2, final boolean b) {
        if (proDataGraphMetaData != null) {
            if (this.m_metaSchema == null) {
                this.m_metaSchema = new MetaSchema();
            }
            this.m_metaSchema.addProDataGraphSchema(proDataGraphMetaData, n, n2, b);
            if (!b) {
                this.m_metaSchema.setMetaDataIndInfoForTables(proDataGraphMetaData, n, n2);
            }
        }
    }
    
    void validate() throws Open4GLException {
        this.m_params.validate();
    }
}
