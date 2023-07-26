// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.javaproxy;

import java.util.Iterator;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Hashtable;
import java.util.Enumeration;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.dynamicapi.SessionPool;
import java.util.Properties;
import com.progress.open4gl.dynamicapi.IPoolProps;

public class Connection implements IPoolProps
{
    private Properties m_props;
    private Connection m_parent;
    private SessionPool m_sessionPool;
    
    public Connection(final Connection parent) {
        this.m_props = null;
        this.m_parent = null;
        this.m_sessionPool = null;
        this.m_parent = parent;
        this.createProperties((parent == null) ? null : parent.m_props);
    }
    
    public Connection(final String url, final String userId, final String password, final String appServerInfo) {
        this(RunTimeProperties.getStaticProperties());
        this.setUrl(url);
        this.setUserId(userId);
        this.setPassword(password);
        this.setAppServerInfo(appServerInfo);
    }
    
    public Connection(final String userId, final String password, final String appServerInfo) {
        this(RunTimeProperties.getStaticProperties());
        this.setUserId(userId);
        this.setPassword(password);
        this.setAppServerInfo(appServerInfo);
    }
    
    public SessionPool getSessionPool() {
        return this.m_sessionPool;
    }
    
    public void addReference(final SessionPool sessionPool) {
        if (sessionPool != null && this.getIntProperty("PROGRESS.Session.sessionModel") == 1) {
            (this.m_sessionPool = sessionPool).addReference();
        }
    }
    
    public void releaseConnection() throws Open4GLException {
        if (this.m_sessionPool != null) {
            this.m_sessionPool.deleteReference();
        }
        this.m_sessionPool = null;
    }
    
    public void finalize() {
        try {
            this.releaseConnection();
        }
        catch (Open4GLException ex) {}
        this.m_sessionPool = null;
    }
    
    public void setProperty(final String key, final Object value) {
        ((Hashtable<String, Object>)this.m_props).put(key, value);
    }
    
    public Object setStringProperty(final String key, final String original) {
        return this.m_props.setProperty(key, (original == null) ? "" : new String(original));
    }
    
    public Object setIntProperty(final String key, final int value) {
        return this.m_props.setProperty(key, new Integer(value).toString());
    }
    
    public Object setLongProperty(final String key, final long value) {
        return this.m_props.setProperty(key, new Long(value).toString());
    }
    
    public Object setBooleanProperty(final String s, final boolean b) {
        return this.setIntProperty(s, b ? 1 : 0);
    }
    
    public Object getProperty(final String key) {
        Object o = ((Hashtable<K, Object>)this.m_props).get(key);
        if (o == null && this.m_parent != null) {
            o = this.m_parent.getProperty(key);
        }
        return o;
    }
    
    public String getStringProperty(final String key) {
        String s = this.m_props.getProperty(key);
        if (s == null && this.m_parent != null) {
            s = this.m_parent.getStringProperty(key);
        }
        return s;
    }
    
    public int getIntProperty(final String s) {
        int int1 = 0;
        final String stringProperty = this.getStringProperty(s);
        if (stringProperty != null) {
            try {
                int1 = Integer.parseInt(stringProperty);
            }
            catch (NumberFormatException ex) {
                int1 = 0;
            }
        }
        return int1;
    }
    
    public long getLongProperty(final String s) {
        long long1 = 0L;
        final String stringProperty = this.getStringProperty(s);
        if (stringProperty != null) {
            try {
                long1 = Long.parseLong(stringProperty);
            }
            catch (NumberFormatException ex) {
                long1 = 0L;
            }
        }
        return long1;
    }
    
    public boolean getBooleanProperty(final String s) {
        int int1 = 0;
        final String stringProperty = this.getStringProperty(s);
        if (stringProperty != null) {
            try {
                int1 = Integer.parseInt(stringProperty);
            }
            catch (NumberFormatException ex) {
                int1 = 0;
            }
        }
        return int1 != 0;
    }
    
    public void setProperties(final Properties properties) {
        final Enumeration<String> keys = ((Hashtable<String, V>)properties).keys();
        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            this.setProperty(key, properties.getProperty(key));
        }
    }
    
    public Hashtable getProperties() {
        return new Hashtable((Map<? extends K, ? extends V>)this.m_props);
    }
    
    public String getUrl() {
        return this.getStringProperty("PROGRESS.Session.url");
    }
    
    public void setUrl(final String s) {
        this.setStringProperty("PROGRESS.Session.url", s);
    }
    
    public String getUserId() {
        return this.getStringProperty("PROGRESS.Session.userId");
    }
    
    public void setUserId(final String s) {
        this.setStringProperty("PROGRESS.Session.userId", s);
    }
    
    public String getPassword() {
        return this.getStringProperty("PROGRESS.Session.password");
    }
    
    public void setPassword(final String s) {
        this.setStringProperty("PROGRESS.Session.password", s);
    }
    
    public String getAppServerInfo() {
        return this.getStringProperty("PROGRESS.Session.appServerInfo");
    }
    
    public void setAppServerInfo(final String s) {
        this.setStringProperty("PROGRESS.Session.appServerInfo", s);
    }
    
    public boolean getWaitIfBusy() {
        return this.getBooleanProperty("PROGRESS.Session.waitIfBusy");
    }
    
    public void setWaitIfBusy() {
        this.setIntProperty("PROGRESS.Session.waitIfBusy", 1);
    }
    
    public void setNoWaitIfBusy() {
        this.setIntProperty("PROGRESS.Session.waitIfBusy", 0);
    }
    
    public String getProxyHost() {
        return this.getStringProperty("PROGRESS.Session.proxyHost");
    }
    
    public void setProxyHost(final String s) throws Open4GLException {
        this.setStringProperty("PROGRESS.Session.proxyHost", s);
    }
    
    public int getProxyPort() {
        return this.getIntProperty("PROGRESS.Session.proxyPort");
    }
    
    public void setProxyPort(final int n) throws Open4GLException {
        this.setIntProperty("PROGRESS.Session.proxyPort", n);
    }
    
    public String getCertificateStore() {
        return this.getStringProperty("PROGRESS.Session.certificateStore");
    }
    
    public void setCertificateStore(final String s) throws Open4GLException {
        this.setStringProperty("PROGRESS.Session.certificateStore", s);
    }
    
    public boolean getNoHostVerify() {
        return this.getBooleanProperty("PROGRESS.Session.noHostVerify");
    }
    
    public void setNoHostVerify(final boolean b) throws Open4GLException {
        this.setBooleanProperty("PROGRESS.Session.noHostVerify", b);
    }
    
    public boolean getNoSslSessionReuse() {
        return this.getBooleanProperty("PROGRESS.Session.noSslSessionReuse");
    }
    
    public void setNoSslSessionReuse(final boolean b) throws Open4GLException {
        this.setBooleanProperty("PROGRESS.Session.noSslSessionReuse", b);
    }
    
    public String getProxyUserId() {
        return this.getStringProperty("PROGRESS.Session.proxyUserId");
    }
    
    public void setProxyUserId(final String s) throws Open4GLException {
        this.setStringProperty("PROGRESS.Session.proxyUserId", s);
    }
    
    public String getProxyPassword() {
        return this.getStringProperty("PROGRESS.Session.proxyPassword");
    }
    
    public void setProxyPassword(final String s) throws Open4GLException {
        this.setStringProperty("PROGRESS.Session.proxyPassword", s);
    }
    
    public int getSessionModel() {
        return this.getIntProperty("PROGRESS.Session.sessionModel");
    }
    
    public void setSessionModel(final int n) {
        this.setIntProperty("PROGRESS.Session.sessionModel", n);
    }
    
    public int getInitialConnections() {
        return this.getIntProperty("PROGRESS.Session.initialConnections");
    }
    
    public void setInitialConnections(final int n) {
        this.setIntProperty("PROGRESS.Session.initialConnections", n);
    }
    
    public int getMinConnections() {
        return this.getIntProperty("PROGRESS.Session.minConnections");
    }
    
    public void setMinConnections(final int n) {
        this.setIntProperty("PROGRESS.Session.minConnections", n);
    }
    
    public int getMaxConnections() {
        return this.getIntProperty("PROGRESS.Session.maxConnections");
    }
    
    public void setMaxConnections(final int n) {
        this.setIntProperty("PROGRESS.Session.maxConnections", n);
    }
    
    public int getIdleConnectionTimeout() {
        return this.getIntProperty("PROGRESS.Session.idleConnectionTimeout");
    }
    
    public void setIdleConnectionTimeout(final int n) {
        this.setIntProperty("PROGRESS.Session.idleConnectionTimeout", n);
    }
    
    public int getConnectionLifetime() {
        return this.getIntProperty("PROGRESS.Session.connectionLifetime");
    }
    
    public void setConnectionLifetime(final int n) {
        this.setIntProperty("PROGRESS.Session.connectionLifetime", n);
    }
    
    public int getNsClientMinPort() {
        return this.getIntProperty("PROGRESS.Session.nsClientMinPort");
    }
    
    public void setNsClientMinPort(final int n) {
        this.setIntProperty("PROGRESS.Session.nsClientMinPort", n);
    }
    
    public int getNsClientMaxPort() {
        return this.getIntProperty("PROGRESS.Session.nsClientMaxPort");
    }
    
    public void setNsClientMaxPort(final int n) {
        this.setIntProperty("PROGRESS.Session.nsClientMaxPort", n);
    }
    
    public int getNsClientPortRetry() {
        return this.getIntProperty("PROGRESS.Session.nsClientPortRetry");
    }
    
    public void setNsClientPortRetry(final int n) {
        this.setIntProperty("PROGRESS.Session.nsClientPortRetry", n);
    }
    
    public int getNsClientPortRetryInterval() {
        return this.getIntProperty("PROGRESS.Session.nsClientPortRetryInterval");
    }
    
    public void setNsClientPortRetryInterval(final int n) {
        this.setIntProperty("PROGRESS.Session.nsClientPortRetryInterval", n);
    }
    
    public int getNsClientPicklistSize() {
        return this.getIntProperty("PROGRESS.Session.nsClientPicklistSize");
    }
    
    public void setNsClientPicklistSize(final int n) {
        this.setIntProperty("PROGRESS.Session.nsClientPicklistSize", n);
    }
    
    public int getRequestWaitTimeout() {
        return this.getIntProperty("PROGRESS.Session.requestWaitTimeout");
    }
    
    public void setRequestWaitTimeout(final int n) {
        this.setIntProperty("PROGRESS.Session.requestWaitTimeout", n);
    }
    
    public String getUUID() {
        return this.getStringProperty("PROGRESS.Session.UUID");
    }
    
    public void setUUID(final String s) {
        this.setStringProperty("PROGRESS.Session.UUID", s);
    }
    
    public String getLogfileName() {
        return this.getStringProperty("PROGRESS.Session.logfileName");
    }
    
    public void setLogfileName(final String s) {
        this.setStringProperty("PROGRESS.Session.logfileName", s);
    }
    
    public int getTraceLevel() {
        return this.getIntProperty("PROGRESS.Session.traceLevel");
    }
    
    public void setTraceLevel(final int n) {
        this.setIntProperty("PROGRESS.Session.traceLevel", n);
    }
    
    public String getLogEntryTypes() {
        return this.getStringProperty("PROGRESS.Session.logEntryTypes");
    }
    
    public void setLogEntryTypes(final String s) {
        this.setStringProperty("PROGRESS.Session.logEntryTypes", s);
    }
    
    public String getAppServerKeepalive() {
        return this.getStringProperty("PROGRESS.Session.appServerKeepalive");
    }
    
    public void setAppServerKeepalive(final String s) {
        this.setStringProperty("PROGRESS.Session.appServerKeepalive", s);
    }
    
    public int getClientASKActivityTimeout() {
        return this.getIntProperty("PROGRESS.Session.clientASKActivityTimeout");
    }
    
    public void setClientASKActivityTimeout(final int n) {
        this.setIntProperty("PROGRESS.Session.clientASKActivityTimeout", n);
    }
    
    public int getClientASKResponseTimeout() {
        return this.getIntProperty("PROGRESS.Session.clientASKResponseTimeout");
    }
    
    public void setClientASKResponseTimeout(final int n) {
        this.setIntProperty("PROGRESS.Session.clientASKResponseTimeout", n);
    }
    
    public void setDatasetNullInitials(final boolean b) {
        this.setBooleanProperty("PROGRESS.Session.datasetSetNullInitials", b);
    }
    
    public boolean getDatasetNullInitials() {
        return this.getBooleanProperty("PROGRESS.Session.datasetSetNullInitials");
    }
    
    public void setPostponeRelationInfo(final boolean b) {
        this.setBooleanProperty("PROGRESS.Session.postponeRelationInfo", b);
    }
    
    public boolean getPostponeRelationInfo() {
        return this.getBooleanProperty("PROGRESS.Session.postponeRelationInfo");
    }
    
    private void createProperties(final Properties defaults) {
        this.m_props = new Properties(defaults);
    }
    
    private Object cloneGenericObject(final Serializable obj) {
        Object object;
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
            object = objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (Exception ex) {
            object = null;
        }
        return object;
    }
    
    public Properties getAsProperties() {
        final Properties properties = (this.m_parent == null) ? new Properties() : new Properties(this.m_parent.getAsProperties());
        for (final String s : ((Hashtable<String, V>)this.m_props).keySet()) {
            final String property = this.m_props.getProperty(s);
            if (property instanceof String) {
                properties.setProperty(s, property);
            }
        }
        return properties;
    }
}
