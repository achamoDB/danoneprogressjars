// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.shell;

import com.ms.win32.User32;
import com.ms.dll.Callback;
import com.ms.dll.DllLib;
import com.ms.dll.Root;
import com.ms.wfc.ui.Control;
import com.mim.wfc.license._cls753A;

public class ShellBrowser
{
    private String p_Title;
    private String p_StartDirectory;
    static final int BFFM_INITIALIZED = 1;
    private static final int BFFM_SELCHANGED = 2;
    private static final int BFFM_VALIDATEFAILED = 3;
    private static final int BFFM_SETSTATUSTEXT = 1124;
    private static final int BFFM_ENABLEOK = 1125;
    static final int BFFM_SETSELECTION = 1126;
    private static final int BIF_RETURNONLYFSDIRS = 1;
    private static final int BIF_DONTGOBELOWDOMAIN = 2;
    private static final int BIF_STATUSTEXT = 4;
    private static final int BIF_RETURNFSANCESTORS = 8;
    private static final int BIF_EDITBOX = 16;
    private static final int BIF_VALIDATE = 32;
    private static final int BIF_BROWSEFORCOMPUTER = 4096;
    private static final int BIF_BROWSEFORPRINTER = 8192;
    private static final int BIF_BROWSEINCLUDEFILES = 16384;
    
    public ShellBrowser() {
        _cls753A._mth821F();
    }
    
    public ShellBrowser(final String title, final String startDirectory) {
        this();
        this.setTitle(title);
        this.setStartDirectory(startDirectory);
    }
    
    private static native int SHGetPathFromIDList(final int p0, final StringBuffer p1);
    
    public String browse(final Control control) {
        final SHBROWSEINFO shbrowseinfo = new SHBROWSEINFO();
        shbrowseinfo.hWndOwner = ((control != null) ? control.getHandle() : 0);
        shbrowseinfo.ulFlags = 1;
        if (this.p_Title != null) {
            shbrowseinfo.lpszTitle = this.p_Title;
        }
        shbrowseinfo.lpfnCallback = DllLib.addrOf(Root.alloc((Object)new BrowseCallback()));
        if (this.p_StartDirectory != null) {
            shbrowseinfo.lParam = DllLib.stringToHGlobalAnsi(this.p_StartDirectory);
        }
        String string = null;
        final int shBrowseForFolder = SHBrowseForFolder(shbrowseinfo);
        if (shBrowseForFolder != 0) {
            final StringBuffer sb = new StringBuffer(500);
            SHGetPathFromIDList(shBrowseForFolder, sb);
            string = sb.toString();
        }
        if (shbrowseinfo.lParam != 0) {
            DllLib.freeHGlobal(shbrowseinfo.lParam);
        }
        return string;
    }
    
    public void setTitle(final String p_Title) {
        this.p_Title = p_Title;
    }
    
    private static native int SHBrowseForFolder(final SHBROWSEINFO p0);
    
    public void setStartDirectory(final String p_StartDirectory) {
        this.p_StartDirectory = p_StartDirectory;
    }
    
    private class SHBROWSEINFO
    {
        public int hWndOwner;
        public int pIDLRoot;
        public int pszDisplayName;
        public String lpszTitle;
        public int ulFlags;
        public int lpfnCallback;
        public int lParam;
        public int iImage;
        
        SHBROWSEINFO() {
            ShellBrowser.this.getClass();
        }
    }
    
    class BrowseCallback extends Callback
    {
        BrowseCallback() {
            ShellBrowser.this.getClass();
        }
        
        public int callback(final int n, final int n2, final int n3, final int n4) {
            if (n2 == 1 && n4 != 0) {
                User32.SendMessage(n, 1126, 1, n4);
            }
            return 0;
        }
    }
}
