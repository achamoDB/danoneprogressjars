// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.container.events;

import com.sonicsw.mf.common.runtime.INotification;
import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.util.IEClientSpecificEvent;
import com.progress.common.networkevents.EventObject;

public class ChimeraContainerEventSuper extends EventObject implements IChimeraContainerEventSuper, IEClientSpecificEvent, INotificationEvent
{
    protected IChimeraHierarchy parentNode;
    protected IChimeraHierarchy nodeObj;
    protected Object guiID;
    protected String m_source;
    protected Object m_eventContent;
    public static String notificationType;
    
    public ChimeraContainerEventSuper(final Object o, final IChimeraHierarchy parentNode, final IChimeraHierarchy nodeObj, final Object guiID) throws RemoteException {
        super(o);
        this.parentNode = null;
        this.nodeObj = null;
        this.guiID = null;
        this.m_source = null;
        this.m_eventContent = null;
        this.parentNode = parentNode;
        this.nodeObj = nodeObj;
        this.guiID = guiID;
    }
    
    public IChimeraHierarchy getNodeObj() throws RemoteException {
        return this.nodeObj;
    }
    
    public IChimeraHierarchy getParentNode() throws RemoteException {
        return this.parentNode;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
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
        return "ChimeraContainerEventSuper";
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
        ChimeraContainerEventSuper.notificationType = "application.state.ChimeraContainerEventSuper";
    }
}
