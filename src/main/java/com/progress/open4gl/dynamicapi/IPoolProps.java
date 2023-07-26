// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.Properties;
import com.progress.open4gl.Open4GLException;

public interface IPoolProps
{
    public static final String ROOT = "ApplicationRuntimeProperties";
    public static final String XML_TYPE = "IPoolProps";
    public static final int _RUNTIME_PROPERTY_VERSION = 4;
    public static final long serialVersionUID = -966098172748195846L;
    public static final String RUNTIME_PROPERTY_VERSION = "runtimePropertyVersion";
    public static final String _APPSERVICE_PROTOCOL = "Appserver";
    public static final String _APPSERVICE_HOST = "localhost";
    public static final int _APPSERVICE_PORT = 5162;
    public static final String _APPSERVICE_NAME = "asbroker1";
    public static final long _STALE_OBJECT_TIMEOUT = 0L;
    public static final int _MIN_SESSIONS = 1;
    public static final int _MAX_SESSIONS = 0;
    public static final int _INITIAL_SESSIONS = 1;
    public static final long _IDLE_SESSION_TIMEOUT = 0L;
    public static final long _REQUEST_WAIT_TIMEOUT = -1L;
    public static final int _NS_CLIENT_MIN_PORT = 0;
    public static final int _NS_CLIENT_MAX_PORT = 0;
    public static final int _NS_CLIENT_RETRY = 3;
    public static final int _NS_CLIENT_RETRY_INTERVAL = 200;
    public static final int _NS_CLIENT_PICKLIST = 8;
    public static final int _NS_CLIENT_PICKLIST_EXP = 300;
    public static final int _SERVICE_AVAILABLE = 1;
    public static final int _SERVICE_LOGGING_LEVEL = 2;
    public static final long _SERVICE_LOGGING_ENTRIES = 0L;
    public static final String _SERVICE_LOGGING_ENTRY_TYPES = "AppDefault";
    public static final int _APPSERVICE_CONNECTION_MODE = 0;
    public static final int _SERVICE_FAULT_LEVEL = 2;
    public static final int _WAIT_IF_BUSY = 0;
    public static final long _CONNECTION_LIFETIME = 0L;
    public static final int _MIN_IDLE_CONNECTIONS = 0;
    public static final int _ENABLE_TRACING = 0;
    public static final String _LOGFILE_NAME = "";
    public static final int _TRACE_LEVEL = 0;
    public static final String _LOGGING_ENTRY_TYPES = "O4glDefault";
    public static final String _PROXY_HOST = "";
    public static final int _PROXY_PORT = 0;
    public static final String _PROXY_USERID = "";
    public static final String _PROXY_PASSWORD = "";
    public static final String _SESSION_URL = "";
    public static final String _SESSION_USERID = "";
    public static final String _SESSION_PASSWORD = "";
    public static final String _SESSION_APPSERVERINFO = "";
    public static final String _SSL_CERTIFICATE_STORE = "psccerts.jar";
    public static final String _SSL_WIN_CERTIFICATE_STORE = "psccerts.zip";
    public static final String _SSL_APPLET_CERTIFICATE_STORE = "psccerts.dcl";
    public static final int _SSL_NO_HOST_VERIFY = 0;
    public static final int _SSL_NO_SESSION_REUSE = 0;
    public static final String _APPLICATION_UUID = "O4GL";
    public static final String _APPSERVER_VERSION = "V10";
    public static final int _SOCKET_TIMEOUT = 3000;
    public static final int _SERIALIZE_DATASET_AS_XML = 0;
    public static final String X_SERIALIZE_DATASET_AS_XML = "serializeDatasetAsXML";
    public static final int _DATASET_SET_NULL_INITIALS = 0;
    public static final String _ACTIONAL_GROUP = "OpenEdge";
    public static final int _DISABLE_READ_THREAD = 1;
    public static final boolean _POSTPONE_RELATION_INFO = false;
    public static final String _APPSERVER_KEEPALIVE_CAPS = "denyClientASK,allowServerASK";
    public static final int _CASK_ACTIVITY_TIMEOUT = 60;
    public static final int _CASK_RESPONSE_TIMEOUT = 60;
    public static final String X_ACTIONAL_GROUP_NAME = "actionalGroupName";
    public static final String X_DISABLE_READ_THREAD = "disableReadThread";
    public static final String X_DATASET_SET_NULL_INITIALS = "datasetSetNullInitials";
    public static final String X_APPSERVER_KEEPALIVE_CAPS = "appServerKeepalive";
    public static final String X_CASK_ACTIVITY_TIMEOUT = "clientASKActivityTimeout";
    public static final String X_CASK_RESPONSE_TIMEOUT = "clientASKResponseTimeout";
    public static final String X_POSTPONE_RELATION_INFO = "postponeRelationInfo";
    public static final String X_SSL_NO_HOST_VERIFY = "noHostVerify";
    public static final String X_SSL_NO_SESSION_REUSE = "noSessionReuse";
    public static final String X_APPSERVICE_PROTOCOL = "appServiceProtocol";
    public static final String X_APPSERVICE_HOST = "appServiceHost";
    public static final String X_APPSERVICE_PORT = "appServicePort";
    public static final String X_APPSERVICE_NAME = "appServiceName";
    public static final String X_STALE_OBJECT_TIMEOUT = "staleO4GLObjectTimeout";
    public static final String X_MIN_SESSIONS = "minSessions";
    public static final String X_MAX_SESSIONS = "maxSessions";
    public static final String X_INITIAL_SESSIONS = "initialSessions";
    public static final String X_IDLE_SESSION_TIMEOUT = "idleSessionTimeout";
    public static final String X_REQUEST_WAIT_TIMEOUT = "requestWaitTimeout";
    public static final String X_NS_CLIENT_MIN_PORT = "nsClientMinPort";
    public static final String X_NS_CLIENT_MAX_PORT = "nsClientMaxPort";
    public static final String X_NS_CLIENT_RETRY = "nsClientPortRetry";
    public static final String X_NS_CLIENT_RETRY_INTERVAL = "nsClientPortRetryInterval";
    public static final String X_NS_CLIENT_PICKLIST = "nsClientPicklistSize";
    public static final String X_NS_CLIENT_PICKLIST_EXP = "nsClientPicklistExpiration";
    public static final String X_SERVICE_AVAILABLE = "serviceAvailable";
    public static final String X_SERVICE_LOGGING_LEVEL = "serviceLoggingLevel";
    public static final String X_SERVICE_LOGGING_ENTRIES = "serviceLoggingEntries";
    public static final String X_SERVICE_LOGGING_ENTRY_TYPES = "serviceLoggingEntryTypes";
    public static final String X_APPSERVICE_CONNECTION_MODE = "appServiceConnectionMode";
    public static final String X_SERVICE_FAULT_LEVEL = "serviceFaultLevel";
    public static final String X_WAIT_IF_BUSY = "waitIfBusy";
    public static final String X_CONNECTION_LIFETIME = "connectionLifetime";
    public static final String X_MIN_IDLE_CONNECTIONS = "minIdleConnections";
    public static final String PROPNAME_PREFIX = "PROGRESS";
    public static final String APPSERVICE_PROTOCOL = "PROGRESS.Session.appServiceProtocol";
    public static final String APPSERVICE_HOST = "PROGRESS.Session.appServiceHost";
    public static final String APPSERVICE_PORT = "PROGRESS.Session.appServicePort";
    public static final String APPSERVICE_NAME = "PROGRESS.Session.appServiceName";
    public static final String STALE_OBJECT_TIMEOUT = "PROGRESS.Session.staleO4GLObjectTimeout";
    public static final String MIN_SESSIONS = "PROGRESS.Session.minConnections";
    public static final String MAX_SESSIONS = "PROGRESS.Session.maxConnections";
    public static final String INITIAL_SESSIONS = "PROGRESS.Session.initialConnections";
    public static final String IDLE_SESSION_TIMEOUT = "PROGRESS.Session.idleConnectionTimeout";
    public static final String REQUEST_WAIT_TIMEOUT = "PROGRESS.Session.requestWaitTimeout";
    public static final String NS_CLIENT_MIN_PORT = "PROGRESS.Session.nsClientMinPort";
    public static final String NS_CLIENT_MAX_PORT = "PROGRESS.Session.nsClientMaxPort";
    public static final String NS_CLIENT_RETRY = "PROGRESS.Session.nsClientPortRetry";
    public static final String NS_CLIENT_RETRY_INTERVAL = "PROGRESS.Session.nsClientPortRetryInterval";
    public static final String NS_CLIENT_PICKLIST = "PROGRESS.Session.nsClientPicklistSize";
    public static final String NS_CLIENT_PICKLIST_EXP = "PROGRESS.Session.nsClientPicklistExpiration";
    public static final String SERVICE_AVAILABLE = "PROGRESS.Session.serviceAvailable";
    public static final String SERVICE_LOGGING_LEVEL = "PROGRESS.Session.serviceLoggingLevel";
    public static final String SERVICE_LOGGING_ENTRIES = "PROGRESS.Session.serviceLoggingEntries";
    public static final String SERVICE_LOGGING_ENTRY_TYPES = "PROGRESS.Session.serviceLoggingEntryTypes";
    public static final String APPSERVICE_CONNECTION_MODE = "PROGRESS.Session.sessionModel";
    public static final String SERVICE_FAULT_LEVEL = "PROGRESS.Session.serviceFaultLevel";
    public static final String WAIT_IF_BUSY = "PROGRESS.Session.waitIfBusy";
    public static final String CONNECTION_LIFETIME = "PROGRESS.Session.connectionLifetime";
    public static final String MIN_IDLE_CONNECTIONS = "PROGRESS.Session.minIdleConnections";
    public static final String ENABLE_TRACING = "PROGRESS.Session.enableTracing";
    public static final String LOGFILE_NAME = "PROGRESS.Session.logfileName";
    public static final String TRACE_LEVEL = "PROGRESS.Session.traceLevel";
    public static final String LOGGING_ENTRY_TYPES = "PROGRESS.Session.logEntryTypes";
    public static final String PROXY_HOST = "PROGRESS.Session.proxyHost";
    public static final String PROXY_PORT = "PROGRESS.Session.proxyPort";
    public static final String PROXY_USERID = "PROGRESS.Session.proxyUserId";
    public static final String PROXY_PASSWORD = "PROGRESS.Session.proxyPassword";
    public static final String SESSION_URL = "PROGRESS.Session.url";
    public static final String SESSION_USERID = "PROGRESS.Session.userId";
    public static final String SESSION_PASSWORD = "PROGRESS.Session.password";
    public static final String SESSION_APPSERVERINFO = "PROGRESS.Session.appServerInfo";
    public static final String SESSION_POOL_NAME = "PROGRESS.Session.poolName";
    public static final String APPLICATION_UUID = "PROGRESS.Session.UUID";
    public static final String SSL_CERTIFICATE_STORE = "PROGRESS.Session.certificateStore";
    public static final String SSL_NO_HOST_VERIFY = "PROGRESS.Session.noHostVerify";
    public static final String SSL_NO_SESSION_REUSE = "PROGRESS.Session.noSslSessionReuse";
    public static final String SOCKET_TIMEOUT = "PROGRESS.Session.socketTimeout";
    public static final String HTTP_TIMEOUT = "PROGRESS.Session.httpTimeout";
    public static final String SERIALIZE_DATASET_AS_XML = "PROGRESS.Session.serializeDatasetAsXML";
    public static final String APPSERVER_KEEPALIVE_CAPS = "PROGRESS.Session.appServerKeepalive";
    public static final String CASK_ACTIVITY_TIMEOUT = "PROGRESS.Session.clientASKActivityTimeout";
    public static final String CASK_RESPONSE_TIMEOUT = "PROGRESS.Session.clientASKResponseTimeout";
    public static final String DATASET_SET_NULL_INITIALS = "PROGRESS.Session.datasetSetNullInitials";
    public static final String ACTIONAL_GROUP_NAME = "PROGRESS.Session.actionalGroupName";
    public static final String ACTIONAL_MANIFEST = "PROGRESS.Session.manifest";
    public static final String DISABLE_READ_THREAD = "PROGRESS.Session.disableReadThread";
    public static final String POSTPONE_RELATION_INFO = "PROGRESS.Session.postponeRelationInfo";
    public static final int SM_SESSION_MANAGED = 0;
    public static final int SM_SESSION_FREE = 1;
    public static final int INVALID_ASK_VERSION = 0;
    public static final int ASK_MAJOR_VER = 1;
    public static final int ASK_MINOR_VER = 0;
    public static final int ASK_VERSION = 65536;
    public static final int ASK_DISABLED = 0;
    public static final int ASK_SERVERASK_ENABLED = 1;
    public static final int ASK_CLIENTASK_ENABLED = 2;
    public static final int ASK_DEFAULT = 1;
    public static final String[] runtimePropNames = { "PROGRESS.Session.serviceLoggingLevel", "PROGRESS.Session.serviceFaultLevel" };
    public static final String[] defaultPropNames = { "PROGRESS.Session.appServiceProtocol", "PROGRESS.Session.appServiceHost", "PROGRESS.Session.appServicePort", "PROGRESS.Session.appServiceName", "PROGRESS.Session.staleO4GLObjectTimeout", "PROGRESS.Session.minConnections", "PROGRESS.Session.maxConnections", "PROGRESS.Session.initialConnections", "PROGRESS.Session.idleConnectionTimeout", "PROGRESS.Session.requestWaitTimeout", "PROGRESS.Session.nsClientMinPort", "PROGRESS.Session.nsClientMaxPort", "PROGRESS.Session.nsClientPortRetry", "PROGRESS.Session.nsClientPortRetryInterval", "PROGRESS.Session.nsClientPicklistSize", "PROGRESS.Session.nsClientPicklistExpiration", "PROGRESS.Session.serviceLoggingLevel", "PROGRESS.Session.serviceFaultLevel", "PROGRESS.Session.waitIfBusy", "PROGRESS.Session.connectionLifetime", "PROGRESS.Session.minIdleConnections", "PROGRESS.Session.noHostVerify", "PROGRESS.Session.noSslSessionReuse", "PROGRESS.Session.appServerKeepalive", "PROGRESS.Session.clientASKActivityTimeout", "PROGRESS.Session.clientASKResponseTimeout", "PROGRESS.Session.disableReadThread" };
    public static final String[] sessionFreePropNames = { "PROGRESS.Session.appServiceProtocol", "PROGRESS.Session.appServiceHost", "PROGRESS.Session.appServicePort", "PROGRESS.Session.appServiceName", "PROGRESS.Session.staleO4GLObjectTimeout", "PROGRESS.Session.minConnections", "PROGRESS.Session.maxConnections", "PROGRESS.Session.initialConnections", "PROGRESS.Session.idleConnectionTimeout", "PROGRESS.Session.requestWaitTimeout", "PROGRESS.Session.nsClientMinPort", "PROGRESS.Session.nsClientMaxPort", "PROGRESS.Session.nsClientPortRetry", "PROGRESS.Session.nsClientPortRetryInterval", "PROGRESS.Session.nsClientPicklistSize", "PROGRESS.Session.nsClientPicklistExpiration", "PROGRESS.Session.serviceLoggingLevel", "PROGRESS.Session.serviceFaultLevel", "PROGRESS.Session.waitIfBusy", "PROGRESS.Session.connectionLifetime", "PROGRESS.Session.minIdleConnections", "PROGRESS.Session.noHostVerify", "PROGRESS.Session.noSslSessionReuse", "PROGRESS.Session.appServerKeepalive", "PROGRESS.Session.clientASKActivityTimeout", "PROGRESS.Session.clientASKResponseTimeout", "PROGRESS.Session.disableReadThread" };
    public static final String[] sessionManagedPropNames = { "PROGRESS.Session.appServiceProtocol", "PROGRESS.Session.appServiceHost", "PROGRESS.Session.appServicePort", "PROGRESS.Session.appServiceName", "PROGRESS.Session.staleO4GLObjectTimeout", "PROGRESS.Session.requestWaitTimeout", "PROGRESS.Session.nsClientMinPort", "PROGRESS.Session.nsClientMaxPort", "PROGRESS.Session.nsClientPortRetry", "PROGRESS.Session.nsClientPortRetryInterval", "PROGRESS.Session.serviceLoggingLevel", "PROGRESS.Session.serviceFaultLevel", "PROGRESS.Session.waitIfBusy", "PROGRESS.Session.connectionLifetime", "PROGRESS.Session.noHostVerify", "PROGRESS.Session.noSslSessionReuse", "PROGRESS.Session.appServerKeepalive", "PROGRESS.Session.clientASKActivityTimeout", "PROGRESS.Session.clientASKResponseTimeout", "PROGRESS.Session.disableReadThread" };
    public static final String[] askPropNames = { "PROGRESS.Session.appServerKeepalive", "PROGRESS.Session.clientASKActivityTimeout", "PROGRESS.Session.clientASKResponseTimeout" };
    
    void setProperty(final String p0, final Object p1);
    
    Object getProperty(final String p0);
    
    Object setStringProperty(final String p0, final String p1);
    
    Object setLongProperty(final String p0, final long p1);
    
    Object setIntProperty(final String p0, final int p1);
    
    Object setBooleanProperty(final String p0, final boolean p1);
    
    String getStringProperty(final String p0);
    
    int getIntProperty(final String p0);
    
    long getLongProperty(final String p0);
    
    boolean getBooleanProperty(final String p0);
    
    SessionPool getSessionPool();
    
    void addReference(final SessionPool p0);
    
    void releaseConnection() throws Open4GLException;
    
    Properties getAsProperties();
}
