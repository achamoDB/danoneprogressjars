// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import com.progress.common.property.EPropertiesChanged;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import java.rmi.RemoteException;
import com.progress.wsa.tools.WsaPluginLog;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.networkevents.IEventInterestObject;
import java.rmi.server.RemoteStub;

public class WsaPropChangeListenerObject
{
    WsaInstanceRemoteObject wsa;
    RemoteStub issuer;
    IEventInterestObject propChangeEieio;
    
    public WsaPropChangeListenerObject(final WsaInstanceRemoteObject wsa, final RemoteStub issuer) {
        this.wsa = null;
        this.issuer = null;
        this.propChangeEieio = null;
        this.wsa = wsa;
        this.issuer = issuer;
        try {
            this.propChangeEieio = wsa.getEventBroker().expressInterest(EPropertiesChanged.class, new PropChangeListener(this), issuer, wsa.getEventStream(wsa.fullPropGroupPath));
        }
        catch (RemoteException obj) {
            WsaPluginLog.logError(5, "Error expressing interest for " + wsa.m_svcName + " " + obj);
        }
    }
    
    public void finalize() {
        try {
            this.wsa.getEventBroker().revokeInterest(this.propChangeEieio);
        }
        catch (RemoteException obj) {
            WsaPluginLog.logError(5, "Error revoking interest for " + this.wsa.m_svcName + " " + obj);
        }
    }
    
    class PropChangeListener extends EventListener
    {
        WsaPropChangeListenerObject self;
        
        PropChangeListener(final WsaPropChangeListenerObject self) {
            this.self = null;
            this.self = self;
        }
        
        public void processEvent(final IEventObject eventObject) throws RemoteException {
            WsaPropChangeListenerObject.this.wsa.updateSecuritySettings();
        }
    }
}
