// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public class SQLEnumIdxProperty extends SQLProperty
{
    public SQLEnumIdxProperty(final String s, final int n, final String s2, final String[] array) throws SQLProperties.PropertyValueException {
        this(s, 5, n, s2, null, array);
    }
    
    protected SQLEnumIdxProperty(final String s, final int n, final int n2, final String s2, final String s3, final String[] array) throws SQLProperties.PropertyValueException {
        super(s, n, n2, s2, s3, array);
        if (s3 != null) {
            this.validateValue(s3);
        }
        if (s2 != null) {
            this.validateValue(s2);
        }
    }
    
    public String getHelpRange() {
        return this.getHelpRange(this.getName(), this.getRange()) + super.NEWLINE + "            - or - " + super.NEWLINE + this.getHelpRange(this.getName(), this.getRange(true));
    }
    
    public String getShow() {
        final String value = this.getValue();
        int i = 0;
        for (int j = 0; j < super.m_enum.length; ++j) {
            if (super.m_enum[j].equalsIgnoreCase(value)) {
                i = j;
            }
        }
        return i + ": " + value;
    }
}
