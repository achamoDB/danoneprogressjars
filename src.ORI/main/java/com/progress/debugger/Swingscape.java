// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.undo.UndoableEdit;
import java.util.Vector;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.AbstractUndoableEdit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.event.HyperlinkEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JScrollPane;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkListener;
import javax.swing.JFrame;

public class Swingscape extends JFrame implements HyperlinkListener
{
    public static final String TITLE_TEXT = "Debugger Help";
    public String HOME_DEFAULT;
    protected String m_CurrentURL;
    protected JEditorPane m_HtmlPane;
    protected JToolBar m_toolBar;
    protected JButton m_btnBack;
    protected JButton m_btnForward;
    protected JMenuItem m_mnuBack;
    protected JMenuItem m_mnuForward;
    protected JTextField m_txtURL;
    protected URLundoManager m_undo;
    
    public Swingscape() {
        super("Debugger Help");
        this.HOME_DEFAULT = "http://www.progress.com";
        this.m_CurrentURL = this.HOME_DEFAULT;
        this.m_undo = new URLundoManager();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (screenSize.width <= 640) {
            this.setSize(screenSize.width, screenSize.height);
        }
        else {
            final int width = screenSize.width - screenSize.width / 10;
            final int height = screenSize.height - screenSize.height / 10;
            this.setSize(width, height);
            this.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
        }
        this.setJMenuBar(this.createMenuBar());
        this.getContentPane().add(this.m_toolBar, "North");
        (this.m_HtmlPane = new JEditorPane()).setEditorKit(new HTMLEditorKit());
        this.m_HtmlPane.setEditable(false);
        this.m_HtmlPane.addHyperlinkListener(this);
        final JScrollPane comp = new JScrollPane();
        comp.getViewport().add(this.m_HtmlPane);
        this.getContentPane().add(comp, "Center");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                Swingscape.this.dispose();
            }
        });
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("debug.gif")));
        this.setVisible(true);
    }
    
    public void setHomeDefault(final String home_DEFAULT) {
        this.HOME_DEFAULT = home_DEFAULT;
    }
    
    protected JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu c = new JMenu("File");
        c.setMnemonic('f');
        final JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic('x');
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                Swingscape.this.dispose();
            }
        });
        c.add(menuItem);
        menuBar.add(c);
        final JMenu c2 = new JMenu("Go");
        c2.setMnemonic('g');
        (this.m_mnuBack = new JMenuItem("Back")).setMnemonic('b');
        this.m_mnuBack.setEnabled(false);
        final AbstractAction abstractAction = new AbstractAction("Back") {
            public void actionPerformed(final ActionEvent actionEvent) {
                new Thread() {
                    public void run() {
                        try {
                            final String swapURL = Swingscape.this.m_undo.swapURL(Swingscape.this.m_CurrentURL);
                            Swingscape.this.m_undo.undo();
                            Swingscape.this.DisplayPageDirect(swapURL);
                        }
                        catch (CannotUndoException ex) {}
                        finally {
                            Swingscape.this.updateMenu_Buttons();
                        }
                    }
                }.start();
            }
        };
        this.m_mnuBack.addActionListener(abstractAction);
        c2.add(this.m_mnuBack);
        (this.m_mnuForward = new JMenuItem("Forward")).setMnemonic('f');
        this.m_mnuForward.setEnabled(false);
        final AbstractAction abstractAction2 = new AbstractAction("Forward") {
            public void actionPerformed(final ActionEvent actionEvent) {
                new Thread() {
                    public void run() {
                        try {
                            Swingscape.this.m_undo.redo();
                            Swingscape.this.DisplayPageDirect(Swingscape.this.m_undo.swapURL(Swingscape.this.m_CurrentURL));
                        }
                        catch (CannotRedoException ex) {}
                        finally {
                            Swingscape.this.updateMenu_Buttons();
                        }
                    }
                }.start();
            }
        };
        this.m_mnuForward.addActionListener(abstractAction2);
        c2.add(this.m_mnuForward);
        final JMenuItem menuItem2 = new JMenuItem("Index");
        menuItem2.setMnemonic('h');
        final AbstractAction abstractAction3 = new AbstractAction("Index") {
            public void actionPerformed(final ActionEvent actionEvent) {
                new Thread() {
                    public void run() {
                        Swingscape.this.Display_RecordUndo(Swingscape.this.HOME_DEFAULT);
                    }
                }.start();
            }
        };
        menuItem2.addActionListener(abstractAction3);
        c2.add(menuItem2);
        menuBar.add(c2);
        this.m_toolBar = new JToolBar();
        (this.m_btnBack = this.m_toolBar.add(abstractAction)).setEnabled(false);
        this.m_btnBack.setBorderPainted(false);
        this.m_btnBack.setRequestFocusEnabled(false);
        this.m_btnBack.addMouseListener(new PopButtonListener());
        this.m_toolBar.addSeparator();
        (this.m_btnForward = this.m_toolBar.add(abstractAction2)).setEnabled(false);
        this.m_btnForward.setBorderPainted(false);
        this.m_btnForward.setRequestFocusEnabled(false);
        this.m_btnForward.addMouseListener(new PopButtonListener());
        this.m_toolBar.addSeparator();
        this.m_toolBar.addSeparator();
        final JButton add = this.m_toolBar.add(abstractAction3);
        add.setBorderPainted(false);
        add.setRequestFocusEnabled(false);
        add.addMouseListener(new PopButtonListener());
        this.m_toolBar.addSeparator();
        (this.m_txtURL = new JTextField(this.HOME_DEFAULT)).setEditable(false);
        this.m_txtURL.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                new Thread() {
                    public void run() {
                        String s = Swingscape.this.m_txtURL.getText().trim();
                        if (s.length() > 0) {
                            final String lowerCase = s.substring(0, 7).toLowerCase();
                            if (!lowerCase.equals("http://") && !lowerCase.startsWith("file:/")) {
                                if (lowerCase.indexOf(58) == 1) {
                                    s = "file:/" + s;
                                }
                                else {
                                    s = "http://" + s;
                                }
                            }
                            Swingscape.this.Display_RecordUndo(s);
                        }
                    }
                }.start();
            }
        });
        this.m_toolBar.setFloatable(false);
        return menuBar;
    }
    
    public void hyperlinkUpdate(final HyperlinkEvent hyperlinkEvent) {
        if (hyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            new Thread() {
                private final /* synthetic */ String val$dest = hyperlinkEvent.getURL().toString();
                
                public void run() {
                    Swingscape.this.Display_RecordUndo(this.val$dest);
                }
            }.start();
        }
    }
    
    public void updateMenu_Buttons() {
        final boolean canUndo = this.m_undo.canUndo();
        this.m_mnuBack.setEnabled(canUndo);
        this.m_btnBack.setEnabled(canUndo);
        final boolean canRedo = this.m_undo.canRedo();
        this.m_mnuForward.setEnabled(canRedo);
        this.m_btnForward.setEnabled(canRedo);
    }
    
    public void Display_RecordUndo(final String s) {
        final String intern = s.intern();
        if (this.m_CurrentURL != intern) {
            this.m_undo.addURL(this.m_CurrentURL);
            this.updateMenu_Buttons();
            this.DisplayPageDirect(intern);
        }
    }
    
    public void DisplayPageDirect(final String page) {
        this.m_CurrentURL = page;
        this.m_txtURL.setText(page);
        try {
            this.m_HtmlPane.setPage(page);
        }
        catch (Exception ex) {}
    }
    
    public static void main(final String[] array) {
        new Swingscape();
    }
    
    class PopButtonListener extends MouseAdapter
    {
        public void mouseEntered(final MouseEvent mouseEvent) {
            ((JButton)mouseEvent.getSource()).setBorderPainted(true);
        }
        
        public void mouseExited(final MouseEvent mouseEvent) {
            ((JButton)mouseEvent.getSource()).setBorderPainted(false);
        }
    }
    
    class UndoableURL extends AbstractUndoableEdit
    {
        private String m_URL;
        
        public UndoableURL(final String url) {
            this.m_URL = url;
        }
        
        public String getPresentationName() {
            return this.m_URL;
        }
    }
    
    class URLundoManager extends CompoundEdit
    {
        int m_IdxAdd;
        
        URLundoManager() {
            this.m_IdxAdd = 0;
        }
        
        public String getUndoPresentationName() {
            return super.edits.elementAt(this.m_IdxAdd - 1).getPresentationName();
        }
        
        public String getRedoPresentationName() {
            return super.edits.elementAt(this.m_IdxAdd).getPresentationName();
        }
        
        public void addURL(final String s) {
            if (super.edits.size() > this.m_IdxAdd) {
                super.edits.setElementAt(new UndoableURL(s), this.m_IdxAdd++);
                for (int i = this.m_IdxAdd; i < super.edits.size(); ++i) {
                    super.edits.removeElementAt(i);
                }
            }
            else {
                super.edits.addElement(new UndoableURL(s));
                ++this.m_IdxAdd;
            }
        }
        
        public String swapURL(final String s) {
            final String undoPresentationName = this.getUndoPresentationName();
            super.edits.setElementAt(new UndoableURL(s), this.m_IdxAdd - 1);
            return undoPresentationName;
        }
        
        public synchronized boolean canUndo() {
            if (this.m_IdxAdd > 0) {
                final UndoableURL undoableURL = super.edits.elementAt(this.m_IdxAdd - 1);
                return undoableURL != null && undoableURL.canUndo();
            }
            return false;
        }
        
        public synchronized boolean canRedo() {
            if (super.edits.size() > this.m_IdxAdd) {
                final UndoableURL undoableURL = super.edits.elementAt(this.m_IdxAdd);
                return undoableURL != null && undoableURL.canRedo();
            }
            return false;
        }
        
        public synchronized void undo() throws CannotUndoException {
            final Vector<UndoableEdit> edits = (Vector<UndoableEdit>)super.edits;
            final int n = this.m_IdxAdd - 1;
            this.m_IdxAdd = n;
            edits.elementAt(n).undo();
        }
        
        public synchronized void redo() throws CannotRedoException {
            super.edits.elementAt(this.m_IdxAdd++).redo();
        }
    }
}
