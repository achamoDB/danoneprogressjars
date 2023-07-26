// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Frame;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;

public class FindDialog extends JDialog
{
    private ButtonGroup buttonGroupUpDown;
    private JPanel jPanel1;
    private JTextField jTextFieldFind;
    private JButton jButtonFind;
    private JButton jButtonCancel;
    private JPanel jPanel2;
    private JRadioButton jRadioButtonUp;
    private JRadioButton jRadioButtonDown;
    private JCheckBox jCheckBoxMatchCase;
    private Frame1 application;
    private boolean matchCase;
    private boolean searchDown;
    BorderLayout borderLayout1;
    Box box1;
    JPanel jPanelButtons;
    JPanel jPanel4;
    JLabel jLabelFind;
    Box boxButtons;
    JLabel jLabel1;
    Box box2;
    JPanel jPanel5;
    JPanel jPanel6;
    JLabel jLabel2;
    
    public FindDialog(final Frame1 frame1, final String title, final boolean modal) {
        super(frame1, title, modal);
        this.searchDown = true;
        this.borderLayout1 = new BorderLayout();
        this.jPanelButtons = new JPanel();
        this.jPanel4 = new JPanel();
        this.jLabelFind = new JLabel();
        this.jLabel1 = new JLabel();
        this.jPanel5 = new JPanel();
        this.jPanel6 = new JPanel();
        this.jLabel2 = new JLabel();
        try {
            this.application = frame1;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    public FindDialog() {
        this((Frame1)null, "", false);
    }
    
    void jbInit() throws Exception {
        this.box1 = Box.createHorizontalBox();
        this.boxButtons = Box.createVerticalBox();
        this.box2 = Box.createVerticalBox();
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                FindDialog.this.dialogKeyPressed(keyEvent);
            }
        });
        this.buttonGroupUpDown = new ButtonGroup();
        this.jPanel1 = new JPanel();
        (this.jTextFieldFind = new JTextField()).setPreferredSize(new Dimension(250, 21));
        this.jTextFieldFind.setText(this.application.frameUtl.getLastWordSearch());
        this.jTextFieldFind.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                FindDialog.this.jTextFieldFind_keyReleased(keyEvent);
            }
        });
        this.jButtonFind = new JButton();
        this.jButtonCancel = new JButton();
        this.jPanel2 = new JPanel();
        this.jRadioButtonUp = new JRadioButton();
        (this.jRadioButtonDown = new JRadioButton()).setFont(new Font("Dialog", 0, 12));
        this.jRadioButtonDown.setSelected(true);
        this.jCheckBoxMatchCase = new JCheckBox();
        this.jPanel1.setLayout(this.borderLayout1);
        this.jButtonFind.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                FindDialog.this.jButtonFind_actionPerformed(actionEvent);
            }
        });
        this.jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                FindDialog.this.jButtonCancel_actionPerformed(actionEvent);
            }
        });
        this.jCheckBoxMatchCase.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                FindDialog.this.jCheckBoxMatchCase_actionPerformed(actionEvent);
            }
        });
        this.jRadioButtonUp.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                FindDialog.this.jRadioButtonUp_actionPerformed(actionEvent);
            }
        });
        this.jRadioButtonDown.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                FindDialog.this.jRadioButtonDown_actionPerformed(actionEvent);
            }
        });
        this.jPanel1.setFont(new Font("Dialog", 0, 12));
        this.jPanel1.setMinimumSize(new Dimension(440, 104));
        this.jPanel1.setPreferredSize(new Dimension(440, 104));
        this.jButtonFind.setFont(new Font("Dialog", 0, 12));
        this.jButtonFind.setMaximumSize(new Dimension(85, 27));
        this.jButtonFind.setMinimumSize(new Dimension(119, 30));
        this.jButtonFind.setPreferredSize(new Dimension(119, 30));
        this.jButtonCancel.setFont(new Font("Dialog", 0, 12));
        this.jButtonCancel.setMaximumSize(new Dimension(73, 27));
        this.jButtonCancel.setMinimumSize(new Dimension(73, 27));
        this.jButtonCancel.setPreferredSize(new Dimension(73, 27));
        this.jRadioButtonUp.setFont(new Font("Dialog", 0, 12));
        this.jCheckBoxMatchCase.setFont(new Font("Dialog", 0, 12));
        this.jCheckBoxMatchCase.setAlignmentX(0.5f);
        this.jLabelFind.setAlignmentX(0.5f);
        this.jLabelFind.setText("Find What:");
        this.jLabel1.setFont(new Font("Dialog", 0, 4));
        this.jLabel1.setText(" ");
        this.jButtonFind.setText("Find Next");
        this.jButtonCancel.setText("Cancel");
        this.jPanel2.setLayout(new GridLayout(1, 0));
        this.jPanel2.setBorder(new TitledBorder("Direction"));
        this.jRadioButtonUp.setText("Up");
        this.jLabel2.setText("                           ");
        this.setResizable(false);
        this.buttonGroupUpDown.add(this.jRadioButtonUp);
        this.jRadioButtonDown.setText("Down");
        this.buttonGroupUpDown.add(this.jRadioButtonDown);
        this.jPanel2.add(this.jRadioButtonUp, null);
        this.jPanel2.add(this.jRadioButtonDown);
        this.jPanel1.add(this.box1, "Center");
        this.box1.add(this.jPanel4, null);
        this.jPanel4.add(this.box2, null);
        this.box2.add(this.jPanel5, null);
        this.jPanel5.add(this.jLabelFind, null);
        this.jPanel5.add(this.jTextFieldFind, null);
        this.box2.add(this.jPanel6, null);
        this.jPanel6.add(this.jCheckBoxMatchCase, null);
        this.jPanel6.add(this.jLabel2, null);
        this.jPanel6.add(this.jPanel2, null);
        this.box1.add(this.jPanelButtons, null);
        this.jPanelButtons.add(this.boxButtons, null);
        this.boxButtons.add(this.jButtonFind, null);
        this.boxButtons.add(this.jLabel1, null);
        this.boxButtons.add(this.jButtonCancel, null);
        this.jCheckBoxMatchCase.setText("Match Case");
        this.getContentPane().add(this.jPanel1, "Center");
    }
    
    void jButtonFind_actionPerformed(final ActionEvent actionEvent) {
        int n = -1;
        final String text = this.jTextFieldFind.getText();
        if (text != null) {
            if (this.searchDown) {
                n = this.application.frameUtl.tableSearchDown(this.application.searchData, text, this.jCheckBoxMatchCase.isSelected());
            }
            else {
                n = this.application.frameUtl.tableSearchUp(this.application.searchData, text, this.jCheckBoxMatchCase.isSelected());
            }
        }
        this.dispose();
        if (n == -1) {
            JOptionPane.showMessageDialog(this, "Text Not Found.", "Error", 0);
        }
        else {
            this.application.highlightFindRow(n);
        }
    }
    
    void jButtonCancel_actionPerformed(final ActionEvent actionEvent) {
        this.dispose();
    }
    
    void jCheckBoxMatchCase_actionPerformed(final ActionEvent actionEvent) {
        this.matchCase = this.jCheckBoxMatchCase.isSelected();
    }
    
    void jRadioButtonUp_actionPerformed(final ActionEvent actionEvent) {
        this.searchDown = this.jRadioButtonDown.isSelected();
    }
    
    void jRadioButtonDown_actionPerformed(final ActionEvent actionEvent) {
        this.searchDown = this.jRadioButtonDown.isSelected();
    }
    
    void dialogKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonFind_actionPerformed(null);
        }
    }
    
    void jTextFieldFind_keyReleased(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonFind_actionPerformed(null);
        }
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
    }
}
