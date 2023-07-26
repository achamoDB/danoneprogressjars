// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class NewBreakpointDialog extends JDialog
{
    JPanel panel1;
    BorderLayout borderLayout1;
    Box box4;
    JTabbedPane jTabbedPane1;
    JButton jButtonOK;
    JButton jButtonCancel;
    JPanel jPanelAtLine;
    JPanel jPanelOnError;
    Box boxAtLine;
    JPanel jPanelProcedure;
    JPanel jPanelCondition;
    JPanel jPanelLine;
    JLabel jLabelProcedure;
    JTextField jTextFieldProcedure;
    JTextField jTextFieldLine;
    JLabel jLabelLine;
    JTextField jTextFieldCondition;
    JLabel jLabelCondition;
    Box box2;
    JRadioButton jRadioButtonOnError;
    JPanel jPanel1;
    JRadioButton jRadioButtonAnyUnError;
    JTextField jTextFieldErrorNum;
    JLabel jLabel3;
    ButtonGroup buttonGroup1;
    JLabel jLabel4;
    JLabel jLabel5;
    JPanel jPanel2;
    JPanel jPanel3;
    JPanel jPanel4;
    JPanel jPanel5;
    JPanel jPanel6;
    private boolean newBreakpoint;
    Frame1 application;
    BreakpointDialogBox breakDialog;
    String breakpointNumberEditing;
    String lineOrErrorNumber;
    JRadioButton jRadioButtonAnyError;
    JPanel jPanelWatchpoint;
    Box box3;
    JPanel jPanelExpression;
    JPanel jPanelWatchCondition;
    JTextField jTextFieldWatchCondition;
    JLabel jLabelWatchCondition;
    JPanel jPanelExpLabel;
    JLabel jLabel1;
    JTextField jTextFieldExp;
    JLabel jLabelExpression;
    JCheckBox jCheckBoxAddWatch;
    Box box6;
    JPanel jPanel7;
    JPanel jPanel8;
    JPanel jPanel9;
    
    public NewBreakpointDialog(final Frame1 frame1, final BreakpointDialogBox breakDialog, final String title, final String breakpointNumberEditing, final String s, final String s2, final String lineOrErrorNumber) {
        super(frame1, title, true);
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jTabbedPane1 = new JTabbedPane();
        this.jButtonOK = new JButton();
        this.jButtonCancel = new JButton();
        this.jPanelAtLine = new JPanel();
        this.jPanelOnError = new JPanel();
        this.jPanelProcedure = new JPanel();
        this.jPanelCondition = new JPanel();
        this.jPanelLine = new JPanel();
        this.jLabelProcedure = new JLabel();
        this.jTextFieldProcedure = new JTextField();
        this.jTextFieldLine = new JTextField();
        this.jLabelLine = new JLabel();
        this.jTextFieldCondition = new JTextField();
        this.jLabelCondition = new JLabel();
        this.jRadioButtonOnError = new JRadioButton();
        this.jPanel1 = new JPanel();
        this.jRadioButtonAnyUnError = new JRadioButton();
        this.jTextFieldErrorNum = new JTextField();
        this.jLabel3 = new JLabel();
        this.buttonGroup1 = new ButtonGroup();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jPanel2 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel6 = new JPanel();
        this.jRadioButtonAnyError = new JRadioButton();
        this.jPanelWatchpoint = new JPanel();
        this.jPanelExpression = new JPanel();
        this.jPanelWatchCondition = new JPanel();
        this.jTextFieldWatchCondition = new JTextField();
        this.jLabelWatchCondition = new JLabel();
        this.jPanelExpLabel = new JPanel();
        this.jLabel1 = new JLabel();
        this.jTextFieldExp = new JTextField();
        this.jLabelExpression = new JLabel();
        this.jCheckBoxAddWatch = new JCheckBox();
        this.jPanel7 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jPanel9 = new JPanel();
        try {
            this.application = frame1;
            this.breakDialog = breakDialog;
            this.jbInit();
            if (title.compareTo("New Breakpoint") == 0) {
                this.newBreakpoint = true;
                this.jTextFieldErrorNum.setEnabled(false);
            }
            else {
                this.newBreakpoint = false;
                this.breakpointNumberEditing = breakpointNumberEditing;
                this.lineOrErrorNumber = lineOrErrorNumber;
                this.setTextFields(s, s2);
            }
            this.pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public NewBreakpointDialog() {
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jTabbedPane1 = new JTabbedPane();
        this.jButtonOK = new JButton();
        this.jButtonCancel = new JButton();
        this.jPanelAtLine = new JPanel();
        this.jPanelOnError = new JPanel();
        this.jPanelProcedure = new JPanel();
        this.jPanelCondition = new JPanel();
        this.jPanelLine = new JPanel();
        this.jLabelProcedure = new JLabel();
        this.jTextFieldProcedure = new JTextField();
        this.jTextFieldLine = new JTextField();
        this.jLabelLine = new JLabel();
        this.jTextFieldCondition = new JTextField();
        this.jLabelCondition = new JLabel();
        this.jRadioButtonOnError = new JRadioButton();
        this.jPanel1 = new JPanel();
        this.jRadioButtonAnyUnError = new JRadioButton();
        this.jTextFieldErrorNum = new JTextField();
        this.jLabel3 = new JLabel();
        this.buttonGroup1 = new ButtonGroup();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jPanel2 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jPanel4 = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel6 = new JPanel();
        this.jRadioButtonAnyError = new JRadioButton();
        this.jPanelWatchpoint = new JPanel();
        this.jPanelExpression = new JPanel();
        this.jPanelWatchCondition = new JPanel();
        this.jTextFieldWatchCondition = new JTextField();
        this.jLabelWatchCondition = new JLabel();
        this.jPanelExpLabel = new JPanel();
        this.jLabel1 = new JLabel();
        this.jTextFieldExp = new JTextField();
        this.jLabelExpression = new JLabel();
        this.jCheckBoxAddWatch = new JCheckBox();
        this.jPanel7 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jPanel9 = new JPanel();
    }
    
    void jbInit() throws Exception {
        this.box4 = Box.createVerticalBox();
        this.boxAtLine = Box.createVerticalBox();
        this.box2 = Box.createVerticalBox();
        this.box3 = Box.createVerticalBox();
        this.jPanel8.setMinimumSize(new Dimension(80, 25));
        this.jPanel8.setPreferredSize(new Dimension(80, 25));
        this.jPanelWatchpoint.add(this.box3, null);
        this.box6 = Box.createHorizontalBox();
        this.panel1.setLayout(this.borderLayout1);
        this.jButtonOK.setMaximumSize(new Dimension(73, 27));
        this.jButtonOK.setMinimumSize(new Dimension(73, 27));
        this.jButtonOK.setPreferredSize(new Dimension(73, 27));
        this.jButtonOK.setText("OK");
        this.jButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                NewBreakpointDialog.this.jButtonOK_actionPerformed(actionEvent);
            }
        });
        this.jButtonCancel.setMaximumSize(new Dimension(73, 27));
        this.jButtonCancel.setMinimumSize(new Dimension(73, 27));
        this.jButtonCancel.setPreferredSize(new Dimension(73, 27));
        this.jButtonCancel.setText("Cancel");
        this.jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                NewBreakpointDialog.this.jButtonCancel_actionPerformed(actionEvent);
            }
        });
        this.jLabelProcedure.setText("Procedure Name:");
        this.jLabelProcedure.setMaximumSize(new Dimension(210, 17));
        this.jLabelProcedure.setMinimumSize(new Dimension(94, 17));
        this.jLabelProcedure.setPreferredSize(new Dimension(150, 17));
        this.jTextFieldProcedure.setMaximumSize(new Dimension(200, 21));
        this.jTextFieldProcedure.setPreferredSize(new Dimension(200, 21));
        this.jTextFieldProcedure.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                NewBreakpointDialog.this.jTextFieldProcedure_keyReleased(keyEvent);
            }
        });
        this.jTextFieldProcedure.requestFocus(true);
        this.jLabelLine.setMaximumSize(new Dimension(200, 17));
        this.jLabelLine.setMinimumSize(new Dimension(94, 17));
        this.jLabelLine.setPreferredSize(new Dimension(150, 17));
        this.jLabelLine.setText("Line Number:");
        this.jTextFieldCondition.setPreferredSize(new Dimension(200, 21));
        this.jTextFieldCondition.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                NewBreakpointDialog.this.jTextFieldCondition_keyReleased(keyEvent);
            }
        });
        this.jLabelCondition.setMaximumSize(new Dimension(200, 17));
        this.jLabelCondition.setPreferredSize(new Dimension(150, 17));
        this.jLabelCondition.setText("Condition:");
        this.jTextFieldLine.setPreferredSize(new Dimension(200, 21));
        this.jTextFieldLine.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                NewBreakpointDialog.this.jTextFieldLine_keyReleased(keyEvent);
            }
        });
        this.jRadioButtonOnError.setMaximumSize(new Dimension(300, 25));
        this.jRadioButtonOnError.setPreferredSize(new Dimension(300, 25));
        this.jRadioButtonOnError.setText("On Error");
        this.jRadioButtonOnError.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                NewBreakpointDialog.this.jRadioButtonOnError_actionPerformed(actionEvent);
            }
        });
        this.jRadioButtonAnyUnError.setMaximumSize(new Dimension(300, 25));
        this.jRadioButtonAnyUnError.setPreferredSize(new Dimension(300, 25));
        this.jRadioButtonAnyUnError.setSelected(true);
        this.jRadioButtonAnyUnError.setText("On Any Unsuppressed Error");
        this.jRadioButtonAnyUnError.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                NewBreakpointDialog.this.jRadioButtonAnyUnError_actionPerformed(actionEvent);
            }
        });
        this.jLabel3.setText("Error Number:");
        this.jTextFieldErrorNum.setPreferredSize(new Dimension(50, 21));
        this.jTextFieldErrorNum.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                NewBreakpointDialog.this.jTextFieldErrorNum_keyReleased(keyEvent);
            }
        });
        this.jTabbedPane1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                NewBreakpointDialog.this.jTabbedPane1_keyReleased(keyEvent);
            }
        });
        this.jLabel4.setText("          ");
        this.jLabel5.setAlignmentX(0.5f);
        this.jLabel5.setMaximumSize(new Dimension(70, 7));
        this.jLabel5.setMinimumSize(new Dimension(8, 7));
        this.jLabel5.setPreferredSize(new Dimension(20, 17));
        this.jLabel5.setText("   ");
        this.panel1.setMaximumSize(new Dimension(20000, 20000));
        this.panel1.setMinimumSize(new Dimension(600, 200));
        this.panel1.setPreferredSize(new Dimension(600, 200));
        this.jPanelAtLine.setMinimumSize(new Dimension(450, 144));
        this.jPanelAtLine.setPreferredSize(new Dimension(450, 144));
        this.jTabbedPane1.setMinimumSize(new Dimension(450, 144));
        this.jTabbedPane1.setPreferredSize(new Dimension(450, 144));
        this.jPanelCondition.setMinimumSize(new Dimension(113, 31));
        this.jRadioButtonAnyError.setMaximumSize(new Dimension(300, 25));
        this.jRadioButtonAnyError.setPreferredSize(new Dimension(300, 25));
        this.jRadioButtonAnyError.setText("On Any Error");
        this.jRadioButtonAnyError.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                NewBreakpointDialog.this.jRadioButtonAnyError_actionPerformed(actionEvent);
            }
        });
        this.jLabelWatchCondition.setAlignmentX(0.5f);
        this.jLabelWatchCondition.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabelWatchCondition.setMinimumSize(new Dimension(100, 17));
        this.jLabelWatchCondition.setPreferredSize(new Dimension(100, 17));
        this.jLabelWatchCondition.setText("Condition:");
        this.jTextFieldWatchCondition.setMinimumSize(new Dimension(200, 21));
        this.jTextFieldWatchCondition.setPreferredSize(new Dimension(200, 21));
        this.jTextFieldWatchCondition.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                NewBreakpointDialog.this.jTextFieldErrorNum_keyReleased(keyEvent);
            }
        });
        this.jLabel1.setAlignmentX(0.5f);
        this.jLabel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabel1.setMinimumSize(new Dimension(350, 17));
        this.jLabel1.setPreferredSize(new Dimension(350, 17));
        this.jLabel1.setText("(Variable, field, or attribute reference to watch)");
        this.jLabelExpression.setAlignmentX(0.5f);
        this.jLabelExpression.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabelExpression.setMinimumSize(new Dimension(100, 17));
        this.jLabelExpression.setPreferredSize(new Dimension(100, 17));
        this.jLabelExpression.setText("Expression:");
        this.jTextFieldExp.setMinimumSize(new Dimension(200, 21));
        this.jTextFieldExp.setPreferredSize(new Dimension(200, 21));
        this.jTextFieldExp.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                NewBreakpointDialog.this.jTextFieldErrorNum_keyReleased(keyEvent);
            }
        });
        this.jCheckBoxAddWatch.setSelected(true);
        this.jCheckBoxAddWatch.setText("Add Watch");
        this.jPanelExpLabel.setMinimumSize(new Dimension(450, 35));
        this.jPanelExpLabel.setPreferredSize(new Dimension(450, 35));
        this.jPanelWatchCondition.setMinimumSize(new Dimension(450, 35));
        this.jPanelWatchCondition.setPreferredSize(new Dimension(450, 35));
        this.jPanelOnError.setMinimumSize(new Dimension(450, 144));
        this.jPanelOnError.setPreferredSize(new Dimension(450, 144));
        this.jPanelWatchpoint.setMinimumSize(new Dimension(450, 144));
        this.jPanelWatchpoint.setPreferredSize(new Dimension(450, 144));
        this.jPanelExpression.setMinimumSize(new Dimension(450, 35));
        this.jPanelExpression.setPreferredSize(new Dimension(450, 35));
        this.getContentPane().add(this.panel1);
        this.panel1.add(this.box4, "East");
        this.box4.add(this.jButtonOK, null);
        this.box4.add(this.jLabel5, null);
        this.box4.add(this.jButtonCancel, null);
        this.panel1.add(this.jTabbedPane1, "Center");
        this.jTabbedPane1.add(this.jPanelAtLine, "At Line");
        this.jPanelAtLine.add(this.boxAtLine, null);
        this.boxAtLine.add(this.jPanel2, null);
        this.boxAtLine.add(this.jPanelProcedure, null);
        this.jPanelProcedure.add(this.jLabelProcedure, null);
        this.jPanelProcedure.add(this.jTextFieldProcedure, null);
        this.boxAtLine.add(this.jPanelLine, null);
        this.boxAtLine.add(this.jPanelCondition, null);
        this.jTabbedPane1.add(this.jPanelOnError, "On Error");
        this.jPanelOnError.add(this.box2, null);
        this.box2.add(this.jPanel9, null);
        this.box2.add(this.jRadioButtonAnyUnError, null);
        this.box2.add(this.jRadioButtonAnyError, null);
        this.box2.add(this.jRadioButtonOnError, null);
        this.box2.add(this.jPanel1, null);
        this.jPanel1.add(this.jLabel4, null);
        this.jPanel1.add(this.jLabel3, null);
        this.jPanel1.add(this.jTextFieldErrorNum, null);
        this.jTabbedPane1.add(this.jPanelWatchpoint, "Watchpoint");
        this.box3.add(this.jPanel7, null);
        this.box3.add(this.jPanelExpression, null);
        this.jPanelExpLabel.add(this.jLabel1, null);
        this.box3.add(this.jPanelExpLabel, null);
        this.box3.add(this.jPanelWatchCondition, null);
        this.panel1.add(this.jPanel3, "South");
        this.panel1.add(this.jPanel4, "West");
        this.panel1.add(this.jPanel5, "North");
        this.getContentPane().add(this.jPanel6, "East");
        this.jPanelLine.add(this.jLabelLine, null);
        this.jPanelLine.add(this.jTextFieldLine, null);
        this.jPanelCondition.add(this.jLabelCondition, null);
        this.jPanelCondition.add(this.jTextFieldCondition, null);
        this.buttonGroup1.add(this.jRadioButtonAnyUnError);
        this.buttonGroup1.add(this.jRadioButtonOnError);
        this.buttonGroup1.add(this.jRadioButtonAnyError);
        this.jPanelWatchCondition.add(this.jLabelWatchCondition, null);
        this.jPanelWatchCondition.add(this.jTextFieldWatchCondition, null);
        this.jPanelWatchCondition.add(this.jPanel8, null);
        this.jPanelExpression.add(this.jLabelExpression, null);
        this.jPanelExpression.add(this.jTextFieldExp, null);
        this.jPanelExpression.add(this.jCheckBoxAddWatch, null);
        this.jLabel3.setMaximumSize(new Dimension(200, 17));
        this.jLabel3.setPreferredSize(new Dimension(120, 17));
        this.jCheckBoxAddWatch.setAlignmentX(0.5f);
    }
    
    void jButtonOK_actionPerformed(final ActionEvent actionEvent) {
        final int selectedIndex = this.jTabbedPane1.getSelectedIndex();
        if (selectedIndex == 0) {
            if (this.jTextFieldProcedure.getText().length() == 0 && this.jTextFieldLine.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "You must enter a procedure name or a line number.", "Error", 0);
                this.jTextFieldLine.requestFocus(true);
                return;
            }
            String s;
            if (this.newBreakpoint) {
                s = "break ";
            }
            else {
                s = "update break " + this.breakpointNumberEditing + " ";
            }
            if (this.jTextFieldProcedure.getText().length() > 0) {
                s = s.concat(this.jTextFieldProcedure.getText() + " ");
            }
            if (this.jTextFieldLine.getText().length() > 0) {
                if (!this.isInteger(this.jTextFieldLine.getText())) {
                    JOptionPane.showMessageDialog(this, "You must enter an integer for the line number.", "Error", 0);
                    this.jTextFieldLine.setText("");
                    this.jTextFieldLine.requestFocus(true);
                    return;
                }
                s = s.concat(this.jTextFieldLine.getText() + " ");
            }
            if (this.jTextFieldCondition.getText().length() > 0) {
                s = s.concat(" when " + this.jTextFieldCondition.getText());
            }
            this.application.sendSocket.sendMessage(s);
        }
        else if (selectedIndex == 1) {
            if (this.newBreakpoint) {
                String s2 = "break error ";
                if (this.jRadioButtonOnError.isSelected()) {
                    if (!this.isInteger(this.jTextFieldErrorNum.getText())) {
                        JOptionPane.showMessageDialog(this, "You must enter an integer for the error number.", "Error", 0);
                        this.jTextFieldErrorNum.setText("");
                        this.jTextFieldErrorNum.requestFocus(true);
                        return;
                    }
                    s2 = s2.concat(this.jTextFieldErrorNum.getText());
                }
                else if (this.jRadioButtonAnyError.isSelected()) {
                    s2 = s2.concat("-1");
                }
                else if (this.jRadioButtonAnyUnError.isSelected()) {
                    s2 = s2.concat("-2");
                }
                this.application.sendSocket.sendMessage(s2);
            }
            else {
                String s3 = "update break " + this.breakpointNumberEditing + " error ";
                if (this.jRadioButtonOnError.isSelected()) {
                    if (!this.isInteger(this.jTextFieldErrorNum.getText())) {
                        JOptionPane.showMessageDialog(this, "You must enter an integer for the error number.", "Error", 0);
                        this.jTextFieldErrorNum.setText("");
                        this.jTextFieldErrorNum.requestFocus(true);
                        return;
                    }
                    s3 = s3.concat(this.jTextFieldErrorNum.getText());
                }
                else if (this.jRadioButtonAnyError.isSelected()) {
                    s3 = s3.concat("-1");
                }
                else if (this.jRadioButtonAnyUnError.isSelected()) {
                    s3 = s3.concat("-2");
                }
                this.application.sendSocket.sendMessage(s3);
            }
        }
        else {
            if (this.jTextFieldExp.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "You must enter an expression for the watchpoint.", "Error", 0);
                this.jTextFieldExp.requestFocus(true);
                return;
            }
            if (this.newBreakpoint) {
                String s4 = "watchpoint " + this.jTextFieldExp.getText();
                if (this.jTextFieldWatchCondition.getText().length() > 0) {
                    s4 = s4.concat(" when " + this.jTextFieldWatchCondition.getText());
                }
                this.application.sendSocket.sendMessage(s4);
            }
            else {
                String s5 = "update watchpoint " + this.breakpointNumberEditing + " " + this.jTextFieldExp.getText();
                if (this.jTextFieldWatchCondition.getText().length() > 0) {
                    s5 = s5.concat(" when " + this.jTextFieldWatchCondition.getText());
                }
                this.application.sendSocket.sendMessage(s5);
            }
            if (this.jCheckBoxAddWatch.isSelected()) {
                this.application.sendSocket.sendMessage("watch " + this.jTextFieldExp.getText());
                this.application.sendSocket.sendMessage("show watch");
            }
        }
        this.dispose();
        this.breakDialog.checkEnabled();
        this.breakDialog.dispose();
        this.application.sendSocket.sendMessage("show breaks");
    }
    
    void jButtonCancel_actionPerformed(final ActionEvent actionEvent) {
        this.dispose();
    }
    
    void jTextFieldProcedure_keyReleased(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonOK_actionPerformed(null);
        }
    }
    
    void jTextFieldLine_keyReleased(final KeyEvent keyEvent) {
        this.jTextFieldProcedure_keyReleased(keyEvent);
    }
    
    void jTextFieldCondition_keyReleased(final KeyEvent keyEvent) {
        this.jTextFieldProcedure_keyReleased(keyEvent);
    }
    
    void jTextFieldErrorNum_keyReleased(final KeyEvent keyEvent) {
        this.jTextFieldProcedure_keyReleased(keyEvent);
    }
    
    void setTextFields(final String text, final String s) {
        if (s.compareToIgnoreCase("e") == 0) {
            this.jTabbedPane1.setEnabledAt(0, false);
            this.jTabbedPane1.setEnabledAt(2, false);
            this.jTabbedPane1.setSelectedIndex(1);
            if (text.indexOf("Break on any unsuppressed error") >= 0) {
                this.jRadioButtonAnyUnError.setSelected(true);
            }
            else if (text.indexOf("Break on any error") >= 0) {
                this.jRadioButtonAnyError.setSelected(true);
            }
            else {
                this.jRadioButtonOnError.setSelected(true);
                this.jTextFieldErrorNum.setText(text.substring(text.indexOf("error") + 6, text.length()));
            }
        }
        else if (s.compareToIgnoreCase("c") == 0) {
            this.jTabbedPane1.setEnabledAt(1, false);
            final int index = text.indexOf("line #");
            if (index >= 0) {
                this.jTextFieldProcedure.setText(text.substring(0, index));
                final String substring = text.substring(index + 6, text.length());
                final int index2 = substring.indexOf(" ");
                final String substring2 = substring.substring(index2, substring.length());
                final String substring3 = substring.substring(0, index2);
                this.jTextFieldCondition.setText(substring2);
                this.jTextFieldLine.setText(substring3);
            }
            else {
                final int index3 = text.indexOf(" ");
                this.jTextFieldProcedure.setText(text.substring(0, index3));
                this.jTextFieldCondition.setText(text.substring(index3, text.length()));
            }
        }
        else if (s.compareToIgnoreCase("w") == 0) {
            this.jTabbedPane1.setSelectedIndex(2);
            this.jTabbedPane1.setEnabledAt(0, false);
            this.jTabbedPane1.setEnabledAt(1, false);
            this.jCheckBoxAddWatch.setSelected(false);
            this.jTextFieldExp.setText(text.substring(text.indexOf(" ") + 1, text.indexOf("changes") - 1));
        }
        else if (s.compareToIgnoreCase("x") == 0) {
            this.jTabbedPane1.setSelectedIndex(2);
            this.jTabbedPane1.setEnabledAt(0, false);
            this.jTabbedPane1.setEnabledAt(1, false);
            this.jCheckBoxAddWatch.setSelected(false);
            this.jTextFieldExp.setText(text.substring(text.indexOf(" ") + 1, text.indexOf("changes") - 1));
            this.jTextFieldWatchCondition.setText(text.substring(text.indexOf("and") + 4, text.length() - 1));
        }
        else {
            this.jTabbedPane1.setEnabledAt(1, false);
            this.jTabbedPane1.setEnabledAt(2, false);
            final int index4 = text.indexOf("line #");
            if (index4 >= 0) {
                this.jTextFieldProcedure.setText(text.substring(0, index4));
                final String trim = text.substring(index4 + 6, text.length()).trim();
                if (trim.compareTo("0") != 0) {
                    this.jTextFieldLine.setText(trim);
                }
            }
            else {
                this.jTextFieldProcedure.setText(text);
            }
        }
    }
    
    void jRadioButtonAnyUnError_actionPerformed(final ActionEvent actionEvent) {
        if (this.jRadioButtonAnyUnError.isSelected()) {
            this.jTextFieldErrorNum.setEnabled(false);
        }
        else {
            this.jTextFieldErrorNum.setEnabled(true);
        }
    }
    
    void jRadioButtonOnError_actionPerformed(final ActionEvent actionEvent) {
        if (this.jRadioButtonOnError.isSelected()) {
            this.jTextFieldErrorNum.setEnabled(true);
            this.jTextFieldErrorNum.requestFocus(true);
        }
        else {
            this.jTextFieldErrorNum.setEnabled(false);
        }
    }
    
    void jRadioButtonAnyError_actionPerformed(final ActionEvent actionEvent) {
        if (this.jRadioButtonAnyError.isSelected()) {
            this.jTextFieldErrorNum.setEnabled(false);
        }
        else {
            this.jTextFieldErrorNum.setEnabled(true);
        }
    }
    
    void jTabbedPane1_keyReleased(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
    }
    
    boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
