// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.ubroker.tools.UBToolsMsg;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteStub;
import com.progress.chimera.common.IChimeraHierarchy;

class WebServicesPersonality implements IChimeraHierarchy
{
    RemoteStub m_stub;
    WsaInstanceRemoteObject parent;
    
    public WebServicesPersonality(final WsaInstanceRemoteObject parent) throws RemoteException {
        this.m_stub = null;
        this.parent = null;
        this.parent = parent;
        try {
            this.m_stub = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381978L, new Object[] { "WSRemoteObject", "Web Services", ex.getMessage() }));
        }
    }
    
    public String getDisplayName() throws RemoteException {
        return "Web Services";
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.ubroker.wsa.WSCategoryMMCClient";
    }
    
    public Enumeration getChildren() throws RemoteException {
        return new SerializableEnumeration(this.parent.getWebServices());
    }
    
    public RemoteStub getRemStub() throws RemoteException {
        return this.m_stub;
    }
    
    public WsaInstanceRemoteObject getParent() {
        return this.parent;
    }
}
