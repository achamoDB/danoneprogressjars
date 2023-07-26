// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf;

public interface IManagedPlugin
{
    public static final int COMPONENT_STATE_STARTING = 1;
    public static final int COMPONENT_STATE_ONLINE = 2;
    public static final int COMPONENT_STATE_STOPPING = 3;
    public static final int COMPONENT_STATE_OFFLINE = 4;
    
    void initManagedPlugin(final AbstractPluginComponent p0);
    
    AbstractPluginComponent getPluginComponent();
    
    String getComponentClassName();
    
    String getPluginName();
    
    void setComponentState(final int p0);
}
