// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.ui.KeyEvent;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.LeftRightAlignment;
import com.ms.wfc.core.Component;
import com.mim.wfc.license._cls753A;
import com.ms.lang.Delegate;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.Event;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.UserControl;

public class ColorRadioButton extends UserControl
{
    private int \u0389;
    private int \u038e;
    private boolean \u039b;
    private boolean \u038f;
    private EventHandler \u0391;
    private static Bitmap \u0393;
    private static Bitmap \u0395;
    private Bitmap \u0396;
    static Color[] \u039c;
    static Color[] \u039d;
    private static Class \u0397;
    private static Class \u0398;
    
    public int getColorStyle() {
        return this.\u0389;
    }
    
    public void setColorStyle(final int \u03ae) {
        if (!ControlColorStyle.valid(\u03ae)) {
            throw new WFCInvalidEnumException("style", \u03ae, (ColorRadioButton.\u0397 != null) ? ColorRadioButton.\u0397 : (ColorRadioButton.\u0397 = \u00c6("com.mim.wfc.ui.ControlColorStyle")));
        }
        String s = null;
        switch (this.\u0389 = \u03ae) {
            case 1: {
                s = "imageBlue";
                break;
            }
            case 2: {
                s = "imageGreen";
                break;
            }
            case 3: {
                s = "imageRed";
                break;
            }
            case 4: {
                s = "imageYellow";
                break;
            }
            default: {
                s = "imageBlack";
                break;
            }
        }
        UIUtil.convertBitmapColors(this.\u0396 = (Bitmap)((IResourceManager)new ResourceManager((IResourceLoader)this, "ColorRadioButton")).getObject(s), ColorRadioButton.\u039c, ColorRadioButton.\u039d);
        ((Control)this).invalidate();
    }
    
    public boolean getChecked() {
        return this.\u039b;
    }
    
    public void setChecked(final boolean \u03bb) {
        if (\u03bb) {
            final Control parent = ((Control)this).getParent();
            if (parent != null) {
                for (int controlCount = parent.getControlCount(), i = 0; i < controlCount; ++i) {
                    final Control control = parent.getControl(i);
                    if (control instanceof ColorRadioButton && control != this) {
                        ((ColorRadioButton)control).setChecked(false);
                    }
                }
            }
        }
        this.\u039b = \u03bb;
        ((Control)this).invalidate();
        this.onCheckedChanged(Event.EMPTY);
    }
    
    protected boolean processMnemonic(final char c) {
        if (Control.isMnemonic(c, ((Control)this).getText()) && ((Control)this).canSelect()) {
            this.\u0256();
            ((Control)this).onClick(Event.EMPTY);
            return true;
        }
        return false;
    }
    
    public void removeOnCheckedChanged(final EventHandler eventHandler) {
        this.\u0391 = (EventHandler)Delegate.remove((Delegate)this.\u0391, (Delegate)eventHandler);
    }
    
    public ColorRadioButton() {
        this.\u0389 = 1;
        this.\u038e = 1;
        this.\u039b = false;
        this.\u038f = true;
        _cls753A._mth821F();
        ((Control)this).setSize(100, 23);
        ((Control)this).setStyle(2, true);
        \u039b(false);
        final ResourceManager resourceManager = new ResourceManager((IResourceLoader)this, "ColorRadioButton");
        if (ColorRadioButton.\u0393 == null) {
            UIUtil.convertBitmapColors(ColorRadioButton.\u0393 = (Bitmap)((IResourceManager)resourceManager).getObject("imageEmpty"), ColorRadioButton.\u039c, ColorRadioButton.\u039d);
        }
        if (ColorRadioButton.\u0395 == null) {
            UIUtil.convertBitmapColors(ColorRadioButton.\u0395 = (Bitmap)((IResourceManager)resourceManager).getObject("imageGray"), ColorRadioButton.\u039c, ColorRadioButton.\u039d);
        }
        if (this.\u0396 == null) {
            UIUtil.convertBitmapColors(this.\u0396 = (Bitmap)((IResourceManager)resourceManager).getObject("imageBlue"), ColorRadioButton.\u039c, ColorRadioButton.\u039d);
        }
    }
    
    public void addOnCheckedChanged(final EventHandler eventHandler) {
        this.\u0391 = (EventHandler)Delegate.combine((Delegate)this.\u0391, (Delegate)eventHandler);
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    protected void onLeave(final Event event) {
        super.onLeave(event);
        ((Control)this).invalidate();
    }
    
    public int getTextAlign() {
        return this.\u038e;
    }
    
    public void setTextAlign(final int \u03cd) {
        if (!LeftRightAlignment.valid(\u03cd)) {
            throw new WFCInvalidEnumException("alignment", \u03cd, (ColorRadioButton.\u0398 != null) ? ColorRadioButton.\u0398 : (ColorRadioButton.\u0398 = \u00c6("com.ms.wfc.ui.LeftRightAlignment")));
        }
        this.\u038e = \u03cd;
        ((Control)this).invalidate();
    }
    
    static void \u039b(final boolean b) {
        if (b || ColorRadioButton.\u039c == null) {
            ColorRadioButton.\u039c = new Color[3];
            ColorRadioButton.\u039d = new Color[3];
            ColorRadioButton.\u039c[0] = new Color(192, 192, 192);
            ColorRadioButton.\u039d[0] = Color.CONTROL;
            ColorRadioButton.\u039c[1] = new Color(128, 128, 128);
            ColorRadioButton.\u039d[1] = Color.CONTROLDARK;
            ColorRadioButton.\u039c[2] = new Color(255, 255, 255);
            ColorRadioButton.\u039d[2] = Color.CONTROLLIGHTLIGHT;
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
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        this.\u0256();
        super.onMouseDown(mouseEvent);
        ((Control)this).focus();
        ((Control)this).invalidate();
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (((Control)this).getCreated()) {
            ((Control)this).invalidate();
        }
    }
    
    protected void onEnter(final Event event) {
        super.onEnter(event);
        ((Control)this).invalidate();
    }
    
    private void \u0256() {
        if (this.\u038f) {
            this.setChecked(true);
        }
    }
    
    public boolean getAutoCheck() {
        return this.\u038f;
    }
    
    public void setAutoCheck(final boolean \u03ce) {
        this.\u038f = \u03ce;
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        super.onPaint(paintEvent);
        final boolean enabled = ((Control)this).getEnabled();
        final Bitmap bitmap = this.getChecked() ? (enabled ? this.\u0396 : ColorRadioButton.\u0395) : ColorRadioButton.\u0393;
        bitmap.setTransparent(true);
        bitmap.setTransparentColor(new Color(255, 0, 255));
        final Graphics graphics = paintEvent.graphics;
        final Rectangle clientRect = ((Control)this).getClientRect();
        final boolean b = this.getTextAlign() == 1;
        final Point size = bitmap.getSize();
        final Rectangle rectangle = new Rectangle(clientRect);
        rectangle.width = size.x;
        rectangle.height = size.y;
        final Rectangle rectangle2 = rectangle;
        rectangle2.y += (clientRect.height - size.y) / 2;
        String text = ((Control)this).getText();
        if (text != null && text.length() == 0) {
            text = null;
        }
        Rectangle rectangle3 = null;
        if (text != null) {
            final Point textSize = graphics.getTextSize(text);
            rectangle3 = new Rectangle(clientRect.x, clientRect.y + (clientRect.height - size.y) / 2, textSize.x, textSize.y);
        }
        if (b) {
            graphics.drawImage((Image)bitmap, rectangle.x, rectangle.y);
            if (text != null) {
                final Rectangle rectangle4 = rectangle3;
                rectangle4.x += size.x + 4;
                UIUtil.drawString(graphics, text, rectangle3, 36, enabled);
            }
        }
        else {
            graphics.drawImage((Image)bitmap, rectangle.x = clientRect.x + clientRect.width - rectangle.width, rectangle.y);
            if (text != null) {
                final Rectangle rectangle5 = rectangle3;
                rectangle5.x += 2;
                UIUtil.drawString(graphics, text, rectangle3, 36, enabled);
            }
        }
        if (text != null && text.length() != 0 && ((Control)this).getFocused()) {
            rectangle3.inflate(2, 2);
            graphics.drawFocusRect(rectangle3);
        }
    }
    
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        ((Control)this).invalidate();
    }
    
    protected void onCheckedChanged(final Event event) {
        if (this.\u0391 != null) {
            this.\u0391.invoke((Object)this, event);
        }
    }
    
    public void setText(final String text) {
        super.setText(text);
        ((Control)this).invalidate();
    }
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        final int keyCode = keyEvent.getKeyCode();
        if (keyCode == 13 || keyCode == 32) {
            super.onKeyDown(keyEvent);
            ((Control)this).focus();
            ((Control)this).invalidate();
            this.\u0256();
            ((Control)this).onClick(Event.EMPTY);
        }
    }
}
