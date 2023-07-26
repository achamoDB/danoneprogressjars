// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public class SQLBooleanProperty extends SQLProperty
{
    public SQLBooleanProperty(final String s, final int n, final String s2) throws SQLProperties.PropertyValueException {
        this(s, n, s2, null);
    }
    
    protected SQLBooleanProperty(final String s, final int n, final String s2, final String s3) throws SQLProperties.PropertyValueException {
        super(s, 3, n, s2, s3, new String[] { "true", "false" });
        if (s3 != null) {
            this.validateValue(s3);
        }
        if (s2 != null) {
            this.validateValue(s2);
        }
        this.addHelpRange(this.getRange());
    }
}
