// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.win32.Windows;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Pen;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.ui.Form;
import com.ms.wfc.app.Message;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.KeyEvent;
import com.ms.util.TimerListener;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.Brush;
import com.ms.wfc.core.Event;
import com.ms.util.Timer;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.IButtonControl;
import com.ms.wfc.ui.Control;

public class ImageBtn extends Control implements IButtonControl
{
    private Image \u0191;
    private Image \u0192;
    private Color \u0193;
    private boolean \u0194;
    private boolean \u0195;
    private boolean \u0196;
    private int \u0197;
    private int \u0198;
    private int \u0199;
    private int \u019a;
    private Color \u019b;
    private int \u019c;
    private boolean \u019d;
    private boolean \u019e;
    private boolean \u019f;
    private boolean \u01a0;
    private Timer \u01a1;
    private static Class \u01a2;
    private static Class \u01a3;
    
    public void setToggleButton(final boolean \u0195) {
        this.\u0195 = \u0195;
    }
    
    protected void onMouseEnter(final Event event) {
        if (this.\u0199 == 2 || this.\u0192 != null) {
            this.\u01a0 = true;
            this.invalidate();
        }
    }
    
    public boolean getToggleButton() {
        return this.\u0195;
    }
    
    public void setText(final String text) {
        super.setText(text);
        this.invalidate();
    }
    
    public final int getDialogResult() {
        return this.\u019c;
    }
    
    public void setDialogResult(final int \u026f) {
        this.\u019c = \u026f;
    }
    
    public Brush getBrush() {
        return Control.defaultBrush;
    }
    
    public void setBrush(final Brush brush) {
    }
    
    public void setState(final boolean \u0269) {
        this.\u0196 = \u0269;
    }
    
    public boolean getState() {
        return this.\u0196;
    }
    
    protected boolean processMnemonic(final char c) {
        if (Control.isMnemonic(c, this.getText()) && this.canSelect()) {
            this.onClick(Event.EMPTY);
            return true;
        }
        return false;
    }
    
    private boolean \u0191() {
        if (this.\u019e) {
            return true;
        }
        boolean b = Control.getMouseButtons() != 0;
        if (b && !this.rectToScreen(this.getClientRect()).contains(Control.getMousePosition())) {
            b = false;
        }
        return b;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    public void setBorderDisplay(final int \u0199) {
        if (!ImageBtnBorderDisplay.valid(\u0199)) {
            throw new WFCInvalidEnumException("borderDisplay", \u0199, (ImageBtn.\u01a2 != null) ? ImageBtn.\u01a2 : (ImageBtn.\u01a2 = \u00c6("com.mim.wfc.ui.ImageBtnBorderDisplay")));
        }
        this.\u0199 = \u0199;
        this.invalidate();
    }
    
    public int getBorderDisplay() {
        return this.\u0199;
    }
    
    public final Color getBackColor() {
        return Color.CONTROL;
    }
    
    public final void setBackColor(final Color color) {
    }
    
    public void setTextColor(final Color \u019b) {
        this.\u019b = \u019b;
        this.invalidate();
    }
    
    public Color getTextColor() {
        return this.\u019b;
    }
    
    protected void onMouseLeave(final Event event) {
        if (this.\u0199 == 2 || this.\u0192 != null) {
            this.\u01a0 = false;
            this.invalidate();
        }
    }
    
    private void \u0192(final Image image) {
        if (image != null && image instanceof Bitmap) {
            final Bitmap bitmap = (Bitmap)image;
            bitmap.setTransparent(true);
            if (this.\u0194) {
                final Graphics graphics = bitmap.getGraphics();
                bitmap.setTransparentColor(graphics.getPixel(0, 0));
                graphics.dispose();
                return;
            }
            bitmap.setTransparentColor(this.\u0193);
        }
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (this.getCreated()) {
            this.invalidate();
        }
    }
    
    protected void onEnter(final Event event) {
        super.onEnter(event);
        this.invalidate();
    }
    
    void \u0193() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        this.onClick(Event.EMPTY);
        if (this.\u01a1 != null) {
            (this.\u01a1 = new Timer((TimerListener)new ImageBtn$TimerListener(this), (long)(1000 / this.\u0197), false)).start();
        }
    }
    
    public void setImage(final Image \u0192) {
        this.\u0192(this.\u0191 = \u0192);
        this.invalidate();
    }
    
    public void performClick() {
        if (this.canSelect()) {
            this.onClick(Event.EMPTY);
        }
    }
    
    public Image getImage() {
        return this.\u0191;
    }
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        super.onKeyDown(keyEvent);
        if (keyEvent.getKeyCode() == 32) {
            this.\u019e = true;
            this.performClick();
            this.invalidate();
        }
    }
    
    protected void onLostFocus(final Event event) {
        super.onLostFocus(event);
        this.invalidate();
    }
    
    public void setXDistance(final int \u019a) {
        this.\u019a = \u019a;
        this.invalidate();
    }
    
    public int getXDistance() {
        return this.\u019a;
    }
    
    protected void propertyChanged(final int n) {
        switch (n) {
            default: {
                super.propertyChanged(n);
            }
            case 8:
            case 9:
            case 10:
            case 11: {}
        }
    }
    
    public String toString() {
        return super.toString() + ", text: " + this.getText();
    }
    
    public ImageBtn() {
        this.\u0191 = null;
        this.\u0192 = null;
        this.\u0193 = new Color(192, 192, 192);
        this.\u0194 = true;
        this.\u0195 = false;
        this.\u0196 = false;
        this.\u0197 = 0;
        this.\u0198 = 2;
        this.\u0199 = 1;
        this.\u019a = 10;
        this.\u019e = false;
        this.\u019f = false;
        this.\u01a0 = false;
        this.\u01a1 = null;
        _cls753A._mth821F();
        this.setSize(100, 35);
        this.setStyle(4, true);
        this.setStyle(256, true);
        this.setStyle(2, true);
    }
    
    public void setRepeatRate(final int \u0268) {
        this.\u0197 = \u0268;
    }
    
    public Color getForeColor() {
        return Color.WINDOWTEXT;
    }
    
    protected void onMouseUp(final MouseEvent mouseEvent) {
        if (this.\u0197 != 0 && this.\u01a1 != null) {
            this.\u01a1.stop();
            this.\u01a1 = null;
        }
        final boolean contains = this.rectToScreen(this.getClientRect()).contains(Control.getMousePosition());
        super.onMouseUp(mouseEvent);
        if (contains) {
            this.invalidate();
        }
    }
    
    public void setForeColor(final Color color) {
    }
    
    public void setTransparentColor(final Color \u0260) {
        this.\u0193 = \u0260;
        this.\u0192(this.\u0191);
        this.\u0192(this.\u0192);
        this.invalidate();
    }
    
    protected void onLeave(final Event event) {
        super.onLeave(event);
        this.invalidate();
    }
    
    public Color getTransparentColor() {
        return this.\u0193;
    }
    
    public int getRepeatRate() {
        return this.\u0197;
    }
    
    protected void wndProc(final Message message) {
        if (message.msg == 8465) {
            if (message.wParam >> 16 == 0) {
                this.onClick(Event.EMPTY);
            }
            return;
        }
        super.wndProc(message);
    }
    
    public void setHoverImage(final Image \u0192) {
        this.\u0192(this.\u0192 = \u0192);
        this.invalidate();
    }
    
    public Image getHoverImage() {
        return this.\u0192;
    }
    
    protected void onKeyUp(final KeyEvent keyEvent) {
        super.onKeyUp(keyEvent);
        if (keyEvent.getKeyCode() == 32) {
            this.\u019e = false;
            this.invalidate();
        }
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    protected void onGotFocus(final Event event) {
        super.onGotFocus(event);
        this.invalidate();
    }
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        super.onMouseDown(mouseEvent);
        this.focus();
        this.invalidate();
        if (this.\u0197 != 0) {
            (this.\u01a1 = new Timer((TimerListener)new ImageBtn$TimerListener(this), 500L, false)).start();
        }
    }
    
    public void dispose() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        super.dispose();
    }
    
    protected void onClick(final Event event) {
        if (this.\u0195) {
            this.\u0196 = !this.\u0196;
        }
        final Form form = this.getForm();
        if (form != null) {
            form.setDialogResult(this.\u019c);
        }
        this.invalidate();
        this.update();
        super.onClick(event);
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        super.onPaint(paintEvent);
        final Rectangle clientRect = this.getClientRect();
        final Bitmap bitmap = new Bitmap(clientRect.width, clientRect.height);
        final Graphics graphics = paintEvent.graphics;
        final Graphics graphics2 = bitmap.getGraphics();
        graphics2.setBackColor(graphics.getBackColor());
        graphics2.setTextColor((this.\u019b != null) ? this.\u019b : graphics.getTextColor());
        graphics2.setFont(graphics.getFont());
        graphics2.setBrush(graphics.getBrush());
        graphics2.setPen(graphics.getPen());
        final boolean \u0192 = this.\u0191();
        this.\u019f = \u0192;
        final boolean enabled = this.getEnabled();
        final boolean focused = this.getFocused();
        final boolean b = this.\u0198 == 2 || this.\u0198 == 1;
        final boolean b2 = this.\u0198 == 3 || this.\u0198 == 4;
        final boolean b3 = this.\u0198 == 2 || this.\u0198 == 3;
        final Image image = (this.\u01a0 && this.\u0192 != null && enabled) ? this.\u0192 : this.\u0191;
        String text = this.getText();
        if (text != null && text.length() == 0) {
            text = null;
        }
        this.getBrush();
        this.getBackColor();
        if (this.\u0199 == 1 || (this.\u0199 == 2 && (this.\u01a0 || focused || \u0192 || this.\u0196))) {
            int n;
            if (this.\u0196) {
                n = 1024;
                graphics2.setBackColor(Color.CONTROLLIGHTLIGHT);
            }
            else if (\u0192) {
                n = 512;
            }
            else if (!enabled) {
                n = 256;
                graphics2.setTextColor(Color.GRAYTEXT);
            }
            else {
                n = 0;
            }
            graphics2.drawButton(clientRect, n);
        }
        else {
            graphics2.setPen((Pen)null);
            graphics2.drawRect(clientRect);
        }
        clientRect.inflate(-2, -2);
        if (this.getTabStop()) {
            clientRect.inflate(-1, -1);
            if (focused) {
                graphics2.drawFocusRect(clientRect);
            }
            clientRect.inflate(-1, -1);
        }
        graphics2.setClip(clientRect);
        if ((this.\u0199 == 1 || this.\u0199 == 2) && (\u0192 || this.\u0196)) {
            clientRect.offset(1, 1);
        }
        final Rectangle rectangle = new Rectangle(0, 0, 0, 0);
        final Rectangle rectangle2 = new Rectangle(0, 0, 0, 0);
        if (image != null) {
            final Point size = image.getSize();
            rectangle.width = size.x;
            rectangle.height = size.y;
        }
        boolean b4 = false;
        if (text != null) {
            Point point = graphics2.getTextSize(text);
            int width = 0;
            if (image != null && (b || b2)) {
                if (point.x + rectangle.width > clientRect.width) {
                    b4 = true;
                    width = clientRect.width - rectangle.width - 6;
                }
            }
            else if (point.x > clientRect.width) {
                b4 = true;
                width = clientRect.width;
            }
            if (b4) {
                point = graphics2.getTextSize(text, width, 16);
            }
            rectangle2.width = point.x;
            rectangle2.height = point.y;
        }
        if (text == null) {
            rectangle.x = clientRect.x + (clientRect.width - rectangle.width) / 2;
            rectangle.y = clientRect.y + (clientRect.height - rectangle.height) / 2;
        }
        if (image == null) {
            rectangle2.x = clientRect.x + (clientRect.width - rectangle2.width) / 2;
            rectangle2.y = clientRect.y + (clientRect.height - rectangle2.height) / 2;
        }
        if (text != null && image != null) {
            if (b || b2) {
                int n2 = clientRect.width - rectangle.width - rectangle2.width;
                if (n2 < 0) {
                    n2 = 0;
                }
                rectangle.y = clientRect.y + (clientRect.height - rectangle.height) / 2;
                rectangle2.y = clientRect.y + (clientRect.height - rectangle2.height) / 2;
                if (b) {
                    if (b3) {
                        rectangle.x = clientRect.x + n2 / 3;
                        rectangle2.x = rectangle.x + rectangle.width + n2 / 3;
                    }
                    else {
                        rectangle.x = clientRect.x + this.\u019a;
                        rectangle2.x = rectangle.x + rectangle.width + this.\u019a;
                    }
                }
                else if (b3) {
                    rectangle2.x = clientRect.x + n2 / 3;
                    rectangle.x = rectangle2.x + rectangle2.width + n2 / 3;
                }
                else {
                    rectangle.x = clientRect.x + clientRect.width - rectangle.width - this.\u019a;
                    rectangle2.x = clientRect.x + this.\u019a;
                }
            }
            else {
                int n3 = clientRect.height - rectangle.height - rectangle2.height;
                if (n3 < 0) {
                    n3 = 0;
                }
                rectangle.y = clientRect.y + n3 / 3;
                rectangle.x = clientRect.x + (clientRect.width - rectangle.width) / 2;
                rectangle2.y = rectangle.y + rectangle.width + n3 / 3;
                rectangle2.x = clientRect.x + (clientRect.width - rectangle2.width) / 2;
            }
        }
        if (text != null) {
            if (rectangle2.x < clientRect.x) {
                rectangle2.x = clientRect.x;
            }
            if (rectangle2.y < clientRect.y) {
                rectangle2.y = clientRect.y;
            }
            if (rectangle2.x + rectangle2.width > clientRect.x + clientRect.width) {
                rectangle2.width = clientRect.x + clientRect.width - rectangle2.x;
            }
            if (rectangle2.y + rectangle2.height > clientRect.y + clientRect.height) {
                rectangle2.height = clientRect.y + clientRect.height - rectangle2.x;
            }
            int n4;
            if (b4) {
                n4 = 17;
            }
            else {
                n4 = 32805;
            }
            UIUtil.drawString(graphics2, text, rectangle2, n4, enabled);
        }
        if (image != null) {
            if (rectangle2.x < clientRect.x) {
                rectangle2.x = clientRect.x;
            }
            if (rectangle2.y < clientRect.y) {
                rectangle2.y = clientRect.y;
            }
            if (rectangle2.x + rectangle2.width > clientRect.x + clientRect.width) {
                rectangle2.width = clientRect.x + clientRect.width - rectangle2.x;
            }
            if (rectangle2.y + rectangle2.height > clientRect.y + clientRect.height) {
                rectangle2.height = clientRect.y + clientRect.height - rectangle2.x;
            }
            if (rectangle.x < clientRect.x) {
                rectangle.x = clientRect.x;
            }
            if (rectangle.y < clientRect.y) {
                rectangle.y = clientRect.y;
            }
            if (rectangle.x + rectangle.width > clientRect.x + clientRect.width) {
                rectangle.width = clientRect.x + clientRect.width - rectangle.x;
            }
            if (rectangle.y + rectangle.height > clientRect.y + clientRect.height) {
                rectangle.height = clientRect.y + clientRect.height - rectangle.x;
            }
            UIUtil.drawImage(graphics2, image, rectangle, enabled);
        }
        graphics2.dispose();
        graphics.drawImage((Image)bitmap, 0, 0);
        bitmap.dispose();
    }
    
    public void setAutoTransparent(final boolean \u0263) {
        this.\u0194 = \u0263;
        this.\u0192(this.\u0191);
        this.\u0192(this.\u0192);
        this.invalidate();
    }
    
    public boolean getAutoTransparent() {
        return this.\u0194;
    }
    
    public void setAlignment(final int \u0199) {
        if (!ImageBtnAlignStyle.valid(\u0199)) {
            throw new WFCInvalidEnumException("alignment", \u0199, (ImageBtn.\u01a3 != null) ? ImageBtn.\u01a3 : (ImageBtn.\u01a3 = \u00c6("com.mim.wfc.ui.ImageBtnAlignStyle")));
        }
        this.\u0198 = \u0199;
        this.invalidate();
    }
    
    public int getAlignment() {
        return this.\u0198;
    }
    
    public void setEnabled(final boolean b) {
        super.setEnabled(b);
        this.setTabStop(b);
        this.invalidate();
        this.update();
    }
    
    public void notifyDefault(final boolean \u0272) {
        if (this.\u019d != \u0272) {
            this.\u019d = \u0272;
            if (this.getHandleCreated()) {
                final int \u02722 = this.\u019d ? 1 : 0;
                if ((Windows.GetWindowLong(this.getHandle(), -16) & 0xF) != \u02722) {
                    this.sendMessage(244, \u02722, 1);
                }
            }
        }
    }
    
    protected void onMouseMove(final MouseEvent mouseEvent) {
        super.onMouseMove(mouseEvent);
        if (this.\u0191() != this.\u019f) {
            this.invalidate();
        }
    }
}
