// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.chimera.adminserver.AdminServer;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventBroker;
import java.rmi.Remote;
import java.util.Enumeration;
import com.progress.common.comsockagent.ComMsgAgent;
import com.progress.common.exception.ProException;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.EventBroker;
import com.progress.chimera.adminserver.IAdminServerConst;
import com.progress.common.util.IVersionable;
import java.rmi.RemoteException;
import com.progress.common.comsockagent.ServerComSockAgentListener;
import java.io.IOException;
import com.progress.common.log.ProLog;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Hashtable;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.common.comsockagent.ServerComSockAgent;
import java.util.Vector;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.chimera.adminserver.IServerPlugin;
import com.progress.common.property.IPropertyProvider;
import com.progress.chimera.common.ChimeraRemoteObject;

public class AgentPlugIn extends ChimeraRemoteObject implements IAgentAPI, IAgentPlugIn, IPropertyProvider, IServerPlugin
{
    public static final String OS_NAME;
    public static final String NEWLINE;
    public static final String FILE_SEPARATOR;
    protected static IAdministrationServer adminServer;
    protected static PropertyManager properties;
    private static AgentPlugIn self;
    private static Vector m_agentVec;
    private static Integer m_agentPort;
    private static ServerComSockAgent m_serverComSockAgent;
    private static AgentListener m_agentListener;
    private static AgentConnectionMgr m_agentConnectionMgr;
    private static boolean m_isAgentPlugInShuttingDown;
    private static String m_version;
    public static final String PLUGIN_ID = "DatabaseAgent";
    public static ConnectionManagerLog theLog;
    static String propertyFileName;
    private static Hashtable agentDatabases;
    
    public static AgentPlugIn get() {
        return AgentPlugIn.self;
    }
    
    public static String normalizeDatabaseName(String s) {
        s = s.replace('/', AgentPlugIn.FILE_SEPARATOR.charAt(0));
        if (s.endsWith(".db")) {
            s = s.substring(0, s.length() - 3);
        }
        return s;
    }
    
    public static boolean isAgentPlugInShuttingDown() {
        return AgentPlugIn.m_isAgentPlugInShuttingDown;
    }
    
    public static AgentConnectionMgr getAgentConnectionMgr() {
        if (AgentPlugIn.m_agentConnectionMgr == null) {
            AgentPlugIn.m_agentConnectionMgr = new AgentConnectionMgr();
        }
        return AgentPlugIn.m_agentConnectionMgr;
    }
    
    private static void initSocket() throws IOException {
        final String property = AgentPlugIn.properties.getProperty("Agent.port");
        if (property != null) {
            AgentPlugIn.m_agentPort = new Integer(property);
        }
        if (AgentPlugIn.m_agentPort == null) {
            AgentPlugIn.m_agentPort = new Integer(IAgentAPI.DEFAULT_AGENT_PORT);
        }
        InetAddress byName = null;
        try {
            final String property2 = System.getProperty("java.rmi.server.hostname");
            final String property3 = System.getProperty("forceIPver");
            if (property2 != null && property3 != null) {
                byName = InetAddress.getByName(property2);
            }
        }
        catch (UnknownHostException ex2) {}
        if (byName != null) {
            ProLog.logd("DatabaseAgent", 3, "Opening socket connection on port " + (int)AgentPlugIn.m_agentPort + " at address " + byName);
            AgentPlugIn.m_serverComSockAgent = new ServerComSockAgent(AgentPlugIn.m_agentPort, byName);
        }
        else {
            ProLog.logd("DatabaseAgent", 3, "Opening socket connection on port " + (int)AgentPlugIn.m_agentPort);
            AgentPlugIn.m_serverComSockAgent = new ServerComSockAgent(AgentPlugIn.m_agentPort);
        }
        try {
            AgentPlugIn.m_serverComSockAgent.connect();
        }
        catch (IOException ex) {
            final String string = "Error creating listening port [" + AgentPlugIn.m_agentPort + "]: " + ex.getMessage();
            final String s = "Unable to create DB Agent listening port, database monitoring will not be possible.";
            ProLog.logd("DatabaseAgent", 2, string);
            ProLog.logd("DatabaseAgent", 2, s);
            throw ex;
        }
        if (AgentPlugIn.m_serverComSockAgent != null) {
            AgentPlugIn.m_serverComSockAgent.addEventListener(AgentPlugIn.m_agentListener = new AgentListener());
        }
    }
    
    public static ServerComSockAgent getServerComSockAgent() throws IOException {
        if (AgentPlugIn.m_serverComSockAgent == null) {
            initSocket();
        }
        return AgentPlugIn.m_serverComSockAgent;
    }
    
    public AgentPlugIn() throws RemoteException {
        AgentPlugIn.self = this;
    }
    
    public PropertyManager properties() {
        return AgentPlugIn.properties;
    }
    
    public static PropertyManager propertiesS() {
        return AgentPlugIn.properties;
    }
    
    public String getAgentPort() {
        String s = String.valueOf(IAgentAPI.DEFAULT_AGENT_PORT);
        if (AgentPlugIn.m_agentPort != null) {
            s = AgentPlugIn.m_agentPort.toString();
        }
        return s;
    }
    
    public void run() {
    }
    
    public String getVersion() {
        return AgentPlugIn.m_version;
    }
    
    public boolean init(final int n, final IAdministrationServer adminServer, final String[] array) {
        boolean b = false;
        try {
            AgentPlugIn.adminServer = adminServer;
            AgentPlugIn.m_version = ((IVersionable)AgentPlugIn.adminServer).getVersion();
            AgentPlugIn.propertyFileName = adminServer.getDbaPropFile();
            if (AgentPlugIn.propertyFileName == null) {
                AgentPlugIn.propertyFileName = System.getProperty("agent.propertyfile");
            }
            if (AgentPlugIn.propertyFileName == null) {
                AgentPlugIn.propertyFileName = IAdminServerConst.INSTALL_DIR + IAdminServerConst.FILE_SEPARATOR + array[0].replace('/', IAdminServerConst.FILE_SEPARATOR.charAt(0));
                ProLog.log("DatabaseAgent", 2, 7669630165411962937L, new Object[] { AgentPlugIn.propertyFileName });
            }
            else {
                ProLog.log("DatabaseAgent", 2, 7669630165411962938L, new Object[] { AgentPlugIn.propertyFileName });
            }
            AgentPlugIn.properties = new AgentProperties(AgentPlugIn.propertyFileName, (EventBroker)this.getEventBroker());
            b = true;
        }
        catch (PropertyManager.GroupNameException ex) {
            Tools.px(ex, "Property file contains invalid group names.");
        }
        catch (PropertyManager.PropertyNameException ex2) {
            Tools.px(ex2, "Property file contains invalid property names.");
        }
        catch (PropertyManager.PropertyValueException ex3) {
            Tools.px(ex3, "Property file contains invalid property values.");
        }
        catch (PropertyManager.PropertySyntaxException ex4) {
            Tools.px(ex4, "Property file is malformed.");
        }
        catch (PropertyManager.LoadIOException ex5) {
            Tools.px(ex5, "Property file failed to load.");
        }
        catch (PropertyManager.PropertyVersionException ex6) {
            Tools.px(ex6, "Incorrect property file version.");
        }
        catch (PropertyManager.LoadFileNotFoundException ex9) {
            try {
                AgentPlugIn.properties = new AgentProperties(null, (EventBroker)this.getEventBroker());
                ((AgentProperties)AgentPlugIn.properties).generatePropFile(AgentPlugIn.propertyFileName);
                b = true;
            }
            catch (ProException ex7) {
                Tools.px(ex7, "Could not create file: agent.properties.");
            }
        }
        catch (PropertyManager.PropertyException ex8) {
            Tools.px(ex8, ex8.getMessage());
        }
        catch (Throwable t) {
            ProLog.logd("DatabaseAgent", 2, "Unable to load agent properties: " + t.getMessage());
        }
        return b;
    }
    
    public void shutdown() {
        AgentPlugIn.m_isAgentPlugInShuttingDown = true;
        synchronized (this) {
            final Enumeration<AgentDatabase> elements = AgentPlugIn.agentDatabases.elements();
            while (elements.hasMoreElements()) {
                final AgentDatabase agentDatabase = elements.nextElement();
                if (!agentDatabase.isIdle()) {
                    agentDatabase.sendPacket(new ComMsgAgent(400));
                }
            }
        }
        if (AgentPlugIn.m_serverComSockAgent != null) {
            AgentPlugIn.m_serverComSockAgent.disconnect();
        }
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        return this;
    }
    
    public String getOS() throws RemoteException {
        return AgentPlugIn.adminServer.getOS();
    }
    
    public IAdministrationServer getAdminServer() throws RemoteException {
        return AgentPlugIn.adminServer;
    }
    
    public synchronized IEventBroker getEventBroker() {
        try {
            return AgentPlugIn.adminServer.getEventBroker();
        }
        catch (Throwable t) {
            Excp.print(t);
            return null;
        }
    }
    
    public synchronized IEventStream getEventStream() throws RemoteException {
        try {
            return AgentPlugIn.adminServer.getEventStream();
        }
        catch (Throwable t) {
            Excp.print(t);
            return null;
        }
    }
    
    public IAgentPlugIn getPlugIn() throws RemoteException {
        return AgentPlugIn.self;
    }
    
    public IPropertyManagerRemote getPropertyManager() throws RemoteException {
        return AgentPlugIn.properties;
    }
    
    public String getPropertyFileName() {
        return AgentPlugIn.propertyFileName;
    }
    
    public synchronized ILicenseMgr getLicenseManager() throws RemoteException {
        return ((AdminServer)AgentPlugIn.adminServer).getLicenseManager();
    }
    
    public PropertyManager getPlugInPropertyManager() {
        return this.properties();
    }
    
    protected static void addAgentDatabase(final String key, final AgentDatabase value) {
        if (key != null && value != null) {
            AgentPlugIn.agentDatabases.put(key, value);
        }
    }
    
    protected static void removeAgentDatabase(final String key) {
        if (key != null) {
            AgentPlugIn.agentDatabases.remove(key);
        }
    }
    
    public static Enumeration getAgentDatabases() {
        return AgentPlugIn.agentDatabases.elements();
    }
    
    static {
        OS_NAME = System.getProperty("os.name");
        NEWLINE = System.getProperty("line.separator");
        FILE_SEPARATOR = System.getProperty("file.separator");
        AgentPlugIn.adminServer = null;
        AgentPlugIn.properties = null;
        AgentPlugIn.self = null;
        AgentPlugIn.m_agentVec = new Vector();
        AgentPlugIn.m_agentPort = null;
        AgentPlugIn.m_serverComSockAgent = null;
        AgentPlugIn.m_agentListener = null;
        AgentPlugIn.m_agentConnectionMgr = null;
        AgentPlugIn.m_isAgentPlugInShuttingDown = false;
        AgentPlugIn.m_version = "";
        AgentPlugIn.theLog = ConnectionManagerLog.get();
        AgentPlugIn.propertyFileName = null;
        AgentPlugIn.agentDatabases = new Hashtable();
    }
}
