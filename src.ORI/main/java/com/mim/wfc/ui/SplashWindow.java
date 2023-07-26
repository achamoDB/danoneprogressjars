// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.io.WinIOException;
import com.ms.wfc.io.IDataStream;
import com.ms.wfc.io.File;
import com.ms.util.TimerListener;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Bitmap;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.MouseEventHandler;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.core.Component;
import com.ms.wfc.core.Event;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.KeyEvent;
import com.ms.wfc.ui.PictureBox;
import com.ms.wfc.core.Container;
import com.ms.util.Timer;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.Form;

public class SplashWindow extends Form
{
    private Image \u0191;
    private Color \u0193;
    private boolean \u0194;
    private boolean \u0259;
    private int \u01cf;
    private boolean \u025a;
    private Timer \u01a1;
    Container \u011d;
    PictureBox \u025b;
    
    protected void onKeyDown(final KeyEvent keyEvent) {
        super.onKeyDown(keyEvent);
        if (this.\u025a) {
            this.remove();
        }
    }
    
    protected void pictureBox_mouseDown(final Object o, final MouseEvent mouseEvent) {
        if (this.\u025a) {
            this.remove();
        }
    }
    
    public SplashWindow() {
        this.\u0191 = null;
        this.\u0193 = new Color(192, 192, 192);
        this.\u0194 = false;
        this.\u0259 = false;
        this.\u01cf = 3000;
        this.\u025a = true;
        this.\u01a1 = null;
        this.\u011d = new Container();
        this.\u025b = new PictureBox();
        _cls753A._mth821F();
        this.\u0118();
    }
    
    public void setTransparentColor(final Color \u0260) {
        this.\u0193 = \u0260;
        this.\u0259();
        this.\u025a();
    }
    
    public Color getTransparentColor() {
        return this.\u0193;
    }
    
    protected void onCreateHandle(final Event event) {
        super.onCreateHandle(event);
        _cls753A._mth563B((Component)this);
    }
    
    private void \u0118() {
        ((Control)this).setCursor(Cursor.APPSTARTING);
        ((Control)this).setText("SplashWindow");
        this.setAutoScaleBaseSize(new Point(5, 13));
        this.setBorderStyle(0);
        ((Control)this).setClientSize(new Point(400, 200));
        this.setShowInTaskbar(false);
        this.setStartPosition(1);
        this.setTopMost(true);
        ((Control)this.\u025b).setCursor(Cursor.APPSTARTING);
        ((Control)this.\u025b).setLocation(new Point(10, 10));
        ((Control)this.\u025b).setSize(new Point(100, 50));
        ((Control)this.\u025b).setTabIndex(0);
        ((Control)this.\u025b).setTabStop(false);
        ((Control)this.\u025b).setText("pictureBox");
        this.\u025b.setSizeMode(2);
        ((Control)this.\u025b).addOnMouseDown(new MouseEventHandler((Object)this, "pictureBox_mouseDown"));
        this.setNewControls(new Control[] { (Control)this.\u025b });
    }
    
    private void \u0259() {
        if (this.\u0191 != null && this.\u0191 instanceof Bitmap) {
            final Bitmap bitmap = (Bitmap)this.\u0191;
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
    
    public void setFrame3D(final boolean \u0259) {
        this.\u0259 = \u0259;
        this.\u025a();
    }
    
    public boolean getFrame3D() {
        return this.\u0259;
    }
    
    protected void onMouseDown(final MouseEvent mouseEvent) {
        if (this.\u025a) {
            this.remove();
        }
        super.onMouseDown(mouseEvent);
    }
    
    public void dispose() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
            this.\u01a1 = null;
        }
        super.dispose();
        this.\u011d.dispose();
    }
    
    protected void onResize(final Event event) {
        super.onResize(event);
        if (((Control)this).getCreated()) {
            this.\u025a();
        }
    }
    
    void \u0193() {
        this.remove();
    }
    
    private void \u025a() {
        ((Control)this).setSize(((Control)this.\u025b).getSize());
        ((Control)this.\u025b).setLocation(0, 0);
        ((Control)this).invalidate();
    }
    
    public void setImage(final Image \u0192) {
        this.\u0191 = \u0192;
        this.\u0259();
        this.\u025b.setImage(this.\u0191);
        this.\u025a();
    }
    
    public void display() {
        this.\u025a();
        super.show();
        if (this.\u01cf != 0) {
            (this.\u01a1 = new Timer((TimerListener)new SplashWindow$TimerListener(this), (long)this.\u01cf, false)).start();
        }
    }
    
    public Image getImage() {
        return this.\u0191;
    }
    
    public void setAutoTransparent(final boolean \u0263) {
        this.\u0194 = \u0263;
        this.\u0259();
        this.\u025a();
    }
    
    public boolean getAutoTransparent() {
        return this.\u0194;
    }
    
    public boolean getCheckUserAction() {
        return this.\u025a;
    }
    
    public void setCheckUserAction(final boolean \u025a) {
        this.\u025a = \u025a;
        this.\u025a();
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
        this.onClosed(Event.EMPTY);
        this.setVisible(false);
    }
    
    public boolean loadImageFile(final String s) {
        try {
            final Image loadImage = Image.loadImage((IDataStream)new File(s, 3, Integer.MIN_VALUE, 1));
            if (loadImage == null) {
                return false;
            }
            this.setImage(loadImage);
        }
        catch (WinIOException ex) {
            return false;
        }
        return true;
    }
}
