// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.LocalizableAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Form$ClassInfo;

public class MsgBox$ClassInfo extends Form$ClassInfo
{
    public static final PropertyInfo maxWidth;
    public static final PropertyInfo buttonWidth;
    public static final PropertyInfo useImageButtons;
    public static final PropertyInfo okImage;
    public static final PropertyInfo cancelImage;
    public static final PropertyInfo yesImage;
    public static final PropertyInfo noImage;
    public static final PropertyInfo retryImage;
    public static final PropertyInfo ignoreImage;
    public static final PropertyInfo abortImage;
    public static final PropertyInfo cancelYesNoImage;
    private static Class \u01a7;
    private static Class \u01a5;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        maxWidth = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "maxWidth", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(400)), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Maximum width of dialog"));
        buttonWidth = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "buttonWidth", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Width of control bar button"));
        useImageButtons = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "useImageButtons", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("If set to true, the control bar buttons will have text and images"));
        okImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "okImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Ok' button"));
        cancelImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "cancelImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Cancel' button"));
        yesImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "yesImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Yes' button"));
        noImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "noImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'No' button"));
        retryImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "retryImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Retry' button"));
        ignoreImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "ignoreImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Ignore' button"));
        abortImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "abortImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Abort' button"));
        cancelYesNoImage = new PropertyInfo((MsgBox$ClassInfo.\u01a7 != null) ? MsgBox$ClassInfo.\u01a7 : (MsgBox$ClassInfo.\u01a7 = \u00c6("com.mim.wfc.ui.MsgBox")), "cancelYesNoImage", (MsgBox$ClassInfo.\u01a5 != null) ? MsgBox$ClassInfo.\u01a5 : (MsgBox$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'CancelYesNo' button"));
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
        properties.add(MsgBox$ClassInfo.okImage);
        properties.add(MsgBox$ClassInfo.cancelImage);
        properties.add(MsgBox$ClassInfo.yesImage);
        properties.add(MsgBox$ClassInfo.noImage);
        properties.add(MsgBox$ClassInfo.retryImage);
        properties.add(MsgBox$ClassInfo.ignoreImage);
        properties.add(MsgBox$ClassInfo.abortImage);
        properties.add(MsgBox$ClassInfo.cancelYesNoImage);
        properties.add(MsgBox$ClassInfo.useImageButtons);
        properties.add(MsgBox$ClassInfo.buttonWidth);
        properties.add(MsgBox$ClassInfo.maxWidth);
    }
}
