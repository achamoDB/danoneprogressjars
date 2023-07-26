// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.PaintEvent;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.wfc.ui.HorizontalAlignment;
import com.ms.wfc.core.Component;
import com.ms.wfc.core.Event;
import com.mim.wfc.license._cls753A;
import com.mim.wfc.shell.Shell;
import com.mim.wfc.mapi.MapiException;
import com.mim.wfc.mapi.Mapi;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.UserControl;

public class HyperLink extends UserControl
{
    private String \u03bd;
    private int \u038e;
    private static Color \u03be;
    private static Color \u03bf;
    private static Class \u01bd;
    
    public void navigate() {
        String s = this.\u03bd;
        if (s == null) {
            s = ((Control)this).getText();
        }
        if (s == null) {
            return;
        }
        if (navigate(s, (Control)this)) {
            ((Control)this).setForeColor(HyperLink.\u03be);
        }
    }
    
    public static boolean navigate(final String s, final Control control) {
        if (s == null) {
            return false;
        }
        if (s.indexOf(64) >= 0) {
            try {
                Mapi.sendMail(0, null, null, s, s, null, null, control, 9);
                return true;
            }
            catch (MapiException ex) {
                return false;
            }
        }
        return Shell.execute(control, "open", s, null, null, 1);
    }
    
    public HyperLink() {
        this.\u038e = 0;
        _cls753A._mth821F();
        ((Control)this).setSize(100, 20);
        ((Control)this).setStyle(2, true);
        ((Control)this).setForeColor(HyperLink.\u03bf);
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    protected void onLeave(final Event event) {
        super.onLeave(event);
        ((Control)this).invalidate();
    }
    
    public final int getTextAlign() {
        return this.\u038e;
    }
    
    public void setTextAlign(final int \u03cd) {
        if (!HorizontalAlignment.valid(\u03cd)) {
            throw new WFCInvalidEnumException("value", \u03cd, (HyperLink.\u01bd != null) ? HyperLink.\u01bd : (HyperLink.\u01bd = \u00c6("com.ms.wfc.ui.HorizontalAlignment")));
        }
        if (this.\u038e != \u03cd) {
            this.\u038e = \u03cd;
            ((Control)this).invalidate();
        }
    }
    
    public static void setVisitedColor(final Color \u03be) {
        HyperLink.\u03be = \u03be;
    }
    
    static {
        HyperLink.\u03be = new Color(128, 0, 128);
        HyperLink.\u03bf = new Color(0, 0, 255);
    }
    
    public static void setUnVisitedColor(final Color \u03bf) {
        HyperLink.\u03bf = \u03bf;
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public String getURL() {
        return this.\u03bd;
    }
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        ((Control)this).focus();
        ((Control)this).invalidate();
        this.navigate();
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
    
    private void \u03bd() {
        String s = this.\u03bd;
        if (s == null) {
            s = ((Control)this).getText();
        }
        if (s != null) {
            ((Control)this).setCursor((Cursor)((IResourceManager)new ResourceManager((IResourceLoader)this, "HyperLink")).getObject("cursorHand"));
            return;
        }
        ((Control)this).setCursor(Cursor.ARROW);
    }
    
    public void setURL(final String \u03bd) {
        this.\u03bd = \u03bd;
        this.\u03bd();
    }
    
    protected void onPaint(final PaintEvent paintEvent) {
        super.onPaint(paintEvent);
        final Graphics graphics = paintEvent.graphics;
        final Rectangle clientRect = ((Control)this).getClientRect();
        String text = ((Control)this).getText();
        if (text != null && text.length() == 0) {
            text = null;
        }
        if (text != null) {
            clientRect.inflate(-2, -2);
            UIUtil.drawStringEllipsis(graphics, text, clientRect, 2084, ((Control)this).getEnabled(), this.\u038e != 1);
        }
        if (text != null && ((Control)this).getFocused()) {
            clientRect.inflate(2, 2);
            graphics.drawFocusRect(clientRect);
        }
    }
    
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        ((Control)this).invalidate();
    }
    
    public void setText(final String text) {
        super.setText(text);
        this.\u03bd();
        ((Control)this).invalidate();
    }
}
