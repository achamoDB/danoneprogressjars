// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProDataGraph;
import com.progress.open4gl.ProResultSetMetaData;
import com.progress.open4gl.Open4GLException;
import java.sql.SQLException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.ProSQLException;
import java.sql.ResultSet;
import java.util.Vector;
import com.progress.open4gl.ProDataGraphMetaData;

public class MetaSchema extends ResultSetSchema
{
    private int numElements;
    
    public MetaSchema() {
        super(false);
    }
    
    public MetaSchema(final ResultSetSchema resultSetSchema) {
        super(false);
        super.schemas = resultSetSchema.getSchemas();
        this.numElements = resultSetSchema.getSchemaCount();
    }
    
    public void addResultSetSchema(final ResultSetMetaData resultSetMetaData, final int n, final int n2) {
        super.schemas.addElement(new ResultSetMetaDataIndicator(resultSetMetaData, n, n2));
        ++this.numElements;
    }
    
    public void addResultSetSchemaByParamNum(final ResultSetMetaData resultSetMetaData, final int n, final int n2) {
        int size;
        int n3;
        for (size = super.schemas.size(), n3 = 0; n3 < size && ((ResultSetMetaDataIndicator)super.schemas.elementAt(n3)).getParamNum() <= n; ++n3) {}
        super.schemas.add(n3, new ResultSetMetaDataIndicator(resultSetMetaData, n, n2));
        ++this.numElements;
    }
    
    public void addProDataGraphSchema(final ProDataGraphMetaData proDataGraphMetaData, final int n, final int n2, final boolean b) {
        super.schemas.addElement(new ProDataGraphMetaDataIndicator(proDataGraphMetaData, n, n2, b));
        ++this.numElements;
    }
    
    public void addProDataGraphSchemaByParamNum(final ProDataGraphMetaData proDataGraphMetaData, final int n, final int n2, final boolean b) {
        int size;
        int n3;
        for (size = super.schemas.size(), n3 = 0; n3 < size && ((ProDataGraphMetaDataIndicator)super.schemas.elementAt(n3)).getParamNum() <= n; ++n3) {}
        super.schemas.add(n3, new ProDataGraphMetaDataIndicator(proDataGraphMetaData, n, n2, b));
        ++this.numElements;
    }
    
    public void addProDataGraphSchema(final ProDataGraphMetaData proDataGraphMetaData, final int n, final int n2) {
        this.addProDataGraphSchema(proDataGraphMetaData, n, n2, false);
    }
    
    public void setMetaDataIndInfoForTables(final DataGraphMetaData dataGraphMetaData, final int paramNum, final int inOut) {
        for (int i = 0; i < dataGraphMetaData.m_TableCount; ++i) {
            dataGraphMetaData.m_domdi[i].m_paramNum = paramNum;
            dataGraphMetaData.m_domdi[i].m_inOut = inOut;
        }
    }
    
    void addResultSetSchema(final ResultSetMetaDataIndicator obj) {
        super.schemas.addElement(obj);
    }
    
    Vector getSchemas() {
        return super.schemas;
    }
    
    boolean validateParameters(final ParameterSet set) {
        int index = 0;
        for (int i = 1; i <= set.getNumParams(); ++i) {
            if (set.isResultSet(i) || set.isDataGraph(i)) {
                if (index >= this.numElements) {
                    return false;
                }
                if (set.getMode(i) != super.schemas.elementAt(index).getInOut()) {
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
            if (set.isResultSet(i) || set.isDataGraph(i)) {
                ((MetaDataIndicator)super.schemas.elementAt(index)).print(tracer);
                ++index;
            }
        }
        tracer.print("");
    }
    
    int getSchemaCount() {
        return this.numElements;
    }
    
    void ensureMetaData(final ParameterSet set) throws Open4GLException, ProSQLException {
        int n = 0;
        for (int i = 1; i <= set.getNumParams(); ++i) {
            if (set.isResultSet(i)) {
                if (set.getProType(i) == 17 && set.isInput(i)) {
                    final ResultSetMetaDataIndicator resultSetMetaDataIndicator = super.schemas.elementAt(n);
                    final ResultSet set2 = (ResultSet)set.getValue(i);
                    if (set2 == null) {
                        ++n;
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
                    resultSetMetaDataIndicator.setMetaData(metaData2);
                }
                else if (set.getProType(i) == 17 && set.isOutput(i)) {
                    ((ResultSetMetaDataIndicator)super.schemas.elementAt(n)).setMetaData(null);
                }
                ++n;
            }
            if (set.isDataGraph(i)) {
                if (set.getProType(i) == 37 && set.isInput(i)) {
                    final ProDataGraphMetaDataIndicator proDataGraphMetaDataIndicator = super.schemas.elementAt(n);
                    proDataGraphMetaDataIndicator.setMetaData(null);
                    final ProDataGraph proDataGraph = (ProDataGraph)set.getValue(i);
                    if (proDataGraph != null) {
                        final ProDataGraphMetaData metaData3 = proDataGraph.getMetaData();
                        if (metaData3 == null) {
                            throw new Open4GLException(7665970990714725438L, null);
                        }
                        proDataGraphMetaDataIndicator.setMetaData(metaData3);
                    }
                }
                else if (set.getProType(i) == 37 && set.isOutput(i)) {
                    ((ProDataGraphMetaDataIndicator)super.schemas.elementAt(n)).setMetaData(null);
                }
                ++n;
            }
        }
    }
}
