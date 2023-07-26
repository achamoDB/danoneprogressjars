// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.javafrom4gl.implementation.StopInterface;
import com.progress.ubroker.util.ubMsg;
import java.net.Socket;
import com.progress.open4gl.IntHolder;
import com.progress.javafrom4gl.implementation.JavaServlet;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubConstants;

class ubServerThreadIPC implements ubConstants, IServerIPC
{
    private static final byte FGL_NO_COND = 0;
    private static final byte FGL_ERROR_COND = 1;
    private static final int WRITE_LAST_OK = 1;
    private static final int WRITE_LAST_BAD = 2;
    IAppLogger log;
    ubAppServerMsg javaResponse;
    JavaServlet currentServlet;
    int serverState;
    IntHolder how_manyH;
    private final int READY_STATE = 1;
    private final int RESPONSE_READY_STATE = 2;
    private final int WRITE_STATE = 3;
    private final int READ_STATE = 4;
    
    public ubServerThreadIPC(final int n, final IAppLogger log, final int n2) {
        this.log = log;
        this.setStateReady();
        this.how_manyH = new IntHolder();
    }
    
    public ubServerThreadIPC(final Socket socket, final int n, final IAppLogger appLogger, final int n2) {
        this(n, appLogger, n2);
    }
    
    public void create(final int n) throws ServerIPCException {
    }
    
    public void create(final int n, final int n2) throws ServerIPCException {
    }
    
    public void delete() throws ServerIPCException {
    }
    
    public void writeLast(final ubMsg ubMsg, final boolean b) {
    }
    
    public ubAppServerMsg getJavaFinish() {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 71, 0, 0, 0);
        ubAppServerMsg.setubSrc(4);
        ubAppServerMsg.setubRq(17);
        ubAppServerMsg.setubRsp(0);
        ubAppServerMsg.setMsgbuf(new byte[1], 1);
        ubAppServerMsg.setCsHeaders(0, ubAppServerMsg.getBuflen(), 71);
        return ubAppServerMsg;
    }
    
    public ubAppServerMsg getJavaDataResponse(final byte[] array, final int n, final boolean b) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 71, 0, 0, 0);
        ubAppServerMsg.setubSrc(4);
        ubAppServerMsg.setubRq(b ? 13 : 12);
        ubAppServerMsg.setubRsp(0);
        ubAppServerMsg.setMsgbuf(array, n);
        ubAppServerMsg.setCsHeaders(0, ubAppServerMsg.getBuflen(), 71);
        return ubAppServerMsg;
    }
    
    public ubAppServerMsg getJavaShutdownResponse(final short n) throws Exception {
        final byte[] array = new byte[5];
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(n, 31, 0, 0, 0);
        ubAppServerMsg.setubSrc(4);
        ubAppServerMsg.setubRq(8);
        ubAppServerMsg.setubRsp(0);
        ubAppServerMsg.setMsgbuf(array, array.length);
        ubAppServerMsg.setCsHeaders(0, ubAppServerMsg.getBuflen(), 31);
        return ubAppServerMsg;
    }
    
    public ubAppServerMsg getJavaDisconnResponse(final short n) throws Exception {
        final byte[] array = new byte[5];
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(n, 21, 0, 0, 0);
        ubAppServerMsg.setubSrc(4);
        ubAppServerMsg.setubRq(15);
        ubAppServerMsg.setubRsp(0);
        ubAppServerMsg.setMsgbuf(array, array.length);
        ubAppServerMsg.setCsHeaders(0, ubAppServerMsg.getBuflen(), 21);
        return ubAppServerMsg;
    }
    
    public ubAppServerMsg getJavaConnResponse(final String s, final byte b, final String s2, final short n) throws Exception {
        final byte[] bytes = s.getBytes("UTF8");
        byte[] bytes2 = null;
        int n2 = 2;
        if (s2 != null) {
            bytes2 = s2.getBytes("UTF8");
            n2 += bytes2.length + 1;
        }
        final byte[] array = new byte[3 + n2 + 1 + 4 + 4 + 2 + bytes.length + 1 + 2];
        final int n3 = 3;
        final int n4 = 3 + n2 + 1 + 4 + 4;
        array[0] = b;
        if (s2 != null) {
            ubAppServerMsg.setNetString(array, n3, bytes2);
        }
        ubAppServerMsg.setNetString(array, n4, bytes);
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(n, 11, 0, 0, 0);
        ubAppServerMsg.setubSrc(4);
        ubAppServerMsg.setubRq(14);
        ubAppServerMsg.setubRsp(0);
        ubAppServerMsg.setMsgbuf(array, array.length);
        ubAppServerMsg.setCsHeaders(0, ubAppServerMsg.getBuflen(), 11);
        if (System.getProperty("USE_V9_PROTOCOL") != null) {
            if (this.log.ifLogBasic(16384L, 14)) {
                this.log.logBasic(14, "Using V9 protocol.");
            }
            return ubAppServerMsg;
        }
        if (n != 108) {
            try {
                for (int i = 0; i < ubConstants.APPSRVCAPINFO_TYPE.length; ++i) {
                    ubAppServerMsg.appendTlvField(ubConstants.APPSRVCAPINFO_TYPE[i], ubConstants.APPSRVCAPINFO_VALUE[i]);
                }
                if (this.log.ifLogBasic(16384L, 14)) {
                    this.log.logBasic(14, "Using V10 protocol.");
                }
            }
            catch (Exception ex) {
                if (this.log.ifLogBasic(16384L, 14)) {
                    this.log.logBasic(14, "Using V9 protocol.");
                }
            }
        }
        else if (this.log.ifLogBasic(16384L, 14)) {
            this.log.logBasic(14, "Using V9 protocol.");
        }
        return ubAppServerMsg;
    }
    
    public void write(final ubMsg ubMsg, final boolean b) throws ServerIPCException {
        try {
            final ubAppServerMsg ubAppServerMsg = (ubAppServerMsg)ubMsg;
            final short n = (short)ubMsg.getubVer();
            switch (ubAppServerMsg.getubRq()) {
                case 4:
                case 5: {
                    if (this.serverState != 3) {
                        throw new Error("Bad state for write(): " + this.serverState);
                    }
                    final boolean b2 = ubAppServerMsg.getubRq() == 5;
                    int n2 = 1;
                    if (b2) {
                        n2 = (ubAppServerMsg.getubRqExt() & 0xFF00) >> 8;
                    }
                    if (n2 != 1) {
                        throw new Exception("ubServerThreadIPC.write(): Client request interrupted.");
                    }
                    this.currentServlet.write(ubMsg.getMsgbuf(), ubMsg.getBuflen(), 0, b2);
                    if (b2) {
                        this.serverState = 4;
                        break;
                    }
                    break;
                }
                case 16: {
                    if (this.serverState != 1) {
                        throw new Error("Bad state for init(): " + this.serverState);
                    }
                    (this.currentServlet = JavaServices.service.getServletByID(new InitRequest(ubMsg.getMsgbuf()).connId)).initRequest();
                    this.serverState = 3;
                    break;
                }
                case 3: {
                    if (this.serverState != 1) {
                        throw new Error("Bad state for connect(): " + this.serverState);
                    }
                    byte b3 = 0;
                    String string = null;
                    final connectionInfo connectionInfo = new connectionInfo(ubMsg.getMsgbuf());
                    String tlvField_NoThrow = null;
                    if (ubMsg.getubVer() > 108) {
                        tlvField_NoThrow = ubMsg.getTlvField_NoThrow((short)14);
                    }
                    if (tlvField_NoThrow == null) {
                        tlvField_NoThrow = "unknown";
                    }
                    try {
                        this.currentServlet = JavaServices.service.createConnectionServlet(connectionInfo.connId, connectionInfo.user, connectionInfo.password, connectionInfo.appinfo, new StopBroker(), connectionInfo.codePage, tlvField_NoThrow);
                    }
                    catch (Throwable t) {
                        if (!Exception.class.isAssignableFrom(t.getClass())) {
                            this.log.logStackTrace("createConnectionServlet: ", t);
                        }
                        b3 = 1;
                        string = t.toString();
                    }
                    this.javaResponse = this.getJavaConnResponse(connectionInfo.connId, b3, string, n);
                    this.serverState = 2;
                    break;
                }
                case 6: {
                    if (this.serverState != 1) {
                        throw new Error("Bad state for discconnect(): " + this.serverState);
                    }
                    try {
                        final InitRequest initRequest = new InitRequest(ubMsg.getMsgbuf());
                        this.currentServlet = JavaServices.service.getServletByID(initRequest.connId);
                        if (this.currentServlet != null) {
                            this.currentServlet.disconnect();
                            JavaServices.service.removeServletByID(initRequest.connId);
                        }
                    }
                    catch (Throwable t2) {
                        this.log.logStackTrace("currentServlet.disconnect(): ", t2);
                    }
                    this.javaResponse = this.getJavaDisconnResponse(n);
                    this.serverState = 2;
                    break;
                }
                case 8: {
                    if (this.serverState != 1) {
                        throw new Error("Bad state for shutdown(): " + this.serverState);
                    }
                    this.javaResponse = this.getJavaShutdownResponse(n);
                    this.serverState = 2;
                    break;
                }
                case 7: {
                    if (this.currentServlet != null) {
                        this.currentServlet.doStop();
                        break;
                    }
                    break;
                }
                default: {
                    this.log.logError("ubServerThreadIPC.write(): appMsg.getubRq() - unknown message type: " + new Integer(ubAppServerMsg.getubRq()).toString());
                    throw new Error("Unknwon message type");
                }
            }
        }
        catch (Exception ex) {
            this.log.logStackTrace("write(): ", ex);
            throw new Error(ex.toString());
        }
    }
    
    public ubMsg read() throws ServerIPCException {
        try {
            switch (this.serverState) {
                case 4: {
                    final byte[] read = this.currentServlet.read(this.how_manyH);
                    if (read == null) {
                        return null;
                    }
                    final int intValue = this.how_manyH.getIntValue();
                    final boolean b = intValue < read.length;
                    if (b) {
                        this.javaResponse = this.getJavaFinish();
                        this.serverState = 2;
                    }
                    return this.getJavaDataResponse(read, intValue, b);
                }
                case 2: {
                    this.setStateReady();
                    return this.javaResponse;
                }
                default: {
                    throw new Error("Bad state for read(): " + this.serverState);
                }
            }
        }
        catch (Exception ex) {
            this.log.logStackTrace("read(): ", ex);
            throw new Error(ex.toString());
        }
    }
    
    public int available() throws ServerIPCException {
        return 0;
    }
    
    private void setStateReady() {
        this.serverState = 1;
        this.currentServlet = null;
    }
    
    static class StopBroker implements StopInterface
    {
        public void doStop() {
        }
    }
    
    static class connectionInfo
    {
        String codePage;
        String user;
        String password;
        String appinfo;
        String connId;
        
        connectionInfo(final byte[] array) {
            final int n = 0;
            final short netShort = ubMsg.getNetShort(array, n);
            final int n2 = n + 2;
            final int n3 = n + (netShort + 2);
            final short netShort2 = ubMsg.getNetShort(array, n3);
            final int n4 = n3 + 2;
            final int n5 = n3 + (netShort2 + 2);
            final short netShort3 = ubMsg.getNetShort(array, n5);
            final int n6 = n5 + 2;
            final int n7 = n5 + (netShort3 + 2);
            final short netShort4 = ubMsg.getNetShort(array, n7);
            final int n8 = n7 + 2;
            final int n9 = n7 + (netShort4 + 2);
            final short netShort5 = ubMsg.getNetShort(array, n9);
            final int n10 = n9 + 2;
            this.codePage = ubMsg.newNetString(array, n2, netShort - 1);
            this.user = ubMsg.newNetString(array, n4, netShort2 - 1);
            this.password = ubMsg.newNetString(array, n6, netShort3 - 1);
            this.appinfo = ubMsg.newNetString(array, n8, netShort4 - 1);
            this.connId = ubMsg.newNetString(array, n10, netShort5 - 1);
        }
    }
    
    static class InitRequest
    {
        String codePage;
        String connId;
        
        InitRequest(final byte[] array) {
            final int n = 0;
            final short netShort = ubMsg.getNetShort(array, n);
            final int n2 = n + 2;
            final int n3 = n + (netShort + 2);
            final short netShort2 = ubMsg.getNetShort(array, n3);
            final int n4 = n3 + 2;
            this.codePage = ubMsg.newNetString(array, n2, netShort - 1);
            this.connId = ubMsg.newNetString(array, n4, netShort2 - 1);
        }
    }
}
