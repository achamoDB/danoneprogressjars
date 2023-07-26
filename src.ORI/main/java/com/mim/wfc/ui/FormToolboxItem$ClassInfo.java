// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.PropertyInfo;

public class FormToolboxItem$ClassInfo extends ToolboxItem$ClassInfo
{
    public static final PropertyInfo formClassName;
    private static Class \u03bc;
    private static Class \u00e8;
    
    static {
        formClassName = new PropertyInfo((FormToolboxItem$ClassInfo.\u03bc != null) ? FormToolboxItem$ClassInfo.\u03bc : (FormToolboxItem$ClassInfo.\u03bc = \u00c6("com.mim.wfc.ui.FormToolboxItem")), "formClassName", (FormToolboxItem$ClassInfo.\u00e8 != null) ? FormToolboxItem$ClassInfo.\u00e8 : (FormToolboxItem$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The item's form class name;\nthe form's title and icon will be used as the item's text and image"));
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
        properties.add(FormToolboxItem$ClassInfo.formClassName);
    }
}
