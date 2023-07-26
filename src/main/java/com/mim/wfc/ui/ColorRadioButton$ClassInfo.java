// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.BindableAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.UserControl$ClassInfo;

public class ColorRadioButton$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo colorStyle;
    public static final PropertyInfo textAlign;
    public static final PropertyInfo checked;
    public static final PropertyInfo autoCheck;
    public static final EventInfo checkedChanged;
    private static Class \u039e;
    private static Class \u0397;
    private static Class \u0398;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(ColorRadioButton$ClassInfo.checkedChanged);
    }
    
    static {
        colorStyle = new PropertyInfo((ColorRadioButton$ClassInfo.\u039e != null) ? ColorRadioButton$ClassInfo.\u039e : (ColorRadioButton$ClassInfo.\u039e = \u00c6("com.mim.wfc.ui.ColorRadioButton")), "colorStyle", (ColorRadioButton$ClassInfo.\u0397 != null) ? ColorRadioButton$ClassInfo.\u0397 : (ColorRadioButton$ClassInfo.\u0397 = \u00c6("com.mim.wfc.ui.ControlColorStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Selects the color of the check mark"));
        textAlign = new PropertyInfo((ColorRadioButton$ClassInfo.\u039e != null) ? ColorRadioButton$ClassInfo.\u039e : (ColorRadioButton$ClassInfo.\u039e = \u00c6("com.mim.wfc.ui.ColorRadioButton")), "textAlign", (ColorRadioButton$ClassInfo.\u0398 != null) ? ColorRadioButton$ClassInfo.\u0398 : (ColorRadioButton$ClassInfo.\u0398 = \u00c6("com.ms.wfc.ui.LeftRightAlignment")), (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Controls the alignment of the text and the radio button"));
        checked = new PropertyInfo((ColorRadioButton$ClassInfo.\u039e != null) ? ColorRadioButton$ClassInfo.\u039e : (ColorRadioButton$ClassInfo.\u039e = \u00c6("com.mim.wfc.ui.ColorRadioButton")), "checked", (Class)Boolean.TYPE, (MemberAttribute)BindableAttribute.YES, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Controls the check state of the control"));
        autoCheck = new PropertyInfo((ColorRadioButton$ClassInfo.\u039e != null) ? ColorRadioButton$ClassInfo.\u039e : (ColorRadioButton$ClassInfo.\u039e = \u00c6("com.mim.wfc.ui.ColorRadioButton")), "autoCheck", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Determines if the state of the control changes if the control is clicked."));
        checkedChanged = new EventInfo((ColorRadioButton$ClassInfo.\u039e != null) ? ColorRadioButton$ClassInfo.\u039e : (ColorRadioButton$ClassInfo.\u039e = \u00c6("com.mim.wfc.ui.ColorRadioButton")), "checkedChanged", (ColorRadioButton$ClassInfo.\u00ec != null) ? ColorRadioButton$ClassInfo.\u00ec : (ColorRadioButton$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("Triggered when the check state changed"));
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
        properties.add(ColorRadioButton$ClassInfo.colorStyle);
        properties.add(ColorRadioButton$ClassInfo.textAlign);
        properties.add(ColorRadioButton$ClassInfo.checked);
        properties.add(ColorRadioButton$ClassInfo.autoCheck);
    }
}
