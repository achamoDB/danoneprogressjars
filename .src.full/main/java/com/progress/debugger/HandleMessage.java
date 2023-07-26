// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.io.IOException;
import javax.swing.JCheckBox;
import java.awt.Point;
import java.awt.Dimension;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.File;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.util.Vector;

public class HandleMessage
{
    Frame1 application;
    String labelName;
    String fileName;
    String currentFileName;
    String currentFileNameRelativePath;
    String lastFailedFileName;
    Vector breakpointRowData;
    Vector TextRowData;
    Vector breakpoints;
    Vector breakpointIds;
    Vector breakpointList;
    Vector disabledBreakpointsList;
    Vector breakpointProperties;
    int focusLineNumber;
    int previousFocusLineNumber;
    private Icon icon;
    private Icon breakimage;
    private Icon disabledbreakimage;
    private Icon breakfocusimage;
    private Icon disabledbreakfocusimage;
    private Icon empty;
    private Icon yellowFocusImage;
    private Icon yellowFocusBreakImage;
    private Icon yellowFocusDisabledBreakImage;
    private boolean needToDisplayMessageBox;
    private String messageBoxText;
    private long lastFileSize;
    private String previousFileName;
    private String sendPropMessage;
    private boolean receivedMSG_COMPLETE;
    private int previousRowCount;
    private static final String FILE_SEPARATOR;
    
    public HandleMessage(final Frame1 application) {
        this.currentFileName = " ";
        this.currentFileNameRelativePath = " ";
        this.lastFailedFileName = null;
        this.breakpointRowData = new Vector();
        this.TextRowData = new Vector();
        this.breakpoints = new Vector();
        this.breakpointIds = new Vector();
        this.disabledBreakpointsList = new Vector();
        this.breakpointProperties = new Vector();
        this.focusLineNumber = 0;
        this.previousFocusLineNumber = 0;
        this.icon = new ImageIcon(Frame1.class.getResource("focusline.jpg"));
        this.breakimage = new ImageIcon(Frame1.class.getResource("breakpoint.jpg"));
        this.disabledbreakimage = new ImageIcon(Frame1.class.getResource("disabled.jpg"));
        this.breakfocusimage = new ImageIcon(Frame1.class.getResource("breakpointfocus.jpg"));
        this.disabledbreakfocusimage = new ImageIcon(Frame1.class.getResource("disabledfocusline.jpg"));
        this.empty = new ImageIcon(Frame1.class.getResource("empty.jpg"));
        this.yellowFocusImage = new ImageIcon(Frame1.class.getResource("notfocusline.jpg"));
        this.yellowFocusBreakImage = new ImageIcon(Frame1.class.getResource("notbreakpointfocusline.jpg"));
        this.yellowFocusDisabledBreakImage = new ImageIcon(Frame1.class.getResource("disablednotfocusline.jpg"));
        this.needToDisplayMessageBox = false;
        this.lastFileSize = 0L;
        this.previousFileName = " ";
        this.sendPropMessage = " ";
        this.receivedMSG_COMPLETE = false;
        this.previousRowCount = 0;
        this.application = application;
    }
    
    public void changeBreakpointsFocusLine() {
        this.breakpointRowData.clear();
        final int size = this.TextRowData.size();
        if (size <= 0) {
            return;
        }
        for (int i = 0; i < size; ++i) {
            final Vector<Icon> obj = new Vector<Icon>();
            obj.addElement(this.empty);
            this.breakpointRowData.addElement(obj);
        }
        if (this.focusLineNumber > 0 && this.focusLineNumber - 1 <= this.breakpointRowData.size()) {
            this.breakpointRowData.elementAt(this.focusLineNumber - 1).clear();
            if (this.application.makeFocusLineYellow) {
                this.breakpointRowData.elementAt(this.focusLineNumber - 1).addElement(this.yellowFocusImage);
            }
            else {
                this.breakpointRowData.elementAt(this.focusLineNumber - 1).addElement(this.icon);
            }
        }
        for (int size2 = this.breakpoints.size(), j = 0; j < size2; ++j) {
            boolean b = false;
            int a = this.stringToInt(this.breakpoints.elementAt(j).toString());
            if (a < 0) {
                b = true;
                a = Math.abs(a);
            }
            if (a - 1 < this.breakpointRowData.size()) {
                ((Vector)this.breakpointRowData.elementAt(a - 1)).clear();
                if (a == this.focusLineNumber) {
                    if (this.application.makeFocusLineYellow && !b) {
                        ((Vector<Icon>)this.breakpointRowData.elementAt(a - 1)).addElement(this.yellowFocusBreakImage);
                    }
                    else if (this.application.makeFocusLineYellow && b) {
                        ((Vector<Icon>)this.breakpointRowData.elementAt(a - 1)).addElement(this.yellowFocusDisabledBreakImage);
                    }
                    else if (!this.application.makeFocusLineYellow && b) {
                        ((Vector<Icon>)this.breakpointRowData.elementAt(a - 1)).addElement(this.disabledbreakfocusimage);
                    }
                    else {
                        ((Vector<Icon>)this.breakpointRowData.elementAt(a - 1)).addElement(this.breakfocusimage);
                        if (this.application.animating) {
                            this.application.jMenuItemStopAnimation_actionPerformed(null);
                        }
                    }
                }
                else if (b) {
                    ((Vector<Icon>)this.breakpointRowData.elementAt(a - 1)).addElement(this.disabledbreakimage);
                }
                else {
                    ((Vector<Icon>)this.breakpointRowData.elementAt(a - 1)).addElement(this.breakimage);
                }
            }
        }
    }
    
    void setScrollPosition() {
        final Rectangle visibleRect = this.application.debugListingFile.getVisibleRect();
        final Rectangle cellRect = this.application.debugListingFile.getCellRect(this.focusLineNumber - 1, 0, true);
        final Rectangle rectangle = new Rectangle(visibleRect.x, visibleRect.y, visibleRect.width, (int)(visibleRect.height * 0.85));
        final int rowCount = this.application.debugListingFile.getRowCount();
        int n;
        if (cellRect.height != 0) {
            n = visibleRect.height / cellRect.height;
        }
        else {
            n = 0;
        }
        final int n2 = n - (int)(n * 0.85);
        if (!rectangle.intersects(cellRect)) {
            if (cellRect.getY() > visibleRect.getY()) {
                if (rowCount > this.previousRowCount) {
                    this.application.jScrollPaneDebugListing.getViewport().setViewPosition(cellRect.getLocation());
                }
                this.application.debugListingFile.scrollRectToVisible(this.application.debugListingFile.getCellRect(this.focusLineNumber + n2, 0, true));
            }
            else {
                this.application.jScrollPaneDebugListing.getViewport().setViewPosition(cellRect.getLocation());
            }
        }
        else {
            this.application.debugListingFile.scrollRectToVisible(this.application.debugListingFile.getCellRect(this.focusLineNumber - 1, 0, true));
        }
        this.previousRowCount = rowCount;
    }
    
    void openFile(String name) {
        this.application.setCursor(Cursor.getPredefinedCursor(3));
        this.application.setHandleMessage(this);
        final StringBuffer sb = new StringBuffer();
        final Vector<String> vector = new Vector<String>();
        vector.addElement("");
        final long length = new File(name).length();
        final int compareTo = name.compareTo(this.currentFileName);
        boolean b = true;
        final boolean b2 = !this.application.localHost && this.application.debuggerMode.compareToIgnoreCase("2") == 0;
        Label_0788: {
            if (compareTo != 0 || length != this.lastFileSize) {
                if (!b2 || this.currentFileNameRelativePath.compareTo(name) != 0) {
                    try {
                        this.application.updateWindowMenu(this.labelName);
                        this.TextRowData.clear();
                        if (b2) {
                            if (this.lastFailedFileName != null && name.compareTo(this.lastFailedFileName) == 0) {
                                this.application.setCursor(Cursor.getPredefinedCursor(0));
                                return;
                            }
                            this.application.statusBar.setText("Loading file.  Please wait.");
                            String replace = name;
                            if (HandleMessage.FILE_SEPARATOR.charAt(0) == '/') {
                                replace = replace.replace('\\', HandleMessage.FILE_SEPARATOR.charAt(0));
                            }
                            final String fileSpecified = this.findFileSpecified(replace);
                            if (fileSpecified.compareTo("error") != 0) {
                                this.currentFileNameRelativePath = name;
                                name = fileSpecified;
                                this.application.sendSourceInformation(false);
                                this.lastFailedFileName = null;
                            }
                            else {
                                this.application.setCursor(Cursor.getPredefinedCursor(0));
                                if (JOptionPane.showConfirmDialog(this.application, "File " + name + " not found.  Do you wish to specify the location of the source file?", "File Not Found", 0) == 0) {
                                    final PreferencesDialogBox preferencesDialogBox = new PreferencesDialogBox(this.application, "Preferences", true);
                                    final Dimension preferredSize = preferencesDialogBox.getPreferredSize();
                                    final Dimension size = this.application.getSize();
                                    final Point location = this.application.getLocation();
                                    preferencesDialogBox.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
                                    preferencesDialogBox.jTabbedPane1.setSelectedIndex(2);
                                    preferencesDialogBox.show();
                                    this.lastFailedFileName = null;
                                    this.openFile(name);
                                }
                                else {
                                    this.application.statusBar.setText(" ");
                                    this.application.sendSourceInformation(true);
                                    this.lastFileSize = length;
                                    this.lastFailedFileName = name;
                                    this.currentFileNameRelativePath = " ";
                                }
                                b = false;
                            }
                        }
                        else {
                            this.application.statusBar.setText("Loading file.  Please wait.");
                        }
                        if (b) {
                            this.currentFileName = name;
                            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(name), "UTF8"));
                            int value = 1;
                            int n = 0;
                            final boolean b3 = name.indexOf("dbg_") > -1;
                            String str;
                            while ((str = bufferedReader.readLine()) != null) {
                                if (n != 0 || b3) {
                                    str = str.substring(12, str.length());
                                }
                                else if (value == 1 && str.substring(0, 12).compareTo("        1   ") == 0) {
                                    n = 1;
                                    str = str.substring(12, str.length());
                                }
                                final Vector<String> obj = new Vector<String>();
                                sb.delete(0, sb.length());
                                if (this.application.showLineNumbers) {
                                    sb.append(new Integer(value).toString());
                                    this.addLineNumberWhiteSpace(sb);
                                }
                                sb.append(str);
                                obj.addElement(sb.toString());
                                this.TextRowData.addElement(obj);
                                ++value;
                            }
                            this.changeBreakpointsFocusLine();
                            this.application.setListingAndBreakpointsTables(this.breakpointRowData, this.TextRowData, vector);
                            this.application.statusBar.setText(" ");
                            this.lastFileSize = length;
                        }
                    }
                    catch (Exception ex) {
                        this.lastFileSize = length;
                    }
                    break Label_0788;
                }
            }
            try {
                this.changeBreakpointsFocusLine();
            }
            catch (Exception ex2) {}
        }
        this.application.setCursor(Cursor.getPredefinedCursor(0));
    }
    
    public int stringToInt(final String s) {
        int int1 = 0;
        try {
            int1 = Integer.parseInt(s.trim());
        }
        catch (NumberFormatException ex) {}
        return int1;
    }
    
    public void parseMessage(final String s) {
        final Sockets sendSocket = this.application.sendSocket;
        final String removeReturnChar = this.removeReturnChar(s);
        if (this.application.debugDlgOpen) {
            this.application.debugDlg.addMessage(removeReturnChar);
        }
        final DebuggerStringTokenizer debuggerStringTokenizer = new DebuggerStringTokenizer(removeReturnChar, ";");
        if (debuggerStringTokenizer.countTokens() < 1) {
            return;
        }
        final String nextToken = debuggerStringTokenizer.nextToken();
        if (nextToken.compareToIgnoreCase("MSG_LISTING") == 0) {
            this.application.toFront();
            this.fileName = debuggerStringTokenizer.nextToken();
            this.labelName = debuggerStringTokenizer.nextToken();
            debuggerStringTokenizer.nextToken();
            final String nextToken2 = debuggerStringTokenizer.nextToken();
            this.breakpoints.clear();
            this.breakpointIds.clear();
            this.application.setWindowTitle(this.labelName);
            final int stringToInt = this.stringToInt(nextToken2);
            if (stringToInt > 0) {
                this.previousFocusLineNumber = this.focusLineNumber;
                this.focusLineNumber = stringToInt;
            }
            else if (stringToInt == 0 && this.previousFileName.compareTo(this.fileName) != 0) {
                this.focusLineNumber = stringToInt;
            }
            while (debuggerStringTokenizer.hasMoreTokens() && debuggerStringTokenizer.countTokens() > 1) {
                this.breakpoints.add(new Integer(this.stringToInt(debuggerStringTokenizer.nextToken())));
                this.breakpointIds.add(debuggerStringTokenizer.nextToken());
            }
            this.openFile(this.fileName);
            this.previousFileName = this.fileName;
            this.application.breakpointSelect.revalidate();
            this.application.breakpointSelect.repaint();
            if (stringToInt > 0) {
                this.setScrollPosition();
            }
            if (this.needToDisplayMessageBox) {
                JOptionPane.showMessageDialog(this.application, this.messageBoxText);
                if (this.application.getAttrOrGetFieldsRequest) {
                    this.application.dataViewDlg.toFront();
                    this.application.getAttrOrGetFieldsRequest = false;
                }
                else {
                    this.application.toFront();
                }
                this.messageBoxText = null;
                this.needToDisplayMessageBox = false;
            }
            if (this.application.animating) {
                this.application.processAnimationDelay();
            }
            return;
        }
        if (nextToken.compareToIgnoreCase("Breakpoints") == 0) {
            final Vector<String> vector = new Vector<String>();
            final Vector<String> vector2 = new Vector<String>();
            this.breakpointList = new Vector();
            this.breakpointProperties.clear();
            this.disabledBreakpointsList.clear();
            while (debuggerStringTokenizer.hasMoreTokens() && debuggerStringTokenizer.countTokens() > 1) {
                final String s2 = "#";
                final Vector<String> obj = new Vector<String>();
                final String concat = s2.concat(debuggerStringTokenizer.nextToken() + " ");
                final String nextToken3 = debuggerStringTokenizer.nextToken();
                vector.add(nextToken3);
                final String nextToken4 = debuggerStringTokenizer.nextToken();
                String s3;
                if (nextToken3.compareToIgnoreCase("e") == 0) {
                    final String nextToken5 = debuggerStringTokenizer.nextToken();
                    vector2.add(nextToken5);
                    if (nextToken5.compareToIgnoreCase("-1") == 0) {
                        s3 = concat.concat("Break on any error");
                    }
                    else if (nextToken5.compareToIgnoreCase("-2") == 0) {
                        s3 = concat.concat("Break on any unsuppressed error");
                    }
                    else {
                        s3 = concat.concat("Break on error " + nextToken5);
                    }
                }
                else if (nextToken3.compareToIgnoreCase("c") == 0) {
                    String s4 = concat.concat(debuggerStringTokenizer.nextToken() + " ");
                    final String nextToken6 = debuggerStringTokenizer.nextToken();
                    vector2.add(nextToken6);
                    if (nextToken6.compareToIgnoreCase("0") != 0) {
                        s4 = s4.concat(" line #" + nextToken6 + " ");
                    }
                    s3 = s4.concat(debuggerStringTokenizer.nextToken());
                }
                else if (nextToken3.compareToIgnoreCase("w") == 0) {
                    s3 = concat.concat(" When " + debuggerStringTokenizer.nextToken() + " changes");
                    vector2.add("0");
                }
                else if (nextToken3.compareToIgnoreCase("x") == 0) {
                    s3 = concat.concat(" When " + debuggerStringTokenizer.nextToken() + " changes and " + debuggerStringTokenizer.nextToken());
                    vector2.add("0");
                }
                else {
                    s3 = concat.concat(debuggerStringTokenizer.nextToken());
                    final String nextToken7 = debuggerStringTokenizer.nextToken();
                    vector2.add(nextToken7);
                    if (nextToken7.compareToIgnoreCase("0") != 0) {
                        s3 = s3.concat(" line #" + nextToken7);
                    }
                }
                final JCheckBox obj2 = new JCheckBox("");
                this.breakpointProperties.addElement(nextToken4);
                if (nextToken4.compareToIgnoreCase("e") == 0) {
                    obj2.setSelected(true);
                }
                else {
                    obj2.setSelected(false);
                    this.disabledBreakpointsList.addElement(s3);
                }
                obj.addElement((String)obj2);
                obj.addElement(s3);
                this.breakpointList.addElement(obj);
            }
            final BreakpointDialogBox breakpointDialogBox = new BreakpointDialogBox(this.application, "Breakpoints", true, vector, vector2);
            final Dimension preferredSize = breakpointDialogBox.getPreferredSize();
            final Dimension size = this.application.getSize();
            final Point location = this.application.getLocation();
            breakpointDialogBox.setLocation((size.width - preferredSize.width) / 2 + location.x, (size.height - preferredSize.height) / 2 + location.y);
            breakpointDialogBox.show();
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_ENTER") == 0) {
            if (!this.receivedMSG_COMPLETE) {
                boolean b = false;
                if (this.labelName == null) {
                    this.labelName = "No Program Loaded";
                    b = true;
                }
                this.application.setHandleMessage(this);
                if (b && this.application.debuggerMode.compareToIgnoreCase("1") == 0) {
                    this.application.standAloneDisableButtonsAndMenus();
                }
                else {
                    this.application.enableButtonsAndMenus();
                }
                this.application.show();
                this.application.requestUpdates(true);
                sendSocket.sendMessage("show stack");
            }
            else {
                if (this.application.debuggerMode.compareToIgnoreCase("1") == 0) {
                    this.application.standAloneDisableButtonsAndMenus();
                }
                this.receivedMSG_COMPLETE = false;
            }
            this.application.toFront();
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_LEAVE") == 0) {
            this.application.disableButtonsAndMenus();
            return;
        }
        if (nextToken.compareToIgnoreCase("STACK") == 0) {
            final Vector<String> vector3 = new Vector<String>();
            while (debuggerStringTokenizer.hasMoreTokens()) {
                vector3.addElement(debuggerStringTokenizer.nextToken());
            }
            this.application.updateStackWindow(vector3);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_SHUTDOWN") == 0) {
            try {
                sendSocket.close();
            }
            catch (IOException ex) {}
            this.application.savePreferences();
            System.exit(0);
        }
        if (nextToken.compareToIgnoreCase("MSG_EXIT") == 0) {
            this.application.savePreferences();
            if (!this.application.menuFileExitSelected && !this.application.optionDetachSelected) {
                JOptionPane.showMessageDialog(this.application, "4GL Process is exiting. Detached from the process.");
            }
            this.lastFailedFileName = null;
        }
        if (nextToken.compareToIgnoreCase("MSG_VARIABLES") == 0) {
            final Vector<Vector<String>> vector4 = new Vector<Vector<String>>();
            final Vector<String> vector5 = new Vector<String>();
            debuggerStringTokenizer.countTokens();
            int countTokens;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens = debuggerStringTokenizer.countTokens()) > 3) {
                final String nextToken8 = debuggerStringTokenizer.nextToken();
                final String nextToken9 = debuggerStringTokenizer.nextToken();
                debuggerStringTokenizer.nextToken();
                final String nextToken10 = debuggerStringTokenizer.nextToken();
                final String nextToken11 = debuggerStringTokenizer.nextToken();
                final Vector<String> e = new Vector<String>();
                e.add(nextToken8);
                e.add(nextToken11);
                e.add(nextToken9);
                vector4.add(e);
                vector5.add(nextToken10);
            }
            this.application.updateVariablesPane(vector4, vector5);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_BUFFERS") == 0) {
            final Vector<Vector<String>> vector6 = new Vector<Vector<String>>();
            int countTokens2;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens2 = debuggerStringTokenizer.countTokens()) > 1) {
                final String nextToken12 = debuggerStringTokenizer.nextToken();
                final String nextToken13 = debuggerStringTokenizer.nextToken();
                final Vector<String> e2 = new Vector<String>();
                e2.add(nextToken12);
                e2.add(nextToken13);
                vector6.add(e2);
            }
            this.application.updateBuffersPane(vector6);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_PARAMETERS") == 0) {
            final Vector<Vector<String>> vector7 = new Vector<Vector<String>>();
            final Vector<String> vector8 = new Vector<String>();
            int countTokens3;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens3 = debuggerStringTokenizer.countTokens()) > 4) {
                final String nextToken14 = debuggerStringTokenizer.nextToken();
                final String nextToken15 = debuggerStringTokenizer.nextToken();
                debuggerStringTokenizer.nextToken();
                debuggerStringTokenizer.nextToken();
                final String nextToken16 = debuggerStringTokenizer.nextToken();
                final String nextToken17 = debuggerStringTokenizer.nextToken();
                final Vector<String> e3 = new Vector<String>();
                e3.add(nextToken15);
                e3.add(nextToken17);
                e3.add(nextToken14);
                vector7.add(e3);
                vector8.add(nextToken16);
            }
            this.application.updateParametersPane(vector7, vector8);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_TEMPTABLES") == 0) {
            final Vector<Vector<String>> vector9 = new Vector<Vector<String>>();
            int countTokens4;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens4 = debuggerStringTokenizer.countTokens()) > 1) {
                final String nextToken18 = debuggerStringTokenizer.nextToken();
                final String nextToken19 = debuggerStringTokenizer.nextToken();
                final Vector<String> e4 = new Vector<String>();
                e4.add(nextToken18);
                e4.add(nextToken19);
                vector9.add(e4);
            }
            this.application.updateTempTablesPane(vector9);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_DATASETS") == 0) {
            final Vector<Vector<String>> vector10 = new Vector<Vector<String>>();
            int countTokens5;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens5 = debuggerStringTokenizer.countTokens()) > 1) {
                final String nextToken20 = debuggerStringTokenizer.nextToken();
                final String nextToken21 = debuggerStringTokenizer.nextToken();
                final Vector<String> e5 = new Vector<String>();
                e5.add(nextToken20);
                e5.add(nextToken21);
                vector10.add(e5);
            }
            this.application.updateDatasetTablePane(vector10);
        }
        if (nextToken.compareToIgnoreCase("MSG_ATTRS") == 0) {
            final Vector vector11 = new Vector<Vector<String>>();
            final Vector<String> vector12 = new Vector<String>();
            final Vector<String> vector13 = new Vector<String>();
            final String nextToken22 = debuggerStringTokenizer.nextToken();
            int countTokens6;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens6 = debuggerStringTokenizer.countTokens()) > 3) {
                final String nextToken23 = debuggerStringTokenizer.nextToken();
                final String nextToken24 = debuggerStringTokenizer.nextToken();
                final String nextToken25 = debuggerStringTokenizer.nextToken();
                final String nextToken26 = debuggerStringTokenizer.nextToken();
                final Vector<String> e6 = new Vector<String>();
                e6.add(nextToken23);
                vector12.add(nextToken24);
                vector13.add(nextToken25);
                e6.add(nextToken26);
                e6.add(nextToken24);
                vector11.add(e6);
            }
            if (vector11.size() > 0) {
                this.application.setPropertySheetData(nextToken22, vector11, vector12, vector13, 0);
            }
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_ATTRS_ERR") == 0) {
            this.application.setErrorMessageDataView(debuggerStringTokenizer.nextToken(), 0);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_FIELDS") == 0) {
            final Vector vector14 = new Vector<Vector<String>>();
            final Vector<String> vector15 = new Vector<String>();
            final Vector<String> vector16 = new Vector<String>();
            final String nextToken27 = debuggerStringTokenizer.nextToken();
            int countTokens7;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens7 = debuggerStringTokenizer.countTokens()) > 3) {
                final String nextToken28 = debuggerStringTokenizer.nextToken();
                final String nextToken29 = debuggerStringTokenizer.nextToken();
                final String nextToken30 = debuggerStringTokenizer.nextToken();
                final String nextToken31 = debuggerStringTokenizer.nextToken();
                final Vector<String> e7 = new Vector<String>();
                e7.add(nextToken28);
                vector15.add(nextToken29);
                vector16.add(nextToken30);
                e7.add(nextToken31);
                e7.add(nextToken29);
                vector14.add(e7);
            }
            if (vector14.size() > 0) {
                this.application.setPropertySheetData(nextToken27, vector14, vector15, vector16, 1);
            }
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_FIELDS_ERR") == 0) {
            this.application.setErrorMessageDataView(debuggerStringTokenizer.nextToken(), 1);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_WATCHPOINTS") == 0) {
            final Vector<Vector<String>> vector17 = new Vector<Vector<String>>();
            final Vector<String> vector18 = new Vector<String>();
            final Vector<String> vector19 = new Vector<String>();
            int countTokens8;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens8 = debuggerStringTokenizer.countTokens()) > 2) {
                final String nextToken32 = debuggerStringTokenizer.nextToken();
                final String nextToken33 = debuggerStringTokenizer.nextToken();
                debuggerStringTokenizer.nextToken();
                debuggerStringTokenizer.nextToken();
                final String nextToken34 = debuggerStringTokenizer.nextToken();
                final String nextToken35 = debuggerStringTokenizer.nextToken();
                final Vector<String> e8 = new Vector<String>();
                e8.add(nextToken33);
                e8.add(nextToken35);
                vector17.add(e8);
                vector18.add(nextToken32);
                vector19.add(nextToken34);
            }
            final Vector<String> e9 = new Vector<String>();
            e9.add("");
            e9.add("");
            vector17.add(e9);
            this.application.updateWatchPoints(vector17, vector18, vector19);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_HIDE") == 0) {
            if (this.application.debuggerMode.compareTo("2") != 0) {
                if (this.application.dataViewDlg != null && this.application.dataViewDlg.isShowing()) {
                    this.application.dataViewDlg.jButton1ActionPerformed(null);
                }
                this.application.hide();
                this.application.debugDlgOpen = false;
            }
            if (this.application.dynamicDlg != null && this.application.dynamicDlg.isShowing()) {
                this.application.dynamicDlg.monitoring = false;
                this.application.dynamicDlg.dispose();
            }
            this.messageBoxText = "";
            this.needToDisplayMessageBox = false;
            if (this.application.animating) {
                this.application.jMenuItemStopAnimation_actionPerformed(null);
            }
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_DBG_MESSAGE") == 0) {
            if (this.messageBoxText == null) {
                this.messageBoxText = debuggerStringTokenizer.nextToken();
            }
            else {
                this.messageBoxText = this.messageBoxText.concat("\n");
                this.messageBoxText = this.messageBoxText.concat(debuggerStringTokenizer.nextToken());
            }
            this.needToDisplayMessageBox = true;
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_TRANSACTION") == 0) {
            if (debuggerStringTokenizer.nextToken().compareTo("1") == 0) {
                JOptionPane.showMessageDialog(this.application, "No transaction is active.");
            }
            else {
                JOptionPane.showMessageDialog(this.application, "A transaction is active.");
            }
            return;
        }
        final int index = removeReturnChar.indexOf("MSG_ERROR");
        if (index >= 0) {
            try {
                final int beginIndex = debuggerStringTokenizer.scanToken(index) + 1;
                JOptionPane.showMessageDialog(this.application, removeReturnChar.substring(beginIndex, debuggerStringTokenizer.scanToken(beginIndex)), "Error", 0);
            }
            catch (Exception ex2) {}
            if (this.application.getAttrOrGetFieldsRequest) {
                this.application.dataViewDlg.toFront();
                this.application.getAttrOrGetFieldsRequest = false;
            }
            else {
                this.application.toFront();
            }
            if (this.application.dataViewDlg != null && this.application.dataViewDlg.isShowing()) {
                this.application.dataViewDlg.stopWaitRequest();
            }
            if (index == 0) {
                debuggerStringTokenizer.nextToken();
                if (!debuggerStringTokenizer.hasMoreTokens()) {
                    return;
                }
                debuggerStringTokenizer.nextToken();
            }
        }
        if (nextToken.compareToIgnoreCase("MSG_COMPLETE") == 0) {
            this.receivedMSG_COMPLETE = true;
            if (this.focusLineNumber > 0) {
                final ImageIcon imageIcon = this.breakpointRowData.elementAt(this.focusLineNumber - 1).elementAt(0);
                if (imageIcon == this.breakfocusimage) {
                    this.breakpointRowData.elementAt(this.focusLineNumber - 1).clear();
                    this.breakpointRowData.elementAt(this.focusLineNumber - 1).addElement(this.breakimage);
                }
                else if (imageIcon == this.disabledbreakfocusimage) {
                    this.breakpointRowData.elementAt(this.focusLineNumber - 1).clear();
                    this.breakpointRowData.elementAt(this.focusLineNumber - 1).addElement(this.disabledbreakimage);
                }
                else {
                    this.breakpointRowData.elementAt(this.focusLineNumber - 1).clear();
                    this.breakpointRowData.elementAt(this.focusLineNumber - 1).addElement(this.empty);
                }
            }
            this.application.breakpointSelect.revalidate();
            this.application.breakpointSelect.repaint();
            this.application.standAloneDisableButtonsAndMenus();
            if (this.application.animating) {
                this.application.jMenuItemStopAnimation_actionPerformed(null);
            }
            this.application.toFront();
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_OBJLIST") == 0) {
            final Vector<CheckBoxNode> vector20 = new Vector<CheckBoxNode>();
            final Vector<String> vector21 = new Vector<String>();
            final Vector<CheckBoxNode> vector22 = new Vector<CheckBoxNode>();
            final Vector<String> vector23 = new Vector<String>();
            final Vector<CheckBoxNode> vector24 = new Vector<CheckBoxNode>();
            final Vector<String> vector25 = new Vector<String>();
            final Vector<CheckBoxNode> vector26 = new Vector<CheckBoxNode>();
            final Vector<String> vector27 = new Vector<String>();
            while (debuggerStringTokenizer.hasMoreTokens() && debuggerStringTokenizer.countTokens() > 2) {
                final String nextToken36 = debuggerStringTokenizer.nextToken();
                final String nextToken37 = debuggerStringTokenizer.nextToken();
                final CheckBoxNode checkBoxNode = new CheckBoxNode(debuggerStringTokenizer.nextToken(), false);
                switch (this.stringToInt(nextToken37)) {
                    case 2: {
                        vector20.addElement(checkBoxNode);
                        vector21.addElement(nextToken36);
                        continue;
                    }
                    case 1: {
                        vector22.addElement(checkBoxNode);
                        vector23.addElement(nextToken36);
                        continue;
                    }
                    case 3: {
                        vector24.addElement(checkBoxNode);
                        vector25.addElement(nextToken36);
                        continue;
                    }
                    case 4: {
                        vector26.addElement(checkBoxNode);
                        vector27.addElement(nextToken36);
                        continue;
                    }
                }
            }
            if (this.application.dynamicDlg == null) {
                (this.application.dynamicDlg = new DynamicObjectDialog(this.application, "stuff", false)).setObjects(vector20, vector22, vector24, vector26);
                this.application.dynamicDlg.setHexValues(vector21, vector23, vector25, vector27);
                this.application.dynamicDlg.show();
            }
            else if (this.application.dynamicDlg != null && !this.application.dynamicDlg.isVisible()) {
                this.application.dynamicDlg.monitoring = true;
                this.application.dynamicDlg.show();
                if (this.application.dynamicDlg.startedMonitoring()) {
                    sendSocket.sendMessage("GET-DYN-OBJS");
                }
            }
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_DYNOBJ") == 0) {
            final Vector objectTable = new Vector<Vector<String>>();
            if (debuggerStringTokenizer.countTokens() < 2) {
                this.application.dynamicDlg.clearObjectTable();
                return;
            }
            while (debuggerStringTokenizer.hasMoreTokens() && debuggerStringTokenizer.countTokens() > 2) {
                final Vector<String> obj3 = new Vector<String>();
                obj3.addElement(debuggerStringTokenizer.nextToken());
                obj3.addElement(debuggerStringTokenizer.nextToken());
                obj3.addElement(debuggerStringTokenizer.nextToken());
                obj3.addElement(debuggerStringTokenizer.nextToken());
                objectTable.addElement(obj3);
            }
            if (this.application.dynamicDlg != null && objectTable.size() > 0) {
                this.application.dynamicDlg.setObjectTable(objectTable);
            }
        }
        if (nextToken.compareToIgnoreCase("MSG_STATUS") == 0) {
            String text = debuggerStringTokenizer.nextToken();
            if (text.compareToIgnoreCase("MSG_NO_SOURCE") == 0) {
                if (this.application.animating) {
                    this.application.jMenuItemStopAnimation_actionPerformed(null);
                }
                text = debuggerStringTokenizer.nextToken();
            }
            this.application.statusBar.setText(text);
        }
        if (nextToken.compareToIgnoreCase("MSG_HELPFILE") == 0) {
            debuggerStringTokenizer.nextToken();
        }
        if (nextToken.compareToIgnoreCase("MSG_INFO") == 0) {
            String message = debuggerStringTokenizer.nextToken();
            if (message.compareToIgnoreCase("MSG_NO_SOURCE") == 0) {
                if (this.application.animating) {
                    this.application.jMenuItemStopAnimation_actionPerformed(null);
                }
                message = debuggerStringTokenizer.nextToken();
            }
            JOptionPane.showMessageDialog(this.application, message);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_ARRAY") == 0) {
            final Vector<Vector<String>> vector28 = new Vector<Vector<String>>();
            final Vector<String> vector29 = new Vector<String>();
            final String nextToken38 = debuggerStringTokenizer.nextToken();
            int countTokens9;
            while (debuggerStringTokenizer.hasMoreTokens() && (countTokens9 = debuggerStringTokenizer.countTokens()) > 2) {
                final String nextToken39 = debuggerStringTokenizer.nextToken();
                final String nextToken40 = debuggerStringTokenizer.nextToken();
                final String nextToken41 = debuggerStringTokenizer.nextToken();
                final Vector<String> e10 = new Vector<String>();
                e10.add(nextToken39);
                vector29.add(nextToken40);
                e10.add(nextToken41);
                vector28.add(e10);
            }
            this.application.setPropertySheetArrayData(nextToken38, vector28, vector29);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_ARRAY_ERR") == 0) {
            this.application.setErrorMessageDataView(debuggerStringTokenizer.nextToken(), 2);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_VALUE") == 0) {
            this.application.setPropertySheetValueData(debuggerStringTokenizer.nextToken(), debuggerStringTokenizer.nextToken(), debuggerStringTokenizer.nextToken());
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_VALUE_ERR") == 0) {
            this.application.setErrorMessageDataView(debuggerStringTokenizer.nextToken(), 3);
            return;
        }
        if (nextToken.compareToIgnoreCase("MSG_SENDPROP") == 0) {
            this.application.sendSocket.sendMessage(this.application.sendPropMessage);
        }
    }
    
    public String removeReturnChar(final String s) {
        String s2 = s;
        for (int i = s2.indexOf("\n"); i != -1; i = s2.indexOf("\n")) {
            final StringBuffer sb = new StringBuffer();
            sb.append(s2);
            sb.deleteCharAt(i);
            s2 = sb.toString();
        }
        for (int j = s2.indexOf("\u0000"); j != -1; j = s2.indexOf("\u0000")) {
            final StringBuffer sb2 = new StringBuffer();
            sb2.append(s2);
            sb2.deleteCharAt(j);
            s2 = sb2.toString();
        }
        return s2;
    }
    
    void addLineNumberWhiteSpace(final StringBuffer sb) {
        switch (sb.length()) {
            case 2: {
                sb.append("      ");
                break;
            }
            case 3: {
                sb.append("     ");
                break;
            }
            case 4: {
                sb.append("    ");
                break;
            }
            case 5: {
                sb.append("   ");
                break;
            }
            default: {
                sb.append("       ");
                break;
            }
        }
    }
    
    String findFileSpecified(final String s) {
        String s2 = "error";
        try {
            final FileInputStream fileInputStream = new FileInputStream(s);
            s2 = s;
            fileInputStream.close();
            return s2;
        }
        catch (Exception ex) {
            for (int i = 0; i < this.application.debuggerListingDirs.size(); ++i) {
                final String concat = (this.application.debuggerListingDirs.elementAt(i).toString() + System.getProperty("file.separator")).concat(s);
                try {
                    final FileInputStream fileInputStream2 = new FileInputStream(concat);
                    s2 = concat;
                    fileInputStream2.close();
                }
                catch (Exception ex2) {
                    s2 = "error";
                }
                if (s2.compareTo("error") != 0) {
                    break;
                }
            }
            return s2;
        }
    }
    
    void setPropMessage(final String sendPropMessage) {
        this.sendPropMessage = sendPropMessage;
    }
    
    static {
        FILE_SEPARATOR = System.getProperty("file.separator");
    }
}
