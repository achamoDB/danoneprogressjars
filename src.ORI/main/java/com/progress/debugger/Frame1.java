// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.net.URL;
import javax.help.HelpSet;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.awt.Insets;
import javax.swing.table.TableColumnModel;
import javax.swing.DefaultCellEditor;
import javax.swing.border.Border;
import java.util.StringTokenizer;
import javax.swing.JViewport;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.event.WindowEvent;
import java.awt.Point;
import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import javax.swing.table.TableModel;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.Timer;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.ListCellRenderer;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;
import javax.swing.JList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.table.TableCellEditor;
import javax.swing.JTextField;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JFrame;

public class Frame1 extends JFrame
{
    HandleMessage meshandle;
    JTable breakpointSelect;
    JTable debugListingFile;
    Vector debugListingFileData;
    DefaultTableModel debuggListingModel;
    int focusLine;
    Vector propertySheetData;
    Vector parametersData;
    Vector variablesData;
    String propertySheetName;
    Sockets sendSocket;
    Sockets recvSocket;
    GetInfoThread getInfoThread;
    boolean makeFocusLineYellow;
    TableRenderer dbglist;
    boolean showLineNumbers;
    boolean propertySheetOpen;
    File currentDirectory;
    PropertySheetDialogBox dataViewDlg;
    Frame1SearchUtilities frameUtl;
    String watchTableNameValueBeforeEdit;
    String watchTableValueValueBeforeEdit;
    int lastSelectedWatchTableRow;
    String variableTableNameValueBeforeEdit;
    String variableTableValueValueBeforeEdit;
    int lastSelectedVariableTableRow;
    String parameterTableNameValueBeforeEdit;
    String parameterTableValueValueBeforeEdit;
    int lastSelectedparameterTableRow;
    int lastFindLine;
    String lastFindWord;
    boolean findDirectionDown;
    boolean findCaseSensitive;
    Vector searchData;
    DebugPvmMessagesDialog debugDlg;
    boolean debugDlgOpen;
    JTextField debugTextField;
    TableCellEditor editor;
    JTextField watchpointTextField;
    JTextField localPaneTextField;
    JLabel statusBar;
    boolean animating;
    boolean stepInAnimate;
    boolean nextAnimate;
    int animationDelayTime;
    private JPanel contentPaneMainWindow;
    private JMenuBar jMenuBar1;
    private JMenu jMenuFile;
    private JMenuItem jMenuFileExit;
    private JMenu jMenuHelp;
    private JMenuItem jMenuHelpAbout;
    private JToolBar jToolBar;
    private JButton fileOpenButton;
    private JButton helpButton;
    private ImageIcon imageOpenFile;
    private ImageIcon imageCloseFile;
    private ImageIcon imageHelp;
    private ImageIcon imageStepIn;
    private ImageIcon imageStepOver;
    private ImageIcon imageContinue;
    private ImageIcon ImageStop;
    private ImageIcon imageInterrupt;
    private ImageIcon imageStepOut;
    private BorderLayout borderLayoutMainWindow;
    private JButton stepInButton;
    private JButton continueButton;
    private JButton stepOverButton;
    private JButton jButtoneFocusLine;
    private JButton stepOutButton;
    private JMenu jMenuDebug;
    private JMenuItem jMenuItemStepOver;
    private JMenuItem jMenuItemStepIn;
    private JMenuItem jMenuItemStepOut;
    private JMenuItem jMenuItemStop;
    private JMenuItem jMenuItemInterupt;
    private JMenuItem jMenuItemAttachToProcess;
    private JSplitPane jSplitPaneMain;
    private JPanel desktop;
    private JSplitPane jSplitPaneLocalDataAndWatch;
    private JPanel jPanelDebugListing;
    private JSplitPane jSplitPaneListingAndStack;
    JScrollPane jScrollPaneDebugListing;
    private BorderLayout borderLayoutDebugListing;
    private JTable watchTable;
    private Vector watchTableData;
    private WatchTableDropTarget watchDropTarget;
    private Vector bufferData;
    private Vector tempTableData;
    private Vector datasetTableData;
    private Vector watchPointNumbers;
    private JMenu jMenuView;
    private JMenu jMenuWindow;
    private JCheckBoxMenuItem jCheckBoxMenuItemDataWindow;
    private JCheckBoxMenuItem jCheckBoxMenuItemStackWindow;
    private JCheckBoxMenuItem jCheckBoxMenuItemWatchWindow;
    private boolean internalWindowsClosed;
    private boolean watchClosed;
    private boolean localDataClosed;
    private BorderLayout borderLayoutDesktop;
    private JMenu jMenuEdit;
    private JMenu jMenuDiag;
    private JMenuItem jMenuItemMonitorDynObj;
    private JMenuItem jMenuItemBreakpoints;
    private JScrollPane jScrollPaneStack;
    private JScrollPane jScrollPaneWatchTable;
    private Vector stack;
    private JList jListStack;
    private JMenuItem jMenuItemOpen;
    private JMenu jMenuSearch;
    private JMenuItem jMenuItemFind;
    private JMenuItem jMenuItemFindNext;
    private JMenuItem jMenuItemFindPrev;
    private JPopupMenu jAddRemoveWatch;
    private JMenuItem jMenuItemAddWatchpoint;
    private JMenuItem jMenuItemRemoveWatchpoint;
    private JPopupMenu jSourcePopupMenu;
    private JPopupMenu jSourceScrollPanePopupMenu;
    private MouseListener mouseListener;
    private MouseListener tableListener;
    private JPopupMenu jPopupMenuBufferAndTempTable;
    private JMenuItem jPopupMenuItemBufferAndTempTableViewFields;
    private MouseListener mouseListenerBufferAndTempTable;
    private MouseListener mouseListenerVariableAndParameter;
    private MouseListener mouseListenerSource;
    private MouseListener mouseListenerSourceScrollPane;
    private MouseListener mouseListenerWatchtable;
    private ListSelectionModel watchTableListSelection;
    private ListSelectionModel parameterTableListSelection;
    private ListSelectionModel variableTableListSelection;
    private Vector rowData;
    private Vector columnData;
    private Vector emptyLabel;
    private JMenuItem jMenuItemCopy;
    private JMenuItem jMenuItemCut;
    private JMenuItem jMenuItemPaste;
    private JMenuItem jMenuItemContinue;
    private JButton stopButton;
    private JButton interruptButton;
    private JMenuItem newItem;
    private Vector fileHistory;
    private JTabbedPane jTabbedPane1;
    private JScrollPane variables;
    private JScrollPane buffers;
    private JScrollPane parameters;
    private JScrollPane tempTables;
    private JTable variablesTable;
    private JTable buffersTable;
    private JTable parametersTable;
    private JTable tempTablesTable;
    private JTable datasetTable;
    private Vector tempRowData;
    private Vector tempColumnNames;
    private int tabbedPaneIndex;
    private int previousStackClickedLine;
    private String currentLabelName;
    private JMenuItem jMenuItemViewPropertySheet;
    private int localDataWatchSplitterLastPosition;
    private int debugListingStackSplitterLastPosition;
    private int mainSplitterLastPosition;
    private JMenuItem jMenuItemPreferences;
    private JMenuItem jMenuItemHelpTopics;
    private JMenuItem jMenuItemDataview;
    private JMenuItem jMenuItemAddWatch;
    private JMenuItem jMenuItemRemoveWatch;
    private ListCellRenderer stackRenderer;
    Font currentFont;
    private JMenu jMenuData;
    private JMenuItem jMenuItemActiveWindow;
    private JMenuItem jMenuItemClipboard;
    private JMenuItem jMenuItemCodeBase;
    private JMenuItem jMenuItemColorTable;
    private JMenuItem jMenuItemCompiler;
    private JMenuItem jMenuItemCurrentWindow;
    private JMenuItem jMenuItemDebugger;
    private JMenuItem jMenuItemDefaultWindow;
    private JMenuItem jMenuItemErrorStatus;
    private JMenuItem jMenuItemFileInfo;
    private JMenuItem jMenuItemFocus;
    private JMenuItem jMenuItemFontTable;
    private JMenuItem jMenuItemLastEvent;
    private JMenuItem jMenuItemProfiler;
    private JMenuItem jMenuItemRcodeInfo;
    private JMenuItem jMenuItemSelf;
    private JMenuItem jMenuItemSession;
    private JMenuItem jMenuItemSourceProc;
    private JMenuItem jMenuItemTargetProc;
    private JMenuItem jMenuItemThisObjectProc;
    private JMenuItem jMenuItemThisProc;
    private JMenuItem jMenuItemWebContext;
    private JMenuItem jMenuItemRun;
    private JMenuItem jMenuItemShowTrans;
    private JMenuItem jMenuItemPvmMsg;
    private JMenuItem jPopupMenuItemBufferAndTempTableAddWatchpoint;
    private JMenuItem jPopupMenuItemBufferAndTempTableCopy;
    private JMenuItem jPopupMenuItemBufferAndTempTableCut;
    private JMenuItem jPopupMenuItemBufferAndTempTablePaste;
    private JMenuItem jMenuItemAddRemoveWatchCopy;
    private JMenuItem jMenuItemAddRemoveWatchCut;
    private JMenuItem jMenuItemAddRemoveWatchPaste;
    private JMenuItem jMenuItemSourcePopupCopy;
    private JMenuItem jMenuItemSourcePopupFind;
    private JMenuItem jMenuItemSourcePopupFindNext;
    private JMenuItem jMenuItemSourcePopupFindPrevious;
    private JMenuItem jMenuItemSourcePopupAddBreak;
    private JMenuItem jMenuItemSourcePopupRemoveBreak;
    private JMenuItem jMenuItemSourcePopupEnableBreak;
    private JMenuItem jMenuItemSourcePopupDisableBreak;
    private JMenuItem jMenuItemSourceScrollPopupFind;
    private JMenuItem jMenuItemSourceScrollPopupFindNext;
    private JMenuItem jMenuItemSourceScrollPopupFindPrevious;
    private boolean debugListingHasFocus;
    private boolean watchTableHasFocus;
    private boolean variablesTableHasFocus;
    private boolean parametersTableHasFocus;
    JMenuItem jMenuItemSourcePopupAddWatch;
    DynamicObjectDialog dynamicDlg;
    boolean dynamicDlgIsMonitoring;
    private String lastDataviewRequest;
    JComboBox propertySheetComboBox;
    boolean getAttrOrGetFieldsRequest;
    boolean debuggerHasControl;
    private String helpUrlPage;
    private JMenuItem jMenuItemRunToLine;
    private JMenuItem jMenuItemStopAnimation;
    private JMenu jMenuStartAnimation;
    private JMenuItem jMenuItemStepAni;
    private JMenuItem jMenuItemNextAni;
    private Timer timer;
    private String helpFileName;
    JButton jButtonStopAnimation;
    JMenuItem jMenuItemLogManager;
    JScrollPane jScrollPaneDataSet;
    String debuggerMode;
    ProDebugLib prodll;
    boolean prodllLoaded;
    boolean pvmMachineDir;
    boolean localHost;
    Vector debuggerListingDirs;
    String sendPropMessage;
    boolean madePVMDebugReady;
    String attachId;
    String operatingSystem;
    boolean menuFileExitSelected;
    boolean optionDetachSelected;
    JMenuItem jMenuItemWatchpoint;
    String processid;
    String port1;
    String hostName;
    String port2;
    
    public Frame1() {
        this.breakpointSelect = new JTable();
        this.debugListingFile = new JTable();
        this.debugListingFileData = new Vector();
        this.propertySheetData = new Vector();
        this.parametersData = new Vector();
        this.variablesData = new Vector();
        this.sendSocket = null;
        this.recvSocket = null;
        this.getInfoThread = null;
        this.makeFocusLineYellow = false;
        this.dbglist = new TableRenderer(this);
        this.showLineNumbers = true;
        this.propertySheetOpen = false;
        this.frameUtl = new Frame1SearchUtilities();
        this.watchTableNameValueBeforeEdit = " ";
        this.watchTableValueValueBeforeEdit = " ";
        this.lastSelectedWatchTableRow = -1;
        this.variableTableNameValueBeforeEdit = " ";
        this.variableTableValueValueBeforeEdit = " ";
        this.lastSelectedVariableTableRow = -1;
        this.parameterTableNameValueBeforeEdit = " ";
        this.parameterTableValueValueBeforeEdit = " ";
        this.lastSelectedparameterTableRow = -1;
        this.lastFindLine = 1;
        this.lastFindWord = "";
        this.findDirectionDown = true;
        this.findCaseSensitive = false;
        this.debugDlgOpen = false;
        this.debugTextField = new JTextField();
        this.editor = new SingleClickCellEditor(this.debugTextField);
        this.watchpointTextField = new JTextField();
        this.localPaneTextField = new JTextField();
        this.statusBar = new JLabel();
        this.animating = false;
        this.stepInAnimate = false;
        this.nextAnimate = false;
        this.animationDelayTime = 3;
        this.jMenuBar1 = new JMenuBar();
        this.jMenuFile = new JMenu();
        this.jMenuFileExit = new JMenuItem();
        this.jMenuHelp = new JMenu();
        this.jMenuHelpAbout = new JMenuItem();
        this.jToolBar = new JToolBar();
        this.fileOpenButton = new JButton();
        this.helpButton = new JButton();
        this.borderLayoutMainWindow = new BorderLayout();
        this.stepInButton = new JButton();
        this.continueButton = new JButton();
        this.stepOverButton = new JButton();
        this.jButtoneFocusLine = new JButton();
        this.stepOutButton = new JButton();
        this.jMenuDebug = new JMenu();
        this.jMenuItemStepOver = new JMenuItem();
        this.jMenuItemStepIn = new JMenuItem();
        this.jMenuItemStepOut = new JMenuItem();
        this.jMenuItemStop = new JMenuItem();
        this.jMenuItemInterupt = new JMenuItem();
        this.jMenuItemAttachToProcess = new JMenuItem();
        this.jSplitPaneMain = new JSplitPane();
        this.desktop = new JPanel();
        this.jSplitPaneLocalDataAndWatch = new JSplitPane();
        this.jPanelDebugListing = new JPanel();
        this.jSplitPaneListingAndStack = new JSplitPane();
        this.jScrollPaneDebugListing = new JScrollPane();
        this.borderLayoutDebugListing = new BorderLayout();
        this.jMenuView = new JMenu();
        this.jMenuWindow = new JMenu();
        this.jCheckBoxMenuItemDataWindow = new JCheckBoxMenuItem();
        this.jCheckBoxMenuItemStackWindow = new JCheckBoxMenuItem();
        this.jCheckBoxMenuItemWatchWindow = new JCheckBoxMenuItem();
        this.internalWindowsClosed = false;
        this.watchClosed = false;
        this.localDataClosed = false;
        this.borderLayoutDesktop = new BorderLayout();
        this.jMenuEdit = new JMenu();
        this.jMenuDiag = new JMenu("Diagnostics");
        this.jMenuItemMonitorDynObj = new JMenuItem("Monitor Dynamic Objects...");
        this.jMenuItemBreakpoints = new JMenuItem();
        this.jScrollPaneStack = new JScrollPane();
        this.jScrollPaneWatchTable = new JScrollPane();
        this.stack = new Vector();
        this.jListStack = new JList(this.stack);
        this.jMenuItemOpen = new JMenuItem();
        this.jMenuSearch = new JMenu();
        this.jMenuItemFind = new JMenuItem();
        this.jMenuItemFindNext = new JMenuItem();
        this.jMenuItemFindPrev = new JMenuItem();
        this.jAddRemoveWatch = new JPopupMenu();
        this.jMenuItemAddWatchpoint = new JMenuItem();
        this.jMenuItemRemoveWatchpoint = new JMenuItem();
        this.jSourcePopupMenu = new JPopupMenu();
        this.jSourceScrollPanePopupMenu = new JPopupMenu();
        this.jPopupMenuBufferAndTempTable = new JPopupMenu();
        this.jPopupMenuItemBufferAndTempTableViewFields = new JMenuItem("Dataview");
        this.rowData = new Vector();
        this.columnData = new Vector();
        this.emptyLabel = new Vector();
        this.jMenuItemCopy = new JMenuItem();
        this.jMenuItemCut = new JMenuItem();
        this.jMenuItemPaste = new JMenuItem();
        this.jMenuItemContinue = new JMenuItem();
        this.stopButton = new JButton();
        this.interruptButton = new JButton();
        this.newItem = new JMenuItem();
        this.fileHistory = new Vector();
        this.jTabbedPane1 = new JTabbedPane();
        this.variables = new JScrollPane();
        this.buffers = new JScrollPane();
        this.parameters = new JScrollPane();
        this.tempTables = new JScrollPane();
        this.variablesTable = new JTable();
        this.buffersTable = new JTable();
        this.parametersTable = new JTable();
        this.tempTablesTable = new JTable();
        this.datasetTable = new JTable();
        this.tabbedPaneIndex = 0;
        this.previousStackClickedLine = -1;
        this.jMenuItemViewPropertySheet = new JMenuItem();
        this.localDataWatchSplitterLastPosition = 0;
        this.debugListingStackSplitterLastPosition = 0;
        this.mainSplitterLastPosition = 0;
        this.jMenuItemPreferences = new JMenuItem();
        this.jMenuItemHelpTopics = new JMenuItem();
        this.jMenuItemDataview = new JMenuItem();
        this.jMenuItemAddWatch = new JMenuItem();
        this.jMenuItemRemoveWatch = new JMenuItem();
        this.stackRenderer = new StackListCellRenderer();
        this.currentFont = new Font("dialoginput", 0, 12);
        this.jMenuData = new JMenu();
        this.jMenuItemActiveWindow = new JMenuItem();
        this.jMenuItemClipboard = new JMenuItem();
        this.jMenuItemCodeBase = new JMenuItem();
        this.jMenuItemColorTable = new JMenuItem();
        this.jMenuItemCompiler = new JMenuItem();
        this.jMenuItemCurrentWindow = new JMenuItem();
        this.jMenuItemDebugger = new JMenuItem();
        this.jMenuItemDefaultWindow = new JMenuItem();
        this.jMenuItemErrorStatus = new JMenuItem();
        this.jMenuItemFileInfo = new JMenuItem();
        this.jMenuItemFocus = new JMenuItem();
        this.jMenuItemFontTable = new JMenuItem();
        this.jMenuItemLastEvent = new JMenuItem();
        this.jMenuItemProfiler = new JMenuItem();
        this.jMenuItemRcodeInfo = new JMenuItem();
        this.jMenuItemSelf = new JMenuItem();
        this.jMenuItemSession = new JMenuItem();
        this.jMenuItemSourceProc = new JMenuItem();
        this.jMenuItemTargetProc = new JMenuItem();
        this.jMenuItemThisObjectProc = new JMenuItem();
        this.jMenuItemThisProc = new JMenuItem();
        this.jMenuItemWebContext = new JMenuItem();
        this.jMenuItemRun = new JMenuItem();
        this.jMenuItemShowTrans = new JMenuItem();
        this.jMenuItemPvmMsg = new JMenuItem();
        this.jPopupMenuItemBufferAndTempTableAddWatchpoint = new JMenuItem();
        this.jPopupMenuItemBufferAndTempTableCopy = new JMenuItem();
        this.jPopupMenuItemBufferAndTempTableCut = new JMenuItem();
        this.jPopupMenuItemBufferAndTempTablePaste = new JMenuItem();
        this.jMenuItemAddRemoveWatchCopy = new JMenuItem();
        this.jMenuItemAddRemoveWatchCut = new JMenuItem();
        this.jMenuItemAddRemoveWatchPaste = new JMenuItem();
        this.jMenuItemSourcePopupCopy = new JMenuItem();
        this.jMenuItemSourcePopupFind = new JMenuItem();
        this.jMenuItemSourcePopupFindNext = new JMenuItem();
        this.jMenuItemSourcePopupFindPrevious = new JMenuItem();
        this.jMenuItemSourcePopupAddBreak = new JMenuItem();
        this.jMenuItemSourcePopupRemoveBreak = new JMenuItem();
        this.jMenuItemSourcePopupEnableBreak = new JMenuItem();
        this.jMenuItemSourcePopupDisableBreak = new JMenuItem();
        this.jMenuItemSourceScrollPopupFind = new JMenuItem("Find...");
        this.jMenuItemSourceScrollPopupFindNext = new JMenuItem("Find Next");
        this.jMenuItemSourceScrollPopupFindPrevious = new JMenuItem("Find Previous");
        this.debugListingHasFocus = true;
        this.watchTableHasFocus = false;
        this.variablesTableHasFocus = false;
        this.parametersTableHasFocus = false;
        this.jMenuItemSourcePopupAddWatch = new JMenuItem();
        this.dynamicDlgIsMonitoring = false;
        this.getAttrOrGetFieldsRequest = false;
        this.debuggerHasControl = false;
        this.helpUrlPage = "http://www.progress.com";
        this.jMenuItemRunToLine = new JMenuItem();
        this.jMenuItemStopAnimation = new JMenuItem();
        this.jMenuStartAnimation = new JMenu();
        this.jMenuItemStepAni = new JMenuItem();
        this.jMenuItemNextAni = new JMenuItem();
        this.helpFileName = "/prohelp/html/dbgrhelp/dbgrhelp.hs";
        this.jButtonStopAnimation = new JButton();
        this.jMenuItemLogManager = new JMenuItem();
        this.jScrollPaneDataSet = new JScrollPane();
        this.pvmMachineDir = false;
        this.localHost = true;
        this.debuggerListingDirs = new Vector();
        this.sendPropMessage = " ";
        this.madePVMDebugReady = false;
        this.attachId = " ";
        this.menuFileExitSelected = false;
        this.optionDetachSelected = false;
        this.jMenuItemWatchpoint = new JMenuItem();
        this.processid = "";
        this.port1 = "0";
        this.hostName = "localhost";
        this.port2 = "0";
        try {
            this.enableEvents(64L);
            this.operatingSystem = System.getProperty("os.name");
            try {
                this.debuggerMode = System.getProperty("Mode");
                this.jbInit();
                if (this.debuggerMode.compareTo("1") == 0 || this.debuggerMode.compareTo("2") == 0) {
                    this.standAloneDisableButtonsAndMenus();
                }
                if (this.debuggerMode.compareTo("2") == 0) {
                    this.jMenuItemRun.setEnabled(false);
                    this.jMenuItemOpen.setEnabled(false);
                    this.fileOpenButton.setEnabled(false);
                    this.setVisible(true);
                    this.prodll = new ProDebugLib();
                    this.prodllLoaded = this.prodll.loadStatus();
                }
            }
            catch (Exception ex2) {
                this.debuggerMode = "0";
                this.jbInit();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void jbInit() throws Exception {
        this.setFont(new Font("dialog", 0, 12));
        this.setTitle("OpenEdge Debugger");
        this.jMenuHelp.setMnemonic('H');
        this.jMenuDebug.setMnemonic('D');
        this.jAddRemoveWatch.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
                Frame1.this.jAddRemoveWatch_popupMenuWillBecomeVisible(popupMenuEvent);
            }
            
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent popupMenuEvent) {
            }
            
            public void popupMenuCanceled(final PopupMenuEvent popupMenuEvent) {
            }
        });
        this.jAddRemoveWatch.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
                Frame1.this.jAddRemoveWatch_popupMenuWillBecomeVisible(popupMenuEvent);
            }
            
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent popupMenuEvent) {
            }
            
            public void popupMenuCanceled(final PopupMenuEvent popupMenuEvent) {
            }
        });
        this.jMenuItemSourcePopupCopy.setText("Copy");
        this.jMenuItemSourcePopupCopy.setAccelerator(KeyStroke.getKeyStroke(67, 2, false));
        this.jMenuItemSourcePopupCopy.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSourcePopupCopy_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupFind.setText("Find...");
        this.jMenuItemSourcePopupFind.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFind_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourceScrollPopupFind.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFind_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupFindNext.setText("Find Next");
        this.jMenuItemSourcePopupFindNext.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFindNext_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourceScrollPopupFindNext.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFindNext_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupFindPrevious.setText("Find Previous");
        this.jMenuItemSourcePopupFindPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFindPrev_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourceScrollPopupFindPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFindPrev_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupAddBreak.setText("Add Breakpoint");
        this.jMenuItemSourcePopupAddBreak.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSourcePopupAddBreak_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupRemoveBreak.setText("Remove Breakpoint");
        this.jMenuItemSourcePopupRemoveBreak.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSourcePopupRemoveBreak_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupEnableBreak.setText("Enable Breakpoint");
        this.jMenuItemSourcePopupEnableBreak.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSourcePopupEnableBreak_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupDisableBreak.setText("Disable Breakpoint");
        this.jMenuItemSourcePopupDisableBreak.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSourcePopupDisableBreak_actionPerformed(actionEvent);
            }
        });
        this.jPopupMenuItemBufferAndTempTableAddWatchpoint.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jPopupMenuItemBufferAndTempTableAddWatchpoint_actionPerformed(actionEvent);
            }
        });
        this.jSourcePopupMenu.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
                Frame1.this.jSourcePopupMenu_popupMenuWillBecomeVisible(popupMenuEvent);
            }
            
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent popupMenuEvent) {
            }
            
            public void popupMenuCanceled(final PopupMenuEvent popupMenuEvent) {
            }
        });
        this.jPopupMenuItemBufferAndTempTableCopy.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jPopupMenuItemBufferAndTempTableCopy_actionPerformed(actionEvent);
            }
        });
        this.jPopupMenuItemBufferAndTempTableCut.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jPopupMenuItemBufferAndTempTableCut_actionPerformed(actionEvent);
            }
        });
        this.jPopupMenuItemBufferAndTempTablePaste.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jPopupMenuItemBufferAndTempTablePaste_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemAddRemoveWatchCopy.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemAddRemoveWatchCopy_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemAddRemoveWatchCut.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemAddRemoveWatchCut_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemAddRemoveWatchPaste.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemAddRemoveWatchPaste_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemAddRemoveWatchCopy.setAccelerator(KeyStroke.getKeyStroke(67, 2, false));
        this.jMenuItemAddRemoveWatchPaste.setAccelerator(KeyStroke.getKeyStroke(86, 2, false));
        this.jMenuItemAddRemoveWatchCut.setAccelerator(KeyStroke.getKeyStroke(88, 2, false));
        this.jPopupMenuItemBufferAndTempTableCopy.setAccelerator(KeyStroke.getKeyStroke(67, 2, false));
        this.jPopupMenuItemBufferAndTempTableCut.setAccelerator(KeyStroke.getKeyStroke(88, 2, false));
        this.jPopupMenuItemBufferAndTempTablePaste.setAccelerator(KeyStroke.getKeyStroke(86, 2, false));
        this.debugListingFile.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.debugListingFile_focusGained(focusEvent);
            }
        });
        this.parametersTable.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.parametersTable_focusGained(focusEvent);
            }
        });
        this.buffersTable.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.buffersTable_focusGained(focusEvent);
            }
        });
        this.tempTablesTable.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.tempTablesTable_focusGained(focusEvent);
            }
        });
        this.jMenuItemMonitorDynObj.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemMonitorDynObj_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourcePopupAddWatch.setText("Add Watch");
        this.jMenuItemSourcePopupAddWatch.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSourcePopupAddWatch_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemMonitorDynObj.setMnemonic('M');
        this.jMenuItemRunToLine.setText("Run to Line");
        this.jMenuItemRunToLine.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemRunToLine_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemStopAnimation.setText("Stop Animation");
        this.jMenuItemStopAnimation.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemStopAnimation_actionPerformed(actionEvent);
            }
        });
        this.jMenuStartAnimation.setText("Start Animation");
        this.jMenuItemStepAni.setMnemonic('I');
        this.jMenuItemStepAni.setText("Step Into");
        this.jMenuItemStepAni.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemStepAni_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemNextAni.setMnemonic('O');
        this.jMenuItemNextAni.setText("Step Over");
        this.jMenuItemNextAni.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemNextAni_actionPerformed(actionEvent);
            }
        });
        this.jButtonStopAnimation.setActionCommand("jButtonStopAnimation");
        this.jButtonStopAnimation.setText("Stop Animation");
        this.jButtonStopAnimation.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jButtonStopAnimation_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemLogManager.setText("LOG-MANAGER");
        this.jMenuItemLogManager.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemLogManager_actionPerformed(actionEvent);
            }
        });
        this.jToolBar.setFont(new Font("Dialog", 0, 12));
        this.jMenuItemWatchpoint.setEnabled(false);
        this.jMenuItemWatchpoint.setText("Add Watchpoint");
        this.jMenuItemWatchpoint.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemWatchpoint_actionPerformed(actionEvent);
            }
        });
        this.jAddRemoveWatch.add(this.jMenuItemAddRemoveWatchCopy);
        this.jAddRemoveWatch.add(this.jMenuItemAddRemoveWatchCut);
        this.jAddRemoveWatch.add(this.jMenuItemAddRemoveWatchPaste);
        this.jAddRemoveWatch.addSeparator();
        this.jMenuItemAddRemoveWatchCopy.setText("Copy");
        this.jMenuItemAddRemoveWatchCut.setText("Cut");
        this.jMenuItemAddRemoveWatchPaste.setText("Paste");
        this.jPopupMenuBufferAndTempTable.add(this.jPopupMenuItemBufferAndTempTableCopy);
        this.jPopupMenuBufferAndTempTable.add(this.jPopupMenuItemBufferAndTempTableCut);
        this.jPopupMenuBufferAndTempTable.add(this.jPopupMenuItemBufferAndTempTablePaste);
        this.jPopupMenuBufferAndTempTable.addSeparator();
        this.jMenuItemRun.setAccelerator(KeyStroke.getKeyStroke(113, 0, false));
        this.fileHistory.clear();
        this.setFont(new Font("dialog", 0, 12));
        this.initializeIcons();
        this.initializeLocalDataWindowAndWatchWindow();
        (this.contentPaneMainWindow = (JPanel)this.getContentPane()).setLayout(this.borderLayoutMainWindow);
        this.setSize(new Dimension(770, 706));
        this.statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        this.statusBar.setText(" ");
        this.initializeMenuItems();
        this.initializeButtons();
        this.initializeActionListeners();
        this.addItemsToMenusAndToolbar();
        this.contentPaneMainWindow.setBorder(BorderFactory.createEtchedBorder());
        this.contentPaneMainWindow.setMinimumSize(new Dimension(640, 480));
        this.contentPaneMainWindow.setPreferredSize(new Dimension(800, 600));
        this.initializePanes();
        this.jToolBar.setRequestFocusEnabled(false);
        this.jToolBar.setVerifyInputWhenFocusTarget(false);
        this.jToolBar.setFloatable(false);
        this.jListStack.setSelectionMode(0);
        this.jTabbedPane1.setTabPlacement(3);
        this.jTabbedPane1.setMinimumSize(new Dimension(40, 40));
        this.jTabbedPane1.setPreferredSize(new Dimension(40, 40));
        this.setJMenuBar(this.jMenuBar1);
        this.addInitialContentToPanes();
        this.watchTable.setEnabled(true);
        this.mouseListenerWatchtable = new JPopupMenuShower(this.jAddRemoveWatch, this.watchTable, true, false);
        this.watchTable.addMouseListener(this.mouseListenerWatchtable);
        this.emptyLabel.addElement("");
        this.rowData.addElement(" ");
        this.columnData.addElement(" ");
        this.rowData.addElement(" ");
        this.columnData.addElement(" ");
        final Vector debugListingFileData = new Vector();
        new Vector<String>().addElement(" ");
        this.debugListingFileData = debugListingFileData;
        this.debuggListingModel = new DefaultTableModel(this.debugListingFileData, this.emptyLabel);
        this.debugListingFile = new JTable(this.debuggListingModel);
        this.setListingFileDefaultSettings();
        this.debugListingFile.setShowHorizontalLines(false);
        this.debugListingFile.setShowVerticalLines(false);
        this.debugListingFile.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.debugListingFile_focusGained(focusEvent);
            }
        });
        this.jPopupMenuItemBufferAndTempTableViewFields.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jPopupMenuItemBufferAndTempTableViewFields_actionPerformed(actionEvent);
            }
        });
        this.jMenuData.setText("Data");
        this.jMenuItemActiveWindow.setText("ACTIVE-WINDOW");
        this.jMenuItemClipboard.setText("CLIPBOARD");
        this.jMenuItemCodeBase.setText("CODEBASE-LOCATOR");
        this.jMenuItemColorTable.setText("COLOR-TABLE");
        this.jMenuItemCompiler.setText("COMPILER");
        this.jMenuItemCurrentWindow.setText("CURRENT-WINDOW");
        this.jMenuItemDebugger.setText("DEBUGGER");
        this.jMenuItemDefaultWindow.setText("DEFAULT-WINDOW");
        this.jMenuItemErrorStatus.setText("ERROR-STATUS");
        this.jMenuItemFileInfo.setText("FILE-INFORMATION");
        this.jMenuItemFocus.setText("FOCUS");
        this.jMenuItemFontTable.setText("FONT-TABLE");
        this.jMenuItemLastEvent.setText("LAST-EVENT");
        this.jMenuItemProfiler.setText("PROFILER");
        this.jMenuItemRcodeInfo.setText("RCODE-INFORMATION");
        this.jMenuItemSelf.setText("SELF");
        this.jMenuItemSession.setText("SESSION");
        this.jMenuItemSourceProc.setText("SOURCE-PROCEDURE");
        this.jMenuItemTargetProc.setText("TARGET-PROCEDURE");
        this.jMenuItemThisObjectProc.setText("THIS-OBJECT");
        this.jMenuItemThisProc.setText("THIS-PROCEDURE");
        this.jMenuItemWebContext.setText("WEB-CONTEXT");
        this.jMenuItemRun.setText("Run");
        this.jMenuItemShowTrans.setText("Show Transaction");
        this.jMenuItemShowTrans.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemShowTrans_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemPvmMsg.setText("PVM Messages");
        this.jMenuItemPvmMsg.setAccelerator(KeyStroke.getKeyStroke(120, 11, false));
        this.jMenuItemPvmMsg.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemPvmMsg_actionPerformed(actionEvent);
            }
        });
        this.jPopupMenuItemBufferAndTempTableAddWatchpoint.setText("Add Watch");
        this.jPopupMenuItemBufferAndTempTableCopy.setText("Copy");
        this.jPopupMenuItemBufferAndTempTableCut.setText("Cut");
        this.jPopupMenuItemBufferAndTempTablePaste.setText("Paste");
        this.jScrollPaneDebugListing.getViewport().add(this.debugListingFile);
        this.buffers.getViewport().add(this.buffersTable, null);
        this.parameters.getViewport().add(this.parametersTable, null);
        this.tempTables.getViewport().add(this.tempTablesTable, null);
        this.jScrollPaneDataSet.getViewport().add(this.datasetTable, null);
        this.jScrollPaneDebugListing.setCorner("UPPER_LEFT_CORNER", this.breakpointSelect.getTableHeader());
        this.jTabbedPane1.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent changeEvent) {
                Frame1.this.jTabbedPane1_stateChanged(changeEvent);
            }
        });
        this.disableButtonsAndMenus();
        this.loadPreferences();
        if (this.debuggerMode.compareTo("2") != 0) {
            this.initializeSocket();
        }
        this.jMenuData.add(this.jMenuItemActiveWindow);
        this.jMenuData.add(this.jMenuItemClipboard);
        this.jMenuData.add(this.jMenuItemCodeBase);
        this.jMenuData.add(this.jMenuItemColorTable);
        this.jMenuData.add(this.jMenuItemCompiler);
        this.jMenuData.add(this.jMenuItemCurrentWindow);
        this.jMenuData.add(this.jMenuItemDebugger);
        this.jMenuData.add(this.jMenuItemDefaultWindow);
        this.jMenuData.add(this.jMenuItemErrorStatus);
        this.jMenuData.add(this.jMenuItemFileInfo);
        this.jMenuData.add(this.jMenuItemFocus);
        this.jMenuData.add(this.jMenuItemFontTable);
        this.jMenuData.add(this.jMenuItemLastEvent);
        this.jMenuData.add(this.jMenuItemLogManager);
        this.jMenuData.add(this.jMenuItemProfiler);
        this.jMenuData.add(this.jMenuItemRcodeInfo);
        this.jMenuData.add(this.jMenuItemSelf);
        this.jMenuData.add(this.jMenuItemSession);
        this.jMenuData.add(this.jMenuItemSourceProc);
        this.jMenuData.add(this.jMenuItemTargetProc);
        this.jMenuData.add(this.jMenuItemThisObjectProc);
        this.jMenuData.add(this.jMenuItemThisProc);
        this.jMenuData.add(this.jMenuItemWebContext);
        this.jMenuHelp.add(this.jMenuItemPvmMsg);
        this.jMenuItemPvmMsg.setVisible(false);
        this.jPopupMenuBufferAndTempTable.add(this.jPopupMenuItemBufferAndTempTableAddWatchpoint);
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupCopy);
        this.jSourcePopupMenu.addSeparator();
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupFind);
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupFindNext);
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupFindPrevious);
        this.jSourcePopupMenu.addSeparator();
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupAddBreak);
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupRemoveBreak);
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupEnableBreak);
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupDisableBreak);
        this.jSourcePopupMenu.addSeparator();
        this.jSourcePopupMenu.add(this.jMenuItemSourcePopupAddWatch);
        this.jSourcePopupMenu.add(this.jMenuItemRunToLine);
        this.jSourceScrollPanePopupMenu.add(this.jMenuItemSourceScrollPopupFind);
        this.jSourceScrollPanePopupMenu.add(this.jMenuItemSourceScrollPopupFindNext);
        this.jSourceScrollPanePopupMenu.add(this.jMenuItemSourceScrollPopupFindPrevious);
        this.watchTable.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.watchTable_focusGained(focusEvent);
            }
        });
        this.jListStack.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.jListStack_focusGained(focusEvent);
            }
        });
        this.jMenuItemRun.setMnemonic('R');
        this.jMenuItemShowTrans.setMnemonic('H');
        this.jMenuData.setMnemonic('A');
        this.timer = new Timer(this.animationDelayTime * 1000, new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.sendAnimationCommand(actionEvent);
            }
        });
        this.jButtonStopAnimation.setVisible(false);
        this.jTabbedPane1.setFont(new Font("Dialog", 0, 12));
        this.jMenuStartAnimation.setMnemonic('A');
        this.jMenuItemStopAnimation.setMnemonic('P');
        try {
            String helpUrlPage = System.getProperty("Install.Dir") + this.helpFileName;
            if (System.getProperty("os.name").startsWith("Windows")) {
                helpUrlPage = helpUrlPage.replace('/', '\\');
            }
            this.setHelpUrlPage(helpUrlPage);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void initializeMenuItems() {
        this.jMenuFile.setMnemonic('F');
        this.jMenuFile.setText("File");
        this.jMenuFileExit.setText("Exit");
        this.jMenuHelp.setText("Help");
        this.jMenuHelpAbout.setActionCommand("About");
        this.jMenuHelpAbout.setMnemonic('A');
        this.jMenuHelpAbout.setText("About...");
        this.jMenuDebug.setText("Debug");
        this.jMenuItemStepOver.setText("Step Over");
        this.jMenuItemStepOver.setAccelerator(KeyStroke.getKeyStroke(117, 0, true));
        this.jMenuItemStepIn.setText("Step Into");
        this.jMenuItemStepIn.setAccelerator(KeyStroke.getKeyStroke(118, 0, false));
        this.jMenuItemStepOut.setText("Step Out");
        this.jMenuItemStepOut.setAccelerator(KeyStroke.getKeyStroke(118, 1, false));
        this.jMenuItemStop.setText("Stop");
        this.jMenuItemInterupt.setMnemonic('N');
        this.jMenuItemInterupt.setText("Interrupt");
        this.jMenuItemInterupt.setAccelerator(KeyStroke.getKeyStroke(3, 2, false));
        this.jMenuItemInterupt.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.interruptButton_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemAttachToProcess.setText("Attach To Process...");
        this.jMenuItemAttachToProcess.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemAttachToProcess_actionPerformed(actionEvent);
            }
        });
        this.jMenuView.setMnemonic('V');
        this.jMenuView.setText("View");
        this.jMenuWindow.setText("Window");
        this.jCheckBoxMenuItemDataWindow.setMnemonic('V');
        this.jCheckBoxMenuItemDataWindow.setSelected(true);
        this.jCheckBoxMenuItemDataWindow.setText("Variables");
        this.jCheckBoxMenuItemStackWindow.setMnemonic('S');
        this.jCheckBoxMenuItemStackWindow.setSelected(true);
        this.jCheckBoxMenuItemStackWindow.setText("Stack Trace");
        this.jCheckBoxMenuItemWatchWindow.setMnemonic('W');
        this.jCheckBoxMenuItemWatchWindow.setSelected(true);
        this.jCheckBoxMenuItemWatchWindow.setText("Watches");
        this.jMenuEdit.setMnemonic('E');
        this.jMenuEdit.setText("Edit");
        this.jMenuEdit.addMenuListener(new MenuListener() {
            public void menuSelected(final MenuEvent menuEvent) {
                Frame1.this.jMenuEdit_menuSelected(menuEvent);
            }
            
            public void menuDeselected(final MenuEvent menuEvent) {
            }
            
            public void menuCanceled(final MenuEvent menuEvent) {
            }
        });
        this.jMenuItemBreakpoints.setText("Breakpoints...");
        this.jMenuItemBreakpoints.setAccelerator(KeyStroke.getKeyStroke(66, 2, false));
        this.jMenuItemOpen.setText("Open...");
        this.jMenuItemOpen.setAccelerator(KeyStroke.getKeyStroke(114, 0, false));
        this.jMenuItemOpen.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemOpen_actionPerformed(actionEvent);
            }
        });
        this.jMenuSearch.setText("Search");
        this.jMenuItemFind.setMnemonic('F');
        this.jMenuItemFind.setText("Find...");
        this.jMenuItemFind.setAccelerator(KeyStroke.getKeyStroke(70, 2, false));
        this.jMenuItemFind.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFind_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemFindNext.setMnemonic('N');
        this.jMenuItemFindNext.setText("Find Next");
        this.jMenuItemFindNext.setAccelerator(KeyStroke.getKeyStroke(120, 0, false));
        this.jMenuItemFindNext.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFindNext_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemFindPrev.setMnemonic('P');
        this.jMenuItemFindPrev.setText("Find Prev");
        this.jMenuItemFindPrev.setAccelerator(KeyStroke.getKeyStroke(120, 1, false));
        this.jMenuItemFindPrev.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFindPrev_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemAddWatchpoint.setText("Add Watch");
        this.jMenuItemRemoveWatchpoint.setText("Remove Watch");
        this.jMenuItemCopy.setText("Copy");
        this.jMenuItemCopy.setAccelerator(KeyStroke.getKeyStroke(67, 2, false));
        this.jMenuItemCopy.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemCopy_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemCut.setText("Cut");
        this.jMenuItemCut.setAccelerator(KeyStroke.getKeyStroke(88, 2, false));
        this.jMenuItemCut.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemCut_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemPaste.setText("Paste");
        this.jMenuItemPaste.setAccelerator(KeyStroke.getKeyStroke(86, 2, false));
        this.jMenuItemPaste.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemPaste_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemContinue.setText("Continue");
        this.jMenuItemContinue.setAccelerator(KeyStroke.getKeyStroke(116, 0, false));
        this.jMenuItemViewPropertySheet.setText("Dataview");
        this.jMenuItemDataview.setText("Dataview...");
        this.jMenuItemDataview.setAccelerator(KeyStroke.getKeyStroke(68, 2, false));
        this.jMenuItemDataview.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemDataview_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemAddWatch.setText("Add Watch...");
        this.jMenuItemRemoveWatch.setText("Remove Watch");
        this.jMenuItemPreferences.setMnemonic('E');
        this.jMenuItemPreferences.setText("Preferences...");
        this.jMenuItemHelpTopics.setText("Help Topics");
        this.jMenuItemHelpTopics.setAccelerator(KeyStroke.getKeyStroke(112, 0, false));
        this.jMenuItemHelpTopics.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.helpButton_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemOpen.setMnemonic('O');
        this.jMenuFileExit.setMnemonic('X');
        this.jMenuItemCut.setMnemonic('T');
        this.jMenuItemCopy.setMnemonic('C');
        this.jMenuItemPaste.setMnemonic('P');
        this.jMenuItemBreakpoints.setMnemonic('B');
        this.jMenuSearch.setMnemonic('S');
        this.jMenuItemStepIn.setMnemonic('I');
        this.jMenuItemStepOver.setMnemonic('O');
        this.jMenuItemContinue.setMnemonic('C');
        this.jMenuItemStop.setMnemonic('S');
        this.jMenuItemDataview.setMnemonic('D');
        this.jMenuItemAddWatch.setMnemonic('W');
        this.jMenuItemRemoveWatch.setMnemonic('E');
        this.jMenuWindow.setMnemonic('W');
        this.jMenuItemHelpTopics.setMnemonic('H');
        this.jMenuItemStepOut.setMnemonic('T');
        this.jMenuItemAttachToProcess.setMnemonic('H');
    }
    
    private void addItemsToMenusAndToolbar() {
        this.jToolBar.add(this.fileOpenButton);
        this.jToolBar.add(this.stepInButton, null);
        this.jToolBar.add(this.stepOverButton, null);
        this.jToolBar.add(this.stepOutButton, null);
        this.jToolBar.add(this.continueButton, null);
        this.jToolBar.add(this.stopButton, null);
        this.jToolBar.add(this.interruptButton, null);
        this.jToolBar.add(this.helpButton);
        this.jToolBar.add(this.jButtonStopAnimation, null);
        this.jMenuFile.add(this.jMenuItemOpen);
        this.jMenuFile.addSeparator();
        this.jMenuFile.add(this.jMenuFileExit);
        this.jMenuHelp.add(this.jMenuItemHelpTopics);
        this.jMenuHelp.add(this.jMenuHelpAbout);
        this.jMenuBar1.add(this.jMenuFile);
        this.jMenuBar1.add(this.jMenuEdit);
        this.jMenuBar1.add(this.jMenuSearch);
        this.jMenuBar1.add(this.jMenuView);
        this.jMenuBar1.add(this.jMenuDebug);
        this.jMenuBar1.add(this.jMenuDiag);
        this.jMenuBar1.add(this.jMenuData);
        this.jMenuBar1.add(this.jMenuWindow);
        this.jMenuBar1.add(this.jMenuHelp);
        this.jMenuDebug.add(this.jMenuItemRun);
        this.jMenuDebug.add(this.jMenuItemContinue);
        this.jMenuDebug.add(this.jMenuItemStepIn);
        this.jMenuDebug.add(this.jMenuItemStepOver);
        this.jMenuDebug.add(this.jMenuItemStepOut);
        this.jMenuDebug.addSeparator();
        this.jMenuDebug.add(this.jMenuItemStop);
        this.jMenuDebug.add(this.jMenuItemInterupt);
        this.jMenuDebug.add(this.jMenuItemAttachToProcess);
        this.jMenuDebug.addSeparator();
        this.jMenuDebug.add(this.jMenuStartAnimation);
        this.jMenuDebug.add(this.jMenuItemStopAnimation);
        this.jMenuItemStopAnimation.setEnabled(false);
        this.jMenuStartAnimation.add(this.jMenuItemStepAni);
        this.jMenuStartAnimation.add(this.jMenuItemNextAni);
        this.jMenuDebug.addSeparator();
        this.jMenuDebug.add(this.jMenuItemDataview);
        this.jMenuDebug.add(this.jMenuItemAddWatch);
        this.jMenuDebug.add(this.jMenuItemRemoveWatch);
        this.jMenuDebug.add(this.jMenuItemShowTrans);
        this.jMenuView.add(this.jCheckBoxMenuItemDataWindow);
        this.jMenuView.add(this.jCheckBoxMenuItemStackWindow);
        this.jMenuView.add(this.jCheckBoxMenuItemWatchWindow);
        this.jMenuEdit.add(this.jMenuItemCut);
        this.jMenuEdit.add(this.jMenuItemCopy);
        this.jMenuEdit.add(this.jMenuItemPaste);
        this.jMenuEdit.addSeparator();
        this.jMenuEdit.add(this.jMenuItemBreakpoints);
        this.jMenuEdit.addSeparator();
        this.jMenuEdit.add(this.jMenuItemPreferences);
        this.jMenuSearch.add(this.jMenuItemFind);
        this.jMenuSearch.add(this.jMenuItemFindNext);
        this.jMenuSearch.add(this.jMenuItemFindPrev);
        this.jAddRemoveWatch.add(this.jMenuItemAddWatchpoint);
        this.jAddRemoveWatch.add(this.jMenuItemRemoveWatchpoint);
        this.jAddRemoveWatch.add(this.jMenuItemWatchpoint);
        this.jAddRemoveWatch.add(this.jMenuItemViewPropertySheet);
        this.jPopupMenuBufferAndTempTable.add(this.jPopupMenuItemBufferAndTempTableViewFields);
        this.jMenuDiag.add(this.jMenuItemMonitorDynObj);
        this.jMenuDiag.setMnemonic('I');
    }
    
    private void initializeButtons() {
        this.fileOpenButton.setIcon(this.imageOpenFile);
        this.fileOpenButton.setRolloverEnabled(true);
        this.fileOpenButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.fileOpenButton_actionPerformed(actionEvent);
            }
        });
        this.fileOpenButton.setToolTipText("Open File");
        this.helpButton.setIcon(this.imageHelp);
        this.helpButton.setRolloverEnabled(true);
        this.helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.helpButton_actionPerformed(actionEvent);
            }
        });
        this.helpButton.setMaximumSize(new Dimension(32, 32));
        this.helpButton.setMinimumSize(new Dimension(16, 16));
        this.helpButton.setRequestFocusEnabled(false);
        this.helpButton.setToolTipText("Help");
        this.stepInButton.setIcon(this.imageStepIn);
        this.stepInButton.setRolloverEnabled(true);
        this.stepInButton.setToolTipText("Step Into");
        this.continueButton.setIcon(this.imageContinue);
        this.continueButton.setRolloverEnabled(true);
        this.continueButton.setToolTipText("Continue");
        this.stepOverButton.setIcon(this.imageStepOver);
        this.stepOverButton.setRolloverEnabled(true);
        this.stepOverButton.setToolTipText("Step Over");
        this.stopButton.setToolTipText("Stop");
        this.stopButton.setIcon(this.ImageStop);
        this.stopButton.setRolloverEnabled(true);
        this.interruptButton.setMaximumSize(new Dimension(32, 32));
        this.interruptButton.setMinimumSize(new Dimension(16, 16));
        this.interruptButton.setRequestFocusEnabled(false);
        this.interruptButton.setToolTipText("Interrupt");
        this.interruptButton.setIcon(this.imageInterrupt);
        this.interruptButton.setRolloverEnabled(true);
        this.interruptButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.interruptButton_actionPerformed(actionEvent);
            }
        });
        this.jButtoneFocusLine.setEnabled(false);
        this.jButtoneFocusLine.setBorder(BorderFactory.createEtchedBorder());
        this.fileOpenButton.setMaximumSize(new Dimension(32, 32));
        this.fileOpenButton.setMinimumSize(new Dimension(16, 16));
        this.fileOpenButton.setRequestFocusEnabled(false);
        this.stepInButton.setMaximumSize(new Dimension(32, 32));
        this.stepInButton.setMinimumSize(new Dimension(32, 32));
        this.stepInButton.setRequestFocusEnabled(false);
        this.stepOverButton.setMaximumSize(new Dimension(32, 32));
        this.stepOverButton.setMinimumSize(new Dimension(16, 16));
        this.stepOverButton.setRequestFocusEnabled(false);
        this.continueButton.setMaximumSize(new Dimension(32, 32));
        this.continueButton.setMinimumSize(new Dimension(32, 32));
        this.continueButton.setRequestFocusEnabled(false);
        this.stopButton.setMaximumSize(new Dimension(32, 32));
        this.stopButton.setMinimumSize(new Dimension(32, 32));
        this.stopButton.setRequestFocusEnabled(false);
        this.stepOutButton.setToolTipText("Step Out");
        this.stepOutButton.setIcon(this.imageStepOut);
        this.stepOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stepOutButton_actionPerformed(actionEvent);
            }
        });
        this.stepOutButton.setMaximumSize(new Dimension(32, 32));
        this.stepOutButton.setMinimumSize(new Dimension(32, 32));
        this.stepOutButton.setRequestFocusEnabled(false);
        this.stepOutButton.setEnabled(false);
    }
    
    private void initializeIcons() {
        this.imageOpenFile = new ImageIcon(Frame1.class.getResource("openFile.gif"));
        this.imageCloseFile = new ImageIcon(Frame1.class.getResource("closeFile1.gif"));
        this.imageHelp = new ImageIcon(Frame1.class.getResource("help.gif"));
        this.imageStepIn = new ImageIcon(Frame1.class.getResource("stepin1.gif"));
        this.imageStepOver = new ImageIcon(Frame1.class.getResource("stepover1.gif"));
        this.imageContinue = new ImageIcon(Frame1.class.getResource("continue1.gif"));
        this.ImageStop = new ImageIcon(Frame1.class.getResource("stop1.gif"));
        this.imageInterrupt = new ImageIcon(Frame1.class.getResource("interrupt1.gif"));
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("debug.gif")));
        this.imageStepOut = new ImageIcon(Frame1.class.getResource("stepout.gif"));
    }
    
    private void initializeActionListeners() {
        this.jMenuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuFileExit_actionPerformed(actionEvent);
            }
        });
        this.jMenuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuHelpAbout_actionPerformed(actionEvent);
            }
        });
        this.stepInButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stepIn_actionPerformed(actionEvent);
            }
        });
        this.continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.continue_actionPerformed(actionEvent);
            }
        });
        this.stepOverButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stepOver_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemStepOver.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stepOver_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemStepIn.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stepIn_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemStop.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stop_actionPerformed(actionEvent);
            }
        });
        this.jCheckBoxMenuItemDataWindow.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jCheckBoxMenuItemDataWindow_actionPerformed(actionEvent);
            }
        });
        this.jCheckBoxMenuItemStackWindow.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jCheckBoxMenuItemStackWindow_actionPerformed(actionEvent);
            }
        });
        this.jCheckBoxMenuItemWatchWindow.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jCheckBoxMenuItemWatchWindow_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemBreakpoints.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemBreakpoints_actionPerformed(actionEvent);
            }
        });
        this.jListStack.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                Frame1.this.jListStack_valueChanged(listSelectionEvent);
            }
        });
        this.jMenuItemAddWatchpoint.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.addWatchpoint_Selected(actionEvent);
            }
        });
        this.jMenuItemRemoveWatchpoint.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.removeWatchpointSelected(actionEvent);
            }
        });
        this.jMenuItemContinue.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.continue_actionPerformed(actionEvent);
            }
        });
        this.stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stop_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemViewPropertySheet.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.viewPropertySheetDialog(actionEvent);
            }
        });
        this.jMenuItemAddWatch.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.addWatchpoint_Selected(actionEvent);
            }
        });
        this.jMenuItemRemoveWatch.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemRemoveWatch_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemPreferences.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemPreferences_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemRun.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemRun_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemActiveWindow.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemActiveWindow_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemClipboard.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemClipboard_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemCodeBase.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemCodeBase_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemColorTable.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemColorTable_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemCompiler.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemCompiler_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemCurrentWindow.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemCurrentWindow_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemDebugger.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemDebugger_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemDefaultWindow.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemDefaultWindow_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemErrorStatus.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemErrorStatus_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemFileInfo.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFileInfo_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemFocus.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFocus_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemFontTable.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemFontTable_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemLastEvent.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemLastEvent_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemProfiler.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemProfiler_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemRcodeInfo.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemRcodeInfo_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSelf.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSelf_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSession.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSession_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemSourceProc.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemSourceProc_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemTargetProc.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemTargetProc_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemThisObjectProc.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemThisObjectProc_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemThisProc.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemThisProc_actionPerformed(actionEvent);
            }
        });
        this.jMenuItemWebContext.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.jMenuItemWebContext_actionPerformed(actionEvent);
            }
        });
        this.jAddRemoveWatch.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
                Frame1.this.jAddRemoveWatch_popupMenuWillBecomeVisible(popupMenuEvent);
            }
            
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent popupMenuEvent) {
            }
            
            public void popupMenuCanceled(final PopupMenuEvent popupMenuEvent) {
            }
        });
        this.jPopupMenuBufferAndTempTable.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
                Frame1.this.jPopupMenuBufferAndTempTable_popupMenuWillBecomeVisible(popupMenuEvent);
            }
            
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent popupMenuEvent) {
            }
            
            public void popupMenuCanceled(final PopupMenuEvent popupMenuEvent) {
            }
        });
        this.jMenuItemStepOut.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Frame1.this.stepOutButton_actionPerformed(actionEvent);
            }
        });
    }
    
    private void initializePanes() {
        this.jSplitPaneMain.setOrientation(0);
        this.jSplitPaneMain.setBorder(BorderFactory.createEtchedBorder());
        this.jSplitPaneMain.setContinuousLayout(true);
        this.jSplitPaneMain.setDividerSize(3);
        this.jSplitPaneMain.setResizeWeight(0.4);
        this.desktop.setLayout(this.borderLayoutDesktop);
        this.jSplitPaneListingAndStack.setResizeWeight(0.6);
        this.jSplitPaneListingAndStack.setDividerSize(5);
        this.jSplitPaneLocalDataAndWatch.setContinuousLayout(true);
        this.jSplitPaneLocalDataAndWatch.setDividerSize(6);
        this.jSplitPaneLocalDataAndWatch.setResizeWeight(0.5);
        this.jPanelDebugListing.setLayout(this.borderLayoutDebugListing);
        this.jPanelDebugListing.setBorder(BorderFactory.createRaisedBevelBorder());
        this.jScrollPaneDebugListing.getViewport().setBackground(this.debugListingFile.getBackground());
        this.jScrollPaneWatchTable.addMouseListener(this.mouseListener);
        this.jTabbedPane1.setTabPlacement(3);
        this.jTabbedPane1.setMinimumSize(new Dimension(40, 40));
        this.jTabbedPane1.setPreferredSize(new Dimension(40, 40));
    }
    
    private void addInitialContentToPanes() {
        this.contentPaneMainWindow.add(this.jToolBar, "North");
        this.contentPaneMainWindow.add(this.statusBar, "South");
        this.contentPaneMainWindow.add(this.jSplitPaneMain, "Center");
        this.jSplitPaneMain.add(this.jPanelDebugListing, "top");
        this.jPanelDebugListing.add(this.jSplitPaneListingAndStack, null);
        this.jSplitPaneListingAndStack.add(this.jScrollPaneDebugListing, "left");
        this.jSplitPaneListingAndStack.add(this.jScrollPaneStack, "right");
        this.jScrollPaneDebugListing.getViewport().add(this.debugListingFile, null);
        this.jSplitPaneMain.add(this.desktop, "bottom");
        this.desktop.add(this.jSplitPaneLocalDataAndWatch, "Center");
        this.jSplitPaneLocalDataAndWatch.add(this.jScrollPaneWatchTable, "right");
        this.jSplitPaneLocalDataAndWatch.add(this.jTabbedPane1, "left");
        this.jTabbedPane1.add(this.variables, "Variables");
        this.variables.getViewport().add(this.variablesTable, null);
        this.jTabbedPane1.add(this.buffers, "Buffers");
        this.jTabbedPane1.add(this.parameters, "Parameters");
        this.jTabbedPane1.add(this.tempTables, "Temp Tables");
        this.jTabbedPane1.add(this.jScrollPaneDataSet, "Dataset");
        this.jScrollPaneStack.getViewport().add(this.jListStack, null);
        this.jScrollPaneWatchTable.getViewport().add(this.watchTable, null);
    }
    
    private void initializeLocalDataWindowAndWatchWindow() {
        this.tempColumnNames = new Vector();
        final Vector<String> vector = new Vector<String>();
        vector.addElement(" ");
        this.tempColumnNames.addElement("Name");
        this.tempColumnNames.addElement("Value");
        final Vector<String> obj = new Vector<String>();
        this.tempRowData = new Vector();
        obj.addElement(" ");
        obj.addElement(" ");
        this.tempRowData.addElement(obj);
        this.watchTable = new JTable(new WatchTableModel(this, this.tempRowData, vector, vector, true));
        this.watchDropTarget = new WatchTableDropTarget(this, this.watchTable);
        this.watchTable.setSelectionMode(0);
        this.tempTablesTable = new JTable(this.tempRowData, this.tempColumnNames);
        this.buffersTable = new JTable(this.tempRowData, this.tempColumnNames);
        this.datasetTable = new JTable(this.tempRowData, this.tempColumnNames);
        this.tempColumnNames.addElement("Type");
        final Vector<String> obj2 = new Vector<String>();
        obj2.addElement(" ");
        obj2.addElement(" ");
        obj2.addElement(" ");
        final Vector<Vector<String>> vector2 = new Vector<Vector<String>>();
        vector2.addElement(obj2);
        this.variablesTable = new JTable(vector2, this.tempColumnNames);
        this.parametersTable = new JTable(vector2, this.tempColumnNames);
        this.tableListener = new tableDoubleClickListener(this, this.variablesTable);
        this.variablesTable.addMouseListener(this.tableListener);
    }
    
    public void jMenuFileExit_actionPerformed(final ActionEvent actionEvent) {
        if (this.debuggerMode.compareToIgnoreCase("2") != 0) {
            if (this.debuggerHasControl) {
                this.sendSocket.sendMessage("exit");
            }
            else if (this.meshandle != null && this.debuggerMode.compareToIgnoreCase("2") != 0) {
                this.meshandle.parseMessage("MSG_SHUTDOWN");
            }
        }
        else {
            if (!this.menuFileExitSelected) {
                this.setVisible(true);
                if (JOptionPane.showConfirmDialog(this, "Close the debugger?", "Confirmation", 0) == 1) {
                    this.setVisible(true);
                    return;
                }
            }
            this.menuFileExitSelected = true;
            if (this.debuggerHasControl) {
                this.sendSocket.sendMessage("exit");
            }
            else {
                this.closeSockets();
            }
            if (this.madePVMDebugReady) {
                this.leave4GLDebugReady();
                this.madePVMDebugReady = false;
            }
            this.savePreferences();
            this.dispose();
            System.exit(0);
        }
    }
    
    public void jMenuHelpAbout_actionPerformed(final ActionEvent actionEvent) {
        final Frame1_AboutBox frame1_AboutBox = new Frame1_AboutBox(this);
        final Dimension preferredSize = frame1_AboutBox.getPreferredSize();
        final Dimension size = this.getSize();
        final Point location = this.getLocation();
        frame1_AboutBox.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        frame1_AboutBox.setModal(true);
        frame1_AboutBox.setVisible(true);
    }
    
    protected void processWindowEvent(final WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == 201) {
            this.jMenuFileExit_actionPerformed(null);
        }
    }
    
    private void jCheckBoxMenuItemDataWindow_actionPerformed(final ActionEvent actionEvent) {
        if (!this.jCheckBoxMenuItemDataWindow.getState()) {
            if (!this.watchClosed) {
                this.localDataWatchSplitterLastPosition = this.jSplitPaneLocalDataAndWatch.getDividerLocation();
            }
            this.jSplitPaneLocalDataAndWatch.remove(this.jTabbedPane1);
            this.localDataClosed = true;
            this.checkInternalWindows();
        }
        else {
            if (this.internalWindowsClosed) {
                this.openInternalWindowsPane();
                this.internalWindowsClosed = false;
            }
            this.jSplitPaneLocalDataAndWatch.add(this.jTabbedPane1, "left");
            this.jSplitPaneLocalDataAndWatch.setDividerLocation(this.localDataWatchSplitterLastPosition);
            this.localDataClosed = false;
        }
    }
    
    private void jCheckBoxMenuItemStackWindow_actionPerformed(final ActionEvent actionEvent) {
        if (!this.jCheckBoxMenuItemStackWindow.getState()) {
            this.debugListingStackSplitterLastPosition = this.jSplitPaneListingAndStack.getDividerLocation();
            this.jSplitPaneListingAndStack.remove(this.jScrollPaneStack);
        }
        else {
            this.jSplitPaneListingAndStack.add(this.jScrollPaneStack, "right");
            this.jSplitPaneListingAndStack.setDividerLocation(this.debugListingStackSplitterLastPosition);
        }
    }
    
    private void jCheckBoxMenuItemWatchWindow_actionPerformed(final ActionEvent actionEvent) {
        if (!this.jCheckBoxMenuItemWatchWindow.getState()) {
            if (!this.localDataClosed) {
                this.localDataWatchSplitterLastPosition = this.jSplitPaneLocalDataAndWatch.getDividerLocation();
            }
            this.jSplitPaneLocalDataAndWatch.remove(this.jScrollPaneWatchTable);
            this.watchClosed = true;
            this.checkInternalWindows();
        }
        else {
            if (this.internalWindowsClosed) {
                this.openInternalWindowsPane();
                this.internalWindowsClosed = false;
            }
            this.jSplitPaneLocalDataAndWatch.add(this.jScrollPaneWatchTable, "right");
            this.jSplitPaneLocalDataAndWatch.setDividerLocation(this.localDataWatchSplitterLastPosition);
            this.watchClosed = false;
        }
    }
    
    void checkInternalWindows() {
        if (!this.jCheckBoxMenuItemDataWindow.getState() && !this.jCheckBoxMenuItemWatchWindow.getState()) {
            this.closeInternalWindowsPane();
            this.internalWindowsClosed = true;
        }
    }
    
    void closeInternalWindowsPane() {
        this.mainSplitterLastPosition = this.jSplitPaneMain.getDividerLocation();
        this.jSplitPaneMain.remove(this.desktop);
    }
    
    void openInternalWindowsPane() {
        this.jSplitPaneMain.add(this.desktop, "bottom");
        this.jSplitPaneMain.setDividerLocation(this.mainSplitterLastPosition);
    }
    
    void updateStackWindow(final Vector stack) {
        this.jScrollPaneStack.setEnabled(false);
        this.jScrollPaneStack.remove(this.jListStack);
        this.stack.clear();
        this.stack = stack;
        (this.jListStack = new JList(this.stack)).setCellRenderer(this.stackRenderer);
        this.jListStack.setFont(this.currentFont);
        this.jListStack.setSelectionMode(0);
        this.removeStackTextArrow();
        this.jListStack.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                Frame1.this.jListStack_valueChanged(listSelectionEvent);
            }
        });
        this.jListStack.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.jListStack_focusGained(focusEvent);
            }
        });
        this.jScrollPaneStack.getViewport().add(this.jListStack, null);
        this.jScrollPaneStack.setEnabled(true);
        this.debugListingFile.revalidate();
        this.debugListingFile.repaint();
        this.breakpointSelect.revalidate();
        this.breakpointSelect.repaint();
    }
    
    void jListStack_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final int selectedIndex = ((JList)listSelectionEvent.getSource()).getSelectedIndex();
        String s = " ";
        if (selectedIndex != this.previousStackClickedLine) {
            if (selectedIndex < this.previousStackClickedLine) {
                s = "up ";
            }
            else if (selectedIndex > this.previousStackClickedLine) {
                s = "down ";
            }
            this.sendSocket.sendMessage(s.concat(Integer.toString(Math.abs(selectedIndex - this.previousStackClickedLine))));
            this.previousStackClickedLine = selectedIndex;
            this.sendSocket.sendMessage("show stack");
            this.requestUpdates(true);
        }
    }
    
    void setHandleMessage(final HandleMessage meshandle) {
        if (meshandle != null) {
            this.meshandle = meshandle;
            this.focusLine = this.meshandle.focusLineNumber;
        }
    }
    
    void setListingAndBreakpointsTables(final Vector vector, final Vector vector2, final Vector columnIdentifiers) {
        this.jScrollPaneDebugListing.setEnabled(false);
        this.searchData = vector2;
        this.debuggListingModel.setDataVector(vector2, columnIdentifiers);
        this.debugTextField.setAutoscrolls(false);
        this.debugTextField.setFont(this.currentFont);
        this.editor = new SingleClickCellEditor(this.debugTextField);
        this.debugListingFile.getColumnModel().getColumn(0).setCellEditor(this.editor);
        this.debugListingFile.setSelectionMode(0);
        this.debugListingFile.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer(this));
        this.debugListingFile.revalidate();
        this.debugListingFile.getColumnModel().getColumn(0).setPreferredWidth(1000);
        this.debugListingFile.setTableHeader(null);
        (this.breakpointSelect = new JTable(new BreakpointsTableModel(vector))).setRequestFocusEnabled(false);
        this.breakpointSelect.setRowSelectionAllowed(false);
        this.breakpointSelect.setColumnSelectionAllowed(false);
        this.breakpointSelect.setSelectionMode(0);
        this.breakpointSelect.getColumnModel().getColumn(0).setCellRenderer(new TableIconRenderer(this.breakpointSelect));
        this.breakpointSelect.getColumnModel().getColumn(0).setPreferredWidth(25);
        this.breakpointSelect.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent mouseEvent) {
                Frame1.this.breakpointSelect_mouseClicked(mouseEvent);
            }
        });
        this.breakpointSelect.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                Frame1.this.breakpointSelect_keyPressed(keyEvent);
            }
        });
        this.breakpointSelect.setBackground(Color.lightGray);
        this.breakpointSelect.setSelectionBackground(Color.lightGray);
        this.breakpointSelect.setSelectionForeground(Color.lightGray);
        this.breakpointSelect.setGridColor(Color.lightGray);
        final Dimension preferredSize = this.breakpointSelect.getPreferredSize();
        final JViewport rowHeader = new JViewport();
        rowHeader.setBackground(Color.lightGray);
        rowHeader.setView(this.breakpointSelect);
        rowHeader.setPreferredSize(preferredSize);
        rowHeader.setMaximumSize(preferredSize);
        this.breakpointSelect.setShowHorizontalLines(false);
        this.breakpointSelect.setShowVerticalLines(false);
        this.jScrollPaneDebugListing.setCorner("UPPER_LEFT_CORNER", this.breakpointSelect.getTableHeader());
        this.jScrollPaneDebugListing.setRowHeader(rowHeader);
        this.jScrollPaneDebugListing.setEnabled(true);
        this.debugListingFile.changeSelection(this.focusLine - 1, 0, true, false);
    }
    
    private void setListingFileDefaultSettings() {
        this.debugListingFile.setAutoscrolls(false);
        this.mouseListenerSource = new JPopupMenuShower(this.jSourcePopupMenu, this.debugListingFile, this.debugTextField, true, true);
        this.mouseListenerSourceScrollPane = new JPopupMenuShower(this.jSourceScrollPanePopupMenu, null, false, false);
        this.jScrollPaneDebugListing.addMouseListener(this.mouseListenerSourceScrollPane);
        this.debugListingFile.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.debugListingFile_focusGained(focusEvent);
            }
        });
        this.debugListingFile.addMouseListener(this.mouseListenerSource);
        this.debugListingFile.setFont(this.currentFont);
        this.debugListingFile.setSelectionMode(0);
        this.debugTextField.setDragEnabled(true);
        this.debugTextField.addMouseListener(this.mouseListenerSource);
        this.debugListingFile.getColumnModel().getColumn(0).setCellEditor(this.editor);
        this.debugListingFile.getColumnModel().getColumn(0).setCellRenderer(this.dbglist);
        this.debugListingFile.setShowHorizontalLines(false);
        this.debugListingFile.setShowVerticalLines(false);
        this.debugListingFile.setAutoResizeMode(0);
        this.debugListingFile.setAutoscrolls(true);
        this.debugListingFile.setCellSelectionEnabled(true);
        this.debugListingFile.setTableHeader(null);
        this.debugListingFile.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer(this));
        this.debugListingFile.setEnabled(true);
    }
    
    private void stepOver_actionPerformed(final ActionEvent actionEvent) {
        this.disableButtonsAndMenus();
        this.statusBar.setText(" ");
        this.sendSocket.sendMessage("next");
    }
    
    private void stepIn_actionPerformed(final ActionEvent actionEvent) {
        this.disableButtonsAndMenus();
        this.statusBar.setText(" ");
        this.sendSocket.sendMessage("step");
    }
    
    private void continue_actionPerformed(final ActionEvent actionEvent) {
        this.disableButtonsAndMenus();
        this.statusBar.setText(" ");
        this.sendSocket.sendMessage("cont");
    }
    
    private void stop_actionPerformed(final ActionEvent actionEvent) {
        this.disableButtonsAndMenus();
        this.sendSocket.sendMessage("stop");
        if (this.dynamicDlg != null && this.dynamicDlg.isShowing()) {
            this.dynamicDlg.dispose();
        }
    }
    
    private void jMenuItemBreakpoints_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("show breaks");
    }
    
    void updateWindowMenu(final String str) {
        final String nextToken = new StringTokenizer(str, "(").nextToken();
        nextToken.trim();
        this.currentLabelName = nextToken;
        this.newItem = new JMenuItem(nextToken);
        boolean b = false;
        final int size = this.fileHistory.size();
        if (size != 0) {
            for (int i = 0; i < size; ++i) {
                if (this.fileHistory.elementAt(i).toString().compareTo(nextToken) == 0) {
                    b = true;
                    break;
                }
            }
        }
        if (!b) {
            this.jMenuWindow.add(this.newItem);
            this.fileHistory.addElement(nextToken);
            this.newItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent actionEvent) {
                    Frame1.this.newItem_actionPerformed(actionEvent);
                }
            });
        }
    }
    
    void newItem_actionPerformed(final ActionEvent actionEvent) {
        final Object o = new Object();
        final String text = ((JMenuItem)actionEvent.getSource()).getText();
        if (text.compareTo(this.currentLabelName) != 0) {
            this.sendSocket.sendMessage("file \"".concat(text).concat("\""));
        }
    }
    
    void updateVariablesPane(final Vector variablesData, final Vector vector) {
        this.variables.setEnabled(false);
        this.variablesData = variablesData;
        final int width = this.variablesTable.getColumnModel().getColumn(0).getWidth();
        final int width2 = this.variablesTable.getColumnModel().getColumn(1).getWidth();
        this.variablesTable.getColumnModel().getColumn(2).getWidth();
        this.variables.remove(this.variablesTable);
        this.variablesTable = new JTable(new ParameterVariableTableModel(this, this.variablesData, vector));
        this.variablesTable.getTableHeader().setReorderingAllowed(false);
        this.variablesTable.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.variablesTable.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.variablesTable.getColumnModel().getColumn(2).setPreferredWidth(width2);
        this.variablesTable.setFont(this.currentFont);
        this.mouseListenerVariableAndParameter = new JPopupMenuShower(this.jPopupMenuBufferAndTempTable, this.variablesTable, true, false);
        this.variablesTable.addMouseListener(this.mouseListenerVariableAndParameter);
        this.variablesTable.setSelectionMode(0);
        (this.variableTableListSelection = this.variablesTable.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                Frame1.this.variableTableListSelection_valueChanged(listSelectionEvent);
            }
        });
        this.variablesTable.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.variablesTable_focusGained(focusEvent);
            }
        });
        final TableColumnModel columnModel = this.variablesTable.getColumnModel();
        (this.localPaneTextField = new JTextField()).addMouseListener(this.mouseListenerVariableAndParameter);
        this.localPaneTextField.setBorder(null);
        final DefaultCellEditor cellEditor = new DefaultCellEditor(this.localPaneTextField);
        cellEditor.getComponent().setFont(this.currentFont);
        columnModel.getColumn(1).setCellEditor(cellEditor);
        this.variables.getViewport().add(this.variablesTable);
        this.variables.setEnabled(true);
    }
    
    void updateBuffersPane(final Vector bufferData) {
        this.buffers.setEnabled(false);
        this.bufferData = bufferData;
        final int width = this.buffersTable.getColumnModel().getColumn(0).getWidth();
        final int width2 = this.buffersTable.getColumnModel().getColumn(1).getWidth();
        this.buffers.remove(this.buffersTable);
        this.buffersTable = new JTable(new ReadOnlyTableModel(this.bufferData));
        this.buffersTable.getTableHeader().setReorderingAllowed(false);
        this.buffersTable.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.buffersTable.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.mouseListenerBufferAndTempTable = new JPopupMenuShower(this.jPopupMenuBufferAndTempTable, this.buffersTable, true, false);
        this.buffersTable.addMouseListener(this.mouseListenerBufferAndTempTable);
        this.buffersTable.setFont(this.currentFont);
        this.buffersTable.setSelectionMode(0);
        this.buffers.getViewport().add(this.buffersTable);
        this.buffers.setEnabled(true);
    }
    
    void updateParametersPane(final Vector parametersData, final Vector vector) {
        this.parameters.setEnabled(false);
        this.parametersData = parametersData;
        final int width = this.parametersTable.getColumnModel().getColumn(0).getWidth();
        final int width2 = this.parametersTable.getColumnModel().getColumn(1).getWidth();
        this.parametersTable.getColumnModel().getColumn(2).getWidth();
        this.parameters.remove(this.parametersTable);
        this.parametersTable = new JTable(new ParameterVariableTableModel(this, this.parametersData, vector));
        this.parametersTable.getTableHeader().setReorderingAllowed(false);
        this.parametersTable.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.parametersTable.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.parametersTable.getColumnModel().getColumn(2).setPreferredWidth(width2);
        this.parametersTable.setFont(this.currentFont);
        this.parameterTableListSelection = this.parametersTable.getSelectionModel();
        this.mouseListenerVariableAndParameter = new JPopupMenuShower(this.jPopupMenuBufferAndTempTable, this.parametersTable, true, false);
        this.parametersTable.addMouseListener(this.mouseListenerVariableAndParameter);
        this.parametersTable.setSelectionMode(0);
        this.parameterTableListSelection.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                Frame1.this.parameterTableListSelection_valueChanged(listSelectionEvent);
            }
        });
        this.parametersTable.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.parametersTable_focusGained(focusEvent);
            }
        });
        final TableColumnModel columnModel = this.parametersTable.getColumnModel();
        (this.localPaneTextField = new JTextField()).addMouseListener(this.mouseListenerVariableAndParameter);
        this.localPaneTextField.setBorder(null);
        final DefaultCellEditor cellEditor = new DefaultCellEditor(this.localPaneTextField);
        cellEditor.getComponent().setFont(this.currentFont);
        columnModel.getColumn(1).setCellEditor(cellEditor);
        this.parameters.getViewport().add(this.parametersTable);
        this.parameters.setEnabled(true);
    }
    
    void updateTempTablesPane(final Vector tempTableData) {
        this.tempTables.setEnabled(false);
        this.tempTableData = tempTableData;
        final int width = this.tempTablesTable.getColumnModel().getColumn(0).getWidth();
        final int width2 = this.tempTablesTable.getColumnModel().getColumn(1).getWidth();
        this.tempTables.remove(this.tempTablesTable);
        this.tempTablesTable = new JTable(new ReadOnlyTableModel(this.tempTableData));
        this.tempTablesTable.getTableHeader().setReorderingAllowed(false);
        this.tempTablesTable.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.tempTablesTable.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.mouseListenerBufferAndTempTable = new JPopupMenuShower(this.jPopupMenuBufferAndTempTable, this.tempTablesTable, true, false);
        this.tempTablesTable.addMouseListener(this.mouseListenerBufferAndTempTable);
        this.tempTablesTable.setFont(this.currentFont);
        this.tempTablesTable.setSelectionMode(0);
        this.tempTables.getViewport().add(this.tempTablesTable);
        this.tempTables.setEnabled(true);
    }
    
    void updateDatasetTablePane(final Vector datasetTableData) {
        this.jScrollPaneDataSet.setEnabled(false);
        this.datasetTableData = datasetTableData;
        final int width = this.tempTablesTable.getColumnModel().getColumn(0).getWidth();
        final int width2 = this.tempTablesTable.getColumnModel().getColumn(1).getWidth();
        this.jScrollPaneDataSet.remove(this.datasetTable);
        this.datasetTable = new JTable(new ReadOnlyTableModel(this.datasetTableData));
        this.datasetTable.getTableHeader().setReorderingAllowed(false);
        this.datasetTable.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.datasetTable.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.mouseListenerBufferAndTempTable = new JPopupMenuShower(this.jPopupMenuBufferAndTempTable, this.datasetTable, true, false);
        this.datasetTable.addMouseListener(this.mouseListenerBufferAndTempTable);
        this.datasetTable.setFont(this.currentFont);
        this.datasetTable.setSelectionMode(0);
        this.jScrollPaneDataSet.getViewport().add(this.datasetTable);
        this.jScrollPaneDataSet.setEnabled(true);
    }
    
    void updateWatchPoints(final Vector watchTableData, final Vector watchPointNumbers, final Vector vector) {
        this.jScrollPaneWatchTable.setEnabled(false);
        this.watchPointNumbers = watchPointNumbers;
        int width;
        int width2;
        try {
            width = this.watchTable.getColumnModel().getColumn(0).getWidth();
            width2 = this.watchTable.getColumnModel().getColumn(1).getWidth();
        }
        catch (Exception ex) {
            width = 179;
            width2 = 179;
        }
        this.jScrollPaneWatchTable.remove(this.watchTable);
        this.watchTableData = watchTableData;
        this.watchTable = new JTable(new WatchTableModel(this, this.watchTableData, this.watchPointNumbers, vector, true));
        this.watchTable.getTableHeader().setReorderingAllowed(false);
        this.watchTable.getColumnModel().getColumn(0).setPreferredWidth(width);
        this.watchTable.getColumnModel().getColumn(1).setPreferredWidth(width2);
        this.watchDropTarget = new WatchTableDropTarget(this, this.watchTable);
        this.watchTable.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                Frame1.this.watchTable_focusGained(focusEvent);
            }
        });
        this.watchTable.setSelectionMode(0);
        this.mouseListenerWatchtable = new JPopupMenuShower(this.jAddRemoveWatch, this.watchTable, true, false);
        this.watchTable.addMouseListener(this.mouseListenerWatchtable);
        this.watchTable.addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent keyEvent) {
                Frame1.this.watchTableKeyPressed(keyEvent);
            }
        });
        (this.watchTableListSelection = this.watchTable.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent listSelectionEvent) {
                Frame1.this.watchTableListSelection_valueChanged(listSelectionEvent);
            }
        });
        this.watchTable.setFont(this.currentFont);
        final TableColumnModel columnModel = this.watchTable.getColumnModel();
        (this.watchpointTextField = new JTextField()).addMouseListener(this.mouseListener);
        this.watchpointTextField.setBorder(null);
        this.watchpointTextField.setSize(this.watchTable.getWidth() + 10, this.watchTable.getRowHeight() + 10);
        final DefaultCellEditor defaultCellEditor = new DefaultCellEditor(this.watchpointTextField);
        defaultCellEditor.getComponent().setFont(this.currentFont);
        defaultCellEditor.getComponent().setSize(this.watchTable.getWidth() + 10, this.watchTable.getRowHeight() + 10);
        columnModel.getColumn(0).setCellEditor(defaultCellEditor);
        columnModel.getColumn(1).setCellEditor(defaultCellEditor);
        this.jScrollPaneWatchTable.getViewport().add(this.watchTable);
        this.jScrollPaneWatchTable.setEnabled(true);
    }
    
    void jTabbedPane1_stateChanged(final ChangeEvent changeEvent) {
        this.tabbedPaneIndex = this.jTabbedPane1.getSelectedIndex();
        this.switchTabbedPane();
    }
    
    void switchTabbedPane() {
        switch (this.tabbedPaneIndex) {
            case 1: {
                this.sendSocket.sendMessage("list buffers");
                break;
            }
            case 2: {
                this.sendSocket.sendMessage("list parameters");
                break;
            }
            case 3: {
                this.sendSocket.sendMessage("list temp-table");
                break;
            }
            case 0: {
                this.sendSocket.sendMessage("list variables");
                break;
            }
            case 4: {
                this.sendSocket.sendMessage("list datasets");
                break;
            }
        }
    }
    
    void addWatchpoint_Selected(final ActionEvent actionEvent) {
        final addWatchDialog addWatchDialog = new addWatchDialog(this, "Add Watch", true);
        final Dimension preferredSize = addWatchDialog.getPreferredSize();
        final Dimension size = this.getSize();
        final Point location = this.getLocation();
        addWatchDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        addWatchDialog.setVisible(true);
    }
    
    void removeWatchpointSelected(final ActionEvent actionEvent) {
        this.deleteWatch();
    }
    
    private void watchTableKeyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 127) {
            this.deleteWatch();
        }
    }
    
    void deleteWatch() {
        final int selectedRow = this.watchTable.getSelectedRow();
        if (selectedRow != -1) {
            this.watchTableData.removeElementAt(selectedRow);
            this.sendSocket.sendMessage("cancel watch #".concat(this.watchPointNumbers.elementAt(selectedRow).toString()));
            this.sendSocket.sendMessage("show watch");
        }
    }
    
    void disableButtonsAndMenus() {
        this.debuggerHasControl = false;
        if (this.meshandle != null) {
            this.setWindowTitle(this.meshandle.labelName);
        }
        this.stepInButton.setEnabled(false);
        this.stepOverButton.setEnabled(false);
        this.continueButton.setEnabled(false);
        this.stopButton.setEnabled(false);
        this.fileOpenButton.setEnabled(false);
        this.interruptButton.setEnabled(true);
        this.jMenuItemInterupt.setEnabled(true);
        this.jMenuItemStepIn.setEnabled(false);
        this.jMenuItemStepOver.setEnabled(false);
        this.jMenuItemContinue.setEnabled(false);
        this.jMenuItemStop.setEnabled(false);
        this.jMenuItemBreakpoints.setEnabled(false);
        this.jMenuSearch.setEnabled(false);
        this.jMenuWindow.setEnabled(false);
        this.jMenuData.setEnabled(false);
        this.jMenuItemDataview.setEnabled(false);
        this.jMenuItemAddWatchpoint.setEnabled(false);
        this.jMenuItemRemoveWatchpoint.setEnabled(false);
        this.jMenuItemAddWatch.setEnabled(false);
        this.jMenuItemRemoveWatch.setEnabled(false);
        this.jMenuItemViewPropertySheet.setEnabled(false);
        this.jMenuItemOpen.setEnabled(false);
        this.jMenuFileExit.setEnabled(false);
        this.jMenuItemShowTrans.setEnabled(false);
        this.jMenuItemRun.setEnabled(false);
        this.jMenuItemCopy.setEnabled(false);
        this.jMenuItemCut.setEnabled(false);
        this.jMenuItemPaste.setEnabled(false);
        this.jMenuItemHelpTopics.setEnabled(true);
        this.jTabbedPane1.setEnabled(false);
        this.watchTable.setEnabled(false);
        this.breakpointSelect.setEnabled(false);
        this.debugListingFile.setEnabled(false);
        this.breakpointSelect.setRequestFocusEnabled(false);
        this.jListStack.setEnabled(false);
        this.jMenuDiag.setEnabled(false);
        this.stepOutButton.setEnabled(false);
        this.jMenuItemStepOut.setEnabled(false);
        this.frameUtl.findHasBeenActivated = false;
        this.jMenuStartAnimation.setEnabled(false);
        if (this.debuggerMode.compareToIgnoreCase("2") != 0) {
            this.jMenuItemAttachToProcess.setEnabled(false);
        }
        this.buffersTable.setEnabled(false);
        this.buffersTable.removeMouseListener(this.mouseListenerBufferAndTempTable);
        this.tempTablesTable.setEnabled(false);
        this.tempTablesTable.removeMouseListener(this.mouseListenerBufferAndTempTable);
        this.variablesTable.setEnabled(false);
        this.variablesTable.removeMouseListener(this.mouseListenerVariableAndParameter);
        this.parametersTable.setEnabled(false);
        this.parametersTable.removeMouseListener(this.mouseListenerVariableAndParameter);
        this.watchTable.setEnabled(false);
        this.watchTable.removeMouseListener(this.mouseListener);
        this.debugListingFile.removeMouseListener(this.mouseListenerSource);
        this.debugTextField.removeMouseListener(this.mouseListenerSource);
        this.jScrollPaneDebugListing.removeMouseListener(this.mouseListenerSourceScrollPane);
        this.jMenuItemWatchpoint.setEnabled(false);
    }
    
    void standAloneDisableButtonsAndMenus() {
        this.disableButtonsAndMenus();
        this.jMenuFileExit.setEnabled(true);
        this.interruptButton.setEnabled(false);
        if (this.debuggerMode.compareToIgnoreCase("2") == 0) {
            this.jMenuItemAttachToProcess.setEnabled(true);
        }
        else {
            this.jMenuItemRun.setEnabled(true);
            this.jMenuItemOpen.setEnabled(true);
            this.fileOpenButton.setEnabled(true);
        }
        this.jMenuItemInterupt.setEnabled(false);
    }
    
    void enableButtonsAndMenus() {
        this.debuggerHasControl = true;
        if (this.meshandle != null) {
            this.setWindowTitle(this.meshandle.labelName);
        }
        this.stepInButton.setEnabled(true);
        this.stepOverButton.setEnabled(true);
        this.continueButton.setEnabled(true);
        this.stopButton.setEnabled(true);
        this.fileOpenButton.setEnabled(true);
        this.interruptButton.setEnabled(false);
        this.jMenuItemInterupt.setEnabled(false);
        this.jMenuItemStepIn.setEnabled(true);
        this.jMenuItemStepOver.setEnabled(true);
        this.jMenuItemContinue.setEnabled(true);
        this.jMenuItemStop.setEnabled(true);
        this.jMenuItemBreakpoints.setEnabled(true);
        this.jMenuSearch.setEnabled(true);
        this.jMenuWindow.setEnabled(true);
        this.jMenuItemDataview.setEnabled(true);
        this.jMenuItemAddWatch.setEnabled(true);
        this.jMenuItemRemoveWatch.setEnabled(true);
        this.jMenuItemAddWatchpoint.setEnabled(true);
        this.jMenuItemRemoveWatchpoint.setEnabled(true);
        this.jMenuItemViewPropertySheet.setEnabled(true);
        this.jMenuItemOpen.setEnabled(true);
        this.jMenuFileExit.setEnabled(true);
        this.jMenuData.setEnabled(true);
        this.jMenuItemShowTrans.setEnabled(true);
        this.jMenuItemRun.setEnabled(true);
        this.jTabbedPane1.setEnabled(true);
        this.watchTable.setEnabled(true);
        this.breakpointSelect.setEnabled(true);
        this.debugListingFile.setEnabled(true);
        this.jListStack.setEnabled(true);
        this.jMenuDiag.setEnabled(true);
        this.jMenuItemHelpTopics.setEnabled(true);
        this.stepOutButton.setEnabled(true);
        this.jMenuItemStepOut.setEnabled(true);
        if (!this.animating) {
            this.jMenuStartAnimation.setEnabled(true);
        }
        if (this.debuggerMode.compareToIgnoreCase("2") != 0) {
            this.jMenuItemAttachToProcess.setEnabled(false);
        }
        this.buffersTable.setEnabled(true);
        this.buffersTable.addMouseListener(this.mouseListenerBufferAndTempTable);
        this.tempTablesTable.setEnabled(true);
        this.tempTablesTable.addMouseListener(this.mouseListenerBufferAndTempTable);
        this.variablesTable.setEnabled(true);
        this.variablesTable.addMouseListener(this.mouseListenerVariableAndParameter);
        this.parametersTable.setEnabled(true);
        this.parametersTable.addMouseListener(this.mouseListenerVariableAndParameter);
        this.watchTable.setEnabled(true);
        this.watchTable.addMouseListener(this.mouseListener);
        this.debugListingFile.addMouseListener(this.mouseListenerSource);
        this.debugTextField.addMouseListener(this.mouseListenerSource);
        this.jScrollPaneDebugListing.addMouseListener(this.mouseListenerSourceScrollPane);
        this.jMenuItemWatchpoint.setEnabled(true);
    }
    
    void AttachableDetachCleanup() {
        try {
            if (this.variablesTable != null) {
                this.variables.remove(this.variablesTable);
            }
            final Vector<String> obj = new Vector<String>();
            obj.addElement(" ");
            obj.addElement(" ");
            obj.addElement(" ");
            final Vector<Vector<String>> rowData = new Vector<Vector<String>>();
            rowData.addElement(obj);
            this.variablesTable = new JTable(rowData, this.tempColumnNames);
            this.variables.add(this.variablesTable);
            this.variables.getViewport().add(this.variablesTable);
            this.variables.repaint();
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTabbedPane1.repaint();
            this.watchTable.removeMouseListener(this.mouseListenerWatchtable);
            this.watchTableData.clear();
            final Vector<String> e = new Vector<String>();
            e.add("");
            e.add("");
            this.rowData.add(e);
            this.watchTableData.add(this.rowData);
            this.jScrollPaneWatchTable.getViewport().remove(this.watchTable);
            this.jScrollPaneWatchTable.getViewport().add(this.watchTable);
            this.jScrollPaneWatchTable.repaint();
            this.watchTable.repaint();
            final Vector<Vector> dataVector = new Vector<Vector>();
            final Vector<String> columnIdentifiers = new Vector<String>();
            columnIdentifiers.addElement(" ");
            this.debuggListingModel.setDataVector(dataVector, columnIdentifiers);
            this.breakpointSelect = new JTable();
            this.jScrollPaneDebugListing.setRowHeader(new JViewport());
            this.jScrollPaneDebugListing.repaint();
            this.debugListingFile.revalidate();
            this.debugListingFile.repaint();
            this.jScrollPaneStack.remove(this.jListStack);
            this.stack.clear();
            this.stack = new Vector();
            this.jListStack = new JList(this.stack);
            this.jScrollPaneStack.getViewport().add(this.jListStack, null);
            this.jListStack.repaint();
            if (this.meshandle != null) {
                this.meshandle.labelName = "";
            }
        }
        catch (Exception ex) {}
        this.setTitle("OpenEdge Debugger");
        this.statusBar.setText(" ");
        this.repaint();
    }
    
    void viewPropertySheetDialog(final ActionEvent actionEvent) {
        this.requestGetAttrs(this.watchTableData.elementAt(this.watchTable.getSelectedRow()).elementAt(0).toString());
    }
    
    void setPropertySheetData(final String propertySheetName, final Vector propertySheetData, final Vector vector, final Vector vector2, final int n) {
        final String s = "Dataview";
        this.propertySheetName = propertySheetName;
        this.propertySheetData = propertySheetData;
        if (!this.propertySheetOpen) {
            this.dataViewDlg = new PropertySheetDialogBox(this, s, false, vector2, n);
            final Dimension preferredSize = this.dataViewDlg.getPreferredSize();
            final Dimension size = this.getSize();
            final Point location = this.getLocation();
            this.dataViewDlg.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
            this.dataViewDlg.setVisible(true);
        }
        else if (this.dataViewDlg.tabbedPaneIndex == 1) {
            this.dataViewDlg.updateFieldData(vector2);
        }
        else {
            this.dataViewDlg.updateAttributeData(vector2);
        }
    }
    
    void setPropertySheetArrayData(final String propertySheetName, final Vector propertySheetData, final Vector vector) {
        final String s = "Dataview";
        this.propertySheetName = propertySheetName;
        this.propertySheetData = propertySheetData;
        if (!this.propertySheetOpen) {
            this.dataViewDlg = new PropertySheetDialogBox(this, s, true, vector, 2);
            final Dimension preferredSize = this.dataViewDlg.getPreferredSize();
            final Dimension size = this.getSize();
            final Point location = this.getLocation();
            this.dataViewDlg.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
            this.dataViewDlg.setVisible(true);
        }
        else {
            this.dataViewDlg.updateArrayData(vector);
        }
    }
    
    void setPropertySheetValueData(final String s, final String s2, final String s3) {
        if (this.propertySheetOpen) {
            this.dataViewDlg.updateValueData(s, s2, s3);
        }
    }
    
    void jMenuItemRemoveWatch_actionPerformed(final ActionEvent actionEvent) {
        this.deleteWatch();
    }
    
    void removeStackTextArrow() {
        Color color = Color.white;
        this.makeFocusLineYellow = false;
        ImageIcon imageIcon = new ImageIcon(Frame1.class.getResource("stackfocusline.gif"));
        for (int size = this.stack.size(), i = 0; i < size; ++i) {
            final String string = this.stack.elementAt(i).toString();
            if (string.startsWith("-->")) {
                final int index = string.indexOf("-->");
                final StringBuffer sb = new StringBuffer();
                sb.append(string);
                sb.delete(index, index + 3);
                final String string2 = sb.toString();
                if (i != size - 1) {
                    color = Color.white;
                    this.makeFocusLineYellow = true;
                    imageIcon = new ImageIcon(Frame1.class.getResource("stacknotfocusline.gif"));
                }
                this.stack.setElementAt(new Object[][] { { imageIcon, string2, color } }, i);
                this.previousStackClickedLine = i;
                break;
            }
        }
        if (this.meshandle != null) {
            this.meshandle.changeBreakpointsFocusLine();
        }
    }
    
    void jMenuItemPreferences_actionPerformed(final ActionEvent actionEvent) {
        final PreferencesDialogBox preferencesDialogBox = new PreferencesDialogBox(this, "Preferences", true);
        final Dimension preferredSize = preferencesDialogBox.getPreferredSize();
        final Dimension size = this.getSize();
        final Point location = this.getLocation();
        preferencesDialogBox.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        preferencesDialogBox.setModal(true);
        preferencesDialogBox.setVisible(true);
    }
    
    void setAllFonts(final String name, final int size) {
        this.currentFont = new Font(name, 0, size);
        final TableUtilities tableUtilities = new TableUtilities();
        this.debugListingFile.setFont(this.currentFont);
        TableUtilities.setColumnWidths(this.debugListingFile, new Insets(0, 4, 0, 4), true, false);
        this.jListStack.setFont(this.currentFont);
        this.watchTable.setFont(this.currentFont);
        this.variablesTable.setFont(this.currentFont);
        this.buffersTable.setFont(this.currentFont);
        this.parametersTable.setFont(this.currentFont);
        this.tempTablesTable.setFont(this.currentFont);
    }
    
    void savePreferences() {
        try {
            final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("debugger.pref"));
            dataOutputStream.writeUTF(this.currentFont.getName());
            dataOutputStream.writeInt(this.currentFont.getSize());
            dataOutputStream.writeBoolean(this.showLineNumbers);
            dataOutputStream.writeBoolean(this.jCheckBoxMenuItemStackWindow.getState());
            dataOutputStream.writeBoolean(this.jCheckBoxMenuItemDataWindow.getState());
            dataOutputStream.writeBoolean(this.jCheckBoxMenuItemWatchWindow.getState());
            if (!this.internalWindowsClosed) {
                dataOutputStream.writeInt(this.jSplitPaneMain.getDividerLocation());
            }
            else {
                dataOutputStream.writeInt(this.mainSplitterLastPosition);
            }
            if (this.jCheckBoxMenuItemStackWindow.getState()) {
                dataOutputStream.writeInt(this.jSplitPaneListingAndStack.getDividerLocation());
            }
            else {
                dataOutputStream.writeInt(this.debugListingStackSplitterLastPosition);
            }
            if (this.jCheckBoxMenuItemDataWindow.getState() && this.jCheckBoxMenuItemWatchWindow.getState()) {
                dataOutputStream.writeInt(this.jSplitPaneLocalDataAndWatch.getDividerLocation());
            }
            else {
                dataOutputStream.writeInt(this.localDataWatchSplitterLastPosition);
            }
            final Dimension size = this.getSize();
            final double height = size.getHeight();
            dataOutputStream.writeDouble(size.getWidth());
            dataOutputStream.writeDouble(height);
            dataOutputStream.writeInt(this.animationDelayTime);
            dataOutputStream.writeBoolean(this.pvmMachineDir);
            dataOutputStream.writeInt(this.debuggerListingDirs.size());
            for (int i = 0; i < this.debuggerListingDirs.size(); ++i) {
                dataOutputStream.writeUTF(this.debuggerListingDirs.elementAt(i).toString());
            }
            dataOutputStream.close();
        }
        catch (IOException ex) {}
    }
    
    void loadPreferences() {
        try {
            final DataInputStream dataInputStream = new DataInputStream(new FileInputStream("debugger.pref"));
            this.currentFont = new Font(dataInputStream.readUTF(), 0, dataInputStream.readInt());
            this.showLineNumbers = dataInputStream.readBoolean();
            this.jCheckBoxMenuItemStackWindow.setState(dataInputStream.readBoolean());
            this.jCheckBoxMenuItemDataWindow.setState(dataInputStream.readBoolean());
            this.jCheckBoxMenuItemWatchWindow.setState(dataInputStream.readBoolean());
            this.mainSplitterLastPosition = dataInputStream.readInt();
            this.debugListingStackSplitterLastPosition = dataInputStream.readInt();
            this.localDataWatchSplitterLastPosition = dataInputStream.readInt();
            final double double1 = dataInputStream.readDouble();
            final double double2 = dataInputStream.readDouble();
            try {
                this.animationDelayTime = dataInputStream.readInt();
                this.pvmMachineDir = dataInputStream.readBoolean();
                for (int int1 = dataInputStream.readInt(), i = 0; i < int1; ++i) {
                    this.debuggerListingDirs.addElement(dataInputStream.readUTF());
                }
            }
            catch (IOException ex) {
                this.animationDelayTime = 3;
            }
            dataInputStream.close();
            this.setSize((int)double1, (int)double2);
            this.jSplitPaneMain.setDividerLocation(this.mainSplitterLastPosition);
            this.jSplitPaneListingAndStack.setDividerLocation(this.debugListingStackSplitterLastPosition);
            this.jSplitPaneLocalDataAndWatch.setDividerLocation(this.localDataWatchSplitterLastPosition);
            if (!this.jCheckBoxMenuItemStackWindow.getState()) {
                this.jSplitPaneListingAndStack.remove(this.jScrollPaneStack);
            }
            if (!this.jCheckBoxMenuItemWatchWindow.getState()) {
                this.jSplitPaneLocalDataAndWatch.remove(this.jScrollPaneWatchTable);
                this.checkInternalWindows();
            }
            if (!this.jCheckBoxMenuItemDataWindow.getState()) {
                this.jSplitPaneLocalDataAndWatch.remove(this.jTabbedPane1);
                this.checkInternalWindows();
            }
        }
        catch (IOException ex2) {
            this.currentFont = new Font("dialoginput", 0, 12);
            this.setSize(new Dimension(770, 706));
            this.jSplitPaneMain.setDividerLocation(450);
            this.jSplitPaneLocalDataAndWatch.setDividerLocation(350);
            this.jSplitPaneListingAndStack.setDividerLocation(520);
        }
        this.addCurrentWorkingDirectory();
    }
    
    void jMenuItemDataview_actionPerformed(final ActionEvent actionEvent) {
        if (this.dataViewDlg == null || !this.dataViewDlg.isShowing()) {
            this.dataViewDlg = new PropertySheetDialogBox(this);
            final Dimension preferredSize = this.dataViewDlg.getPreferredSize();
            final Dimension size = this.getSize();
            final Point location = this.getLocation();
            this.dataViewDlg.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
            this.dataViewDlg.setVisible(true);
        }
    }
    
    void jAddRemoveWatch_popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
        final int selectedRow = this.watchTable.getSelectedRow();
        final String string = this.watchTableData.elementAt(selectedRow).elementAt(0).toString();
        if (selectedRow < 0 || this.watchTableData == null || string.compareTo("") == 0) {
            this.jMenuItemViewPropertySheet.setEnabled(false);
            this.jMenuItemRemoveWatchpoint.setEnabled(false);
            this.jMenuItemWatchpoint.setEnabled(false);
        }
        else {
            this.jMenuItemViewPropertySheet.setEnabled(true);
            this.jMenuItemRemoveWatchpoint.setEnabled(true);
            this.jMenuItemWatchpoint.setEnabled(true);
        }
        if (this.watchTable.isEditing()) {
            this.jMenuItemAddRemoveWatchCopy.setEnabled(true);
            this.jMenuItemAddRemoveWatchCut.setEnabled(true);
            this.jMenuItemAddRemoveWatchPaste.setEnabled(true);
        }
        else {
            this.jMenuItemAddRemoveWatchCopy.setEnabled(false);
            this.jMenuItemAddRemoveWatchCut.setEnabled(false);
            this.jMenuItemAddRemoveWatchPaste.setEnabled(false);
        }
    }
    
    void requestGetFields(final String str) {
        final String concat = "GET-FIELDS ".concat(str);
        this.lastDataviewRequest = concat;
        this.sendSocket.sendMessage(concat);
    }
    
    void requestGetAttrs(final String str) {
        final String concat = "GET-ATTRS ".concat(str);
        this.lastDataviewRequest = concat;
        this.sendSocket.sendMessage(concat);
    }
    
    void requestGetArray(final String str) {
        final String concat = "GET-ARRAY ".concat(str);
        this.lastDataviewRequest = concat;
        this.sendSocket.sendMessage(concat);
    }
    
    void requestGetValue(final String str) {
        final String concat = "GET-VALUE ".concat(str);
        this.lastDataviewRequest = concat;
        this.sendSocket.sendMessage(concat);
    }
    
    void jPopupMenuItemBufferAndTempTableViewFields_actionPerformed(final ActionEvent actionEvent) {
        if (this.tabbedPaneIndex == 1) {
            this.requestGetFields(this.bufferData.elementAt(this.buffersTable.getSelectedRow()).elementAt(0).toString());
        }
        else if (this.tabbedPaneIndex == 3) {
            this.requestGetFields(this.tempTableData.elementAt(this.tempTablesTable.getSelectedRow()).elementAt(0).toString());
        }
        else if (this.tabbedPaneIndex == 2) {
            this.requestGetAttrs(this.parametersData.elementAt(this.parametersTable.getSelectedRow()).elementAt(0).toString());
        }
        else if (this.tabbedPaneIndex == 0) {
            this.requestGetAttrs(this.variablesData.elementAt(this.variablesTable.getSelectedRow()).elementAt(0).toString());
        }
        else {
            this.requestGetAttrs(this.datasetTableData.elementAt(this.datasetTable.getSelectedRow()).elementAt(0).toString());
        }
    }
    
    void jPopupMenuBufferAndTempTable_popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
        int n = 0;
        int n2 = 0;
        switch (this.tabbedPaneIndex) {
            case 1: {
                n = this.buffersTable.getSelectedRow();
                n2 = (this.buffersTable.isEditing() ? 1 : 0);
                break;
            }
            case 2: {
                n = this.parametersTable.getSelectedRow();
                n2 = (this.parametersTable.isEditing() ? 1 : 0);
                break;
            }
            case 3: {
                n = this.tempTablesTable.getSelectedRow();
                n2 = (this.tempTablesTable.isEditing() ? 1 : 0);
                break;
            }
            case 0: {
                n = this.variablesTable.getSelectedRow();
                n2 = (this.variablesTable.isEditing() ? 1 : 0);
                break;
            }
        }
        if (n < 0) {
            this.jPopupMenuItemBufferAndTempTableViewFields.setEnabled(false);
        }
        else {
            this.jPopupMenuItemBufferAndTempTableViewFields.setEnabled(true);
        }
        if (n2 == 1) {
            this.jPopupMenuItemBufferAndTempTableCopy.setEnabled(true);
            this.jPopupMenuItemBufferAndTempTableCut.setEnabled(true);
            this.jPopupMenuItemBufferAndTempTablePaste.setEnabled(true);
        }
        else {
            this.jPopupMenuItemBufferAndTempTableCopy.setEnabled(false);
            this.jPopupMenuItemBufferAndTempTableCut.setEnabled(false);
            this.jPopupMenuItemBufferAndTempTablePaste.setEnabled(false);
        }
    }
    
    void watchTableListSelection_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final int selectedRow = this.watchTable.getSelectedRow();
        if (selectedRow >= 0) {
            this.lastSelectedWatchTableRow = selectedRow;
            this.watchTableNameValueBeforeEdit = this.watchTable.getValueAt(selectedRow, 0).toString();
            this.watchTableValueValueBeforeEdit = this.watchTable.getValueAt(selectedRow, 1).toString();
        }
    }
    
    void parameterTableListSelection_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final int selectedRow = this.parametersTable.getSelectedRow();
        this.lastSelectedparameterTableRow = selectedRow;
        this.parameterTableNameValueBeforeEdit = this.parametersTable.getValueAt(selectedRow, 0).toString();
        this.parameterTableValueValueBeforeEdit = this.parametersTable.getValueAt(selectedRow, 1).toString();
    }
    
    void variableTableListSelection_valueChanged(final ListSelectionEvent listSelectionEvent) {
        final int selectedRow = this.variablesTable.getSelectedRow();
        this.lastSelectedVariableTableRow = selectedRow;
        this.variableTableNameValueBeforeEdit = this.variablesTable.getValueAt(selectedRow, 0).toString();
        this.variableTableValueValueBeforeEdit = this.variablesTable.getValueAt(selectedRow, 1).toString();
    }
    
    int getTabbedPaneIndex() {
        return this.tabbedPaneIndex;
    }
    
    void jMenuItemOpen_actionPerformed(final ActionEvent actionEvent) {
        this.openFileDialog();
    }
    
    void fileOpenButton_actionPerformed(final ActionEvent actionEvent) {
        this.openFileDialog();
    }
    
    void openFileDialog() {
        final FileOpenDialog fileOpenDialog = new FileOpenDialog(this, "open", true);
        final Dimension preferredSize = fileOpenDialog.getPreferredSize();
        final Dimension size = this.getSize();
        final Point location = this.getLocation();
        fileOpenDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        fileOpenDialog.setVisible(true);
    }
    
    void jMenuItemRun_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("RUN");
        this.statusBar.setText(" ");
    }
    
    void jMenuItemActiveWindow_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("ACTIVE-WINDOW");
    }
    
    void jMenuItemClipboard_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("CLIPBOARD");
    }
    
    void jMenuItemCodeBase_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("CODEBASE-LOCATOR");
    }
    
    void jMenuItemColorTable_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("COLOR-TABLE");
    }
    
    void jMenuItemCompiler_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("COMPILER");
    }
    
    void jMenuItemCurrentWindow_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("CURRENT-WINDOW");
    }
    
    void jMenuItemDebugger_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("DEBUGGER");
    }
    
    void jMenuItemDefaultWindow_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("DEFAULT-WINDOW");
    }
    
    void jMenuItemErrorStatus_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("ERROR-STATUS");
    }
    
    void jMenuItemFileInfo_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("FILE-INFORMATION");
    }
    
    void jMenuItemFocus_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("FOCUS");
    }
    
    void jMenuItemFontTable_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("FONT-TABLE");
    }
    
    void jMenuItemLastEvent_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("LAST-EVENT");
    }
    
    void jMenuItemProfiler_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("PROFILER");
    }
    
    void jMenuItemRcodeInfo_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("RCODE-INFORMATION");
    }
    
    void jMenuItemSelf_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("SELF");
    }
    
    void jMenuItemSession_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("SESSION");
    }
    
    void jMenuItemSourceProc_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("SOURCE-PROCEDURE");
    }
    
    void jMenuItemTargetProc_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("TARGET-PROCEDURE");
    }
    
    void jMenuItemThisObjectProc_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("THIS-OBJECT");
    }
    
    void jMenuItemThisProc_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("THIS-PROCEDURE");
    }
    
    void jMenuItemWebContext_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("WEB-CONTEXT");
    }
    
    void jMenuItemFind_actionPerformed(final ActionEvent actionEvent) {
        final FindDialog findDialog = new FindDialog(this, "Find", true);
        final Dimension preferredSize = findDialog.getPreferredSize();
        final Dimension size = this.getSize();
        final Point location = this.getLocation();
        findDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
        findDialog.setVisible(true);
    }
    
    void interruptButton_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("interrupt");
    }
    
    void highlightFindRow(final int rowIndex) {
        this.debugListingFile.changeSelection(rowIndex, 0, true, false);
    }
    
    void jMenuItemFindNext_actionPerformed(final ActionEvent actionEvent) {
        final int tableSearchDown = this.frameUtl.tableSearchDown(this.searchData, this.frameUtl.getLastWordSearch(), this.frameUtl.caseMatchOn);
        if (tableSearchDown == -1) {
            JOptionPane.showMessageDialog(this, "Text Not Found", "Error", 0);
        }
        else {
            this.highlightFindRow(tableSearchDown);
        }
    }
    
    void jMenuItemFindPrev_actionPerformed(final ActionEvent actionEvent) {
        final int tableSearchUp = this.frameUtl.tableSearchUp(this.searchData, this.frameUtl.getLastWordSearch(), this.frameUtl.caseMatchOn);
        if (tableSearchUp == -1) {
            JOptionPane.showMessageDialog(this, "Text Not Found.", "Error", 0);
        }
        else {
            this.highlightFindRow(tableSearchUp);
        }
    }
    
    void jMenuItemShowTrans_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("SHOW TRANSACTION");
    }
    
    void breakpointSelect_mouseClicked(final MouseEvent mouseEvent) {
        boolean b = false;
        final int selectedRow = this.breakpointSelect.getSelectedRow();
        final int size = this.meshandle.breakpoints.size();
        String s = "";
        for (int i = 0; i < size; ++i) {
            int n = 0;
            int n2 = this.meshandle.stringToInt(this.meshandle.breakpoints.elementAt(i).toString());
            if (n2 < 0) {
                n2 = Math.abs(n2);
                n = 1;
            }
            if (Integer.toString(n2).compareTo(Integer.toString(selectedRow + 1)) == 0) {
                String s2;
                if (n == 1) {
                    s2 = "enable break #";
                }
                else {
                    s2 = "cancel break #";
                }
                s = s2.concat(this.meshandle.breakpointIds.elementAt(i).toString());
                b = true;
                break;
            }
        }
        if (!b) {
            s = "break ".concat(Integer.toString(selectedRow + 1));
        }
        if (s.compareTo("break 0") != 0) {
            this.sendSocket.sendMessage(s);
            this.breakpointSelect.clearSelection();
            this.breakpointSelect.revalidate();
            this.breakpointSelect.repaint();
        }
    }
    
    void breakpointSelect_keyPressed(final KeyEvent keyEvent) {
        keyEvent.consume();
    }
    
    void jMenuItemPvmMsg_actionPerformed(final ActionEvent actionEvent) {
        if (!this.debugDlgOpen) {
            (this.debugDlg = new DebugPvmMessagesDialog(this, "dlg", false)).setVisible(true);
            this.debugDlgOpen = true;
        }
    }
    
    void jPopupMenuItemBufferAndTempTableAddWatchpoint_actionPerformed(final ActionEvent actionEvent) {
        final int selectedIndex = this.jTabbedPane1.getSelectedIndex();
        String s = "watch ";
        switch (selectedIndex) {
            case 0: {
                s = s.concat(this.variablesData.elementAt(this.variablesTable.getSelectedRow()).elementAt(0).toString());
                break;
            }
            case 1: {
                s = s.concat(this.bufferData.elementAt(this.buffersTable.getSelectedRow()).elementAt(0).toString());
                break;
            }
            case 2: {
                s = s.concat(this.parametersData.elementAt(this.parametersTable.getSelectedRow()).elementAt(0).toString());
                break;
            }
            case 3: {
                s = s.concat(this.tempTableData.elementAt(this.tempTablesTable.getSelectedRow()).elementAt(0).toString());
                break;
            }
            case 4: {
                s = s.concat(this.datasetTableData.elementAt(this.datasetTable.getSelectedRow()).elementAt(0).toString());
                break;
            }
        }
        this.sendSocket.sendMessage(s);
        this.sendSocket.sendMessage("show watch");
    }
    
    void jSourcePopupMenu_popupMenuWillBecomeVisible(final PopupMenuEvent popupMenuEvent) {
        if (this.debugListingFile.isEditing() && this.debugTextField.getSelectedText() != null) {
            this.jMenuItemSourcePopupCopy.setEnabled(true);
            this.jMenuItemSourcePopupAddWatch.setEnabled(true);
        }
        else {
            this.jMenuItemSourcePopupCopy.setEnabled(false);
            this.jMenuItemSourcePopupAddWatch.setEnabled(false);
        }
        this.jMenuItemSourcePopupAddBreak.setEnabled(false);
        this.jMenuItemSourcePopupRemoveBreak.setEnabled(false);
        this.jMenuItemSourcePopupEnableBreak.setEnabled(false);
        this.jMenuItemSourcePopupDisableBreak.setEnabled(false);
        final int selectedRow = this.debugListingFile.getSelectedRow();
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < this.meshandle.breakpoints.size(); ++i) {
            n2 = 0;
            int a = this.meshandle.stringToInt(this.meshandle.breakpoints.elementAt(i).toString());
            if (a < 0) {
                a = Math.abs(a);
                n2 = 1;
            }
            if (a - 1 == selectedRow) {
                n = 1;
                break;
            }
        }
        if (n == 1) {
            this.jMenuItemSourcePopupRemoveBreak.setEnabled(true);
            if (n2 == 1) {
                this.jMenuItemSourcePopupEnableBreak.setEnabled(true);
            }
            else {
                this.jMenuItemSourcePopupDisableBreak.setEnabled(true);
            }
        }
        else {
            this.jMenuItemSourcePopupAddBreak.setEnabled(true);
        }
    }
    
    void jMenuItemSourcePopupAddBreak_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("break ".concat(Integer.toString(this.debugListingFile.getSelectedRow() + 1)));
    }
    
    void jMenuItemSourcePopupRemoveBreak_actionPerformed(final ActionEvent actionEvent) {
        final String s = "cancel break #";
        final int breakpointId = this.getBreakpointId(this.debugListingFile.getSelectedRow() + 1);
        if (breakpointId != -1) {
            this.sendSocket.sendMessage(s.concat(Integer.toString(breakpointId)));
        }
    }
    
    void jMenuItemSourcePopupEnableBreak_actionPerformed(final ActionEvent actionEvent) {
        final String s = "enable break #";
        final int breakpointId = this.getBreakpointId(this.debugListingFile.getSelectedRow() + 1);
        if (breakpointId != -1) {
            this.sendSocket.sendMessage(s.concat(Integer.toString(breakpointId)));
        }
    }
    
    void jMenuItemSourcePopupDisableBreak_actionPerformed(final ActionEvent actionEvent) {
        final String s = "disable break #";
        final int breakpointId = this.getBreakpointId(this.debugListingFile.getSelectedRow() + 1);
        if (breakpointId != -1) {
            this.sendSocket.sendMessage(s.concat(Integer.toString(breakpointId)));
        }
    }
    
    int getBreakpointId(final int n) {
        for (int size = this.meshandle.breakpoints.size(), i = 0; i < size; ++i) {
            if (Math.abs(this.meshandle.stringToInt(this.meshandle.breakpoints.elementAt(i).toString())) == n) {
                return this.meshandle.stringToInt(this.meshandle.breakpointIds.elementAt(i).toString());
            }
        }
        return -1;
    }
    
    void jPopupMenuItemBufferAndTempTableCopy_actionPerformed(final ActionEvent actionEvent) {
        this.localPaneTextField.copy();
    }
    
    void jPopupMenuItemBufferAndTempTableCut_actionPerformed(final ActionEvent actionEvent) {
        this.localPaneTextField.cut();
    }
    
    void jPopupMenuItemBufferAndTempTablePaste_actionPerformed(final ActionEvent actionEvent) {
        this.localPaneTextField.paste();
    }
    
    void jMenuItemSourcePopupCopy_actionPerformed(final ActionEvent actionEvent) {
        this.debugTextField.copy();
    }
    
    void jMenuItemAddRemoveWatchCopy_actionPerformed(final ActionEvent actionEvent) {
        this.watchpointTextField.copy();
    }
    
    void jMenuItemAddRemoveWatchCut_actionPerformed(final ActionEvent actionEvent) {
        this.watchpointTextField.cut();
    }
    
    void jMenuItemAddRemoveWatchPaste_actionPerformed(final ActionEvent actionEvent) {
        this.watchpointTextField.paste();
    }
    
    void jMenuEdit_menuSelected(final MenuEvent menuEvent) {
        if (this.debugListingHasFocus && this.debugListingFile.isEditing()) {
            this.jMenuItemCopy.setEnabled(true);
            this.jMenuItemCut.setEnabled(false);
            this.jMenuItemPaste.setEnabled(false);
        }
        else if (this.watchTableHasFocus && this.watchTable.isEditing()) {
            this.jMenuItemCopy.setEnabled(true);
            this.jMenuItemCut.setEnabled(true);
            this.jMenuItemPaste.setEnabled(true);
        }
        else if ((this.variablesTableHasFocus && this.variablesTable.isEditing() && this.jTabbedPane1.getSelectedIndex() == 0) || (this.parametersTableHasFocus && this.parametersTable.isEditing() && this.jTabbedPane1.getSelectedIndex() == 2)) {
            this.jMenuItemCopy.setEnabled(true);
            this.jMenuItemCut.setEnabled(true);
            this.jMenuItemPaste.setEnabled(true);
        }
        else {
            this.jMenuItemCopy.setEnabled(false);
            this.jMenuItemCut.setEnabled(false);
            this.jMenuItemPaste.setEnabled(false);
        }
    }
    
    void debugListingFile_focusGained(final FocusEvent focusEvent) {
        this.debugListingHasFocus = true;
        this.watchTableHasFocus = false;
        this.variablesTableHasFocus = false;
        this.parametersTableHasFocus = false;
    }
    
    void watchTable_focusGained(final FocusEvent focusEvent) {
        this.debugListingHasFocus = false;
        this.watchTableHasFocus = true;
        this.variablesTableHasFocus = false;
        this.parametersTableHasFocus = false;
    }
    
    void buffersTable_focusGained(final FocusEvent focusEvent) {
        this.debugListingHasFocus = false;
        this.watchTableHasFocus = false;
        this.variablesTableHasFocus = false;
        this.parametersTableHasFocus = false;
    }
    
    void parametersTable_focusGained(final FocusEvent focusEvent) {
        this.debugListingHasFocus = false;
        this.watchTableHasFocus = false;
        this.variablesTableHasFocus = false;
        this.parametersTableHasFocus = true;
    }
    
    void tempTablesTable_focusGained(final FocusEvent focusEvent) {
        this.debugListingHasFocus = false;
        this.watchTableHasFocus = false;
        this.variablesTableHasFocus = false;
        this.parametersTableHasFocus = false;
    }
    
    void variablesTable_focusGained(final FocusEvent focusEvent) {
        this.debugListingHasFocus = false;
        this.watchTableHasFocus = false;
        this.variablesTableHasFocus = true;
        this.parametersTableHasFocus = false;
    }
    
    void jListStack_focusGained(final FocusEvent focusEvent) {
        this.debugListingHasFocus = false;
        this.watchTableHasFocus = false;
        this.variablesTableHasFocus = false;
        this.parametersTableHasFocus = false;
    }
    
    void jMenuItemCut_actionPerformed(final ActionEvent actionEvent) {
        if (this.watchTableHasFocus) {
            this.watchpointTextField.cut();
        }
        else {
            this.localPaneTextField.cut();
        }
    }
    
    void jMenuItemCopy_actionPerformed(final ActionEvent actionEvent) {
        if (this.debugListingHasFocus) {
            this.debugTextField.copy();
        }
        else if (this.watchTableHasFocus) {
            this.watchpointTextField.copy();
        }
        else {
            this.localPaneTextField.copy();
        }
    }
    
    void jMenuItemPaste_actionPerformed(final ActionEvent actionEvent) {
        if (this.watchTableHasFocus) {
            this.watchpointTextField.paste();
        }
        else {
            this.localPaneTextField.paste();
        }
    }
    
    void jMenuItemMonitorDynObj_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("GET-OBJ-LIST");
    }
    
    void jMenuItemSourcePopupAddWatch_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("watch ".concat(this.debugTextField.getSelectedText()));
        this.sendSocket.sendMessage("show watch");
    }
    
    void helpButton_actionPerformed(final ActionEvent actionEvent) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            HelpSet.findHelpSet(classLoader, this.helpUrlPage);
            new HelpSet(classLoader, new URL(this.helpUrlPage)).createHelpBroker().setDisplayed(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Helpset " + ex.getMessage(), "Error", 0);
            JOptionPane.showMessageDialog(this, "Helpset " + this.helpUrlPage + " not found", "Error", 0);
            System.out.println("Helpset " + ex.getMessage());
            System.out.println("Helpset " + this.helpUrlPage + " not found");
        }
    }
    
    String getLastDataviewRequest() {
        return this.lastDataviewRequest;
    }
    
    void savePropertySheetComboBox(final JComboBox propertySheetComboBox) {
        this.propertySheetComboBox = propertySheetComboBox;
    }
    
    void requestUpdates(final boolean b) {
        this.sendSocket.sendMessage("show watch");
        this.switchTabbedPane();
        if (this.dataViewDlg != null && this.dataViewDlg.isShowing() && b) {
            this.sendSocket.sendMessage(this.getLastDataviewRequest());
        }
        if (this.dynamicDlg != null && this.dynamicDlg.monitoring) {
            this.sendSocket.sendMessage("GET-DYN-OBJS");
        }
    }
    
    void setWindowTitle(final String s) {
        if (this.debuggerHasControl) {
            this.setTitle("OpenEdge Debugger [break] - ".concat(s));
        }
        else {
            this.setTitle("OpenEdge Debugger [run] - ".concat(s));
        }
    }
    
    void setHelpUrlPage(final String str) {
        this.helpUrlPage = "file:";
        this.helpUrlPage = this.helpUrlPage.concat(str);
    }
    
    void stepOutButton_actionPerformed(final ActionEvent actionEvent) {
        this.disableButtonsAndMenus();
        this.statusBar.setText(" ");
        this.sendSocket.sendMessage("step-out");
    }
    
    void jMenuItemRunToLine_actionPerformed(final ActionEvent actionEvent) {
        this.disableButtonsAndMenus();
        this.sendSocket.sendMessage("run-to ".concat(Integer.toString(this.debugListingFile.getSelectedRow() + 1)));
    }
    
    void jMenuItemStepAni_actionPerformed(final ActionEvent actionEvent) {
        this.animating = true;
        this.stepInAnimate = true;
        this.jMenuItemStopAnimation.setEnabled(true);
        this.jMenuStartAnimation.setEnabled(false);
        this.sendSocket.sendMessage("step");
        this.disableButtonsAndMenus();
        this.jButtonStopAnimation.setVisible(true);
    }
    
    void jMenuItemNextAni_actionPerformed(final ActionEvent actionEvent) {
        this.animating = true;
        this.nextAnimate = true;
        this.jMenuItemStopAnimation.setEnabled(true);
        this.jMenuStartAnimation.setEnabled(false);
        this.sendSocket.sendMessage("next");
        this.disableButtonsAndMenus();
        this.jButtonStopAnimation.setVisible(true);
    }
    
    void jMenuItemStopAnimation_actionPerformed(final ActionEvent actionEvent) {
        this.timer.stop();
        this.animating = false;
        this.jMenuItemStopAnimation.setEnabled(false);
        this.jMenuStartAnimation.setEnabled(true);
        this.stepInAnimate = false;
        this.nextAnimate = false;
        this.enableButtonsAndMenus();
        this.jButtonStopAnimation.setVisible(false);
    }
    
    void processAnimationDelay() {
        this.timer.restart();
    }
    
    void sendSourceInformation(final boolean b) {
        if (b) {
            this.sendSocket.sendMessage("SETPROP SEND-NOSOURCE-MSG 1");
        }
        else {
            this.sendSocket.sendMessage("SETPROP SEND-NOSOURCE-MSG 0");
        }
    }
    
    private void sendAnimationCommand(final ActionEvent actionEvent) {
        this.disableButtonsAndMenus();
        if (this.stepInAnimate) {
            this.sendSocket.sendMessage("step");
        }
        else {
            this.sendSocket.sendMessage("next");
        }
        this.timer.stop();
    }
    
    void changeAnimationDelay(final int animationDelayTime) {
        this.animationDelayTime = animationDelayTime;
        this.timer.setDelay(this.animationDelayTime * 1000);
        this.timer.setInitialDelay(this.animationDelayTime * 1000);
    }
    
    void setErrorMessageDataView(final String s, final int n) {
        if (!this.propertySheetOpen) {
            this.propertySheetName = s.substring(0, s.indexOf(" "));
            this.dataViewDlg = new PropertySheetDialogBox(this, s, n);
            final Dimension preferredSize = this.dataViewDlg.getPreferredSize();
            final Dimension size = this.getSize();
            final Point location = this.getLocation();
            this.dataViewDlg.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
            this.dataViewDlg.setVisible(true);
        }
        else {
            this.dataViewDlg.setErrorMessage(s, n);
        }
    }
    
    void jButtonStopAnimation_actionPerformed(final ActionEvent actionEvent) {
        this.jMenuItemStopAnimation_actionPerformed(actionEvent);
    }
    
    void jMenuItemLogManager_actionPerformed(final ActionEvent actionEvent) {
        this.requestGetAttrs("LOG-MANAGER");
    }
    
    void jMenuItemAttachToProcess_actionPerformed(final ActionEvent actionEvent) {
        this.ProcessAttachToProcess(false);
    }
    
    void ProcessAttachToProcess(final boolean b) {
        if (!b && this.jMenuItemAttachToProcess.getText().compareTo("Detach from Process") != 0) {
            final AttachToProcessDialog attachToProcessDialog = new AttachToProcessDialog(this, " ", true);
            final Dimension preferredSize = attachToProcessDialog.getPreferredSize();
            final Dimension size = this.getSize();
            final Point location = this.getLocation();
            attachToProcessDialog.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
            attachToProcessDialog.setVisible(true);
            this.optionDetachSelected = false;
        }
        else {
            if (!b) {
                this.optionDetachSelected = true;
            }
            this.closeSockets();
            this.changeDetachMenuText();
            this.disableButtonsAndMenus();
            this.standAloneDisableButtonsAndMenus();
            this.AttachableDetachCleanup();
            if (!b) {
                JOptionPane.showMessageDialog(this, "Detached from process.", "Message", 0);
            }
            if (this.madePVMDebugReady) {
                this.madePVMDebugReady = false;
                if (!b) {
                    this.leave4GLDebugReady();
                }
            }
            this.optionDetachSelected = false;
        }
    }
    
    void initializeSocket() {
        this.recvSocket = new Sockets(this, 2);
        this.sendSocket = new Sockets(this, 1);
        (this.getInfoThread = new GetInfoThread(this.recvSocket)).start();
    }
    
    void initializeAttachSocket(final String s, final int n) throws Exception {
        this.recvSocket = new Sockets(this, 2, s, n);
        this.sendSocket = new Sockets(this, 1, s, n);
        (this.getInfoThread = new GetInfoThread(this.recvSocket)).start();
    }
    
    void changeAttachMenuText() {
        this.jMenuItemAttachToProcess.setText("Detach from Process");
    }
    
    void changeDetachMenuText() {
        this.jMenuItemAttachToProcess.setText("Attach To Process...");
    }
    
    void setPropMessage(final String sendPropMessage) {
        this.sendPropMessage = sendPropMessage;
    }
    
    void leave4GLDebugReady() {
        if (JOptionPane.showConfirmDialog(this, "Do you want to leave the Progress 4GL debug-ready?", "Debug-Ready", 0) == 1) {
            final StringBuffer sb = new StringBuffer();
            final String s = new String();
            String title;
            if (this.prodll.pingOffJNI(this.attachId) != 0) {
                sb.append("Cannot make process unready for debugging.");
                title = "Error";
            }
            else {
                sb.append("Process is no longer ready for debugging.");
                title = "Message";
            }
            final String errorMsg = this.prodll.getErrorMsg();
            if (errorMsg.length() > 0) {
                sb.append(errorMsg);
            }
            if (sb.length() > 0) {
                JOptionPane.showMessageDialog(this, sb.toString(), title, 0);
            }
        }
    }
    
    void addCurrentWorkingDirectory() {
        final String property = System.getProperty("user.dir");
        for (int i = 0; i < this.debuggerListingDirs.size(); ++i) {
            if (property.compareTo(this.debuggerListingDirs.elementAt(i).toString()) == 0) {
                return;
            }
        }
        this.debuggerListingDirs.addElement(property);
    }
    
    void closeSockets() {
        try {
            if (this.sendSocket != null) {
                this.sendSocket.close();
                this.sendSocket = null;
            }
            if (this.recvSocket != null) {
                this.recvSocket.close();
                this.recvSocket = null;
            }
        }
        catch (Exception ex) {}
    }
    
    void jMenuItemWatchpoint_actionPerformed(final ActionEvent actionEvent) {
        this.sendSocket.sendMessage("watchpoint " + this.watchTableData.elementAt(this.watchTable.getSelectedRow()).elementAt(0).toString());
    }
}
