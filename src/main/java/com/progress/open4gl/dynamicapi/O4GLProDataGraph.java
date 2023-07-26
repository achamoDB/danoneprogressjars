// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProDataRelationMetaData;
import com.progress.open4gl.ProDataObjectMetaData;
import com.progress.open4gl.ProChangeSummary;
import com.progress.open4gl.RunTimeProperties;
import java.util.List;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ProDataObject;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.ProDataGraphMetaData;
import com.progress.open4gl.ProDataGraph;

public class O4GLProDataGraph
{
    protected ProDataGraph m_dataGraph;
    private StreamReader m_reader;
    private int m_paramNum;
    private int m_currentRow;
    private boolean m_isFilled;
    private O4GLProDataObjectList[] m_O4GLProDataObjectList;
    private Session m_session;
    
    public O4GLProDataGraph(final ProDataGraphMetaData proDataGraphMetaData, final StreamReader streamReader) throws ProDataException, Exception {
        this(proDataGraphMetaData, streamReader, 0);
    }
    
    public O4GLProDataGraph(final ProDataGraphMetaData proDataGraphMetaData, final StreamReader reader, final int paramNum) throws ProDataException, Exception {
        this.m_dataGraph = null;
        this.m_paramNum = 0;
        this.m_currentRow = 0;
        this.m_isFilled = false;
        this.m_dataGraph = new ProDataGraph(proDataGraphMetaData);
        this.m_paramNum = paramNum;
        this.m_reader = reader;
        this.m_O4GLProDataObjectList = new O4GLProDataObjectList[proDataGraphMetaData.getNumTables()];
    }
    
    public ProDataObject createProDataObject(final String s) throws Open4GLException {
        return this.m_dataGraph.createProDataObject(s);
    }
    
    public void addO4GLProDataObjectList(final int n, final O4GLProDataObjectList list) {
        this.m_O4GLProDataObjectList[n] = list;
    }
    
    private O4GLProDataObjectList getO4GLProDataObjectList(final int n) {
        return this.m_O4GLProDataObjectList[n];
    }
    
    public List getProDataObjects(final int n) throws Exception {
        return this.m_dataGraph.getProDataObjects(n);
    }
    
    public ProDataGraph getProDataGraph() {
        return this.m_dataGraph;
    }
    
    public void close() throws ProDataException {
        this.close(true, false);
    }
    
    public void close(final boolean b, final boolean b2) throws ProDataException {
        if (this.m_reader == null) {
            return;
        }
        if (b) {
            while (this.next()) {}
        }
        this.m_reader = null;
    }
    
    public void fillSelf() throws Open4GLException, ProDataException, ClientException {
        final ProChangeSummary proChangeSummary = this.m_dataGraph.getProChangeSummary();
        boolean b = false;
        if (this.m_isFilled) {
            return;
        }
        try {
            for (int i = 0; i < this.m_dataGraph.getNumTables(); ++i) {
                final O4GLProDataObjectList o4GLProDataObjectList = this.getO4GLProDataObjectList(i);
                if (o4GLProDataObjectList != null) {
                    final ProDataObjectMetaData metaData = o4GLProDataObjectList.getMetaData();
                    if (metaData != null && metaData.getBImageFlag()) {
                        b = true;
                        break;
                    }
                }
            }
            if (b && proChangeSummary.isLogging()) {
                proChangeSummary.endLogging();
            }
            for (int j = 0; j < this.m_dataGraph.getNumTables(); ++j) {
                final O4GLProDataObjectList o4GLProDataObjectList2 = this.getO4GLProDataObjectList(j);
                if (o4GLProDataObjectList2 != null) {
                    o4GLProDataObjectList2.fillSelf();
                }
            }
            if (!RunTimeProperties.getPostponeRelationInfo()) {
                final ProDataGraphMetaData metaData2 = this.m_dataGraph.getMetaData();
                for (int k = 0; k < metaData2.getNumRelations(); ++k) {
                    this.fillDataRelation(metaData2.getRelationMetaData(k));
                }
            }
            if (b) {
                if (!proChangeSummary.isLogging()) {
                    proChangeSummary.beginLogging();
                }
                for (int l = 0; l < this.m_dataGraph.getNumTables(); ++l) {
                    final O4GLProDataObjectList o4GLProDataObjectList3 = this.getO4GLProDataObjectList(l);
                    if (o4GLProDataObjectList3 != null) {
                        o4GLProDataObjectList3.applyChangesToSelf();
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new Open4GLException(ex.getMessage());
        }
        if (b) {
            proChangeSummary.endLogging();
        }
        this.m_isFilled = true;
    }
    
    private void fillDataRelation(final ProDataRelationMetaData proDataRelationMetaData) throws Open4GLException {
        proDataRelationMetaData.fillChildRelations(this.getO4GLProDataObjectList(proDataRelationMetaData.m_ParentIx).getList(), this.getO4GLProDataObjectList(proDataRelationMetaData.m_ChildIx).getList());
    }
    
    public boolean next() throws ProDataException {
        if (this.m_reader == null) {
            return false;
        }
        boolean nextRow;
        try {
            nextRow = this.m_reader.getNextRow();
            if (nextRow) {
                ++this.m_currentRow;
                if (RunTimeProperties.isTracing() && this.m_paramNum > 0) {
                    RunTimeProperties.tracer.print("\tOUTPUT table, parameter: " + this.m_paramNum + ", " + "row " + this.m_currentRow, 3);
                }
            }
            else {
                this.close(false, false);
            }
        }
        catch (ClientException ex) {
            if (this.m_session != null) {
                this.m_session.handleOutputErrors();
            }
            ExceptionConverter.convertToProDataException(ex);
            return false;
        }
        return nextRow;
    }
    
    void setSession(final Session session) {
        this.m_session = session;
    }
}
