// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Component;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeCellEditor;
import javax.swing.AbstractCellEditor;

public class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor
{
    CheckBoxNodeRenderer renderer;
    ChangeEvent changeEvent;
    TreePath path;
    Object node;
    Object userObject;
    CheckBoxNode checkBoxNode;
    JTree tree;
    DynamicObjectDialog objectDialog;
    
    public CheckBoxNodeEditor(final JTree tree, final DynamicObjectDialog objectDialog) {
        this.changeEvent = null;
        this.objectDialog = objectDialog;
        this.tree = tree;
        this.renderer = new CheckBoxNodeRenderer(this.objectDialog);
    }
    
    public Object getCellEditorValue() {
        final JCheckBox leafRenderer = this.renderer.getLeafRenderer();
        this.checkBoxNode = new CheckBoxNode(leafRenderer.getText(), leafRenderer.isSelected());
        this.objectDialog.setCheckboxValue(this.checkBoxNode.getText(), this.checkBoxNode.selected);
        return this.checkBoxNode;
    }
    
    public boolean isCellEditable(final EventObject eventObject) {
        boolean b = false;
        if (eventObject instanceof MouseEvent) {
            final MouseEvent mouseEvent = (MouseEvent)eventObject;
            this.path = this.tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
            if (this.path != null) {
                this.node = this.path.getLastPathComponent();
                if (this.node != null && this.node instanceof DefaultMutableTreeNode) {
                    final DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode)this.node;
                    this.userObject = defaultMutableTreeNode.getUserObject();
                    b = (defaultMutableTreeNode.isLeaf() && this.userObject instanceof CheckBoxNode);
                    if (!(this.userObject instanceof CheckBoxNode)) {
                        final String name = ((NamedVector)defaultMutableTreeNode.getUserObject()).name;
                        if (name.compareTo("Database Objects") == 0) {
                            this.objectDialog.toggleDatabaseCheckBox();
                        }
                        if (name.compareTo("User Interface Widgets") == 0) {
                            this.objectDialog.toggleUICheckBox();
                        }
                        if (name.compareTo("XML Objects") == 0) {
                            this.objectDialog.toggleXMLCheckBox();
                        }
                        if (name.compareTo("Other Objects") == 0) {
                            this.objectDialog.toggleOtherCheckBox();
                        }
                    }
                }
            }
        }
        return b;
    }
    
    public Component getTreeCellEditorComponent(final JTree tree, final Object o, final boolean b, final boolean b2, final boolean b3, final int n) {
        final Component treeCellRendererComponent = this.renderer.getTreeCellRendererComponent(tree, o, true, b2, b3, n, true);
        final ItemListener l = new ItemListener() {
            public void itemStateChanged(final ItemEvent itemEvent) {
                if (CheckBoxNodeEditor.this.stopCellEditing()) {
                    AbstractCellEditor.this.fireEditingStopped();
                }
            }
        };
        if (treeCellRendererComponent instanceof JCheckBox) {
            ((JCheckBox)treeCellRendererComponent).addItemListener(l);
        }
        return treeCellRendererComponent;
    }
}
