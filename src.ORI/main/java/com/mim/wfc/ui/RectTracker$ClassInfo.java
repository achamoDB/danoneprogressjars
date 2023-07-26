// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.core.Component$ClassInfo;

public class RectTracker$ClassInfo extends Component$ClassInfo
{
    public static final PropertyInfo viewWindow;
    public static final PropertyInfo selectionRect;
    public static final PropertyInfo minSize;
    public static final PropertyInfo maxSize;
    public static final PropertyInfo enabled;
    public static final PropertyInfo enableMove;
    public static final PropertyInfo enableResize;
    public static final PropertyInfo enableRubberBand;
    public static final PropertyInfo resizeCentered;
    public static final PropertyInfo handleSize;
    public static final PropertyInfo handleMask;
    public static final PropertyInfo dottedLine;
    public static final PropertyInfo invertInside;
    public static final PropertyInfo resizeInside;
    public static final PropertyInfo trackerColor;
    public static final PropertyInfo outOfBoundsRepeatRate;
    public static final EventInfo startMove;
    public static final EventInfo moving;
    public static final EventInfo moved;
    public static final EventInfo startResize;
    public static final EventInfo resizing;
    public static final EventInfo resized;
    public static final EventInfo startTrackRubberBand;
    public static final EventInfo trackingRubberBand;
    public static final EventInfo trackedRubberBand;
    public static final EventInfo outOfBounds;
    public static final EventInfo cancel;
    private static Class \u020b;
    private static Class \u00ea;
    private static Class \u020c;
    private static Class \u0120;
    private static Class \u0164;
    private static Class \u00eb;
    private static Class \u00ec;
    private static Class \u020d;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(RectTracker$ClassInfo.startMove);
        events.add(RectTracker$ClassInfo.moving);
        events.add(RectTracker$ClassInfo.moved);
        events.add(RectTracker$ClassInfo.startResize);
        events.add(RectTracker$ClassInfo.resizing);
        events.add(RectTracker$ClassInfo.resized);
        events.add(RectTracker$ClassInfo.startTrackRubberBand);
        events.add(RectTracker$ClassInfo.trackingRubberBand);
        events.add(RectTracker$ClassInfo.trackedRubberBand);
        events.add(RectTracker$ClassInfo.outOfBounds);
        events.add(RectTracker$ClassInfo.cancel);
    }
    
    static {
        viewWindow = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "viewWindow", (RectTracker$ClassInfo.\u00ea != null) ? RectTracker$ClassInfo.\u00ea : (RectTracker$ClassInfo.\u00ea = \u00c6("com.ms.wfc.ui.Control")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The rect tracker's view window"));
        selectionRect = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "selectionRect", (RectTracker$ClassInfo.\u020c != null) ? RectTracker$ClassInfo.\u020c : (RectTracker$ClassInfo.\u020c = \u00c6("com.ms.wfc.ui.Rectangle")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The current tracker rectangle"));
        minSize = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "minSize", (RectTracker$ClassInfo.\u0120 != null) ? RectTracker$ClassInfo.\u0120 : (RectTracker$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The minimum size of the tracker rectangle"));
        maxSize = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "maxSize", (RectTracker$ClassInfo.\u0120 != null) ? RectTracker$ClassInfo.\u0120 : (RectTracker$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The minimum size of the tracker rectangle"));
        enabled = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "enabled", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Enables or disables the tracker"));
        enableMove = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "enableMove", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Enables moving of the tracker"));
        enableResize = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "enableResize", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Enables resizing of the tracker"));
        enableRubberBand = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "enableRubberBand", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Enables tracking a rubber band"));
        resizeCentered = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "resizeCentered", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Centered resize mode"));
        handleSize = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "handleSize", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(10)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The size of the tracker handles in pixel"));
        handleMask = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "handleMask", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(510)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Determines which handles to display"));
        dottedLine = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "dottedLine", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Draws a dotted or solid line around the tracker"));
        invertInside = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "invertInside", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Draws a dotted or solid line around the tracker"));
        resizeInside = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "resizeInside", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Displays the resize handles inside or outside the tracker rectangle"));
        trackerColor = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "trackerColor", (RectTracker$ClassInfo.\u0164 != null) ? RectTracker$ClassInfo.\u0164 : (RectTracker$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("The tracker color used for solid lines"));
        outOfBoundsRepeatRate = new PropertyInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "outOfBoundsRepeatRate", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Timer repeat rate (events per second) for the out of bounds event"));
        startMove = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "startMove", (RectTracker$ClassInfo.\u00eb != null) ? RectTracker$ClassInfo.\u00eb : (RectTracker$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The rect tracker is about to be moved"));
        moving = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "moving", (RectTracker$ClassInfo.\u00ec != null) ? RectTracker$ClassInfo.\u00ec : (RectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker is being moved."));
        moved = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "moved", (RectTracker$ClassInfo.\u00ec != null) ? RectTracker$ClassInfo.\u00ec : (RectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker was moved."));
        startResize = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "startResize", (RectTracker$ClassInfo.\u00eb != null) ? RectTracker$ClassInfo.\u00eb : (RectTracker$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The RectTracker is about to be resized"));
        resizing = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "resizing", (RectTracker$ClassInfo.\u00ec != null) ? RectTracker$ClassInfo.\u00ec : (RectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker is being resized."));
        resized = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "resized", (RectTracker$ClassInfo.\u00ec != null) ? RectTracker$ClassInfo.\u00ec : (RectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker was resized."));
        startTrackRubberBand = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "startTrackRubberBand", (RectTracker$ClassInfo.\u00eb != null) ? RectTracker$ClassInfo.\u00eb : (RectTracker$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("A rubber band is about to be tracked"));
        trackingRubberBand = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "trackingRubberBand", (RectTracker$ClassInfo.\u00ec != null) ? RectTracker$ClassInfo.\u00ec : (RectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The rubber band is being tracked."));
        trackedRubberBand = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "trackedRubberBand", (RectTracker$ClassInfo.\u00ec != null) ? RectTracker$ClassInfo.\u00ec : (RectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The rubber band was tracked."));
        outOfBounds = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "outOfBounds", (RectTracker$ClassInfo.\u020d != null) ? RectTracker$ClassInfo.\u020d : (RectTracker$ClassInfo.\u020d = \u00c6("com.mim.wfc.ui.OutOfBoundsEventHandler")), (MemberAttribute)new DescriptionAttribute("Mouse moved outside of the view windows's bounds"));
        cancel = new EventInfo((RectTracker$ClassInfo.\u020b != null) ? RectTracker$ClassInfo.\u020b : (RectTracker$ClassInfo.\u020b = \u00c6("com.mim.wfc.ui.RectTracker")), "cancel", (RectTracker$ClassInfo.\u00ec != null) ? RectTracker$ClassInfo.\u00ec : (RectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("A tracker was cancelled."));
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public void getProperties(final IProperties properties) {
        super.getProperties(properties);
        properties.add(RectTracker$ClassInfo.viewWindow);
        properties.add(RectTracker$ClassInfo.selectionRect);
        properties.add(RectTracker$ClassInfo.minSize);
        properties.add(RectTracker$ClassInfo.maxSize);
        properties.add(RectTracker$ClassInfo.enabled);
        properties.add(RectTracker$ClassInfo.enableMove);
        properties.add(RectTracker$ClassInfo.enableResize);
        properties.add(RectTracker$ClassInfo.enableRubberBand);
        properties.add(RectTracker$ClassInfo.resizeCentered);
        properties.add(RectTracker$ClassInfo.handleSize);
        properties.add(RectTracker$ClassInfo.handleMask);
        properties.add(RectTracker$ClassInfo.dottedLine);
        properties.add(RectTracker$ClassInfo.invertInside);
        properties.add(RectTracker$ClassInfo.resizeInside);
        properties.add(RectTracker$ClassInfo.trackerColor);
        properties.add(RectTracker$ClassInfo.outOfBoundsRepeatRate);
    }
    
    public String getDefaultPropertyName() {
        return new String("viewWindow");
    }
}
