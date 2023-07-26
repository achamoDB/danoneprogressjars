// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.system;

import com.sonicsw.mf.common.runtime.INotification;
import java.rmi.RemoteException;
import java.util.Hashtable;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventObject;

public class ESystemObject extends EventObject implements IEventObject, INotificationEvent
{
    protected String m_source;
    protected Object m_eventContent;
    public static String notificationType;
    
    public ESystemObject(final Object o, final Hashtable eventContent) throws RemoteException {
        super(o);
        this.m_source = null;
        this.m_eventContent = null;
        this.m_eventContent = eventContent;
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
        return "EReloadLogFileViewer";
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
        return this.m_eventContent;
    }
    
    public String getErrorString() throws RemoteException {
        return "";
    }
    
    static {
        ESystemObject.notificationType = "application.state.ESystemObject";
    }
}
