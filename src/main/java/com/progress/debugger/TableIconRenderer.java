// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableIconRenderer extends DefaultTableCellRenderer
{
    JTable theTable;
    int thisColumn;
    
    public TableIconRenderer(final JTable theTable) {
        this.thisColumn = 0;
        this.theTable = theTable;
    }
    
    protected void setValue(final Object o) {
        this.thisColumn = this.theTable.getSelectedColumn();
        this.setIcon((Icon)o);
        this.setHorizontalAlignment(4);
    }
}
