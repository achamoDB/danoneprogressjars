// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.log.IFileRef;
import com.progress.common.networkevents.IEventListener;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import java.rmi.RemoteException;
import com.progress.chimera.common.NameContext;
import java.rmi.server.RemoteStub;
import com.progress.chimera.common.IChimeraHierarchy;

public class UBTRemoteObject extends AbstractGuiToolRemObj implements IChimeraHierarchy, IYodaSharedResources, IBTMsgCodes
{
    static final String UBT_REMOTE_OBJ_NAME = "Unified Broker Tool Remote Object";
    String m_logInUser;
    String m_loginUserPwd;
    RemoteStub m_stubObject;
    protected static NameContext nameTable;
    
    public UBTRemoteObject(final IYodaRMI yodaRMI, final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7) throws RemoteException {
        super(yodaRMI, s, s2, s3, s4, s5, s6, s7);
        this.m_logInUser = null;
        this.m_loginUserPwd = null;
        this.m_stubObject = null;
        this.m_stubObject = super.stub();
    }
    
    protected NameContext getNameContext() {
        return UBTRemoteObject.nameTable;
    }
    
    public static UBTRemoteObject findUBTRemoteObject(final String s) {
        try {
            return UBTRemoteObject.nameTable.get(AbstractGuiToolRemObj.adjustName(s));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.ubroker.UBTMMCClient";
    }
    
    public String getDisplayName() throws RemoteException {
        return super.m_svcName;
    }
    
    public String getApplicationName() throws RemoteException {
        return "Unified Broker Tool Remote Object";
    }
    
    public void setEmptyJPanel() {
    }
    
    public void redoJPanel() {
    }
    
    public String getPropGroupPath() throws RemoteException {
        return super.getPropGroupPath();
    }
    
    public String getSvcName() throws RemoteException {
        return super.getSvcName();
    }
    
    public String getAdminSrvrURL() throws RemoteException {
        return super.getAdminSrvrURL();
    }
    
    public String getAdminSrvrOSName() throws RemoteException {
        return super.getAdminSrvrOSName();
    }
    
    public String getAdminSrvrHost() throws RemoteException {
        return super.getAdminSrvrHost();
    }
    
    public String getAdminSrvrPort() throws RemoteException {
        return super.getAdminSrvrPort();
    }
    
    public IPropertyManagerRemote getRPM() throws RemoteException {
        return super.getRPM();
    }
    
    public String getPropertyFilename() throws RemoteException {
        return super.getPropertyFilename();
    }
    
    public String getSchemaFilename() throws RemoteException {
        return super.getSchemaFilename();
    }
    
    public String[] getSchemaPropFnList() throws RemoteException {
        return super.getSchemaPropFnList();
    }
    
    public IEventBroker getEventBroker() throws RemoteException {
        return super.getEventBroker();
    }
    
    public IYodaRMI getRemoteManageObject() throws RemoteException {
        return super.getRemoteManageObject();
    }
    
    public RemoteStub getRemStub() throws RemoteException {
        return this.m_stubObject;
    }
    
    public String getAdminServerIPAddr() throws RemoteException {
        return super.getAdminServerIPAddr();
    }
    
    public Hashtable getLogFiles(final String s) throws RemoteException {
        return super.getLogFiles(s);
    }
    
    public IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        return super.openFile(s, eventListener);
    }
    
    static {
        UBTRemoteObject.nameTable = new NameContext();
    }
}
