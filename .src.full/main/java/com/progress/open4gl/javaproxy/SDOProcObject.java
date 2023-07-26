// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import com.progress.open4gl.IntHolder;
import java.sql.ResultSet;
import com.progress.open4gl.StringHolder;
import com.progress.open4gl.ResultSetHolder;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.dynamicapi.ResultSetSchema;
import com.progress.open4gl.SDOInterface;

public class SDOProcObject extends Procedure implements SDOInterface
{
    private ResultSetSchema serverCommit_MetaSchema;
    private ResultSetSchema serverSendRows_MetaSchema;
    
    public SDOProcObject(final ProObject proObject, final String s) throws Open4GLException, RunTime4GLException, SystemErrorException {
        super(proObject);
        super.m_persistProc = this.runPersistentProcedure(s, new ParameterSet(0));
    }
    
    public void setSchema(final ResultSetMetaData resultSetMetaData, final ResultSetMetaData resultSetMetaData2) {
        (this.serverCommit_MetaSchema = new ResultSetSchema()).addSchema(resultSetMetaData2, 1, 3);
        (this.serverSendRows_MetaSchema = new ResultSetSchema()).addSchema(resultSetMetaData, 6, 2);
    }
    
    public boolean closeQuery() throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(0);
        set.setBooleanFunction();
        super.m_persistProc.runProcedure("closeQuery", set);
        if (set.getFunctionReturnValue() == null) {
            throw new Open4GLException(7665970990714724358L, null);
        }
        return (boolean)set.getFunctionReturnValue();
    }
    
    public String columnProps(final String s, final String s2) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(2);
        set.setStringParameter(1, s, 1);
        set.setStringParameter(2, s2, 1);
        set.setStringFunction();
        super.m_persistProc.runProcedure("columnProps", set);
        return (String)set.getFunctionReturnValue();
    }
    
    public String getObjectVersion() throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.getStringProperty("getObjectVersion");
    }
    
    public String getUpdatableColumns() throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.getStringProperty("getUpdatableColumns");
    }
    
    public String getQueryWhere() throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.getStringProperty("getQueryWhere");
    }
    
    public boolean openQuery() throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(0);
        set.setBooleanFunction();
        super.m_persistProc.runProcedure("openQuery", set);
        if (set.getFunctionReturnValue() == null) {
            throw new Open4GLException(7665970990714724358L, null);
        }
        return (boolean)set.getFunctionReturnValue();
    }
    
    public boolean setQuerySort(final String s) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(1);
        set.setStringParameter(1, s, 1);
        set.setBooleanFunction();
        super.m_persistProc.runProcedure("setQuerySort", set);
        if (set.getFunctionReturnValue() == null) {
            throw new Open4GLException(7665970990714724358L, null);
        }
        return (boolean)set.getFunctionReturnValue();
    }
    
    public boolean setQueryWhere(final String s) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(1);
        set.setStringParameter(1, s, 1);
        set.setBooleanFunction();
        super.m_persistProc.runProcedure("setQueryWhere", set);
        if (set.getFunctionReturnValue() == null) {
            throw new Open4GLException(7665970990714724358L, null);
        }
        return (boolean)set.getFunctionReturnValue();
    }
    
    public boolean addQueryWhere(final String s, final String s2, final String s3) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(3);
        set.setStringParameter(1, s, 1);
        set.setStringParameter(2, s2, 1);
        set.setStringParameter(3, s3, 1);
        set.setBooleanFunction();
        super.m_persistProc.runProcedure("addQueryWhere", set);
        if (set.getFunctionReturnValue() == null) {
            throw new Open4GLException(7665970990714724358L, null);
        }
        return (boolean)set.getFunctionReturnValue();
    }
    
    public boolean assignQuerySelection(final String s, final String s2, final String s3) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(3);
        set.setStringParameter(1, s, 1);
        set.setStringParameter(2, s2, 1);
        set.setStringParameter(3, s3, 1);
        set.setBooleanFunction();
        super.m_persistProc.runProcedure("assignQuerySelection", set);
        if (set.getFunctionReturnValue() == null) {
            throw new Open4GLException(7665970990714724358L, null);
        }
        return (boolean)set.getFunctionReturnValue();
    }
    
    public String getTables() throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.getStringProperty("getTables");
    }
    
    public String fetchMessages() throws Open4GLException, RunTime4GLException, SystemErrorException {
        return this.getStringProperty("fetchMessages");
    }
    
    public void initializeObject() throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        super.m_persistProc.runProcedure("initializeObject", new ParameterSet(0));
    }
    
    public void serverCommit(final ResultSetHolder resultSetHolder, final StringHolder stringHolder, final StringHolder stringHolder2) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(3);
        set.setResultSetParameter(1, (ResultSet)resultSetHolder.getValue(), 3);
        set.setStringParameter(2, null, 2);
        set.setStringParameter(3, null, 2);
        super.m_persistProc.runProcedure("serverCommit", set, this.serverCommit_MetaSchema);
        resultSetHolder.setValue(set.getOutputParameter(1));
        stringHolder.setValue(set.getOutputParameter(2));
        stringHolder2.setValue(set.getOutputParameter(3));
    }
    
    public void serverSendRows(final Integer n, final String s, final boolean b, final int n2, final IntHolder intHolder, final ResultSetHolder resultSetHolder) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(6);
        set.setIntegerParameter(1, n, 1);
        set.setStringParameter(2, s, 1);
        set.setBooleanParameter(3, b, 1);
        set.setIntegerParameter(4, n2, 1);
        set.setIntegerParameter(5, null, 2);
        set.setResultSetParameter(6, null, 2);
        super.m_persistProc.runProcedure("serverSendRows", set, this.serverSendRows_MetaSchema);
        intHolder.setValue(set.getOutputParameter(5));
        resultSetHolder.setValue(set.getOutputParameter(6));
    }
    
    private String getStringProperty(final String s) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(0);
        set.setStringFunction();
        super.m_persistProc.runProcedure(s, set);
        return (String)set.getFunctionReturnValue();
    }
    
    public void batchServices(final String s, final StringHolder stringHolder) throws Open4GLException {
        if (this.getSessionPool() == null) {
            throw new Open4GLException(7665970990714723420L, null);
        }
        final ParameterSet set = new ParameterSet(2);
        set.setStringParameter(1, s, 1);
        set.setStringParameter(2, null, 2);
        super.m_persistProc.runProcedure("batchServices", set);
        stringHolder.setValue(set.getOutputParameter(2));
    }
}
