// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.events;

import com.sonicsw.mf.common.runtime.INotification;
import java.rmi.RemoteException;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventStream;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.util.IEClientSpecificEvent;
import com.progress.common.networkevents.EventObject;

public class YodaPluginEventSuper extends EventObject implements IYodaPluginEventSuper, IEClientSpecificEvent, INotificationEvent
{
    IChimeraHierarchy m_pluginObj;
    String m_propGrpFullSpec;
    String m_instanceName;
    IEventStream m_evtStream;
    Object m_issuer;
    String m_source;
    Object m_eventContent;
    public static String notificationType;
    
    public YodaPluginEventSuper(final Object o, final IChimeraHierarchy pluginObj, final String s, final String s2, final IEventStream evtStream) throws RemoteException {
        super(o);
        this.m_pluginObj = null;
        this.m_propGrpFullSpec = null;
        this.m_instanceName = null;
        this.m_evtStream = null;
        this.m_issuer = null;
        this.m_source = null;
        this.m_eventContent = null;
        try {
            this.m_pluginObj = pluginObj;
            this.m_propGrpFullSpec = s;
            this.m_instanceName = s2;
            this.m_evtStream = evtStream;
            this.m_eventContent = new Hashtable();
            ((Hashtable)this.m_eventContent).put("fullPath", s);
            ((Hashtable)this.m_eventContent).put("instName", s2);
        }
        catch (Exception ex) {
            System.out.println("failed to instantiate YodaPluginEventSuper");
        }
    }
    
    public YodaPluginEventSuper(final Object o, final String s, final String s2) throws RemoteException {
        super(o);
        this.m_pluginObj = null;
        this.m_propGrpFullSpec = null;
        this.m_instanceName = null;
        this.m_evtStream = null;
        this.m_issuer = null;
        this.m_source = null;
        this.m_eventContent = null;
        try {
            this.m_pluginObj = null;
            this.m_propGrpFullSpec = s2;
            this.m_instanceName = s;
            this.m_evtStream = null;
            this.m_eventContent = new Hashtable();
            ((Hashtable)this.m_eventContent).put("fullPath", s2);
            ((Hashtable)this.m_eventContent).put("instName", s);
        }
        catch (Exception ex) {
            System.out.println("failed to instantiate YodaPluginEventSuper");
        }
    }
    
    public YodaPluginEventSuper(final Object o, final IChimeraHierarchy pluginObj, final String propGrpFullSpec, final String instanceName, final IEventStream evtStream, final Object eventContent) throws RemoteException {
        super(o);
        this.m_pluginObj = null;
        this.m_propGrpFullSpec = null;
        this.m_instanceName = null;
        this.m_evtStream = null;
        this.m_issuer = null;
        this.m_source = null;
        this.m_eventContent = null;
        try {
            this.m_pluginObj = pluginObj;
            this.m_propGrpFullSpec = propGrpFullSpec;
            this.m_instanceName = instanceName;
            this.m_evtStream = evtStream;
            this.m_eventContent = eventContent;
        }
        catch (Exception ex) {
            System.out.println("failed to instantiate YodaPluginEventSuper");
        }
    }
    
    public YodaPluginEventSuper(final Object o, final String instanceName, final String propGrpFullSpec, final Object eventContent) throws RemoteException {
        super(o);
        this.m_pluginObj = null;
        this.m_propGrpFullSpec = null;
        this.m_instanceName = null;
        this.m_evtStream = null;
        this.m_issuer = null;
        this.m_source = null;
        this.m_eventContent = null;
        try {
            this.m_pluginObj = null;
            this.m_propGrpFullSpec = propGrpFullSpec;
            this.m_instanceName = instanceName;
            this.m_evtStream = null;
            this.m_eventContent = eventContent;
        }
        catch (Exception ex) {
            System.out.println("failed to instantiate YodaPluginEventSuper");
        }
    }
    
    public void setEventContent(final Object eventContent) {
        this.m_eventContent = eventContent;
    }
    
    public IChimeraHierarchy getPluginObj() throws RemoteException {
        return this.m_pluginObj;
    }
    
    public String getPropGrpFullSpec() throws RemoteException {
        return this.m_propGrpFullSpec;
    }
    
    public String getInstanceName() throws RemoteException {
        return this.m_instanceName;
    }
    
    public Object guiID() throws RemoteException {
        return this.m_evtStream;
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
        return "YodaPluginEventSuper";
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
        YodaPluginEventSuper.notificationType = "application.state.YodaPluginEventSuper";
    }
}
