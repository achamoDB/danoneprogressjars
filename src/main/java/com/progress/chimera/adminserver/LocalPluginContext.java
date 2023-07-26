// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.sonicsw.mf.common.runtime.INotification;
import com.progress.mf.AbstractPluginContext;

public class LocalPluginContext extends AbstractPluginContext
{
    public INotification createNotification(final short n, final String s, final String s2, final int n2) {
        final LocalPluginNotification localPluginNotification = new LocalPluginNotification(n, s, s2, n2);
        localPluginNotification.setCanonicalName(super.m_pluginComponent.getCanonicalName());
        return (INotification)localPluginNotification;
    }
}
