// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import java.sql.SQLException;
import java.util.Vector;
import com.progress.open4gl.InputResultSet;

public class OutMessageBodySet extends InputResultSet
{
    public static final int MSG_ID = 1;
    public static final int PART_ID = 2;
    public static final int ITEM_NAME = 3;
    public static final int ITEM_DATA_TYPE = 4;
    public static final int VALUE = 5;
    public static final int BYTES_VALUE = 6;
    public static final int STREAM_SEQ = 7;
    public static final int COMP_SEQ = 8;
    protected Vector rows;
    protected int numRows;
    private int currentRowNum;
    private Vector currentRow;
    
    void addRow(final Integer obj, final Integer obj2, final String obj3, final Integer obj4, final String obj5, final byte[] obj6, final Integer obj7) {
        final Vector<Integer> obj8 = new Vector<Integer>();
        obj8.addElement(obj);
        obj8.addElement(obj2);
        obj8.addElement((Integer)obj3);
        obj8.addElement(obj4);
        obj8.addElement((Integer)obj5);
        obj8.addElement((Integer)(Object)obj6);
        obj8.addElement(obj7);
        obj8.addElement(null);
        this.rows.addElement(obj8);
        ++this.numRows;
    }
    
    void addRow(final Integer obj, final Integer obj2, final String obj3, final Integer obj4, final String obj5, final byte[] obj6, final Integer obj7, final Integer obj8) {
        final Vector<Integer> obj9 = new Vector<Integer>();
        obj9.addElement(obj);
        obj9.addElement(obj2);
        obj9.addElement((Integer)obj3);
        obj9.addElement(obj4);
        obj9.addElement((Integer)obj5);
        obj9.addElement((Integer)(Object)obj6);
        obj9.addElement(obj7);
        obj9.addElement(obj8);
        this.rows.addElement(obj9);
        ++this.numRows;
    }
    
    OutMessageBodySet() {
        this.currentRow = null;
        this.currentRowNum = 0;
        this.numRows = 0;
        this.rows = new Vector();
    }
    
    public boolean next() throws SQLException {
        if (this.currentRowNum >= this.numRows) {
            return false;
        }
        this.currentRow = this.rows.elementAt(this.currentRowNum++);
        return true;
    }
    
    public Object getObject(final int n) throws SQLException {
        return this.currentRow.elementAt(n - 1);
    }
    
    public String getString(final int n) throws SQLException {
        return this.currentRow.elementAt(n - 1);
    }
    
    public int getInt(final int n) throws SQLException {
        return this.currentRow.elementAt(n - 1);
    }
}
