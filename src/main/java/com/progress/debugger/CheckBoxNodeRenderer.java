// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Component;
import javax.swing.JTree;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxNodeRenderer implements TreeCellRenderer
{
    private JCheckBox leafRenderer;
    private JCheckBox databaseCheckBox;
    private JCheckBox uiCheckBox;
    private JCheckBox xmlCheckBox;
    private JCheckBox otherCheckBox;
    private DefaultTreeCellRenderer nonLeafRenderer;
    Color selectionBorderColor;
    Color selectionForeground;
    Color selectionBackground;
    Color textForeground;
    Color textBackground;
    DynamicObjectDialog objectDialog;
    
    protected JCheckBox getLeafRenderer() {
        return this.leafRenderer;
    }
    
    public CheckBoxNodeRenderer(final DynamicObjectDialog objectDialog) {
        this.leafRenderer = new JCheckBox();
        this.databaseCheckBox = new JCheckBox("Database Objects");
        this.uiCheckBox = new JCheckBox("User Interface Widgets");
        this.xmlCheckBox = new JCheckBox("XML Objects");
        this.otherCheckBox = new JCheckBox("Other Objects");
        this.nonLeafRenderer = new DefaultTreeCellRenderer();
        this.objectDialog = objectDialog;
        final Font font = UIManager.getFont("Tree.font");
        if (font != null) {
            this.leafRenderer.setFont(font);
        }
        final Boolean b = (Boolean)UIManager.get("Tree.drawsFocusBorderAroundIcon");
        this.leafRenderer.setFocusPainted(b != null && b);
        this.selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        this.selectionForeground = UIManager.getColor("Tree.selectionForeground");
        this.selectionBackground = UIManager.getColor("Tree.selectionBackground");
        this.textForeground = UIManager.getColor("Tree.textForeground");
        this.textBackground = UIManager.getColor("Tree.textBackground");
    }
    
    public Component getTreeCellRendererComponent(final JTree tree, final Object o, final boolean sel, final boolean b, final boolean b2, final int n, final boolean hasFocus) {
        final String convertValueToText = tree.convertValueToText(o, sel, b, b2, n, false);
        Component component;
        if (b2) {
            this.leafRenderer.setText(convertValueToText);
            this.leafRenderer.setSelected(sel);
            this.leafRenderer.setEnabled(tree.isEnabled());
            if (sel) {
                this.leafRenderer.setForeground(this.selectionForeground);
                this.leafRenderer.setBackground(this.selectionBackground);
            }
            else {
                this.leafRenderer.setForeground(this.textForeground);
                this.leafRenderer.setBackground(this.textBackground);
            }
            if (o != null && o instanceof DefaultMutableTreeNode) {
                final Object userObject = ((DefaultMutableTreeNode)o).getUserObject();
                if (userObject instanceof CheckBoxNode) {
                    final CheckBoxNode checkBoxNode = (CheckBoxNode)userObject;
                    this.leafRenderer.setText(checkBoxNode.getText());
                    this.leafRenderer.setSelected(this.objectDialog.returnCheckBoxState(checkBoxNode.getText()));
                }
            }
            component = this.leafRenderer;
        }
        else if (n != -1) {
            final String name = ((NamedVector)((DefaultMutableTreeNode)o).getUserObject()).name;
            if (name.compareTo("Database Objects") == 0) {
                this.databaseCheckBox.setSelected(this.objectDialog.databaseCheckBox.isSelected());
                component = this.databaseCheckBox;
            }
            else if (name.compareTo("User Interface Widgets") == 0) {
                this.uiCheckBox.setSelected(this.objectDialog.uiCheckBox.isSelected());
                component = this.uiCheckBox;
            }
            else if (name.compareTo("XML Objects") == 0) {
                this.xmlCheckBox.setSelected(this.objectDialog.xmlCheckBox.isSelected());
                component = this.xmlCheckBox;
            }
            else {
                this.otherCheckBox.setSelected(this.objectDialog.otherCheckBox.isSelected());
                component = this.otherCheckBox;
            }
            component.setForeground(this.textForeground);
            component.setBackground(this.textBackground);
        }
        else {
            component = this.nonLeafRenderer.getTreeCellRendererComponent(tree, o, sel, b, b2, n, hasFocus);
            component.setForeground(this.textForeground);
            component.setBackground(this.textBackground);
        }
        return component;
    }
}
