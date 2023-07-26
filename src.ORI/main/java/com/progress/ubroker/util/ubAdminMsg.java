// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.ehnlog.IAppLogger;

public class ubAdminMsg extends ubMsg
{
    public static final byte DEF_SRC = 0;
    private static final int ADMSGHDRLEN = 10;
    private static final int OFST_SRC = 0;
    private static final int OFST_RQ = 1;
    private static final int OFST_RSP = 2;
    private static final int OFST_MSGLEN = 6;
    public static final byte DEF_ADSRC = 0;
    public static final byte ADSRC_LISTNER = 1;
    public static final byte ADSRC_WATCHDOG = 2;
    public static final byte ADSRC_SERVER = 3;
    public static final byte ADSRC_CLIENT = 4;
    public static final byte ADSRC_ADMIN = 5;
    public static final String[] DESC_ADSRC;
    public static final byte DEF_ADRQ = 0;
    public static final byte ADRQ_THREAD_STARTUP = 1;
    public static final byte ADRQ_THREAD_SHUTDOWN = 2;
    public static final byte ADRQ_THREAD_TERMINATE = 3;
    public static final byte ADRQ_SERVER_TERMINATE = 4;
    public static final byte ADRQ_CLIENT_CONNECT = 5;
    public static final byte ADRQ_CLIENT_DISCONNECT = 6;
    public static final byte ADRQ_IOEXCEPTION = 6;
    public static final byte ADRQ_THREAD_WAKEUP = 7;
    public static final byte ADRQ_TIMEOUT = 8;
    public static final byte ADRQ_PROCSTATS = 9;
    public static final byte ADRQ_ASK_ACTIVITY_TIMEOUT = 10;
    public static final byte ADRQ_ASK_RESPONSE_TIMEOUT = 11;
    public static final byte ADRQ_PROPERTY_FILE_UPDATE = 12;
    public static final byte ADRQ_MESSAGE_FORMAT_ERROR = 13;
    public static final byte ADRQ_SOCKET_TIMEOUT = 14;
    public static final byte ADRQ_NETWORK_PROTOCOL_ERROR = 15;
    public static final String[] DESC_ADRQ;
    public static final int DEF_ADRSP = 0;
    public static final int ADRSP_OK = 0;
    public static final int ADRSP_ERROR = 1;
    public static final int ADRSP_UNABLE_TO_START_SERVER = 2;
    public static final int ADRSP_UNABLE_TO_STOP_SERVER = 3;
    public static final int ADRSP_UNABLE_TO_INIT_CLIENT = 4;
    public static final String[] DESC_ADRSP;
    private static final int DEF_MSGLEN = 0;
    private byte[] admsghdr;
    private Object[] admparm;
    
    public ubAdminMsg() {
        super((short)109, (byte)1);
        this.initADMsg((byte)0, (byte)0, 0, 0);
    }
    
    public ubAdminMsg(final int n) {
        super((short)109, (byte)1, n);
        this.initADMsg((byte)0, (byte)0, 0, 0);
    }
    
    public ubAdminMsg(final byte b) {
        super((short)109, (byte)1);
        this.initADMsg((byte)0, (byte)0, 0, 0);
        this.setubRq(1);
        this.setadRq(b);
    }
    
    public byte[] getadMsghdr() {
        return this.admsghdr;
    }
    
    public void setadMsghdr(final byte[] admsghdr) {
        this.admsghdr = admsghdr;
    }
    
    public byte getadSrc() {
        return this.admsghdr[0];
    }
    
    public void setadSrc(final byte b) {
        this.admsghdr[0] = b;
    }
    
    public byte getadRq() {
        return this.admsghdr[1];
    }
    
    public String getadDesc() {
        final byte getadRq = this.getadRq();
        String string;
        try {
            string = ubAdminMsg.DESC_ADRQ[getadRq];
        }
        catch (Exception ex) {
            string = "[ADRQ= 0x" + Integer.toString(getadRq, 16) + "]";
        }
        return string;
    }
    
    public void setadRq(final byte b) {
        this.admsghdr[1] = b;
    }
    
    public int getadRsp() {
        return ubMsg.getNetInt(this.admsghdr, 2);
    }
    
    public void setadRsp(final int n) {
        ubMsg.setNetInt(this.admsghdr, 2, n);
    }
    
    public int getMsglen() {
        return ubMsg.getNetInt(this.admsghdr, 6);
    }
    
    public void setMsglen(final int n) {
        ubMsg.setNetInt(this.admsghdr, 6, n);
    }
    
    public boolean cmpMsgHdr(final ubMsg ubMsg) {
        final ubAdminMsg ubAdminMsg = (ubAdminMsg)ubMsg;
        return this.getadSrc() == ubAdminMsg.getadSrc() && this.getadRq() == ubAdminMsg.getadRq() && this.getadRsp() == ubAdminMsg.getadRsp() && this.getMsglen() == ubAdminMsg.getMsglen();
    }
    
    public void printSrvHeader() {
        System.err.println(" src= " + this.getadSrc());
        System.err.println(" adRq= " + this.getadRq() + " " + ubAdminMsg.DESC_ADRQ[this.getadRq()]);
        System.err.println(" adRq= " + this.getadRsp() + " " + ubAdminMsg.DESC_ADRSP[this.getadRsp()]);
        System.err.println(" msglen= " + this.getMsglen());
    }
    
    public void printSrvHeader(final int n, final int n2, final IAppLogger appLogger) {
        appLogger.logWithThisLevel(n, n2, " src= " + this.getadSrc());
        appLogger.logWithThisLevel(n, n2, " adRq= " + this.getadRq() + " " + ubAdminMsg.DESC_ADRQ[this.getadRq()]);
        appLogger.logWithThisLevel(n, n2, " rsp= " + this.getadRsp() + " " + ubAdminMsg.DESC_ADRSP[this.getadRsp()]);
        appLogger.logWithThisLevel(n, n2, " msglen= " + this.getMsglen());
    }
    
    public byte[] getSrvHeader() {
        return this.getadMsghdr();
    }
    
    public int getSrvHeaderlen() {
        return 10;
    }
    
    public Object[] getadParm() {
        return this.admparm;
    }
    
    public void setadParm(final Object[] admparm) {
        this.admparm = admparm;
    }
    
    private void initADMsg(final byte b, final byte b2, final int n, final int n2) {
        (this.admsghdr = new byte[10])[0] = b;
        this.admsghdr[1] = b2;
        ubMsg.setNetInt(this.admsghdr, 2, n);
        ubMsg.setNetInt(this.admsghdr, 6, n2);
    }
    
    static {
        DESC_ADSRC = new String[] { "DEFAULT_ADSRC", "ADSRC_LISTENER", "ADSRC_WATCHDOG", "ADSRC_SERVER", "ADSRC_CLIENT" };
        DESC_ADRQ = new String[] { "DEF_ADRQ", "ADRQ_THREAD_STARTUP", "ADRQ_THREAD_SHUTDOWN", "ADRQ_THREAD_TERMINATE", "ADRQ_SERVER_TERMINATE", "ADRQ_CLIENT_CONNECT", "ADRQ_CLIENT_DISCONNECT", "ADRQ_THREAD_WAKEUP", "ADRQ_TIMEOUT", "ADRQ_PROCSTATS", "ADRQ_ASK_ACTIVITY_TIMEOUT", "ADRQ_ASK_RESPONSE_TIMEOUT", "ADRQ_PROPERTY_FILE_UPDATE", "ADRQ_READMSG_ERROR", "ADRQ_SOCKET_TIMEOUT", "ADRQ_NET_PROTOCOL_ERROR" };
        DESC_ADRSP = new String[] { "ADRSP_OK", "ADRSP_ERROR", "ADRSP_UNABLE_TO_START_SERVER", "ADRSP_UNABLE_TO_STOP_SERVER" };
    }
}
