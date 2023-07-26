// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import com.sonicsw.mf.common.runtime.INotification;
import java.rmi.RemoteException;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.EventObject;

public abstract class EOpenEdgeManagementEvent extends EventObject implements IOpenEdgeManagementEvent, INotificationEvent
{
    String m_source;
    String m_error;
    COpenEdgeManagementContent m_content;
    public static String notificationType;
    
    public EOpenEdgeManagementEvent(final Object o) throws RemoteException {
        super(o);
        this.m_source = "";
        this.m_error = "";
        this.m_content = null;
    }
    
    public EOpenEdgeManagementEvent(final Object o, final COpenEdgeManagementContent content) throws RemoteException {
        super(o);
        this.m_source = "";
        this.m_error = "";
        this.m_content = null;
        this.m_content = content;
    }
    
    public EOpenEdgeManagementEvent(final Object o, final String source, final String error, final COpenEdgeManagementContent content) throws RemoteException {
        super(o);
        this.m_source = "";
        this.m_error = "";
        this.m_content = null;
        this.m_source = source;
        this.m_error = error;
        this.m_content = content;
    }
    
    public COpenEdgeManagementContent getContent() {
        return this.m_content;
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
        return "EOpenEdgeManagementEvent";
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
    
    public Object getEventData() throws RemoteException {
        return this.m_content;
    }
    
    public String getErrorString() throws RemoteException {
        return this.m_error;
    }
    
    static {
        EOpenEdgeManagementEvent.notificationType = "application.state.EOpenEdgeManagementEvent";
    }
}
