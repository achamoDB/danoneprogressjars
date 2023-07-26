// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import com.progress.common.event.LogEvent;
import com.progress.ubroker.management.events.EUbrokerLogFileNameChanged;
import com.progress.common.property.ERenegadePropertyFileChange;
import com.progress.common.networkevents.EventListener;
import java.util.Vector;
import java.io.Serializable;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.progress.chimera.adminserver.IAdminServerConnection;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.RMISecurityManager;
import com.progress.ubroker.management.events.ENSReregisteredBrokerEvent;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import com.progress.ubroker.management.events.ENSBrokerRegistrationFailureEvent;
import com.progress.ubroker.management.events.ENSClientRequestRejectedEvent;
import com.progress.ubroker.management.events.ENSDuplicateBrokerUUIDEvent;
import com.progress.ubroker.management.events.ENSBrokerTimeoutEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.management.events.ENSAppServiceNotFoundEvent;
import com.progress.ubroker.management.events.COpenEdgeManagementContent;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Date;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.rmi.RemoteException;
import com.progress.nameserver.util.MsgUtil;
import java.io.IOException;
import java.io.InterruptedIOException;
import com.progress.ubroker.util.IWatchable;
import com.progress.nameserver.util.nsRMIWatchDog;
import java.io.OutputStream;
import java.io.InputStream;
import com.progress.nameserver.util.NetSocket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import com.progress.common.licensemgr.LicenseMgr;
import com.progress.common.exception.ProException;
import com.progress.common.event.ExceptionEvent;
import com.progress.common.event.ProEvent;
import com.progress.ubroker.util.PropFilename;
import com.progress.common.util.PropertyFilter;
import com.progress.common.util.InstallPath;
import com.progress.common.util.Getopt;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsFile;
import com.progress.common.ehnlog.ILogEvntHandler;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import com.progress.ubroker.util.ubWatchDog;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.property.PropertyManager;
import com.progress.common.event.IEventListener;
import com.progress.common.event.EventManager;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Hashtable;
import com.progress.ubroker.management.IRemoteManagerConst;
import com.progress.ubroker.management.IRemoteManager;

public final class NameServer extends Thread implements INSRemote, IRemoteManager, IRemoteManagerConst
{
    private static Hashtable asTable;
    private static Hashtable brokerTable;
    private static DatagramSocket ds;
    private static DatagramPacket dgIn;
    private static DatagramPacket dgOut;
    private static ByteArrayInputStream dataIn;
    private static DataInputStream dataStreamIn;
    private static ByteArrayOutputStream dataOut;
    private static DataOutputStream dataStreamOut;
    private static TimeoutChecker to;
    private static NameServer ns;
    private int timeoutScan;
    private static boolean shutdown;
    private static String nsURL;
    private int nsPort;
    private static boolean verbose;
    private static long startTime;
    private String nameServerName;
    private String nameServerHost;
    private static boolean loadBalEnabled;
    private static EventManager eventManager;
    private static IEventListener logManager;
    private static NeighborNameServer[] neighbor;
    private static PropertyManager propMgr;
    private static IEventInterestObject m_propChangeInterestObj;
    private static PropChangeListener m_propChangeListener;
    private static EventBroker propertiesEventBroker;
    private static ubWatchDog rmiWatchDog;
    private static IEventBroker adminServerEventBroker;
    private static IEventStream nsEventStream;
    private RemoteStub nsStub;
    private long cntClientCxRequests;
    private long cntClientCxRequestFailures;
    private Object statsSyncObject;
    private static String canonicalName;
    private static ILogEvntHandler event_handler;
    private static int restartNSport;
    
    public static void main(final String[] array) throws Throwable {
        int i = 0;
        String optArg = null;
        String optArg2 = null;
        int n = -1;
        String optArg3 = null;
        int n2 = -1;
        String str = null;
        int n3 = 0;
        String s = null;
        int intProperty = 2;
        int intProperty2 = 0;
        long longProperty = 0L;
        String property = new String("");
        boolean b = true;
        boolean b2 = false;
        ExceptionMessageAdapter.setMessageSubsystem(new PromsgsFile());
        NameServer.eventManager = new EventManager();
        NameServer.startTime = System.currentTimeMillis();
        NameServer.canonicalName = System.getProperty("CanonicalName");
        try {
            Getopt getopt;
            int n4;
            for (getopt = new Getopt(array), n4 = getopt.getOpt("i:r:m:p:k:f:vl:d"); n4 != -1 && n4 != 63; n4 = getopt.getOpt("i:r:m:p:k:f:vl:d")) {
                switch (n4) {
                    case 100: {
                        b2 = true;
                        break;
                    }
                    case 105: {
                        optArg = getopt.getOptArg();
                        break;
                    }
                    case 114: {
                        NameServer.verbose = false;
                        optArg2 = getopt.getOptArg();
                        break;
                    }
                    case 109: {
                        optArg3 = getopt.getOptArg();
                        break;
                    }
                    case 112: {
                        n = Integer.parseInt(getopt.getOptArg());
                        break;
                    }
                    case 107: {
                        n2 = Integer.parseInt(getopt.getOptArg());
                        break;
                    }
                    case 102: {
                        str = getopt.getOptArg();
                        break;
                    }
                    case 118: {
                        n3 = 1;
                        break;
                    }
                    case 108: {
                        s = getopt.getOptArg();
                        break;
                    }
                    default: {
                        throw new NameServerInitException();
                    }
                }
            }
            if (n3 == 1) {
                NameServer.verbose = true;
            }
            if (NameServer.verbose) {
                final LogManager logManager = new LogManager(4);
                NameServer.eventManager.expressInterest(LogEvent.class, logManager);
                NameServer.eventManager.unhandledEvents(logManager);
            }
            if (n4 == 63) {
                throw new InvalidStartupParamException();
            }
            String installPath = new InstallPath().getInstallPath();
            if (installPath != null && installPath.equals("")) {
                installPath = null;
            }
            if (optArg != null && (installPath != null || str != null)) {
                NameServer.propertiesEventBroker = new EventBroker();
                NameServer.propMgr = new PropertyManager(NameServer.propertiesEventBroker);
                if (installPath != null) {
                    NameServer.propMgr.setGetPropertyFilter(new PropertyFilter(NameServer.propMgr));
                }
                if (str == null) {
                    final PropFilename propFilename = new PropFilename();
                    str = PropFilename.getFullPath();
                }
                if (NameServer.verbose) {
                    NameServer.eventManager.post(new LoadingNameServerEvent(optArg, str));
                }
                try {
                    NameServer.propMgr.load(str);
                }
                catch (Exception ex) {
                    NameServer.eventManager.post(new ExceptionEvent(NameServer.eventManager, ex));
                    NameServer.eventManager.post(new LoadingNameServerExceptionEvent());
                    i = 1;
                }
                if (i == 0) {
                    try {
                        setNameServerGroup(NameServer.propMgr, optArg, str);
                    }
                    catch (NameServerNotFoundException ex2) {
                        i = 1;
                    }
                }
                if (i == 0) {
                    final String property2 = NameServer.propMgr.getProperty("location");
                    if (property2 == null || !property2.equals("local")) {
                        NameServer.eventManager.post(new NameServerNotLocalExceptionEvent(optArg, str));
                        i = 1;
                    }
                }
                if (i == 0) {
                    if (s == null) {
                        s = NameServer.propMgr.getProperty("srvrLogFile");
                    }
                    intProperty = NameServer.propMgr.getIntProperty("loggingLevel", 2);
                    b = (NameServer.propMgr.getIntProperty("logAppend", 1) == 1);
                    property = NameServer.propMgr.getProperty("logEntryTypes");
                    if (property == null) {
                        property = new String("NSPlumbing");
                    }
                    longProperty = NameServer.propMgr.getLongProperty("logThreshold", 0L);
                    intProperty2 = NameServer.propMgr.getIntProperty("numLogFiles", 0);
                    if (optArg3 == null) {
                        final String property3 = NameServer.propMgr.getProperty("multiCastGroup");
                        if (property3 != null) {
                            optArg3 = property3;
                        }
                    }
                    if (n == -1 || n == 0) {
                        n = NameServer.propMgr.getIntProperty("portNumber", 5162);
                    }
                    if (n2 == -1 || n2 == 0) {
                        n2 = NameServer.propMgr.getIntProperty("brokerKeepAliveTimeout", 30);
                    }
                    if (NameServer.propMgr.getIntProperty("allowRuntimeUpdates", 0) != 0) {
                        NameServer.propMgr.startPropertyFileMonitor();
                    }
                    final String[] arrayProperty = NameServer.propMgr.getArrayProperty("neighborNameServers");
                    if (arrayProperty != null && arrayProperty.length > 0 && !arrayProperty[0].equals("")) {
                        NameServer.neighbor = new NeighborNameServer[arrayProperty.length];
                        for (int j = 0; j < arrayProperty.length; ++j) {
                            try {
                                setNameServerGroup(NameServer.propMgr, arrayProperty[j], str);
                            }
                            catch (NameServerNotFoundException ex3) {
                                i = 1;
                                break;
                            }
                            NameServer.neighbor[j] = new NeighborNameServer(NameServer.propMgr.getProperty("hostName"), NameServer.propMgr.getIntProperty("portNumber"));
                        }
                    }
                }
            }
            else {
                if (n == -1 || n == 0) {
                    n = 5162;
                }
                if (n2 == -1 || n2 == 0) {
                    n2 = 30;
                }
            }
            if (i == 0 && s != null && !s.equals("")) {
                try {
                    NameServer.logManager = new LogManager(s, intProperty, b, property, longProperty, intProperty2);
                    NameServer.eventManager.expressInterest(LogEvent.class, NameServer.logManager);
                    NameServer.eventManager.unhandledEvents(NameServer.logManager);
                }
                catch (ProException ex4) {
                    NameServer.eventManager.post(new LogFileInitExceptionEvent(s));
                    i = 1;
                }
            }
            if (i == 0 && !b2) {
                LicenseMgr licenseMgr = null;
                try {
                    licenseMgr = new LicenseMgr();
                    licenseMgr.checkR2Run(4718592);
                }
                catch (LicenseMgr.CannotContactLicenseMgr cannotContactLicenseMgr) {
                    NameServer.eventManager.post(new ExceptionEvent(NameServer.eventManager, cannotContactLicenseMgr));
                    i = 1;
                }
                catch (LicenseMgr.NotLicensed notLicensed) {
                    NameServer.eventManager.post(new NotLicensedExceptionEvent());
                    i = 1;
                }
                if (i == 0) {
                    try {
                        licenseMgr.checkR2Run(720896);
                        NameServer.loadBalEnabled = true;
                    }
                    catch (LicenseMgr.NotLicensed notLicensed2) {
                        NameServer.loadBalEnabled = false;
                    }
                }
            }
            else {
                NameServer.loadBalEnabled = true;
            }
            if (i == 0) {
                try {
                    String string = "Reading Ubroker Property File Name   : " + str;
                    NameServer.eventManager.post(new GeneralMsg(string));
                    if (i == 0) {
                        try {
                            setNameServerEnvGroup(NameServer.propMgr, optArg, str);
                        }
                        catch (NameServerNotFoundException ex5) {}
                    }
                    if (i == 0) {
                        final String property4 = NameServer.propMgr.getProperty("restartport");
                        if (property4 != null && property4.equals("1")) {
                            NameServer.restartNSport = 1;
                        }
                        else {
                            NameServer.restartNSport = 0;
                        }
                        if (NameServer.restartNSport == 1) {
                            string = "RestartPort Property Value :1";
                        }
                        else if (NameServer.restartNSport == 0) {
                            string = "RestartPort Property Value :0";
                        }
                        NameServer.eventManager.post(new GeneralMsg(string));
                    }
                    (NameServer.ns = new NameServer(optArg, optArg2, optArg3, n, n2)).start();
                    if (NameServer.adminServerEventBroker != null && longProperty > 0L && NameServer.logManager != null) {
                        NameServer.event_handler = new NSLogEvntHandler(NameServer.adminServerEventBroker, NameServer.eventManager, optArg, NameServer.canonicalName);
                        if (!((LogManager)NameServer.logManager).registerThresholdEventHandler(NameServer.event_handler)) {
                            NameServer.event_handler = null;
                        }
                    }
                }
                catch (NameServerInitException ex6) {
                    i = 1;
                }
            }
            if (i == 0) {
                if (optArg2 != null) {
                    while (i == 0) {
                        try {
                            NameServer.ns.join();
                            NameServer.ns = null;
                            Thread.sleep(2000L);
                            i = 1;
                        }
                        catch (Exception ex7) {}
                    }
                }
                else {
                    System.out.println("\n\nHit ENTER TO EXIT:\n\n");
                    Thread.currentThread().setPriority(4);
                    System.in.read();
                    NameServer.ns.shutDown();
                    NameServer.ns = null;
                }
            }
        }
        catch (Throwable t) {
            NameServer.eventManager.post(new UnexpectedExceptionEvent(t));
            NameServer.eventManager.post(new ExceptionEvent(NameServer.eventManager, t));
        }
        System.exit(0);
    }
    
    NameServer(final String s, final String s2, final String s3, final int nsPort, final int timeoutScan) throws NameServerInitException {
        this.nsStub = null;
        this.cntClientCxRequests = 0L;
        this.cntClientCxRequestFailures = 0L;
        this.statsSyncObject = new Object();
        if (s == null) {
            this.nameServerName = "";
        }
        else {
            this.setName(this.nameServerName = s);
        }
        try {
            this.nameServerHost = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception ex3) {
            this.nameServerHost = "Unknown";
        }
        this.nsPort = nsPort;
        (NameServer.to = new TimeoutChecker(timeoutScan)).start();
        this.timeoutScan = timeoutScan;
        try {
            NameServer.eventManager.post(new StartupEvent(this, s, s2, nsPort, timeoutScan));
            if (NameServer.loadBalEnabled) {
                NameServer.eventManager.post(new LoadBalancingEnabledEvent(this, s));
            }
            else {
                NameServer.eventManager.post(new LoadBalancingDisabledEvent(this, s));
            }
            if (s3 != null && !s3.equals("")) {
                if (NameServer.verbose) {
                    System.out.println("MultiCastGroup      = " + s3);
                }
                NameServer.eventManager.post(new MultiCastEnabledEvent(this, s3));
            }
            InetAddress byName = null;
            Label_0269: {
                if (s3 != null) {
                    try {
                        byName = InetAddress.getByName(s3);
                        break Label_0269;
                    }
                    catch (UnknownHostException ex4) {
                        NameServer.eventManager.post(new InvalidMultiCastGroupEvent(this, s3));
                        throw new HostUnknownException(s3);
                    }
                }
                byName = null;
                try {
                    (NameServer.ds = NetSocket.create_netSocket(byName, nsPort)).setSoTimeout(10000);
                }
                catch (Exception ex) {
                    NameServer.eventManager.post(new InvalidPortEvent(this, nsPort));
                    throw ex;
                }
            }
            NameServer.dgIn = new DatagramPacket(new byte[1000], 1000);
            NameServer.dataIn = new ByteArrayInputStream(NameServer.dgIn.getData());
            NameServer.dataStreamIn = new DataInputStream(NameServer.dataIn);
            NameServer.dataOut = new ByteArrayOutputStream();
            NameServer.dataStreamOut = new DataOutputStream(NameServer.dataOut);
            NameServer.dgOut = new DatagramPacket(NameServer.dataOut.toByteArray(), NameServer.dataOut.size(), byName, 0);
            if (s2 != null) {
                this.nsBind(s, s2);
                this.setFathomEventStream();
                if (NameServer.nsURL != null) {
                    (NameServer.rmiWatchDog = new ubWatchDog("RMIWatchDog", new nsRMIWatchDog(this, NameServer.nsURL, this.nameServerName, NameServer.eventManager), 60000L)).start();
                }
            }
            if (NameServer.propertiesEventBroker != null) {
                NameServer.m_propChangeListener = new PropChangeListener(NameServer.propMgr, s);
                NameServer.m_propChangeInterestObj = NameServer.propertiesEventBroker.expressInterest(ERenegadePropertyFileChange.class, NameServer.m_propChangeListener, NameServer.propertiesEventBroker.openEventStream("Event Stream for " + s));
            }
        }
        catch (Exception ex5) {
            final NameServerInitException ex2 = new NameServerInitException();
            NameServer.eventManager.post(new ExceptionEvent(this, ex2));
            throw ex2;
        }
    }
    
    public void run() {
        try {
            int n = 0;
            while (!NameServer.shutdown) {
                NameServer.dataIn.reset();
                NameServer.dgIn.setLength(1000);
                try {
                    NameServer.ds.receive(NameServer.dgIn);
                }
                catch (InterruptedIOException ex3) {
                    continue;
                }
                catch (IOException ex) {
                    if (NameServer.shutdown) {
                        continue;
                    }
                    if (n > 100) {
                        NameServer.eventManager.post(new TerminateIOExceptionEvent(this.nsPort));
                        String s = "";
                        if (NameServer.restartNSport == 0) {
                            s = "Check RestartPort Property value Before reopening the UDP port :0.";
                        }
                        else if (NameServer.restartNSport == 1) {
                            s = "Check RestartPort Property value Before reopening the UDP port :1.";
                        }
                        NameServer.eventManager.post(new GeneralMsg(s));
                        if (NameServer.restartNSport == 0) {
                            break;
                        }
                        NameServer.eventManager.post(new GeneralMsg("Start Re-Opening of NameServer Port ."));
                        if (!this.createNewDataGramSocket()) {
                            break;
                        }
                        n = 0;
                    }
                    else {
                        ++n;
                        NameServer.eventManager.post(new ListenIOExceptionEvent(this.nsPort));
                        NameServer.eventManager.post(new CaughtExceptionEvent(ex));
                        NameServer.eventManager.post(new IsClosedEvent(NameServer.ds.isClosed()));
                        try {
                            Thread.sleep(1000L);
                        }
                        catch (Exception ex4) {}
                    }
                    continue;
                }
                n = 0;
                if (!NameServer.shutdown) {
                    final int n2 = 0;
                    int n3 = 1;
                    MsgUtil.Header header = null;
                    try {
                        header = MsgUtil.readHeader(NameServer.dataStreamIn);
                    }
                    catch (IOException ex5) {
                        n3 = 0;
                        if (!this.isFromFathom(NameServer.dgIn)) {
                            NameServer.eventManager.post(new BadMessageExceptionEvent(NameServer.dgIn));
                            this.postClientRequestRejectedEvent(NameServer.dgIn);
                        }
                    }
                    catch (MsgUtil.InvalidMsgVersionException ex2) {
                        n3 = 0;
                        if (!this.isFromFathom(NameServer.dgIn)) {
                            NameServer.eventManager.post(new BadMessageExceptionEvent(NameServer.dgIn));
                            NameServer.eventManager.post(new ExceptionEvent(NameServer.eventManager, ex2));
                            this.postClientRequestRejectedEvent(NameServer.dgIn);
                        }
                    }
                    if (n3 != 1) {
                        continue;
                    }
                    switch (header.getMsgCode()) {
                        case 1: {
                            this.processRegister(header);
                            break;
                        }
                        case 2: {
                            this.processUnregister();
                            break;
                        }
                        case 3: {
                            this.processGetBroker(header);
                            break;
                        }
                        case 4: {
                            this.processGetStatus(header);
                            break;
                        }
                        default: {
                            NameServer.eventManager.post(new InvalidMessageCodeExceptionEvent(n2, NameServer.dgIn));
                            break;
                        }
                    }
                    if (!NameServer.verbose) {
                        continue;
                    }
                    System.out.println("\n\t\t\tApplication Service Table\n\n" + this.getStatus());
                }
            }
            System.out.println("Shutting down");
        }
        catch (Throwable t) {
            NameServer.eventManager.post(new UnexpectedExceptionEvent(t));
            NameServer.eventManager.post(new ExceptionEvent(this, t));
            NameServer.shutdown = true;
        }
    }
    
    void processRegister(final MsgUtil.Header header) throws MarshallFailureException {
        int int1 = 0;
        boolean boolean1 = false;
        String trim = null;
        String trim2 = null;
        String trim3 = null;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;
        String trim4 = null;
        String trim5 = null;
        String[] array = null;
        int n = 1;
        try {
            boolean1 = NameServer.dataStreamIn.readBoolean();
            int1 = NameServer.dataStreamIn.readInt();
            trim = NameServer.dataStreamIn.readUTF().trim();
            trim2 = NameServer.dataStreamIn.readUTF().trim();
            trim3 = NameServer.dataStreamIn.readUTF().trim();
            int2 = NameServer.dataStreamIn.readInt();
            int3 = NameServer.dataStreamIn.readInt();
            int4 = NameServer.dataStreamIn.readInt();
            trim4 = NameServer.dataStreamIn.readUTF().trim();
            trim5 = NameServer.dataStreamIn.readUTF().trim();
            array = new String[NameServer.dataStreamIn.readInt()];
            for (int i = 0; i < array.length; ++i) {
                array[i] = NameServer.dataStreamIn.readUTF();
            }
        }
        catch (IOException ex) {
            n = 0;
            NameServer.eventManager.post(new BadMessageExceptionEvent(NameServer.dgIn));
        }
        if (n == 1) {
            String trim6;
            try {
                trim6 = NameServer.dataStreamIn.readUTF().trim();
                if (trim6 != null && trim6.equals("")) {
                    trim6 = null;
                }
            }
            catch (IOException ex2) {
                trim6 = null;
            }
            this.register(header.getVersion(), boolean1, int1, trim, trim2, trim3, trim6, int2, int3, int4, trim4, trim5, array);
        }
    }
    
    void processUnregister() throws IOException {
        String trim = null;
        String trim2 = null;
        int n = 1;
        try {
            trim = NameServer.dataStreamIn.readUTF().trim();
            trim2 = NameServer.dataStreamIn.readUTF().trim();
        }
        catch (IOException ex) {
            n = 0;
            NameServer.eventManager.post(new BadMessageExceptionEvent(NameServer.dgIn));
        }
        if (n == 1) {
            synchronized (this.statsSyncObject) {
                this.unregister(trim, trim2);
            }
        }
    }
    
    private void ackBroker(final short n, final boolean b, final boolean v, final int v2) throws MarshallFailureException {
        NameServer.dataOut.reset();
        if (b) {
            NameServer.dgOut.setAddress(NameServer.dgIn.getAddress());
            NameServer.dgOut.setPort(NameServer.dgIn.getPort());
            try {
                MsgUtil.writeHeader(NameServer.dataStreamOut, n, 1);
                NameServer.dataStreamOut.writeInt(v2);
                NameServer.dataStreamOut.writeBoolean(v);
            }
            catch (IOException ex3) {
                final MarshallFailureException ex = new MarshallFailureException();
                NameServer.eventManager.post(new ExceptionEvent(this, ex));
                throw ex;
            }
            NameServer.dgOut.setData(NameServer.dataOut.toByteArray());
            NameServer.dgOut.setLength(NameServer.dataOut.size());
            NameServer.eventManager.post(new ResponseEvent(this, NameServer.dgOut));
            try {
                NameServer.ds.send(NameServer.dgOut);
            }
            catch (IOException ex2) {
                NameServer.eventManager.post(new BrokerSendExceptionEvent(NameServer.dgOut));
                NameServer.eventManager.post(new CaughtExceptionEvent(ex2));
            }
        }
    }
    
    void processGetBroker(final MsgUtil.Header header) throws MarshallFailureException {
        boolean b = true;
        AppService asObject = null;
        int int1;
        String trim;
        String trim2;
        int int2;
        boolean boolean1;
        String s;
        int i;
        try {
            int1 = NameServer.dataStreamIn.readInt();
            trim = NameServer.dataStreamIn.readUTF().trim();
            trim2 = NameServer.dataStreamIn.readUTF().trim();
            int2 = NameServer.dataStreamIn.readInt();
            boolean1 = NameServer.dataStreamIn.readBoolean();
            if (boolean1) {
                s = NameServer.dataStreamIn.readUTF().trim();
                i = NameServer.dataStreamIn.readInt();
            }
            else {
                final InetAddress address = NameServer.dgIn.getAddress();
                s = address.getHostAddress();
                if (address.getAddress()[0] == 127) {
                    s = InetAddress.getLocalHost().getHostAddress();
                }
                i = NameServer.dgIn.getPort();
            }
        }
        catch (IOException ex6) {
            NameServer.eventManager.post(new BadMessageExceptionEvent(NameServer.dgIn));
            return;
        }
        String s2;
        if (trim2.length() == 0) {
            s2 = trim + "." + "[Default]";
        }
        else {
            s2 = trim + "." + trim2;
        }
        NameServer.dataOut.reset();
        NameServer.eventManager.post(new GetBrokerEvent(this, NameServer.dgIn, s2));
        try {
            asObject = this.getASObject(s2);
        }
        catch (NoSuchAppServiceException ex7) {
            b = false;
        }
        NameServer.eventManager.post(new AppServiceFoundEvent(this, s2, b, int2));
        int n;
        if (!b && NameServer.neighbor != null && NameServer.neighbor.length > 0) {
            if (header.getRq() != 0 && header.getRq() == header.getRqExt()) {
                NameServer.eventManager.post(new MaxHopsReachedEvent(this, s2, s, i, header.getRq()));
                n = 1;
            }
            else {
                n = 0;
                if (boolean1) {
                    try {
                        if (header.getRq() == 0) {
                            MsgUtil.setRq(NameServer.dgIn.getData(), 10, 1);
                        }
                        else {
                            MsgUtil.setRqExt(NameServer.dgIn.getData(), header.getRqExt() + 1);
                        }
                    }
                    catch (IOException ex8) {
                        final MarshallFailureException ex = new MarshallFailureException();
                        NameServer.eventManager.post(new ExceptionEvent(this, ex));
                        throw ex;
                    }
                    NameServer.dgOut.setData(NameServer.dgIn.getData());
                    NameServer.dgOut.setLength(NameServer.dgIn.getLength());
                }
                else {
                    try {
                        MsgUtil.writeHeader(NameServer.dataStreamOut, header.getVersion(), 3);
                        NameServer.dataStreamOut.writeInt(int1);
                        NameServer.dataStreamOut.writeUTF(trim);
                        NameServer.dataStreamOut.writeUTF(trim2);
                        NameServer.dataStreamOut.writeInt(int2);
                        NameServer.dataStreamOut.writeBoolean(true);
                        NameServer.dataStreamOut.writeUTF(s);
                        NameServer.dataStreamOut.writeInt(i);
                    }
                    catch (IOException ex9) {
                        final MarshallFailureException ex2 = new MarshallFailureException();
                        NameServer.eventManager.post(new ExceptionEvent(this, ex2));
                        throw ex2;
                    }
                    NameServer.dgOut.setData(NameServer.dataOut.toByteArray());
                    NameServer.dgOut.setLength(NameServer.dataOut.size());
                }
                for (int j = 0; j < NameServer.neighbor.length; ++j) {
                    NameServer.dgOut.setAddress(NameServer.neighbor[j].getHost());
                    NameServer.dgOut.setPort(NameServer.neighbor[j].getPort());
                    NameServer.eventManager.post(new NeighborRequestEvent(this, s, i, s2, NameServer.dgOut));
                    try {
                        NameServer.ds.send(NameServer.dgOut);
                    }
                    catch (IOException ex3) {
                        NameServer.eventManager.post(new NeighborSendExceptionEvent(NameServer.dgOut));
                        NameServer.eventManager.post(new CaughtExceptionEvent(ex3));
                        n = 1;
                    }
                }
            }
        }
        else {
            n = 1;
        }
        if (n == 1) {
            boolean b2 = false;
            if (boolean1) {
                try {
                    NameServer.dgOut.setAddress(InetAddress.getByName(s));
                    NameServer.dgOut.setPort(i);
                }
                catch (UnknownHostException ex10) {
                    NameServer.eventManager.post(new ClientHostNotFoundExceptionEvent(s));
                    b2 = true;
                }
            }
            else {
                NameServer.dgOut.setAddress(NameServer.dgIn.getAddress());
                NameServer.dgOut.setPort(NameServer.dgIn.getPort());
            }
            if (!b2) {
                try {
                    MsgUtil.writeHeader(NameServer.dataStreamOut, header.getVersion(), 3);
                    NameServer.dataStreamOut.writeInt(int1);
                    if (b) {
                        NameServer.dataStreamOut.writeInt(int2);
                        for (int k = 0; k < int2; ++k) {
                            final Broker selectBroker = asObject.selectBroker();
                            NameServer.dataStreamOut.writeUTF(selectBroker.getUUID());
                            NameServer.dataStreamOut.writeUTF(selectBroker.getIP());
                            NameServer.dataStreamOut.writeInt(selectBroker.getPort());
                            NameServer.dataStreamOut.writeInt(selectBroker.getWeight());
                            if (k == 0) {
                                synchronized (this.statsSyncObject) {
                                    selectBroker.incrementClientCxCount(asObject.getApplicationService());
                                    asObject.incrementClientCxCount();
                                    ++this.cntClientCxRequests;
                                }
                            }
                        }
                    }
                    else {
                        NameServer.dataStreamOut.writeInt(0);
                        synchronized (this.statsSyncObject) {
                            final String string = "" + i;
                            ++this.cntClientCxRequests;
                            ++this.cntClientCxRequestFailures;
                            this.postAppServiceNotFoundEvent(s2, s, string);
                        }
                    }
                }
                catch (IOException ex11) {
                    final MarshallFailureException ex4 = new MarshallFailureException();
                    NameServer.eventManager.post(new ExceptionEvent(this, ex4));
                    throw ex4;
                }
                NameServer.dgOut.setData(NameServer.dataOut.toByteArray());
                NameServer.dgOut.setLength(NameServer.dataOut.size());
                NameServer.eventManager.post(new ResponseEvent(this, NameServer.dgOut));
                try {
                    NameServer.ds.send(NameServer.dgOut);
                }
                catch (IOException ex5) {
                    NameServer.eventManager.post(new ClientSendExceptionEvent(NameServer.dgOut));
                    NameServer.eventManager.post(new CaughtExceptionEvent(ex5));
                }
            }
        }
    }
    
    void processGetStatus(final MsgUtil.Header header) throws MarshallFailureException {
        int int1 = 0;
        String string = null;
        int n = 1;
        try {
            int1 = NameServer.dataStreamIn.readInt();
        }
        catch (IOException ex3) {
            NameServer.eventManager.post(new BadMessageExceptionEvent(NameServer.dgIn));
            n = 0;
        }
        if (n == 1) {
            NameServer.dataOut.reset();
            NameServer.eventManager.post(new GetStatusEvent(this, NameServer.dgIn));
            NameServer.dgOut.setAddress(NameServer.dgIn.getAddress());
            NameServer.dgOut.setPort(NameServer.dgIn.getPort());
            try {
                string = "\n" + this.getStatusFormatted(0) + "\n" + this.getStatusFormatted(1);
            }
            catch (RemoteException ex4) {}
            try {
                MsgUtil.writeHeader(NameServer.dataStreamOut, header.getVersion(), 4);
                NameServer.dataStreamOut.writeInt(int1);
                NameServer.dataStreamOut.writeUTF(string);
            }
            catch (IOException ex5) {
                final MarshallFailureException ex = new MarshallFailureException();
                NameServer.eventManager.post(new ExceptionEvent(this, ex));
                throw ex;
            }
            NameServer.dgOut.setData(NameServer.dataOut.toByteArray());
            NameServer.dgOut.setLength(NameServer.dataOut.size());
            try {
                NameServer.ds.send(NameServer.dgOut);
            }
            catch (IOException ex2) {
                NameServer.eventManager.post(new ClientSendExceptionEvent(NameServer.dgOut));
                NameServer.eventManager.post(new CaughtExceptionEvent(ex2));
            }
        }
    }
    
    void register(final short n, final boolean b, final int n2, final String str, final String s, final String s2, final String str2, final int i, int weight, int n3, final String str3, String string, final String[] array) throws MarshallFailureException {
        if (weight < 0) {
            weight = 0;
        }
        if (n3 < 0) {
            n3 = 30;
        }
        string = str3 + "." + string;
        final Broker broker = NameServer.brokerTable.get(str);
        if (broker == null) {
            if (str2 == null) {
                NameServer.eventManager.post(new RegisterEvent(this, str, string, s2, i));
            }
            else {
                NameServer.eventManager.post(new RegisterEvent(this, str, string, s2 + "/" + str2, i));
            }
            final AppService[] array2 = new AppService[array.length];
            final int n4 = (int)Math.ceil((n3 + 0.4 * this.timeoutScan) / this.timeoutScan);
            synchronized (this.statsSyncObject) {
                final Broker value = new Broker(str, s, s2, str2, i, weight, n4, n3, string, array2);
                NameServer.brokerTable.put(str, value);
                for (int j = 0; j < array.length; ++j) {
                    String str4;
                    if (array[j].length() == 0) {
                        str4 = str3 + "." + "[Default]";
                    }
                    else {
                        str4 = str3 + "." + array[j];
                    }
                    final AppService as = this.getAS(str4, !NameServer.loadBalEnabled);
                    if (as == null) {
                        NameServer.eventManager.post(new NoLoadBalancingEvent(this, value, str4));
                        this.unregister(str, s);
                        this.ackBroker(n, b, false, n2);
                        this.postBrokerRegistrationFailureEvent(str4 + "," + value.getName() + "," + value.getHost() + "," + value.getPort() + "," + str);
                        break;
                    }
                    NameServer.eventManager.post(new BrokerRegisteredEvent(this, value, str4));
                    if (value != null) {
                        value.initializeClientASCxCount(str4);
                    }
                    as.addElement(value);
                    array2[j] = as;
                }
            }
            this.ackBroker(n, b, true, n2);
        }
        else if (s2.equals(broker.getHost()) && i == broker.getPort() && string.equals(broker.getName())) {
            if (s.equals(broker.getInstanceID())) {
                final int weight2 = broker.getWeight();
                if (weight != weight2) {
                    broker.setWeight(weight);
                    NameServer.eventManager.post(new BrokerWeightChangedEvent(this, broker.getName(), weight2, weight));
                    for (int k = 0; k < array.length; ++k) {
                        String s3;
                        if (array[k].length() == 0) {
                            s3 = str3 + "." + "[Default]";
                        }
                        else {
                            s3 = str3 + "." + array[k];
                        }
                        AppService asObject;
                        try {
                            asObject = this.getASObject(s3);
                        }
                        catch (NoSuchAppServiceException ex) {
                            asObject = null;
                        }
                        if (asObject != null) {
                            asObject.setTotalWeight(asObject.getTotalWeight() - weight2 + weight);
                        }
                    }
                }
                broker.resetTimeoutFlag();
                NameServer.eventManager.post(new KnownBrokerEvent(this, broker));
                this.ackBroker(n, b, true, n2);
            }
            else {
                NameServer.eventManager.post(new ReregisterEvent(this, broker));
                synchronized (this.statsSyncObject) {
                    this.unregister(str, s);
                }
                this.register(n, b, n2, str, s, s2, str2, i, weight, n3, str3, string, array);
                this.postReregisteredBrokerEvent(broker.getName() + "," + broker.getHost() + "," + broker.getPort() + "," + str);
            }
        }
        else {
            NameServer.eventManager.post(new ErroneousUUIDExceptionEvent(this, str, broker.getName(), broker.getHost(), broker.getPort(), string, s2, i));
            this.postDuplicateBrokerUUIDEvent(broker.getName() + "," + broker.getHost() + "," + broker.getPort() + "," + string + "," + s2 + "," + i + "," + str);
            this.ackBroker(n, b, false, n2);
        }
    }
    
    void unregister(final String s, final String s2) {
        final Broker broker = NameServer.brokerTable.get(s);
        if (broker != null) {
            if (s2.equals(broker.getInstanceID())) {
                final Broker broker2 = NameServer.brokerTable.remove(s);
                if (broker2 != null) {
                    NameServer.eventManager.post(new UnregisterEvent(this, broker2));
                    final AppService[] appService = broker2.getAppService();
                    for (int i = 0; i < appService.length; ++i) {
                        if (appService[i] != null) {
                            appService[i].removeElement(broker2);
                        }
                    }
                }
            }
            else {
                NameServer.eventManager.post(new ErroneousUnregisterEvent(this, broker, s2));
            }
        }
        else {
            NameServer.eventManager.post(new UnknownUUIDUnregisterEvent(this, s));
        }
    }
    
    public int shutDown() throws RemoteException {
        NameServer.eventManager.post(new ShutDownEvent(this, this.nameServerName, NameServer.nsURL, this.nsPort));
        if (NameServer.nsURL != null) {
            try {
                if (NameServer.rmiWatchDog != null) {
                    NameServer.rmiWatchDog.setInterval(0L);
                }
                unbindService(NameServer.nsURL);
            }
            catch (Exception ex) {}
        }
        NameServer.shutdown = true;
        NameServer.to.shutDown();
        NameServer.to = null;
        try {
            this.join();
        }
        catch (InterruptedException ex2) {}
        NameServer.ds.close();
        NameServer.dgIn = null;
        NameServer.dgOut = null;
        NameServer.dataIn = null;
        NameServer.dataStreamIn = null;
        NameServer.dataOut = null;
        NameServer.dataStreamOut = null;
        NameServer.brokerTable = null;
        NameServer.asTable = null;
        return 0;
    }
    
    public int emergencyShutdown() throws RemoteException {
        return this.shutDown();
    }
    
    public String getStatusFormatted(final int n) throws RemoteException {
        if (n == 0) {
            return "NameServer " + this.nameServerName + " running on Host " + this.nameServerHost + " Port " + NameServer.ds.getLocalPort() + " Timeout " + this.timeoutScan + " seconds.";
        }
        if (n == 3) {
            return "ACTIVE";
        }
        return this.getStatus();
    }
    
    public Object[][] getSummaryStatus() throws RemoteException {
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.nameserver.NSBundle");
        return new Object[][] { { progressResources.getTranString("Name"), this.nameServerName }, { progressResources.getTranString("Port"), new Integer(this.nsPort) }, { progressResources.getTranString("Timeout"), new Integer(this.timeoutScan) }, { progressResources.getTranString("StartTime"), new Date(NameServer.startTime) }, { progressResources.getTranString("NumAppServices"), new Integer(NameServer.asTable.size()) }, { progressResources.getTranString("NumBrokers"), new Integer(NameServer.brokerTable.size()) } };
    }
    
    public AppService[] getDetailStatus() throws RemoteException {
        final AppService[] array = new AppService[NameServer.asTable.size()];
        if (NameServer.asTable.size() > 0) {
            int n = 0;
            final Enumeration<AppService> elements = (Enumeration<AppService>)NameServer.asTable.elements();
            while (elements.hasMoreElements()) {
                array[n] = elements.nextElement();
                ++n;
            }
        }
        return array;
    }
    
    public static String[] getSummaryLabels() {
        final String[] array = new String[5];
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.nameserver.NSBundle");
        array[0] = progressResources.getTranString("UUID");
        array[1] = progressResources.getTranString("Host");
        array[2] = progressResources.getTranString("Port");
        array[3] = progressResources.getTranString("Weight");
        array[4] = progressResources.getTranString("Timeout");
        return array;
    }
    
    public String getStatus() {
        if (NameServer.asTable.isEmpty()) {
            return "There are no registered Application Services for this NameServer";
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("Application Service\t\tUUID\t\tName\t\tHost\t\t Port\tWeight\tTimeout\n");
        final Enumeration<AppService> elements = NameServer.asTable.elements();
        while (elements.hasMoreElements()) {
            sb.append(elements.nextElement().toString());
        }
        return sb.toString();
    }
    
    public long ping() throws RemoteException {
        return System.currentTimeMillis() - NameServer.startTime;
    }
    
    AppService getASObject(final String key) throws NoSuchAppServiceException {
        final AppService appService = NameServer.asTable.get(key);
        if (appService == null) {
            throw new NoSuchAppServiceException(key);
        }
        return appService;
    }
    
    AppService getAS(final String s, final boolean b) {
        AppService value = NameServer.asTable.get(s);
        if (value == null) {
            value = new AppService(s);
            NameServer.asTable.put(s, value);
        }
        else if (b) {
            value = null;
        }
        return value;
    }
    
    void nsBind(final String s, final String nsURL) throws InvalidURLException, UnableToRegisterException {
        NameServer.nsURL = nsURL;
        try {
            this.nsStub = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, ex));
            final UnableToRegisterException ex2 = new UnableToRegisterException(s, nsURL);
            NameServer.eventManager.post(new ExceptionEvent(this, ex2));
            throw ex2;
        }
        int intProperty;
        int intProperty2;
        if (NameServer.propMgr != null) {
            NameServer.propMgr.setCurrentGroup("PreferenceRoot.Preference");
            intProperty = NameServer.propMgr.getIntProperty("admSrvrRegisteredRetry");
            intProperty2 = NameServer.propMgr.getIntProperty("admSrvrRegisteredRetryInterval");
        }
        else {
            intProperty = 6;
            intProperty2 = 3000;
        }
        int n = 0;
        for (int n2 = 0; n2 < intProperty && n == 0; ++n2) {
            try {
                rebindService(nsURL, this);
                n = 1;
            }
            catch (RemoteException ex5) {
                try {
                    Thread.sleep(intProperty2);
                }
                catch (Exception ex6) {}
            }
            catch (MalformedURLException ex7) {
                final InvalidURLException ex3 = new InvalidURLException(s, nsURL);
                NameServer.eventManager.post(new ExceptionEvent(this, ex3));
                throw ex3;
            }
        }
        if (n == 0) {
            final UnableToRegisterException ex4 = new UnableToRegisterException(s, nsURL);
            NameServer.eventManager.post(new ExceptionEvent(this, ex4));
            throw ex4;
        }
    }
    
    static void setNameServerGroup(final PropertyManager propertyManager, final String str, final String s) throws NameServerNotFoundException {
        final String string = "NameServer." + str;
        if (propertyManager.isKnownGroup(string)) {
            propertyManager.setCurrentGroup(string);
            return;
        }
        final NameServerNotFoundException ex = new NameServerNotFoundException(str, s);
        NameServer.eventManager.post(new ExceptionEvent(new Object(), ex));
        throw ex;
    }
    
    public void setFathomEventStream() throws RemoteException {
        if (NameServer.canonicalName != null && NameServer.canonicalName.length() > 0) {
            NameServer.adminServerEventBroker = this.getAdminServerEventBroker();
            if (NameServer.adminServerEventBroker != null) {
                NameServer.nsEventStream = NameServer.adminServerEventBroker.openEventStream("NameServer Event Stream");
            }
        }
    }
    
    RemoteStub evThis() {
        if (this.nsStub == null) {
            try {
                this.nsStub = UnicastRemoteObject.exportObject(this);
            }
            catch (RemoteException ex) {
                NameServer.eventManager.post(new ExceptionEvent(this, ex));
            }
        }
        return this.nsStub;
    }
    
    private void postAppServiceNotFoundEvent(final String str, final String str2, final String str3) {
        try {
            final ENSAppServiceNotFoundEvent ensAppServiceNotFoundEvent = new ENSAppServiceNotFoundEvent(this.evThis(), new COpenEdgeManagementContent(this.nameServerName, new Object[] { new String(this.nameServerHost), new String(this.nameServerName), new String("" + this.nsPort), new String(str + "," + str2 + "," + str3) }));
            ensAppServiceNotFoundEvent.setSource(NameServer.canonicalName);
            if (NameServer.adminServerEventBroker != null) {
                NameServer.adminServerEventBroker.postEvent(ensAppServiceNotFoundEvent);
            }
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new PostEventException("AppServiceNotFound")));
        }
    }
    
    private void postBrokerTimeoutEvent(final String original) {
        try {
            final ENSBrokerTimeoutEvent ensBrokerTimeoutEvent = new ENSBrokerTimeoutEvent(this.evThis(), new COpenEdgeManagementContent(this.nameServerName, new Object[] { new String(this.nameServerHost), new String(this.nameServerName), new String("" + this.nsPort), new String(original) }));
            ensBrokerTimeoutEvent.setSource(NameServer.canonicalName);
            if (NameServer.adminServerEventBroker != null) {
                NameServer.adminServerEventBroker.postEvent(ensBrokerTimeoutEvent);
            }
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new PostEventException("BrokerTimeout")));
        }
    }
    
    private void postDuplicateBrokerUUIDEvent(final String original) {
        try {
            final ENSDuplicateBrokerUUIDEvent ensDuplicateBrokerUUIDEvent = new ENSDuplicateBrokerUUIDEvent(this.evThis(), new COpenEdgeManagementContent(this.nameServerName, new Object[] { new String(this.nameServerHost), new String(this.nameServerName), new String("" + this.nsPort), new String(original) }));
            ensDuplicateBrokerUUIDEvent.setSource(NameServer.canonicalName);
            if (NameServer.adminServerEventBroker != null) {
                NameServer.adminServerEventBroker.postEvent(ensDuplicateBrokerUUIDEvent);
            }
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new PostEventException("DuplicateBrokerUUID")));
        }
    }
    
    private void postClientRequestRejectedEvent(final DatagramPacket datagramPacket) {
        try {
            String hostAddress = "";
            String string = "";
            if (datagramPacket != null) {
                hostAddress = datagramPacket.getAddress().getHostAddress();
                string = new Integer(datagramPacket.getPort()).toString();
            }
            final ENSClientRequestRejectedEvent ensClientRequestRejectedEvent = new ENSClientRequestRejectedEvent(this.evThis(), new COpenEdgeManagementContent(this.nameServerName, new Object[] { new String(this.nameServerHost), new String(this.nameServerName), new String("" + this.nsPort), new String(hostAddress + "," + string) }));
            ensClientRequestRejectedEvent.setSource(NameServer.canonicalName);
            if (NameServer.adminServerEventBroker != null) {
                NameServer.adminServerEventBroker.postEvent(ensClientRequestRejectedEvent);
            }
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new PostEventException("ClientRequestRejected")));
        }
    }
    
    private void postBrokerRegistrationFailureEvent(final String original) {
        try {
            final ENSBrokerRegistrationFailureEvent ensBrokerRegistrationFailureEvent = new ENSBrokerRegistrationFailureEvent(this.evThis(), new COpenEdgeManagementContent(this.nameServerName, new Object[] { new String(this.nameServerHost), new String(this.nameServerName), new String("" + this.nsPort), new String(original) }));
            ensBrokerRegistrationFailureEvent.setSource(NameServer.canonicalName);
            if (NameServer.adminServerEventBroker != null) {
                NameServer.adminServerEventBroker.postEvent(ensBrokerRegistrationFailureEvent);
            }
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new PostEventException("BrokerRegistrationFailure")));
        }
    }
    
    public static void rebindService(final String s, final Remote remote) throws MalformedURLException, RemoteException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        LocateRegistry.getRegistry(pscURLParser.getHost(), pscURLParser.getPort()).rebind(pscURLParser.getService(), remote);
    }
    
    private static void unbindService(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        LocateRegistry.getRegistry(pscURLParser.getHost(), pscURLParser.getPort()).unbind(pscURLParser.getService());
    }
    
    public Remote lookupService(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        final String host = pscURLParser.getHost();
        final int port = pscURLParser.getPort();
        final String service = pscURLParser.getService();
        final Registry registry = LocateRegistry.getRegistry(host, port);
        return (service == null) ? registry : registry.lookup(service);
    }
    
    private void postReregisteredBrokerEvent(final String original) {
        try {
            final ENSReregisteredBrokerEvent ensReregisteredBrokerEvent = new ENSReregisteredBrokerEvent(this.evThis(), new COpenEdgeManagementContent(this.nameServerName, new Object[] { new String(this.nameServerHost), new String(this.nameServerName), new String("" + this.nsPort), new String(original) }));
            ensReregisteredBrokerEvent.setSource(NameServer.canonicalName);
            if (NameServer.adminServerEventBroker != null) {
                NameServer.adminServerEventBroker.postEvent(ensReregisteredBrokerEvent);
            }
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new PostEventException("ReregisteredBroker")));
        }
    }
    
    public IEventBroker getAdminServerEventBroker() {
        final String url = this.createURL();
        IEventBroker eventBroker = null;
        try {
            System.setSecurityManager(new RMISecurityManager());
            eventBroker = ((IAdministrationServer)this.lookupService(url)).getEventBroker();
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new LookupBindingException(url)));
        }
        return eventBroker;
    }
    
    protected String createURL() {
        final String s = "rmi://";
        String substring = null;
        String substring2 = null;
        final int index;
        final int n;
        final int n2;
        if (NameServer.nsURL != null && NameServer.nsURL.length() > 0 && (index = NameServer.nsURL.indexOf(s)) >= 0 && (n = index + s.length()) < NameServer.nsURL.length() && (n2 = NameServer.nsURL.indexOf(":", n) + 1) > 0 && n2 < NameServer.nsURL.length()) {
            final int index2;
            if ((index2 = NameServer.nsURL.indexOf("/", n2)) > 0 && index2 < NameServer.nsURL.length()) {
                substring = NameServer.nsURL.substring(n, n2 - 1);
            }
            substring2 = NameServer.nsURL.substring(n2, index2);
            NameServer.nsURL.substring(index2, NameServer.nsURL.length());
        }
        final StringBuffer sb = new StringBuffer(64);
        sb.append(s);
        if (substring != null) {
            sb.append(substring);
        }
        else if (this.nameServerHost != null) {
            sb.append(this.nameServerHost);
        }
        else {
            sb.append("localhost");
        }
        sb.append(":");
        if (substring2 != null) {
            sb.append(substring2);
        }
        else {
            sb.append(20931);
        }
        sb.append("/");
        sb.append("Chimera");
        return sb.toString();
    }
    
    private boolean isFromFathom(final DatagramPacket datagramPacket) {
        boolean b = false;
        final String str = "Fathom UDP";
        try {
            final int length = datagramPacket.getLength();
            if (length >= str.length() && length < 256) {
                final String s = new String(datagramPacket.getData(), 0, str.length());
                if (s != null && s.indexOf(str) == 0) {
                    b = true;
                }
            }
        }
        catch (Exception ex) {
            b = false;
        }
        return b;
    }
    
    private Hashtable getDetailStatistics() {
        synchronized (this.statsSyncObject) {
            final Hashtable<String, String[][]> hashtable = new Hashtable<String, String[][]>();
            final String[][] value = { { new Long(this.cntClientCxRequests).toString(), new Long(this.cntClientCxRequestFailures).toString(), "0", "0" } };
            if (NameServer.asTable.size() > 0) {
                value[0][2] = new Integer(NameServer.asTable.size()).toString();
                value[0][3] = new Integer(NameServer.brokerTable.size()).toString();
                final String[][] value2 = new String[NameServer.asTable.size()][3];
                final Enumeration elements = NameServer.asTable.elements();
                for (int n = 0; elements.hasMoreElements() && n < value2.length; ++n) {
                    final AppService appService = elements.nextElement();
                    value2[n][0] = new String(appService.getApplicationService());
                    value2[n][1] = new Long(appService.getClientCxCount()).toString();
                    value2[n][2] = new Integer(appService.getBrokerListSize()).toString();
                    final String[][] value3 = new String[appService.getBrokerListSize()][8];
                    final Enumeration brokerList = appService.getBrokerList();
                    for (int n2 = 0; n2 < value3.length && brokerList.hasMoreElements(); ++n2) {
                        final Object[] array = new Object[Broker.getBrokerTableWidth()];
                        final Broker broker = brokerList.nextElement();
                        broker.getBroker(array);
                        value3[n2][0] = new Long(broker.getClientCxCount()).toString();
                        value3[n2][1] = new Long(broker.getClientCxCount(appService.getApplicationService())).toString();
                        value3[n2][2] = new String(array[0].toString());
                        value3[n2][3] = new String(array[1].toString());
                        value3[n2][4] = new String(array[2].toString());
                        value3[n2][5] = new String(array[3].toString());
                        value3[n2][6] = new String(array[4].toString());
                        value3[n2][7] = new String(array[5].toString());
                    }
                    if (value3 != null && value3.length > 0) {
                        hashtable.put(appService.getApplicationService(), value3);
                    }
                }
                if (value2 != null && value2.length > 0) {
                    hashtable.put("ActNSAppService", value2);
                }
            }
            hashtable.put("ActNSStat", value);
            return hashtable;
        }
    }
    
    private Hashtable getSummaryStatistics() {
        final Hashtable<String, String[]> hashtable = new Hashtable<String, String[]>();
        hashtable.put("Name", new String[] { this.nameServerName });
        hashtable.put("Port", new String[] { "" + this.nsPort });
        hashtable.put("Timout", new String[] { "" + this.timeoutScan });
        hashtable.put("Start Time", new String[] { "" + new Date(NameServer.startTime) });
        hashtable.put("Num AppServices", new String[] { "" + NameServer.asTable.size() });
        hashtable.put("Num Brokers", new String[] { "" + NameServer.brokerTable.size() });
        return hashtable;
    }
    
    private Hashtable getCurrLogFileName() {
        Hashtable<String, String> hashtable = null;
        if (NameServer.propMgr != null) {
            NameServer.propMgr.setCurrentGroup("NameServer." + this.nameServerName);
            final String property = NameServer.propMgr.getProperty("srvrLogFile");
            final long longProperty = NameServer.propMgr.getLongProperty("logThreshold", 0L);
            hashtable = new Hashtable<String, String>();
            if (longProperty > 0L && NameServer.logManager != null) {
                hashtable.put("srvrLogFile", ((LogManager)NameServer.logManager).getCurrentLogFileName());
            }
            else {
                hashtable.put("srvrLogFile", property);
            }
        }
        return hashtable;
    }
    
    private Hashtable list() {
        return this.list(null, null);
    }
    
    private Hashtable list(final String s) {
        return this.list(s, null);
    }
    
    private Hashtable list(final String regex, final String regex2) {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        final String string = "NameServer." + this.nameServerName;
        final PropertyFilter propertyFilter = new PropertyFilter(NameServer.propMgr);
        Hashtable properties;
        try {
            properties = NameServer.propMgr.properties(string, true, true).getProperties();
        }
        catch (PropertyManager.NoSuchGroupException ex) {
            return hashtable;
        }
        if (properties == null) {
            return hashtable;
        }
        final Enumeration keys = properties.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            final PropertyManager.Property property = properties.get(s.toLowerCase());
            String value = property.getValueOrDefault();
            if (value != null) {
                value = propertyFilter.filterValue(string, s, value);
            }
            try {
                if (regex == null && regex2 == null) {
                    hashtable.put(property.getName(), value);
                }
                else if (regex != null && property.getName().matches(regex)) {
                    hashtable.put(property.getName(), value);
                }
                else {
                    if (regex2 == null || !value.matches(regex2)) {
                        continue;
                    }
                    hashtable.put(property.getName(), value);
                }
            }
            catch (Exception ex2) {}
        }
        return hashtable;
    }
    
    public int invokeCommand(final int n, final Object[] array) throws RemoteException {
        int shutDown = 0;
        switch (n) {
            case 2: {
                shutDown = this.shutDown();
                break;
            }
        }
        return shutDown;
    }
    
    public Object getData(final String[] array) throws RemoteException {
        Object o = null;
        try {
            if (array != null) {
                if (array[0].equalsIgnoreCase("summaryStatus")) {
                    o = this.getSummaryStatistics();
                }
                else if (array[0].equalsIgnoreCase("detailStatus")) {
                    o = this.getDetailStatistics();
                }
                else if (array[0].equalsIgnoreCase("srvrLogFile")) {
                    o = this.getCurrLogFileName();
                }
            }
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new ExceptionEvent(this, new StatisticsException("GetStatisticsData")));
            return null;
        }
        return o;
    }
    
    public Hashtable getStatusStructured(final int n, final Object o) throws RemoteException {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        switch (n) {
            case 3: {
                hashtable = (Hashtable<String, String>)this.list((String)o);
                break;
            }
            case 4: {
                hashtable = (Hashtable<String, String>)this.list();
                break;
            }
            case 2: {
                hashtable.put("table name", "debug");
                break;
            }
            default: {
                hashtable.put("table name", "unknown");
                break;
            }
        }
        return hashtable;
    }
    
    boolean createNewDataGramSocket() {
        try {
            this.logNetStatOutput();
            NameServer.ds.close();
            NameServer.eventManager.post(new SocketClosingMsg(true));
        }
        catch (Exception ex) {
            NameServer.eventManager.post(new SocketClosingMsg(false));
            return false;
        }
        try {
            (NameServer.ds = NetSocket.create_netSocket(null, this.nsPort)).setSoTimeout(10000);
            NameServer.eventManager.post(new SocketReOpenMsg(true));
            this.logNetStatOutput();
            NameServer.eventManager.post(new GeneralMsg("Successfully ReOpen NameServer UDP port."));
            return true;
        }
        catch (Exception ex2) {
            NameServer.eventManager.post(new SocketReOpenMsg(false));
            return false;
        }
    }
    
    void logNetStatOutput() {
        try {
            final String string = Integer.toString(this.nsPort);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("netstat -aon").getInputStream()));
            for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                if (s.contains(string)) {
                    NameServer.eventManager.post(new NetstatOutMsg(s));
                }
            }
        }
        catch (IOException ex) {}
        catch (Exception ex2) {}
    }
    
    static void setNameServerEnvGroup(final PropertyManager propertyManager, final String str, final String s) throws NameServerNotFoundException {
        final String string = "Environment." + str;
        if (propertyManager.isKnownGroup(string)) {
            propertyManager.setCurrentGroup(string);
            return;
        }
        final NameServerNotFoundException ex = new NameServerNotFoundException(str, s);
        NameServer.eventManager.post(new ExceptionEvent(new Object(), ex));
        throw ex;
    }
    
    static {
        if (System.getProperty("os.name").startsWith("Windows")) {
            System.load(new InstallPath().fullyQualifyFile("ntjavamain.dll"));
        }
        NameServer.asTable = new Hashtable();
        NameServer.brokerTable = new Hashtable();
        NameServer.shutdown = false;
        NameServer.verbose = true;
        NameServer.neighbor = null;
        NameServer.propMgr = null;
        NameServer.m_propChangeInterestObj = null;
        NameServer.m_propChangeListener = null;
        NameServer.propertiesEventBroker = null;
        NameServer.rmiWatchDog = null;
        NameServer.adminServerEventBroker = null;
        NameServer.nsEventStream = null;
        NameServer.canonicalName = "";
        NameServer.event_handler = null;
        NameServer.restartNSport = 0;
    }
    
    static class Broker implements Serializable
    {
        private String uuid;
        private String instanceID;
        private String host;
        private String ip;
        private int port;
        private AppService[] appService;
        private int weight;
        private int timeoutCount;
        private int timeoutCheckLimit;
        private String brokerName;
        private int count;
        private int timeout;
        private long lastTime;
        private long clientCxCount;
        private Hashtable clientASCxCounts;
        
        Broker(final String uuid, final String instanceID, final String host, final String ip, final int port, final int weight, final int timeoutCheckLimit, final int timeout, final String brokerName, final AppService[] appService) {
            this.uuid = uuid;
            this.instanceID = instanceID;
            this.host = host;
            this.ip = ip;
            this.port = port;
            this.weight = weight;
            this.timeout = timeout;
            this.timeoutCheckLimit = timeoutCheckLimit;
            this.brokerName = brokerName;
            this.appService = appService;
            this.count = 1;
            this.lastTime = System.currentTimeMillis();
            this.clientCxCount = 0L;
            this.clientASCxCounts = new Hashtable();
        }
        
        String getUUID() {
            return this.uuid;
        }
        
        String getInstanceID() {
            return this.instanceID;
        }
        
        String getHost() {
            return this.host;
        }
        
        String getIP() {
            if (this.ip == null) {
                return this.host;
            }
            return this.ip;
        }
        
        int getPort() {
            return this.port;
        }
        
        int getWeight() {
            return this.weight;
        }
        
        String getName() {
            return this.brokerName;
        }
        
        AppService[] getAppService() {
            return this.appService;
        }
        
        int getTimeout() {
            return this.timeout;
        }
        
        long getLastTime() {
            return this.lastTime;
        }
        
        boolean checkTimeout() {
            return this.timeoutCount++ > this.timeoutCheckLimit;
        }
        
        void resetTimeoutFlag() {
            this.timeoutCount = 0;
            this.setLastTime();
        }
        
        int getCount() {
            return this.count++;
        }
        
        String getBrokerFullName() {
            String str;
            if (this.ip != null) {
                str = this.host + "/" + this.ip;
            }
            else {
                str = this.host;
            }
            return "\t" + this.uuid + "\t" + this.brokerName + "\t" + str + "\t" + this.port;
        }
        
        public String toString() {
            String str;
            if (this.ip != null) {
                str = this.host + "/" + this.ip;
            }
            else {
                str = this.host;
            }
            return "\t" + this.uuid + "\t" + this.brokerName + "\t" + str + "\t " + this.port + "\t" + this.weight + "\t" + this.timeout;
        }
        
        void getBroker(final Object[] array) {
            String s;
            if (this.ip != null) {
                s = this.host + "/" + this.ip;
            }
            else {
                s = this.host;
            }
            array[0] = this.uuid;
            array[1] = this.brokerName;
            array[2] = s;
            array[3] = new Integer(this.port);
            array[4] = new Integer(this.weight);
            array[5] = new Integer(this.timeout);
        }
        
        static int getBrokerTableWidth() {
            return 6;
        }
        
        public static String[] getBrokerTableLabels() {
            final String[] array = new String[6];
            final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.nameserver.NSBundle");
            array[0] = progressResources.getTranString("UUID");
            array[1] = progressResources.getTranString("Name");
            array[2] = progressResources.getTranString("Host");
            array[3] = progressResources.getTranString("Port");
            array[4] = progressResources.getTranString("Weight");
            array[5] = progressResources.getTranString("Timeout");
            return array;
        }
        
        private void setLastTime() {
            this.lastTime = System.currentTimeMillis();
        }
        
        void setWeight(final int weight) {
            this.weight = weight;
        }
        
        void setTimeout(final int timeout) {
            this.timeout = timeout;
        }
        
        void initializeClientASCxCount(final String key) {
            this.clientASCxCounts.put(key, new Long(0L));
        }
        
        void incrementClientCxCount(final String s) {
            if (s == null || s.length() <= 0 || this.clientASCxCounts.isEmpty()) {
                return;
            }
            final Long n = this.clientASCxCounts.get(s);
            if (n == null) {
                return;
            }
            this.clientASCxCounts.put(s, new Long(n + 1L));
            this.incrementClientCxCount();
        }
        
        void incrementClientCxCount() {
            ++this.clientCxCount;
        }
        
        long getClientCxCount(final String key) {
            if (key == null || key.length() <= 0 || this.clientASCxCounts.isEmpty()) {
                return 0L;
            }
            final Long n = this.clientASCxCounts.get(key);
            if (n == null) {
                return 0L;
            }
            return n;
        }
        
        long getClientCxCount() {
            return this.clientCxCount;
        }
    }
    
    public static class AppService implements Serializable
    {
        private String applicationService;
        private Vector brokerList;
        private int totalWeight;
        private long clientCxCount;
        
        AppService(final String applicationService) {
            this.applicationService = applicationService;
            this.brokerList = new Vector(2);
            this.clientCxCount = 0L;
        }
        
        void addElement(final Broker obj) {
            this.brokerList.addElement(obj);
            this.totalWeight += obj.getWeight();
        }
        
        void removeElement(final Broker o) {
            final int index = this.brokerList.indexOf(o);
            if (index >= 0) {
                this.totalWeight -= o.getWeight();
                this.brokerList.removeElementAt(index);
            }
            if (this.brokerList.isEmpty()) {
                final AppService appService = NameServer.asTable.remove(this.applicationService);
            }
        }
        
        Enumeration getBrokerList() {
            return this.brokerList.elements();
        }
        
        Broker selectBroker() {
            Broker broker;
            if (this.totalWeight == 0) {
                int index = (int)(Math.random() * this.brokerList.size());
                if (index == this.brokerList.size()) {
                    --index;
                }
                broker = this.brokerList.elementAt(index);
            }
            else {
                final double n = Math.random() * this.totalWeight;
                final Enumeration<Broker> elements = this.brokerList.elements();
                final int n2 = 0;
                broker = elements.nextElement();
                for (int n3 = n2 + broker.getWeight(); n3 < n; n3 += broker.getWeight()) {
                    broker = elements.nextElement();
                }
            }
            return broker;
        }
        
        void incrementClientCxCount() {
            ++this.clientCxCount;
        }
        
        long getClientCxCount() {
            return this.clientCxCount;
        }
        
        int getBrokerListSize() {
            return this.brokerList.size();
        }
        
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            final Enumeration brokerList = this.getBrokerList();
            while (brokerList.hasMoreElements()) {
                sb.append(brokerList.nextElement().toString() + "\n");
            }
            return "\n" + this.getApplicationService() + "\n" + sb.toString();
        }
        
        public String getApplicationService() {
            return this.applicationService;
        }
        
        int getTotalWeight() {
            return this.totalWeight;
        }
        
        public Object[][] getBrokerTable() {
            final Object[][] array = new Object[this.brokerList.size()][Broker.getBrokerTableWidth()];
            final Enumeration brokerList = this.getBrokerList();
            for (int i = 0; i < this.brokerList.size(); ++i) {
                brokerList.nextElement().getBroker(array[i]);
            }
            return array;
        }
        
        public static int getBrokerTableWidth() {
            return Broker.getBrokerTableWidth();
        }
        
        public static String[] getBrokerTableLabels() {
            return Broker.getBrokerTableLabels();
        }
        
        void setTotalWeight(final int totalWeight) {
            this.totalWeight = totalWeight;
        }
    }
    
    static class TimeoutChecker extends Thread
    {
        private boolean shutdown;
        private long timeout;
        
        public TimeoutChecker(final int n) {
            this.shutdown = false;
            this.timeout = n * 1000;
            this.setName("BrokerWatchDog");
        }
        
        public void run() {
            while (true) {
                final Enumeration<Broker> elements = NameServer.brokerTable.elements();
                while (elements.hasMoreElements()) {
                    final Broker broker = elements.nextElement();
                    if (broker.checkTimeout()) {
                        NameServer.eventManager.post(new TimeoutEvent(this, broker));
                        NameServer.ns.postBrokerTimeoutEvent(broker.getBrokerFullName());
                        synchronized (NameServer.ns.statsSyncObject) {
                            NameServer.ns.unregister(broker.getUUID(), broker.getInstanceID());
                        }
                    }
                }
                try {
                    Thread.sleep(this.timeout);
                }
                catch (InterruptedException ex) {
                    if (this.shutdown) {
                        break;
                    }
                    continue;
                }
            }
        }
        
        void shutDown() {
            this.shutdown = true;
            this.interrupt();
            try {
                this.join();
            }
            catch (Exception ex) {}
        }
        
        void setTimeout(final long timeout) {
            this.timeout = timeout;
        }
        
        static class TimeoutEvent extends NSLogEvent
        {
            public TimeoutEvent(final Object o, final Broker broker) {
                super(o, 2, "Broker %s has timed out. Timeout = %d Last Check = %s", new Object[] { broker.getBrokerFullName(), new Integer(broker.getTimeout()), new Date(broker.getLastTime()) });
            }
        }
    }
    
    static class NeighborNameServer
    {
        private InetAddress ia;
        private int port;
        
        NeighborNameServer(final String host, final int port) throws HostUnknownException {
            this.port = port;
            try {
                this.ia = InetAddress.getByName(host);
            }
            catch (UnknownHostException ex) {
                throw new HostUnknownException(host);
            }
        }
        
        int getPort() {
            return this.port;
        }
        
        InetAddress getHost() {
            return this.ia;
        }
    }
    
    class PropChangeListener extends EventListener
    {
        private static final String LOGGINGLEVEL = "loggingLevel";
        private static final String LOGENTRYTYPES = "logEntryTypes";
        private static final String KATIMEOUT = "brokerKeepAliveTimeout";
        PropertyManager m_props;
        PropertyManager m_newProps;
        LogManager m_log;
        String m_name;
        
        public PropChangeListener(final PropertyManager props, final String name) {
            this.m_props = null;
            this.m_newProps = new PropertyManager();
            this.m_log = (LogManager)NameServer.logManager;
            this.m_name = name;
            this.m_props = props;
            this.m_newProps.setGetPropertyFilter(new PropertyFilter(this.m_newProps));
        }
        
        public synchronized void processEvent(final IEventObject eventObject) {
            if (!(eventObject instanceof ERenegadePropertyFileChange)) {
                return;
            }
            final String propertyFileName = ((ERenegadePropertyFileChange)eventObject).getPropertyFileName();
            this.m_props.stopMonitors();
            try {
                this.m_newProps.load(propertyFileName);
                NameServer.setNameServerGroup(this.m_newProps, this.m_name, propertyFileName);
                NameServer.setNameServerGroup(this.m_props, this.m_name, propertyFileName);
                if (this.updateProperties()) {
                    NameServer.eventManager.post(new PropertyFileChangedEvent(this, propertyFileName));
                }
            }
            catch (Throwable t) {
                NameServer.eventManager.post(new PropertyFileChangedException(this, propertyFileName, t.getMessage()));
            }
            this.m_props.startPropertyFileMonitor();
        }
        
        private boolean updateProperties() {
            boolean b = false;
            final int intProperty = this.m_newProps.getIntProperty("loggingLevel");
            final int intProperty2 = this.m_props.getIntProperty("loggingLevel");
            if (intProperty != intProperty2) {
                try {
                    if (intProperty2 > intProperty) {
                        NameServer.eventManager.post(new PropertyChangedEvent(this, "loggingLevel", Integer.toString(intProperty2), Integer.toString(intProperty)));
                    }
                    this.m_props.putIntProperty("loggingLevel", intProperty);
                    this.m_log.setLoggingLevel(intProperty);
                    b = true;
                    if (intProperty2 < intProperty) {
                        NameServer.eventManager.post(new PropertyChangedEvent(this, "loggingLevel", Integer.toString(intProperty2), Integer.toString(intProperty)));
                    }
                }
                catch (Exception ex) {
                    NameServer.eventManager.post(new PropertyChangedException(this, "loggingLevel", ex.getMessage()));
                }
            }
            String property = this.m_newProps.getProperty("logEntryTypes");
            final String property2 = this.m_props.getProperty("logEntryTypes");
            if (property == null) {
                property = new String("NSPlumbing");
            }
            if (!property.equals(property2)) {
                try {
                    this.m_props.putProperty("logEntryTypes", property);
                    this.m_log.setLogEntries(property);
                    b = true;
                    NameServer.eventManager.post(new PropertyChangedEvent(this, "logEntryTypes", property2, property));
                }
                catch (Exception ex2) {
                    NameServer.eventManager.post(new PropertyChangedException(this, "logEntryTypes", ex2.getMessage()));
                }
            }
            final long n = this.m_newProps.getIntProperty("brokerKeepAliveTimeout");
            final long i = this.m_props.getIntProperty("brokerKeepAliveTimeout");
            if (n != i) {
                try {
                    this.m_props.putLongProperty("brokerKeepAliveTimeout", n);
                    NameServer.to.setTimeout(n);
                    b = true;
                    NameServer.eventManager.post(new PropertyChangedEvent(this, "brokerKeepAliveTimeout", Long.toString(i), Long.toString(n)));
                }
                catch (Exception ex3) {
                    NameServer.eventManager.post(new PropertyChangedException(this, "brokerKeepAliveTimeout", ex3.getMessage()));
                }
            }
            return b;
        }
    }
    
    static class PropertyChangedEvent extends NSLogEvent
    {
        public PropertyChangedEvent(final Object o, final String s, final String s2, final String s3) {
            super(o, 3, 7953638416913021774L, new Object[] { s, s2, s3 });
        }
    }
    
    static class PropertyChangedException extends NSLogEvent
    {
        public PropertyChangedException(final Object o, final String s, final String s2) {
            super(o, 3, 7953638416913021775L, new Object[] { s, s2 });
        }
    }
    
    static class PropertyFileChangedEvent extends NSLogEvent
    {
        public PropertyFileChangedEvent(final Object o, final String s) {
            super(o, 2, 7953638416913021776L, new Object[] { s });
        }
    }
    
    static class PropertyFileChangedException extends NSLogEvent
    {
        public PropertyFileChangedException(final Object o, final String s, final String s2) {
            super(o, 2, 7953638416913021777L, new Object[] { s, s2 });
        }
    }
    
    static class BrokerWeightChangedEvent extends NSLogEvent
    {
        public BrokerWeightChangedEvent(final Object o, final String s, final int value, final int value2) {
            super(o, 3, 7953638416913021787L, new Object[] { "priorityWeight", s, new Integer(value), new Integer(value2) });
        }
    }
    
    static class NoSuchAppServiceException extends ProException
    {
        public NoSuchAppServiceException(final String s) {
            super(7953638416913014599L, new Object[] { s });
        }
    }
    
    static class HostUnknownException extends ProException
    {
        public HostUnknownException(final String s) {
            super(7953638416913014600L, new Object[] { s });
        }
    }
    
    public static class UnableToRegisterException extends ProException
    {
        public UnableToRegisterException(final String s, final String s2) {
            super(7953638416913014783L, new Object[] { s, s2 });
        }
    }
    
    public static class InvalidURLException extends ProException
    {
        public InvalidURLException(final String s, final String s2) {
            super(7953638416913014784L, new Object[] { s, s2 });
        }
    }
    
    static class NameServerInitException extends ProException
    {
        public NameServerInitException() {
            super(7953638416913014785L, null);
        }
    }
    
    static class InvalidStartupParamException extends ProException
    {
        public InvalidStartupParamException() {
            super(7953638416913014786L, null);
        }
    }
    
    static class NameServerNotFoundException extends ProException
    {
        public NameServerNotFoundException(final String s, final String s2) {
            super(7953638416913014787L, new Object[] { s, s2 });
        }
    }
    
    static class MarshallFailureException extends ProException
    {
        public MarshallFailureException() {
            super(7953638416913015097L, null);
        }
    }
    
    static class NeighborSendFailureException extends ProException
    {
        public NeighborSendFailureException(final DatagramPacket datagramPacket, final Throwable t) {
            super(7953638416913015098L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()), t.getMessage() });
        }
    }
    
    static class ClientSendFailureException extends ProException
    {
        public ClientSendFailureException(final DatagramPacket datagramPacket, final Throwable t) {
            super(7953638416913015141L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()), t.getMessage() });
        }
    }
    
    static class BrokerSendFailureException extends ProException
    {
        public BrokerSendFailureException(final DatagramPacket datagramPacket, final Throwable t) {
            super(7953638416913015145L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()), t.getMessage() });
        }
    }
    
    static class StartupEvent extends NSLogEvent
    {
        public StartupEvent(final Object o, final String s, final String s2, final int value, final int value2) {
            super(o, 2, 7953638416913014788L, new Object[] { (s == null) ? "" : s, (s2 == null) ? "" : s2, new Integer(value), new Integer(value2) });
        }
    }
    
    static class ShutDownEvent extends NSLogEvent
    {
        public ShutDownEvent(final Object o, final String s, final String s2, final int value) {
            super(o, 2, 7953638416913014789L, new Object[] { (s == null) ? "" : s, (s2 == null) ? "" : s2, new Integer(value) });
        }
    }
    
    static class LoadBalancingEnabledEvent extends NSLogEvent
    {
        public LoadBalancingEnabledEvent(final Object o, final String s) {
            super(o, 2, 7953638416913014791L, new Object[] { (s == null) ? "" : s });
        }
    }
    
    static class LoadBalancingDisabledEvent extends NSLogEvent
    {
        public LoadBalancingDisabledEvent(final Object o, final String s) {
            super(o, 2, 7953638416913014792L, new Object[] { (s == null) ? "" : s });
        }
    }
    
    static class GetBrokerEvent extends NSLogEvent
    {
        public GetBrokerEvent(final Object o, final DatagramPacket datagramPacket, final String s) {
            super(o, 3, 7953638416913014793L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()), s });
        }
    }
    
    static class ResponseEvent extends NSLogEvent
    {
        public ResponseEvent(final Object o, final DatagramPacket datagramPacket) {
            super(o, 3, 7953638416913014794L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()) });
        }
    }
    
    static class NeighborRequestEvent extends NSLogEvent
    {
        public NeighborRequestEvent(final Object o, final String s, final int value, final String s2, final DatagramPacket datagramPacket) {
            super(o, 3, 7953638416913015472L, new Object[] { s, new Integer(value), s2, datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()) });
        }
    }
    
    static class GetStatusEvent extends NSLogEvent
    {
        public GetStatusEvent(final Object o, final DatagramPacket datagramPacket) {
            super(o, 3, 7953638416913014796L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()) });
        }
    }
    
    static class RegisterEvent extends NSLogEvent
    {
        public RegisterEvent(final Object o, final String s, final String s2, final String s3, final int value) {
            super(o, 2, 7953638416913014797L, new Object[] { s, s2, s3, new Integer(value) });
        }
    }
    
    static class AppServiceFoundEvent extends NSLogEvent
    {
        public AppServiceFoundEvent(final Object o, final String s, final boolean value, final int value2) {
            super(o, 3, 7953638416913014798L, new Object[] { s, new Boolean(value), new Integer(value2) });
        }
    }
    
    static class MaxHopsReachedEvent extends NSLogEvent
    {
        public MaxHopsReachedEvent(final Object o, final String s, final String s2, final int value, final int value2) {
            super(o, 3, 7953638416913015470L, new Object[] { s, s2, new Integer(value), new Integer(value2) });
        }
    }
    
    static class BrokerRegisteredEvent extends NSLogEvent
    {
        public BrokerRegisteredEvent(final Object o, final Broker broker, final String s) {
            super(o, 2, 7953638416913014799L, new Object[] { broker.getUUID(), s });
        }
    }
    
    static class NoLoadBalancingEvent extends NSLogEvent
    {
        public NoLoadBalancingEvent(final Object o, final Broker broker, final String s) {
            super(o, 1, 7953638416913014800L, new Object[] { broker.getBrokerFullName(), s });
        }
    }
    
    static class KnownBrokerEvent extends NSLogEvent
    {
        public KnownBrokerEvent(final Object o, final Broker broker) {
            super(o, 3, 7953638416913014801L, new Object[] { broker.getBrokerFullName() });
        }
    }
    
    static class UnregisterEvent extends NSLogEvent
    {
        public UnregisterEvent(final Object o, final Broker broker) {
            super(o, 2, 7953638416913014802L, new Object[] { broker.getBrokerFullName() });
        }
    }
    
    static class ReregisterEvent extends NSLogEvent
    {
        public ReregisterEvent(final Object o, final Broker broker) {
            super(o, 2, 7953638416913014803L, new Object[] { broker.getBrokerFullName() });
        }
    }
    
    static class InvalidPortEvent extends NSLogEvent
    {
        public InvalidPortEvent(final Object o, final int value) {
            super(o, 1, 7953638416913014804L, new Object[] { new Integer(value) });
        }
    }
    
    static class LoadingNameServerEvent extends NSLogEvent
    {
        public LoadingNameServerEvent(final String s, final String s2) {
            super(new Object(), 2, 7953638416913014805L, new Object[] { s, s2 });
        }
    }
    
    static class LoadingNameServerExceptionEvent extends NSLogEvent
    {
        public LoadingNameServerExceptionEvent() {
            super(new Object(), 1, 7953638416913014806L, null);
        }
    }
    
    static class NameServerNotLocalExceptionEvent extends NSLogEvent
    {
        public NameServerNotLocalExceptionEvent(final String s, final String s2) {
            super(new Object(), 1, 7953638416913014807L, new Object[] { s, s2 });
        }
    }
    
    static class NotLicensedExceptionEvent extends NSLogEvent
    {
        public NotLicensedExceptionEvent() {
            super(new Object(), 1, 7953638416913014808L, null);
        }
    }
    
    static class UnexpectedExceptionEvent extends NSLogEvent
    {
        public UnexpectedExceptionEvent(final Throwable t) {
            super(new Object(), 1, 7953638416913014809L, new Object[] { t });
        }
    }
    
    static class BadMessageExceptionEvent extends NSLogEvent
    {
        public BadMessageExceptionEvent(final DatagramPacket datagramPacket) {
            super(new Object(), 1, 7953638416913015128L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()) });
        }
    }
    
    static class InvalidMessageCodeExceptionEvent extends NSLogEvent
    {
        public InvalidMessageCodeExceptionEvent(final int value, final DatagramPacket datagramPacket) {
            super(new Object(), 1, 7953638416913014810L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()), new Integer(value) });
        }
    }
    
    static class LogFileInitExceptionEvent extends NSLogEvent
    {
        public LogFileInitExceptionEvent(final String s) {
            super(new Object(), 1, 7953638416913014811L, new Object[] { s });
        }
    }
    
    static class ClientHostNotFoundExceptionEvent extends NSLogEvent
    {
        public ClientHostNotFoundExceptionEvent(final String s) {
            super(new Object(), 1, 7953638416913014812L, new Object[] { s });
        }
    }
    
    static class ErroneousUUIDExceptionEvent extends NSLogEvent
    {
        public ErroneousUUIDExceptionEvent(final Object o, final String s, final String s2, final String s3, final int value, final String s4, final String s5, final int value2) {
            super(o, 1, 7953638416913014813L, new Object[] { s, s4, s5, new Integer(value2), s2, s3, new Integer(value) });
        }
    }
    
    static class ErroneousUnregisterEvent extends NSLogEvent
    {
        public ErroneousUnregisterEvent(final Object o, final Broker broker, final String s) {
            super(o, 4, 7953638416913014814L, new Object[] { broker.getBrokerFullName(), broker.getInstanceID(), s });
        }
    }
    
    static class UnknownUUIDUnregisterEvent extends NSLogEvent
    {
        public UnknownUUIDUnregisterEvent(final Object o, final String s) {
            super(o, 4, 7953638416913014815L, new Object[] { s });
        }
    }
    
    static class ListenIOExceptionEvent extends NSLogEvent
    {
        public ListenIOExceptionEvent(final int value) {
            super(new Object(), 1, 7953638416913015954L, new Object[] { new Integer(value) });
        }
    }
    
    static class IsClosedEvent extends NSLogEvent
    {
        public IsClosedEvent(final boolean b) {
            super(new Object(), 2, "ds.isClosed() = " + b, null);
        }
    }
    
    static class SocketReOpenMsg extends NSLogEvent
    {
        public SocketReOpenMsg(final boolean b) {
            super(new Object(), 2, "Re-Open Or Create the DatagramSocket =" + b, null);
        }
    }
    
    static class SocketClosingMsg extends NSLogEvent
    {
        public SocketClosingMsg(final boolean b) {
            super(new Object(), 1, "Close the DatagramSocket For Error Condition =" + b, null);
        }
    }
    
    static class NetstatOutMsg extends NSLogEvent
    {
        public NetstatOutMsg(final String s) {
            super(new Object(), 2, s, null);
        }
    }
    
    static class GeneralMsg extends NSLogEvent
    {
        public GeneralMsg(final String s) {
            super(new Object(), 2, s, null);
        }
    }
    
    static class ClientSendExceptionEvent extends NSLogEvent
    {
        public ClientSendExceptionEvent(final DatagramPacket datagramPacket) {
            super(new Object(), 1, 7953638416913016000L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()) });
        }
    }
    
    static class BrokerSendExceptionEvent extends NSLogEvent
    {
        public BrokerSendExceptionEvent(final DatagramPacket datagramPacket) {
            super(new Object(), 1, 7953638416913016001L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()) });
        }
    }
    
    static class NeighborSendExceptionEvent extends NSLogEvent
    {
        public NeighborSendExceptionEvent(final DatagramPacket datagramPacket) {
            super(new Object(), 1, 7953638416913016002L, new Object[] { datagramPacket.getAddress().getHostAddress(), new Integer(datagramPacket.getPort()) });
        }
    }
    
    static class CaughtExceptionEvent extends NSLogEvent
    {
        public CaughtExceptionEvent(final Throwable t) {
            super(new Object(), 1, 7953638416913016003L, new Object[] { t.getMessage() });
        }
    }
    
    static class TerminateIOExceptionEvent extends NSLogEvent
    {
        public TerminateIOExceptionEvent(final int value) {
            super(new Object(), 1, 7953638416913016004L, new Object[] { new Integer(value) });
        }
    }
    
    static class PostEventException extends ProException
    {
        public PostEventException(final String original) {
            super(7953638416913017610L, new Object[] { new String(original) });
        }
    }
    
    static class StatisticsException extends ProException
    {
        public StatisticsException(final String s) {
            super(7953638416913017611L, null);
        }
    }
    
    static class LookupBindingException extends ProException
    {
        public LookupBindingException(final String original) {
            super(7953638416913017617L, new Object[] { new String(original) });
        }
    }
    
    static class MultiCastEnabledEvent extends NSLogEvent
    {
        public MultiCastEnabledEvent(final Object o, final String s) {
            super(o, 2, 7953638416913021183L, new Object[] { (s == null) ? "" : s });
        }
    }
    
    static class InvalidMultiCastGroupEvent extends NSLogEvent
    {
        public InvalidMultiCastGroupEvent(final Object o, final String s) {
            super(o, 2, 7953638416913021184L, new Object[] { (s == null) ? "" : s });
        }
    }
    
    static class FileNameChangedEvent extends NSLogEvent
    {
        public FileNameChangedEvent(final Object o, final String s, final String s2) {
            super(o, 3, 7953638416913021773L, new Object[] { s, s2 });
        }
    }
    
    static class NSLogEvntHandler implements ILogEvntHandler
    {
        private IEventBroker HandlerAdminServerEventBroker;
        private String canonicalName;
        private String nameServerName;
        private EventManager eventManager;
        
        public NSLogEvntHandler(final IEventBroker handlerAdminServerEventBroker, final EventManager eventManager, final String nameServerName, final String canonicalName) {
            this.HandlerAdminServerEventBroker = null;
            this.HandlerAdminServerEventBroker = handlerAdminServerEventBroker;
            this.canonicalName = canonicalName;
            this.nameServerName = nameServerName;
            this.eventManager = eventManager;
        }
        
        public void sendFileNameChangedEvent(final String s) {
            try {
                final EUbrokerLogFileNameChanged eUbrokerLogFileNameChanged = new EUbrokerLogFileNameChanged(this.nameServerName, s, "", this.canonicalName);
                if (this.HandlerAdminServerEventBroker != null) {
                    this.HandlerAdminServerEventBroker.postEvent(eUbrokerLogFileNameChanged);
                }
                this.eventManager.post(new FileNameChangedEvent(this, this.nameServerName, s));
            }
            catch (Exception ex) {
                this.eventManager.post(new ExceptionEvent(this, new PostEventException("EUbrokerLogFileNameChanged")));
            }
        }
    }
}
