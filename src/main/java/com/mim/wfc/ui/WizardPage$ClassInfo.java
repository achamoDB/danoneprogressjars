// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.ICustomizer;
import com.ms.wfc.core.EventInfo;

public class WizardPage$ClassInfo extends ViewPanel$ClassInfo
{
    public static final EventInfo activatePage;
    private static Class \u02e2;
    private static Class \u00eb;
    
    public ICustomizer getCustomizer(final Object o) {
        return (ICustomizer)new WizardPage$Customizer((WizardPage)o);
    }
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(WizardPage$ClassInfo.activatePage);
    }
    
    public String getDefaultEventName() {
        return new String("activatePage");
    }
    
    static {
        activatePage = new EventInfo((WizardPage$ClassInfo.\u02e2 != null) ? WizardPage$ClassInfo.\u02e2 : (WizardPage$ClassInfo.\u02e2 = \u00c6("com.mim.wfc.ui.WizardPage")), "activatePage", (WizardPage$ClassInfo.\u00eb != null) ? WizardPage$ClassInfo.\u00eb : (WizardPage$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("Triggered when the wizard page becomes active"));
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
        properties.add(new PropertyInfo(Control$ClassInfo.anchor, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.dock, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.enabled, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.location, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.size, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.tabIndex, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.tabStop, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.visible, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
