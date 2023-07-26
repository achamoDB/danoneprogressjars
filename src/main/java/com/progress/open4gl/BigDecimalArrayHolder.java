// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.math.BigDecimal;

public class BigDecimalArrayHolder extends Holder
{
    public BigDecimalArrayHolder() {
    }
    
    public BigDecimalArrayHolder(final BigDecimal[] value) {
        super.setValue(value);
    }
    
    public BigDecimalArrayHolder(final double[] value) {
        super.setValue(value);
    }
    
    public void setBigDecimalArrayValue(final BigDecimal[] value) {
        super.setValue(value);
    }
    
    public BigDecimal[] getBigDecimalArrayValue() {
        return (BigDecimal[])super.getValue();
    }
}
