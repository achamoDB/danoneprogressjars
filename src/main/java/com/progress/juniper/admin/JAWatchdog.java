// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.IEventObject;
import java.rmi.RemoteException;

class JAWatchdog extends JAAuxiliary
{
    JAWatchdog(final JAPlugIn jaPlugIn, final JAConfiguration jaConfiguration, final String s) throws RemoteException {
        super(jaPlugIn, jaConfiguration, s);
    }
    
    IEventObject getCrashEvent() {
        try {
            return new EWatchdogCrashEvent((IJAAuxiliary)this.stub());
        }
        catch (RemoteException ex) {
            Tools.px(ex);
            return null;
        }
    }
}
