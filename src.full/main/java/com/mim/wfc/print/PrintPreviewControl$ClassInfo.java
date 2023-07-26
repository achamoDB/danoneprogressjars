// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

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
import com.ms.wfc.ui.UserControl$ClassInfo;

public class PrintPreviewControl$ClassInfo extends UserControl$ClassInfo
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
    public static final PropertyInfo printImmediate;
    public static final EventInfo drawPage;
    public static final EventInfo queryPageCount;
    public static final EventInfo pageSelected;
    public static final EventInfo close;
    public static final EventInfo pageLayoutChanged;
    private static Class \u0163;
    private static Class \u0164;
    private static Class \u0165;
    private static Class \u0120;
    private static Class \u00e8;
    private static Class \u0166;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(PrintPreviewControl$ClassInfo.drawPage);
        events.add(PrintPreviewControl$ClassInfo.queryPageCount);
        events.add(PrintPreviewControl$ClassInfo.pageSelected);
        events.add(PrintPreviewControl$ClassInfo.close);
        events.add(PrintPreviewControl$ClassInfo.pageLayoutChanged);
    }
    
    static {
        zoomFactor = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "zoomFactor", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(75)), (MemberAttribute)new DescriptionAttribute("The zoom factor of the page display in percent"));
        borderWidth = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "borderWidth", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(10)), (MemberAttribute)new DescriptionAttribute("The distance between the border of the display surface and the page display in pixel"));
        pageDistance = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "pageDistance", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(15)), (MemberAttribute)new DescriptionAttribute("The distance between two pages in the page display in pixel"));
        pageColor = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "pageColor", (PrintPreviewControl$ClassInfo.\u0164 != null) ? PrintPreviewControl$ClassInfo.\u0164 : (PrintPreviewControl$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Color(192, 192, 192)), (MemberAttribute)new DescriptionAttribute("The page background color"));
        activeFrameColor = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0165 != null) ? PrintPreviewControl$ClassInfo.\u0165 : (PrintPreviewControl$ClassInfo.\u0165 = \u00c6("com.mim.wfc.print.PrintPreviewPanel")), "activeFrameColor", (PrintPreviewControl$ClassInfo.\u0164 != null) ? PrintPreviewControl$ClassInfo.\u0164 : (PrintPreviewControl$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)Color.ACTIVECAPTION), (MemberAttribute)new DescriptionAttribute("Color of the frame of an active page"));
        pageLayout = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "pageLayout", (PrintPreviewControl$ClassInfo.\u0120 != null) ? PrintPreviewControl$ClassInfo.\u0120 : (PrintPreviewControl$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Point(1, 1)), (MemberAttribute)new DescriptionAttribute("The number of pages (x and y) to display simultaneously in the view"));
        activePage = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "activePage", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("The currently active page"));
        firstPage = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "firstPage", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("The first page to display in the view"));
        pageCount = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "pageCount", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("The number of pages"));
        xOffset = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "xOffset", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The horizontal offset of the page display"));
        yOffset = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "yOffset", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The vertical offset of the page display"));
        docTitle = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "docTitle", (PrintPreviewControl$ClassInfo.\u00e8 != null) ? PrintPreviewControl$ClassInfo.\u00e8 : (PrintPreviewControl$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Sets the documents title for printing"));
        printImmediate = new PropertyInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "printImmediate", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Prevents display of the print dialog when printing"));
        drawPage = new EventInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "drawPage", (PrintPreviewControl$ClassInfo.\u0166 != null) ? PrintPreviewControl$ClassInfo.\u0166 : (PrintPreviewControl$ClassInfo.\u0166 = \u00c6("com.mim.wfc.print.PrintPreviewEventHandler")), (MemberAttribute)new DescriptionAttribute("A page needs to be drawn"));
        queryPageCount = new EventInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "queryPageCount", (PrintPreviewControl$ClassInfo.\u0166 != null) ? PrintPreviewControl$ClassInfo.\u0166 : (PrintPreviewControl$ClassInfo.\u0166 = \u00c6("com.mim.wfc.print.PrintPreviewEventHandler")), (MemberAttribute)new DescriptionAttribute("The page count needs to be retrieved"));
        pageSelected = new EventInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "pageSelected", (PrintPreviewControl$ClassInfo.\u00ec != null) ? PrintPreviewControl$ClassInfo.\u00ec : (PrintPreviewControl$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("A page was selected"));
        close = new EventInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "close", (PrintPreviewControl$ClassInfo.\u00ec != null) ? PrintPreviewControl$ClassInfo.\u00ec : (PrintPreviewControl$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The print preview was closed"));
        pageLayoutChanged = new EventInfo((PrintPreviewControl$ClassInfo.\u0163 != null) ? PrintPreviewControl$ClassInfo.\u0163 : (PrintPreviewControl$ClassInfo.\u0163 = \u00c6("com.mim.wfc.print.PrintPreviewControl")), "pageLayoutChanged", (PrintPreviewControl$ClassInfo.\u00ec != null) ? PrintPreviewControl$ClassInfo.\u00ec : (PrintPreviewControl$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The page layout (number of pages in the view or zoom factor) has changed"));
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
        properties.add(PrintPreviewControl$ClassInfo.zoomFactor);
        properties.add(PrintPreviewControl$ClassInfo.borderWidth);
        properties.add(PrintPreviewControl$ClassInfo.pageDistance);
        properties.add(PrintPreviewControl$ClassInfo.pageColor);
        properties.add(PrintPreviewControl$ClassInfo.activeFrameColor);
        properties.add(PrintPreviewControl$ClassInfo.pageCount);
        properties.add(PrintPreviewControl$ClassInfo.pageLayout);
        properties.add(PrintPreviewControl$ClassInfo.activePage);
        properties.add(PrintPreviewControl$ClassInfo.firstPage);
        properties.add(PrintPreviewControl$ClassInfo.xOffset);
        properties.add(PrintPreviewControl$ClassInfo.yOffset);
        properties.add(PrintPreviewControl$ClassInfo.docTitle);
        properties.add(PrintPreviewControl$ClassInfo.printImmediate);
    }
}
