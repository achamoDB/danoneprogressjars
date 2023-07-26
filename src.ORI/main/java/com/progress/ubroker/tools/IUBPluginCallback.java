// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.chimera.adminserver.ServerPolicyInfo;

public interface IUBPluginCallback
{
    boolean addBroker(final String p0, final IAdminRemote p1);
    
    boolean removeBroker(final String p0);
    
    IAdminRemote getBroker(final String p0);
    
    IAdminRemote connectToBroker(final String p0, final int p1);
    
    ServerPolicyInfo getPolicy(final String p0);
    
    boolean isReqUserName();
}
