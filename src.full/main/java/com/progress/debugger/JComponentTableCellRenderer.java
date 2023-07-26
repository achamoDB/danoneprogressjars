// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.JComponent;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JComponentTableCellRenderer implements TableCellRenderer
{
    public Component getTableCellRendererComponent(final JTable table, final Object o, final boolean b, final boolean b2, final int n, final int n2) {
        return (JComponent)o;
    }
}
