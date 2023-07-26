// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver.client;

import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsBundle;
import java.io.InterruptedIOException;
import com.progress.open4gl.NoSuchAppServiceException;
import com.progress.open4gl.MsgVersionInvalidException;
import com.progress.open4gl.NameServerMessageFormatException;
import com.progress.nameserver.util.MsgUtil;
import com.progress.open4gl.NameServerInterruptException;
import java.net.SocketException;
import com.progress.open4gl.InvalidNameServerPortException;
import java.io.OutputStream;
import java.io.InputStream;
import com.progress.open4gl.NameServerClientPortException;
import java.io.IOException;
import com.progress.open4gl.NameServerClientPortRetryException;
import com.progress.open4gl.NameServerClientPortRangeException;
import com.progress.nameserver.util.NetSocket;
import com.progress.open4gl.NameServerCommunicationsException;
import java.net.UnknownHostException;
import com.progress.open4gl.HostUnknownException;
import java.net.InetAddress;
import com.progress.open4gl.ConnectException;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class NameServerClient
{
    DatagramSocket ds;
    String clientType;
    DatagramPacket dgIn;
    DatagramPacket dgOut;
    ByteArrayInputStream dataIn;
    DataInputStream dataStreamIn;
    ByteArrayOutputStream dataOut;
    DataOutputStream dataStreamOut;
    RTTimer rtt;
    static int requestNumber;
    int count;
    private static final int MAX_RETRANS = 5;
    
    public NameServerClient(final String s, final int n, final String s2) throws ConnectException {
        this(s, n, s2, 0, 0, 0, 0);
    }
    
    public NameServerClient(final String s, final int n, final String s2, final int n2) throws ConnectException {
        this(s, n, s2, n2, n2, 0, 0);
    }
    
    public NameServerClient(final String host, final int port, final String clientType, final int n, final int n2, final int n3, final int n4) throws ConnectException {
        this.count = 0;
        InetAddress byName = null;
        Label_0048: {
            if (host != null) {
                try {
                    byName = InetAddress.getByName(host);
                    break Label_0048;
                }
                catch (UnknownHostException ex2) {
                    throw new HostUnknownException(host);
                }
                catch (Throwable t) {
                    throw new NameServerCommunicationsException(t);
                }
            }
            byName = null;
            try {
                if (n == 0 && n2 == 0) {
                    this.ds = NetSocket.create_netSocket(byName, n);
                }
                else {
                    boolean b = false;
                    if (n <= 0 || n2 <= 0 || n < 1024 || n2 > 65535 || n > n2) {
                        throw new NameServerClientPortRangeException();
                    }
                    if (n3 < 0 || n3 > 500 || n4 < 0 || n4 > 10000) {
                        throw new NameServerClientPortRetryException();
                    }
                    for (int i = 0; i <= n3; ++i) {
                        int j = n;
                        while (j <= n2) {
                            Label_0192: {
                                Label_0198: {
                                    try {
                                        this.ds = NetSocket.create_netSocket(byName, j);
                                    }
                                    catch (IOException ex3) {
                                        break Label_0198;
                                    }
                                    break Label_0192;
                                }
                                ++j;
                                continue;
                            }
                            b = true;
                            break;
                        }
                        if (b) {
                            break;
                        }
                        if (i + 1 <= n3) {
                            Thread.sleep(n4);
                        }
                    }
                    if (!b) {
                        throw new NameServerClientPortException();
                    }
                }
                this.clientType = clientType;
                this.dgIn = new DatagramPacket(new byte[1000], 1000);
                this.dataIn = new ByteArrayInputStream(this.dgIn.getData());
                this.dataStreamIn = new DataInputStream(this.dataIn);
                this.dataOut = new ByteArrayOutputStream();
                this.dataStreamOut = new DataOutputStream(this.dataOut);
            }
            catch (IOException ex) {
                throw new NameServerCommunicationsException(ex);
            }
            catch (Throwable t2) {
                throw new NameServerCommunicationsException(t2);
            }
        }
        try {
            this.dgOut = new DatagramPacket(this.dataOut.toByteArray(), this.dataOut.size(), byName, port);
        }
        catch (IllegalArgumentException ex4) {
            throw new InvalidNameServerPortException(port);
        }
        catch (Throwable t3) {
            throw new NameServerCommunicationsException(t3);
        }
        this.rtt = new RTTimer();
    }
    
    public Broker getBroker(final String s) throws ConnectException {
        return this.getBrokerList(s, 1)[0];
    }
    
    public Broker getBroker(final short n, final String s) throws ConnectException {
        return this.getBrokerList(n, s, 1)[0];
    }
    
    public Broker[] getBrokerList(final String s, final int n) throws ConnectException {
        return this.getBrokerList((short)109, s, n);
    }
    
    public Broker[] getBrokerList_save(final short n, final String s, final int n2) throws ConnectException {
        Broker[] response = null;
        ++this.count;
        if (n2 == 0) {
            return response;
        }
        final int requestNumber = this.getRequestNumber();
        this.writeRequestStream(n, s, n2, requestNumber);
        int n3 = 0;
        while (response == null) {
            try {
                this.ds.send(this.dgOut);
                this.ds.setSoTimeout(this.rtt.getRTO());
            }
            catch (SocketException ex) {
                throw new NameServerCommunicationsException(ex);
            }
            catch (IOException ex2) {
                throw new NameServerCommunicationsException(ex2);
            }
            try {
                response = this.getResponse(s, requestNumber);
            }
            catch (NameServerInterruptException ex3) {
                if (++n3 == 5) {
                    this.rtt.reinit();
                    throw ex3;
                }
                this.rtt.timeout();
            }
        }
        return response;
    }
    
    public Broker[] getBrokerList(final short n, final String s, final int n2) throws ConnectException {
        Broker[] list = null;
        ++this.count;
        if (n2 == 0) {
            return list;
        }
        final int requestNumber = this.getRequestNumber();
        int n3 = 0;
        short n4 = n;
        while (list == null) {
            try {
                list = this.getList(n4, requestNumber, s, n2);
            }
            catch (NameServerInterruptException ex) {
                if (n4 > 108) {
                    --n4;
                }
                else {
                    n4 = n;
                    if (++n3 == 5) {
                        this.rtt.reinit();
                        throw ex;
                    }
                    this.rtt.timeout();
                }
            }
        }
        return list;
    }
    
    private Broker[] getList(final short n, final int n2, final String s, final int n3) throws ConnectException {
        this.writeRequestStream(n, s, n3, n2);
        try {
            this.ds.send(this.dgOut);
            this.ds.setSoTimeout(this.rtt.getRTO());
        }
        catch (SocketException ex) {
            throw new NameServerCommunicationsException(ex);
        }
        catch (IOException ex2) {
            throw new NameServerCommunicationsException(ex2);
        }
        return this.getResponse(s, n2);
    }
    
    private synchronized int getRequestNumber() {
        return ++NameServerClient.requestNumber;
    }
    
    private void writeRequestStream(final short n, String str, final int v, final int v2) throws ConnectException {
        this.dataOut.reset();
        try {
            MsgUtil.writeHeader(this.dataStreamOut, n, 3);
            this.dataStreamOut.writeInt(v2);
            this.dataStreamOut.writeUTF(this.clientType);
            if (str == null) {
                str = "";
            }
            this.dataStreamOut.writeUTF(str);
            this.dataStreamOut.writeInt(v);
            this.dataStreamOut.writeBoolean(false);
        }
        catch (IOException ex) {
            throw new NameServerMessageFormatException();
        }
        catch (Throwable t) {
            throw new NameServerMessageFormatException();
        }
        this.dgOut.setData(this.dataOut.toByteArray());
        this.dgOut.setLength(this.dataOut.size());
    }
    
    private Broker[] getResponse(final String s, final int n) throws ConnectException {
        int n2 = 0;
        Broker[] brokerListFromMsg = null;
        int i = 0;
        while (i != 1) {
            this.receiveResponse(n2, s);
            int int1;
            int int2;
            try {
                MsgUtil.readHeader(this.dataStreamIn);
                int1 = this.dataStreamIn.readInt();
                int2 = this.dataStreamIn.readInt();
            }
            catch (MsgUtil.InvalidMsgVersionException ex) {
                throw new MsgVersionInvalidException(ex.gotVersion(), ex.expectedVersion());
            }
            catch (IOException ex3) {
                throw new NameServerMessageFormatException();
            }
            if (int1 == n) {
                if (int2 > 0) {
                    i = 1;
                    brokerListFromMsg = this.getBrokerListFromMsg(int2);
                }
                else {
                    if (n2 > 20) {
                        throw new NoSuchAppServiceException(s, this.dgOut.getAddress().getHostName(), this.dgOut.getPort());
                    }
                    if (n2 == 0) {
                        try {
                            this.ds.setSoTimeout(2000);
                        }
                        catch (SocketException ex2) {
                            throw new NameServerCommunicationsException(ex2);
                        }
                    }
                    ++n2;
                }
            }
        }
        return brokerListFromMsg;
    }
    
    private void receiveResponse(final int n, final String s) throws ConnectException {
        this.dataIn.reset();
        this.dgIn.setLength(1000);
        try {
            this.ds.receive(this.dgIn);
        }
        catch (IOException ex) {
            if (n != 0) {
                throw new NoSuchAppServiceException(s, this.dgOut.getAddress().getHostName(), this.dgOut.getPort());
            }
            if (ex instanceof InterruptedIOException) {
                throw new NameServerInterruptException();
            }
            throw new NameServerCommunicationsException(ex);
        }
    }
    
    private Broker[] getBrokerListFromMsg(final int n) throws ConnectException {
        final Broker[] array = new Broker[n];
        for (int i = 0; i < n; ++i) {
            String utf;
            String utf2;
            int int1;
            int int2;
            try {
                utf = this.dataStreamIn.readUTF();
                utf2 = this.dataStreamIn.readUTF();
                int1 = this.dataStreamIn.readInt();
                int2 = this.dataStreamIn.readInt();
            }
            catch (IOException ex) {
                throw new NameServerMessageFormatException();
            }
            array[i] = new Broker(utf, utf2, int1, int2);
        }
        return array;
    }
    
    public static void main(final String[] array) {
        Broker[] brokerList = null;
        String s = null;
        ExceptionMessageAdapter.setMessageSubsystem(new PromsgsBundle());
        if (array.length > 3) {
            s = array[3];
        }
        NameServerClient nameServerClient;
        try {
            System.out.println("\nCreating NameServer Client on Port " + array[0] + "with " + array[1] + " " + array[2]);
            final int int1 = Integer.parseInt(array[0]);
            if (int1 > 0) {
                nameServerClient = new NameServerClient(array[1], Integer.parseInt(array[2]), "AS", int1);
            }
            else {
                nameServerClient = new NameServerClient(array[1], Integer.parseInt(array[2]), "AS");
            }
        }
        catch (ConnectException obj) {
            System.out.println("\nNameServerClient Failure: " + obj);
            return;
        }
        int n = 0;
        if (array.length > 3) {
            System.out.println(" AppService = " + array[3]);
        }
        else {
            System.out.println(" AppService = [DEFAULT]");
        }
        for (int i = 0; i < 1000; ++i) {
            System.out.println("Attempt number " + i);
            try {
                brokerList = null;
                brokerList = nameServerClient.getBrokerList(s, 1);
            }
            catch (ConnectException obj2) {
                System.out.println("\nGetBrokerList Failure: " + obj2);
            }
            System.out.println("\nGot them count = " + ++n);
            if (brokerList != null) {
                for (int j = 0; j < brokerList.length; ++j) {
                    System.out.println("Broker " + (j + 1) + ":\n" + brokerList[j]);
                }
            }
        }
    }
    
    public static class Broker
    {
        private String uuid;
        private String host;
        private int port;
        private int weight;
        
        Broker(final String uuid, final String host, final int port, final int weight) {
            this.uuid = uuid;
            this.host = host;
            this.port = port;
            this.weight = weight;
        }
        
        public String getUUID() {
            return this.uuid;
        }
        
        public String getHost() {
            return this.host;
        }
        
        public int getPort() {
            return this.port;
        }
        
        public int getWeight() {
            return this.weight;
        }
        
        public String toString() {
            return "\n\t\tUUID = " + this.uuid + "\n\t\t\tHost = " + this.host + "\n\t\t\tPort = " + this.port + "\n\t\t\tWeight = " + this.weight + "\n";
        }
    }
}
