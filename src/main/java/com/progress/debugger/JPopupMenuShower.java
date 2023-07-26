// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;

public class JPopupMenuShower extends MouseAdapter
{
    private JPopupMenu popup;
    private boolean forceRowSelection;
    private boolean isDebugListingTable;
    private JTable theTable;
    private int previousDebugListingSelectedRow;
    private JTextField textField;
    private boolean debuggerTextField;
    private int startSelectPosition;
    private int endSelectPosition;
    
    public JPopupMenuShower(final JPopupMenu popup, final JTable theTable, final boolean forceRowSelection, final boolean isDebugListingTable) {
        this.forceRowSelection = true;
        this.previousDebugListingSelectedRow = 0;
        this.debuggerTextField = false;
        this.startSelectPosition = 0;
        this.endSelectPosition = 0;
        this.popup = popup;
        this.theTable = theTable;
        this.forceRowSelection = forceRowSelection;
        this.isDebugListingTable = isDebugListingTable;
    }
    
    public JPopupMenuShower(final JPopupMenu popup, final JTable theTable, final JTextField textField, final boolean forceRowSelection, final boolean isDebugListingTable) {
        this.forceRowSelection = true;
        this.previousDebugListingSelectedRow = 0;
        this.debuggerTextField = false;
        this.startSelectPosition = 0;
        this.endSelectPosition = 0;
        this.popup = popup;
        this.theTable = theTable;
        this.forceRowSelection = forceRowSelection;
        this.isDebugListingTable = isDebugListingTable;
        this.debuggerTextField = true;
        this.textField = textField;
    }
    
    private void showIfPopupTrigger(final MouseEvent e) {
        if (this.popup.isPopupTrigger(e)) {
            final Point point = new Point(e.getX(), e.getY());
            if (this.theTable != null) {
                final int rowAtPoint = this.theTable.rowAtPoint(point);
                if ((this.forceRowSelection && rowAtPoint != this.theTable.getSelectedRow() && !this.theTable.isEditing()) || this.isDebugListingTable) {
                    if (this.isDebugListingTable && this.theTable.isEditing()) {
                        if (this.previousDebugListingSelectedRow != rowAtPoint && rowAtPoint != 0) {
                            this.theTable.getCellEditor(this.previousDebugListingSelectedRow, 0).stopCellEditing();
                            this.theTable.changeSelection(rowAtPoint, 0, true, false);
                        }
                        this.previousDebugListingSelectedRow = rowAtPoint;
                    }
                    else {
                        this.theTable.changeSelection(rowAtPoint, 0, true, false);
                    }
                }
            }
            if (this.theTable != null) {
                int height = this.popup.getHeight();
                if (height <= 0) {
                    height = 111;
                }
                if (!this.isDebugListingTable) {
                    this.popup.show(e.getComponent(), e.getX(), e.getY() - height + 4);
                }
                else {
                    this.popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            else {
                this.popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
        this.showIfPopupTrigger(mouseEvent);
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
        this.showIfPopupTrigger(mouseEvent);
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
    }
}
