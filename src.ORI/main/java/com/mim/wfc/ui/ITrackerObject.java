// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Rectangle;

public interface ITrackerObject
{
    void deselect();
    
    void invalidate();
    
    boolean intersectsWith(final Rectangle p0);
    
    boolean isSelected();
    
    void select();
    
    Rectangle getTrackPos();
    
    void setTrackPos(final Rectangle p0);
    
    void moveTo(final Point p0);
    
    void moveTo(final Rectangle p0);
    
    boolean isInside(final Point p0);
    
    Rectangle getPos();
    
    boolean isContainedIn(final Rectangle p0);
}
