// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.ICustomizer;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.UserControl$ClassInfo;

public class ImageBar$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo smallHover;
    public static final PropertyInfo alignment;
    public static final PropertyInfo imageSize;
    public static final PropertyInfo itemList;
    public static final PropertyInfo selectionMode;
    public static final EventInfo itemClicked;
    private static Class \u0188;
    private static Class \u0189;
    private static Class \u0120;
    private static Class \u018a;
    private static Class \u018b;
    private static Class \u018c;
    
    public ICustomizer getCustomizer(final Object o) {
        return (ICustomizer)new ImageBar$Customizer((ImageBar)o);
    }
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(ImageBar$ClassInfo.itemClicked);
    }
    
    static {
        smallHover = new PropertyInfo((ImageBar$ClassInfo.\u0188 != null) ? ImageBar$ClassInfo.\u0188 : (ImageBar$ClassInfo.\u0188 = \u00c6("com.mim.wfc.ui.ImageBar")), "smallHover", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Hover button area covers the image only if set to true, otherwise the entire list box item"));
        alignment = new PropertyInfo((ImageBar$ClassInfo.\u0188 != null) ? ImageBar$ClassInfo.\u0188 : (ImageBar$ClassInfo.\u0188 = \u00c6("com.mim.wfc.ui.ImageBar")), "alignment", (ImageBar$ClassInfo.\u0189 != null) ? ImageBar$ClassInfo.\u0189 : (ImageBar$ClassInfo.\u0189 = \u00c6("com.mim.wfc.ui.ImageBarAlignStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(3)), (MemberAttribute)new DescriptionAttribute("Displays the item's text to the left, to the right or centered below the image"));
        imageSize = new PropertyInfo((ImageBar$ClassInfo.\u0188 != null) ? ImageBar$ClassInfo.\u0188 : (ImageBar$ClassInfo.\u0188 = \u00c6("com.mim.wfc.ui.ImageBar")), "imageSize", (ImageBar$ClassInfo.\u0120 != null) ? ImageBar$ClassInfo.\u0120 : (ImageBar$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Point(32, 32)), (MemberAttribute)new DescriptionAttribute("The size of an item's image\nSet the size to 0;0 to use the standard icon size"));
        itemList = new PropertyInfo((ImageBar$ClassInfo.\u0188 != null) ? ImageBar$ClassInfo.\u0188 : (ImageBar$ClassInfo.\u0188 = \u00c6("com.mim.wfc.ui.ImageBar")), "itemList", (ImageBar$ClassInfo.\u018a != null) ? ImageBar$ClassInfo.\u018a : (ImageBar$ClassInfo.\u018a = \u00c6("[Lcom.mim.wfc.ui.ImageBarItem;")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The items to display in the image bar"));
        selectionMode = new PropertyInfo((ImageBar$ClassInfo.\u0188 != null) ? ImageBar$ClassInfo.\u0188 : (ImageBar$ClassInfo.\u0188 = \u00c6("com.mim.wfc.ui.ImageBar")), "selectionMode", (ImageBar$ClassInfo.\u018b != null) ? ImageBar$ClassInfo.\u018b : (ImageBar$ClassInfo.\u018b = \u00c6("com.mim.wfc.ui.ImageBarSelectionMode")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The type of the button selection"));
        itemClicked = new EventInfo((ImageBar$ClassInfo.\u0188 != null) ? ImageBar$ClassInfo.\u0188 : (ImageBar$ClassInfo.\u0188 = \u00c6("com.mim.wfc.ui.ImageBar")), "itemClicked", (ImageBar$ClassInfo.\u018c != null) ? ImageBar$ClassInfo.\u018c : (ImageBar$ClassInfo.\u018c = \u00c6("com.mim.wfc.ui.ImageBarEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("An item was clicked"));
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
        properties.add(ImageBar$ClassInfo.smallHover);
        properties.add(ImageBar$ClassInfo.alignment);
        properties.add(ImageBar$ClassInfo.imageSize);
        properties.add(ImageBar$ClassInfo.itemList);
        properties.add(ImageBar$ClassInfo.selectionMode);
        properties.add(new PropertyInfo(Control$ClassInfo.text, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
