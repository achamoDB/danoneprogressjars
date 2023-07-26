// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public class SQLStringProperty extends SQLProperty
{
    protected String[] m_enum;
    
    public SQLStringProperty(final String s, final int n, final String s2, final String[] array) throws SQLProperties.PropertyValueException {
        this(s, 1, n, s2, null, array);
    }
    
    protected SQLStringProperty(final String s, final int n, final int n2, final String s2, final String s3, final String[] array) throws SQLProperties.PropertyValueException {
        super(s, n, n2, s2, s3);
        for (int i = 0; i < array.length; ++i) {
            this.addHelpRange(array[i]);
        }
    }
}
