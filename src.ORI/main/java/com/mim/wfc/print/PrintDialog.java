// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.IComponentSite;
import com.ms.win32.Comdlg32;
import com.ms.dll.DllLib;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.Control;
import com.ms.win32.Gdi32;
import com.ms.win32.Kernel32;
import com.ms.win32.PRINTDLG;
import com.ms.wfc.core.Component;

public class PrintDialog extends Component
{
    private PRINTDLG \u0121;
    private static Class \u0122;
    
    private void \u0114() {
        if (this.\u0121.hDevNames != 0) {
            Kernel32.GlobalFree(this.\u0121.hDevNames);
            this.\u0121.hDevNames = 0;
        }
        if (this.\u0121.hDevMode != 0) {
            Kernel32.GlobalFree(this.\u0121.hDevMode);
            this.\u0121.hDevMode = 0;
        }
        if (this.\u0121.hDC != 0) {
            Gdi32.DeleteDC(this.\u0121.hDC);
            this.\u0121.hDC = 0;
        }
    }
    
    public PrintDialog() {
        this(null);
    }
    
    public PrintDialog(final Control control) {
        _cls753A._mth821F();
        this.\u0121 = new PRINTDLG();
        this.\u0121.lStructSize = DllLib.sizeOf((PrintDialog.\u0122 != null) ? PrintDialog.\u0122 : (PrintDialog.\u0122 = \u00c6("com.ms.win32.PRINTDLG")));
        this.\u0121.Flags = 1310988;
        this.\u0121.nCopies = 1;
        if (control != null) {
            this.\u0121.hwndOwner = control.getHandle();
        }
    }
    
    PRINTDLG \u0121() {
        return this.\u0121;
    }
    
    public void setPrintToFile(final boolean b) {
        if (b) {
            final PRINTDLG \u0121 = this.\u0121;
            \u0121.Flags |= 0x20;
            return;
        }
        final PRINTDLG \u01212 = this.\u0121;
        \u01212.Flags &= 0xFFFFFFDF;
    }
    
    public boolean getPrintToFile() {
        return (this.\u0121.Flags & 0x20) != 0x0;
    }
    
    public void setFromToPages(final int n, final int n2) {
        final PRINTDLG \u0121 = this.\u0121;
        \u0121.Flags &= 0xFFFFFFF7;
        this.\u0121.nFromPage = (short)n;
        this.\u0121.nToPage = (short)n2;
    }
    
    public int getFromPage() {
        return this.\u0121.nFromPage;
    }
    
    public void setRangeType(final int n) {
        final PRINTDLG \u0121 = this.\u0121;
        \u0121.Flags &= 0xFFFFFFFC;
        final PRINTDLG \u01212 = this.\u0121;
        \u01212.Flags |= n;
    }
    
    public int getRangeType() {
        return this.\u0121.Flags & 0x3;
    }
    
    public int getToPage() {
        return this.\u0121.nToPage;
    }
    
    public void setCollate(final boolean b) {
        if (b) {
            final PRINTDLG \u0121 = this.\u0121;
            \u0121.Flags |= 0x10;
            return;
        }
        final PRINTDLG \u01212 = this.\u0121;
        \u01212.Flags &= 0xFFFFFFEF;
    }
    
    public boolean getCollate() {
        return (this.\u0121.Flags & 0x10) != 0x0;
    }
    
    public boolean getDefault() {
        final PRINTDLG \u0121 = this.\u0121;
        \u0121.Flags |= 0x400;
        final boolean printDlg = Comdlg32.PrintDlg(this.\u0121);
        final PRINTDLG \u01212 = this.\u0121;
        \u01212.Flags &= 0xFFFFFBFF;
        return printDlg;
    }
    
    public Printer getPrinter() {
        final Printer printer = new Printer(this.\u0121.hDC);
        this.\u0121.hDC = 0;
        return printer;
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public void setCopies(final int n) {
        this.\u0121.nCopies = (short)n;
    }
    
    protected void finalize() {
        this.dispose();
    }
    
    public void dispose() {
        this.\u0114();
    }
    
    public int getCopies() {
        return this.\u0121.nCopies;
    }
    
    public void setNetworkButton(final boolean b) {
        if (b) {
            final PRINTDLG \u0121 = this.\u0121;
            \u0121.Flags &= 0xFFDFFFFF;
            return;
        }
        final PRINTDLG \u01212 = this.\u0121;
        \u01212.Flags |= 0x200000;
    }
    
    public void setMinMaxPages(final int n, final int n2) {
        final PRINTDLG \u0121 = this.\u0121;
        \u0121.Flags &= 0xFFFFFFF7;
        this.\u0121.nMinPage = (short)n;
        this.\u0121.nMaxPage = (short)n2;
    }
    
    public boolean getNetworkButton() {
        return (this.\u0121.Flags & 0x200000) == 0x0;
    }
    
    public void setComponentSite(final IComponentSite componentSite) {
        super.setComponentSite(componentSite);
        _cls753A._mth563B(this);
    }
    
    public boolean display() {
        return Comdlg32.PrintDlg(this.\u0121);
    }
}
