// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver.util;

import java.net.MulticastSocket;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetSocket
{
    private NetSocket() {
    }
    
    public static DatagramSocket create_netSocket(final InetAddress inetAddress) throws IOException {
        return create_netSocket(inetAddress, 0);
    }
    
    public static DatagramSocket create_netSocket(final int n) throws IOException {
        return create_netSocket(null, n);
    }
    
    public static DatagramSocket create_netSocket(final InetAddress inetAddress, final int port) throws IOException {
        DatagramSocket datagramSocket;
        if (inetAddress != null && inetAddress.isMulticastAddress()) {
            NetMulticastSocket netMulticastSocket;
            if (port <= 0) {
                netMulticastSocket = new NetMulticastSocket(inetAddress);
            }
            else {
                netMulticastSocket = new NetMulticastSocket(inetAddress, port);
            }
            netMulticastSocket.setTTL((byte)122);
            datagramSocket = netMulticastSocket;
        }
        else if (port <= 0) {
            datagramSocket = new DatagramSocket();
        }
        else {
            datagramSocket = new DatagramSocket(port);
        }
        return datagramSocket;
    }
    
    private static class NetMulticastSocket extends MulticastSocket
    {
        InetAddress address;
        
        NetMulticastSocket(final InetAddress inetAddress, final int port) throws IOException {
            super(port);
            this.joinGroup(inetAddress);
            this.address = inetAddress;
        }
        
        NetMulticastSocket(final InetAddress inetAddress) throws IOException {
            this.joinGroup(inetAddress);
            this.address = inetAddress;
        }
        
        public void close() {
            try {
                this.leaveGroup(this.address);
            }
            catch (Exception ex) {}
            super.close();
        }
    }
    
    static class HostUnknownException extends Exception
    {
        String host;
        
        public HostUnknownException(final String s) {
            super("Host " + s + "unknown.");
            this.host = s;
        }
        
        String getHost() {
            return this.host;
        }
    }
}
