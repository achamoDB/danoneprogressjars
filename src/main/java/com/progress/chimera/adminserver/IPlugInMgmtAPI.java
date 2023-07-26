// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.registry.Registry;
import com.progress.common.property.PropertyList;
import java.util.Vector;

public interface IPlugInMgmtAPI
{
    String[] convertAddress(final String p0) throws PlugInMgmtAPI.UnknownAddressFormatException;
    
    Vector getPluginInstances(final String p0) throws PlugInMgmtAPI.UnknownAddressFormatException;
    
    Vector getServerPluginInfo();
    
    Vector getPluginInfo(final String p0) throws PlugInMgmtAPI.UnknownAddressFormatException;
    
    PropertyList getPropertyValues(final String p0) throws PlugInMgmtAPI.UnknownAddressFormatException, PlugInMgmtAPI.UnknownPluginException;
    
    Registry getRMIRegistry();
}
