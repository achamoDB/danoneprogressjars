// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.UserControl$ClassInfo;

public class HyperLink$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo URL;
    public static final PropertyInfo textAlign;
    private static Class \u03c0;
    private static Class \u00e8;
    private static Class \u01bd;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        URL = new PropertyInfo((HyperLink$ClassInfo.\u03c0 != null) ? HyperLink$ClassInfo.\u03c0 : (HyperLink$ClassInfo.\u03c0 = \u00c6("com.mim.wfc.ui.HyperLink")), "URL", (HyperLink$ClassInfo.\u00e8 != null) ? HyperLink$ClassInfo.\u00e8 : (HyperLink$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The URL to jump to if the control is clicked."));
        textAlign = new PropertyInfo((HyperLink$ClassInfo.\u03c0 != null) ? HyperLink$ClassInfo.\u03c0 : (HyperLink$ClassInfo.\u03c0 = \u00c6("com.mim.wfc.ui.HyperLink")), "textAlign", (HyperLink$ClassInfo.\u01bd != null) ? HyperLink$ClassInfo.\u01bd : (HyperLink$ClassInfo.\u01bd = \u00c6("com.ms.wfc.ui.HorizontalAlignment")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Controls the alignment of the control's text"));
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
        properties.add(HyperLink$ClassInfo.URL);
        properties.add(HyperLink$ClassInfo.textAlign);
        properties.add(new PropertyInfo(Control$ClassInfo.cursor, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
}
