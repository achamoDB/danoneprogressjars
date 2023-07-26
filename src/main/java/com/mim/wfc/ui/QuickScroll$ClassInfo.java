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
import com.ms.wfc.core.Component$ClassInfo;

public class QuickScroll$ClassInfo extends Component$ClassInfo
{
    public static final PropertyInfo enabled;
    public static final PropertyInfo viewWindow;
    public static final PropertyInfo toggleMode;
    public static final PropertyInfo moveActivation;
    public static final PropertyInfo zoomActivation;
    public static final PropertyInfo rotateActivation;
    public static final PropertyInfo repeatRate;
    public static final PropertyInfo neutralDistance;
    public static final EventInfo quickScroll;
    public static final EventInfo quickScrollBegin;
    public static final EventInfo quickScrollEnd;
    private static Class \u01e5;
    private static Class \u00ea;
    private static Class \u01e4;
    private static Class \u01e3;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(QuickScroll$ClassInfo.quickScroll);
        events.add(QuickScroll$ClassInfo.quickScrollBegin);
        events.add(QuickScroll$ClassInfo.quickScrollEnd);
    }
    
    static {
        enabled = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "enabled", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Enables or disables the quick scroll"));
        viewWindow = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "viewWindow", (QuickScroll$ClassInfo.\u00ea != null) ? QuickScroll$ClassInfo.\u00ea : (QuickScroll$ClassInfo.\u00ea = \u00c6("com.ms.wfc.ui.Control")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The quick scroll's view window"));
        toggleMode = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "toggleMode", (QuickScroll$ClassInfo.\u01e4 != null) ? QuickScroll$ClassInfo.\u01e4 : (QuickScroll$ClassInfo.\u01e4 = \u00c6("com.mim.wfc.ui.QuickScrollToggleMode")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(2)), (MemberAttribute)new DescriptionAttribute("Controls how the quick scroll mode is toggled."));
        moveActivation = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "moveActivation", (QuickScroll$ClassInfo.\u01e3 != null) ? QuickScroll$ClassInfo.\u01e3 : (QuickScroll$ClassInfo.\u01e3 = \u00c6("com.mim.wfc.ui.QuickScrollActivation")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Selects the quick scroll's move mode activation"));
        zoomActivation = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "zoomActivation", (QuickScroll$ClassInfo.\u01e3 != null) ? QuickScroll$ClassInfo.\u01e3 : (QuickScroll$ClassInfo.\u01e3 = \u00c6("com.mim.wfc.ui.QuickScrollActivation")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Selects the quick scroll's zoom mode activation"));
        rotateActivation = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "rotateActivation", (QuickScroll$ClassInfo.\u01e3 != null) ? QuickScroll$ClassInfo.\u01e3 : (QuickScroll$ClassInfo.\u01e3 = \u00c6("com.mim.wfc.ui.QuickScrollActivation")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Selects the quick scroll's rotate mode activation"));
        repeatRate = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "repeatRate", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(10)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("If the repeat rate is not 0, the quick scroll action will be triggered if a timer elapses only (except in ROTATE mode). Otherwise the quick scroll action will be triggered if the mouse moves in quick scroll mode."));
        neutralDistance = new PropertyInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "neutralDistance", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(5)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Determines the minimum distance in pixel from the quick scroll center the mouse must move to cause any quick scroll events."));
        quickScroll = new EventInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "quickScroll", (QuickScroll$ClassInfo.\u00ec != null) ? QuickScroll$ClassInfo.\u00ec : (QuickScroll$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("The mouse was moved in quick scroll mode."));
        quickScrollBegin = new EventInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "quickScrollBegin", (QuickScroll$ClassInfo.\u00ec != null) ? QuickScroll$ClassInfo.\u00ec : (QuickScroll$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("A quick scroll action was started."));
        quickScrollEnd = new EventInfo((QuickScroll$ClassInfo.\u01e5 != null) ? QuickScroll$ClassInfo.\u01e5 : (QuickScroll$ClassInfo.\u01e5 = \u00c6("com.mim.wfc.ui.QuickScroll")), "quickScrollEnd", (QuickScroll$ClassInfo.\u00ec != null) ? QuickScroll$ClassInfo.\u00ec : (QuickScroll$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("A quick scroll action ended."));
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
        properties.add(QuickScroll$ClassInfo.enabled);
        properties.add(QuickScroll$ClassInfo.viewWindow);
        properties.add(QuickScroll$ClassInfo.toggleMode);
        properties.add(QuickScroll$ClassInfo.moveActivation);
        properties.add(QuickScroll$ClassInfo.zoomActivation);
        properties.add(QuickScroll$ClassInfo.rotateActivation);
        properties.add(QuickScroll$ClassInfo.repeatRate);
        properties.add(QuickScroll$ClassInfo.neutralDistance);
    }
    
    public String getDefaultPropertyName() {
        return new String("viewWindow");
    }
}
