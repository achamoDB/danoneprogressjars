// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class ReadOnlyTableModel extends AbstractTableModel
{
    Object[] columnNames;
    Vector tableData;
    
    public ReadOnlyTableModel(final Vector tableData) {
        this.columnNames = new Object[] { "Name", "Type" };
        this.tableData = tableData;
    }
    
    public String getColumnName(final int n) {
        return this.columnNames[n].toString();
    }
    
    public int getRowCount() {
        return this.tableData.size();
    }
    
    public int getColumnCount() {
        return this.columnNames.length;
    }
    
    public Object getValueAt(final int index, final int index2) {
        return this.tableData.elementAt(index).elementAt(index2);
    }
    
    public boolean isCellEditable(final int n, final int n2) {
        return false;
    }
}
