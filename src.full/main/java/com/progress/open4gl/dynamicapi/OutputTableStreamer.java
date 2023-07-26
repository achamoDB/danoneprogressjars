// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.ProDataException;
import java.util.List;
import com.progress.open4gl.ProDataObjectMetaData;
import com.progress.open4gl.ProDataGraphMetaData;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class OutputTableStreamer
{
    private ParameterSet params;
    private Vector schemas;
    private Vector infoSet;
    private int numOutputSets;
    private InputStream inputStream;
    
    public OutputTableStreamer(final ParameterSet params, final ResultSetSchema resultSetSchema, final InputStream inputStream) {
        this.params = params;
        this.schemas = resultSetSchema.getSchemas();
        this.params.setOutputReady();
        this.inputStream = inputStream;
    }
    
    public ResultSet createResults(final Session session) throws ClientException, ProDataException {
        ResultSet prev = null;
        int skipTblHdr = 0;
        int n = 0;
        for (int i = 1; i <= this.params.getNumParams(); ++i) {
            if (this.params.isResultSet(i)) {
                if (this.params.isOutput(i)) {
                    final ResultSetMetaDataIndicator resultSetMetaDataIndicator = this.schemas.elementAt(n);
                    if (resultSetMetaDataIndicator.getMetaData() == null) {
                        this.params.setValue(i, null);
                        ++skipTblHdr;
                        if (prev == null) {
                            this.params.setReadHdr(true);
                        }
                        else {
                            prev.setSkipTblHdr(skipTblHdr);
                        }
                    }
                    else {
                        if (this.params.getReadHdr()) {
                            this.processSkipTags(skipTblHdr);
                            skipTblHdr = 0;
                            this.params.setReadHdr(false);
                        }
                        final ResultSet next = new ResultSet((ResultSetMetaData)resultSetMetaDataIndicator.getMetaData(), new StreamReader(this.inputStream), i);
                        next.setSession(session);
                        if (prev != null) {
                            prev.setNext(next);
                            skipTblHdr = 0;
                        }
                        next.setPrev(prev);
                        prev = next;
                        this.params.setValue(i, next);
                    }
                }
                ++n;
            }
            if (this.params.isDataGraph(i)) {
                if (this.params.isOutput(i)) {
                    final ProDataGraphMetaDataIndicator proDataGraphMetaDataIndicator = this.schemas.elementAt(n);
                    if (proDataGraphMetaDataIndicator.getMetaData() == null) {
                        this.params.setValue(i, null);
                    }
                    else {
                        if (this.params.getReadHdr()) {
                            try {
                                if (this.inputStream.read() == 1) {
                                    this.inputStream.read();
                                    this.inputStream.read();
                                }
                            }
                            catch (IOException ex) {
                                ExceptionConverter.convertIOException(ex);
                            }
                            this.params.setReadHdr(false);
                        }
                        final ProDataGraphMetaData proDataGraphMetaData = (ProDataGraphMetaData)proDataGraphMetaDataIndicator.getMetaData();
                        O4GLProDataGraph o4GLProDataGraph = null;
                        try {
                            o4GLProDataGraph = new O4GLProDataGraph(proDataGraphMetaData, null, proDataGraphMetaDataIndicator.getParamNum());
                        }
                        catch (Exception ex2) {
                            ExceptionConverter.convertException(ex2);
                        }
                        for (int j = 0; j < proDataGraphMetaData.getNumTables(); ++j) {
                            final ProDataObjectMetaData tableMetaData = proDataGraphMetaData.getTableMetaData(j);
                            List proDataObjects = null;
                            try {
                                proDataObjects = o4GLProDataGraph.getProDataObjects(j);
                            }
                            catch (Exception ex3) {
                                ExceptionConverter.convertException(ex3);
                            }
                            final O4GLProDataObjectList list = new O4GLProDataObjectList(o4GLProDataGraph, tableMetaData, proDataObjects, new StreamReader(this.inputStream, session.getServerMajorVersion()), i);
                            o4GLProDataGraph.addO4GLProDataObjectList(j, list);
                            list.setSession(session);
                        }
                        this.params.setValue(i, o4GLProDataGraph);
                    }
                }
                ++n;
            }
        }
        return prev;
    }
    
    public void fillResults(final Session session) throws ClientException, ProDataException {
        try {
            int n = 0;
            int n2 = 0;
            for (int i = 1; i <= this.params.getNumParams(); ++i) {
                if (this.params.isDataGraph(i)) {
                    if (this.params.isOutput(i)) {
                        final Object value = this.params.getValue(i);
                        if (value != null) {
                            if (n > 0) {
                                this.processSkipTags(n);
                                n = 0;
                            }
                            ((O4GLProDataGraph)value).fillSelf();
                        }
                        else {
                            ++n;
                            if (RunTimeProperties.isTracing()) {
                                RunTimeProperties.tracer.print("\tOUTPUT table, parameter: " + i + " is null. Reading header", 3);
                            }
                        }
                    }
                    ++n2;
                }
            }
            if (n > 1) {
                this.processSkipTags(n - 1);
            }
        }
        catch (Exception ex) {
            ExceptionConverter.convertException(ex);
        }
    }
    
    private void processSkipTags(int i) throws ClientException, ProDataException {
        try {
            while (i > 0) {
                final int read = this.inputStream.read();
                if (read != 1) {
                    throw new ClientException(2, 16L, new Object[] { read + ". Expected table tag" });
                }
                this.inputStream.read();
                this.inputStream.read();
                --i;
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
}
