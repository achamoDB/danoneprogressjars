// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Component;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.filechooser.FileFilter;
import java.awt.Frame;
import javax.swing.border.TitledBorder;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class FileOpenDialog extends JDialog
{
    private JPanel panel1;
    private BorderLayout borderLayout1;
    private JFileChooser jFileChooser1;
    private TitledBorder titledBorder1;
    Frame1 application;
    
    public FileOpenDialog(final Frame1 frame1, final String s, final boolean modal) {
        super(frame1, "Open", modal);
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jFileChooser1 = new JFileChooser();
        try {
            this.application = frame1;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    public FileOpenDialog() {
        this((Frame1)null, "", false);
    }
    
    void jbInit() throws Exception {
        if (this.application.currentDirectory != null) {
            this.jFileChooser1 = new JFileChooser(this.application.currentDirectory);
        }
        else {
            this.jFileChooser1 = new JFileChooser(System.getProperty("user.dir"));
        }
        final ExtensionFileFilter extensionFileFilter = new ExtensionFileFilter("Source Files(*.cls,*.i,*.p,*.w)", new String[] { "cls", "i", "p", "w" });
        this.jFileChooser1.addChoosableFileFilter(extensionFileFilter);
        this.jFileChooser1.setRequestFocusEnabled(false);
        this.jFileChooser1.setFileFilter(extensionFileFilter);
        this.jFileChooser1.addKeyListener(new KeyAdapter() {
            public void keyTyped(final KeyEvent keyEvent) {
                FileOpenDialog.this.dialogKeyPressed(keyEvent);
            }
        });
        this.titledBorder1 = new TitledBorder("");
        this.panel1.setLayout(this.borderLayout1);
        this.jFileChooser1.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                FileOpenDialog.this.jFileChooser1_actionPerformed(actionEvent);
            }
        });
        this.panel1.setBorder(this.titledBorder1);
        this.getContentPane().add(this.panel1);
        this.panel1.add(this.jFileChooser1, "Center");
    }
    
    void jFileChooser1_actionPerformed(final ActionEvent actionEvent) {
        final String actionCommand = actionEvent.getActionCommand();
        final JFileChooser jFileChooser1 = this.jFileChooser1;
        if (actionCommand.equals("ApproveSelection")) {
            this.application.currentDirectory = this.jFileChooser1.getCurrentDirectory();
            this.application.sendSocket.sendMessage("file \"".concat(this.jFileChooser1.getSelectedFile().toString()).concat("\""));
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
    
    void dialogKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
    }
}
