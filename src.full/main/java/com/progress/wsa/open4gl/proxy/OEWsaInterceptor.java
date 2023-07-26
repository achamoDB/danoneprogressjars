// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl.proxy;

import com.actional.lg.interceptor.sdk.FlowStub;
import com.actional.lg.interceptor.sdk.Interaction;
import com.actional.lg.interceptor.sdk.SiteStub;
import com.actional.lg.interceptor.sdk.InteractionStub;
import com.actional.lg.interceptor.sdk.ServerInteraction;
import com.progress.common.util.PscURLParser;
import java.util.HashMap;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import com.actional.lg.interceptor.sdk.ClientInteraction;
import java.io.Serializable;
import com.progress.open4gl.javaproxy.IActionalInterceptor;

public class OEWsaInterceptor implements IActionalInterceptor, Serializable
{
    protected String m_lgGroup;
    protected String m_lgService;
    protected String m_lgOperation;
    protected String m_lgURL;
    protected String m_lgPeer;
    protected boolean m_isAnalized;
    protected static final long serialVersionUID = -966498172848195846L;
    public static final String INTERCEPTOR_TYPE = "OpenEdge WSA Interceptor";
    public static final String INTERCEPTOR_GROUP = "WSA";
    
    public OEWsaInterceptor() {
        this.m_lgGroup = null;
        this.m_lgService = null;
        this.m_lgOperation = null;
        this.m_lgURL = null;
        this.m_lgPeer = null;
        this.m_isAnalized = false;
        this.m_lgGroup = "WSA";
    }
    
    public OEWsaInterceptor(final String lgService) {
        this.m_lgGroup = null;
        this.m_lgService = null;
        this.m_lgOperation = null;
        this.m_lgURL = null;
        this.m_lgPeer = null;
        this.m_isAnalized = false;
        this.m_lgGroup = "WSA";
        this.m_lgService = lgService;
    }
    
    public String analyze(final String opName) {
        final ClientInteraction value = ClientInteraction.get();
        if (null != opName) {
            ((InteractionStub)value).setOpName(opName);
        }
        String writeHeader;
        try {
            writeHeader = InterHelpBase.writeHeader(value);
        }
        catch (Exception ex) {
            writeHeader = null;
        }
        value.requestAnalyzed();
        this.m_isAnalized = true;
        return writeHeader;
    }
    
    public void beginInteraction(final HashMap hashMap) {
        final ClientInteraction begin = ClientInteraction.begin();
        if (null != hashMap) {
            final String value = hashMap.get("LG_URL");
            if (null != value) {
                this.m_lgURL = value;
            }
            final String value2 = hashMap.get("LG_Group");
            if (null != value2) {
                this.m_lgGroup = value2;
            }
            final String value3 = hashMap.get("LG_Service");
            if (null != value3) {
                this.m_lgService = value3;
            }
            final String value4 = hashMap.get("LG_Operation");
            if (null != value4) {
                this.m_lgOperation = value4;
            }
        }
        ((InteractionStub)begin).setUrl(this.m_lgURL);
        ((InteractionStub)begin).setGroupName(this.m_lgGroup);
        ((SiteStub)begin).setAppType((short)681);
        ((InteractionStub)begin).setServiceName(this.m_lgService);
        ((SiteStub)begin).setSvcType((short)686);
        ((InteractionStub)begin).setPeerAddr(this.m_lgPeer);
        ((InteractionStub)begin).setOpName(this.m_lgOperation);
        this.m_isAnalized = false;
    }
    
    public void endInteraction(final String failure) {
        final ClientInteraction value = ClientInteraction.get();
        if (null != failure) {
            ((InteractionStub)value).setFailure(failure);
        }
        ((Interaction)value).end();
        this.m_isAnalized = false;
    }
    
    public String getGroup() {
        return this.m_lgGroup;
    }
    
    public String getHeader() {
        String writeHeader = null;
        if (this.m_isAnalized) {
            final ClientInteraction value = ClientInteraction.get();
            try {
                writeHeader = InterHelpBase.writeHeader(value);
            }
            catch (Exception ex) {
                writeHeader = null;
            }
            this.m_isAnalized = false;
        }
        return writeHeader;
    }
    
    public String getOperation() {
        return this.m_lgOperation;
    }
    
    public String getPeer() {
        return this.m_lgPeer;
    }
    
    public String getService() {
        return this.m_lgService;
    }
    
    public String getUrl() {
        return this.m_lgURL;
    }
    
    public void setGroup(final String lgGroup) {
        this.m_lgGroup = lgGroup;
    }
    
    public void setOperation(final String lgOperation) {
        this.m_lgOperation = lgOperation;
    }
    
    public void setPeer(final String lgPeer) {
        this.m_lgPeer = lgPeer;
    }
    
    public void setService(final String lgService) {
        this.m_lgService = lgService;
    }
    
    public void setUrl(final String lgURL) {
        this.m_lgURL = lgURL;
        try {
            this.m_lgPeer = new PscURLParser(this.m_lgURL).getHost();
        }
        catch (Exception ex) {
            this.m_lgPeer = "localhost";
        }
    }
    
    public boolean isInstrumented() {
        return ServerInteraction.get() != null;
    }
    
    public String toString() {
        String str = "";
        String interactionID = "";
        final ClientInteraction value = ClientInteraction.get();
        if (value != null) {
            str = "CI";
            interactionID = ((InteractionStub)value).getInteractionID();
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("OpenEdge WSA Interceptor").append(" ").append(str).append("(").append(interactionID).append(") on thread: ").append(Thread.currentThread().getName());
        if (value != null) {
            sb.append(" ==>");
            sb.append(" PeerAddr: [ " + ((SiteStub)value).getPeerAddr());
            sb.append(" ] URL: [ " + ((SiteStub)value).getUrl());
            sb.append(" ] Type: [ " + ((SiteStub)value).getSvcType());
            sb.append(" ] Flow ID: [ " + ((FlowStub)value).getFlowID());
            sb.append(" ] Op ID: [ " + ((InteractionStub)value).getOpID());
            sb.append(" ] Locus ID: [ " + ((InteractionStub)value).getParentID());
            sb.append(" ] Chain ID: [ " + ((FlowStub)value).getChainID());
            sb.append(" ] OneWay: [ " + ((InteractionStub)value).getOneWay());
            sb.append(" ] isFault: [ " + (((InteractionStub)value).getFailure() != null));
            sb.append(" ] G: " + ((SiteStub)value).getGroupName());
            sb.append(" S: " + ((SiteStub)value).getServiceName());
            sb.append(" O: " + ((SiteStub)value).getOpName());
        }
        return sb.toString();
    }
}
