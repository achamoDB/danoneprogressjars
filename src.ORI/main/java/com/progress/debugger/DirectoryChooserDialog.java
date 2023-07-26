// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.Frame;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class DirectoryChooserDialog extends JDialog
{
    JPanel panel1;
    BorderLayout borderLayout1;
    JFileChooser jFileChooser1;
    PreferencesDialogBox prefDlg;
    Frame1 application;
    
    public DirectoryChooserDialog(final Frame1 frame1, final PreferencesDialogBox prefDlg) {
        super(frame1, "Choose Directory", true);
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jFileChooser1 = new JFileChooser();
        try {
            this.application = frame1;
            this.prefDlg = prefDlg;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    void jbInit() throws Exception {
        if (this.application.currentDirectory != null) {
            this.jFileChooser1 = new JFileChooser(this.application.currentDirectory);
        }
        else {
            this.jFileChooser1 = new JFileChooser(System.getProperty("user.dir"));
        }
        this.panel1.setLayout(this.borderLayout1);
        this.jFileChooser1.setFileSelectionMode(1);
        this.jFileChooser1.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DirectoryChooserDialog.this.jFileChooser1_actionPerformed(actionEvent);
            }
        });
        this.getContentPane().add(this.panel1);
        this.panel1.add(this.jFileChooser1, "East");
    }
    
    void jFileChooser1_actionPerformed(final ActionEvent actionEvent) {
        final String actionCommand = actionEvent.getActionCommand();
        final JFileChooser jFileChooser1 = this.jFileChooser1;
        if (actionCommand.equals("ApproveSelection")) {
            this.prefDlg.addToDirList(this.jFileChooser1.getSelectedFile().toString());
            this.application.currentDirectory = this.jFileChooser1.getCurrentDirectory();
            this.dispose();
        }
        else {
            final String s = actionCommand;
            final JFileChooser jFileChooser2 = this.jFileChooser1;
            if (s.equals("CancelSelection")) {
                this.application.currentDirectory = this.jFileChooser1.getCurrentDirectory();
                this.dispose();
            }
        }
    }
}
