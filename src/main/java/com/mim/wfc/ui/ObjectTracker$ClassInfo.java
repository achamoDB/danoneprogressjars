// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;

public class ObjectTracker$ClassInfo extends RectTracker$ClassInfo
{
    public static final PropertyInfo moveImmediate;
    public static final PropertyInfo resizeImmediate;
    public static final PropertyInfo dragOnClick;
    public static final PropertyInfo rubberBandSelection;
    public static final EventInfo recalcTracker;
    public static final EventInfo beforeMoving;
    public static final EventInfo beforeMoved;
    public static final EventInfo beforeResizing;
    public static final EventInfo beforeResized;
    private static Class \u01c8;
    private static Class \u00ec;
    private static Class \u00eb;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(ObjectTracker$ClassInfo.recalcTracker);
        events.add(ObjectTracker$ClassInfo.beforeMoving);
        events.add(ObjectTracker$ClassInfo.beforeMoved);
        events.add(ObjectTracker$ClassInfo.beforeResizing);
        events.add(ObjectTracker$ClassInfo.beforeResized);
    }
    
    static {
        moveImmediate = new PropertyInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "moveImmediate", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Immediate movement mode"));
        resizeImmediate = new PropertyInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "resizeImmediate", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Immediate resize mode"));
        dragOnClick = new PropertyInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "dragOnClick", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Immediately drag a single object when clicked instead of performing a rubberband selection"));
        rubberBandSelection = new PropertyInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "rubberBandSelection", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Selects object by tracking a rubber band"));
        recalcTracker = new EventInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "recalcTracker", (ObjectTracker$ClassInfo.\u00ec != null) ? ObjectTracker$ClassInfo.\u00ec : (ObjectTracker$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The tracker rectangle was recalculated"));
        beforeMoving = new EventInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "beforeMoving", (ObjectTracker$ClassInfo.\u00eb != null) ? ObjectTracker$ClassInfo.\u00eb : (ObjectTracker$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker will be moving"));
        beforeMoved = new EventInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "beforeMoved", (ObjectTracker$ClassInfo.\u00eb != null) ? ObjectTracker$ClassInfo.\u00eb : (ObjectTracker$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker will be moved"));
        beforeResizing = new EventInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "beforeResizing", (ObjectTracker$ClassInfo.\u00eb != null) ? ObjectTracker$ClassInfo.\u00eb : (ObjectTracker$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker will be resized"));
        beforeResized = new EventInfo((ObjectTracker$ClassInfo.\u01c8 != null) ? ObjectTracker$ClassInfo.\u01c8 : (ObjectTracker$ClassInfo.\u01c8 = \u00c6("com.mim.wfc.ui.ObjectTracker")), "beforeResized", (ObjectTracker$ClassInfo.\u00eb != null) ? ObjectTracker$ClassInfo.\u00eb : (ObjectTracker$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The tracker will be resized"));
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
        properties.add(ObjectTracker$ClassInfo.moveImmediate);
        properties.add(ObjectTracker$ClassInfo.resizeImmediate);
        properties.add(ObjectTracker$ClassInfo.dragOnClick);
        properties.add(ObjectTracker$ClassInfo.rubberBandSelection);
    }
}
