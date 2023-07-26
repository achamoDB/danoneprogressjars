// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import commonj.sdo.Type;
import commonj.sdo.Property;
import java.util.Iterator;
import commonj.sdo.ChangeSummary;
import java.util.List;
import java.io.IOException;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.ProChangeSummary;
import com.progress.open4gl.ProDataObjectMetaData;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ProDataGraph;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.ProDataObject;
import java.io.OutputStream;

public class InputProDataGraphStreamer
{
    private OutputStream stream;
    private ColumnStreamer streamer;
    
    public InputProDataGraphStreamer(final OutputStream stream, final ColumnStreamer streamer) {
        this.stream = stream;
        this.streamer = streamer;
    }
    
    protected void streamProDataObject(final ProDataObject proDataObject, final boolean b) throws ClientException, ProDataException {
        final ProDataObjectMetaData metaData = proDataObject.getMetaData();
        try {
            final Tracer tracer = RunTimeProperties.tracer;
            if (!metaData.getBImageFlag()) {
                this.streamCurrentProDataObject(proDataObject, b);
            }
            else {
                final ProDataObject changedDataObject = ChangeSumm.getChangedDataObject(proDataObject);
                if (changedDataObject != null) {
                    final ProChangeSummary proChangeSummary = ((ProDataGraph)changedDataObject.getDataGraph()).getProChangeSummary();
                    final String name = changedDataObject.getType().getName();
                    if (proChangeSummary.isCreated(changedDataObject)) {
                        this.streamNewProDataObject(changedDataObject, b);
                    }
                    else if (!name.equalsIgnoreCase("RootClass")) {
                        this.streamModifiedProDataObject(changedDataObject, b);
                    }
                }
                else {
                    this.streamCurrentProDataObject(proDataObject, b);
                }
            }
        }
        catch (Exception ex) {
            ExceptionConverter.convertException(ex, new Open4GLException(55L, null));
        }
    }
    
    private void streamCurrentProDataObject(final ProDataObject proDataObject, final boolean b) throws ClientException, ProDataException {
        final ProDataObjectMetaData metaData = proDataObject.getMetaData();
        try {
            final Tracer tracer = RunTimeProperties.tracer;
            this.stream.write(2);
            if (b) {
                tracer.print("\t   First Row:", 3);
            }
            int i = 0;
            int n = 0;
            while (i < metaData.getColumnCount()) {
                final int proColumnType = metaData.getProColumnType(i);
                final int columnExtent = metaData.getColumnExtent(i);
                if (columnExtent > 1) {
                    final List list = proDataObject.getList(n);
                    Object value = null;
                    for (int j = 0; j < columnExtent; ++j) {
                        ++i;
                        if (j < list.size()) {
                            value = list.get(j);
                        }
                        final int validateInputValue = JdbcDataType.validateInputValue(value, proColumnType);
                        if (b && RunTimeProperties.isTracing()) {
                            tracer.print("           COLUMN " + i + ": Type " + Parameter.proToName(proColumnType) + " - " + value, 3);
                        }
                        this.streamer.streamColumn(JdbcDataType.convertInputObject(value, validateInputValue), validateInputValue);
                    }
                }
                else {
                    final Object value2 = proDataObject.get(n);
                    ++i;
                    final int validateInputValue2 = JdbcDataType.validateInputValue(value2, proColumnType);
                    if (b && RunTimeProperties.isTracing()) {
                        tracer.print("           COLUMN " + i + ": Type " + Parameter.proToName(proColumnType) + " - " + value2, 3);
                    }
                    this.streamer.streamColumn(JdbcDataType.convertInputObject(value2, validateInputValue2), validateInputValue2);
                }
                ++n;
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
    
    private void streamNewProDataObject(final ProDataObject proDataObject, final boolean b) throws ClientException, ProDataException {
        proDataObject.getMetaData();
        final Object o = null;
        try {
            this.stream.write(17);
            this.streamer.streamColumn(JdbcDataType.convertInputObject(new Integer(3), 4), 4);
            this.stream.write(3);
            this.streamer.streamColumn(o, 8);
            this.streamer.streamColumn(null, 1);
            this.streamCurrentProDataObject(proDataObject, b);
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
    
    protected void streamDeletedProDataObject(final ProDataObject proDataObject, final boolean b) throws ClientException, ProDataException {
        final ProDataObjectMetaData metaData = proDataObject.getMetaData();
        final ProChangeSummary proChangeSummary = ((ProDataGraph)proDataObject.getDataGraph()).getProChangeSummary();
        final Tracer tracer = RunTimeProperties.tracer;
        final Object o = null;
        try {
            this.stream.write(17);
            this.streamer.streamColumn(JdbcDataType.convertInputObject(new Integer(1), 4), 4);
            this.stream.write(3);
            this.streamer.streamColumn(o, 8);
            this.streamer.streamColumn(null, 1);
            final Iterator iterator = proChangeSummary.getOldValues(proDataObject).iterator();
            if (b) {
                tracer.print("\t   First Row:", 3);
            }
            int i = 0;
            while (i < metaData.getColumnCount()) {
                final ChangeSummary.Setting setting = iterator.next();
                final Property property = setting.getProperty();
                final int proColumnType = metaData.getProColumnType(i);
                final int columnExtent = metaData.getColumnExtent(i);
                if (columnExtent > 1) {
                    List<Object> list = null;
                    if (property.getType().getName().equals("EJavaObject")) {
                        if (setting.isSet()) {
                            list = (List<Object>)setting.getValue();
                        }
                    }
                    else {
                        list = (List<Object>)setting.getValue();
                    }
                    Object value = null;
                    for (int j = 0; j < columnExtent; ++j) {
                        ++i;
                        if (list != null && j < list.size()) {
                            value = list.get(j);
                        }
                        final int validateInputValue = JdbcDataType.validateInputValue(value, proColumnType);
                        if (b && RunTimeProperties.isTracing()) {
                            tracer.print("           COLUMN " + i + ": Type " + Parameter.proToName(proColumnType) + " - " + value, 3);
                        }
                        this.streamer.streamColumn(JdbcDataType.convertInputObject(value, validateInputValue), validateInputValue);
                    }
                }
                else {
                    Object obj = null;
                    if (property.getType().getName().equals("EJavaObject")) {
                        if (setting.isSet()) {
                            obj = setting.getValue();
                        }
                    }
                    else {
                        obj = setting.getValue();
                    }
                    ++i;
                    final int validateInputValue2 = JdbcDataType.validateInputValue(obj, proColumnType);
                    if (b && RunTimeProperties.isTracing()) {
                        tracer.print("           COLUMN " + i + ": Type " + Parameter.proToName(proColumnType) + " - " + obj, 3);
                    }
                    this.streamer.streamColumn(JdbcDataType.convertInputObject(obj, validateInputValue2), validateInputValue2);
                }
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
    
    private void streamModifiedProDataObject(final ProDataObject proDataObject, final boolean b) throws ClientException, ProDataException {
        final ProDataGraph proDataGraph = (ProDataGraph)proDataObject.getDataGraph();
        final ProDataObjectMetaData metaData = proDataObject.getMetaData();
        final ProChangeSummary proChangeSummary = proDataGraph.getProChangeSummary();
        final Tracer tracer = RunTimeProperties.tracer;
        final Object o = null;
        try {
            this.stream.write(17);
            this.streamer.streamColumn(JdbcDataType.convertInputObject(new Integer(2), 4), 4);
            this.stream.write(3);
            this.streamer.streamColumn(o, 8);
            this.streamer.streamColumn(null, 1);
            final Type type = proDataObject.getType();
            final List oldValues = proChangeSummary.getOldValues(proDataObject);
            final Iterator iterator = type.getProperties().iterator();
            if (b) {
                tracer.print("\t   First Row:", 3);
            }
            int i = 0;
            while (i < metaData.getColumnCount()) {
                final Property property = iterator.next();
                final int proColumnType = metaData.getProColumnType(i);
                final int columnExtent = metaData.getColumnExtent(i);
                if (columnExtent > 1) {
                    final List list = (List)ChangeSumm.getOrigValue(proDataObject, property, oldValues);
                    Object value = null;
                    for (int j = 0; j < columnExtent; ++j) {
                        ++i;
                        if (j < list.size()) {
                            value = list.get(j);
                        }
                        final int validateInputValue = JdbcDataType.validateInputValue(value, proColumnType);
                        if (b && RunTimeProperties.isTracing()) {
                            tracer.print("           COLUMN " + i + ": Type " + Parameter.proToName(proColumnType) + " - " + value, 3);
                        }
                        this.streamer.streamColumn(JdbcDataType.convertInputObject(value, validateInputValue), validateInputValue);
                    }
                }
                else {
                    final Object origValue = ChangeSumm.getOrigValue(proDataObject, property, oldValues);
                    ++i;
                    final int validateInputValue2 = JdbcDataType.validateInputValue(origValue, proColumnType);
                    if (b && RunTimeProperties.isTracing()) {
                        tracer.print("           COLUMN " + i + ": Type " + Parameter.proToName(proColumnType) + " - " + origValue, 3);
                    }
                    this.streamer.streamColumn(JdbcDataType.convertInputObject(origValue, validateInputValue2), validateInputValue2);
                }
            }
            this.streamCurrentProDataObject(proDataObject, b);
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
}
