// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Panel$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.BindableAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.ICustomizer;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;

public class Toolbox$ClassInfo extends ViewPanel$ClassInfo
{
    public static final PropertyInfo itemList;
    public static final PropertyInfo selectedIndex;
    public static final PropertyInfo enableReorder;
    public static final PropertyInfo itemSize;
    public static final PropertyInfo itemBorderSize;
    public static final PropertyInfo textDistance;
    public static final PropertyInfo borderSize;
    public static final PropertyInfo selectionMode;
    public static final PropertyInfo alignment;
    public static final PropertyInfo singleColumn;
    public static final PropertyInfo smallHover;
    public static final EventInfo itemClicked;
    public static final EventInfo itemReleased;
    public static final EventInfo itemCurrent;
    private static Class \u0288;
    private static Class \u0289;
    private static Class \u0120;
    private static Class \u028a;
    private static Class \u0287;
    private static Class \u00ec;
    
    public ICustomizer getCustomizer(final Object o) {
        return (ICustomizer)new Toolbox$Customizer((Toolbox)o);
    }
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(Toolbox$ClassInfo.itemClicked);
        events.add(Toolbox$ClassInfo.itemReleased);
        events.add(Toolbox$ClassInfo.itemCurrent);
    }
    
    public String getDefaultEventName() {
        return new String("itemClicked");
    }
    
    static {
        itemList = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "itemList", (Toolbox$ClassInfo.\u0289 != null) ? Toolbox$ClassInfo.\u0289 : (Toolbox$ClassInfo.\u0289 = \u00c6("[Lcom.mim.wfc.ui.ToolboxItem;")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("List of tool box items"));
        selectedIndex = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "selectedIndex", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(-1)), (MemberAttribute)BindableAttribute.YES, (MemberAttribute)new DescriptionAttribute("Index of the selected item or -1"));
        enableReorder = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "enableReorder", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Enable drag and drop-reorder of the items"));
        itemSize = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "itemSize", (Toolbox$ClassInfo.\u0120 != null) ? Toolbox$ClassInfo.\u0120 : (Toolbox$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Point(40, 80)), (MemberAttribute)new DescriptionAttribute("Size of an item rectangle (including space for image and text)"));
        itemBorderSize = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "itemBorderSize", (Toolbox$ClassInfo.\u0120 != null) ? Toolbox$ClassInfo.\u0120 : (Toolbox$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Point(4, 4)), (MemberAttribute)new DescriptionAttribute("Border size of a tool box item"));
        textDistance = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "textDistance", (Toolbox$ClassInfo.\u0120 != null) ? Toolbox$ClassInfo.\u0120 : (Toolbox$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Point(4, 4)), (MemberAttribute)new DescriptionAttribute("Distance between item image and item text"));
        borderSize = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "borderSize", (Toolbox$ClassInfo.\u0120 != null) ? Toolbox$ClassInfo.\u0120 : (Toolbox$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Point(4, 4)), (MemberAttribute)new DescriptionAttribute("Border size of the control"));
        selectionMode = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "selectionMode", (Toolbox$ClassInfo.\u028a != null) ? Toolbox$ClassInfo.\u028a : (Toolbox$ClassInfo.\u028a = \u00c6("com.mim.wfc.ui.ToolboxSelectionMode")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The type of the button selection"));
        alignment = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "alignment", (Toolbox$ClassInfo.\u0287 != null) ? Toolbox$ClassInfo.\u0287 : (Toolbox$ClassInfo.\u0287 = \u00c6("com.mim.wfc.ui.ToolboxAlignStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(3)), (MemberAttribute)new DescriptionAttribute("Displays the item's text to the left, to the right or centered below the image"));
        singleColumn = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "singleColumn", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Single column mode"));
        smallHover = new PropertyInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "smallHover", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Hover button area covers the image only if set to true, otherwise the entire tool box item"));
        itemClicked = new EventInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "itemClicked", (Toolbox$ClassInfo.\u00ec != null) ? Toolbox$ClassInfo.\u00ec : (Toolbox$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The item was clicked"));
        itemReleased = new EventInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "itemReleased", (Toolbox$ClassInfo.\u00ec != null) ? Toolbox$ClassInfo.\u00ec : (Toolbox$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("An item was released"));
        itemCurrent = new EventInfo((Toolbox$ClassInfo.\u0288 != null) ? Toolbox$ClassInfo.\u0288 : (Toolbox$ClassInfo.\u0288 = \u00c6("com.mim.wfc.ui.Toolbox")), "itemCurrent", (Toolbox$ClassInfo.\u00ec != null) ? Toolbox$ClassInfo.\u00ec : (Toolbox$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The current item changed"));
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
        properties.add(Toolbox$ClassInfo.itemList);
        properties.add(Toolbox$ClassInfo.selectedIndex);
        properties.add(Toolbox$ClassInfo.enableReorder);
        properties.add(Toolbox$ClassInfo.itemSize);
        properties.add(Toolbox$ClassInfo.itemBorderSize);
        properties.add(Toolbox$ClassInfo.textDistance);
        properties.add(Toolbox$ClassInfo.borderSize);
        properties.add(Toolbox$ClassInfo.selectionMode);
        properties.add(Toolbox$ClassInfo.singleColumn);
        properties.add(Toolbox$ClassInfo.alignment);
        properties.add(Toolbox$ClassInfo.smallHover);
        properties.add(new PropertyInfo(Panel$ClassInfo.tabStop, (MemberAttribute)DefaultValueAttribute.TRUE));
        properties.add(new PropertyInfo(Panel$ClassInfo.borderStyle, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(2))));
        properties.add(new PropertyInfo(Panel$ClassInfo.text, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(ViewPanel$ClassInfo.mode, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(ViewPanel$ClassInfo.zoomMode, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(ViewPanel$ClassInfo.zoomFactor, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(ViewPanel$ClassInfo.doubleBuffer, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(ViewPanel$ClassInfo.margin, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(ViewPanel$ClassInfo.docSize, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
