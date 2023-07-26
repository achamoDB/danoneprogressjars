// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Externalizable;
import java.util.StringTokenizer;
import com.progress.open4gl.javaproxy.SDOProcObject;
import java.util.Vector;
import java.net.URL;
import java.util.Calendar;
import java.sql.Array;
import java.sql.Ref;
import java.util.Map;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.Reader;
import com.progress.open4gl.IntHolder;
import java.sql.SQLWarning;
import java.sql.Time;
import java.io.InputStream;
import com.progress.open4gl.ProBlob;
import com.progress.open4gl.ProClob;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Timestamp;
import java.sql.Date;
import java.math.BigDecimal;
import com.progress.open4gl.StringHolder;
import com.progress.open4gl.ResultSetHolder;
import com.progress.open4gl.SDOInterface;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.SDOFactory;
import com.progress.open4gl.SDOScrollingMode;
import com.progress.open4gl.SDOParameters;
import java.io.Serializable;
import com.progress.open4gl.SDOResultSet;

public class SDOResultSetImpl implements SDOResultSet, Serializable
{
    private static final String SDO_COLUMN_PROPERTIES = "valexp,valmsg,table,width,mandatory,format,initial,label,datatype,readonly";
    private static final int SDO_NUM_COLUMN_PROPS = 10;
    static final int COL_PROP_VALEXP = 1;
    static final int COL_PROP_VALMSG = 2;
    static final int COL_PROP_TABLE = 3;
    static final int COL_PROP_WIDTH = 4;
    static final int COL_PROP_MANDATORY = 5;
    static final int COL_PROP_FORMAT = 6;
    static final int COL_PROP_INITIAL = 7;
    static final int COL_PROP_LABEL = 8;
    static final int COL_PROP_DATATYPE = 9;
    static final int COL_PROP_READONLY = 10;
    static final int OPEN_QUERY = 1;
    static final int ADD_ROWS = 2;
    static final int REFRESH_ROW = 3;
    static final int OPEN_QUERY_LIMIT_ROWS = 4;
    private int sizeOfBatch;
    private int limitFetchSize;
    private boolean statelessMode;
    private static final int STATE_UNINITIALIZED = 0;
    private static final int STATE_EMPTY = 1;
    private static final int STATE_NONEMPTY = 2;
    private int resultSetState;
    private TtableImageHolder currentSubset;
    private int highWaterMark;
    private boolean finalHighMark;
    private SDOResultSetMetaDataImpl schema;
    private int cursorPosition;
    private boolean beforeFirst;
    private boolean afterLast;
    private boolean onInsertRow;
    private boolean wasNullIndicator;
    private Tracer tracer;
    private SDOParameters sdoParameters;
    private SDOScrollingMode scrollingMode;
    private SdoProc sdoProc;
    private StateChecker stateChecker;
    private String initialRowIdPosition;
    private boolean closed;
    
    public SDOResultSetImpl(final SDOFactory sdoFactory, final String s, final String s2, final String s3, final SDOParameters sdoParameters) throws Open4GLException, ProSQLException {
        this.tracer = RunTimeProperties.tracer;
        this.closed = false;
        this.sdoParameters = sdoParameters;
        this.sizeOfBatch = 0;
        if (this.sdoParameters != null) {
            this.statelessMode = this.sdoParameters.getStateless();
            this.scrollingMode = this.sdoParameters.getScrollingMode();
            this.initialRowIdPosition = this.sdoParameters.getRowIdentity();
            if (this.statelessMode && this.scrollingMode.getMode() != 3) {
                if (this.sdoParameters.scrollingModeWasSet()) {
                    throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725111L, "S1000", SDOScrollingMode.getModeName(3));
                }
                this.scrollingMode = SDOScrollingMode.PREFETCH;
                this.sdoParameters.setScrollingMode(SDOScrollingMode.PREFETCH);
            }
        }
        else {
            this.initialRowIdPosition = null;
            this.statelessMode = false;
            this.scrollingMode = SDOScrollingMode.KEEP_ROWS;
        }
        this.stateChecker = new StateChecker(new Boolean(this.statelessMode));
        if (this.sdoParameters != null) {
            this.limitFetchSize = this.sdoParameters.getPrefetchMaxRows();
        }
        this.tracer.print("SDO.setup: mode: " + this.scrollingMode.getModeName());
        switch (this.scrollingMode.getMode()) {
            case 3: {
                this.sizeOfBatch = 100000000;
                break;
            }
            case 1:
            case 2: {
                this.limitFetchSize = -1;
                break;
            }
            default: {
                throw new Open4GLError();
            }
        }
        if (this.sdoParameters != null && this.sizeOfBatch == 0) {
            this.sizeOfBatch = this.sdoParameters.getFetchSize();
        }
        else if (this.sizeOfBatch == 0) {
            this.sizeOfBatch = 200;
        }
        this.tracer.print("SDO.setup: Fetch size: " + new Integer(this.sizeOfBatch).toString());
        try {
            this.sdoProc = new SdoProc(sdoFactory, s, s2, s3, this.statelessMode);
            this.openQuery(true, this.initialRowIdPosition);
        }
        finally {
            if (this.sdoProc != null && this.statelessMode) {
                this.sdoProc.unPopulateProc();
            }
        }
    }
    
    public boolean isAttached() {
        return this.sdoProc.isConnected();
    }
    
    public void detachFromAppObj() throws ProSQLException {
        this.validateOpen();
        if (!this.statelessMode) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725112L);
        }
        this.sdoProc.disconnect();
    }
    
    public void attachToAppObj(final SDOFactory sdoFactory) throws ProSQLException, Open4GLException {
        this.validateOpen();
        this.sdoProc.reConnect(sdoFactory);
    }
    
    public SDOInterface getSDOInterface() throws ProSQLException {
        this.validateOpen();
        try {
            return this.sdoProc.getSDOInterface();
        }
        catch (Open4GLException ex) {
            throw new ProSQLException(ex.getMessage());
        }
    }
    
    public void releaseSDOInterface() throws ProSQLException {
        this.validateOpen();
        if (this.sdoProc != null && this.statelessMode) {
            this.sdoProc.unPopulateProc();
        }
    }
    
    private void openQuery(final boolean b, final String s) throws ProSQLException, Open4GLException {
        this.resultSetState = 0;
        this.cursorPosition = 0;
        this.finalHighMark = false;
        final boolean beforeFirst = false;
        this.onInsertRow = beforeFirst;
        this.afterLast = beforeFirst;
        this.beforeFirst = beforeFirst;
        this.highWaterMark = 0;
        this.wasNullIndicator = false;
        if (!b && !this.sdoProc.getSDOInterface().openQuery()) {
            this.silentClose();
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725113L);
        }
        final TtableImageHolder ttableImageHolder = new TtableImageHolder();
        this.getSubset(this.sdoProc, s, 1, (this.limitFetchSize != -1) ? this.limitFetchSize : this.sizeOfBatch, ttableImageHolder, (this.limitFetchSize != -1) ? 4 : 1);
        this.replaceCurrentSubset(ttableImageHolder);
        this.repositionBefore();
        if (s != null && !this.checkRowidPosition(s)) {
            this.replaceCurrentSubset(ttableImageHolder.emptyOut());
        }
    }
    
    public void startBatch() throws ProSQLException {
        this.tracer.print("SDO.call: startBatch");
        this.validateOpen();
        if (this.inBatch()) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725114L);
        }
        this.currentSubset.ttableImage.startBatch();
    }
    
    public void cancelBatch() throws ProSQLException {
        this.tracer.print("SDO.call: cancelBatch");
        if (!this.inBatch()) {
            return;
        }
        this.currentSubset.ttableImage.cancelBatch();
    }
    
    public boolean inBatch() {
        this.tracer.print("SDO.call: inBatch");
        return this.currentSubset != null && this.currentSubset.ttableImage != null && this.currentSubset.ttableImage.inBatch();
    }
    
    public void sendBatchAndReOpen() throws ProSQLException {
        this.tracer.print("SDO.call: sendBatchAndReOpen()");
        this.validateOpen();
        try {
            this.sendBatch0(true);
            this.reOpenQuery0(null);
        }
        finally {
            if (this.sdoProc != null && this.statelessMode) {
                this.sdoProc.unPopulateProc();
            }
        }
    }
    
    public void sendBatchAndReOpen(final String s) throws ProSQLException {
        this.tracer.print("SDO.call: sendBatchAndReOpen()");
        this.validateOpen();
        try {
            this.sendBatch0(true);
            this.reOpenQuery0(s);
        }
        finally {
            if (this.sdoProc != null && this.statelessMode) {
                this.sdoProc.unPopulateProc();
            }
        }
    }
    
    public void sendBatch() throws ProSQLException {
        this.tracer.print("SDO.call: sendBatch");
        this.validateOpen();
        try {
            this.sendBatch0(false);
        }
        finally {
            if (this.sdoProc != null && this.statelessMode) {
                this.sdoProc.unPopulateProc();
            }
        }
    }
    
    private boolean checkRowidPosition(final String s) throws ProSQLException {
        if (this.resultSetState == 1) {
            return false;
        }
        this.next();
        final String rowIdentity = this.getRowIdentity();
        this.previous();
        return s.equals(rowIdentity);
    }
    
    private void sendBatch0(final boolean b) throws ProSQLException {
        if (!this.inBatch()) {
            return;
        }
        final ResultSetHolder resultSetHolder = new ResultSetHolder();
        final StringHolder stringHolder = new StringHolder();
        final StringHolder stringHolder2 = new StringHolder();
        resultSetHolder.setResultSetValue(this.currentSubset.ttableImage.getBatch());
        try {
            this.sdoProc.getSDOInterface().serverCommit(resultSetHolder, stringHolder, stringHolder2);
        }
        catch (Open4GLException ex) {
            this.silentClose();
            throw new ProSQLException(ex.getMessage());
        }
        final String stringValue = stringHolder.getStringValue();
        if (stringValue != null && stringValue.length() > 0) {
            this.cancelBatch();
            this.currentSubset.ttableImage.doSendError(stringValue, (com.progress.open4gl.dynamicapi.ResultSet)resultSetHolder.getResultSetValue(), -1);
        }
        else {
            this.currentSubset.ttableImage.afterSend((com.progress.open4gl.dynamicapi.ResultSet)resultSetHolder.getResultSetValue(), b);
            this.tracer.print("SDO.Set: Successful sendBatch().", 3);
            this.currentSubset.ttableImage.endBatch();
        }
    }
    
    public void reOpenQuery() throws ProSQLException {
        this.tracer.print("SDO.call: reOpenQuery");
        this.validateOpen();
        this.reOpenQuery1(null);
    }
    
    public void reOpenQuery(final String str) throws ProSQLException {
        this.tracer.print("SDO.call: reOpenQuery rowId = " + str);
        this.validateOpen();
        this.reOpenQuery1(str);
    }
    
    private void reOpenQuery1(final String s) throws ProSQLException {
        this.cancelBatch();
        try {
            this.reOpenQuery0(s);
        }
        finally {
            if (this.sdoProc != null && this.statelessMode) {
                this.sdoProc.unPopulateProc();
            }
        }
    }
    
    private void reOpenQuery0(final String s) throws ProSQLException {
        try {
            this.sdoProc.getSDOInterface().closeQuery();
            this.openQuery(false, s);
        }
        catch (Open4GLException ex) {
            throw new ProSQLException(ex.getMessage());
        }
    }
    
    public String getQuery() throws ProSQLException {
        this.validateOpen();
        return this.sdoProc.getQuery();
    }
    
    public int getFetchSize() throws ProSQLException {
        return this.sizeOfBatch;
    }
    
    public int getRow() throws ProSQLException {
        this.tracer.print("SDO.call: getRow");
        this.validateOpen();
        if (this.resultSetState == 1 || this.cursorPosition == 0 || this.onInsertRow) {
            return 0;
        }
        return this.cursorPosition;
    }
    
    private void inStatelessBatch() throws ProSQLException {
        if (this.statelessMode && !this.inBatch()) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725115L);
        }
    }
    
    public void updateRow() throws ProSQLException {
        this.tracer.print("SDO.call: updateRow");
        this.validateOpen();
        this.inStatelessBatch();
        if (this.resultSetState == 1 || this.cursorPosition == 0) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725116L);
        }
        this.sendRowToServer(3);
    }
    
    public boolean rowDeleted() throws ProSQLException {
        this.tracer.print("SDO.call: rowDeleted");
        this.validateOpen();
        return !this.onInsertRow && this.resultSetState != 1 && this.cursorPosition != 0 && this.currentSubset.ttableImage.rowDeleted(false);
    }
    
    public boolean rowUpdated() throws ProSQLException {
        this.tracer.print("SDO.call: rowUpdated");
        this.validateOpen();
        return !this.onInsertRow && this.resultSetState != 1 && this.cursorPosition != 0 && this.currentSubset.ttableImage.rowUpdated();
    }
    
    public boolean rowInserted() throws ProSQLException {
        this.tracer.print("SDO.call: rowInserted");
        this.validateOpen();
        return this.onInsertRow && this.currentSubset.ttableImage.rowInserted();
    }
    
    public void deleteRow() throws ProSQLException {
        this.tracer.print("SDO.call: deleteRow");
        this.validateOpen();
        this.inStatelessBatch();
        if (this.onInsertRow) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725117L);
        }
        if (this.resultSetState == 1 || this.cursorPosition == 0) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725116L);
        }
        this.currentSubset.ttableImage.rowDeleted(true);
        this.currentSubset.ttableImage.deleteRow();
        this.sendRowToServer(2);
    }
    
    public void insertRow() throws ProSQLException {
        this.tracer.print("SDO.call: insertRow");
        this.validateOpen();
        this.inStatelessBatch();
        if (!this.onInsertRow) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725118L);
        }
        this.sendRowToServer(1);
    }
    
    public void cancelRowUpdates() throws ProSQLException {
        this.tracer.print("SDO.call: cancelRowUpdates");
        this.validateOpen();
        if ((this.resultSetState == 1 || this.cursorPosition == 0) && !this.onInsertRow) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725116L);
        }
        if (this.currentSubset.ttableImage.rowDeleted(false)) {
            return;
        }
        this.currentSubset.ttableImage.undoUpdates();
    }
    
    public void moveToInsertRow() throws ProSQLException {
        this.tracer.print("SDO.call: moveToInsertRow");
        this.validateOpen();
        this.inStatelessBatch();
        this.onInsertRow = true;
        this.currentSubset.ttableImage.repositionOnInsert();
    }
    
    public void moveToCurrentRow() throws ProSQLException {
        this.tracer.print("SDO.call: moveToCurrentRow");
        this.validateOpen();
        this.onInsertRow = false;
        this.currentSubset.ttableImage.repositionOnCurrent();
    }
    
    public boolean previous() throws ProSQLException {
        this.tracer.print("SDO.call: previous");
        this.validateOpen();
        this.onlyNextSupported();
        if (this.resultSetState == 1 || this.beforeFirst) {
            return false;
        }
        if (this.afterLast) {
            this.onInsertRow = false;
            return this.reposition(this.currentSubset.subsetStart + this.currentSubset.subsetNumRows - 1);
        }
        if (this.cursorPosition == 1) {
            this.onInsertRow = false;
            this.beforeFirst = true;
            this.cursorPosition = 0;
            return false;
        }
        this.onInsertRow = false;
        return this.reposition(this.cursorPosition - 1);
    }
    
    public boolean relative(final int n) throws ProSQLException {
        this.tracer.print("SDO.call: relative");
        this.validateOpen();
        this.onlyNextSupported();
        if (this.resultSetState == 1 || this.cursorPosition == 0) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725116L);
        }
        this.onInsertRow = false;
        return this.absoluteReposition(this.cursorPosition + n);
    }
    
    public boolean absolute(final String s) throws ProSQLException {
        this.tracer.print("SDO.call: absolute(rowId)");
        this.validateOpen();
        this.onlyNextSupported();
        if (s == null) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725119L);
        }
        if (this.resultSetState == 1) {
            return false;
        }
        this.onInsertRow = false;
        final int position = this.currentSubset.ttableImage.findPosition(s);
        if (position > 0) {
            return this.reposition(position);
        }
        if (this.isAfterLast()) {
            return false;
        }
        while (this.next()) {
            if (s.equals(this.getRowIdentity())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean absolute(final int n) throws ProSQLException {
        this.tracer.print("SDO.call: absolute(position)");
        this.validateOpen();
        this.onlyNextSupported();
        if (n == 0) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725120L);
        }
        if (this.resultSetState == 1) {
            return false;
        }
        this.onInsertRow = false;
        if (n > 0) {
            return this.absoluteReposition(n);
        }
        this.last();
        return this.relative(n + 1);
    }
    
    public boolean isBeforeFirst() throws ProSQLException {
        this.tracer.print("SDO.call: isBeforeFirst");
        this.validateOpen();
        return this.resultSetState != 1 && this.beforeFirst;
    }
    
    public boolean isAfterLast() throws ProSQLException {
        this.tracer.print("SDO.call: isAfterLast");
        this.validateOpen();
        return this.resultSetState != 1 && this.afterLast;
    }
    
    public void beforeFirst() throws ProSQLException {
        this.tracer.print("SDO.call: beforeFirst");
        this.validateOpen();
        this.onlyNextSupported();
        if (this.resultSetState == 1) {
            return;
        }
        this.onInsertRow = false;
        if (!this.absolute(1)) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725121L);
        }
        this.previous();
    }
    
    public boolean isFirst() throws ProSQLException {
        this.tracer.print("SDO.call: isFirst");
        this.validateOpen();
        return this.cursorPosition == 1;
    }
    
    public boolean first() throws ProSQLException {
        this.tracer.print("SDO.call: first");
        this.validateOpen();
        this.onlyNextSupported();
        if (this.resultSetState == 1) {
            return false;
        }
        this.onInsertRow = false;
        return this.absolute(1);
    }
    
    public void afterLast() throws ProSQLException {
        this.tracer.print("SDO.call: afterLast");
        this.validateOpen();
        this.onlyNextSupported();
        if (this.resultSetState == 1) {
            return;
        }
        this.onInsertRow = false;
        while (this.next()) {}
    }
    
    public boolean isLast() throws ProSQLException {
        this.tracer.print("SDO.call: isLast");
        this.validateOpen();
        return this.resultSetState != 1 && this.cursorPosition != 0 && this.cursorPosition >= this.currentSubset.subsetStart + this.currentSubset.subsetNumRows - 1 && (this.finalHighMark || this.addMoreRows(this.sizeOfBatch) <= 0);
    }
    
    public boolean last() throws ProSQLException {
        this.tracer.print("SDO.call: last");
        this.validateOpen();
        this.onlyNextSupported();
        if (this.resultSetState == 1) {
            return false;
        }
        this.onInsertRow = false;
        while (this.next()) {}
        return this.previous();
    }
    
    public void updateObject(final int n, final Object o) throws ProSQLException {
        this.tracer.print("SDO.call: updateObject");
        this.validateOpen();
        this.inStatelessBatch();
        this.validateColumn(n);
        this.currentSubset.ttableImage.rowDeleted(true);
        this.currentSubset.ttableImage.updateObject(n, o);
    }
    
    public void updateNull(final String s) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), null);
    }
    
    public void updateBoolean(final String s, final boolean value) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), new Boolean(value));
    }
    
    public void updateInt(final String s, final int value) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), new Integer(value));
    }
    
    public void updateLong(final String s, final long value) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), new Long(value));
    }
    
    public void updateDouble(final String s, final double value) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), new Double(value));
    }
    
    public void updateBigDecimal(final String s, final BigDecimal bigDecimal) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), bigDecimal);
    }
    
    public void updateString(final String s, final String s2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), s2);
    }
    
    public void updateBytes(final String s, final byte[] array) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), array);
    }
    
    public void updateDate(final String s, final Date date) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), date);
    }
    
    public void updateTimestamp(final String s, final Timestamp timestamp) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), timestamp);
    }
    
    public void updateBlob(final String s, final Blob blob) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), blob);
    }
    
    public void updateClob(final String s, final Clob clob) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), clob);
    }
    
    public void updateObject(final String s, final Object o) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), o);
    }
    
    public BigDecimal getBigDecimal(final int n) throws ProSQLException {
        return this.getBigDecimal(n, 0);
    }
    
    public BigDecimal getBigDecimal(final String s) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(s), 0);
    }
    
    public void refreshRow() throws ProSQLException {
        this.tracer.print("SDO.call: refreshRow");
        this.validateOpen();
        if (this.statelessMode) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725122L);
        }
        if (this.resultSetState == 1 || this.cursorPosition == 0 || this.onInsertRow) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725116L);
        }
        if (this.currentSubset.ttableImage.isRowUpdated(this.cursorPosition)) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725123L, "S1010", this.getRowIdentity());
        }
        SdoProc lightWeightProc;
        try {
            lightWeightProc = this.sdoProc.createLightWeightProc();
        }
        catch (Open4GLException ex) {
            throw new ProSQLException(ex.getMessage());
        }
        try {
            final TtableImageHolder ttableImageHolder = new TtableImageHolder();
            this.getSubset(lightWeightProc, this.getRowIdentity(), this.cursorPosition, 1, ttableImageHolder, 3);
            this.currentSubset.ttableImage.replaceCurrentRow(ttableImageHolder.ttableImage);
        }
        finally {
            lightWeightProc.unPopulateProc();
        }
    }
    
    public void updateNull(final int n) throws ProSQLException {
        this.updateObject(n, null);
    }
    
    public void updateBoolean(final int n, final boolean value) throws ProSQLException {
        this.updateObject(n, new Boolean(value));
    }
    
    public void updateInt(final int n, final int value) throws ProSQLException {
        this.updateObject(n, new Integer(value));
    }
    
    public void updateLong(final int n, final long value) throws ProSQLException {
        this.updateObject(n, new Long(value));
    }
    
    public void updateDouble(final int n, final double value) throws ProSQLException {
        this.updateObject(n, new Double(value));
    }
    
    public void updateBigDecimal(final int n, final BigDecimal bigDecimal) throws ProSQLException {
        this.updateObject(n, bigDecimal);
    }
    
    public void updateString(final int n, final String s) throws ProSQLException {
        this.updateObject(n, s);
    }
    
    public void updateBytes(final int n, final byte[] array) throws ProSQLException {
        this.updateObject(n, array);
    }
    
    public void updateDate(final int n, final Date date) throws ProSQLException {
        this.updateObject(n, date);
    }
    
    public void updateTimestamp(final int n, final Timestamp timestamp) throws ProSQLException {
        this.updateObject(n, timestamp);
    }
    
    public void updateBlob(final int n, final Blob blob) throws ProSQLException {
        this.updateObject(n, blob);
    }
    
    public void updateClob(final int n, final Clob clob) throws ProSQLException {
        this.updateObject(n, clob);
    }
    
    public byte[] getBytes(final String s) throws ProSQLException {
        return this.getBytes(this.schema.fieldToColumn(s));
    }
    
    public String getString(final String s) throws ProSQLException {
        return this.getString(this.schema.fieldToColumn(s));
    }
    
    public long getLong(final String s) throws ProSQLException {
        return this.getLong(this.schema.fieldToColumn(s));
    }
    
    public int getInt(final String s) throws ProSQLException {
        return this.getInt(this.schema.fieldToColumn(s));
    }
    
    public double getDouble(final String s) throws ProSQLException {
        return this.getDouble(this.schema.fieldToColumn(s));
    }
    
    public BigDecimal getBigDecimal(final String s, final int n) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(s), n);
    }
    
    public boolean getBoolean(final String s) throws ProSQLException {
        return this.getBoolean(this.schema.fieldToColumn(s));
    }
    
    public Date getDate(final String s) throws ProSQLException {
        return this.getDate(this.schema.fieldToColumn(s));
    }
    
    public Timestamp getTimestamp(final String s) throws ProSQLException {
        return this.getTimestamp(this.schema.fieldToColumn(s));
    }
    
    public Blob getBlob(final String s) throws ProSQLException {
        return this.getBlob(this.schema.fieldToColumn(s));
    }
    
    public Clob getClob(final String s) throws ProSQLException {
        return this.getClob(this.schema.fieldToColumn(s));
    }
    
    public GregorianCalendar getGregorianCalendar(final String s) throws ProSQLException {
        return this.getGregorianCalendar(this.schema.fieldToColumn(s));
    }
    
    public Object getObject(final String s) throws ProSQLException {
        return this.getObject(this.schema.fieldToColumn(s));
    }
    
    public byte[] getBytes(final int n) throws ProSQLException {
        if (this.schema.getProColumnType(n) == 18) {
            return (byte[])this.getObject(n, true);
        }
        switch (this.schema.getProColumnType(n)) {
            case 8:
            case 13: {
                return (byte[])this.getObject(n);
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "byte array");
            }
        }
    }
    
    public String getString(final int n) throws ProSQLException {
        if (this.schema.getProColumnType(n) == 19) {
            return (String)this.getObject(n, true);
        }
        final Object object = this.getObject(n);
        if (object == null) {
            return null;
        }
        switch (this.schema.getProColumnType(n)) {
            case 1: {
                return (String)object;
            }
            case 2: {
                return ((Date)object).toString();
            }
            case 34: {
                return ((Timestamp)object).toString();
            }
            case 40: {
                final GregorianCalendar gregorianCalendar = (GregorianCalendar)object;
                final DecimalFormat decimalFormat = new DecimalFormat("#0000;-#0000");
                final DecimalFormat decimalFormat2 = new DecimalFormat("00");
                final DecimalFormat decimalFormat3 = new DecimalFormat("000");
                final StringBuffer sb = new StringBuffer();
                sb.append(decimalFormat.format(gregorianCalendar.get(1)) + "-");
                sb.append(decimalFormat2.format(gregorianCalendar.get(2) + 1) + "-");
                sb.append(decimalFormat2.format(gregorianCalendar.get(5)) + " ");
                sb.append(decimalFormat2.format(gregorianCalendar.get(11)) + ":");
                sb.append(decimalFormat2.format(gregorianCalendar.get(12)) + ":");
                sb.append(decimalFormat2.format(gregorianCalendar.get(13)) + ".");
                sb.append(decimalFormat3.format(gregorianCalendar.get(14)));
                int n2 = (gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 60000;
                if (n2 < 0) {
                    sb.append("-");
                    n2 *= -1;
                }
                else {
                    sb.append("+");
                }
                sb.append(decimalFormat2.format(n2 / 60) + ":");
                sb.append(decimalFormat2.format(n2 % 60));
                return sb.toString();
            }
            case 3: {
                return ((Boolean)object).toString();
            }
            case 4:
            case 7:
            case 10:
            case 11:
            case 14: {
                if (object instanceof Integer) {
                    return ((Integer)object).toString();
                }
                return ((Long)object).toString();
            }
            case 41: {
                return ((Long)object).toString();
            }
            case 5: {
                return ((BigDecimal)object).toString();
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "String");
            }
        }
    }
    
    public long getLong(final int n) throws ProSQLException {
        final Object object = this.getObject(n);
        if (object == null) {
            return 0L;
        }
        switch (this.schema.getProColumnType(n)) {
            case 4:
            case 7:
            case 10:
            case 11:
            case 14: {
                if (object instanceof Integer) {
                    return (int)object;
                }
                return (long)object;
            }
            case 41: {
                return (long)object;
            }
            case 5: {
                return ((BigDecimal)object).longValue();
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "Long");
            }
        }
    }
    
    public int getInt(final int n) throws ProSQLException {
        final Object object = this.getObject(n);
        if (object == null) {
            return 0;
        }
        switch (this.schema.getProColumnType(n)) {
            case 4: {
                return (int)object;
            }
            case 3: {
                return ((boolean)object) ? 1 : 0;
            }
            case 5: {
                return ((BigDecimal)object).intValue();
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "int");
            }
        }
    }
    
    public double getDouble(final int n) throws ProSQLException {
        final Object object = this.getObject(n);
        if (object == null) {
            return 0.0;
        }
        switch (this.schema.getProColumnType(n)) {
            case 5: {
                return ((BigDecimal)object).doubleValue();
            }
            case 4: {
                return (int)object;
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "Double");
            }
        }
    }
    
    public BigDecimal getBigDecimal(final int n, final int n2) throws ProSQLException {
        final Object object = this.getObject(n);
        if (object == null) {
            return null;
        }
        switch (this.schema.getProColumnType(n)) {
            case 5: {
                return (BigDecimal)object;
            }
            case 41: {
                return new BigDecimal(((Long)object).toString());
            }
            case 4: {
                return new BigDecimal(((Integer)object).toString());
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "BigDecimal");
            }
        }
    }
    
    public boolean getBoolean(final int n) throws ProSQLException {
        final Object object = this.getObject(n);
        if (object == null) {
            return false;
        }
        switch (this.schema.getProColumnType(n)) {
            case 3: {
                return (boolean)object;
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "Boolean");
            }
        }
    }
    
    public Date getDate(final int n) throws ProSQLException {
        switch (this.schema.getProColumnType(n)) {
            case 2: {
                return (Date)this.getObject(n);
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "Date");
            }
        }
    }
    
    public Timestamp getTimestamp(final int n) throws ProSQLException {
        switch (this.schema.getProColumnType(n)) {
            case 34: {
                return (Timestamp)this.getObject(n);
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "Timestamp");
            }
        }
    }
    
    public Clob getClob(final int n) throws ProSQLException {
        switch (this.schema.getProColumnType(n)) {
            case 19: {
                return (Clob)this.getObject(n);
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "Clob");
            }
        }
    }
    
    public Blob getBlob(final int n) throws ProSQLException {
        switch (this.schema.getProColumnType(n)) {
            case 18: {
                return (Blob)this.getObject(n);
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "Blob");
            }
        }
    }
    
    public GregorianCalendar getGregorianCalendar(final int n) throws ProSQLException {
        switch (this.schema.getProColumnType(n)) {
            case 2:
            case 34:
            case 40: {
                return (GregorianCalendar)this.getObject(n, true);
            }
            default: {
                throw com.progress.open4gl.dynamicapi.ResultSet.getOutConvertException(this.schema.getProColumnType(n), "GregorianCalendar");
            }
        }
    }
    
    public ResultSetMetaData getMetaData() throws ProSQLException {
        return this.schema;
    }
    
    public Object getObject(final int n) throws ProSQLException {
        this.tracer.print("SDO.call: getObject");
        return this.getObject(n, false);
    }
    
    private Object getObject(final int n, final boolean b) throws ProSQLException {
        this.validateOpen();
        this.validateColumn(n);
        this.currentSubset.ttableImage.rowDeleted(true);
        Object o = this.currentSubset.ttableImage.getObject(n);
        this.wasNullIndicator = (o == null);
        if (!this.wasNullIndicator && !b) {
            switch (this.schema.getColumnProType(n)) {
                case 2: {
                    o = new Date(((GregorianCalendar)o).getTime().getTime());
                    break;
                }
                case 34: {
                    o = new Timestamp(((GregorianCalendar)o).getTime().getTime());
                    break;
                }
                case 19: {
                    o = new ProClob((String)o);
                    break;
                }
                case 18: {
                    o = new ProBlob(((byte[])o).clone());
                    break;
                }
            }
        }
        if (o != null) {
            if (o instanceof byte[]) {
                o = ((byte[])o).clone();
            }
            else if (o instanceof GregorianCalendar) {
                o = ((GregorianCalendar)o).clone();
            }
        }
        return o;
    }
    
    public boolean wasNull() throws ProSQLException {
        return this.wasNullIndicator;
    }
    
    public void clearWarnings() throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public int findColumn(final String s) throws ProSQLException {
        return this.schema.fieldToColumn(s);
    }
    
    public InputStream getAsciiStream(final int n) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public InputStream getAsciiStream(final String s) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public InputStream getBinaryStream(final int n) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public InputStream getBinaryStream(final String s) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public byte getByte(final int n) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public byte getByte(final String s) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public String getCursorName() throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public float getFloat(final int n) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public float getFloat(final String s) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public short getShort(final int n) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public short getShort(final String s) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public Time getTime(final int n) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public Time getTime(final String s) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public InputStream getUnicodeStream(final int n) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public InputStream getUnicodeStream(final String s) throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public SQLWarning getWarnings() throws ProSQLException {
        throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException();
    }
    
    public boolean next() throws ProSQLException {
        this.tracer.print("SDO.call: next");
        this.validateOpen();
        if (this.resultSetState == 1 || this.afterLast) {
            return false;
        }
        if (this.beforeFirst) {
            this.onInsertRow = false;
            return this.reposition(1);
        }
        this.onInsertRow = false;
        if (this.reposition(this.cursorPosition + 1)) {
            return true;
        }
        if (this.addMoreRows(this.sizeOfBatch) == 0) {
            this.afterLast = true;
            this.cursorPosition = 0;
            return false;
        }
        return this.reposition(this.cursorPosition + 1);
    }
    
    private void silentClose() {
        try {
            this.close();
        }
        catch (Throwable t) {}
    }
    
    public void close() throws ProSQLException {
        this.tracer.print("SDO.call: close");
        try {
            this.cancelBatch();
        }
        finally {
            if (this.sdoProc != null) {
                this.sdoProc.unPopulateProc();
            }
            this.closed = true;
        }
    }
    
    private void validateOpen() throws ProSQLException {
        if (this.closed) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725163L);
        }
    }
    
    private void validateColumn(final int value) throws ProSQLException {
        if (this.cursorPosition == 0 && !this.onInsertRow) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725116L);
        }
        if (value < 1 || value > this.schema.getColumnCount()) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725124L, "S1000", new Integer(value).toString());
        }
    }
    
    private boolean repositionForward(final int n) throws ProSQLException {
        this.absoluteReposition(this.highWaterMark);
        while (this.next()) {
            if (this.cursorPosition == n) {
                return true;
            }
        }
        return false;
    }
    
    private boolean absoluteReposition(final int i) throws ProSQLException {
        this.tracer.print("SDO.Set: absoluteReposition() to: " + i, 3);
        if (this.reposition(i)) {
            this.tracer.print("SDO.Set:absoluteReposition() success in current set.", 3);
            return true;
        }
        if (i > 0) {
            return this.repositionForward(i);
        }
        this.tracer.print("SDO.Set: absoluteReposition() do repositionBefore. ", 3);
        this.repositionBefore();
        return false;
    }
    
    private boolean reposition(final int n) throws ProSQLException {
        this.tracer.print("SDO.Set: reposition() to: " + n, 3);
        if (this.resultSetState == 1 || n < this.currentSubset.subsetStart || n > this.currentSubset.subsetStart + this.currentSubset.subsetNumRows - 1) {
            this.tracer.print("SDO.Set: cannot reposition. ", 3);
            return false;
        }
        this.cursorPosition = n;
        this.beforeFirst = false;
        this.afterLast = false;
        this.currentSubset.ttableImage.reposition(n - this.currentSubset.subsetStart + 1);
        this.tracer.print("SDO.Set: reposition successful. ", 3);
        return true;
    }
    
    private void repositionBefore() throws ProSQLException {
        if (this.resultSetState == 1) {
            return;
        }
        this.cursorPosition = 0;
        this.beforeFirst = true;
        this.afterLast = false;
        this.currentSubset.ttableImage.repositionBefore();
    }
    
    private void sendRowToServer(final int n) throws ProSQLException {
        this.tracer.print("SDO.Set: In sendRowToServer().", 3);
        if (n != 2) {
            this.currentSubset.ttableImage.rowDeleted(true);
            if (!this.currentSubset.ttableImage.commitUpdates()) {
                return;
            }
        }
        if (this.inBatch()) {
            this.currentSubset.ttableImage.putRowInBatch();
            return;
        }
        final ResultSetHolder resultSetHolder = new ResultSetHolder();
        final StringHolder stringHolder = new StringHolder();
        final StringHolder stringHolder2 = new StringHolder();
        resultSetHolder.setResultSetValue(this.currentSubset.ttableImage.prepareRowToSend());
        try {
            this.sdoProc.getSDOInterface().serverCommit(resultSetHolder, stringHolder, stringHolder2);
        }
        catch (Open4GLException ex) {
            this.silentClose();
            throw new ProSQLException(ex.getMessage());
        }
        final String stringValue = stringHolder.getStringValue();
        if (stringValue != null && stringValue.length() > 0) {
            this.tracer.print("SDO.Set: sendRowToServer() failed.", 3);
            this.currentSubset.ttableImage.doSendError(stringValue, (com.progress.open4gl.dynamicapi.ResultSet)resultSetHolder.getResultSetValue(), n);
        }
        else {
            this.currentSubset.ttableImage.afterSend((com.progress.open4gl.dynamicapi.ResultSet)resultSetHolder.getResultSetValue(), false);
            this.tracer.print("SDO.Set: Successful sendRowToServer().", 3);
        }
    }
    
    private void getSubset(final SdoProc sdoProc, final String str, final int subsetStart, final int i, final TtableImageHolder ttableImageHolder, final int n) throws ProSQLException {
        this.tracer.print("SDO.Set: in getSubset(). rowId: " + str + " howMany: " + i, 3);
        if (n == 2 && this.finalHighMark) {
            this.tracer.print("SDO.Set: getSubset - above high water no more rows", 3);
            ttableImageHolder.subsetStart = 0;
            ttableImageHolder.subsetNumRows = 0;
            return;
        }
        final IntHolder intHolder = new IntHolder();
        final ResultSetHolder resultSetHolder = new ResultSetHolder();
        try {
            int n2 = i;
            if (n == 2) {
                ++n2;
            }
            sdoProc.getSDOInterface().serverSendRows(null, str, n == 2, n2, intHolder, resultSetHolder);
        }
        catch (Open4GLException ex) {
            this.silentClose();
            throw new ProSQLException(ex.getMessage());
        }
        final com.progress.open4gl.dynamicapi.ResultSet set = (com.progress.open4gl.dynamicapi.ResultSet)resultSetHolder.getResultSetValue();
        this.tracer.print("SDO.Set: getSubset, serverSendRows sent " + intHolder.getIntValue(), 3);
        if (ttableImageHolder.ttableImage == null) {
            ttableImageHolder.ttableImage = new TtableImage((com.progress.open4gl.dynamicapi.ResultSetMetaData)set.getMetaData(), this.scrollingMode.getMode() == 1);
        }
        if (this.schema == null) {
            this.schema = SDOResultSetMetaDataImpl.createSDOSchema((com.progress.open4gl.dynamicapi.ResultSetMetaData)set.getMetaData(), sdoProc.getColumnProperties());
        }
        final int rePopulateTable = ttableImageHolder.ttableImage.rePopulateTable(set, subsetStart, i, this.highWaterMark);
        if (rePopulateTable == i && n == 4) {
            this.finalHighMark = true;
        }
        else if (rePopulateTable < i) {
            this.finalHighMark = true;
        }
        this.tracer.print("SDO.Set: getSubset rowsAdded: " + rePopulateTable, 3);
        if (rePopulateTable == 0) {
            ttableImageHolder.subsetStart = 0;
            ttableImageHolder.subsetNumRows = 0;
        }
        else {
            final int highWaterMark = subsetStart + rePopulateTable - 1;
            ttableImageHolder.subsetStart = subsetStart;
            ttableImageHolder.subsetNumRows = rePopulateTable;
            if (this.highWaterMark < highWaterMark) {
                this.highWaterMark = highWaterMark;
                if (this.finalHighMark) {
                    this.tracer.print("SDO.Set: getSubset final water mark is: " + this.highWaterMark, 3);
                }
            }
        }
    }
    
    private void onlyNextSupported() throws ProSQLException {
        if (this.scrollingMode.getMode() == 1) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725126L, "S1000", SDOScrollingMode.getModeName(1));
        }
    }
    
    private int addMoreRows(final int n) throws ProSQLException {
        if (this.resultSetState == 0 || this.resultSetState == 1) {
            throw new Open4GLError();
        }
        if (this.finalHighMark) {
            return 0;
        }
        final int n2 = this.currentSubset.subsetStart + this.currentSubset.subsetNumRows;
        final TtableImageHolder ttableImageHolder = new TtableImageHolder();
        String highestRowid = this.currentSubset.ttableImage.getHighestRowid();
        if (!this.statelessMode) {
            highestRowid = null;
        }
        this.getSubset(this.sdoProc, highestRowid, n2, n, ttableImageHolder, 2);
        if (ttableImageHolder.subsetNumRows == 0) {
            return 0;
        }
        if (this.currentSubset.ttableImage.appendRows(ttableImageHolder.ttableImage)) {
            throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725157L);
        }
        final TtableImageHolder currentSubset = this.currentSubset;
        currentSubset.subsetNumRows += ttableImageHolder.subsetNumRows;
        return ttableImageHolder.subsetNumRows;
    }
    
    private void replaceCurrentSubset(final TtableImageHolder currentSubset) throws ProSQLException {
        this.currentSubset = currentSubset;
        if (currentSubset.subsetNumRows > 0) {
            this.resultSetState = 2;
        }
        else {
            this.finalHighMark = true;
            this.highWaterMark = 0;
            this.cursorPosition = 0;
            final boolean beforeFirst = false;
            this.onInsertRow = beforeFirst;
            this.afterLast = beforeFirst;
            this.beforeFirst = beforeFirst;
            this.resultSetState = 1;
        }
    }
    
    public String getRowIdentity() throws ProSQLException {
        this.tracer.print("SDO.call: getRowIdentity");
        this.validateOpen();
        if (this.cursorPosition == 0 && !this.onInsertRow) {
            return null;
        }
        return this.currentSubset.ttableImage.getRowIdentity();
    }
    
    public byte[] getBytes(final int n, final int n2) throws ProSQLException {
        return this.getBytes(this.schema.fieldToColumn(n, n2));
    }
    
    public String getString(final int n, final int n2) throws ProSQLException {
        return this.getString(this.schema.fieldToColumn(n, n2));
    }
    
    public long getLong(final int n, final int n2) throws ProSQLException {
        return this.getLong(this.schema.fieldToColumn(n, n2));
    }
    
    public int getInt(final int n, final int n2) throws ProSQLException {
        return this.getInt(this.schema.fieldToColumn(n, n2));
    }
    
    public double getDouble(final int n, final int n2) throws ProSQLException {
        return this.getDouble(this.schema.fieldToColumn(n, n2));
    }
    
    public BigDecimal getBigDecimal(final int n, final int n2, final int n3) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(n, n2), n3);
    }
    
    public boolean getBoolean(final int n, final int n2) throws ProSQLException {
        return this.getBoolean(this.schema.fieldToColumn(n, n2));
    }
    
    public Date getDate(final int n, final int n2) throws ProSQLException {
        return this.getDate(this.schema.fieldToColumn(n, n2));
    }
    
    public Timestamp getTimestamp(final int n, final int n2) throws ProSQLException {
        return this.getTimestamp(this.schema.fieldToColumn(n, n2));
    }
    
    public GregorianCalendar getGregorianCalendar(final int n, final int n2) throws ProSQLException {
        return this.getGregorianCalendar(this.schema.fieldToColumn(n, n2));
    }
    
    public Blob getBlob(final int n, final int n2) throws ProSQLException {
        return this.getBlob(this.schema.fieldToColumn(n, n2));
    }
    
    public Clob getClob(final int n, final int n2) throws ProSQLException {
        return this.getClob(this.schema.fieldToColumn(n, n2));
    }
    
    public Object getObject(final int n, final int n2) throws ProSQLException {
        return this.getObject(this.schema.fieldToColumn(n, n2));
    }
    
    public byte[] getBytes(final String s, final int n) throws ProSQLException {
        return this.getBytes(this.schema.fieldToColumn(s, n));
    }
    
    public String getString(final String s, final int n) throws ProSQLException {
        return this.getString(this.schema.fieldToColumn(s, n));
    }
    
    public long getLong(final String s, final int n) throws ProSQLException {
        return this.getLong(this.schema.fieldToColumn(s, n));
    }
    
    public int getInt(final String s, final int n) throws ProSQLException {
        return this.getInt(this.schema.fieldToColumn(s, n));
    }
    
    public double getDouble(final String s, final int n) throws ProSQLException {
        return this.getDouble(this.schema.fieldToColumn(s, n));
    }
    
    public BigDecimal getBigDecimal(final String s, final int n, final int n2) throws ProSQLException {
        return this.getBigDecimal(this.schema.fieldToColumn(s, n), n2);
    }
    
    public boolean getBoolean(final String s, final int n) throws ProSQLException {
        return this.getBoolean(this.schema.fieldToColumn(s, n));
    }
    
    public Date getDate(final String s, final int n) throws ProSQLException {
        return this.getDate(this.schema.fieldToColumn(s, n));
    }
    
    public Timestamp getTimestamp(final String s, final int n) throws ProSQLException {
        return this.getTimestamp(this.schema.fieldToColumn(s, n));
    }
    
    public GregorianCalendar getGregorianCalendar(final String s, final int n) throws ProSQLException {
        return this.getGregorianCalendar(this.schema.fieldToColumn(s, n));
    }
    
    public Blob getBlob(final String s, final int n) throws ProSQLException {
        return this.getBlob(this.schema.fieldToColumn(s, n));
    }
    
    public Clob getClob(final String s, final int n) throws ProSQLException {
        return this.getClob(this.schema.fieldToColumn(s, n));
    }
    
    public Object getObject(final String s, final int n) throws ProSQLException {
        return this.getObject(this.schema.fieldToColumn(s, n));
    }
    
    public void updateNull(final String s, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), null);
    }
    
    public void updateBoolean(final String s, final boolean value, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), new Boolean(value));
    }
    
    public void updateInt(final String s, final int value, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), new Integer(value));
    }
    
    public void updateLong(final String s, final long value, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), new Long(value));
    }
    
    public void updateDouble(final String s, final double value, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), new Double(value));
    }
    
    public void updateBigDecimal(final String s, final BigDecimal bigDecimal, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), bigDecimal);
    }
    
    public void updateString(final String s, final String s2, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), s2);
    }
    
    public void updateBytes(final String s, final byte[] array, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), array);
    }
    
    public void updateDate(final String s, final Date date, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), date);
    }
    
    public void updateTimestamp(final String s, final Timestamp timestamp, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), timestamp);
    }
    
    public void updateBlob(final String s, final Blob blob, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), blob);
    }
    
    public void updateClob(final String s, final Clob clob, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), clob);
    }
    
    public void updateObject(final String s, final Object o, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), o);
    }
    
    public void updateNull(final int n, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), null);
    }
    
    public void updateBoolean(final int n, final boolean value, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), new Boolean(value));
    }
    
    public void updateInt(final int n, final int value, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), new Integer(value));
    }
    
    public void updateLong(final int n, final long value, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), new Long(value));
    }
    
    public void updateDouble(final int n, final double value, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), new Double(value));
    }
    
    public void updateBigDecimal(final int n, final BigDecimal bigDecimal, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), bigDecimal);
    }
    
    public void updateString(final int n, final String s, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), s);
    }
    
    public void updateBytes(final int n, final byte[] array, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), array);
    }
    
    public void updateDate(final int n, final Date date, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), date);
    }
    
    public void updateTimestamp(final int n, final Timestamp timestamp, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), timestamp);
    }
    
    public void updateBlob(final int n, final Blob blob, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), blob);
    }
    
    public void updateClob(final int n, final Clob clob, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), clob);
    }
    
    public void updateObject(final int n, final Object o, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), o);
    }
    
    public void updateGregorianCalendar(final String s, final GregorianCalendar gregorianCalendar) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s), gregorianCalendar);
    }
    
    public void updateGregorianCalendar(final int n, final GregorianCalendar gregorianCalendar) throws ProSQLException {
        this.updateObject(n, gregorianCalendar);
    }
    
    public void updateGregorianCalendar(final String s, final GregorianCalendar gregorianCalendar, final int n) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(s, n), gregorianCalendar);
    }
    
    public void updateGregorianCalendar(final int n, final GregorianCalendar gregorianCalendar, final int n2) throws ProSQLException {
        this.updateObject(this.schema.fieldToColumn(n, n2), gregorianCalendar);
    }
    
    protected void finalize() {
        try {
            this.close();
        }
        catch (ProSQLException ex) {}
    }
    
    public Reader getCharacterStream(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Reader getCharacterStream(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void setFetchDirection(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public int getFetchDirection() throws SQLException {
        throw new SQLException();
    }
    
    public void setFetchSize(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public int getType() throws SQLException {
        throw new SQLException();
    }
    
    public int getConcurrency() throws SQLException {
        throw new SQLException();
    }
    
    public void updateByte(final int n, final byte b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateShort(final int n, final short n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateFloat(final int n, final float n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTime(final int n, final Time time) throws SQLException {
        throw new SQLException();
    }
    
    public void updateAsciiStream(final int n, final InputStream inputStream, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBinaryStream(final int n, final InputStream inputStream, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateCharacterStream(final int n, final Reader reader, final int n2) throws SQLException {
        throw new SQLException();
    }
    
    public void updateByte(final String s, final byte b) throws SQLException {
        throw new SQLException();
    }
    
    public void updateShort(final String s, final short n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateFloat(final String s, final float n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateTime(final String s, final Time time) throws SQLException {
        throw new SQLException();
    }
    
    public void updateAsciiStream(final String s, final InputStream inputStream, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateBinaryStream(final String s, final InputStream inputStream, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public void updateCharacterStream(final String s, final Reader reader, final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Statement getStatement() throws SQLException {
        throw new SQLException();
    }
    
    public Object getObject(final int n, final Map map) throws SQLException {
        throw new SQLException();
    }
    
    public Ref getRef(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Array getArray(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public Object getObject(final String s, final Map map) throws SQLException {
        throw new SQLException();
    }
    
    public Ref getRef(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Array getArray(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public Date getDate(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Date getDate(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Time getTime(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Time getTime(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Timestamp getTimestamp(final int n, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public Timestamp getTimestamp(final String s, final Calendar calendar) throws SQLException {
        throw new SQLException();
    }
    
    public URL getURL(final int n) throws SQLException {
        throw new SQLException();
    }
    
    public URL getURL(final String s) throws SQLException {
        throw new SQLException();
    }
    
    public void updateArray(final int n, final Array array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateArray(final String s, final Array array) throws SQLException {
        throw new SQLException();
    }
    
    public void updateRef(final int n, final Ref ref) throws SQLException {
        throw new SQLException();
    }
    
    public void updateRef(final String s, final Ref ref) throws SQLException {
        throw new SQLException();
    }
    
    static class SdoProc implements Serializable
    {
        private transient SDOFactory theAppObject;
        private String appObjectName;
        private String procObjectName;
        private transient SDOInterface sdoProc;
        private String queryString;
        private String whereClause;
        private String sortByClause;
        private Vector columProperties;
        private com.progress.open4gl.dynamicapi.ResultSetMetaData sendRowsSchems;
        private com.progress.open4gl.dynamicapi.ResultSetMetaData serverCommitSchema;
        private boolean statelessMode;
        private Tracer tracer;
        
        SdoProc(final SDOFactory theAppObject, final String procObjectName, final String whereClause, final String sortByClause, final boolean statelessMode) throws Open4GLException, ProSQLException {
            (this.tracer = RunTimeProperties.tracer).print("SDO.setup: create SdoProc.");
            this.theAppObject = theAppObject;
            this.procObjectName = procObjectName;
            this.whereClause = whereClause;
            this.sortByClause = sortByClause;
            this.statelessMode = statelessMode;
            this.queryString = null;
            this.sdoProc = null;
            this.columProperties = SDOResultSetMetaDataImpl.getColumnProperties(this.getSDOInterface().columnProps("*", "valexp,valmsg,table,width,mandatory,format,initial,label,datatype,readonly"), 10);
            this.sendRowsSchems = SDOResultSetMetaDataImpl.createSendRowsMetadata(this.columProperties, 10);
            this.serverCommitSchema = SDOResultSetMetaDataImpl.createServerCommitMetaData(this.columProperties, 10);
            SDOResultSetMetaDataImpl.trimSystemFields(this.columProperties);
            ((SDOProcObject)this.sdoProc).setSchema(this.sendRowsSchems, this.serverCommitSchema);
        }
        
        SdoProc(final SDOFactory theAppObject, final String procObjectName, final com.progress.open4gl.dynamicapi.ResultSetMetaData sendRowsSchems) throws Open4GLException, ProSQLException {
            this.tracer = RunTimeProperties.tracer;
            this.theAppObject = theAppObject;
            this.sendRowsSchems = sendRowsSchems;
            this.procObjectName = procObjectName;
            this.populateProc();
        }
        
        SdoProc createLightWeightProc() throws Open4GLException, ProSQLException {
            return new SdoProc(this.theAppObject, this.procObjectName, this.sendRowsSchems);
        }
        
        Vector getColumnProperties() {
            return this.columProperties;
        }
        
        void disconnect() {
            this.theAppObject = null;
        }
        
        boolean isConnected() {
            return this.theAppObject != null;
        }
        
        void reConnect(final SDOFactory theAppObject) throws Open4GLException, ProSQLException {
            this.theAppObject = theAppObject;
            this.populateProc();
            this.unPopulateProc();
        }
        
        SDOInterface getSDOInterface() throws Open4GLException, ProSQLException {
            if (this.sdoProc == null) {
                if (!this.isConnected()) {
                    throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725127L);
                }
                this.populateProc();
            }
            return this.sdoProc;
        }
        
        void populateProc() throws Open4GLException, ProSQLException {
            if (this.sdoProc != null) {
                return;
            }
            this.tracer.print("SDO.setup: createProcObject().");
            this.sdoProc = this.createProcObject(this.theAppObject, this.procObjectName);
            final String objectVersion = this.sdoProc.getObjectVersion();
            this.tracer.print("SDO.setup: ADM Version: " + objectVersion);
            if (objectVersion.substring(0, 4).equals("ADM1")) {
                throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725141L, "S1000", objectVersion);
            }
            if (objectVersion.equals("ADM2.0")) {
                this.initializeProc(this.sdoProc);
            }
            else {
                this.batchInitProc(this.sdoProc);
            }
        }
        
        private void batchInitProc(final SDOInterface sdoInterface) throws Open4GLException, ProSQLException {
            final BatchSDOInitRequest batchSDOInitRequest = new BatchSDOInitRequest();
            if (this.whereClause != null && this.whereClause.length() > 0) {
                batchSDOInitRequest.setRequest("setQueryWhere", this.whereClause);
            }
            if (this.sortByClause != null && this.sortByClause.length() > 0) {
                final String convertTo4GLSort = convertTo4GLSort(this.sortByClause);
                this.tracer.print("SDO.setup: Sort clause: " + convertTo4GLSort);
                batchSDOInitRequest.setRequest("setQuerySort", convertTo4GLSort);
            }
            batchSDOInitRequest.setRequest("setOpenOnInit", "NO");
            batchSDOInitRequest.setRequest("initializeObject");
            final StringHolder stringHolder = new StringHolder();
            sdoInterface.batchServices(batchSDOInitRequest.getRequestString(), stringHolder);
            final BatchSDOInitResult batchSDOInitResult = new BatchSDOInitResult((String)stringHolder.getValue());
            if (this.whereClause != null && this.whereClause.length() > 0 && (!batchSDOInitResult.next() || batchSDOInitResult.currentFailed())) {
                throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725128L, "S1000", this.whereClause);
            }
            if (this.sortByClause != null && this.sortByClause.length() > 0 && (!batchSDOInitResult.next() || batchSDOInitResult.currentFailed())) {
                throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725129L, "S1000", this.sortByClause);
            }
        }
        
        private void initializeProc(final SDOInterface sdoInterface) throws Open4GLException, ProSQLException {
            if (this.whereClause != null && this.whereClause.length() > 0) {
                this.tracer.print("SDO.setup: Where clause: " + this.whereClause);
                if (!sdoInterface.setQueryWhere(this.whereClause)) {
                    throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725128L, "S1000", this.whereClause);
                }
            }
            if (this.sortByClause != null && this.sortByClause.length() > 0) {
                final String convertTo4GLSort = convertTo4GLSort(this.sortByClause);
                this.tracer.print("SDO.setup: Sort clause: " + convertTo4GLSort);
                if (!sdoInterface.setQuerySort(convertTo4GLSort)) {
                    throw com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725129L, "S1000", this.sortByClause);
                }
            }
            sdoInterface.initializeObject();
        }
        
        private static String convertTo4GLSort(final String str) {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, ",", false);
            String string = new String(stringTokenizer.nextToken());
            while (stringTokenizer.hasMoreTokens()) {
                string = string + " BY " + stringTokenizer.nextToken();
            }
            return string;
        }
        
        void unPopulateProc() throws ProSQLException {
            if (this.sdoProc == null) {
                return;
            }
            try {
                this.sdoProc._release();
            }
            catch (Open4GLException ex) {
                throw new ProSQLException(ex.getMessage());
            }
            finally {
                this.sdoProc = null;
            }
        }
        
        String getQuery() throws ProSQLException {
            if (this.queryString != null) {
                return this.queryString;
            }
            try {
                this.populateProc();
                return this.queryString = this.sdoProc.getQueryWhere();
            }
            catch (Open4GLException ex) {
                throw new ProSQLException(ex.getMessage());
            }
            finally {
                if (this.statelessMode) {
                    this.unPopulateProc();
                }
            }
        }
        
        private SDOInterface createProcObject(final SDOFactory sdoFactory, final String s) throws Open4GLException {
            try {
                final SDOInterface createSDOProcObject = sdoFactory._createSDOProcObject(s);
                ((SDOProcObject)createSDOProcObject).setSchema(this.sendRowsSchems, this.serverCommitSchema);
                return createSDOProcObject;
            }
            catch (Exception ex) {
                throw new Open4GLException(com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725130L, "S1000", s, ex.getMessage()).getMessage());
            }
        }
    }
    
    static class BatchSDOInitRequest
    {
        static final char FUNC_DELIMITER = '\u0001';
        private static final char PARAM_DELIMITER = '\u0002';
        private StringBuffer requestString;
        
        BatchSDOInitRequest() {
            this.requestString = new StringBuffer();
        }
        
        void setRequest(final String str) {
            if (this.requestString.length() != 0) {
                this.requestString.append('\u0001');
            }
            this.requestString.append(str);
        }
        
        private void setParameter(final String str) {
            this.requestString.append('\u0002').append(str);
        }
        
        void setRequest(final String request, final String parameter) {
            this.setRequest(request);
            this.setParameter(parameter);
        }
        
        void setRequest(final String request, final String parameter, final String parameter2) {
            this.setRequest(request);
            this.setParameter(parameter);
            this.setParameter(parameter2);
        }
        
        String getRequestString() {
            return new String(this.requestString);
        }
    }
    
    static class BatchSDOInitResult
    {
        static final char RESULT_DELIMITER = '\u0001';
        private static final char[] RESULT_DELIMITER_ARRAY;
        private static final String RESULT_DELIMITER_STR;
        private StringTokenizer results;
        private String current;
        
        BatchSDOInitResult(final String str) {
            if (str != null) {
                this.results = new StringTokenizer(str, BatchSDOInitResult.RESULT_DELIMITER_STR, false);
            }
            else {
                this.results = null;
            }
            this.current = null;
        }
        
        boolean next() {
            if (this.results == null || !this.results.hasMoreTokens()) {
                return false;
            }
            this.current = this.results.nextToken();
            return true;
        }
        
        String getResult() {
            return this.current;
        }
        
        boolean currentFailed() {
            return this.current == null || (!this.current.equals("yes") && !this.current.equals("YES"));
        }
        
        static {
            RESULT_DELIMITER_ARRAY = new char[] { '\u0001' };
            RESULT_DELIMITER_STR = new String(BatchSDOInitResult.RESULT_DELIMITER_ARRAY);
        }
    }
    
    static class TtableImageHolder implements Serializable
    {
        TtableImage ttableImage;
        int subsetStart;
        int subsetNumRows;
        
        TtableImageHolder emptyOut() {
            if (this.ttableImage != null) {
                this.ttableImage.emptyOut();
            }
            this.subsetStart = 0;
            this.subsetNumRows = 0;
            return this;
        }
        
        TtableImageHolder() {
            this.ttableImage = null;
            this.subsetStart = 0;
            this.subsetNumRows = 0;
        }
    }
    
    public static class StateChecker implements Externalizable
    {
        Boolean stateless;
        
        public StateChecker(final Boolean stateless) {
            this.stateless = stateless;
        }
        
        public StateChecker() {
            this.stateless = null;
        }
        
        public void writeExternal(final ObjectOutput objectOutput) throws IOException {
            if (!this.stateless) {
                throw new IOException(com.progress.open4gl.dynamicapi.ResultSet.getProSQLException(7665970990714725131L).getMessage());
            }
            objectOutput.writeObject(this.stateless);
        }
        
        public void readExternal(final ObjectInput objectInput) throws ClassNotFoundException, IOException {
            this.stateless = (Boolean)objectInput.readObject();
        }
    }
}
