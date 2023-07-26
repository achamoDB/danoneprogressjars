// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Form;
import com.ms.wfc.ui.PaintEvent;
import com.ms.util.TimerListener;
import com.ms.wfc.win32.Windows;
import com.ms.wfc.app.SystemInformation;
import com.ms.wfc.core.Component;
import com.ms.wfc.core.Event;
import com.ms.wfc.ui.Point;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;
import com.ms.util.Timer;
import com.ms.wfc.ui.UserControl;

public class PopupText extends UserControl
{
    private int \u01cd;
    private int \u01ce;
    private int \u01cf;
    private boolean \u01d0;
    private TextRect \u01d1;
    private Timer \u01a1;
    
    public void setText(final String text) {
        super.setText(text);
        this.\u01ce(true);
    }
    
    public String getText() {
        return super.getText();
    }
    
    public void setNewText(final String text) {
        super.setText(text);
        this.\u01ce(false);
    }
    
    public void setShadowSize(final int \u01ce) {
        this.\u01cd = \u01ce;
        this.\u01ce(true);
    }
    
    public int getShadowSize() {
        return this.\u01cd;
    }
    
    private void \u01cd(final Graphics graphics, final Rectangle rectangle, final int n) {
        this.\u01cf(graphics, Color.BLACK, (this.\u01cf(graphics, Color.BLACK, 0, rectangle.x + rectangle.width, rectangle.y + n, n, rectangle.height - n) == 0) ? 1 : 0, rectangle.x + n, rectangle.y + rectangle.height, rectangle.width, n);
    }
    
    public void setMaxWidth(final int \u01ce) {
        this.\u01ce = \u01ce;
    }
    
    public PopupText() {
        this.\u01cd = 0;
        this.\u01ce = 200;
        this.\u01cf = 0;
        this.\u01d0 = false;
        this.\u01a1 = null;
        _cls753A._mth821F();
        ((Form)this).setVisible(false);
        ((Form)this).setTopLevel(true);
        ((Control)this).setStyle(4, true);
        ((Control)this).setBackColor(Color.INFO);
        ((Control)this).setForeColor(Color.INFOTEXT);
        ((Control)this).setSize(new Point(100, 40));
    }
    
    public int getMaxWidth() {
        return this.\u01ce;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    private void \u01ce(final boolean b) {
        final Graphics graphics = ((Control)this).createGraphics();
        this.\u01d1 = new TextRect(graphics, super.getText(), this.\u01ce - 10);
        graphics.dispose();
        if (b) {
            final Point textSize;
            final Point size = textSize = this.\u01d1.getTextSize();
            textSize.x += 10 + this.\u01cd;
            final Point point = size;
            point.y += 10 + this.\u01cd;
            ((Control)this).setSize(size);
        }
        ((Control)this).invalidate();
    }
    
    public void setAutoTimeout(final boolean \u01d0) {
        this.\u01d0 = \u01d0;
    }
    
    public boolean getAutoTimeout() {
        return this.\u01d0;
    }
    
    public void dispose() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
            this.\u01a1 = null;
        }
        super.dispose();
    }
    
    void \u0193() {
        this.remove();
    }
    
    public void display(final String text, final Point point, final int n) {
        this.setText(text);
        this.display(point, n);
    }
    
    public void display(final String text, final Point point) {
        this.setText(text);
        this.display(point, 0);
    }
    
    public void display(final Point point, final int n) {
        final Rectangle workingArea = SystemInformation.getWorkingArea();
        final int width = ((Control)this).getWidth();
        final int height = ((Control)this).getHeight();
        int n2 = 0;
        switch (n) {
            case 3:
            case 6:
            case 9: {
                n2 = width;
                break;
            }
            case 2:
            case 5:
            case 8: {
                n2 = width / 2;
                break;
            }
            default: {
                n2 = 0;
                break;
            }
        }
        int n3 = 0;
        switch (n) {
            case 1:
            case 2:
            case 3: {
                n3 = height;
                break;
            }
            case 4:
            case 5:
            case 6: {
                n3 = height / 2;
                break;
            }
            default: {
                n3 = 0;
                break;
            }
        }
        final Point location = new Point(point.x - n2, point.y - n3);
        if (location.x < 0) {
            location.x = 0;
        }
        if (location.x + width > workingArea.width) {
            location.x = workingArea.width - width;
        }
        if (location.y < 0) {
            location.y = 0;
        }
        if (location.y + height > workingArea.height) {
            location.y = workingArea.height - height;
        }
        ((Control)this).setLocation(location);
        Windows.SetWindowPos(((Control)this).getHandle(), -1, 0, 0, 0, 0, 83);
        if (this.\u01cf != 0 || this.\u01d0) {
            int n4 = this.\u01cf;
            if (this.\u01d0) {
                if (this.\u01d1 != null) {
                    n4 = this.\u01d1.getTextLength() * 100;
                }
                if (n4 < 1000) {
                    n4 = 1000;
                }
                if (n4 < this.\u01cf) {
                    n4 = this.\u01cf;
                }
            }
            (this.\u01a1 = new Timer((TimerListener)new PopupText$TimerListener(this), (long)n4, false)).start();
        }
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        final Graphics graphics = paintEvent.graphics;
        final Point size = ((Control)this).getSize();
        final Rectangle rectangle = new Rectangle(0, 0, size.x - this.\u01cd, size.y - this.\u01cd);
        graphics.drawRect(rectangle);
        if (this.\u01d1 != null) {
            this.\u01d1.paint(paintEvent.graphics, 5, 5);
        }
        if (this.\u01cd > 0) {
            this.\u01cd(graphics, rectangle, this.\u01cd);
        }
    }
    
    public void setTimeout(final int \u01d0) {
        this.\u01cf = \u01d0;
    }
    
    public int getTimeout() {
        return this.\u01cf;
    }
    
    public void remove() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
            this.\u01a1 = null;
        }
        ((Form)this).onClosed(Event.EMPTY);
        Windows.SetWindowPos(((Control)this).getHandle(), 0, 0, 0, 0, 0, 147);
    }
    
    private int \u01cf(final Graphics graphics, final Color color, int n, final int n2, final int n3, final int n4, final int n5) {
        for (int i = 0; i < n5; ++i) {
            for (int j = 0; j < n4; ++j) {
                if (j % 2 == n) {
                    graphics.setPixel(n2 + j, n3 + i, color);
                }
            }
            n = ((n == 0) ? 1 : 0);
        }
        return n;
    }
}
