// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IResourceManager;
import com.ms.wfc.core.IComponentSite;
import com.ms.wfc.ui.Rectangle;
import com.ms.util.TimerListener;
import com.ms.wfc.core.IResourceLoader;
import com.ms.wfc.core.ResourceManager;
import com.ms.wfc.ui.MouseEvent;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Image;
import com.ms.wfc.ui.PaintEvent;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.PaintEventHandler;
import com.ms.wfc.ui.MouseEventHandler;
import com.ms.wfc.core.WFCInvalidEnumException;
import com.ms.lang.Delegate;
import com.ms.wfc.core.Event;
import com.ms.util.Timer;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.ui.Icon;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.ui.Control;
import com.ms.wfc.core.Component;

public class QuickScroll extends Component
{
    public static final int OFF = 0;
    public static final int MOVE = 1;
    public static final int ZOOM = 2;
    public static final int ROTATE = 3;
    private Control \u01d3;
    private boolean \u01d4;
    private int \u01d5;
    private int \u01d6;
    private int \u01d7;
    private int \u01d8;
    private int \u0197;
    private int \u01d9;
    private EventHandler \u01da;
    private EventHandler \u01db;
    private EventHandler \u01dc;
    private Point \u01dd;
    private Point \u01de;
    private int mode;
    private int \u01df;
    private Icon image;
    private Cursor \u01e0;
    private Timer \u01a1;
    private boolean \u01e1;
    private boolean \u01e2;
    private static Class \u01e3;
    private static Class \u01e4;
    
    public void setNeutralDistance(final int \u01da) {
        this.\u01d9 = \u01da;
    }
    
    protected void _mouseEnter(final Object o, final Event event) {
        this.setCursorType(true);
    }
    
    public int getNeutralDistance() {
        return this.\u01d9;
    }
    
    public final void removeOnQuickScrollBegin(final EventHandler eventHandler) {
        this.\u01db = (EventHandler)Delegate.remove((Delegate)this.\u01db, (Delegate)eventHandler);
    }
    
    public void onQuickScrollEnd(final Event event) {
        if (this.\u01dc != null) {
            this.\u01dc.invoke((Object)this, event);
        }
    }
    
    public void setZoomActivation(final int \u01d8) {
        if (!QuickScrollActivation.valid(\u01d8)) {
            throw new WFCInvalidEnumException("mode", \u01d8, (QuickScroll.\u01e3 != null) ? QuickScroll.\u01e3 : (QuickScroll.\u01e3 = \u00c6("com.mim.wfc.ui.QuickScrollActivation")));
        }
        this.\u01d7 = \u01d8;
    }
    
    public int getZoomActivation() {
        return this.\u01d7;
    }
    
    private void \u01d3(final int mode, final boolean \u01e1) {
        if (this.mode == mode) {
            this.setMode(0);
            this.\u01e1 = false;
            return;
        }
        this.setMode(mode);
        this.\u01e1 = \u01e1;
    }
    
    protected void _mouseLeave(final Object o, final Event event) {
        this.setCursorType(false);
    }
    
    private void \u01d4() {
        if (this.\u01e2) {
            return;
        }
        this.\u01e2 = true;
        this.onQuickScroll(Event.EMPTY);
        this.\u01e2 = false;
    }
    
    public final void removeOnQuickScroll(final EventHandler eventHandler) {
        this.\u01da = (EventHandler)Delegate.remove((Delegate)this.\u01da, (Delegate)eventHandler);
    }
    
    void \u0193() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        if (this.\u01de.x < -this.\u01d9 || this.\u01de.x > this.\u01d9 || this.\u01de.y < -this.\u01d9 || this.\u01de.y > this.\u01d9) {
            this.\u01d4();
        }
        if (this.\u01a1 != null) {
            this.\u01a1.start();
        }
    }
    
    public Point getOffset() {
        return this.\u01de;
    }
    
    public int getXOffset() {
        return this.\u01de.x;
    }
    
    public int getDistance() {
        return (int)(Math.sqrt(this.\u01de.x * this.\u01de.x + this.\u01de.y * this.\u01de.y) + 0.5);
    }
    
    public void setRotateActivation(final int \u01d8) {
        if (!QuickScrollActivation.valid(\u01d8)) {
            throw new WFCInvalidEnumException("mode", \u01d8, (QuickScroll.\u01e3 != null) ? QuickScroll.\u01e3 : (QuickScroll.\u01e3 = \u00c6("com.mim.wfc.ui.QuickScrollActivation")));
        }
        this.\u01d8 = \u01d8;
    }
    
    public int getRotateActivation() {
        return this.\u01d8;
    }
    
    public void setViewWindow(final Control \u01d4) {
        this.\u01d5();
        this.\u01d3 = \u01d4;
        if (this.\u01d3 != null) {
            this.\u01d3.addOnMouseDown(new MouseEventHandler((Object)this, "_mouseDown"));
            this.\u01d3.addOnMouseUp(new MouseEventHandler((Object)this, "_mouseUp"));
            this.\u01d3.addOnMouseMove(new MouseEventHandler((Object)this, "_mouseMove"));
            this.\u01d3.addOnMouseEnter(new EventHandler((Object)this, "_mouseEnter"));
            this.\u01d3.addOnMouseLeave(new EventHandler((Object)this, "_mouseLeave"));
            this.\u01d3.addOnPaint(new PaintEventHandler((Object)this, "_paint"));
        }
        this.\u01d5();
    }
    
    public Control getViewWindow() {
        return this.\u01d3;
    }
    
    public int getYOffset() {
        return this.\u01de.y;
    }
    
    public void onQuickScrollBegin(final Event event) {
        if (this.\u01db != null) {
            this.\u01db.invoke((Object)this, event);
        }
    }
    
    public QuickScroll() {
        this.\u01d3 = null;
        this.\u01d4 = true;
        this.\u01d5 = 2;
        this.\u01d6 = 1;
        this.\u01d7 = 1;
        this.\u01d8 = 1;
        this.\u0197 = 10;
        this.\u01d9 = 5;
        this.\u01dd = new Point(0, 0);
        this.\u01de = new Point(0, 0);
        this.mode = 0;
        this.\u01df = 0;
        this.image = null;
        this.\u01e0 = null;
        this.\u01a1 = null;
        this.\u01e1 = false;
        _cls753A._mth821F();
    }
    
    protected void _paint(final Object o, final PaintEvent paintEvent) {
        if (this.image != null && this.\u01d3 != null) {
            final Graphics graphics = paintEvent.graphics;
            if (this.\u01d3 instanceof ViewPanel) {
                ((ViewPanel)this.\u01d3).resetGraphics(graphics);
            }
            final Point pointToClient = this.\u01d3.pointToClient(this.\u01dd);
            graphics.drawImage((Image)this.image, pointToClient.x - 16, pointToClient.y - 16);
        }
    }
    
    public void toggleMode() {
        int mode = 0;
        Label_0076: {
            switch (this.mode) {
                case 0: {
                    if ((this.\u01d6 & 0x1) != 0x0) {
                        mode = 1;
                        break Label_0076;
                    }
                }
                case 1: {
                    if ((this.\u01d7 & 0x1) != 0x0) {
                        mode = 2;
                        break Label_0076;
                    }
                }
                case 2: {
                    if ((this.\u01d8 & 0x1) != 0x0) {
                        mode = 3;
                        break Label_0076;
                    }
                    break;
                }
            }
            mode = 0;
        }
        this.setMode(mode);
    }
    
    protected void _mouseUp(final Object o, final MouseEvent mouseEvent) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        if (this.\u01e1) {
            this.setMode(0);
            this.\u01e1 = false;
        }
    }
    
    public void setMode(final int mode) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        if (mode == this.mode) {
            return;
        }
        String s = null;
        switch (this.mode = mode) {
            case 1: {
                s = "imageMove";
                break;
            }
            case 2: {
                s = "imageZoom";
                break;
            }
            case 3: {
                s = "imageRotate";
                break;
            }
            default: {
                s = null;
                break;
            }
        }
        if (s != null) {
            this.image = (Icon)((IResourceManager)new ResourceManager((IResourceLoader)this, "QuickScroll")).getObject(s);
        }
        else {
            this.image = null;
        }
        this.\u01d5();
        final Point mousePosition = Control.getMousePosition();
        this.\u01dd.x = mousePosition.x;
        this.\u01dd.y = mousePosition.y;
        this.\u01d5();
        this.setCursorType(true);
        if (mode != 0 && mode != 3) {
            if (this.\u0197 != 0) {
                (this.\u01a1 = new Timer((TimerListener)new QuickScroll$TimerListener(this), (long)(1000 / this.\u0197), false)).start();
            }
        }
        else if (this.\u01a1 != null) {
            this.\u01a1.stop();
            this.\u01a1 = null;
        }
    }
    
    public int getMode() {
        return this.mode;
    }
    
    private void setCursorType(final boolean b) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        int \u01df = 0;
        String s = null;
        if (b) {
            switch (this.mode) {
                case 1: {
                    \u01df = 1;
                    s = "cursorMove";
                    break;
                }
                case 2: {
                    if (this.\u01de.x < -this.\u01d9) {
                        \u01df = 4;
                        s = "cursorZoomOut";
                        break;
                    }
                    if (this.\u01de.x > this.\u01d9) {
                        \u01df = 3;
                        s = "cursorZoomIn";
                        break;
                    }
                    \u01df = 2;
                    s = "cursorZoom";
                    break;
                }
                case 3: {
                    \u01df = 5;
                    s = "cursorRotate";
                    break;
                }
                default: {
                    \u01df = 0;
                    s = null;
                    break;
                }
            }
        }
        else {
            \u01df = 0;
            s = null;
        }
        if (\u01df != this.\u01df) {
            this.\u01df = \u01df;
            if (s != null) {
                final Cursor cursor = (Cursor)((IResourceManager)new ResourceManager((IResourceLoader)this, "QuickScroll")).getObject(s);
                if (this.\u01d3 != null) {
                    this.\u01d3.setCapture(true);
                    if (this.\u01e0 != null) {
                        this.\u01e0 = this.\u01d3.getCursor();
                    }
                    this.\u01d3.setCursor(cursor);
                }
            }
            else if (this.\u01d3 != null) {
                this.\u01d3.setCapture(false);
                this.\u01d3.setCursor(this.\u01e0);
                this.\u01e0 = null;
            }
        }
    }
    
    public void setRepeatRate(final int \u0268) {
        this.\u0197 = \u0268;
    }
    
    public Point getCenter() {
        return this.\u01dd;
    }
    
    public int getRepeatRate() {
        return this.\u0197;
    }
    
    public void onQuickScroll(final Event event) {
        if (this.\u01da != null) {
            this.\u01da.invoke((Object)this, event);
        }
    }
    
    public final void addOnQuickScrollBegin(final EventHandler eventHandler) {
        this.\u01db = (EventHandler)Delegate.combine((Delegate)this.\u01db, (Delegate)eventHandler);
    }
    
    public final void removeOnQuickScrollEnd(final EventHandler eventHandler) {
        this.\u01dc = (EventHandler)Delegate.remove((Delegate)this.\u01dc, (Delegate)eventHandler);
    }
    
    private void \u01d5() {
        if (this.\u01d3 != null) {
            final Point pointToClient = this.\u01d3.pointToClient(this.\u01dd);
            this.\u01d3.invalidate(new Rectangle(pointToClient.x - 16, pointToClient.y - 16, 32, 32));
        }
    }
    
    public void setToggleMode(final int \u01d6) {
        if (!QuickScrollToggleMode.valid(\u01d6)) {
            throw new WFCInvalidEnumException("mode", \u01d6, (QuickScroll.\u01e4 != null) ? QuickScroll.\u01e4 : (QuickScroll.\u01e4 = \u00c6("com.mim.wfc.ui.QuickScrollToggleMode")));
        }
        this.\u01d5 = \u01d6;
    }
    
    public int getToggleMode() {
        return this.\u01d5;
    }
    
    public int getAngle() {
        int n = (int)(Math.acos(this.\u01de.x / Math.sqrt(this.\u01de.x * this.\u01de.x + this.\u01de.y * this.\u01de.y)) * 180.0 / 3.141592653589793 + 0.5);
        if (this.\u01de.y > 0) {
            n += 2 * (180 - n);
        }
        return n;
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public final void addOnQuickScroll(final EventHandler eventHandler) {
        this.\u01da = (EventHandler)Delegate.combine((Delegate)this.\u01da, (Delegate)eventHandler);
    }
    
    protected void _mouseDown(final Object o, final MouseEvent mouseEvent) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        if (mouseEvent.button == 1048576) {
            if (this.\u01d5 == 1) {
                this.toggleMode();
                return;
            }
            if ((this.\u01d6 & 0x2) != 0x0) {
                this.\u01d3(1, false);
                return;
            }
            if ((this.\u01d7 & 0x2) != 0x0) {
                this.\u01d3(2, false);
                return;
            }
            if ((this.\u01d8 & 0x2) != 0x0) {
                this.\u01d3(3, false);
                return;
            }
            if ((this.\u01d6 & 0x10) != 0x0) {
                this.\u01d3(1, true);
                return;
            }
            if ((this.\u01d7 & 0x10) != 0x0) {
                this.\u01d3(2, true);
                return;
            }
            if ((this.\u01d8 & 0x10) != 0x0) {
                this.\u01d3(3, true);
            }
        }
        else if (mouseEvent.button == 2097152) {
            if (this.\u01d5 == 2) {
                this.toggleMode();
                return;
            }
            if ((this.\u01d6 & 0x4) != 0x0) {
                this.\u01d3(1, false);
                return;
            }
            if ((this.\u01d7 & 0x4) != 0x0) {
                this.\u01d3(2, false);
                return;
            }
            if ((this.\u01d8 & 0x4) != 0x0) {
                this.\u01d3(3, false);
                return;
            }
            if ((this.\u01d6 & 0x20) != 0x0) {
                this.\u01d3(1, true);
                return;
            }
            if ((this.\u01d7 & 0x20) != 0x0) {
                this.\u01d3(2, true);
                return;
            }
            if ((this.\u01d8 & 0x20) != 0x0) {
                this.\u01d3(3, true);
            }
        }
        else if (mouseEvent.button == 4194304) {
            if (this.\u01d5 == 3) {
                this.toggleMode();
                return;
            }
            if ((this.\u01d6 & 0x8) != 0x0) {
                this.\u01d3(1, false);
                return;
            }
            if ((this.\u01d7 & 0x8) != 0x0) {
                this.\u01d3(2, false);
                return;
            }
            if ((this.\u01d8 & 0x8) != 0x0) {
                this.\u01d3(3, false);
                return;
            }
            if ((this.\u01d6 & 0x40) != 0x0) {
                this.\u01d3(1, true);
                return;
            }
            if ((this.\u01d7 & 0x40) != 0x0) {
                this.\u01d3(2, true);
                return;
            }
            if ((this.\u01d8 & 0x40) != 0x0) {
                this.\u01d3(3, true);
            }
        }
    }
    
    public void dispose() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        super.dispose();
    }
    
    public void setMoveActivation(final int \u01d6) {
        if (!QuickScrollActivation.valid(\u01d6)) {
            throw new WFCInvalidEnumException("mode", \u01d6, (QuickScroll.\u01e3 != null) ? QuickScroll.\u01e3 : (QuickScroll.\u01e3 = \u00c6("com.mim.wfc.ui.QuickScrollActivation")));
        }
        this.\u01d6 = \u01d6;
    }
    
    public int getMoveActivation() {
        return this.\u01d6;
    }
    
    public void setComponentSite(final IComponentSite componentSite) {
        super.setComponentSite(componentSite);
        _cls753A._mth563B(this);
    }
    
    public void setEnabled(final boolean \u01d4) {
        this.\u01d4 = \u01d4;
    }
    
    public boolean getEnabled() {
        return this.\u01d4;
    }
    
    public final void addOnQuickScrollEnd(final EventHandler eventHandler) {
        this.\u01dc = (EventHandler)Delegate.combine((Delegate)this.\u01dc, (Delegate)eventHandler);
    }
    
    protected void _mouseMove(final Object o, final MouseEvent mouseEvent) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        if (this.mode != 0) {
            final Point mousePosition = Control.getMousePosition();
            this.\u01de.x = mousePosition.x - this.\u01dd.x;
            this.\u01de.y = mousePosition.y - this.\u01dd.y;
            if (this.mode == 2) {
                this.setCursorType(true);
            }
            if ((this.\u0197 == 0 || this.mode == 3) && (this.\u01de.x < -this.\u01d9 || this.\u01de.x > this.\u01d9 || this.\u01de.y < -this.\u01d9 || this.\u01de.y > this.\u01d9)) {
                this.\u01d4();
            }
        }
    }
}
