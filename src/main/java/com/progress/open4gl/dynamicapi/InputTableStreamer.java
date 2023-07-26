// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Parameter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import com.progress.open4gl.ProDataObjectMetaData;
import java.io.IOException;
import com.progress.open4gl.ProDataObject;
import com.progress.open4gl.ProChangeSummary;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.ProDataGraphMetaData;
import com.progress.open4gl.ProDataGraph;
import java.sql.ResultSet;
import com.progress.open4gl.RunTimeProperties;
import java.util.Vector;
import java.io.OutputStream;

public class InputTableStreamer implements Interruptable
{
    protected OutputStream stream;
    protected ColumnStreamer streamer;
    protected boolean stopFlag;
    
    public InputTableStreamer(final OutputStream outputStream) {
        this(outputStream, false);
    }
    
    public InputTableStreamer(final OutputStream stream, final boolean b) {
        this.stream = stream;
        this.streamer = new ColumnStreamer(this.stream, b);
        this.stopFlag = false;
    }
    
    public void stop() {
        this.stopFlag = true;
    }
    
    protected boolean wasStopped() {
        if (this.stopFlag) {
            this.stopFlag = false;
            return true;
        }
        return false;
    }
    
    public void streamOut(final ParameterSet set, final Vector vector, final int n) throws ClientException, ProDataException {
        int n2 = 0;
        for (int i = 1; i <= set.getNumParams(); ++i) {
            if (set.isResultSet(i)) {
                if (set.isInput(i)) {
                    RunTimeProperties.tracer.print("\tINPUT Table, parameter " + i + ":", 3);
                    final MetaDataIndicator metaDataIndicator = vector.elementAt(n2);
                    this.DoStreamResultSet((ResultSet)set.getValue(i), (ResultSetMetaData)metaDataIndicator.getMetaData(), metaDataIndicator.getParamNum() - 1, true);
                }
                ++n2;
            }
            if (set.isDataGraph(i)) {
                if (set.isInput(i)) {
                    RunTimeProperties.tracer.print("\tINPUT DataSet, parameter " + i + ":", 3);
                    final MetaDataIndicator metaDataIndicator2 = vector.elementAt(n2);
                    this.doStreamProDataGraph((ProDataGraph)set.getValue(i), (ProDataGraphMetaData)metaDataIndicator2.getMetaData(), metaDataIndicator2.getParamNum() - 1, true);
                }
                ++n2;
            }
        }
    }
    
    protected void doStreamProDataGraph(final ProDataGraph proDataGraph, final ProDataGraphMetaData proDataGraphMetaData, final int n, final boolean b) throws ClientException, ProDataException {
        if (proDataGraph == null) {
            return;
        }
        final ProChangeSummary proChangeSummary = proDataGraph.getProChangeSummary();
        if (proChangeSummary.isLogging()) {
            proChangeSummary.endLogging();
        }
        try {
            int i = 0;
            while (i < proDataGraph.getNumTables()) {
                if (this.wasStopped()) {
                    throw new StopException();
                }
                this.doStreamProDataObjectList(proDataGraph, i++, n, b);
            }
        }
        catch (Exception ex) {
            ExceptionConverter.convertException(ex, new Open4GLException(55L, null));
        }
    }
    
    private void doStreamProDataObjectList(final ProDataGraph proDataGraph, final int n, final int n2, final boolean b) throws ClientException, ProDataException {
        final ProDataObjectMetaData tableMetaData = proDataGraph.getMetaData().getTableMetaData(n);
        final String tableName = tableMetaData.getTableName();
        final InputProDataGraphStreamer inputProDataGraphStreamer = new InputProDataGraphStreamer(this.stream, this.streamer);
        try {
            final List proDataObjects = proDataGraph.getProDataObjects(n);
            final ProChangeSummary proChangeSummary = proDataGraph.getProChangeSummary();
            final List changedDataObjects = proChangeSummary.getChangedDataObjects();
            if (proDataObjects == null) {
                return;
            }
            final short n3 = (short)n2;
            this.stream.write(1);
            this.stream.write(Util.getHighByte(n3));
            this.stream.write(Util.getLowByte(n3));
            int i = 0;
            while (i < proDataObjects.size()) {
                if (this.wasStopped()) {
                    throw new StopException();
                }
                inputProDataGraphStreamer.streamProDataObject(proDataObjects.get(i), i++ == 0 && b);
            }
            int n4 = 0;
            if (tableMetaData.getBImageFlag() && changedDataObjects != null) {
                for (final ProDataObject proDataObject : changedDataObjects) {
                    final String tableName2 = proDataObject.getTableName();
                    if (tableName2 != null && tableName.compareToIgnoreCase(tableName2) == 0 && proChangeSummary.isDeleted(proDataObject)) {
                        if (this.wasStopped()) {
                            throw new StopException();
                        }
                        inputProDataGraphStreamer.streamDeletedProDataObject(proDataObject, n4++ == 0 && b);
                    }
                }
            }
            if (b) {
                final Tracer tracer = RunTimeProperties.tracer;
                tracer.print("\t   TOTAL Number Of Rows: " + (i + n4), 3);
                tracer.print("", 3);
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        catch (Exception ex2) {
            ExceptionConverter.convertException(ex2, new Open4GLException(55L, null));
        }
    }
    
    public void streamResultSet(final ResultSet set, final ResultSetMetaData resultSetMetaData) throws ClientException {
        this.streamResultSet(set, resultSetMetaData, 9, 0);
    }
    
    public void streamResultSet(final ResultSet set, final ResultSetMetaData resultSetMetaData, final int n, final int n2) throws ClientException {
        this.DoStreamResultSet(set, resultSetMetaData, n2, false);
    }
    
    protected void DoStreamResultSet(final ResultSet set, final ResultSetMetaData resultSetMetaData, final int n, final boolean b) throws ClientException {
        if (set == null) {
            return;
        }
        try {
            final short n2 = (short)n;
            this.stream.write(1);
            this.stream.write(Util.getHighByte(n2));
            this.stream.write(Util.getLowByte(n2));
            int i = 0;
            while (set.next()) {
                if (this.wasStopped()) {
                    throw new StopException();
                }
                this.streamRow(set, resultSetMetaData, i == 0 && b, ++i);
            }
            if (b) {
                final Tracer tracer = RunTimeProperties.tracer;
                tracer.print("\t   TOTAL Number Of Rows: " + i, 3);
                tracer.print("", 3);
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        catch (SQLException ex2) {
            ExceptionConverter.convertSQLException(ex2);
        }
        catch (Exception ex3) {
            ExceptionConverter.convertException(ex3, new Open4GLException(55L, null));
        }
    }
    
    protected void streamRow(final ResultSet set, final ResultSetMetaData resultSetMetaData, final boolean b, final int n) throws ClientException {
        try {
            this.stream.write(2);
            final Tracer tracer = RunTimeProperties.tracer;
            if (b) {
                tracer.print("\t   First Row:", 3);
            }
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
                final Object object = set.getObject(i);
                final int proColumnType = resultSetMetaData.getProColumnType(i);
                final int validateInputValue = JdbcDataType.validateInputValue(object, proColumnType);
                if (b && RunTimeProperties.isTracing()) {
                    tracer.print("           COLUMN " + i + ": Type " + Parameter.proToName(proColumnType) + " - " + object, 3);
                }
                this.streamer.streamColumn(JdbcDataType.convertInputObject(object, validateInputValue), validateInputValue);
            }
        }
        catch (SQLException ex) {
            ExceptionConverter.convertSQLException(ex);
        }
        catch (IOException ex2) {
            ExceptionConverter.convertIOException(ex2);
        }
        catch (Exception ex3) {
            ExceptionConverter.convertException(ex3, new Open4GLException(55L, null));
        }
    }
}
