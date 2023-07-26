// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Cursor;
import java.awt.event.WindowEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.LayoutManager;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import java.util.Vector;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JDialog;

public class PropertySheetDialogBox extends JDialog
{
    JLabel jLabelView;
    JComboBox jComboBox1;
    JLabel jLabelCurrentValue;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPaneFields;
    JTable jTableAttributeData;
    JTable jTableFieldData;
    JButton jButtonClose;
    JButton jButtonView;
    JButton jButtonAddWatchpoint;
    static String originalName;
    Frame1 app;
    ListSelectionModel attribueTableList;
    ListSelectionModel fieldTableList;
    ListSelectionModel arrayTableList;
    Vector typeData;
    Vector flagsData;
    Vector fieldFlagsData;
    Vector arrayFlagsData;
    JTabbedPane jTabbedPaneDataview;
    int lastSelectedAttributeRow;
    int lastSelectedFieldRow;
    String lastAttributeValueBeforeEdit;
    String lastFieldValueBeforeEdit;
    boolean triggerAttributeUpdate;
    boolean triggerFieldUpdate;
    int tabbedPaneIndex;
    JScrollPane jScrollPaneArray;
    JScrollPane jScrollPaneValue;
    JTable jTableArrayData;
    JTextPane value;
    JTextPane error_msg;
    BorderLayout borderLayout1;
    String lastArrayValueBeforeEdit;
    int lastSelectedArrayRow;
    boolean triggerArrayUpdate;
    Box boxButtons;
    Box boxDataView;
    JLabel jLabel4;
    JPanel jPanel2;
    BorderLayout borderLayout2;
    JLabel jLabel3;
    TitledBorder titledBorder1;
    String lastArrayViewName;
    JPanel jPanel1;
    Box box1;
    JPanel jPanel3;
    JLabel jLabel1;
    JPanel jPanel4;
    
    public PropertySheetDialogBox(final Frame1 frame1, final String s, final boolean b, final Vector flagsData, final int tabbedPaneIndex) {
        super(frame1, "Dataview", false);
        this.jLabelView = new JLabel();
        this.jComboBox1 = new JComboBox();
        this.jLabelCurrentValue = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jScrollPaneFields = new JScrollPane();
        this.jTableAttributeData = new JTable();
        this.jTableFieldData = new JTable();
        this.jButtonClose = new JButton();
        this.jButtonView = new JButton();
        this.jButtonAddWatchpoint = new JButton();
        this.typeData = new Vector();
        this.flagsData = new Vector();
        this.fieldFlagsData = new Vector();
        this.arrayFlagsData = new Vector();
        this.jTabbedPaneDataview = new JTabbedPane();
        this.lastSelectedAttributeRow = -1;
        this.lastSelectedFieldRow = -1;
        this.lastAttributeValueBeforeEdit = " ";
        this.lastFieldValueBeforeEdit = " ";
        this.triggerAttributeUpdate = false;
        this.triggerFieldUpdate = false;
        this.tabbedPaneIndex = 0;
        this.jScrollPaneArray = new JScrollPane();
        this.jScrollPaneValue = new JScrollPane();
        this.jTableArrayData = new JTable();
        this.value = new JTextPane();
        this.error_msg = new JTextPane();
        this.borderLayout1 = new BorderLayout();
        this.lastArrayValueBeforeEdit = " ";
        this.lastSelectedArrayRow = -1;
        this.triggerArrayUpdate = false;
        this.jLabel4 = new JLabel();
        this.jPanel2 = new JPanel();
        this.borderLayout2 = new BorderLayout();
        this.jLabel3 = new JLabel();
        this.lastArrayViewName = "";
        this.jPanel1 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jPanel4 = new JPanel();
        try {
            this.app = frame1;
            this.setDataWithNoValues();
            this.tabbedPaneIndex = tabbedPaneIndex;
            PropertySheetDialogBox.originalName = this.app.propertySheetName;
            this.flagsData = flagsData;
            this.setDataWithValues();
            this.jbInit();
            this.app.propertySheetOpen = true;
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    public PropertySheetDialogBox(final Frame1 frame1) {
        super(frame1, "Dataview", false);
        this.jLabelView = new JLabel();
        this.jComboBox1 = new JComboBox();
        this.jLabelCurrentValue = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jScrollPaneFields = new JScrollPane();
        this.jTableAttributeData = new JTable();
        this.jTableFieldData = new JTable();
        this.jButtonClose = new JButton();
        this.jButtonView = new JButton();
        this.jButtonAddWatchpoint = new JButton();
        this.typeData = new Vector();
        this.flagsData = new Vector();
        this.fieldFlagsData = new Vector();
        this.arrayFlagsData = new Vector();
        this.jTabbedPaneDataview = new JTabbedPane();
        this.lastSelectedAttributeRow = -1;
        this.lastSelectedFieldRow = -1;
        this.lastAttributeValueBeforeEdit = " ";
        this.lastFieldValueBeforeEdit = " ";
        this.triggerAttributeUpdate = false;
        this.triggerFieldUpdate = false;
        this.tabbedPaneIndex = 0;
        this.jScrollPaneArray = new JScrollPane();
        this.jScrollPaneValue = new JScrollPane();
        this.jTableArrayData = new JTable();
        this.value = new JTextPane();
        this.error_msg = new JTextPane();
        this.borderLayout1 = new BorderLayout();
        this.lastArrayValueBeforeEdit = " ";
        this.lastSelectedArrayRow = -1;
        this.triggerArrayUpdate = false;
        this.jLabel4 = new JLabel();
        this.jPanel2 = new JPanel();
        this.borderLayout2 = new BorderLayout();
        this.jLabel3 = new JLabel();
        this.lastArrayViewName = "";
        this.jPanel1 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jPanel4 = new JPanel();
        try {
            this.app = frame1;
            this.setDataWithNoValues();
            this.jbInit();
            this.app.propertySheetOpen = true;
            this.pack();
            this.disableButtons();
        }
        catch (Exception ex) {}
    }
    
    public PropertySheetDialogBox(final Frame1 frame1, final String s, final int tabbedPaneIndex) {
        super(frame1, "Dataview", false);
        this.jLabelView = new JLabel();
        this.jComboBox1 = new JComboBox();
        this.jLabelCurrentValue = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jScrollPaneFields = new JScrollPane();
        this.jTableAttributeData = new JTable();
        this.jTableFieldData = new JTable();
        this.jButtonClose = new JButton();
        this.jButtonView = new JButton();
        this.jButtonAddWatchpoint = new JButton();
        this.typeData = new Vector();
        this.flagsData = new Vector();
        this.fieldFlagsData = new Vector();
        this.arrayFlagsData = new Vector();
        this.jTabbedPaneDataview = new JTabbedPane();
        this.lastSelectedAttributeRow = -1;
        this.lastSelectedFieldRow = -1;
        this.lastAttributeValueBeforeEdit = " ";
        this.lastFieldValueBeforeEdit = " ";
        this.triggerAttributeUpdate = false;
        this.triggerFieldUpdate = false;
        this.tabbedPaneIndex = 0;
        this.jScrollPaneArray = new JScrollPane();
        this.jScrollPaneValue = new JScrollPane();
        this.jTableArrayData = new JTable();
        this.value = new JTextPane();
        this.error_msg = new JTextPane();
        this.borderLayout1 = new BorderLayout();
        this.lastArrayValueBeforeEdit = " ";
        this.lastSelectedArrayRow = -1;
        this.triggerArrayUpdate = false;
        this.jLabel4 = new JLabel();
        this.jPanel2 = new JPanel();
        this.borderLayout2 = new BorderLayout();
        this.jLabel3 = new JLabel();
        this.lastArrayViewName = "";
        this.jPanel1 = new JPanel();
        this.jPanel3 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jPanel4 = new JPanel();
        try {
            this.app = frame1;
            this.setDataWithNoValues();
            this.tabbedPaneIndex = tabbedPaneIndex;
            PropertySheetDialogBox.originalName = this.app.propertySheetName;
            this.setComboBox();
            this.jbInit();
            this.app.propertySheetOpen = true;
            this.setErrorMessage(s, tabbedPaneIndex);
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    void jbInit() throws Exception {
        this.boxButtons = Box.createVerticalBox();
        this.boxDataView = Box.createVerticalBox();
        this.titledBorder1 = new TitledBorder("");
        this.box1 = Box.createHorizontalBox();
        this.setFont(new Font("dialog", 0, 12));
        this.setResizable(true);
        this.jButtonClose.setFont(new Font("Dialog", 0, 12));
        this.jButtonClose.setMaximumSize(new Dimension(93, 27));
        this.jButtonClose.setPreferredSize(new Dimension(93, 27));
        this.jButtonAddWatchpoint.setFont(new Font("Dialog", 0, 12));
        this.jButtonAddWatchpoint.setMaximumSize(new Dimension(93, 27));
        this.jButtonAddWatchpoint.setMinimumSize(new Dimension(93, 27));
        this.jButtonAddWatchpoint.setPreferredSize(new Dimension(93, 27));
        this.jButtonAddWatchpoint.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PropertySheetDialogBox.this.jButtonAddWatchpoint_actionPerformed(actionEvent);
            }
        });
        this.jButtonView.setFont(new Font("Dialog", 0, 12));
        this.jButtonView.setMaximumSize(new Dimension(93, 27));
        this.jButtonView.setPreferredSize(new Dimension(93, 27));
        this.jButtonView.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PropertySheetDialogBox.this.jButtonView_actionPerformed(actionEvent);
            }
        });
        this.jTabbedPaneDataview.setTabPlacement(1);
        this.jTabbedPaneDataview.setFont(new Font("Dialog", 0, 12));
        this.jTabbedPaneDataview.setMaximumSize(new Dimension(550, 400));
        this.jTabbedPaneDataview.setPreferredSize(new Dimension(550, 400));
        this.jComboBox1.setMaximumSize(new Dimension(550, 21));
        this.jComboBox1.setPreferredSize(new Dimension(550, 20));
        this.jComboBox1.setRequestFocusEnabled(true);
        this.jComboBox1.setEditable(true);
        this.jComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PropertySheetDialogBox.this.jComboBox1_actionPerformed(actionEvent);
            }
        });
        this.jLabelView.setFont(new Font("Dialog", 0, 12));
        this.jLabelView.setAlignmentX(0.5f);
        this.jLabelView.setMaximumSize(new Dimension(550, 20));
        this.jLabelView.setPreferredSize(new Dimension(550, 20));
        this.jLabelView.setText("View:");
        this.value.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.value_keyPressed(keyEvent);
            }
        });
        this.jLabelCurrentValue.setFont(new Font("Dialog", 0, 12));
        this.jLabelCurrentValue.setAlignmentX(0.5f);
        this.jLabelCurrentValue.setMaximumSize(new Dimension(550, 20));
        this.jLabelCurrentValue.setMinimumSize(new Dimension(380, 17));
        this.jLabelCurrentValue.setPreferredSize(new Dimension(550, 20));
        this.jLabelCurrentValue.setText("Current Value:");
        this.jScrollPane1.setViewportView(this.jTableAttributeData);
        this.jScrollPaneFields.setViewportView(this.jTableFieldData);
        this.jScrollPaneArray.setViewportView(this.jTableArrayData);
        this.jScrollPaneValue.setViewportView(this.value);
        this.jButtonView.setText("View");
        this.jButtonAddWatchpoint.setText("Add Watch");
        this.jButtonClose.setText("Close");
        this.jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                PropertySheetDialogBox.this.jButton1ActionPerformed(actionEvent);
            }
        });
        this.value.setSize(380, 320);
        this.error_msg.setSize(380, 320);
        this.jLabel4.setFont(new Font("Dialog", 0, 4));
        this.jLabel4.setText(" ");
        this.jPanel2.setBorder(this.titledBorder1);
        this.jPanel2.setDoubleBuffered(false);
        this.jPanel2.setMaximumSize(new Dimension(800, 600));
        this.jPanel2.setMinimumSize(new Dimension(400, 183));
        this.jPanel2.setPreferredSize(new Dimension(540, 472));
        this.jPanel2.setLayout(this.borderLayout2);
        this.jLabel3.setFont(new Font("Dialog", 0, 4));
        this.jLabel3.setText(" ");
        this.jScrollPane1.setMaximumSize(new Dimension(800, 600));
        this.jLabel1.setMaximumSize(new Dimension(1, 7));
        this.jLabel1.setMinimumSize(new Dimension(1, 7));
        this.jLabel1.setPreferredSize(new Dimension(1, 7));
        this.jLabel1.setText(" ");
        this.jTabbedPaneDataview.add(this.jScrollPane1, "Attributes");
        this.jTabbedPaneDataview.add(this.jScrollPaneFields, "Fields");
        this.jTabbedPaneDataview.add(this.jScrollPaneArray, "Array");
        this.jTabbedPaneDataview.add(this.jScrollPaneValue, "Value");
        this.box1.add(this.jPanel4, null);
        this.boxDataView.add(this.jLabelView, null);
        this.boxDataView.add(this.jComboBox1, null);
        this.boxDataView.add(this.jLabelCurrentValue, null);
        this.boxDataView.add(this.jTabbedPaneDataview, null);
        this.boxButtons.add(this.jLabel1, null);
        this.boxButtons.add(this.jButtonView, null);
        this.boxButtons.add(this.jLabel3, null);
        this.boxButtons.add(this.jButtonAddWatchpoint, null);
        this.boxButtons.add(this.jLabel4, null);
        this.boxButtons.add(this.jButtonClose, null);
        this.jPanel2.add(this.box1, "North");
        this.box1.add(this.boxDataView, null);
        this.box1.add(this.jPanel3, null);
        this.jPanel3.add(this.boxButtons, null);
        this.jPanel2.add(this.jPanel1, "South");
        this.jTabbedPaneDataview.setSelectedIndex(this.tabbedPaneIndex);
        this.jTabbedPaneDataview.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent changeEvent) {
                PropertySheetDialogBox.this.jTabbedPaneDataview_stateChanged(changeEvent);
            }
        });
        this.addKeyListners();
        this.error_msg.setEditable(false);
        this.app.setEnabled(false);
        this.getContentPane().add(this.jPanel2, "Center");
    }
    
    private void setDataWithValues() {
        if (this.tabbedPaneIndex == 1) {
            (this.jTableFieldData = new JTable(new PropertySheetTableModel(this.app, this.app.propertySheetData, this.typeData, this.flagsData))).setFont(this.app.currentFont);
            this.jTableFieldData.setAutoResizeMode(0);
            this.jTableFieldData.getTableHeader().setReorderingAllowed(false);
            this.jTableFieldData.getColumnModel().getColumn(0).setPreferredWidth(120);
            this.jTableFieldData.getColumnModel().getColumn(1).setPreferredWidth(120);
            this.jTableFieldData.getColumnModel().getColumn(2).setPreferredWidth(120);
            this.jTableFieldData.setSelectionMode(0);
            (this.fieldTableList = this.jTableFieldData.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                    PropertySheetDialogBox.this.fieldTableList_valueChanged(listSelectionEvent);
                }
            });
        }
        else if (this.tabbedPaneIndex == 2) {
            (this.jTableArrayData = new JTable(new ArrayTableModel(this.app, this.app.propertySheetData, this.flagsData))).setFont(this.app.currentFont);
            this.jTableArrayData.setAutoResizeMode(0);
            this.jTableArrayData.getTableHeader().setReorderingAllowed(false);
            this.jTableArrayData.getColumnModel().getColumn(0).setPreferredWidth(180);
            this.jTableArrayData.getColumnModel().getColumn(1).setPreferredWidth(180);
            this.jTableArrayData.setSelectionMode(0);
            this.arrayTableList = this.jTableArrayData.getSelectionModel();
            this.jTableArrayData.getTableHeader().setReorderingAllowed(false);
            this.arrayTableList.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                    PropertySheetDialogBox.this.arrayTableList_valueChanged(listSelectionEvent);
                }
            });
        }
        else if (this.tabbedPaneIndex != 3) {
            (this.jTableAttributeData = new JTable(new PropertySheetTableModel(this.app, this.app.propertySheetData, this.typeData, this.flagsData))).setFont(this.app.currentFont);
            this.jTableAttributeData.setAutoResizeMode(0);
            this.jTableAttributeData.getTableHeader().setReorderingAllowed(false);
            this.jTableAttributeData.getColumnModel().getColumn(0).setPreferredWidth(120);
            this.jTableAttributeData.getColumnModel().getColumn(1).setPreferredWidth(120);
            this.jTableAttributeData.getColumnModel().getColumn(2).setPreferredWidth(120);
            this.jTableAttributeData.setSelectionMode(0);
            (this.attribueTableList = this.jTableAttributeData.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                    PropertySheetDialogBox.this.attributeTableList_valueChanged(listSelectionEvent);
                }
            });
            this.jTableAttributeData.setSelectionModel(this.attribueTableList);
        }
        this.setComboBox();
    }
    
    private void setComboBox() {
        if (this.app.propertySheetComboBox != null) {
            this.jComboBox1 = this.app.propertySheetComboBox;
        }
        if (!this.comboBoxItemCheck(this.app.propertySheetName)) {
            this.jComboBox1.insertItemAt(this.app.propertySheetName, 0);
            this.jComboBox1.setSelectedIndex(0);
        }
        else {
            this.jComboBox1.setSelectedItem(this.app.propertySheetName);
            this.jComboBox1.getEditor().setItem(this.app.propertySheetName);
        }
    }
    
    private void setDataWithNoValues() {
        final Vector<String> vector = new Vector<String>();
        final Vector<Object> columnNames = new Vector<Object>();
        final Vector<Vector> rowData = new Vector<Vector>();
        vector.addElement("Name");
        vector.addElement("Value");
        vector.addElement("Type");
        columnNames.addElement("Index");
        columnNames.addElement("Value");
        this.jTableAttributeData = new JTable(rowData, vector);
        this.jTableFieldData = new JTable(rowData, vector);
        this.jTableArrayData = new JTable(rowData, columnNames);
        if (this.app.propertySheetComboBox != null) {
            (this.jComboBox1 = this.app.propertySheetComboBox).setSelectedIndex(-1);
            this.jComboBox1.getEditor().setItem("");
        }
        PropertySheetDialogBox.originalName = " ";
        this.addKeyListners();
    }
    
    void jButton1ActionPerformed(final ActionEvent actionEvent) {
        this.app.propertySheetOpen = false;
        this.app.savePropertySheetComboBox(this.jComboBox1);
        this.app.getAttrOrGetFieldsRequest = false;
        this.app.setEnabled(true);
        this.dispose();
    }
    
    void jButtonView_actionPerformed(final ActionEvent actionEvent) {
        final String string = this.jComboBox1.getEditor().getItem().toString();
        if (string.compareTo(PropertySheetDialogBox.originalName) != 0 && string.length() > 0) {
            this.requestWait();
            this.app.propertySheetData.clear();
            if (!this.comboBoxItemCheck(string)) {
                this.jComboBox1.insertItemAt(string, 0);
                this.jComboBox1.setSelectedIndex(0);
            }
            PropertySheetDialogBox.originalName = string;
            if (this.tabbedPaneIndex == 0) {
                this.app.requestGetAttrs(PropertySheetDialogBox.originalName);
            }
            else if (this.tabbedPaneIndex == 2) {
                this.lastArrayViewName = PropertySheetDialogBox.originalName;
                this.app.requestGetArray(PropertySheetDialogBox.originalName);
            }
            else if (this.tabbedPaneIndex == 3) {
                this.app.requestGetValue(PropertySheetDialogBox.originalName);
                this.jScrollPaneValue.remove(this.value);
                this.jScrollPaneValue.remove(this.error_msg);
                this.jScrollPaneValue.revalidate();
                this.jScrollPaneValue.repaint();
            }
            else {
                this.app.requestGetFields(PropertySheetDialogBox.originalName);
            }
            this.app.getAttrOrGetFieldsRequest = true;
        }
        this.jTableAttributeData.revalidate();
        this.jTableAttributeData.repaint();
        this.jTableFieldData.revalidate();
        this.jTableFieldData.repaint();
        this.error_msg.revalidate();
        this.error_msg.repaint();
        this.value.revalidate();
        this.value.repaint();
    }
    
    void jButtonAddWatchpoint_actionPerformed(final ActionEvent actionEvent) {
        this.app.sendSocket.sendMessage("watch ".concat(this.jComboBox1.getEditor().getItem().toString()));
        this.app.sendSocket.sendMessage("show watch");
    }
    
    void attributeTableList_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final int selectedRow = this.jTableAttributeData.getSelectedRow();
        this.lastSelectedAttributeRow = selectedRow;
        final String string = this.jTableAttributeData.getValueAt(selectedRow, 0).toString();
        this.lastAttributeValueBeforeEdit = this.jTableAttributeData.getValueAt(selectedRow, 1).toString();
        final int index = PropertySheetDialogBox.originalName.indexOf(" ");
        String item;
        if (index > 0) {
            final StringBuffer sb = new StringBuffer();
            sb.append(PropertySheetDialogBox.originalName);
            sb.insert(index, ":".concat(string));
            item = sb.toString();
        }
        else {
            item = PropertySheetDialogBox.originalName.concat(":").concat(string);
        }
        this.jComboBox1.getEditor().setItem(item);
    }
    
    void fieldTableList_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final int selectedRow = this.jTableFieldData.getSelectedRow();
        this.lastSelectedFieldRow = selectedRow;
        final String string = this.jTableFieldData.getValueAt(selectedRow, 0).toString();
        this.lastFieldValueBeforeEdit = this.jTableFieldData.getValueAt(selectedRow, 1).toString();
        this.jComboBox1.getEditor().setItem(PropertySheetDialogBox.originalName.concat(".").concat(string));
    }
    
    void arrayTableList_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final int selectedRow = this.jTableArrayData.getSelectedRow();
        this.lastSelectedArrayRow = selectedRow;
        try {
            final String string = this.jTableArrayData.getValueAt(selectedRow, 0).toString();
            this.lastArrayValueBeforeEdit = this.jTableArrayData.getValueAt(selectedRow, 1).toString();
            final int index = PropertySheetDialogBox.originalName.indexOf(" ");
            String item;
            if (index > 0) {
                final StringBuffer sb = new StringBuffer();
                sb.append(PropertySheetDialogBox.originalName);
                sb.insert(index, ":".concat(string));
                item = sb.toString();
            }
            else {
                int lastIndex = PropertySheetDialogBox.originalName.lastIndexOf(":");
                if (lastIndex < 0) {
                    lastIndex = 0;
                }
                final int index2 = PropertySheetDialogBox.originalName.indexOf("]", lastIndex);
                final int index3 = PropertySheetDialogBox.originalName.indexOf("[", lastIndex);
                if (index2 > 0 && index3 > 0 && index3 < index2) {
                    PropertySheetDialogBox.originalName = PropertySheetDialogBox.originalName.substring(0, index3);
                }
                item = PropertySheetDialogBox.originalName.concat("[" + string + "]");
            }
            this.jComboBox1.getEditor().setItem(item);
        }
        catch (Exception ex) {}
    }
    
    void updateAttributeData(final Vector flagsData) {
        this.jScrollPane1.setEnabled(false);
        int width = 120;
        int width2 = 120;
        PropertySheetDialogBox.originalName = this.app.propertySheetName;
        try {
            width = this.jTableAttributeData.getColumnModel().getColumn(0).getWidth();
            width2 = this.jTableAttributeData.getColumnModel().getColumn(1).getWidth();
            this.jTableAttributeData.getColumnModel().getColumn(2).getWidth();
        }
        catch (Exception ex) {}
        this.jScrollPane1.remove(this.jTableAttributeData);
        this.flagsData = flagsData;
        (this.jTableAttributeData = new JTable(new PropertySheetTableModel(this.app, this.app.propertySheetData, this.typeData, this.flagsData))).setFont(this.app.currentFont);
        this.jTableAttributeData.setAutoResizeMode(0);
        this.jTableAttributeData.getTableHeader().setReorderingAllowed(false);
        this.jTableAttributeData.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.jTableAttributeData.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.jTableAttributeData.getColumnModel().getColumn(2).setPreferredWidth(width2);
        this.jTableAttributeData.setSelectionMode(0);
        (this.attribueTableList = this.jTableAttributeData.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                PropertySheetDialogBox.this.attributeTableList_valueChanged(listSelectionEvent);
            }
        });
        this.jTableAttributeData.setSelectionModel(this.attribueTableList);
        this.jScrollPane1.setViewportView(this.jTableAttributeData);
        this.jComboBox1.getEditor().setItem(this.app.propertySheetName);
        if (this.lastSelectedAttributeRow != -1 && this.triggerAttributeUpdate) {
            this.jTableAttributeData.changeSelection(this.lastSelectedAttributeRow, 0, true, false);
            this.triggerAttributeUpdate = false;
        }
        this.addKeyListners();
        this.jScrollPane1.setEnabled(true);
        this.stopWaitRequest();
    }
    
    void updateFieldData(final Vector fieldFlagsData) {
        this.jScrollPaneFields.setEnabled(false);
        int width = 120;
        int width2 = 120;
        PropertySheetDialogBox.originalName = this.app.propertySheetName;
        try {
            width = this.jTableFieldData.getColumnModel().getColumn(0).getWidth();
            width2 = this.jTableFieldData.getColumnModel().getColumn(1).getWidth();
            this.jTableFieldData.getColumnModel().getColumn(2).getWidth();
        }
        catch (Exception ex) {}
        this.jTableFieldData.getSelectedRow();
        this.jScrollPaneFields.remove(this.jTableFieldData);
        this.fieldFlagsData = fieldFlagsData;
        (this.jTableFieldData = new JTable(new PropertySheetTableModel(this.app, this.app.propertySheetData, this.typeData, this.fieldFlagsData))).setFont(this.app.currentFont);
        this.jTableFieldData.setAutoResizeMode(0);
        this.jTableFieldData.getTableHeader().setReorderingAllowed(false);
        this.jTableFieldData.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.jTableFieldData.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.jTableFieldData.getColumnModel().getColumn(2).setPreferredWidth(width2);
        this.jTableFieldData.setSelectionMode(0);
        (this.fieldTableList = this.jTableFieldData.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                PropertySheetDialogBox.this.fieldTableList_valueChanged(listSelectionEvent);
            }
        });
        this.jTableFieldData.setSelectionModel(this.fieldTableList);
        this.jScrollPaneFields.setViewportView(this.jTableFieldData);
        this.jComboBox1.getEditor().setItem(this.app.propertySheetName);
        if (this.lastSelectedFieldRow != -1 && this.triggerFieldUpdate) {
            this.jTableFieldData.changeSelection(this.lastSelectedFieldRow, 0, true, false);
            this.triggerFieldUpdate = false;
        }
        this.addKeyListners();
        this.jScrollPaneFields.setEnabled(true);
        this.stopWaitRequest();
    }
    
    void updateArrayData(final Vector arrayFlagsData) {
        this.jScrollPaneArray.setEnabled(false);
        this.requestWait();
        int width = 180;
        int width2 = 180;
        PropertySheetDialogBox.originalName = this.app.propertySheetName;
        try {
            width = this.jTableArrayData.getColumnModel().getColumn(0).getWidth();
            width2 = this.jTableArrayData.getColumnModel().getColumn(1).getWidth();
        }
        catch (Exception ex) {}
        this.arrayFlagsData = arrayFlagsData;
        (this.jTableArrayData = new JTable(new ArrayTableModel(this.app, this.app.propertySheetData, this.arrayFlagsData))).setFont(this.app.currentFont);
        this.jTableArrayData.setAutoResizeMode(0);
        this.jTableArrayData.getTableHeader().setReorderingAllowed(false);
        this.jTableArrayData.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.jTableArrayData.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.jTableArrayData.setSelectionMode(0);
        (this.arrayTableList = this.jTableArrayData.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                PropertySheetDialogBox.this.arrayTableList_valueChanged(listSelectionEvent);
            }
        });
        this.jTableArrayData.setSelectionModel(this.arrayTableList);
        this.jScrollPaneArray.setViewportView(this.jTableArrayData);
        this.jComboBox1.getEditor().setItem(this.app.propertySheetName);
        if (this.lastSelectedArrayRow != -1 && this.triggerArrayUpdate) {
            this.jTableArrayData.changeSelection(this.lastSelectedArrayRow, 0, true, false);
            this.triggerArrayUpdate = false;
        }
        this.addKeyListners();
        this.stopWaitRequest();
        this.jScrollPaneArray.setEnabled(true);
    }
    
    void updateValueData(final String s, final String text, final String s2) {
        this.jScrollPaneValue.setEnabled(false);
        this.jScrollPaneValue.remove(this.value);
        this.jScrollPaneValue.remove(this.error_msg);
        (this.value = new JTextPane()).addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.value_keyPressed(keyEvent);
            }
        });
        this.value.setText(text);
        if (s2.compareToIgnoreCase("R") == 0) {
            this.value.setEditable(false);
        }
        else {
            this.value.setEditable(true);
        }
        this.jScrollPaneValue.setViewportView(this.value);
        this.jScrollPaneValue.setEnabled(true);
        this.value.setCaretPosition(0);
        this.stopWaitRequest();
    }
    
    void setErrorMessage(final String text, final int n) {
        this.error_msg.setText(text);
        switch (n) {
            default: {
                this.jScrollPane1.remove(this.jTableAttributeData);
                this.jScrollPane1.setViewportView(this.error_msg);
                break;
            }
            case 1: {
                this.jScrollPaneFields.remove(this.jTableFieldData);
                this.jScrollPaneFields.setViewportView(this.error_msg);
                break;
            }
            case 2: {
                this.jScrollPaneArray.remove(this.jTableArrayData);
                this.jScrollPaneArray.setViewportView(this.error_msg);
                break;
            }
            case 3: {
                this.jScrollPaneValue.remove(this.value);
                this.jScrollPaneValue.remove(this.error_msg);
                this.jScrollPaneValue.setViewportView(this.error_msg);
                break;
            }
        }
        this.stopWaitRequest();
        this.enableScrollPanes();
    }
    
    void jTabbedPaneDataview_stateChanged(final ChangeEvent changeEvent) {
        this.disableScrollPanes();
        final String string = this.jComboBox1.getEditor().getItem().toString();
        if (!this.comboBoxItemCheck(string) && string.length() > 0) {
            this.jComboBox1.insertItemAt(string, 0);
            this.jComboBox1.setSelectedIndex(0);
            PropertySheetDialogBox.originalName = string;
        }
        this.tabbedPaneIndex = this.jTabbedPaneDataview.getSelectedIndex();
        if (this.tabbedPaneIndex == 1) {
            this.jScrollPaneFields.remove(this.jTableFieldData);
            if (this.jComboBox1.getEditor().getItem().toString().length() > 0) {
                this.requestWait();
                this.app.requestGetFields(this.jComboBox1.getEditor().getItem().toString());
                this.app.getAttrOrGetFieldsRequest = true;
            }
        }
        else if (this.tabbedPaneIndex == 2) {
            this.jScrollPaneArray.remove(this.jTableArrayData);
            if (this.jComboBox1.getEditor().getItem().toString().length() > 0) {
                this.requestWait();
                this.lastArrayViewName = this.jComboBox1.getEditor().getItem().toString();
                this.app.requestGetArray(this.jComboBox1.getEditor().getItem().toString());
                this.app.getAttrOrGetFieldsRequest = true;
            }
        }
        else if (this.tabbedPaneIndex == 3) {
            this.jScrollPaneValue.remove(this.value);
            if (this.jComboBox1.getEditor().getItem().toString().length() > 0) {
                this.requestWait();
                this.app.requestGetValue(this.jComboBox1.getEditor().getItem().toString());
                this.app.getAttrOrGetFieldsRequest = true;
            }
        }
        else {
            this.jScrollPane1.remove(this.jTableAttributeData);
            if (this.jComboBox1.getEditor().getItem().toString().length() > 0) {
                this.requestWait();
                this.app.requestGetAttrs(this.jComboBox1.getEditor().getItem().toString());
                this.app.getAttrOrGetFieldsRequest = true;
            }
        }
    }
    
    void dialogKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.jButton1ActionPerformed(null);
        }
        if (keyEvent.getKeyCode() == 10) {
            this.jButtonView_actionPerformed(null);
        }
    }
    
    private void addKeyListners() {
        this.jComboBox1.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.jComboBox1.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.jComboBox1_keyReleased(keyEvent);
            }
        });
        this.jTableAttributeData.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.jTableFieldData.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.jTabbedPaneDataview.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.jTableArrayData.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                PropertySheetDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
    }
    
    protected void processWindowEvent(final WindowEvent e) {
        if (e.getID() == 201) {
            this.app.propertySheetOpen = false;
            this.jComboBox1.removeItem("");
            this.app.getAttrOrGetFieldsRequest = false;
            this.app.savePropertySheetComboBox(this.jComboBox1);
            this.app.setEnabled(true);
            this.dispose();
        }
        super.processWindowEvent(e);
    }
    
    protected boolean comboBoxItemCheck(final String anotherString) {
        for (int itemCount = this.jComboBox1.getItemCount(), i = 0; i < itemCount; ++i) {
            if (this.jComboBox1.getItemAt(i).toString().compareTo(anotherString) == 0) {
                return true;
            }
        }
        return false;
    }
    
    void value_keyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 10) {
            this.requestWait();
            this.app.sendSocket.sendMessage("ASSIGN ".concat(this.jComboBox1.getEditor().getItem().toString() + " = " + this.value.getText()));
            this.app.requestGetValue(this.jComboBox1.getEditor().getItem().toString());
            this.app.getAttrOrGetFieldsRequest = true;
            this.app.requestUpdates(false);
        }
    }
    
    private void disableButtons() {
        this.jButtonView.setEnabled(false);
        this.jButtonAddWatchpoint.setEnabled(false);
    }
    
    private void enableButtons() {
        this.jButtonView.setEnabled(true);
        this.jButtonAddWatchpoint.setEnabled(true);
    }
    
    void jComboBox1_keyReleased(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.jButton1ActionPerformed(null);
        }
        if (this.jComboBox1.getEditor().getItem().toString().compareTo("") != 0) {
            this.enableButtons();
            if (keyEvent.getKeyCode() == 10) {
                this.jButtonView_actionPerformed(null);
            }
        }
        else {
            this.disableButtons();
        }
    }
    
    void requestWait() {
        this.setCursor(Cursor.getPredefinedCursor(3));
        this.jTabbedPaneDataview.setEnabled(false);
        this.disableButtons();
        this.jButtonClose.setEnabled(false);
        this.jComboBox1.setEnabled(false);
    }
    
    void stopWaitRequest() {
        this.jTabbedPaneDataview.setEnabled(true);
        this.enableButtons();
        this.jButtonClose.setEnabled(true);
        this.jComboBox1.setEnabled(true);
        this.setCursor(Cursor.getPredefinedCursor(0));
    }
    
    void jComboBox1_actionPerformed(final ActionEvent actionEvent) {
        this.enableButtons();
    }
    
    void disableScrollPanes() {
        this.jScrollPaneArray.setEnabled(false);
        this.jScrollPane1.setEnabled(false);
        this.jScrollPaneFields.setEnabled(false);
        this.jScrollPaneValue.setEnabled(false);
    }
    
    void enableScrollPanes() {
        this.jScrollPaneArray.setEnabled(true);
        this.jScrollPane1.setEnabled(true);
        this.jScrollPaneFields.setEnabled(true);
        this.jScrollPaneValue.setEnabled(true);
    }
}
