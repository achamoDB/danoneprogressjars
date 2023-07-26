// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Point;
import javax.swing.JOptionPane;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.border.Border;
import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JDialog;

public class PreferencesDialogBox extends JDialog
{
    JTabbedPane jTabbedPane1;
    JPanel jPanelButtons;
    JPanel jPanelFont;
    JPanel jPanelSettings;
    JPanel panel1;
    BorderLayout borderLayout1;
    private JComboBox jComboBoxFont;
    private JComboBox jComboBoxFontSize;
    private JLabel jLabelFont;
    private JLabel jLabelSize;
    private JCheckBox jCheckBoxLineNumbers;
    private JButton jButtonOk;
    private JButton jButtonCancel;
    private JLabel jLabelPreview;
    private String fontName;
    private int fontSize;
    private JLabel jLabelNote;
    private JLabel animationLabel;
    private JTextField delay;
    Frame1 app;
    JLabel jLabelPreviewText;
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    Box boxFont;
    Box boxSettings;
    JPanel jPanelFontType;
    JPanel jPanelSize;
    JPanel jPanelPreviewText;
    JPanel jPanelPreview;
    JPanel jPanel1;
    JPanel jPanel2;
    JPanel jPanel3;
    JPanel jPanelAttachable;
    JLabel jLabel1;
    JButton jButtonAddDirectory;
    JScrollPane jScrollPane1;
    JList jList1;
    Vector listData;
    JButton jButtonRemoveDir;
    Box boxAttachable;
    Box boxButtons;
    Box boxListings;
    JLabel jLabel2;
    JLabel jLabel3;
    JLabel jLabel4;
    JLabel jLabel5;
    JLabel jLabel6;
    JLabel jLabel7;
    JPanel jPanel4;
    
    public PreferencesDialogBox(final Frame1 frame1, final String title, final boolean b) {
        super(frame1, title, true);
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanelButtons = new JPanel();
        this.jPanelFont = new JPanel();
        this.jPanelSettings = new JPanel();
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jComboBoxFont = new JComboBox();
        this.jComboBoxFontSize = new JComboBox();
        this.jLabelFont = new JLabel();
        this.jLabelSize = new JLabel("Size:");
        this.jCheckBoxLineNumbers = new JCheckBox("Include Source Line Numbers");
        this.jButtonOk = new JButton("Ok");
        this.jButtonCancel = new JButton("Cancel");
        this.jLabelPreview = new JLabel("Preview:");
        this.jLabelNote = new JLabel("(Line number changes will not take effect immediately.)");
        this.animationLabel = new JLabel("Animation Delay(in seconds):");
        this.delay = new JTextField();
        this.jLabelPreviewText = new JLabel();
        this.jPanelFontType = new JPanel();
        this.jPanelSize = new JPanel();
        this.jPanelPreviewText = new JPanel();
        this.jPanelPreview = new JPanel();
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jPanelAttachable = new JPanel();
        this.jLabel1 = new JLabel();
        this.jButtonAddDirectory = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.jButtonRemoveDir = new JButton();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jPanel4 = new JPanel();
        try {
            this.app = frame1;
            this.listData = (Vector)this.app.debuggerListingDirs.clone();
            this.jList1 = new JList(this.listData);
            this.setFont(new Font("dialoginput", 0, 12));
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    public PreferencesDialogBox() {
        this((Frame1)null, "", false);
    }
    
    void jbInit() throws Exception {
        this.titledBorder1 = new TitledBorder("");
        this.titledBorder2 = new TitledBorder("");
        this.boxFont = Box.createVerticalBox();
        this.boxSettings = Box.createVerticalBox();
        this.boxAttachable = Box.createHorizontalBox();
        this.boxButtons = Box.createVerticalBox();
        this.boxListings = Box.createVerticalBox();
        this.jTabbedPane1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jTabbedPane1.setMinimumSize(new Dimension(200, 200));
        this.panel1.setLayout(this.borderLayout1);
        this.jComboBoxFont.setFont(new Font("Dialog", 0, 12));
        this.jComboBoxFont.setMaximumSize(new Dimension(150, 20));
        this.jComboBoxFont.setMinimumSize(new Dimension(150, 20));
        this.jComboBoxFont.setPreferredSize(new Dimension(150, 20));
        this.jLabelPreviewText.setMaximumSize(new Dimension(190, 20));
        this.jLabelPreviewText.setMinimumSize(new Dimension(190, 20));
        this.jLabelPreviewText.setText("Aa Bb Cc Dd");
        this.jComboBoxFontSize.setFont(new Font("Dialog", 0, 12));
        this.jComboBoxFontSize.setMaximumSize(new Dimension(150, 20));
        this.jComboBoxFontSize.setMinimumSize(new Dimension(150, 20));
        this.jComboBoxFontSize.setPreferredSize(new Dimension(150, 20));
        this.jLabelFont.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabelFont.setMinimumSize(new Dimension(40, 20));
        this.jLabelFont.setText("Font:");
        this.jLabelSize.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabelSize.setMinimumSize(new Dimension(40, 20));
        this.jLabelSize.setHorizontalAlignment(2);
        this.jLabelSize.setHorizontalTextPosition(2);
        this.jPanelFont.setBorder(this.titledBorder1);
        this.jPanelFont.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanelFont.setMinimumSize(new Dimension(400, 240));
        this.jPanelSettings.setBorder(this.titledBorder2);
        this.jPanelSettings.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanelSettings.setMinimumSize(new Dimension(350, 250));
        this.jLabelPreview.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabelPreview.setMinimumSize(new Dimension(190, 20));
        this.animationLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.animationLabel.setMinimumSize(new Dimension(165, 25));
        this.animationLabel.setLabelFor(this.delay);
        this.delay.setFont(new Font("Dialog", 0, 12));
        this.delay.setMaximumSize(new Dimension(40, 25));
        this.delay.setMinimumSize(new Dimension(40, 25));
        this.delay.setPreferredSize(new Dimension(40, 25));
        this.jButtonOk.setMaximumSize(new Dimension(73, 27));
        this.jButtonOk.setMinimumSize(new Dimension(73, 27));
        this.jButtonOk.setPreferredSize(new Dimension(73, 27));
        this.jButtonOk.setMnemonic('0');
        this.jButtonOk.setText("OK");
        this.panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.panel1.setMinimumSize(new Dimension(340, 240));
        this.jCheckBoxLineNumbers.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jCheckBoxLineNumbers.setMinimumSize(new Dimension(210, 25));
        this.jLabelNote.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabelNote.setMinimumSize(new Dimension(301, 25));
        this.jPanel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanel1.setMinimumSize(new Dimension(311, 35));
        this.jPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanel2.setMinimumSize(new Dimension(311, 35));
        this.jPanel3.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanelFontType.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanelSize.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanelPreview.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanelPreviewText.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabel1.setText("Debugger Listing Directories:");
        this.jButtonAddDirectory.setMaximumSize(new Dimension(80, 27));
        this.jButtonAddDirectory.setMinimumSize(new Dimension(80, 27));
        this.jButtonAddDirectory.setPreferredSize(new Dimension(80, 27));
        this.jButtonAddDirectory.setText("Add");
        this.jButtonAddDirectory.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferencesDialogBox.this.jButtonAddDirectory_actionPerformed(actionEvent);
            }
        });
        this.jList1.setAlignmentX(1.0f);
        this.jList1.setSelectionMode(0);
        this.jScrollPane1.setAlignmentX(0.0f);
        this.jScrollPane1.setMaximumSize(new Dimension(360, 132));
        this.jScrollPane1.setPreferredSize(new Dimension(320, 132));
        this.jButtonRemoveDir.setMaximumSize(new Dimension(80, 27));
        this.jButtonRemoveDir.setMinimumSize(new Dimension(80, 27));
        this.jButtonRemoveDir.setPreferredSize(new Dimension(80, 27));
        this.jButtonRemoveDir.setText("Remove");
        this.jButtonRemoveDir.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferencesDialogBox.this.jButtonRemoveDir_actionPerformed(actionEvent);
            }
        });
        this.jPanelAttachable.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jLabel2.setText(" ");
        this.jLabel3.setMaximumSize(new Dimension(1, 7));
        this.jLabel3.setMinimumSize(new Dimension(1, 7));
        this.jLabel3.setPreferredSize(new Dimension(1, 7));
        this.jLabel3.setText(" ");
        this.jLabel4.setText(" ");
        this.jLabel5.setText(" ");
        this.jLabel6.setText(" ");
        this.jLabel7.setText(" ");
        this.jPanelButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jButtonCancel.setMaximumSize(new Dimension(73, 27));
        this.jButtonCancel.setMinimumSize(new Dimension(73, 27));
        this.jButtonCancel.setPreferredSize(new Dimension(73, 27));
        this.jPanel1.add(this.animationLabel, null);
        this.jPanel1.add(this.delay, null);
        this.jPanelSettings.add(this.boxSettings, null);
        this.boxSettings.add(this.jPanel2, null);
        this.jPanel2.add(this.jCheckBoxLineNumbers, null);
        this.boxSettings.add(this.jPanel3, null);
        this.jPanel3.add(this.jLabelNote, null);
        this.boxSettings.add(this.jPanel1, null);
        this.jTabbedPane1.add(this.jPanelFont, "Font");
        this.jPanelFont.add(this.boxFont, null);
        this.jTabbedPane1.add(this.jPanelSettings, "Settings");
        this.jTabbedPane1.add(this.jPanelAttachable, "Attachable");
        this.jPanelAttachable.add(this.boxAttachable, null);
        this.boxAttachable.add(this.boxListings, null);
        this.boxAttachable.add(this.jPanel4, null);
        this.boxListings.add(this.jLabel1, null);
        this.boxListings.add(this.jScrollPane1, null);
        this.jScrollPane1.getViewport().add(this.jList1, null);
        this.boxAttachable.add(this.boxButtons, null);
        this.boxButtons.add(this.jButtonAddDirectory, null);
        this.boxButtons.add(this.jLabel3, null);
        this.boxButtons.add(this.jButtonRemoveDir, null);
        this.getContentPane().add(this.panel1, "Center");
        this.panel1.add(this.jPanelButtons, "South");
        this.jPanelButtons.add(this.jButtonOk, null);
        this.jPanelButtons.add(this.jButtonCancel, null);
        this.panel1.add(this.jTabbedPane1, "Center");
        this.boxFont.add(this.jPanelFontType, null);
        this.boxFont.add(this.jPanelSize, null);
        this.jPanelFontType.add(this.jLabelFont, null);
        this.jPanelFontType.add(this.jComboBoxFont, null);
        this.boxFont.add(this.jPanelPreview, null);
        this.jPanelPreview.add(this.jLabelPreview, null);
        this.boxFont.add(this.jPanelPreviewText, null);
        this.jPanelPreviewText.add(this.jLabelPreviewText, null);
        this.jPanelSize.add(this.jLabelSize, null);
        this.jPanelSize.add(this.jComboBoxFontSize, null);
        this.boxButtons.add(this.jLabel2, null);
        this.boxButtons.add(this.jLabel4, null);
        this.boxButtons.add(this.jLabel5, null);
        this.boxButtons.add(this.jLabel6, null);
        this.boxButtons.add(this.jLabel7, null);
        this.delay.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PreferencesDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.panel1.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PreferencesDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.jTabbedPane1.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PreferencesDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.jComboBoxFontSize.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PreferencesDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.jComboBoxFont.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PreferencesDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PreferencesDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        if (this.app.showLineNumbers) {
            this.jCheckBoxLineNumbers.setSelected(true);
        }
        else {
            this.jCheckBoxLineNumbers.setSelected(false);
        }
        this.jButtonOk.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferencesDialogBox.this.jButtonOkActionPerformed(actionEvent);
            }
        });
        this.jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferencesDialogBox.this.jButtonCancelActionPerformed(actionEvent);
            }
        });
        this.initilizeFonts();
        this.initilizeFontSizes();
        this.fontName = this.app.debugListingFile.getFont().getName();
        this.fontSize = this.app.debugListingFile.getFont().getSize();
        this.jComboBoxFont.setSelectedItem(this.fontName);
        this.jComboBoxFontSize.setSelectedItem(new Integer(this.fontSize).toString());
        this.delay.setText(Integer.toString(this.app.animationDelayTime));
        this.jButtonOk.setSelected(true);
    }
    
    private void initilizeFonts() {
        this.jComboBoxFont.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferencesDialogBox.this.jComboBoxFont_actionPerformed(actionEvent);
            }
        });
        this.jComboBoxFont.addItem("dialog");
        this.jComboBoxFont.addItem("dialoginput");
        this.jComboBoxFont.addItem("monospaced");
        this.jComboBoxFont.addItem("sansserif");
        this.jComboBoxFont.addItem("serif");
    }
    
    private void initilizeFontSizes() {
        this.jComboBoxFontSize.addItem("6");
        this.jComboBoxFontSize.addItem("8");
        this.jComboBoxFontSize.addItem("10");
        this.jComboBoxFontSize.addItem("12");
        this.jComboBoxFontSize.addItem("14");
        this.jComboBoxFontSize.addItem("16");
        this.jComboBoxFontSize.addItem("18");
        this.jComboBoxFontSize.addItem("20");
        this.jComboBoxFontSize.addItem("22");
        this.jComboBoxFontSize.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PreferencesDialogBox.this.jComboBoxFontSize_actionPerformed(actionEvent);
            }
        });
    }
    
    private void jButtonOkActionPerformed(final ActionEvent actionEvent) {
        final String string = this.jComboBoxFont.getSelectedItem().toString();
        final String string2 = this.jComboBoxFontSize.getSelectedItem().toString();
        int int1;
        try {
            int1 = Integer.parseInt(string2);
        }
        catch (NumberFormatException ex) {
            int1 = 14;
        }
        this.app.setAllFonts(string, int1);
        if (this.jCheckBoxLineNumbers.isSelected()) {
            this.app.showLineNumbers = true;
        }
        else {
            this.app.showLineNumbers = false;
        }
        try {
            this.app.changeAnimationDelay(Integer.parseInt(this.delay.getText()));
            this.dispose();
        }
        catch (Exception ex2) {
            JOptionPane.showMessageDialog(this, "You must enter an integer for the delay time.", "Error", 0);
        }
    }
    
    private void jButtonCancelActionPerformed(final ActionEvent actionEvent) {
        this.dispose();
    }
    
    void jComboBoxFontSize_actionPerformed(final ActionEvent actionEvent) {
        int int1;
        try {
            int1 = Integer.parseInt(this.jComboBoxFontSize.getSelectedItem().toString().trim());
        }
        catch (NumberFormatException ex) {
            int1 = 14;
        }
        this.fontSize = int1;
        this.jLabelPreviewText.setFont(new Font(this.fontName, 0, this.fontSize));
    }
    
    void jComboBoxFont_actionPerformed(final ActionEvent actionEvent) {
        this.fontName = this.jComboBoxFont.getSelectedItem().toString();
        this.jLabelPreviewText.setFont(new Font(this.fontName, 0, this.fontSize));
    }
    
    void dialogKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonOkActionPerformed(null);
        }
    }
    
    void jButtonAddDirectory_actionPerformed(final ActionEvent actionEvent) {
        final DirectoryChooserDialog directoryChooserDialog = new DirectoryChooserDialog(this.app, this);
        final Dimension preferredSize = directoryChooserDialog.getPreferredSize();
        final Dimension size = this.app.getSize();
        final Point location = this.app.getLocation();
        directoryChooserDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        directoryChooserDialog.setVisible(true);
    }
    
    void addToDirList(final String e) {
        this.jScrollPane1.remove(this.jList1);
        this.listData.add(e);
        (this.jList1 = new JList(this.listData)).setSelectionMode(0);
        this.jScrollPane1.getViewport().add(this.jList1, null);
        this.app.debuggerListingDirs = (Vector)this.listData.clone();
    }
    
    void jButtonRemoveDir_actionPerformed(final ActionEvent actionEvent) {
        final int selectedIndex = this.jList1.getSelectedIndex();
        if (selectedIndex >= 0) {
            this.jScrollPane1.remove(this.jList1);
            this.listData.removeElementAt(selectedIndex);
            (this.jList1 = new JList(this.listData)).setSelectionMode(0);
            this.jScrollPane1.getViewport().add(this.jList1, null);
            this.app.debuggerListingDirs = (Vector)this.listData.clone();
        }
    }
}
