// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.Point;
import java.util.StringTokenizer;
import javax.swing.table.TableColumnModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
import javax.swing.table.TableCellEditor;
import javax.swing.JCheckBox;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Frame;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JPanel;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JDialog;

public class BreakpointDialogBox extends JDialog
{
    Frame1 application;
    private JTable breakpointsTable;
    private JButton jButtonRemoveAll;
    private JButton jButtonRemove;
    private JButton jButtonClose;
    private JScrollPane jScrollPane1;
    private JButton jButtonNew;
    private Vector BreaksToRemove;
    private String BreakPointNumbers;
    private Vector breakpointsWithCheckbox;
    private JPanel main;
    private Vector bp;
    private Vector disabledList;
    private Vector disabledListNumbers;
    private Vector bpNumbers;
    private String breaksToEnable;
    private String breaksToDisable;
    private boolean remove;
    Box box1;
    JLabel jLabel1;
    JLabel jLabel2;
    JLabel jLabel3;
    JLabel jLabel4;
    JLabel jLabel5;
    JLabel jLabel6;
    JLabel jLabel7;
    JButton jButtonEdit;
    JLabel jLabel8;
    private Vector bpType;
    Vector bpLineErrorNum;
    ListSelectionModel breakpointListSelectionModel;
    
    public BreakpointDialogBox(final Frame1 frame1, final String s, final boolean b, final Vector bpType, final Vector bpLineErrorNum) {
        super(frame1, "Breakpoints", true);
        this.jButtonRemoveAll = new JButton();
        this.jButtonRemove = new JButton();
        this.jButtonClose = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.jButtonNew = new JButton();
        this.BreaksToRemove = new Vector();
        this.BreakPointNumbers = " ";
        this.breakpointsWithCheckbox = new Vector();
        this.main = new JPanel();
        this.remove = false;
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jButtonEdit = new JButton();
        this.jLabel8 = new JLabel();
        try {
            this.application = frame1;
            this.bpType = bpType;
            this.bpLineErrorNum = bpLineErrorNum;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    public BreakpointDialogBox() {
        this(null, "", false, null, null);
    }
    
    void jbInit() throws Exception {
        this.box1 = Box.createVerticalBox();
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                BreakpointDialogBox.this.dialogKeyPressed(keyEvent);
            }
        });
        this.setResizable(true);
        this.main = new JPanel();
        final TableUtilities tableUtilities = new TableUtilities();
        final Vector<String> vector = new Vector<String>();
        final JComponentTableCellRenderer cellRenderer = new JComponentTableCellRenderer();
        this.bp = this.application.meshandle.breakpointList;
        this.disabledList = this.application.meshandle.disabledBreakpointsList;
        this.removeNumbersFromBreakpoints();
        vector.addElement("");
        vector.addElement("");
        (this.breakpointsTable = new JTable(new BreakpointDialogTableModel(this.bp))).addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                BreakpointDialogBox.this.main_keyReleased(keyEvent);
            }
        });
        (this.breakpointListSelectionModel = this.breakpointsTable.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                BreakpointDialogBox.this.breakpointListSelectionModel_valueChanged(listSelectionEvent);
            }
        });
        this.breakpointsTable.setSelectionMode(0);
        this.breakpointsTable.setAutoResizeMode(0);
        this.breakpointsTable.setTableHeader(null);
        this.breakpointsTable.setShowGrid(false);
        final TableColumnModel columnModel = this.breakpointsTable.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(cellRenderer);
        final JCheckBox checkBox = new JCheckBox();
        columnModel.getColumn(0).setCellEditor(new JComponentTableCellEditor());
        TableUtilities.setColumnWidths(this.breakpointsTable, new Insets(0, 1, 0, 1), true, false);
        this.jButtonNew.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                BreakpointDialogBox.this.newButtonPushed(actionEvent);
            }
        });
        this.jButtonRemoveAll.setFont(new Font("Dialog", 0, 12));
        this.jButtonRemoveAll.setMaximumSize(new Dimension(97, 27));
        this.jButtonRemoveAll.setMinimumSize(new Dimension(97, 27));
        this.jButtonRemoveAll.setPreferredSize(new Dimension(97, 27));
        this.jButtonRemoveAll.setText("Remove All");
        this.jButtonRemoveAll.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                BreakpointDialogBox.this.removeAllPerformed(actionEvent);
            }
        });
        this.jButtonRemove.setText("Remove");
        this.jButtonRemove.setEnabled(false);
        this.jButtonRemove.setFont(new Font("Dialog", 0, 12));
        this.jButtonRemove.setMaximumSize(new Dimension(97, 27));
        this.jButtonRemove.setPreferredSize(new Dimension(97, 27));
        this.jButtonRemove.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                BreakpointDialogBox.this.removeButtonPushed(actionEvent);
            }
        });
        this.jButtonClose.setFont(new Font("Dialog", 0, 12));
        this.jButtonClose.setMaximumSize(new Dimension(97, 27));
        this.jButtonClose.setPreferredSize(new Dimension(97, 27));
        this.jButtonClose.setText("Close");
        this.jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                BreakpointDialogBox.this.jButtonClose_actionPerformed(actionEvent);
            }
        });
        this.jButtonNew.setFont(new Font("Dialog", 0, 12));
        this.jButtonNew.setMaximumSize(new Dimension(97, 27));
        this.jButtonNew.setPreferredSize(new Dimension(97, 27));
        this.jButtonNew.setText("New...");
        this.main.setFont(new Font("Dialog", 0, 12));
        this.main.setMaximumSize(new Dimension(3276700, 3276700));
        this.main.setMinimumSize(new Dimension(420, 256));
        this.main.setPreferredSize(new Dimension(420, 320));
        this.jScrollPane1.setPreferredSize(new Dimension(300, 300));
        this.jLabel1.setFont(new Font("Dialog", 0, 4));
        this.jLabel1.setText(" ");
        this.jLabel2.setFont(new Font("Dialog", 0, 4));
        this.jLabel2.setText(" ");
        this.jLabel3.setText(" ");
        this.jLabel4.setFont(new Font("Dialog", 0, 4));
        this.jLabel4.setText(" ");
        this.jLabel5.setText(" ");
        this.jLabel6.setText(" ");
        this.jLabel7.setText(" ");
        this.jButtonEdit.setFont(new Font("Dialog", 0, 12));
        this.jButtonEdit.setMaximumSize(new Dimension(97, 27));
        this.jButtonEdit.setPreferredSize(new Dimension(97, 27));
        this.jButtonEdit.setEnabled(false);
        this.jButtonEdit.setText("Edit...");
        this.jButtonEdit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                BreakpointDialogBox.this.jButtonEdit_actionPerformed(actionEvent);
            }
        });
        this.jLabel8.setFont(new Font("Dialog", 0, 4));
        this.jLabel8.setText(" ");
        this.main.add(this.jScrollPane1, null);
        this.jScrollPane1.setViewportView(this.breakpointsTable);
        this.main.add(this.box1, null);
        this.box1.add(this.jButtonClose, null);
        this.box1.add(this.jLabel1, null);
        this.box1.add(this.jButtonNew, null);
        this.box1.add(this.jLabel2, null);
        this.box1.add(this.jButtonEdit, null);
        this.box1.add(this.jLabel8, null);
        this.box1.add(this.jButtonRemove, null);
        this.box1.add(this.jLabel4, null);
        this.box1.add(this.jButtonRemoveAll, null);
        this.box1.add(this.jLabel3, null);
        this.box1.add(this.jLabel5, null);
        this.box1.add(this.jLabel7, null);
        this.box1.add(this.jLabel6, null);
        this.main.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                BreakpointDialogBox.this.main_keyReleased(keyEvent);
            }
        });
        this.jScrollPane1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                BreakpointDialogBox.this.main_keyReleased(keyEvent);
            }
        });
        this.getContentPane().addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent keyEvent) {
                BreakpointDialogBox.this.main_keyReleased(keyEvent);
            }
        });
        this.getContentPane().add(this.main);
    }
    
    void jButtonClose_actionPerformed(final ActionEvent actionEvent) {
        this.checkEnabled();
        this.dispose();
    }
    
    void checkIfAlreadyDisabled(final String s, final int index) {
        for (int size = this.disabledList.size(), i = 0; i < size; ++i) {
            if (s.compareTo(this.disabledList.elementAt(i).toString()) == 0) {
                return;
            }
        }
        final String string = this.bpNumbers.elementAt(index).toString();
        if (this.breaksToDisable == null) {
            this.breaksToDisable = string;
        }
        else {
            this.breaksToDisable = this.breaksToDisable.concat(string + " ");
        }
    }
    
    void checkIfNeedsToBeEnabled(final String s, final int index) {
        for (int size = this.disabledList.size(), i = 0; i < size; ++i) {
            if (s.compareTo(this.disabledList.elementAt(i).toString()) == 0) {
                final String string = this.bpNumbers.elementAt(index).toString();
                if (this.breaksToEnable == null) {
                    this.breaksToEnable = string;
                }
                else {
                    this.breaksToEnable = this.breaksToEnable.concat(string + " ");
                }
            }
        }
    }
    
    void removeAllPerformed(final ActionEvent actionEvent) {
        if (this.breakpointsTable.getRowCount() > 0) {
            this.application.sendSocket.sendMessage("cancel all breaks");
            this.dispose();
            this.application.sendSocket.sendMessage("show breaks");
        }
    }
    
    void removeButtonPushed(final ActionEvent actionEvent) {
        final String s = "cancel break ";
        final int selectedRow = this.breakpointsTable.getSelectedRow();
        if (selectedRow >= 0) {
            this.application.sendSocket.sendMessage(s.concat(this.bpNumbers.elementAt(selectedRow).toString()));
            this.checkEnabled();
            this.dispose();
            this.application.sendSocket.sendMessage("show breaks");
        }
    }
    
    void dialogKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
    }
    
    private void removeNumbersFromBreakpoints() {
        this.bpNumbers = new Vector();
        for (int i = 0; i < this.bp.size(); ++i) {
            final Vector<Object> vector = this.bp.elementAt(i);
            final StringTokenizer stringTokenizer = new StringTokenizer(vector.elementAt(1).toString(), " ");
            final String nextToken = stringTokenizer.nextToken();
            String obj = stringTokenizer.nextToken();
            if (stringTokenizer.countTokens() > 1) {
                while (stringTokenizer.countTokens() > 1) {
                    obj = obj.concat(" " + stringTokenizer.nextToken());
                }
            }
            if (stringTokenizer.hasMoreTokens()) {
                obj = obj.concat(" " + stringTokenizer.nextToken() + " ");
            }
            this.bpNumbers.addElement(nextToken);
            vector.setElementAt(obj, 1);
        }
        this.removeNumbersFromDisabledBreakpoints();
    }
    
    private void removeNumbersFromDisabledBreakpoints() {
        this.disabledListNumbers = new Vector();
        for (int i = 0; i < this.disabledList.size(); ++i) {
            final StringTokenizer stringTokenizer = new StringTokenizer(this.disabledList.elementAt(i).toString(), " ");
            final String nextToken = stringTokenizer.nextToken();
            String obj = stringTokenizer.nextToken();
            if (stringTokenizer.countTokens() > 1) {
                while (stringTokenizer.countTokens() > 1) {
                    obj = obj.concat(" " + stringTokenizer.nextToken());
                }
            }
            if (stringTokenizer.hasMoreTokens()) {
                obj = obj.concat(" " + stringTokenizer.nextToken() + " ");
            }
            this.disabledListNumbers.addElement(nextToken);
            this.disabledList.setElementAt(obj, i);
        }
    }
    
    void newButtonPushed(final ActionEvent actionEvent) {
        final NewBreakpointDialog newBreakpointDialog = new NewBreakpointDialog(this.application, this, "New Breakpoint", " ", " ", " ", "");
        final Dimension preferredSize = newBreakpointDialog.getPreferredSize();
        final Dimension size = this.application.getSize();
        final Point location = this.application.getLocation();
        newBreakpointDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        newBreakpointDialog.show();
    }
    
    void jButtonEdit_actionPerformed(final ActionEvent actionEvent) {
        final int selectedRow = this.breakpointsTable.getSelectedRow();
        NewBreakpointDialog newBreakpointDialog;
        if (selectedRow < 0) {
            newBreakpointDialog = new NewBreakpointDialog(this.application, this, "New Breakpoint", " ", " ", " ", "");
        }
        else {
            newBreakpointDialog = new NewBreakpointDialog(this.application, this, "Edit Breakpoint", this.bpNumbers.elementAt(selectedRow).toString(), this.breakpointsTable.getValueAt(selectedRow, 1).toString(), this.bpType.elementAt(selectedRow).toString(), this.bpLineErrorNum.elementAt(selectedRow).toString());
        }
        final Dimension preferredSize = newBreakpointDialog.getPreferredSize();
        final Dimension size = this.application.getSize();
        final Point location = this.application.getLocation();
        newBreakpointDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        newBreakpointDialog.show();
    }
    
    void main_keyReleased(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.dispose();
        }
    }
    
    void breakpointListSelectionModel_valueChanged(final ListSelectionEvent listSelectionEvent) {
        if (this.breakpointsTable.getSelectedRow() >= 0) {
            this.jButtonEdit.setEnabled(true);
            this.jButtonRemove.setEnabled(true);
        }
        else {
            this.jButtonEdit.setEnabled(false);
            this.jButtonRemove.setEnabled(false);
        }
    }
    
    void checkEnabled() {
        final Sockets sendSocket = this.application.sendSocket;
        for (int rowCount = this.breakpointsTable.getRowCount(), i = 0; i < rowCount; ++i) {
            final Vector<JCheckBox> vector = this.bp.elementAt(i);
            final boolean selected = vector.elementAt(0).isSelected();
            final String string = vector.elementAt(1).toString();
            if (!selected) {
                this.checkIfAlreadyDisabled(string, i);
            }
            else {
                this.checkIfNeedsToBeEnabled(string, i);
            }
        }
        if (this.breaksToDisable != null) {
            sendSocket.sendMessage("disable break ".concat(this.breaksToDisable));
        }
        if (this.breaksToEnable != null) {
            sendSocket.sendMessage("enable break ".concat(this.breaksToEnable));
        }
    }
}
