// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.IComponentSite;
import com.ms.win32.Comdlg32;
import com.ms.dll.DllLib;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Point;
import com.ms.win32.Kernel32;
import com.ms.win32.PAGESETUPDLG;
import com.ms.wfc.core.Component;

public class PageSetupDialog extends Component
{
    private PAGESETUPDLG \u0114;
    private static Class \u0115;
    
    private void \u0114() {
        if (this.\u0114.hDevNames != 0) {
            Kernel32.GlobalFree(this.\u0114.hDevNames);
            this.\u0114.hDevNames = 0;
        }
        if (this.\u0114.hDevMode != 0) {
            Kernel32.GlobalFree(this.\u0114.hDevMode);
            this.\u0114.hDevMode = 0;
        }
    }
    
    public Point getPaperSize() {
        return new Point(this.\u0114.ptPaperSize_x, this.\u0114.ptPaperSize_y);
    }
    
    public void setPrinterButton(final boolean b) {
        if (b) {
            final PAGESETUPDLG \u0115 = this.\u0114;
            \u0115.Flags &= 0xFFFFFFDF;
            return;
        }
        final PAGESETUPDLG \u01152 = this.\u0114;
        \u01152.Flags |= 0x20;
    }
    
    public boolean getPrinterButton() {
        return (this.\u0114.Flags & 0x20) == 0x0;
    }
    
    public int getBottomMargin() {
        return this.\u0114.rtMargin_bottom;
    }
    
    public PageSetupDialog() {
        this(null);
    }
    
    public PageSetupDialog(final Control control) {
        _cls753A._mth821F();
        this.\u0114 = new PAGESETUPDLG();
        this.\u0114.lStructSize = DllLib.sizeOf((PageSetupDialog.\u0115 != null) ? PageSetupDialog.\u0115 : (PageSetupDialog.\u0115 = \u00c6("com.ms.win32.PAGESETUPDLG")));
        this.\u0114.Flags = 0;
        if (control != null) {
            this.\u0114.hwndOwner = control.getHandle();
        }
        this.getDefault();
    }
    
    public int getTopMargin() {
        return this.\u0114.rtMargin_top;
    }
    
    public void setPaperControls(final boolean b) {
        if (b) {
            final PAGESETUPDLG \u0115 = this.\u0114;
            \u0115.Flags &= 0xFFFFFDFF;
            return;
        }
        final PAGESETUPDLG \u01152 = this.\u0114;
        \u01152.Flags |= 0x200;
    }
    
    public void setOrientationControls(final boolean b) {
        if (b) {
            final PAGESETUPDLG \u0115 = this.\u0114;
            \u0115.Flags &= 0xFFFFFEFF;
            return;
        }
        final PAGESETUPDLG \u01152 = this.\u0114;
        \u01152.Flags |= 0x100;
    }
    
    public boolean getOrientationControls() {
        return (this.\u0114.Flags & 0x100) == 0x0;
    }
    
    public boolean getPaperControls() {
        return (this.\u0114.Flags & 0x200) == 0x0;
    }
    
    public boolean getDefault() {
        final PAGESETUPDLG \u0115 = this.\u0114;
        \u0115.Flags |= 0x400;
        final boolean pageSetupDlg = Comdlg32.PageSetupDlg(this.\u0114);
        final PAGESETUPDLG \u01152 = this.\u0114;
        \u01152.Flags &= 0xFFFFFBFF;
        return pageSetupDlg;
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public boolean getUnitInches() {
        return (this.\u0114.Flags & 0x8) == 0x0;
    }
    
    protected void finalize() {
        this.dispose();
    }
    
    public void dispose() {
        this.\u0114();
        super.dispose();
    }
    
    public void setNetworkButton(final boolean b) {
        if (b) {
            final PAGESETUPDLG \u0115 = this.\u0114;
            \u0115.Flags &= 0xFFDFFFFF;
            return;
        }
        final PAGESETUPDLG \u01152 = this.\u0114;
        \u01152.Flags |= 0x200000;
    }
    
    public boolean getNetworkButton() {
        return (this.\u0114.Flags & 0x200000) == 0x0;
    }
    
    public void setComponentSite(final IComponentSite componentSite) {
        super.setComponentSite(componentSite);
        _cls753A._mth563B(this);
    }
    
    public boolean display() {
        final boolean pageSetupDlg = Comdlg32.PageSetupDlg(this.\u0114);
        if (pageSetupDlg) {
            final PAGESETUPDLG \u0115 = this.\u0114;
            \u0115.Flags |= 0x2;
        }
        return pageSetupDlg;
    }
    
    public void setMinMargin(final int rtMinMargin_left, final int rtMinMargin_top, final int rtMinMargin_right, final int rtMinMargin_bottom) {
        this.\u0114.rtMinMargin_left = rtMinMargin_left;
        this.\u0114.rtMinMargin_right = rtMinMargin_right;
        this.\u0114.rtMinMargin_top = rtMinMargin_top;
        this.\u0114.rtMinMargin_bottom = rtMinMargin_bottom;
    }
    
    public void setMargin(final int rtMargin_left, final int rtMargin_top, final int rtMargin_right, final int rtMargin_bottom) {
        this.\u0114.rtMargin_left = rtMargin_left;
        this.\u0114.rtMargin_right = rtMargin_right;
        this.\u0114.rtMargin_top = rtMargin_top;
        this.\u0114.rtMargin_bottom = rtMargin_bottom;
        final PAGESETUPDLG \u0115 = this.\u0114;
        \u0115.Flags |= 0x2;
    }
    
    public int getLeftMargin() {
        return this.\u0114.rtMargin_left;
    }
    
    public int getRightMargin() {
        return this.\u0114.rtMargin_right;
    }
    
    public void setMarginControls(final boolean b) {
        if (b) {
            final PAGESETUPDLG \u0115 = this.\u0114;
            \u0115.Flags &= 0xFFFFFFEF;
            return;
        }
        final PAGESETUPDLG \u01152 = this.\u0114;
        \u01152.Flags |= 0x10;
    }
    
    public boolean getMarginControls() {
        return (this.\u0114.Flags & 0x10) == 0x0;
    }
    
    PAGESETUPDLG \u0115() {
        return this.\u0114;
    }
}
