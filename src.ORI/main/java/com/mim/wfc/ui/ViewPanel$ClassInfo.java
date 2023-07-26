// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Panel$ClassInfo;

public class ViewPanel$ClassInfo extends Panel$ClassInfo
{
    public static final PropertyInfo mode;
    public static final PropertyInfo zoomMode;
    public static final PropertyInfo zoomFactor;
    public static final PropertyInfo doubleBuffer;
    public static final PropertyInfo margin;
    public static final PropertyInfo docSize;
    private static Class \u029d;
    private static Class \u029e;
    private static Class \u029f;
    private static Class \u0120;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        mode = new PropertyInfo((ViewPanel$ClassInfo.\u029d != null) ? ViewPanel$ClassInfo.\u029d : (ViewPanel$ClassInfo.\u029d = \u00c6("com.mim.wfc.ui.ViewPanel")), "mode", (ViewPanel$ClassInfo.\u029e != null) ? ViewPanel$ClassInfo.\u029e : (ViewPanel$ClassInfo.\u029e = \u00c6("com.mim.wfc.ui.ViewPanelMode")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Scroll behavior of the panel"));
        zoomMode = new PropertyInfo((ViewPanel$ClassInfo.\u029d != null) ? ViewPanel$ClassInfo.\u029d : (ViewPanel$ClassInfo.\u029d = \u00c6("com.mim.wfc.ui.ViewPanel")), "zoomMode", (ViewPanel$ClassInfo.\u029f != null) ? ViewPanel$ClassInfo.\u029f : (ViewPanel$ClassInfo.\u029f = \u00c6("com.mim.wfc.ui.ViewPanelZoomMode")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Zoom behavior of the panel"));
        zoomFactor = new PropertyInfo((ViewPanel$ClassInfo.\u029d != null) ? ViewPanel$ClassInfo.\u029d : (ViewPanel$ClassInfo.\u029d = \u00c6("com.mim.wfc.ui.ViewPanel")), "zoomFactor", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(100)), (MemberAttribute)new DescriptionAttribute("Zoom factor in percent"));
        doubleBuffer = new PropertyInfo((ViewPanel$ClassInfo.\u029d != null) ? ViewPanel$ClassInfo.\u029d : (ViewPanel$ClassInfo.\u029d = \u00c6("com.mim.wfc.ui.ViewPanel")), "doubleBuffer", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Double buffering"));
        margin = new PropertyInfo((ViewPanel$ClassInfo.\u029d != null) ? ViewPanel$ClassInfo.\u029d : (ViewPanel$ClassInfo.\u029d = \u00c6("com.mim.wfc.ui.ViewPanel")), "margin", (ViewPanel$ClassInfo.\u0120 != null) ? ViewPanel$ClassInfo.\u0120 : (ViewPanel$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Point(0, 0)), (MemberAttribute)new DescriptionAttribute("Additional auto scroll margin"));
        docSize = new PropertyInfo((ViewPanel$ClassInfo.\u029d != null) ? ViewPanel$ClassInfo.\u029d : (ViewPanel$ClassInfo.\u029d = \u00c6("com.mim.wfc.ui.ViewPanel")), "docSize", (ViewPanel$ClassInfo.\u0120 != null) ? ViewPanel$ClassInfo.\u0120 : (ViewPanel$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Point(0, 0)), (MemberAttribute)new DescriptionAttribute("Total size of the document"));
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
        properties.add(ViewPanel$ClassInfo.mode);
        properties.add(ViewPanel$ClassInfo.zoomMode);
        properties.add(ViewPanel$ClassInfo.zoomFactor);
        properties.add(ViewPanel$ClassInfo.doubleBuffer);
        properties.add(ViewPanel$ClassInfo.margin);
        properties.add(ViewPanel$ClassInfo.docSize);
    }
}
