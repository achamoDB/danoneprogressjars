// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Point;
import java.util.Vector;
import com.ms.lang.Delegate;
import com.ms.wfc.core.Event;
import com.ms.wfc.core.CancelEvent;
import com.ms.wfc.ui.Rectangle;
import java.util.Enumeration;
import com.mim.wfc.util.MVector;
import com.ms.wfc.core.CancelEventHandler;
import com.ms.wfc.core.EventHandler;

public class ObjectTracker extends RectTracker
{
    private boolean \u01be;
    private boolean \u01bf;
    private boolean \u01c0;
    private boolean \u01c1;
    private EventHandler \u01c2;
    private CancelEventHandler \u01c3;
    private CancelEventHandler \u01c4;
    private CancelEventHandler \u01c5;
    private CancelEventHandler \u01c6;
    MVector \u01c7;
    
    private void \u01be(final boolean b) {
        if (this.\u01c7 != null) {
            final Enumeration<ITrackerObject> elements = this.\u01c7.elements();
            while (elements.hasMoreElements()) {
                final ITrackerObject trackerObject = elements.nextElement();
                if (trackerObject.isSelected()) {
                    Rectangle rectangle = trackerObject.getTrackPos();
                    if (rectangle == null) {
                        rectangle = trackerObject.getPos();
                    }
                    Rectangle trackPos;
                    if (b) {
                        trackPos = this.applyLastToRect(rectangle);
                    }
                    else {
                        trackPos = this.applyOrigToRect(rectangle);
                    }
                    trackerObject.setTrackPos(trackPos);
                    trackerObject.moveTo(trackPos);
                }
            }
            if (!b) {
                this.recalcTracker();
            }
        }
    }
    
    public void setMoveImmediate(final boolean \u01be) {
        this.\u01be = \u01be;
    }
    
    public void onStartTrackRubberBand(final CancelEvent cancelEvent) {
        if (this.\u01c0) {
            final ITrackerObject object = this.findObjectAt(this.\u01ea());
            if (object != null) {
                cancelEvent.cancel = true;
                this.clearTracker(false);
                object.select();
                this.recalcTracker();
                this.startMove();
                return;
            }
        }
        super.onTrackingRubberBand((Event)cancelEvent);
    }
    
    public boolean getMoveImmediate() {
        return this.\u01be;
    }
    
    public void removeOnBeforeResized(final CancelEventHandler cancelEventHandler) {
        this.\u01c6 = (CancelEventHandler)Delegate.remove((Delegate)this.\u01c6, (Delegate)cancelEventHandler);
    }
    
    public void onResized(final Event event) {
        if (!this.\u01bf) {
            final CancelEvent cancelEvent = new CancelEvent();
            this.onBeforeResized(cancelEvent);
            if (!cancelEvent.cancel) {
                this.\u01be(false);
            }
            else {
                this.recalcTracker();
            }
        }
        else {
            this.recalcTracker();
        }
        super.onResized(event);
    }
    
    public void addOnBeforeResized(final CancelEventHandler cancelEventHandler) {
        this.\u01c6 = (CancelEventHandler)Delegate.combine((Delegate)this.\u01c6, (Delegate)cancelEventHandler);
    }
    
    protected void onBeforeMoving(final CancelEvent cancelEvent) {
        if (this.\u01c3 != null) {
            this.\u01c3.invoke((Object)this, cancelEvent);
        }
    }
    
    public void removeSelectedObjects() {
        if (this.\u01c7 != null) {
            final Object[] array = this.\u01c7.getArray();
            final int size = this.\u01c7.size();
            int i = 0;
            while (i < size) {
                if (((ITrackerObject)array[i]).isSelected()) {
                    this.\u01c7.removeElementAt(i);
                }
                else {
                    ++i;
                }
            }
            if (this.\u01c7.isEmpty()) {
                this.\u01c7 = null;
            }
            this.clearSelection();
        }
    }
    
    public void addObject(final ITrackerObject obj) {
        if (this.\u01c7 == null) {
            this.\u01c7 = new MVector();
        }
        this.\u01c7.addElement(obj);
    }
    
    protected void onBeforeMoved(final CancelEvent cancelEvent) {
        if (this.\u01c4 != null) {
            this.\u01c4.invoke((Object)this, cancelEvent);
        }
    }
    
    public void recalcTracker() {
        this.clearSelection();
        if (this.\u01c7 != null) {
            final Enumeration<ITrackerObject> elements = this.\u01c7.elements();
            while (elements.hasMoreElements()) {
                final ITrackerObject trackerObject = elements.nextElement();
                if (trackerObject.isSelected()) {
                    this.addSelectionRect(trackerObject.getPos());
                }
            }
        }
        this.onRecalcTracker(Event.EMPTY);
    }
    
    public void addOnRecalcTracker(final EventHandler eventHandler) {
        this.\u01c2 = (EventHandler)Delegate.combine((Delegate)this.\u01c2, (Delegate)eventHandler);
    }
    
    public void setObjects(final Vector vector) {
        if (this.\u01c7 == null) {
            this.\u01c7 = new MVector();
        }
        else {
            this.\u01c7.removeAllElements();
        }
        if (vector != null) {
            for (int size = vector.size(), i = 0; i < size; ++i) {
                this.\u01c7.addElement(vector.elementAt(i));
            }
        }
    }
    
    public void setObjects(final ITrackerObject[] array) {
        if (this.\u01c7 == null) {
            this.\u01c7 = new MVector();
        }
        else {
            this.\u01c7.removeAllElements();
        }
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                this.\u01c7.addElement(array[i]);
            }
        }
    }
    
    public Vector getObjects() {
        return this.\u01c7;
    }
    
    public void removeAllObjects() {
        this.\u01c7 = null;
        this.recalcTracker();
    }
    
    protected void onBeforeResized(final CancelEvent cancelEvent) {
        if (this.\u01c6 != null) {
            this.\u01c6.invoke((Object)this, cancelEvent);
        }
    }
    
    public void onMoving(final Event event) {
        if (this.\u01be) {
            final CancelEvent cancelEvent = new CancelEvent();
            this.onBeforeMoving(cancelEvent);
            if (!cancelEvent.cancel) {
                this.\u01be(true);
            }
        }
        super.onMoving(event);
    }
    
    public void addOnBeforeMoving(final CancelEventHandler cancelEventHandler) {
        this.\u01c3 = (CancelEventHandler)Delegate.combine((Delegate)this.\u01c3, (Delegate)cancelEventHandler);
    }
    
    public void removeOnBeforeResizing(final CancelEventHandler cancelEventHandler) {
        this.\u01c5 = (CancelEventHandler)Delegate.remove((Delegate)this.\u01c5, (Delegate)cancelEventHandler);
    }
    
    protected void onRecalcTracker(final Event event) {
        if (this.\u01c2 != null) {
            this.\u01c2.invoke((Object)this, event);
        }
    }
    
    public Enumeration getSelectedObjects() {
        return new ObjectTrackerEnumeration(this, true);
    }
    
    public boolean removeObject(final ITrackerObject obj) {
        if (this.\u01c7 != null) {
            final boolean removeElement = this.\u01c7.removeElement(obj);
            if (obj.isSelected()) {
                this.recalcTracker();
            }
            return removeElement;
        }
        return false;
    }
    
    public void setRubberBandSelection(final boolean \u01c1) {
        this.\u01c1 = \u01c1;
    }
    
    public boolean getRubberBandSelection() {
        return this.\u01c1;
    }
    
    public ITrackerObject findObjectAt(final Point point) {
        if (this.\u01c7 != null) {
            final Enumeration<ITrackerObject> elements = this.\u01c7.elements();
            while (elements.hasMoreElements()) {
                final ITrackerObject trackerObject = elements.nextElement();
                if (trackerObject.isInside(point)) {
                    return trackerObject;
                }
            }
        }
        return null;
    }
    
    public ITrackerObject findObjectAt(final Point point, final int n) {
        if (this.\u01c7 != null) {
            final Rectangle rectangle = new Rectangle(point.x - n, point.y - n, n * 2, n * 2);
            final Enumeration<ITrackerObject> elements = (Enumeration<ITrackerObject>)this.\u01c7.elements();
            while (elements.hasMoreElements()) {
                final ITrackerObject trackerObject = elements.nextElement();
                if (trackerObject.intersectsWith(rectangle)) {
                    return trackerObject;
                }
            }
        }
        return null;
    }
    
    public void onMoved(final Event event) {
        if (!this.\u01be) {
            final CancelEvent cancelEvent = new CancelEvent();
            this.onBeforeMoved(cancelEvent);
            if (!cancelEvent.cancel) {
                this.\u01be(false);
            }
            else {
                this.recalcTracker();
            }
        }
        else {
            this.recalcTracker();
        }
        super.onMoved(event);
    }
    
    public void addOnBeforeMoved(final CancelEventHandler cancelEventHandler) {
        this.\u01c4 = (CancelEventHandler)Delegate.combine((Delegate)this.\u01c4, (Delegate)cancelEventHandler);
    }
    
    public void onResizing(final Event event) {
        if (this.\u01bf) {
            final CancelEvent cancelEvent = new CancelEvent();
            this.onBeforeResizing(cancelEvent);
            if (!cancelEvent.cancel) {
                this.\u01be(true);
            }
        }
        super.onResizing(event);
    }
    
    public ObjectTracker() {
        this.\u01c1 = true;
    }
    
    public void onTrackedRubberBand(final Event event) {
        if (this.\u01c1) {
            final Rectangle selectionRect = this.getSelectionRect();
            final boolean b = selectionRect.width == 1 && selectionRect.height == 1;
            this.clearSelection();
            if (selectionRect != null && this.\u01c7 != null) {
                final boolean wasShiftPressed = this.wasShiftPressed();
                boolean b2 = false;
                final Enumeration<ITrackerObject> elements = (Enumeration<ITrackerObject>)this.\u01c7.elements();
                while (elements.hasMoreElements()) {
                    final ITrackerObject trackerObject = elements.nextElement();
                    if (b ? trackerObject.intersectsWith(selectionRect) : trackerObject.isContainedIn(selectionRect)) {
                        b2 = true;
                        if (wasShiftPressed && b && trackerObject.isSelected()) {
                            trackerObject.deselect();
                            trackerObject.setTrackPos(null);
                        }
                        else {
                            trackerObject.select();
                        }
                    }
                    else {
                        if (wasShiftPressed) {
                            continue;
                        }
                        trackerObject.deselect();
                        trackerObject.setTrackPos(null);
                    }
                }
                if (!b2) {
                    final ITrackerObject object = this.findObjectAt(new Point(selectionRect.x + selectionRect.width / 2, selectionRect.y + selectionRect.height / 2));
                    if (object != null) {
                        if (wasShiftPressed && object.isSelected()) {
                            object.deselect();
                            object.setTrackPos(null);
                        }
                        else {
                            object.select();
                        }
                    }
                }
                this.recalcTracker();
            }
            else {
                this.clearSelection();
            }
        }
        super.onTrackedRubberBand(event);
    }
    
    public void setResizeImmediate(final boolean \u01bf) {
        this.\u01bf = \u01bf;
    }
    
    public boolean getResizeImmediate() {
        return this.\u01bf;
    }
    
    public void addOnBeforeResizing(final CancelEventHandler cancelEventHandler) {
        this.\u01c5 = (CancelEventHandler)Delegate.combine((Delegate)this.\u01c5, (Delegate)cancelEventHandler);
    }
    
    public void clearTracker(final boolean b) {
        this.clearSelection();
        if (this.\u01c7 != null) {
            final Enumeration<ITrackerObject> elements = this.\u01c7.elements();
            while (elements.hasMoreElements()) {
                final ITrackerObject trackerObject = elements.nextElement();
                if (trackerObject.isSelected()) {
                    trackerObject.deselect();
                    trackerObject.setTrackPos(null);
                }
            }
        }
        if (b) {
            this.onRecalcTracker(Event.EMPTY);
        }
    }
    
    public void removeOnRecalcTracker(final EventHandler eventHandler) {
        this.\u01c2 = (EventHandler)Delegate.remove((Delegate)this.\u01c2, (Delegate)eventHandler);
    }
    
    public void setDragOnClick(final boolean \u01c0) {
        this.\u01c0 = \u01c0;
    }
    
    public boolean getDragOnClick() {
        return this.\u01c0;
    }
    
    public void removeOnBeforeMoving(final CancelEventHandler cancelEventHandler) {
        this.\u01c3 = (CancelEventHandler)Delegate.remove((Delegate)this.\u01c3, (Delegate)cancelEventHandler);
    }
    
    protected void onBeforeResizing(final CancelEvent cancelEvent) {
        if (this.\u01c5 != null) {
            this.\u01c5.invoke((Object)this, cancelEvent);
        }
    }
    
    public Enumeration getAllObjects() {
        return new ObjectTrackerEnumeration(this, false);
    }
    
    public void removeOnBeforeMoved(final CancelEventHandler cancelEventHandler) {
        this.\u01c4 = (CancelEventHandler)Delegate.remove((Delegate)this.\u01c4, (Delegate)cancelEventHandler);
    }
    
    public void onCancel(final Event event) {
        this.recalcTracker();
        super.onCancel(event);
    }
}
