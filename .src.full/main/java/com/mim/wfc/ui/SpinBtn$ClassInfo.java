// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.UserControl$ClassInfo;

public class SpinBtn$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo horizontal;
    public static final PropertyInfo buddyControl;
    public static final PropertyInfo autoBuddy;
    public static final PropertyInfo autoSize;
    public static final PropertyInfo alignment;
    public static final PropertyInfo validateBuddy;
    public static final PropertyInfo repeatRate;
    public static final EventInfo spinUp;
    public static final EventInfo spinDown;
    public static final EventInfo spinEnd;
    private static Class \u0258;
    private static Class \u00ea;
    private static Class \u0257;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(SpinBtn$ClassInfo.spinUp);
        events.add(SpinBtn$ClassInfo.spinDown);
        events.add(SpinBtn$ClassInfo.spinEnd);
    }
    
    static {
        horizontal = new PropertyInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "horizontal", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Selects the orientation of the button arrows"));
        buddyControl = new PropertyInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "buddyControl", (SpinBtn$ClassInfo.\u00ea != null) ? SpinBtn$ClassInfo.\u00ea : (SpinBtn$ClassInfo.\u00ea = \u00c6("com.ms.wfc.ui.Control")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The spin button's buddy control"));
        autoBuddy = new PropertyInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "autoBuddy", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("If this property is set, the control will automatically select the previous control in the tab order as it's buddy control."));
        autoSize = new PropertyInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "autoSize", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("If this property is set, the control will automatically adjust it's size according to the size of it's buddy control."));
        alignment = new PropertyInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "alignment", (SpinBtn$ClassInfo.\u0257 != null) ? SpinBtn$ClassInfo.\u0257 : (SpinBtn$ClassInfo.\u0257 = \u00c6("com.ms.wfc.ui.UpDownAlignment")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("Displays the item's text to the left, to the right or centered below the image"));
        validateBuddy = new PropertyInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "validateBuddy", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("If set to true, the buddy control will be validated if it is a NumEdit control after the spin button was pressed."));
        repeatRate = new PropertyInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "repeatRate", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(20)), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The repeat rate controls how often per second a spin action is triggered when the user keeps the mouse key pressed."));
        spinUp = new EventInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "spinUp", (SpinBtn$ClassInfo.\u00ec != null) ? SpinBtn$ClassInfo.\u00ec : (SpinBtn$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("Triggered after an up action was performed"));
        spinDown = new EventInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "spinDown", (SpinBtn$ClassInfo.\u00ec != null) ? SpinBtn$ClassInfo.\u00ec : (SpinBtn$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("Triggered after a down action was performed"));
        spinEnd = new EventInfo((SpinBtn$ClassInfo.\u0258 != null) ? SpinBtn$ClassInfo.\u0258 : (SpinBtn$ClassInfo.\u0258 = \u00c6("com.mim.wfc.ui.SpinBtn")), "spinEnd", (SpinBtn$ClassInfo.\u00ec != null) ? SpinBtn$ClassInfo.\u00ec : (SpinBtn$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("Triggered after the spin button was released"));
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
        properties.add(SpinBtn$ClassInfo.horizontal);
        properties.add(SpinBtn$ClassInfo.buddyControl);
        properties.add(SpinBtn$ClassInfo.autoBuddy);
        properties.add(SpinBtn$ClassInfo.autoSize);
        properties.add(SpinBtn$ClassInfo.alignment);
        properties.add(SpinBtn$ClassInfo.validateBuddy);
        properties.add(SpinBtn$ClassInfo.repeatRate);
        properties.add(new PropertyInfo(Control$ClassInfo.text, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
}
