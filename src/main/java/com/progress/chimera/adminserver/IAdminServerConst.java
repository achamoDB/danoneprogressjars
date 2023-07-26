// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

public interface IAdminServerConst
{
    public static final String ADMIN_LOGFILE = "admserv.log";
    public static final String ADMIN_EXCPFILE = "ads0.exp";
    public static final boolean IS_NT = System.getProperty("os.name").startsWith("Windows");
    public static final String PROP_PREFIX = "-D";
    public static final String NT_CLASSNAME = "com.progress.chimera.adminserver.NTAdminServer";
    public static final String NON_NT_CLASSNAME = "com.progress.chimera.adminserver.NonNTAdminServer";
    public static final String ADMIN_SERVER_CLASSNAME = IAdminServerConst.IS_NT ? "com.progress.chimera.adminserver.NTAdminServer" : "com.progress.chimera.adminserver.NonNTAdminServer";
    public static final String ADMIN_STARTER_CLASSNAME = "com.progress.chimera.adminserver.AdminServerStarter";
    public static final String ADMIN_SHUTDOWN_CLASSNAME = "com.progress.chimera.adminserver.AdminServerShutdown";
    public static final String ADMIN_QUERY_CLASSNAME = "com.progress.chimera.adminserver.AdminServerQuery";
    public static final String RMI_REGISTRY_PORT = Integer.toString(20931);
    public static final String RMI_BIND_NAME = "Chimera";
    public static final String PLUGINS_FILE_NAME = "AdminServerPlugins.properties";
    public static final boolean AUTOSTART_RMI_REGISTRY = true;
    public static final String INSTALL_DIR_PROPERTY = "Install.Dir";
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");
    public static final String INSTALL_DIR1 = System.getProperty("Install.Dir");
    public static final String INSTALL_DIR = (IAdminServerConst.INSTALL_DIR1 == null) ? null : IAdminServerConst.INSTALL_DIR1.replace('/', IAdminServerConst.FILE_SEPARATOR.charAt(0));
    public static final String PLUGINS_FULLFILE_NAME = IAdminServerConst.INSTALL_DIR + IAdminServerConst.FILE_SEPARATOR + "properties" + IAdminServerConst.FILE_SEPARATOR + "AdminServerPlugins.properties";
    public static final String SONIC_DIR_PROPERTY = "SONICMQ_HOME";
    public static final String SONIC_CONTAINER_PROPERTY = "SONICMQ_CONTAINER_FILE";
    public static final String SONIC_DS_PROPERTY = "SONICMQ_DS_FILE";
    public static final String SONIC_CONTAINER_FILE = "container.xml";
    public static final String SONIC_DS_FILE = "ds.xml";
    public static final String SONIC_CONTAINER_CLASS_PROPERTY = "sonicsw.mf.containerClass";
    public static final String SONIC_AGENT_CLASS = "com.sonicsw.mf.Agent";
    public static final String SONIC_XBOOTPATH_PROPERTY = "SONICMQ_XBOOTPATH";
    public static final String SONIC_XBOOTTOOLJAR1 = "xercesImpl_mq61.jar";
    public static final String SONIC_XBOOTJAR1 = "xercesImpl.jar";
    public static final String SONIC_XBOOTJAR2 = "xmlParserAPIs.jar";
    public static final String SONIC_XBOOTJAR3 = "jmxri.jar";
    public static final String MF_ENABLED_PROPERTY = "ENABLE_MF";
    public static final String DS_ENABLED_PROPERTY = "ENABLE_DS";
    public static final String SONICMQ_INSTALL_DIR = "MQ6.1";
    public static final String CACHE_LOCK_NAME = "data/_MFSystem/lock";
    public static final String SMQDB_LOCK_NAME = "SonicMQStore/db.lck";
    public static final String ADMSVR_LOCK_NAME = "admsvr.lock";
    public static final String FATHOM_LOCK_NAME = "fathom.odx";
    public static final String[] SONIC_JARS = { "broker.jar", "sonic_Client.jar", "sonic_Crypto.jar", "defdb_server.jar", "cert.jar", "ssl.jar", "sslj.jar", "jsafe.jar", "asn1.jar" };
    public static final String[] SONIC_CLIENT_JARS = { "jmxri.jar", "mgmt_agent.jar", "sonic_Client.jar", "sonic_Crypto.jar" };
    public static final String RMI_REGISTRY_PORT_PROPERTY = "RMIRegistryPort";
}
