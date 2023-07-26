// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl;

import org.apache.soap.rpc.RPCMessage;
import java.util.GregorianCalendar;
import java.sql.ResultSet;
import com.progress.wsa.xmr.XmrResultSetHolder;
import com.progress.open4gl.ProResultSet;
import com.progress.wsa.open4gl.proxy.WSAProcObject;
import com.progress.wsa.open4gl.proxy.WSAProxyObject;
import java.lang.reflect.Method;
import com.progress.open4gl.dynamicapi.ClientException;
import com.progress.open4gl.broker.BrokerException;
import com.progress.open4gl.Open4GLException;
import com.progress.common.ehnlog.AppLogger;
import com.progress.open4gl.ConnectException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.RunTime4GLErrorException;
import java.lang.reflect.InvocationTargetException;
import org.apache.soap.util.MethodUtils;
import com.progress.common.util.UUID;
import com.progress.wsa.admin.MethodParam;
import java.util.Iterator;
import org.apache.soap.encoding.SOAPMappingRegistry;
import com.progress.wsa.xmr.XmrResultSet;
import com.progress.wsa.xmr.ResultSetSerializer;
import org.w3c.dom.Element;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.DOMUtils;
import com.progress.wsa.xmr.SOAP4glXMR;
import com.progress.wsa.xmr.SoapEncParameterSerializer;
import com.progress.open4gl.Holder;
import org.apache.soap.util.Bean;
import org.apache.soap.Header;
import com.progress.wsa.xmr.WsaNil;
import org.apache.soap.rpc.Response;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import java.io.Writer;
import java.io.StringWriter;
import java.util.Vector;
import org.apache.soap.SOAPException;
import org.apache.soap.rpc.Parameter;
import com.progress.wsa.WsaSOAPException;
import org.apache.soap.server.DeploymentDescriptor;
import com.progress.open4gl.dynamicapi.RqContext;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.open4gl.dynamicapi.ParameterSet;
import com.progress.open4gl.dynamicapi.ResultSetSchema;
import com.progress.wsa.admin.MethodDescriptor;
import com.progress.wsa.open4gl.proxy.WSAProxyPool;
import org.apache.soap.util.xml.QName;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.rpc.Call;
import org.apache.soap.Envelope;
import com.progress.wsa.admin.PscDeploymentDescriptor;
import com.progress.wsa.WsaConstants;
import org.apache.soap.util.Provider;

public class Rpc4glProvider implements Provider, WsaConstants
{
    protected PscDeploymentDescriptor m_dd;
    protected Envelope m_envelope;
    protected Call m_call;
    protected String m_targetObjectURI;
    protected String m_methodName;
    protected SOAPContext m_reqContext;
    protected QName m_methodQName;
    protected String m_execMethodName;
    protected SoapHeader m_soapHeader;
    protected WSAProxyPool m_poolManager;
    protected SoapHeader m_respHeader;
    protected String m_respEncStyle;
    protected MethodDescriptor m_methodDesc;
    protected String m_appObjectUUID;
    protected Object m_appObject;
    protected boolean m_isNewAppObject;
    protected String m_connectionURL;
    protected Object[] m_wsaObjArgs;
    protected Class[] m_wsaObjArgTypes;
    protected ResultSetSchema m_resultSetSchema;
    protected ParameterSet m_procParams;
    protected IAppLogger m_log;
    protected String m_reason;
    protected String m_referenceID;
    protected String m_returnValueName;
    protected RqContext m_stateFreeRequestContext;
    protected long m_startTime;
    protected long m_stopTime;
    protected static final String RET_VALUE_NAME = "result";
    protected static final String PROC_FUNC_RET_VALUE_NAME = "retVal";
    protected static final Class[] CONNECT_ARG_CLASSES;
    protected static final Class[] RELEASE_ARG_CLASSES;
    protected static final Class[] RUN_PROC_ARG_CLASSES;
    protected static final Class[] CREATE_PROC_ARG_CLASSES;
    protected static final Class[] CREATE_SUB_ARG_CLASSES;
    protected static final Class[] TEST_APPLICATION_ARG_CLASSES;
    
    public Rpc4glProvider() {
        this.m_dd = null;
        this.m_envelope = null;
        this.m_call = null;
        this.m_targetObjectURI = null;
        this.m_methodName = null;
        this.m_reqContext = null;
        this.m_methodQName = null;
        this.m_execMethodName = null;
        this.m_soapHeader = null;
        this.m_poolManager = null;
        this.m_respHeader = null;
        this.m_respEncStyle = null;
        this.m_methodDesc = null;
        this.m_appObjectUUID = null;
        this.m_appObject = null;
        this.m_isNewAppObject = false;
        this.m_connectionURL = "";
        this.m_wsaObjArgs = null;
        this.m_wsaObjArgTypes = null;
        this.m_resultSetSchema = new ResultSetSchema();
        this.m_procParams = null;
        this.m_log = null;
        this.m_reason = "";
        this.m_referenceID = null;
        this.m_returnValueName = "result";
        this.m_stateFreeRequestContext = null;
        this.m_startTime = 0L;
        this.m_stopTime = 0L;
    }
    
    public long methodStartTime() {
        return this.m_startTime;
    }
    
    public long methodStopTime() {
        return this.m_stopTime;
    }
    
    public void locate(final DeploymentDescriptor deploymentDescriptor, final Envelope envelope, final Call call, final String methodName, final String targetObjectURI, final SOAPContext reqContext) throws SOAPException {
        try {
            final Vector params = ((RPCMessage)call).getParams();
            this.m_dd = (PscDeploymentDescriptor)deploymentDescriptor;
            this.m_envelope = envelope;
            this.m_call = call;
            this.m_methodName = methodName;
            this.m_targetObjectURI = targetObjectURI;
            this.m_reqContext = reqContext;
            this.m_referenceID = new String((String)this.m_reqContext.getProperty("psc.wsa.uuid") + "#" + (String)this.m_reqContext.getProperty("psc.wsa.request.refid"));
            if (null == this.m_referenceID) {
                this.m_referenceID = "0#0";
            }
            this.m_log = this.m_dd.getLogSink();
            if (!(deploymentDescriptor instanceof PscDeploymentDescriptor)) {
                this.m_reason = "Not a Psc Deployment Descriptor.";
                this.m_log.logError(8607504787811871405L, new Object[] { this.m_reason });
                this.m_dd.runtimeStats().incrementCounter(5);
                throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871405L, new Object[] { this.m_reason });
            }
            if (!this.m_dd.getApplicationEnabled()) {
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871401L, new Object[] { this.m_targetObjectURI });
                }
                throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871401L, new Object[] { this.m_targetObjectURI });
            }
            if (this.m_log.ifLogExtended(4L, 2)) {
                this.m_log.logExtended(2, 8607504787811871408L, new Object[] { methodName, targetObjectURI });
            }
            this.m_poolManager = this.m_dd.poolManager();
            this.m_methodQName = new QName((null == this.m_targetObjectURI) ? "" : this.m_targetObjectURI, this.m_methodName);
            try {
                this.m_methodDesc = this.m_dd.getMethod(this.m_methodQName);
            }
            catch (Exception ex) {
                final Object[] array = { this.m_targetObjectURI, this.m_methodName };
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871409L, array);
                }
                this.m_dd.runtimeStats().incrementCounter(4);
                throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Method", 8607504787811871396L, new Object[] { "Not found." }, ex);
            }
            if (0 == this.m_dd.getConnectionMode()) {
                if (0 == this.m_methodDesc.wsaMethodType()) {
                    this.m_isNewAppObject = true;
                }
            }
            else if (null == this.m_poolManager.getSFAppObjectID() && 8 != this.m_methodDesc.wsaMethodType()) {
                this.m_isNewAppObject = true;
            }
            if (this.m_log.ifLogExtended(4L, 2)) {
                if (null == params) {
                    this.m_log.logExtended(2, "Method input params: none.");
                }
                else {
                    final StringBuffer sb = new StringBuffer(256);
                    sb.append("Method input params: \n");
                    for (int i = 0; i < params.size(); ++i) {
                        final Parameter parameter = params.elementAt(i);
                        sb.append("     Parameter: " + parameter.getName() + "\n");
                        sb.append("         data type: " + parameter.getType().toString() + "\n");
                        sb.append("         encoding: " + parameter.getEncodingStyleURI() + "\n");
                        if (null == parameter.getValue()) {
                            sb.append("         value: nil\n");
                        }
                        else {
                            sb.append("         value: " + parameter.getValue().toString() + "\n");
                        }
                    }
                    this.m_log.logExtended(2, sb.toString());
                }
            }
            if (this.m_dd.defaultEncoding().equals(WsaConstants.WSA_SERVICE_ENCODING[1])) {
                this.m_respEncStyle = this.m_dd.defaultEncoding();
            }
            if (8 != this.m_methodDesc.wsaMethodType()) {
                this.locateHeaderInformation(call);
            }
        }
        catch (Exception ex2) {
            if (ex2 instanceof WsaSOAPException) {
                this.releaseProviderReferences();
                throw (SOAPException)ex2;
            }
            final Object[] array2 = { ex2.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871405L, array2);
            }
            this.m_dd.runtimeStats().incrementCounter(5);
            this.releaseProviderReferences();
            throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871405L, array2, ex2);
        }
    }
    
    public void invoke(final SOAPContext soapContext, final SOAPContext soapContext2) throws SOAPException {
        try {
            Response response;
            if (1 == this.m_dd.getConnectionMode()) {
                this.createSessionFreeAppObject();
                if (null == this.m_appObjectUUID) {
                    this.m_appObjectUUID = this.m_poolManager.getSFAppObjectID();
                }
                if (8 != this.m_methodDesc.wsaMethodType()) {
                    this.locateProxyObject();
                }
                response = this.invokeSOAPMethod(soapContext, soapContext2);
            }
            else {
                if (!this.m_isNewAppObject && 8 != this.m_methodDesc.wsaMethodType()) {
                    this.locateProxyObject();
                }
                response = this.invokeSOAPMethod(soapContext, soapContext2);
            }
            if (null != this.m_respHeader) {
                ((RPCMessage)response).setHeader(this.m_respHeader.getSOAPHeader());
            }
            final Envelope buildEnvelope = response.buildEnvelope();
            final StringWriter stringWriter = new StringWriter();
            buildEnvelope.marshall((Writer)stringWriter, (XMLJavaMappingRegistry)this.m_call.getSOAPMappingRegistry(), soapContext2);
            soapContext2.setRootPart(stringWriter.toString(), "text/xml;charset=utf-8");
        }
        catch (Exception ex) {
            if (ex instanceof WsaSOAPException) {
                throw (WsaSOAPException)ex;
            }
            final Object[] array = { ex.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871411L, array);
            }
            this.m_dd.runtimeStats().incrementCounter(5);
            throw new WsaSOAPException(this.resolveFaultCode(ex), 8607504787811871411L, array, ex);
        }
        finally {
            if (null != this.m_stateFreeRequestContext) {
                try {
                    this.m_stateFreeRequestContext._release();
                }
                catch (Exception ex2) {
                    final Object[] array2 = { ex2.getMessage() };
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811871406L, array2);
                    }
                    this.m_dd.runtimeStats().incrementCounter(5);
                }
            }
            this.releaseProviderReferences();
        }
    }
    
    public String runInternalTest(final String s, final String s2) throws WsaSOAPException {
        String s3 = s2;
        if (null == s3) {
            s3 = "SUCCESS";
        }
        return s3;
    }
    
    protected void locateHeaderInformation(final Call call) throws WsaSOAPException {
        this.m_soapHeader = new SoapHeader(((RPCMessage)call).getHeader(), this.m_dd);
        this.m_appObjectUUID = this.m_soapHeader.getObjectUUID();
        if (this.m_isNewAppObject) {
            this.m_connectionURL = this.m_dd.getConnectionURL();
        }
        else {
            final String uuidHeader = this.m_soapHeader.getUUIDHeader();
            if (1 != this.m_dd.getConnectionMode() && null == uuidHeader) {
                final Object[] array = { this.m_targetObjectURI, this.m_methodName, "UUID" };
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871410L, array);
                }
                this.m_dd.runtimeStats().incrementCounter(4);
                throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Header", 8607504787811871410L, array);
            }
            if (null != uuidHeader) {
                final String wsauuid = this.m_soapHeader.getWSAUUID();
                final String anObject = (String)this.m_reqContext.getProperty("psc.wsa.uuid");
                if (!wsauuid.equals(anObject)) {
                    final Object[] array2 = { wsauuid, anObject };
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811871420L, array2);
                    }
                    this.m_dd.runtimeStats().incrementCounter(4);
                    throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Header", 8607504787811871419L, new Object[0]);
                }
            }
        }
    }
    
    protected Response invokeSOAPMethod(final SOAPContext soapContext, final SOAPContext soapContext2) throws SOAPException {
        this.fixupStaticParameterList();
        this.buildJavaMethodParameters();
        final Bean invokeJavaMethod = this.invokeJavaMethod();
        this.postProcessMethodResults(invokeJavaMethod);
        Parameter parameter = null;
        if (invokeJavaMethod.type != Void.TYPE) {
            if (null == invokeJavaMethod.value && null == this.m_respEncStyle) {
                invokeJavaMethod.value = new WsaNil();
                invokeJavaMethod.type = WsaNil.class;
            }
            parameter = new Parameter(this.m_returnValueName, invokeJavaMethod.type, invokeJavaMethod.value, this.m_respEncStyle);
        }
        if (this.m_log.ifLogExtended(4L, 2)) {
            if (null == parameter) {
                this.m_log.logExtended(2, "Method return parameter: none.");
            }
            else {
                final StringBuffer sb = new StringBuffer(256);
                sb.append("Method return parameter: \n");
                sb.append("     Parameter: " + parameter.getName() + "\n");
                sb.append("         data type: " + parameter.getType().toString() + "\n");
                sb.append("         encoding: " + parameter.getEncodingStyleURI() + "\n");
                if (null == parameter.getValue()) {
                    sb.append("         value: nil\n");
                }
                else {
                    sb.append("         value: " + parameter.getValue().toString() + "\n");
                }
                this.m_log.logExtended(2, sb.toString());
            }
        }
        Vector buildResponseParameters;
        try {
            buildResponseParameters = this.buildResponseParameters();
            if (this.m_log.ifLogExtended(4L, 2)) {
                if (null == buildResponseParameters) {
                    this.m_log.logExtended(2, "Method output params: none.");
                }
                else {
                    final StringBuffer sb2 = new StringBuffer(256);
                    sb2.append("Method output params: \n");
                    for (int i = 0; i < buildResponseParameters.size(); ++i) {
                        final Parameter parameter2 = buildResponseParameters.elementAt(i);
                        sb2.append("     Parameter: " + parameter2.getName() + "\n");
                        sb2.append("         data type: " + parameter2.getType().toString() + "\n");
                        sb2.append("         encoding: " + parameter2.getEncodingStyleURI() + "\n");
                        if (null == parameter2.getValue()) {
                            sb2.append("         value: nil\n");
                        }
                        else {
                            sb2.append("         value: " + parameter2.getValue().toString() + "\n");
                        }
                    }
                    this.m_log.logExtended(2, sb2.toString());
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof WsaSOAPException) {
                throw (SOAPException)ex;
            }
            this.m_reason = "Error detected building response argument list.";
            final Object[] array = { ex.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871411L, array);
            }
            this.m_dd.runtimeStats().incrementCounter(4);
            throw new WsaSOAPException("SOAP-ENV:ServerNetwork", 8607504787811871411L, array, ex);
        }
        return new WsaResponse(((RPCMessage)this.m_call).getTargetObjectURI(), ((RPCMessage)this.m_call).getMethodName(), parameter, (0 == buildResponseParameters.size()) ? null : buildResponseParameters, null, this.m_respEncStyle, soapContext2);
    }
    
    protected boolean isHolderType(final Parameter parameter) {
        boolean b = false;
        if (null != parameter && null != parameter.getValue() && parameter.getValue() instanceof Holder) {
            b = true;
        }
        return b;
    }
    
    protected void fixupStaticParameterList() throws WsaSOAPException {
        try {
            final Vector params = ((RPCMessage)this.m_call).getParams();
            final SoapEncParameterSerializer soapEncParameterSerializer = new SoapEncParameterSerializer();
            String name = null;
            final SOAPMappingRegistry buildSOAPMappingRegistry = DeploymentDescriptor.buildSOAPMappingRegistry((DeploymentDescriptor)this.m_dd, this.m_reqContext);
            if (!(buildSOAPMappingRegistry instanceof SOAP4glXMR)) {
                this.m_reason = "Invalid SOAPMappingRegistry detected";
                final Object[] array = { this.m_reason };
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871399L, array);
                }
                this.m_dd.runtimeStats().incrementCounter(5);
                throw new WsaSOAPException("SOAP-ENV:Server.Wsa", 8607504787811871399L, array);
            }
            if (null != params) {
                for (final Parameter parameter : params) {
                    if (name == parameter.getName()) {
                        continue;
                    }
                    MethodParam parameter2;
                    try {
                        String s = parameter.getName();
                        if (s.lastIndexOf(":") != -1) {
                            s = s.substring(s.lastIndexOf(":") + 1);
                        }
                        parameter2 = this.m_methodDesc.parameter(s);
                    }
                    catch (Exception ex3) {
                        final Object[] array2 = { this.m_methodName, parameter.getName() };
                        if (this.m_log.ifLogVerbose(4L, 2)) {
                            this.m_log.logVerbose(2, 8607504787811871413L, array2);
                        }
                        this.m_dd.runtimeStats().incrementCounter(4);
                        throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Parameter", 8607504787811871413L, array2);
                    }
                    if (2 == parameter2.inOutType() || Element.class != parameter.getType()) {
                        continue;
                    }
                    final Object value = parameter.getValue();
                    parameter.setType(parameter2.javaType());
                    if (null == value && parameter2.isExtent() && this.m_dd.defaultEncodingType() == 3) {
                        final String name2 = parameter.getName();
                        final Element arrayElemFromBody = this.getArrayElemFromBody(name2);
                        if (null != arrayElemFromBody) {
                            final String attributeNS = DOMUtils.getAttributeNS(arrayElemFromBody, "http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle");
                            final QName qName = new QName("http://www.w3.org/2001/XMLSchema", this.soapTypeFromClass(parameter2, name2) + "Array");
                            parameter.setValue(((XMLJavaMappingRegistry)buildSOAPMappingRegistry).queryDeserializer(qName, attributeNS).unmarshall(attributeNS, qName, (Node)arrayElemFromBody, (XMLJavaMappingRegistry)buildSOAPMappingRegistry, this.m_reqContext).value);
                            parameter.setEncodingStyleURI(((RPCMessage)this.m_call).getEncodingStyleURI());
                            name = name2;
                        }
                    }
                    if (null == value && ResultSet.class == parameter2.javaType()) {
                        this.m_resultSetSchema.addSchema(null, parameter2.paramNumber(), parameter2.inOutType());
                        parameter.setValue((Object)null);
                        parameter.setEncodingStyleURI(((RPCMessage)this.m_call).getEncodingStyleURI());
                    }
                    if (null == value) {
                        continue;
                    }
                    final Element element = (Element)value;
                    String s2 = DOMUtils.getAttributeNS(element, "http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle");
                    String s3 = this.soapTypeFromClass(parameter2, parameter.getName());
                    if (parameter2.isExtent()) {
                        s3 += "Array";
                        name = parameter.getName();
                    }
                    if (null == DOMUtils.getNamespaceURIFromPrefix((Node)element, "xsd")) {
                        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
                    }
                    if (null == DOMUtils.getNamespaceURIFromPrefix((Node)element, "xsi")) {
                        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                    }
                    element.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "xsd:" + s3);
                    if (null == s2) {
                        s2 = parameter.getEncodingStyleURI();
                        if (null == s2) {
                            s2 = ((RPCMessage)this.m_call).getEncodingStyleURI();
                        }
                    }
                    if (ResultSet.class != parameter2.javaType()) {
                        final Parameter parameter3 = (Parameter)soapEncParameterSerializer.unmarshall(s2, parameter2.name(), (Node)element, (XMLJavaMappingRegistry)buildSOAPMappingRegistry, this.m_reqContext).value;
                        parameter.setValue(parameter3.getValue());
                        parameter.setEncodingStyleURI(parameter3.getEncodingStyleURI());
                    }
                    else {
                        final Bean unmarshall = new ResultSetSerializer().unmarshall(s2, parameter2.name(), element, (XMLJavaMappingRegistry)buildSOAPMappingRegistry, this.m_reqContext, parameter2.metaData());
                        this.m_resultSetSchema.addSchema((ResultSetMetaData)((XmrResultSet)unmarshall.value).getMetaData(), parameter2.paramNumber(), parameter2.inOutType());
                        parameter.setValue(unmarshall.value);
                        parameter.setEncodingStyleURI(((RPCMessage)this.m_call).getEncodingStyleURI());
                    }
                }
            }
        }
        catch (WsaSOAPException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            if (ex2 instanceof WsaSOAPException) {
                throw (WsaSOAPException)ex2;
            }
            final Object[] array3 = { ex2.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871394L, array3);
            }
            this.m_dd.runtimeStats().incrementCounter(4);
            throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Parameter", 8607504787811871394L, array3, ex2);
        }
    }
    
    protected String soapTypeFromClass(final MethodParam methodParam, final String s) throws SOAPException {
        final Class javaType = methodParam.javaType();
        if (null != javaType) {
            final String name = javaType.getName();
            final String substring = name.substring(name.lastIndexOf(".") + 1);
            String s2;
            if (0 == substring.compareTo("GregorianCalendar")) {
                s2 = this.gregorianCalenderType(methodParam);
            }
            else if (0 == substring.compareTo("String")) {
                s2 = "stringPrgs";
            }
            else if (0 == substring.compareTo("Integer")) {
                s2 = "intPrgs";
            }
            else if (0 == substring.compareTo("Boolean")) {
                s2 = "booleanPrgs";
            }
            else if (0 == substring.compareTo("BigDecimal")) {
                s2 = "decimalPrgs";
            }
            else if (0 == substring.compareTo("Long")) {
                s2 = "recid";
            }
            else if (0 == substring.compareTo("[B")) {
                s2 = "raw";
            }
            else {
                s2 = substring.toLowerCase();
            }
            return s2;
        }
        final Object[] array = { this.m_methodName, s };
        if (this.m_log.ifLogVerbose(4L, 2)) {
            this.m_log.logVerbose(2, 8607504787811871414L, array);
        }
        this.m_dd.runtimeStats().incrementCounter(4);
        throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Parameter", 8607504787811871414L, array);
    }
    
    private String gregorianCalenderType(final MethodParam methodParam) {
        if (methodParam.pscType() == 2) {
            return "datePrgs";
        }
        if (methodParam.pscType() == 34) {
            return "dateTimeS";
        }
        if (methodParam.pscType() == 40) {
            return "dateTimeTZ";
        }
        return null;
    }
    
    protected String getObjectUUID(final Object o) throws NullPointerException {
        if (null == o) {
            throw new NullPointerException("Cannot get a UUID from a null object.");
        }
        String s;
        if (this.m_dd.isDynamicApplication()) {
            s = new UUID().toString();
        }
        else {
            s = new UUID().toString();
        }
        return s;
    }
    
    protected void locateProxyObject() throws WsaSOAPException {
        try {
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871468L, new Object[] { this.m_soapHeader.getUUIDHeader() });
            }
            if (null != this.m_appObjectUUID) {
                this.m_appObject = this.m_poolManager.getProxyObject(this.m_appObjectUUID);
            }
        }
        catch (Exception ex) {
            final Object[] array = { this.m_targetObjectURI, "UUID", this.m_soapHeader.getObjectUUID() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871412L, array);
            }
            this.m_dd.runtimeStats().incrementCounter(3);
            throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Header", 8607504787811871397L, new Object[] { "Cannot find object." });
        }
        if (null == this.m_appObject) {
            final Object[] array2 = { this.m_targetObjectURI, "UUID", this.m_soapHeader.getObjectUUID() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871412L, array2);
            }
            this.m_dd.runtimeStats().incrementCounter(3);
            throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Header", 8607504787811871397L, new Object[] { "Cannot find object." });
        }
    }
    
    protected void buildJavaMethodParameters() throws WsaSOAPException {
        final Vector params = ((RPCMessage)this.m_call).getParams();
        try {
            int n = 0;
            boolean b = false;
            switch (this.m_methodDesc.wsaMethodType()) {
                case 0: {
                    this.m_appObject = this.m_poolManager;
                    this.m_execMethodName = "newAppObject";
                    (this.m_wsaObjArgs = new Object[Rpc4glProvider.CONNECT_ARG_CLASSES.length])[0] = this.m_referenceID;
                    this.m_wsaObjArgs[1] = this.m_connectionURL;
                    this.m_wsaObjArgTypes = Rpc4glProvider.CONNECT_ARG_CLASSES;
                    n = 2;
                    break;
                }
                case 2: {
                    this.m_appObject = this.m_poolManager;
                    this.m_execMethodName = "newProcObject";
                    (this.m_wsaObjArgs = new Object[Rpc4glProvider.CREATE_PROC_ARG_CLASSES.length])[0] = this.m_referenceID;
                    this.m_wsaObjArgs[1] = this.m_appObjectUUID;
                    this.m_wsaObjArgs[2] = this.m_methodDesc.procedureName() + ".p";
                    this.m_wsaObjArgTypes = Rpc4glProvider.CREATE_PROC_ARG_CLASSES;
                    n = 3;
                    b = true;
                    this.m_wsaObjArgs[4] = this.m_resultSetSchema;
                    break;
                }
                case 6: {
                    this.m_appObject = this.m_poolManager;
                    this.m_execMethodName = "newSubAppObject";
                    (this.m_wsaObjArgs = new Object[Rpc4glProvider.CREATE_SUB_ARG_CLASSES.length])[0] = this.m_appObjectUUID;
                    this.m_wsaObjArgTypes = Rpc4glProvider.CREATE_SUB_ARG_CLASSES;
                    n = -1;
                    break;
                }
                case 1: {
                    this.m_execMethodName = "_release";
                    this.m_wsaObjArgs = new Object[0];
                    this.m_wsaObjArgTypes = Rpc4glProvider.RELEASE_ARG_CLASSES;
                    n = -1;
                    break;
                }
                case 3: {
                    this.m_execMethodName = "runProcedure";
                    (this.m_wsaObjArgs = new Object[Rpc4glProvider.RUN_PROC_ARG_CLASSES.length])[0] = this.m_referenceID;
                    this.m_wsaObjArgs[1] = this.m_methodDesc.procedureName() + ".p";
                    b = true;
                    this.m_wsaObjArgTypes = Rpc4glProvider.RUN_PROC_ARG_CLASSES;
                    n = 2;
                    this.m_wsaObjArgs[3] = this.m_resultSetSchema;
                    break;
                }
                case 4:
                case 7: {
                    this.m_execMethodName = "runProcedure";
                    (this.m_wsaObjArgs = new Object[Rpc4glProvider.RUN_PROC_ARG_CLASSES.length])[0] = this.m_referenceID;
                    this.m_wsaObjArgs[1] = this.m_methodDesc.procedureName();
                    b = true;
                    this.m_wsaObjArgTypes = Rpc4glProvider.RUN_PROC_ARG_CLASSES;
                    n = 2;
                    this.m_wsaObjArgs[3] = this.m_resultSetSchema;
                    break;
                }
                case 8: {
                    this.m_appObject = this;
                    this.m_execMethodName = this.m_methodDesc.wsaMethodName();
                    this.m_wsaObjArgs = new Object[Rpc4glProvider.TEST_APPLICATION_ARG_CLASSES.length];
                    this.m_wsaObjArgTypes = Rpc4glProvider.TEST_APPLICATION_ARG_CLASSES;
                    n = 0;
                    break;
                }
                default: {
                    this.m_execMethodName = this.m_methodDesc.wsaMethodName();
                    break;
                }
            }
            if (-1 != n) {
                this.buildReflectionParams(params, n, b);
            }
            switch (this.m_methodDesc.wsaMethodType()) {
                case 4: {
                    this.setProcInternalFunction((ParameterSet)this.m_wsaObjArgs[2]);
                    break;
                }
            }
        }
        catch (Exception ex) {
            if (ex instanceof WsaSOAPException) {
                throw (WsaSOAPException)ex;
            }
            final Object[] array = { ex.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871394L, array);
            }
            this.m_dd.runtimeStats().incrementCounter(4);
            throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Parameter", 8607504787811871394L, array, ex);
        }
    }
    
    protected Bean invokeJavaMethod() throws WsaSOAPException {
        final Bean bean = new Bean((Class)Void.TYPE, (Object)null);
        try {
            Method method;
            try {
                method = MethodUtils.getMethod(this.m_appObject, this.m_execMethodName, this.m_wsaObjArgTypes);
            }
            catch (NoSuchMethodException ex) {
                final Object[] array = { this.m_methodName };
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871396L, array);
                }
                this.m_dd.runtimeStats().incrementCounter(4);
                throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Method", 8607504787811871396L, array, ex);
            }
            catch (Exception ex2) {
                final Object[] array2 = { ex2.getMessage() };
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871396L, array2);
                }
                this.m_dd.runtimeStats().incrementCounter(4);
                throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Method", 8607504787811871396L, array2, ex2);
            }
            try {
                try {
                    this.m_startTime = System.currentTimeMillis();
                    final Object invoke = method.invoke(this.m_appObject, this.m_wsaObjArgs);
                    this.m_stopTime = System.currentTimeMillis();
                    if (null != invoke) {
                        bean.type = invoke.getClass();
                        bean.value = invoke;
                    }
                }
                catch (InvocationTargetException ex3) {
                    final Throwable targetException = ex3.getTargetException();
                    if (null != targetException) {
                        throw targetException;
                    }
                    throw ex3;
                }
            }
            catch (Exception ex4) {
                Object[] array3 = null;
                String s = null;
                final int intValue = this.m_dd.getProps().get("PROGRESS.Session.serviceFaultLevel");
                if (ex4 instanceof RunTime4GLErrorException && ((RunTime4GLException)ex4).hasProcReturnString()) {
                    this.m_reason = "The server application returned the error: " + ((RunTime4GLException)ex4).getProcReturnString();
                    array3 = new Object[] { this.m_reason };
                    if (2 >= intValue) {
                        s = ((RunTime4GLException)ex4).getProcReturnString();
                    }
                    else {
                        s = "The server application returned the error: " + ((RunTime4GLException)ex4).getProcReturnString();
                    }
                }
                if (null == array3) {
                    array3 = new Object[] { ex4.getMessage() };
                }
                if (ex4 instanceof ConnectException) {
                    if (this.m_log.ifLogBasic(4L, 2)) {
                        this.m_log.logBasic(2, 8607504787811871406L, array3);
                    }
                    if (null == s) {
                        if (((ConnectException)ex4).hasProcReturnString()) {
                            s = ((ConnectException)ex4).getProcReturnString();
                        }
                        else if (2 >= intValue) {
                            s = this.resolveFaultString(ex4);
                        }
                        else {
                            s = AppLogger.formatMessage(8607504787811871406L, array3);
                        }
                    }
                }
                else if (ex4 instanceof Open4GLException) {
                    if (this.m_log.ifLogBasic(4L, 2)) {
                        this.m_log.logBasic(2, 8607504787811871406L, array3);
                    }
                    if (null == s) {
                        if (((ConnectException)ex4).hasProcReturnString()) {
                            s = ((ConnectException)ex4).getProcReturnString();
                        }
                        else if (2 >= intValue) {
                            s = this.resolveFaultString(ex4);
                        }
                        else {
                            s = AppLogger.formatMessage(8607504787811871406L, array3);
                        }
                    }
                }
                else if (ex4 instanceof RunTime4GLException || ex4 instanceof BrokerException || ex4 instanceof ClientException) {
                    if (this.m_log.ifLogBasic(4L, 2)) {
                        this.m_log.logBasic(2, 8607504787811871406L, array3);
                    }
                    if (null == s) {
                        if (2 >= intValue) {
                            s = this.resolveFaultString(ex4);
                        }
                        else {
                            s = AppLogger.formatMessage(8607504787811871406L, array3);
                        }
                    }
                }
                else {
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811871406L, array3);
                    }
                    s = AppLogger.formatMessage(8607504787811871406L, array3);
                }
                this.countOpenClientException(ex4);
                throw new WsaSOAPException(this.resolveFaultCode(ex4), s, ex4);
            }
        }
        catch (WsaSOAPException ex5) {
            throw ex5;
        }
        catch (Throwable t) {
            final Object[] array4 = { t.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871396L, array4);
            }
            this.m_dd.runtimeStats().incrementCounter(5);
            throw new WsaSOAPException("SOAP-ENV:ServerNetwork", 8607504787811871396L, array4, t);
        }
        return bean;
    }
    
    protected void postProcessMethodResults(final Bean bean) throws WsaSOAPException {
        final Object value = bean.value;
        try {
            switch (this.m_methodDesc.wsaMethodType()) {
                case 0: {
                    if (1 == this.m_dd.getConnectionMode()) {
                        final Object[] array = { "Cannot execute a Session-Free AppObject constructor." };
                        if (this.m_log.ifLogVerbose(4L, 2)) {
                            this.m_log.logVerbose(2, 8607504787811871406L, array);
                        }
                        this.m_dd.runtimeStats().incrementCounter(5);
                        throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871406L, array);
                    }
                    (this.m_respHeader = new SoapHeader(this.m_dd)).buildNewSoapHeader((String)this.m_reqContext.getProperty("psc.wsa.uuid"), ((WSAProxyObject)value).getWSAObjectID(), null, null);
                    if (this.m_log.ifLogExtended(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811871469L, new Object[] { this.m_respHeader.getUUIDHeader(), ((WSAProcObject)value)._getConnectionId() });
                    }
                    if (!this.connectReturnsValue()) {
                        bean.value = null;
                        bean.type = Void.TYPE;
                        break;
                    }
                    bean.value = null;
                    bean.type = String.class;
                    if (((WSAProcObject)value).getReturnValue() != null) {
                        bean.value = ((WSAProcObject)value).getReturnValue();
                        break;
                    }
                    break;
                }
                case 2:
                case 6: {
                    String substring = "InvalidObjectName";
                    final int index = this.m_methodName.indexOf("_");
                    if (-1 != index) {
                        substring = this.m_methodName.substring(index + 1);
                    }
                    final String s = this.m_targetObjectURI.startsWith("urn:") ? ":" : "/";
                    (this.m_respHeader = new SoapHeader(this.m_dd)).buildNewSoapHeader((String)this.m_reqContext.getProperty("psc.wsa.uuid"), ((WSAProcObject)value).getWSAObjectID(), substring, this.m_targetObjectURI.substring(0, this.m_targetObjectURI.lastIndexOf(s)) + s + substring);
                    if (this.m_log.ifLogExtended(4L, 2)) {
                        this.m_log.logExtended(2, 8607504787811871470L, new Object[] { this.m_respHeader.getUUIDHeader() });
                    }
                    bean.value = null;
                    bean.type = Void.TYPE;
                    if (2 == this.m_methodDesc.wsaMethodType() && (3 == this.m_dd.defaultEncodingType() || (3 != this.m_dd.defaultEncodingType() && this.m_methodDesc.useRetVal()))) {
                        bean.type = String.class;
                        Object value2 = null;
                        if (this.m_methodDesc.useRetVal()) {
                            if (1 == this.m_dd.getConnectionMode()) {
                                if (null != value && value instanceof WSAProcObject) {
                                    value2 = ((WSAProcObject)value)._getReturnValue(((WSAProcObject)value).getSessionID());
                                }
                            }
                            else {
                                value2 = ((WSAProcObject)value)._getReturnValue();
                            }
                        }
                        bean.value = value2;
                    }
                    break;
                }
                case 1: {
                    this.m_poolManager._release(this.m_appObjectUUID);
                    if (this.m_log.ifLogExtended(4L, 2)) {
                        this.m_log.logExtended(2, 8607504787811871471L, new Object[] { this.m_soapHeader.getUUIDHeader() });
                    }
                    bean.value = null;
                    bean.type = Void.TYPE;
                    break;
                }
                case 4: {
                    if (1 == this.m_dd.getConnectionMode()) {
                        this.m_stateFreeRequestContext = (RqContext)value;
                    }
                    bean.type = this.m_methodDesc.returnType();
                    final ParameterSet set = (ParameterSet)this.m_wsaObjArgs[2];
                    bean.value = set.getFunctionReturnValue();
                    if (GregorianCalendar.class == bean.type || set.getIsFunctionReturnExtent()) {
                        final ParamHolderDetail paramHolderDetail = new ParamHolderDetail(this.m_methodDesc.getReturnParam(), bean.value);
                        if (paramHolderDetail.hasHolder()) {
                            bean.value = paramHolderDetail.getHolder();
                        }
                        bean.type = paramHolderDetail.getType();
                    }
                    break;
                }
                case 3:
                case 7: {
                    if (this.m_appObject instanceof WSAProxyObject) {
                        Object value3 = null;
                        bean.value = null;
                        bean.type = Void.TYPE;
                        if (1 == this.m_dd.getConnectionMode()) {
                            this.m_stateFreeRequestContext = (RqContext)value;
                        }
                        if (3 == this.m_dd.defaultEncodingType() || (3 != this.m_dd.defaultEncodingType() && this.m_methodDesc.useRetVal())) {
                            bean.type = String.class;
                            if (this.m_methodDesc.useRetVal()) {
                                if (1 == this.m_dd.getConnectionMode()) {
                                    this.m_stateFreeRequestContext = (RqContext)value;
                                    if (null != value) {
                                        value3 = ((RqContext)value)._getReturnValue();
                                    }
                                }
                                else {
                                    value3 = ((WSAProxyObject)this.m_appObject)._getReturnValue();
                                }
                            }
                            bean.value = value3;
                        }
                        break;
                    }
                    break;
                }
                case 8: {
                    if (null != value) {
                        bean.type = ((RqContext)value).getClass();
                        bean.value = value;
                        break;
                    }
                    break;
                }
                default: {
                    if (null != value) {
                        bean.type = ((RqContext)value).getClass();
                        bean.value = value;
                        break;
                    }
                    break;
                }
            }
        }
        catch (WsaSOAPException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            final Object[] array2 = { ex2.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871406L, array2);
            }
            this.m_dd.runtimeStats().incrementCounter(5);
            throw new WsaSOAPException(this.resolveFaultCode(ex2), 8607504787811871406L, array2, ex2);
        }
    }
    
    protected void createSessionFreeAppObject() throws WsaSOAPException {
        try {
            if (null != this.m_poolManager.getSFAppObjectID() || 8 == this.m_methodDesc.wsaMethodType()) {
                return;
            }
            Method method;
            try {
                method = MethodUtils.getMethod((Object)this.m_poolManager, "newSFAppObject", Rpc4glProvider.CONNECT_ARG_CLASSES);
            }
            catch (NoSuchMethodException ex) {
                final Object[] array = { "newSFAppObject" };
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871396L, array);
                }
                this.m_dd.runtimeStats().incrementCounter(5);
                throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Method", 8607504787811871396L, array, ex);
            }
            catch (Exception ex2) {
                final Object[] array2 = { ex2.getMessage() };
                if (this.m_log.ifLogVerbose(4L, 2)) {
                    this.m_log.logVerbose(2, 8607504787811871396L, array2);
                }
                this.m_dd.runtimeStats().incrementCounter(5);
                throw new WsaSOAPException("SOAP-ENV:Client.SOAP-Method", 8607504787811871396L, array2, ex2);
            }
            try {
                try {
                    method.invoke(this.m_poolManager, this.m_referenceID, this.m_connectionURL, "", "", "");
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811871472L, new Object[] { this.m_poolManager.getSFAppObjectID() });
                    }
                }
                catch (InvocationTargetException ex3) {
                    final Throwable targetException = ex3.getTargetException();
                    if (null != targetException) {
                        throw targetException;
                    }
                    throw ex3;
                }
            }
            catch (Exception ex4) {
                Object[] array3 = null;
                String s = null;
                final int intValue = this.m_dd.getProps().get("PROGRESS.Session.serviceFaultLevel");
                if (ex4 instanceof RunTime4GLErrorException && ((RunTime4GLException)ex4).hasProcReturnString()) {
                    this.m_reason = "The server application returned the error: " + ((RunTime4GLException)ex4).getProcReturnString();
                    array3 = new Object[] { this.m_reason };
                    if (2 >= intValue) {
                        s = ((RunTime4GLException)ex4).getProcReturnString();
                    }
                    else {
                        s = "The server application returned the error: " + ((RunTime4GLException)ex4).getProcReturnString();
                    }
                }
                if (null == array3) {
                    array3 = new Object[] { ex4.getMessage() };
                }
                if (ex4 instanceof ConnectException || ex4 instanceof RunTime4GLException || ex4 instanceof BrokerException || ex4 instanceof ClientException || ex4 instanceof Open4GLException) {
                    if (this.m_log.ifLogBasic(4L, 2)) {
                        this.m_log.logBasic(2, 8607504787811871406L, array3);
                    }
                    if (null == s) {
                        if (2 >= intValue) {
                            s = this.resolveFaultString(ex4);
                        }
                        else {
                            s = AppLogger.formatMessage(8607504787811871406L, array3);
                        }
                    }
                }
                else {
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811871406L, array3);
                    }
                    s = AppLogger.formatMessage(8607504787811871406L, array3);
                }
                this.countOpenClientException(ex4);
                throw new WsaSOAPException(this.resolveFaultCode(ex4), s, ex4);
            }
        }
        catch (WsaSOAPException ex5) {
            throw ex5;
        }
        catch (Throwable t) {
            final Object[] array4 = { t.getMessage() };
            if (this.m_log.ifLogVerbose(4L, 2)) {
                this.m_log.logVerbose(2, 8607504787811871406L, array4);
            }
            this.m_dd.runtimeStats().incrementCounter(5);
            throw new WsaSOAPException("SOAP-ENV:ServerNetwork", 8607504787811871406L, array4, t);
        }
    }
    
    protected void buildReflectionParams(final Vector vector, final int n, final boolean b) throws Exception {
        if (b) {
            this.buildParameterSetParam(vector, n);
        }
        else {
            this.buildJavaMethodParams(vector, n);
        }
    }
    
    protected void buildParameterSetParam(final Vector vector, final int n) throws Exception {
        int index = 0;
        final int n2 = (null == vector) ? 0 : vector.size();
        final int defaultEncodingType = this.m_dd.defaultEncodingType();
        final boolean b = defaultEncodingType || 2 == defaultEncodingType;
        final int parameterCount = this.m_methodDesc.parameterCount();
        this.m_procParams = new ParameterSet(parameterCount);
        this.m_wsaObjArgs[n] = this.m_procParams;
        for (int i = 0; i < parameterCount; ++i) {
            final MethodParam parameter = this.m_methodDesc.parameter(i);
            final int pscType = parameter.pscType();
            final boolean b2 = 36 == pscType || 37 == pscType;
            if (2 == parameter.inOutType()) {
                final Object o = null;
                if (ResultSet.class == parameter.javaType()) {
                    this.m_resultSetSchema.addSchemaByParamNum(parameter.metaData(), parameter.paramNumber(), parameter.inOutType());
                }
                this.m_procParams.setParameter(i + 1, o, parameter.inOutType(), pscType, parameter.isExtent(), parameter.getExtent(), b2, b, parameter.writeXmlBeforeImage());
            }
            else if (index >= n2) {
                this.m_procParams.setParameter(i + 1, null, parameter.inOutType(), pscType, parameter.isExtent(), parameter.getExtent(), b2, b, parameter.writeXmlBeforeImage());
            }
            else {
                final Parameter parameter2 = vector.elementAt(index);
                if (parameter2.getType() != parameter.javaType()) {
                    if (this.m_log.ifLogVerbose(4L, 2)) {
                        this.m_log.logVerbose(2, 8607504787811871416L, new Object[] { this.m_methodName, parameter.name().toString(), parameter.javaType().getName() });
                    }
                    final Object[] array = { parameter.name(), this.soapTypeFromClass(parameter, parameter.name().getLocalPart()) };
                    this.m_dd.runtimeStats().incrementCounter(4);
                    throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871417L, array);
                }
                this.m_procParams.setParameter(i + 1, parameter2.getValue(), parameter.inOutType(), pscType, parameter.isExtent(), parameter.getExtent(), b2, b, parameter.writeXmlBeforeImage());
                ++index;
                if (parameter.isExtent() && this.getCurrentEncoding() == 3 && index < n2) {
                    for (String paramLocal = this.getParamLocal(parameter2.getName()), anotherString = this.getParamLocal(vector.elementAt(index).getName()); paramLocal.compareTo(anotherString) == 0 && ++index < n2; anotherString = this.getParamLocal(vector.elementAt(index).getName())) {}
                }
            }
        }
    }
    
    protected void buildJavaMethodParams(final Vector vector, final int n) throws Exception {
        int n2 = n;
        int n3 = 0;
        for (int n4 = (null == vector) ? 0 : vector.size(), i = 0; i < n4; ++i) {
            final MethodParam parameter = this.m_methodDesc.parameter(n3);
            if (2 != parameter.inOutType()) {
                if (n3 >= n4) {
                    this.m_wsaObjArgs[n2] = null;
                    this.m_wsaObjArgTypes[n2++] = Void.TYPE;
                }
                else {
                    final Parameter parameter2 = vector.elementAt(n3++);
                    if (parameter2.getType() != parameter.javaType()) {
                        if (this.m_log.ifLogVerbose(4L, 2)) {
                            this.m_log.logVerbose(2, 8607504787811871416L, new Object[] { this.m_methodName, parameter.name().toString(), parameter.javaType().getName() });
                        }
                        final Object[] array = { parameter.name(), this.soapTypeFromClass(parameter, parameter.name().getLocalPart()) };
                        this.m_dd.runtimeStats().incrementCounter(4);
                        throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871416L, array);
                    }
                    this.m_wsaObjArgs[n2] = parameter2.getValue();
                    this.m_wsaObjArgTypes[n2++] = parameter2.getType();
                }
            }
            else {
                this.m_wsaObjArgs[i] = null;
                this.m_wsaObjArgTypes[n2++] = parameter.javaType();
            }
        }
    }
    
    protected Vector buildResponseParameters() throws Exception {
        final Vector<Parameter> vector = new Vector<Parameter>();
        if (3 != this.m_methodDesc.wsaMethodType() && 2 != this.m_methodDesc.wsaMethodType() && 4 != this.m_methodDesc.wsaMethodType() && 7 != this.m_methodDesc.wsaMethodType()) {
            final int parameterCount = this.m_methodDesc.parameterCount();
            if (0 < parameterCount) {
                for (int i = 0; i < parameterCount; ++i) {
                    final MethodParam parameter = this.m_methodDesc.parameter(i);
                    if (1 != parameter.inOutType()) {
                        vector.add(new Parameter(parameter.name().getLocalPart(), parameter.javaType(), this.m_wsaObjArgs[i], this.m_respEncStyle));
                    }
                }
            }
        }
        else {
            final int parameterCount2 = this.m_methodDesc.parameterCount();
            if (0 < parameterCount2) {
                for (int j = 0; j < parameterCount2; ++j) {
                    final MethodParam parameter2 = this.m_methodDesc.parameter(j);
                    if (1 != parameter2.inOutType()) {
                        try {
                            Object o = this.m_procParams.getOutputParameter(j + 1);
                            Class clazz = parameter2.javaType();
                            if (GregorianCalendar.class == clazz || parameter2.isExtent()) {
                                final ParamHolderDetail paramHolderDetail = new ParamHolderDetail(parameter2, o);
                                if (paramHolderDetail.hasHolder()) {
                                    o = paramHolderDetail.getHolder();
                                }
                                clazz = paramHolderDetail.getType();
                            }
                            if (ResultSet.class == clazz) {
                                String s = null;
                                switch (this.m_methodDesc.wsaMethodType()) {
                                    case 2: {
                                        s = this.m_methodName.substring(this.m_methodName.indexOf("_") + 1);
                                        break;
                                    }
                                    case 4:
                                    case 7: {
                                        s = null;
                                        break;
                                    }
                                    case 3: {
                                        s = this.m_methodName;
                                        break;
                                    }
                                    default: {
                                        s = this.m_methodName;
                                        break;
                                    }
                                }
                                o = new XmrResultSetHolder((ProResultSet)o, s, this.m_targetObjectURI, 15 == parameter2.pscType());
                                clazz = XmrResultSetHolder.class;
                            }
                            else if (null == o && null == this.m_respEncStyle) {
                                o = new WsaNil();
                                clazz = WsaNil.class;
                            }
                            vector.add(new Parameter(parameter2.name().getLocalPart(), clazz, o, this.m_respEncStyle));
                        }
                        catch (Exception ex) {
                            final Object[] array = { parameter2.name().toString(), this.m_methodDesc.name().toString(), ex.getMessage() };
                            if (this.m_log.ifLogVerbose(4L, 2)) {
                                this.m_log.logVerbose(2, 8607504787811871418L, array);
                            }
                            this.m_dd.runtimeStats().incrementCounter(5);
                            throw new WsaSOAPException("SOAP-ENV:Server.Provider", 8607504787811871418L, array, ex);
                        }
                    }
                }
            }
        }
        return vector;
    }
    
    protected String resolveFaultCode(final Exception ex) {
        String s;
        if (ex instanceof ConnectException) {
            s = "SOAP-ENV:Server.Connection";
        }
        else if (ex instanceof RunTime4GLException) {
            s = "SOAP-ENV:Server.Application";
        }
        else if (ex instanceof BrokerException) {
            s = "SOAP-ENV:Server.Application-Server";
        }
        else if (ex instanceof ClientException) {
            s = "SOAP-ENV:Server.Application-Server";
        }
        else if (ex instanceof Open4GLException) {
            s = "SOAP-ENV:ServerNetwork";
        }
        else {
            s = "SOAP-ENV:ServerNetwork";
        }
        return s;
    }
    
    protected String resolveFaultString(final Exception ex) {
        final Object[] array = new Object[0];
        long n;
        if (ex instanceof ConnectException) {
            n = 8607504787811871381L;
        }
        else if (ex instanceof RunTime4GLException) {
            n = 8607504787811871382L;
        }
        else if (ex instanceof BrokerException) {
            n = 8607504787811871379L;
        }
        else if (ex instanceof ClientException) {
            n = 8607504787811871380L;
        }
        else if (ex instanceof Open4GLException) {
            n = 8607504787811871379L;
        }
        else {
            n = 8607504787811871377L;
        }
        return AppLogger.formatMessage(n, array);
    }
    
    protected void countOpenClientException(final Exception ex) {
        int n;
        if (ex instanceof ConnectException) {
            n = 6;
        }
        else if (ex instanceof RunTime4GLException) {
            n = 9;
        }
        else if (ex instanceof BrokerException) {
            n = 8;
        }
        else if (ex instanceof ClientException) {
            n = 7;
        }
        else if (ex instanceof Open4GLException) {
            n = 7;
        }
        else {
            n = 5;
        }
        this.m_dd.runtimeStats().incrementCounter(n);
    }
    
    protected void setProcInternalFunction(final ParameterSet set) {
        if (this.m_methodDesc.funcReturnsExtent()) {
            set.setIsFuncReturnExtent(true);
            set.setIsReturnUnknown(true);
        }
        switch (this.m_methodDesc.proReturnType()) {
            case 1: {
                set.setStringFunction();
                break;
            }
            case 3: {
                set.setBooleanFunction();
                break;
            }
            case 4: {
                set.setIntegerFunction();
                break;
            }
            case 5: {
                set.setDecimalFunction();
                break;
            }
            case 2: {
                set.setDateFunction();
                break;
            }
            case 34: {
                set.setDateTimeFunction();
                break;
            }
            case 40: {
                set.setDateTimeTzFunction();
                break;
            }
            case 7: {
                set.setLongFunction();
                break;
            }
            case 8: {
                set.setByteFunction();
                break;
            }
            case 14: {
                set.setCOMHandleFunction();
                break;
            }
            case 10: {
                set.setHandleFunction();
                break;
            }
            case 13: {
                set.setRowidFunction();
                break;
            }
            case 41: {
                set.setInt64Function();
                break;
            }
        }
    }
    
    private int getCurrentEncoding() {
        return this.m_dd.defaultEncodingType();
    }
    
    private void releaseProviderReferences() {
        this.m_dd = null;
        this.m_envelope = null;
        this.m_call = null;
        this.m_targetObjectURI = null;
        this.m_methodName = null;
        this.m_reqContext = null;
        this.m_methodQName = null;
        this.m_execMethodName = null;
        this.m_soapHeader = null;
        this.m_poolManager = null;
        this.m_respHeader = null;
        this.m_respEncStyle = null;
        this.m_methodDesc = null;
        this.m_appObjectUUID = null;
        this.m_appObject = null;
        this.m_connectionURL = null;
        this.m_wsaObjArgs = null;
        this.m_wsaObjArgTypes = null;
        this.m_resultSetSchema = null;
        this.m_procParams = null;
        this.m_log = null;
        this.m_reason = null;
        this.m_referenceID = null;
        this.m_stateFreeRequestContext = null;
    }
    
    private String getParamLocal(final String s) {
        int lastIndex = s.lastIndexOf(":");
        if (lastIndex < 0) {
            return s;
        }
        ++lastIndex;
        return s.substring(lastIndex);
    }
    
    private Element getArrayElemFromBody(final String s) {
        for (final Element next : this.m_envelope.getBody().getBodyEntries()) {
            if (next instanceof Element) {
                for (Node node = next.getFirstChild(); node != null; node = node.getNextSibling()) {
                    if (node.getNodeType() == 1 && ((Element)node).getTagName() == s) {
                        return (Element)node;
                    }
                }
            }
        }
        return null;
    }
    
    private boolean connectReturnsValue() {
        return this.m_methodDesc.hasConnectReturnString();
    }
    
    static {
        CONNECT_ARG_CLASSES = new Class[] { String.class, String.class, String.class, String.class, String.class };
        RELEASE_ARG_CLASSES = new Class[0];
        RUN_PROC_ARG_CLASSES = new Class[] { String.class, String.class, ParameterSet.class, ResultSetSchema.class };
        CREATE_PROC_ARG_CLASSES = new Class[] { String.class, String.class, String.class, ParameterSet.class, ResultSetSchema.class };
        CREATE_SUB_ARG_CLASSES = new Class[] { String.class };
        TEST_APPLICATION_ARG_CLASSES = new Class[] { String.class, String.class };
    }
}
