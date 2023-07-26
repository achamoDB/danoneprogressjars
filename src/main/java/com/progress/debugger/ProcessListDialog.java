// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Frame;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class ProcessListDialog extends JDialog
{
    JPanel panel1;
    BorderLayout borderLayout1;
    JScrollPane jScrollPane1;
    JList jList1;
    JButton jButtonOk;
    TitledBorder titledBorder1;
    Box box1;
    JPanel jPanel1;
    JButton jButtonCancel;
    JPanel jPanel2;
    JLabel jLabel1;
    JTextField jTextField1;
    AttachToProcessDialog parentDlg;
    
    public ProcessListDialog(final Frame1 owner, final AttachToProcessDialog parentDlg, final Vector listData) {
        super(owner, "Select Process", true);
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jScrollPane1 = new JScrollPane();
        this.jButtonOk = new JButton();
        this.jPanel1 = new JPanel();
        this.jButtonCancel = new JButton();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jTextField1 = new JTextField();
        try {
            this.parentDlg = parentDlg;
            this.jList1 = new JList(listData);
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    void jbInit() throws Exception {
        this.titledBorder1 = new TitledBorder("");
        this.box1 = Box.createVerticalBox();
        this.panel1.setLayout(this.borderLayout1);
        this.jButtonOk.setMaximumSize(new Dimension(73, 27));
        this.jButtonOk.setMinimumSize(new Dimension(73, 27));
        this.jButtonOk.setPreferredSize(new Dimension(73, 27));
        this.jButtonOk.setText("OK");
        this.jButtonOk.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                ProcessListDialog.this.jButtonOk_actionPerformed(actionEvent);
            }
        });
        this.panel1.setBorder(this.titledBorder1);
        this.panel1.setMinimumSize(new Dimension(272, 212));
        this.panel1.setPreferredSize(new Dimension(500, 400));
        this.jButtonCancel.setMaximumSize(new Dimension(73, 27));
        this.jButtonCancel.setMinimumSize(new Dimension(73, 27));
        this.jButtonCancel.setPreferredSize(new Dimension(73, 27));
        this.jButtonCancel.setText("Cancel");
        this.jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                ProcessListDialog.this.jButtonCancel_actionPerformed(actionEvent);
            }
        });
        this.jList1.setRequestFocusEnabled(false);
        this.jList1.setSelectionMode(0);
        this.jLabel1.setAlignmentX(0.5f);
        this.jLabel1.setText("Process ID:");
        this.jTextField1.setMinimumSize(new Dimension(63, 21));
        this.jTextField1.setPreferredSize(new Dimension(63, 21));
        this.jTextField1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                ProcessListDialog.this.jTextField1_keyReleased(keyEvent);
            }
        });
        this.jScrollPane1.setPreferredSize(new Dimension(400, 300));
        this.getContentPane().add(this.panel1);
        this.panel1.add(this.box1, "North");
        this.jPanel1.add(this.jButtonOk, null);
        this.jPanel1.add(this.jButtonCancel, null);
        this.box1.add(this.jScrollPane1, null);
        this.box1.add(this.jPanel2, null);
        this.jPanel2.add(this.jLabel1, null);
        this.jPanel2.add(this.jTextField1, null);
        this.box1.add(this.jPanel1, null);
        this.jScrollPane1.getViewport().add(this.jList1, null);
        this.jList1.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                ProcessListDialog.this.jList1_valueChanged(listSelectionEvent);
            }
        });
    }
    
    void jButtonOk_actionPerformed(final ActionEvent actionEvent) {
        try {
            final String text = this.jTextField1.getText();
            Integer.parseInt(text);
            this.parentDlg.jTextFieldProcessid.setText(text);
            this.dispose();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "You must enter an integer for the process id.", "Error", 0);
        }
    }
    
    void jButtonCancel_actionPerformed(final ActionEvent actionEvent) {
        this.dispose();
    }
    
    void jTextField1_keyReleased(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonOk_actionPerformed(null);
        }
    }
    
    void jList1_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final String string = this.jList1.getSelectedValue().toString();
        this.jTextField1.setText(string.substring(0, string.indexOf(" ")));
    }
}
