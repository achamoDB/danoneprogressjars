// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.tools;

import com.sonicsw.mx.config.util.SonicFSFile;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import com.sonicsw.mx.config.util.SonicFSFileSystem;
import com.progress.wsa.admin.AppContainer;

public class OEServiceDefEditor
{
    protected AppContainer m_theApp;
    protected SonicFSFileSystem m_fs;
    protected String m_currentPath;
    
    public OEServiceDefEditor() {
        this.m_theApp = null;
        this.m_fs = null;
        this.m_currentPath = null;
    }
    
    public boolean editInDialog(final Window locationRelativeTo, final AppContainer theApp, final boolean b) {
        this.m_theApp = theApp;
        OEServiceDlg oeServiceDlg = null;
        if (locationRelativeTo instanceof Frame) {
            oeServiceDlg = new OEServiceDlg((Frame)locationRelativeTo, "Edit OpenEdge Service Definition", true, this.m_theApp, this.m_fs, this.m_currentPath);
        }
        else if (locationRelativeTo instanceof Dialog) {
            oeServiceDlg = new OEServiceDlg((Dialog)locationRelativeTo, "Edit OpenEdge Service Definition", true, this.m_theApp, this.m_fs, this.m_currentPath);
        }
        oeServiceDlg.setDefaultCloseOperation(2);
        oeServiceDlg.setSize(400, 300);
        oeServiceDlg.addNotify();
        oeServiceDlg.validate();
        oeServiceDlg.setSize(oeServiceDlg.getPreferredSize());
        oeServiceDlg.setLocationRelativeTo(locationRelativeTo);
        oeServiceDlg.setVisible(true);
        return oeServiceDlg.isPropListDirty();
    }
    
    public AppContainer getAppContainer() {
        return this.m_theApp;
    }
    
    public void setFileSystem(final SonicFSFileSystem fs) {
        this.m_fs = fs;
    }
    
    public void setCurrentPath(final String s) {
        if (s != null) {
            this.m_currentPath = SonicFSFile.toParent(s);
            System.out.println("Current path = " + this.m_currentPath);
        }
    }
}
