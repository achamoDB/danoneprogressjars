// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;

public class ImageBarItem$ClassInfo extends ToolboxItem$ClassInfo
{
    public static final PropertyInfo formClassName;
    private static Class \u018f;
    private static Class \u00e8;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        formClassName = new PropertyInfo((ImageBarItem$ClassInfo.\u018f != null) ? ImageBarItem$ClassInfo.\u018f : (ImageBarItem$ClassInfo.\u018f = \u00c6("com.mim.wfc.ui.ImageBarItem")), "formClassName", (ImageBarItem$ClassInfo.\u00e8 != null) ? ImageBarItem$ClassInfo.\u00e8 : (ImageBarItem$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The item's form class name;\nthe form's title and icon will be used as the item's text and image"));
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
        properties.add(ImageBarItem$ClassInfo.formClassName);
    }
    
    public String getDefaultPropertyName() {
        return new String("text");
    }
}
