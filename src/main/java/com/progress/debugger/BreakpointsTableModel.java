// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

class BreakpointsTableModel extends AbstractTableModel
{
    Vector myData;
    Object[] columnNames;
    
    public BreakpointsTableModel(final Vector myData) {
        this.columnNames = new Object[] { " " };
        this.myData = myData;
    }
    
    public String getColumnName(final int n) {
        return this.columnNames[n].toString();
    }
    
    public int getRowCount() {
        return this.myData.size();
    }
    
    public int getColumnCount() {
        return this.columnNames.length;
    }
    
    public Object getValueAt(final int index, final int index2) {
        if (index >= this.myData.size()) {
            return null;
        }
        return this.myData.elementAt(index).elementAt(index2);
    }
    
    public boolean isCellEditable(final int n, final int n2) {
        return false;
    }
}
