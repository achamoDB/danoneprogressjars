// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Form;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.ui.UpDownAlignment;
import com.ms.wfc.ui.Brush;
import com.ms.wfc.ui.Pen;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.PaintEvent;
import com.ms.util.TimerListener;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.core.Event;
import com.ms.wfc.ui.Rectangle;
import com.ms.util.Timer;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.UserControl;

public class SpinBtn extends UserControl
{
    private boolean \u0215;
    private boolean \u0216;
    private boolean \u0217;
    private boolean \u0250;
    private Control \u0251;
    private int \u0198;
    private int \u0197;
    private EventHandler \u0252;
    private EventHandler \u0253;
    private EventHandler \u0254;
    private int \u0255;
    private int \u0256;
    private Timer \u01a1;
    private static Class \u0257;
    
    private Rectangle \u0215() {
        final Rectangle clientRect;
        final Rectangle rectangle = clientRect = ((Control)this).getClientRect();
        clientRect.x += rectangle.width / 2;
        final Rectangle rectangle2 = rectangle;
        rectangle2.width -= rectangle.width / 2;
        return rectangle;
    }
    
    protected void _mouseEnter(final Object o, final Event event) {
        if (this.\u0254()) {
            final int \u0217 = this.\u0216();
            if (\u0217 != this.\u0255) {
                this.\u0255 = \u0217;
                ((Control)this).invalidate();
            }
        }
    }
    
    protected void onMouseMove(final MouseEvent mouseEvent) {
        if (this.\u0254()) {
            super.onMouseMove(mouseEvent);
            final int \u0217 = this.\u0216();
            if (\u0217 != this.\u0255) {
                this.\u0255 = \u0217;
                ((Control)this).invalidate();
            }
        }
    }
    
    private int \u0216() {
        this.\u0256 = Control.getMouseButtons();
        if (this.\u0256 != 0) {
            final Point mousePosition = Control.getMousePosition();
            if (this.\u0215) {
                if (((Form)this).rectToScreen(this.\u0257()).contains(mousePosition)) {
                    return 2;
                }
                if (((Form)this).rectToScreen(this.\u0215()).contains(mousePosition)) {
                    return 1;
                }
            }
            else {
                if (((Form)this).rectToScreen(this.\u0217()).contains(mousePosition)) {
                    return 1;
                }
                if (((Form)this).rectToScreen(this.\u0253()).contains(mousePosition)) {
                    return 2;
                }
            }
        }
        return 0;
    }
    
    public Control getBuddyControl() {
        return this.\u0251;
    }
    
    public void setBuddyControl(final Control \u0251) {
        if (this.\u0251 != null) {
            this.\u0251.removeOnMove(new EventHandler((Object)this, "_moveBuddy"));
            this.\u0251.removeOnResize(new EventHandler((Object)this, "_resizeBuddy"));
            this.\u0251.removeOnDestroyHandle(new EventHandler((Object)this, "_destroyBuddy"));
        }
        this.\u0251 = \u0251;
        this.autoSize();
        this.\u0252();
        if (\u0251 != null) {
            \u0251.addOnResize(new EventHandler((Object)this, "_resizeBuddy"));
            \u0251.addOnMove(new EventHandler((Object)this, "_moveBuddy"));
            \u0251.addOnDestroyHandle(new EventHandler((Object)this, "_destroyBuddy"));
        }
    }
    
    public void addOnSpinEnd(final EventHandler eventHandler) {
        this.\u0254 = (EventHandler)Delegate.combine((Delegate)this.\u0254, (Delegate)eventHandler);
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
        this.autoBuddy();
    }
    
    private Rectangle \u0217() {
        final Rectangle clientRect;
        final Rectangle rectangle = clientRect = ((Control)this).getClientRect();
        clientRect.height /= 2;
        return rectangle;
    }
    
    protected void onSpinDown(final Event event) {
        if (this.\u0253 != null) {
            this.\u0253.invoke((Object)this, event);
        }
    }
    
    private void \u0250(final boolean b) {
        if (this.\u0251 != null) {
            int n = (this.\u0255 == 1) ? 38 : 40;
            if ((this.\u0256 & 0x400000) != 0x0) {
                n |= 0x20000;
            }
            else if ((this.\u0256 & 0x200000) != 0x0) {
                n |= 0x10000;
            }
            if (b) {
                this.\u0251.sendMessage(256, n, 0);
                return;
            }
            this.\u0251.sendMessage(257, n, Integer.MIN_VALUE);
        }
    }
    
    private void autoBuddy() {
        final Control parent;
        if (this.\u0217 && (parent = ((Control)this).getParent()) != null) {
            this.setBuddyControl(parent.getNextControl((Control)this, false));
        }
    }
    
    public void addOnSpinUp(final EventHandler eventHandler) {
        this.\u0252 = (EventHandler)Delegate.combine((Delegate)this.\u0252, (Delegate)eventHandler);
    }
    
    protected void _mouseLeave(final Object o, final Event event) {
        if (this.\u0254()) {
            final int \u0217 = this.\u0216();
            if (\u0217 != this.\u0255) {
                this.\u0255 = \u0217;
                ((Control)this).invalidate();
            }
        }
    }
    
    private void \u0251(final Graphics graphics, final Rectangle rectangle, final boolean b) {
        final Point[] array = { new Point(), new Point(), new Point() };
        boolean b2 = false;
        int n = rectangle.height * 2 / 3;
        if (n % 2 == 0) {
            --rectangle.y;
            b2 = true;
        }
        else {
            --n;
        }
        int n2 = n / 2;
        if (n2 > rectangle.width * 2 / 3) {
            n2 = rectangle.width * 2 / 3;
            n = n2 * 2;
        }
        if (!b2 && rectangle.height % 2 != n % 2) {
            --rectangle.y;
        }
        final int y = rectangle.y + (rectangle.height - n) / 2;
        final int y2 = y + n;
        final int y3 = y + n / 2;
        int n3;
        int x;
        if (b) {
            n3 = rectangle.x + (rectangle.width - n2) / 2;
            x = n3 + n2;
        }
        else {
            x = rectangle.x + (rectangle.width - n2) / 2;
            n3 = x + n2;
        }
        array[0].x = n3;
        array[0].y = y;
        array[1].x = n3;
        array[1].y = y2;
        array[2].x = x;
        array[2].y = y3;
        graphics.drawPolygon(array);
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        this.autoSize();
        if (((Control)this).getCreated()) {
            ((Control)this).invalidate();
        }
    }
    
    void \u0193() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        if (((Form)this).rectToScreen(((Control)this).getClientRect()).contains(Control.getMousePosition())) {
            this.\u0256();
            if (this.\u01a1 != null) {
                (this.\u01a1 = new Timer((TimerListener)new SpinBtn$TimerListener(this), (long)(1000 / this.\u0197), false)).start();
            }
        }
    }
    
    private void \u0252() {
        if (this.\u0251 != null && this.\u0198 != 2) {
            final Point location = this.\u0251.getLocation();
            if (this.\u0198 == 0) {
                final Point point = location;
                point.x -= ((Control)this).getWidth();
            }
            else {
                final Point point2 = location;
                point2.x += this.\u0251.getWidth();
            }
            ((Control)this).setLocation(location);
            ((Control)this).invalidate();
        }
    }
    
    public boolean getHorizontal() {
        return this.\u0215;
    }
    
    public void setHorizontal(final boolean \u0215) {
        this.\u0215 = \u0215;
        this.autoSize();
        ((Control)this).invalidate();
    }
    
    public void addOnSpinDown(final EventHandler eventHandler) {
        this.\u0253 = (EventHandler)Delegate.combine((Delegate)this.\u0253, (Delegate)eventHandler);
    }
    
    private Rectangle \u0253() {
        final Rectangle clientRect;
        final Rectangle rectangle = clientRect = ((Control)this).getClientRect();
        clientRect.y += rectangle.height / 2;
        final Rectangle rectangle2 = rectangle;
        rectangle2.height -= rectangle.height / 2;
        return rectangle;
    }
    
    private boolean \u0254() {
        if (this.\u0251 != null) {
            return this.\u0251.getEnabled();
        }
        return ((Control)this).getEnabled();
    }
    
    protected void _moveBuddy(final Object o, final Event event) {
        this.\u0252();
    }
    
    public boolean getValidateBuddy() {
        return this.\u0216;
    }
    
    public void setValidateBuddy(final boolean \u0217) {
        this.\u0216 = \u0217;
    }
    
    public SpinBtn() {
        this.\u0215 = false;
        this.\u0216 = true;
        this.\u0217 = false;
        this.\u0250 = true;
        this.\u0251 = null;
        this.\u0198 = 1;
        this.\u0197 = 20;
        this.\u0255 = 0;
        this.\u0256 = 0;
        this.\u01a1 = null;
        _cls753A._mth821F();
        ((Control)this).setSize(23, 23);
        ((Control)this).setStyle(256, true);
        ((Control)this).setStyle(2, true);
        ((Control)this).setTabStop(false);
        ((Control)this).setText("");
        ((Control)this).addOnMouseEnter(new EventHandler((Object)this, "_mouseEnter"));
        ((Control)this).addOnMouseLeave(new EventHandler((Object)this, "_mouseLeave"));
    }
    
    public void setRepeatRate(final int \u0268) {
        this.\u0197 = \u0268;
    }
    
    public int getRepeatRate() {
        return this.\u0197;
    }
    
    protected void onMouseUp(final MouseEvent mouseEvent) {
        if (this.\u0197 != 0 && this.\u01a1 != null) {
            this.\u01a1.stop();
            this.\u01a1 = null;
        }
        if (this.\u0254()) {
            this.\u0250(false);
            this.\u0255 = 0;
            if (((Form)this).rectToScreen(((Control)this).getClientRect()).contains(Control.getMousePosition())) {
                ((Control)this).invalidate();
            }
            if (this.\u0216 && this.\u0251 != null && this.\u0251 instanceof NumEdit) {
                ((NumEdit)this.\u0251).validate();
            }
            this.onSpinEnd((Event)mouseEvent);
            super.onMouseUp(mouseEvent);
        }
    }
    
    public void removeOnSpinUp(final EventHandler eventHandler) {
        this.\u0252 = (EventHandler)Delegate.remove((Delegate)this.\u0252, (Delegate)eventHandler);
    }
    
    public void removeOnSpinEnd(final EventHandler eventHandler) {
        this.\u0254 = (EventHandler)Delegate.remove((Delegate)this.\u0254, (Delegate)eventHandler);
    }
    
    private void autoSize() {
        if (this.\u0251 != null && this.\u0250) {
            final int height = this.\u0251.getHeight();
            int n;
            if (this.\u0215) {
                if (height == 20) {
                    n = 12;
                }
                else {
                    n = height * 3 / 2;
                    if (n % 2 == 1) {
                        --n;
                    }
                }
            }
            else {
                n = height * 2 / 3;
                if (n % 2 == 1) {
                    --n;
                }
            }
            ((Control)this).setSize(n, height);
            ((Control)this).invalidate();
        }
    }
    
    private void \u0255(final Graphics graphics, final Rectangle rectangle, final boolean b) {
        final Point[] array = { new Point(), new Point(), new Point() };
        boolean b2 = false;
        int n = rectangle.width * 2 / 3;
        if (n % 2 == 0) {
            --rectangle.x;
            b2 = true;
        }
        else {
            --n;
        }
        int n2 = n / 2;
        if (n2 > rectangle.height * 2 / 3) {
            n2 = rectangle.height * 2 / 3;
            n = n2 * 2;
        }
        if (!b2 && rectangle.width % 2 != n % 2) {
            --rectangle.x;
        }
        final int x = rectangle.x + (rectangle.width - n) / 2;
        final int x2 = x + n;
        final int x3 = x + n / 2;
        int n3;
        int y;
        if (b) {
            n3 = rectangle.y + (rectangle.height - n2) / 2;
            y = n3 + n2;
        }
        else {
            y = rectangle.y + (rectangle.height - n2) / 2;
            n3 = y + n2;
        }
        array[0].x = x;
        array[0].y = n3;
        array[1].x = x2;
        array[1].y = n3;
        array[2].x = x3;
        array[2].y = y;
        graphics.drawPolygon(array);
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        if (this.\u0254()) {
            super.onMouseDown(mouseEvent);
            this.\u0255 = this.\u0216();
            ((Control)this).invalidate();
            if (this.\u0251 != null && !this.\u0251.getFocused()) {
                this.\u0251.focus();
            }
            this.\u0256();
            if (this.\u0197 != 0) {
                (this.\u01a1 = new Timer((TimerListener)new SpinBtn$TimerListener(this), 500L, false)).start();
            }
        }
    }
    
    public void dispose() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        super.dispose();
    }
    
    private void \u0256() {
        this.\u0250(true);
        if (this.\u0255 == 1) {
            this.onSpinUp(Event.EMPTY);
            return;
        }
        this.onSpinDown(Event.EMPTY);
    }
    
    public boolean shouldPersistBuddyControl() {
        return !this.\u0217 && this.\u0251 != null;
    }
    
    public boolean getAutoSize() {
        return this.\u0250;
    }
    
    public void setAutoSize(final boolean \u0250) {
        this.\u0250 = \u0250;
        this.autoSize();
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        super.onPaint(paintEvent);
        final Graphics graphics = paintEvent.graphics;
        final boolean \u0254 = this.\u0254();
        Rectangle rectangle;
        Rectangle rectangle2;
        if (this.\u0215) {
            rectangle = this.\u0257();
            rectangle2 = this.\u0215();
        }
        else {
            rectangle = this.\u0217();
            rectangle2 = this.\u0253();
        }
        int n2;
        int n;
        if (!\u0254) {
            n = (n2 = 256);
        }
        else {
            n = (n2 = 0);
            if (this.\u0255 == 1) {
                n2 = 512;
            }
            if (this.\u0255 == 2) {
                n = 512;
            }
        }
        graphics.drawButton(rectangle, n2);
        graphics.drawButton(rectangle2, n);
        if (this.\u0255 == 1) {
            final Rectangle rectangle3 = rectangle;
            ++rectangle3.x;
            final Rectangle rectangle4 = rectangle;
            ++rectangle4.y;
        }
        if (this.\u0255 == 2) {
            final Rectangle rectangle5 = rectangle2;
            ++rectangle5.x;
            final Rectangle rectangle6 = rectangle2;
            ++rectangle6.y;
        }
        if (this.\u0215) {
            if (rectangle2.width != rectangle.width) {
                rectangle2.width = rectangle.width;
            }
            else {
                final Rectangle rectangle7 = rectangle2;
                --rectangle7.x;
            }
        }
        else if (rectangle2.height != rectangle.height) {
            rectangle2.height = rectangle.height;
        }
        else {
            final Rectangle rectangle8 = rectangle2;
            --rectangle8.y;
        }
        rectangle.inflate(-3, -3);
        rectangle2.inflate(-3, -3);
        final Pen pen = new Pen(\u0254 ? Color.CONTROLDARKDARK : Color.CONTROLDARK);
        final Brush brush = new Brush(\u0254 ? Color.CONTROLDARKDARK : Color.CONTROLDARK);
        graphics.setPen(pen);
        graphics.setBrush(brush);
        if (this.\u0215) {
            this.\u0251(graphics, rectangle, false);
            this.\u0251(graphics, rectangle2, true);
            return;
        }
        this.\u0255(graphics, rectangle, false);
        this.\u0255(graphics, rectangle2, true);
    }
    
    protected void _resizeBuddy(final Object o, final Event event) {
        this.autoSize();
    }
    
    protected void _destroyBuddy(final Object o, final Event event) {
        this.setBuddyControl(null);
    }
    
    public boolean getAutoBuddy() {
        return this.\u0217;
    }
    
    public void setAutoBuddy(final boolean \u0217) {
        this.\u0217 = \u0217;
        this.autoBuddy();
    }
    
    public int getAlignment() {
        return this.\u0198;
    }
    
    public void setAlignment(final int \u0199) {
        if (!UpDownAlignment.valid(\u0199)) {
            throw new WFCInvalidEnumException("alignment", \u0199, (SpinBtn.\u0257 != null) ? SpinBtn.\u0257 : (SpinBtn.\u0257 = \u00c6("com.ms.wfc.ui.UpDownAlignment")));
        }
        this.\u0198 = \u0199;
        this.\u0252();
    }
    
    public void removeOnSpinDown(final EventHandler eventHandler) {
        this.\u0253 = (EventHandler)Delegate.remove((Delegate)this.\u0253, (Delegate)eventHandler);
    }
    
    protected void onSpinUp(final Event event) {
        if (this.\u0252 != null) {
            this.\u0252.invoke((Object)this, event);
        }
    }
    
    protected void onSpinEnd(final Event event) {
        if (this.\u0254 != null) {
            this.\u0254.invoke((Object)this, event);
        }
    }
    
    private Rectangle \u0257() {
        final Rectangle clientRect;
        final Rectangle rectangle = clientRect = ((Control)this).getClientRect();
        clientRect.width /= 2;
        return rectangle;
    }
}
