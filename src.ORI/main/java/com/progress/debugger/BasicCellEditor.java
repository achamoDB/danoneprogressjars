// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.beans.PropertyChangeEvent;
import javax.swing.event.CellEditorListener;
import java.util.EventObject;
import javax.swing.event.EventListenerList;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.beans.PropertyChangeListener;
import javax.swing.CellEditor;

public class BasicCellEditor implements CellEditor, PropertyChangeListener
{
    protected static JCheckBox checkBox;
    protected static JTextField textfield;
    protected static ChangeEvent changeEvent;
    protected JComponent editor;
    protected EventListenerList listeners;
    protected EventObject editingEvent;
    
    public BasicCellEditor() {
        this.listeners = new EventListenerList();
        this.editor = null;
    }
    
    public BasicCellEditor(final JComponent editor) {
        this.listeners = new EventListenerList();
        (this.editor = editor).addPropertyChangeListener(this);
    }
    
    public Object getCellEditorValue() {
        return null;
    }
    
    public boolean isCellEditable(final EventObject editingEvent) {
        this.editingEvent = editingEvent;
        return true;
    }
    
    public boolean shouldSelectCell(final EventObject eventObject) {
        return true;
    }
    
    public boolean stopCellEditing() {
        this.fireEditingStopped();
        return true;
    }
    
    public void cancelCellEditing() {
        this.fireEditingCanceled();
    }
    
    public void addCellEditorListener(final CellEditorListener l) {
        this.listeners.add(CellEditorListener.class, l);
    }
    
    public void removeCellEditorListener(final CellEditorListener l) {
        this.listeners.remove(CellEditorListener.class, l);
    }
    
    public JComponent getComponent() {
        return this.editor;
    }
    
    public void setComponent(final JComponent editor) {
        this.editor = editor;
    }
    
    public EventObject getEditingEvent() {
        return this.editingEvent;
    }
    
    public void editingStarted(final EventObject eventObject) {
    }
    
    protected void fireEditingStopped() {
        final Object[] listenerList = this.listeners.getListenerList();
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            if (listenerList[i] == CellEditorListener.class) {
                if (BasicCellEditor.changeEvent == null) {
                    BasicCellEditor.changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener)listenerList[i + 1]).editingStopped(BasicCellEditor.changeEvent);
            }
        }
    }
    
    protected void fireEditingCanceled() {
        final Object[] listenerList = this.listeners.getListenerList();
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            if (listenerList[i] == CellEditorListener.class) {
                if (BasicCellEditor.changeEvent == null) {
                    BasicCellEditor.changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener)listenerList[i + 1]).editingCanceled(BasicCellEditor.changeEvent);
            }
        }
    }
    
    public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("ancestor") && propertyChangeEvent.getNewValue() != null) {
            this.editingStarted(this.editingEvent);
        }
    }
    
    static {
        BasicCellEditor.checkBox = new JCheckBox();
        BasicCellEditor.textfield = new JTextField();
    }
}
