// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.JComponent;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;

public class JComponentTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
    JCheckBox theBox;
    
    public JComponentTableCellEditor() {
        this.theBox = new JCheckBox();
    }
    
    public Object getCellEditorValue() {
        return this.theBox;
    }
    
    public Component getTableCellEditorComponent(final JTable table, final Object o, final boolean b, final int n, final int n2) {
        this.theBox = (JCheckBox)o;
        return (JComponent)o;
    }
}
