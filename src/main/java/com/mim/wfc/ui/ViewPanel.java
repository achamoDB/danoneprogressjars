// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.win32.RECT;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.win32.Util;
import com.ms.wfc.win32.Windows;
import com.ms.dll.DllLib;
import com.ms.win32.SCROLLINFO;
import com.ms.win32.Gdi32;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.app.Message;
import com.ms.wfc.ui.KeyEvent;
import com.ms.wfc.ui.LayoutEvent;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.core.Event;
import com.ms.wfc.ui.CreateParams;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Control;
import com.ms.wfc.app.SystemInformation;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.ui.Panel;

public class ViewPanel extends Panel
{
    protected Bitmap buffer;
    private Point \u0293;
    private int \u0167;
    private boolean \u0294;
    private int \u0295;
    private int \u0296;
    private double \u016c;
    boolean \u0297;
    private Point \u0298;
    private Point \u0299;
    private boolean \u029a;
    private boolean \u029b;
    private static Class \u029c;
    
    public boolean isHScrollVisible() {
        return this.\u029a;
    }
    
    Point \u0293(final Point point) {
        final Point point2 = new Point(point);
        if (this.\u0296 != 0) {
            point2.x = (int)(point2.x / this.\u016c + 0.5);
            point2.y = (int)(point2.y / this.\u016c + 0.5);
        }
        return point2;
    }
    
    public Point getMaxClientSize() {
        final Point clientSize = ((Control)this).getClientSize();
        if (this.\u029b) {
            final Point point = clientSize;
            point.x += SystemInformation.getVertScrollBarWidth();
        }
        if (this.\u029a) {
            final Point point2 = clientSize;
            point2.y += SystemInformation.getHorizScrollBarHeight();
        }
        return clientSize;
    }
    
    Point \u0294(final Point point) {
        final Point point2 = new Point(point);
        if (this.\u0296 != 0) {
            point2.x = (int)(point2.x * this.\u016c + 0.5);
            point2.y = (int)(point2.y * this.\u016c + 0.5);
        }
        return point2;
    }
    
    public void scrollIntoView(final Control control) {
        if ((this.\u0295 & 0x2) != 0x0 && (this.\u029a || this.\u029b) && control != null) {
            final Rectangle bounds = control.getBounds();
            final Point clientSize = ((Control)this).getClientSize();
            int x = this.\u0293.x;
            int y = this.\u0293.y;
            final int x2 = this.\u0299.x;
            final int y2 = this.\u0299.y;
            if (bounds.x < x2) {
                x = x2 - bounds.x - this.\u0293.x;
            }
            else if (bounds.x + bounds.width + x2 > clientSize.x) {
                x = clientSize.x - (bounds.x + bounds.width + x2 + this.\u0293.x);
                if (x + bounds.x + this.\u0293.x < x2) {
                    x = x2 - bounds.x - this.\u0293.x;
                }
            }
            if (bounds.y < y2) {
                y = y2 - bounds.y - this.\u0293.y;
            }
            else if (bounds.y + bounds.height + y2 > clientSize.y) {
                y = clientSize.y - (bounds.y + bounds.height + y2 + this.\u0293.y);
                if (y + bounds.y + this.\u0293.y < y2) {
                    y = y2 - bounds.y - this.\u0293.y;
                }
            }
            this.setScrollOffset(x, y);
        }
    }
    
    public void scrollIntoView(final Rectangle rectangle) {
        if (this.\u029a || this.\u029b) {
            final Point \u0293 = this.\u0293(((Control)this).getClientSize());
            int n = this.\u0293.x;
            int n2 = this.\u0293.y;
            if (rectangle.x + rectangle.width > n + \u0293.x) {
                n = rectangle.x + rectangle.width - \u0293.x;
            }
            if (rectangle.x < n) {
                n = rectangle.x;
            }
            if (rectangle.y + rectangle.height > n2 + \u0293.y) {
                n2 = rectangle.y + rectangle.height - \u0293.y;
            }
            if (rectangle.y < n2) {
                n2 = rectangle.y;
            }
            this.setScrollOffset(n, n2);
        }
    }
    
    protected CreateParams getCreateParams() {
        final CreateParams createParams = super.getCreateParams();
        if (this.\u029a) {
            final CreateParams createParams2 = createParams;
            createParams2.style |= 0x100000;
        }
        else {
            final CreateParams createParams3 = createParams;
            createParams3.style &= 0xFFEFFFFF;
        }
        if (this.\u029b) {
            final CreateParams createParams4 = createParams;
            createParams4.style |= 0x200000;
        }
        else {
            final CreateParams createParams5 = createParams;
            createParams5.style &= 0xFFDFFFFF;
        }
        return createParams;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    protected void onLayout(final LayoutEvent layoutEvent) {
        super.onLayout(layoutEvent);
        this.\u0295();
        ((Control)this).invalidate();
    }
    
    public void setZoomFactor(final int n) {
        this.\u016c = n / 100.0;
        ((Control)this).performLayout();
    }
    
    public int getZoomFactor() {
        if (this.\u0296 == 0) {
            return 100;
        }
        return (int)(this.\u016c * 100.0 + 0.5);
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (((Control)this).getCreated()) {
            if (this.buffer != null) {
                this.buffer.dispose();
            }
            this.\u0119();
            ((Control)this).invalidate();
        }
    }
    
    public void setMargin(final Point point) {
        if (point != null) {
            this.setMargin(point.x, point.y);
            return;
        }
        this.setMargin(0, 0);
    }
    
    public void setMargin(final int x, final int y) {
        if (x != this.\u0299.x || y != this.\u0299.y) {
            this.\u0299.x = x;
            this.\u0299.y = y;
            if ((this.\u0295 & 0x2) != 0x0 && (this.\u029a || this.\u029b)) {
                ((Control)this).performLayout();
            }
        }
    }
    
    public Point getMargin() {
        if (this.\u0299 == null) {
            this.\u0299 = new Point(0, 0);
        }
        return new Point(this.\u0299);
    }
    
    private void \u0295() {
        if (!((Control)this).getHandleCreated()) {
            this.\u0294 = true;
            return;
        }
        this.\u0294 = false;
        Point \u0294 = new Point(0, 0);
        final Point \u02942 = this.\u0294(this.\u0298);
        final Point maxClientSize = this.getMaxClientSize();
        if ((this.\u0295 & 0x2) != 0x0) {
            if (((Control)this).getControlCount() != 0) {
                int x = this.\u0299.x;
                int y = this.\u0299.y;
                for (int i = 0; i < ((Control)this).getControlCount(); ++i) {
                    final Control control = ((Control)this).getControl(i);
                    if (control != null) {
                        switch (control.getDock()) {
                            case 2: {
                                y += control.getSize().y;
                                break;
                            }
                            case 4: {
                                x += control.getSize().x;
                                break;
                            }
                        }
                    }
                }
                for (int j = 0; j < ((Control)this).getControlCount(); ++j) {
                    boolean b = true;
                    boolean b2 = true;
                    final Control control2 = ((Control)this).getControl(j);
                    if (control2 != null) {
                        switch (control2.getDock()) {
                            case 1: {
                                b = false;
                                break;
                            }
                            case 3: {
                                b2 = false;
                                break;
                            }
                            case 2:
                            case 4:
                            case 5: {
                                b = false;
                                b2 = false;
                                break;
                            }
                        }
                        final int anchor = control2.getAnchor();
                        if ((anchor & 0x8) != 0x0) {
                            b = false;
                        }
                        if ((anchor & 0x4) == 0x0) {
                            b = false;
                        }
                        if ((anchor & 0x2) != 0x0) {
                            b2 = false;
                        }
                        if ((anchor & 0x1) == 0x0) {
                            b2 = false;
                        }
                        final Rectangle bounds = control2.getBounds();
                        if (b) {
                            final int x2 = x + bounds.x + bounds.width;
                            if (x2 > \u0294.x) {
                                \u0294.x = x2;
                            }
                        }
                        if (b2) {
                            final int y2 = y + bounds.y + bounds.height;
                            if (y2 > \u0294.y) {
                                \u0294.y = y2;
                            }
                        }
                    }
                }
            }
            this.\u0298.x = \u0294.x;
            this.\u0298.y = \u0294.y;
        }
        else {
            if (this.\u0298.x == 0 || this.\u0298.y == 0) {
                this.\u0298.x = maxClientSize.x;
                this.\u0298.y = maxClientSize.y;
            }
            double min = 0.0;
            switch (this.\u0296) {
                case 2: {
                    min = Math.min(maxClientSize.x / (double)this.\u0298.x, maxClientSize.y / (double)this.\u0298.y);
                    break;
                }
                case 3: {
                    min = maxClientSize.x / (double)this.\u0298.x;
                    break;
                }
                case 4: {
                    min = maxClientSize.y / (double)this.\u0298.y;
                    break;
                }
            }
            if (min != 0.0) {
                this.setZoomFactor((int)(min * 100.0 + 0.5));
            }
            \u0294 = this.\u0294(this.\u0298);
        }
        boolean b3 = false;
        final boolean \u029a = \u0294.x != 0 && \u0294.x > maxClientSize.x;
        final boolean \u029b = \u0294.y != 0 && \u0294.y > maxClientSize.y;
        if (\u029a != this.\u029a || \u029b != this.\u029b) {
            this.\u029a = \u029a;
            this.\u029b = \u029b;
            ((Control)this).updateStyles();
            b3 = true;
        }
        this.setScrollOffsetCheckError(\u029a ? this.\u0293.x : 0, \u029b ? this.\u0293.y : 0, true);
        if (!\u0294.equals((Object)\u02942)) {
            b3 = true;
        }
        if (b3) {
            ((Control)this).performLayout();
        }
        this.updateScrollbars();
    }
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        super.onKeyDown(keyEvent);
        if (keyEvent.handled) {
            return;
        }
        final int keyCode = keyEvent.getKeyCode();
        switch (keyCode) {
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40: {
                final Point scrollOffset = this.getScrollOffset();
                switch (keyCode) {
                    case 38: {
                        final Point point = scrollOffset;
                        point.y -= 5;
                        break;
                    }
                    case 40: {
                        final Point point2 = scrollOffset;
                        point2.y += 5;
                        break;
                    }
                    case 37: {
                        final Point point3 = scrollOffset;
                        point3.x -= 5;
                        break;
                    }
                    case 39: {
                        final Point point4 = scrollOffset;
                        point4.x += 5;
                        break;
                    }
                    case 33: {
                        final Point point5 = scrollOffset;
                        point5.y -= 100;
                        break;
                    }
                    case 34: {
                        final Point point6 = scrollOffset;
                        point6.y += 100;
                        break;
                    }
                    case 36: {
                        scrollOffset.x = 0;
                        break;
                    }
                    case 35: {
                        scrollOffset.x = 100000;
                        break;
                    }
                }
                this.setScrollOffset(scrollOffset);
                keyEvent.handled = true;
                break;
            }
        }
    }
    
    public ViewPanel() {
        this.\u0293 = new Point(0, 0);
        this.\u0294 = false;
        this.\u0295 = 0;
        this.\u0296 = 0;
        this.\u016c = 1.0;
        this.\u0298 = new Point(0, 0);
        this.\u0299 = new Point(0, 0);
        _cls753A._mth821F();
    }
    
    public void setMode(final int \u0295) {
        this.\u0295 = \u0295;
        if (\u0295 == 0) {
            final Point \u0298 = this.\u0298;
            final Point \u02982 = this.\u0298;
            final int n = 0;
            \u02982.y = n;
            \u0298.x = n;
        }
        ((Control)this).performLayout();
    }
    
    public int getMode() {
        return this.\u0295;
    }
    
    public void invalidateDoc(final Rectangle rectangle) {
        ((Control)this).invalidate(this.docToView(rectangle));
    }
    
    protected void wndProc(final Message message) {
        switch (message.msg) {
            case 276: {
                this.\u0297(message);
            }
            case 277: {
                this.\u0298(message);
            }
            case 135: {
                super.wndProc(message);
                message.result |= 0x1;
            }
            default: {
                super.wndProc(message);
            }
        }
    }
    
    public void setDocSize(final Point point) {
        if (point != null) {
            this.setDocSize(point.x, point.y);
            return;
        }
        this.setDocSize(0, 0);
    }
    
    public void setDocSize(final int x, final int y) {
        if (x != this.\u0298.x || y != this.\u0298.y || this.\u0294) {
            this.\u0298.x = x;
            this.\u0298.y = y;
            this.\u0295();
        }
    }
    
    public Point getDocSize() {
        return this.\u0298;
    }
    
    public Point getScrollOffset() {
        return new Point(this.\u0293);
    }
    
    public void setScrollOffset(final Point point) {
        this.setScrollOffset(point.x, point.y);
    }
    
    public void setScrollOffset(final int n, final int n2) {
        this.setScrollOffsetCheckError(n, n2, false);
    }
    
    public void updateScrollbars() {
        if (((Control)this).getHandleCreated() && (this.\u029a || this.\u029b)) {
            final Point \u0293 = this.\u0293(((Control)this).getClientSize());
            if (this.\u029a) {
                this.\u0296(this.\u0298.x, \u0293.x, this.\u0293.x, 0);
            }
            if (this.\u029b) {
                this.\u0296(this.\u0298.y, \u0293.y, this.\u0293.y, 1);
            }
        }
    }
    
    public void setGraphics(final Graphics graphics) {
        if (this.\u0295 == 0 && this.\u0296 == 0) {
            return;
        }
        if (this.\u0167 == 0) {
            this.\u0167 = Gdi32.SaveDC(graphics.getHandle());
        }
        graphics.setCoordinateSystem(0);
        graphics.setCoordinateOrigin(new Point(this.\u0293.x, this.\u0293.y), new Point(0, 0));
        final Point point = new Point(1000, 1000);
        graphics.setCoordinateScale(point, this.\u0294(point));
    }
    
    public void resetGraphics(final Graphics graphics) {
        if (this.\u0295 == 0 && this.\u0296 == 0) {
            return;
        }
        if (this.\u0167 != 0) {
            Gdi32.RestoreDC(graphics.getHandle(), this.\u0167);
            this.\u0167 = 0;
        }
    }
    
    public void setZoomMode(final int \u0296) {
        this.\u0296 = \u0296;
        ((Control)this).performLayout();
    }
    
    public int getZoomMode() {
        return this.\u0296;
    }
    
    private void \u0296(final int n, final int n2, final int n3, final int n4) {
        int n5;
        if (n < 10) {
            n5 = 1000;
        }
        else if (n < 100) {
            n5 = 100;
        }
        else if (n < 1000) {
            n5 = 10;
        }
        else {
            n5 = 1;
        }
        final SCROLLINFO scrollinfo = new SCROLLINFO();
        scrollinfo.cbSize = DllLib.sizeOf((ViewPanel.\u029c != null) ? ViewPanel.\u029c : (ViewPanel.\u029c = \u00c6("com.ms.win32.SCROLLINFO")));
        scrollinfo.fMask = 7;
        scrollinfo.nMin = 0;
        scrollinfo.nMax = n * n5;
        scrollinfo.nPage = n2 * n5;
        scrollinfo.nPos = n3 * n5;
        Windows.SetScrollInfo(((Control)this).getHandle(), n4, scrollinfo, true);
    }
    
    private void \u0297(final Message message) {
        if (message.lParam != 0) {
            super.wndProc(message);
            return;
        }
        final SCROLLINFO scrollinfo = new SCROLLINFO();
        scrollinfo.cbSize = DllLib.sizeOf((ViewPanel.\u029c != null) ? ViewPanel.\u029c : (ViewPanel.\u029c = \u00c6("com.ms.win32.SCROLLINFO")));
        scrollinfo.fMask = 7;
        Windows.GetScrollInfo(((Control)this).getHandle(), 0, scrollinfo);
        int x = this.\u0293.x;
        final int n = scrollinfo.nMax / this.\u0298.x;
        switch (Util.LOWORD(message.wParam)) {
            case 4:
            case 5: {
                x = Util.HIWORD(message.wParam) / n;
                break;
            }
            case 0: {
                x -= 5;
                break;
            }
            case 1: {
                x += 5;
                break;
            }
            case 2: {
                x -= scrollinfo.nPage / n;
                break;
            }
            case 3: {
                x += scrollinfo.nPage / n;
                break;
            }
        }
        this.setScrollOffset(x, this.\u0293.y);
    }
    
    private void \u0298(final Message message) {
        if (message.lParam != 0) {
            super.wndProc(message);
            return;
        }
        final SCROLLINFO scrollinfo = new SCROLLINFO();
        scrollinfo.cbSize = DllLib.sizeOf((ViewPanel.\u029c != null) ? ViewPanel.\u029c : (ViewPanel.\u029c = \u00c6("com.ms.win32.SCROLLINFO")));
        scrollinfo.fMask = 7;
        Windows.GetScrollInfo(((Control)this).getHandle(), 1, scrollinfo);
        int y = this.\u0293.y;
        final int n = scrollinfo.nMax / this.\u0298.y;
        switch (Util.LOWORD(message.wParam)) {
            case 4:
            case 5: {
                y = Util.HIWORD(message.wParam) / n;
                break;
            }
            case 0: {
                y -= 5;
                break;
            }
            case 1: {
                y += 5;
                break;
            }
            case 2: {
                y -= scrollinfo.nPage / n;
                break;
            }
            case 3: {
                y += scrollinfo.nPage / n;
                break;
            }
        }
        this.setScrollOffset(this.\u0293.x, y);
    }
    
    void \u0119() {
        if (this.\u0297) {
            final Point clientSize = ((Control)this).getClientSize();
            this.buffer = new Bitmap(clientSize.x, clientSize.y);
        }
    }
    
    public void dispose() {
        if (this.buffer != null) {
            this.buffer.dispose();
            this.buffer = null;
        }
        super.dispose();
    }
    
    public void setDoubleBuffer(final boolean \u0297) {
        this.\u0297 = \u0297;
        if (this.\u0297) {
            ((Control)this).setStyle(4, true);
            return;
        }
        ((Control)this).setStyle(4, false);
    }
    
    public boolean getDoubleBuffer() {
        return this.\u0297;
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        if (this.\u0297) {
            if (this.buffer == null) {
                this.\u0119();
            }
            final Graphics graphics = paintEvent.graphics;
            final Graphics graphics2 = this.buffer.getGraphics();
            graphics2.setBackColor(graphics.getBackColor());
            graphics2.setTextColor(graphics.getTextColor());
            graphics2.setFont(graphics.getFont());
            graphics2.setBrush(graphics.getBrush());
            graphics2.setPen(graphics.getPen());
            super.onPaint(new PaintEvent(graphics2, paintEvent.clipRect));
            graphics2.dispose();
            paintEvent.graphics.drawImage((Image)this.buffer, 0, 0);
            return;
        }
        super.onPaint(paintEvent);
    }
    
    public Point docToView(final Point point) {
        final Point point3;
        final Point point2 = point3 = new Point(point);
        point3.x -= this.\u0293.x;
        final Point point4 = point2;
        point4.y -= this.\u0293.y;
        if (this.\u0296 != 0) {
            point2.x = (int)(point2.x * this.\u016c + 0.5);
            point2.y = (int)(point2.y * this.\u016c + 0.5);
        }
        return point2;
    }
    
    public Rectangle docToView(final Rectangle rectangle) {
        final Rectangle rectangle3;
        final Rectangle rectangle2 = rectangle3 = new Rectangle(rectangle);
        rectangle3.x -= this.\u0293.x;
        final Rectangle rectangle4 = rectangle2;
        rectangle4.y -= this.\u0293.y;
        if (this.\u0296 != 0) {
            rectangle2.x = (int)(rectangle2.x * this.\u016c + 0.5);
            rectangle2.y = (int)(rectangle2.y * this.\u016c + 0.5);
            rectangle2.width = (int)(rectangle2.width * this.\u016c + 0.5);
            rectangle2.height = (int)(rectangle2.height * this.\u016c + 0.5);
        }
        return rectangle2;
    }
    
    public void setScrollOffsetCheckError(int x, int y, final boolean b) {
        final Point \u0293 = this.\u0293(((Control)this).getClientSize());
        final int max = Math.max(this.\u0298.x - \u0293.x, 0);
        final int max2 = Math.max(this.\u0298.y - \u0293.y, 0);
        if (x < 0) {
            x = 0;
        }
        if (x > max) {
            x = max;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > max2) {
            y = max2;
        }
        int n = 0;
        int n2 = 0;
        if (this.\u0293.x != x) {
            n = this.\u0293.x - x;
        }
        if (this.\u0293.y != y) {
            n2 = this.\u0293.y - y;
        }
        if (b) {
            final int n3 = (this.getZoomFactor() + 99) / 100 - 1;
            if (n3 > 0 && n <= n3 && n >= -n3 && n2 <= n3 && n2 >= -n3) {
                return;
            }
        }
        this.\u0293.x = x;
        this.\u0293.y = y;
        if (n != 0 || n2 != 0) {
            Windows.ScrollWindowEx(((Control)this).getHandle(), n, n2, (RECT)null, ((Control)this).getClientRect().toRECT(), 0, ((Control)this).getClientRect().toRECT(), 7);
            ((Control)this).invalidate();
            this.updateScrollbars();
        }
    }
    
    public Point viewToDoc(final Point point) {
        final Point point2 = new Point(point);
        if (this.\u0296 != 0) {
            point2.x = (int)(point2.x / this.\u016c + 0.5);
            point2.y = (int)(point2.y / this.\u016c + 0.5);
        }
        final Point point3 = point2;
        point3.x += this.\u0293.x;
        final Point point4 = point2;
        point4.y += this.\u0293.y;
        return point2;
    }
    
    public boolean isVScrollVisible() {
        return this.\u029b;
    }
    
    public Rectangle viewToDoc(final Rectangle rectangle) {
        final Rectangle rectangle2 = new Rectangle(rectangle);
        if (this.\u0296 != 0) {
            rectangle2.x = (int)(rectangle2.x / this.\u016c + 0.5);
            rectangle2.y = (int)(rectangle2.y / this.\u016c + 0.5);
            rectangle2.width = (int)(rectangle2.width / this.\u016c + 0.5);
            rectangle2.height = (int)(rectangle2.height / this.\u016c + 0.5);
        }
        final Rectangle rectangle3 = rectangle2;
        rectangle3.x += this.\u0293.x;
        final Rectangle rectangle4 = rectangle2;
        rectangle4.y += this.\u0293.y;
        return rectangle2;
    }
}
