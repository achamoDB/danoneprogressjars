// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.BindableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Edit$ClassInfo;

public class RegExEdit$ClassInfo extends Edit$ClassInfo
{
    public static final PropertyInfo expression;
    public static final PropertyInfo mode;
    public static final EventInfo validate;
    public static final EventInfo matchFailed;
    public static final EventInfo invalidCharacter;
    private static Class \u0214;
    private static Class \u00e8;
    private static Class \u0213;
    private static Class \u00eb;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(RegExEdit$ClassInfo.validate);
        events.add(RegExEdit$ClassInfo.matchFailed);
        events.add(RegExEdit$ClassInfo.invalidCharacter);
    }
    
    public String getDefaultEventName() {
        return new String("validate");
    }
    
    static {
        expression = new PropertyInfo((RegExEdit$ClassInfo.\u0214 != null) ? RegExEdit$ClassInfo.\u0214 : (RegExEdit$ClassInfo.\u0214 = \u00c6("com.mim.wfc.ui.RegExEdit")), "expression", (RegExEdit$ClassInfo.\u00e8 != null) ? RegExEdit$ClassInfo.\u00e8 : (RegExEdit$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Regular expression specification"));
        mode = new PropertyInfo((RegExEdit$ClassInfo.\u0214 != null) ? RegExEdit$ClassInfo.\u0214 : (RegExEdit$ClassInfo.\u0214 = \u00c6("com.mim.wfc.ui.RegExEdit")), "mode", (RegExEdit$ClassInfo.\u0213 != null) ? RegExEdit$ClassInfo.\u0213 : (RegExEdit$ClassInfo.\u0213 = \u00c6("com.mim.wfc.ui.RegExEditMode")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Text validation mode"));
        validate = new EventInfo((RegExEdit$ClassInfo.\u0214 != null) ? RegExEdit$ClassInfo.\u0214 : (RegExEdit$ClassInfo.\u0214 = \u00c6("com.mim.wfc.ui.RegExEdit")), "validate", (RegExEdit$ClassInfo.\u00eb != null) ? RegExEdit$ClassInfo.\u00eb : (RegExEdit$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("Triggered when the control needs to be validated when it's contents have changed"));
        matchFailed = new EventInfo((RegExEdit$ClassInfo.\u0214 != null) ? RegExEdit$ClassInfo.\u0214 : (RegExEdit$ClassInfo.\u0214 = \u00c6("com.mim.wfc.ui.RegExEdit")), "matchFailed", (RegExEdit$ClassInfo.\u00ec != null) ? RegExEdit$ClassInfo.\u00ec : (RegExEdit$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The text the user has entered does not match the regular expression"));
        invalidCharacter = new EventInfo((RegExEdit$ClassInfo.\u0214 != null) ? RegExEdit$ClassInfo.\u0214 : (RegExEdit$ClassInfo.\u0214 = \u00c6("com.mim.wfc.ui.RegExEdit")), "invalidCharacter", (RegExEdit$ClassInfo.\u00ec != null) ? RegExEdit$ClassInfo.\u00ec : (RegExEdit$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The user entered an invalid character"));
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
        properties.add(RegExEdit$ClassInfo.expression);
        properties.add(RegExEdit$ClassInfo.mode);
        properties.add(new PropertyInfo(Control$ClassInfo.text, (MemberAttribute)BindableAttribute.YES));
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
