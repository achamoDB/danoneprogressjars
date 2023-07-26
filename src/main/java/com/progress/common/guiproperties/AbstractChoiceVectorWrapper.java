// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Vector;
import java.io.Serializable;

public abstract class AbstractChoiceVectorWrapper extends AbstractChoiceVector implements Serializable, IDatatypeWrapper
{
    public AbstractChoiceVectorWrapper(final Vector vector) {
        super(vector);
    }
    
    AbstractChoiceVectorWrapper() {
    }
    
    void setDomain(final Vector domain) {
        super.domain = domain;
    }
    
    public Object baseObject() {
        return super.domain;
    }
}
