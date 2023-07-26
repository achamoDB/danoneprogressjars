// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class DebugPvmMessagesDialog extends JFrame
{
    private JPanel panel1;
    private BorderLayout borderLayout1;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextPane jTextPane1;
    private JButton jButtonClose;
    private String message;
    private Frame1 application;
    TitledBorder titledBorder1;
    JTextField jTextField1;
    JButton jButtonSend;
    
    public DebugPvmMessagesDialog(final Frame1 application, final String s, final boolean b) {
        super("PVM Messages");
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jPanel1 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jTextPane1 = new JTextPane();
        this.jButtonClose = new JButton();
        this.message = "";
        this.jTextField1 = new JTextField();
        this.jButtonSend = new JButton();
        try {
            this.application = application;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    public DebugPvmMessagesDialog() {
        this(null, "", false);
    }
    
    void jbInit() throws Exception {
        this.titledBorder1 = new TitledBorder("");
        this.panel1.setLayout(this.borderLayout1);
        this.jButtonClose.setText("Close");
        this.jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DebugPvmMessagesDialog.this.jButtonClose_actionPerformed(actionEvent);
            }
        });
        this.panel1.setBorder(this.titledBorder1);
        this.jScrollPane1.setHorizontalScrollBarPolicy(32);
        this.jTextField1.setMinimumSize(new Dimension(150, 21));
        this.jTextField1.setPreferredSize(new Dimension(150, 21));
        this.jButtonSend.setText("send");
        this.jButtonSend.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DebugPvmMessagesDialog.this.jButtonSend_actionPerformed(actionEvent);
            }
        });
        this.getContentPane().add(this.panel1);
        this.panel1.add(this.jPanel1, "South");
        this.jPanel1.add(this.jButtonSend, null);
        this.jPanel1.add(this.jTextField1, null);
        this.jPanel1.add(this.jButtonClose, null);
        this.panel1.add(this.jScrollPane1, "Center");
        this.jScrollPane1.getViewport().add(this.jTextPane1, null);
    }
    
    void addMessage(final String str) {
        this.message = this.message.concat(str);
        this.message = this.message.concat("\n");
        this.message = this.message.concat("\n");
        this.jTextPane1.setText(this.message);
    }
    
    void jButtonClose_actionPerformed(final ActionEvent actionEvent) {
        this.dispose();
        this.application.debugDlgOpen = false;
    }
    
    void jButtonSend_actionPerformed(final ActionEvent actionEvent) {
        this.application.sendSocket.sendMessage(this.jTextField1.getText());
    }
}
