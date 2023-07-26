// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.ResourceBundle;
import java.util.Enumeration;
import com.progress.common.property.PropertyManager;
import com.progress.common.property.PropertyTransferObject;
import java.rmi.RemoteException;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.IEventObject;
import java.rmi.server.RemoteStub;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.international.resources.ProgressResources;
import com.progress.chimera.common.Server;

public class JAService extends Server implements IJAService, IJAParameterizedObject
{
    static ProgressResources resources;
    StateObject state;
    JAPlugIn plugIn;
    JAConfiguration configuration;
    static ConnectionManagerLog theLog;
    static int sessionCounter;
    String displayName;
    
    static ProgressResources resources() {
        return JAService.resources;
    }
    
    public JAStatusObject getStatus() {
        final JAStatusObject jaStatusObject = new JAStatusObject();
        jaStatusObject.displayName = this.getDisplayName();
        jaStatusObject.stateDescriptor = this.getStateDescriptor();
        jaStatusObject.isStartable = this.isStartable();
        jaStatusObject.isStopable = this.isStopable();
        jaStatusObject.isIdle = this.isIdle();
        jaStatusObject.isStarting = this.isStarting();
        jaStatusObject.isInitializing = this.isInitializing();
        jaStatusObject.isRunning = this.isRunning();
        jaStatusObject.isShuttingDown = this.isShuttingDown();
        return jaStatusObject;
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    IJAService evThis() {
        return (IJAService)this.remoteStub();
    }
    
    public void propertiesChanged() {
        this.configuration.updateServiceByPort(this);
    }
    
    public String getStateDescriptor() {
        synchronized (this.plugIn) {
            return this.state.get().displayName();
        }
    }
    
    void setState(final State state) throws StateException {
        if (state == this.state.get()) {
            return;
        }
        synchronized (this.plugIn) {
            synchronized (this.state) {
                try {
                    this.state.changeState(this, state);
                    this.plugIn.getEventBroker().postEvent(new EServerGroupStateChanged(this.configuration.evThis(), this));
                }
                catch (StateException ex) {
                    Tools.px(ex, "Error changing states on server group.");
                    throw ex;
                }
                catch (RemoteException ex2) {
                    Tools.px(ex2, "Can't post event to mark server group state change.");
                }
            }
        }
    }
    
    public void setIdle() throws StateException {
        this.setState(SStateIdle.get());
    }
    
    public void setStarting() throws StateException {
        this.setState(SStateStarting.get());
    }
    
    public void setInitializing() throws StateException {
        this.setState(SStateInitializing.get());
    }
    
    public void setRunning() throws StateException {
        this.setState(SStateRunning.get());
    }
    
    public void setShuttingDown() throws StateException {
        this.setState(SStateShuttingDown.get());
    }
    
    public boolean isIdle() {
        synchronized (this.plugIn) {
            return this.state.get() == SStateIdle.get();
        }
    }
    
    public boolean isStarting() {
        synchronized (this.plugIn) {
            return this.state.get() == SStateStarting.get();
        }
    }
    
    public boolean isInitializing() {
        synchronized (this.plugIn) {
            return this.state.get() == SStateInitializing.get();
        }
    }
    
    public boolean isRunning() {
        synchronized (this.plugIn) {
            return this.state.get() == SStateRunning.get();
        }
    }
    
    public boolean isShuttingDown() {
        synchronized (this.plugIn) {
            return this.state.get() == SStateShuttingDown.get();
        }
    }
    
    public boolean isStartable() {
        return false;
    }
    
    public void start() {
    }
    
    public boolean isStopable() {
        return false;
    }
    
    public void stop() {
    }
    
    public boolean isDeleteable() {
        synchronized (this.plugIn) {
            try {
                if (this.isRunning()) {
                    return false;
                }
                if (this.getSiblingCount() <= 1) {
                    return false;
                }
            }
            catch (RemoteException ex) {
                Tools.px(ex);
                return false;
            }
        }
        return true;
    }
    
    public static ConnectionManagerLog getLog() {
        if (JAService.theLog == null) {
            JAService.theLog = ConnectionManagerLog.get();
        }
        return JAService.theLog;
    }
    
    void handleCrash() {
        try {
            this.setIdle();
            this.plugIn.getEventBroker().postEvent(new EServerGroupCrashEvent(this.configuration.evThis(), this));
        }
        catch (StateException ex) {
            Tools.px(ex, "Can't set state of server group: " + this.name() + " to idle.");
        }
        catch (RemoteException ex2) {
            Tools.px(ex2, "Can't post server group crash event: " + this.name() + " to idle.");
        }
    }
    
    public String getServiceNameOrPort() {
        synchronized (this.plugIn) {
            return this.getProperty("Port");
        }
    }
    
    public String getHostName() {
        synchronized (this.plugIn) {
            return this.getProperty("Host");
        }
    }
    
    public IJAPlugIn getPlugIn() {
        synchronized (this.plugIn) {
            return this.plugIn;
        }
    }
    
    public IJAHierarchy getParent() {
        synchronized (this.plugIn) {
            return this.getConfiguration();
        }
    }
    
    public IJAConfiguration getConfiguration() {
        return this.configuration;
    }
    
    String groupName() {
        return "Servergroup." + this.name();
    }
    
    public PropertyTransferObject makeJuniperPropertySubset() {
        synchronized (this.plugIn) {
            return new PropertyTransferObject(this.plugIn.properties(), this.groupName());
        }
    }
    
    boolean isNetworked() {
        final String serviceNameOrPort = this.getServiceNameOrPort();
        boolean b;
        try {
            b = (Integer.parseInt(serviceNameOrPort) != 0);
        }
        catch (NumberFormatException ex) {
            b = true;
        }
        return b;
    }
    
    public int getSiblingCount() throws RemoteException {
        synchronized (this.plugIn) {
            return this.configuration.services.size();
        }
    }
    
    public String makeNewChildName() {
        return null;
    }
    
    static String makeNewName(final JAConfiguration jaConfiguration) {
        String string;
        do {
            ++JAService.sessionCounter;
            string = JAService.resources.getTranString("ServerGroup-") + JAService.sessionCounter;
        } while (nameUsed(jaConfiguration, string));
        return string;
    }
    
    public boolean childNameUsed(final String s) {
        return false;
    }
    
    static boolean nameUsed(final JAConfiguration jaConfiguration, final String s) {
        return findService(makeLongName(s, jaConfiguration)) != null;
    }
    
    public IJAHierarchy createChild(final String s, final Object o, final Object o2) throws RemoteException {
        throw new RemoteException("Can't create child to server group " + this.name() + ".  No such thing.");
    }
    
    public void delete(final Object o) {
        synchronized (this.plugIn) {
            this.delete(o, true);
        }
    }
    
    void delete(final Object o, final boolean b) {
        final String name = this.name();
        final String groupName = this.groupName();
        try {
            String[] groups = null;
            try {
                this.configuration.removeService(this, o);
                super.delete();
                final JAPlugIn plugIn = this.plugIn;
                JAPlugIn.properties.removeGroup(groupName);
                final JAPlugIn plugIn2 = this.plugIn;
                groups = JAPlugIn.properties.groups("Configuration", true, false);
            }
            catch (PropertyManager.PropertyException ex) {
                Tools.px(ex);
            }
            for (int i = 0; i < groups.length; ++i) {
                JATools.removeFromArrayProp(this.plugIn, name, "Configuration." + groups[i] + ".ServerGroups");
            }
            if (b) {
                JATools.writeOutProperties(this.plugIn);
            }
            this.plugIn.getEventBroker().postEvent(new EServerGroupDeleted(this.evThis(), o));
        }
        catch (RemoteException ex2) {
            Tools.px(ex2, "Failed to complete deletion of server group: " + name + "; remote error encountered.");
        }
    }
    
    public String getDisplayName() {
        return this.getDisplayName(false);
    }
    
    public String getDisplayName(final boolean b) {
        synchronized (this.plugIn) {
            String s;
            if (!b) {
                s = this.displayName;
            }
            else {
                s = this.configuration.getDisplayName(true) + "." + this.displayName;
            }
            return s;
        }
    }
    
    public String toString() {
        return this.getDisplayName();
    }
    
    String getLongName() {
        return makeLongName(this.displayName, this.configuration);
    }
    
    private static String adjustName(final String s) {
        return s.trim().toLowerCase();
    }
    
    static String makeShortName(final String s, final JAConfiguration jaConfiguration) {
        return s.substring(4 + jaConfiguration.name().length() + 1);
    }
    
    static String makeLongName(final String s, final JAConfiguration jaConfiguration) {
        return jaConfiguration.name() + "." + adjustName(s);
    }
    
    static JAService findService(final String s) {
        return (JAService)Server.find(s.toLowerCase());
    }
    
    public String groupPrefix() {
        return this.groupName() + ".";
    }
    
    public String getProperty(final String str) {
        return this.plugIn.properties().getProperty(this.groupPrefix() + str);
    }
    
    public boolean getBooleanProperty(final String str) {
        return this.plugIn.properties().getBooleanProperty(this.groupPrefix() + str);
    }
    
    public boolean isType4GL() {
        synchronized (this.plugIn) {
            return this.getProperty("type").equalsIgnoreCase("4gl");
        }
    }
    
    public boolean isTypeSQL() {
        synchronized (this.plugIn) {
            return this.getProperty("type").equalsIgnoreCase("sql");
        }
    }
    
    public boolean isTypeBoth() {
        synchronized (this.plugIn) {
            return this.getProperty("type").equalsIgnoreCase("both");
        }
    }
    
    public boolean supportsNetworkConnections() {
        synchronized (this.plugIn) {
            return this.getBooleanProperty("NetworkClientSupport");
        }
    }
    
    public boolean supportsSQLConnections() {
        synchronized (this.plugIn) {
            return this.supportsNetworkConnections() && (this.isTypeSQL() || this.isTypeBoth());
        }
    }
    
    public boolean supports4GLConnections() {
        synchronized (this.plugIn) {
            return this.supportsNetworkConnections() && (this.isType4GL() || this.isTypeBoth());
        }
    }
    
    public boolean supportsBothSqlAnd4glConnections() {
        synchronized (this.plugIn) {
            return this.supportsNetworkConnections() && this.isTypeBoth();
        }
    }
    
    protected JAService(final JAPlugIn plugIn, final JAConfiguration configuration, final String s, final String displayName) throws RemoteException {
        super(s);
        this.state = new StateObject(SStateIdle.get());
        this.displayName = "";
        this.displayName = displayName;
        this.plugIn = plugIn;
        this.configuration = configuration;
        new PropChangeListenerObject(this.configuration.database, this.remoteStub(), null, null);
    }
    
    static JAService instantiateNew(final JAPlugIn jaPlugIn, final JAConfiguration jaConfiguration, final String s, final Object o) {
        final JAService instantiateX = instantiateX(jaPlugIn, jaConfiguration, s, s, o);
        try {
            jaPlugIn.properties().putProperty(instantiateX.groupName() + ".DisplayName", s);
            jaPlugIn.properties().putProperty(instantiateX.groupName() + ".Configuration", jaConfiguration.name());
            JATools.addToArrayProp(jaPlugIn, instantiateX.name(), jaConfiguration.groupName() + ".ServerGroups");
        }
        catch (PropertyManager.PropertyException ex) {
            Tools.px(ex);
        }
        instantiateX.configuration.addService(instantiateX, o);
        JATools.writeOutProperties(instantiateX.plugIn);
        return instantiateX;
    }
    
    static JAService instantiateExisting(final JAPlugIn jaPlugIn, final JAConfiguration jaConfiguration, final String s) throws ServiceException {
        final String string = "ServerGroup." + makeLongName(s, jaConfiguration) + ".DisplayName";
        String property = jaPlugIn.properties().getProperty(string);
        if (property == null) {
            property = s;
            try {
                jaPlugIn.properties().putProperty(string, property);
            }
            catch (PropertyManager.PropertyException ex) {
                throw new ServiceException(ex.getMessage());
            }
            JATools.writeOutProperties(jaPlugIn);
        }
        final JAService instantiateX = instantiateX(jaPlugIn, jaConfiguration, s, property, null);
        instantiateX.configuration.addService(instantiateX, null);
        return instantiateX;
    }
    
    static JAService instantiateX(final JAPlugIn jaPlugIn, final JAConfiguration jaConfiguration, final String s, final String s2, final Object o) {
        final String longName = makeLongName(s, jaConfiguration);
        JAService service = findService(longName);
        if (service != null) {
            return service;
        }
        try {
            service = new JAService(jaPlugIn, jaConfiguration, longName, s2);
        }
        catch (RemoteException ex) {
            Tools.px(ex, "Juniper: Failed to instantiate new server group:" + longName);
        }
        return service;
    }
    
    public Enumeration getChildrenObjects() {
        return null;
    }
    
    public String getMMCClientClass() {
        return "com.progress.vj.juniper.JuniperMMCServerGroupClient";
    }
    
    static {
        JAService.resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.JAGenericBundle");
        JAService.theLog = null;
        JAService.sessionCounter = 0;
    }
    
    public static class ServiceException extends Exception
    {
        public ServiceException(final String message) {
            super(message);
        }
    }
}
