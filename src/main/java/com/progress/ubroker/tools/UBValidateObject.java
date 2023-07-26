// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.property.PropertyManager;

public class UBValidateObject
{
    public String m_fullSvcPath;
    public PropertyManager.PropertyCollection m_propColl;
    private PropertyManager.PropertyCollection m_noAncestor;
    
    public UBValidateObject(final PropertyManager.PropertyCollection propColl, final String original) {
        this.m_fullSvcPath = new String(original);
        this.m_propColl = propColl;
        this.m_noAncestor = null;
    }
    
    public UBValidateObject(final PropertyManager.PropertyCollection propColl, final PropertyManager.PropertyCollection noAncestor, final String original) {
        this.m_fullSvcPath = new String(original);
        this.m_propColl = propColl;
        this.m_noAncestor = noAncestor;
    }
    
    public void setNoAncestor(final PropertyManager.PropertyCollection noAncestor) {
        this.m_noAncestor = noAncestor;
    }
    
    public PropertyManager.PropertyCollection getNoAncestor() {
        return this.m_noAncestor;
    }
}
