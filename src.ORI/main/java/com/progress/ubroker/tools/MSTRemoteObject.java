// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Hashtable;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Vector;
import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.chimera.common.NameContext;
import com.progress.chimera.common.IChimeraHierarchy;

public class MSTRemoteObject extends AbstractGuiToolRemObj implements IChimeraHierarchy, IYodaSharedResources, IBTMsgCodes
{
    static final String MST_REMOTE_OBJ_NAME = "Messenger Tool Remote Object";
    String m_logInUser;
    String m_loginUserPwd;
    protected static NameContext nameTable;
    
    public MSTRemoteObject(final IYodaRMI yodaRMI, final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7) throws RemoteException {
        super(yodaRMI, s3, s4, s, s2, s5, s6, s7);
        this.m_logInUser = null;
        this.m_loginUserPwd = null;
    }
    
    protected NameContext getNameContext() {
        return MSTRemoteObject.nameTable;
    }
    
    public static MSTRemoteObject findMSTRemoteObject(final String s) {
        try {
            return MSTRemoteObject.nameTable.get(AbstractGuiToolRemObj.adjustName(s));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public Enumeration getChildren() throws RemoteException {
        return new SerializableEnumeration(new Vector());
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.ubroker.MSTMMCClient";
    }
    
    public String getHelpMapFile() throws RemoteException {
        return null;
    }
    
    public String getApplicationName() throws RemoteException {
        return "Messenger Tool Remote Object";
    }
    
    public String getDisplayName() throws RemoteException {
        return super.m_svcName;
    }
    
    private String getOsName() {
        return System.getProperty("os.name");
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
    
    public IPropertyManagerRemote getRPM() throws RemoteException {
        return super.getRPM();
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
        return super.stub();
    }
    
    public String getAdminServerIPAddr() throws RemoteException {
        return super.getAdminServerIPAddr();
    }
    
    public Hashtable getLogFiles(final String s) throws RemoteException {
        return null;
    }
    
    static {
        MSTRemoteObject.nameTable = new NameContext();
    }
}
