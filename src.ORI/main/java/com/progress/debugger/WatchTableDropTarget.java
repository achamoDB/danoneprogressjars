// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.net.MalformedURLException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.datatransfer.FlavorMap;
import java.awt.Component;
import javax.swing.JTable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

public class WatchTableDropTarget implements DropTargetListener
{
    protected DropTarget dropTarget;
    protected boolean acceptableType;
    protected Frame1 application;
    protected JTable watchTable;
    
    public WatchTableDropTarget(final Frame1 application, final JTable watchTable) {
        this.application = application;
        this.watchTable = watchTable;
        this.dropTarget = new DropTarget(this.watchTable, 3, this, true, null);
    }
    
    public void dragEnter(final DropTargetDragEvent dropTargetDragEvent) {
        DnDUtils.debugPrintln("dragEnter, drop action = " + DnDUtils.showActions(dropTargetDragEvent.getDropAction()));
        this.checkTransferType(dropTargetDragEvent);
        this.acceptOrRejectDrag(dropTargetDragEvent);
    }
    
    public void dragExit(final DropTargetEvent dropTargetEvent) {
        DnDUtils.debugPrintln("DropTarget dragExit");
    }
    
    public void dragOver(final DropTargetDragEvent dropTargetDragEvent) {
        DnDUtils.debugPrintln("DropTarget dragOver, drop action = " + DnDUtils.showActions(dropTargetDragEvent.getDropAction()));
        this.acceptOrRejectDrag(dropTargetDragEvent);
    }
    
    public void dropActionChanged(final DropTargetDragEvent dropTargetDragEvent) {
        DnDUtils.debugPrintln("DropTarget dropActionChanged, drop action = " + DnDUtils.showActions(dropTargetDragEvent.getDropAction()));
        this.acceptOrRejectDrag(dropTargetDragEvent);
    }
    
    public void drop(final DropTargetDropEvent dropTargetDropEvent) {
        DnDUtils.debugPrintln("DropTarget drop, drop action = " + DnDUtils.showActions(dropTargetDropEvent.getDropAction()));
        if ((dropTargetDropEvent.getDropAction() & 0x3) != 0x0) {
            dropTargetDropEvent.acceptDrop(dropTargetDropEvent.getDropAction());
            final Transferable transferable = dropTargetDropEvent.getTransferable();
            try {
                final boolean dropString = this.dropString(transferable);
                dropTargetDropEvent.dropComplete(dropString);
                DnDUtils.debugPrintln("Drop completed, success: " + dropString);
            }
            catch (Exception obj) {
                DnDUtils.debugPrintln("Exception while handling drop " + obj);
                dropTargetDropEvent.dropComplete(false);
            }
        }
        else {
            DnDUtils.debugPrintln("Drop target rejected drop");
            dropTargetDropEvent.rejectDrop();
        }
    }
    
    protected boolean acceptOrRejectDrag(final DropTargetDragEvent dropTargetDragEvent) {
        final int dropAction = dropTargetDragEvent.getDropAction();
        final int sourceActions = dropTargetDragEvent.getSourceActions();
        boolean b = false;
        DnDUtils.debugPrintln("\tSource actions are " + DnDUtils.showActions(sourceActions) + ", drop action is " + DnDUtils.showActions(dropAction));
        if (!this.acceptableType || (sourceActions & 0x3) == 0x0) {
            DnDUtils.debugPrintln("Drop target rejecting drag");
            dropTargetDragEvent.rejectDrag();
        }
        else if ((dropAction & 0x3) == 0x0) {
            DnDUtils.debugPrintln("Drop target offering COPY");
            dropTargetDragEvent.acceptDrag(1);
            b = true;
        }
        else {
            DnDUtils.debugPrintln("Drop target accepting drag");
            dropTargetDragEvent.acceptDrag(dropAction);
            b = true;
        }
        return b;
    }
    
    protected void checkTransferType(final DropTargetDragEvent dropTargetDragEvent) {
        this.acceptableType = dropTargetDragEvent.isDataFlavorSupported(DataFlavor.stringFlavor);
        DnDUtils.debugPrintln("File type acceptable - " + this.acceptableType);
    }
    
    protected boolean dropString(final Transferable transferable) throws IOException, UnsupportedFlavorException, MalformedURLException {
        this.application.sendSocket.sendMessage("watch ".concat((String)transferable.getTransferData(DataFlavor.stringFlavor)));
        this.application.sendSocket.sendMessage("show watch");
        return true;
    }
}
