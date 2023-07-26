// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.io.Serializable;

public abstract class AbstractChoiceArrayWrapper extends AbstractChoiceArray implements Serializable, IDatatypeWrapper
{
    public AbstractChoiceArrayWrapper(final Object[] array) {
        super(array);
    }
    
    AbstractChoiceArrayWrapper() {
    }
    
    void setDomain(final Object[] domain) {
        super.domain = domain;
    }
    
    public Object baseObject() {
        return super.domain;
    }
}
