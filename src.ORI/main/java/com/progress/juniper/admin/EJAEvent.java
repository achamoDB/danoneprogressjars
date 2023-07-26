// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.sonicsw.mf.common.runtime.INotification;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.RemoteException;
import java.util.Hashtable;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.EventObject;

public class EJAEvent extends EventObject implements IEJAEvent, IJAComponentConstants, INotificationEvent
{
    public static String notificationName;
    public static String notificationType;
    String m_source;
    StringBuffer m_error;
    Hashtable m_content;
    Object effectedObject;
    
    public EJAEvent(final Object o, final Object o2) throws RemoteException {
        this(o, o2, null);
    }
    
    public EJAEvent(final Object o, final Object o2, final Hashtable hashtable) throws RemoteException {
        super(o);
        this.m_source = null;
        this.m_error = new StringBuffer();
        this.m_content = null;
        this.effectedObject = o2;
        this.m_content = ((hashtable == null) ? new Hashtable() : hashtable);
        if (o != null && o2 != null && !o.equals(o2) && o2 instanceof IChimeraHierarchy) {
            this.m_content.put("affectedObject", ((IChimeraHierarchy)o2).getDisplayName());
        }
        this.setSource(JAPlugIn.getCanonicalName());
    }
    
    public Object getObject() {
        return this.effectedObject;
    }
    
    public String effectedObjectName() {
        final Object object = this.getObject();
        try {
            if (object instanceof IJAHierarchy) {
                return ((IJAHierarchy)object).getDisplayName(true);
            }
        }
        catch (RemoteException ex) {}
        return object.toString();
    }
    
    public String issuerName() throws RemoteException {
        if (this.issuer() instanceof IJAHierarchy) {
            return ((IJAHierarchy)this.issuer()).getDisplayName(true);
        }
        if (this.issuer() instanceof IChimeraHierarchy) {
            return ((IChimeraHierarchy)this.issuer()).getDisplayName();
        }
        return super.issuerName();
    }
    
    public String description() throws RemoteException {
        return super.description() + " applied to object " + this.effectedObjectName();
    }
    
    public String getNotificationType() throws RemoteException {
        return INotification.CATEGORY_TEXT[this.getCategory()] + "." + this.getSubCategory() + "." + this.getNotificationName();
    }
    
    public short getCategory() throws RemoteException {
        return 2;
    }
    
    public String getSubCategory() throws RemoteException {
        return INotification.SUBCATEGORY_TEXT[0];
    }
    
    public String getNotificationName() throws RemoteException {
        String s = this.getClass().getName();
        final int lastIndex = s.lastIndexOf(46);
        if (lastIndex > 0) {
            s = s.substring(lastIndex + 1);
        }
        return s;
    }
    
    public int getSeverityLevel() throws RemoteException {
        return 0;
    }
    
    public String getSource() throws RemoteException {
        return this.m_source;
    }
    
    public void setSource(final String source) throws RemoteException {
        this.m_source = source;
    }
    
    public void addEventData(final JAStatusObject jaStatusObject) throws RemoteException {
        if (this.m_content == null) {
            this.m_content = new Hashtable();
        }
        if (jaStatusObject != null) {
            this.m_content.put("isIdle", new Boolean(jaStatusObject.isIdle()));
            this.m_content.put("isStarting", new Boolean(jaStatusObject.isStarting()));
            this.m_content.put("isInitializing", new Boolean(jaStatusObject.isInitializing()));
            this.m_content.put("isRunning", new Boolean(jaStatusObject.isRunning()));
            this.m_content.put("isShuttingDown", new Boolean(jaStatusObject.isShuttingDown()));
            this.m_content.put("isStartable", new Boolean(jaStatusObject.isStartable()));
            this.m_content.put("isStoppable", new Boolean(jaStatusObject.isStopable()));
            this.m_content.put("stateDescriptor", jaStatusObject.stateDescriptor());
        }
    }
    
    public void addEventData(final String key, final Object value) throws RemoteException {
        if (this.m_content == null) {
            this.m_content = new Hashtable();
        }
        if (key != null && value != null) {
            this.m_content.put(key, value);
        }
    }
    
    public Object getEventData() throws RemoteException {
        return this.m_content;
    }
    
    public String getErrorString() throws RemoteException {
        if (this.m_error.length() == 0) {
            this.m_error.append(this.description());
        }
        return this.m_error.toString();
    }
    
    public void setErrorString(final String str) throws RemoteException {
        this.m_error.append(str);
        this.addEventData("ReasonForFailure", str);
    }
    
    static {
        EJAEvent.notificationName = "EJAEvent";
        EJAEvent.notificationType = "application.state." + EJAEvent.notificationName;
    }
}
