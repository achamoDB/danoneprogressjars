// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.win32.PRINTER_DEFAULTS;
import com.ms.win32.Spoolss;
import com.mim.wfc.license._cls753A;
import com.ms.win32.SIZE;
import com.mim.wfc.ui.UIUtil;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Point;
import com.ms.win32.PAGESETUPDLG;
import com.ms.wfc.ui.Bitmap;
import com.ms.win32.DOCINFO;
import com.ms.win32.DEVMODE;
import com.ms.win32.Gdi32;
import com.ms.win32.Comdlg32;
import com.ms.win32.PRINTDLG;
import com.ms.win32.DEVNAMES;
import com.ms.dll.DllLib;
import com.ms.win32.Kernel32;

public class Printer
{
    private int \u0125;
    private int \u0126;
    private int \u0127;
    private boolean \u0128;
    private boolean \u0129;
    private int \u012a;
    private int \u012b;
    private int \u012c;
    private int \u012d;
    private int \u012e;
    private int \u012f;
    private boolean \u0130;
    public static final int ORIENT_PORTRAIT = 1;
    public static final int ORIENT_LANDSCAPE = 2;
    public static final int PAPER_LETTER = 1;
    public static final int PAPER_LEGAL = 5;
    public static final int PAPER_A4 = 9;
    public static final int PAPER_CSHEET = 24;
    public static final int PAPER_DSHEET = 25;
    public static final int PAPER_ESHEET = 26;
    public static final int PAPER_LETTERSMALL = 2;
    public static final int PAPER_TABLOID = 3;
    public static final int PAPER_LEDGER = 4;
    public static final int PAPER_STATEMENT = 6;
    public static final int PAPER_EXECUTIVE = 7;
    public static final int PAPER_A3 = 8;
    public static final int PAPER_A4SMALL = 10;
    public static final int PAPER_A5 = 11;
    public static final int PAPER_B4 = 12;
    public static final int PAPER_B5 = 13;
    public static final int PAPER_FOLIO = 14;
    public static final int PAPER_QUARTO = 15;
    public static final int PAPER_10X14 = 16;
    public static final int PAPER_11X17 = 17;
    public static final int PAPER_NOTE = 18;
    public static final int PAPER_ENV_9 = 19;
    public static final int PAPER_ENV_10 = 20;
    public static final int PAPER_ENV_11 = 21;
    public static final int PAPER_ENV_12 = 22;
    public static final int PAPER_ENV_14 = 23;
    public static final int PAPER_ENV_DL = 27;
    public static final int PAPER_ENV_C5 = 28;
    public static final int PAPER_ENV_C3 = 29;
    public static final int PAPER_ENV_C4 = 30;
    public static final int PAPER_ENV_C6 = 31;
    public static final int PAPER_ENV_C65 = 32;
    public static final int PAPER_ENV_B4 = 33;
    public static final int PAPER_ENV_B5 = 34;
    public static final int PAPER_ENV_B6 = 35;
    public static final int PAPER_ENV_ITALY = 36;
    public static final int PAPER_ENV_MONARCH = 37;
    public static final int PAPER_ENV_PERSONAL = 38;
    public static final int PAPER_FANFOLD_US = 39;
    public static final int PAPER_FANFOLD_STD_GERMAN = 40;
    public static final int PAPER_FANFOLD_LGL_GERMAN = 41;
    public static final int RES_HIGH = -4;
    public static final int RES_MEDIUM = -3;
    public static final int RES_LOW = -2;
    public static final int RES_DRAFT = -1;
    public static final int COLOR_COLOR = 2;
    public static final int COLOR_MONOCHROME = 1;
    public static final int DUP_SIMPLEX = 1;
    public static final int DUP_HORIZONTAL = 3;
    public static final int DUP_VERTICAL = 2;
    public static final int TT_BITMAP = 1;
    public static final int TT_DOWNLOAD = 2;
    public static final int TT_SUBDEV = 3;
    public static final int MEDIA_STANDARD = 1;
    public static final int MEDIA_GLOSSY = 3;
    public static final int MEDIA_TRANSPARENCY = 2;
    public static final int MEDIA_USER = 256;
    public static final int DITHER_NONE = 1;
    public static final int DITHER_COARSE = 2;
    public static final int DITHER_FINE = 3;
    public static final int DITHER_LINEART = 4;
    public static final int DITHER_GRAYSCALE = 5;
    public static final int DITHER_USER = 256;
    private static Class \u0131;
    private static Class \u0122;
    private static Class \u0132;
    private static Class \u0133;
    
    public int getPaperWidth() {
        this.\u0125();
        return this.\u012a;
    }
    
    public int getTopMargin() {
        return this.\u012e;
    }
    
    public String getDeviceName() {
        if (!this.\u0125()) {
            return null;
        }
        final int globalLock = Kernel32.GlobalLock(this.\u0125);
        final String ptrToString = DllLib.ptrToString(globalLock + DllLib.systemDefaultCharSize * ((DEVNAMES)DllLib.ptrToStruct((Printer.\u0131 != null) ? Printer.\u0131 : (Printer.\u0131 = \u00c6("com.ms.win32.DEVNAMES")), globalLock)).wDeviceOffset);
        Kernel32.GlobalUnlock(this.\u0125);
        return ptrToString;
    }
    
    public boolean getDefault() {
        this.\u0128();
        final PRINTDLG printdlg = new PRINTDLG();
        printdlg.lStructSize = DllLib.sizeOf((Printer.\u0122 != null) ? Printer.\u0122 : (Printer.\u0122 = \u00c6("com.ms.win32.PRINTDLG")));
        printdlg.Flags = 1280;
        if (!Comdlg32.PrintDlg(printdlg)) {
            return false;
        }
        this.\u0125 = printdlg.hDevNames;
        this.\u0126 = printdlg.hDevMode;
        this.\u012b();
        this.\u0127 = printdlg.hDC;
        this.\u012a();
        this.\u012c = 254;
        this.\u012d = 254;
        this.\u012e = 254;
        this.\u012f = 254;
        return true;
    }
    
    public int escape(final int n, final byte[] array, final byte[] array2) throws PrinterException {
        this.getPrinterDC();
        final int extEscape = Gdi32.ExtEscape(this.\u0127, n, (array != null) ? array.length : 0, (Object)array, (array2 != null) ? array2.length : 0, (Object)array2);
        if (!this.\u0130 && extEscape <= 0) {
            throw new PrinterException(Kernel32.GetLastError(), "Printer.escape");
        }
        return extEscape;
    }
    
    public void setScale(final int n) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmScale = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x10;
        this.\u012c();
        this.\u012b();
    }
    
    public int getScale() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x10) != 0x0) ? \u0129.dmScale : 0);
        this.\u012c();
        return n;
    }
    
    public void setDitherType(final int dmDitherType) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmDitherType = dmDitherType;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x10000000;
        this.\u012c();
        this.\u012b();
    }
    
    public int getDitherType() {
        final DEVMODE \u0129 = this.\u0129();
        final int n = ((\u0129.dmFields & 0x10000000) != 0x0) ? \u0129.dmDitherType : 0;
        this.\u012c();
        return n;
    }
    
    public int startDoc(final String s) throws PrinterException {
        this.getPrinterDC();
        if (this.\u0128) {
            return 0;
        }
        final DOCINFO docinfo = new DOCINFO();
        docinfo.cbSize = DllLib.sizeOf((Printer.\u0132 != null) ? Printer.\u0132 : (Printer.\u0132 = \u00c6("com.ms.win32.DOCINFO")));
        docinfo.lpszDocName = ((s != null) ? s : "");
        final int startDoc = Gdi32.StartDoc(this.\u0127, docinfo);
        if (startDoc > 0) {
            this.\u0128 = true;
        }
        else if (!this.\u0130) {
            throw new PrinterException(Kernel32.GetLastError(), "Printer.startDoc");
        }
        return startDoc;
    }
    
    public void setFormName(final String dmFormName) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmFormName = dmFormName;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x10000;
        this.\u012c();
        this.\u012b();
    }
    
    public String getFormName() {
        final DEVMODE \u0129 = this.\u0129();
        final String s = ((\u0129.dmFields & 0x10000) != 0x0) ? \u0129.dmFormName : null;
        this.\u012c();
        return s;
    }
    
    public void abortDoc() throws PrinterException {
        this.getPrinterDC();
        if (this.\u0128) {
            this.endPage();
            if (Gdi32.AbortDoc(this.\u0127) > 0) {
                this.\u0128 = false;
                return;
            }
            if (!this.\u0130) {
                throw new PrinterException(Kernel32.GetLastError(), "Printer.abortDoc");
            }
        }
    }
    
    public void printBitmap(final Bitmap bitmap, final int n, final int n2) {
        this.printBitmap(bitmap, n, n2, 0.01);
    }
    
    public void transferFromDialog(final PrintDialog printDialog) {
        this.\u0128();
        final PRINTDLG \u0121 = printDialog.\u0121();
        this.\u0125 = \u0121.hDevNames;
        \u0121.hDevNames = 0;
        this.\u0126 = \u0121.hDevMode;
        \u0121.hDevMode = 0;
        this.\u012b();
        if (\u0121.hDC != 0) {
            this.\u0127 = \u0121.hDC;
            \u0121.hDC = 0;
        }
        this.\u012a();
    }
    
    public void transferFromDialog(final PageSetupDialog pageSetupDialog) {
        this.\u0128();
        final PAGESETUPDLG \u0115 = pageSetupDialog.\u0115();
        this.\u0125 = \u0115.hDevNames;
        \u0115.hDevNames = 0;
        this.\u0126 = \u0115.hDevMode;
        \u0115.hDevMode = 0;
        this.\u012b();
        this.\u012a();
        this.\u0126(pageSetupDialog);
    }
    
    public void startPage() throws PrinterException {
        this.getPrinterDC();
        if (!this.\u0129) {
            this.startDoc(null);
            if (Gdi32.StartPage(this.\u0127) > 0) {
                this.\u0129 = true;
                return;
            }
            if (!this.\u0130) {
                throw new PrinterException(Kernel32.GetLastError(), "Printer.startPage");
            }
        }
    }
    
    public void printBitmap(final Bitmap bitmap, final int n, final int n2, final double n3) {
        this.getPrinterDC();
        this.printBitmap(bitmap, n, n2, bitmap.getSize().x * this.getLogicalSizeX((float)n3));
    }
    
    public void printBitmap(final Bitmap bitmap, int n, int n2, int x) {
        this.startPage();
        final Point size = bitmap.getSize();
        final int x2 = size.x;
        final int y = size.y;
        int n8;
        if (n < 0 || n2 < 0 || x <= 0) {
            final int getDeviceCaps = Gdi32.GetDeviceCaps(this.\u0127, 88);
            final int getDeviceCaps2 = Gdi32.GetDeviceCaps(this.\u0127, 90);
            final int \u012b = this.\u012a;
            final int \u012b2 = this.\u012b;
            final int n3 = \u012b * getDeviceCaps / 254;
            final int n4 = \u012b2 * getDeviceCaps2 / 254;
            final int n5 = this.\u012c * getDeviceCaps / 254;
            final int n6 = this.\u012d * getDeviceCaps / 254;
            final int n7 = this.\u012e * getDeviceCaps2 / 254;
            final Point point = new Point(n3 - n5 - n6, n4 - n7 - this.\u012f * getDeviceCaps2 / 254);
            if (x <= 0) {
                x = point.x;
            }
            if (n < 0) {
                if (n == -2) {
                    n = n5 + (point.x - x) / 2;
                }
                else if (n == -3) {
                    n = n5 + point.x - x;
                }
                else {
                    n = n5;
                }
            }
            n8 = (int)(size.y / (size.x / (double)x) + 0.5);
            if (n2 < 0) {
                if (n2 == -2) {
                    n2 = n7 + (point.y - n8) / 2;
                }
                else if (n == -3) {
                    n2 = n7 + point.y - n8;
                }
                else {
                    n2 = n7;
                }
            }
        }
        else {
            n8 = (int)(size.y / (size.x / (double)x) + 0.5);
        }
        PrinterUtil.printBitmap(this.\u0127, bitmap, n, n2, x, n8, 0, 0, x2, y);
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
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmCopies = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x100;
        this.\u012c();
        this.\u012b();
    }
    
    public int getCopies() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x100) != 0x0) ? \u0129.dmCopies : 0);
        this.\u012c();
        return n;
    }
    
    public void dispose() {
        if (this.\u0128) {
            this.\u0130 = true;
            this.endDoc();
        }
        this.\u012b();
        this.\u0128();
    }
    
    public void setYResolution(final int n) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmYResolution = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x2000;
        this.\u012c();
        this.\u012b();
    }
    
    public int getYResolution() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x2000) != 0x0) ? \u0129.dmYResolution : 0);
        this.\u012c();
        return n;
    }
    
    public int getOrientation() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x1) != 0x0) ? \u0129.dmOrientation : 0);
        this.\u012c();
        return n;
    }
    
    private boolean \u0125() {
        if (this.\u0125 != 0) {
            return true;
        }
        if (this.\u0127 != 0) {
            return false;
        }
        this.getDefault();
        return this.\u0125 != 0;
    }
    
    public void setTTOption(final int n) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmTTOption = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x4000;
        this.\u012c();
        this.\u012b();
    }
    
    public int getTTOption() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x4000) != 0x0) ? \u0129.dmTTOption : 0);
        this.\u012c();
        return n;
    }
    
    public void setOrientation(final int n) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmOrientation = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x1;
        this.\u012c();
        this.\u012b();
    }
    
    public void setPaperSize(final int n) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmPaperSize = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x2;
        this.\u012c();
        this.\u012b();
    }
    
    public int getPaperSize() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x2) != 0x0) ? \u0129.dmPaperSize : 0);
        this.\u012c();
        return n;
    }
    
    public void closeGraphicsForPrinting(final Graphics graphics) {
        if (graphics != null) {
            graphics.dispose();
        }
        this.endPage();
        this.endDoc();
    }
    
    public void printForm(final Control control, final int n, final int n2) {
        this.printForm(control, n, n2, 0.01);
    }
    
    public void printForm(final Control control, final int n, final int n2, final double n3) {
        final Bitmap printToBitmap = UIUtil.printToBitmap(control);
        this.printBitmap(printToBitmap, n, n2, n3);
        printToBitmap.dispose();
    }
    
    public void setMediaType(final int dmMediaType) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmMediaType = dmMediaType;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x8000000;
        this.\u012c();
        this.\u012b();
    }
    
    public int getMediaType() {
        final DEVMODE \u0129 = this.\u0129();
        final int n = ((\u0129.dmFields & 0x8000000) != 0x0) ? \u0129.dmMediaType : 0;
        this.\u012c();
        return n;
    }
    
    public void printForm(final Control control, final int n, final int n2, final int n3) {
        final Bitmap printToBitmap = UIUtil.printToBitmap(control);
        this.printBitmap(printToBitmap, n, n2, n3);
        printToBitmap.dispose();
    }
    
    public void setCollate(final boolean b) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmCollate = (short)(b ? 1 : 0);
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x8000;
        this.\u012c();
        this.\u012b();
    }
    
    public boolean getCollate() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x8000) != 0x0) ? \u0129.dmCollate : 0);
        this.\u012c();
        return n == 1;
    }
    
    public void setDuplex(final int n) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmDuplex = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x1000;
        this.\u012c();
        this.\u012b();
    }
    
    public int getDuplex() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x1000) != 0x0) ? \u0129.dmDuplex : 0);
        this.\u012c();
        return n;
    }
    
    public void endDoc() throws PrinterException {
        this.getPrinterDC();
        if (this.\u0128) {
            this.endPage();
            if (Gdi32.EndDoc(this.\u0127) > 0) {
                this.\u0128 = false;
                return;
            }
            if (!this.\u0130) {
                throw new PrinterException(Kernel32.GetLastError(), "Printer.endDoc");
            }
        }
    }
    
    public void endPage() throws PrinterException {
        this.getPrinterDC();
        if (this.\u0129) {
            if (Gdi32.EndPage(this.\u0127) > 0) {
                this.\u0129 = false;
                return;
            }
            if (!this.\u0130) {
                throw new PrinterException(Kernel32.GetLastError(), "Printer.endPage");
            }
        }
    }
    
    public void transferToDialog(final PrintDialog printDialog) {
        final PRINTDLG \u0121 = printDialog.\u0121();
        \u0121.hDevNames = this.\u0125;
        this.\u0125 = 0;
        \u0121.hDevMode = this.\u0126;
        this.\u0126 = 0;
    }
    
    public void transferToDialog(final PageSetupDialog pageSetupDialog) {
        final PAGESETUPDLG \u0115 = pageSetupDialog.\u0115();
        \u0115.hDevNames = this.\u0125;
        this.\u0125 = 0;
        \u0115.hDevMode = this.\u0126;
        this.\u0126 = 0;
        this.\u0127(pageSetupDialog);
    }
    
    private void \u0126(final PageSetupDialog pageSetupDialog) {
        this.\u012c = pageSetupDialog.getLeftMargin();
        this.\u012d = pageSetupDialog.getRightMargin();
        this.\u012e = pageSetupDialog.getTopMargin();
        this.\u012f = pageSetupDialog.getBottomMargin();
        if (pageSetupDialog.getUnitInches()) {
            this.\u012c = this.\u012c * 254 / 1000;
            this.\u012d = this.\u012d * 254 / 1000;
            this.\u012e = this.\u012e * 254 / 1000;
            this.\u012f = this.\u012f * 254 / 1000;
            return;
        }
        this.\u012c /= 10;
        this.\u012d /= 10;
        this.\u012e /= 10;
        this.\u012f /= 10;
    }
    
    private void \u0127(final PageSetupDialog pageSetupDialog) {
        final int \u012d = this.\u012c;
        final int \u012d2 = this.\u012d;
        final int \u012f = this.\u012e;
        final int \u012f2 = this.\u012f;
        int n;
        int n2;
        int n3;
        int n4;
        if (pageSetupDialog.getUnitInches()) {
            n = \u012d * 1000 / 254;
            n2 = \u012d2 * 1000 / 254;
            n3 = \u012f * 1000 / 254;
            n4 = \u012f2 * 1000 / 254;
        }
        else {
            n = \u012d * 10;
            n2 = \u012d2 * 10;
            n3 = \u012f * 10;
            n4 = \u012f2 * 10;
        }
        pageSetupDialog.setMargin(n, n3, n2, n4);
    }
    
    public void setMargin(final int \u012d, final int \u012f, final int \u012d2, final int \u012f2) {
        this.\u012c = \u012d;
        this.\u012d = \u012d2;
        this.\u012e = \u012f;
        this.\u012f = \u012f2;
    }
    
    public int getLeftMargin() {
        return this.\u012c;
    }
    
    public int getRightMargin() {
        return this.\u012d;
    }
    
    public int getPaperHeight() {
        this.\u0125();
        return this.\u012b;
    }
    
    public final int getLogicalSizeX(final float n) {
        final int getDeviceCaps = Gdi32.GetDeviceCaps(this.\u0127, 88);
        final SIZE size = new SIZE();
        final SIZE size2 = new SIZE();
        Gdi32.GetWindowExtEx(this.\u0127, size);
        Gdi32.GetViewportExtEx(this.\u0127, size2);
        return Math.abs((int)(n * getDeviceCaps * size.cx) / size2.cx);
    }
    
    public int getBottomMargin() {
        return this.\u012f;
    }
    
    public int getPrinterDC() {
        if (this.\u0127 == 0) {
            if (this.\u0126 != 0 && this.\u0125 != 0) {
                final DEVMODE devmode = (DEVMODE)DllLib.ptrToStruct((Printer.\u0133 != null) ? Printer.\u0133 : (Printer.\u0133 = \u00c6("com.ms.win32.DEVMODE")), Kernel32.GlobalLock(this.\u0126));
                final int globalLock = Kernel32.GlobalLock(this.\u0125);
                final DEVNAMES devnames = (DEVNAMES)DllLib.ptrToStruct((Printer.\u0131 != null) ? Printer.\u0131 : (Printer.\u0131 = \u00c6("com.ms.win32.DEVNAMES")), globalLock);
                final int systemDefaultCharSize = DllLib.systemDefaultCharSize;
                final int createDC = Gdi32.CreateDC(DllLib.ptrToString(globalLock + systemDefaultCharSize * devnames.wDriverOffset), DllLib.ptrToString(globalLock + systemDefaultCharSize * devnames.wDeviceOffset), (String)null, devmode);
                Kernel32.GlobalUnlock(this.\u0125);
                Kernel32.GlobalUnlock(this.\u0126);
                if (createDC == 0) {
                    throw new PrinterException(0, "Printer.getPrinterDC");
                }
                this.\u0127 = createDC;
                this.\u0128 = false;
                this.\u0129 = false;
            }
            else {
                this.\u0125();
            }
            if (this.\u0127 == 0) {
                return 0;
            }
        }
        return this.\u0127;
    }
    
    public boolean ignorePrintErrors(final boolean i\u0307) {
        final boolean i\u03072 = this.\u0130;
        this.\u0130 = i\u0307;
        return i\u03072;
    }
    
    public Printer() {
        this.\u0130 = false;
        _cls753A._mth821F();
    }
    
    public Printer(final int \u0127) {
        this.\u0130 = false;
        this.\u0127 = \u0127;
    }
    
    public Printer(final String s) {
        this.\u0130 = false;
        final int[] array = { 0 };
        if (!Spoolss.OpenPrinter(s, array, (PRINTER_DEFAULTS)null)) {
            throw new PrinterException(0, "Printer(String deviceName) (1)");
        }
        final int n = array[0];
        final int[] array2 = { 0 };
        Spoolss.GetPrinter(n, 2, (Object)null, 0, array2);
        final int n2 = array2[0];
        final int[] array3 = new int[n2 / 4 + 1];
        if (!Spoolss.GetPrinter(n, 2, (Object)array3, n2, new int[1])) {
            Spoolss.ClosePrinter(n);
            throw new PrinterException(0, "Printer(String deviceName) (2)");
        }
        Spoolss.ClosePrinter(n);
        final DEVMODE devmode = (DEVMODE)DllLib.ptrToStruct((Printer.\u0133 != null) ? Printer.\u0133 : (Printer.\u0133 = \u00c6("com.ms.win32.DEVMODE")), array3[7]);
        final int n3 = array3[4];
        final int n4 = array3[1];
        final int n5 = array3[3];
        final String ptrToString = DllLib.ptrToString(n3);
        final String ptrToString2 = DllLib.ptrToString(n4);
        final String ptrToString3 = DllLib.ptrToString(n5);
        final int size = DllLib.sizeOf((Printer.\u0133 != null) ? Printer.\u0133 : (Printer.\u0133 = \u00c6("com.ms.win32.DEVMODE")));
        this.\u0126 = Kernel32.GlobalAlloc(66, size);
        DllLib.copy((Object)devmode, 0, Kernel32.GlobalLock(this.\u0126), size);
        Kernel32.GlobalUnlock(this.\u0126);
        final int systemDefaultCharSize = DllLib.systemDefaultCharSize;
        final int n6 = ptrToString.length() * systemDefaultCharSize;
        final int n7 = ptrToString2.length() * systemDefaultCharSize;
        final int n8 = ptrToString3.length() * systemDefaultCharSize;
        final int size2 = DllLib.sizeOf((Printer.\u0131 != null) ? Printer.\u0131 : (Printer.\u0131 = \u00c6("com.ms.win32.DEVNAMES")));
        this.\u0125 = Kernel32.GlobalAlloc(66, size2 + n7 + n8 + n6 + 3 * systemDefaultCharSize);
        final int globalLock = Kernel32.GlobalLock(this.\u0125);
        final DEVNAMES devnames = (DEVNAMES)DllLib.ptrToStruct((Printer.\u0131 != null) ? Printer.\u0131 : (Printer.\u0131 = \u00c6("com.ms.win32.DEVNAMES")), globalLock);
        final int n9 = size2;
        final int n10 = size2 + n6 + systemDefaultCharSize;
        final int n11 = size2 + n6 + n7 + 2 * systemDefaultCharSize;
        devnames.wDefault = 0;
        devnames.wDriverOffset = (short)(n9 / systemDefaultCharSize);
        devnames.wDeviceOffset = (short)(n10 / systemDefaultCharSize);
        devnames.wOutputOffset = (short)(n11 / systemDefaultCharSize);
        final byte[] array4 = new byte[n6];
        final byte[] array5 = new byte[n7];
        final byte[] array6 = new byte[n8];
        DllLib.copy(n3, array4, 0, n6);
        DllLib.copy(n4, array5, 0, n7);
        DllLib.copy(n5, array6, 0, n8);
        DllLib.copy(array4, 0, globalLock + n9, n6);
        DllLib.copy(array5, 0, globalLock + n10, n7);
        DllLib.copy(array6, 0, globalLock + n11, n8);
        Kernel32.GlobalUnlock(this.\u0125);
        this.\u012a();
    }
    
    public void setColor(final boolean b) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmColor = (short)(b ? 2 : 1);
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x800;
        this.\u012c();
        this.\u012b();
    }
    
    public boolean getColor() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x800) != 0x0) ? \u0129.dmColor : 0);
        this.\u012c();
        return n == 2;
    }
    
    public void setPrintQuality(final int n) {
        final DEVMODE \u0129 = this.\u0129();
        \u0129.dmPrintQuality = (short)n;
        final DEVMODE devmode = \u0129;
        devmode.dmFields |= 0x400;
        this.\u012c();
        this.\u012b();
    }
    
    public int getPrintQuality() {
        final DEVMODE \u0129 = this.\u0129();
        final short n = (short)(((\u0129.dmFields & 0x400) != 0x0) ? \u0129.dmPrintQuality : 0);
        this.\u012c();
        return n;
    }
    
    public Graphics openGraphicsForPrinting() {
        this.startDoc(null);
        this.startPage();
        return new Graphics(this.\u0127);
    }
    
    private void \u0128() {
        if (this.\u0126 != 0) {
            Kernel32.GlobalFree(this.\u0126);
            this.\u0126 = 0;
        }
        if (this.\u0125 != 0) {
            Kernel32.GlobalFree(this.\u0125);
            this.\u0125 = 0;
        }
    }
    
    private DEVMODE \u0129() {
        if (!this.\u0125()) {
            return null;
        }
        return (DEVMODE)DllLib.ptrToStruct((Printer.\u0133 != null) ? Printer.\u0133 : (Printer.\u0133 = \u00c6("com.ms.win32.DEVMODE")), Kernel32.GlobalLock(this.\u0126));
    }
    
    protected void finalize() {
        this.dispose();
    }
    
    private void \u012a() {
        this.getPrinterDC();
        final int getDeviceCaps = Gdi32.GetDeviceCaps(this.\u0127, 88);
        final int getDeviceCaps2 = Gdi32.GetDeviceCaps(this.\u0127, 90);
        final int getDeviceCaps3 = Gdi32.GetDeviceCaps(this.\u0127, 110);
        final int getDeviceCaps4 = Gdi32.GetDeviceCaps(this.\u0127, 111);
        this.\u012a = getDeviceCaps3 * 254 / getDeviceCaps;
        this.\u012b = getDeviceCaps4 * 254 / getDeviceCaps2;
    }
    
    private void \u012b() {
        if (this.\u0127 != 0) {
            Gdi32.DeleteDC(this.\u0127);
            this.\u0127 = 0;
        }
    }
    
    public static Point logToDev(final Graphics graphics, final Point point) {
        final int handle = graphics.getHandle();
        return new Point(point.x * Gdi32.GetDeviceCaps(handle, 88) / 254, point.y * Gdi32.GetDeviceCaps(handle, 90) / 254);
    }
    
    private void \u012c() {
        Kernel32.GlobalUnlock(this.\u0126);
    }
}
