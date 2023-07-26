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
import com.ms.wfc.core.Component$ClassInfo;

public class BrowseDialog$ClassInfo extends Component$ClassInfo
{
    public static final PropertyInfo title;
    public static final PropertyInfo startDirectory;
    private static Class \u0388;
    private static Class \u00e8;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        title = new PropertyInfo((BrowseDialog$ClassInfo.\u0388 != null) ? BrowseDialog$ClassInfo.\u0388 : (BrowseDialog$ClassInfo.\u0388 = \u00c6("com.mim.wfc.ui.BrowseDialog")), "title", (BrowseDialog$ClassInfo.\u00e8 != null) ? BrowseDialog$ClassInfo.\u00e8 : (BrowseDialog$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Defines the title of the dialog"));
        startDirectory = new PropertyInfo((BrowseDialog$ClassInfo.\u0388 != null) ? BrowseDialog$ClassInfo.\u0388 : (BrowseDialog$ClassInfo.\u0388 = \u00c6("com.mim.wfc.ui.BrowseDialog")), "startDirectory", (BrowseDialog$ClassInfo.\u00e8 != null) ? BrowseDialog$ClassInfo.\u00e8 : (BrowseDialog$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Sets the directory to start in"));
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
        properties.add(BrowseDialog$ClassInfo.title);
        properties.add(BrowseDialog$ClassInfo.startDirectory);
    }
    
    public String getDefaultPropertyName() {
        return new String("title");
    }
}
