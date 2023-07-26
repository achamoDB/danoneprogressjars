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

public class ColorCheckBox$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo colorStyle;
    public static final PropertyInfo textAlign;
    public static final PropertyInfo checkState;
    public static final PropertyInfo checked;
    public static final PropertyInfo autoCheck;
    public static final PropertyInfo threeState;
    public static final EventInfo checkedChanged;
    public static final EventInfo checkStateChanged;
    private static Class \u039a;
    private static Class \u0397;
    private static Class \u0398;
    private static Class \u0399;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(ColorCheckBox$ClassInfo.checkedChanged);
        events.add(ColorCheckBox$ClassInfo.checkStateChanged);
    }
    
    static {
        colorStyle = new PropertyInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "colorStyle", (ColorCheckBox$ClassInfo.\u0397 != null) ? ColorCheckBox$ClassInfo.\u0397 : (ColorCheckBox$ClassInfo.\u0397 = \u00c6("com.mim.wfc.ui.ControlColorStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Selects the color of the check mark"));
        textAlign = new PropertyInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "textAlign", (ColorCheckBox$ClassInfo.\u0398 != null) ? ColorCheckBox$ClassInfo.\u0398 : (ColorCheckBox$ClassInfo.\u0398 = \u00c6("com.ms.wfc.ui.LeftRightAlignment")), (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Controls the alignment of the text and the check box"));
        checkState = new PropertyInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "checkState", (ColorCheckBox$ClassInfo.\u0399 != null) ? ColorCheckBox$ClassInfo.\u0399 : (ColorCheckBox$ClassInfo.\u0399 = \u00c6("com.ms.wfc.ui.CheckState")), (MemberAttribute)BindableAttribute.YES, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Controls the check state of the control"));
        checked = new PropertyInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "checked", (Class)Boolean.TYPE, (MemberAttribute)BindableAttribute.YES, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Controls the check state of the control"));
        autoCheck = new PropertyInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "autoCheck", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Determines if the state of the control changes if the control is clicked."));
        threeState = new PropertyInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "threeState", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("If this property is set, the control will allow three check states"));
        checkedChanged = new EventInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "checkedChanged", (ColorCheckBox$ClassInfo.\u00ec != null) ? ColorCheckBox$ClassInfo.\u00ec : (ColorCheckBox$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("Triggered when the check state changed"));
        checkStateChanged = new EventInfo((ColorCheckBox$ClassInfo.\u039a != null) ? ColorCheckBox$ClassInfo.\u039a : (ColorCheckBox$ClassInfo.\u039a = \u00c6("com.mim.wfc.ui.ColorCheckBox")), "checkStateChanged", (ColorCheckBox$ClassInfo.\u00ec != null) ? ColorCheckBox$ClassInfo.\u00ec : (ColorCheckBox$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("Triggered when the check state changed"));
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
        properties.add(ColorCheckBox$ClassInfo.colorStyle);
        properties.add(ColorCheckBox$ClassInfo.textAlign);
        properties.add(ColorCheckBox$ClassInfo.checkState);
        properties.add(ColorCheckBox$ClassInfo.checked);
        properties.add(ColorCheckBox$ClassInfo.autoCheck);
        properties.add(ColorCheckBox$ClassInfo.threeState);
    }
}
