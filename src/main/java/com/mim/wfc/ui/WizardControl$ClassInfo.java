// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.ui.Control$ClassInfo;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.LocalizableAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.ICustomizer;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.UserControl$ClassInfo;

public class WizardControl$ClassInfo extends UserControl$ClassInfo
{
    public static final PropertyInfo xButtonDistance;
    public static final PropertyInfo yButtonDistance;
    public static final PropertyInfo buttonWidth;
    public static final PropertyInfo separator;
    public static final PropertyInfo controlBarType;
    public static final PropertyInfo finishEnabled;
    public static final PropertyInfo useImageButtons;
    public static final PropertyInfo helpVisible;
    public static final EventInfo finish;
    public static final EventInfo cancel;
    public static final EventInfo help;
    public static final EventInfo nextPage;
    public static final EventInfo previousPage;
    public static final EventInfo activatePage;
    public static final PropertyInfo pages;
    public static final PropertyInfo nextImage;
    public static final PropertyInfo backImage;
    public static final PropertyInfo finishImage;
    public static final PropertyInfo cancelImage;
    public static final PropertyInfo helpImage;
    public static final PropertyInfo nextText;
    public static final PropertyInfo backText;
    public static final PropertyInfo finishText;
    public static final PropertyInfo cancelText;
    public static final PropertyInfo helpText;
    private static Class \u02c1;
    private static Class \u025e;
    private static Class \u00ec;
    private static Class \u00eb;
    private static Class \u02d0;
    private static Class \u01a5;
    private static Class \u00e8;
    
    public ICustomizer getCustomizer(final Object o) {
        return (ICustomizer)new WizardControl$Customizer((WizardControl)o);
    }
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(WizardControl$ClassInfo.activatePage);
        events.add(WizardControl$ClassInfo.nextPage);
        events.add(WizardControl$ClassInfo.previousPage);
        events.add(WizardControl$ClassInfo.finish);
        events.add(WizardControl$ClassInfo.cancel);
        events.add(WizardControl$ClassInfo.help);
    }
    
    public String getDefaultEventName() {
        return new String("finish");
    }
    
    static {
        xButtonDistance = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "XButtonDistance", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(10)), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Distance between control bar buttons"));
        yButtonDistance = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "YButtonDistance", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(10)), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Distance between control bar buttons"));
        buttonWidth = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "buttonWidth", (Class)Integer.TYPE, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Width of control bar button"));
        separator = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "separator", (WizardControl$ClassInfo.\u025e != null) ? WizardControl$ClassInfo.\u025e : (WizardControl$ClassInfo.\u025e = \u00c6("com.mim.wfc.ui.Splitter3DBorderStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(3)), (MemberAttribute)new DescriptionAttribute("Type of separator between the wizard page and the control bar"));
        controlBarType = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "controlBar3Button", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Choose between a 3 button (Back, Next/Finish, Cancel) or a 4 button (Back, Next, Finish, Cancel) control bar"));
        finishEnabled = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "finishEnabled", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("If set to true, the finish button will be enabled."));
        useImageButtons = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "useImageButtons", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("If set to true, the control bar buttons will have text and images"));
        helpVisible = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "helpVisible", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Shows/hides the help button"));
        finish = new EventInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "finish", (WizardControl$ClassInfo.\u00ec != null) ? WizardControl$ClassInfo.\u00ec : (WizardControl$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("User pressed the 'Finish' button"));
        cancel = new EventInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "cancel", (WizardControl$ClassInfo.\u00ec != null) ? WizardControl$ClassInfo.\u00ec : (WizardControl$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("User pressed the 'Cancel' button"));
        help = new EventInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "help", (WizardControl$ClassInfo.\u00ec != null) ? WizardControl$ClassInfo.\u00ec : (WizardControl$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("User pressed the 'Help' button"));
        nextPage = new EventInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "nextPage", (WizardControl$ClassInfo.\u00eb != null) ? WizardControl$ClassInfo.\u00eb : (WizardControl$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("User pressed the 'Next' button"));
        previousPage = new EventInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "previousPage", (WizardControl$ClassInfo.\u00eb != null) ? WizardControl$ClassInfo.\u00eb : (WizardControl$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("User pressed the 'Back' button"));
        activatePage = new EventInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "activatePage", (WizardControl$ClassInfo.\u00ec != null) ? WizardControl$ClassInfo.\u00ec : (WizardControl$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("A wizard page becomes active"));
        pages = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "pages", (WizardControl$ClassInfo.\u02d0 != null) ? WizardControl$ClassInfo.\u02d0 : (WizardControl$ClassInfo.\u02d0 = \u00c6("[Lcom.mim.wfc.ui.WizardPage;")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Array of wizard pages that will be managed by the wizard control"));
        nextImage = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "nextImage", (WizardControl$ClassInfo.\u01a5 != null) ? WizardControl$ClassInfo.\u01a5 : (WizardControl$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Next' button"));
        backImage = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "backImage", (WizardControl$ClassInfo.\u01a5 != null) ? WizardControl$ClassInfo.\u01a5 : (WizardControl$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Back' button"));
        finishImage = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "finishImage", (WizardControl$ClassInfo.\u01a5 != null) ? WizardControl$ClassInfo.\u01a5 : (WizardControl$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Finish' button"));
        cancelImage = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "cancelImage", (WizardControl$ClassInfo.\u01a5 != null) ? WizardControl$ClassInfo.\u01a5 : (WizardControl$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Cancel' button"));
        helpImage = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "helpImage", (WizardControl$ClassInfo.\u01a5 != null) ? WizardControl$ClassInfo.\u01a5 : (WizardControl$ClassInfo.\u01a5 = \u00c6("com.ms.wfc.ui.Image")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Image to be displayed on the 'Help' button"));
        nextText = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "nextText", (WizardControl$ClassInfo.\u00e8 != null) ? WizardControl$ClassInfo.\u00e8 : (WizardControl$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Text to be displayed on the 'Next' button"));
        backText = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "backText", (WizardControl$ClassInfo.\u00e8 != null) ? WizardControl$ClassInfo.\u00e8 : (WizardControl$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Text to be displayed on the 'Back' button"));
        finishText = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "finishText", (WizardControl$ClassInfo.\u00e8 != null) ? WizardControl$ClassInfo.\u00e8 : (WizardControl$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Text to be displayed on the 'Finish' button"));
        cancelText = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "cancelText", (WizardControl$ClassInfo.\u00e8 != null) ? WizardControl$ClassInfo.\u00e8 : (WizardControl$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Text to be displayed on the 'Cancel' button"));
        helpText = new PropertyInfo((WizardControl$ClassInfo.\u02c1 != null) ? WizardControl$ClassInfo.\u02c1 : (WizardControl$ClassInfo.\u02c1 = \u00c6("com.mim.wfc.ui.WizardControl")), "helpText", (WizardControl$ClassInfo.\u00e8 != null) ? WizardControl$ClassInfo.\u00e8 : (WizardControl$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)LocalizableAttribute.YES, (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DescriptionAttribute("Text to be displayed on the 'Help' button"));
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
        properties.add(WizardControl$ClassInfo.pages);
        properties.add(WizardControl$ClassInfo.nextImage);
        properties.add(WizardControl$ClassInfo.backImage);
        properties.add(WizardControl$ClassInfo.finishImage);
        properties.add(WizardControl$ClassInfo.cancelImage);
        properties.add(WizardControl$ClassInfo.helpImage);
        properties.add(WizardControl$ClassInfo.nextText);
        properties.add(WizardControl$ClassInfo.backText);
        properties.add(WizardControl$ClassInfo.finishText);
        properties.add(WizardControl$ClassInfo.cancelText);
        properties.add(WizardControl$ClassInfo.helpText);
        properties.add(WizardControl$ClassInfo.controlBarType);
        properties.add(WizardControl$ClassInfo.useImageButtons);
        properties.add(WizardControl$ClassInfo.buttonWidth);
        properties.add(WizardControl$ClassInfo.xButtonDistance);
        properties.add(WizardControl$ClassInfo.yButtonDistance);
        properties.add(WizardControl$ClassInfo.separator);
        properties.add(WizardControl$ClassInfo.helpVisible);
        properties.add(new PropertyInfo(Control$ClassInfo.text, (MemberAttribute)BrowsableAttribute.NO, (MemberAttribute)PersistableAttribute.NO));
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
