// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.Socket;
import com.progress.common.ehnlog.IAppLogger;

public class MsgReader implements Runnable
{
    public static final byte STATE_READY = 0;
    public static final byte STATE_CLOSED = 1;
    static final String[] DESC_STATE;
    static final String[] DESC_STATE_EXT;
    private MsgInputStream m_inMsgStream;
    private IAppLogger m_log;
    private RequestQueue m_requestQueue;
    private Thread m_readerThread;
    private Socket m_sock;
    private String m_parent;
    private int m_current_state;
    private boolean m_shutdownRequested;
    
    public static MsgReader newMsgReader(final String str, final Socket socket, final MsgInputStream msgInputStream, final RequestQueue requestQueue, final IAppLogger appLogger) {
        final MsgReader target = new MsgReader(str, socket, msgInputStream, requestQueue, appLogger);
        final Thread thread = new Thread(target);
        thread.setDaemon(true);
        thread.setName(str + "-" + (socket.getInetAddress().getHostAddress() + ":" + socket.getPort()));
        target.setThread(thread);
        thread.start();
        return target;
    }
    
    public MsgReader(final String parent, final Socket sock, final MsgInputStream inMsgStream, final RequestQueue requestQueue, final IAppLogger log) {
        this.m_parent = parent;
        this.m_sock = sock;
        this.m_inMsgStream = inMsgStream;
        this.m_log = log;
        this.m_requestQueue = requestQueue;
        this.m_current_state = 0;
        this.m_shutdownRequested = false;
    }
    
    public void run() {
        try {
            this.mainline();
        }
        catch (Throwable obj) {
            if (this.m_log.ifLogBasic(1L, 0)) {
                this.m_log.logBasic(0, "Error reading from msg input stream = " + obj);
            }
        }
        if (this.m_log.ifLogBasic(1L, 0)) {
            this.m_log.logBasic(0, this.getThread().getName() + " done.");
        }
        this.m_current_state = 1;
    }
    
    public String toString() {
        return this.getThread().getName();
    }
    
    public Thread getThread() {
        return this.m_readerThread;
    }
    
    public int getState() {
        return this.m_current_state;
    }
    
    public boolean isClosed() {
        return this.m_current_state == 1;
    }
    
    public void close() {
        this.m_current_state = 1;
    }
    
    private void mainline() {
        Object msg = null;
        int i = 1;
        while (i != 0) {
            try {
                msg = this.m_inMsgStream.readMsg();
            }
            catch (SocketTimeoutException obj) {
                if (this.m_log.ifLogExtended(1L, 0)) {
                    this.m_log.logExtended(0, "Timeout reading msg from " + this.m_inMsgStream + " = " + obj);
                }
                final ubAdminMsg ubAdminMsg = new ubAdminMsg((byte)14);
                ubAdminMsg.setadParm(new Object[] { obj });
                msg = ubAdminMsg;
            }
            catch (SocketException obj2) {
                if (this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, "SocketException reading msg from " + this.m_inMsgStream + " = " + obj2);
                }
                this.close();
                i = 0;
            }
            catch (IOException obj3) {
                if (this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, "IOException reading msg from " + this.m_inMsgStream + " = " + obj3);
                }
                final ubAdminMsg ubAdminMsg2 = new ubAdminMsg((byte)6);
                ubAdminMsg2.setadParm(new Object[] { obj3 });
                msg = ubAdminMsg2;
                i = 0;
            }
            catch (ubMsg.MsgFormatException obj4) {
                if (this.m_log.ifLogBasic(1L, 0)) {
                    this.m_log.logBasic(0, "Error reading msg from " + this.m_inMsgStream + " = " + obj4);
                }
                final ubAdminMsg ubAdminMsg3 = new ubAdminMsg((byte)13);
                ubAdminMsg3.setadParm(new Object[] { obj4 });
                msg = ubAdminMsg3;
                i = 0;
            }
            catch (Exception obj5) {
                if (this.m_log.ifLogBasic(1L, 0)) {
                    this.m_log.logBasic(0, "Error reading msg from " + this.m_inMsgStream + " = " + obj5);
                }
                final ubAdminMsg ubAdminMsg4 = new ubAdminMsg((byte)6);
                ubAdminMsg4.setadParm(new Object[] { obj5 });
                msg = ubAdminMsg4;
                i = 0;
            }
            if (this.m_current_state == 1) {
                i = 0;
            }
            else {
                final Request request = new Request(msg, null);
                try {
                    this.m_requestQueue.enqueueRequest(request);
                }
                catch (Queue.QueueException obj6) {
                    if (this.m_log.ifLogBasic(1L, 0)) {
                        this.m_log.logBasic(0, "Error enqueuing rq to " + this.m_requestQueue + " = " + obj6);
                    }
                    i = 0;
                }
            }
            msg = null;
        }
    }
    
    private void setThread(final Thread readerThread) {
        this.m_readerThread = readerThread;
    }
    
    static {
        DESC_STATE = new String[] { " STATE_READY ", " STATE_CLOSED " };
        DESC_STATE_EXT = new String[] { " READY ", " CLOSED " };
    }
}
