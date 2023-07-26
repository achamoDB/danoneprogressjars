// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public class SQLIntProperty extends SQLProperty
{
    protected int m_min;
    protected int m_max;
    
    public SQLIntProperty(final String s, final int n, final String s2) throws SQLProperties.PropertyValueException {
        this(s, 2, n, s2, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public SQLIntProperty(final String s, final int n, final String s2, final int n2, final int n3) throws SQLProperties.PropertyValueException {
        this(s, 2, n, s2, null, n2, n3);
    }
    
    protected SQLIntProperty(final String s, final int n, final int n2, final String s2, final String s3, final int min, final int max) throws SQLProperties.PropertyValueException {
        super(s, n, n2, s2, s3);
        this.m_min = min;
        this.m_max = max;
        if (s3 != null) {
            this.validateValue(s3);
        }
        if (s2 != null) {
            this.validateValue(s2);
        }
    }
    
    public int getMax() {
        return this.m_max;
    }
    
    public int getMin() {
        return this.m_min;
    }
    
    public void validateValue(final String s) throws SQLProperties.PropertyValueException {
        boolean b = false;
        if (s != null) {
            int int1;
            try {
                int1 = Integer.parseInt(s);
            }
            catch (NumberFormatException ex) {
                int1 = 0;
                b = true;
            }
            if (b || int1 < this.m_min || int1 > this.m_max) {
                throw new SQLProperties.PropertyValueException(this.getName(), s);
            }
        }
    }
    
    public String getRange() {
        return "{ " + this.m_min + "..." + this.m_max + " }";
    }
    
    public String getHelpRange() {
        return this.getHelpRange(this.getName(), this.getRange());
    }
}
