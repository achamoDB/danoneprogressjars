// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JTable;
import java.util.EventObject;
import javax.swing.border.Border;
import javax.swing.JComponent;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionListener;

public class SingleClickCellEditor extends BasicCellEditor implements ActionListener, TableCellEditor
{
    protected Object value;
    protected Color foreground;
    protected Color background;
    protected Font font;
    protected JTextField textField;
    
    public SingleClickCellEditor(final JTextField textField) {
        super(textField);
        (this.textField = textField).addActionListener(this);
        this.textField.setBorder(null);
        this.textField.setEditable(false);
    }
    
    public void setForeground(final Color color) {
        this.foreground = color;
        super.editor.setForeground(color);
    }
    
    public void setBackground(final Color color) {
        this.background = color;
        super.editor.setBackground(color);
    }
    
    public void setFont(final Font font) {
        this.font = font;
        super.editor.setFont(font);
    }
    
    public Object getCellEditorValue() {
        return this.value;
    }
    
    public void editingStarted(final EventObject eventObject) {
    }
    
    public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean b, final int n, final int n2) {
        super.editor.setForeground((this.foreground != null) ? this.foreground : table.getForeground());
        super.editor.setBackground((this.background != null) ? this.background : table.getBackground());
        super.editor.setFont((this.font != null) ? this.font : table.getFont());
        this.setValue(this.value = value);
        table.getSelectionModel().setSelectionMode(0);
        table.getSelectionModel().setSelectionInterval(n, n);
        table.setRowSelectionAllowed(false);
        return super.editor;
    }
    
    protected void setValue(final Object o) {
        this.textField = (JTextField)super.editor;
        if (o == null) {
            this.textField.setText("");
        }
        else {
            this.textField.setText(o.toString());
        }
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        this.stopCellEditing();
    }
}
