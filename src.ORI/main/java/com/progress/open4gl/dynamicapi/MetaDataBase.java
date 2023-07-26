// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.Serializable;

public class MetaDataBase implements Serializable
{
    private int m_type;
    
    public int getTypeCode() {
        return this.m_type;
    }
    
    public MetaDataBase(final int type) {
        this.m_type = type;
    }
}
