// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public class PropGroupDescriptor
{
    private String svcTypeStr;
    private String fullPropSpec;
    
    public PropGroupDescriptor(final String fullPropSpec) {
        this.svcTypeStr = PropMgrUtils.getSvcTypeStr(fullPropSpec);
        this.fullPropSpec = fullPropSpec;
    }
    
    public String getSvcTypeStr() {
        return this.svcTypeStr;
    }
    
    public String getfullPropSpec() {
        return this.fullPropSpec;
    }
}
