// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import java.rmi.RemoteException;
import com.progress.ubroker.tools.IYodaRMI;
import com.progress.chimera.common.NameContext;
import java.rmi.server.RemoteStub;
import com.progress.ubroker.tools.AbstractGuiToolRemObj;

class WebService extends AbstractGuiToolRemObj
{
    RemoteStub m_stub;
    String name;
    String namespace;
    boolean enabled;
    WsaInstanceRemoteObject parent;
    protected static NameContext nameTable;
    
    public WebService(final String s, final String namespace, final WsaInstanceRemoteObject parent) throws RemoteException {
        super(parent, s, parent.getPropGroupPath() + "." + s, parent.m_logInUser, parent.m_loginUserPwd, parent.m_rmiHost, parent.m_rmiPort, parent.getAdminSrvrURL());
        this.m_stub = null;
        this.name = "";
        this.namespace = "";
        this.enabled = false;
        this.parent = null;
        this.name = s;
        this.namespace = namespace;
        this.parent = parent;
        this.m_stub = super.stub();
    }
    
    public WebService(final String s, final String s2, final boolean enabled, final WsaInstanceRemoteObject wsaInstanceRemoteObject) throws RemoteException {
        this(s, s2, wsaInstanceRemoteObject);
        this.enabled = enabled;
    }
    
    protected NameContext getNameContext() {
        return WebService.nameTable;
    }
    
    public static WebService findWebService(final String s) {
        try {
            return WebService.nameTable.get(AbstractGuiToolRemObj.adjustName(s));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public String getDisplayName() throws RemoteException {
        return this.name;
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.ubroker.wsa.WSMMCClient";
    }
    
    public RemoteStub getRemStub() throws RemoteException {
        return this.m_stub;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setNamespace(final String namespace) {
        this.namespace = namespace;
    }
    
    static {
        WebService.nameTable = new NameContext();
    }
}
