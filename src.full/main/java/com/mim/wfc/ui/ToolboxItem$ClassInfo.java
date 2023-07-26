// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Color;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.core.ClassInfo;

public class ToolboxItem$ClassInfo extends ClassInfo
{
    public static final PropertyInfo text;
    public static final PropertyInfo image;
    public static final PropertyInfo data;
    public static final PropertyInfo cursor;
    public static final PropertyInfo autoTransparent;
    public static final PropertyInfo transparentColor;
    public static final PropertyInfo selected;
    public static final PropertyInfo enabled;
    public static final EventInfo itemClicked;
    public static final EventInfo itemReleased;
    public static final EventInfo itemCurrent;
    private static Class \u028f;
    private static Class \u00e8;
    private static Class \u01a5;
    private static Class \u0290;
    private static Class \u0291;
    private static Class \u0164;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(ToolboxItem$ClassInfo.itemClicked);
        events.add(ToolboxItem$ClassInfo.itemReleased);
        events.add(ToolboxItem$ClassInfo.itemCurrent);
    }
    
    static {
        text = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "text", (ToolboxItem$ClassInfo.\u00e8 != null) ? ToolboxItem$ClassInfo.\u00e8 : (ToolboxItem$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The item's text"));
        image = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "image", (ToolboxItem$ClassInfo.\u01a5 != null) ? ToolboxItem$ClassInfo.\u01a5 : (ToolboxItem$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The item's image"));
        data = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "data", (ToolboxItem$ClassInfo.\u0290 != null) ? ToolboxItem$ClassInfo.\u0290 : (ToolboxItem$ClassInfo.\u0290 = \u00c6("java.lang.Object")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Application-specific item data"));
        cursor = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "cursor", (ToolboxItem$ClassInfo.\u0291 != null) ? ToolboxItem$ClassInfo.\u0291 : (ToolboxItem$ClassInfo.\u0291 = \u00c6("com.ms.wfc.ui.Cursor")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Item cursor"));
        autoTransparent = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "autoTransparent", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("When set to true, the color of the left upper pixel of the image is used as transparent color"));
        transparentColor = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "transparentColor", (ToolboxItem$ClassInfo.\u0164 != null) ? ToolboxItem$ClassInfo.\u0164 : (ToolboxItem$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Color(192, 192, 192)), (MemberAttribute)new DescriptionAttribute("Specifies the transparent color; this property is ignored if autoTransparent is set to true"));
        selected = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "selected", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("If the item is selected"));
        enabled = new PropertyInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "enabled", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("The item's enable state"));
        itemClicked = new EventInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "itemClicked", (ToolboxItem$ClassInfo.\u00ec != null) ? ToolboxItem$ClassInfo.\u00ec : (ToolboxItem$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The item was clicked"));
        itemReleased = new EventInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "itemReleased", (ToolboxItem$ClassInfo.\u00ec != null) ? ToolboxItem$ClassInfo.\u00ec : (ToolboxItem$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("An item was released"));
        itemCurrent = new EventInfo((ToolboxItem$ClassInfo.\u028f != null) ? ToolboxItem$ClassInfo.\u028f : (ToolboxItem$ClassInfo.\u028f = \u00c6("com.mim.wfc.ui.ToolboxItem")), "itemCurrent", (ToolboxItem$ClassInfo.\u00ec != null) ? ToolboxItem$ClassInfo.\u00ec : (ToolboxItem$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("An item became the current item"));
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
        properties.add(ToolboxItem$ClassInfo.text);
        properties.add(ToolboxItem$ClassInfo.image);
        properties.add(ToolboxItem$ClassInfo.data);
        properties.add(ToolboxItem$ClassInfo.cursor);
        properties.add(ToolboxItem$ClassInfo.autoTransparent);
        properties.add(ToolboxItem$ClassInfo.transparentColor);
        properties.add(ToolboxItem$ClassInfo.selected);
        properties.add(ToolboxItem$ClassInfo.enabled);
    }
    
    public String getDefaultPropertyName() {
        return new String("text");
    }
}
