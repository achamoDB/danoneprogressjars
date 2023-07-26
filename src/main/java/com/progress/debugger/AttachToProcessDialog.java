// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Point;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Frame;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class AttachToProcessDialog extends JDialog
{
    JPanel panel1;
    BorderLayout borderLayout1;
    Box box1;
    JRadioButton jRadioButtonMakeAttach;
    JPanel jPanel1;
    JRadioButton jRadioButtonAttach;
    JPanel jPanelTop;
    JPanel jPanelOkCancel;
    JButton jButtonCancel;
    JButton jButtonOk;
    ButtonGroup buttonGroup1;
    Box boxIdPort;
    JLabel jLabel1;
    JTextField jTextFieldProcessid;
    JButton jButtonGetList;
    JLabel jLabelPort1;
    JTextField jTextFieldPort1;
    Box box3;
    JTextField jTextFieldHostName;
    JLabel jLabelHostName;
    JTextField jTextFieldPort2;
    JLabel jLabelPort2;
    TitledBorder titledBorder1;
    Frame1 application;
    BorderLayout borderLayout2;
    BorderLayout borderLayout3;
    ButtonGroup buttonGroup2;
    Box box4;
    Box box5;
    JLabel jLabel2;
    Box boxPort1;
    Box boxID;
    JLabel jLabel4;
    JLabel jLabel3;
    
    public AttachToProcessDialog(final Frame1 frame1, final String s, final boolean modal) {
        super(frame1, "Attach to Process", modal);
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jRadioButtonMakeAttach = new JRadioButton();
        this.jPanel1 = new JPanel();
        this.jRadioButtonAttach = new JRadioButton();
        this.jPanelTop = new JPanel();
        this.jPanelOkCancel = new JPanel();
        this.jButtonCancel = new JButton();
        this.jButtonOk = new JButton();
        this.buttonGroup1 = new ButtonGroup();
        this.jLabel1 = new JLabel();
        this.jTextFieldProcessid = new JTextField();
        this.jButtonGetList = new JButton();
        this.jLabelPort1 = new JLabel();
        this.jTextFieldPort1 = new JTextField();
        this.jTextFieldHostName = new JTextField();
        this.jLabelHostName = new JLabel();
        this.jTextFieldPort2 = new JTextField();
        this.jLabelPort2 = new JLabel();
        this.borderLayout2 = new BorderLayout();
        this.borderLayout3 = new BorderLayout();
        this.buttonGroup2 = new ButtonGroup();
        this.jLabel2 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel3 = new JLabel();
        try {
            this.application = frame1;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public AttachToProcessDialog() {
        this((Frame1)null, "", false);
    }
    
    void jbInit() throws Exception {
        this.box1 = Box.createVerticalBox();
        this.boxIdPort = Box.createVerticalBox();
        this.box3 = Box.createVerticalBox();
        this.titledBorder1 = new TitledBorder("");
        this.box5 = Box.createHorizontalBox();
        this.box4 = Box.createHorizontalBox();
        this.boxPort1 = Box.createHorizontalBox();
        this.boxID = Box.createHorizontalBox();
        this.panel1.setLayout(this.borderLayout1);
        this.jRadioButtonMakeAttach.setAlignmentX(0.5f);
        this.jRadioButtonMakeAttach.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jRadioButtonMakeAttach.setSelected(true);
        this.jRadioButtonMakeAttach.setText("Make debug-ready and Attach (local host only)");
        this.jRadioButtonMakeAttach.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                AttachToProcessDialog.this.jRadioButtonMakeAttach_actionPerformed(actionEvent);
            }
        });
        this.jRadioButtonAttach.setAlignmentX(0.5f);
        this.jRadioButtonAttach.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jRadioButtonAttach.setMinimumSize(new Dimension(275, 25));
        this.jRadioButtonAttach.setPreferredSize(new Dimension(275, 25));
        this.jRadioButtonAttach.setText("Attach to debug-ready Process");
        this.jRadioButtonAttach.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                AttachToProcessDialog.this.jRadioButtonAttach_actionPerformed(actionEvent);
            }
        });
        this.jLabel1.setMaximumSize(new Dimension(62, 17));
        this.jLabel1.setText("Process Id:");
        this.jTextFieldProcessid.setAlignmentX(0.0f);
        this.jTextFieldProcessid.setMaximumSize(new Dimension(90, 21));
        this.jTextFieldProcessid.setMinimumSize(new Dimension(90, 21));
        this.jTextFieldProcessid.setPreferredSize(new Dimension(90, 21));
        this.jTextFieldProcessid.setText(this.application.processid);
        this.jTextFieldProcessid.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                AttachToProcessDialog.this.jTextFieldProcessid_keyPressed(keyEvent);
            }
        });
        this.jButtonGetList.setMaximumSize(new Dimension(90, 32));
        this.jButtonGetList.setMinimumSize(new Dimension(73, 27));
        this.jButtonGetList.setPreferredSize(new Dimension(73, 27));
        this.jButtonGetList.setText("Get List...");
        this.jButtonGetList.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                AttachToProcessDialog.this.jButtonGetList_actionPerformed(actionEvent);
            }
        });
        this.jLabelPort1.setMaximumSize(new Dimension(62, 17));
        this.jLabelPort1.setMinimumSize(new Dimension(62, 17));
        this.jLabelPort1.setPreferredSize(new Dimension(62, 17));
        this.jLabelPort1.setText("Port:");
        this.jTextFieldPort1.setAlignmentX(0.0f);
        this.jTextFieldPort1.setMaximumSize(new Dimension(90, 21));
        this.jTextFieldPort1.setMinimumSize(new Dimension(90, 21));
        this.jTextFieldPort1.setPreferredSize(new Dimension(90, 21));
        this.jTextFieldPort1.setText(this.application.port1);
        this.jTextFieldPort1.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                AttachToProcessDialog.this.jTextFieldPort1_keyPressed(keyEvent);
            }
        });
        this.jTextFieldHostName.setEnabled(false);
        this.jTextFieldHostName.setMaximumSize(new Dimension(150, 21));
        this.jTextFieldHostName.setMinimumSize(new Dimension(150, 21));
        this.jTextFieldHostName.setPreferredSize(new Dimension(150, 21));
        this.jTextFieldHostName.setText(this.application.hostName);
        this.jTextFieldHostName.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                AttachToProcessDialog.this.jTextFieldHostName_keyPressed(keyEvent);
            }
        });
        this.jLabelHostName.setMaximumSize(new Dimension(66, 17));
        this.jLabelHostName.setText("Host Name:");
        this.jTextFieldPort2.setEnabled(false);
        this.jTextFieldPort2.setMaximumSize(new Dimension(150, 21));
        this.jTextFieldPort2.setMinimumSize(new Dimension(150, 21));
        this.jTextFieldPort2.setPreferredSize(new Dimension(150, 21));
        this.jTextFieldPort2.setText(this.application.port2);
        this.jTextFieldPort2.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                AttachToProcessDialog.this.jTextFieldPort2_keyPressed(keyEvent);
            }
        });
        this.jLabelPort2.setMaximumSize(new Dimension(66, 17));
        this.jLabelPort2.setMinimumSize(new Dimension(66, 17));
        this.jLabelPort2.setPreferredSize(new Dimension(66, 17));
        this.jLabelPort2.setText("Port:");
        this.jButtonOk.setMaximumSize(new Dimension(73, 27));
        this.jButtonOk.setMinimumSize(new Dimension(73, 27));
        this.jButtonOk.setPreferredSize(new Dimension(73, 27));
        this.jButtonOk.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                AttachToProcessDialog.this.jButtonOk_actionPerformed(actionEvent);
            }
        });
        this.jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                AttachToProcessDialog.this.jButtonCancel_actionPerformed(actionEvent);
            }
        });
        this.panel1.setBorder(this.titledBorder1);
        this.panel1.setMaximumSize(new Dimension(600, 600));
        this.panel1.setMinimumSize(new Dimension(475, 350));
        this.panel1.setPreferredSize(new Dimension(475, 350));
        this.jButtonCancel.setMaximumSize(new Dimension(90, 32));
        this.jButtonCancel.setMinimumSize(new Dimension(73, 27));
        this.jButtonCancel.setPreferredSize(new Dimension(73, 27));
        this.jPanel1.setLayout(this.borderLayout2);
        this.getContentPane().setLayout(this.borderLayout3);
        this.jLabel2.setText(" ");
        this.jLabel4.setMaximumSize(new Dimension(85, 27));
        this.jLabel4.setMinimumSize(new Dimension(85, 27));
        this.jLabel4.setPreferredSize(new Dimension(85, 27));
        this.jLabel4.setText(" ");
        this.jLabel3.setText(" ");
        this.jPanelTop.setMinimumSize(new Dimension(350, 86));
        this.jPanelTop.setPreferredSize(new Dimension(350, 86));
        this.jPanel1.setMinimumSize(new Dimension(350, 59));
        this.jPanel1.setPreferredSize(new Dimension(350, 59));
        this.buttonGroup1.add(this.jRadioButtonMakeAttach);
        this.buttonGroup1.add(this.jRadioButtonAttach);
        this.jButtonCancel.setText("Cancel");
        this.jButtonOk.setText("OK");
        this.getContentPane().add(this.panel1, "Center");
        this.panel1.add(this.box1, "Center");
        this.box1.add(this.jRadioButtonMakeAttach, null);
        this.box1.add(this.jPanelTop, null);
        this.jPanelTop.add(this.boxIdPort, null);
        this.box1.add(this.jRadioButtonAttach, null);
        this.box1.add(this.jPanel1, null);
        this.jPanel1.add(this.box3, "Center");
        this.panel1.add(this.jPanelOkCancel, "South");
        this.jPanelOkCancel.add(this.jButtonOk, null);
        this.jPanelOkCancel.add(this.jButtonCancel, null);
        this.box4.add(this.jLabelPort2, null);
        this.box4.add(this.jTextFieldPort2, null);
        this.box3.add(this.box5, null);
        this.box3.add(this.jLabel2, null);
        this.box3.add(this.box4, null);
        this.box5.add(this.jLabelHostName, null);
        this.box5.add(this.jTextFieldHostName, null);
        this.boxIdPort.add(this.boxID, null);
        this.boxIdPort.add(this.jLabel3, null);
        this.boxIdPort.add(this.boxPort1, null);
        this.boxID.add(this.jLabel1, null);
        this.boxID.add(this.jTextFieldProcessid, null);
        this.boxID.add(this.jButtonGetList, null);
        this.boxPort1.add(this.jLabelPort1, null);
        this.boxPort1.add(this.jTextFieldPort1, null);
        this.boxPort1.add(this.jLabel4, null);
    }
    
    void jButtonOk_actionPerformed(final ActionEvent actionEvent) {
        if (this.jRadioButtonMakeAttach.isSelected()) {
            final String text = this.jTextFieldProcessid.getText();
            final String text2 = this.jTextFieldPort1.getText();
            if (text.length() > 0 && this.isInteger(text)) {
                if (text2.length() <= 0 || !this.isInteger(text2)) {
                    JOptionPane.showMessageDialog(this, "You must enter an integer for the port number.", "Error", 0);
                    return;
                }
                final short short1 = Short.parseShort(text2);
                this.application.attachId = text;
                final int pingOnJNI = this.application.prodll.pingOnJNI(text, short1);
                final String errorMsg = this.application.prodll.getErrorMsg();
                if (pingOnJNI != 0) {
                    JOptionPane.showMessageDialog(this.application, errorMsg);
                }
                else {
                    try {
                        this.application.initializeAttachSocket("localhost", this.application.prodll.getPortNumber());
                        this.application.recvSocket.lock();
                        this.application.changeAttachMenuText();
                        this.application.setPropMessage("SETPROP RELPATH 0 GENLISTING 1");
                        this.application.sendSocket.sendMessage("SETPROP RELPATH 0 GENLISTING 1");
                        this.application.processid = this.jTextFieldProcessid.getText();
                        this.application.port1 = this.jTextFieldPort1.getText();
                        this.dispose();
                        final String string = new Short(this.application.prodll.getPortNumber()).toString();
                        final StringBuffer sb = new StringBuffer();
                        if (errorMsg.length() > 0) {
                            sb.append(errorMsg);
                        }
                        sb.append("Using port " + string);
                        JOptionPane.showMessageDialog(this.application, sb.toString());
                        this.application.madePVMDebugReady = true;
                    }
                    catch (Exception ex) {}
                    if (this.application.recvSocket != null) {
                        this.application.recvSocket.unlock();
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Please specify a process Id to attach to.", "Error", 0);
            }
        }
        else if (this.jTextFieldPort2.getText().length() > 0 && this.isInteger(this.jTextFieldPort2.getText()) && this.jTextFieldPort2.getText().compareTo("0") != 0 && this.jTextFieldHostName.getText().length() > 0) {
            final int int1 = Integer.parseInt(this.jTextFieldPort2.getText());
            try {
                this.application.initializeAttachSocket(this.jTextFieldHostName.getText(), int1);
                if (this.jTextFieldHostName.getText().compareTo("localhost") == 0) {
                    this.application.setPropMessage("SETPROP RELPATH 0 GENLISTING 1");
                    this.application.sendSocket.sendMessage("SETPROP RELPATH 0 GENLISTING 1");
                    this.application.changeAttachMenuText();
                }
                else {
                    this.application.setPropMessage("SETPROP RELPATH 1 GENLISTING 0");
                    this.application.sendSocket.sendMessage("SETPROP RELPATH 1 GENLISTING 0");
                    this.application.changeAttachMenuText();
                    this.application.localHost = false;
                }
                this.application.hostName = this.jTextFieldHostName.getText();
                this.application.port2 = this.jTextFieldPort2.getText();
                this.dispose();
            }
            catch (Exception ex2) {}
        }
        else {
            JOptionPane.showMessageDialog(this, "You must enter a host name and an integer greater than zero for the port number.", "Error", 0);
        }
    }
    
    void jButtonCancel_actionPerformed(final ActionEvent actionEvent) {
        this.dispose();
    }
    
    void jButtonGetList_actionPerformed(final ActionEvent actionEvent) {
        final Vector vector = new Vector<String>();
        if (this.application.prodll.getProcessInfoJNI() == 0) {
            for (int numProcessInfo = this.application.prodll.getNumProcessInfo(), i = 0; i < numProcessInfo; ++i) {
                vector.addElement(this.application.prodll.getProcessInfoAt(i));
            }
            if (vector.size() > 0) {
                final ProcessListDialog processListDialog = new ProcessListDialog(this.application, this, vector);
                final Dimension preferredSize = processListDialog.getPreferredSize();
                final Dimension size = this.getSize();
                final Point location = this.getLocation();
                processListDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
                processListDialog.jTextField1.requestFocus(true);
                processListDialog.show();
            }
            else {
                JOptionPane.showMessageDialog(this, "No 4GL process is running on this machine.", "Error", 0);
            }
        }
    }
    
    void jRadioButtonAttach_actionPerformed(final ActionEvent actionEvent) {
        if (this.jRadioButtonAttach.isSelected()) {
            this.jTextFieldProcessid.setEnabled(false);
            this.jTextFieldPort1.setEnabled(false);
            this.jTextFieldHostName.setEnabled(true);
            this.jTextFieldPort2.setEnabled(true);
            this.jButtonGetList.setEnabled(false);
        }
    }
    
    void jRadioButtonMakeAttach_actionPerformed(final ActionEvent actionEvent) {
        if (this.jRadioButtonMakeAttach.isSelected()) {
            this.jTextFieldProcessid.setEnabled(true);
            this.jTextFieldPort1.setEnabled(true);
            this.jTextFieldHostName.setEnabled(false);
            this.jTextFieldPort2.setEnabled(false);
            this.jButtonGetList.setEnabled(true);
        }
    }
    
    boolean isInteger(final String s) {
        try {
            return Integer.parseInt(s) >= 0;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    void jTextFieldProcessid_keyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonOk_actionPerformed(null);
        }
    }
    
    void jTextFieldPort1_keyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonOk_actionPerformed(null);
        }
    }
    
    void jTextFieldHostName_keyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonOk_actionPerformed(null);
        }
    }
    
    void jTextFieldPort2_keyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonOk_actionPerformed(null);
        }
    }
}
