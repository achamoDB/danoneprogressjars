// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;

public class StackListCellRenderer implements ListCellRenderer
{
    protected DefaultListCellRenderer defaultRenderer;
    
    public StackListCellRenderer() {
        this.defaultRenderer = new DefaultListCellRenderer();
    }
    
    public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
        Icon icon = null;
        final JLabel label = (JLabel)this.defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String text;
        if (value instanceof Object[]) {
            final Object[] array = (Object[])((Object[])value)[0];
            icon = (Icon)array[0];
            text = array[1].toString();
            label.setBackground((Color)array[2]);
        }
        else {
            text = label.getText();
        }
        if (icon != null) {
            label.setIcon(icon);
        }
        label.setText(text);
        label.setForeground(Color.black);
        return label;
    }
}
