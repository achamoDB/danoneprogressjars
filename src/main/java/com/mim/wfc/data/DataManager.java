// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.ui.AxHost$PeerInformation;
import com.ms.wfc.core.IComponentSite;
import com.ms.wfc.data.Command;
import com.ms.wfc.data.Connection;
import com.ms.wfc.data.RecordsetEventHandler;
import com.ms.wfc.app.Time;
import com.ms.wfc.data.AdoException;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.data.RecordsetEvent;
import com.mim.wfc.ui.MsgBox;
import com.ms.wfc.data.AdoProperty;
import com.ms.wfc.data.Fields;
import com.ms.wfc.core.IComponent;
import com.ms.wfc.core.IContainer;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.data.IDataSource;
import com.ms.wfc.data.Field;
import com.ms.com.Variant;
import com.ms.wfc.core.CancelEvent;
import com.ms.lang.Delegate;
import com.ms.wfc.core.Event;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.core.CancelEventHandler;
import com.ms.wfc.ui.Control;
import com.ms.wfc.data.Recordset;
import com.ms.wfc.data.ui.DataSource;
import com.ms.wfc.core.IRequireBegin;
import com.ms.wfc.core.Component;

public class DataManager extends Component implements IRequireBegin
{
    private DataSource \u00c1;
    private Recordset \u00c2;
    private String \u00c3;
    private DataManagerField[] \u00c4;
    private String \u00c5;
    private String \u00c6;
    private boolean \u00c7;
    private boolean \u00c8;
    private Control \u00c9;
    private DataManagerBinder \u00ca;
    private int \u00cb;
    private CancelEventHandler \u00cc;
    private EventHandler \u00cd;
    private CancelEventHandler \u00ce;
    private EventHandler \u00cf;
    private CancelEventHandler \u00d0;
    private EventHandler \u00d1;
    private CancelEventHandler \u00d2;
    private EventHandler \u00d3;
    private EventHandler \u00d4;
    private EventHandler \u00d5;
    private EventHandler \u00d6;
    private EventHandler \u00d8;
    private EventHandler \u00d9;
    private CancelEventHandler \u00da;
    private DataManager \u00db;
    private String \u00dc;
    private String \u00dd;
    private String \u00de;
    private boolean \u00df;
    private boolean \u00e0;
    private boolean \u00e1;
    private boolean \u00e2;
    private boolean \u00e3;
    private static Class \u00e4;
    private static Class \u00e5;
    
    public void setDataSource(final DataSource \u00e1) {
        if (this.\u00c1 == \u00e1) {
            return;
        }
        if (this.\u00c1 != null) {
            this.resetNotifications();
        }
        if ((this.\u00c1 = \u00e1) != null) {
            this.setNotifications();
        }
    }
    
    public DataSource getDataSource() {
        return this.\u00c1;
    }
    
    public void resync() {
        if (!this.saveModified()) {
            return;
        }
        this.resetNotifications();
        try {
            this.\u00c2().resync(1, 1);
            this.\u00c1();
        }
        finally {
            this.setNotifications();
        }
        this.updateFormFromRecord();
    }
    
    protected void onAutoSelectStart(final Event event) {
        if (this.\u00d6 != null) {
            this.\u00d6.invoke((Object)this, event);
        }
    }
    
    public void setBookmark(final Object bookmark) {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().setBookmark(bookmark);
    }
    
    public Object getBookmark() {
        final Recordset recordset = this.getRecordset();
        if (recordset != null) {
            return recordset.getBookmark();
        }
        return null;
    }
    
    public void addOnRecordCopied(final EventHandler eventHandler) {
        this.\u00cf = (EventHandler)Delegate.combine((Delegate)this.\u00cf, (Delegate)eventHandler);
    }
    
    protected int findIndexAdd(final String s) {
        int \u00ea = this.\u00ca(s);
        if (\u00ea < 0) {
            \u00ea = ((this.\u00c4 != null) ? this.\u00c4.length : 0);
            final DataManagerField[] \u00e4 = new DataManagerField[\u00ea + 1];
            if (this.\u00c4 != null) {
                System.arraycopy(this.\u00c4, 0, \u00e4, 0, \u00ea);
            }
            final DataManagerField dataManagerField = new DataManagerField(s);
            dataManagerField.\u00f4 = true;
            dataManagerField.\u00f2 = false;
            dataManagerField.\u00f3 = false;
            \u00e4[\u00ea] = dataManagerField;
            this.\u00c4 = \u00e4;
        }
        return \u00ea;
    }
    
    public void addOnRecordAdded(final EventHandler eventHandler) {
        this.\u00cd = (EventHandler)Delegate.combine((Delegate)this.\u00cd, (Delegate)eventHandler);
    }
    
    public boolean getBOF() {
        final Recordset recordset = this.getRecordset();
        return recordset == null || !recordset.isOpen() || recordset.getBOF();
    }
    
    public boolean isLastRecord() {
        final Recordset recordset = this.getRecordset();
        if (recordset == null || !recordset.isOpen()) {
            return false;
        }
        final int recordCount = recordset.getRecordCount();
        final int absolutePosition = recordset.getAbsolutePosition();
        return recordCount != -1 && absolutePosition != -1 && absolutePosition != -2 && absolutePosition != -3 && absolutePosition == recordCount;
    }
    
    public void addOnAddingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00cc = (CancelEventHandler)Delegate.combine((Delegate)this.\u00cc, (Delegate)cancelEventHandler);
    }
    
    public void removeOnCopyingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00ce = (CancelEventHandler)Delegate.remove((Delegate)this.\u00ce, (Delegate)cancelEventHandler);
    }
    
    protected void onDeletingRecord(final CancelEvent cancelEvent) {
        if (this.\u00d2 != null) {
            this.\u00d2.invoke((Object)this, cancelEvent);
        }
    }
    
    public void setAutoSelectMatch(final boolean \u00e8) {
        this.\u00c8 = \u00e8;
    }
    
    public boolean getAutoSelectMatch() {
        return this.\u00c8;
    }
    
    public int getInt(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getInt(\u00ea);
        }
        return -1;
    }
    
    public void setInt(final String s, final int n) {
        this.setInt(this.findIndexAdd(s), n);
    }
    
    public int getInt(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getInt();
    }
    
    public void setInt(final int n, final int int1) {
        this.\u00c4();
        this.\u00c4[n].setInt(int1);
    }
    
    public void clearBoundForm() {
        this.clearManager();
        this.updateFormFromManager();
    }
    
    public void updateRecordFromForm() {
        this.updateManagerFromForm();
        this.updateRecordFromManager();
    }
    
    protected void onUpdatingForm(final Event event) {
        if (this.\u00d4 != null) {
            this.\u00d4.invoke((Object)this, event);
        }
    }
    
    public void moveLast() {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().moveLast();
    }
    
    public byte getByte(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getByte(\u00ea);
        }
        return -1;
    }
    
    public void setByte(final String s, final byte b) {
        this.setByte(this.findIndexAdd(s), b);
    }
    
    public byte getByte(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getByte();
    }
    
    public void setByte(final int n, final byte byte1) {
        this.\u00c4();
        this.\u00c4[n].setByte(byte1);
    }
    
    public boolean getBoolean(final String s) {
        final int \u00ea = this.\u00ca(s);
        return \u00ea >= 0 && this.getBoolean(\u00ea);
    }
    
    public void setBoolean(final String s, final boolean b) {
        this.setBoolean(this.findIndexAdd(s), b);
    }
    
    public boolean getBoolean(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getBoolean();
    }
    
    public void setBoolean(final int n, final boolean boolean1) {
        this.\u00c4();
        this.\u00c4[n].setBoolean(boolean1);
    }
    
    public boolean isNull(final String s) {
        final int \u00ea = this.\u00ca(s);
        return \u00ea < 0 || this.isNull(\u00ea);
    }
    
    public boolean isNull(final int n) {
        this.\u00c4();
        return this.\u00c4[n].\u00f0.getvt() == 1;
    }
    
    public void movePrevious() {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().movePrevious();
    }
    
    public void startAutoSelect() {
        if (!this.saveModified()) {
            return;
        }
        this.setAutoSelectMode(true);
        this.clearBoundForm();
        this.onAutoSelectStart(new Event());
    }
    
    public void abortAutoSelect() {
        this.setAutoSelectMode(false);
        this.onAutoSelectAborted(new Event());
    }
    
    public void executeAutoSelect() {
        this.updateManagerFromForm();
        this.setAutoSelectMode(this.\u00c7 = false);
        this.doAutoSelect();
        this.onAutoSelectExecuted(new Event());
    }
    
    public void commitTrans() {
        this.\u00c8().commitTrans();
    }
    
    public void doAutoSelect() {
        this.\u00c4();
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.\u00c4.length; ++i) {
            if (this.\u00c4[i].\u00f3) {
                final Variant \u00f0 = this.\u00c4[i].\u00f0;
                final short getvt = \u00f0.getvt();
                if (getvt != 1 && (getvt == 8 || getvt == 3 || getvt == 2 || getvt == 17 || getvt == 5 || getvt == 4 || getvt == 7 || getvt == 11)) {
                    if (sb.length() > 0) {
                        sb.append(" and ");
                    }
                    sb.append(this.\u00c4[i].\u00ee);
                    boolean b = false;
                    if (getvt == 8) {
                        final String string = \u00f0.getString();
                        final int length = string.length();
                        if (length > 0 && string.charAt(length - 1) == '%') {
                            b = true;
                        }
                        else if (this.\u00c8) {
                            \u00f0.putString(string + "%");
                            b = true;
                        }
                    }
                    sb.append(b ? " like " : " = ");
                    sb.append('$');
                    sb.append(this.\u00c4[i].\u00ee);
                }
            }
        }
        this.doSelect((sb.length() != 0) ? sb.toString() : null);
    }
    
    protected void onAutoSelectExecuted(final Event event) {
        if (this.\u00d8 != null) {
            this.\u00d8.invoke((Object)this, event);
        }
    }
    
    protected void onAutoSelectAborted(final Event event) {
        if (this.\u00d9 != null) {
            this.\u00d9.invoke((Object)this, event);
        }
    }
    
    public void executeSelect(final String s) {
        if (!this.saveModified()) {
            return;
        }
        this.doSelect(s);
    }
    
    public void excludeFromAutoSelect(final String anObject, final boolean b) {
        this.\u00c4();
        for (int i = 0; i < this.\u00c4.length; ++i) {
            if (this.\u00c4[i].\u00ee.equals(anObject)) {
                this.\u00c4[i].\u00f3 = !b;
            }
        }
    }
    
    public void setChildRelation(final DataManager \u00fb, final String \u00fc, final String \u00fe, final String \u00fd) {
        final DataSource \u00e7 = this.\u00c7();
        final DataSource dataSource = \u00fb.getDataSource();
        if (dataSource == null) {
            throw new DataException(12, "Data source of child manager not set, use the setDataSource method");
        }
        this.\u00db = \u00fb;
        this.\u00dc = \u00fc;
        this.\u00dd = \u00fd;
        this.\u00de = \u00fe;
        dataSource.setParentDataSource(\u00e7);
        dataSource.setParentFieldName("ChildCMD");
    }
    
    public void doSelect(final String str) {
        final StringBuffer sb = new StringBuffer("select ");
        boolean b = false;
        if (this.\u00c4 != null) {
            for (int i = 0; i < this.\u00c4.length; ++i) {
                if (!this.\u00c4[i].\u00f4) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(this.\u00c4[i].\u00ee);
                    b = true;
                }
            }
        }
        if (!b) {
            sb.append('*');
        }
        sb.append(" from ");
        sb.append(this.\u00c3);
        if (this.\u00c6 != null) {
            sb.append(" where ");
            sb.append(this.\u00c6);
        }
        if (str != null && str.length() != 0) {
            sb.append((this.\u00c6 != null) ? " and " : " where ");
            sb.append(str);
        }
        if (this.\u00c5 != null) {
            sb.append(" order by ");
            sb.append(this.\u00c5);
        }
        this.doSelectStatement(sb.toString());
    }
    
    public long getCurrency(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getCurrency(\u00ea);
        }
        return 0L;
    }
    
    public void setCurrency(final String s, final long n) {
        this.setCurrency(this.findIndexAdd(s), n);
    }
    
    public long getCurrency(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getCurrency();
    }
    
    public void setCurrency(final int n, final long currency) {
        this.\u00c4();
        this.\u00c4[n].setCurrency(currency);
    }
    
    public boolean hasCurrent() {
        final Recordset recordset = this.getRecordset();
        return recordset != null && recordset.isOpen() && !recordset.getEOF();
    }
    
    protected void onRecordCopied(final Event event) {
        if (this.\u00cf != null) {
            this.\u00cf.invoke((Object)this, event);
        }
    }
    
    protected void onRecordUpdated(final Event event) {
        if (this.\u00d1 != null) {
            this.\u00d1.invoke((Object)this, event);
        }
    }
    
    public void removeOnRecordDeleted(final EventHandler eventHandler) {
        this.\u00d3 = (EventHandler)Delegate.remove((Delegate)this.\u00d3, (Delegate)eventHandler);
    }
    
    public void addOnFormUpdated(final EventHandler eventHandler) {
        this.\u00d5 = (EventHandler)Delegate.combine((Delegate)this.\u00d5, (Delegate)eventHandler);
    }
    
    public void removeOnAutoSelectExecuted(final EventHandler eventHandler) {
        this.\u00d8 = (EventHandler)Delegate.remove((Delegate)this.\u00d8, (Delegate)eventHandler);
    }
    
    public void removeOnSaveModified(final CancelEventHandler cancelEventHandler) {
        this.\u00da = (CancelEventHandler)Delegate.remove((Delegate)this.\u00da, (Delegate)cancelEventHandler);
    }
    
    public boolean isAdding() {
        return this.\u00df;
    }
    
    public void updateManagerFromForm() {
        this.\u00cb(false);
    }
    
    public void setBoundForm(final Control \u00e9) {
        this.\u00c9 = \u00e9;
    }
    
    public Control getBoundForm() {
        return this.\u00c9;
    }
    
    public void removeOnUpdatingForm(final EventHandler eventHandler) {
        this.\u00d4 = (EventHandler)Delegate.remove((Delegate)this.\u00d4, (Delegate)eventHandler);
    }
    
    public float getFloat(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getFloat(\u00ea);
        }
        return 0.0f;
    }
    
    public void setFloat(final String s, final float n) {
        this.setFloat(this.findIndexAdd(s), n);
    }
    
    public float getFloat(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getFloat();
    }
    
    public void setFloat(final int n, final float float1) {
        this.\u00c4();
        this.\u00c4[n].setFloat(float1);
    }
    
    public void addOnAutoSelectStart(final EventHandler eventHandler) {
        this.\u00d6 = (EventHandler)Delegate.combine((Delegate)this.\u00d6, (Delegate)eventHandler);
    }
    
    public void updateFormFromManager() {
        this.onUpdatingForm(new Event());
        this.\u00cb(true);
        this.onFormUpdated(new Event());
    }
    
    public void beginTrans() {
        this.\u00c8().beginTrans();
    }
    
    public void rollbackTrans() {
        this.\u00c8().rollbackTrans();
    }
    
    public byte[] getBytes(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getBytes(\u00ea);
        }
        return null;
    }
    
    public void setBytes(final String s, final byte[] array) {
        this.setBytes(this.findIndexAdd(s), array);
    }
    
    public byte[] getBytes(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getBytes();
    }
    
    public void setBytes(final int n, final byte[] bytes) {
        this.\u00c4();
        this.\u00c4[n].setBytes(bytes);
    }
    
    private void \u00c1() {
        if (this.\u00db != null) {
            this.\u00db.setDataSource(null);
            Recordset recordset = null;
            final Recordset recordset2 = this.getRecordset();
            if (recordset2 != null) {
                final Field field = recordset2.getField("ChildCMD");
                if (field != null) {
                    recordset = (Recordset)field.getObject();
                }
            }
            this.\u00db.setRecordset(recordset);
        }
    }
    
    public IDataSource getChildDataSource() {
        if (this.\u00db == null) {
            return null;
        }
        return (IDataSource)this.\u00c2().getField("ChildCMD").getObject();
    }
    
    public void begin() {
        this.\u00e1 = true;
    }
    
    private Recordset \u00c2() {
        Recordset recordset;
        if (this.\u00c1 != null) {
            recordset = this.\u00c1.getRecordset();
        }
        else {
            recordset = this.\u00c2;
        }
        if (recordset == null) {
            throw new DataException(3, "Data source has no current recordset");
        }
        return recordset;
    }
    
    public DataManager() {
        this.\u00c1 = null;
        this.\u00c2 = null;
        this.\u00c3 = null;
        this.\u00c4 = null;
        this.\u00c5 = null;
        this.\u00c6 = null;
        this.\u00c7 = false;
        this.\u00c8 = false;
        this.\u00c9 = null;
        this.\u00ca = null;
        this.\u00cb = 0;
        this.\u00db = null;
        this.\u00dc = null;
        this.\u00dd = null;
        this.\u00de = null;
        this.\u00df = false;
        this.\u00e0 = false;
        this.\u00e1 = false;
        this.\u00e2 = false;
        this.\u00e3 = false;
        _cls753A._mth821F();
    }
    
    public DataManager(final IContainer container) {
        this.\u00c1 = null;
        this.\u00c2 = null;
        this.\u00c3 = null;
        this.\u00c4 = null;
        this.\u00c5 = null;
        this.\u00c6 = null;
        this.\u00c7 = false;
        this.\u00c8 = false;
        this.\u00c9 = null;
        this.\u00ca = null;
        this.\u00cb = 0;
        this.\u00db = null;
        this.\u00dc = null;
        this.\u00dd = null;
        this.\u00de = null;
        this.\u00df = false;
        this.\u00e0 = false;
        this.\u00e1 = false;
        this.\u00e2 = false;
        this.\u00e3 = false;
        container.add((IComponent)this);
        _cls753A._mth821F();
    }
    
    public DataManager(final String tableName, final DataManagerField[] fields) {
        this();
        this.setTableName(tableName);
        this.setFields(fields);
        this.clearManager();
    }
    
    public DataManager(final String s, final DataManagerField[] array, final DataSource dataSource) {
        this(s, array);
        this.setDataSource(dataSource);
    }
    
    public int getEditMode() {
        final Recordset recordset = this.getRecordset();
        if (recordset == null) {
            return 0;
        }
        return recordset.getEditMode();
    }
    
    public void doSelectStatement(final String s) {
        if (!this.saveModified()) {
            return;
        }
        if (this.\u00c1 == null) {
            this.\u00c2();
        }
        if ((this.\u00cb & 0x1) != 0x0) {
            this.\u00c3(s, false);
        }
        String s2 = this.expandStatement(s, true);
        if ((this.\u00cb & 0x2) != 0x0) {
            this.\u00c3(s2, true);
        }
        if (this.\u00dc != null && this.\u00dd != null && this.\u00de != null) {
            s2 = "SHAPE {" + s2 + "} AS ParentCMD APPEND ({" + this.\u00dc + "} AS ChildCMD RELATE " + this.\u00de + " TO " + this.\u00dd + ") AS ChildCMD";
        }
        this.resetNotifications();
        try {
            if (this.\u00c1 != null) {
                final Recordset recordset = this.\u00c1.getRecordset();
                if (recordset != null && recordset.isOpen()) {
                    recordset.cancelUpdate();
                    this.\u00c7 = false;
                }
                this.\u00c1.setCommandType(1);
                this.\u00c1.setCommandText(s2);
            }
            else {
                final Recordset recordset2 = this.getRecordset();
                recordset2.cancelUpdate();
                this.\u00c7 = false;
                if (recordset2.isOpen()) {
                    recordset2.close();
                }
                recordset2.open((Object)s2);
            }
            this.\u00c1();
        }
        finally {
            this.setNotifications();
        }
        this.updateFormFromRecord();
    }
    
    public String expandStatement(final String s, final boolean b) {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        sb.ensureCapacity(1000);
        sb2.ensureCapacity(50);
        boolean b2 = false;
        boolean b3 = false;
        int n = 0;
    Label_0306:
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\\') {
                n = ((n == 0) ? 1 : 0);
                sb.append(char1);
            }
            else {
                if (char1 == '\'') {
                    if (!b3 && n == 0) {
                        b2 = !b2;
                    }
                }
                else if (char1 == '\"') {
                    if (!b2 && n == 0) {
                        b3 = !b3;
                    }
                }
                else if (char1 == '$' && !b2 && !b3 && n == 0) {
                    sb2.setLength(0);
                    final int n2 = i++;
                    while (true) {
                        while (i < length) {
                            final char char2 = s.charAt(i);
                            if (Character.isJavaIdentifierPart(char2)) {
                                sb2.append(char2);
                                ++i;
                            }
                            else {
                                if (sb2.length() == 0) {
                                    sb.append('$');
                                    i = n2;
                                    continue Label_0306;
                                }
                                if (!this.\u00c5(sb, sb2.toString(), b)) {
                                    sb.append('$');
                                    i = n2;
                                    continue Label_0306;
                                }
                                if (char2 != '\0') {
                                    sb.append(char2);
                                }
                                continue Label_0306;
                            }
                        }
                        final char char2 = '\0';
                        continue;
                    }
                }
                sb.append(char1);
                n = 0;
            }
        }
        return sb.toString();
    }
    
    public void updateManagerFromRecord() {
        final Recordset recordset = this.getRecordset();
        if (recordset == null || recordset.getEOF()) {
            this.clearManager();
            return;
        }
        final Fields fields = recordset.getFields();
        for (int count = fields.getCount(), i = 0; i < count; ++i) {
            final Field item = fields.getItem(i);
            if (item != null) {
                final AdoProperty item2 = item.getProperties().getItem("BASECOLUMNNAME");
                String string = (item2 == null) ? null : item2.getString();
                if (string == null) {
                    string = "expr" + i;
                }
                final DataManagerField dataManagerField = this.\u00c4[this.findIndexAdd(string)];
                dataManagerField.\u00f0 = item.getValue();
                if (dataManagerField.\u00f1 == 0) {
                    dataManagerField.\u00f1 = DataManagerField.\u00ee(item.getType());
                }
            }
        }
        this.\u00c7 = false;
    }
    
    protected void onAddingRecord(final CancelEvent cancelEvent) {
        if (this.\u00cc != null) {
            this.\u00cc.invoke((Object)this, cancelEvent);
        }
    }
    
    public void removeOnUpdatingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00d0 = (CancelEventHandler)Delegate.remove((Delegate)this.\u00d0, (Delegate)cancelEventHandler);
    }
    
    public void addOnDeletingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00d2 = (CancelEventHandler)Delegate.combine((Delegate)this.\u00d2, (Delegate)cancelEventHandler);
    }
    
    private void \u00c3(final String str, final boolean b) {
        final String title = MsgBox.getTitle();
        MsgBox.setTitle("DataManager debug");
        MsgBox.show((b ? "SQL statement after expansion:\n" : "SQL statement before expansion:\n") + str);
        MsgBox.setTitle(title);
    }
    
    public void removeOnRecordAdded(final EventHandler eventHandler) {
        this.\u00cd = (EventHandler)Delegate.remove((Delegate)this.\u00cd, (Delegate)eventHandler);
    }
    
    public void delete() {
        final CancelEvent cancelEvent = new CancelEvent();
        this.onDeletingRecord(cancelEvent);
        if (cancelEvent.cancel) {
            return;
        }
        final Recordset \u00e2 = this.\u00c2();
        \u00e2.cancelUpdate();
        this.\u00c7 = false;
        \u00e2.delete();
        this.onRecordDeleted(new Event());
    }
    
    public void cancelUpdate() {
        final Recordset recordset = this.getRecordset();
        if (recordset != null) {
            recordset.cancelUpdate();
            this.\u00c7 = false;
        }
    }
    
    protected void _moveComplete(final Object o, final RecordsetEvent recordsetEvent) {
        if (!this.\u00e1 || this.\u00e3 || this.\u00e2) {
            return;
        }
        this.\u00e3 = true;
        try {
            this.updateFormFromRecord();
        }
        finally {
            this.\u00e3 = false;
        }
    }
    
    public void setTableName(final String \u00e3) {
        this.\u00c3 = \u00e3;
    }
    
    public String getTableName() {
        return this.\u00c3;
    }
    
    public void setBinder(final DataManagerBinder \u00ea) {
        this.\u00ca = \u00ea;
    }
    
    public DataManagerBinder getBinder() {
        return this.\u00ca;
    }
    
    public void setNull(final String s) {
        this.setNull(this.findIndexAdd(s));
    }
    
    public void setNull(final int n) {
        this.\u00c4();
        this.\u00c4[n].setNull();
    }
    
    public boolean saveModified() {
        if (this.\u00e0) {
            this.\u00c7 = false;
            this.abortAutoSelect();
            return true;
        }
        if (!this.\u00c7) {
            return true;
        }
        final Recordset recordset = this.getRecordset();
        if (recordset == null || !recordset.isOpen() || recordset.getEOF()) {
            return true;
        }
        final CancelEvent cancelEvent = new CancelEvent();
        this.onSaveModified(cancelEvent);
        return !cancelEvent.cancel;
    }
    
    public void setModified(final boolean \u00e7) {
        this.\u00c7 = \u00e7;
    }
    
    public boolean getModified() {
        return this.\u00c7;
    }
    
    public void addOnRecordUpdated(final EventHandler eventHandler) {
        this.\u00d1 = (EventHandler)Delegate.combine((Delegate)this.\u00d1, (Delegate)eventHandler);
    }
    
    protected void onFormUpdated(final Event event) {
        if (this.\u00d5 != null) {
            this.\u00d5.invoke((Object)this, event);
        }
    }
    
    public void moveFirst() {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().moveFirst();
    }
    
    public void setOrderByClause(final String s) {
        this.\u00c5 = ((s != null) ? ((s.length() != 0) ? s : null) : null);
    }
    
    public String getOrderByClause() {
        return this.\u00c5;
    }
    
    private void \u00c4() {
        if (this.\u00c4 == null) {
            throw new DataException(7, "Field list not set, use the setFields method");
        }
    }
    
    public void setDebug(final int \u00eb) {
        if (!DataManagerDebug.valid(\u00eb)) {
            throw new WFCInvalidEnumException("debug", \u00eb, (DataManager.\u00e4 != null) ? DataManager.\u00e4 : (DataManager.\u00e4 = \u00c6("com.mim.wfc.data.DataManagerDebug")));
        }
        this.\u00cb = \u00eb;
    }
    
    public int getDebug() {
        return this.\u00cb;
    }
    
    public void move(final int n, final Object o) {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().move(n, o);
    }
    
    public void move(final int n) {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().move(n);
    }
    
    public short getShort(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getShort(\u00ea);
        }
        return -1;
    }
    
    public void updateRecordFromManager() {
        this.\u00c4();
        final Recordset \u00e2 = this.\u00c2();
        this.\u00e2 = true;
        try {
            for (int i = 0; i < this.\u00c4.length; ++i) {
                if (this.\u00c4[i].\u00f2) {
                    final String \u00ee = this.\u00c4[i].\u00ee;
                    Field field = null;
                    try {
                        field = \u00e2.getField(\u00ee);
                        if (field != null) {
                            final Variant \u00f0 = this.\u00c4[i].\u00f0;
                            final short getvt = \u00f0.getvt();
                            final int \u00f1 = this.\u00c4[i].\u00f1;
                            if (getvt != 1 && \u00f1 != 0 && \u00f1 != 10 && \u00f1 != getvt) {
                                try {
                                    \u00f0.changeType(\u00f1);
                                }
                                catch (ClassCastException ex2) {
                                    DataException.failVariantType(\u00f1, getvt, \u00ee);
                                }
                            }
                            field.setValue(\u00f0);
                        }
                    }
                    catch (AdoException ex) {
                        DataException.failAdoType(this.\u00c4[i].\u00f0.getvt(), field.getType(), \u00ee, ex);
                    }
                }
            }
        }
        finally {
            this.\u00e2 = false;
        }
    }
    
    public void setShort(final String s, final short n) {
        this.setShort(this.findIndexAdd(s), n);
    }
    
    public short getShort(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getShort();
    }
    
    public void setShort(final int n, final short short1) {
        this.\u00c4();
        this.\u00c4[n].setShort(short1);
    }
    
    public Variant getValue(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getValue(\u00ea);
        }
        return null;
    }
    
    public void setValue(final String s, final Variant variant) {
        this.setValue(this.findIndexAdd(s), variant);
    }
    
    public Variant getValue(final int n) {
        this.\u00c4();
        return this.\u00c4[n].\u00f0;
    }
    
    public void setValue(final int n, final Variant \u00f0) {
        this.\u00c4();
        this.\u00c4[n].\u00f0 = \u00f0;
    }
    
    public void addBinding(final String s, final DataManagerBinding dataManagerBinding) {
        if (this.\u00ca == null) {
            this.\u00ca = new DataManagerBinder();
        }
        this.\u00ca.addBinding(s, dataManagerBinding);
    }
    
    public Time getDate(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getDate(\u00ea);
        }
        return null;
    }
    
    public void setDate(final String s, final Time time) {
        this.setDate(this.findIndexAdd(s), time);
    }
    
    public Time getDate(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getDate();
    }
    
    public void setDate(final int n, final Time date) {
        this.\u00c4();
        this.\u00c4[n].setDate(date);
    }
    
    public void removeOnAutoSelectStart(final EventHandler eventHandler) {
        this.\u00d6 = (EventHandler)Delegate.remove((Delegate)this.\u00d6, (Delegate)eventHandler);
    }
    
    private boolean \u00c5(final StringBuffer sb, final String s, final boolean b) {
        this.\u00c4();
        final int \u00ea = this.\u00ca(s);
        if (\u00ea < 0) {
            throw new DataException(8, "Parameter '" + s + "' unknown");
        }
        final Variant \u00f0 = this.\u00c4[\u00ea].\u00f0;
        final short getvt = \u00f0.getvt();
        switch (getvt) {
            case 1: {
                sb.append("null");
                break;
            }
            case 2:
            case 3:
            case 17: {
                sb.append(\u00f0.toInt());
                break;
            }
            case 8: {
                if (b) {
                    sb.append("\"");
                }
                sb.append(\u00f0.getString());
                if (b) {
                    sb.append("\"");
                    break;
                }
                break;
            }
            case 4:
            case 5: {
                sb.append(\u00f0.toDouble());
                break;
            }
            case 7: {
                final Time time = new Time(\u00f0.getDate());
                sb.append("#");
                sb.append(time.getMonth());
                sb.append("/");
                sb.append(time.getDay());
                sb.append("/");
                sb.append(time.getYear());
                sb.append("#");
                break;
            }
            case 11: {
                sb.append(\u00f0.getBoolean() ? "true" : "false");
                break;
            }
            default: {
                throw new DataException(9, "Parameter type " + DataException.vtToString(getvt) + " of parameter '" + s + "' not allowed");
            }
        }
        return true;
    }
    
    public Object getDispatch(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getDispatch(\u00ea);
        }
        return null;
    }
    
    public void setDispatch(final String s, final Object o) {
        this.setDispatch(this.findIndexAdd(s), o);
    }
    
    public Object getDispatch(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getDispatch();
    }
    
    public void setDispatch(final int n, final Object dispatch) {
        this.\u00c4();
        this.\u00c4[n].setDispatch(dispatch);
    }
    
    public void setRecordset(final Recordset \u00e2) {
        if (this.\u00c2 == \u00e2) {
            return;
        }
        if (this.\u00c2 != null) {
            this.resetNotifications();
        }
        if ((this.\u00c2 = \u00e2) != null) {
            this.setNotifications();
        }
    }
    
    public Recordset getRecordset() {
        if (this.\u00c1 != null) {
            return this.\u00c1.getRecordset();
        }
        return this.\u00c2;
    }
    
    public void copy() {
        if (!this.saveModified()) {
            return;
        }
        final CancelEvent cancelEvent = new CancelEvent();
        this.onCopyingRecord(cancelEvent);
        if (cancelEvent.cancel) {
            return;
        }
        try {
            this.\u00df = true;
            this.resetNotifications();
            try {
                this.\u00c2().addNew();
            }
            finally {
                this.setNotifications();
            }
            this.onRecordCopied(new Event());
            this.updateRecordFromManager();
            this.updateFormFromManager();
        }
        finally {
            this.\u00df = false;
        }
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public void clearChildRelation() {
        if (this.\u00db == null) {
            return;
        }
        this.\u00dc = null;
        this.\u00dd = null;
        this.\u00de = null;
        final DataSource dataSource = this.\u00db.getDataSource();
        if (dataSource != null) {
            dataSource.setParentDataSource((DataSource)null);
            dataSource.setParentFieldName((String)null);
        }
    }
    
    private DataSource \u00c7() {
        if (this.\u00c1 == null) {
            throw new DataException(1, "Data source not set, use the setDataSource method");
        }
        return this.\u00c1;
    }
    
    private Control getBoundControl(final DataManagerField dataManagerField) {
        Control \u00ef = dataManagerField.\u00ef;
        if (\u00ef == null && dataManagerField.\u00f5) {
            \u00ef = this.\u00c9(this.\u00c9, dataManagerField.\u00ee);
            if (\u00ef != null) {
                dataManagerField.\u00ef = \u00ef;
            }
            else {
                dataManagerField.\u00f5 = false;
            }
        }
        return \u00ef;
    }
    
    protected void onRecordDeleted(final Event event) {
        if (this.\u00d3 != null) {
            this.\u00d3.invoke((Object)this, event);
        }
    }
    
    public void removeOnFormUpdated(final EventHandler eventHandler) {
        this.\u00d5 = (EventHandler)Delegate.remove((Delegate)this.\u00d5, (Delegate)eventHandler);
    }
    
    protected void onSaveModified(final CancelEvent cancelEvent) {
        if (this.\u00da != null) {
            this.\u00da.invoke((Object)this, cancelEvent);
        }
    }
    
    public boolean getEOF() {
        final Recordset recordset = this.getRecordset();
        return recordset == null || !recordset.isOpen() || recordset.getEOF();
    }
    
    public double getDouble(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getDouble(\u00ea);
        }
        return 0.0;
    }
    
    public void setDouble(final String s, final double n) {
        this.setDouble(this.findIndexAdd(s), n);
    }
    
    public double getDouble(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getDouble();
    }
    
    public void setDouble(final int n, final double double1) {
        this.\u00c4();
        this.\u00c4[n].setDouble(double1);
    }
    
    public void removeOnAutoSelectAborted(final EventHandler eventHandler) {
        this.\u00d9 = (EventHandler)Delegate.remove((Delegate)this.\u00d9, (Delegate)eventHandler);
    }
    
    protected void onRecordAdded(final Event event) {
        if (this.\u00cd != null) {
            this.\u00cd.invoke((Object)this, event);
        }
    }
    
    public boolean isFirstRecord() {
        final Recordset recordset = this.getRecordset();
        if (recordset == null || !recordset.isOpen()) {
            return false;
        }
        final int absolutePosition = recordset.getAbsolutePosition();
        return absolutePosition != -1 && absolutePosition != -2 && absolutePosition != -3 && absolutePosition == 1;
    }
    
    public void addOnCopyingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00ce = (CancelEventHandler)Delegate.combine((Delegate)this.\u00ce, (Delegate)cancelEventHandler);
    }
    
    public String getString(final String s) {
        final int \u00ea = this.\u00ca(s);
        if (\u00ea >= 0) {
            return this.getString(\u00ea);
        }
        return null;
    }
    
    public void setString(final String s, final String s2) {
        this.setString(this.findIndexAdd(s), s2);
    }
    
    public String getString(final int n) {
        this.\u00c4();
        return this.\u00c4[n].getString();
    }
    
    public void setString(final int n, final String string) {
        this.\u00c4();
        this.\u00c4[n].setString(string);
    }
    
    public void clearManager() {
        if (this.\u00c4 != null) {
            for (int i = 0; i < this.\u00c4.length; ++i) {
                this.\u00c4[i].\u00f0.putNull();
            }
        }
        this.\u00c7 = false;
    }
    
    protected void onUpdatingRecord(final CancelEvent cancelEvent) {
        if (this.\u00d0 != null) {
            this.\u00d0.invoke((Object)this, cancelEvent);
        }
    }
    
    public void resetNotifications() {
        if (this.\u00c1 != null) {
            this.\u00c1.removeOnMoveComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
            this.\u00c1.removeOnFieldChangeComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
        }
        if (this.\u00c2 != null) {
            this.\u00c2.removeOnMoveComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
            this.\u00c2.removeOnFieldChangeComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
        }
    }
    
    public void setNotifications() {
        if (this.\u00c1 != null) {
            this.\u00c1.addOnMoveComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
            this.\u00c1.addOnFieldChangeComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
        }
        if (this.\u00c2 != null) {
            this.\u00c2.addOnMoveComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
            this.\u00c2.addOnFieldChangeComplete(new RecordsetEventHandler((Object)this, "_moveComplete"));
        }
    }
    
    public void removeOnDeletingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00d2 = (CancelEventHandler)Delegate.remove((Delegate)this.\u00d2, (Delegate)cancelEventHandler);
    }
    
    public void excludeFromUpdate(final String anObject, final boolean b) {
        this.\u00c4();
        for (int i = 0; i < this.\u00c4.length; ++i) {
            if (this.\u00c4[i].\u00ee.equals(anObject)) {
                this.\u00c4[i].\u00f2 = !b;
            }
        }
    }
    
    public void update() {
        this.updateRecordFromForm();
        final CancelEvent cancelEvent = new CancelEvent();
        this.onUpdatingRecord(cancelEvent);
        if (cancelEvent.cancel) {
            return;
        }
        this.resetNotifications();
        try {
            this.\u00c2().update();
            this.\u00c7 = false;
        }
        finally {
            this.setNotifications();
        }
        this.onRecordUpdated(new Event());
    }
    
    public void addOnUpdatingForm(final EventHandler eventHandler) {
        this.\u00d4 = (EventHandler)Delegate.combine((Delegate)this.\u00d4, (Delegate)eventHandler);
    }
    
    public void requery() {
        if (!this.saveModified()) {
            return;
        }
        this.resetNotifications();
        try {
            if (this.\u00c1 != null) {
                this.\u00c1.requery();
            }
            else {
                this.\u00c2().requery();
            }
            this.\u00c1();
        }
        finally {
            this.setNotifications();
        }
        this.updateFormFromRecord();
    }
    
    public void addNew() {
        if (!this.saveModified()) {
            return;
        }
        final CancelEvent cancelEvent = new CancelEvent();
        this.onAddingRecord(cancelEvent);
        if (cancelEvent.cancel) {
            return;
        }
        try {
            this.\u00df = true;
            this.resetNotifications();
            try {
                this.\u00c2().addNew();
            }
            finally {
                this.setNotifications();
            }
            this.clearManager();
            this.onRecordAdded(new Event());
            this.updateRecordFromManager();
            this.updateFormFromManager();
        }
        finally {
            this.\u00df = false;
        }
    }
    
    public void setAbsolutePosition(final int absolutePosition) {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().setAbsolutePosition(absolutePosition);
    }
    
    public void close() {
        if (!this.saveModified()) {
            return;
        }
        final Recordset recordset = this.getRecordset();
        if (recordset != null && recordset.isOpen()) {
            recordset.close();
        }
    }
    
    public int getAbsolutePosition() {
        final Recordset recordset = this.getRecordset();
        if (recordset != null) {
            return recordset.getAbsolutePosition();
        }
        return -1;
    }
    
    private Connection \u00c8() {
        Connection connection = null;
        if (this.\u00c1 != null) {
            connection = this.\u00c1.getConnection();
        }
        else if (this.\u00c2 != null) {
            Command command = this.\u00c2.getActiveCommand();
            if (command == null) {
                command = this.\u00c2.getCommand();
            }
            if (command == null) {
                throw new DataException(2, "The recordset has no current command");
            }
            connection = command.getActiveConnection();
        }
        if (connection == null) {
            throw new DataException(13, "Data source/recordset has no current connection");
        }
        return connection;
    }
    
    public int getRecordCount() {
        final Recordset recordset = this.getRecordset();
        if (recordset != null) {
            return recordset.getRecordCount();
        }
        return -1;
    }
    
    public void removeOnRecordCopied(final EventHandler eventHandler) {
        this.\u00cf = (EventHandler)Delegate.remove((Delegate)this.\u00cf, (Delegate)eventHandler);
    }
    
    public void removeOnRecordUpdated(final EventHandler eventHandler) {
        this.\u00d1 = (EventHandler)Delegate.remove((Delegate)this.\u00d1, (Delegate)eventHandler);
    }
    
    public void addOnRecordDeleted(final EventHandler eventHandler) {
        this.\u00d3 = (EventHandler)Delegate.combine((Delegate)this.\u00d3, (Delegate)eventHandler);
    }
    
    public void addOnAutoSelectExecuted(final EventHandler eventHandler) {
        this.\u00d8 = (EventHandler)Delegate.combine((Delegate)this.\u00d8, (Delegate)eventHandler);
    }
    
    public void addOnAutoSelectAborted(final EventHandler eventHandler) {
        this.\u00d9 = (EventHandler)Delegate.combine((Delegate)this.\u00d9, (Delegate)eventHandler);
    }
    
    public void addOnSaveModified(final CancelEventHandler cancelEventHandler) {
        this.\u00da = (CancelEventHandler)Delegate.combine((Delegate)this.\u00da, (Delegate)cancelEventHandler);
    }
    
    private Control \u00c9(final Control control, final String anObject) {
        if (control != null) {
            for (Control control2 = control.getNextControl((Control)null, true); control2 != null; control2 = control.getNextControl(control2, true)) {
                final String name = ((Component)control2).getName();
                if (name != null && name.equals(anObject)) {
                    return control2;
                }
            }
            for (Control control3 = control.getNextControl((Control)null, true); control3 != null; control3 = control.getNextControl(control3, true)) {
                final Control \u00e9 = this.\u00c9(control3, anObject);
                if (\u00e9 != null) {
                    return \u00e9;
                }
            }
        }
        return null;
    }
    
    public void setFields(final DataManagerField[] \u00e4) {
        this.\u00c4 = \u00e4;
    }
    
    public void setWhereClause(final String s) {
        this.\u00c6 = ((s != null) ? ((s.length() != 0) ? s : null) : null);
    }
    
    public String getWhereClause() {
        return this.\u00c6;
    }
    
    public DataManagerField[] getFields() {
        return this.\u00c4;
    }
    
    public void setAutoSelectMode(final boolean \u00e0) {
        this.\u00c4();
        for (int i = 0; i < this.\u00c4.length; ++i) {
            final int \u00f1 = this.\u00c4[i].\u00f1;
            if (!this.\u00c4[i].\u00f3 || (\u00f1 != 8 && \u00f1 != 3 && \u00f1 != 2 && \u00f1 != 17 && \u00f1 != 5 && \u00f1 != 4 && \u00f1 != 7 && \u00f1 != 11)) {
                final Control boundControl = this.getBoundControl(this.\u00c4[i]);
                if (boundControl != null) {
                    boundControl.setEnabled(!\u00e0);
                }
            }
        }
        this.\u00e0 = \u00e0;
    }
    
    public boolean getAutoSelectMode() {
        return this.\u00e0;
    }
    
    private int \u00ca(final String anObject) {
        if (this.\u00c4 != null) {
            for (int i = 0; i < this.\u00c4.length; ++i) {
                if (this.\u00c4[i].\u00ee.equals(anObject)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void moveNext() {
        if (!this.saveModified()) {
            return;
        }
        this.\u00c2().moveNext();
    }
    
    public void updateFormFromRecord() {
        this.updateManagerFromRecord();
        this.updateFormFromManager();
    }
    
    public void removeOnAddingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00cc = (CancelEventHandler)Delegate.remove((Delegate)this.\u00cc, (Delegate)cancelEventHandler);
    }
    
    protected void onCopyingRecord(final CancelEvent cancelEvent) {
        if (this.\u00ce != null) {
            this.\u00ce.invoke((Object)this, cancelEvent);
        }
    }
    
    public void addOnUpdatingRecord(final CancelEventHandler cancelEventHandler) {
        this.\u00d0 = (CancelEventHandler)Delegate.combine((Delegate)this.\u00d0, (Delegate)cancelEventHandler);
    }
    
    private void \u00cb(final boolean b) {
        if (this.\u00ca == null) {
            this.\u00ca = new DataManagerBinder();
        }
        if (this.\u00c4 != null) {
            for (int i = 0; i < this.\u00c4.length; ++i) {
                if (b || this.\u00c4[i].\u00f2) {
                    final Control boundControl = this.getBoundControl(this.\u00c4[i]);
                    if (boundControl != null) {
                        this.\u00ca.updateData(boundControl, this.\u00c4[i], b);
                    }
                }
            }
        }
    }
    
    public void setComponentSite(final IComponentSite componentSite) {
        if (componentSite != null) {
            final Object service = componentSite.getService((DataManager.\u00e5 != null) ? DataManager.\u00e5 : (DataManager.\u00e5 = \u00c6("com.ms.wfc.ui.AxHost$PeerInformation")));
            if (service instanceof AxHost$PeerInformation) {
                ((AxHost$PeerInformation)service).request();
            }
        }
        super.setComponentSite(componentSite);
        _cls753A._mth563B(this);
    }
}
