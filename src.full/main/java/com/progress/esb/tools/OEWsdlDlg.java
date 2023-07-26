// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.tools;

import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import com.progress.wsa.WsaSOAPException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import com.progress.open4gl.proxygen.PGAppObj;
import java.awt.Dialog;
import com.progress.open4gl.wsdlgen.DWGenInfo;
import com.progress.open4gl.proxygen.PGGenInfo;
import com.progress.wsa.admin.AppContainer;
import java.io.File;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

public class OEWsdlDlg extends JDialog
{
    JButton btnOk;
    JButton btnCancel;
    JTextField txtWebSvcName;
    private JTextField txtFileName;
    private File m_wsdlFile;
    private String m_webSvcName;
    AppContainer m_app;
    PGGenInfo m_genInfo;
    DWGenInfo m_dwGenInfo;
    
    public OEWsdlDlg(final Dialog dialog, final boolean modal, final AppContainer app) {
        super(dialog, modal);
        this.btnOk = new JButton("OK");
        this.btnCancel = new JButton("Cancel");
        this.txtWebSvcName = new JTextField(20);
        this.txtFileName = new JTextField(20);
        this.m_wsdlFile = null;
        this.m_webSvcName = "";
        this.m_app = null;
        this.m_genInfo = null;
        this.m_dwGenInfo = null;
        this.m_app = app;
        this.m_app.getWSAD().getPGAppObj();
        this.m_genInfo = PGAppObj.getGenInfo();
        (this.m_dwGenInfo = this.m_app.getWSAD().getPGAppObj().getDWInfo()).setSoapEndPointURL("sonic:///local/" + this.m_genInfo.getServiceName());
        this.m_webSvcName = this.m_dwGenInfo.getSoapEndPointURL();
        String pathname = this.m_app.getWSDLFileName();
        if (pathname == null) {
            pathname = this.m_app.getWSAD().getPGAppObj().getAppObjectName() + ".wsdl";
        }
        this.m_wsdlFile = new File(pathname);
        this.setLocationRelativeTo(dialog);
        this.init();
    }
    
    private void init() {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(this.createButtonPanel(), "South");
        this.getContentPane().add(this.createDlgPanel(), "West");
        this.setTitle("Generate WSDL");
        this.setSize(420, 200);
        this.setResizable(false);
    }
    
    private JPanel createDlgPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 1, 1, 1);
        gridBagConstraints.fill = 3;
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 1, 1, 1);
        constraints.fill = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = 13;
        panel.add(new JLabel(" Web Service URL:"), gridBagConstraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.txtWebSvcName.setText(this.m_webSvcName);
        panel.add(this.txtWebSvcName, constraints);
        final GridBagConstraints gridBagConstraints2 = gridBagConstraints;
        ++gridBagConstraints2.gridy;
        panel.add(new JLabel(" File Name:"), gridBagConstraints);
        constraints.gridx = 1;
        final GridBagConstraints gridBagConstraints3 = constraints;
        ++gridBagConstraints3.gridy;
        this.txtFileName.setText(this.m_wsdlFile.getAbsolutePath());
        panel.add(this.txtFileName, constraints);
        final GridBagConstraints gridBagConstraints4 = constraints;
        ++gridBagConstraints4.gridx;
        final JButton comp = new JButton("Browse");
        comp.addActionListener(new BrowseActionListener());
        panel.add(comp, constraints);
        return panel;
    }
    
    private JPanel createButtonPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        final Box horizontalBox = Box.createHorizontalBox();
        this.btnOk.addActionListener(new OkActionListener());
        horizontalBox.add(this.btnOk);
        horizontalBox.add(Box.createRigidArea(OEServiceDlg.BUTTON_SEPARATOR));
        horizontalBox.add(this.btnCancel);
        this.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                OEWsdlDlg.this.dispose();
            }
        });
        panel.add(horizontalBox, "East");
        return panel;
    }
    
    public class OkActionListener implements ActionListener
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            try {
                OEWsdlDlg.this.m_webSvcName = OEWsdlDlg.this.txtWebSvcName.getText();
                OEWsdlDlg.this.m_wsdlFile = new File(OEWsdlDlg.this.txtFileName.getText());
                OEWsdlDlg.this.m_app.setWSAUrl(OEWsdlDlg.this.m_webSvcName);
                OEWsdlDlg.this.m_dwGenInfo.setEncoding(OEWsdlDlg.this.m_dwGenInfo.getESBEncoding());
                OEWsdlDlg.this.m_app.createWSDLFile(OEWsdlDlg.this.m_wsdlFile.getAbsolutePath(), 2);
                JOptionPane.showMessageDialog(OEWsdlDlg.this, "WSDL successfully generated", "Success", 1, null);
                OEWsdlDlg.this.dispose();
            }
            catch (WsaSOAPException ex) {
                JOptionPane.showMessageDialog(OEWsdlDlg.this, "Unable to create WSDL", "Failure", 0, null);
            }
            catch (NullPointerException ex2) {
                JOptionPane.showMessageDialog(OEWsdlDlg.this, "Unable to create WSDL", "Failure", 0, null);
            }
        }
    }
    
    public class BrowseActionListener implements ActionListener
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            final JFileChooser fileChooser = new JFileChooser(".");
            final WSDLFileFilter fileFilter = new WSDLFileFilter();
            fileChooser.setFileSelectionMode(0);
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setSelectedFile(OEWsdlDlg.this.m_wsdlFile);
            fileChooser.setDialogTitle("Open File");
            if (fileChooser.showOpenDialog(OEWsdlDlg.this) == 0 && fileFilter.accept(fileChooser.getSelectedFile())) {
                OEWsdlDlg.this.txtFileName.setText(fileChooser.getSelectedFile().getPath());
                OEWsdlDlg.this.m_wsdlFile = new File(fileChooser.getSelectedFile().getPath());
            }
        }
    }
    
    public class WSDLFileFilter extends FileFilter
    {
        public String getDescription() {
            return "WSDL (*.wsdl)";
        }
        
        public boolean accept(final File file) {
            return file.getName().toLowerCase().endsWith(".wsdl") || file.isDirectory();
        }
    }
}
