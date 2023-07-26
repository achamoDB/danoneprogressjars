// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.event.InputEvent;
import java.util.Iterator;
import java.awt.datatransfer.Transferable;
import java.awt.Cursor;
import java.awt.dnd.DragGestureEvent;
import java.awt.Component;
import java.awt.dnd.DragSource;
import javax.swing.JTextField;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragGestureListener;

public class JTextFieldDragSource implements DragGestureListener, DragSourceListener
{
    JTextField watchField;
    
    public JTextFieldDragSource(final JTextField watchField) {
        this.watchField = watchField;
        DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this.watchField, 3, this);
    }
    
    public void dragGestureRecognized(final DragGestureEvent dragGestureEvent) {
        if (DnDUtils.isDebugEnabled()) {
            DnDUtils.debugPrintln("Initiating event is " + dragGestureEvent.getTriggerEvent());
            DnDUtils.debugPrintln("Complete event set is:");
            final Iterator<InputEvent> iterator = dragGestureEvent.iterator();
            while (iterator.hasNext()) {
                DnDUtils.debugPrintln("\t" + iterator.next());
            }
        }
        dragGestureEvent.startDrag(null, new JTextFieldTransferable(this.watchField), this);
    }
    
    public void dragEnter(final DragSourceDragEvent dragSourceDragEvent) {
        DnDUtils.debugPrintln("Drag Source: dragEnter, drop action = " + DnDUtils.showActions(dragSourceDragEvent.getDropAction()));
    }
    
    public void dragOver(final DragSourceDragEvent dragSourceDragEvent) {
        DnDUtils.debugPrintln("Drag Source: dragOver, drop action = " + DnDUtils.showActions(dragSourceDragEvent.getDropAction()));
    }
    
    public void dragExit(final DragSourceEvent dragSourceEvent) {
        DnDUtils.debugPrintln("Drag Source: dragExit");
    }
    
    public void dropActionChanged(final DragSourceDragEvent dragSourceDragEvent) {
        DnDUtils.debugPrintln("Drag Source: dropActionChanged, drop action = " + DnDUtils.showActions(dragSourceDragEvent.getDropAction()));
    }
    
    public void dragDropEnd(final DragSourceDropEvent dragSourceDropEvent) {
        DnDUtils.debugPrintln("Drag Source: drop completed, drop action = " + DnDUtils.showActions(dragSourceDropEvent.getDropAction()) + ", success: " + dragSourceDropEvent.getDropSuccess());
    }
}
