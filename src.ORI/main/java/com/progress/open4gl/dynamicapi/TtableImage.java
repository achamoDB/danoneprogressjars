// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.Vector;
import java.io.Externalizable;
import com.progress.open4gl.SDOModificationException;
import com.progress.open4gl.OutputSetException;
import com.progress.open4gl.ProSQLException;
import java.sql.ResultSet;
import java.util.Enumeration;
import com.progress.open4gl.RunTimeProperties;
import java.io.Serializable;

class TtableImage implements Serializable
{
    private int cursorPosition;
    private boolean onInsertRow;
    private ResultSetMetaData outputMetaData;
    private RowCollection rowCollection;
    private TtabRow currentRow;
    private TtabRow saveCurrent;
    private SDOBatchSet batchSet;
    private String highWaterRowId;
    private boolean dontKeepCacheRows;
    private Tracer tracer;
    
    private static int CALC_POSITION(final int n, final int n2) {
        return n2 - n + 1;
    }
    
    void emptyOut() {
        this.cursorPosition = 0;
        this.rowCollection = new RowCollection();
        this.currentRow = null;
        this.saveCurrent = null;
        this.batchSet = null;
        this.highWaterRowId = null;
    }
    
    TtableImage(final ResultSetMetaData outputMetaData, final boolean dontKeepCacheRows) {
        this.cursorPosition = 0;
        this.onInsertRow = false;
        this.tracer = RunTimeProperties.tracer;
        this.outputMetaData = outputMetaData;
        this.dontKeepCacheRows = dontKeepCacheRows;
    }
    
    private int getFirstRowNum() {
        if (this.rowCollection == null) {
            return -1;
        }
        return this.rowCollection.getFirstRowNum();
    }
    
    private Enumeration getRows() {
        return this.rowCollection.getRows();
    }
    
    void startBatch() {
        this.batchSet = new SDOBatchSet();
    }
    
    boolean inBatch() {
        return this.batchSet != null;
    }
    
    void endBatch() {
        this.batchSet = null;
    }
    
    ResultSet getBatch() {
        return this.batchSet;
    }
    
    int findPosition(final String s) throws ProSQLException {
        return this.rowCollection.getRow(s);
    }
    
    void reposition(final int cursorPosition) throws ProSQLException {
        this.undoUpdates();
        final int cursorPosition2 = this.cursorPosition;
        final TtabRow currentRow = this.currentRow;
        this.cursorPosition = cursorPosition;
        this.currentRow = this.rowCollection.getRow(this.cursorPosition - 1);
        this.onInsertRow = false;
        if (this.dontKeepCacheRows && cursorPosition2 >= 1) {
            this.rowCollection.replaceRow(new TtabRow(currentRow.getRowNum(), currentRow.getRowIdentity(), true), cursorPosition2 - 1);
        }
    }
    
    void repositionOnInsert() throws ProSQLException {
        if (!this.onInsertRow) {
            this.undoUpdates();
            this.saveCurrent = this.currentRow;
        }
        this.currentRow = new TtabRow(this.outputMetaData, false);
        this.onInsertRow = true;
    }
    
    void repositionOnCurrent() throws ProSQLException {
        this.currentRow = this.saveCurrent;
        this.onInsertRow = false;
    }
    
    void repositionBefore() throws ProSQLException {
        this.undoUpdates();
        this.cursorPosition = 0;
        this.currentRow = null;
        this.onInsertRow = false;
    }
    
    void repositionAfter() throws ProSQLException {
        this.undoUpdates();
        this.currentRow = null;
        this.cursorPosition = 0;
        this.onInsertRow = false;
    }
    
    Object getObject(final int n) {
        return this.currentRow.getValue(n);
    }
    
    boolean rowUpdated() {
        return this.currentRow.rowUpdated();
    }
    
    boolean rowDeleted(final boolean b) throws ProSQLException {
        return !this.currentRow.validateNotDeleted(b);
    }
    
    boolean rowInserted() {
        return this.currentRow.getRowStatus() == 6;
    }
    
    void updateObject(final int n, final Object o) throws ProSQLException {
        this.currentRow.setValue(n, o);
    }
    
    void deleteRow() throws ProSQLException {
        this.currentRow.deleteRow();
    }
    
    int rePopulateTable(final com.progress.open4gl.dynamicapi.ResultSet set, final int n, final int n2, final int n3) throws ProSQLException {
        this.rowCollection = new RowCollection();
        int n4 = 0;
        int n5 = n;
        TtabRow ttabRow = null;
        while (set.next()) {
            ++n4;
            ttabRow = new TtabRow(set, false, n5++);
            this.rowCollection.addRow(ttabRow);
        }
        if (ttabRow != null) {
            this.highWaterRowId = ttabRow.getRowIdentity();
        }
        else {
            this.highWaterRowId = null;
        }
        this.repositionBefore();
        return n4;
    }
    
    boolean appendRows(final TtableImage ttableImage) {
        boolean b = false;
        final Enumeration rows = ttableImage.getRows();
        TtabRow ttabRow = null;
        while (rows.hasMoreElements()) {
            ttabRow = rows.nextElement();
            ttabRow.getRowNum();
            if (this.rowCollection.addRow(ttabRow)) {
                b = true;
            }
        }
        if (ttabRow != null) {
            this.highWaterRowId = ttabRow.getRowIdentity();
        }
        return b;
    }
    
    String getHighestRowid() {
        return this.highWaterRowId;
    }
    
    void replaceCurrentRow(final TtableImage ttableImage) {
        if (this.replaceRows(ttableImage.getRows(), true) == 0) {
            final int firstRowNum = this.getFirstRowNum();
            final int rowNum = this.currentRow.getRowNum();
            this.replaceRow(new TtabRow(rowNum, this.currentRow.getRowIdentity(), false), false, CALC_POSITION(firstRowNum, rowNum), false);
        }
    }
    
    void cancelBatch() throws ProSQLException {
        if (this.batchSet == null) {
            return;
        }
        if (this.onInsertRow && this.currentRow.insertedInThisBatc(this.batchSet)) {
            this.currentRow = new TtabRow(this.outputMetaData, false);
        }
        this.replaceRows(this.batchSet.createImageSet(true), false);
        this.endBatch();
    }
    
    boolean isRowUpdated(final int n) {
        return this.batchSet != null && this.batchSet.isRowUpdated(n);
    }
    
    private int replaceRows(final Enumeration enumeration, final boolean b) {
        int n = 0;
        final int firstRowNum = this.getFirstRowNum();
        while (enumeration.hasMoreElements()) {
            final TtabRow ttabRow = enumeration.nextElement();
            this.replaceRow(ttabRow, false, CALC_POSITION(firstRowNum, ttabRow.getRowNum()), b);
            ++n;
            if (b) {
                break;
            }
        }
        return n;
    }
    
    ResultSet prepareRowToSend() {
        return new SDOUpdateSet(this.currentRow);
    }
    
    void afterSend(final com.progress.open4gl.dynamicapi.ResultSet set, final boolean b) throws ProSQLException {
        if (b) {
            set.close();
            return;
        }
        final int firstRowNum = this.getFirstRowNum();
        while (set.next()) {
            final TtabRow ttabRow = new TtabRow(set, true, -1);
            final String rowIdentity = ttabRow.getRowIdentity();
            final int row = this.rowCollection.getRow(rowIdentity);
            ttabRow.setRowNum(row);
            final int statusFromMod = TtabRow.statusFromMod(ttabRow.getRowModValue());
            this.tracer.print("SDO.Image: in afterSend() mode: " + statusFromMod, 3);
            switch (statusFromMod) {
                case 2: {
                    this.replaceRow(new TtabRow(row, rowIdentity, false), false, CALC_POSITION(firstRowNum, row), false);
                    continue;
                }
                case 1: {
                    this.replaceRow(ttabRow, true, row, false);
                    continue;
                }
                case 3: {
                    this.replaceRow(ttabRow, false, CALC_POSITION(firstRowNum, row), false);
                    ttabRow.makeOriginal();
                    continue;
                }
            }
        }
    }
    
    void doSendError(final String s, final com.progress.open4gl.dynamicapi.ResultSet set, final int n) throws ProSQLException {
        set.close();
        if (n != -1) {
            this.currentRow.reTentativeValues(n);
        }
        throw new SDOModificationException(new OutputSetException(7665970990714725172L, new Object[] { s }), "S1000", s);
    }
    
    private TtabRow replaceRow(TtabRow ttabRow, final boolean b, final int n, final boolean b2) {
        TtabRow ttabRow2 = null;
        if (!b) {
            final String rowIdentity = ttabRow.getRowIdentity();
            ttabRow2 = this.rowCollection.replaceRow(ttabRow, n - 1);
            if (b2 && !rowIdentity.equals(ttabRow2.getRowIdentity())) {
                ttabRow = new TtabRow(ttabRow.getRowNum(), ttabRow2.getRowIdentity(), false);
                this.rowCollection.replaceRow(ttabRow, n - 1);
            }
            if (n == this.cursorPosition && !this.onInsertRow) {
                this.currentRow = ttabRow;
            }
        }
        else if (this.onInsertRow) {
            ttabRow2 = this.currentRow;
            this.currentRow = ttabRow;
        }
        return ttabRow2;
    }
    
    void undoUpdates() throws ProSQLException {
        if (this.currentRow != null) {
            this.currentRow.commitNewValues(false);
        }
    }
    
    void putRowInBatch() throws ProSQLException {
        final int firstRowNum = this.getFirstRowNum();
        final int rowNum = this.currentRow.getRowNum();
        final TtabRow beforeAfterImageCopy = this.currentRow.beforeAfterImageCopy(false);
        if (this.currentRow.getRowStatus() == 1) {
            beforeAfterImageCopy.setBatch(this.batchSet);
            this.batchSet.insertRow(this.replaceRow(beforeAfterImageCopy, true, rowNum, false));
        }
        else {
            this.batchSet.updateRow(this.replaceRow(beforeAfterImageCopy, false, CALC_POSITION(firstRowNum, rowNum), false));
        }
    }
    
    boolean commitUpdates() throws ProSQLException {
        return this.currentRow.commitNewValues(true);
    }
    
    int getRowNum() {
        if (this.currentRow != null) {
            return this.currentRow.getRowNum();
        }
        return -1;
    }
    
    String getRowIdentity() {
        if (this.currentRow != null) {
            return this.currentRow.getRowIdentity();
        }
        return null;
    }
    
    public static class RowIdIndex extends ExternalHashtable implements Externalizable
    {
        boolean add(final String s, final int value) {
            return s != null && s.length() != 0 && this.put(s, new Integer(value)) != null;
        }
        
        void replace(final String s, final String s2, final int n) {
            final String s3 = (s != null && s.length() == 0) ? null : s;
            final String anObject = (s2 != null && s2.length() == 0) ? null : s2;
            if (s3 == null && anObject == null) {
                return;
            }
            if (s3 == null) {
                this.add(anObject, n);
            }
            else if (anObject == null) {
                this.remove(s3);
            }
            else {
                if (s3.equals(anObject)) {
                    return;
                }
                this.remove(s3);
                this.add(anObject, n);
            }
        }
    }
    
    static class RowCollection extends Vector implements Serializable
    {
        private RowIdIndex idIndex;
        
        RowCollection() {
            this.idIndex = new RowIdIndex();
        }
        
        boolean addRow(final TtabRow obj) {
            this.addElement(obj);
            return this.idIndex.add(obj.getRowIdentity(), obj.getRowNum());
        }
        
        int getFirstRowNum() {
            if (this.isEmpty()) {
                return -1;
            }
            return this.elementAt(0).getRowNum();
        }
        
        Enumeration getRows() {
            return this.elements();
        }
        
        TtabRow getRow(final int index) {
            return this.elementAt(index);
        }
        
        int getRow(final String s) {
            final Integer n = (Integer)this.idIndex.get(s);
            if (n == null) {
                return -1;
            }
            return n;
        }
        
        TtabRow replaceRow(final TtabRow obj, final int n) {
            final TtabRow ttabRow = this.elementAt(n);
            if (ttabRow.isPlaceHolder()) {
                return ttabRow;
            }
            this.setElementAt(obj, n);
            this.idIndex.replace(ttabRow.getRowIdentity(), obj.getRowIdentity(), obj.getRowNum());
            return ttabRow;
        }
    }
}
