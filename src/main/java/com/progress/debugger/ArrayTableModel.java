// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class ArrayTableModel extends AbstractTableModel
{
    Frame1 application;
    Vector arrayData;
    Object[] arrayColumnNames;
    Vector flags;
    
    public ArrayTableModel(final Frame1 application, final Vector arrayData, final Vector flags) {
        this.arrayColumnNames = new Object[] { "Index", "Value" };
        this.application = application;
        this.arrayData = arrayData;
        this.flags = flags;
    }
    
    public String getColumnName(final int n) {
        return this.arrayColumnNames[n].toString();
    }
    
    public int getRowCount() {
        return this.arrayData.size();
    }
    
    public int getColumnCount() {
        return this.arrayColumnNames.length;
    }
    
    public Object getValueAt(final int index, final int index2) {
        try {
            return this.arrayData.elementAt(index).elementAt(index2);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void setValueAt(final Object obj, final int index, final int index2) {
        if (this.application.dataViewDlg.lastArrayValueBeforeEdit.compareTo(obj.toString()) != 0) {
            this.application.dataViewDlg.requestWait();
            this.arrayData.elementAt(index).setElementAt(obj, index2);
            this.application.sendSocket.sendMessage("ASSIGN ".concat(this.application.dataViewDlg.jComboBox1.getEditor().getItem().toString()).concat(" = ").concat(obj.toString()));
            this.application.dataViewDlg.triggerArrayUpdate = true;
            this.application.requestGetArray(this.application.dataViewDlg.lastArrayViewName);
            this.application.requestUpdates(false);
        }
    }
    
    public boolean isCellEditable(final int index, final int n) {
        return n != 0 && this.flags.elementAt(index).toString().compareToIgnoreCase("R") != 0;
    }
}
