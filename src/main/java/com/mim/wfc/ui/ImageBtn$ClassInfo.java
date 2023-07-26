// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Color;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Button$ClassInfo;

public class ImageBtn$ClassInfo extends Button$ClassInfo
{
    public static final PropertyInfo autoTransparent;
    public static final PropertyInfo toggleButton;
    public static final PropertyInfo toggleState;
    public static final PropertyInfo repeatRate;
    public static final PropertyInfo alignment;
    public static final PropertyInfo borderDisplay;
    public static final PropertyInfo xDistance;
    public static final PropertyInfo image;
    public static final PropertyInfo hoverImage;
    public static final PropertyInfo transparentColor;
    public static final PropertyInfo textColor;
    public static final PropertyInfo dialogResult;
    private static Class \u01a4;
    private static Class \u01a3;
    private static Class \u01a2;
    private static Class \u01a5;
    private static Class \u0164;
    private static Class \u01a6;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(new EventInfo(Control$ClassInfo.doubleClick, (MemberAttribute)BrowsableAttribute.NO));
    }
    
    static {
        autoTransparent = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "autoTransparent", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("When set to true, the color of the left upper pixel of the image is used as transparent color"));
        toggleButton = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "toggleButton", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Toggle the button state when the button gets pressed"));
        toggleState = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "state", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("State of the button if it is a toggle button"));
        repeatRate = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "repeatRate", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Repeat rate of the button click event per second if the user keeps the button depressed"));
        alignment = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "alignment", (ImageBtn$ClassInfo.\u01a3 != null) ? ImageBtn$ClassInfo.\u01a3 : (ImageBtn$ClassInfo.\u01a3 = \u00c6("com.mim.wfc.ui.ImageBtnAlignStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(2)), (MemberAttribute)new DescriptionAttribute("Displays the item's text to the left, to the right or centered below the image"));
        borderDisplay = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "borderDisplay", (ImageBtn$ClassInfo.\u01a2 != null) ? ImageBtn$ClassInfo.\u01a2 : (ImageBtn$ClassInfo.\u01a2 = \u00c6("com.mim.wfc.ui.ImageBtnBorderDisplay")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Controls when to display the button border"));
        xDistance = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "xDistance", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(10)), (MemberAttribute)new DescriptionAttribute("Horizontal distance between button border and image and image and text with leftmost or rightmost alignment"));
        image = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "image", (ImageBtn$ClassInfo.\u01a5 != null) ? ImageBtn$ClassInfo.\u01a5 : (ImageBtn$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the button"));
        hoverImage = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "hoverImage", (ImageBtn$ClassInfo.\u01a5 != null) ? ImageBtn$ClassInfo.\u01a5 : (ImageBtn$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the button when hovered"));
        transparentColor = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "transparentColor", (ImageBtn$ClassInfo.\u0164 != null) ? ImageBtn$ClassInfo.\u0164 : (ImageBtn$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Color(192, 192, 192)), (MemberAttribute)new DescriptionAttribute("Specifies the transparent color; this property is ignored if autoTransparent is set to true"));
        textColor = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "textColor", (ImageBtn$ClassInfo.\u0164 != null) ? ImageBtn$ClassInfo.\u0164 : (ImageBtn$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Text (foreground) color"));
        dialogResult = new PropertyInfo((ImageBtn$ClassInfo.\u01a4 != null) ? ImageBtn$ClassInfo.\u01a4 : (ImageBtn$ClassInfo.\u01a4 = \u00c6("com.mim.wfc.ui.ImageBtn")), "dialogResult", (ImageBtn$ClassInfo.\u01a6 != null) ? ImageBtn$ClassInfo.\u01a6 : (ImageBtn$ClassInfo.\u01a6 = \u00c6("com.ms.wfc.ui.DialogResult")), (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The dialog result that will be set when in a model form by pressing the button."));
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
        properties.add(ImageBtn$ClassInfo.toggleButton);
        properties.add(ImageBtn$ClassInfo.toggleState);
        properties.add(ImageBtn$ClassInfo.repeatRate);
        properties.add(ImageBtn$ClassInfo.alignment);
        properties.add(ImageBtn$ClassInfo.borderDisplay);
        properties.add(ImageBtn$ClassInfo.xDistance);
        properties.add(ImageBtn$ClassInfo.image);
        properties.add(ImageBtn$ClassInfo.hoverImage);
        properties.add(ImageBtn$ClassInfo.autoTransparent);
        properties.add(ImageBtn$ClassInfo.transparentColor);
        properties.add(ImageBtn$ClassInfo.textColor);
        properties.add(ImageBtn$ClassInfo.dialogResult);
        properties.add(new PropertyInfo(Control$ClassInfo.backColor, (MemberAttribute)BrowsableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.foreColor, (MemberAttribute)BrowsableAttribute.NO));
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
