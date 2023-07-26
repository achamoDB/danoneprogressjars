// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.Iterator;
import commonj.sdo.Property;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.ProDataObject;
import com.progress.open4gl.ProDataException;
import com.progress.open4gl.RunTimeProperties;
import java.util.Vector;
import com.progress.open4gl.ProDataObjectMetaData;
import java.util.List;

public class O4GLProDataObjectList
{
    private boolean allInvalid;
    private O4GLProDataGraph m_o4glDataGraph;
    private List m_doList;
    private ProDataObjectMetaData m_schema;
    private StreamReader m_reader;
    private ProToJava m_toJava;
    private int m_currentCol;
    private int m_paramNum;
    private int m_currentRow;
    private boolean m_isNull;
    private boolean m_isFilled;
    private boolean m_hasAppliedChanges;
    private boolean gotSchemaHeader;
    private int m_marshalLevel;
    private Vector m_rowState;
    private Vector m_changeDataObjs;
    private Vector m_newDataObjs;
    private Vector m_deleteDataObjs;
    private static Integer DELETE;
    private static Integer CHANGE;
    private static Integer ADD;
    private static Integer NOCHANGE;
    private Session m_session;
    
    public O4GLProDataObjectList(final O4GLProDataGraph o4GLProDataGraph, final ProDataObjectMetaData proDataObjectMetaData, final List list, final StreamReader streamReader) {
        this(o4GLProDataGraph, proDataObjectMetaData, list, streamReader, 0);
    }
    
    public O4GLProDataObjectList(final O4GLProDataGraph o4glDataGraph, final ProDataObjectMetaData schema, final List doList, final StreamReader reader, final int paramNum) {
        this.m_currentCol = 0;
        this.m_paramNum = 0;
        this.m_currentRow = 0;
        this.m_isNull = false;
        this.m_isFilled = false;
        this.m_hasAppliedChanges = false;
        this.gotSchemaHeader = false;
        this.m_marshalLevel = 0;
        this.m_o4glDataGraph = o4glDataGraph;
        this.m_schema = schema;
        this.m_doList = doList;
        this.m_reader = reader;
        this.m_paramNum = paramNum;
        this.m_toJava = new ProToJava(reader);
    }
    
    public void setSession(final Session session) {
        this.m_session = session;
    }
    
    public Session getSession() {
        return this.m_session;
    }
    
    public boolean hasSchemaHeader() {
        return this.gotSchemaHeader;
    }
    
    public boolean next() throws ProDataException {
        if (this.m_reader == null) {
            throw getProDataException(5L);
        }
        try {
            final boolean nextRow = this.m_reader.getNextRow();
            if (nextRow) {
                this.m_currentCol = 1;
                ++this.m_currentRow;
                if (RunTimeProperties.isTracing() && this.m_paramNum > 0) {
                    RunTimeProperties.tracer.print("\tOUTPUT table, parameter: " + this.m_paramNum + ", " + "row " + this.m_currentRow, 3);
                }
            }
            else {
                this.gotSchemaHeader = this.m_reader.hasSchemaHeader();
                this.m_marshalLevel = this.m_reader.getMarshalLevel();
                this.close(false, false);
            }
            return nextRow;
        }
        catch (ClientException ex) {
            if (this.m_session != null) {
                this.m_session.handleOutputErrors();
            }
            ExceptionConverter.convertToProDataException(ex);
            return false;
        }
    }
    
    private void validatePos(final int value) throws ProDataException {
        if (this.m_reader == null) {
            throw getProDataException(7L);
        }
        if (value < 1 || value > this.m_schema.getColumnCount() || this.m_currentCol == 0 || value < this.m_currentCol) {
            throw getProDataException(8L, new Integer(value).toString());
        }
    }
    
    private void positionCol(final int n) throws ProDataException {
        try {
            for (int i = n - this.m_currentCol; i >= 0; --i) {
                this.m_reader.getNextColumn();
                if (i > 0) {
                    this.m_reader.skipColumn();
                }
            }
            if (this.m_toJava.isNull()) {
                this.m_isNull = true;
            }
            else {
                this.m_isNull = false;
            }
            this.m_currentCol = n + 1;
        }
        catch (ClientException ex) {
            if (this.m_session != null) {
                this.m_session.handleOutputErrors();
            }
            ExceptionConverter.convertToProDataException(ex);
        }
    }
    
    public boolean wasNull() {
        return this.m_isNull;
    }
    
    public ProDataObjectMetaData GetSchemaTable() {
        return this.m_schema;
    }
    
    public ProDataObjectMetaData getMetaData() {
        return this.m_schema;
    }
    
    protected List getList() {
        return this.m_doList;
    }
    
    public void close() throws ProDataException {
        this.close(true, false);
    }
    
    private synchronized void close(final boolean b, final boolean b2) throws ProDataException {
        if (this.m_reader == null) {
            return;
        }
        if (b) {
            while (this.next()) {}
        }
        this.m_reader = null;
        this.m_toJava = null;
    }
    
    private ProDataObject buildRowNoBI(final boolean b) throws Open4GLException, ProDataException {
        final int columnCount = this.m_schema.getColumnCount();
        final ProDataObject proDataObject = this.m_o4glDataGraph.createProDataObject(this.m_schema.getTableName());
        int n = 0;
        int i = 0;
        while (i < columnCount) {
            final int columnExtent = this.m_schema.getColumnExtent(i);
            if (columnExtent > 1) {
                final Vector<Object> vector = new Vector<Object>();
                for (int j = 0; j < columnExtent; ++j) {
                    vector.add(this.getObject(i++));
                }
                proDataObject.set(n, (Object)vector);
            }
            else {
                proDataObject.set(n, this.getObject(i++));
            }
            ++n;
        }
        if (b) {
            this.m_doList.add(proDataObject);
        }
        return proDataObject;
    }
    
    private ProDataObject buildRowBI() throws Open4GLException, ProDataException, ClientException {
        this.m_schema.getColumnCount();
        if (this.m_reader.getColumnType() != 17) {
            final ProDataObject buildRowNoBI = this.buildRowNoBI(true);
            this.m_rowState.add(O4GLProDataObjectList.NOCHANGE);
            return buildRowNoBI;
        }
        if (!this.m_reader.getNextColumn()) {
            throw new ClientException(2, 16L, new Object[] { "false" });
        }
        if (this.m_reader.getColumnType() != 5) {
            throw new ClientException(2, 16L, new Object[] { "false" });
        }
        final int proInt = this.m_toJava.getProInt();
        this.m_reader.getNextColumn();
        final int proInt2 = this.m_toJava.getProInt();
        this.m_reader.getNextColumn();
        this.m_toJava.getProRaw();
        if (!this.m_reader.getNextColumn()) {
            throw new ClientException(2, 16L, new Object[] { "false" });
        }
        String proString = this.m_toJava.getProString();
        if ((proInt2 & 0x1) != 0x0 && proString.compareToIgnoreCase("") == 0) {
            proString = "Unspecified error in Row";
        }
        ProDataObject proDataObject;
        if (proInt == 1) {
            proDataObject = this.buildRowNoBI(true);
            this.m_rowState.add(O4GLProDataObjectList.DELETE);
            this.m_deleteDataObjs.add(proDataObject);
        }
        else if (proInt == 2) {
            proDataObject = this.buildRowNoBI(true);
            this.m_rowState.add(O4GLProDataObjectList.CHANGE);
            if (!this.next()) {
                throw new ClientException(2, 16L, new Object[] { "false" });
            }
            final ProDataObject buildRowNoBI2 = this.buildRowNoBI(false);
            this.m_changeDataObjs.add(buildRowNoBI2);
            if ((proInt2 & 0x1) != 0x0) {
                buildRowNoBI2.setHasRowError(true);
                buildRowNoBI2.setRowErrorString(proString);
            }
        }
        else {
            if (proInt != 3) {
                throw new ClientException(2, 16L, new Object[] { "false" });
            }
            if (!this.next()) {
                throw new ClientException(2, 16L, new Object[] { "false" });
            }
            proDataObject = this.buildRowNoBI(false);
            this.m_newDataObjs.add(proDataObject);
            this.m_rowState.add(O4GLProDataObjectList.ADD);
        }
        if ((proInt2 & 0x1) != 0x0) {
            proDataObject.setHasRowError(true);
            proDataObject.setRowErrorString(proString);
        }
        return proDataObject;
    }
    
    private void buildRow() throws Open4GLException, ProDataException, ClientException {
        if (!this.m_schema.getBImageFlag()) {
            this.buildRowNoBI(true);
        }
        else {
            this.buildRowBI();
        }
    }
    
    protected void fillSelf() throws Open4GLException, ProDataException, ClientException {
        if (this.m_isFilled) {
            return;
        }
        this.m_rowState = new Vector();
        this.m_changeDataObjs = new Vector();
        this.m_newDataObjs = new Vector();
        this.m_deleteDataObjs = new Vector();
        int n = 1;
        while (this.next()) {
            this.buildRow();
            ++n;
        }
        this.m_isFilled = true;
    }
    
    protected void applyChangesToSelf() {
        if (this.m_hasAppliedChanges || !this.m_schema.getBImageFlag()) {
            return;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        final Iterator iterator = this.m_rowState.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == (int)O4GLProDataObjectList.ADD) {
                this.m_doList.add(n3, this.m_newDataObjs.get(n++));
            }
            ++n3;
        }
        int n4 = 0;
        final Iterator iterator2 = this.m_rowState.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next() == (int)O4GLProDataObjectList.CHANGE) {
                final ProDataObject proDataObject = this.m_doList.get(n4);
                final ProDataObject proDataObject2 = this.m_changeDataObjs.get(n2++);
                for (final Property property : proDataObject.getType().getProperties()) {
                    proDataObject.set(property, proDataObject2.get(property));
                }
            }
            ++n4;
        }
        final Iterator iterator4 = this.m_deleteDataObjs.iterator();
        while (iterator4.hasNext()) {
            iterator4.next().delete();
        }
        this.m_hasAppliedChanges = true;
    }
    
    private Object getObject(final int n) throws ProDataException {
        return this.getValue(n);
    }
    
    private Object getValue(final int n) throws ProDataException {
        this.validatePos(n + 1);
        this.positionCol(n + 1);
        if (this.m_isNull) {
            return null;
        }
        try {
            switch (this.m_schema.getProColumnType(n)) {
                case 1:
                case 19: {
                    return this.m_toJava.getProString();
                }
                case 4: {
                    return new Integer(this.m_toJava.getProInt());
                }
                case 41: {
                    return new Long(this.m_toJava.getProInt64());
                }
                case 7:
                case 10:
                case 14: {
                    return new Long(this.m_toJava.getProInt64());
                }
                case 3: {
                    return new Boolean(this.m_toJava.getProBoolean());
                }
                case 2: {
                    return this.m_toJava.getProDate();
                }
                case 34: {
                    return this.m_toJava.getProDateTime();
                }
                case 40: {
                    return this.m_toJava.getProDateTimeTz();
                }
                case 5: {
                    return this.m_toJava.getProDecimal(null);
                }
                case 8:
                case 13:
                case 18: {
                    return this.m_toJava.getProRaw();
                }
                default: {
                    throw new ProDataException();
                }
            }
        }
        catch (ClientException ex) {
            if (this.m_session != null) {
                this.m_session.handleOutputErrors();
            }
            ExceptionConverter.convertToProDataException(ex);
            return null;
        }
    }
    
    public static ProDataException getProDataException(final long n) {
        return new ProDataException(n, null);
    }
    
    public static ProDataException getProDataException(final long n, final String s) {
        return new ProDataException(n, new Object[] { s });
    }
    
    public static ProDataException getProDataException(final long n, final Object[] array) {
        return new ProDataException(n, array);
    }
    
    static {
        O4GLProDataObjectList.DELETE = new Integer(1);
        O4GLProDataObjectList.CHANGE = new Integer(2);
        O4GLProDataObjectList.ADD = new Integer(3);
        O4GLProDataObjectList.NOCHANGE = new Integer(4);
    }
}
