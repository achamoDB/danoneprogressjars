// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableRenderer implements TableCellRenderer
{
    Frame1 application;
    
    public TableRenderer(final Frame1 application) {
        this.application = application;
    }
    
    public Component getTableCellRendererComponent(final JTable table, final Object o, final boolean b, final boolean b2, final int n, final int column) {
        TableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
        if (defaultRenderer == null) {
            defaultRenderer = table.getDefaultRenderer(table.getColumnClass(column));
        }
        final Component tableCellRendererComponent = defaultRenderer.getTableCellRendererComponent(table, o, b, b2, n, column);
        if (n == this.application.focusLine - 1) {
            if (this.application.makeFocusLineYellow) {
                tableCellRendererComponent.setForeground(Color.black);
                tableCellRendererComponent.setBackground(table.getBackground());
            }
            else {
                tableCellRendererComponent.setForeground(Color.black);
                tableCellRendererComponent.setBackground(table.getBackground());
            }
        }
        else {
            tableCellRendererComponent.setForeground(table.getForeground());
            tableCellRendererComponent.setBackground(table.getBackground());
        }
        if (this.application.frameUtl.findHasBeenActivated && n == this.application.frameUtl.getRowIndex()) {
            tableCellRendererComponent.setForeground(Color.blue);
        }
        return tableCellRendererComponent;
    }
}
