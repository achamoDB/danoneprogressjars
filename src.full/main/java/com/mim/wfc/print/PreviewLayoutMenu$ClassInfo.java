// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.ui.Point;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Form$ClassInfo;

public class PreviewLayoutMenu$ClassInfo extends Form$ClassInfo
{
    public static final PropertyInfo pages;
    public static final PropertyInfo maxPages;
    private static Class \u011f;
    private static Class \u0120;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        pages = new PropertyInfo((PreviewLayoutMenu$ClassInfo.\u011f != null) ? PreviewLayoutMenu$ClassInfo.\u011f : (PreviewLayoutMenu$ClassInfo.\u011f = \u00c6("com.mim.wfc.print.PreviewLayoutMenu")), "pages", (PreviewLayoutMenu$ClassInfo.\u0120 != null) ? PreviewLayoutMenu$ClassInfo.\u0120 : (PreviewLayoutMenu$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Defines the number of pages in x and y direction"));
        maxPages = new PropertyInfo((PreviewLayoutMenu$ClassInfo.\u011f != null) ? PreviewLayoutMenu$ClassInfo.\u011f : (PreviewLayoutMenu$ClassInfo.\u011f = \u00c6("com.mim.wfc.print.PreviewLayoutMenu")), "maxPages", (PreviewLayoutMenu$ClassInfo.\u0120 != null) ? PreviewLayoutMenu$ClassInfo.\u0120 : (PreviewLayoutMenu$ClassInfo.\u0120 = \u00c6("com.ms.wfc.ui.Point")), (MemberAttribute)new DefaultValueAttribute((Object)new Point(10, 4)), (MemberAttribute)new DescriptionAttribute("Defines the maximum number of pages in x and y direction"));
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
        properties.add(PreviewLayoutMenu$ClassInfo.pages);
        properties.add(PreviewLayoutMenu$ClassInfo.maxPages);
    }
}
