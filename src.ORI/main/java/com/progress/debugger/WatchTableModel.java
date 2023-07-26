// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class WatchTableModel extends AbstractTableModel
{
    Frame1 application;
    Vector watchData;
    Vector watchNumbers;
    Object[] columnNames;
    Object[] arrayColumnNames;
    Vector flags;
    boolean isWatchTable;
    
    public WatchTableModel(final Frame1 application, final Vector watchData, final Vector watchNumbers, final Vector flags, final boolean isWatchTable) {
        this.columnNames = new Object[] { "Name", "Value" };
        this.arrayColumnNames = new Object[] { "Index", "Value" };
        this.application = application;
        this.watchData = watchData;
        this.watchNumbers = watchNumbers;
        this.flags = flags;
        this.isWatchTable = isWatchTable;
    }
    
    public String getColumnName(final int n) {
        if (this.isWatchTable) {
            return this.columnNames[n].toString();
        }
        return this.arrayColumnNames[n].toString();
    }
    
    public int getRowCount() {
        return this.watchData.size();
    }
    
    public int getColumnCount() {
        if (this.isWatchTable) {
            return this.columnNames.length;
        }
        return this.arrayColumnNames.length;
    }
    
    public Object getValueAt(final int index, final int index2) {
        return this.watchData.elementAt(index).elementAt(index2);
    }
    
    public void setValueAt(final Object o, final int index, final int n) {
        if (n == 0) {
            final int compareTo = this.application.watchTableNameValueBeforeEdit.compareTo(o.toString());
            if (compareTo != 0 && index != this.watchData.size() - 1) {
                String s;
                if (o.toString().length() == 0) {
                    s = "cancel watch #";
                }
                else {
                    s = "update watch #";
                }
                this.application.sendSocket.sendMessage(s.concat(this.watchNumbers.elementAt(index).toString()).concat(" ").concat(o.toString()));
                this.application.requestUpdates(true);
            }
            else if (compareTo != 0 && index == this.watchData.size() - 1) {
                this.application.sendSocket.sendMessage("watch ".concat(o.toString()));
                this.application.requestUpdates(true);
            }
        }
        else if (this.application.watchTableValueValueBeforeEdit.compareTo(o.toString()) != 0 && index != this.watchData.size() - 1) {
            this.application.sendSocket.sendMessage("ASSIGN ".concat(this.application.watchTableNameValueBeforeEdit).concat(" = ").concat(o.toString()));
            this.application.requestUpdates(true);
        }
    }
    
    public boolean isCellEditable(final int index, final int n) {
        int compareToIgnoreCase = -1;
        if (index != this.watchData.size() - 1) {
            compareToIgnoreCase = this.flags.elementAt(index).toString().compareToIgnoreCase("R");
        }
        return n != 1 || compareToIgnoreCase != 0;
    }
}
