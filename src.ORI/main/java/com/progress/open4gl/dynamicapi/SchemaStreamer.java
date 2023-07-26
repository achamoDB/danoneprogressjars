// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProDataRelationMetaData;
import com.progress.open4gl.ProDataGraphMetaData;
import java.sql.ResultSet;
import com.progress.open4gl.ProDataObjectMetaData;
import com.progress.open4gl.InputResultSet;
import com.progress.open4gl.ProDataGraph;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.ProDataException;
import java.io.IOException;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.Open4GLError;
import java.io.OutputStream;
import java.util.Vector;

class SchemaStreamer
{
    private Vector schema;
    private OutputStream outStream;
    private int tableCount;
    private OutputBuffer outBuffer;
    private ColumnStreamer colStreamer;
    private int m_serverMajorVersion;
    private ParameterSet paramSet;
    
    SchemaStreamer(final MetaSchema metaSchema, final ParameterSet paramSet, final OutputStream outStream, final OutputBuffer outBuffer, final int serverMajorVersion) {
        this.m_serverMajorVersion = serverMajorVersion;
        if (metaSchema == null) {
            this.schema = null;
            this.tableCount = 0;
            return;
        }
        this.schema = metaSchema.getSchemas();
        this.outStream = outStream;
        this.tableCount = metaSchema.getSchemaCount();
        this.outBuffer = outBuffer;
        this.colStreamer = new ColumnStreamer();
        this.paramSet = paramSet;
    }
    
    void streamOut() throws ProDataException, ClientException {
        try {
            this.streamOut0();
        }
        catch (ProSQLException ex) {
            throw new Open4GLError();
        }
        catch (IOException ex2) {
            throw new Open4GLError();
        }
    }
    
    private void streamOut0() throws ClientException, ProSQLException, IOException, ProDataException {
        if (this.schema == null) {
            return;
        }
        for (int i = 0; i < this.tableCount; ++i) {
            this.outBuffer.reset();
            final MetaDataBase metaData = this.schema.elementAt(i).getMetaData();
            if (metaData == null) {
                this.outStream.write(RunTimeProperties.getStreamProtocolVersion());
                this.outStream.write(Util.getHighByte((short)1));
                this.outStream.write(Util.getLowByte((short)1));
                this.outStream.write(14);
            }
            else if (metaData.getTypeCode() == 0) {
                final ResultSetMetaDataIndicator resultSetMetaDataIndicator = this.schema.elementAt(i);
                this.streamOutResultSet(this.paramSet.getValue(resultSetMetaDataIndicator.getParamNum()), resultSetMetaDataIndicator, resultSetMetaDataIndicator.getParamNum(), resultSetMetaDataIndicator.getInOut());
            }
            else if (metaData.getTypeCode() == 1) {
                final ProDataGraphMetaDataIndicator proDataGraphMetaDataIndicator = this.schema.elementAt(i);
                this.streamOutProDataGraph((ProDataGraph)this.paramSet.getValue(proDataGraphMetaDataIndicator.getParamNum()), proDataGraphMetaDataIndicator);
            }
        }
    }
    
    private void streamOutResultSet(final Object o, final MetaDataIndicator metaDataIndicator, final int n, final int n2) throws ClientException, ProSQLException, IOException, ProDataException {
        int n3 = 0;
        ResultSet set = null;
        String s = "buff";
        final String s2 = null;
        String multiIndexCols = null;
        int numIndexes = 0;
        int n4 = 0;
        final int n5 = 0;
        final String s3 = null;
        final String s4 = null;
        final String s5 = null;
        String tableName = null;
        ResultSetMetaData resultSetMetaData;
        int n6;
        if (metaDataIndicator.m_typeCode == 0) {
            final ResultSetMetaDataIndicator resultSetMetaDataIndicator = (ResultSetMetaDataIndicator)metaDataIndicator;
            resultSetMetaData = (ResultSetMetaData)metaDataIndicator.getMetaData();
            n6 = resultSetMetaData.getFieldCount();
            if (this.m_serverMajorVersion > 9) {
                n3 = 2;
                if (o instanceof InputResultSet && ((InputResultSet)o).getNoSchemaMarshal()) {
                    n3 = 4;
                }
            }
        }
        else {
            final ProDataObjectMetaData proDataObjectMetaData = (ProDataObjectMetaData)((ProDataObjectMetaDataIndicator)metaDataIndicator).getMetaData();
            resultSetMetaData = proDataObjectMetaData.getResultSetMetaData();
            n6 = proDataObjectMetaData.getFieldCount();
            numIndexes = proDataObjectMetaData.getNumIndexes();
            multiIndexCols = proDataObjectMetaData.getMultiIndexCols();
            if (proDataObjectMetaData.getBImageFlag()) {
                n4 |= 0x2;
            }
            tableName = proDataObjectMetaData.getTableName();
            if (this.m_serverMajorVersion > 9) {
                n3 = 2;
                if (o != null) {
                    if (((ProDataObjectMetaData)o).getNoSchemaMarshal()) {
                        n3 = 4;
                    }
                }
                else if (proDataObjectMetaData.getNoSchemaMarshal()) {
                    n3 = 4;
                }
            }
        }
        resultSetMetaData.validateFields();
        TableResultSet set2;
        if (this.m_serverMajorVersion <= 9) {
            set2 = new TableResultSet(n, n2, resultSetMetaData.getFieldCount());
            set = new FieldResultSet(resultSetMetaData);
        }
        else {
            if (tableName != null && tableName.length() != 0) {
                s = tableName;
            }
            if (n3 != 4) {
                set2 = new TableResultSet(n, n2, n6, "utf-8", s, numIndexes, n4, s2, n5, multiIndexCols, s3, s4, s5);
                set = new FieldResultSet(resultSetMetaData);
            }
            else {
                set2 = new TableResultSet(n, n2, n6, "utf-8", s, numIndexes, n4, s2, n5);
            }
        }
        final InputTableStreamer inputTableStreamer = new InputTableStreamer(this.outBuffer);
        if (this.m_serverMajorVersion > 9) {
            if (n3 == 2) {
                inputTableStreamer.streamResultSet(set2, TableParamMetaSchema.metaDataV2, this.m_serverMajorVersion, n3);
                inputTableStreamer.streamResultSet(set, FieldParamMetaSchema.metaDataV2, this.m_serverMajorVersion, n3);
            }
            else {
                inputTableStreamer.streamResultSet(set2, TableParamMetaSchema.metaDataV4, this.m_serverMajorVersion, n3);
            }
        }
        else {
            inputTableStreamer.streamResultSet(set2, TableParamMetaSchema.metaData, this.m_serverMajorVersion, n3);
            inputTableStreamer.streamResultSet(set, FieldParamMetaSchema.metaData, this.m_serverMajorVersion, n3);
        }
        this.outStream.write(RunTimeProperties.getStreamProtocolVersion());
        final short n7 = (short)this.outBuffer.getLen();
        this.outStream.write(Util.getHighByte(n7));
        this.outStream.write(Util.getLowByte(n7));
        this.outStream.write(this.outBuffer.getBuf(), 0, this.outBuffer.getLen());
    }
    
    private void streamOutProDataGraph(final ProDataGraph proDataGraph, final ProDataGraphMetaDataIndicator proDataGraphMetaDataIndicator) throws ClientException, ProSQLException, IOException, ProDataException {
        final ProDataGraphMetaData proDataGraphMetaData = (ProDataGraphMetaData)proDataGraphMetaDataIndicator.getMetaData();
        DataGraphMetaData metaData = null;
        if (proDataGraph != null) {
            metaData = proDataGraph.getMetaData();
        }
        if (proDataGraphMetaDataIndicator.isMappedTempTable()) {
            Object tableMetaData = null;
            if (metaData != null) {
                tableMetaData = metaData.getTableMetaData(0);
            }
            this.streamOutResultSet(tableMetaData, proDataGraphMetaData.getProDataObjectMetaDataIndicator(0), proDataGraphMetaDataIndicator.getParamNum(), proDataGraphMetaDataIndicator.getInOut());
            return;
        }
        final InputTableStreamer inputTableStreamer = new InputTableStreamer(this.outBuffer);
        inputTableStreamer.stream.write(1);
        inputTableStreamer.stream.write(1);
        inputTableStreamer.stream.write(2);
        inputTableStreamer.stream.write(2);
        final ColumnStreamer columnStreamer = new ColumnStreamer(this.outBuffer);
        columnStreamer.streamColumn(proDataGraphMetaData.getCodePage(), 1);
        columnStreamer.streamColumn(proDataGraphMetaData.getProDataGraphName(), 1);
        columnStreamer.streamColumn(new Integer(proDataGraphMetaDataIndicator.getParamNum()), 4);
        columnStreamer.streamColumn(new Integer(proDataGraphMetaDataIndicator.getInOut()), 4);
        columnStreamer.streamColumn(new Integer(proDataGraphMetaData.getNumTables()), 4);
        columnStreamer.streamColumn(new Integer(proDataGraphMetaData.getNumRelations()), 4);
        columnStreamer.streamColumn(new Integer(proDataGraphMetaData.getFlag()), 4);
        columnStreamer.streamColumn(proDataGraphMetaData.getXMLNamespace(), 1);
        columnStreamer.streamColumn(proDataGraphMetaData.getXMLPrefix(), 1);
        columnStreamer.streamColumn(proDataGraphMetaData.getExtendedProperties(), 1);
        final int numRelations = proDataGraphMetaData.getNumRelations();
        if (numRelations > 0) {
            inputTableStreamer.stream.write(1);
            inputTableStreamer.stream.write(0);
            inputTableStreamer.stream.write(0);
            for (int i = 0; i < numRelations; ++i) {
                final ProDataRelationMetaData relationMetaData = proDataGraphMetaData.getRelationMetaData(i);
                inputTableStreamer.stream.write(2);
                columnStreamer.streamColumn(new Integer(relationMetaData.m_ChildIx), 4);
                columnStreamer.streamColumn(new Integer(relationMetaData.m_ParentIx), 4);
                columnStreamer.streamColumn(new Integer(relationMetaData.getFlag()), 4);
                columnStreamer.streamColumn(new Integer(relationMetaData.m_NumPairs), 4);
                columnStreamer.streamColumn(relationMetaData.getPairsList(), 1);
                columnStreamer.streamColumn(relationMetaData.getRelationName(), 1);
                columnStreamer.streamColumn(relationMetaData.m_ExtendedProperties, 1);
            }
        }
        this.outStream.write(RunTimeProperties.getStreamProtocolVersion());
        final short n = (short)this.outBuffer.getLen();
        this.outStream.write(Util.getHighByte(n));
        this.outStream.write(Util.getLowByte(n));
        this.outStream.write(this.outBuffer.getBuf(), 0, this.outBuffer.getLen());
        for (int numTables = proDataGraphMetaData.getNumTables(), j = 0; j < numTables; ++j) {
            Object tableMetaData2 = null;
            if (metaData != null) {
                tableMetaData2 = metaData.getTableMetaData(j);
            }
            this.outBuffer.reset();
            this.streamOutResultSet(tableMetaData2, proDataGraphMetaData.getProDataObjectMetaDataIndicator(j), proDataGraphMetaDataIndicator.getParamNum(), proDataGraphMetaDataIndicator.getInOut());
        }
    }
}
