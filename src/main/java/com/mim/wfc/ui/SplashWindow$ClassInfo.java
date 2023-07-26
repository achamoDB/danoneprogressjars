// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Color;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.UserControl$ClassInfo;

public class SplashWindow$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo frame3D;
    public static final PropertyInfo checkUserAction;
    public static final PropertyInfo timeout;
    public static final PropertyInfo image;
    public static final PropertyInfo transparentColor;
    public static final PropertyInfo autoTransparent;
    private static Class \u025c;
    private static Class \u01a5;
    private static Class \u0164;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        frame3D = new PropertyInfo((SplashWindow$ClassInfo.\u025c != null) ? SplashWindow$ClassInfo.\u025c : (SplashWindow$ClassInfo.\u025c = \u00c6("com.mim.wfc.ui.SplashWindow")), "frame3D", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Draws a 3D frame around the window"));
        checkUserAction = new PropertyInfo((SplashWindow$ClassInfo.\u025c != null) ? SplashWindow$ClassInfo.\u025c : (SplashWindow$ClassInfo.\u025c = \u00c6("com.mim.wfc.ui.SplashWindow")), "checkUserAction", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Removes the window when it is be clicked"));
        timeout = new PropertyInfo((SplashWindow$ClassInfo.\u025c != null) ? SplashWindow$ClassInfo.\u025c : (SplashWindow$ClassInfo.\u025c = \u00c6("com.mim.wfc.ui.SplashWindow")), "timeout", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(3000)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Time in milliseconds the splash screen will be shown"));
        image = new PropertyInfo((SplashWindow$ClassInfo.\u025c != null) ? SplashWindow$ClassInfo.\u025c : (SplashWindow$ClassInfo.\u025c = \u00c6("com.mim.wfc.ui.SplashWindow")), "image", (SplashWindow$ClassInfo.\u01a5 != null) ? SplashWindow$ClassInfo.\u01a5 : (SplashWindow$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the splash window"));
        transparentColor = new PropertyInfo((SplashWindow$ClassInfo.\u025c != null) ? SplashWindow$ClassInfo.\u025c : (SplashWindow$ClassInfo.\u025c = \u00c6("com.mim.wfc.ui.SplashWindow")), "transparentColor", (SplashWindow$ClassInfo.\u0164 != null) ? SplashWindow$ClassInfo.\u0164 : (SplashWindow$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Color(192, 192, 192)), (MemberAttribute)new DescriptionAttribute("Specifies the transparent color of the image; this property is ignored if autoTransparent is set to true"));
        autoTransparent = new PropertyInfo((SplashWindow$ClassInfo.\u025c != null) ? SplashWindow$ClassInfo.\u025c : (SplashWindow$ClassInfo.\u025c = \u00c6("com.mim.wfc.ui.SplashWindow")), "autoTransparent", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("When set to true, the color of the left upper pixel of the image is used as transparent color"));
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
        properties.add(SplashWindow$ClassInfo.frame3D);
        properties.add(SplashWindow$ClassInfo.timeout);
        properties.add(SplashWindow$ClassInfo.checkUserAction);
        properties.add(SplashWindow$ClassInfo.image);
        properties.add(SplashWindow$ClassInfo.autoTransparent);
        properties.add(SplashWindow$ClassInfo.transparentColor);
    }
}
