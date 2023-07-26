// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.Node;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import java.util.Enumeration;
import org.xml.sax.SAXException;
import com.progress.open4gl.ProSQLException;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.util.StringTokenizer;
import com.progress.wsa.WsaConstants;
import com.progress.wsa.open4gl.proxy.WSAProxyPool;
import com.progress.common.ehnlog.AppLogger;
import org.apache.soap.util.xml.QName;
import com.progress.wsa.WsaSOAPEngineContext;
import java.util.Hashtable;
import com.progress.wsa.WsaProperties;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.wsa.open4gl.PoolManager;
import com.progress.wsa.open4gl.XMLSerializable;
import org.apache.soap.server.DeploymentDescriptor;

public class PscDeploymentDescriptor extends DeploymentDescriptor implements XMLSerializable
{
    public static final String STATIC_MODE = "static";
    public static final String DYNAMIC_MODE = "dynamic";
    private PoolManager m_poolManager;
    private boolean m_applicationEnabled;
    private int m_faultDetailLevel;
    private boolean m_useServletLog;
    private String m_displayName;
    private String m_logFilePath;
    private IAppLogger m_logSink;
    private IAppLogger m_wsaLogSink;
    private WsaProperties m_config;
    private String m_defaultEncoding;
    private String m_lookupMode;
    private boolean m_isDynamicObject;
    private String m_connectProtocol;
    private String m_connectHost;
    private String m_connectPort;
    private String m_connectPath;
    private String m_connectService;
    private String m_appDisabledMessage;
    private String m_pscObjectName;
    private int m_connectionMode;
    private AppRuntimeStats m_localRuntimeStats;
    private Hashtable m_methods;
    private WsaSOAPEngineContext m_context;
    private AppContainer m_appCtr;
    
    public PscDeploymentDescriptor() {
        this.m_poolManager = null;
        this.m_applicationEnabled = true;
        this.m_faultDetailLevel = 0;
        this.m_useServletLog = true;
        this.m_displayName = super.id;
        this.m_logFilePath = this.m_displayName + ".log";
        this.m_logSink = null;
        this.m_wsaLogSink = null;
        this.m_config = null;
        this.m_defaultEncoding = "";
        this.m_lookupMode = "dynamic";
        this.m_isDynamicObject = true;
        this.m_connectProtocol = "AppServerDC";
        this.m_connectHost = "localhost";
        this.m_connectPort = "3090";
        this.m_connectPath = "/";
        this.m_connectService = "asbroker1";
        this.m_appDisabledMessage = "The Web Service is temporarily disabled.";
        this.m_pscObjectName = null;
        this.m_connectionMode = 0;
        this.m_localRuntimeStats = new AppRuntimeStats();
        this.m_methods = new Hashtable();
        this.m_context = null;
        this.m_appCtr = null;
    }
    
    public Hashtable getProps() {
        if (this.m_appCtr == null) {
            return null;
        }
        return this.m_appCtr.getRuntimeProperties().getProperties();
    }
    
    public void setProps(final Hashtable hashtable) {
        throw new RuntimeException("Setting properties through the DeploymentDescriptor is not allowed");
    }
    
    public boolean hasMethod(final QName qName) {
        boolean b = false;
        if (null != qName && null != this.m_methods.get(qName.toString())) {
            b = true;
        }
        return b;
    }
    
    public MethodDescriptor getMethod(final String s) throws NoSuchMethodException {
        MethodDescriptor methodDescriptor = null;
        if (null != s && 0 < s.length()) {
            methodDescriptor = this.m_methods.get(this.newQName(s).toString());
            if (null == methodDescriptor) {
                throw new NoSuchMethodException("The method methodName does not exist");
            }
        }
        return methodDescriptor;
    }
    
    public MethodDescriptor getMethod(final QName qName) throws NoSuchMethodException {
        MethodDescriptor methodDescriptor = null;
        if (null != qName) {
            methodDescriptor = this.m_methods.get(qName.toString());
            if (null == methodDescriptor) {
                throw new NoSuchMethodException("The method methodName does not exist");
            }
        }
        return methodDescriptor;
    }
    
    public boolean isDynamicApplication() {
        return this.m_isDynamicObject;
    }
    
    public boolean isSessionFreeApplication() {
        return this.getIntProp("PROGRESS.Session.sessionModel") != 0;
    }
    
    public boolean getApplicationEnabled() {
        return this.m_appCtr.getAppStatus() == 0;
    }
    
    public void setApplicationEnabled(final boolean applicationEnabled) {
        if (!this.m_isDynamicObject) {
            this.m_applicationEnabled = applicationEnabled;
        }
    }
    
    public String getAppDisabledMessage() {
        if (!this.m_isDynamicObject) {
            return this.m_appDisabledMessage;
        }
        return "The Web Service is temporarily disabled.";
    }
    
    public boolean isDebugEnabled() {
        return this.getIntProp("PROGRESS.Session.serviceLoggingLevel") >= 3;
    }
    
    public int getFaultDetailLevel() {
        return this.m_faultDetailLevel;
    }
    
    public void setFaultDetailLevel(final int faultDetailLevel) {
        if (0 > faultDetailLevel) {
            this.m_faultDetailLevel = 0;
        }
        else if (6 < faultDetailLevel) {
            this.m_faultDetailLevel = 6;
        }
        else {
            this.m_faultDetailLevel = faultDetailLevel;
        }
    }
    
    public int getLogginLevel() {
        return this.getIntProp("PROGRESS.Session.serviceLoggingLevel");
    }
    
    public void setLoggingLevel(final int loggingLevel) {
        if (null == this.m_appCtr && null != this.m_logSink) {
            this.m_logSink.setLoggingLevel(loggingLevel);
        }
    }
    
    public String getLoggingEntries() {
        return this.getStringProp("PROGRESS.Session.serviceLoggingEntryTypes");
    }
    
    public void setLoggingEntries(final String logEntries) {
        if (null == this.m_appCtr && null != this.m_logSink) {
            this.m_logSink.setLogEntries(logEntries);
        }
    }
    
    public IAppLogger getLogSink() {
        IAppLogger logSink = null;
        if (this.m_useServletLog) {
            if (null != this.m_appCtr) {
                logSink = this.m_appCtr.getApplicationLog();
                this.m_logSink = logSink;
            }
            if (null == logSink) {
                logSink = new AppLogger(this.m_wsaLogSink);
                logSink.setLoggingLevel(this.getIntProp("PROGRESS.Session.serviceLoggingLevel"));
                logSink.setExecEnvId((null != this.m_displayName) ? this.m_displayName : this.m_pscObjectName);
            }
        }
        else {
            if (null == this.m_logSink) {
                try {
                    this.m_logSink = new AppLogger(this.m_logFilePath, this.m_config.logAppend, this.getIntProp("PROGRESS.Session.serviceLoggingLevel"), this.m_config.logThreshold, this.m_config.numLogFiles, this.getStringProp("PROGRESS.Session.serviceLoggingEntryTypes"), this.m_displayName, "Wsa");
                }
                catch (Exception ex) {
                    (this.m_logSink = new AppLogger(this.m_wsaLogSink)).setLoggingLevel(this.getIntProp("PROGRESS.Session.serviceLoggingLevel"));
                    this.m_logSink.setExecEnvId(this.m_displayName);
                }
            }
            logSink = this.m_logSink;
        }
        return logSink;
    }
    
    public AppRuntimeStats runtimeStats() {
        if (null == this.m_appCtr) {
            return this.m_localRuntimeStats;
        }
        return this.m_appCtr.getRuntimeStats();
    }
    
    public int getConnectionMode() {
        if (!this.m_isDynamicObject) {
            return this.m_connectionMode;
        }
        return this.getIntProp("PROGRESS.Session.sessionModel");
    }
    
    public WSAProxyPool poolManager() {
        if (null == this.m_appCtr) {
            return null;
        }
        return this.m_appCtr.getPoolManager();
    }
    
    public String defaultEncoding() {
        if (!this.m_isDynamicObject) {
            return this.m_defaultEncoding;
        }
        if (null != this.m_appCtr) {
            return this.m_appCtr.getCurrentEncoding();
        }
        return WsaConstants.WSA_SERVICE_ENCODING[1];
    }
    
    public int defaultEncodingType() {
        if (null != this.m_appCtr) {
            return this.m_appCtr.getCurrentEncodingInt();
        }
        return 1;
    }
    
    public String getConnectionURL() {
        if (!this.m_isDynamicObject) {
            final StringBuffer sb = new StringBuffer();
            sb.append(this.m_connectProtocol);
            sb.append("://");
            sb.append(this.m_connectHost);
            sb.append(":");
            sb.append(this.m_connectPort);
            sb.append(this.m_connectPath);
            sb.append(this.m_connectService);
            return sb.toString();
        }
        return this.m_appCtr.getConnectionURL();
    }
    
    public String getProgressObjectName() {
        return this.m_pscObjectName;
    }
    
    public void setProgressObjectName(final String pscObjectName) {
        this.m_pscObjectName = pscObjectName;
    }
    
    public void initRuntime(final WsaSOAPEngineContext context) throws Exception {
        if (this.m_isDynamicObject) {
            throw new RuntimeException("This initRuntime method is only for the prototype.");
        }
        this.m_context = context;
        this.m_config = (WsaProperties)context.get("psc.wsa.params");
        this.m_wsaLogSink = (IAppLogger)this.m_context.get("psc.wsa.log");
        if ("static" == this.m_lookupMode) {
            this.m_poolManager = new PoolManager();
        }
        final Hashtable<String, Class<Boolean>> hashtable = new Hashtable<String, Class<Boolean>>();
        hashtable.put("boolean.class", Boolean.TYPE);
        hashtable.put("byte.class", (Class<Boolean>)Byte.TYPE);
        hashtable.put("int.class", (Class<Boolean>)Integer.TYPE);
        hashtable.put("char.class", (Class<Boolean>)Character.TYPE);
        hashtable.put("short.class", (Class<Boolean>)Short.TYPE);
        hashtable.put("long.class", (Class<Boolean>)Long.TYPE);
        hashtable.put("float.class", (Class<Boolean>)Float.TYPE);
        hashtable.put("double.class", (Class<Boolean>)Double.TYPE);
        hashtable.put("void.class", (Class<Boolean>)Void.TYPE);
        final String lookupMode = super.props.get("lookupMode");
        if (null != lookupMode) {
            this.m_lookupMode = lookupMode;
        }
        if ("static" == this.m_lookupMode) {
            this.m_isDynamicObject = false;
        }
        else {
            this.m_isDynamicObject = true;
        }
        final String connectProtocol = super.props.get("protocol");
        if (null != connectProtocol) {
            this.m_connectProtocol = connectProtocol;
        }
        final String connectHost = super.props.get("host");
        if (null != connectHost) {
            this.m_connectHost = connectHost;
        }
        final String connectPort = super.props.get("port");
        if (null != connectPort) {
            this.m_connectPort = connectPort;
        }
        final String connectPath = super.props.get("path");
        if (null != connectPath) {
            this.m_connectPath = connectPath;
        }
        final String connectService = super.props.get("service");
        if (null != connectService) {
            this.m_connectService = connectService;
        }
        this.m_pscObjectName = super.props.get("objectname");
        for (int i = 0; i < super.methods.length; ++i) {
            Class<?> clazz = Void.TYPE;
            final String s = super.methods[i];
            final QName qName = this.newQName(s);
            final String str = super.props.get(s);
            if (null == str) {
                System.err.println("PDD: Method " + s + " option definition is missing.");
            }
            else {
                final StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
                String nextToken = null;
                try {
                    nextToken = stringTokenizer.nextToken();
                    clazz = this.m_context.getClass().getClassLoader().loadClass(nextToken);
                }
                catch (Exception ex) {
                    if (null != nextToken) {
                        clazz = hashtable.get(nextToken);
                    }
                    if (null == clazz) {
                        System.err.println("PDD: Exception handling method definition for: " + s);
                        System.err.println("    using default void.class");
                        System.err.println("    " + ex.toString());
                        clazz = Void.TYPE;
                    }
                }
                clazz.getName();
                final String nextToken2 = stringTokenizer.nextToken();
                final String nextToken3 = stringTokenizer.nextToken();
                final int int1 = Integer.parseInt(stringTokenizer.nextToken());
                String nextToken4;
                try {
                    nextToken4 = stringTokenizer.nextToken();
                }
                catch (Exception ex3) {
                    nextToken4 = "";
                }
                final StringTokenizer stringTokenizer2 = new StringTokenizer(nextToken4, ",");
                final int countTokens = stringTokenizer2.countTokens();
                final MethodParam[] array = new MethodParam[countTokens];
                if (0 < countTokens) {
                    for (int j = 0; j < countTokens; ++j) {
                        final String nextToken5 = stringTokenizer2.nextToken();
                        final QName qName2 = this.newQName(nextToken5);
                        final String str2 = super.props.get(s + "." + nextToken5);
                        if (null == str2) {
                            System.err.println("PDD: Method " + s + "." + nextToken5 + " option definition is missing.");
                        }
                        else {
                            final StringTokenizer stringTokenizer3 = new StringTokenizer(str2, ";");
                            Class<?> clazz2 = null;
                            String nextToken6 = null;
                            try {
                                nextToken6 = stringTokenizer3.nextToken();
                                clazz2 = this.m_context.getClass().getClassLoader().loadClass(nextToken6);
                            }
                            catch (Exception ex2) {
                                if (null != nextToken6) {
                                    clazz2 = hashtable.get(nextToken6);
                                }
                                if (null == clazz2) {
                                    System.err.println("PDD: Exception handling method parameter definition for: " + s + "." + nextToken5);
                                    System.err.println("    using default void.class");
                                    System.err.println("    " + ex2.toString());
                                    clazz2 = Void.TYPE;
                                }
                            }
                            clazz2.getName();
                            array[j] = new MethodParam(qName2, clazz2, Integer.parseInt(stringTokenizer3.nextToken()), j, Integer.parseInt(stringTokenizer3.nextToken()), Boolean.getBoolean(stringTokenizer3.nextToken()), false, false, 0);
                        }
                    }
                }
                this.m_methods.put(qName.toString(), new MethodDescriptor(qName, nextToken2, nextToken3, int1, new MethodParam(new QName("", "result"), clazz, 0, 0, 0, false, false, false, 0), array));
            }
        }
    }
    
    public void initRuntime(final WsaSOAPEngineContext context, final Hashtable methods, final AppContainer appCtr) throws Exception {
        this.m_context = context;
        this.m_methods = methods;
        this.m_appCtr = appCtr;
        if (this.m_context != null) {
            this.m_config = (WsaProperties)this.m_context.get("psc.wsa.params");
        }
        if (this.m_context != null) {
            this.m_wsaLogSink = (IAppLogger)this.m_context.get("psc.wsa.log");
        }
        super.checkMustUnderstands = false;
        super.serviceType = 0;
        super.scope = 2;
        super.setDefaultSMRClass("com.progress.wsa.xmr.SOAP4glXMR");
        super.methods = new String[0];
        super.providerType = 3;
        super.serviceClass = "com.progress.wsa.open4gl.Rpc4glProvider";
        super.providerClass = "com.progress.wsa.open4gl.Rpc4glProvider";
        super.isStatic = true;
        (super.faultListener = new String[1])[0] = "org.apache.soap.server.DOMFaultListener";
    }
    
    protected QName newQName(final String s) {
        int lastIndex = s.lastIndexOf(":");
        QName qName;
        if (-1 != lastIndex) {
            qName = new QName(s.substring(0, lastIndex++), s.substring(lastIndex));
        }
        else {
            qName = new QName("", s);
        }
        return qName;
    }
    
    private boolean getBooleanProp(final String s) {
        final Integer n = (Integer)this.m_appCtr.getRuntimeProperties().getProperty(s);
        if (n == null) {}
        return n != 0;
    }
    
    private int getIntProp(final String s) {
        final Integer n = (Integer)this.m_appCtr.getRuntimeProperties().getProperty(s);
        if (n == null) {}
        return n;
    }
    
    private long getLongProp(final String s) {
        final Long n = (Long)this.m_appCtr.getRuntimeProperties().getProperty(s);
        if (n == null) {}
        return n;
    }
    
    private String getStringProp(final String s) {
        final String s2 = (String)this.m_appCtr.getRuntimeProperties().getProperty(s);
        if (s2 == null) {}
        return s2;
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String s2) throws SAXException {
        try {
            if (super.id != null) {
                xmlSerializer.startElement(s, "ServiceID", "ServiceID", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(super.id.toCharArray(), 0, super.id.length());
                xmlSerializer.endElement(s, "ServiceID", "ServiceID");
            }
            final String s3 = (super.serviceType == 0) ? "RPC" : "Message";
            xmlSerializer.startElement(s, "ServiceType", "ServiceType", (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s3.toCharArray(), 0, s3.length());
            xmlSerializer.endElement(s, "ServiceType", "ServiceType");
            String s4;
            if (super.scope == 0) {
                s4 = "Request";
            }
            else if (super.scope == 1) {
                s4 = "Session";
            }
            else {
                s4 = "Application";
            }
            xmlSerializer.startElement(s, "Scope", "Scope", (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s4.toCharArray(), 0, s4.length());
            xmlSerializer.endElement(s, "Scope", "Scope");
            String s5;
            if (super.providerType == 0) {
                s5 = "Java";
            }
            else if (super.providerType == 1) {
                s5 = "Script File";
            }
            else if (super.providerType == 2) {
                s5 = "Script String";
            }
            else {
                s5 = "User Defined";
            }
            xmlSerializer.startElement(s, "ProviderType", "ProviderType", (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(s5.toCharArray(), 0, s5.length());
            xmlSerializer.endElement(s, "ProviderType", "ProviderType");
            if (super.providerClass != null) {
                xmlSerializer.startElement(s, "ProviderClass", "ProviderClass", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(super.providerClass.toCharArray(), 0, super.providerClass.length());
                xmlSerializer.endElement(s, "ProviderClass", "ProviderClass");
            }
            if (super.serviceClass != null) {
                xmlSerializer.startElement(s, "ServiceClass", "ServiceClass", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(super.serviceClass.toCharArray(), 0, super.serviceClass.length());
                xmlSerializer.endElement(s, "ServiceClass", "ServiceClass");
            }
            if (this.m_pscObjectName != null) {
                xmlSerializer.startElement(s, "PscObjectName", "PscObjectName", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_pscObjectName.toCharArray(), 0, this.m_pscObjectName.length());
                xmlSerializer.endElement(s, "PscObjectName", "PscObjectName");
            }
            xmlSerializer.startElement(s, "ApplicationRuntimeProps", "ApplicationRuntimeProps", (Attributes)null);
            this.m_appCtr.getRuntimeProperties().writeXML(xmlSerializer, s, s2);
            xmlSerializer.endElement(s, "ApplicationRuntimeProps", "ApplicationRuntimeProps");
            final Enumeration<MethodDescriptor> elements = (Enumeration<MethodDescriptor>)this.m_methods.elements();
            while (elements.hasMoreElements()) {
                final MethodDescriptor methodDescriptor = elements.nextElement();
                xmlSerializer.startElement(s, "MethodDescriptor", "MethodDescriptor", (Attributes)null);
                final String string = methodDescriptor.name().toString();
                xmlSerializer.startElement(s, "SOAPMethodName", "SOAPMethodName", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(string.toCharArray(), 0, string.length());
                xmlSerializer.endElement(s, "SOAPMethodName", "SOAPMethodName");
                final String string2 = methodDescriptor.returnType().toString();
                xmlSerializer.startElement(s, "ReturnType", "ReturnType", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(string2.toCharArray(), 0, string2.length());
                xmlSerializer.endElement(s, "ReturnType", "ReturnType");
                final String procedureName = methodDescriptor.procedureName();
                xmlSerializer.startElement(s, "ProcedureName", "ProcedureName", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(procedureName.toCharArray(), 0, procedureName.length());
                xmlSerializer.endElement(s, "ProcedureName", "ProcedureName");
                final String wsaMethodName = methodDescriptor.wsaMethodName();
                if (wsaMethodName != null) {
                    xmlSerializer.startElement(s, "WsaMethodName", "WsaMethodName", (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(wsaMethodName.toCharArray(), 0, wsaMethodName.length());
                    xmlSerializer.endElement(s, "WsaMethodName", "WsaMethodName");
                }
                final String string3 = Integer.toString(methodDescriptor.wsaMethodType());
                xmlSerializer.startElement(s, "WsaMethodType", "WsaMethodType", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(string3.toCharArray(), 0, string3.length());
                xmlSerializer.endElement(s, "WsaMethodType", "WsaMethodType");
                xmlSerializer.startElement(s, "Parameters", "Parameters", (Attributes)null);
                for (int parameterCount = methodDescriptor.parameterCount(), i = 0; i < parameterCount; ++i) {
                    final MethodParam parameter = methodDescriptor.parameter(i);
                    xmlSerializer.startElement(s, "MethodParameter", "MethodParameter", (Attributes)null);
                    final String string4 = parameter.name().toString();
                    xmlSerializer.startElement(s, "SOAPParamName", "SOAPParamName", (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(string4.toCharArray(), 0, string4.length());
                    xmlSerializer.endElement(s, "SOAPParamName", "SOAPParamName");
                    final String string5 = parameter.javaType().toString();
                    xmlSerializer.startElement(s, "JavaType", "JavaType", (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(string5.toCharArray(), 0, string5.length());
                    xmlSerializer.endElement(s, "JavaType", "JavaType");
                    final String string6 = Integer.toString(parameter.pscType());
                    xmlSerializer.startElement(s, "PSCType", "PSCType", (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(string6.toCharArray(), 0, string6.length());
                    xmlSerializer.endElement(s, "PSCType", "PSCType");
                    final String string7 = Integer.toString(parameter.paramNumber());
                    xmlSerializer.startElement(s, "Ordinal", "Ordinal", (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(string7.toCharArray(), 0, string7.length());
                    xmlSerializer.endElement(s, "Ordinal", "Ordinal");
                    final String string8 = Integer.toString(parameter.inOutType());
                    xmlSerializer.startElement(s, "InOutType", "InOutType", (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(string8.toCharArray(), 0, string8.length());
                    xmlSerializer.endElement(s, "InOutType", "InOutType");
                    final String s6 = parameter.isNillable() ? "true" : "false";
                    xmlSerializer.startElement(s, "Nillable", "Nillable", (Attributes)null);
                    ((BaseMarkupSerializer)xmlSerializer).characters(s6.toCharArray(), 0, s6.length());
                    xmlSerializer.endElement(s, "Nillable", "Nillable");
                    xmlSerializer.startElement(s, "MetaData", "MetaData", (Attributes)null);
                    final ResultSetMetaData metaData = parameter.metaData();
                    if (null != metaData) {
                        try {
                            for (int j = 1; j <= metaData.getFieldCount(); ++j) {
                                xmlSerializer.startElement(s, "FieldName", "FieldName", (Attributes)null);
                                final String string9 = metaData.getFieldName(j).toString();
                                ((BaseMarkupSerializer)xmlSerializer).characters(string9.toCharArray(), 0, string9.length());
                                xmlSerializer.endElement(s, "FieldName", "FieldName");
                                xmlSerializer.startElement(s, "FieldExtent", "FieldExtent", (Attributes)null);
                                final String string10 = Integer.toString(metaData.getFieldExtent(j));
                                ((BaseMarkupSerializer)xmlSerializer).characters(string10.toCharArray(), 0, string10.length());
                                xmlSerializer.endElement(s, "FieldExtent", "FieldExtent");
                                xmlSerializer.startElement(s, "FieldType", "FieldType", (Attributes)null);
                                final String string11 = metaData.getFieldTypeName(j).toString();
                                ((BaseMarkupSerializer)xmlSerializer).characters(string11.toCharArray(), 0, string11.length());
                                xmlSerializer.endElement(s, "FieldType", "FieldType");
                            }
                        }
                        catch (ProSQLException ex2) {}
                    }
                    xmlSerializer.endElement(s, "MetaData", "MetaData");
                    xmlSerializer.endElement(s, "MethodParameter", "MethodParameter");
                }
                xmlSerializer.endElement(s, "Parameters", "Parameters");
                xmlSerializer.endElement(s, "MethodDescriptor", "MethodDescriptor");
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
    }
}
