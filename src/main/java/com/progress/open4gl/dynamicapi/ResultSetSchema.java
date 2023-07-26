// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProResultSetMetaData;
import com.progress.open4gl.Open4GLException;
import java.sql.SQLException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.ProSQLException;
import java.sql.ResultSet;
import java.util.Vector;

public class ResultSetSchema
{
    protected Vector schemas;
    private int numElements;
    private boolean resultSetsOnly;
    
    public ResultSetSchema() {
        this.schemas = new Vector();
        this.numElements = 0;
        this.resultSetsOnly = true;
    }
    
    public ResultSetSchema(final boolean resultSetsOnly) {
        this.schemas = new Vector();
        this.numElements = 0;
        this.resultSetsOnly = resultSetsOnly;
    }
    
    public void addSchema(final ResultSetMetaData resultSetMetaData, final int n, final int n2) {
        this.schemas.addElement(new ResultSetMetaDataIndicator(resultSetMetaData, n, n2));
        ++this.numElements;
    }
    
    public void addSchemaByParamNum(final ResultSetMetaData resultSetMetaData, final int n, final int n2) {
        int size;
        int n3;
        for (size = this.schemas.size(), n3 = 0; n3 < size && ((ResultSetMetaDataIndicator)this.schemas.elementAt(n3)).getParamNum() <= n; ++n3) {}
        this.schemas.add(n3, new ResultSetMetaDataIndicator(resultSetMetaData, n, n2));
        ++this.numElements;
    }
    
    MetaDataIndicator getSchemaIndicator(final int n) {
        if (this.schemas == null) {
            return null;
        }
        try {
            for (int i = 0; i < this.schemas.size(); ++i) {
                final MetaDataIndicator metaDataIndicator = this.schemas.elementAt(i);
                if (n == metaDataIndicator.getParamNum()) {
                    return metaDataIndicator;
                }
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public ResultSetMetaData getSchema(final int n) {
        return (ResultSetMetaData)this.getSchemaIndicator(n).getMetaData();
    }
    
    public boolean isResultSetSchema() {
        return this.resultSetsOnly;
    }
    
    void addSchema(final MetaDataIndicator obj) {
        this.schemas.addElement(obj);
    }
    
    Vector getSchemas() {
        return this.schemas;
    }
    
    boolean validateParameters(final ParameterSet set) {
        int index = 0;
        for (int i = 1; i <= set.getNumParams(); ++i) {
            if (set.isResultSet(i)) {
                if (index >= this.numElements) {
                    return false;
                }
                if (set.getMode(i) != this.schemas.elementAt(index).getInOut()) {
                    return false;
                }
                ++index;
            }
        }
        return this.numElements <= index;
    }
    
    void print(final ParameterSet set, final Tracer tracer) {
        int index = 0;
        for (int i = 1; i <= set.getNumParams(); ++i) {
            if (set.isResultSet(i)) {
                ((MetaDataIndicator)this.schemas.elementAt(index)).print(tracer);
                ++index;
            }
        }
        tracer.print("");
    }
    
    int getSchemaCount() {
        return this.numElements;
    }
    
    void ensureMetaData(final ParameterSet set) throws Open4GLException, ProSQLException {
        int index = 0;
        for (int i = 1; i <= set.getNumParams(); ++i) {
            if (set.isResultSet(i)) {
                if (set.getProType(i) == 17 && set.isInput(i)) {
                    final MetaDataIndicator metaDataIndicator = this.schemas.elementAt(index);
                    final ResultSet set2 = (ResultSet)set.getValue(i);
                    if (set2 == null) {
                        ++index;
                        continue;
                    }
                    java.sql.ResultSetMetaData metaData;
                    try {
                        metaData = set2.getMetaData();
                    }
                    catch (ProSQLException ex) {
                        throw ex;
                    }
                    catch (SQLException ex2) {
                        RunTimeProperties.tracer.print(ex2, 1);
                        throw new ProSQLException(ex2.toString());
                    }
                    if (metaData == null) {
                        throw new Open4GLException(7665970990714725438L, null);
                    }
                    ResultSetMetaData metaData2;
                    if (metaData instanceof ProResultSetMetaData) {
                        metaData2 = (ResultSetMetaData)metaData;
                    }
                    else {
                        metaData2 = new ResultSetMetaData(metaData);
                    }
                    metaDataIndicator.setMetaData(metaData2);
                }
                ++index;
            }
        }
    }
}
