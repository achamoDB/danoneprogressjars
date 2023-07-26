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

public class PrintDialog$ClassInfo extends Component$ClassInfo
{
    public static final PropertyInfo copies;
    public static final PropertyInfo rangeType;
    public static final PropertyInfo collate;
    public static final PropertyInfo printToFile;
    public static final PropertyInfo networkButton;
    private static Class \u0123;
    private static Class \u0124;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        copies = new PropertyInfo((PrintDialog$ClassInfo.\u0123 != null) ? PrintDialog$ClassInfo.\u0123 : (PrintDialog$ClassInfo.\u0123 = \u00c6("com.mim.wfc.print.PrintDialog")), "copies", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The number of copies to be printed"));
        rangeType = new PropertyInfo((PrintDialog$ClassInfo.\u0123 != null) ? PrintDialog$ClassInfo.\u0123 : (PrintDialog$ClassInfo.\u0123 = \u00c6("com.mim.wfc.print.PrintDialog")), "rangeType", (PrintDialog$ClassInfo.\u0124 != null) ? PrintDialog$ClassInfo.\u0124 : (PrintDialog$ClassInfo.\u0124 = \u00c6("com.mim.wfc.print.PrintRangeType")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The range type preference"));
        collate = new PropertyInfo((PrintDialog$ClassInfo.\u0123 != null) ? PrintDialog$ClassInfo.\u0123 : (PrintDialog$ClassInfo.\u0123 = \u00c6("com.mim.wfc.print.PrintDialog")), "collate", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("The collation preference"));
        printToFile = new PropertyInfo((PrintDialog$ClassInfo.\u0123 != null) ? PrintDialog$ClassInfo.\u0123 : (PrintDialog$ClassInfo.\u0123 = \u00c6("com.mim.wfc.print.PrintDialog")), "printToFile", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("The state of the 'Print to file' check box"));
        networkButton = new PropertyInfo((PrintDialog$ClassInfo.\u0123 != null) ? PrintDialog$ClassInfo.\u0123 : (PrintDialog$ClassInfo.\u0123 = \u00c6("com.mim.wfc.print.PrintDialog")), "networkButton", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("The enable state of the network button"));
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
        properties.add(PrintDialog$ClassInfo.copies);
        properties.add(PrintDialog$ClassInfo.rangeType);
        properties.add(PrintDialog$ClassInfo.collate);
        properties.add(PrintDialog$ClassInfo.printToFile);
        properties.add(PrintDialog$ClassInfo.networkButton);
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
