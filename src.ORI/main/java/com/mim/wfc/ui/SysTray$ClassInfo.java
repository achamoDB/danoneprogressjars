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

public class SysTray$ClassInfo extends Component$ClassInfo
{
    public static final PropertyInfo enabled;
    public static final PropertyInfo visible;
    public static final PropertyInfo text;
    public static final PropertyInfo icon;
    public static final PropertyInfo contextMenu;
    public static final EventInfo mouseMove;
    public static final EventInfo mouseDown;
    public static final EventInfo mouseUp;
    private static Class \u026d;
    private static Class \u00e8;
    private static Class \u026e;
    private static Class \u026f;
    private static Class \u0270;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(SysTray$ClassInfo.mouseMove);
        events.add(SysTray$ClassInfo.mouseDown);
        events.add(SysTray$ClassInfo.mouseUp);
    }
    
    public String getDefaultEventName() {
        return new String("mouseDown");
    }
    
    static {
        enabled = new PropertyInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "enabled", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Enables the handling of mouse events in the system tray"));
        visible = new PropertyInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "visible", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Shows/hides the icon in the system tray"));
        text = new PropertyInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "text", (SysTray$ClassInfo.\u00e8 != null) ? SysTray$ClassInfo.\u00e8 : (SysTray$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The text to display as popup when the mouse moves over the tray icon"));
        icon = new PropertyInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "icon", (SysTray$ClassInfo.\u026e != null) ? SysTray$ClassInfo.\u026e : (SysTray$ClassInfo.\u026e = \u00c6("com.ms.wfc.ui.Icon")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The icon to display in the system tray"));
        contextMenu = new PropertyInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "contextMenu", (SysTray$ClassInfo.\u026f != null) ? SysTray$ClassInfo.\u026f : (SysTray$ClassInfo.\u026f = \u00c6("com.ms.wfc.ui.ContextMenu")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The context menu to display"));
        mouseMove = new EventInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "mouseMove", (SysTray$ClassInfo.\u0270 != null) ? SysTray$ClassInfo.\u0270 : (SysTray$ClassInfo.\u0270 = \u00c6("com.ms.wfc.ui.MouseEventHandler")), (MemberAttribute)new DescriptionAttribute("The mouse moved over the system tray icon"));
        mouseDown = new EventInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "mouseDown", (SysTray$ClassInfo.\u0270 != null) ? SysTray$ClassInfo.\u0270 : (SysTray$ClassInfo.\u0270 = \u00c6("com.ms.wfc.ui.MouseEventHandler")), (MemberAttribute)new DescriptionAttribute("The mouse button was pressed over the system tray icon"));
        mouseUp = new EventInfo((SysTray$ClassInfo.\u026d != null) ? SysTray$ClassInfo.\u026d : (SysTray$ClassInfo.\u026d = \u00c6("com.mim.wfc.ui.SysTray")), "mouseUp", (SysTray$ClassInfo.\u0270 != null) ? SysTray$ClassInfo.\u0270 : (SysTray$ClassInfo.\u0270 = \u00c6("com.ms.wfc.ui.MouseEventHandler")), (MemberAttribute)new DescriptionAttribute("The mouse button was released"));
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
        properties.add(SysTray$ClassInfo.enabled);
        properties.add(SysTray$ClassInfo.visible);
        properties.add(SysTray$ClassInfo.text);
        properties.add(SysTray$ClassInfo.icon);
        properties.add(SysTray$ClassInfo.contextMenu);
    }
    
    public String getDefaultPropertyName() {
        return new String("text");
    }
}
