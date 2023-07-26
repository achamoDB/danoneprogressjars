// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public class SQLEnumProperty extends SQLProperty
{
    public SQLEnumProperty(final String s, final int n, final String s2, final String[] array) throws SQLProperties.PropertyValueException {
        this(s, 4, n, s2, null, array);
    }
    
    protected SQLEnumProperty(final String s, final int n, final int n2, final String s2, final String s3, final String[] array) throws SQLProperties.PropertyValueException {
        super(s, n, n2, s2, s3, array);
        if (s3 != null) {
            this.validateValue(s3);
        }
        if (s2 != null) {
            this.validateValue(s2);
        }
    }
    
    public String getHelpRange() {
        return this.getHelpRange(this.getName(), this.getRange());
    }
}
