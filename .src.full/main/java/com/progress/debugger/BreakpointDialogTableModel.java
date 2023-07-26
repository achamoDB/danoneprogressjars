// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class BreakpointDialogTableModel extends AbstractTableModel
{
    Object[] columnNames;
    Vector tableData;
    
    public BreakpointDialogTableModel(final Vector tableData) {
        this.columnNames = new Object[] { "", "" };
        this.tableData = tableData;
    }
    
    public String getColumnName(final int n) {
        return this.columnNames[n].toString();
    }
    
    public int getColumnCount() {
        return this.columnNames.length;
    }
    
    public int getRowCount() {
        return this.tableData.size();
    }
    
    public Object getValueAt(final int index, final int index2) {
        return this.tableData.elementAt(index).elementAt(index2);
    }
    
    public boolean isCellEditable(final int n, final int n2) {
        return n2 == 0;
    }
}
