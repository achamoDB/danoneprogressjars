// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.tools;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.Dialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Box;
import java.util.Hashtable;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class RuntimePropertyDlg extends JDialog implements ActionListener
{
    public static final Dimension BUTTON_SEPARATOR;
    public static final int VSTRUT = 9;
    public static final int HSTRUT = 9;
    public static final int TEXT_LENGTH = 20;
    private EsbRuntimeProperties runtimeProps;
    private Hashtable props;
    private boolean propsChanged;
    Box buttonBox;
    Box box;
    JButton btnOK;
    JButton btnCancel;
    JButton btnReset;
    JLabel[] label;
    JTextField[] text;
    
    RuntimePropertyDlg(final Dialog owner, final String title, final boolean modal, final EsbRuntimeProperties runtimeProps) {
        super(owner, title, modal);
        this.runtimeProps = null;
        this.props = null;
        this.propsChanged = false;
        this.buttonBox = Box.createHorizontalBox();
        this.box = Box.createVerticalBox();
        this.btnOK = new JButton("OK");
        this.btnCancel = new JButton("Cancel");
        this.btnReset = new JButton("Reset");
        this.label = null;
        this.text = null;
        try {
            this.runtimeProps = runtimeProps;
            this.init();
            this.pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    RuntimePropertyDlg(final Frame owner, final String title, final boolean modal, final EsbRuntimeProperties runtimeProps) {
        super(owner, title, modal);
        this.runtimeProps = null;
        this.props = null;
        this.propsChanged = false;
        this.buttonBox = Box.createHorizontalBox();
        this.box = Box.createVerticalBox();
        this.btnOK = new JButton("OK");
        this.btnCancel = new JButton("Cancel");
        this.btnReset = new JButton("Reset");
        this.label = null;
        this.text = null;
        try {
            this.runtimeProps = runtimeProps;
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
        this.box.add(this.createPropsPanel());
        this.box.add(Box.createVerticalStrut(9));
        this.btnOK.addActionListener(this);
        this.buttonBox.add(this.btnOK);
        this.buttonBox.add(Box.createRigidArea(RuntimePropertyDlg.BUTTON_SEPARATOR));
        this.btnCancel.addActionListener(this);
        this.buttonBox.add(this.btnCancel);
        this.buttonBox.add(Box.createRigidArea(RuntimePropertyDlg.BUTTON_SEPARATOR));
        this.btnReset.addActionListener(this);
        this.buttonBox.add(this.btnReset);
        this.box.add(Box.createVerticalStrut(9));
        contentPane.add(this.box, "West");
        final JPanel comp = new JPanel();
        comp.setLayout(new BorderLayout());
        comp.add(this.buttonBox, "East");
        contentPane.add(comp, "South");
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
            this.props = this.runtimeProps.getESBProperties();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
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
            this.resetRuntimeProps();
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
            this.runtimeProps.setProperties(this.props);
            this.dispose();
        }
    }
    
    public EsbRuntimeProperties getRuntimeProperties() {
        return this.runtimeProps;
    }
    
    public boolean isPropListDirty() {
        return this.propsChanged;
    }
    
    static {
        BUTTON_SEPARATOR = new Dimension(5, 0);
    }
}
