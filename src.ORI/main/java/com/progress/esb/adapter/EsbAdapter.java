// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.sonicsw.xq.XQProcessContext;
import com.sonicsw.xq.XQEnvelopeFactory;
import com.progress.wsa.WsaSOAPResponse;
import com.sonicsw.xq.XQPart;
import com.sonicsw.xq.XQEnvelope;
import com.sonicsw.xq.XQMessage;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import com.sonicsw.xq.XQMessageException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import com.sonicsw.xq.XQServiceContext;
import com.sonicsw.xq.XQParameters;
import com.sonicsw.xq.XQLog;
import com.progress.wsa.WsaSOAPException;
import com.progress.wsa.WsaSOAPEngineException;
import com.sonicsw.xq.XQServiceException;
import java.util.Map;
import com.progress.common.ehnlog.LogHandler;
import java.util.HashMap;
import com.progress.wsa.WsaState;
import com.progress.wsa.WsaSOAPEngineFactory;
import com.sonicsw.xq.XQAddress;
import java.util.Hashtable;
import com.sonicsw.xq.XQInitContext;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.wsa.admin.PscConfigManager2;
import com.progress.wsa.admin.PscServiceManager;
import com.progress.wsa.WsaSOAPEngine;
import com.sonicsw.xq.XQService;

public class EsbAdapter implements XQService
{
    private static final String OLD_XSD_NS = "http://www.w3.org/1999/XMLSchema";
    private static final String OLD_XSI_NS = "http://www.w3.org/1999/XMLSchema-instance";
    private static final String NEW_XSD_NS = "http://www.w3.org/2001/XMLSchema";
    private static final String NEW_XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    protected WsaSOAPEngine m_engine;
    protected PscServiceManager m_serviceMgr;
    protected PscConfigManager2 m_configMgr;
    protected String m_installDir;
    protected String m_instanceName;
    protected IAppLogger m_log;
    protected String m_serviceName;
    protected boolean m_faultEndpoint;
    protected int m_faultProcessing;
    
    public EsbAdapter() {
        this.m_engine = null;
        this.m_serviceMgr = null;
        this.m_configMgr = null;
        this.m_installDir = null;
        this.m_instanceName = " ";
        this.m_log = null;
        this.m_serviceName = null;
        this.m_faultEndpoint = false;
        this.m_faultProcessing = 0;
    }
    
    public void init(final XQInitContext xqInitContext) throws XQServiceException {
        int int1 = 0;
        int int2 = 0;
        final Hashtable<String, Boolean> hashtable = new Hashtable<String, Boolean>();
        final XQLog log = xqInitContext.getLog();
        final XQParameters parameters = xqInitContext.getParameters();
        final String parameter = parameters.getParameter("WSM", 1, (String)null);
        this.m_serviceName = parameters.getParameter("SonicXQ.ServiceName", 1, (String)null);
        final String parameter2 = parameters.getParameter("webServiceNamespace", 1, (String)null);
        final String parameter3 = parameters.getParameter("appServiceProtocol", 1, (String)null);
        final String parameter4 = parameters.getParameter("appServiceHost", 1, (String)null);
        final String parameter5 = parameters.getParameter("appServiceName", 1, (String)null);
        try {
            this.m_faultProcessing = Integer.parseInt(parameters.getParameter("faultProcessing", 1, (String)null));
        }
        catch (NumberFormatException ex3) {
            this.m_faultProcessing = 0;
        }
        int int3;
        try {
            int3 = Integer.parseInt(parameters.getParameter("appServicePort", 1, (String)null));
        }
        catch (NumberFormatException ex4) {
            int3 = -1;
        }
        try {
            final String parameter6 = parameters.getParameter("noHostVerify", 1, (String)null);
            if (parameter6 != null) {
                int1 = Integer.parseInt(parameter6);
            }
            final String parameter7 = parameters.getParameter("noSessionReuse", 1, (String)null);
            if (parameter7 != null) {
                int2 = Integer.parseInt(parameter7);
            }
            if (int1 > 0) {
                hashtable.put("PROGRESS.Session.noHostVerify", (int1 == 1) ? new Boolean(true) : new Boolean(false));
            }
            if (int2 > 0) {
                hashtable.put("PROGRESS.Session.noSslSessionReuse", (int2 == 1) ? new Boolean(true) : new Boolean(false));
            }
        }
        catch (NumberFormatException ex5) {
            hashtable.put("PROGRESS.Session.noHostVerify", new Boolean(false));
            hashtable.put("PROGRESS.Session.noSslSessionReuse", new Boolean(false));
        }
        if (parameters.getParameterObject("SonicXQ.FaultEndpointAddress", 3) != null) {
            this.m_faultEndpoint = true;
        }
        if (parameter3 != null && parameter3.length() > 0) {
            hashtable.put("PROGRESS.Session.appServiceProtocol", (Boolean)parameter3);
        }
        if (parameter4 != null && parameter4.length() > 0) {
            hashtable.put("PROGRESS.Session.appServiceHost", (Boolean)parameter4);
        }
        if (parameter5 != null && parameter5.length() > 0) {
            hashtable.put("PROGRESS.Session.appServiceName", (Boolean)parameter5);
        }
        if (int3 > 0) {
            hashtable.put("PROGRESS.Session.appServicePort", (Boolean)(Object)new Integer(int3));
        }
        final String property = System.getProperty("com.progress.openedge.home");
        if (property != null) {
            this.m_installDir = property;
        }
        else {
            this.m_installDir = System.getProperty("com.sonicsw.xq.home");
        }
        final String property2 = System.getProperty("com.progress.openedge.logLevel");
        final String string = this.m_installDir + "/esbadapter";
        String value = System.getProperty("PROGRESS.Session.certificateStore");
        if (value == null) {
            value = this.m_installDir + "/certs";
        }
        try {
            this.m_engine = WsaSOAPEngineFactory.getInstance("Container");
            if (WsaState.UNINITIALIZED == this.m_engine.getState()) {
                final HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("instanceName", this.m_instanceName);
                hashMap.put("InstallDir", this.m_installDir);
                hashMap.put("deploymentDir", string);
                hashMap.put("certStorePath", value);
                if (property2 != null && this.validInt(property2)) {
                    hashMap.put("loggingLevel", property2);
                }
                this.m_engine.setLogger(new EsbLogger(log));
                this.m_engine.initialize(hashMap);
            }
        }
        catch (WsaSOAPEngineException ex) {
            log.logError("Unable to initialize OpenEdge Sonic ESB Adapter Engine: " + ex.getMessage());
            throw new XQServiceException((Throwable)ex);
        }
        this.m_log = this.m_engine.getLogger();
        if (null == property2) {
            this.m_log.setLoggingLevel(2);
        }
        else {
            try {
                this.m_log.setLoggingLevel(Integer.parseInt(property2));
                this.m_log.logBasic(0, "Logging level set to " + property2);
            }
            catch (NumberFormatException ex6) {
                this.m_log.logError("Invalid logging level set - " + property2);
                this.m_log.setLoggingLevel(2);
            }
        }
        this.m_serviceMgr = (PscServiceManager)this.m_engine.getServiceManager();
        this.m_configMgr = this.m_serviceMgr.configManager();
        try {
            this.m_configMgr.installApp(this.m_serviceName, parameter2, parameter, hashtable);
        }
        catch (WsaSOAPException ex2) {
            throw new XQServiceException((Throwable)ex2);
        }
        if (!this.m_faultEndpoint && this.m_faultProcessing > 0) {
            log.logWarning("SOAP Fault processing specified, but no Fault Endpoint is set");
        }
    }
    
    public void destroy() {
        if (this.m_engine != null) {
            this.m_log.logBasic(0, "Shuting down OpenEdge Service: " + this.m_serviceName);
            try {
                this.m_engine.shutdown();
            }
            catch (WsaSOAPEngineException ex) {
                this.m_log.logBasic(0, ex.getMessage());
            }
        }
    }
    
    public void service(final XQServiceContext xqServiceContext) throws XQServiceException {
        XQMessage message = null;
        int int1 = 0;
        int int2 = 0;
        if (xqServiceContext == null) {
            throw new XQServiceException("ESB Service Context is null");
        }
        final XQParameters parameters = xqServiceContext.getParameters();
        final String parameter = parameters.getParameter("InputMessagePart", 1, (String)null);
        if (parameter != null) {
            try {
                int1 = Integer.parseInt(parameter);
                this.m_log.logError("Using input part " + parameter);
            }
            catch (NumberFormatException ex5) {
                int1 = 0;
            }
        }
        final String parameter2 = parameters.getParameter("OutputMessagePart", 1, (String)null);
        if (parameter2 != null) {
            try {
                int2 = Integer.parseInt(parameter2);
                this.m_log.logError("Using output part " + parameter2);
            }
            catch (NumberFormatException ex6) {
                int2 = 0;
            }
        }
        final XQEnvelope nextIncoming = xqServiceContext.getNextIncoming();
        if (nextIncoming == null) {
            throw new XQServiceException("Adapter called with no message");
        }
        XQMessage message2;
        WsaSOAPResponse execute;
        String str;
        try {
            message2 = nextIncoming.getMessage();
            XQPart xqPart;
            if (int1 > 0) {
                if (int1 > message2.getPartCount()) {
                    throw new XQServiceException("Message has incorrect number of parts");
                }
                xqPart = message2.getPart(int1 - 1);
            }
            else {
                xqPart = message2.getPart(0);
            }
            final String s = (String)xqPart.getContent();
            final String validateContentType = this.validateContentType(xqPart.getContentType(), s);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes());
            execute = this.m_engine.createRequest(byteArrayInputStream, byteArrayInputStream.available(), validateContentType).execute();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            execute.write(byteArrayOutputStream);
            str = byteArrayOutputStream.toString();
            boolean b = false;
            final StringBuffer sb = new StringBuffer(str);
            for (int i = str.indexOf("http://www.w3.org/1999/XMLSchema"); i >= 0; i = str.indexOf("http://www.w3.org/1999/XMLSchema", i)) {
                b = true;
                i += 18;
                sb.setCharAt(i++, '2');
                sb.setCharAt(i++, '0');
                sb.setCharAt(i++, '0');
                sb.setCharAt(i++, '1');
            }
            if (b) {
                str = sb.toString();
            }
        }
        catch (XQMessageException ex) {
            this.m_log.logError(8607504787811871625L, null);
            throw new XQServiceException((Throwable)ex);
        }
        catch (WsaSOAPEngineException ex2) {
            this.m_log.logError(8607504787811871626L, null);
            throw new XQServiceException((Throwable)ex2);
        }
        catch (IOException ex3) {
            this.m_log.logError(8607504787811871627L, null);
            throw new XQServiceException((Throwable)ex3);
        }
        catch (LoginException ex7) {
            throw new XQServiceException("Unexpected security exception");
        }
        XQEnvelopeFactory envelopeFactory;
        try {
            envelopeFactory = xqServiceContext.getEnvelopeFactory();
            final XQEnvelope defaultEnvelope = envelopeFactory.createDefaultEnvelope();
            if (int2 > 0) {
                final XQPart part = message2.createPart((Object)str, "text/xml");
                if (int2 == 1) {
                    message2.replacePart(part, 0);
                }
                else {
                    message2.addPartAt(part, int2 - 1);
                }
                defaultEnvelope.setMessage(message2);
            }
            else {
                message = xqServiceContext.getMessageFactory().createMessage();
                message.addPart(message.createPart((Object)str, "text/xml"));
                defaultEnvelope.setMessage(message);
            }
            if (!defaultEnvelope.getAddresses().hasNext()) {
                this.m_log.logBasic(0, 8607504787811871628L, null);
            }
            else {
                xqServiceContext.addOutgoing(defaultEnvelope);
            }
        }
        catch (XQMessageException ex4) {
            this.m_log.logError(8607504787811871629L, null);
            throw new XQServiceException((Throwable)ex4);
        }
        if (this.m_faultProcessing > 0 && execute.isFault()) {
            XQEnvelope xqEnvelope = null;
            boolean b2 = true;
            final XQProcessContext processContext = xqServiceContext.getProcessContext();
            if (processContext != null && processContext.getFaultAddress() == null && !this.m_faultEndpoint) {
                this.m_log.logError(8607504787811871619L, null);
                b2 = false;
            }
            if (b2) {
                if (this.m_faultProcessing == 1) {
                    xqEnvelope = envelopeFactory.createFaultEnvelope(message2);
                }
                else if (this.m_faultProcessing == 2) {
                    xqEnvelope = envelopeFactory.createFaultEnvelope(message);
                }
                if (xqEnvelope != null) {
                    xqServiceContext.addFault(xqEnvelope);
                }
            }
        }
    }
    
    protected String validateContentType(final String str, final String s) {
        if (str.indexOf("charset=") != -1) {
            return str;
        }
        return str + "; charset=" + System.getProperty("file.encoding");
    }
    
    private boolean validInt(final String s) {
        boolean b = false;
        try {
            Integer.parseInt(s);
            b = true;
        }
        catch (NumberFormatException ex) {}
        return b;
    }
}
