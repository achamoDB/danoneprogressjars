// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Properties;
import com.progress.common.util.UUID;
import com.progress.open4gl.dynamicapi.Tracer;
import com.progress.open4gl.javaproxy.Connection;

public class RunTimeProperties
{
    private static Connection root_props;
    public static final Tracer tracer;
    private static boolean tracing;
    
    public static int getDynamicApiVersion() {
        return 5;
    }
    
    public static int getStreamProtocolVersion() {
        return 82;
    }
    
    public static void traceOn() {
        RunTimeProperties.tracing = true;
        RunTimeProperties.tracer.startTrace();
    }
    
    public static void traceOn(final int n) {
        if (n > 0) {
            RunTimeProperties.tracing = true;
        }
        RunTimeProperties.tracer.startTrace(n);
    }
    
    public static void traceOn(final String s) {
        RunTimeProperties.tracing = true;
        RunTimeProperties.tracer.startTrace(s);
    }
    
    public static void traceOn(final String s, final int n) {
        if (n > 0) {
            RunTimeProperties.tracing = true;
        }
        RunTimeProperties.tracer.startTrace(s, n);
    }
    
    public static void traceOff() {
        RunTimeProperties.tracing = false;
        RunTimeProperties.tracer.endTrace();
    }
    
    public static boolean isTracing() {
        return RunTimeProperties.tracing;
    }
    
    public static void setWaitIfBusy() {
        RunTimeProperties.root_props.setWaitIfBusy();
    }
    
    public static void setNoWaitIfBusy() {
        RunTimeProperties.root_props.setNoWaitIfBusy();
    }
    
    public static boolean getWaitIfBusy() {
        return RunTimeProperties.root_props.getWaitIfBusy();
    }
    
    public static void setProxyHost(final String proxyHost) throws Open4GLException {
        RunTimeProperties.root_props.setProxyHost(proxyHost);
    }
    
    public static String getProxyHost() {
        return RunTimeProperties.root_props.getProxyHost();
    }
    
    public static void setProxyPort(final int proxyPort) throws Open4GLException {
        RunTimeProperties.root_props.setProxyPort(proxyPort);
    }
    
    public static int getProxyPort() {
        return RunTimeProperties.root_props.getProxyPort();
    }
    
    public static void setCertificateStore(final String certificateStore) throws Open4GLException {
        RunTimeProperties.root_props.setCertificateStore(certificateStore);
    }
    
    public static String getCertificateStore() {
        return RunTimeProperties.root_props.getCertificateStore();
    }
    
    public static void setNoHostVerify(final boolean noHostVerify) throws Open4GLException {
        RunTimeProperties.root_props.setNoHostVerify(noHostVerify);
    }
    
    public static boolean getNoHostVerify() {
        return RunTimeProperties.root_props.getNoHostVerify();
    }
    
    public static void setNoSslSessionReuse(final boolean noSslSessionReuse) throws Open4GLException {
        RunTimeProperties.root_props.setNoSslSessionReuse(noSslSessionReuse);
    }
    
    public static boolean getNoSslSessionReuse() {
        return RunTimeProperties.root_props.getNoSslSessionReuse();
    }
    
    public static void setProxyUserId(final String proxyUserId) throws Open4GLException {
        RunTimeProperties.root_props.setProxyUserId(proxyUserId);
    }
    
    public static String getProxyUserId() {
        return RunTimeProperties.root_props.getProxyUserId();
    }
    
    public static void setProxyPassword(final String proxyPassword) throws Open4GLException {
        RunTimeProperties.root_props.setProxyPassword(proxyPassword);
    }
    
    public static String getProxyPassword() {
        return RunTimeProperties.root_props.getProxyPassword();
    }
    
    public static int getSessionModel() {
        return RunTimeProperties.root_props.getSessionModel();
    }
    
    public static void setSessionModel(final int sessionModel) {
        RunTimeProperties.root_props.setSessionModel(sessionModel);
    }
    
    public static int getInitialConnections() {
        return RunTimeProperties.root_props.getInitialConnections();
    }
    
    public static void setInitialConnections(final int initialConnections) {
        RunTimeProperties.root_props.setInitialConnections(initialConnections);
    }
    
    public static int getMinConnections() {
        return RunTimeProperties.root_props.getMinConnections();
    }
    
    public static void setMinConnections(final int minConnections) {
        RunTimeProperties.root_props.setMinConnections(minConnections);
    }
    
    public static int getMaxConnections() {
        return RunTimeProperties.root_props.getMaxConnections();
    }
    
    public static void setMaxConnections(final int maxConnections) {
        RunTimeProperties.root_props.setMaxConnections(maxConnections);
    }
    
    public static int getIdleConnectionTimeout() {
        return RunTimeProperties.root_props.getIdleConnectionTimeout();
    }
    
    public static void setIdleConnectionTimeout(final int idleConnectionTimeout) {
        RunTimeProperties.root_props.setIdleConnectionTimeout(idleConnectionTimeout);
    }
    
    public static int getConnectionLifetime() {
        return RunTimeProperties.root_props.getConnectionLifetime();
    }
    
    public static void setConnectionLifetime(final int connectionLifetime) {
        RunTimeProperties.root_props.setConnectionLifetime(connectionLifetime);
    }
    
    public static int getNsClientMinPort() {
        return RunTimeProperties.root_props.getNsClientMinPort();
    }
    
    public static void setNsClientMinPort(final int nsClientMinPort) {
        RunTimeProperties.root_props.setNsClientMinPort(nsClientMinPort);
    }
    
    public static int getNsClientMaxPort() {
        return RunTimeProperties.root_props.getNsClientMaxPort();
    }
    
    public static void setNsClientMaxPort(final int nsClientMaxPort) {
        RunTimeProperties.root_props.setNsClientMaxPort(nsClientMaxPort);
    }
    
    public static int getNsClientPortRetry() {
        return RunTimeProperties.root_props.getNsClientPortRetry();
    }
    
    public static void setNsClientPortRetry(final int nsClientPortRetry) {
        RunTimeProperties.root_props.setNsClientPortRetry(nsClientPortRetry);
    }
    
    public static int getNsClientPortRetryInterval() {
        return RunTimeProperties.root_props.getNsClientPortRetryInterval();
    }
    
    public static void setNsClientPortRetryInterval(final int nsClientPortRetryInterval) {
        RunTimeProperties.root_props.setNsClientPortRetryInterval(nsClientPortRetryInterval);
    }
    
    public static int getNsClientPicklistSize() {
        return RunTimeProperties.root_props.getNsClientPicklistSize();
    }
    
    public static void setNsClientPicklistSize(final int nsClientPicklistSize) {
        RunTimeProperties.root_props.setNsClientPicklistSize(nsClientPicklistSize);
    }
    
    public static int getRequestWaitTimeout() {
        return RunTimeProperties.root_props.getRequestWaitTimeout();
    }
    
    public static void setRequestWaitTimeout(final int requestWaitTimeout) {
        RunTimeProperties.root_props.setRequestWaitTimeout(requestWaitTimeout);
    }
    
    public static String getUUID() {
        return RunTimeProperties.root_props.getUUID();
    }
    
    public static void setUUID(final String uuid) {
        RunTimeProperties.root_props.setUUID(uuid);
    }
    
    public static String getLogfileName() {
        return RunTimeProperties.root_props.getLogfileName();
    }
    
    public static void setLogfileName(final String logfileName) {
        RunTimeProperties.root_props.setLogfileName(logfileName);
    }
    
    public static int getTraceLevel() {
        return RunTimeProperties.root_props.getTraceLevel();
    }
    
    public static void setTraceLevel(final int traceLevel) {
        RunTimeProperties.root_props.setTraceLevel(traceLevel);
    }
    
    public static String getLogEntryTypes() {
        return RunTimeProperties.root_props.getLogEntryTypes();
    }
    
    public static void setLogEntryTypes(final String logEntryTypes) {
        RunTimeProperties.root_props.setLogEntryTypes(logEntryTypes);
    }
    
    public static String getAppServerKeepalive() {
        return RunTimeProperties.root_props.getAppServerKeepalive();
    }
    
    public static void setAppServerKeepalive(final String appServerKeepalive) {
        RunTimeProperties.root_props.setAppServerKeepalive(appServerKeepalive);
    }
    
    public static int getClientASKActivityTimeout() {
        return RunTimeProperties.root_props.getClientASKActivityTimeout();
    }
    
    public static void setClientASKActivityTimeout(final int clientASKActivityTimeout) {
        RunTimeProperties.root_props.setClientASKActivityTimeout(clientASKActivityTimeout);
    }
    
    public static int getClientASKResponseTimeout() {
        return RunTimeProperties.root_props.getClientASKResponseTimeout();
    }
    
    public static void setClientASKResponseTimeout(final int clientASKResponseTimeout) {
        RunTimeProperties.root_props.setClientASKResponseTimeout(clientASKResponseTimeout);
    }
    
    public static void setDatasetNullInitials(final boolean datasetNullInitials) {
        RunTimeProperties.root_props.setDatasetNullInitials(datasetNullInitials);
    }
    
    public static boolean getDatasetNullInitials() {
        return RunTimeProperties.root_props.getDatasetNullInitials();
    }
    
    public static void setPostponeRelationInfo(final boolean postponeRelationInfo) {
        RunTimeProperties.root_props.setPostponeRelationInfo(postponeRelationInfo);
    }
    
    public static boolean getPostponeRelationInfo() {
        return RunTimeProperties.root_props.getPostponeRelationInfo();
    }
    
    public static void setStringProperty(final String s, final String s2) {
        RunTimeProperties.root_props.setStringProperty(s, s2);
    }
    
    public static void setIntProperty(final String s, final int n) {
        RunTimeProperties.root_props.setIntProperty(s, n);
    }
    
    public static void setLongProperty(final String s, final long n) {
        RunTimeProperties.root_props.setLongProperty(s, n);
    }
    
    public static void setBooleanProperty(final String s, final boolean b) {
        RunTimeProperties.root_props.setBooleanProperty(s, b);
    }
    
    public static String getStringProperty(final String s) {
        return RunTimeProperties.root_props.getStringProperty(s);
    }
    
    public static int getIntProperty(final String s) {
        return RunTimeProperties.root_props.getIntProperty(s);
    }
    
    public static long getLongProperty(final String s) {
        return RunTimeProperties.root_props.getLongProperty(s);
    }
    
    public static boolean getBooleanProperty(final String s) {
        return RunTimeProperties.root_props.getBooleanProperty(s);
    }
    
    public static Connection getStaticProperties() {
        return RunTimeProperties.root_props;
    }
    
    private static void initializeDefaultProperties(final Connection connection) {
        connection.setIntProperty("runtimePropertyVersion", 4);
        connection.setStringProperty("PROGRESS.Session.appServiceProtocol", "Appserver");
        connection.setStringProperty("PROGRESS.Session.appServiceHost", "localhost");
        connection.setIntProperty("PROGRESS.Session.appServicePort", 5162);
        connection.setStringProperty("PROGRESS.Session.appServiceName", "asbroker1");
        connection.setLongProperty("PROGRESS.Session.staleO4GLObjectTimeout", 0L);
        connection.setIntProperty("PROGRESS.Session.minConnections", 1);
        connection.setIntProperty("PROGRESS.Session.maxConnections", 0);
        connection.setIntProperty("PROGRESS.Session.initialConnections", 1);
        connection.setLongProperty("PROGRESS.Session.idleConnectionTimeout", 0L);
        connection.setLongProperty("PROGRESS.Session.requestWaitTimeout", -1L);
        connection.setIntProperty("PROGRESS.Session.nsClientMinPort", 0);
        connection.setIntProperty("PROGRESS.Session.nsClientMaxPort", 0);
        connection.setIntProperty("PROGRESS.Session.nsClientPortRetry", 3);
        connection.setIntProperty("PROGRESS.Session.nsClientPortRetryInterval", 200);
        connection.setIntProperty("PROGRESS.Session.nsClientPicklistSize", 8);
        connection.setIntProperty("PROGRESS.Session.nsClientPicklistExpiration", 300);
        connection.setIntProperty("PROGRESS.Session.serviceAvailable", 1);
        connection.setIntProperty("PROGRESS.Session.serviceLoggingLevel", 2);
        connection.setStringProperty("PROGRESS.Session.serviceLoggingEntryTypes", "AppDefault");
        connection.setIntProperty("PROGRESS.Session.sessionModel", 0);
        connection.setIntProperty("PROGRESS.Session.serviceFaultLevel", 2);
        connection.setIntProperty("PROGRESS.Session.waitIfBusy", 0);
        connection.setLongProperty("PROGRESS.Session.connectionLifetime", 0L);
        connection.setIntProperty("PROGRESS.Session.minIdleConnections", 0);
        connection.setIntProperty("PROGRESS.Session.enableTracing", 0);
        connection.setStringProperty("PROGRESS.Session.logfileName", "");
        connection.setIntProperty("PROGRESS.Session.traceLevel", 0);
        connection.setStringProperty("PROGRESS.Session.proxyHost", "");
        connection.setIntProperty("PROGRESS.Session.proxyPort", 0);
        connection.setStringProperty("PROGRESS.Session.proxyUserId", "");
        connection.setStringProperty("PROGRESS.Session.proxyPassword", "");
        connection.setStringProperty("PROGRESS.Session.url", "");
        connection.setStringProperty("PROGRESS.Session.userId", "");
        connection.setStringProperty("PROGRESS.Session.password", "");
        connection.setStringProperty("PROGRESS.Session.appServerInfo", "");
        connection.setStringProperty("PROGRESS.Session.certificateStore", "psccerts.jar");
        connection.setIntProperty("PROGRESS.Session.noHostVerify", 0);
        connection.setIntProperty("PROGRESS.Session.noSslSessionReuse", 0);
        connection.setStringProperty("PROGRESS.Session.UUID", new UUID().toString());
        connection.setIntProperty("PROGRESS.Session.socketTimeout", 3000);
        connection.setStringProperty("PROGRESS.Session.appServerKeepalive", "denyClientASK,allowServerASK");
        connection.setIntProperty("PROGRESS.Session.clientASKActivityTimeout", 60);
        connection.setIntProperty("PROGRESS.Session.clientASKResponseTimeout", 60);
        connection.setIntProperty("PROGRESS.Session.datasetSetNullInitials", 0);
        connection.setIntProperty("PROGRESS.Session.disableReadThread", 1);
    }
    
    private static void setSystemProperties(final Connection connection) {
        final Properties properties = System.getProperties();
        final Enumeration<String> keys = ((Hashtable<String, V>)properties).keys();
        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            if (key.startsWith("PROGRESS")) {
                connection.setStringProperty(key, properties.getProperty(key));
            }
        }
    }
    
    private static void initializeLogging() {
        if (getIntProperty("PROGRESS.Session.enableTracing") != 0) {
            traceOn(getStringProperty("PROGRESS.Session.logfileName"), getIntProperty("PROGRESS.Session.traceLevel"));
        }
    }
    
    static {
        initializeDefaultProperties(RunTimeProperties.root_props = new Connection(null));
        setSystemProperties(RunTimeProperties.root_props);
        tracer = new Tracer();
        initializeLogging();
    }
}
