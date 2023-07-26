// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;
import java.rmi.UnknownHostException;
import java.rmi.RMISecurityManager;
import com.progress.chimera.common.IChimeraRemoteCommand;
import com.progress.common.util.Getopt;
import com.progress.chimera.adminserver.IAdminServer;
import com.progress.chimera.adminserver.IAdminServerConnection;
import com.progress.common.util.ICmdConst;

public class RemoteCommandClient implements ICmdConst, IBTMsgCodes
{
    protected String m_host;
    protected String m_userName;
    protected String m_password;
    protected String m_rmiPort;
    protected String m_rmiService;
    protected String m_personality;
    protected IAdminServerConnection m_adminConStub;
    protected IAdminServer m_adminStub;
    public static Object[] argList;
    Getopt.GetoptList[] optArray;
    
    public RemoteCommandClient() {
        this.optArray = new Getopt.GetoptList[] { new Getopt.GetoptList("query", 10), new Getopt.GetoptList("q", 10), new Getopt.GetoptList("start", 20), new Getopt.GetoptList("x", 20), new Getopt.GetoptList("stop", 30), new Getopt.GetoptList("e", 30), new Getopt.GetoptList("help", 40), new Getopt.GetoptList("h", 40), new Getopt.GetoptList("name:", 50), new Getopt.GetoptList("i:", 50), new Getopt.GetoptList("user:", 60), new Getopt.GetoptList("u:", 60), new Getopt.GetoptList("host:", 90), new Getopt.GetoptList("r:", 90), new Getopt.GetoptList("port:", 100), new Getopt.GetoptList("addservers:", 70), new Getopt.GetoptList("addagents:", 70), new Getopt.GetoptList("s:", 70), new Getopt.GetoptList("trimservers:", 80), new Getopt.GetoptList("trimagents:", 80), new Getopt.GetoptList("trim:", 80), new Getopt.GetoptList("t:", 110), new Getopt.GetoptList("", 0) };
    }
    
    protected IChimeraRemoteCommand connect(final String s) {
        IChimeraRemoteCommand chimeraRemoteCommand = null;
        this.m_adminConStub = null;
        final String url = this.createURL();
        final String s2 = "com.progress.chimera.common.IChimeraRemoteCommand";
        try {
            System.setSecurityManager(new RMISecurityManager());
            this.m_adminConStub = (IAdminServerConnection)lookupService(url);
        }
        catch (UnknownHostException ex) {
            RemoteCommandClient.argList = new Object[] { this.m_host };
            System.err.println(UBToolsMsg.getMsg(7094295313015382391L, RemoteCommandClient.argList));
        }
        catch (Exception ex2) {
            RemoteCommandClient.argList = new Object[] { url };
            System.err.println(UBToolsMsg.getMsg(7094295313015382111L, RemoteCommandClient.argList));
            return chimeraRemoteCommand;
        }
        if (s != null) {
            try {
                this.m_adminStub = this.m_adminConStub.connect(this.m_userName, this.m_password);
                if (this.m_adminStub == null) {
                    System.err.println(UBToolsMsg.getMsgStripCode(7094295313015382352L));
                    return null;
                }
                final Vector plugins = this.m_adminStub.getPlugins(s2);
                RemoteCommandClient.argList = new Object[] { s };
                System.out.println(UBToolsMsg.getMsg(7094295313015382112L, RemoteCommandClient.argList));
                for (int i = 0; i <= plugins.size(); ++i) {
                    final Enumeration children = plugins.elementAt(i).getChildren();
                    while (children.hasMoreElements()) {
                        final IChimeraRemoteCommand chimeraRemoteCommand2 = children.nextElement();
                        if (s.equals(chimeraRemoteCommand2.getDisplayName())) {
                            chimeraRemoteCommand = chimeraRemoteCommand2;
                            break;
                        }
                    }
                    if (chimeraRemoteCommand != null) {
                        break;
                    }
                }
            }
            catch (Exception ex3) {
                RemoteCommandClient.argList = new Object[] { s };
                System.out.println(UBToolsMsg.getMsg(7094295313015382113L, RemoteCommandClient.argList));
            }
        }
        return chimeraRemoteCommand;
    }
    
    protected IAdminRemote connect() {
        IAdminRemote adminRemote = null;
        this.m_adminConStub = null;
        final String url = this.createURL();
        try {
            System.setSecurityManager(new RMISecurityManager());
            adminRemote = (IAdminRemote)lookupService(url);
        }
        catch (UnknownHostException ex) {
            System.err.println("Unkown host " + this.m_host);
        }
        catch (Exception ex2) {
            RemoteCommandClient.argList = new Object[] { url };
            System.err.println(UBToolsMsg.getMsg(7094295313015382113L, RemoteCommandClient.argList));
        }
        return adminRemote;
    }
    
    protected boolean parseConnectParams(final String[] array) {
        final Getopt getopt = new Getopt(array);
        boolean b = false;
        final boolean b2 = false;
        int opt;
        while ((opt = getopt.getOpt(this.optArray)) != -1) {
            switch (opt) {
                case 60: {
                    this.m_userName = getopt.getOptArg();
                    b = true;
                    continue;
                }
            }
        }
        if (!b2) {
            b = true;
        }
        return b;
    }
    
    protected boolean shutdownAll() {
        this.connect(null);
        try {
            this.m_adminStub.shutdown();
        }
        catch (RemoteException ex) {
            RemoteCommandClient.argList = new Object[] { ex.toString() };
            System.err.println(UBToolsMsg.getMsg(7094295313015382114L, RemoteCommandClient.argList));
            return false;
        }
        return true;
    }
    
    protected String createURL() {
        final StringBuffer sb = new StringBuffer(64);
        sb.append("rmi://");
        if (this.m_host == null) {
            sb.append("localhost");
        }
        else {
            sb.append(this.m_host);
        }
        sb.append(":");
        if (this.m_rmiPort == null) {
            sb.append(20931);
        }
        else {
            sb.append(this.m_rmiPort);
        }
        if (this.m_rmiService == null) {
            sb.append("/");
            sb.append("Chimera");
        }
        else {
            sb.append(this.m_rmiService);
        }
        return sb.toString();
    }
    
    private static Remote lookupService(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        final String host = pscURLParser.getHost();
        final int port = pscURLParser.getPort();
        final String service = pscURLParser.getService();
        final Registry registry = LocateRegistry.getRegistry(host, port);
        return (service == null) ? registry : registry.lookup(service);
    }
}
