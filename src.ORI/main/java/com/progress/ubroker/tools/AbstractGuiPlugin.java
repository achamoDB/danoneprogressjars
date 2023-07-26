// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.events.EPMPWarmStartFinishedEvent;
import com.progress.ubroker.management.events.EPMPWarmStartBeganEvent;
import com.progress.ubroker.tools.events.ETrimSrvrEvent;
import com.progress.ubroker.tools.events.EAddSrvrEvent;
import com.progress.chimera.container.events.ContainerAddTreeNodeEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.chimera.container.events.ContainerDeleteTreeNodeEvent;
import java.rmi.server.RemoteStub;
import com.progress.mf.runtime.ManagementPlugin;
import com.progress.common.networkevents.IEventListener;
import com.progress.ubroker.management.OpenEdgePluginComponent;
import java.rmi.Remote;
import com.progress.common.log.ProLog;
import com.progress.ubroker.tools.wsa.WsaPluginRemoteObject;
import com.progress.ubroker.tools.aia.AiaRemoteObject;
import com.progress.ubroker.tools.adapter.AdapterRemoteObject;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Vector;
import com.progress.chimera.adminserver.ServerPluginInfo;
import com.progress.chimera.adminserver.AdminServer;
import com.progress.common.networkevents.IEventStream;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.ubroker.tools.wsa.WsaGuiPlugin;
import com.progress.ubroker.tools.aia.AiaGuiPlugin;
import com.progress.ubroker.tools.adapter.AdapterGuiPlugin;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.mf.AbstractPluginComponent;
import com.progress.mf.IManagedPlugin;

public abstract class AbstractGuiPlugin extends AbstractUbrokerPlugin implements IBTMsgCodes, IManagedPlugin
{
    static final int FETCH_PMP_RETRIES = 3;
    static boolean osChecked;
    static boolean osIsSupported;
    protected String PLUGIN_ID;
    protected AbstractPluginComponent m_pluginComponent;
    static IPropertyManagerRemote m_rpm;
    static IEventBroker m_eventBroker;
    static String[] m_schemaPropFnList;
    static String m_propFilename;
    static String m_schemaFilename;
    static String m_admSrvrIPAddr;
    static boolean m_sharedResourcesInited;
    static Hashtable m_lookupClients;
    private static UBGuiPlugin m_asPlugin;
    private static UBGuiPlugin m_wsPlugin;
    private static UBGuiPlugin m_orPlugin;
    private static UBGuiPlugin m_odPlugin;
    private static UBGuiPlugin m_mssPlugin;
    private static MSGuiPlugin m_msngrPlugin;
    private static NSGuiPlugin m_nsPlugin;
    private static AdapterGuiPlugin m_adapterPlugin;
    private static AiaGuiPlugin m_aiaPlugin;
    private static WsaGuiPlugin m_wsaPlugin;
    public String[] m_args;
    public String m_personality;
    public String m_rmiHost;
    public String m_rmiPort;
    public String m_adminSrvrURL;
    public String m_adminSrvrBindName;
    public String m_connectUserName;
    public String m_connectUserPwd;
    public PropMgrPlugin m_pmpObject;
    public IChimeraHierarchy m_wkRemote;
    public AbstractGuiPluginRemObj m_remoteObject;
    public String m_groupPrefix;
    public ToolRemoteCmdDescriptor m_cmdDescriptor;
    public ToolRemoteCmdStatus m_cmdStatus;
    private IEventStream m_myEvtStream;
    
    public static String mapToPluginName(final String s) {
        String canonicalName = "";
        String anObject;
        if (s.equals("WS")) {
            anObject = "WebSpeed";
        }
        else if (s.equals("AS")) {
            anObject = "AppServer";
        }
        else if (s.equals("NS")) {
            anObject = "NameServer";
        }
        else {
            anObject = s;
        }
        final Vector serverPluginInfo = AdminServer.getPluginMgr().getServerPluginInfo();
        if (serverPluginInfo != null) {
            final Enumeration<ServerPluginInfo> elements = serverPluginInfo.elements();
            while (elements.hasMoreElements()) {
                final ServerPluginInfo serverPluginInfo2 = elements.nextElement();
                final String[] args = serverPluginInfo2.getArgs();
                if (((args == null || args.length == 0) ? "" : args[0]).trim().equals(anObject) && serverPluginInfo2.getPluginInstance() instanceof IManagedPlugin) {
                    canonicalName = ((IManagedPlugin)serverPluginInfo2.getPluginInstance()).getPluginComponent().getCanonicalName();
                    break;
                }
            }
        }
        return canonicalName;
    }
    
    public static String getInstanceNameOnly(String substring) {
        final int lastIndex = substring.lastIndexOf(".");
        if (lastIndex >= 0) {
            substring = substring.substring(lastIndex + 1);
        }
        return substring;
    }
    
    public static void setRPM(final IPropertyManagerRemote rpm) {
        AbstractGuiPlugin.m_rpm = rpm;
    }
    
    public static IPropertyManagerRemote getRPM() {
        if (!AbstractUbrokerPlugin.getWarmStartInProgress()) {
            return AbstractGuiPlugin.m_rpm;
        }
        return null;
    }
    
    public static IEventBroker getEventBroker() {
        if (AbstractGuiPlugin.m_eventBroker == null) {
            AbstractGuiPlugin.m_eventBroker = AbstractUbrokerPlugin.getEventBroker();
        }
        return AbstractGuiPlugin.m_eventBroker;
    }
    
    public IEventStream getEventStream(final String s) {
        if (this.m_myEvtStream == null) {
            try {
                this.m_myEvtStream = getEventBroker().openEventStream("EventStream_For_" + s);
            }
            catch (Exception ex) {
                UBToolsMsg.logException("Failed to open event stream for " + s + ": " + ex.toString());
            }
        }
        return this.m_myEvtStream;
    }
    
    public static void setPropSchemaFnInfo(final String[] schemaPropFnList, final String propFilename, final String schemaFilename) {
        AbstractGuiPlugin.m_schemaPropFnList = schemaPropFnList;
        AbstractGuiPlugin.m_schemaFilename = schemaFilename;
        AbstractGuiPlugin.m_propFilename = propFilename;
    }
    
    public static String getPropertyFilename() {
        return AbstractGuiPlugin.m_propFilename;
    }
    
    public static String getSchemaFilename() {
        return AbstractGuiPlugin.m_schemaFilename;
    }
    
    public static String[] getSchemaPropFnList() {
        return AbstractGuiPlugin.m_schemaPropFnList;
    }
    
    public static void setSharedResourcesInited(final boolean sharedResourcesInited) {
        AbstractGuiPlugin.m_sharedResourcesInited = sharedResourcesInited;
    }
    
    public static boolean getSharedResourcesInited() {
        return AbstractGuiPlugin.m_sharedResourcesInited;
    }
    
    public static void setAdminServerIPAddr() {
        try {
            AbstractGuiPlugin.m_admSrvrIPAddr = InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception ex) {}
    }
    
    public static String getAdminServerIPAddr() {
        return AbstractGuiPlugin.m_admSrvrIPAddr;
    }
    
    public static boolean getRscLookedUp(final String key) {
        final Boolean value = AbstractGuiPlugin.m_lookupClients.get(key);
        return value != null && value;
    }
    
    public static void regRscLookedUp(final String s) {
        if (AbstractGuiPlugin.m_lookupClients.get(s) == null) {
            AbstractGuiPlugin.m_lookupClients.put(s, new Boolean(true));
        }
    }
    
    public static synchronized void unregAllRscLookedUp() {
        AbstractGuiPlugin.m_lookupClients.clear();
    }
    
    public static boolean initSharedResources(final PropMgrPlugin propMgrPlugin) {
        if (getSharedResourcesInited()) {
            return true;
        }
        try {
            setRPM(propMgrPlugin.getRPM());
            getEventBroker();
            setAdminServerIPAddr();
            final String[] schemaPropFilename = propMgrPlugin.getSchemaPropFilename();
            if (schemaPropFilename != null) {
                setPropSchemaFnInfo(schemaPropFilename, propMgrPlugin.getPropFilename(), propMgrPlugin.getSchemaFilename());
                setSharedResourcesInited(true);
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public AbstractGuiPlugin() {
        this.PLUGIN_ID = "";
        this.m_pluginComponent = null;
        this.m_personality = null;
        this.m_rmiHost = null;
        this.m_rmiPort = null;
        this.m_adminSrvrURL = null;
        this.m_adminSrvrBindName = null;
        this.m_connectUserName = null;
        this.m_connectUserPwd = null;
        this.m_pmpObject = null;
        this.m_wkRemote = null;
        this.m_remoteObject = null;
        this.m_groupPrefix = null;
        this.m_cmdDescriptor = null;
        this.m_cmdStatus = null;
        this.m_myEvtStream = null;
        this.m_adminSrvrBindName = "Chimera";
        this.m_cmdDescriptor = new ToolRemoteCmdDescriptor();
        this.m_cmdStatus = new ToolRemoteCmdStatus();
    }
    
    public boolean init(final int myIndex, final IAdministrationServer administrationServer, final String[] args) {
        super.init(myIndex, administrationServer, args);
        super.m_myIndex = myIndex;
        this.m_args = args;
        final String s = this.m_args[0];
        this.m_personality = s;
        this.PLUGIN_ID = s;
        this.getHostPortInfo();
        checkOS();
        if (!AbstractGuiPlugin.osIsSupported) {
            UBToolsMsg.logMsg(UBToolsMsg.getMsg(7094295313015382284L, new Object[] { this.m_personality, LoadablePlatform.getInvalidOSStr() }));
            return false;
        }
        this.m_pmpObject = PropMgrPluginFinder.get();
        if (this.m_pmpObject == null) {
            UBToolsMsg.logMsg(UBToolsMsg.getMsg(7094295313015381953L));
            return false;
        }
        String groupPrefix = null;
        try {
            if (this instanceof AdapterGuiPlugin) {
                AbstractGuiPlugin.m_adapterPlugin = (AdapterGuiPlugin)this;
                groupPrefix = "Adapter";
                this.m_remoteObject = new AdapterRemoteObject((AdapterGuiPlugin)this, this.m_personality, this.m_pmpObject);
            }
            else if (this instanceof AiaGuiPlugin) {
                AbstractGuiPlugin.m_aiaPlugin = (AiaGuiPlugin)this;
                groupPrefix = "AIA";
                this.m_remoteObject = new AiaRemoteObject((AiaGuiPlugin)this, this.m_personality, this.m_pmpObject);
            }
            else if (this instanceof WsaGuiPlugin) {
                AbstractGuiPlugin.m_wsaPlugin = (WsaGuiPlugin)this;
                groupPrefix = "WSA";
                this.m_remoteObject = new WsaPluginRemoteObject((WsaGuiPlugin)this, this.m_personality, this.m_pmpObject);
            }
            else if (this instanceof UBGuiPlugin) {
                if (this.m_personality.equals("AppServer")) {
                    AbstractGuiPlugin.m_asPlugin = (UBGuiPlugin)this;
                    groupPrefix = "UBroker.AS";
                }
                else if (this.m_personality.equals("WebSpeed")) {
                    AbstractGuiPlugin.m_wsPlugin = (UBGuiPlugin)this;
                    groupPrefix = "UBroker.WS";
                }
                else if (this.m_personality.equals("Oracle DataServer")) {
                    AbstractGuiPlugin.m_orPlugin = (UBGuiPlugin)this;
                    groupPrefix = "UBroker.OR";
                }
                else if (this.m_personality.equals("ODBC DataServer")) {
                    AbstractGuiPlugin.m_odPlugin = (UBGuiPlugin)this;
                    groupPrefix = "UBroker.OD";
                }
                else if (this.m_personality.equals("MSS DataServer")) {
                    AbstractGuiPlugin.m_mssPlugin = (UBGuiPlugin)this;
                    groupPrefix = "UBroker.MS";
                }
                this.m_remoteObject = new UBRemoteObject((UBGuiPlugin)this, this.m_personality, this.m_pmpObject, administrationServer);
            }
            else if (this instanceof NSGuiPlugin) {
                AbstractGuiPlugin.m_nsPlugin = (NSGuiPlugin)this;
                groupPrefix = "NameServer";
                this.m_remoteObject = new NSRemoteObject((NSGuiPlugin)this, this.m_personality, this.m_pmpObject);
            }
            else if (this instanceof MSGuiPlugin) {
                AbstractGuiPlugin.m_msngrPlugin = (MSGuiPlugin)this;
                groupPrefix = "WebSpeed.Messengers";
                this.m_remoteObject = new MSRemoteObject((MSGuiPlugin)this, this.m_personality, this.m_pmpObject);
            }
        }
        catch (Exception ex) {
            ProLog.logd("OpenEdge", 1, "Unable to create remote object for " + this.m_personality + " plugin: " + ex.getMessage());
            return false;
        }
        this.setGroupPrefix(groupPrefix);
        this.m_wkRemote = this.m_remoteObject;
        return initSharedResources(this.m_pmpObject);
    }
    
    public static AdapterGuiPlugin getAdapterPlugin() {
        return AbstractGuiPlugin.m_adapterPlugin;
    }
    
    public static AiaGuiPlugin getAiaPlugin() {
        return AbstractGuiPlugin.m_aiaPlugin;
    }
    
    public static WsaGuiPlugin getWsaPlugin() {
        return AbstractGuiPlugin.m_wsaPlugin;
    }
    
    public static UBGuiPlugin getWebSpeedPlugin() {
        return AbstractGuiPlugin.m_wsPlugin;
    }
    
    public static UBGuiPlugin getAppServerPlugin() {
        return AbstractGuiPlugin.m_asPlugin;
    }
    
    public static UBGuiPlugin getOracleDataServerPlugin() {
        return AbstractGuiPlugin.m_orPlugin;
    }
    
    public static UBGuiPlugin getOdbcDataServerPlugin() {
        return AbstractGuiPlugin.m_odPlugin;
    }
    
    public static UBGuiPlugin getMssDataServerPlugin() {
        return AbstractGuiPlugin.m_mssPlugin;
    }
    
    public static NSGuiPlugin getNameServerPlugin() {
        return AbstractGuiPlugin.m_nsPlugin;
    }
    
    public static MSGuiPlugin getMsngrPlugin() {
        return AbstractGuiPlugin.m_msngrPlugin;
    }
    
    public Remote getRemoteObject(final String connectUserName, final String connectUserPwd) {
        this.m_adminSrvrURL = "rmi://" + this.m_rmiHost + ":" + this.m_rmiPort + "/" + this.m_adminSrvrBindName;
        this.m_connectUserName = connectUserName;
        this.m_connectUserPwd = connectUserPwd;
        return this.m_wkRemote;
    }
    
    public void initManagedPlugin(final AbstractPluginComponent abstractPluginComponent) {
        this.m_pluginComponent = abstractPluginComponent;
        this.m_pmpObject.addCanonicalName(this.m_pmpObject.getParentGroupValue(this.m_personality), this.m_pluginComponent.getCanonicalName());
        try {
            getEventBroker().expressInterest(EPMPWarmStartBeganEvent.class, new PMPWarmStartBeganListener(this), this.getEventStream(this.m_personality));
            getEventBroker().expressInterest(EPMPWarmStartFinishedEvent.class, new PMPWarmStartFinishedListener(this), this.getEventStream(this.m_personality));
        }
        catch (Exception ex) {}
        this.m_pluginComponent.start();
    }
    
    public AbstractPluginComponent getPluginComponent() {
        return this.m_pluginComponent;
    }
    
    public abstract String getComponentClassName();
    
    public void setComponentState(final int n) {
        ManagementPlugin.setComponentAdapterState(n, this.m_pluginComponent);
    }
    
    public String getPluginName() {
        return this.PLUGIN_ID;
    }
    
    public void setManagementPluginOnline() {
        this.setComponentState(2);
    }
    
    public void setManagementPluginOffline() {
        this.setComponentState(4);
    }
    
    public String getLoginUserName() {
        return this.m_connectUserName;
    }
    
    public String getLoginUserPassword() {
        return this.m_connectUserPwd;
    }
    
    public Hashtable getLogFiles() {
        return null;
    }
    
    public boolean delChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final String value, final IEventStream eventStream) {
        try {
            final Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("pluginName", this.m_personality);
            hashtable.put("instName", value);
            final ContainerDeleteTreeNodeEvent containerDeleteTreeNodeEvent = new ContainerDeleteTreeNodeEvent(remoteStub, chimeraHierarchy, eventStream, hashtable);
            containerDeleteTreeNodeEvent.setSource(this.m_pmpObject.getCanonicalName(this.m_pmpObject.getParentGroupValue(this.m_personality)));
            getEventBroker().postEvent(containerDeleteTreeNodeEvent);
            return true;
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015382235L, new Object[] { value, ex.toString() }));
            return false;
        }
    }
    
    public boolean addChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, final IEventStream eventStream) {
        return this.addChildNode(remoteStub, chimeraHierarchy, chimeraHierarchy2, new Hashtable(), eventStream);
    }
    
    public boolean addChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, Hashtable hashtable, final IEventStream eventStream) {
        try {
            if (hashtable == null) {
                hashtable = new Hashtable<String, String>();
            }
            hashtable.put("pluginName", this.m_personality);
            hashtable.put("instName", chimeraHierarchy2.getDisplayName());
            final ContainerAddTreeNodeEvent containerAddTreeNodeEvent = new ContainerAddTreeNodeEvent(remoteStub, chimeraHierarchy, chimeraHierarchy2, eventStream, hashtable);
            containerAddTreeNodeEvent.setSource(this.m_pmpObject.getCanonicalName(this.m_pmpObject.getParentGroupValue(this.m_personality)));
            getEventBroker().postEvent(containerAddTreeNodeEvent);
            return true;
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015382236L, new Object[] { ex.toString() }));
            return false;
        }
    }
    
    public void postAddSrvrEvent(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final String str, final String s, final IEventStream eventStream) {
        try {
            final EAddSrvrEvent eAddSrvrEvent = new EAddSrvrEvent(remoteStub, chimeraHierarchy, str, s, eventStream);
            eAddSrvrEvent.setSource(this.m_pmpObject.getCanonicalName(this.m_pmpObject.getParentGroupValue(this.m_personality)));
            getEventBroker().postEvent(eAddSrvrEvent);
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post addServer event for " + str + "(" + ex.toString() + ")");
        }
    }
    
    public void postTrimSrvrEvent(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final String str, final String s, final IEventStream eventStream) {
        try {
            final ETrimSrvrEvent eTrimSrvrEvent = new ETrimSrvrEvent(remoteStub, chimeraHierarchy, str, s, eventStream);
            eTrimSrvrEvent.setSource(this.m_pmpObject.getCanonicalName(this.m_pmpObject.getParentGroupValue(this.m_personality)));
            getEventBroker().postEvent(eTrimSrvrEvent);
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post trimServer event for " + str + "(" + ex.toString() + ")");
        }
    }
    
    public void refreshToolPanel(final IChimeraHierarchy chimeraHierarchy, final String s) {
    }
    
    public static void checkOS() {
        if (!AbstractGuiPlugin.osChecked) {
            AbstractGuiPlugin.osIsSupported = LoadablePlatform.validate();
            AbstractGuiPlugin.osChecked = true;
        }
    }
    
    public abstract void shutdown();
    
    public void getHostPortInfo() {
        try {
            this.m_rmiHost = AbstractUbrokerPlugin.m_iAdministrationServer.getHost();
            this.m_rmiPort = AbstractUbrokerPlugin.m_iAdministrationServer.getPort();
        }
        catch (Exception ex) {
            this.m_rmiHost = "localhost";
            this.m_rmiPort = this.getDefaultRMIPort();
        }
        if (this.m_rmiHost == null || this.m_rmiHost.length() == 0) {
            this.m_rmiHost = "localhost";
        }
        if (this.m_rmiPort == null || this.m_rmiPort.length() == 0) {
            this.m_rmiPort = this.getDefaultRMIPort();
        }
    }
    
    private String getDefaultRMIPort() {
        return new Integer(20931).toString();
    }
    
    public AbstractGuiPluginRemObj getRemoteObject() {
        return this.m_remoteObject;
    }
    
    public void setRemoteObject(final AbstractGuiPluginRemObj remoteObject) {
        this.m_remoteObject = remoteObject;
    }
    
    public void setGroupPrefix(final String groupPrefix) {
        this.m_groupPrefix = groupPrefix;
    }
    
    public String getGroupPrefix() {
        return this.m_groupPrefix;
    }
    
    public ToolRemoteCmdDescriptor getToolRemoteCmdDescriptor() {
        return this.m_cmdDescriptor;
    }
    
    public ToolRemoteCmdStatus getToolRemoteCmdStatus() {
        return this.m_cmdStatus;
    }
    
    public boolean createInstance(final String[] array) {
        boolean fetchAddNewGuiToolStatus = false;
        final AbstractGuiPluginRemObj remoteObject = this.getRemoteObject();
        try {
            if (array != null && array.length > 0) {
                final String s = array[0];
                final String s2 = (array.length > 1) ? array[1] : null;
                final String s3 = (array.length > 2) ? array[1] : null;
                final String instanceNameOnly = getInstanceNameOnly(s);
                if (instanceNameOnly != null && remoteObject != null) {
                    if (s2 != null && s3 != null) {
                        this.m_cmdDescriptor.makeAddNewGuiToolPkt(instanceNameOnly, s2, s3);
                    }
                    else if (s2 != null) {
                        this.m_cmdDescriptor.makeAddNewGuiToolPkt(instanceNameOnly, s2);
                    }
                    else {
                        this.m_cmdDescriptor.makeAddNewGuiToolPkt(instanceNameOnly);
                    }
                    this.m_cmdStatus = remoteObject.doRemoteToolCmd(this.m_cmdDescriptor);
                    fetchAddNewGuiToolStatus = this.m_cmdStatus.fetchAddNewGuiToolStatus();
                }
            }
        }
        catch (Exception ex) {
            fetchAddNewGuiToolStatus = false;
        }
        return fetchAddNewGuiToolStatus;
    }
    
    public boolean deleteInstance(String instanceNameOnly) {
        boolean fetchRemoveGuiToolStatus = false;
        final AbstractGuiPluginRemObj remoteObject = this.getRemoteObject();
        final String groupPrefix = this.getGroupPrefix();
        try {
            instanceNameOnly = getInstanceNameOnly(instanceNameOnly);
            if (instanceNameOnly != null && remoteObject != null) {
                this.m_cmdDescriptor.makeRemoveGuiToolPkt(instanceNameOnly, groupPrefix);
                this.m_cmdStatus = remoteObject.doRemoteToolCmd(this.m_cmdDescriptor);
                fetchRemoveGuiToolStatus = this.m_cmdStatus.fetchRemoveGuiToolStatus();
            }
        }
        catch (Exception ex) {
            fetchRemoveGuiToolStatus = false;
        }
        return fetchRemoveGuiToolStatus;
    }
    
    static {
        AbstractGuiPlugin.osChecked = false;
        AbstractGuiPlugin.osIsSupported = true;
        AbstractGuiPlugin.m_rpm = null;
        AbstractGuiPlugin.m_eventBroker = null;
        AbstractGuiPlugin.m_schemaPropFnList = null;
        AbstractGuiPlugin.m_propFilename = null;
        AbstractGuiPlugin.m_schemaFilename = null;
        AbstractGuiPlugin.m_admSrvrIPAddr = null;
        AbstractGuiPlugin.m_sharedResourcesInited = false;
        AbstractGuiPlugin.m_lookupClients = new Hashtable();
        AbstractGuiPlugin.m_asPlugin = null;
        AbstractGuiPlugin.m_wsPlugin = null;
        AbstractGuiPlugin.m_orPlugin = null;
        AbstractGuiPlugin.m_odPlugin = null;
        AbstractGuiPlugin.m_mssPlugin = null;
        AbstractGuiPlugin.m_msngrPlugin = null;
        AbstractGuiPlugin.m_nsPlugin = null;
        AbstractGuiPlugin.m_adapterPlugin = null;
        AbstractGuiPlugin.m_aiaPlugin = null;
        AbstractGuiPlugin.m_wsaPlugin = null;
    }
}
