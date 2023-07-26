// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Sockets
{
    private HandleMessage m_msgHandler;
    private Socket m_socket;
    private OutputStreamWriter m_sendStream;
    private InputStreamReader m_recvStream;
    private char[] m_msgBytes;
    private int m_curMsgPos;
    private int m_readLength;
    private Frame1 m_app;
    private boolean m_wait;
    static final int SOCKET_SEND = 1;
    static final int SOCKET_RECV = 2;
    static final String timeoutErr;
    static final String connectErr;
    static final String hostErr;
    static final String getMsgErr;
    
    Sockets(final Frame1 app, final int n) {
        this.m_msgBytes = new char[512];
        this.m_curMsgPos = 0;
        this.m_readLength = 0;
        this.m_wait = false;
        this.m_app = app;
        final String property = System.getProperty("PVMPort");
        final String property2 = System.getProperty("Ipver");
        final String property3 = System.getProperty("Msg");
        final int intValue = new Integer(property);
        if (property2 != null && property2.compareToIgnoreCase("1") == 0) {
            try {
                this.createSocket("::1", intValue, n);
                if (property3 != null && property3.compareToIgnoreCase("1") == 0) {
                    JOptionPane.showMessageDialog(this.m_app, "Java client connection is ::1", "Info", 1);
                }
            }
            catch (Exception ex3) {
                try {
                    this.createSocket("127.0.0.1", intValue, n);
                    if (property3 != null && property3.compareToIgnoreCase("1") == 0) {
                        JOptionPane.showMessageDialog(this.m_app, "Java client connection is 127.0.0.1", "Info", 1);
                    }
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.m_app, Sockets.connectErr + ex.getMessage(), "Error", 0);
                    System.exit(0);
                }
            }
        }
        else {
            try {
                this.createSocket("127.0.0.1", intValue, n);
                if (property3 != null && property3.compareToIgnoreCase("1") == 0) {
                    JOptionPane.showMessageDialog(this.m_app, "Java client connection is 127.0.0.1", "Info", 1);
                }
            }
            catch (Exception ex2) {
                JOptionPane.showMessageDialog(this.m_app, Sockets.connectErr + ex2.getMessage(), "Error", 0);
                System.exit(0);
            }
        }
    }
    
    Sockets(final Frame1 app, final int n, final String s, final int n2) throws Exception {
        this.m_msgBytes = new char[512];
        this.m_curMsgPos = 0;
        this.m_readLength = 0;
        this.m_wait = false;
        this.m_app = app;
        boolean b = false;
        int n3 = 0;
        long currentTimeMillis = 0L;
        long currentTimeMillis2 = 0L;
        final String property = System.getProperty("Msg");
        do {
            try {
                if (s.length() == 0 || s.equalsIgnoreCase("localhost")) {
                    if (app.prodll.IsIPv6()) {
                        if (n3 == 0) {
                            b = this.createSocket("::1", n2, n);
                            if (property != null && property.compareToIgnoreCase("1") == 0) {
                                JOptionPane.showMessageDialog(this.m_app, "Java client connection is ::1", "Info", 1);
                            }
                        }
                        else {
                            b = this.createSocket("127.0.0.1", n2, n);
                            if (property != null && property.compareToIgnoreCase("1") == 0) {
                                JOptionPane.showMessageDialog(this.m_app, "Java client connection is 127.0.0.1", "Info", 1);
                            }
                        }
                    }
                    else {
                        b = this.createSocket("127.0.0.1", n2, n);
                        if (property != null && property.compareToIgnoreCase("1") == 0) {
                            JOptionPane.showMessageDialog(this.m_app, "Java client connection is 127.0.0.1", "Info", 1);
                        }
                    }
                }
                else {
                    b = this.createSocket(s, n2, n);
                }
            }
            catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(this.m_app, Sockets.hostErr + ex.getMessage(), "Error", 0);
                throw ex;
            }
            catch (IOException ex2) {
                if (currentTimeMillis == 0L) {
                    currentTimeMillis = System.currentTimeMillis();
                }
                currentTimeMillis2 = System.currentTimeMillis();
                if (n3 == 0) {
                    n3 = 1;
                }
                else {
                    n3 = 0;
                }
            }
        } while (!b && currentTimeMillis2 - currentTimeMillis < 15000L);
        if (!b) {
            JOptionPane.showMessageDialog(this.m_app, Sockets.timeoutErr, "Error", 0);
            throw new IOException();
        }
    }
    
    private boolean createSocket(final String host, final int port, final int n) throws UnknownHostException, IOException {
        this.m_socket = new Socket(host, port);
        if (n == 1) {
            this.m_sendStream = new OutputStreamWriter(this.m_socket.getOutputStream(), "UTF8");
        }
        else {
            this.m_recvStream = new InputStreamReader(this.m_socket.getInputStream(), "UTF8");
            this.m_msgHandler = new HandleMessage(this.m_app);
        }
        return true;
    }
    
    void processMessage(final String s) {
        SwingUtilities.invokeLater(new ParseMessage(s));
    }
    
    void getMessages() {
        final int intValue = new Integer(System.getProperty("Mode"));
        try {
            while (true) {
                final String nextMessage = this.nextMessage();
                if (this.m_wait && intValue == 2) {
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                    }
                    catch (InterruptedException ex2) {}
                }
                if (nextMessage == null) {
                    break;
                }
                this.processMessage(nextMessage);
            }
        }
        catch (IOException ex) {
            if (!this.m_app.optionDetachSelected && !this.m_app.menuFileExitSelected) {
                JOptionPane.showMessageDialog(this.m_app, Sockets.getMsgErr + ex.getMessage(), "Error", 0);
            }
        }
        finally {
            if (intValue != 2) {
                this.m_app.meshandle.parseMessage("MSG_SHUTDOWN");
            }
            else if (!this.m_app.optionDetachSelected) {
                this.m_app.ProcessAttachToProcess(true);
            }
        }
    }
    
    void close() throws IOException {
        this.m_socket.close();
    }
    
    void sendMessage(final String original) {
        if (this.m_app.debugDlgOpen) {
            this.m_app.debugDlg.addMessage(original);
        }
        final String string = new String(original) + "\u0000";
        try {
            this.m_sendStream.write(string);
            this.m_sendStream.flush();
        }
        catch (Exception ex) {}
    }
    
    private String nextMessage() throws IOException {
        final StringBuffer sb = new StringBuffer();
        int curMsgPos = this.m_curMsgPos;
        while (true) {
            if (this.m_curMsgPos == 0) {
                this.m_readLength = this.m_recvStream.read(this.m_msgBytes);
            }
            if (this.m_readLength > 0) {
                while (this.m_curMsgPos < this.m_readLength && this.m_msgBytes[this.m_curMsgPos] != '\0') {
                    ++this.m_curMsgPos;
                }
                if (this.m_curMsgPos < this.m_readLength) {
                    ++this.m_curMsgPos;
                    sb.append(new String(this.m_msgBytes, curMsgPos, this.m_curMsgPos - curMsgPos));
                    if (this.m_curMsgPos == this.m_readLength) {
                        this.m_curMsgPos = 0;
                    }
                    return sb.toString();
                }
                sb.append(new String(this.m_msgBytes, curMsgPos, this.m_curMsgPos - curMsgPos));
                this.m_curMsgPos = 0;
                curMsgPos = 0;
            }
            else {
                if (this.m_readLength == -1) {
                    this.m_curMsgPos = 0;
                    return null;
                }
                continue;
            }
        }
    }
    
    synchronized void lock() {
        this.m_wait = true;
    }
    
    synchronized void unlock() {
        if (this.m_wait) {
            this.m_wait = false;
            this.notifyAll();
        }
    }
    
    static {
        timeoutErr = new String("Timed out trying to connect to Progress");
        connectErr = new String("Error trying to connect to Progress: ");
        hostErr = new String("Host for Progress connection is unknown: ");
        getMsgErr = new String("Error getting message from Progress: ");
    }
    
    class ParseMessage implements Runnable
    {
        String localMessage;
        
        public ParseMessage(final String localMessage) {
            this.localMessage = localMessage;
        }
        
        public void run() {
            Sockets.this.m_msgHandler.parseMessage(this.localMessage);
        }
    }
}
