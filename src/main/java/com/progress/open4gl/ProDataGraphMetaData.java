// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.open4gl.dynamicapi.ProDataObjectMetaDataIndicator;
import commonj.sdo.DataGraph;
import com.progress.open4gl.dynamicapi.DataGraphMetaData;

public class ProDataGraphMetaData extends DataGraphMetaData
{
    public ProDataGraphMetaData(final int n) {
        super(n);
    }
    
    public ProDataGraphMetaData(final int n, final String s, final int n2, final int n3) {
        super(n, s, n2, n3);
    }
    
    public ProDataGraphMetaData(final String s) {
        super(s);
    }
    
    public ProDataGraphMetaData(final ProDataGraphMetaData proDataGraphMetaData) {
        super(proDataGraphMetaData);
    }
    
    public ProDataGraphMetaData(final DataGraph dataGraph, final String s, final ProDataRelationMetaData[] array) throws ProDataException, Open4GLException {
        super(dataGraph, s, array);
    }
    
    public void addTable(final ProDataObjectMetaData proDataObjectMetaData) {
        final ProDataObjectMetaDataIndicator proDataObjectMetaDataIndicator = new ProDataObjectMetaDataIndicator(proDataObjectMetaData, super.m_ParmNum, super.m_InOut);
        if (super.m_TableCount == 0) {
            ++super.m_TableCount;
            (super.m_domdi = new ProDataObjectMetaDataIndicator[super.m_TableCount])[0] = proDataObjectMetaDataIndicator;
            (super.m_tableNames = new String[super.m_TableCount])[0] = proDataObjectMetaData.getTableName();
        }
        else {
            ++super.m_TableCount;
            final ProDataObjectMetaDataIndicator[] domdi = new ProDataObjectMetaDataIndicator[super.m_TableCount];
            final String[] tableNames = new String[super.m_TableCount];
            for (int i = 0; i < super.m_TableCount - 1; ++i) {
                domdi[i] = super.m_domdi[i];
                tableNames[i] = super.m_tableNames[i];
            }
            domdi[super.m_TableCount - 1] = proDataObjectMetaDataIndicator;
            tableNames[super.m_TableCount - 1] = proDataObjectMetaData.getTableName();
            super.m_domdi = domdi;
            super.m_tableNames = tableNames;
        }
    }
    
    public void addDataRelation(final ProDataRelationMetaData proDataRelationMetaData) {
        super.addDataRelation(proDataRelationMetaData);
    }
    
    public ProDataRelationMetaData getRelationMetaData(final int n) {
        if (super.m_drmd == null || n >= super.m_drmd.length) {
            return null;
        }
        return super.m_drmd[n];
    }
    
    public ProDataRelationMetaData getRelationMetaData(final String str) {
        if (super.m_drmd == null) {
            return null;
        }
        for (int i = 0; i < super.m_drmd.length; ++i) {
            final ProDataRelationMetaData proDataRelationMetaData = super.m_drmd[i];
            if (proDataRelationMetaData.getRelationName().compareToIgnoreCase(str) == 0) {
                return proDataRelationMetaData;
            }
        }
        return null;
    }
    
    public int getNumRelations() {
        if (super.m_drmd == null) {
            return 0;
        }
        return super.m_drmd.length;
    }
    
    public int getNumTables() {
        return super.m_TableCount;
    }
    
    public String[] getTableNames() {
        return super.m_tableNames;
    }
    
    public int getTableIndex(final String str) throws Open4GLException {
        for (int i = 0; i < super.m_TableCount; ++i) {
            if (str.compareToIgnoreCase(super.m_tableNames[i]) == 0) {
                return i;
            }
        }
        throw new Open4GLException("Invalid table name: " + str + ". Does not exist in this ProDataGraph");
    }
    
    protected boolean hasError() {
        return super.hasError();
    }
}
