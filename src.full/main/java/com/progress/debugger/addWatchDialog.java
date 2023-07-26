// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Frame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JDialog;

public class addWatchDialog extends JDialog
{
    Frame1 app;
    BorderLayout borderLayout1;
    private JButton jButtonOk;
    private JButton jButtonCancel;
    private JLabel jLabelWatch;
    private JTextField jTextFieldWatch;
    Box box1;
    JPanel jPanelButtons;
    JPanel jPanelWatch;
    JPanel jPanelRoot;
    
    public addWatchDialog(final Frame1 frame1, final String title, final boolean modal) {
        super(frame1, title, modal);
        this.borderLayout1 = new BorderLayout();
        this.jButtonOk = new JButton();
        this.jButtonCancel = new JButton();
        this.jLabelWatch = new JLabel();
        this.jTextFieldWatch = new JTextField();
        this.jPanelButtons = new JPanel();
        this.jPanelWatch = new JPanel();
        this.jPanelRoot = new JPanel();
        try {
            this.app = frame1;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    public addWatchDialog() {
        this((Frame1)null, "", false);
    }
    
    void jbInit() throws Exception {
        this.box1 = Box.createVerticalBox();
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                addWatchDialog.this.dialogKeyPressed(keyEvent);
            }
        });
        this.setResizable(false);
        this.jButtonOk.setFont(new Font("Dialog", 0, 12));
        this.jButtonOk.setAlignmentX(0.5f);
        this.jButtonOk.setMaximumSize(new Dimension(73, 27));
        this.jButtonOk.setMinimumSize(new Dimension(73, 27));
        this.jButtonOk.setPreferredSize(new Dimension(73, 27));
        this.jButtonOk.setText("OK");
        this.jButtonOk.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                addWatchDialog.this.okButtonPushed(actionEvent);
            }
        });
        this.jLabelWatch.setFont(new Font("Dialog", 0, 12));
        this.jButtonCancel.setFont(new Font("Dialog", 0, 12));
        this.jButtonCancel.setAlignmentX(0.5f);
        this.jButtonCancel.setMaximumSize(new Dimension(73, 27));
        this.jButtonCancel.setMinimumSize(new Dimension(73, 27));
        this.jButtonCancel.setPreferredSize(new Dimension(73, 27));
        this.jButtonCancel.setText("Cancel");
        this.jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                addWatchDialog.this.cancelButtonPushed(actionEvent);
            }
        });
        this.jLabelWatch.setText("Watch:");
        this.jTextFieldWatch.setPreferredSize(new Dimension(250, 21));
        this.jTextFieldWatch.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                addWatchDialog.this.jTextFieldWatch_keyReleased(keyEvent);
            }
        });
        this.jPanelRoot.add(this.box1, null);
        this.box1.add(this.jPanelWatch, null);
        this.box1.add(this.jPanelButtons, null);
        this.jPanelWatch.add(this.jLabelWatch, null);
        this.jPanelWatch.add(this.jTextFieldWatch, null);
        this.jPanelButtons.add(this.jButtonOk, null);
        this.jPanelButtons.add(this.jButtonCancel, null);
        this.getContentPane().add(this.jPanelRoot, "Center");
    }
    
    private void cancelButtonPushed(final ActionEvent actionEvent) {
        this.dispose();
    }
    
    private void okButtonPushed(final ActionEvent actionEvent) {
        final String text = this.jTextFieldWatch.getText();
        final int length = text.length();
        final Sockets sendSocket = this.app.sendSocket;
        if (length > 0) {
            sendSocket.sendMessage("watch ".concat(text));
            sendSocket.sendMessage("show watch");
        }
        this.dispose();
    }
    
    void dialogKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
        if (keyEvent.getKeyCode() == 10) {
            this.okButtonPushed(null);
        }
    }
    
    void jTextFieldWatch_keyReleased(final KeyEvent keyEvent) {
        this.dialogKeyPressed(keyEvent);
    }
}
