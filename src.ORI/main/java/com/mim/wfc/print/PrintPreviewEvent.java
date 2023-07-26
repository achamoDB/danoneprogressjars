// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.ui.Point;
import com.ms.win32.Gdi32;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.core.Event;

public class PrintPreviewEvent extends Event
{
    public Printer printer;
    public Graphics graphics;
    public int page;
    public boolean printing;
    public Rectangle drawRect;
    public int zoomFactor;
    private int \u0167;
    
    public PrintPreviewEvent() {
    }
    
    PrintPreviewEvent(final Printer printer, final Graphics graphics, final int page, final boolean printing, final Rectangle drawRect, final int zoomFactor) {
        this.printer = printer;
        this.graphics = graphics;
        this.page = page;
        this.printing = printing;
        this.drawRect = drawRect;
        this.zoomFactor = zoomFactor;
    }
    
    public void setGraphics() {
        if (this.\u0167 != 0) {
            return;
        }
        int n;
        if (this.graphics != null) {
            n = this.graphics.getHandle();
        }
        else {
            n = this.printer.getPrinterDC();
        }
        this.\u0167 = Gdi32.SaveDC(n);
        final Point point2;
        final Point point = point2 = new Point(this.printer.getPaperWidth(), this.printer.getPaperHeight());
        point2.x -= this.printer.getLeftMargin() + this.printer.getRightMargin();
        final Point point3 = point;
        point3.y -= this.printer.getTopMargin() + this.printer.getBottomMargin();
        this.graphics.setCoordinateSystem(0);
        this.graphics.setCoordinateOrigin(new Point(0, 0), new Point(this.drawRect.x, this.drawRect.y));
        this.graphics.setCoordinateScale(point, new Point(this.drawRect.width, this.drawRect.height));
        final Rectangle drawRect = this.drawRect;
        final Rectangle drawRect2 = this.drawRect;
        final int n2 = 0;
        drawRect2.y = n2;
        drawRect.x = n2;
        this.drawRect.width = point.x;
        this.drawRect.height = point.y;
    }
    
    public void resetGraphics() {
        if (this.\u0167 != 0) {
            int n;
            if (this.graphics != null) {
                n = this.graphics.getHandle();
            }
            else {
                n = this.printer.getPrinterDC();
            }
            Gdi32.RestoreDC(n, this.\u0167);
            this.\u0167 = 0;
        }
    }
}
