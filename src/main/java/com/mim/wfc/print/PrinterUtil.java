// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.dll.Root;
import com.ms.win32.BITMAPINFO;
import com.ms.dll.DllLib;
import com.mim.wfc.ui.UIUtil;
import com.ms.wfc.ui.Color;
import com.ms.win32.Gdi32;
import com.ms.wfc.ui.Bitmap;

public class PrinterUtil
{
    private static /* synthetic */ Class class$com$ms$win32$BITMAPINFO;
    private static /* synthetic */ Class class$com$ms$win32$BITMAPINFOHEADER;
    
    public static native int CreateDIBSection(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    public static void printBitmap(final int n, Bitmap bitmap, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int bmiHeader_biWidth, final int bmiHeader_biHeight) {
        if (bitmap.getTransparent()) {
            final Color transparentColor = bitmap.getTransparentColor();
            final Color color = new Color(Gdi32.GetBkColor(n));
            final Color[] array = { transparentColor };
            final Color[] array2 = { color };
            bitmap = (Bitmap)bitmap.clone();
            UIUtil.convertBitmapColors(bitmap, array, array2);
        }
        final int createCompatibleDC = Gdi32.CreateCompatibleDC(bitmap.getGraphics().getHandle());
        final short bmiHeader_biBitCount = (short)Gdi32.GetDeviceCaps(createCompatibleDC, 12);
        final int allocHGlobal = DllLib.allocHGlobal(DllLib.sizeOf((PrinterUtil.class$com$ms$win32$BITMAPINFO != null) ? PrinterUtil.class$com$ms$win32$BITMAPINFO : (PrinterUtil.class$com$ms$win32$BITMAPINFO = class$("com.ms.win32.BITMAPINFO"))));
        final BITMAPINFO bitmapinfo = (BITMAPINFO)DllLib.ptrToStruct((PrinterUtil.class$com$ms$win32$BITMAPINFO != null) ? PrinterUtil.class$com$ms$win32$BITMAPINFO : (PrinterUtil.class$com$ms$win32$BITMAPINFO = class$("com.ms.win32.BITMAPINFO")), allocHGlobal);
        bitmapinfo.bmiHeader_biSize = DllLib.sizeOf((PrinterUtil.class$com$ms$win32$BITMAPINFOHEADER != null) ? PrinterUtil.class$com$ms$win32$BITMAPINFOHEADER : (PrinterUtil.class$com$ms$win32$BITMAPINFOHEADER = class$("com.ms.win32.BITMAPINFOHEADER")));
        bitmapinfo.bmiHeader_biWidth = bmiHeader_biWidth;
        bitmapinfo.bmiHeader_biHeight = bmiHeader_biHeight;
        bitmapinfo.bmiHeader_biPlanes = 1;
        bitmapinfo.bmiHeader_biBitCount = bmiHeader_biBitCount;
        bitmapinfo.bmiHeader_biCompression = 0;
        bitmapinfo.bmiHeader_biSizeImage = bmiHeader_biWidth * bmiHeader_biHeight * bmiHeader_biBitCount / 8;
        bitmapinfo.bmiHeader_biXPelsPerMeter = Gdi32.GetDeviceCaps(createCompatibleDC, 8) / Gdi32.GetDeviceCaps(createCompatibleDC, 4);
        bitmapinfo.bmiHeader_biYPelsPerMeter = Gdi32.GetDeviceCaps(createCompatibleDC, 10) / Gdi32.GetDeviceCaps(createCompatibleDC, 6);
        bitmapinfo.bmiHeader_biClrUsed = 0;
        bitmapinfo.bmiHeader_biClrImportant = 0;
        final Win32Ptr win32Ptr = new Win32Ptr();
        final int alloc = Root.alloc((Object)win32Ptr);
        final int createDIBSection = CreateDIBSection(createCompatibleDC, allocHGlobal, 0, DllLib.addrOf(alloc), 0, 0);
        final int adr = win32Ptr.adr;
        Root.free(alloc);
        if (createDIBSection != 0 && Gdi32.GetDIBits(createCompatibleDC, bitmap.getHandle(), 0, bmiHeader_biHeight, adr, allocHGlobal, 0) != 0) {
            StretchDIBits(n, n2, n3, n4, n5, n6, n7, bmiHeader_biWidth, bmiHeader_biHeight, adr, allocHGlobal, 0, 13369376);
        }
        Gdi32.DeleteDC(createCompatibleDC);
        DllLib.freeHGlobal(allocHGlobal);
    }
    
    private static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public static native int StretchDIBits(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final int p12);
}
