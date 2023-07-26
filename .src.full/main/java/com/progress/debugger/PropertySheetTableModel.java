// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class PropertySheetTableModel extends AbstractTableModel
{
    Frame1 application;
    Vector dataviewData;
    Vector typeData;
    Vector flagsData;
    Object[] columnNames;
    
    public PropertySheetTableModel(final Frame1 application, final Vector dataviewData, final Vector typeData, final Vector flagsData) {
        this.columnNames = new Object[] { "Name", "Value", "Type" };
        this.application = application;
        this.dataviewData = dataviewData;
        this.typeData = typeData;
        this.flagsData = flagsData;
    }
    
    public String getColumnName(final int n) {
        return this.columnNames[n].toString();
    }
    
    public int getRowCount() {
        return this.dataviewData.size();
    }
    
    public int getColumnCount() {
        return this.columnNames.length;
    }
    
    public Object getValueAt(final int index, final int index2) {
        return this.dataviewData.elementAt(index).elementAt(index2);
    }
    
    public void setValueAt(final Object obj, final int index, final int index2) {
        int n;
        if (this.application.dataViewDlg.tabbedPaneIndex == 0) {
            n = this.application.dataViewDlg.lastAttributeValueBeforeEdit.compareTo(obj.toString());
        }
        else {
            n = this.application.dataViewDlg.lastFieldValueBeforeEdit.compareTo(obj.toString());
        }
        if (n != 0) {
            this.dataviewData.elementAt(index).setElementAt(obj, index2);
            this.application.sendSocket.sendMessage("ASSIGN ".concat(this.application.dataViewDlg.jComboBox1.getEditor().getItem().toString()).concat(" = ").concat(obj.toString()));
            if (this.application.dataViewDlg.tabbedPaneIndex == 0) {
                this.application.dataViewDlg.triggerAttributeUpdate = true;
                final Frame1 application = this.application;
                final PropertySheetDialogBox dataViewDlg = this.application.dataViewDlg;
                application.requestGetAttrs(PropertySheetDialogBox.originalName);
            }
            else {
                this.application.dataViewDlg.triggerFieldUpdate = true;
                final Frame1 application2 = this.application;
                final PropertySheetDialogBox dataViewDlg2 = this.application.dataViewDlg;
                application2.requestGetFields(PropertySheetDialogBox.originalName);
            }
            this.application.requestUpdates(false);
        }
    }
    
    public boolean isCellEditable(final int index, final int n) {
        final int index2 = this.flagsData.elementAt(index).toString().indexOf("W");
        return n != 0 && index2 != -1 && n != 2;
    }
}
