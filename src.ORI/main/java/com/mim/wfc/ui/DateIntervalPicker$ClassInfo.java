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

public class DateIntervalPicker$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo fromValue;
    public static final PropertyInfo toValue;
    public static final PropertyInfo pastButtons;
    public static final PropertyInfo futureButtons;
    public static final PropertyInfo presentButtons;
    public static final PropertyInfo presentStyle;
    public static final PropertyInfo workDay;
    public static final PropertyInfo allowNullValues;
    private static Class \u03b8;
    private static Class \u03b9;
    private static Class \u03b7;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
    }
    
    static {
        fromValue = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "fromValue", (DateIntervalPicker$ClassInfo.\u03b9 != null) ? DateIntervalPicker$ClassInfo.\u03b9 : (DateIntervalPicker$ClassInfo.\u03b9 = \u00c6("com.ms.wfc.app.Time")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The time value of the From control"));
        toValue = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "toValue", (DateIntervalPicker$ClassInfo.\u03b9 != null) ? DateIntervalPicker$ClassInfo.\u03b9 : (DateIntervalPicker$ClassInfo.\u03b9 = \u00c6("com.ms.wfc.app.Time")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("The time value of the To control"));
        pastButtons = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "pastButtons", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Display buttons for selection of timer intervals in the past"));
        futureButtons = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "futureButtons", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Display buttons for selection of timer intervals in the future"));
        presentButtons = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "presentButtons", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Display buttons for selection of timer intervals in the present (see presentStyle property)"));
        presentStyle = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "presentStyle", (DateIntervalPicker$ClassInfo.\u03b7 != null) ? DateIntervalPicker$ClassInfo.\u03b7 : (DateIntervalPicker$ClassInfo.\u03b7 = \u00c6("com.mim.wfc.ui.DateIntervalPickerPresentStyle")), (MemberAttribute)new DefaultValueAttribute((Object)new Integer(2)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The buttons for selection of dates in the present will select\nPAST: Dates from begin of week/month/year up to now\nWHOLE: Whole week/month/year\nFUTURE: Dates from now up to end of week/month/year"));
        workDay = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "workDay", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("If set to true, the week-oriented functions will consider Monday to Friday only"));
        allowNullValues = new PropertyInfo((DateIntervalPicker$ClassInfo.\u03b8 != null) ? DateIntervalPicker$ClassInfo.\u03b8 : (DateIntervalPicker$ClassInfo.\u03b8 = \u00c6("com.mim.wfc.ui.DateIntervalPicker")), "allowNullValues", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("If set to true, displays a check box inside the date controls"));
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
        properties.add(DateIntervalPicker$ClassInfo.fromValue);
        properties.add(DateIntervalPicker$ClassInfo.toValue);
        properties.add(DateIntervalPicker$ClassInfo.pastButtons);
        properties.add(DateIntervalPicker$ClassInfo.futureButtons);
        properties.add(DateIntervalPicker$ClassInfo.presentButtons);
        properties.add(DateIntervalPicker$ClassInfo.presentStyle);
        properties.add(DateIntervalPicker$ClassInfo.workDay);
        properties.add(DateIntervalPicker$ClassInfo.allowNullValues);
        properties.add(new PropertyInfo(Control$ClassInfo.text, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
}
