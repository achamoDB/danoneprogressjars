// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.tools.events.EStopServiceEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.tools.events.EStartServiceEvent;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.ubroker.util.PropGroupDescriptor;
import com.progress.common.util.acctAuthenticate;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import com.progress.ubroker.util.Logger;
import java.util.Hashtable;
import java.util.Vector;
import com.progress.ubroker.util.IPropConst;
import com.progress.chimera.common.IChimeraRemoteCommand;

public class UBRemoteCommand extends AbstractUbrokerPlugin implements IChimeraRemoteCommand, IPropConst, IBTMsgCodes
{
    public static final boolean DEBUG_TRACE = false;
    public static Object[] argList;
    private PropMgrPlugin m_pmpObject;
    private String[] m_brokerEnvironment;
    private Vector m_brokers;
    private String m_brokerName;
    private String m_input;
    private String m_output;
    private Hashtable m_structuredOutput;
    private String m_error;
    private String m_rmiHost;
    private String m_rmiPort;
    private String m_userName;
    private IAdminRemote m_ubRemote;
    private IUBPluginCallback m_ubCmdPlugin;
    private Logger m_logFile;
    private int m_logDest;
    private String m_theMessage;
    private IEventBroker m_eventBroker;
    private IEventStream m_eventStream;
    private RemoteStub m_stub;
    private boolean reqUserName;
    
    UBRemoteCommand(final IUBPluginCallback ubCmdPlugin, final PropMgrPlugin pmpObject, final String rmiHost, final String rmiPort, final String userName, final Logger logFile, final int logDest) {
        this.reqUserName = false;
        this.m_pmpObject = pmpObject;
        this.m_rmiHost = rmiHost;
        this.m_rmiPort = rmiPort;
        this.m_brokerName = "Remote Commander";
        this.m_brokerEnvironment = null;
        this.m_ubCmdPlugin = ubCmdPlugin;
        this.m_ubRemote = null;
        this.m_logFile = logFile;
        this.m_logDest = logDest;
        this.m_userName = userName;
        this.m_brokers = this.getBrokerNames();
        this.m_eventBroker = AbstractUbrokerPlugin.getEventBroker();
        if (ubCmdPlugin != null) {
            this.reqUserName = ubCmdPlugin.isReqUserName();
        }
        UBRemoteCommand.argList = new Object[] { userName };
        this.m_theMessage = UBToolsMsg.getMsg(7094295313015382145L, UBRemoteCommand.argList);
        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
        try {
            this.m_stub = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            UBRemoteCommand.argList = new Object[] { ex.getMessage() + " " + "first one" };
            this.m_theMessage = UBToolsMsg.getMsg(7094295313015382146L, UBRemoteCommand.argList);
            this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_theMessage);
        }
    }
    
    UBRemoteCommand(final IUBPluginCallback ubCmdPlugin, final PropMgrPlugin pmpObject, final String s, final String rmiHost, final String rmiPort, final String userName, final Logger logFile, final int logDest) {
        this.reqUserName = false;
        this.m_pmpObject = pmpObject;
        this.m_brokerName = s;
        this.m_rmiHost = rmiHost;
        this.m_rmiPort = rmiPort;
        this.m_brokers = null;
        this.m_ubCmdPlugin = ubCmdPlugin;
        this.m_ubRemote = null;
        this.m_logFile = logFile;
        this.m_logDest = logDest;
        this.m_userName = userName;
        this.m_eventBroker = AbstractUbrokerPlugin.getEventBroker();
        if (ubCmdPlugin != null) {
            this.reqUserName = ubCmdPlugin.isReqUserName();
        }
        try {
            this.m_stub = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            UBRemoteCommand.argList = new Object[] { ex.getMessage() + " " + s };
            this.m_theMessage = UBToolsMsg.getMsg(7094295313015382146L, UBRemoteCommand.argList);
            this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_theMessage);
        }
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        return this;
    }
    
    public Enumeration getChildren() throws RemoteException {
        if (!this.m_brokerName.equals("Remote Commander")) {
            return null;
        }
        final Vector<UBRemoteCommand> vector = new Vector<UBRemoteCommand>();
        for (int i = 0; i < this.m_brokers.size(); ++i) {
            final String s = this.m_brokers.elementAt(i);
            UBRemoteCommand.argList = new Object[] { s };
            this.m_theMessage = UBToolsMsg.getMsg(7094295313015382147L, UBRemoteCommand.argList);
            this.m_logFile.LogMsgln(this.m_logDest, 5, true, this.m_theMessage);
            try {
                vector.addElement(new UBRemoteCommand(this.m_ubCmdPlugin, this.m_pmpObject, s, this.m_rmiHost, this.m_rmiPort, this.m_userName, this.m_logFile, this.m_logDest));
            }
            catch (Exception ex) {
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, "Exception: " + ex.toString());
            }
        }
        return new SerializableEnumeration(vector);
    }
    
    public String getDisplayName() throws RemoteException {
        return this.m_brokerName;
    }
    
    public void setSystemInput(final String input) throws RemoteException {
        this.m_input = input;
    }
    
    public int runIt(final String[] array) throws RemoteException {
        int n = -1;
        final char char1 = array[1].charAt(0);
        final String s = array[0];
        try {
            if (this.m_pmpObject.getWarmStartState()) {
                this.m_theMessage = "Cannot execute command - restart in progress.  Try again later.";
                this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
                return -2;
            }
            String str;
            if (!s.equals("NS")) {
                str = "Broker";
            }
            else {
                str = "NameServer";
            }
            final PropGroupDescriptor ubPersonStrForSvcName = this.m_pmpObject.getUBPersonStrForSvcName(this.m_brokerName);
            final SvcStartArgsPkt svcStartArgsPkt = new SvcStartArgsPkt(this.m_ubCmdPlugin.getPolicy(s), ubPersonStrForSvcName.getfullPropSpec(), this.m_brokerName, this.m_pmpObject);
            final UBControlCmd ubControlCmd = new UBControlCmd(svcStartArgsPkt, this.m_brokerName, this.m_rmiHost, this.m_rmiPort);
            final String brokerName = this.m_brokerName;
            ubPersonStrForSvcName.getfullPropSpec();
            UBRemoteCommand.argList = new Object[] { this.m_userName };
            this.m_theMessage = UBToolsMsg.getMsg(7094295313015382148L, UBRemoteCommand.argList);
            this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
            final String string = "rmi://" + this.m_rmiHost + ":" + this.m_rmiPort + "/" + this.m_brokerName;
            if (this.m_ubRemote == null) {
                this.m_ubRemote = this.m_ubCmdPlugin.getBroker(this.m_brokerName);
            }
            if (this.reqUserName && (char1 == 'x' || char1 == 'e')) {
                if (!new acctAuthenticate().verifyUserJNI(svcStartArgsPkt.m_username, svcStartArgsPkt.m_password)) {
                    this.m_logFile.LogMsgln(this.m_logDest, 1, true, "Failed to verify user " + svcStartArgsPkt.m_username + "'s password.");
                    UBRemoteCommand.argList = new Object[] { svcStartArgsPkt.m_username };
                    this.m_error = UBToolsMsg.getMsg(7094295313015382128L, UBRemoteCommand.argList);
                    this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_error);
                    return -1;
                }
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, "Password of user " + svcStartArgsPkt.m_username + " was successfully verified.");
            }
            switch (char1) {
                case 120: {
                    if (this.m_ubRemote != null && this.pingBroker(this.m_ubRemote)) {
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382149L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_error);
                        return -1;
                    }
                    if (ubPersonStrForSvcName == null) {
                        UBRemoteCommand.argList = new Object[] { str };
                        this.m_theMessage = UBToolsMsg.getMsg(7094295313015382150L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
                        return -1;
                    }
                    if (!ubControlCmd.startService()) {
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382121L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_error);
                        break;
                    }
                    this.m_ubRemote = this.m_ubCmdPlugin.connectToBroker(string, 10);
                    if (this.m_ubRemote != null) {
                        this.m_ubCmdPlugin.addBroker(this.m_brokerName, this.m_ubRemote);
                        n = 0;
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_output = UBToolsMsg.getMsg(7094295313015382120L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_output);
                        break;
                    }
                    UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                    this.m_error = UBToolsMsg.getMsg(7094295313015382121L, UBRemoteCommand.argList);
                    this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_error);
                    break;
                }
                case 101: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_error);
                        return -1;
                    }
                    UBRemoteCommand.argList = new Object[] { this.m_userName, str, brokerName };
                    this.m_theMessage = UBToolsMsg.getMsg(7094295313015382152L, UBRemoteCommand.argList);
                    this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
                    try {
                        if (ubControlCmd.stopService(ubPersonStrForSvcName.getfullPropSpec())) {
                            UBRemoteCommand.argList = new Object[] { this.m_userName };
                            this.m_theMessage = UBToolsMsg.getMsg(7094295313015382153L, UBRemoteCommand.argList);
                            this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
                            this.m_ubCmdPlugin.removeBroker(this.m_brokerName);
                            UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                            this.m_output = UBToolsMsg.getMsg(7094295313015382101L, UBRemoteCommand.argList);
                            n = 0;
                        }
                        else {
                            UBRemoteCommand.argList = new Object[] { brokerName };
                            this.m_error = UBToolsMsg.getMsg(7094295313015382103L, UBRemoteCommand.argList);
                            this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_error);
                        }
                    }
                    catch (Exception ex2) {
                        UBRemoteCommand.argList = new Object[] { brokerName };
                        this.m_theMessage = UBToolsMsg.getMsg(7094295313015382136L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_theMessage);
                    }
                    break;
                }
                case 107: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_error);
                        return -1;
                    }
                    try {
                        if (ubControlCmd.StopServiceImmediately(ubPersonStrForSvcName.getfullPropSpec()) == 0) {
                            this.m_ubCmdPlugin.removeBroker(this.m_brokerName);
                            UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                            this.m_output = UBToolsMsg.getMsg(7094295313015382264L, UBRemoteCommand.argList);
                            this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_output);
                            n = 0;
                        }
                        else {
                            UBRemoteCommand.argList = new Object[] { brokerName };
                            this.m_error = UBToolsMsg.getMsg(7094295313015382260L, UBRemoteCommand.argList);
                            this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_error);
                        }
                    }
                    catch (Exception ex3) {
                        UBRemoteCommand.argList = new Object[] { brokerName };
                        this.m_theMessage = UBToolsMsg.getMsg(7094295313015382136L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 1, true, this.m_theMessage);
                    }
                    break;
                }
                case 115: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_error);
                        return -1;
                    }
                    try {
                        UBRemoteCommand.argList = new Object[] { this.m_userName };
                        this.m_theMessage = UBToolsMsg.getMsg(7094295313015382155L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
                        final Integer n2 = new Integer(array[2]);
                        if ((n = ((IUBRemote)this.m_ubRemote).startServers(n2)) == 0) {
                            UBRemoteCommand.argList = new Object[] { n2.toString() };
                            this.m_output = UBToolsMsg.getMsg(7094295313015382156L, UBRemoteCommand.argList);
                            this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_output);
                        }
                        else {
                            UBRemoteCommand.argList = new Object[] { "", this.m_brokerName };
                            this.m_error = UBToolsMsg.getMsg(7094295313015382154L, UBRemoteCommand.argList);
                        }
                    }
                    catch (RemoteException ex4) {
                        UBRemoteCommand.argList = new Object[] { "", this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382154L, UBRemoteCommand.argList);
                    }
                    catch (NumberFormatException ex5) {
                        UBRemoteCommand.argList = new Object[] { array[2] };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382261L, UBRemoteCommand.argList);
                    }
                    break;
                }
                case 116: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_error);
                        return -1;
                    }
                    try {
                        final Integer n3 = new Integer(array[2]);
                        final int intValue = n3;
                        UBRemoteCommand.argList = new Object[] { this.m_userName };
                        this.m_theMessage = UBToolsMsg.getMsg(7094295313015382158L, UBRemoteCommand.argList);
                        this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
                        if ((n = ((IUBRemote)this.m_ubRemote).trimBy(intValue)) == 0) {
                            UBRemoteCommand.argList = new Object[] { n3.toString() };
                            this.m_output = UBToolsMsg.getMsg(7094295313015382159L, UBRemoteCommand.argList);
                            this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_output);
                        }
                        else {
                            UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                            this.m_error = UBToolsMsg.getMsg(7094295313015382262L, UBRemoteCommand.argList);
                        }
                    }
                    catch (RemoteException ex6) {
                        UBRemoteCommand.argList = new Object[] { this.m_brokerName };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382262L, UBRemoteCommand.argList);
                    }
                    catch (NumberFormatException ex7) {
                        UBRemoteCommand.argList = new Object[] { array[2] };
                        this.m_error = UBToolsMsg.getMsg(7094295313015382261L, UBRemoteCommand.argList);
                    }
                    break;
                }
                case 113: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { str + ": " + this.m_brokerName };
                        this.m_output = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        return 0;
                    }
                    final StringBuffer sb = new StringBuffer(1024);
                    sb.append("\n");
                    sb.append(this.m_ubRemote.getStatusFormatted(0));
                    sb.append("\n");
                    sb.append(this.m_ubRemote.getStatusFormatted(1));
                    this.m_output = sb.toString();
                    n = 0;
                    break;
                }
                case 108: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { str + ": " + this.m_brokerName };
                        this.m_output = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        return 0;
                    }
                    this.m_structuredOutput = this.m_ubRemote.getStatusStructured(0, null);
                    n = 0;
                    break;
                }
                case 99: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { str + ": " + this.m_brokerName };
                        this.m_output = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        return 0;
                    }
                    this.m_structuredOutput = this.m_ubRemote.getStatusStructured(1, new Integer(array[2]));
                    n = 0;
                    break;
                }
                case 122: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { str + ": " + this.m_brokerName };
                        this.m_output = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        return 0;
                    }
                    this.m_structuredOutput = this.m_ubRemote.getStatusStructured(3, new String(array[2]));
                    n = 0;
                    break;
                }
                case 121: {
                    if (this.m_ubRemote == null) {
                        UBRemoteCommand.argList = new Object[] { str + ": " + this.m_brokerName };
                        this.m_output = UBToolsMsg.getMsg(7094295313015382137L, UBRemoteCommand.argList);
                        return 0;
                    }
                    this.m_structuredOutput = this.m_ubRemote.getStatusStructured(4, null);
                    n = 0;
                    break;
                }
                default: {
                    n = 2;
                    this.m_error = UBToolsMsg.getMsg(7094295313015382263L);
                    break;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return n;
    }
    
    public String getSystemOutput() throws RemoteException {
        return this.m_output;
    }
    
    public Hashtable getStructuredSystemOutput() throws RemoteException {
        return this.m_structuredOutput;
    }
    
    public String getSystemError() throws RemoteException {
        return this.m_error;
    }
    
    private Vector getBrokerNames() {
        final Vector<String> vector = new Vector<String>();
        String[] personalities = null;
        try {
            personalities = this.m_pmpObject.getPersonalities();
        }
        catch (PropMgrUtils.CantGetParentGroup cantGetParentGroup) {
            System.err.println(cantGetParentGroup.getMessage());
        }
        catch (PropMgrUtils.CantGetPropCollection collection) {
            System.err.println(collection.getMessage());
        }
        for (int i = 0; i < personalities.length; ++i) {
            try {
                final String s = personalities[i];
                String[] svcNameForParentGrp = null;
                try {
                    svcNameForParentGrp = this.m_pmpObject.getSvcNameForParentGrp(s);
                }
                catch (Exception ex2) {
                    UBRemoteCommand.argList = new Object[] { personalities[i] };
                    this.m_theMessage = UBToolsMsg.getMsg(7094295313015382124L, UBRemoteCommand.argList);
                    this.m_logFile.LogMsgln(this.m_logDest, 2, true, this.m_theMessage);
                }
                if (svcNameForParentGrp != null) {
                    for (int j = 0; j < svcNameForParentGrp.length; ++j) {
                        vector.addElement(svcNameForParentGrp[j]);
                    }
                }
            }
            catch (Exception ex) {
                this.m_logFile.LogMsgln(this.m_logDest, 1, true, ex.getMessage());
            }
        }
        return vector;
    }
    
    private boolean pingBroker(final IAdminRemote adminRemote) {
        boolean b = true;
        try {
            adminRemote.ping();
        }
        catch (RemoteException ex) {
            b = false;
        }
        return b;
    }
    
    public void postStartServiceEvent(final String s, final String s2) {
        try {
            AbstractGuiPlugin.getEventBroker().postEvent(new EStartServiceEvent(AbstractUbrokerPlugin.getIAdministrationServer(), s, s2));
            UBToolsMsg.logMsg(5, "StartServiceEvent for " + s + " is posted...");
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post startService event for " + s + "(" + ex.toString() + ")");
        }
    }
    
    public void postStopServiceEvent(final String s, final String s2) {
        try {
            AbstractGuiPlugin.getEventBroker().postEvent(new EStopServiceEvent(AbstractUbrokerPlugin.getIAdministrationServer(), s, s2));
            UBToolsMsg.logMsg(5, "StopServiceEvent for " + s + " is posted...");
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post stopService event for " + s + "(" + ex.toString() + ")");
        }
    }
}
