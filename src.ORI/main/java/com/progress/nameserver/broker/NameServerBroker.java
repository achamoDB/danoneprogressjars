// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver.broker;

import java.io.InterruptedIOException;
import java.io.InputStream;
import com.progress.common.exception.ProException;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.management.events.ENameServerUnavailableEvent;
import com.progress.ubroker.management.events.COpenEdgeManagementContent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import java.rmi.Remote;
import com.progress.chimera.adminserver.IAdminServerConnection;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.RMISecurityManager;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.util.Getopt;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsFile;
import java.util.Vector;
import com.progress.nameserver.util.MsgUtil;
import com.progress.common.util.UUID;
import java.net.SocketException;
import java.io.IOException;
import java.io.OutputStream;
import com.progress.nameserver.util.NetSocket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import com.progress.common.ehnlog.AppLogger;
import com.progress.common.ehnlog.IAppLogger;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import com.progress.ubroker.util.ubProperties;

public class NameServerBroker
{
    protected int nsUnavailCount;
    protected long nsUnavailTimeStamp;
    protected ubProperties properties;
    private DatagramSocket ds;
    private DatagramPacket dgIn;
    private DatagramPacket dgOut;
    private ByteArrayInputStream dataIn;
    private DataInputStream dataStreamIn;
    private byte[] data;
    private ByteArrayOutputStream dataOut;
    private DataOutputStream dataStreamOut;
    private String uuid;
    private String instanceID;
    private NotifyNameServer keepAlive;
    private boolean shutDown;
    private IAppLogger log;
    
    public NameServerBroker(final String s, final int n, final IAppLogger appLogger) throws HostUnknownException, NameServerInitException {
        this(s, n, -1, appLogger);
    }
    
    public NameServerBroker(final String s, final int n, final IAppLogger appLogger, final ubProperties ubProperties) throws HostUnknownException, NameServerInitException {
        this(s, n, -1, appLogger, ubProperties);
    }
    
    public NameServerBroker(final String host, final int port, final int n, final IAppLogger log, final ubProperties properties) throws HostUnknownException, NameServerInitException {
        this.nsUnavailCount = 0;
        this.nsUnavailTimeStamp = -1L;
        this.properties = null;
        this.shutDown = false;
        this.properties = properties;
        if (log == null) {
            this.log = new AppLogger();
            if (this.log != null) {
                this.log.setExecEnvId("UB");
                this.log.setLoggingLevel(2);
                this.log.setLogEntries(1L, false, new byte[64]);
            }
        }
        else {
            this.log = log;
        }
        try {
            InetAddress byName = null;
            Label_0129: {
                if (host != null) {
                    try {
                        byName = InetAddress.getByName(host);
                        break Label_0129;
                    }
                    catch (UnknownHostException ex3) {
                        throw new HostUnknownException(host);
                    }
                }
                byName = null;
            }
            this.ds = NetSocket.create_netSocket(byName, n);
            this.dataOut = new ByteArrayOutputStream();
            this.dataStreamOut = new DataOutputStream(this.dataOut);
            this.dgOut = new DatagramPacket(this.dataOut.toByteArray(), this.dataOut.size(), byName, port);
        }
        catch (IOException ex) {
            throw new NameServerInitException(ex);
        }
        catch (HostUnknownException ex2) {
            throw ex2;
        }
        catch (Throwable t) {
            throw new NameServerInitException(t);
        }
    }
    
    public NameServerBroker(final String s, final int n, final int n2, final IAppLogger appLogger) throws HostUnknownException, NameServerInitException {
        this(s, n, n2, appLogger, null);
    }
    
    public void regBroker(final short n, final String uuid, final int v, final boolean b, final String str, final int v2, final int v3, final String str2, final String str3, final boolean b2, final String[] array, final int n2) throws MarshallFailureException, NameServerInitException {
        int length = 0;
        try {
            this.ds.setSoTimeout(10000);
        }
        catch (SocketException ex) {
            throw new NameServerInitException(ex);
        }
        this.uuid = uuid;
        this.instanceID = new UUID().toString();
        this.dataOut.reset();
        int size;
        int size2;
        try {
            MsgUtil.writeHeader(this.dataStreamOut, n, 1);
            size = this.dataOut.size();
            this.dataStreamOut.writeBoolean(false);
            this.dataStreamOut.writeInt(0);
            this.dataStreamOut.writeUTF(this.uuid);
            this.dataStreamOut.writeUTF(this.instanceID);
            if (b && str != null && str.length() > 0 && !str.equals("")) {
                this.dataStreamOut.writeUTF(str);
            }
            else {
                this.dataStreamOut.writeUTF(InetAddress.getLocalHost().getHostName());
            }
            this.dataStreamOut.writeInt(v);
            size2 = this.dataOut.size();
            this.dataStreamOut.writeInt(v2);
            this.dataStreamOut.writeInt(v3);
            this.dataStreamOut.writeUTF(str3);
            this.dataStreamOut.writeUTF(str2);
            if (array != null) {
                length = array.length;
            }
            if (b2) {
                this.dataStreamOut.writeInt(length + 1);
                this.dataStreamOut.writeUTF("");
            }
            else {
                this.dataStreamOut.writeInt(length);
            }
            for (int i = 0; i < length; ++i) {
                this.dataStreamOut.writeUTF(array[i]);
            }
            if (!b) {
                this.dataStreamOut.writeUTF(InetAddress.getLocalHost().getHostAddress());
            }
            else {
                this.dataStreamOut.writeUTF("");
            }
        }
        catch (IOException ex2) {
            throw new MarshallFailureException();
        }
        this.data = this.dataOut.toByteArray();
        this.dgOut.setData(this.data);
        this.dgOut.setLength(this.data.length);
        (this.keepAlive = new NotifyNameServer(v3, n2, size, size2)).start();
    }
    
    public void unRegBroker(final short n) throws NameServerSendFailureException, InternalConsistencyException, MarshallFailureException {
        if (this.uuid != null) {
            this.shutDown = true;
            this.keepAlive.interrupt();
            try {
                this.keepAlive.join();
            }
            catch (InterruptedException ex2) {}
            this.keepAlive = null;
            synchronized (this.dgOut) {
                this.dataOut.reset();
                try {
                    MsgUtil.writeHeader(this.dataStreamOut, n, 2);
                    this.dataStreamOut.writeUTF(this.uuid);
                    this.dataStreamOut.writeUTF(this.instanceID);
                    this.dgOut.setData(this.dataOut.toByteArray());
                    this.dgOut.setLength(this.dataOut.size());
                }
                catch (IOException ex3) {
                    throw new MarshallFailureException();
                }
                try {
                    this.ds.send(this.dgOut);
                }
                catch (IOException ex) {
                    throw new NameServerSendFailureException(this.dgOut, ex);
                }
            }
            this.ds.close();
            return;
        }
        throw new InternalConsistencyException();
    }
    
    public int getNameServerUnavailableCount() {
        return this.nsUnavailCount;
    }
    
    public long getNameServerUnavailableDuration() {
        if (this.nsUnavailTimeStamp != -1L) {
            return System.currentTimeMillis() - this.nsUnavailTimeStamp;
        }
        return 0L;
    }
    
    public static void main(final String[] array) {
        String[] anArray = null;
        String optArg = "127.0.0.1";
        String optArg2 = "asbroker1";
        String optArg3 = "AS";
        int int1 = 5192;
        int int2 = 0;
        int int3 = 30;
        int int4 = 1;
        String s = null;
        int int5 = 3166;
        boolean b = false;
        final Vector vector = new Vector<String>();
        try {
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsFile());
        }
        catch (Throwable obj) {
            System.out.println("Error initializing message subsystem - " + obj);
        }
        Getopt getopt;
        int n;
        for (getopt = new Getopt(array), n = getopt.getOpt("h:p:w:a:t:du:H:P:n:k:i:"); n != -1 && n != 63; n = getopt.getOpt("h:p:w:a:t:du:H:P:n:k:i:")) {
            switch (n) {
                case 105: {
                    int4 = Integer.parseInt(getopt.getOptArg());
                    break;
                }
                case 107: {
                    int3 = Integer.parseInt(getopt.getOptArg());
                    break;
                }
                case 110: {
                    optArg2 = getopt.getOptArg();
                    break;
                }
                case 104: {
                    optArg = getopt.getOptArg();
                    break;
                }
                case 112: {
                    int1 = Integer.parseInt(getopt.getOptArg());
                    break;
                }
                case 119: {
                    int2 = Integer.parseInt(getopt.getOptArg());
                    break;
                }
                case 116: {
                    optArg3 = getopt.getOptArg();
                    break;
                }
                case 100: {
                    b = true;
                    break;
                }
                case 97: {
                    vector.addElement(getopt.getOptArg());
                    break;
                }
                case 117: {
                    s = getopt.getOptArg();
                    break;
                }
                case 72: {
                    getopt.getOptArg();
                    break;
                }
                case 80: {
                    int5 = Integer.parseInt(getopt.getOptArg());
                    break;
                }
                default: {
                    System.out.println("\nInvalid option" + (char)n + "!!!!");
                    System.exit(0);
                    break;
                }
            }
        }
        if (n == 63) {
            System.out.println("\nInvalid option " + (char)n + "!!!!");
            System.exit(0);
        }
        final int size = vector.size();
        if (size > 0) {
            anArray = new String[size];
            vector.copyInto(anArray);
        }
        try {
            final NameServerBroker nameServerBroker = new NameServerBroker(optArg, int1, null);
            if (s == null) {
                s = new UUID().toString();
            }
            nameServerBroker.regBroker((short)108, s, int5, false, null, int2, int3, optArg2, optArg3, b, anArray, int4);
            Thread.currentThread().setPriority(4);
            System.out.println("\nHit ENTER TO EXIT:");
            System.out.flush();
            System.in.read();
            nameServerBroker.unRegBroker((short)108);
            System.exit(0);
        }
        catch (HostUnknownException x) {
            System.out.println(x);
        }
        catch (IOException x2) {
            System.out.println(x2);
        }
        catch (InternalConsistencyException x3) {
            System.out.println(x3);
        }
        catch (Exception x4) {
            System.out.println(x4);
        }
    }
    
    protected IEventBroker findAdminServerEventBroker() {
        IEventBroker eventBroker = null;
        try {
            final String rmiURL = this.properties.rmiURL;
            System.setSecurityManager(new RMISecurityManager());
            eventBroker = ((IAdministrationServer)lookupService(rmiURL.substring(0, rmiURL.lastIndexOf(this.properties.brokerName)) + "Chimera")).getEventBroker();
        }
        catch (Exception ex) {
            if (this.properties.rmiURL != null) {
                this.log.logStackTrace("Can't locate AdminServer's EventBroker", ex);
            }
        }
        return eventBroker;
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
    
    protected void sendNameServerUnavailableEvent() {
        final COpenEdgeManagementContent cOpenEdgeManagementContent = new COpenEdgeManagementContent(this.properties.brokerName, new Object[] { new String(this.properties.controllingNS) });
        final IEventBroker adminServerEventBroker = this.findAdminServerEventBroker();
        if (adminServerEventBroker != null) {
            try {
                final ENameServerUnavailableEvent eNameServerUnavailableEvent = new ENameServerUnavailableEvent(this.properties.brokerName, cOpenEdgeManagementContent);
                eNameServerUnavailableEvent.setSource(this.properties.canonicalName);
                adminServerEventBroker.postEvent(eNameServerUnavailableEvent);
                if (this.log.ifLogBasic(1L, 0)) {
                    this.log.logBasic(0, "Posted ENameServerUnavailableEvent for broker: " + this.properties.brokerName);
                }
            }
            catch (Exception ex) {
                this.log.logStackTrace("Error posting ENameServerUnavailableEvent for broker: " + this.properties.brokerName, ex);
            }
        }
    }
    
    public static class HostUnknownException extends ProException
    {
        public HostUnknownException(final String s) {
            super(7665689515738013744L, new Object[] { s });
        }
    }
    
    public static class InternalConsistencyException extends ProException
    {
        public InternalConsistencyException() {
            super(7665689515738013745L, null);
        }
    }
    
    public static class MarshallFailureException extends ProException
    {
        public MarshallFailureException() {
            super(7665689515738014012L, null);
        }
    }
    
    public static class NameServerSendFailureException extends ProException
    {
        public NameServerSendFailureException(final DatagramPacket datagramPacket, final Throwable t) {
            super(7665689515738014013L, new Object[] { datagramPacket.getAddress().toString(), new Integer(datagramPacket.getPort()), t.getMessage() });
        }
    }
    
    public static class NameServerReceiveFailureException extends ProException
    {
        public NameServerReceiveFailureException(final DatagramPacket datagramPacket, final Throwable t) {
            super(7665689515738014055L, new Object[] { datagramPacket.getAddress().toString(), new Integer(datagramPacket.getPort()), t.getMessage() });
        }
    }
    
    public static class NameServerInitException extends ProException
    {
        public NameServerInitException(final Throwable t) {
            super(7665689515738014058L, new Object[] { t.getMessage() });
        }
    }
    
    private class NotifyNameServer extends Thread
    {
        private long waitTime;
        private int ackInterval;
        private int fixupIndex;
        private int weightIndex;
        private int weightValue;
        private ByteArrayOutputStream fixupOut;
        private DataOutputStream fixupStreamOut;
        
        public NotifyNameServer(final int n, final int ackInterval, final int fixupIndex, final int weightIndex) {
            this.waitTime = n * 1000;
            this.ackInterval = ackInterval;
            this.fixupIndex = fixupIndex;
            this.weightIndex = weightIndex;
            if (NameServerBroker.this.properties != null) {
                this.weightValue = NameServerBroker.this.properties.getValueAsInt("priorityWeight");
            }
            this.fixupOut = new ByteArrayOutputStream();
            this.fixupStreamOut = new DataOutputStream(this.fixupOut);
            NameServerBroker.this.dgIn = new DatagramPacket(new byte[1000], 1000);
            NameServerBroker.this.dataIn = new ByteArrayInputStream(NameServerBroker.this.dgIn.getData());
            NameServerBroker.this.dataStreamIn = new DataInputStream(NameServerBroker.this.dataIn);
        }
        
        public void run() {
            Thread.currentThread().setName("NameServer");
            int n = 0;
            String s = "";
            try {
                int n2 = 0;
                int n3 = 0;
                int v = 1;
            Block_8:
                while (true) {
                    synchronized (NameServerBroker.this.dgOut) {
                        if (this.ackInterval > 0 && n3 % this.ackInterval == 0) {
                            this.fixupOut.reset();
                            this.fixupStreamOut.writeBoolean(true);
                            this.fixupStreamOut.writeInt(v);
                            final byte[] byteArray = this.fixupOut.toByteArray();
                            System.arraycopy(byteArray, 0, NameServerBroker.this.data, this.fixupIndex, byteArray.length);
                            n2 = 1;
                        }
                        if (NameServerBroker.this.properties != null && this.weightValue != NameServerBroker.this.properties.getValueAsInt("priorityWeight")) {
                            this.weightValue = NameServerBroker.this.properties.getValueAsInt("priorityWeight");
                            this.fixupOut.reset();
                            this.fixupStreamOut.writeInt(this.weightValue);
                            final byte[] byteArray2 = this.fixupOut.toByteArray();
                            System.arraycopy(byteArray2, 0, NameServerBroker.this.data, this.weightIndex, byteArray2.length);
                        }
                        NameServerBroker.this.ds.send(NameServerBroker.this.dgOut);
                        if (n2 == 1) {
                            NameServerBroker.this.data[this.fixupIndex] = 0;
                        }
                    }
                    if (n2 == 1) {
                        n2 = 0;
                        int n4 = 1;
                        int n5 = 0;
                        while (n5 != 1 && n4 == 1) {
                            NameServerBroker.this.dataIn.reset();
                            NameServerBroker.this.dgIn.setLength(1000);
                            try {
                                NameServerBroker.this.ds.receive(NameServerBroker.this.dgIn);
                                NameServerBroker.this.nsUnavailCount = 0;
                                NameServerBroker.this.nsUnavailTimeStamp = -1L;
                            }
                            catch (IOException ex) {
                                if (ex instanceof InterruptedIOException) {
                                    if (!NameServerBroker.this.shutDown) {
                                        if (n != 2) {
                                            if (NameServerBroker.this.log.ifLogBasic(1L, 0)) {
                                                NameServerBroker.this.log.logBasic(0, 7665689515738014054L, null);
                                            }
                                            NameServerBroker.this.sendNameServerUnavailableEvent();
                                            NameServerBroker.this.nsUnavailTimeStamp = System.currentTimeMillis();
                                        }
                                        final NameServerBroker this$0 = NameServerBroker.this;
                                        ++this$0.nsUnavailCount;
                                        n4 = 0;
                                        n = 2;
                                    }
                                }
                                else {
                                    if (NameServerBroker.this.shutDown) {
                                        return;
                                    }
                                    if (n != 4 || !s.equals(ex.getMessage())) {
                                        NameServerBroker.this.log.logError(7665689515738014055L, new Object[] { ex.getMessage() });
                                    }
                                    s = new String(ex.getMessage());
                                    n4 = 0;
                                    n = 4;
                                    continue;
                                }
                            }
                            if (NameServerBroker.this.shutDown) {
                                break Block_8;
                            }
                            if (n4 != 1) {
                                continue;
                            }
                            try {
                                MsgUtil.readHeader(NameServerBroker.this.dataStreamIn);
                                if (NameServerBroker.this.dataStreamIn.readInt() != v) {
                                    continue;
                                }
                                ++n3;
                                n5 = 1;
                                if (!NameServerBroker.this.dataStreamIn.readBoolean()) {
                                    if (n != 3 && NameServerBroker.this.log.ifLogBasic(1L, 0)) {
                                        NameServerBroker.this.log.logBasic(0, 7665689515738014056L, null);
                                    }
                                    n = 3;
                                }
                                else {
                                    if (n != 1 && NameServerBroker.this.log.ifLogBasic(1L, 0)) {
                                        NameServerBroker.this.log.logBasic(0, 7665689515738014060L, null);
                                    }
                                    n = 1;
                                    s = "";
                                }
                            }
                            catch (IOException ex4) {
                                NameServerBroker.this.log.logError("IOException when reading message from " + NameServerBroker.this.dgIn.getAddress() + ":" + NameServerBroker.this.dgIn.getPort());
                            }
                            catch (MsgUtil.InvalidMsgVersionException ex2) {
                                NameServerBroker.this.log.logError("Received message with invalid version from " + NameServerBroker.this.dgIn.getAddress() + ":" + NameServerBroker.this.dgIn.getPort() + " : expected version: " + ex2.expectedVersion() + " got version: " + ex2.gotVersion());
                            }
                        }
                    }
                    else {
                        ++n3;
                    }
                    Thread.sleep(this.waitTime);
                    ++v;
                }
            }
            catch (InterruptedException ex5) {
                if (!NameServerBroker.this.shutDown) {
                    NameServerBroker.this.log.logError("Fatal Error: Unexpected Interrupt in Notify Thread");
                }
            }
            catch (IOException ex3) {
                if (!NameServerBroker.this.shutDown) {
                    NameServerBroker.this.log.logError(7665689515738014055L, new Object[] { NameServerBroker.this.dgOut.getAddress().toString(), new Integer(NameServerBroker.this.dgOut.getPort()), ex3.getMessage() });
                }
            }
        }
    }
}
