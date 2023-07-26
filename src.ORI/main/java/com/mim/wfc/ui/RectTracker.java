// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IComponentSite;
import com.ms.util.TimerListener;
import com.ms.wfc.ui.Graphics;
import com.ms.wfc.ui.Brush;
import com.ms.wfc.ui.Pen;
import com.ms.wfc.ui.RasterOp;
import com.ms.wfc.ui.PaintEvent;
import com.mim.wfc.license._cls753A;
import com.ms.wfc.ui.PaintEventHandler;
import com.ms.wfc.ui.MouseEventHandler;
import com.ms.wfc.core.CancelEvent;
import com.ms.win32.User32;
import com.ms.wfc.ui.MouseEvent;
import com.ms.lang.Delegate;
import com.ms.wfc.core.Event;
import com.ms.util.Timer;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.core.EventHandler;
import com.ms.wfc.core.CancelEventHandler;
import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Control;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Rectangle;
import com.ms.wfc.core.Component;

public class RectTracker extends Component
{
    public static final int OFF = 0;
    public static final int STATIC = 1;
    public static final int MOVE = 2;
    public static final int RESIZE = 3;
    public static final int RUBBERBAND = 4;
    public static final int HIT_NOTHING = -1;
    public static final int HIT_MIDDLE = 0;
    public static final int HIT_TOPLEFT = 1;
    public static final int HIT_TOPRIGHT = 2;
    public static final int HIT_BOTTOMRIGHT = 3;
    public static final int HIT_BOTTOMLEFT = 4;
    public static final int HIT_TOP = 5;
    public static final int HIT_RIGHT = 6;
    public static final int HIT_BOTTOM = 7;
    public static final int HIT_LEFT = 8;
    public static final int HANDLEMASK_NONE = 0;
    public static final int HANDLEMASK_TOPLEFT = 2;
    public static final int HANDLEMASK_TOPRIGHT = 4;
    public static final int HANDLEMASK_BOTTOMRIGHT = 8;
    public static final int HANDLEMASK_BOTTOMLEFT = 16;
    public static final int HANDLEMASK_TOP = 32;
    public static final int HANDLEMASK_RIGHT = 64;
    public static final int HANDLEMASK_BOTTOM = 128;
    public static final int HANDLEMASK_LEFT = 256;
    public static final int HANDLEMASK_ALL = 510;
    public static final int HANDLEMASK_CORNER = 30;
    public static final int HANDLEMASK_EDGE = 480;
    private Rectangle \u01e6;
    private Point \u01e7;
    private Point \u01e8;
    private Control \u01d3;
    private boolean \u01d4;
    private boolean \u01e9;
    private boolean \u01ea;
    private boolean \u01eb;
    private boolean \u01ec;
    private int \u01ed;
    private int \u01ee;
    private boolean \u01ef;
    private boolean \u01f0;
    private boolean \u01f1;
    private Color \u01f2;
    private int \u01f3;
    private CancelEventHandler \u01f4;
    private EventHandler \u01f5;
    private EventHandler \u01fa;
    private CancelEventHandler \u01fb;
    private EventHandler \u01fc;
    private EventHandler \u01fd;
    private CancelEventHandler \u01fe;
    private EventHandler \u01ff;
    private EventHandler \u0200;
    private OutOfBoundsEventHandler \u0201;
    private EventHandler \u0202;
    private int mode;
    private int \u01df;
    private Cursor cursor;
    private Cursor \u0203;
    private int \u0204;
    private Point \u0205;
    private Rectangle \u0206;
    private Rectangle \u0207;
    private boolean \u0208;
    private boolean \u0209;
    private boolean \u020a;
    private Timer \u01a1;
    
    protected void _mouseEnter(final Object o, final Event event) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        this.setCursorType(true);
    }
    
    protected void onOutOfBounds(final OutOfBoundsEvent outOfBoundsEvent) {
        if (this.\u0201 != null) {
            this.\u0201.invoke(this, outOfBoundsEvent);
        }
    }
    
    public void scrollIntoView() {
        if (this.\u01d3 instanceof ViewPanel && this.\u01e6 != null) {
            ((ViewPanel)this.\u01d3).scrollIntoView(this.\u01e6);
        }
    }
    
    public void onResized(final Event event) {
        if (this.\u01fd != null) {
            this.\u01fd.invoke((Object)this, event);
        }
    }
    
    public void cancelAction() {
        if (this.mode == 0 || this.mode == 1 || this.\u01d3 == null) {
            return;
        }
        this.\u01d5();
        this.\u01e6 = this.\u0206;
        this.mode = 1;
        this.\u01d5();
        this.onCancel(Event.EMPTY);
        if (this.mode != 1) {
            this.mode = 0;
        }
        this.\u0205 = null;
        this.\u0204 = 0;
        final Rectangle rectangle = null;
        this.\u0207 = rectangle;
        this.\u0206 = rectangle;
        this.\u01d3.setCapture(false);
        this.setCursorType(true);
    }
    
    public Rectangle applyOrigToRect(final Rectangle rectangle) {
        return this.\u01e6(rectangle, this.\u0206);
    }
    
    public void clearSelection() {
        this.\u01d5();
        this.\u01e6 = null;
        this.mode = 0;
        this.setCursorType(true);
    }
    
    public void setHandleMask(final int \u01ef) {
        this.\u01d5();
        this.\u01ee = \u01ef;
        this.\u01d5();
    }
    
    public int getHandleMask() {
        return this.\u01ee;
    }
    
    public void addOnStartTrackRubberBand(final CancelEventHandler cancelEventHandler) {
        this.\u01fe = (CancelEventHandler)Delegate.combine((Delegate)this.\u01fe, (Delegate)cancelEventHandler);
    }
    
    public final void removeOnTrackedRubberBand(final EventHandler eventHandler) {
        this.\u0200 = (EventHandler)Delegate.remove((Delegate)this.\u0200, (Delegate)eventHandler);
    }
    
    public void setTrackerColor(final Color \u01f3) {
        this.\u01f2 = \u01f3;
        this.\u01d5();
    }
    
    public Color getTrackerColor() {
        return this.\u01f2;
    }
    
    public void setMinSize(final Point \u01e7) {
        if (\u01e7 == null) {
            this.\u01e7 = new Point(4, 4);
            return;
        }
        this.\u01e7 = \u01e7;
    }
    
    void \u0193() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        final Point \u01e9 = this.\u01e8();
        final Rectangle clientRect = this.\u01d3.getClientRect();
        final Rectangle \u01e92 = this.\u01e9();
        final Point point = new Point();
        if (\u01e9.x < clientRect.x) {
            point.x = \u01e9.x - clientRect.x;
        }
        else if (\u01e9.x > clientRect.x + clientRect.width) {
            point.x = \u01e9.x - (clientRect.x + clientRect.width);
        }
        if (\u01e9.y < clientRect.y) {
            point.y = \u01e9.y - clientRect.y;
        }
        else if (\u01e9.y > clientRect.y + clientRect.height) {
            point.y = \u01e9.y - (clientRect.y + clientRect.height);
        }
        this.onOutOfBounds(new OutOfBoundsEvent(this, point));
        if (this.mode == 4) {
            final Rectangle \u01e93 = this.\u01e9();
            if (\u01e93 != null) {
                final Point \u0205 = this.\u0205;
                \u0205.x += \u01e93.x - \u01e92.x;
                final Point \u02052 = this.\u0205;
                \u02052.y += \u01e93.y - \u01e92.y;
            }
        }
        this.track();
        if (this.\u01a1 != null) {
            this.\u01a1.start();
        }
    }
    
    public Point getMinSize() {
        return this.\u01e7;
    }
    
    public void addOnStartResize(final CancelEventHandler cancelEventHandler) {
        this.\u01fb = (CancelEventHandler)Delegate.combine((Delegate)this.\u01fb, (Delegate)cancelEventHandler);
    }
    
    public void onMoving(final Event event) {
        if (this.\u01f5 != null) {
            this.\u01f5.invoke((Object)this, event);
        }
    }
    
    public final void removeOnCancel(final EventHandler eventHandler) {
        this.\u0202 = (EventHandler)Delegate.remove((Delegate)this.\u0202, (Delegate)eventHandler);
    }
    
    public void addOnStartMove(final CancelEventHandler cancelEventHandler) {
        this.\u01f4 = (CancelEventHandler)Delegate.combine((Delegate)this.\u01f4, (Delegate)cancelEventHandler);
    }
    
    public void onMoved(final Event event) {
        if (this.\u01fa != null) {
            this.\u01fa.invoke((Object)this, event);
        }
    }
    
    public final void addOnCancel(final EventHandler eventHandler) {
        this.\u0202 = (EventHandler)Delegate.combine((Delegate)this.\u0202, (Delegate)eventHandler);
    }
    
    public final void removeOnMoving(final EventHandler eventHandler) {
        this.\u01f5 = (EventHandler)Delegate.remove((Delegate)this.\u01f5, (Delegate)eventHandler);
    }
    
    public void setOutOfBoundsRepeatRate(final int \u01f3) {
        this.\u01f3 = \u01f3;
    }
    
    public void setResizeInside(final boolean \u01f3) {
        this.\u01d5();
        this.\u01f1 = \u01f3;
        this.\u01d5();
    }
    
    public boolean getResizeInside() {
        return this.\u01f1;
    }
    
    protected void _mouseUp(final Object o, final MouseEvent mouseEvent) {
        this.endAction();
    }
    
    public int getOutOfBoundsRepeatRate() {
        return this.\u01f3;
    }
    
    public final void removeOnMoved(final EventHandler eventHandler) {
        this.\u01fa = (EventHandler)Delegate.remove((Delegate)this.\u01fa, (Delegate)eventHandler);
    }
    
    public void setCursorType(final boolean b) {
        if (this.\u01d3 != null) {
            Cursor cursor = null;
            if (b) {
                switch (this.hitTest(null)) {
                    case 0: {
                        cursor = Cursor.SIZEALL;
                        break;
                    }
                    case 1:
                    case 3: {
                        cursor = Cursor.SIZENWSE;
                        break;
                    }
                    case 2:
                    case 4: {
                        cursor = Cursor.SIZENESW;
                        break;
                    }
                    case 5:
                    case 7: {
                        cursor = Cursor.SIZENS;
                        break;
                    }
                    case 6:
                    case 8: {
                        cursor = Cursor.SIZEWE;
                        break;
                    }
                }
            }
            if (cursor == null) {
                cursor = this.\u0203;
            }
            this.\u01d3.setCursor(cursor);
            cursor.setCurrent();
        }
    }
    
    public void onResizing(final Event event) {
        if (this.\u01fc != null) {
            this.\u01fc.invoke((Object)this, event);
        }
    }
    
    public void removeOnOutOfBounds(final OutOfBoundsEventHandler outOfBoundsEventHandler) {
        this.\u0201 = (OutOfBoundsEventHandler)Delegate.remove((Delegate)this.\u0201, (Delegate)outOfBoundsEventHandler);
    }
    
    public int hitTest(Point \u01e9) {
        if (\u01e9 == null) {
            \u01e9 = this.\u01e8();
        }
        final Rectangle trueRect = this.getTrueRect();
        if (trueRect == null || !trueRect.contains(\u01e9)) {
            return -1;
        }
        if (this.\u01ea) {
            int n = 1;
            do {
                if ((this.\u01ee & 1 << n) != 0x0) {
                    this.\u01e7(n, trueRect);
                    if (trueRect.contains(\u01e9)) {
                        return n;
                    }
                    continue;
                }
            } while (++n <= 8);
        }
        final Rectangle \u01e92 = this.\u01e9();
        \u01e92.inflate(1, 1);
        if (\u01e92.contains(\u01e9)) {
            return 0;
        }
        return -1;
    }
    
    public boolean wasCtrlPressed() {
        return this.\u0209;
    }
    
    public final void removeOnResized(final EventHandler eventHandler) {
        this.\u01fd = (EventHandler)Delegate.remove((Delegate)this.\u01fd, (Delegate)eventHandler);
    }
    
    private void \u01d5() {
        final Rectangle trueRect;
        if (this.\u01d3 != null && (trueRect = this.getTrueRect()) != null) {
            this.\u01d3.invalidate(trueRect);
        }
    }
    
    protected void _mouseDown(final Object o, final MouseEvent mouseEvent) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        this.\u0208 = ((User32.GetKeyState(16) & 0x80) != 0x0);
        this.\u0209 = ((User32.GetKeyState(17) & 0x80) != 0x0);
        if (mouseEvent.button == 1048576) {
            final int hitTest = this.hitTest(null);
            if (hitTest == -1) {
                if (this.\u01eb) {
                    this.startRubberBand();
                }
            }
            else if (hitTest == 0) {
                if (this.\u01e9) {
                    this.startMove();
                }
            }
            else if (this.\u01ea) {
                this.startResize(hitTest);
            }
        }
        else if (mouseEvent.button == 2097152) {
            this.cancelAction();
        }
    }
    
    public void dispose() {
        if (this.\u01a1 != null) {
            this.\u01a1.stop();
        }
        super.dispose();
    }
    
    public Rectangle getTrueRect() {
        if (this.\u01e6 == null) {
            return null;
        }
        final Rectangle \u01e9 = this.\u01e9();
        \u01e9.inflate(1, 1);
        if (!this.\u01f1) {
            \u01e9.inflate(this.\u01ed, this.\u01ed);
        }
        return \u01e9;
    }
    
    public Rectangle applyLastToRect(final Rectangle rectangle) {
        return this.\u01e6(rectangle, this.\u0207);
    }
    
    private Rectangle \u01e6(final Rectangle rectangle, final Rectangle rectangle2) {
        final Rectangle rectangle3 = new Rectangle(rectangle);
        if (this.mode == 2) {
            final Rectangle rectangle4 = rectangle3;
            rectangle4.x += this.\u01e6.x - rectangle2.x;
            final Rectangle rectangle5 = rectangle3;
            rectangle5.y += this.\u01e6.y - rectangle2.y;
        }
        else {
            if (this.mode != 3) {
                return null;
            }
            if (rectangle2.width == 0 || rectangle2.height == 0) {
                return null;
            }
            final double n = this.\u01e6.width / (double)rectangle2.width;
            final double n2 = this.\u01e6.height / (double)rectangle2.height;
            switch (this.\u0204) {
                case 1:
                case 4:
                case 8: {
                    rectangle3.width = (int)(rectangle3.width * n + 0.499999);
                    rectangle3.x = this.\u01e6.x + (int)((rectangle3.x - rectangle2.x) * n + 0.499999);
                    break;
                }
                case 2:
                case 3:
                case 6: {
                    rectangle3.width = (int)(rectangle3.width * n + 0.499999);
                    rectangle3.x = this.\u01e6.x + (int)((rectangle3.x - rectangle2.x) * n + 0.499999);
                    break;
                }
            }
            switch (this.\u0204) {
                case 1:
                case 2:
                case 5: {
                    rectangle3.height = (int)(rectangle3.height * n2 + 0.499999);
                    rectangle3.y = this.\u01e6.y + (int)((rectangle3.y - rectangle2.y) * n2 + 0.499999);
                    break;
                }
                case 3:
                case 4:
                case 7: {
                    rectangle3.height = (int)(rectangle3.height * n2 + 0.499999);
                    rectangle3.y = this.\u01e6.y + (int)((rectangle3.y - rectangle2.y) * n2 + 0.499999);
                    break;
                }
            }
        }
        return rectangle3;
    }
    
    public void addSelectionRect(final Rectangle rectangle) {
        this.setSelectionRect((this.\u01e6 == null) ? rectangle : Rectangle.union(this.\u01e6, rectangle));
    }
    
    private void \u01e7(final int n, final Rectangle rectangle) {
        Rectangle docToView = new Rectangle(this.\u01e6);
        if (this.\u01d3 instanceof ViewPanel) {
            docToView = ((ViewPanel)this.\u01d3).docToView(docToView);
        }
        if (this.\u01f1) {
            docToView.inflate(1, 1);
        }
        switch (n) {
            case 1:
            case 4:
            case 8: {
                rectangle.x = docToView.x;
                if (!this.\u01f1) {
                    rectangle.x -= this.\u01ed;
                    break;
                }
                break;
            }
            case 2:
            case 3:
            case 6: {
                rectangle.x = docToView.x + docToView.width;
                if (this.\u01f1) {
                    rectangle.x -= this.\u01ed;
                    break;
                }
                break;
            }
            default: {
                rectangle.x = docToView.x + (docToView.width - this.\u01ed) / 2;
                break;
            }
        }
        switch (n) {
            case 1:
            case 2:
            case 5: {
                rectangle.y = docToView.y;
                if (!this.\u01f1) {
                    rectangle.y -= this.\u01ed;
                    break;
                }
                break;
            }
            case 3:
            case 4:
            case 7: {
                rectangle.y = docToView.y + docToView.height;
                if (this.\u01f1) {
                    rectangle.y -= this.\u01ed;
                    break;
                }
                break;
            }
            default: {
                rectangle.y = docToView.y + (docToView.height - this.\u01ed) / 2;
                break;
            }
        }
        rectangle.width = this.\u01ed;
        rectangle.height = this.\u01ed;
    }
    
    public void onTrackingRubberBand(final Event event) {
        if (this.\u01ff != null) {
            this.\u01ff.invoke((Object)this, event);
        }
    }
    
    public final void addOnTrackedRubberBand(final EventHandler eventHandler) {
        this.\u0200 = (EventHandler)Delegate.combine((Delegate)this.\u0200, (Delegate)eventHandler);
    }
    
    public void addOnOutOfBounds(final OutOfBoundsEventHandler outOfBoundsEventHandler) {
        this.\u0201 = (OutOfBoundsEventHandler)Delegate.combine((Delegate)this.\u0201, (Delegate)outOfBoundsEventHandler);
    }
    
    public Rectangle getOriginalRect() {
        return new Rectangle(this.\u0206);
    }
    
    public Rectangle getLastRect() {
        return new Rectangle(this.\u0207);
    }
    
    protected void _mouseMove(final Object o, final MouseEvent mouseEvent) {
        if (this.mode == 0 || this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        if (this.mode == 1) {
            this.setCursorType(true);
            return;
        }
        this.track();
    }
    
    public void setEnableMove(final boolean \u01e9) {
        this.\u01e9 = \u01e9;
    }
    
    public boolean getEnableMove() {
        return this.\u01e9;
    }
    
    public void startResize(final int \u0205) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        final CancelEvent cancelEvent = new CancelEvent();
        this.onStartResize(cancelEvent);
        if (cancelEvent.cancel) {
            return;
        }
        this.mode = 3;
        this.\u0204 = \u0205;
        this.\u0206 = new Rectangle(this.\u01e6);
        this.\u0207 = new Rectangle(this.\u01e6);
        final Rectangle rectangle = new Rectangle();
        this.\u01e7(\u0205, rectangle);
        this.\u0205 = this.\u01e8();
        final Point \u02052 = this.\u0205;
        \u02052.x -= rectangle.x;
        final Point \u02053 = this.\u0205;
        \u02053.y -= rectangle.y;
        if (!this.\u01f1) {
            this.\u0205.x = -this.\u0205.x;
            this.\u0205.y = -this.\u0205.y;
        }
        this.setCursorType(true);
        this.\u01d3.setCapture(true);
    }
    
    public void setMaxSize(final Point \u01e9) {
        this.\u01e8 = \u01e9;
    }
    
    public Point getMaxSize() {
        return this.\u01e8;
    }
    
    public void setEnableResize(final boolean \u01eb) {
        this.\u01ea = \u01eb;
    }
    
    public boolean getEnableResize() {
        return this.\u01ea;
    }
    
    public void startMove() {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        final CancelEvent cancelEvent = new CancelEvent();
        this.onStartMove(cancelEvent);
        if (cancelEvent.cancel) {
            return;
        }
        this.mode = 2;
        this.\u0206 = new Rectangle(this.\u01e6);
        this.\u0207 = new Rectangle(this.\u01e6);
        final Rectangle \u01e9 = this.\u01e9();
        this.\u0205 = this.\u01e8();
        final Point \u0205 = this.\u0205;
        \u0205.x -= \u01e9.x;
        final Point \u02052 = this.\u0205;
        \u02052.y -= \u01e9.y;
        this.\u01d5();
        this.setCursorType(true);
        this.\u01d3.setCapture(true);
    }
    
    protected void onStartTrackRubberBand(final CancelEvent cancelEvent) {
        if (this.\u01fe != null) {
            this.\u01fe.invoke((Object)this, cancelEvent);
        }
    }
    
    public final void removeOnTrackingRubberBand(final EventHandler eventHandler) {
        this.\u01ff = (EventHandler)Delegate.remove((Delegate)this.\u01ff, (Delegate)eventHandler);
    }
    
    Point \u01e8() {
        return this.screenToClient(Control.getMousePosition());
    }
    
    public final void addOnMoving(final EventHandler eventHandler) {
        this.\u01f5 = (EventHandler)Delegate.combine((Delegate)this.\u01f5, (Delegate)eventHandler);
    }
    
    protected void onStartMove(final CancelEvent cancelEvent) {
        if (this.\u01f4 != null) {
            this.\u01f4.invoke((Object)this, cancelEvent);
        }
    }
    
    public final void removeOnResizing(final EventHandler eventHandler) {
        this.\u01fc = (EventHandler)Delegate.remove((Delegate)this.\u01fc, (Delegate)eventHandler);
    }
    
    public void setDottedLine(final boolean \u01ef) {
        this.\u01ef = \u01ef;
        this.\u01d5();
    }
    
    public void setResizeCentered(final boolean \u01ed) {
        this.\u01ec = \u01ed;
    }
    
    public boolean getResizeCentered() {
        return this.\u01ec;
    }
    
    public boolean getDottedLine() {
        return this.\u01ef;
    }
    
    public final void addOnMoved(final EventHandler eventHandler) {
        this.\u01fa = (EventHandler)Delegate.combine((Delegate)this.\u01fa, (Delegate)eventHandler);
    }
    
    protected void _mouseLeave(final Object o, final Event event) {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        this.setCursorType(false);
    }
    
    public boolean wasShiftPressed() {
        return this.\u0208;
    }
    
    public final void addOnResized(final EventHandler eventHandler) {
        this.\u01fd = (EventHandler)Delegate.combine((Delegate)this.\u01fd, (Delegate)eventHandler);
    }
    
    public void endAction() {
        if (this.mode == 0 || this.mode == 1 || this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        switch (this.mode) {
            case 2: {
                if (!this.\u0206.equals((Object)this.\u01e6)) {
                    this.onMoved(Event.EMPTY);
                    break;
                }
                break;
            }
            case 3: {
                if (!this.\u0206.equals((Object)this.\u01e6)) {
                    this.onResized(Event.EMPTY);
                    break;
                }
                break;
            }
            case 4: {
                if (this.\u01e6 == null) {
                    Point viewToDoc = new Point(this.\u0205.x, this.\u0205.y);
                    if (this.\u01d3 instanceof ViewPanel) {
                        viewToDoc = ((ViewPanel)this.\u01d3).viewToDoc(viewToDoc);
                    }
                    this.\u01e6 = new Rectangle(viewToDoc.x, viewToDoc.y, 1, 1);
                }
                this.onTrackedRubberBand(Event.EMPTY);
                break;
            }
        }
        this.\u0205 = null;
        this.\u0204 = 0;
        final Rectangle rectangle = null;
        this.\u0207 = rectangle;
        this.\u0206 = rectangle;
        this.\u01d3.setCapture(false);
        if (this.mode == 2 || this.mode == 3) {
            this.mode = 1;
            this.\u01d5();
        }
        else if (this.mode == 4) {
            this.mode = 0;
            this.\u01d5();
        }
        this.setCursorType(true);
    }
    
    private Rectangle \u01e9() {
        if (this.\u01e6 == null) {
            return null;
        }
        Rectangle docToView = new Rectangle(this.\u01e6);
        if (this.\u01d3 instanceof ViewPanel) {
            docToView = ((ViewPanel)this.\u01d3).docToView(docToView);
        }
        return docToView;
    }
    
    public void setSelectionRect(final Rectangle \u01e7) {
        this.\u01d5();
        this.\u01e6 = \u01e7;
        this.mode = 1;
        this.\u01d5();
        this.setCursorType(true);
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
            this.\u0203 = this.\u01d3.getCursor();
        }
        else {
            this.\u0203 = Cursor.DEFAULT;
        }
        this.\u01d5();
    }
    
    public Control getViewWindow() {
        return this.\u01d3;
    }
    
    public Rectangle getSelectionRect() {
        if (this.\u01e6 != null) {
            return new Rectangle(this.\u01e6);
        }
        return null;
    }
    
    public RectTracker() {
        this.\u01e6 = null;
        this.\u01e7 = new Point(4, 4);
        this.\u01e8 = null;
        this.\u01d3 = null;
        this.\u01d4 = true;
        this.\u01e9 = true;
        this.\u01ea = true;
        this.\u01eb = true;
        this.\u01ed = 10;
        this.\u01ee = 510;
        this.\u01ef = true;
        this.\u01f0 = false;
        this.\u01f1 = true;
        this.\u01f2 = null;
        this.\u01f3 = 0;
        this.mode = 0;
        this.\u01df = 0;
        this.cursor = null;
        this.\u0203 = null;
        this.\u0204 = 0;
        this.\u0205 = null;
        this.\u0206 = null;
        this.\u0207 = null;
        this.\u01a1 = null;
        _cls753A._mth821F();
    }
    
    protected void _paint(final Object o, final PaintEvent paintEvent) {
        if (this.mode == 0 || this.\u01e6 == null || this.\u01d3 == null) {
            return;
        }
        final Graphics graphics = paintEvent.graphics;
        final Pen pen = graphics.getPen();
        final Brush brush = graphics.getBrush();
        final boolean opaque = graphics.getOpaque();
        graphics.setOpaque(false);
        RasterOp invert;
        Color color;
        if (this.\u01f2 == null) {
            invert = RasterOp.TARGET.invert();
            color = Color.WHITE;
        }
        else {
            invert = null;
            color = this.\u01f2;
        }
        Pen pen2;
        if (this.\u01ef) {
            pen2 = new Pen(color, 2);
        }
        else {
            pen2 = new Pen(color);
        }
        Rectangle docToView = new Rectangle(this.\u01e6);
        if (this.\u01d3 instanceof ViewPanel) {
            docToView = ((ViewPanel)this.\u01d3).docToView(docToView);
        }
        docToView.inflate(1, 1);
        graphics.setPen(pen2);
        if (this.\u01f0) {
            if (this.\u01f2 == null) {
                graphics.setBrush(new Brush(color, 4));
            }
            else {
                graphics.setBrush(new Brush(color, 4));
                graphics.drawRect(docToView, RasterOp.TARGET.invert());
                graphics.setBrush((Brush)null);
            }
            graphics.drawRect(docToView, invert);
        }
        else {
            graphics.setBrush((Brush)null);
            graphics.drawRect(docToView, invert);
        }
        if (this.mode != 4) {
            final Brush brush2 = new Brush(color);
            if (this.\u01ef) {
                graphics.setPen(new Pen(color));
            }
            graphics.setBrush(brush2);
            final Rectangle rectangle = new Rectangle();
            int n = 1;
            do {
                if ((this.\u01ee & 1 << n) != 0x0) {
                    this.\u01e7(n, rectangle);
                    graphics.drawRect(rectangle, invert);
                }
            } while (++n <= 8);
        }
        graphics.setOpaque(opaque);
        graphics.setPen(pen);
        graphics.setBrush(brush);
    }
    
    public void startRubberBand() {
        if (this.\u01d3 == null || !this.\u01d4) {
            return;
        }
        final CancelEvent cancelEvent = new CancelEvent();
        this.onStartTrackRubberBand(cancelEvent);
        if (cancelEvent.cancel) {
            return;
        }
        this.clearSelection();
        this.mode = 4;
        this.\u0205 = this.\u01e8();
        this.setCursorType(true);
        this.\u01d3.setCapture(true);
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public Point screenToClient(final Point point) {
        if (this.\u01d3 == null) {
            return null;
        }
        return this.\u01d3.pointToClient(point);
    }
    
    public void setEnableRubberBand(final boolean \u01eb) {
        this.\u01eb = \u01eb;
    }
    
    public boolean getEnableRubberBand() {
        return this.\u01eb;
    }
    
    public void removeOnStartTrackRubberBand(final CancelEventHandler cancelEventHandler) {
        this.\u01fe = (CancelEventHandler)Delegate.remove((Delegate)this.\u01fe, (Delegate)cancelEventHandler);
    }
    
    public final void addOnTrackingRubberBand(final EventHandler eventHandler) {
        this.\u01ff = (EventHandler)Delegate.combine((Delegate)this.\u01ff, (Delegate)eventHandler);
    }
    
    public void onTrackedRubberBand(final Event event) {
        if (this.\u0200 != null) {
            this.\u0200.invoke((Object)this, event);
        }
    }
    
    protected void onStartResize(final CancelEvent cancelEvent) {
        if (this.\u01fb != null) {
            this.\u01fb.invoke((Object)this, cancelEvent);
        }
    }
    
    public final void addOnResizing(final EventHandler eventHandler) {
        this.\u01fc = (EventHandler)Delegate.combine((Delegate)this.\u01fc, (Delegate)eventHandler);
    }
    
    Point \u01ea() {
        Point point = this.\u01e8();
        if (this.\u01d3 instanceof ViewPanel) {
            point = ((ViewPanel)this.\u01d3).viewToDoc(point);
        }
        return point;
    }
    
    public void removeOnStartMove(final CancelEventHandler cancelEventHandler) {
        this.\u01f4 = (CancelEventHandler)Delegate.remove((Delegate)this.\u01f4, (Delegate)cancelEventHandler);
    }
    
    public void track() {
        if (this.\u020a) {
            return;
        }
        this.\u020a = true;
        final Rectangle clientRect = this.\u01d3.getClientRect();
        final Point \u01e9 = this.\u01e8();
        if (\u01e9.x < clientRect.x || \u01e9.y < clientRect.y || \u01e9.x > clientRect.x + clientRect.width || \u01e9.y > clientRect.y + clientRect.height) {
            if (this.\u01a1 == null && this.\u01f3 != 0) {
                (this.\u01a1 = new Timer((TimerListener)new RectTracker$TimerListener(this), (long)(1000 / this.\u01f3), false)).start();
                this.\u020a = false;
                this.\u0193();
                return;
            }
        }
        else if (this.\u01a1 != null) {
            this.\u01a1.stop();
            this.\u01a1 = null;
        }
        Rectangle rectangle;
        Point viewToDoc;
        if (this.\u01d3 instanceof ViewPanel) {
            final ViewPanel viewPanel = (ViewPanel)this.\u01d3;
            final Point docSize = viewPanel.getDocSize();
            rectangle = new Rectangle(0, 0, docSize.x, docSize.y);
            viewToDoc = viewPanel.viewToDoc(\u01e9);
        }
        else {
            rectangle = clientRect;
            viewToDoc = \u01e9;
        }
        if (!this.\u01d4) {
            this.\u020a = false;
            return;
        }
        Rectangle viewToDoc2 = new Rectangle();
        if (this.mode == 4) {
            int x;
            int width;
            if (\u01e9.x < this.\u0205.x) {
                x = \u01e9.x;
                width = this.\u0205.x - \u01e9.x;
            }
            else {
                x = this.\u0205.x;
                width = \u01e9.x - this.\u0205.x;
            }
            int y;
            int height;
            if (\u01e9.y < this.\u0205.y) {
                y = \u01e9.y;
                height = this.\u0205.y - \u01e9.y;
            }
            else {
                y = this.\u0205.y;
                height = \u01e9.y - this.\u0205.y;
            }
            viewToDoc2.x = x;
            viewToDoc2.y = y;
            viewToDoc2.width = width;
            viewToDoc2.height = height;
            if (this.\u01d3 instanceof ViewPanel) {
                viewToDoc2 = ((ViewPanel)this.\u01d3).viewToDoc(viewToDoc2);
            }
        }
        else if (this.mode == 2) {
            Point point = this.\u0205;
            if (this.\u01d3 instanceof ViewPanel) {
                point = ((ViewPanel)this.\u01d3).\u0293(this.\u0205);
            }
            viewToDoc2.x = viewToDoc.x - point.x;
            viewToDoc2.y = viewToDoc.y - point.y;
            viewToDoc2.width = this.\u01e6.width;
            viewToDoc2.height = this.\u01e6.height;
            if (viewToDoc2.x + viewToDoc2.width > rectangle.x + rectangle.width) {
                viewToDoc2.x = rectangle.x + rectangle.width - viewToDoc2.width;
            }
            if (viewToDoc2.y + viewToDoc2.height > rectangle.y + rectangle.height) {
                viewToDoc2.y = rectangle.y + rectangle.height - viewToDoc2.height;
            }
            if (viewToDoc2.x < rectangle.x) {
                viewToDoc2.x = rectangle.x;
            }
            if (viewToDoc2.y < rectangle.y) {
                viewToDoc2.y = rectangle.y;
            }
        }
        else if (this.mode == 3) {
            Point point2 = this.\u0205;
            if (this.\u01d3 instanceof ViewPanel) {
                point2 = ((ViewPanel)this.\u01d3).\u0293(this.\u0205);
            }
            viewToDoc2.x = this.\u01e6.x;
            viewToDoc2.y = this.\u01e6.y;
            viewToDoc2.width = this.\u01e6.width;
            viewToDoc2.height = this.\u01e6.height;
            if (viewToDoc.x > rectangle.x + rectangle.width) {
                viewToDoc.x = rectangle.x + rectangle.width - viewToDoc2.width;
            }
            if (viewToDoc.y > rectangle.x + rectangle.height) {
                viewToDoc.y = rectangle.y + rectangle.height - viewToDoc2.height;
            }
            if (viewToDoc.x < rectangle.x) {
                viewToDoc.x = rectangle.x;
            }
            if (viewToDoc.y < rectangle.y) {
                viewToDoc.y = rectangle.y;
            }
            if (this.\u01ec) {
                final int n = this.\u01e6.x + this.\u01e6.width / 2;
                final int n2 = this.\u01e6.y + this.\u01e6.height / 2;
                int width2 = 0;
                int height2 = 0;
                switch (this.\u0204) {
                    case 1:
                    case 4:
                    case 8: {
                        width2 = this.\u01e6.width + 2 * (this.\u01e6.x - (viewToDoc.x - point2.x));
                        break;
                    }
                    case 2:
                    case 3:
                    case 6: {
                        width2 = this.\u01e6.width + 2 * (viewToDoc.x + point2.x - this.\u01e6.x - this.\u01e6.width);
                        break;
                    }
                }
                switch (this.\u0204) {
                    case 1:
                    case 2:
                    case 5: {
                        height2 = this.\u01e6.height + 2 * (this.\u01e6.y - (viewToDoc.y - point2.y));
                        break;
                    }
                    case 3:
                    case 4:
                    case 7: {
                        height2 = this.\u01e6.height + 2 * (viewToDoc.y + point2.y - this.\u01e6.y - this.\u01e6.height);
                        break;
                    }
                }
                if (width2 != 0) {
                    if (this.\u01e8 != null && width2 > this.\u01e8.x) {
                        width2 = this.\u01e8.x;
                    }
                    if (width2 < this.\u01e7.x) {
                        width2 = this.\u01e7.x;
                    }
                    viewToDoc2.width = width2;
                    viewToDoc2.x = n - viewToDoc2.width / 2;
                }
                if (height2 != 0) {
                    if (this.\u01e8 != null && height2 > this.\u01e8.y) {
                        height2 = this.\u01e8.y;
                    }
                    if (height2 < this.\u01e7.y) {
                        height2 = this.\u01e7.y;
                    }
                    viewToDoc2.height = height2;
                    viewToDoc2.y = n2 - viewToDoc2.height / 2;
                }
            }
            else {
                switch (this.\u0204) {
                    case 1:
                    case 4:
                    case 8: {
                        viewToDoc2.width = this.\u01e6.x + this.\u01e6.width - (viewToDoc.x - point2.x);
                        viewToDoc2.x = viewToDoc.x - point2.x;
                        if (this.\u01e8 != null && viewToDoc2.width > this.\u01e8.x) {
                            viewToDoc2.width = this.\u01e8.x;
                            viewToDoc2.x = this.\u01e6.x + this.\u01e6.width - viewToDoc2.width;
                        }
                        if (viewToDoc2.width < this.\u01e7.x) {
                            viewToDoc2.width = this.\u01e7.x;
                            viewToDoc2.x = this.\u01e6.x + this.\u01e6.width - viewToDoc2.width;
                            break;
                        }
                        break;
                    }
                    case 2:
                    case 3:
                    case 6: {
                        viewToDoc2.width = viewToDoc.x + point2.x - this.\u01e6.x;
                        if (this.\u01e8 != null && viewToDoc2.width > this.\u01e8.x) {
                            viewToDoc2.width = this.\u01e8.x;
                        }
                        if (viewToDoc2.width < this.\u01e7.x) {
                            viewToDoc2.width = this.\u01e7.x;
                            break;
                        }
                        break;
                    }
                }
                switch (this.\u0204) {
                    case 1:
                    case 2:
                    case 5: {
                        viewToDoc2.height = this.\u01e6.y + this.\u01e6.height - (viewToDoc.y - point2.y);
                        viewToDoc2.y = viewToDoc.y - point2.y;
                        if (this.\u01e8 != null && viewToDoc2.height > this.\u01e8.y) {
                            viewToDoc2.height = this.\u01e8.y;
                            viewToDoc2.y = this.\u01e6.y + this.\u01e6.height - viewToDoc2.height;
                        }
                        if (viewToDoc2.width < this.\u01e7.y) {
                            viewToDoc2.height = this.\u01e7.y;
                            viewToDoc2.y = this.\u01e6.y + this.\u01e6.height - viewToDoc2.height;
                            break;
                        }
                        break;
                    }
                    case 3:
                    case 4:
                    case 7: {
                        viewToDoc2.height = viewToDoc.y + point2.y - this.\u01e6.y;
                        if (this.\u01e8 != null && viewToDoc2.height > this.\u01e8.y) {
                            viewToDoc2.height = this.\u01e8.y;
                        }
                        if (viewToDoc2.height < this.\u01e7.y) {
                            viewToDoc2.height = this.\u01e7.y;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        else {
            if (this.mode == 1) {
                this.setCursorType(true);
                this.\u020a = false;
                return;
            }
            this.\u020a = false;
            return;
        }
        if (this.\u01e6 == null || !viewToDoc2.equals((Object)this.\u01e6)) {
            this.\u01d5();
            this.\u01e6 = viewToDoc2;
            this.\u01d5();
            switch (this.mode) {
                case 2: {
                    this.onMoving(Event.EMPTY);
                    break;
                }
                case 3: {
                    this.onResizing(Event.EMPTY);
                    break;
                }
                case 4: {
                    this.onTrackingRubberBand(Event.EMPTY);
                    break;
                }
            }
            if (this.mode == 2 || this.mode == 3) {
                this.\u0207.x = this.\u01e6.x;
                this.\u0207.y = this.\u01e6.y;
                this.\u0207.width = this.\u01e6.width;
                this.\u0207.height = this.\u01e6.height;
            }
        }
        this.\u020a = false;
    }
    
    public void setHandleSize(final int \u01ed) {
        this.\u01d5();
        this.\u01ed = \u01ed;
        this.\u01d5();
    }
    
    public int getHandleSize() {
        return this.\u01ed;
    }
    
    public void removeOnStartResize(final CancelEventHandler cancelEventHandler) {
        this.\u01fb = (CancelEventHandler)Delegate.remove((Delegate)this.\u01fb, (Delegate)cancelEventHandler);
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
    
    public void onCancel(final Event event) {
        if (this.\u0202 != null) {
            this.\u0202.invoke((Object)this, event);
        }
    }
    
    public void setInvertInside(final boolean \u01f0) {
        this.\u01f0 = \u01f0;
        this.\u01d5();
    }
    
    public boolean getInvertInside() {
        return this.\u01f0;
    }
}
