// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.Insets;
import javax.swing.JTable;

public class TableUtilities
{
    public static int calculateColumnWidth(final JTable table, final int n) {
        int n2 = 0;
        for (int rowCount = table.getRowCount(), i = 0; i < rowCount; ++i) {
            final int width = table.getCellRenderer(i, n).getTableCellRendererComponent(table, table.getValueAt(i, n), false, false, i, n).getPreferredSize().width;
            if (width > n2) {
                n2 = width;
            }
        }
        return n2;
    }
    
    public static void setColumnWidths(final JTable table, final Insets insets, final boolean b, final boolean b2) {
        final int columnCount = table.getColumnCount();
        final TableColumnModel columnModel = table.getColumnModel();
        final int n = (insets == null) ? 0 : (insets.left + insets.right);
        for (int i = 0; i < columnCount; ++i) {
            final int maxWidth = calculateColumnWidth(table, i) + n;
            final TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(maxWidth);
            if (b) {
                column.setMinWidth(maxWidth);
            }
            if (b2) {
                column.setMaxWidth(maxWidth);
            }
        }
    }
}
