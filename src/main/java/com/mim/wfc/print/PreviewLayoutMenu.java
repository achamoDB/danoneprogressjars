// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.core.Event;
import com.ms.wfc.app.Application;
import com.ms.wfc.core.WFCInvalidArgumentException;
import com.ms.wfc.core.Sys;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.IButtonControl;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.ui.KeyEvent;
import com.ms.wfc.ui.Button;
import com.ms.wfc.core.Container;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Form;

class PreviewLayoutMenu extends Form
{
    private Point \u0117;
    private Point \u0118;
    private Point \u0119;
    private Point \u011a;
    private Bitmap \u011b;
    private Bitmap \u011c;
    private Bitmap buffer;
    Container \u011d;
    Button \u011e;
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        super.onKeyDown(keyEvent);
        if (keyEvent.getKeyCode() == 27) {
            this.setDialogResult(2);
            this.dispose();
        }
    }
    
    private void \u0117() {
        ((Control)this).setSize(this.\u0118.x * (this.\u011a.x + 2) + 2 + 4 + 2, this.\u0118.y * (this.\u011a.x + 2) + 2 + 4 + 24);
    }
    
    public PreviewLayoutMenu() {
        this.\u0117 = new Point(1, 1);
        this.\u0118 = new Point(10, 4);
        this.\u0119 = new Point(1, 1);
        this.buffer = null;
        this.\u011d = new Container();
        this.\u011e = new Button();
        this.\u0118();
        ((Control)this).setStyle(4, true);
        final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "PrintPreview");
        this.\u011b = (Bitmap)((IResourceManager)resourceManager).getObject("imagePage");
        this.\u011c = (Bitmap)((IResourceManager)resourceManager).getObject("imageSelectedPage");
        this.\u011a = this.\u011b.getSize();
        this.\u0117();
    }
    
    public PreviewLayoutMenu(final Control parent) {
        this();
        ((Control)this).setParent(parent);
    }
    
    protected void onMouseUp(final MouseEvent mouseEvent) {
        super.onMouseUp(mouseEvent);
        if (((Control)this).getClientRect().contains(mouseEvent.x, mouseEvent.y)) {
            this.\u0117.x = this.\u0119.x + 1;
            this.\u0117.y = this.\u0119.y + 1;
            this.setDialogResult(1);
        }
        else {
            this.setDialogResult(2);
        }
        this.dispose();
    }
    
    private void \u0118() {
        ((Control)this.\u011e).setDock(2);
        ((Control)this.\u011e).setLocation(new Point(0, 272));
        ((Control)this.\u011e).setSize(new Point(294, 22));
        ((Control)this.\u011e).setTabIndex(0);
        ((Control)this.\u011e).setTabStop(false);
        ((Control)this.\u011e).setText("&Cancel");
        this.\u011e.setDialogResult(2);
        ((Control)this).setText("");
        this.setAutoScaleBaseSize(new Point(5, 13));
        this.setBorderStyle(3);
        this.setCancelButton((IButtonControl)this.\u011e);
        ((Control)this).setClientSize(new Point(294, 294));
        this.setControlBox(false);
        this.setShowInTaskbar(false);
        this.setStartPosition(0);
        this.setTopMost(true);
        this.setNewControls(new Control[] { (Control)this.\u011e });
    }
    
    private void \u0119() {
        final Rectangle clientRect = ((Control)this).getClientRect();
        this.buffer = new Bitmap(clientRect.width, clientRect.height);
    }
    
    public boolean show(final Control control, final Point point) {
        if (control == null) {
            throw new WFCInvalidArgumentException(Sys.getString("InvalidArgument", "control", "null"));
        }
        final Point pointToScreen = control.pointToScreen(new Point(0, 0));
        ((Control)this).setLocation(pointToScreen.x + point.x, pointToScreen.y + point.y);
        this.\u0117();
        final Form activeForm = Application.getActiveForm();
        final int showDialog = this.showDialog();
        ((Control)activeForm).focus();
        return showDialog == 1;
    }
    
    public void setPages(final Point \u0117) {
        this.\u0117 = \u0117;
    }
    
    public void dispose() {
        if (this.buffer != null) {
            this.buffer.dispose();
            this.buffer = null;
        }
        this.\u011d.dispose();
        super.dispose();
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (((Control)this).getCreated()) {
            if (this.buffer != null) {
                this.buffer.dispose();
                this.buffer = null;
            }
            this.\u0119();
            ((Control)this).invalidate();
        }
    }
    
    public Point getPages() {
        return this.\u0117;
    }
    
    public void setMaxPages(final Point \u0119) {
        this.\u0118 = \u0119;
        if (this.\u0118.x < 1) {
            this.\u0118.x = 1;
        }
        if (this.\u0118.y < 1) {
            this.\u0118.y = 1;
        }
        this.\u0117();
    }
    
    public Point getMaxPages() {
        return this.\u0118;
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        if (this.buffer == null) {
            this.\u0119();
        }
        super.onPaint(paintEvent);
        final Graphics graphics = paintEvent.graphics;
        final Graphics graphics2 = this.buffer.getGraphics();
        graphics2.setBackColor(graphics.getBackColor());
        graphics2.setTextColor(graphics.getTextColor());
        graphics2.setFont(graphics.getFont());
        graphics2.setBrush(graphics.getBrush());
        graphics2.setPen(graphics.getPen());
        final Rectangle clientRect = ((Control)this).getClientRect();
        graphics2.fill(clientRect);
        int n = clientRect.y + 2;
        for (int i = 0; i < this.\u0118.y; ++i) {
            int n2 = clientRect.x + 2;
            for (int j = 0; j < this.\u0118.x; ++j) {
                graphics2.drawImage((Image)((j <= this.\u0119.x && i <= this.\u0119.y) ? this.\u011c : this.\u011b), n2, n);
                n2 += this.\u011a.x + 2;
            }
            n += this.\u011a.y + 2;
        }
        paintEvent.graphics.drawImage((Image)this.buffer, 0, 0);
    }
    
    protected void onMouseMove(final MouseEvent mouseEvent) {
        super.onMouseMove(mouseEvent);
        final Rectangle clientRect = ((Control)this).getClientRect();
        for (int i = this.\u0118.y - 1; i >= 0; --i) {
            for (int j = this.\u0118.x - 1; j >= 0; --j) {
                final int n = clientRect.x + 2 + j * (this.\u011a.x + 2);
                final int n2 = clientRect.y + 2 + i * (this.\u011a.y + 2);
                if (mouseEvent.x >= n && mouseEvent.y >= n2) {
                    if (this.\u0119.x != j || this.\u0119.y != i) {
                        this.\u0119.x = j;
                        this.\u0119.y = i;
                        ((Control)this).invalidate();
                    }
                    return;
                }
            }
        }
        if (this.\u0119.x != 0 || this.\u0119.y != 0) {
            this.\u0119.x = 0;
            this.\u0119.y = 0;
            ((Control)this).invalidate();
        }
    }
}
