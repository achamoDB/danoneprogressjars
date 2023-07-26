// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.math.BigDecimal;

public class BigDecimalHolder extends Holder
{
    public BigDecimalHolder() {
    }
    
    public BigDecimalHolder(final BigDecimal value) {
        super.setValue(value);
    }
    
    public BigDecimalHolder(final double val) {
        super.setValue(new BigDecimal(val));
    }
    
    public void setBigDecimalValue(final BigDecimal value) {
        super.setValue(value);
    }
    
    public BigDecimal getBigDecimalValue() {
        return (BigDecimal)super.getValue();
    }
}
