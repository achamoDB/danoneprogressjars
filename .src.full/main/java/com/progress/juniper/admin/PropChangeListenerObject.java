// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.property.EPropertiesChanged;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import java.rmi.RemoteException;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.networkevents.IEventInterestObject;
import java.rmi.server.RemoteStub;

public class PropChangeListenerObject
{
    IJADatabase database;
    RemoteStub issuer;
    IEventInterestObject propChangeEieio;
    Class addEventType;
    Class removeEventType;
    
    public PropChangeListenerObject(final IJADatabase database, final RemoteStub issuer, final Class addEventType, final Class removeEventType) {
        this.database = null;
        this.issuer = null;
        this.propChangeEieio = null;
        this.addEventType = null;
        this.removeEventType = null;
        this.database = database;
        this.issuer = issuer;
        this.addEventType = addEventType;
        this.removeEventType = removeEventType;
        try {
            this.propChangeEieio = database.getPlugIn().getEventBroker().expressInterest(EPropertiesChanged.class, new PropChangeListener(this), issuer, ((JAPlugIn)database.getPlugIn()).getEventStream());
            if (addEventType != null) {
                this.propChangeEieio = database.getPlugIn().getEventBroker().expressInterest(addEventType, new PropChangeListener(this), issuer, ((JAPlugIn)database.getPlugIn()).getEventStream());
            }
            if (removeEventType != null) {
                this.propChangeEieio = database.getPlugIn().getEventBroker().expressInterest(removeEventType, new PropChangeListener(this), issuer, ((JAPlugIn)database.getPlugIn()).getEventStream());
            }
        }
        catch (RemoteException ex) {
            Tools.px(ex, "Error expressing interest for object " + this);
        }
    }
    
    public void finalize() {
        try {
            this.database.getPlugIn().getEventBroker().revokeInterest(this.propChangeEieio);
        }
        catch (RemoteException ex) {
            Tools.px(ex, "Error expressing interest for object " + this);
        }
    }
    
    class PropChangeListener extends EventListener
    {
        PropChangeListenerObject self;
        
        PropChangeListener(final PropChangeListenerObject self) {
            this.self = null;
            this.self = self;
        }
        
        public void processEvent(final IEventObject eventObject) throws RemoteException {
            final Object issuer = eventObject.issuer();
            JAStatusObject status = null;
            if (issuer instanceof IJAExecutableObject) {
                status = PropChangeListenerObject.this.database.getStatus();
            }
            this.self.database.getPlugIn().getEventBroker().postEvent(new EGlobalPropChange(PropChangeListenerObject.this.database, issuer, status));
            if (issuer instanceof IJAParameterizedObject) {
                ((IJAParameterizedObject)issuer).propertiesChanged();
            }
        }
    }
}
