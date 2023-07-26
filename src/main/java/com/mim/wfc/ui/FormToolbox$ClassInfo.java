// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.PropertyInfo;

public class FormToolbox$ClassInfo extends Toolbox$ClassInfo
{
    public static final PropertyInfo formItemList;
    public static final PropertyInfo singleColumn;
    private static Class \u03ba;
    private static Class \u03bb;
    
    static {
        formItemList = new PropertyInfo((FormToolbox$ClassInfo.\u03ba != null) ? FormToolbox$ClassInfo.\u03ba : (FormToolbox$ClassInfo.\u03ba = \u00c6("com.mim.wfc.ui.FormToolbox")), "formItemList", (FormToolbox$ClassInfo.\u03bb != null) ? FormToolbox$ClassInfo.\u03bb : (FormToolbox$ClassInfo.\u03bb = \u00c6("[Lcom.mim.wfc.ui.FormToolboxItem;")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("List of image bar items"));
        singleColumn = new PropertyInfo((FormToolbox$ClassInfo.\u03ba != null) ? FormToolbox$ClassInfo.\u03ba : (FormToolbox$ClassInfo.\u03ba = \u00c6("com.mim.wfc.ui.FormToolbox")), "singleColumn", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Single column mode"));
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
        properties.add(FormToolbox$ClassInfo.formItemList);
        properties.add(FormToolbox$ClassInfo.singleColumn);
        properties.add(new PropertyInfo(Toolbox$ClassInfo.itemList, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
}
