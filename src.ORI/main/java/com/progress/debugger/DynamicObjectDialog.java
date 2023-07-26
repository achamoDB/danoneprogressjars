// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.awt.event.WindowEvent;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import java.util.Vector;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class DynamicObjectDialog extends JFrame
{
    private JPanel panel1;
    private BorderLayout borderLayout1;
    private JPanel jPanelButtons;
    private JButton jButtonStopMonitor;
    private JButton jButtonStartMonitor;
    private JTabbedPane jTabbedPane1;
    private JPanel controlMonitorPane;
    private JPanel viewObjectsPane;
    private Vector databaseObjectsNode;
    private Vector userInterfaceWidgetsNode;
    private Vector xmlObjectsNode;
    private Vector otherObjectsNode;
    private Vector rootNodes;
    private Vector databaseObjectsHexValues;
    private Vector userInterfaceWidgetsHexValues;
    private Vector xmlObjectsHexValues;
    private Vector otherObjectsHexValues;
    JTree tree;
    private JScrollPane jScrollPaneControlMonitor;
    private JButton jButtonClose;
    JCheckBox databaseCheckBox;
    JCheckBox uiCheckBox;
    JCheckBox xmlCheckBox;
    JCheckBox otherCheckBox;
    CheckBoxNodeRenderer renderer;
    Frame1 application;
    JPanel jPanel1;
    JPanel jPanel2;
    JScrollPane jScrollPane1;
    private DefaultTableModel theModel;
    JTable jTable1;
    JScrollPane jScrollPane2;
    JList jList1;
    Box box1;
    JButton jButtonFilter;
    JButton jButtonShow;
    boolean monitoring;
    Vector databaseVector;
    Vector userInterfaceVector;
    Vector xmlVector;
    Vector otherVector;
    private Vector objectListData;
    private Vector filteredObjectListData;
    private Vector unFilteredTable;
    private Vector names;
    private int objectColumnWidth;
    private int procedureNameColumnWidth;
    private int lineNumberColumnWidth;
    private int otherDataColumnWidth;
    JLabel jLabel2;
    private boolean startedMonitoring;
    
    public DynamicObjectDialog(final Frame1 application, final String s, final boolean b) {
        super("Dynamic Object Tracking");
        this.panel1 = new JPanel();
        this.borderLayout1 = new BorderLayout();
        this.jPanelButtons = new JPanel();
        this.jButtonStopMonitor = new JButton();
        this.jButtonStartMonitor = new JButton();
        this.jTabbedPane1 = new JTabbedPane();
        this.controlMonitorPane = new JPanel();
        this.viewObjectsPane = new JPanel();
        this.databaseObjectsNode = new Vector();
        this.userInterfaceWidgetsNode = new Vector();
        this.xmlObjectsNode = new Vector();
        this.otherObjectsNode = new Vector();
        this.rootNodes = new Vector();
        this.databaseObjectsHexValues = new Vector();
        this.userInterfaceWidgetsHexValues = new Vector();
        this.xmlObjectsHexValues = new Vector();
        this.otherObjectsHexValues = new Vector();
        this.tree = new JTree();
        this.jScrollPaneControlMonitor = new JScrollPane();
        this.jButtonClose = new JButton();
        this.databaseCheckBox = new JCheckBox("Database Objects");
        this.uiCheckBox = new JCheckBox("User Interface Widgets");
        this.xmlCheckBox = new JCheckBox("XML Objects");
        this.otherCheckBox = new JCheckBox("Other Objects");
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.theModel = new DefaultTableModel();
        this.jTable1 = new JTable(this.theModel);
        this.jScrollPane2 = new JScrollPane();
        this.jList1 = new JList();
        this.jButtonFilter = new JButton();
        this.jButtonShow = new JButton();
        this.monitoring = false;
        this.databaseVector = new Vector();
        this.userInterfaceVector = new Vector();
        this.xmlVector = new Vector();
        this.otherVector = new Vector();
        this.objectListData = new Vector();
        this.filteredObjectListData = new Vector();
        this.unFilteredTable = new Vector();
        this.objectColumnWidth = 140;
        this.procedureNameColumnWidth = 150;
        this.lineNumberColumnWidth = 60;
        this.otherDataColumnWidth = 100;
        this.jLabel2 = new JLabel();
        this.startedMonitoring = false;
        try {
            this.application = application;
            this.jbInit();
            this.pack();
        }
        catch (Exception ex) {}
    }
    
    void jbInit() throws Exception {
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("debug.gif")));
        this.jTable1.setAutoResizeMode(0);
        this.jTable1.getTableHeader().setReorderingAllowed(false);
        this.jList1 = new JList(this.objectListData);
        this.box1 = Box.createVerticalBox();
        this.panel1.setLayout(this.borderLayout1);
        this.jButtonStopMonitor.setFont(new Font("Dialog", 0, 12));
        this.jButtonStopMonitor.setText("Stop Monitoring");
        this.jButtonStopMonitor.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DynamicObjectDialog.this.jButtonStopMonitor_actionPerformed(actionEvent);
            }
        });
        this.jButtonStartMonitor.setFont(new Font("Dialog", 0, 12));
        this.jButtonStartMonitor.setText("Start Monitoring");
        this.jButtonStartMonitor.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DynamicObjectDialog.this.jButtonStartMonitor_actionPerformed(actionEvent);
            }
        });
        this.jButtonClose.setFont(new Font("Dialog", 0, 12));
        this.jButtonClose.setText("Close");
        this.jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DynamicObjectDialog.this.jButtonClose_actionPerformed(actionEvent);
            }
        });
        this.jButtonFilter.setText("Filter List");
        this.jButtonFilter.setEnabled(false);
        this.jButtonFilter.setFont(new Font("Dialog", 0, 12));
        this.jButtonFilter.setMaximumSize(new Dimension(145, 27));
        this.jButtonFilter.setMinimumSize(new Dimension(145, 27));
        this.jButtonFilter.setPreferredSize(new Dimension(145, 27));
        this.jButtonFilter.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DynamicObjectDialog.this.jButtonFilter_actionPerformed(actionEvent);
            }
        });
        this.jButtonShow.setText("Show Complete List");
        this.jButtonShow.setEnabled(false);
        this.jButtonShow.setFont(new Font("Dialog", 0, 12));
        this.jButtonShow.setMaximumSize(new Dimension(145, 27));
        this.jButtonShow.setMinimumSize(new Dimension(145, 27));
        this.jButtonShow.setPreferredSize(new Dimension(145, 27));
        this.jButtonShow.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                DynamicObjectDialog.this.jButtonShow_actionPerformed(actionEvent);
            }
        });
        this.jTabbedPane1.setFont(new Font("Dialog", 0, 12));
        this.jTabbedPane1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jTabbedPane1.setMinimumSize(new Dimension(380, 320));
        this.jTabbedPane1.setPreferredSize(new Dimension(380, 320));
        this.panel1.setFont(new Font("Dialog", 0, 12));
        this.panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.panel1.setMinimumSize(new Dimension(450, 500));
        this.panel1.setPreferredSize(new Dimension(550, 500));
        this.jPanel1.setMaximumSize(new Dimension(454, 275));
        this.jPanel1.setMinimumSize(new Dimension(454, 275));
        this.jPanel1.setPreferredSize(new Dimension(454, 275));
        this.jScrollPane1.setMaximumSize(new Dimension(420, 225));
        this.jScrollPane1.setMinimumSize(new Dimension(10, 10));
        this.jScrollPane1.setPreferredSize(new Dimension(420, 225));
        this.jScrollPane2.setMaximumSize(new Dimension(260, 132));
        this.jScrollPane2.setMinimumSize(new Dimension(260, 132));
        this.jList1.setMaximumSize(new Dimension(260, 132));
        this.jList1.setMinimumSize(new Dimension(260, 132));
        this.jList1.setPreferredSize(new Dimension(260, 132));
        this.jLabel2.setText(" ");
        this.jPanelButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.jPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.getContentPane().add(this.panel1);
        this.panel1.add(this.jPanelButtons, "South");
        this.jPanelButtons.add(this.jButtonStartMonitor, null);
        this.jPanelButtons.add(this.jButtonStopMonitor, null);
        this.jPanelButtons.add(this.jButtonClose, null);
        this.panel1.add(this.jTabbedPane1, "Center");
        this.jScrollPaneControlMonitor.setViewportView(this.tree);
        this.jTabbedPane1.add("Control Monitoring", this.jScrollPaneControlMonitor);
        this.jTabbedPane1.add("View Objects", this.viewObjectsPane);
        this.viewObjectsPane.add(this.jPanel2, null);
        this.jPanel2.add(this.jScrollPane2, null);
        this.jPanel2.add(this.box1, null);
        this.box1.add(this.jButtonFilter, null);
        this.box1.add(this.jLabel2, null);
        this.box1.add(this.jButtonShow, null);
        this.jScrollPane2.getViewport().add(this.jList1, null);
        this.viewObjectsPane.add(this.jPanel1, null);
        this.jPanel1.add(this.jScrollPane1, null);
        this.jScrollPane1.getViewport().add(this.jTable1, null);
        (this.names = new Vector()).addElement("Object");
        this.names.addElement("Procedure Name");
        this.names.addElement("Line #");
        this.names.addElement("Other Data");
    }
    
    public void setObjects(final Vector databaseObjectsNode, final Vector userInterfaceWidgetsNode, final Vector xmlObjectsNode, final Vector otherObjectsNode) {
        this.databaseObjectsNode = databaseObjectsNode;
        this.userInterfaceWidgetsNode = userInterfaceWidgetsNode;
        this.xmlObjectsNode = xmlObjectsNode;
        this.otherObjectsNode = otherObjectsNode;
        this.databaseVector = new NamedVector("Database Objects", this.databaseObjectsNode);
        this.userInterfaceVector = new NamedVector("User Interface Widgets", this.userInterfaceWidgetsNode);
        this.xmlVector = new NamedVector("XML Objects", this.xmlObjectsNode);
        this.otherVector = new NamedVector("Other Objects", this.otherObjectsNode);
        (this.rootNodes = new Vector()).addElement(this.databaseVector);
        this.rootNodes.addElement(this.userInterfaceVector);
        this.rootNodes.addElement(this.xmlVector);
        this.rootNodes.addElement(this.otherVector);
        this.tree = new JTree(new NamedVector("Root", this.rootNodes));
        this.renderer = new CheckBoxNodeRenderer(this);
        this.tree.setCellRenderer(this.renderer);
        this.tree.setCellEditor(new CheckBoxNodeEditor(this.tree, this));
        this.tree.setEditable(true);
        this.jScrollPaneControlMonitor.setViewportView(this.tree);
    }
    
    public void setHexValues(final Vector databaseObjectsHexValues, final Vector userInterfaceWidgetsHexValues, final Vector xmlObjectsHexValues, final Vector otherObjectsHexValues) {
        this.databaseObjectsHexValues = databaseObjectsHexValues;
        this.userInterfaceWidgetsHexValues = userInterfaceWidgetsHexValues;
        this.xmlObjectsHexValues = xmlObjectsHexValues;
        this.otherObjectsHexValues = otherObjectsHexValues;
    }
    
    public void setObjectTable(final Vector unFilteredTable) {
        if (this.jTable1.isShowing() && unFilteredTable.size() > 0) {
            this.saveColumnSizes();
        }
        this.unFilteredTable = unFilteredTable;
        this.theModel.setDataVector(this.unFilteredTable, this.names);
        this.jTable1.setAutoResizeMode(0);
        this.jTable1.getTableHeader().setReorderingAllowed(false);
        this.setDefaultTableColumnSize();
        this.jTable1.setEnabled(false);
    }
    
    void jButtonClose_actionPerformed(final ActionEvent actionEvent) {
        this.hide();
        this.monitoring = false;
    }
    
    void toggleDatabaseCheckBox() {
        if (!this.databaseCheckBox.isSelected()) {
            this.toggleAllNodes(this.databaseObjectsNode, true);
            this.rootNodes.setElementAt(this.databaseVector, 0);
            this.databaseCheckBox.setSelected(true);
        }
        else {
            this.toggleAllNodes(this.databaseObjectsNode, false);
            this.rootNodes.setElementAt(this.databaseVector, 0);
            this.databaseCheckBox.setSelected(false);
        }
        this.repaintTree();
    }
    
    void toggleUICheckBox() {
        if (!this.uiCheckBox.isSelected()) {
            this.toggleAllNodes(this.userInterfaceWidgetsNode, true);
            this.uiCheckBox.setSelected(true);
        }
        else {
            this.toggleAllNodes(this.userInterfaceWidgetsNode, false);
            this.uiCheckBox.setSelected(false);
        }
        this.repaintTree();
    }
    
    void toggleXMLCheckBox() {
        if (!this.xmlCheckBox.isSelected()) {
            this.toggleAllNodes(this.xmlObjectsNode, true);
            this.xmlCheckBox.setSelected(true);
        }
        else {
            this.toggleAllNodes(this.xmlObjectsNode, false);
            this.xmlCheckBox.setSelected(false);
        }
        this.repaintTree();
    }
    
    void toggleOtherCheckBox() {
        if (!this.otherCheckBox.isSelected()) {
            this.toggleAllNodes(this.otherObjectsNode, true);
            this.otherCheckBox.setSelected(true);
        }
        else {
            this.toggleAllNodes(this.otherObjectsNode, false);
            this.otherCheckBox.setSelected(false);
        }
        this.repaintTree();
    }
    
    private void toggleAllNodes(final Vector vector, final boolean selected) {
        for (int i = 0; i < vector.size(); ++i) {
            vector.elementAt(i).setSelected(selected);
            this.addOrRemoveJlistItem(vector.elementAt(i).getText(), selected);
        }
    }
    
    private String addHexValues() {
        String str;
        for (str = Long.toHexString(this.addDatabaseVectorData() + this.addUIVectorData() + this.addXmlVectorData() + this.addOtherVectorData()); str.length() != 16; str = "0".concat(str)) {}
        return str.toUpperCase();
    }
    
    private long addDatabaseVectorData() {
        long n = 0L;
        for (int i = 0; i < this.databaseObjectsNode.size(); ++i) {
            if (((CheckBoxNode)this.databaseObjectsNode.elementAt(i)).isSelected()) {
                n += Long.decode("0x".concat(this.databaseObjectsHexValues.elementAt(i).toString()));
            }
        }
        return n;
    }
    
    private long addUIVectorData() {
        long n = 0L;
        for (int i = 0; i < this.userInterfaceWidgetsNode.size(); ++i) {
            if (((CheckBoxNode)this.userInterfaceWidgetsNode.elementAt(i)).isSelected()) {
                n += Long.decode("0x".concat(this.userInterfaceWidgetsHexValues.elementAt(i).toString()));
            }
        }
        return n;
    }
    
    private long addXmlVectorData() {
        long n = 0L;
        for (int i = 0; i < this.xmlObjectsNode.size(); ++i) {
            if (((CheckBoxNode)this.xmlObjectsNode.elementAt(i)).isSelected()) {
                n += Long.decode("0x".concat(this.xmlObjectsHexValues.elementAt(i).toString()));
            }
        }
        return n;
    }
    
    private long addOtherVectorData() {
        long n = 0L;
        for (int i = 0; i < this.otherObjectsNode.size(); ++i) {
            if (((CheckBoxNode)this.otherObjectsNode.elementAt(i)).isSelected()) {
                n += Long.decode("0x".concat(this.otherObjectsHexValues.elementAt(i).toString()));
            }
        }
        return n;
    }
    
    private void repaintTree() {
        this.tree.revalidate();
        this.tree.repaint();
    }
    
    void jButtonStartMonitor_actionPerformed(final ActionEvent actionEvent) {
        this.jList1 = new JList(this.objectListData);
        this.jScrollPane2.remove(this.jList1);
        this.refreshList();
        if (this.jButtonStartMonitor.getText().compareTo("Restart Monitoring") == 0) {
            this.clearObjectTable();
            this.setDefaultTableColumnSize();
        }
        this.jButtonStartMonitor.setText("Restart Monitoring");
        this.application.sendSocket.sendMessage("START-TRACK-OBJ ".concat(this.addHexValues()));
        this.monitoring = true;
        this.jButtonFilter.setEnabled(true);
        this.jButtonShow.setEnabled(true);
        this.startedMonitoring = true;
    }
    
    void jButtonStopMonitor_actionPerformed(final ActionEvent actionEvent) {
        this.jList1 = new JList();
        this.jScrollPane2.remove(this.jList1);
        this.refreshList();
        this.clearObjectTable();
        this.jButtonStartMonitor.setText("Start Monitoring");
        this.application.sendSocket.sendMessage("STOP-TRACK-OBJ");
        this.monitoring = false;
        this.jButtonFilter.setEnabled(false);
        this.jButtonShow.setEnabled(false);
        this.startedMonitoring = false;
    }
    
    void jButtonFilter_actionPerformed(final ActionEvent actionEvent) {
        final int[] selectedIndices = this.jList1.getSelectedIndices();
        if (selectedIndices.length < 1) {
            return;
        }
        this.filteredObjectListData = (Vector)this.unFilteredTable.clone();
        for (int i = 0; i < this.filteredObjectListData.size(); ++i) {
            boolean b = false;
            for (int j = 0; j < selectedIndices.length; ++j) {
                if (this.objectListData.elementAt(selectedIndices[j]).toString().compareTo(this.filteredObjectListData.elementAt(i).elementAt(0)) == 0) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                this.filteredObjectListData.removeElementAt(i);
                --i;
            }
        }
        if (this.jTable1.isShowing()) {
            this.saveColumnSizes();
        }
        this.theModel.setDataVector(this.filteredObjectListData, this.names);
        this.setDefaultTableColumnSize();
    }
    
    void jButtonShow_actionPerformed(final ActionEvent actionEvent) {
        this.refreshTable();
    }
    
    void setCheckboxValue(final String s, final boolean b) {
        if (this.searchForCheckBoxAndToggle(this.userInterfaceWidgetsNode, s, b)) {
            if (!b) {
                this.uiCheckBox.setSelected(false);
            }
            this.repaintTree();
            return;
        }
        if (this.searchForCheckBoxAndToggle(this.databaseObjectsNode, s, b)) {
            if (!b) {
                this.databaseCheckBox.setSelected(false);
            }
            this.repaintTree();
            return;
        }
        if (!this.searchForCheckBoxAndToggle(this.xmlObjectsNode, s, b)) {
            if (this.searchForCheckBoxAndToggle(this.otherObjectsNode, s, b) && !b) {
                this.otherCheckBox.setSelected(false);
            }
            return;
        }
        if (!b) {
            this.xmlCheckBox.setSelected(false);
        }
        this.repaintTree();
    }
    
    private boolean searchForCheckBoxAndToggle(final Vector vector, final String s, final boolean selected) {
        boolean b = false;
        for (int i = 0; i < vector.size(); ++i) {
            if (s.compareTo(vector.elementAt(i).getText()) == 0) {
                vector.elementAt(i).setSelected(selected);
                this.addOrRemoveJlistItem(s, selected);
                b = true;
                break;
            }
        }
        return b;
    }
    
    void addOrRemoveJlistItem(final String s, final boolean b) {
        if (!b) {
            this.objectListData.removeElement(s);
        }
        else {
            boolean b2 = false;
            for (int i = 0; i < this.objectListData.size(); ++i) {
                if (s.compareTo(this.objectListData.elementAt(i).toString()) == 0) {
                    b2 = true;
                    break;
                }
            }
            if (!b2) {
                this.objectListData.addElement(s);
            }
        }
        if (this.monitoring) {
            this.jList1 = new JList(this.objectListData);
            this.jScrollPane2.remove(this.jList1);
            this.refreshList();
        }
        else {
            this.jList1 = new JList();
            this.jScrollPane2.remove(this.jList1);
            this.refreshList();
        }
    }
    
    private void refreshTable() {
        if (this.unFilteredTable != null && this.names != null && this.unFilteredTable.size() > 0) {
            if (this.jTable1.isShowing()) {
                this.saveColumnSizes();
            }
            this.theModel.setDataVector(this.unFilteredTable, this.names);
            this.setDefaultTableColumnSize();
        }
    }
    
    private void setDefaultTableColumnSize() {
        this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(this.objectColumnWidth);
        this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(this.procedureNameColumnWidth);
        this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(this.lineNumberColumnWidth);
        this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(this.otherDataColumnWidth);
    }
    
    void clearObjectTable() {
        if (this.jTable1.isShowing()) {
            this.saveColumnSizes();
        }
        this.unFilteredTable = new Vector();
        this.theModel.setDataVector(this.unFilteredTable, this.names);
        this.setDefaultTableColumnSize();
    }
    
    private void refreshList() {
        this.jScrollPane2.getViewport().add(this.jList1, null);
        this.jScrollPane2.setMaximumSize(new Dimension(260, 132));
        this.jScrollPane2.setMinimumSize(new Dimension(260, 132));
        this.jScrollPane2.setPreferredSize(new Dimension(260, 132));
        this.jList1.revalidate();
        this.jList1.repaint();
    }
    
    protected void processWindowEvent(final WindowEvent e) {
        if (e.getID() == 201) {
            this.hide();
        }
        super.processWindowEvent(e);
    }
    
    private void saveColumnSizes() {
        try {
            this.objectColumnWidth = this.jTable1.getColumnModel().getColumn(0).getWidth();
            this.procedureNameColumnWidth = this.jTable1.getColumnModel().getColumn(1).getWidth();
            this.lineNumberColumnWidth = this.jTable1.getColumnModel().getColumn(2).getWidth();
            this.otherDataColumnWidth = this.jTable1.getColumnModel().getColumn(3).getWidth();
        }
        catch (Exception ex) {}
    }
    
    boolean returnCheckBoxState(final String s) {
        for (int i = 0; i < this.userInterfaceWidgetsNode.size(); ++i) {
            if (s.compareTo(((CheckBoxNode)this.userInterfaceWidgetsNode.elementAt(i)).getText()) == 0) {
                return ((CheckBoxNode)this.userInterfaceWidgetsNode.elementAt(i)).isSelected();
            }
        }
        for (int j = 0; j < this.databaseObjectsNode.size(); ++j) {
            if (s.compareTo(((CheckBoxNode)this.databaseObjectsNode.elementAt(j)).getText()) == 0) {
                return ((CheckBoxNode)this.databaseObjectsNode.elementAt(j)).isSelected();
            }
        }
        for (int k = 0; k < this.xmlObjectsNode.size(); ++k) {
            if (s.compareTo(((CheckBoxNode)this.xmlObjectsNode.elementAt(k)).getText()) == 0) {
                return ((CheckBoxNode)this.xmlObjectsNode.elementAt(k)).isSelected();
            }
        }
        for (int l = 0; l < this.otherObjectsNode.size(); ++l) {
            if (s.compareTo(((CheckBoxNode)this.otherObjectsNode.elementAt(l)).getText()) == 0) {
                return ((CheckBoxNode)this.otherObjectsNode.elementAt(l)).isSelected();
            }
        }
        return false;
    }
    
    boolean startedMonitoring() {
        return this.startedMonitoring;
    }
}
