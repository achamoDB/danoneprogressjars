// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.ui.Control;
import com.ms.dll.DllLib;
import com.ms.dll.Root;
import com.ms.wfc.ui.RichEdit;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;

public class PrintRichEdit
{
    private Printer printer;
    private Graphics graphics;
    private Rectangle \u0180;
    private RichEdit \u0181;
    private int \u0182;
    private int \u0183;
    private Win32FormatRange \u0184;
    private int \u0185;
    private int \u0186;
    private int \u0187;
    
    public boolean printNextPage(final boolean b) {
        if (this.\u0185 == 0) {
            throw new PrinterException(0, "startPrint not called");
        }
        this.\u0184.crMin = ((Control)this.\u0181).sendMessage(1081, (int)(b ? 1 : 0), this.\u0186);
        this.\u0184.crMax = -1;
        return this.\u0184.crMin < this.\u0187;
    }
    
    public PrintRichEdit(final RichEdit \u0253) {
        this.\u0184 = new Win32FormatRange();
        this.\u0181 = \u0253;
    }
    
    public boolean setWysiwigMode(final boolean b) {
        this.\u0180();
        int n;
        if (b) {
            n = ((Control)this.\u0181).sendMessage(1096, this.printer.getPrinterDC(), this.\u0181(true).width);
        }
        else {
            n = ((Control)this.\u0181).sendMessage(1096, 0, 0);
        }
        return n != 0;
    }
    
    private void \u0180() {
        if (this.\u0181 == null) {
            throw new PrinterException(1, "No rich text edit control specified");
        }
        if (this.printer == null) {
            this.printer = new Printer();
        }
        if (this.printer == null) {
            throw new PrinterException(0, "No printer specified");
        }
        this.\u0184.hDC = ((this.graphics != null) ? this.graphics.getHandle() : this.printer.getPrinterDC());
        this.\u0184.hDCTarget = this.printer.getPrinterDC();
        if (this.\u0180 == null) {
            this.\u0180 = this.\u0181(true);
        }
        this.\u0184.rLeft = this.\u0180.x;
        this.\u0184.rTop = this.\u0180.y;
        this.\u0184.rRight = this.\u0180.x + this.\u0180.width;
        this.\u0184.rBottom = this.\u0180.y + this.\u0180.height;
        final Rectangle \u0253 = this.\u0181(false);
        if (\u0253 != null) {
            this.\u0184.rPageLeft = \u0253.x;
            this.\u0184.rPageTop = \u0253.y;
            this.\u0184.rPageRight = \u0253.x + \u0253.width;
            this.\u0184.rPageBottom = \u0253.y + \u0253.height;
        }
        this.\u0184.crMin = 0;
        this.\u0184.crMax = -1;
    }
    
    public int getNumberOfPages() {
        if (!this.startPrint()) {
            return 0;
        }
        int n = 0;
        do {
            ++n;
        } while (this.printNextPage(false));
        this.endPrint();
        return n;
    }
    
    public void setFromPage(final int \u0183) {
        this.\u0182 = \u0183;
    }
    
    public void setToPage(final int \u0183) {
        this.\u0183 = \u0183;
    }
    
    public boolean startPrint() {
        this.\u0180();
        this.\u0187 = ((Control)this.\u0181).getText().length();
        if (this.\u0187 == 0) {
            return false;
        }
        this.\u0185 = Root.alloc((Object)this.\u0184);
        this.\u0186 = DllLib.addrOf(this.\u0185);
        return true;
    }
    
    public void setGraphics(final Graphics graphics) {
        this.graphics = graphics;
    }
    
    public void setPrinter(final Printer printer) {
        this.printer = printer;
    }
    
    private Rectangle \u0181(final boolean b) {
        final Rectangle rectangle = new Rectangle();
        rectangle.width = this.printer.getPaperWidth();
        rectangle.height = this.printer.getPaperHeight();
        if (b) {
            final int leftMargin = this.printer.getLeftMargin();
            final Rectangle rectangle2 = rectangle;
            rectangle2.x += leftMargin;
            final Rectangle rectangle3 = rectangle;
            rectangle3.width -= leftMargin;
            final int rightMargin = this.printer.getRightMargin();
            final Rectangle rectangle4 = rectangle;
            rectangle4.width -= rightMargin;
            final int topMargin = this.printer.getTopMargin();
            final Rectangle rectangle5 = rectangle;
            rectangle5.y += topMargin;
            final Rectangle rectangle6 = rectangle;
            rectangle6.height -= topMargin;
            final int bottomMargin = this.printer.getBottomMargin();
            final Rectangle rectangle7 = rectangle;
            rectangle7.height -= bottomMargin;
        }
        rectangle.x = rectangle.x * 1440 / 254;
        rectangle.y = rectangle.y * 1440 / 254;
        rectangle.width = rectangle.width * 1440 / 254;
        rectangle.height = rectangle.height * 1440 / 254;
        return rectangle;
    }
    
    public void setRichEdit(final RichEdit \u0253) {
        this.\u0181 = \u0253;
    }
    
    public void print(final String s) {
        if (!this.startPrint()) {
            return;
        }
        this.printer.startDoc(s);
        for (int n = 1; this.\u0183 <= 0 || n <= this.\u0183; ++n) {
            boolean b;
            if (n >= this.\u0182) {
                this.printer.startPage();
                b = this.printNextPage(true);
                this.printer.endPage();
            }
            else {
                b = this.printNextPage(false);
            }
            if (!b) {
                break;
            }
        }
        this.endPrint();
        this.printer.endDoc();
    }
    
    public void endPrint() {
        if (this.\u0185 == 0) {
            throw new PrinterException(0, "startPrint not called");
        }
        ((Control)this.\u0181).sendMessage(1081, 0, 0);
        Root.free(this.\u0185);
        this.\u0185 = 0;
    }
    
    public void setRectangle(final Rectangle \u0180) {
        this.\u0180 = \u0180;
    }
}
