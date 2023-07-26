// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.tools;

import java.awt.event.ItemEvent;
import java.io.IOException;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import javax.swing.JFrame;
import com.progress.wsa.WsaSOAPException;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import com.progress.open4gl.wsdlgen.DWGenInfo;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.event.ItemListener;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JPanel;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.Box;
import java.util.Hashtable;
import com.progress.wsa.admin.AppRuntimeProps;
import com.progress.wsa.admin.AppContainer;
import java.awt.Dimension;
import com.sonicsw.mx.config.util.SonicFSFileSystem;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class OEServiceDlg extends JDialog implements ActionListener
{
    protected SonicFSFileSystem m_fs;
    protected String m_path;
    public static final Dimension BUTTON_SEPARATOR;
    public static final int VSTRUT = 9;
    public static final int HSTRUT = 9;
    public static final int TEXT_LENGTH = 20;
    private AppContainer m_theApp;
    private AppRuntimeProps runtimeProps;
    private Hashtable props;
    private boolean propsChanged;
    Box buttonBox;
    Box box;
    JButton btnOK;
    JButton btnCancel;
    JButton btnReset;
    JCheckBox chkAppend;
    JTextField txtWebSvcs;
    JTextField txtSoapAction;
    JButton btnGenWsdl;
    JTabbedPane tab;
    ButtonGroup btnGroupWsdl;
    JRadioButton optRpcEnc;
    JRadioButton optRpcLit;
    JRadioButton optDocLit;
    JLabel[] label;
    JTextField[] text;
    
    OEServiceDlg(final Frame owner, final String title, final boolean modal, final AppContainer theApp, final SonicFSFileSystem fs, final String path) {
        super(owner, title, modal);
        this.m_fs = null;
        this.m_path = null;
        this.m_theApp = null;
        this.runtimeProps = null;
        this.props = null;
        this.propsChanged = false;
        this.buttonBox = Box.createHorizontalBox();
        this.box = Box.createVerticalBox();
        this.btnOK = new JButton("OK");
        this.btnCancel = new JButton("Cancel");
        this.btnReset = new JButton("Reset");
        this.chkAppend = new JCheckBox("Append to SOAP Action", true);
        this.txtWebSvcs = new JTextField(20);
        this.txtSoapAction = new JTextField(20);
        this.btnGenWsdl = new JButton("Generate WSDL");
        this.tab = new JTabbedPane();
        this.btnGroupWsdl = new ButtonGroup();
        this.optRpcEnc = new JRadioButton("RPC/Encoded");
        this.optRpcLit = new JRadioButton("RPC/Literal");
        this.optDocLit = new JRadioButton("Document/Literal");
        this.label = null;
        this.text = null;
        try {
            this.m_fs = fs;
            this.m_theApp = theApp;
            this.m_path = path;
            this.runtimeProps = this.m_theApp.getRuntimeProperties();
            this.init();
            this.pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    OEServiceDlg(final Dialog owner, final String title, final boolean modal, final AppContainer theApp, final SonicFSFileSystem fs, final String path) {
        super(owner, title, modal);
        this.m_fs = null;
        this.m_path = null;
        this.m_theApp = null;
        this.runtimeProps = null;
        this.props = null;
        this.propsChanged = false;
        this.buttonBox = Box.createHorizontalBox();
        this.box = Box.createVerticalBox();
        this.btnOK = new JButton("OK");
        this.btnCancel = new JButton("Cancel");
        this.btnReset = new JButton("Reset");
        this.chkAppend = new JCheckBox("Append to SOAP Action", true);
        this.txtWebSvcs = new JTextField(20);
        this.txtSoapAction = new JTextField(20);
        this.btnGenWsdl = new JButton("Generate WSDL");
        this.tab = new JTabbedPane();
        this.btnGroupWsdl = new ButtonGroup();
        this.optRpcEnc = new JRadioButton("RPC/Encoded");
        this.optRpcLit = new JRadioButton("RPC/Literal");
        this.optDocLit = new JRadioButton("Document/Literal");
        this.label = null;
        this.text = null;
        try {
            this.m_fs = fs;
            this.m_theApp = theApp;
            this.m_path = path;
            this.runtimeProps = this.m_theApp.getRuntimeProperties();
            this.init();
            this.pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void init() throws Exception {
        this.setResizable(false);
        final JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());
        contentPane.setAlignmentX(0.0f);
        this.tab.addTab("Deployment Information", this.createDeploymentPanel());
        this.tab.addTab("Runtime Properties", this.createPropsPanel());
        this.tab.setAlignmentX(0.0f);
        this.tab.setSelectedIndex(0);
        this.tab.validate();
        this.box.add(this.tab);
        this.box.add(Box.createVerticalStrut(9));
        this.btnOK.addActionListener(this);
        this.buttonBox.add(this.btnOK);
        this.buttonBox.add(Box.createRigidArea(OEServiceDlg.BUTTON_SEPARATOR));
        this.btnCancel.addActionListener(this);
        this.buttonBox.add(this.btnCancel);
        this.buttonBox.add(Box.createRigidArea(OEServiceDlg.BUTTON_SEPARATOR));
        this.btnReset.addActionListener(this);
        this.buttonBox.add(this.btnReset);
        this.box.add(Box.createVerticalStrut(9));
        contentPane.add(this.box, "West");
        final JPanel comp = new JPanel();
        comp.setLayout(new BorderLayout());
        comp.add(this.buttonBox, "East");
        contentPane.add(comp, "South");
    }
    
    private JPanel createDeploymentPanel() {
        final DWGenInfo dwInfo = this.m_theApp.getWSAD().getPGAppObj().getDWInfo();
        final JPanel panel = new JPanel();
        final JPanel comp = new JPanel();
        comp.setLayout(new GridBagLayout());
        panel.setLayout(new BorderLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = 13;
        gridBagConstraints.insets = new Insets(5, 1, 1, 1);
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = 1;
        constraints.insets = new Insets(5, 1, 1, 1);
        final Box verticalBox = Box.createVerticalBox();
        final Box horizontalBox = Box.createHorizontalBox();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        comp.add(new JLabel(" Web Service Namespace:"), gridBagConstraints);
        this.txtWebSvcs.setAlignmentX(0.0f);
        this.txtWebSvcs.setText(this.m_theApp.getWSAD().getWebServiceNamespace());
        constraints.gridx = 1;
        constraints.gridy = 0;
        comp.add(this.txtWebSvcs, constraints);
        final JLabel comp2 = new JLabel(" Soap Action URI:");
        final GridBagConstraints gridBagConstraints2 = gridBagConstraints;
        ++gridBagConstraints2.gridy;
        comp.add(comp2, gridBagConstraints);
        this.txtSoapAction.setText(dwInfo.getSoapAction());
        final GridBagConstraints gridBagConstraints3 = constraints;
        ++gridBagConstraints3.gridy;
        comp.add(this.txtSoapAction, constraints);
        this.chkAppend.addItemListener(new AppendSoapItemListener());
        final GridBagConstraints gridBagConstraints4 = constraints;
        ++gridBagConstraints4.gridy;
        constraints.weightx = 0.1;
        constraints.anchor = 11;
        comp.add(this.chkAppend, constraints);
        final JPanel comp3 = new JPanel();
        comp3.setAlignmentX(0.0f);
        comp3.setBorder(BorderFactory.createTitledBorder("WSDL Style/Use"));
        final WsdlStyleItemListener l = new WsdlStyleItemListener();
        this.optRpcEnc.addItemListener(l);
        this.optRpcLit.addItemListener(l);
        this.optDocLit.addItemListener(l);
        switch (this.m_theApp.getCurrentESBEncodingInt()) {
            case 1: {
                this.optRpcEnc.setSelected(true);
                break;
            }
            case 2: {
                this.optRpcLit.setSelected(true);
                break;
            }
            case 3: {
                this.optDocLit.setSelected(true);
                break;
            }
        }
        this.btnGroupWsdl.add(this.optRpcEnc);
        this.btnGroupWsdl.add(this.optRpcLit);
        this.btnGroupWsdl.add(this.optDocLit);
        comp3.add(this.optRpcEnc);
        comp3.add(this.optRpcLit);
        comp3.add(this.optDocLit);
        this.btnGenWsdl.addActionListener(new WsdlActionListener());
        horizontalBox.add(comp3);
        horizontalBox.add(Box.createHorizontalStrut(9));
        horizontalBox.add(this.btnGenWsdl);
        horizontalBox.add(Box.createHorizontalStrut(9));
        horizontalBox.validate();
        verticalBox.add(comp);
        verticalBox.add(Box.createGlue());
        verticalBox.add(horizontalBox);
        verticalBox.add(Box.createGlue());
        panel.add(verticalBox, "West");
        panel.setAlignmentX(0.0f);
        panel.validate();
        return panel;
    }
    
    private JPanel createPropsPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 1, 1, 1);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = 3;
        constraints.anchor = 13;
        final GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.insets = new Insets(5, 1, 1, 1);
        constraints2.gridx = 1;
        constraints2.gridy = 0;
        constraints2.fill = 3;
        try {
            this.props = this.runtimeProps.getAppProperties(true);
        }
        catch (Exception ex) {}
        if (this.props != null) {
            this.label = new JLabel[this.props.size()];
            this.text = new JTextField[this.props.size()];
            final Enumeration<Object> keys = (Enumeration<Object>)this.props.keys();
            int n = 0;
            while (keys.hasMoreElements()) {
                final Object nextElement = keys.nextElement();
                (this.label[n] = new JLabel(" " + nextElement.toString() + ":")).setAlignmentX(1.0f);
                (this.text[n] = new JTextField(10)).setColumns(10);
                this.text[n].setText(this.props.get(nextElement).toString());
                this.text[n].setName(nextElement.toString());
                panel.add(this.label[n], constraints);
                panel.add(this.text[n], constraints2);
                final GridBagConstraints gridBagConstraints = constraints;
                ++gridBagConstraints.gridy;
                final GridBagConstraints gridBagConstraints2 = constraints2;
                ++gridBagConstraints2.gridy;
                if (n != 0 && (n + 1) % 9 == 0) {
                    final GridBagConstraints gridBagConstraints3 = constraints;
                    gridBagConstraints3.gridx += 2;
                    constraints.gridy = 0;
                    final GridBagConstraints gridBagConstraints4 = constraints2;
                    gridBagConstraints4.gridx += 2;
                    constraints2.gridy = 0;
                }
                ++n;
            }
        }
        return panel;
    }
    
    private void resetRuntimeProps() {
        this.props = this.runtimeProps.getProperties();
        final Enumeration<Object> keys = this.props.keys();
        int n = 0;
        while (keys.hasMoreElements()) {
            final Object nextElement = keys.nextElement();
            this.text[n].invalidate();
            this.text[n].setText(this.props.get(nextElement).toString());
            this.text[n].validate();
            ++n;
        }
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(this.btnCancel)) {
            this.propsChanged = false;
            this.dispose();
        }
        if (actionEvent.getSource().equals(this.btnReset)) {
            if (this.tab.getSelectedIndex() == 0) {
                this.txtSoapAction.setText(this.m_theApp.getWSAD().getPGAppObj().getDWInfo().getSoapAction());
                this.txtWebSvcs.setText(this.m_theApp.getWSAD().getWebServiceNamespace());
            }
            else {
                this.resetRuntimeProps();
            }
        }
        if (actionEvent.getSource().equals(this.btnOK)) {
            this.propsChanged = true;
            for (int i = 0; i < this.text.length; ++i) {
                final String name = this.text[i].getName();
                final String text = this.text[i].getText();
                try {
                    this.props.put(name, new Integer(Integer.parseInt(text)));
                }
                catch (NumberFormatException ex) {
                    this.props.put(this.text[i].getName(), this.text[i].getText());
                }
            }
            try {
                this.runtimeProps.setProperty("PROGRESS.Session.serviceAvailable", new Integer(0));
                this.runtimeProps.setRuntimeProperties(this.props);
                this.runtimeProps.setProperty("PROGRESS.Session.serviceAvailable", new Integer(1));
            }
            catch (WsaSOAPException ex2) {}
            this.m_theApp.getWSAD().getPGAppObj().getDWInfo().setSoapAction(this.txtSoapAction.getText());
            this.m_theApp.getWSAD().setWebServiceNamespace(this.txtWebSvcs.getText());
            this.dispose();
        }
    }
    
    public boolean isPropListDirty() {
        return this.propsChanged;
    }
    
    public static void main(final String[] array) {
        final JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException ex) {}
        catch (InstantiationException ex2) {}
        catch (IllegalAccessException ex3) {}
        catch (UnsupportedLookAndFeelException ex4) {}
        AppContainer loadWSDFile = null;
        try {
            loadWSDFile = AppContainer.loadWSDFile(null, "c:\\Projects\\OEResourceEditor\\src\\StockQuote.wsm");
        }
        catch (IOException ex5) {}
        frame.setVisible(true);
        final OEServiceDlg oeServiceDlg = new OEServiceDlg(frame, "Edit OpenEdge Service Definition", true, loadWSDFile, null, null);
        oeServiceDlg.pack();
        oeServiceDlg.validate();
        oeServiceDlg.show();
    }
    
    static {
        BUTTON_SEPARATOR = new Dimension(5, 0);
    }
    
    public class WsdlActionListener implements ActionListener
    {
        public void actionPerformed(final ActionEvent actionEvent) {
            new OEWsdlDlg(OEServiceDlg.this, true, OEServiceDlg.this.m_theApp).setVisible(true);
        }
    }
    
    public class WsdlStyleItemListener implements ItemListener
    {
        public void itemStateChanged(final ItemEvent itemEvent) {
            if (OEServiceDlg.this.optRpcEnc.isSelected()) {
                OEServiceDlg.this.m_theApp.setCurrentESBEncodingInt(1);
            }
            else if (OEServiceDlg.this.optRpcLit.isSelected()) {
                OEServiceDlg.this.m_theApp.setCurrentESBEncodingInt(2);
            }
            else if (OEServiceDlg.this.optDocLit.isSelected()) {
                OEServiceDlg.this.m_theApp.setCurrentESBEncodingInt(3);
            }
        }
    }
    
    public class AppendSoapItemListener implements ItemListener
    {
        public void itemStateChanged(final ItemEvent itemEvent) {
            OEServiceDlg.this.m_theApp.getWSAD().setAppendToSoapAction(OEServiceDlg.this.chkAppend.isSelected());
        }
    }
}
