// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import com.actional.lg.interceptor.sdk.ServerInteraction;
import com.progress.common.util.PscURLParser;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import com.actional.lg.interceptor.sdk.ClientInteraction;
import java.util.HashMap;
import java.io.Serializable;
import com.progress.open4gl.javaproxy.IActionalInterceptor;

public class OEEsbInterceptor implements IActionalInterceptor, Serializable
{
    protected String m_lgGroup;
    protected String m_lgService;
    protected String m_lgOperation;
    protected String m_lgURL;
    protected String m_lgPeer;
    protected String m_appService;
    protected boolean m_isAnalized;
    protected static final long serialVersionUID = -966498172848195846L;
    public static final String INTERCEPTOR_TYPE = "OpenEdge ESB Interceptor";
    public static final String INTERCEPTOR_GROUP = "EsbAdapter2";
    
    public OEEsbInterceptor() {
        this.m_lgGroup = null;
        this.m_lgService = null;
        this.m_lgOperation = null;
        this.m_lgURL = null;
        this.m_lgPeer = null;
        this.m_appService = null;
        this.m_isAnalized = false;
        this.m_lgGroup = "EsbAdapter2";
    }
    
    public OEEsbInterceptor(final String lgService) {
        this.m_lgGroup = null;
        this.m_lgService = null;
        this.m_lgOperation = null;
        this.m_lgURL = null;
        this.m_lgPeer = null;
        this.m_appService = null;
        this.m_isAnalized = false;
        this.m_lgService = lgService;
    }
    
    public void beginInteraction(final HashMap hashMap) {
        final ClientInteraction begin = ClientInteraction.begin();
        final String value = hashMap.get("LG_URL");
        if (null != value) {
            this.m_lgURL = value;
        }
        begin.setUrl(this.m_lgURL);
        final String value2 = hashMap.get("LG_Group");
        if (null != value2) {
            this.m_lgGroup = value2;
        }
        begin.setGroupName(this.m_lgGroup);
        begin.setAppType((short)681);
        final String value3 = hashMap.get("LG_Service");
        if (null != value3) {
            this.m_lgService = value3;
        }
        begin.setServiceName(this.m_lgService);
        begin.setSvcType((short)686);
        final String value4 = hashMap.get("LG_Operation");
        if (null != value4) {
            this.m_lgOperation = value4;
        }
        begin.setOpName(this.m_lgOperation);
        begin.setPeerAddr(this.m_lgPeer);
        this.m_isAnalized = false;
    }
    
    public String analyze(final String opName) {
        final ClientInteraction value = ClientInteraction.get();
        if (null != opName) {
            value.setOpName(opName);
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
    
    public void endInteraction(final String failure) {
        final ClientInteraction value = ClientInteraction.get();
        if (null != failure) {
            value.setFailure(failure);
        }
        value.end();
        this.m_isAnalized = false;
    }
    
    public void setUrl(final String lgURL) {
        this.m_lgURL = lgURL;
        try {
            final PscURLParser pscURLParser = new PscURLParser(this.m_lgURL);
            this.m_lgPeer = pscURLParser.getHost();
            final String service = pscURLParser.getService();
            if (null != service) {
                this.m_lgService = service;
                this.m_appService = service;
            }
        }
        catch (Exception ex) {
            this.m_lgPeer = "localhost";
        }
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
    
    @Override
    public String toString() {
        String str = "";
        String interactionID = "";
        final ClientInteraction value = ClientInteraction.get();
        if (value != null) {
            str = "CI";
            interactionID = value.getInteractionID();
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("OpenEdge ESB Interceptor").append(" ").append(str).append("(").append(interactionID).append(") on thread: ").append(Thread.currentThread().getName());
        if (value != null) {
            sb.append(" ==>");
            sb.append(" PeerAddr: [ " + value.getPeerAddr());
            sb.append(" ] URL: [ " + value.getUrl());
            sb.append(" ] Type: [ " + value.getSvcType());
            sb.append(" ] Flow ID: [ " + value.getFlowID());
            sb.append(" ] Op ID: [ " + value.getOpID());
            sb.append(" ] Locus ID: [ " + value.getParentID());
            sb.append(" ] Chain ID: [ " + value.getChainID());
            sb.append(" ] OneWay: [ " + value.getOneWay());
            sb.append(" ] isFault: [ " + (value.getFailure() != null));
            sb.append(" ] G: " + value.getGroupName());
            sb.append(" S: " + value.getServiceName());
            sb.append(" O: " + value.getOpName());
        }
        return sb.toString();
    }
    
    public String getGroup() {
        return this.m_lgGroup;
    }
    
    public String getOperation() {
        return this.m_lgOperation;
    }
    
    public String getService() {
        return this.m_lgService;
    }
    
    public void setGroup(final String lgGroup) {
        this.m_lgGroup = lgGroup;
    }
    
    public void setOperation(final String lgOperation) {
        this.m_lgOperation = lgOperation;
    }
    
    public void setService(final String lgService) {
        this.m_lgService = lgService;
    }
    
    public String getPeer() {
        return this.m_lgPeer;
    }
    
    public String getUrl() {
        return this.m_lgURL;
    }
    
    public void setPeer(final String lgPeer) {
        this.m_lgPeer = lgPeer;
    }
    
    public boolean isInstrumented() {
        return ServerInteraction.get() != null;
    }
}
