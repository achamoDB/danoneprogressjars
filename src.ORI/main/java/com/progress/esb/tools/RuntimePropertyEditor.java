// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.tools;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import com.sonicsw.xqimpl.util.log.XQLogImpl;
import org.w3c.dom.Node;
import java.awt.Component;
import javax.naming.InitialContext;
import com.sonicsw.xqimpl.config.XQConfigManager;
import com.sonicsw.xq.XQLog;
import java.beans.PropertyEditorSupport;

public class RuntimePropertyEditor extends PropertyEditorSupport
{
    static XQLog m_log;
    private XQConfigManager m_configMgr;
    private InitialContext m_domainContext;
    private EditorComponent m_editorComponent;
    
    public RuntimePropertyEditor() {
        this.m_editorComponent = new EditorComponent();
    }
    
    public RuntimePropertyEditor(final InitialContext domainContext) {
        this.m_domainContext = domainContext;
        this.m_editorComponent = new EditorComponent();
    }
    
    public RuntimePropertyEditor(final XQConfigManager configMgr) {
        this.m_configMgr = configMgr;
        this.m_editorComponent = new EditorComponent();
    }
    
    public boolean supportsCustomEditor() {
        return true;
    }
    
    public Component getCustomEditor() {
        RuntimePropertyEditor.m_log.logInformation("Got custom editor");
        return this.m_editorComponent;
    }
    
    public void setValue(final Object o) {
        this.m_editorComponent.setValue((Node)o);
    }
    
    public Object getValue() {
        return this.m_editorComponent.getValue();
    }
    
    public void setAsText(final String s) {
        throw new IllegalArgumentException("Not supported");
    }
    
    static {
        RuntimePropertyEditor.m_log = XQLogImpl.getCategoryLog(64);
    }
    
    class EditorComponent extends JPanel implements ActionListener
    {
        private EsbRuntimeProperties m_editedValue;
        private JButton m_button;
        
        public EditorComponent() {
            this.setLayout(new GridBagLayout());
            this.m_button = new JButton("Runtime Properties");
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.weightx = 1.0;
            constraints.fill = 2;
            this.add(this.m_button, constraints);
            this.m_button.addActionListener(this);
        }
        
        public void actionPerformed(final ActionEvent actionEvent) {
            if (this.editInDialog(SwingUtilities.getWindowAncestor(this), true)) {
                RuntimePropertyEditor.this.firePropertyChange();
            }
        }
        
        public void setEnabled(final boolean b) {
            super.setEnabled(b);
            this.m_button.setEnabled(b);
        }
        
        public void setValue(final Node node) {
            this.m_editedValue = new EsbRuntimeProperties();
            if (node != null) {
                this.m_editedValue.readXML(node);
            }
        }
        
        public Object getValue() {
            try {
                return this.m_editedValue.toDom();
            }
            catch (Exception ex) {
                return null;
            }
        }
        
        public boolean editInDialog(final Window locationRelativeTo, final boolean b) {
            RuntimePropertyDlg runtimePropertyDlg = null;
            if (this.m_editedValue == null) {
                this.m_editedValue = new EsbRuntimeProperties();
            }
            if (locationRelativeTo instanceof Frame) {
                runtimePropertyDlg = new RuntimePropertyDlg((Frame)locationRelativeTo, "Edit OpenEdge Service Runtime Properties", true, this.m_editedValue);
            }
            else if (locationRelativeTo instanceof Dialog) {
                runtimePropertyDlg = new RuntimePropertyDlg((Dialog)locationRelativeTo, "Edit OpenEdge Service Runtime Properties", true, this.m_editedValue);
            }
            runtimePropertyDlg.setDefaultCloseOperation(2);
            runtimePropertyDlg.setSize(400, 300);
            runtimePropertyDlg.addNotify();
            runtimePropertyDlg.validate();
            runtimePropertyDlg.setSize(runtimePropertyDlg.getPreferredSize());
            runtimePropertyDlg.setLocationRelativeTo(locationRelativeTo);
            runtimePropertyDlg.setVisible(true);
            if (runtimePropertyDlg.isPropListDirty()) {
                this.m_editedValue = runtimePropertyDlg.getRuntimeProperties();
            }
            return runtimePropertyDlg.isPropListDirty();
        }
    }
}
