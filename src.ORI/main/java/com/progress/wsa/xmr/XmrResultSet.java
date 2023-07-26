// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.sql.SQLException;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import java.util.Vector;
import com.progress.open4gl.InputResultSet;

public class XmrResultSet extends InputResultSet
{
    private Vector rows;
    private int rowNum;
    private Vector currentRow;
    private ResultSetMetaData schema;
    
    public XmrResultSet() {
        this.rows = new Vector();
        this.rowNum = 0;
        this.currentRow = null;
        this.schema = null;
    }
    
    public boolean next() throws SQLException {
        try {
            this.currentRow = this.rows.elementAt(this.rowNum++);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public Object getObject(final int n) throws SQLException {
        Object element = null;
        try {
            element = this.currentRow.elementAt(n - 1);
        }
        catch (Exception ex) {}
        return element;
    }
    
    public void addRow(final Vector obj) {
        this.rows.addElement(obj);
    }
    
    public void setMetaData(final ResultSetMetaData schema) {
        this.schema = schema;
    }
    
    public java.sql.ResultSetMetaData getMetaData() {
        return this.schema;
    }
}
