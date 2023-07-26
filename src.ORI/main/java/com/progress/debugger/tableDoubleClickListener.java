// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.JDialog;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;

public class tableDoubleClickListener extends MouseAdapter
{
    Frame1 application;
    JTable table;
    
    public tableDoubleClickListener(final Frame1 application, final JTable table) {
        this.application = application;
        this.table = table;
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && this.table.getValueAt(this.table.getSelectedRow(), this.table.getSelectedColumn()).toString().compareTo("{...}") == 0) {
            final JDialog dialog = new JDialog();
            dialog.setModal(true);
            dialog.setName("expand value");
            dialog.setTitle("expand value");
            dialog.show();
        }
    }
}
