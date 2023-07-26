// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class ParameterVariableTableModel extends AbstractTableModel
{
    Object[] columnNames;
    Vector tableData;
    Frame1 application;
    Vector readWriteFlags;
    
    public ParameterVariableTableModel(final Frame1 application, final Vector tableData, final Vector readWriteFlags) {
        this.columnNames = new Object[] { "Name", "Value", "Type" };
        this.application = application;
        this.tableData = tableData;
        this.readWriteFlags = readWriteFlags;
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
    
    public void setValueAt(final Object o, final int n, final int n2) {
        if (this.application.getTabbedPaneIndex() == 0) {
            if (o.toString().compareTo(this.application.variableTableValueValueBeforeEdit) != 0) {
                this.application.sendSocket.sendMessage("ASSIGN ".concat(this.application.variableTableNameValueBeforeEdit).concat(" = ").concat(o.toString()));
                this.application.requestUpdates(true);
            }
        }
        else if (o.toString().compareTo(this.application.parameterTableValueValueBeforeEdit) != 0) {
            this.application.sendSocket.sendMessage("ASSIGN ".concat(this.application.parameterTableNameValueBeforeEdit).concat(" = ").concat(o.toString()));
            this.application.requestUpdates(true);
        }
    }
    
    public boolean isCellEditable(final int index, final int n) {
        final int compareToIgnoreCase = this.readWriteFlags.elementAt(index).toString().compareToIgnoreCase("R");
        return n == 1 && compareToIgnoreCase != 0;
    }
    
    public void setVectorData(final Vector tableData) {
        this.tableData = tableData;
    }
}
