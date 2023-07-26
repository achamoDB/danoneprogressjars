// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.ui;

import com.ms.wfc.ui.Color;
import com.ms.wfc.ui.Cursor;
import com.ms.wfc.core.BrowsableAttribute;
import com.ms.wfc.core.PersistableAttribute;
import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.BindableAttribute;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.ui.Edit$ClassInfo;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.ui.Control$ClassInfo;

public class NumEdit$ClassInfo extends Control$ClassInfo
{
    public static final PropertyInfo autoSize;
    public static final PropertyInfo borderStyle;
    public static final PropertyInfo digits;
    public static final PropertyInfo grouping;
    public static final PropertyInfo currencySymbol;
    public static final PropertyInfo negativeColor;
    public static final PropertyInfo maxValue;
    public static final PropertyInfo minValue;
    public static final PropertyInfo nullValue;
    public static final PropertyInfo precision;
    public static final PropertyInfo progressiveStepCount;
    public static final PropertyInfo readOnly;
    public static final PropertyInfo showTrailingZeros;
    public static final PropertyInfo stepWidth1;
    public static final PropertyInfo stepWidth2;
    public static final PropertyInfo stepWidth3;
    public static final PropertyInfo textAlign;
    public static final PropertyInfo validateStep;
    public static final PropertyInfo value;
    public static final EventInfo validate;
    public static final EventInfo validationFailed;
    public static final EventInfo valueChanged;
    private static Class \u01bb;
    private static Class \u01bc;
    private static Class \u00e8;
    private static Class \u0164;
    private static Class \u01bd;
    private static Class \u00eb;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        new Edit$ClassInfo().getEvents(events);
        events.add(NumEdit$ClassInfo.validate);
        events.add(NumEdit$ClassInfo.valueChanged);
        events.add(NumEdit$ClassInfo.validationFailed);
    }
    
    public String getDefaultEventName() {
        return new String("validate");
    }
    
    static {
        autoSize = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "autoSize", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Sizes the height of the control according to its font size"));
        borderStyle = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "borderStyle", (NumEdit$ClassInfo.\u01bc != null) ? NumEdit$ClassInfo.\u01bc : (NumEdit$ClassInfo.\u01bc = \u00c6("com.ms.wfc.ui.BorderStyle")), (MemberAttribute)CategoryAttribute.Appearance, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(2)), (MemberAttribute)new DescriptionAttribute("The control's border style"));
        digits = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "digits", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(1)), (MemberAttribute)new DescriptionAttribute("The number of digits before the decimal separator"));
        grouping = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "grouping", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Grouping of the number"));
        currencySymbol = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "currencySymbol", (NumEdit$ClassInfo.\u00e8 != null) ? NumEdit$ClassInfo.\u00e8 : (NumEdit$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Currency symbol"));
        negativeColor = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "negativeColor", (NumEdit$ClassInfo.\u0164 != null) ? NumEdit$ClassInfo.\u0164 : (NumEdit$ClassInfo.\u0164 = \u00c6("com.ms.wfc.ui.Color")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Text color for negative numbers"));
        maxValue = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "maxValue", (Class)Double.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DefaultValueAttribute((Object)new Double(0.0)), (MemberAttribute)new DescriptionAttribute("The maximum value of the control"));
        minValue = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "minValue", (Class)Double.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DefaultValueAttribute((Object)new Double(0.0)), (MemberAttribute)new DescriptionAttribute("The minimum value of the control"));
        nullValue = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "nullValue", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DescriptionAttribute("Set to true if the control does not have a value"));
        precision = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "precision", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("The number of digits after the decimal separator"));
        progressiveStepCount = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "progressiveStepCount", (Class)Integer.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(40)), (MemberAttribute)new DescriptionAttribute("If set to anything != 0, the longer the user presses a scroll button, the bigger the step width will get. This property defines the number of spins until the step width reaches the next level."));
        readOnly = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "readOnly", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Prohibits the user from modifying the control"));
        showTrailingZeros = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "showTrailingZeros", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.TRUE, (MemberAttribute)new DescriptionAttribute("Shows trailing zeros after the decimal separator"));
        stepWidth1 = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "stepWidth1", (Class)Double.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DefaultValueAttribute((Object)new Double(1.0)), (MemberAttribute)new DescriptionAttribute("Up/down key scroll value 1"));
        stepWidth2 = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "stepWidth2", (Class)Double.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DefaultValueAttribute((Object)new Double(5.0)), (MemberAttribute)new DescriptionAttribute("Up/down (shift) key scroll value 2"));
        stepWidth3 = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "stepWidth3", (Class)Double.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)new DefaultValueAttribute((Object)new Double(20.0)), (MemberAttribute)new DescriptionAttribute("Up/down (ctrl) key scroll value 3"));
        textAlign = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "textAlign", (NumEdit$ClassInfo.\u01bd != null) ? NumEdit$ClassInfo.\u01bd : (NumEdit$ClassInfo.\u01bd = \u00c6("com.ms.wfc.ui.HorizontalAlignment")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Controls the alignment of the control's text"));
        validateStep = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "validateStep", (Class)Boolean.TYPE, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("Check if the control's value is dividable by the value of the stepWidth1 property"));
        value = new PropertyInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "value", (Class)Double.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)BindableAttribute.YES, (MemberAttribute)new DefaultValueAttribute((Object)new Double(0.0)), (MemberAttribute)new DescriptionAttribute("The control's numeric value if not null"));
        validate = new EventInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "validate", (NumEdit$ClassInfo.\u00eb != null) ? NumEdit$ClassInfo.\u00eb : (NumEdit$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)CategoryAttribute.Action, (MemberAttribute)new DescriptionAttribute("Triggered when the control needs to be validated when it's contents have changed"));
        validationFailed = new EventInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "validationFailed", (NumEdit$ClassInfo.\u00ec != null) ? NumEdit$ClassInfo.\u00ec : (NumEdit$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The pre-validation of the text the user has entered failed"));
        valueChanged = new EventInfo((NumEdit$ClassInfo.\u01bb != null) ? NumEdit$ClassInfo.\u01bb : (NumEdit$ClassInfo.\u01bb = \u00c6("com.mim.wfc.ui.NumEdit")), "valueChanged", (NumEdit$ClassInfo.\u00ec != null) ? NumEdit$ClassInfo.\u00ec : (NumEdit$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)new DescriptionAttribute("The control's value has changed (triggered after the validate event)"));
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
        properties.add(NumEdit$ClassInfo.autoSize);
        properties.add(NumEdit$ClassInfo.readOnly);
        properties.add(NumEdit$ClassInfo.borderStyle);
        properties.add(NumEdit$ClassInfo.textAlign);
        properties.add(NumEdit$ClassInfo.value);
        properties.add(NumEdit$ClassInfo.nullValue);
        properties.add(NumEdit$ClassInfo.digits);
        properties.add(NumEdit$ClassInfo.precision);
        properties.add(NumEdit$ClassInfo.grouping);
        properties.add(NumEdit$ClassInfo.currencySymbol);
        properties.add(NumEdit$ClassInfo.negativeColor);
        properties.add(NumEdit$ClassInfo.stepWidth1);
        properties.add(NumEdit$ClassInfo.stepWidth2);
        properties.add(NumEdit$ClassInfo.stepWidth3);
        properties.add(NumEdit$ClassInfo.minValue);
        properties.add(NumEdit$ClassInfo.maxValue);
        properties.add(NumEdit$ClassInfo.progressiveStepCount);
        properties.add(NumEdit$ClassInfo.showTrailingZeros);
        properties.add(NumEdit$ClassInfo.validateStep);
        properties.add(new PropertyInfo(Control$ClassInfo.text, (MemberAttribute)PersistableAttribute.NO, (MemberAttribute)BrowsableAttribute.NO));
        properties.add(new PropertyInfo(Control$ClassInfo.cursor, (MemberAttribute)new DefaultValueAttribute((Object)Cursor.IBEAM)));
        properties.add(new PropertyInfo(Control$ClassInfo.backColor, (MemberAttribute)new DefaultValueAttribute((Object)Color.WINDOW)));
    }
    
    public String getDefaultPropertyName() {
        return new String("name");
    }
}
