// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import org.apache.xml.serialize.BaseMarkupSerializer;
import java.util.Iterator;
import java.util.Properties;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.progress.wsa.WsaSOAPException;
import java.util.Map;
import com.progress.open4gl.Open4GLException;
import com.progress.open4gl.dynamicapi.SessionPool;
import java.util.Enumeration;
import java.util.Hashtable;
import com.progress.open4gl.dynamicapi.IPoolProps;
import com.progress.wsa.open4gl.XMLSerializableRoot;
import java.io.Serializable;

public class AppRuntimeProps implements Serializable, XMLSerializableRoot, IPoolProps
{
    protected static final String ROOT = "ApplicationRuntimeProperties";
    protected static final String XML_TYPE = "AppRuntimePropsType";
    protected static final int _RUNTIME_PROPERTY_VERSION = 5;
    protected static final long serialVersionUID = -966098172748195846L;
    protected static final String _WSA_LOGGING_ENTRY_TYPES = "WSADefault";
    public static final String[] m_runtimePropNames;
    public static final String[] m_defaultPropNames;
    public static final String[] m_sessionFreePropNames;
    public static final String[] m_sessionManagedPropNames;
    public static final String[] m_askPropNames;
    public static Hashtable m_propertyMap;
    protected Hashtable m_props;
    protected transient boolean m_dirty;
    protected transient boolean m_isDefaultProps;
    protected transient String m_schemaLoc;
    protected transient String m_prefix;
    protected transient String m_namespace;
    
    public AppRuntimeProps() {
        this.m_props = null;
        this.m_dirty = false;
        this.m_isDefaultProps = false;
        this.m_schemaLoc = null;
        this.m_prefix = "";
        this.m_namespace = "urn:schemas-progress-com:WSAD:0009";
        this.createHashtable();
    }
    
    public AppRuntimeProps(final AppRuntimeProps appRuntimeProps) {
        this.m_props = null;
        this.m_dirty = false;
        this.m_isDefaultProps = false;
        this.m_schemaLoc = null;
        this.m_prefix = "";
        this.m_namespace = "urn:schemas-progress-com:WSAD:0009";
        final Hashtable properties = appRuntimeProps.getProperties();
        final Enumeration<String> keys = properties.keys();
        this.m_props = new Hashtable();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            final Integer value = properties.get(s);
            if (value.getClass().getName().equals("java.lang.Integer")) {
                this.m_props.put(s, new Integer(value));
            }
            else if (value.getClass().getName().equals("java.lang.String")) {
                this.m_props.put(s, new String((String)value));
            }
            else {
                if (!((String)value).getClass().getName().equals("java.lang.Long")) {
                    continue;
                }
                this.m_props.put(s, new Long(value));
            }
        }
    }
    
    public void setSchemaLocation(final String s) {
    }
    
    public String getSchemaLocation() {
        return this.m_schemaLoc;
    }
    
    public void setTargetNamespace(final String namespace) {
        this.m_namespace = namespace;
    }
    
    public String getTargetNamespace() {
        return this.m_namespace;
    }
    
    public void setRoot(final String s) {
    }
    
    public String getRoot() {
        return "ApplicationRuntimeProperties";
    }
    
    public void setPrefix(final String prefix) {
        this.m_prefix = prefix;
    }
    
    public String getPrefix() {
        return this.m_prefix;
    }
    
    public void setXMLType(final String s) {
    }
    
    public String getXMLType() {
        return "AppRuntimePropsType";
    }
    
    public boolean isAvailable() {
        return (int)this.getProperty("PROGRESS.Session.serviceAvailable") == 1;
    }
    
    public void setProperty(String s, final Object o) {
        Object o2 = this.m_props.get(s);
        if (o2 == null) {
            if ("PROGRESS.Session.certificateStore".equals(s)) {
                this.m_props.put(s, o.toString());
            }
            else if ("PROGRESS.Session.poolName".equals(s)) {
                this.m_props.put(s, o);
            }
            else {
                final String xmlNameToPropName = this.xmlNameToPropName(s);
                if (xmlNameToPropName != null) {
                    s = xmlNameToPropName;
                    o2 = this.m_props.get(s);
                }
            }
        }
        if (o2 != null || this.isValidProperty(s)) {
            if (o.getClass().getName().equals("java.lang.Integer")) {
                this.m_props.put(s, new Integer((int)o));
            }
            else if (o.getClass().getName().equals("java.lang.String")) {
                this.m_props.put(s, new String((String)o));
            }
            else if (o.getClass().getName().equals("java.lang.Long")) {
                this.m_props.put(s, new Long((long)o));
            }
            this.m_dirty = true;
        }
        if (s.equals("PROGRESS.Session.manifest")) {
            this.m_props.put(s, o);
        }
    }
    
    protected boolean isValidProperty(final String key) {
        return AppRuntimeProps.m_propertyMap.containsKey(key);
    }
    
    public Object setStringProperty(final String s, final String original) {
        this.setProperty(s, new String(original));
        return null;
    }
    
    public Object setIntProperty(final String s, final int value) {
        this.setProperty(s, new Integer(value));
        return null;
    }
    
    public Object setLongProperty(final String s, final long value) {
        this.setProperty(s, new Long(value));
        return null;
    }
    
    public Object setBooleanProperty(final String s, final boolean b) {
        return this.setIntProperty(s, b ? 1 : 0);
    }
    
    public Object getProperty(final String key) {
        Object o = this.m_props.get(key);
        if (o == null) {
            final String xmlNameToPropName = this.xmlNameToPropName(key);
            if (xmlNameToPropName != null) {
                o = this.m_props.get(xmlNameToPropName);
            }
        }
        return o;
    }
    
    public String getStringProperty(final String s) {
        return (String)this.getProperty(s);
    }
    
    public int getIntProperty(final String s) {
        if (this.getProperty(s) == null) {
            return 0;
        }
        return (int)this.getProperty(s);
    }
    
    public long getLongProperty(final String s) {
        if (this.getProperty(s) == null) {
            return 0L;
        }
        return (long)this.getProperty(s);
    }
    
    public boolean getBooleanProperty(final String s) {
        return this.getIntProperty(s) != 0;
    }
    
    public SessionPool getSessionPool() {
        return null;
    }
    
    public void addReference(final SessionPool sessionPool) {
    }
    
    public void releaseConnection() throws Open4GLException {
    }
    
    public void setProperties(final Hashtable hashtable) {
        final Enumeration<String> keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            this.setProperty(key, hashtable.get(key));
        }
        this.m_dirty = true;
    }
    
    public Hashtable getProperties() {
        return new Hashtable(this.m_props);
    }
    
    public boolean isDirty() {
        return this.m_dirty;
    }
    
    public boolean getIsDefaultProps() {
        return this.m_isDefaultProps;
    }
    
    public void setIsDefaultProps(final boolean isDefaultProps) {
        this.m_isDefaultProps = isDefaultProps;
    }
    
    public void setRuntimeProperties(final Hashtable hashtable) throws WsaSOAPException {
        final Integer n = (Integer)this.getProperty("PROGRESS.Session.serviceAvailable");
        String[] array;
        if (this.m_isDefaultProps) {
            array = AppRuntimeProps.m_defaultPropNames;
        }
        else if (n == 1) {
            array = AppRuntimeProps.m_runtimePropNames;
        }
        else if ((int)this.getProperty("PROGRESS.Session.sessionModel") == 1) {
            array = AppRuntimeProps.m_sessionFreePropNames;
        }
        else {
            array = AppRuntimeProps.m_sessionManagedPropNames;
        }
        final int length = array.length;
        final Enumeration<K> keys = (Enumeration<K>)hashtable.keys();
        while (keys.hasMoreElements()) {
            final String key = (String)keys.nextElement();
            final String value = hashtable.get(key);
            boolean b = false;
            for (int i = 0; i < length; ++i) {
                if (key.equals(array[i])) {
                    b = true;
                    break;
                }
            }
            if (b) {
                try {
                    this.setProperty(key, value);
                    continue;
                }
                catch (Exception ex) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871425L, new Object[] { key, ex.toString() });
                }
            }
            final Object property = this.getProperty(key);
            final Object[] array2 = { key };
            if (property == null) {
                throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871423L, array2);
            }
            if (((Integer)property).getClass().getName().equals("java.lang.Integer")) {
                if ((int)property != (int)value) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871423L, array2);
                }
                continue;
            }
            else if (((Integer)property).getClass().getName().equals("java.lang.String")) {
                if (((String)property).compareTo((String)value) != 0) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871423L, array2);
                }
                continue;
            }
            else {
                if (((String)property).getClass().getName().equals("java.lang.Long") && (long)property != (long)value) {
                    throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871423L, array2);
                }
                continue;
            }
        }
    }
    
    public Hashtable getRuntimeProperties() throws Exception {
        return this.getAppProperties(this.getRuntimePropsArray(false));
    }
    
    public Hashtable getAppProperties(final boolean b) throws Exception {
        return this.getAppProperties(this.getRuntimePropsArray(b));
    }
    
    public Hashtable getAppProperties(final String[] array) throws Exception {
        final Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        for (final String key : array) {
            try {
                hashtable.put(key, this.getProperty(key));
            }
            catch (Exception ex) {
                throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871422L, new Object[] { key, ex.toString() });
            }
        }
        return hashtable;
    }
    
    public void resetProperties(final AppRuntimeProps appRuntimeProps) throws WsaSOAPException {
        if (this.m_isDefaultProps) {
            this.createHashtable();
        }
        else {
            try {
                this.setRuntimeProperties(appRuntimeProps.getAppProperties(this.getRuntimePropsArray(false)));
            }
            catch (Exception ex) {
                throw new WsaSOAPException("SOAP-ENV:Server.Admin", "Error trying to reset properties, exception: " + ex.toString());
            }
        }
    }
    
    private void createHashtable() {
        (this.m_props = new Hashtable()).put("runtimePropertyVersion", new Integer(5));
        this.m_props.put("PROGRESS.Session.appServiceProtocol", new String("Appserver"));
        this.m_props.put("PROGRESS.Session.appServiceHost", new String("localhost"));
        this.m_props.put("PROGRESS.Session.appServicePort", new Integer(5162));
        this.m_props.put("PROGRESS.Session.appServiceName", new String("asbroker1"));
        this.m_props.put("PROGRESS.Session.staleO4GLObjectTimeout", new Long(0L));
        this.m_props.put("PROGRESS.Session.minConnections", new Integer(1));
        this.m_props.put("PROGRESS.Session.maxConnections", new Integer(0));
        this.m_props.put("PROGRESS.Session.initialConnections", new Integer(1));
        this.m_props.put("PROGRESS.Session.idleConnectionTimeout", new Long(0L));
        this.m_props.put("PROGRESS.Session.requestWaitTimeout", new Long(-1L));
        this.m_props.put("PROGRESS.Session.nsClientMinPort", new Integer(0));
        this.m_props.put("PROGRESS.Session.nsClientMaxPort", new Integer(0));
        this.m_props.put("PROGRESS.Session.nsClientPortRetry", new Integer(3));
        this.m_props.put("PROGRESS.Session.nsClientPortRetryInterval", new Integer(200));
        this.m_props.put("PROGRESS.Session.nsClientPicklistSize", new Integer(8));
        this.m_props.put("PROGRESS.Session.nsClientPicklistExpiration", new Integer(300));
        this.m_props.put("PROGRESS.Session.serviceAvailable", new Integer(1));
        this.m_props.put("PROGRESS.Session.serviceLoggingLevel", new Integer(2));
        this.m_props.put("PROGRESS.Session.serviceLoggingEntryTypes", new String("WSADefault"));
        this.m_props.put("PROGRESS.Session.sessionModel", new Integer(0));
        this.m_props.put("PROGRESS.Session.serviceFaultLevel", new Integer(2));
        this.m_props.put("PROGRESS.Session.waitIfBusy", new Integer(0));
        this.m_props.put("PROGRESS.Session.connectionLifetime", new Long(0L));
        this.m_props.put("PROGRESS.Session.minIdleConnections", new Integer(0));
        this.m_props.put("PROGRESS.Session.noHostVerify", new Integer(0));
        this.m_props.put("PROGRESS.Session.noSslSessionReuse", new Integer(0));
        this.m_props.put("PROGRESS.Session.serializeDatasetAsXML", new Integer(1));
        this.m_props.put("PROGRESS.Session.appServerKeepalive", new String("denyClientASK,allowServerASK"));
        this.m_props.put("PROGRESS.Session.clientASKActivityTimeout", new Integer(60));
        this.m_props.put("PROGRESS.Session.clientASKResponseTimeout", new Integer(60));
        this.m_props.put("PROGRESS.Session.actionalGroupName", new String("OpenEdge"));
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int intValue;
        if ((intValue = (int)this.getProperty("runtimePropertyVersion")) < 5) {
            switch (intValue) {
                case 1: {
                    this.m_props.put("PROGRESS.Session.serviceFaultLevel", new Integer(2));
                    this.m_props.put("PROGRESS.Session.waitIfBusy", new Integer(0));
                    this.m_props.put("PROGRESS.Session.connectionLifetime", new Long(0L));
                    this.m_props.put("PROGRESS.Session.minIdleConnections", new Integer(0));
                }
                case 2: {
                    this.m_props.remove("PROGRESS.Session.serviceLoggingEntries");
                    this.m_props.put("PROGRESS.Session.serviceLoggingEntryTypes", "WSADefault");
                }
                case 3: {
                    this.m_props.put("PROGRESS.Session.noHostVerify", new Long(0L));
                    this.m_props.put("PROGRESS.Session.noSslSessionReuse", new Long(0L));
                }
                case 4: {
                    this.m_props.put("PROGRESS.Session.appServerKeepalive", new String("denyClientASK,allowServerASK"));
                    this.m_props.put("PROGRESS.Session.clientASKActivityTimeout", new Integer(60));
                    this.m_props.put("PROGRESS.Session.clientASKResponseTimeout", new Integer(60));
                    this.m_props.put("PROGRESS.Session.actionalGroupName", new String("OpenEdge"));
                    break;
                }
            }
            this.setProperty("runtimePropertyVersion", new Integer(5));
            this.m_dirty = true;
        }
        this.m_schemaLoc = null;
        this.m_prefix = "";
        this.m_namespace = "urn:schemas-progress-com:WSAD:0009";
        this.m_props.put("PROGRESS.Session.serializeDatasetAsXML", new Integer(1));
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final String value = this.m_props.remove("PROGRESS.Session.poolName");
        final Integer value2 = this.m_props.remove("PROGRESS.Session.serializeDatasetAsXML");
        objectOutputStream.defaultWriteObject();
        if (value != null) {
            this.m_props.put("PROGRESS.Session.poolName", value);
        }
        this.m_props.put("PROGRESS.Session.serializeDatasetAsXML", value2);
        this.m_dirty = false;
    }
    
    private String[] getRuntimePropsArray(final boolean b) {
        final boolean b2 = (int)this.getProperty("PROGRESS.Session.serviceAvailable") == 1;
        String[] array;
        if (this.m_isDefaultProps) {
            array = AppRuntimeProps.m_defaultPropNames;
        }
        else if (b2 && !b) {
            array = AppRuntimeProps.m_runtimePropNames;
        }
        else if ((int)this.getProperty("PROGRESS.Session.sessionModel") == 1) {
            array = AppRuntimeProps.m_sessionFreePropNames;
        }
        else {
            array = AppRuntimeProps.m_sessionManagedPropNames;
        }
        return array;
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            final String string = this.getProperty("runtimePropertyVersion").toString();
            String string2;
            if (b) {
                string2 = str + ":" + "runtimePropertyVersion";
            }
            else {
                string2 = "runtimePropertyVersion";
            }
            xmlSerializer.startElement(this.m_namespace, "runtimePropertyVersion", string2, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string.toCharArray(), 0, string.length());
            xmlSerializer.endElement(this.m_namespace, "runtimePropertyVersion", string2);
            final String s2 = (String)this.getProperty("PROGRESS.Session.appServiceProtocol");
            String string3;
            if (b) {
                string3 = str + ":" + "appServiceProtocol";
            }
            else {
                string3 = "appServiceProtocol";
            }
            xmlSerializer.startElement(this.m_namespace, "appServiceProtocol", string3, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s2.toCharArray(), 0, s2.length());
            xmlSerializer.endElement(this.m_namespace, "appServiceProtocol", string3);
            final String s3 = (String)this.getProperty("PROGRESS.Session.appServiceHost");
            String string4;
            if (b) {
                string4 = str + ":" + "appServiceHost";
            }
            else {
                string4 = "appServiceHost";
            }
            xmlSerializer.startElement(this.m_namespace, "appServiceHost", string4, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s3.toCharArray(), 0, s3.length());
            xmlSerializer.endElement(this.m_namespace, "appServiceHost", string4);
            final String string5 = this.getProperty("PROGRESS.Session.appServicePort").toString();
            String string6;
            if (b) {
                string6 = str + ":" + "appServicePort";
            }
            else {
                string6 = "appServicePort";
            }
            xmlSerializer.startElement(this.m_namespace, "appServicePort", string6, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string5.toCharArray(), 0, string5.length());
            xmlSerializer.endElement(this.m_namespace, "appServicePort", string6);
            final String s4 = (String)this.getProperty("PROGRESS.Session.appServiceName");
            String string7;
            if (b) {
                string7 = str + ":" + "appServiceName";
            }
            else {
                string7 = "appServiceName";
            }
            xmlSerializer.startElement(this.m_namespace, "appServiceName", string7, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s4.toCharArray(), 0, s4.length());
            xmlSerializer.endElement(this.m_namespace, "appServiceName", string7);
            final String string8 = this.getProperty("PROGRESS.Session.staleO4GLObjectTimeout").toString();
            String string9;
            if (b) {
                string9 = str + ":" + "staleO4GLObjectTimeout";
            }
            else {
                string9 = "staleO4GLObjectTimeout";
            }
            xmlSerializer.startElement(this.m_namespace, "staleO4GLObjectTimeout", string9, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string8.toCharArray(), 0, string8.length());
            xmlSerializer.endElement(this.m_namespace, "staleO4GLObjectTimeout", string9);
            final String string10 = this.getProperty("PROGRESS.Session.minConnections").toString();
            String string11;
            if (b) {
                string11 = str + ":" + "minSessions";
            }
            else {
                string11 = "minSessions";
            }
            xmlSerializer.startElement(this.m_namespace, "minSessions", string11, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string10.toCharArray(), 0, string10.length());
            xmlSerializer.endElement(this.m_namespace, "minSessions", string11);
            final String string12 = this.getProperty("PROGRESS.Session.maxConnections").toString();
            String string13;
            if (b) {
                string13 = str + ":" + "maxSessions";
            }
            else {
                string13 = "maxSessions";
            }
            xmlSerializer.startElement(this.m_namespace, "maxSessions", string13, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string12.toCharArray(), 0, string12.length());
            xmlSerializer.endElement(this.m_namespace, "maxSessions", string13);
            final String string14 = this.getProperty("PROGRESS.Session.initialConnections").toString();
            String string15;
            if (b) {
                string15 = str + ":" + "initialSessions";
            }
            else {
                string15 = "initialSessions";
            }
            xmlSerializer.startElement(this.m_namespace, "initialSessions", string15, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string14.toCharArray(), 0, string14.length());
            xmlSerializer.endElement(this.m_namespace, "initialSessions", string15);
            final String string16 = this.getProperty("PROGRESS.Session.idleConnectionTimeout").toString();
            String string17;
            if (b) {
                string17 = str + ":" + "idleSessionTimeout";
            }
            else {
                string17 = "idleSessionTimeout";
            }
            xmlSerializer.startElement(this.m_namespace, "idleSessionTimeout", string17, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string16.toCharArray(), 0, string16.length());
            xmlSerializer.endElement(this.m_namespace, "idleSessionTimeout", string17);
            final String string18 = this.getProperty("PROGRESS.Session.requestWaitTimeout").toString();
            String string19;
            if (b) {
                string19 = str + ":" + "requestWaitTimeout";
            }
            else {
                string19 = "requestWaitTimeout";
            }
            xmlSerializer.startElement(this.m_namespace, "requestWaitTimeout", string19, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string18.toCharArray(), 0, string18.length());
            xmlSerializer.endElement(this.m_namespace, "requestWaitTimeout", string19);
            final String string20 = this.getProperty("PROGRESS.Session.nsClientMinPort").toString();
            String string21;
            if (b) {
                string21 = str + ":" + "nsClientMinPort";
            }
            else {
                string21 = "nsClientMinPort";
            }
            xmlSerializer.startElement(this.m_namespace, "nsClientMinPort", string21, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string20.toCharArray(), 0, string20.length());
            xmlSerializer.endElement(this.m_namespace, "nsClientMinPort", string21);
            final String string22 = this.getProperty("PROGRESS.Session.nsClientMaxPort").toString();
            String string23;
            if (b) {
                string23 = str + ":" + "nsClientMaxPort";
            }
            else {
                string23 = "nsClientMaxPort";
            }
            xmlSerializer.startElement(this.m_namespace, "nsClientMaxPort", string23, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string22.toCharArray(), 0, string22.length());
            xmlSerializer.endElement(this.m_namespace, "nsClientMaxPort", string23);
            final String string24 = this.getProperty("PROGRESS.Session.nsClientPortRetry").toString();
            String string25;
            if (b) {
                string25 = str + ":" + "nsClientPortRetry";
            }
            else {
                string25 = "nsClientPortRetry";
            }
            xmlSerializer.startElement(this.m_namespace, "nsClientPortRetry", string25, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string24.toCharArray(), 0, string24.length());
            xmlSerializer.endElement(this.m_namespace, "nsClientPortRetry", string25);
            final String string26 = this.getProperty("PROGRESS.Session.nsClientPortRetryInterval").toString();
            String string27;
            if (b) {
                string27 = str + ":" + "nsClientPortRetryInterval";
            }
            else {
                string27 = "nsClientPortRetryInterval";
            }
            xmlSerializer.startElement(this.m_namespace, "nsClientPortRetryInterval", string27, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string26.toCharArray(), 0, string26.length());
            xmlSerializer.endElement(this.m_namespace, "nsClientPortRetryInterval", string27);
            final String string28 = this.getProperty("PROGRESS.Session.nsClientPicklistSize").toString();
            String string29;
            if (b) {
                string29 = str + ":" + "nsClientPicklistSize";
            }
            else {
                string29 = "nsClientPicklistSize";
            }
            xmlSerializer.startElement(this.m_namespace, "nsClientPicklistSize", string29, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string28.toCharArray(), 0, string28.length());
            xmlSerializer.endElement(this.m_namespace, "nsClientPicklistSize", string29);
            final String string30 = this.getProperty("PROGRESS.Session.nsClientPicklistExpiration").toString();
            String string31;
            if (b) {
                string31 = str + ":" + "nsClientPicklistExpiration";
            }
            else {
                string31 = "nsClientPicklistExpiration";
            }
            xmlSerializer.startElement(this.m_namespace, "nsClientPicklistExpiration", string31, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string30.toCharArray(), 0, string30.length());
            xmlSerializer.endElement(this.m_namespace, "nsClientPicklistExpiration", string31);
            final String string32 = this.getProperty("PROGRESS.Session.serviceAvailable").toString();
            String string33;
            if (b) {
                string33 = str + ":" + "serviceAvailable";
            }
            else {
                string33 = "serviceAvailable";
            }
            xmlSerializer.startElement(this.m_namespace, "serviceAvailable", string33, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string32.toCharArray(), 0, string32.length());
            xmlSerializer.endElement(this.m_namespace, "serviceAvailable", string33);
            final String string34 = this.getProperty("PROGRESS.Session.serviceLoggingLevel").toString();
            String string35;
            if (b) {
                string35 = str + ":" + "serviceLoggingLevel";
            }
            else {
                string35 = "serviceLoggingLevel";
            }
            xmlSerializer.startElement(this.m_namespace, "serviceLoggingLevel", string35, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string34.toCharArray(), 0, string34.length());
            xmlSerializer.endElement(this.m_namespace, "serviceLoggingLevel", string35);
            final String s5 = (String)this.getProperty("PROGRESS.Session.serviceLoggingEntryTypes");
            String string36;
            if (b) {
                string36 = str + ":" + "serviceLoggingEntryTypes";
            }
            else {
                string36 = "serviceLoggingEntryTypes";
            }
            xmlSerializer.startElement(this.m_namespace, "serviceLoggingEntryTypes", string36, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s5.toCharArray(), 0, s5.length());
            xmlSerializer.endElement(this.m_namespace, "serviceLoggingEntryTypes", string36);
            final String string37 = this.getProperty("PROGRESS.Session.sessionModel").toString();
            String string38;
            if (b) {
                string38 = str + ":" + "appServiceConnectionMode";
            }
            else {
                string38 = "appServiceConnectionMode";
            }
            xmlSerializer.startElement(this.m_namespace, "appServiceConnectionMode", string38, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string37.toCharArray(), 0, string37.length());
            xmlSerializer.endElement(this.m_namespace, "appServiceConnectionMode", string38);
            final String string39 = this.getProperty("PROGRESS.Session.serviceFaultLevel").toString();
            String string40;
            if (b) {
                string40 = str + ":" + "serviceFaultLevel";
            }
            else {
                string40 = "serviceFaultLevel";
            }
            xmlSerializer.startElement(this.m_namespace, "serviceFaultLevel", string40, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string39.toCharArray(), 0, string39.length());
            xmlSerializer.endElement(this.m_namespace, "serviceFaultLevel", string40);
            final String string41 = this.getProperty("PROGRESS.Session.waitIfBusy").toString();
            String string42;
            if (b) {
                string42 = str + ":" + "waitIfBusy";
            }
            else {
                string42 = "waitIfBusy";
            }
            xmlSerializer.startElement(this.m_namespace, "waitIfBusy", string42, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string41.toCharArray(), 0, string41.length());
            xmlSerializer.endElement(this.m_namespace, "waitIfBusy", string42);
            final String string43 = this.getProperty("PROGRESS.Session.connectionLifetime").toString();
            String string44;
            if (b) {
                string44 = str + ":" + "connectionLifetime";
            }
            else {
                string44 = "connectionLifetime";
            }
            xmlSerializer.startElement(this.m_namespace, "connectionLifetime", string44, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string43.toCharArray(), 0, string43.length());
            xmlSerializer.endElement(this.m_namespace, "connectionLifetime", string44);
            final String string45 = this.getProperty("PROGRESS.Session.minIdleConnections").toString();
            String string46;
            if (b) {
                string46 = str + ":" + "minIdleConnections";
            }
            else {
                string46 = "minIdleConnections";
            }
            xmlSerializer.startElement(this.m_namespace, "minIdleConnections", string46, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string45.toCharArray(), 0, string45.length());
            xmlSerializer.endElement(this.m_namespace, "minIdleConnections", string46);
            final String string47 = ((Integer)this.getProperty("PROGRESS.Session.noHostVerify")).toString();
            String string48;
            if (b) {
                string48 = str + ":" + "noHostVerify";
            }
            else {
                string48 = "noHostVerify";
            }
            xmlSerializer.startElement(this.m_namespace, "noHostVerify", string48, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string47.toCharArray(), 0, string47.length());
            xmlSerializer.endElement(this.m_namespace, "noHostVerify", string48);
            final String string49 = ((Integer)this.getProperty("PROGRESS.Session.noSslSessionReuse")).toString();
            String string50;
            if (b) {
                string50 = str + ":" + "noSessionReuse";
            }
            else {
                string50 = "noSessionReuse";
            }
            xmlSerializer.startElement(this.m_namespace, "noSessionReuse", string50, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string49.toCharArray(), 0, string49.length());
            xmlSerializer.endElement(this.m_namespace, "noSessionReuse", string50);
            final String s6 = (String)this.getProperty("PROGRESS.Session.appServerKeepalive");
            String string51;
            if (b) {
                string51 = str + ":" + "appServerKeepalive";
            }
            else {
                string51 = "appServerKeepalive";
            }
            xmlSerializer.startElement(this.m_namespace, "appServerKeepalive", string51, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s6.toCharArray(), 0, s6.length());
            xmlSerializer.endElement(this.m_namespace, "appServerKeepalive", string51);
            final String string52 = ((Integer)this.getProperty("PROGRESS.Session.clientASKActivityTimeout")).toString();
            String string53;
            if (b) {
                string53 = str + ":" + "clientASKActivityTimeout";
            }
            else {
                string53 = "clientASKActivityTimeout";
            }
            xmlSerializer.startElement(this.m_namespace, "clientASKActivityTimeout", string53, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string52.toCharArray(), 0, string52.length());
            xmlSerializer.endElement(this.m_namespace, "clientASKActivityTimeout", string53);
            final String string54 = ((Integer)this.getProperty("PROGRESS.Session.clientASKResponseTimeout")).toString();
            String string55;
            if (b) {
                string55 = str + ":" + "clientASKResponseTimeout";
            }
            else {
                string55 = "clientASKResponseTimeout";
            }
            xmlSerializer.startElement(this.m_namespace, "clientASKResponseTimeout", string55, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(string54.toCharArray(), 0, string54.length());
            xmlSerializer.endElement(this.m_namespace, "clientASKResponseTimeout", string55);
            final String s7 = (String)this.getProperty("PROGRESS.Session.actionalGroupName");
            String string56;
            if (b) {
                string56 = str + ":" + "actionalGroupName";
            }
            else {
                string56 = "actionalGroupName";
            }
            xmlSerializer.startElement(this.m_namespace, "actionalGroupName", string56, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s7.toCharArray(), 0, s7.length());
            xmlSerializer.endElement(this.m_namespace, "actionalGroupName", string56);
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue = WsaParser.extractNodeValue(item);
                if (localName.equals("runtimePropertyVersion")) {
                    this.setProperty("runtimePropertyVersion", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("appServiceProtocol")) {
                    this.setProperty("PROGRESS.Session.appServiceProtocol", new String(nodeValue));
                }
                else if (localName.equals("appServiceHost")) {
                    this.setProperty("PROGRESS.Session.appServiceHost", new String(nodeValue));
                }
                else if (localName.equals("appServicePort")) {
                    this.setProperty("PROGRESS.Session.appServicePort", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("appServiceName")) {
                    this.setProperty("PROGRESS.Session.appServiceName", new String(nodeValue));
                }
                else if (localName.equals("staleO4GLObjectTimeout")) {
                    this.setProperty("PROGRESS.Session.staleO4GLObjectTimeout", new Long(Long.parseLong(nodeValue)));
                }
                else if (localName.equals("minSessions")) {
                    this.setProperty("PROGRESS.Session.minConnections", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("maxSessions")) {
                    this.setProperty("PROGRESS.Session.maxConnections", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("initialSessions")) {
                    this.setProperty("PROGRESS.Session.initialConnections", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("idleSessionTimeout")) {
                    this.setProperty("PROGRESS.Session.idleConnectionTimeout", new Long(Long.parseLong(nodeValue)));
                }
                else if (localName.equals("requestWaitTimeout")) {
                    this.setProperty("PROGRESS.Session.requestWaitTimeout", new Long(Long.parseLong(nodeValue)));
                }
                else if (localName.equals("nsClientMinPort")) {
                    this.setProperty("PROGRESS.Session.nsClientMinPort", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("nsClientMaxPort")) {
                    this.setProperty("PROGRESS.Session.nsClientMaxPort", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("nsClientPortRetry")) {
                    this.setProperty("PROGRESS.Session.nsClientPortRetry", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("nsClientPortRetryInterval")) {
                    this.setProperty("PROGRESS.Session.nsClientPortRetryInterval", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("nsClientPicklistSize")) {
                    this.setProperty("PROGRESS.Session.nsClientPicklistSize", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("nsClientPicklistExpiration")) {
                    this.setProperty("PROGRESS.Session.nsClientPicklistExpiration", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("serviceAvailable")) {
                    this.setProperty("PROGRESS.Session.serviceAvailable", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("serviceLoggingLevel")) {
                    this.setProperty("PROGRESS.Session.serviceLoggingLevel", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("serviceLoggingEntries")) {
                    this.setProperty("PROGRESS.Session.serviceLoggingEntries", new Long(Long.parseLong(nodeValue)));
                }
                else if (localName.equals("serviceLoggingEntryTypes")) {
                    this.setProperty("PROGRESS.Session.serviceLoggingEntryTypes", new String(nodeValue));
                }
                else if (localName.equals("appServiceConnectionMode")) {
                    this.setProperty("PROGRESS.Session.sessionModel", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("serviceFaultLevel")) {
                    this.setProperty("PROGRESS.Session.serviceFaultLevel", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("waitIfBusy")) {
                    this.setProperty("PROGRESS.Session.waitIfBusy", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("connectionLifetime")) {
                    this.setProperty("PROGRESS.Session.connectionLifetime", new Long(Long.parseLong(nodeValue)));
                }
                else if (localName.equals("minIdleConnections")) {
                    this.setProperty("PROGRESS.Session.minIdleConnections", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("noHostVerify")) {
                    this.setProperty("PROGRESS.Session.noHostVerify", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("noSessionReuse")) {
                    this.setProperty("PROGRESS.Session.noSslSessionReuse", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("appServerKeepalive")) {
                    this.setProperty("PROGRESS.Session.appServerKeepalive", new String(nodeValue));
                }
                else if (localName.equals("clientASKActivityTimeout")) {
                    this.setProperty("PROGRESS.Session.clientASKActivityTimeout", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("clientASKResponseTimeout")) {
                    this.setProperty("PROGRESS.Session.clientASKResponseTimeout", new Integer(Integer.parseInt(nodeValue)));
                }
                else if (localName.equals("actionalGroupName")) {
                    this.setProperty("PROGRESS.Session.actionalGroupName", new String(nodeValue));
                }
            }
        }
        final String namespaceURI = node.getNamespaceURI();
        if (namespaceURI != null && namespaceURI.equals("urn:schemas-progress-com:WSAD:0001")) {
            this.m_props.put("PROGRESS.Session.serviceFaultLevel", new Integer(2));
            this.m_props.put("PROGRESS.Session.waitIfBusy", new Integer(0));
            this.m_props.put("PROGRESS.Session.connectionLifetime", new Long(0L));
            this.m_props.put("PROGRESS.Session.minIdleConnections", new Integer(0));
            this.setProperty("runtimePropertyVersion", new Integer(5));
        }
        if (namespaceURI != null && namespaceURI.equals("urn:schemas-progress-com:WSAD:0002")) {
            this.m_props.remove("PROGRESS.Session.serviceLoggingEntries");
            this.m_props.put("PROGRESS.Session.serviceLoggingEntryTypes", "WSADefault");
            this.setProperty("runtimePropertyVersion", new Integer(5));
        }
        if (namespaceURI != null && namespaceURI.equals("urn:schemas-progress-com:WSAD:0003")) {
            this.m_props.put("PROGRESS.Session.noHostVerify", new Integer(0));
            this.m_props.put("PROGRESS.Session.noSslSessionReuse", new Integer(0));
            this.setProperty("runtimePropertyVersion", new Integer(5));
        }
        if (namespaceURI != null && namespaceURI.equals("urn:schemas-progress-com:WSAD:0007")) {
            this.m_props.put("PROGRESS.Session.appServerKeepalive", new String("denyClientASK,allowServerASK"));
            this.m_props.put("PROGRESS.Session.clientASKActivityTimeout", new Integer(60));
            this.m_props.put("PROGRESS.Session.clientASKResponseTimeout", new Integer(60));
            this.m_props.put("PROGRESS.Session.actionalGroupName", new String("OpenEdge"));
            this.setProperty("runtimePropertyVersion", new Integer(5));
        }
    }
    
    public Properties getAsProperties() {
        final Properties properties = new Properties();
        for (final String key : this.m_props.keySet()) {
            final Object property = this.getProperty(key);
            if (property instanceof String || property instanceof Integer || property instanceof Long) {
                properties.setProperty(key, property.toString());
            }
        }
        return properties;
    }
    
    private String xmlNameToPropName(final String s) {
        String s2 = null;
        if (s.equals("appServiceProtocol")) {
            s2 = "PROGRESS.Session.appServiceProtocol";
        }
        else if (s.equals("appServiceHost")) {
            s2 = "PROGRESS.Session.appServiceHost";
        }
        else if (s.equals("appServicePort")) {
            s2 = "PROGRESS.Session.appServicePort";
        }
        else if (s.equals("appServiceName")) {
            s2 = "PROGRESS.Session.appServiceName";
        }
        else if (s.equals("staleO4GLObjectTimeout")) {
            s2 = "PROGRESS.Session.staleO4GLObjectTimeout";
        }
        else if (s.equals("minSessions")) {
            s2 = "PROGRESS.Session.minConnections";
        }
        else if (s.equals("maxSessions")) {
            s2 = "PROGRESS.Session.maxConnections";
        }
        else if (s.equals("initialSessions")) {
            s2 = "PROGRESS.Session.initialConnections";
        }
        else if (s.equals("idleSessionTimeout")) {
            s2 = "PROGRESS.Session.idleConnectionTimeout";
        }
        else if (s.equals("requestWaitTimeout")) {
            s2 = "PROGRESS.Session.requestWaitTimeout";
        }
        else if (s.equals("nsClientMinPort")) {
            s2 = "PROGRESS.Session.nsClientMinPort";
        }
        else if (s.equals("nsClientMaxPort")) {
            s2 = "PROGRESS.Session.nsClientMaxPort";
        }
        else if (s.equals("nsClientPortRetry")) {
            s2 = "PROGRESS.Session.nsClientPortRetry";
        }
        else if (s.equals("nsClientPortRetryInterval")) {
            s2 = "PROGRESS.Session.nsClientPortRetryInterval";
        }
        else if (s.equals("nsClientPicklistSize")) {
            s2 = "PROGRESS.Session.nsClientPicklistSize";
        }
        else if (s.equals("nsClientPicklistExpiration")) {
            s2 = "PROGRESS.Session.nsClientPicklistExpiration";
        }
        else if (s.equals("serviceAvailable")) {
            s2 = "PROGRESS.Session.serviceAvailable";
        }
        else if (s.equals("serviceLoggingLevel")) {
            s2 = "PROGRESS.Session.serviceLoggingLevel";
        }
        else if (s.equals("serviceLoggingEntries")) {
            s2 = "PROGRESS.Session.serviceLoggingEntries";
        }
        else if (s.equals("serviceLoggingEntryTypes")) {
            s2 = "PROGRESS.Session.serviceLoggingEntryTypes";
        }
        else if (s.equals("appServiceConnectionMode")) {
            s2 = "PROGRESS.Session.sessionModel";
        }
        else if (s.equals("serviceFaultLevel")) {
            s2 = "PROGRESS.Session.serviceFaultLevel";
        }
        else if (s.equals("waitIfBusy")) {
            s2 = "PROGRESS.Session.waitIfBusy";
        }
        else if (s.equals("connectionLifetime")) {
            s2 = "PROGRESS.Session.connectionLifetime";
        }
        else if (s.equals("minIdleConnections")) {
            s2 = "PROGRESS.Session.minIdleConnections";
        }
        else if (s.equals("noHostVerify")) {
            s2 = "PROGRESS.Session.noHostVerify";
        }
        else if (s.equals("noSessionReuse")) {
            s2 = "PROGRESS.Session.noSslSessionReuse";
        }
        else if (s.equals("appServerKeepalive")) {
            s2 = "PROGRESS.Session.appServerKeepalive";
        }
        else if (s.equals("clientASKActivityTimeout")) {
            s2 = "PROGRESS.Session.clientASKActivityTimeout";
        }
        else if (s.equals("clientASKResponseTimeout")) {
            s2 = "PROGRESS.Session.clientASKResponseTimeout";
        }
        else if (s.equals("actionalGroupName")) {
            s2 = "PROGRESS.Session.actionalGroupName";
        }
        return s2;
    }
    
    static {
        m_runtimePropNames = new String[] { "serviceLoggingLevel", "serviceFaultLevel", "serviceLoggingEntryTypes" };
        m_defaultPropNames = new String[] { "appServiceProtocol", "appServiceHost", "appServicePort", "appServiceName", "staleO4GLObjectTimeout", "minSessions", "maxSessions", "initialSessions", "idleSessionTimeout", "requestWaitTimeout", "nsClientMinPort", "nsClientMaxPort", "nsClientPortRetry", "nsClientPortRetryInterval", "nsClientPicklistSize", "nsClientPicklistExpiration", "serviceLoggingLevel", "serviceFaultLevel", "waitIfBusy", "connectionLifetime", "minIdleConnections", "noHostVerify", "noSessionReuse", "appServerKeepalive", "clientASKActivityTimeout", "clientASKResponseTimeout", "serviceLoggingEntryTypes" };
        m_sessionFreePropNames = new String[] { "appServiceProtocol", "appServiceHost", "appServicePort", "appServiceName", "staleO4GLObjectTimeout", "minSessions", "maxSessions", "initialSessions", "idleSessionTimeout", "requestWaitTimeout", "nsClientMinPort", "nsClientMaxPort", "nsClientPortRetry", "nsClientPortRetryInterval", "nsClientPicklistSize", "nsClientPicklistExpiration", "serviceLoggingLevel", "serviceFaultLevel", "waitIfBusy", "connectionLifetime", "minIdleConnections", "noHostVerify", "noSessionReuse", "appServerKeepalive", "clientASKActivityTimeout", "clientASKResponseTimeout", "serviceLoggingEntryTypes" };
        m_sessionManagedPropNames = new String[] { "appServiceProtocol", "appServiceHost", "appServicePort", "appServiceName", "staleO4GLObjectTimeout", "requestWaitTimeout", "nsClientMinPort", "nsClientMaxPort", "nsClientPortRetry", "nsClientPortRetryInterval", "serviceLoggingLevel", "serviceFaultLevel", "waitIfBusy", "connectionLifetime", "noHostVerify", "noSessionReuse", "appServerKeepalive", "clientASKActivityTimeout", "clientASKResponseTimeout", "serviceLoggingEntryTypes" };
        m_askPropNames = new String[] { "appServerKeepalive", "clientASKActivityTimeout", "clientASKResponseTimeout" };
        (AppRuntimeProps.m_propertyMap = new Hashtable()).put("PROGRESS.Session.appServiceProtocol", "appServiceProtocol");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.appServiceHost", "appServiceHost");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.appServicePort", "appServicePort");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.appServiceName", "appServiceName");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.staleO4GLObjectTimeout", "staleO4GLObjectTimeout");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.minConnections", "minSessions");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.maxConnections", "maxSessions");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.initialConnections", "initialSessions");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.idleConnectionTimeout", "idleSessionTimeout");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.requestWaitTimeout", "requestWaitTimeout");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.nsClientMinPort", "nsClientMinPort");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.nsClientMaxPort", "nsClientMaxPort");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.nsClientPortRetry", "nsClientPortRetry");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.nsClientPortRetryInterval", "nsClientPortRetryInterval");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.nsClientPicklistSize", "nsClientPicklistSize");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.nsClientPicklistExpiration", "nsClientPicklistExpiration");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.serviceAvailable", "serviceAvailable");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.serviceLoggingLevel", "serviceLoggingLevel");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.serviceLoggingEntries", "serviceLoggingEntries");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.serviceLoggingEntryTypes", "serviceLoggingEntryTypes");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.sessionModel", "appServiceConnectionMode");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.serviceFaultLevel", "serviceFaultLevel");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.waitIfBusy", "waitIfBusy");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.connectionLifetime", "connectionLifetime");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.minIdleConnections", "minIdleConnections");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.noHostVerify", "noHostVerify");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.noSslSessionReuse", "noSessionReuse");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.serializeDatasetAsXML", "serializeDatasetAsXML");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.appServerKeepalive", "appServerKeepalive");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.clientASKActivityTimeout", "clientASKActivityTimeout");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.clientASKResponseTimeout", "clientASKResponseTimeout");
        AppRuntimeProps.m_propertyMap.put("PROGRESS.Session.actionalGroupName", "actionalGroupName");
    }
}
