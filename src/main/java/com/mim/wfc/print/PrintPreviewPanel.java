// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.Brush;
import com.ms.wfc.ui.Pen;
import com.ms.win32.Gdi32;
import com.ms.wfc.core.WFCException;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.Rectangle;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.core.Event;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.IRequireBegin;
import com.ms.wfc.ui.Panel;

public class PrintPreviewPanel extends Panel implements IRequireBegin
{
    private int \u0168;
    private int \u0169;
    private int \u016a;
    private Point \u016b;
    private double \u016c;
    private Point \u016d;
    private int \u016e;
    private int \u016f;
    private Color \u0170;
    private Color \u0171;
    private String \u0172;
    private PrintPreviewEventHandler \u0135;
    private PrintPreviewEventHandler \u0136;
    private EventHandler \u0137;
    private EventHandler \u0138;
    public static final int ZOOM_PAGE_WIDTH = -1;
    public static final int ZOOM_WHOLE_PAGE = -2;
    private boolean \u00e1;
    private boolean \u0173;
    private boolean \u0174;
    private Printer \u0175;
    private PageSetupDialog \u0176;
    private Point \u0177;
    private Point \u0178;
    private Point \u0179;
    private Point \u017a;
    private Point \u017b;
    private Point \u017c;
    Cursor \u017d;
    Cursor \u017e;
    String \u017f;
    
    public void setDocTitle(final String \u0173) {
        this.\u0172 = \u0173;
    }
    
    private void \u0168(int \u0169, final boolean b) {
        if (this.\u0168 <= 0) {
            return;
        }
        if (\u0169 <= 0) {
            \u0169 = 1;
        }
        else if (\u0169 > this.\u0168) {
            \u0169 = this.\u0168;
        }
        final int \u01692 = this.\u0169;
        this.\u0169 = \u0169;
        final int n = this.\u016b.x * this.\u016b.y;
        if (\u0169 < this.\u016a || \u0169 >= this.\u016a + n || b) {
            this.setFirstPage((\u0169 - 1) / n * n + 1);
        }
        else {
            final Graphics graphics = ((Control)this).createGraphics();
            this.paintPageFrame(graphics, \u01692);
            this.paintPageFrame(graphics, this.\u0169);
            graphics.dispose();
        }
        this.onPageSelected(Event.EMPTY);
    }
    
    public String getDocTitle() {
        return this.\u0172;
    }
    
    private void \u0169(int n, final Point point) {
        n -= this.\u016a;
        point.x = this.\u0178.x + n % this.\u016b.x * this.\u0179.x - this.\u016d.x;
        point.y = this.\u0178.y + n / this.\u016b.x * this.\u0179.y - this.\u016d.y;
    }
    
    public void addOnDrawPage(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0135 = (PrintPreviewEventHandler)Delegate.combine((Delegate)this.\u0135, (Delegate)printPreviewEventHandler);
    }
    
    public void showPageSetupDialog() {
        this.\u0175.transferToDialog(this.\u0176);
        final boolean display = this.\u0176.display();
        this.\u0175.transferFromDialog(this.\u0176);
        if (display) {
            this.updatePageSetup();
            this.setPageLayout(this.\u016b);
        }
    }
    
    public void printNoDialog(int n, int \u0169) {
        if (n < 1) {
            n = 1;
        }
        if (\u0169 > this.\u0168 || \u0169 < 1) {
            \u0169 = this.\u0168;
        }
        this.\u0175.startDoc((this.\u0172 != null) ? this.\u0172 : this.\u017f);
        final Graphics openGraphicsForPrinting = this.\u0175.openGraphicsForPrinting();
        final PrintPreviewEvent \u016d = this.\u016d(openGraphicsForPrinting, 0, true, null);
        \u016d.printing = true;
        for (int i = n; i <= \u0169; ++i) {
            this.\u0175.startPage();
            \u016d.page = i;
            this.onDrawPage(\u016d);
            this.\u0175.endPage();
        }
        this.\u0175.closeGraphicsForPrinting(openGraphicsForPrinting);
    }
    
    public PageSetupDialog getPageSetupDialog() {
        return this.\u0176;
    }
    
    public void setBorderWidth(final int \u016f) {
        this.\u016e = \u016f;
        this.\u016c();
    }
    
    public int getBorderWidth() {
        return this.\u016e;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    public void showNextPage() {
        this.setActivePage(this.\u0169 + this.\u016b.x * this.\u016b.y);
    }
    
    public void showPreviousPage() {
        this.setActivePage(this.\u0169 - this.\u016b.x * this.\u016b.y);
    }
    
    private void \u016a(final Graphics graphics, final Rectangle rectangle) {
        final int x = rectangle.x;
        final int n = rectangle.x + rectangle.width - 1;
        final int y = rectangle.y;
        final int n2 = rectangle.y + rectangle.height - 1;
        graphics.drawLine(x, y, n + 1, y);
        graphics.drawLine(x, n2, n + 1, n2);
        graphics.drawLine(x, y, x, n2);
        graphics.drawLine(n, y, n, n2);
    }
    
    private void \u016b() {
        try {
            this.onQueryPageCount(this.\u016d(null, 0, false, null));
        }
        catch (PrinterException ex) {
            WFCException.rethrowException((Throwable)ex);
        }
    }
    
    public void removeOnQueryPageCount(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0136 = (PrintPreviewEventHandler)Delegate.remove((Delegate)this.\u0136, (Delegate)printPreviewEventHandler);
    }
    
    public void setPageColor(final Color \u0171) {
        this.\u0170 = \u0171;
    }
    
    public Color getPageColor() {
        return this.\u0170;
    }
    
    public void setActiveFrameColor(final Color \u0171) {
        this.\u0171 = \u0171;
    }
    
    public Color getActiveFrameColor() {
        return this.\u0171;
    }
    
    public void addOnPageSelected(final EventHandler eventHandler) {
        this.\u0137 = (EventHandler)Delegate.combine((Delegate)this.\u0137, (Delegate)eventHandler);
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (this.\u00e1) {
            this.setPageLayout(this.\u016b);
        }
    }
    
    private void \u016c() {
        if (this.\u00e1) {
            this.setPageLayout(this.\u016b);
        }
    }
    
    public void updatePageSetup() {
        this.\u0177.x = this.\u0175.getPaperWidth();
        this.\u0177.y = this.\u0175.getPaperHeight();
        final Graphics graphics = ((Control)this).createGraphics();
        final int handle = graphics.getHandle();
        final int getDeviceCaps = Gdi32.GetDeviceCaps(handle, 88);
        final int getDeviceCaps2 = Gdi32.GetDeviceCaps(handle, 90);
        graphics.dispose();
        this.\u017b.x = this.\u0177.x * getDeviceCaps / 254;
        this.\u017b.y = this.\u0177.y * getDeviceCaps2 / 254;
        this.\u016b();
    }
    
    public void showLastPage() {
        this.setActivePage(this.\u0168);
    }
    
    private void drawPage(final Graphics graphics, final int n, final Point point) {
        graphics.setPen(Pen.WINDOWTEXT);
        graphics.setBrush(new Brush(this.\u0170));
        graphics.setBackColor(this.\u0170);
        final Rectangle rectangle = new Rectangle(point.x, point.y, this.\u017a.x, this.\u017a.y);
        graphics.fill(rectangle);
        this.onDrawPage(this.\u016d(graphics, n, false, rectangle));
    }
    
    public void begin2() {
        this.\u0173 = true;
        this.begin();
    }
    
    protected void onDrawPage(final PrintPreviewEvent printPreviewEvent) {
        if (this.\u0135 != null) {
            this.\u0135.invoke(this, printPreviewEvent);
        }
    }
    
    protected void onPageLayoutChanged(final Event event) {
        if (this.\u0138 != null) {
            this.\u0138.invoke((Object)this, event);
        }
    }
    
    public void setPrinter(final Printer \u0175) {
        this.\u0175 = \u0175;
        this.updatePageSetup();
    }
    
    public Printer getPrinter() {
        return this.\u0175;
    }
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        super.onMouseDown(mouseEvent);
        final int \u0171 = this.\u0170(mouseEvent.x, mouseEvent.y);
        if (\u0171 > 0) {
            if (\u0171 == this.\u0169) {
                this.toggleZoom();
                return;
            }
            this.setActivePage(\u0171);
        }
    }
    
    public void dispose() {
        super.dispose();
    }
    
    public void print() {
        final PrintDialog printDialog = new PrintDialog((Control)this);
        this.\u0175.transferToDialog(printDialog);
        printDialog.setMinMaxPages(1, this.\u0168);
        printDialog.setFromToPages(1, this.\u0168);
        if (!printDialog.display()) {
            return;
        }
        this.\u0175.transferFromDialog(printDialog);
        this.printNoDialog(printDialog.getFromPage(), printDialog.getToPage());
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        if (!this.\u00e1) {
            return;
        }
        final Graphics graphics = paintEvent.graphics;
        final Brush brush = new Brush(this.\u0170);
        final Point point = new Point();
        int i = this.\u016a;
        int \u0169 = i + this.\u016b.x * this.\u016b.y - 1;
        if (\u0169 > this.\u0168) {
            \u0169 = this.\u0168;
        }
        while (i <= \u0169) {
            this.\u0169(i, point);
            this.\u016f(graphics, i, point, brush);
            this.drawPage(graphics, i, point);
            ++i;
        }
    }
    
    public void addOnQueryPageCount(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0136 = (PrintPreviewEventHandler)Delegate.combine((Delegate)this.\u0136, (Delegate)printPreviewEventHandler);
    }
    
    protected void onMouseMove(final MouseEvent mouseEvent) {
        super.onMouseMove(mouseEvent);
        if (this.\u0170(mouseEvent.x, mouseEvent.y) == this.\u0169) {
            ((Control)this).setCursor((this.\u016c == 1.0) ? this.\u017e : this.\u017d);
            return;
        }
        ((Control)this).setCursor(Cursor.ARROW);
    }
    
    public Point getPageSize() {
        return new Point(this.\u017a);
    }
    
    private PrintPreviewEvent \u016d(final Graphics graphics, final int n, final boolean b, Rectangle rectangle) {
        int n2;
        if (graphics != null) {
            n2 = graphics.getHandle();
        }
        else {
            n2 = this.\u0175.getPrinterDC();
        }
        final int getDeviceCaps = Gdi32.GetDeviceCaps(n2, 88);
        final int getDeviceCaps2 = Gdi32.GetDeviceCaps(n2, 90);
        final int leftMargin = this.\u0175.getLeftMargin();
        final int rightMargin = this.\u0175.getRightMargin();
        final int topMargin = this.\u0175.getTopMargin();
        final int bottomMargin = this.\u0175.getBottomMargin();
        final int n3 = this.\u0177.x * getDeviceCaps / 254;
        final int n4 = this.\u0177.y * getDeviceCaps2 / 254;
        if (rectangle == null) {
            rectangle = new Rectangle(0, 0, n3, n4);
        }
        final double n5 = rectangle.width / (double)n3;
        final double n6 = rectangle.height / (double)n4;
        final int n7 = (int)(leftMargin * getDeviceCaps * n5 / 254.0);
        final int n8 = (int)(rightMargin * getDeviceCaps * n5 / 254.0);
        final int n9 = (int)(topMargin * getDeviceCaps2 * n6 / 254.0);
        final int n10 = (int)(bottomMargin * getDeviceCaps2 * n6 / 254.0);
        final Rectangle rectangle2 = rectangle;
        rectangle2.x += n7;
        final Rectangle rectangle3 = rectangle;
        rectangle3.y += n9;
        final Rectangle rectangle4 = rectangle;
        rectangle4.width -= n7 + n8;
        final Rectangle rectangle5 = rectangle;
        rectangle5.height -= n9 + n10;
        return new PrintPreviewEvent(this.\u0175, graphics, n, b, rectangle, b ? 100 : this.getZoomFactor());
    }
    
    public void setPageLayout(final Point \u016b) {
        if (\u016b.x < 1 || \u016b.x > 20 || \u016b.y < 1 || \u016b.y > 10) {
            throw new WFCInvalidEnumException("Invalid value for pageLayout property");
        }
        this.\u016b = \u016b;
        final Rectangle clientRect = ((Control)this).getClientRect();
        final int n = 2 * this.\u016e;
        final Point point2;
        final Point point = point2 = new Point(clientRect.width - n, clientRect.height - n);
        point2.x -= (\u016b.x - 1) * this.\u016f;
        final Point point3 = point;
        point3.y -= (\u016b.y - 1) * this.\u016f;
        this.\u017a.x = point.x / \u016b.x;
        this.\u017a.y = point.y / \u016b.y;
        this.\u016c = Math.min(this.\u017a.x / (double)this.\u017b.x, this.\u017a.y / (double)this.\u017b.y);
        this.\u017a.x = (int)(this.\u017b.x * this.\u016c + 0.5);
        this.\u017a.y = (int)(this.\u017b.y * this.\u016c + 0.5);
        this.\u016e();
    }
    
    public Point getPageLayout() {
        return this.\u016b;
    }
    
    protected void onPageSelected(final Event event) {
        if (this.\u0137 != null) {
            this.\u0137.invoke((Object)this, event);
        }
    }
    
    private void \u016e() {
        final Rectangle clientRect = ((Control)this).getClientRect();
        final Point point = new Point(this.\u016b.x * this.\u017a.x + (this.\u016b.x - 1) * this.\u016f, this.\u016b.y * this.\u017a.y + (this.\u016b.y - 1) * this.\u016f);
        this.\u0178.x = (clientRect.width - point.x) / 2;
        if (this.\u0178.x < this.\u016e) {
            this.\u0178.x = this.\u016e;
        }
        this.\u0178.y = (clientRect.height - point.y) / 2;
        if (this.\u0178.y < this.\u016e) {
            this.\u0178.y = this.\u016e;
        }
        this.\u0179.x = this.\u017a.x + this.\u016f;
        this.\u0179.y = this.\u017a.y + this.\u016f;
        final Point \u016d = this.\u016d;
        final Point \u016d2 = this.\u016d;
        final int n = 0;
        \u016d2.y = n;
        \u016d.x = n;
        ((Control)this).invalidate();
        this.onPageLayoutChanged(Event.EMPTY);
        this.setActivePage(this.\u0169);
    }
    
    public void setPageDistance(final int \u016f) {
        this.\u016f = \u016f;
        this.\u016c();
    }
    
    public int getPageDistance() {
        return this.\u016f;
    }
    
    public int getZoomFactor() {
        return (int)(this.\u016c * 100.0 + 0.5);
    }
    
    public void setZoomFactor(final int n) {
        if (n == -1 || n == -2) {
            final Rectangle clientRect = ((Control)this).getClientRect();
            final int n2 = 2 * this.\u016e;
            final Point point = new Point(clientRect.width - n2, clientRect.height - n2);
            if (n == -1) {
                final Point \u016b = this.\u016b;
                final Point \u016b2 = this.\u016b;
                final int n3 = 1;
                \u016b2.y = n3;
                \u016b.x = n3;
                this.\u016c = point.x / (double)this.\u017b.x;
                this.\u017a.x = point.x;
                this.\u017a.y = (int)(this.\u017b.y / this.\u016c + 0.5);
            }
            else if (n == -2) {
                final Point \u016b3 = this.\u016b;
                final Point \u016b4 = this.\u016b;
                final int n4 = 1;
                \u016b4.y = n4;
                \u016b3.x = n4;
                this.\u016c = Math.min(point.x / (double)(this.\u017b.x + n2), point.y / (double)(this.\u017b.y + n2));
                this.\u017a.x = (int)(this.\u017b.x * this.\u016c + 0.5);
                this.\u017a.y = (int)(this.\u017b.y * this.\u016c + 0.5);
            }
            this.\u016e();
            return;
        }
        this.\u0171(n / 100.0);
    }
    
    private void \u016f(final Graphics graphics, final int n, final Point point, final Brush brush) {
        final Rectangle rectangle = new Rectangle(point.x, point.y, this.\u017a.x, this.\u017a.y);
        rectangle.inflate(1, 1);
        Pen windowtext;
        if (n == this.\u0169) {
            windowtext = new Pen(this.\u0171, 0, 1);
        }
        else {
            windowtext = Pen.WINDOWTEXT;
        }
        graphics.setPen(windowtext);
        if (brush != null) {
            graphics.setBrush(brush);
            graphics.drawRect(rectangle);
        }
        else {
            this.\u016a(graphics, rectangle);
        }
        if (n != this.\u0169) {
            graphics.setPen(new Pen(((Control)this).getBackColor()));
            rectangle.inflate(1, 1);
            this.\u016a(graphics, rectangle);
            rectangle.inflate(-1, -1);
        }
        graphics.setBrush(Brush.CONTROLDARKDARK);
        graphics.fill(rectangle.x + 5, rectangle.y + rectangle.height, rectangle.width, 5);
        graphics.fill(rectangle.x + rectangle.width, rectangle.y + 5, 5, rectangle.height);
        if (n == this.\u0169) {
            rectangle.inflate(1, 1);
            this.\u016a(graphics, rectangle);
        }
    }
    
    public void showFirstPage() {
        this.setActivePage(1);
    }
    
    private int \u0170(final int n, final int n2) {
        final Point point = new Point();
        int i = this.\u016a;
        int \u0169 = i + this.\u016b.x * this.\u016b.y - 1;
        if (\u0169 > this.\u0168) {
            \u0169 = this.\u0168;
        }
        while (i <= \u0169) {
            this.\u0169(i, point);
            if (n >= point.x && n <= point.x + this.\u017a.x && n2 >= point.y && n2 <= point.y + this.\u017a.y) {
                return i;
            }
            ++i;
        }
        return 0;
    }
    
    public void setActivePage(final int n) {
        this.\u0168(n, false);
    }
    
    public int getActivePage() {
        return this.\u0169;
    }
    
    public void setFirstPage(final int \u016b) {
        this.\u016a = \u016b;
        ((Control)this).invalidate();
    }
    
    public int getFirstPage() {
        return this.\u016a;
    }
    
    public void setXOffset(final int x) {
        this.\u016d.x = x;
        ((Control)this).invalidate();
    }
    
    public int getXOffset() {
        return this.\u016d.x;
    }
    
    public void removeOnDrawPage(final PrintPreviewEventHandler printPreviewEventHandler) {
        this.\u0135 = (PrintPreviewEventHandler)Delegate.remove((Delegate)this.\u0135, (Delegate)printPreviewEventHandler);
    }
    
    public void removeOnPageLayoutChanged(final EventHandler eventHandler) {
        this.\u0138 = (EventHandler)Delegate.remove((Delegate)this.\u0138, (Delegate)eventHandler);
    }
    
    public void requireBegin2() {
        this.\u0173 = false;
    }
    
    private void \u0171(final double \u016d) {
        this.\u016c = \u016d;
        final Rectangle clientRect = ((Control)this).getClientRect();
        final int n = 2 * this.\u016e;
        final Point point = new Point(clientRect.width - n, clientRect.height - n);
        this.\u017a.x = (int)(this.\u017b.x * this.\u016c + 0.5);
        this.\u017a.y = (int)(this.\u017b.y * this.\u016c + 0.5);
        this.\u016b.x = point.x / (this.\u017a.x + this.\u016f);
        if (this.\u016b.x == 0) {
            this.\u016b.x = 1;
        }
        this.\u016b.y = point.y / (this.\u017a.y + this.\u016f);
        if (this.\u016b.y == 0) {
            this.\u016b.y = 1;
        }
        this.\u016e();
    }
    
    public void toggleZoom() {
        if (this.\u016c != 1.0) {
            this.\u017c = new Point(this.\u016b);
            this.setZoomFactor(100);
            return;
        }
        if (this.\u017c != null) {
            this.setPageLayout(this.\u017c);
            this.\u017c = null;
            this.\u0168(this.\u0169, true);
            return;
        }
        this.setZoomFactor(-2);
    }
    
    public void begin() {
        if (!this.\u0173) {
            return;
        }
        this.\u00e1 = true;
        try {
            if (this.\u0175 == null) {
                this.\u0175 = new Printer();
            }
            this.\u0176 = new PageSetupDialog((Control)this);
            this.\u0177 = new Point(0, 0);
        }
        catch (PrinterException ex) {
            WFCException.rethrowException((Throwable)ex);
        }
        this.updatePageSetup();
        this.setZoomFactor(-2);
        this.onPageSelected(Event.EMPTY);
    }
    
    public void setPageCount(final int \u0169) {
        this.\u0168 = \u0169;
        ((Control)this).invalidate();
    }
    
    public int getPageCount() {
        return this.\u0168;
    }
    
    public void setYOffset(final int y) {
        this.\u016d.y = y;
        ((Control)this).invalidate();
    }
    
    public int getYOffset() {
        return this.\u016d.y;
    }
    
    public PrintPreviewPanel() {
        this.\u0168 = 1;
        this.\u0169 = 1;
        this.\u016a = 1;
        this.\u016b = new Point(1, 1);
        this.\u016c = 0.75;
        this.\u016d = new Point(0, 0);
        this.\u016e = 10;
        this.\u016f = 15;
        this.\u0170 = new Color(192, 192, 192);
        this.\u0171 = Color.ACTIVECAPTION;
        this.\u00e1 = false;
        this.\u0173 = true;
        this.\u0174 = false;
        this.\u0178 = new Point(0, 0);
        this.\u0179 = new Point(0, 0);
        this.\u017a = new Point(0, 0);
        this.\u017b = new Point(0, 0);
        _cls753A._mth821F();
        ((Control)this).setSize(new Point(200, 200));
        ((Control)this).setBackColor(Color.CONTROLDARK);
        final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "PrintPreview");
        this.\u017d = (Cursor)((IResourceManager)resourceManager).getObject("cursorZoomIn");
        this.\u017e = (Cursor)((IResourceManager)resourceManager).getObject("cursorZoomOut");
        this.\u017f = ((IResourceManager)resourceManager).getString("textDocName");
    }
    
    protected void onQueryPageCount(final PrintPreviewEvent printPreviewEvent) {
        if (this.\u0136 != null) {
            this.\u0136.invoke(this, printPreviewEvent);
        }
    }
    
    public void removeOnPageSelected(final EventHandler eventHandler) {
        this.\u0137 = (EventHandler)Delegate.remove((Delegate)this.\u0137, (Delegate)eventHandler);
    }
    
    public void addOnPageLayoutChanged(final EventHandler eventHandler) {
        this.\u0138 = (EventHandler)Delegate.combine((Delegate)this.\u0138, (Delegate)eventHandler);
    }
    
    protected void paintPageFrame(final Graphics graphics, final int n) {
        if (n > 0 && n <= this.\u0168) {
            final Point point = new Point();
            this.\u0169(n, point);
            this.\u016f(graphics, n, point, null);
        }
    }
}
