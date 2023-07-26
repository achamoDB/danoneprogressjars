// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.io.Serializable;

public class COpenEdgeManagementContent implements Serializable
{
    String issuerName;
    Object[] content;
    
    public COpenEdgeManagementContent(final String issuerName, final Object[] content) {
        this.issuerName = "";
        this.issuerName = issuerName;
        this.content = content;
    }
    
    public String getIssuerName() {
        return this.issuerName;
    }
    
    public Object[] getContent() {
        return this.content;
    }
}
