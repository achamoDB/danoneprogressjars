// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.LayoutManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class Frame1_AboutBox extends JDialog implements ActionListener
{
    JPanel panel1;
    JPanel panel2;
    JPanel insetsPanel1;
    JPanel insetsPanel2;
    JPanel insetsPanel3;
    JButton button1;
    JLabel imageLabel;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    BorderLayout borderLayout1;
    BorderLayout borderLayout2;
    FlowLayout flowLayout1;
    GridLayout gridLayout1;
    String product;
    String version;
    String copyright;
    String comments;
    
    public Frame1_AboutBox(final Frame owner) {
        super(owner);
        this.panel1 = new JPanel();
        this.panel2 = new JPanel();
        this.insetsPanel1 = new JPanel();
        this.insetsPanel2 = new JPanel();
        this.insetsPanel3 = new JPanel();
        this.button1 = new JButton();
        this.imageLabel = new JLabel();
        this.label1 = new JLabel();
        this.label2 = new JLabel();
        this.label3 = new JLabel();
        this.label4 = new JLabel();
        this.borderLayout1 = new BorderLayout();
        this.borderLayout2 = new BorderLayout();
        this.flowLayout1 = new FlowLayout();
        this.gridLayout1 = new GridLayout();
        this.product = "OpenEdge Debugger";
        this.version = "10.2B";
        this.copyright = "Copyright (c) 1984-2009 Progress Software Corporation";
        this.comments = "and/or its subsidiaries or affiliates. All rights reserved.";
        this.enableEvents(64L);
        try {
            this.jbInit();
        }
        catch (Exception ex) {}
        this.pack();
    }
    
    private void jbInit() throws Exception {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                Frame1_AboutBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.imageLabel.setIcon(new ImageIcon(Frame1_AboutBox.class.getResource("debug.gif")));
        this.setTitle("About");
        this.setResizable(false);
        this.panel1.setLayout(this.borderLayout1);
        this.panel2.setLayout(this.borderLayout2);
        this.insetsPanel1.setLayout(this.flowLayout1);
        this.insetsPanel2.setLayout(this.flowLayout1);
        this.insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.gridLayout1.setRows(4);
        this.gridLayout1.setColumns(1);
        this.label1.setFont(new Font("Dialog", 0, 12));
        this.label1.setText(this.product);
        this.label2.setFont(new Font("Dialog", 0, 12));
        this.label2.setText(this.version);
        this.label3.setFont(new Font("Dialog", 0, 12));
        this.label3.setText(this.copyright);
        this.label4.setFont(new Font("Dialog", 0, 12));
        this.label4.setText(this.comments);
        this.insetsPanel3.setLayout(this.gridLayout1);
        this.insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
        this.button1.setFont(new Font("Dialog", 0, 12));
        this.button1.setMaximumSize(new Dimension(73, 27));
        this.button1.setMinimumSize(new Dimension(73, 27));
        this.button1.setPreferredSize(new Dimension(73, 27));
        this.button1.setActionCommand("OK");
        this.button1.setText("OK");
        this.button1.addActionListener(this);
        this.panel1.setFont(new Font("Dialog", 0, 12));
        this.insetsPanel2.add(this.imageLabel, null);
        this.panel2.add(this.insetsPanel2, "West");
        this.getContentPane().add(this.panel1, null);
        this.insetsPanel3.add(this.label1, null);
        this.insetsPanel3.add(this.label2, null);
        this.insetsPanel3.add(this.label3, null);
        this.insetsPanel3.add(this.label4, null);
        this.panel2.add(this.insetsPanel3, "Center");
        this.insetsPanel1.add(this.button1, null);
        this.panel1.add(this.insetsPanel1, "South");
        this.panel1.add(this.panel2, "North");
        this.panel1.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                Frame1_AboutBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.button1.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                Frame1_AboutBox.this.dialogKeyPressed(keyEvent);
            }
        });
    }
    
    protected void processWindowEvent(final WindowEvent e) {
        if (e.getID() == 201) {
            this.cancel();
        }
        super.processWindowEvent(e);
    }
    
    void cancel() {
        this.dispose();
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.button1) {
            this.cancel();
        }
    }
    
    void dialogKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
    }
}
