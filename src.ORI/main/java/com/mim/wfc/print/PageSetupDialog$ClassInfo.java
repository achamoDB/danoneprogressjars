// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.print;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.core.Component$ClassInfo;

public class PageSetupDialog$ClassInfo extends Component$ClassInfo
{
    public static final PropertyInfo paperControls;
    public static final PropertyInfo orientationControls;
    public static final PropertyInfo marginControls;
    public static final PropertyInfo printerButton;
    public static final PropertyInfo networkButton;
    private static Class \u0116;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        paperControls = new PropertyInfo((PageSetupDialog$ClassInfo.\u0116 != null) ? PageSetupDialog$ClassInfo.\u0116 : (PageSetupDialog$ClassInfo.\u0116 = \u00c6("com.mim.wfc.print.PageSetupDialog")), "paperControls", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Enables or disabes the paper controls such as paper size and paper source"));
        orientationControls = new PropertyInfo((PageSetupDialog$ClassInfo.\u0116 != null) ? PageSetupDialog$ClassInfo.\u0116 : (PageSetupDialog$ClassInfo.\u0116 = \u00c6("com.mim.wfc.print.PageSetupDialog")), "orientationControls", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Enables or disables the page orientation controls"));
        marginControls = new PropertyInfo((PageSetupDialog$ClassInfo.\u0116 != null) ? PageSetupDialog$ClassInfo.\u0116 : (PageSetupDialog$ClassInfo.\u0116 = \u00c6("com.mim.wfc.print.PageSetupDialog")), "marginControls", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Enables or disables the page margin controls"));
        printerButton = new PropertyInfo((PageSetupDialog$ClassInfo.\u0116 != null) ? PageSetupDialog$ClassInfo.\u0116 : (PageSetupDialog$ClassInfo.\u0116 = \u00c6("com.mim.wfc.print.PageSetupDialog")), "printerButton", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Enables or disables the printer button"));
        networkButton = new PropertyInfo((PageSetupDialog$ClassInfo.\u0116 != null) ? PageSetupDialog$ClassInfo.\u0116 : (PageSetupDialog$ClassInfo.\u0116 = \u00c6("com.mim.wfc.print.PageSetupDialog")), "networkButton", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("The enable state of the network button"));
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
        properties.add(PageSetupDialog$ClassInfo.marginControls);
        properties.add(PageSetupDialog$ClassInfo.orientationControls);
        properties.add(PageSetupDialog$ClassInfo.paperControls);
        properties.add(PageSetupDialog$ClassInfo.printerButton);
        properties.add(PageSetupDialog$ClassInfo.networkButton);
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
