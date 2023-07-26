// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.UserControl$ClassInfo;

public class PopupText$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo text;
    public static final PropertyInfo shadowSize;
    public static final PropertyInfo maxWidth;
    public static final PropertyInfo timeout;
    public static final PropertyInfo autoTimeout;
    private static Class \u01d2;
    private static Class \u00e8;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        text = new PropertyInfo((PopupText$ClassInfo.\u01d2 != null) ? PopupText$ClassInfo.\u01d2 : (PopupText$ClassInfo.\u01d2 = \u00c6("com.mim.wfc.ui.PopupText")), "text", (PopupText$ClassInfo.\u00e8 != null) ? PopupText$ClassInfo.\u00e8 : (PopupText$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The text to display in the popup text window. Use the '\\n' character to separate lines."));
        shadowSize = new PropertyInfo((PopupText$ClassInfo.\u01d2 != null) ? PopupText$ClassInfo.\u01d2 : (PopupText$ClassInfo.\u01d2 = \u00c6("com.mim.wfc.ui.PopupText")), "shadowSize", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Size of the window's shadow in pixel. Set to 0 to display no shadow."));
        maxWidth = new PropertyInfo((PopupText$ClassInfo.\u01d2 != null) ? PopupText$ClassInfo.\u01d2 : (PopupText$ClassInfo.\u01d2 = \u00c6("com.mim.wfc.ui.PopupText")), "maxWidth", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(200)), (MemberAttribute)new DescriptionAttribute("Maximum width of the popup window"));
        timeout = new PropertyInfo((PopupText$ClassInfo.\u01d2 != null) ? PopupText$ClassInfo.\u01d2 : (PopupText$ClassInfo.\u01d2 = \u00c6("com.mim.wfc.ui.PopupText")), "timeout", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Time in milliseconds the popup text window will be shown"));
        autoTimeout = new PropertyInfo((PopupText$ClassInfo.\u01d2 != null) ? PopupText$ClassInfo.\u01d2 : (PopupText$ClassInfo.\u01d2 = \u00c6("com.mim.wfc.ui.PopupText")), "autoTimeout", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Enables the automatic computation of the display timer"));
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
        properties.add(PopupText$ClassInfo.text);
        properties.add(PopupText$ClassInfo.timeout);
        properties.add(PopupText$ClassInfo.autoTimeout);
        properties.add(PopupText$ClassInfo.shadowSize);
        properties.add(PopupText$ClassInfo.maxWidth);
        properties.add(new PropertyInfo(Control$ClassInfo.backColor, (MemberAttribute)new DefaultValueAttribute((Object)Color.INFO)));
        properties.add(new PropertyInfo(Control$ClassInfo.foreColor, (MemberAttribute)new DefaultValueAttribute((Object)Color.INFOTEXT)));
    }
}
