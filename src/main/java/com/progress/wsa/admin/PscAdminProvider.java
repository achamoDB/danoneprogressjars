// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import org.apache.soap.rpc.RPCMessage;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import java.io.Writer;
import java.io.StringWriter;
import com.progress.wsa.WsaSOAPException;
import org.apache.soap.SOAPException;
import org.apache.soap.server.RPCRouter;
import org.apache.soap.Constants;
import com.progress.wsa.WsaSOAPEngine;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.Envelope;
import org.apache.soap.rpc.Call;
import org.apache.soap.server.DeploymentDescriptor;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.wsa.WsaConstants;
import org.apache.soap.util.Provider;

public class PscAdminProvider implements Provider, WsaConstants
{
    protected IAppLogger m_log;
    protected DeploymentDescriptor m_dd;
    protected Call m_call;
    protected Object m_target;
    protected String m_reason;
    
    public PscAdminProvider() {
        this.m_reason = "";
    }
    
    public void locate(final DeploymentDescriptor dd, final Envelope envelope, final Call call, final String s, final String s2, final SOAPContext soapContext) throws SOAPException {
        try {
            this.m_dd = dd;
            this.m_call = call;
            final WsaSOAPEngine wsaSOAPEngine = (WsaSOAPEngine)soapContext.getProperty(Constants.BAG_HTTPSERVLET);
            this.m_log = wsaSOAPEngine.getLogger();
            if (!RPCRouter.validCall(dd, call)) {
                throw new SOAPException(Constants.FAULT_CODE_SERVER, "Method '" + ((RPCMessage)call).getMethodName() + "' is not supported.");
            }
            this.m_target = wsaSOAPEngine.getServiceManager();
        }
        catch (Exception ex) {
            if (ex instanceof WsaSOAPException) {
                throw (SOAPException)ex;
            }
            final Object[] array = { ex.getMessage() };
            if (this.m_log.ifLogVerbose(8L, 3)) {
                this.m_log.logVerbose(3, 8607504787811871405L, array);
            }
            if (this.m_log.ifLogExtended(8L, 3)) {
                this.m_log.logStackTrace(3, 8607504787811871405L, array, ex);
            }
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871405L, array, ex);
        }
    }
    
    public void invoke(final SOAPContext soapContext, final SOAPContext soapContext2) throws SOAPException {
        try {
            final Envelope buildEnvelope = RPCRouter.invoke(this.m_dd, this.m_call, this.m_target, soapContext, soapContext2).buildEnvelope();
            final StringWriter stringWriter = new StringWriter();
            buildEnvelope.marshall((Writer)stringWriter, (XMLJavaMappingRegistry)this.m_call.getSOAPMappingRegistry(), soapContext2);
            soapContext2.setRootPart(stringWriter.toString(), "text/xml;charset=utf-8");
        }
        catch (Exception ex) {
            if (ex instanceof SOAPException) {
                throw (SOAPException)ex;
            }
            final Object[] array = { ex.getMessage() };
            if (this.m_log.ifLogVerbose(8L, 3)) {
                this.m_log.logVerbose(3, 8607504787811871406L, array);
            }
            if (this.m_log.ifLogExtended(8L, 3)) {
                this.m_log.logStackTrace(3, 8607504787811871406L, array, ex);
            }
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871406L, array, ex);
        }
    }
}
