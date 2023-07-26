// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xqimpl.endpoint.container.EndpointManager;
import com.sonicsw.esb.message.rme.RMEMessageFactory;
import com.sonicsw.xq.XQPart;
import java.util.HashMap;
import com.sonicsw.xqimpl.script.Command;
import com.sonicsw.xq.XQMessage;
import com.sonicsw.xq.XQServiceContext;
import com.sonicsw.xq.XQServiceException;
import java.util.Enumeration;
import java.util.Hashtable;
import com.sonicsw.xq.XQParameters;
import com.sonicsw.mf.common.IComponentContext;
import org.w3c.dom.Node;
import java.io.IOException;
import com.progress.common.ehnlog.LogHandler;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import com.sonicsw.xq.XQInitContext;
import com.sonicsw.xq.XQLog;
import com.progress.common.ehnlog.AppLogger;
import com.progress.esb.tools.EsbRuntimeProperties;
import org.w3c.dom.Element;
import com.sonicsw.xq.XQServiceEx;

public class EsbAdapter2 implements XQServiceEx
{
    public static final String FAULT_RETURN_MODE = "faultReturnMode";
    public static final String[] FAULT_RETURN_MODE_STR;
    public static final int FAULT_ONLY = 0;
    public static final int FAULT_AND_ORIGINAL = 1;
    public static final int REJECTED_AND_ORIGINAL = 2;
    public static final int CONTINUE_PROCESSS = 3;
    public static final String SM_HEADER_NAME = "connIdHeader";
    public static final int STATE_FREE = 1;
    protected Element m_esboeCommand;
    protected int m_operatingMode;
    protected String m_appServiceURL;
    protected EsbRuntimeProperties m_props;
    protected OpenClient m_client;
    protected OEInvocationScriptEngine m_oeSE;
    protected String m_sessionManagedHdr;
    protected String m_installDir;
    protected AppLogger m_log;
    protected OEEsbInterceptor m_oeInterceptor;
    private XQLog xqlog;
    
    public EsbAdapter2() {
        this.m_esboeCommand = null;
        this.m_operatingMode = 0;
        this.m_appServiceURL = null;
        this.m_props = null;
        this.m_client = null;
        this.m_oeSE = null;
        this.m_sessionManagedHdr = null;
        this.m_installDir = null;
        this.m_log = null;
        this.m_oeInterceptor = null;
        this.xqlog = null;
    }
    
    public void init(final XQInitContext xqInitContext) throws XQServiceException {
        this.xqlog = xqInitContext.getLog();
        final EsbLogger esbLogger = new EsbLogger(this.xqlog);
        ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
        final XQParameters parameters = xqInitContext.getParameters();
        final String parameter = parameters.getParameter("SonicXQ.ServiceName", 1);
        try {
            this.m_log = new AppLogger(esbLogger, 2, 3L, parameter, "Esb");
        }
        catch (IOException ex) {
            this.xqlog.logError("Unable to create AppLogger: " + ex.toString());
        }
        if (this.m_log.ifLogBasic(1L, 0)) {
            this.m_log.logBasic(0, 8607504787811874868L, null);
        }
        final Node node = (Node)parameters.getParameterObject("runtimeProperties", 2);
        if (node == null) {
            this.m_props = new EsbRuntimeProperties();
        }
        else {
            (this.m_props = new EsbRuntimeProperties()).readXML(node);
        }
        this.m_log.setLoggingLevel(this.m_props.getIntProperty("PROGRESS.Session.serviceLoggingLevel"));
        this.m_log.setLogEntries(this.m_props.getStringProperty("PROGRESS.Session.serviceLoggingEntryTypes"));
        this.m_oeInterceptor = new OEEsbInterceptor(parameter);
        final String stringProperty = this.m_props.getStringProperty("PROGRESS.Session.actionalGroupName");
        if (null != stringProperty) {
            this.m_oeInterceptor.setGroup(stringProperty);
        }
        final String property = System.getProperty("com.progress.openedge.home");
        if (property != null) {
            this.m_installDir = property;
        }
        else {
            this.m_installDir = System.getProperty("com.sonicsw.xq.home");
        }
        String str = System.getProperty("PROGRESS.Session.certificateStore");
        if (str == null) {
            str = this.m_installDir + "/certs";
        }
        this.m_props.setStringProperty("PROGRESS.Session.certificateStore", str);
        if (this.m_log.ifLogExtended(1L, 0)) {
            try {
                final Hashtable esbProperties = this.m_props.getESBProperties();
                final Enumeration<Object> keys = esbProperties.keys();
                this.m_log.logExtended(0, "ESB Adapter runtime properties...");
                while (keys.hasMoreElements()) {
                    final Object nextElement = keys.nextElement();
                    this.m_log.logExtended(0, "   " + nextElement.toString() + ": " + esbProperties.get(nextElement).toString());
                }
                this.m_log.logExtended(0, "   certStorePath: " + str);
            }
            catch (Exception ex2) {}
        }
        this.m_operatingMode = parameters.getIntParameter("operatingMode", 1);
        Object o = null;
        switch (this.m_operatingMode) {
            case 0: {
                o = "State-aware";
                break;
            }
            case 1: {
                o = "State-free";
                break;
            }
            case 2: {
                o = "State-reset";
                break;
            }
            case 3: {
                o = "Stateless";
                break;
            }
        }
        if (this.m_operatingMode != 1) {
            this.m_operatingMode = 0;
        }
        this.m_props.setIntProperty("PROGRESS.Session.sessionModel", this.m_operatingMode);
        if (this.m_log.ifLogVerbose(1L, 0)) {
            this.m_log.logVerbose(0, 8607504787811874869L, new Object[] { o });
        }
        this.m_appServiceURL = parameters.getParameter("appServiceURL", 1);
        this.m_oeInterceptor.setUrl(this.m_appServiceURL);
        if (this.m_log.ifLogVerbose(1L, 0)) {
            this.m_log.logVerbose(0, 8607504787811874870L, new Object[] { this.m_appServiceURL });
        }
        if (this.m_operatingMode != 1) {
            this.m_sessionManagedHdr = this.createSMHeader(this.m_appServiceURL);
            if (this.m_log.ifLogVerbose(1L, 0)) {
                this.m_log.logVerbose(0, 8607504787811874871L, new Object[] { this.m_sessionManagedHdr });
            }
        }
        (this.m_client = new OpenClient(this.m_operatingMode, this.m_appServiceURL, this.m_props, this.m_sessionManagedHdr, this.m_log)).setInterceptor(this.m_oeInterceptor);
        this.m_oeSE = new OEInvocationScriptEngine(null, this.m_esboeCommand, this.m_client);
        if (this.m_log.ifLogBasic(1L, 0)) {
            this.m_log.logBasic(0, 8607504787811874911L, null);
        }
    }
    
    public void destroy() {
    }
    
    public void service(final XQServiceContext xqServiceContext) throws XQServiceException {
        int int1 = 0;
        boolean instrumented = false;
        String sessionManagedHdr = this.m_sessionManagedHdr;
        String stringHeader = null;
        if (xqServiceContext == null) {
            throw new XQServiceException(AppLogger.formatMessage(8607504787811871622L, null));
        }
        if (this.m_log.ifLogBasic(1L, 0)) {
            this.m_log.logBasic(0, "Received incoming message...");
        }
        final XQMessage message = xqServiceContext.getFirstIncoming().getMessage();
        final XQParameters parameters = xqServiceContext.getParameters();
        try {
            if (this.m_log.ifLogVerbose(1L, 0)) {
                this.m_log.logVerbose(0, "Performing OpenEdge invocation...");
            }
            final Command command = this.m_oeSE.createCommand(message, parameters);
            final HashMap parameterConstants = command.getParameterConstants();
            final String value = parameterConstants.get("faultReturnMode");
            if (null != value) {
                int1 = Integer.parseInt(value);
                if (this.m_log.ifLogExtended(1L, 0)) {
                    this.m_log.logExtended(0, "Fault Return Mode is '" + EsbAdapter2.FAULT_RETURN_MODE_STR[int1] + "'");
                }
            }
            final String value2 = parameterConstants.get("connIdHeader");
            if (null != value2) {
                sessionManagedHdr = value2;
            }
            if (instrumented = this.m_oeInterceptor.isInstrumented()) {
                this.m_oeInterceptor.beginInteraction(parameterConstants);
                if (this.m_log.ifLogExtended(1L, 0)) {
                    this.m_log.logExtended(0, "Actional monitoring enabled");
                }
            }
            else if (this.m_log.ifLogExtended(1L, 0)) {
                this.m_log.logExtended(0, "Actional monitoring disabled");
            }
            if (this.m_operatingMode == 0) {
                stringHeader = message.getStringHeader(sessionManagedHdr);
            }
            command.execute();
            xqServiceContext.addOutgoing(command.getXQMessage());
            if (instrumented) {
                this.m_oeInterceptor.endInteraction(null);
            }
        }
        catch (OEFaultException ex) {
            if (instrumented) {
                this.m_oeInterceptor.endInteraction(ex.getAsXML(xqServiceContext));
            }
            if (int1 == 1) {
                try {
                    final XQPart part = message.createPart((Object)ex.getAsXML(xqServiceContext), "text/xml");
                    part.setContentId("SonicESB.Fault");
                    message.addPartAt(part, 0);
                    xqServiceContext.addFault(message);
                    return;
                }
                catch (Exception ex2) {
                    throw new XQServiceException((Throwable)ex2);
                }
            }
            if (int1 == 0) {
                final XQMessage message2 = xqServiceContext.getMessageFactory().createMessage();
                try {
                    if (this.m_operatingMode == 0) {
                        message2.setStringHeader(sessionManagedHdr, stringHeader);
                    }
                    final XQPart part2 = message.createPart((Object)ex.getAsXML(xqServiceContext), "text/xml");
                    part2.setContentId("SonicESB.Fault");
                    message2.addPartAt(part2, 0);
                    xqServiceContext.addFault(message2);
                }
                catch (Exception ex3) {
                    throw new XQServiceException((Throwable)ex3);
                }
            }
            else {
                if (int1 == 2) {
                    try {
                        this.sendMessageToRME(xqServiceContext, ex.getAsText(xqServiceContext), message, "OPENEDGE_INVOCATION_ERROR");
                        return;
                    }
                    catch (Exception ex4) {
                        throw new XQServiceException((Throwable)ex4);
                    }
                }
                final XQMessage message3 = xqServiceContext.getMessageFactory().createMessage();
                try {
                    if (this.m_operatingMode == 0) {
                        message3.setStringHeader(sessionManagedHdr, stringHeader);
                    }
                    final XQPart part3 = message.createPart((Object)ex.getAsXML(xqServiceContext), "text/xml");
                    part3.setContentId("SonicESB.Fault");
                    message3.addPartAt(part3, 0);
                    xqServiceContext.addOutgoing(message3);
                }
                catch (Exception ex5) {
                    throw new XQServiceException((Throwable)ex5);
                }
            }
        }
        catch (Exception ex6) {
            this.xqlog.logError((Throwable)ex6);
            throw new XQServiceException((Throwable)ex6);
        }
    }
    
    public void start() {
    }
    
    public void stop() {
    }
    
    private String createSMHeader(final String s) {
        final StringBuffer sb = new StringBuffer();
        int n = 0;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if ((char1 >= '0' && char1 <= '9') || (char1 >= 'A' && char1 <= 'Z') || (char1 >= 'a' && char1 <= 'z')) {
                sb.append(char1);
                n = 0;
            }
            else if (n == 0) {
                sb.append('.');
                n = 1;
            }
        }
        return sb.toString();
    }
    
    private boolean sendMessageToRME(final XQServiceContext xqServiceContext, final String s, final XQMessage xqMessage, final String str) {
        boolean b = false;
        try {
            RMEMessageFactory.createInstance(xqServiceContext, s, xqMessage, str).send("OpenEdge Adapter", xqServiceContext.getParameters(), xqServiceContext.getQoS(), EndpointManager.getInvocationEndpointRegistry());
            b = true;
        }
        catch (Throwable t) {
            if (this.m_log.ifLogBasic(1L, 0)) {
                this.m_log.logBasic(0, "Message Rejected, rejected message could not be created.");
                this.m_log.logBasic(0, "Cause of rejected message: " + str);
                this.m_log.logBasic(0, s);
            }
        }
        return b;
    }
    
    static {
        FAULT_RETURN_MODE_STR = new String[] { "Fault Only", "Fault & Original Message", "Rejected Message & Original Message", "Continue Process with Fault" };
    }
}
