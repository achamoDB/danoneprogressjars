// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.juniper.admin.JAPlugIn;
import com.progress.juniper.admin.IJAPlugIn;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import java.util.Enumeration;
import com.progress.ubroker.util.PropGroupDescriptor;
import com.progress.ubroker.util.UBPreferenceProperties;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.Remote;
import java.io.IOException;
import com.progress.common.util.Getopt;
import com.progress.chimera.adminserver.ServerPluginInfo;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.ubroker.util.PropFilename;
import com.progress.chimera.adminserver.ServerPolicyInfo;
import java.util.HashMap;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.ubroker.util.Logger;
import java.util.Vector;
import com.progress.chimera.adminserver.AbstractServerPlugin;

public class UBCommandPlugin extends AbstractServerPlugin implements Runnable, IUBPluginCallback, IPMUAccessCallback, IBTMsgCodes
{
    private static final int FETCH_PMP_RETRIES = 3;
    private static final boolean DEBUG_TRACE = false;
    private Vector m_brokerNames;
    private Vector m_autostartName;
    private Vector m_brokerObjects;
    private Logger m_logFile;
    private String m_rmiString;
    private String m_rmiHost;
    private String m_rmiPort;
    private int m_logDest;
    private boolean m_refreshPropFile;
    private boolean m_reqUserName;
    PropMgrPlugin m_pmpObject;
    PropMgrUtils m_propUtils;
    private HashMap m_policyInfo;
    private ServerPolicyInfo m_myPolicyInfo;
    public String[] m_personalities;
    static boolean osChecked;
    static boolean osIsSupported;
    static boolean bAppendToLog;
    public static Object[] argList;
    public static String m_theMessage;
    public PropFilename m_propFile;
    
    public UBCommandPlugin() {
        this.m_logDest = 2;
        this.m_refreshPropFile = false;
        this.m_reqUserName = false;
        this.m_pmpObject = null;
        this.m_propUtils = null;
        this.m_policyInfo = new HashMap();
        this.m_myPolicyInfo = null;
        this.m_personalities = null;
    }
    
    public boolean init(final int myIndex, final IAdministrationServer administrationServer, final String[] args) {
        final String[] array = new String[2];
        final String str = "cmdplugin.log";
        super.m_myIndex = myIndex;
        super.m_args = args;
        checkOS();
        if (!UBCommandPlugin.osIsSupported) {
            UBCommandPlugin.argList = new Object[] { LoadablePlatform.getInvalidOSStr() };
            System.out.println(UBToolsMsg.getMsg(7094295313015382266L, UBCommandPlugin.argList));
            return false;
        }
        try {
            this.m_rmiHost = administrationServer.getHost();
            this.m_rmiPort = administrationServer.getPort();
            final Vector serverPluginInfo = administrationServer.getServerPluginInfo();
            for (int i = 0; i < serverPluginInfo.size(); ++i) {
                final ServerPluginInfo serverPluginInfo2 = serverPluginInfo.elementAt(i);
                if (serverPluginInfo2.getPersonality() != null) {
                    this.m_policyInfo.put(serverPluginInfo2.getPersonality(), serverPluginInfo2.getPolicy());
                }
                if (serverPluginInfo2.getIndex() == super.m_myIndex) {
                    this.m_myPolicyInfo = serverPluginInfo2.getPolicy();
                }
            }
        }
        catch (Exception ex5) {
            final String msg = UBToolsMsg.getMsg(7094295313015382115L);
            System.err.println(msg);
            this.m_logFile.LogMsgln(this.m_logDest, 1, true, msg);
        }
        try {
            this.m_reqUserName = administrationServer.isReqUserName();
        }
        catch (Exception ex6) {
            this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015383807L));
        }
        if (this.m_rmiHost == null || this.m_rmiPort == null) {
            return false;
        }
        this.m_rmiString = "rmi://" + this.m_rmiHost + ":" + this.m_rmiPort + "/";
        (this.m_pmpObject = PropMgrPluginFinder.get()).registerUser(this);
        if (this.m_pmpObject == null) {
            System.out.println(UBToolsMsg.getMsg(7094295313015382116L));
            return false;
        }
        try {
            this.m_personalities = this.m_pmpObject.getPersonalities();
        }
        catch (PropMgrUtils.CantGetParentGroup cantGetParentGroup) {
            System.err.println(cantGetParentGroup.getMessage());
            return false;
        }
        catch (PropMgrUtils.CantGetPropCollection collection) {
            System.err.println(collection.getMessage());
            return false;
        }
        catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.toString());
            return false;
        }
        final Getopt getopt = new Getopt(args);
        final String s = "l:d";
        this.m_propFile = null;
        int n = 3;
        int opt;
        while ((opt = getopt.getOpt(s)) != -1) {
            if (opt == 108) {
                this.m_logDest = Integer.parseInt(getopt.getOptArg());
            }
            else {
                if (opt != 100) {
                    continue;
                }
                n = 5;
            }
        }
        try {
            final String expandedUBWorkDir = this.m_pmpObject.getExpandedUBWorkDir();
            if (expandedUBWorkDir != null && expandedUBWorkDir.length() > 0) {
                this.m_logFile = new Logger(expandedUBWorkDir + System.getProperty("file.separator") + str, n, UBCommandPlugin.bAppendToLog);
            }
            else {
                this.m_logFile = new Logger(str, n, UBCommandPlugin.bAppendToLog);
            }
            if (n == 5) {
                this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382118L));
            }
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
            UBCommandPlugin.argList = new Object[] { str };
            System.out.println(UBToolsMsg.getMsg(7094295313015382117L, UBCommandPlugin.argList));
            this.m_logFile = new Logger();
        }
        this.m_brokerNames = new Vector();
        this.m_brokerObjects = new Vector();
        this.m_autostartName = new Vector();
        for (int j = 0; j < this.m_personalities.length; ++j) {
            try {
                final String s2 = this.m_personalities[j];
                UBCommandPlugin.argList = new Object[] { this.m_personalities[j] };
                this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382123L, UBCommandPlugin.argList));
                String[] svcNameForParentGrp = null;
                try {
                    svcNameForParentGrp = this.m_pmpObject.getSvcNameForParentGrp(s2);
                }
                catch (Exception ex3) {
                    ex3.printStackTrace();
                    UBCommandPlugin.argList = new Object[] { this.m_personalities[j] };
                    this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382124L, UBCommandPlugin.argList));
                }
                if (svcNameForParentGrp != null) {
                    for (int k = 0; k < svcNameForParentGrp.length; ++k) {
                        UBCommandPlugin.argList = new Object[] { svcNameForParentGrp[k], this.m_personalities[j] };
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382283L, UBCommandPlugin.argList));
                        this.m_brokerNames.addElement(svcNameForParentGrp[k]);
                        final IAdminRemote connectToBroker = this.connectToBroker(this.m_rmiString + svcNameForParentGrp[k], 1);
                        if (connectToBroker != null) {
                            this.addBroker(svcNameForParentGrp[k], connectToBroker);
                        }
                        else if (this.m_pmpObject.getAutoStartValue(this.m_personalities[j] + "." + svcNameForParentGrp[k]).equals("1")) {
                            this.m_autostartName.addElement(svcNameForParentGrp[k]);
                        }
                    }
                }
            }
            catch (Exception ex4) {
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, ex4.getMessage());
            }
        }
        return true;
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        Remote remote = null;
        UBCommandPlugin.argList = new Object[] { s };
        this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382126L, UBCommandPlugin.argList));
        try {
            this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382129L));
            remote = new UBRemoteCommand(this, this.m_pmpObject, this.m_rmiHost, this.m_rmiPort, s, this.m_logFile, this.m_logDest);
        }
        catch (Exception ex) {
            this.m_logFile.LogMsgln(this.m_logDest, 1, true, ex.getMessage());
        }
        return remote;
    }
    
    public void run() {
        boolean b = false;
        int i = 0;
        final UBPreferenceProperties preferences = this.m_pmpObject.getPreferences();
        final int admSrvrRegisteredRetry = preferences.getAdmSrvrRegisteredRetry();
        final int admSrvrRegisteredIntervalRetry = preferences.getAdmSrvrRegisteredIntervalRetry();
        try {
            for (i = 0; i < admSrvrRegisteredRetry; ++i) {
                try {
                    if (lookupService(this.m_rmiString) != null) {
                        b = true;
                        break;
                    }
                }
                catch (UnknownHostException ex4) {
                    UBToolsMsg.getMsg(7094295313015382391L, UBCommandPlugin.argList = new Object[] { this.m_rmiHost });
                    System.err.println("Unknown host " + this.m_rmiHost);
                }
                catch (Exception ex) {
                    System.out.println(ex.toString());
                    this.m_logFile.LogMsgln(this.m_logDest, 2, true, ex.toString());
                }
                Thread.sleep(admSrvrRegisteredIntervalRetry);
            }
        }
        catch (Exception ex2) {
            System.out.println("Exception waiting for AdminServer: " + ex2.toString());
            this.m_logFile.LogMsgln(this.m_logDest, 2, true, ex2.toString());
        }
        Label_0401: {
            if (b) {
                UBCommandPlugin.argList = new Object[] { "UBCommandPlugin.run()", new Integer(i) };
                this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382315L, UBCommandPlugin.argList));
                try {
                    this.autoStartAll(admSrvrRegisteredRetry, admSrvrRegisteredIntervalRetry);
                    break Label_0401;
                }
                catch (Exception ex3) {
                    final String string = "Database autostarts failed: " + ((ex3.toString() != null) ? ex3.toString() : "Unknown");
                    System.out.println(string);
                    this.m_logFile.LogMsgln(this.m_logDest, 2, true, string);
                    break Label_0401;
                }
                break Label_0401;
            }
            UBCommandPlugin.argList = new Object[] { "UBCommandPlugin.run()", new Integer(i) };
            this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382319L));
            try {
                while (true) {
                    Thread.sleep(300000L);
                    for (int j = 0; j < this.m_brokerObjects.size(); ++j) {
                        final BrokerObject brokerObject = this.m_brokerObjects.elementAt(j);
                        try {
                            brokerObject.m_brokerObject.ping();
                        }
                        catch (RemoteException ex5) {
                            UBCommandPlugin.argList = new Object[] { brokerObject.m_brokerName };
                            this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382131L, UBCommandPlugin.argList));
                            this.removeBroker(brokerObject.m_brokerName);
                            break;
                        }
                    }
                }
            }
            catch (InterruptedException ex6) {
                if (this.m_logFile != null) {
                    this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382132L));
                }
            }
            catch (Throwable t) {
                UBCommandPlugin.argList = new Object[] { t.getMessage() };
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382133L, UBCommandPlugin.argList));
            }
        }
    }
    
    public void shutdown() {
        this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382134L));
        for (int i = 0; i < this.m_brokerNames.size(); ++i) {
            final String str = this.m_brokerNames.elementAt(i);
            UBCommandPlugin.argList = new Object[] { str };
            this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382135L, UBCommandPlugin.argList));
            if (this.connectToBroker(this.m_rmiString + str, 1) != null) {
                UBCommandPlugin.argList = new Object[] { str };
                this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382281L, UBCommandPlugin.argList));
                try {
                    final PropGroupDescriptor ubPersonStrForSvcName = this.m_pmpObject.getUBPersonStrForSvcName(str);
                    final String getfullPropSpec = ubPersonStrForSvcName.getfullPropSpec();
                    if (new SvcControlCmd(new SvcStartArgsPkt(this.getPolicy(ubPersonStrForSvcName.getSvcTypeStr()), getfullPropSpec, str, this.m_pmpObject), str, this.m_rmiHost, this.m_rmiPort).stopService(getfullPropSpec)) {
                        UBCommandPlugin.argList = new Object[] { str };
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382281L, UBCommandPlugin.argList));
                    }
                    else {
                        UBCommandPlugin.argList = new Object[] { str };
                        this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382103L, UBCommandPlugin.argList));
                    }
                }
                catch (Exception ex) {
                    UBCommandPlugin.argList = new Object[] { str };
                    this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382136L, UBCommandPlugin.argList));
                }
            }
            else {
                UBCommandPlugin.argList = new Object[] { str };
                this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382137L, UBCommandPlugin.argList));
            }
        }
        this.m_logFile.CloseLogfile();
        this.m_logFile = null;
    }
    
    public boolean addBroker(final String s, final IAdminRemote adminRemote) {
        UBCommandPlugin.argList = new Object[] { s };
        this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382138L, UBCommandPlugin.argList));
        this.m_brokerObjects.addElement(new BrokerObject(s, adminRemote));
        return true;
    }
    
    public boolean removeBroker(final String anObject) {
        boolean b = false;
        UBCommandPlugin.argList = new Object[] { anObject };
        this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382139L, UBCommandPlugin.argList));
        for (int i = 0; i < this.m_brokerObjects.size(); ++i) {
            if (((BrokerObject)this.m_brokerObjects.elementAt(i)).m_brokerName.equals(anObject)) {
                this.m_brokerObjects.removeElementAt(i);
                b = true;
                break;
            }
        }
        return b;
    }
    
    public IAdminRemote getBroker(final String s) {
        IAdminRemote adminRemote = null;
        UBCommandPlugin.argList = new Object[] { s };
        this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382140L, UBCommandPlugin.argList));
        for (int i = 0; i < this.m_brokerObjects.size(); ++i) {
            final BrokerObject brokerObject = this.m_brokerObjects.elementAt(i);
            if (brokerObject.m_brokerName.equals(s)) {
                UBCommandPlugin.argList = new Object[] { s };
                this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382141L, UBCommandPlugin.argList));
                adminRemote = brokerObject.m_brokerObject;
                break;
            }
        }
        try {
            if (adminRemote.ping() <= 0L) {
                adminRemote = null;
            }
        }
        catch (Exception ex) {
            adminRemote = null;
        }
        if (adminRemote == null) {
            UBCommandPlugin.argList = new Object[] { s };
            this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382142L, UBCommandPlugin.argList));
            adminRemote = this.connectToBroker(this.m_rmiString + s, 1);
        }
        return adminRemote;
    }
    
    public boolean isReqUserName() {
        return this.m_reqUserName;
    }
    
    public IAdminRemote connectToBroker(final String s, final int n) {
        IAdminRemote adminRemote = null;
        int i = 0;
        while (i++ < n) {
            try {
                UBCommandPlugin.argList = new Object[] { s };
                this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382143L, UBCommandPlugin.argList));
                adminRemote = (IAdminRemote)lookupService(s);
            }
            catch (UnknownHostException ex) {
                System.err.println("Unkown host");
                continue;
            }
            catch (Exception ex2) {
                UBCommandPlugin.argList = new Object[] { " Retries: " + Integer.toString(i) };
                this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382144L, UBCommandPlugin.argList));
                if (i >= n) {
                    continue;
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException ex3) {
                    this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382132L));
                    break;
                }
                continue;
            }
            break;
        }
        return adminRemote;
    }
    
    public ServerPolicyInfo getPolicy(final String s) {
        if (this.m_policyInfo.containsKey(s)) {
            return this.m_policyInfo.get(s);
        }
        return this.m_myPolicyInfo;
    }
    
    private IAdminRemote autoStart(final String s, final String s2) {
        IAdminRemote connectToBroker = null;
        final String svcName = this.m_pmpObject.getSvcName(s);
        final ServerPolicyInfo policy = this.getPolicy(s2);
        try {
            final SvcStartArgsPkt svcStartArgsPkt = new SvcStartArgsPkt(policy, s, svcName, this.m_pmpObject);
            if (this.m_reqUserName && this.m_pmpObject.reqUserName(s) && (svcStartArgsPkt.m_username == null || svcStartArgsPkt.m_password == null)) {
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015383808L, UBCommandPlugin.argList));
                return null;
            }
            if (new SvcControlCmd(svcStartArgsPkt, svcName, this.m_rmiHost, this.m_rmiPort).startService()) {
                connectToBroker = this.connectToBroker(this.m_rmiString + svcName, 5);
            }
            else {
                UBCommandPlugin.argList = new Object[] { svcName };
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382121L, UBCommandPlugin.argList));
            }
        }
        catch (Exception ex) {
            UBCommandPlugin.argList = new Object[] { svcName };
            this.m_logFile.LogMsgln(this.m_logDest, 5, true, UBToolsMsg.getMsg(7094295313015382136L, UBCommandPlugin.argList));
        }
        return connectToBroker;
    }
    
    public void handlePMUContextReset(final boolean b) {
        if (!b) {
            this.m_pmpObject = null;
        }
        else {
            this.m_pmpObject = PropMgrPluginFinder.get();
            this.getNewProperties();
        }
    }
    
    public boolean getRefreshFlag() {
        return this.m_refreshPropFile;
    }
    
    public boolean getNewProperties() {
        final String[] array = new String[2];
        this.m_logFile.LogMsgln(this.m_logDest, 5, true, "Refreshing command line plugin properties cache ");
        this.m_refreshPropFile = false;
        try {
            this.m_personalities = this.m_pmpObject.getPersonalities();
        }
        catch (PropMgrUtils.CantGetParentGroup cantGetParentGroup) {
            System.err.println(cantGetParentGroup.toString());
            return false;
        }
        catch (PropMgrUtils.CantGetPropCollection collection) {
            System.err.println(collection.getMessage());
            return false;
        }
        catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.toString());
            return false;
        }
        this.m_brokerNames.removeAllElements();
        for (int i = 0; i < this.m_personalities.length; ++i) {
            try {
                final String str = this.m_personalities[i];
                this.m_logFile.LogMsgln(this.m_logDest, 2, true, "Personality: " + str);
                String[] svcNameForParentGrp = null;
                try {
                    svcNameForParentGrp = this.m_pmpObject.getSvcNameForParentGrp(str);
                }
                catch (Exception ex2) {
                    ex2.printStackTrace();
                    this.m_logFile.LogMsgln(this.m_logDest, 2, true, "No entries for personality " + this.m_personalities[i]);
                }
                if (svcNameForParentGrp != null) {
                    for (int j = 0; j < svcNameForParentGrp.length; ++j) {
                        UBCommandPlugin.argList = new Object[] { svcNameForParentGrp[j], this.m_personalities[i] };
                        this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382283L, UBCommandPlugin.argList));
                        this.m_brokerNames.addElement(svcNameForParentGrp[j]);
                        final IAdminRemote connectToBroker = this.connectToBroker(this.m_rmiString + svcNameForParentGrp[j], 1);
                        if (connectToBroker != null) {
                            this.addBroker(svcNameForParentGrp[j], connectToBroker);
                        }
                    }
                }
            }
            catch (Exception ex3) {
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, ex3.getMessage());
            }
        }
        return true;
    }
    
    public static void checkOS() {
        if (!UBCommandPlugin.osChecked) {
            UBCommandPlugin.osIsSupported = LoadablePlatform.validate();
            UBCommandPlugin.osChecked = true;
        }
    }
    
    public void autoStartAll(final int n, final int n2) {
        final Enumeration<String> elements = this.m_autostartName.elements();
        if (this.m_autostartName.size() > 0) {
            final DBObject dbObject = new DBObject(this);
            if (DBObject.getInstance()) {
                final String initDone = DBObject.initDone(n, n2);
                if (initDone != null) {
                    this.m_logFile.LogMsgln(2, 2, true, initDone);
                }
            }
        }
        try {
            for (int i = 0; i < this.m_autostartName.size(); ++i) {
                final String str = elements.nextElement();
                if (this.connectToBroker(this.m_rmiString + str, 1) == null) {
                    final PropGroupDescriptor ubPersonStrForSvcName = this.m_pmpObject.getUBPersonStrForSvcName(str);
                    if (ubPersonStrForSvcName == null) {
                        System.out.println("it's null!!");
                    }
                    final String getfullPropSpec = ubPersonStrForSvcName.getfullPropSpec();
                    String svcTypeStr = ubPersonStrForSvcName.getSvcTypeStr();
                    if (svcTypeStr == null) {
                        svcTypeStr = "";
                    }
                    if (this.m_pmpObject.getAutoStartValue(getfullPropSpec).equals("1")) {
                        if (this.m_pmpObject.isNameServerPersonality(svcTypeStr) || svcTypeStr.equals("NS")) {
                            if (this.m_pmpObject.getNSLocation(getfullPropSpec).equals("local")) {
                                UBCommandPlugin.argList = new Object[] { str };
                                this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382119L, UBCommandPlugin.argList));
                                final IAdminRemote autoStart = this.autoStart(getfullPropSpec, svcTypeStr);
                                if (autoStart != null) {
                                    UBCommandPlugin.argList = new Object[] { str };
                                    this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382120L, UBCommandPlugin.argList));
                                    this.addBroker(str, autoStart);
                                }
                            }
                        }
                        else {
                            UBCommandPlugin.argList = new Object[] { str };
                            this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382119L, UBCommandPlugin.argList));
                            final IAdminRemote autoStart2 = this.autoStart(getfullPropSpec, svcTypeStr);
                            if (autoStart2 != null) {
                                UBCommandPlugin.argList = new Object[] { str };
                                this.m_logFile.LogMsgln(this.m_logDest, 2, true, UBToolsMsg.getMsg(7094295313015382120L, UBCommandPlugin.argList));
                                this.addBroker(str, autoStart2);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            UBCommandPlugin.argList = new Object[] { ex.toString() };
            this.m_logFile.LogMsgln(this.m_logDest, 1, true, UBToolsMsg.getMsg(7094295313015382328L, UBCommandPlugin.argList));
        }
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
    
    static {
        UBCommandPlugin.osChecked = false;
        UBCommandPlugin.osIsSupported = true;
        UBCommandPlugin.bAppendToLog = true;
    }
    
    class BrokerObject
    {
        public String m_brokerName;
        public IAdminRemote m_brokerObject;
        
        BrokerObject(final String original, final IAdminRemote brokerObject) {
            this.m_brokerName = new String(original);
            this.m_brokerObject = brokerObject;
        }
    }
    
    static class DBObject
    {
        static int FETCH_JAP_RETRIES;
        static int GET_JAP_INST_RETRY_INTERVAL;
        static IJAPlugIn dbPluginInstance;
        static UBCommandPlugin parentObject;
        
        public DBObject(final UBCommandPlugin parentObject) {
            DBObject.parentObject = parentObject;
        }
        
        public static boolean getInstance() {
            int n = 0;
            while (DBObject.dbPluginInstance == null && ++n < DBObject.FETCH_JAP_RETRIES) {
                try {
                    Thread.sleep(DBObject.GET_JAP_INST_RETRY_INTERVAL);
                }
                catch (InterruptedException ex) {}
                DBObject.dbPluginInstance = JAPlugIn.get();
            }
            String s;
            if (DBObject.dbPluginInstance != null) {
                s = UBToolsMsg.getMsg(7094295313015382700L, new Object[] { new Integer(n), new Integer(DBObject.GET_JAP_INST_RETRY_INTERVAL) });
            }
            else {
                s = UBToolsMsg.getMsg(7094295313015382701L, new Object[] { new Integer(n), new Integer(DBObject.GET_JAP_INST_RETRY_INTERVAL) });
            }
            DBObject.parentObject.m_logFile.LogMsgln(2, 2, true, s);
            return DBObject.dbPluginInstance != null;
        }
        
        public static String initDone(final int n, final int n2) {
            int n3 = 0;
            boolean b = false;
            final String s = null;
            if (DBObject.dbPluginInstance == null) {
                return s;
            }
            DBObject.parentObject.m_logFile.LogMsgln(2, 2, true, UBToolsMsg.getMsg(7094295313015382703L));
            try {
                b = DBObject.dbPluginInstance.initializationComplete();
            }
            catch (Exception ex) {}
            while (!b && ++n3 < n) {
                try {
                    Thread.sleep(n2);
                    b = DBObject.dbPluginInstance.initializationComplete();
                }
                catch (InterruptedException ex2) {}
                catch (Exception ex3) {}
            }
            String s2;
            if (b) {
                s2 = UBToolsMsg.getMsg(7094295313015382705L, new Object[] { new Integer(n3), new Integer(n2) });
            }
            else {
                s2 = UBToolsMsg.getMsg(7094295313015382706L, new Object[] { new Integer(n3), new Integer(n2) });
            }
            return s2;
        }
        
        static {
            DBObject.FETCH_JAP_RETRIES = 10;
            DBObject.GET_JAP_INST_RETRY_INTERVAL = 500;
            DBObject.dbPluginInstance = null;
            DBObject.parentObject = null;
        }
    }
}
