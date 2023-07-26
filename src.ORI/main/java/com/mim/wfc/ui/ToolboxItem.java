// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.core.Event;
import com.ms.lang.Delegate;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.ui.Image;
import com.ms.wfc.core.Component;

public class ToolboxItem extends Component
{
    private String \u0262;
    private Image \u0191;
    private Object \u028b;
    private Cursor \u028c;
    private Color \u0193;
    private boolean \u0194;
    private boolean \u028d;
    private boolean \u01d4;
    Rectangle \u028e;
    private EventHandler \u0284;
    private EventHandler \u0285;
    private EventHandler \u0286;
    protected IToolbox toolBox;
    
    public void setSelected(final boolean \u028d) {
        if (this.\u028d != \u028d && this.toolBox != null && this.toolBox.getCreated()) {
            this.toolBox.invalidateItem(this);
        }
        this.\u028d = \u028d;
    }
    
    public boolean getSelected() {
        return this.\u028d;
    }
    
    public void addOnItemClicked(final EventHandler eventHandler) {
        this.\u0284 = (EventHandler)Delegate.combine((Delegate)this.\u0284, (Delegate)eventHandler);
    }
    
    public void removeOnItemReleased(final EventHandler eventHandler) {
        this.\u0285 = (EventHandler)Delegate.remove((Delegate)this.\u0285, (Delegate)eventHandler);
    }
    
    public ToolboxItem() {
        this.\u0262 = null;
        this.\u0191 = null;
        this.\u0193 = new Color(192, 192, 192);
        this.\u0194 = true;
        this.\u028d = false;
        this.\u01d4 = true;
        this.\u028e = new Rectangle();
        this.toolBox = null;
    }
    
    public void addOnItemReleased(final EventHandler eventHandler) {
        this.\u0285 = (EventHandler)Delegate.combine((Delegate)this.\u0285, (Delegate)eventHandler);
    }
    
    public void setTransparentColor(final Color \u0260) {
        this.\u0193 = \u0260;
        this.\u0259();
    }
    
    public Color getTransparentColor() {
        return this.\u0193;
    }
    
    protected void onItemCurrent(final Event event) {
        if (this.\u0286 != null) {
            this.\u0286.invoke((Object)this, event);
        }
    }
    
    void \u028b(final IToolbox toolBox) {
        this.toolBox = toolBox;
    }
    
    public void setData(final Object \u028b) {
        this.\u028b = \u028b;
    }
    
    private void \u0259() {
        if (this.\u0191 != null && this.\u0191 instanceof Bitmap) {
            final Bitmap bitmap = (Bitmap)this.\u0191;
            bitmap.setTransparent(true);
            if (this.\u0194) {
                final Graphics graphics = bitmap.getGraphics();
                bitmap.setTransparentColor(graphics.getPixel(0, 0));
                graphics.dispose();
            }
            else {
                bitmap.setTransparentColor(this.\u0193);
            }
        }
        if (this.toolBox != null && this.toolBox.getCreated()) {
            this.toolBox.invalidateItem(this);
        }
    }
    
    public Object getData() {
        return this.\u028b;
    }
    
    protected void onItemClicked(final Event event) {
        if (this.\u0284 != null) {
            this.\u0284.invoke((Object)this, event);
        }
    }
    
    public void removeOnItemCurrent(final EventHandler eventHandler) {
        this.\u0286 = (EventHandler)Delegate.remove((Delegate)this.\u0286, (Delegate)eventHandler);
    }
    
    public void setImage(final Image \u0192) {
        this.\u0191 = \u0192;
        this.\u0259();
    }
    
    public Image getImage() {
        return this.\u0191;
    }
    
    public void setAutoTransparent(final boolean \u0263) {
        this.\u0194 = \u0263;
        this.\u0259();
    }
    
    public boolean getAutoTransparent() {
        return this.\u0194;
    }
    
    public void setEnabled(final boolean \u01d4) {
        if (this.\u01d4 != \u01d4 && this.toolBox != null && this.toolBox.getCreated()) {
            this.toolBox.invalidateItem(this);
        }
        this.\u01d4 = \u01d4;
    }
    
    public boolean getEnabled() {
        return this.\u01d4;
    }
    
    public void removeOnItemClicked(final EventHandler eventHandler) {
        this.\u0284 = (EventHandler)Delegate.remove((Delegate)this.\u0284, (Delegate)eventHandler);
    }
    
    protected void onItemReleased(final Event event) {
        if (this.\u0285 != null) {
            this.\u0285.invoke((Object)this, event);
        }
    }
    
    public void addOnItemCurrent(final EventHandler eventHandler) {
        this.\u0286 = (EventHandler)Delegate.combine((Delegate)this.\u0286, (Delegate)eventHandler);
    }
    
    public void setCursor(final Cursor \u028c) {
        this.\u028c = \u028c;
    }
    
    public Cursor getCursor() {
        return this.\u028c;
    }
    
    public void setText(final String \u0262) {
        this.\u0262 = \u0262;
        if (this.toolBox != null && this.toolBox.getCreated()) {
            this.toolBox.invalidateItem(this);
        }
    }
    
    public String getText() {
        return this.\u0262;
    }
}
