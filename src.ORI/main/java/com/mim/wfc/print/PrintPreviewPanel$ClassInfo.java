// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Point;
import com.ms.wfc.ui.Color;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Panel$ClassInfo;

public class PrintPreviewPanel$ClassInfo extends Panel$ClassInfo
{
    public static final PropertyInfo zoomFactor;
    public static final PropertyInfo borderWidth;
    public static final PropertyInfo pageDistance;
    public static final PropertyInfo pageColor;
    public static final PropertyInfo activeFrameColor;
    public static final PropertyInfo pageLayout;
    public static final PropertyInfo activePage;
    public static final PropertyInfo firstPage;
    public static final PropertyInfo pageCount;
    public static final PropertyInfo xOffset;
    public static final PropertyInfo yOffset;
    public static final PropertyInfo docTitle;
    public static final EventInfo drawPage;
    public static final EventInfo queryPageCount;
    public static final EventInfo pageSelected;
    public static final EventInfo pageLayoutChanged;
    private static Class \u0165;
    private static Class \u0164;
    private static Class \u0120;
    private static Class \u00e8;
    private static Class \u0166;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(PrintPreviewPanel$ClassInfo.drawPage);
        events.add(PrintPreviewPanel$ClassInfo.queryPageCount);
        events.add(PrintPreviewPanel$ClassInfo.pageSelected);
        events.add(PrintPreviewPanel$ClassInfo.pageLayoutChanged);
    }
    
    static {
        zoomFactor = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "zoomFactor", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(75)), (MemberAttribute)new DescriptionAttribute("The zoom factor of the page display in percent"));
        borderWidth = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "borderWidth", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(10)), (MemberAttribute)new DescriptionAttribute("The distance between the control's border and the page display in pixel"));
        pageDistance = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "pageDistance", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(15)), (MemberAttribute)new DescriptionAttribute("The distance between two pages in the page display in pixel"));
        pageColor = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "pageColor", (PrintPreviewPanel$ClassInfo.\u0164 != null) ? PrintPreviewPanel$ClassInfo.\u0164 : (PrintPreviewPanel$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Color(192, 192, 192)), (MemberAttribute)new DescriptionAttribute("The page background color"));
        activeFrameColor = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "activeFrameColor", (PrintPreviewPanel$ClassInfo.\u0164 != null) ? PrintPreviewPanel$ClassInfo.\u0164 : (PrintPreviewPanel$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)Color.ACTIVECAPTION), (MemberAttribute)new DescriptionAttribute("Color of the frame of an active page"));
        pageLayout = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "pageLayout", (PrintPreviewPanel$ClassInfo.\u0120 != null) ? PrintPreviewPanel$ClassInfo.\u0120 : (PrintPreviewPanel$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Point(1, 1)), (MemberAttribute)new DescriptionAttribute("The number of pages (x and y) to display simultaneously in the view"));
        activePage = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "activePage", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("The currently active page"));
        firstPage = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "firstPage", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("The first page to display in the view"));
        pageCount = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "pageCount", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("The number of pages"));
        xOffset = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "xOffset", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The horizontal offset of the page display"));
        yOffset = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "yOffset", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The vertical offset of the page display"));
        docTitle = new PropertyInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "docTitle", (PrintPreviewPanel$ClassInfo.\u00e8 != null) ? PrintPreviewPanel$ClassInfo.\u00e8 : (PrintPreviewPanel$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Sets the documents title for printing"));
        drawPage = new EventInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "drawPage", (PrintPreviewPanel$ClassInfo.\u0166 != null) ? PrintPreviewPanel$ClassInfo.\u0166 : (PrintPreviewPanel$ClassInfo.\u0166 = \u00c6("com.mim.wfc.print.PrintPreviewEventHandler")), (MemberAttribute)new DescriptionAttribute("A page needs to be drawn"));
        queryPageCount = new EventInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "queryPageCount", (PrintPreviewPanel$ClassInfo.\u0166 != null) ? PrintPreviewPanel$ClassInfo.\u0166 : (PrintPreviewPanel$ClassInfo.\u0166 = \u00c6("com.mim.wfc.print.PrintPreviewEventHandler")), (MemberAttribute)new DescriptionAttribute("The page count needs to be retrieved"));
        pageSelected = new EventInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "pageSelected", (PrintPreviewPanel$ClassInfo.\u00ec != null) ? PrintPreviewPanel$ClassInfo.\u00ec : (PrintPreviewPanel$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("A page was selected"));
        pageLayoutChanged = new EventInfo((PrintPreviewPanel$ClassInfo.\u0165 != null) ? PrintPreviewPanel$ClassInfo.\u0165 : (PrintPreviewPanel$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "pageLayoutChanged", (PrintPreviewPanel$ClassInfo.\u00ec != null) ? PrintPreviewPanel$ClassInfo.\u00ec : (PrintPreviewPanel$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The page layout (number of pages in the view or zoom factor) has changed"));
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
        properties.add(PrintPreviewPanel$ClassInfo.zoomFactor);
        properties.add(PrintPreviewPanel$ClassInfo.borderWidth);
        properties.add(PrintPreviewPanel$ClassInfo.pageDistance);
        properties.add(PrintPreviewPanel$ClassInfo.pageColor);
        properties.add(PrintPreviewPanel$ClassInfo.activeFrameColor);
        properties.add(PrintPreviewPanel$ClassInfo.pageCount);
        properties.add(PrintPreviewPanel$ClassInfo.pageLayout);
        properties.add(PrintPreviewPanel$ClassInfo.activePage);
        properties.add(PrintPreviewPanel$ClassInfo.firstPage);
        properties.add(PrintPreviewPanel$ClassInfo.xOffset);
        properties.add(PrintPreviewPanel$ClassInfo.yOffset);
        properties.add(PrintPreviewPanel$ClassInfo.docTitle);
        properties.add(new PropertyInfo(Control$ClassInfo.text, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
}
