// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import java.util.Enumeration;
import java.util.Vector;
import java.util.Properties;

public class SQLProperty extends Properties implements ISQLProperty
{
    protected String m_name;
    protected String m_value;
    protected String m_defaultValue;
    protected int m_type;
    protected String[] m_enum;
    protected int m_mode;
    protected Vector m_helpVector;
    protected String NEWLINE;
    
    public SQLProperty(final String s, final int n, final int n2, final String s2, final String s3) {
        this(s, n, n2, s2, s3, null);
    }
    
    public SQLProperty(final String name, final int type, final int mode, final String defaultValue, final String value, final String[] enum1) {
        this.m_name = null;
        this.m_value = null;
        this.m_defaultValue = null;
        this.m_type = 0;
        this.m_enum = null;
        this.m_mode = 1;
        this.m_helpVector = new Vector();
        this.NEWLINE = System.getProperty("line.separator");
        this.m_name = name;
        this.m_type = type;
        this.m_defaultValue = defaultValue;
        this.m_value = value;
        this.m_enum = enum1;
        this.m_mode = mode;
    }
    
    public String[] getEnum() {
        return this.m_enum;
    }
    
    public String getDefaultValue() {
        return this.m_defaultValue;
    }
    
    public String getName() {
        return this.m_name;
    }
    
    public int getType() {
        return this.m_type;
    }
    
    public int getMode() {
        return this.m_mode;
    }
    
    public String getShow() {
        return this.getValue();
    }
    
    public String getValue() {
        return (this.m_value == null) ? this.m_defaultValue : this.m_value;
    }
    
    public void setDefaultValue(final String defaultValue) throws SQLProperties.PropertyValueException {
        this.validateValue(defaultValue);
        this.m_defaultValue = defaultValue;
    }
    
    public void setValue(final String value) throws SQLProperties.PropertyValueException {
        this.validateValue(value);
        this.m_value = value;
    }
    
    public void validateValue(final String s) throws SQLProperties.PropertyValueException {
        if (this.m_enum == null) {
            return;
        }
        if (s != null && this.m_enum != null) {
            for (int i = 0; i < this.m_enum.length; ++i) {
                if (s.equalsIgnoreCase(this.m_enum[i])) {
                    return;
                }
            }
            throw new SQLProperties.PropertyValueException(this.getName(), s);
        }
    }
    
    public void addHelpRange(final String obj) {
        this.m_helpVector.addElement(obj);
    }
    
    public String getHelpRange(final String str, final String str2) {
        return "    @" + str + " " + str2;
    }
    
    public String getHelpRange() {
        String s = "";
        final Enumeration<String> elements = (Enumeration<String>)this.m_helpVector.elements();
        int n = 0;
        while (elements.hasMoreElements()) {
            if (n > 0) {
                s = s + this.NEWLINE + "            - or - " + this.NEWLINE;
            }
            s += this.getHelpRange(this.getName(), elements.nextElement());
            ++n;
        }
        return s;
    }
    
    public String getRange() {
        return this.getRange(false);
    }
    
    public String getRange(final boolean b) {
        String string = "";
        if (this.m_enum != null) {
            String string2 = "{ ";
            for (int i = 0; i < this.m_enum.length; ++i) {
                if (i != 0) {
                    string2 += " | ";
                }
                string2 = (b ? (string2 + i) : (string2 + this.m_enum[i]));
            }
            string = string2 + " }";
        }
        return string;
    }
}
