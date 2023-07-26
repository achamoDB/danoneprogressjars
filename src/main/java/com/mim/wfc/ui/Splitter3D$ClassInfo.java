// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Splitter$ClassInfo;

public class Splitter3D$ClassInfo extends Splitter$ClassInfo
{
    public static final PropertyInfo borderStyle;
    private static Class \u025f;
    private static Class \u025e;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        borderStyle = new PropertyInfo((Splitter3D$ClassInfo.\u025f != null) ? Splitter3D$ClassInfo.\u025f : (Splitter3D$ClassInfo.\u025f = \u00c6("com.mim.wfc.ui.Splitter3D")), "borderStyle", (Splitter3D$ClassInfo.\u025e != null) ? Splitter3D$ClassInfo.\u025e : (Splitter3D$ClassInfo.\u025e = \u00c6("com.mim.wfc.ui.Splitter3DBorderStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(2)), (MemberAttribute)new DescriptionAttribute("Selects the border style of the splitter bar"));
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
        properties.add(Splitter3D$ClassInfo.borderStyle);
    }
}
