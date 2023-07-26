// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.Vector;
import java.util.Enumeration;
import com.progress.open4gl.DeletedRowAccessException;
import com.progress.open4gl.OutputSetException;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.ProSQLException;
import java.util.GregorianCalendar;
import com.progress.open4gl.RunTimeProperties;
import java.io.Serializable;

class TtabRow implements Serializable
{
    static final String ROW_ADDED = "A";
    static final String ROW_DELETED = "D";
    static final String ROW_UPDATED = "U";
    static final String ROW_HOLE = "H";
    static final String ROW_ORIGINAL = "";
    static final int ROW_STATUS_ADDED = 1;
    static final int ROW_STATUS_DELETED = 2;
    static final int ROW_STATUS_UPDATED = 3;
    static final int ROW_STATUS_HOLE = 4;
    static final int ROW_STATUS_ORIGINAL = 5;
    static final int ROW_STATUS_ADDED_INDB = 6;
    private int rowStatus;
    private boolean unCommittedUpdates;
    private RowValues values;
    private ResultSetMetaData rowMetaData;
    private int columnCount;
    private ExternalHashtable changedFieldsList;
    private boolean schemaType;
    private int changedFieldsIndex;
    private int rowModIndex;
    private int rowIdentIndex;
    private int rowNum;
    private Object rowInsertBatch;
    private String rowIdOfAHole;
    private boolean placeHolderHole;
    private Tracer tracer;
    
    static int statusFromMod(final String s) {
        if (s.equals("A")) {
            return 1;
        }
        if (s.equals("D")) {
            return 2;
        }
        if (s.equals("U")) {
            return 3;
        }
        if (s.equals("H")) {
            return 4;
        }
        if (s.equals("")) {
            return 5;
        }
        return 0;
    }
    
    TtabRow(final ResultSet set, final boolean schemaType, final int rowNum) throws ProSQLException {
        this.tracer = RunTimeProperties.tracer;
        this.rowNum = rowNum;
        this.placeHolderHole = false;
        this.schemaType = schemaType;
        this.values = new RowValues();
        this.changedFieldsList = new ExternalHashtable();
        this.rowMetaData = (ResultSetMetaData)set.getMetaData();
        this.setSpecialIndexes(this.columnCount = this.rowMetaData.getColumnCount() + (this.schemaType ? 0 : 1));
        for (int i = 1; i < this.columnCount; ++i) {
            Object obj = null;
            switch (this.rowMetaData.getProColumnType(i)) {
                case 2:
                case 34:
                case 40: {
                    obj = set.getGregorianCalendar(i);
                    break;
                }
                case 18: {
                    obj = set.getBytes(i);
                    break;
                }
                case 19: {
                    obj = set.getString(i);
                    break;
                }
                default: {
                    obj = set.getObject(i);
                    break;
                }
            }
            this.values.addElement((GregorianCalendar)obj);
        }
        this.values.addElement(null);
        final String str = (String)this.getValue(this.rowModIndex);
        if (str != null && str.equals("A")) {
            this.rowStatus = 6;
        }
        else {
            this.rowStatus = 5;
        }
        this.unCommittedUpdates = false;
        this.tracer.print("SDO.Row: new TtabRow rowNum: " + this.rowNum + " rowMod: " + str, 4);
    }
    
    TtabRow(final ResultSetMetaData rowMetaData, final boolean schemaType) throws ProSQLException {
        this.tracer = RunTimeProperties.tracer;
        this.schemaType = schemaType;
        this.placeHolderHole = false;
        this.values = new RowValues();
        this.changedFieldsList = new ExternalHashtable();
        this.rowMetaData = rowMetaData;
        this.setSpecialIndexes(this.columnCount = this.rowMetaData.getColumnCount() + (this.schemaType ? 0 : 1));
        for (int i = 1; i <= this.columnCount; ++i) {
            this.values.addElement(new ColumnValue(null));
        }
        this.rowStatus = 1;
        this.values.setElementAt("A", this.rowModIndex - 1);
        this.values.setElementAt("", this.rowIdentIndex - 1);
        this.unCommittedUpdates = true;
        this.rowNum = -1;
        this.tracer.print("SDO.Row: new empty TtabRow columnCount: " + this.columnCount, 4);
    }
    
    TtabRow beforeAfterImageCopy(final boolean b) throws ProSQLException {
        if (!b && this.rowStatus == 2) {
            return new TtabRow(this.rowNum, this.getRowIdentity(), false);
        }
        final TtabRow ttabRow = new TtabRow(this.rowMetaData, this.schemaType);
        ttabRow.setRowNum(this.rowNum);
        ttabRow.setRowStatus(this.rowStatus);
        for (int i = 1; i <= this.columnCount; ++i) {
            Object o;
            if (b) {
                o = this.getOldValue(i);
            }
            else {
                o = this.getValue(i);
            }
            ttabRow.setOriginalValue(i, o);
        }
        ttabRow.makeOriginal();
        return ttabRow;
    }
    
    boolean insertedInThisBatc(final Object o) {
        return o == this.rowInsertBatch && this.rowStatus == 6;
    }
    
    void setBatch(final Object rowInsertBatch) {
        this.rowInsertBatch = rowInsertBatch;
    }
    
    void mergeNewRow(final TtabRow ttabRow) throws ProSQLException {
        if (ttabRow.getRowStatus() == 2) {
            this.doDeleteRow();
        }
        else {
            for (int i = 1; i <= this.columnCount; ++i) {
                this.setNewValue(i, ttabRow.getValue(i));
            }
        }
        this.mergeChagenFields(ttabRow.changedFieldsList);
        this.setChangedFieldsList();
    }
    
    void makeOriginal() {
        this.tracer.print("SDO.Row:makeOriginal(): " + this.rowNum, 4);
        this.changedFieldsList = new ExternalHashtable();
        this.values.setElementAt("", this.rowModIndex - 1);
        if (this.rowStatus == 1) {
            this.rowStatus = 6;
        }
        else {
            this.rowStatus = 5;
        }
        this.unCommittedUpdates = false;
    }
    
    TtabRow(final int rowNum, final String rowIdOfAHole, final boolean placeHolderHole) {
        (this.tracer = RunTimeProperties.tracer).print("SDO.Row: new HOLE row. ", 4);
        this.rowStatus = 4;
        this.rowNum = rowNum;
        this.rowIdOfAHole = rowIdOfAHole;
        this.placeHolderHole = placeHolderHole;
    }
    
    boolean isPlaceHolder() {
        return this.placeHolderHole;
    }
    
    String getRowModValue() {
        if (!this.validateNotHole()) {
            return null;
        }
        return (String)this.getValue(this.rowModIndex);
    }
    
    boolean rowUpdated() {
        return this.unCommittedUpdates;
    }
    
    int getColumnCount() {
        if (!this.validateNotHole()) {
            return 0;
        }
        return this.columnCount;
    }
    
    Object getValue(final int n) {
        Object o = this.values.elementAt(n - 1);
        if (o instanceof ColumnValue) {
            o = ((ColumnValue)o).getRecentValue();
        }
        return o;
    }
    
    Object getOldValue(final int n) {
        final ColumnValue element = this.values.elementAt(n - 1);
        if (element instanceof ColumnValue) {
            return element.getOldValue();
        }
        return element;
    }
    
    private void setOriginalValue(final int n, final Object obj) {
        this.values.setElementAt(obj, n - 1);
    }
    
    private void setNewValue(final int n, final Object o) throws ProSQLException {
        final ColumnValue element = this.values.elementAt(n - 1);
        if (element instanceof ColumnValue) {
            element.updDefinate(o);
        }
        else {
            this.values.setElementAt(new ColumnValue(element, o), n - 1);
        }
    }
    
    void setValue(final int i, final Object obj) throws ProSQLException {
        this.validateNotHole(true);
        try {
            JdbcDataType.validateInputValue(obj, this.rowMetaData.getProColumnType(i));
        }
        catch (ClientException ex) {
            throw new ProSQLException(ex.getMessage());
        }
        final ColumnValue element = this.values.elementAt(i - 1);
        if (this.rowStatus == 6) {
            throw ResultSet.getProSQLException(7665970990714725133L);
        }
        if (RunTimeProperties.isTracing()) {
            this.tracer.print("SDO.Row: setValue in column: " + i + " newValue: " + obj + " oldValue: " + this.getValue(i), 4);
        }
        if (element instanceof ColumnValue) {
            element.updTentative(obj);
        }
        else {
            this.values.setElementAt(new ColumnValue(element, obj), i - 1);
        }
        this.unCommittedUpdates = true;
    }
    
    void reTentativeValues(final int i) {
        this.tracer.print("SDO.Row: reTentativeValues() mode: " + i, 4);
        switch (i) {
            case 1: {
                break;
            }
            case 3: {
                this.rowStatus = 5;
                break;
            }
            case 2: {
                this.unDeleteRow();
                return;
            }
            default: {
                throw new Open4GLError();
            }
        }
        for (int index = 0; index + 1 < this.columnCount; ++index) {
            final Object element = this.values.elementAt(index);
            if (element instanceof ColumnValue && ((ColumnValue)element).newVal()) {
                this.unCommittedUpdates = true;
                ((ColumnValue)element).reTentative();
            }
        }
    }
    
    boolean commitNewValues(final boolean b) throws ProSQLException {
        if (b && this.rowStatus == 6) {
            throw ResultSet.getProSQLException(7665970990714725133L);
        }
        if (!this.validateNotHole()) {
            return false;
        }
        if (!this.unCommittedUpdates) {
            this.tracer.print("SDO.Row: commitNewValues() -  no updates " + this.rowNum, 4);
            return false;
        }
        if (this.rowStatus == 5 && b) {
            this.rowStatus = 3;
            this.values.setElementAt(new ColumnValue("", "U"), this.rowModIndex - 1);
        }
        for (int index = 0; index + 1 < this.columnCount; ++index) {
            final Object element = this.values.elementAt(index);
            if (element instanceof ColumnValue) {
                if (b) {
                    if (((ColumnValue)element).tentativeVal()) {
                        this.addFieldName(index + 1);
                    }
                    ((ColumnValue)element).commitUpdate();
                }
                else {
                    ((ColumnValue)element).undoUpdate();
                }
            }
        }
        this.setChangedFieldsList();
        this.unCommittedUpdates = false;
        this.tracer.print("SDO.Row: commitNewValues() -  has updates " + this.rowNum, 4);
        return true;
    }
    
    private void setChangedFieldsList() {
        final String changedFieldsList = this.createChangedFieldsList(this.changedFieldsList);
        ColumnValue obj;
        if (this.rowStatus == 3) {
            obj = new ColumnValue(changedFieldsList, "", true);
        }
        else {
            obj = new ColumnValue(changedFieldsList);
        }
        this.values.setElementAt(obj, this.changedFieldsIndex - 1);
    }
    
    void deleteRow() throws ProSQLException {
        this.validateNotHole(true);
        this.doDeleteRow();
        this.setChangedFieldsList();
        this.tracer.print("SDO.Row: deleteRow () " + this.rowNum, 4);
    }
    
    private void doDeleteRow() throws ProSQLException {
        this.rowStatus = 2;
        this.values.setElementAt("D", this.rowModIndex - 1);
    }
    
    private void unDeleteRow() {
        this.rowStatus = 5;
        this.values.setElementAt("", this.rowModIndex - 1);
    }
    
    String getRowIdentity() {
        if (this.validateNotHole()) {
            return (String)this.getValue(this.rowIdentIndex);
        }
        return this.rowIdOfAHole;
    }
    
    void setRowNum(final int rowNum) {
        this.rowNum = rowNum;
    }
    
    private void setRowStatus(final int rowStatus) {
        this.rowStatus = rowStatus;
    }
    
    int getRowNum() {
        return this.rowNum;
    }
    
    int getRowStatus() {
        return this.rowStatus;
    }
    
    boolean validateNotDeleted(final boolean b) throws DeletedRowAccessException {
        if (this.rowStatus != 4 && this.rowStatus != 2) {
            return true;
        }
        if (b) {
            throw new DeletedRowAccessException(new OutputSetException(7665970990714725134L, null), "S1000");
        }
        return false;
    }
    
    private boolean validateNotHole(final boolean b) throws DeletedRowAccessException {
        if (this.rowStatus != 4) {
            return true;
        }
        if (b) {
            throw new DeletedRowAccessException(new OutputSetException(7665970990714725134L, null), "S1000");
        }
        return false;
    }
    
    private boolean validateNotHole() {
        return this.rowStatus != 4;
    }
    
    private void setSpecialIndexes(final int changedFieldsIndex) {
        this.changedFieldsIndex = changedFieldsIndex;
        this.rowModIndex = changedFieldsIndex - 3;
        this.rowIdentIndex = changedFieldsIndex - 4;
    }
    
    private String createChangedFieldsList(final ExternalHashtable externalHashtable) {
        final Enumeration keys = externalHashtable.keys();
        String string = null;
        while (keys.hasMoreElements()) {
            final String str = keys.nextElement();
            if (string == null) {
                string = str;
            }
            else {
                string = string + "," + str;
            }
        }
        this.tracer.print("SDO.Row: createChangedFieldsList() " + this.rowNum + " " + string, 4);
        return string;
    }
    
    private void addFieldName(final int n) throws ProSQLException {
        if (this.rowStatus == 3 || this.rowStatus == 1) {
            String columnName;
            if (n == this.changedFieldsIndex) {
                columnName = "ChangedFields";
            }
            else {
                columnName = this.rowMetaData.getColumnName(n);
            }
            this.changedFieldsList.put(columnName, "");
        }
    }
    
    private void mergeChagenFields(final ExternalHashtable externalHashtable) {
        final Enumeration keys = externalHashtable.keys();
        while (keys.hasMoreElements()) {
            this.changedFieldsList.put(keys.nextElement(), "");
        }
    }
    
    static class RowValues extends Vector implements Serializable
    {
    }
    
    static class ColumnValue implements Serializable
    {
        private Object oldValue;
        private Object newValue;
        private Object tentativeValue;
        private boolean isTentative;
        private boolean isNew;
        
        ColumnValue() {
            this(null, null);
        }
        
        ColumnValue(final Object oldValue, final Object tentativeValue) {
            this.isTentative = true;
            this.isNew = false;
            this.tentativeValue = tentativeValue;
            this.oldValue = oldValue;
        }
        
        ColumnValue(final Object newValue) {
            this.isTentative = false;
            this.isNew = true;
            this.newValue = newValue;
            this.oldValue = null;
        }
        
        ColumnValue(final Object oldValue, final Object newValue, final boolean b) {
            if (!b) {
                return;
            }
            this.isTentative = false;
            this.isNew = true;
            this.newValue = newValue;
            this.oldValue = oldValue;
        }
        
        boolean tentativeVal() {
            return this.isTentative;
        }
        
        boolean newVal() {
            return this.isNew;
        }
        
        void updTentative(final Object tentativeValue) {
            this.isTentative = true;
            this.tentativeValue = tentativeValue;
        }
        
        void updDefinate(final Object newValue) {
            this.newValue = newValue;
        }
        
        void commitUpdate() {
            if (this.isTentative) {
                this.newValue = this.tentativeValue;
                this.isTentative = false;
                this.isNew = true;
            }
        }
        
        void reTentative() {
            if (this.isNew) {
                this.tentativeValue = this.newValue;
                this.newValue = null;
                this.isTentative = true;
                this.isNew = false;
            }
        }
        
        void undoUpdate() {
            this.isTentative = false;
        }
        
        Object getRecentValue() {
            if (this.isTentative) {
                return this.tentativeValue;
            }
            if (this.isNew) {
                return this.newValue;
            }
            return this.oldValue;
        }
        
        Object getOldValue() {
            return this.oldValue;
        }
    }
}
