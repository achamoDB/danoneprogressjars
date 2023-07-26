// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.core.ClassInfo;

public class DataManagerField$ClassInfo extends ClassInfo
{
    public static final PropertyInfo name;
    public static final PropertyInfo boundControl;
    public static final PropertyInfo type;
    public static final PropertyInfo updatable;
    public static final PropertyInfo autoSelect;
    private static Class \u00f6;
    private static Class \u00e8;
    private static Class \u00ea;
    private static Class \u00f8;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        name = new PropertyInfo((DataManagerField$ClassInfo.\u00f6 != null) ? DataManagerField$ClassInfo.\u00f6 : (DataManagerField$ClassInfo.\u00f6 = \u00c6("com.mim.wfc.data.DataManagerField")), "name", (DataManagerField$ClassInfo.\u00e8 != null) ? DataManagerField$ClassInfo.\u00e8 : (DataManagerField$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The name of the database field\nThis name will also be used to name the object in the code."));
        boundControl = new PropertyInfo((DataManagerField$ClassInfo.\u00f6 != null) ? DataManagerField$ClassInfo.\u00f6 : (DataManagerField$ClassInfo.\u00f6 = \u00c6("com.mim.wfc.data.DataManagerField")), "boundControl", (DataManagerField$ClassInfo.\u00ea != null) ? DataManagerField$ClassInfo.\u00ea : (DataManagerField$ClassInfo.\u00ea = \u00c6("com.ms.wfc.ui.Control")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The boundControl displays the field's data"));
        type = new PropertyInfo((DataManagerField$ClassInfo.\u00f6 != null) ? DataManagerField$ClassInfo.\u00f6 : (DataManagerField$ClassInfo.\u00f6 = \u00c6("com.mim.wfc.data.DataManagerField")), "type", (DataManagerField$ClassInfo.\u00f8 != null) ? DataManagerField$ClassInfo.\u00f8 : (DataManagerField$ClassInfo.\u00f8 = \u00c6("com.mim.wfc.data.DataManagerFieldType")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Determines the type of the database field"));
        updatable = new PropertyInfo((DataManagerField$ClassInfo.\u00f6 != null) ? DataManagerField$ClassInfo.\u00f6 : (DataManagerField$ClassInfo.\u00f6 = \u00c6("com.mim.wfc.data.DataManagerField")), "updatable", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Determines if the field can be updated"));
        autoSelect = new PropertyInfo((DataManagerField$ClassInfo.\u00f6 != null) ? DataManagerField$ClassInfo.\u00f6 : (DataManagerField$ClassInfo.\u00f6 = \u00c6("com.mim.wfc.data.DataManagerField")), "autoSelect", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Determines if the field will be used in auto select queries"));
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
        properties.add(DataManagerField$ClassInfo.name);
        properties.add(DataManagerField$ClassInfo.boundControl);
        properties.add(DataManagerField$ClassInfo.type);
        properties.add(DataManagerField$ClassInfo.updatable);
        properties.add(DataManagerField$ClassInfo.autoSelect);
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
