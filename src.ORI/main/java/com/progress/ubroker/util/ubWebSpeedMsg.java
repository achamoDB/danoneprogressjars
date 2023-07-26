// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.ehnlog.IAppLogger;

public class ubWebSpeedMsg extends ubMsg
{
    private static final int WSHDRLEN = 20;
    private static final int OFST_WHO = 0;
    private static final int OFST_MSGTYPE = 4;
    private static final int OFST_TIMESTAMP = 8;
    private static final int OFST_MSGLEN = 12;
    private static final int OFST_VERSION = 16;
    public static final int WEB_NOBODY = 0;
    public static final int WEB_PROGRESS = 1;
    public static final int WEB_CGIIP = 2;
    public static final int WEB_WSISA = 4;
    public static final int WEB_WSNSA = 8;
    public static final int WEB_MSNGR = 16;
    public static final int WEB_WTB = 32;
    public static final int WEB_WSASP = 64;
    public static final int WEB_WTBMAN = 256;
    public static final int DEF_MSGTYPE = 0;
    public static final int WEB_FORM_INPUT = 1;
    public static final int WEB_CUR_ENV_STRING = 2;
    public static final int WEB_PRG_TO_WTB_ALIVE = 3;
    public static final int WEB_CGIIP_GET_PROG = 4;
    public static final int WEB_PRG_TO_WTB_AV = 5;
    public static final int WEB_PRG_TO_WTB_BU = 6;
    public static final int WEB_PRG_TO_WTB_LI = 7;
    public static final int WEB_PRG_TO_WTB_LO = 8;
    public static final int WEB_SHUTDOWN = 9;
    public static final int WEB_MAN_TO_WTB_START = 10;
    public static final int WEB_MAN_TO_WTB_SHUT = 11;
    public static final int WEB_MAN_TO_WTB_STOP = 12;
    public static final int WEB_STATUS = 13;
    public static final int WEB_PRG_TO_WTB_LOG = 14;
    public static final int WEB_MAN_TO_WTB_STOPPID = 15;
    public static final int WEB_RECONNECT = 16;
    public static final int WEB_CUR_ENV_STRING_10 = 17;
    public static final int WEB_ADMIN = 18;
    public static final int DEF_TIMESTAMP = 0;
    public static final int DEF_MSGLEN = 0;
    private static final int CUR_WS_VERSION = 9010;
    private byte[] wsmsghdr;
    
    public ubWebSpeedMsg(final short n) {
        super(n, (byte)3);
        this.initWSMsg(0, 0, 0, 0, 9010);
    }
    
    public ubWebSpeedMsg(final short n, final int n2) {
        super(n, (byte)3, n2);
        this.initWSMsg(0, 0, 0, 0, 9010);
    }
    
    public ubWebSpeedMsg(final byte[] array, final byte[] array2, final byte[] wsmsghdr) throws InvalidMsgVersionException {
        super(array, array2);
        this.wsmsghdr = wsmsghdr;
    }
    
    public static int setNetString(final byte[] array, int n, final String s) {
        final int n2 = (s == null) ? 0 : s.length();
        if (s != null) {
            System.arraycopy(s.getBytes(), 0, array, n, n2);
        }
        n += n2;
        array[n] = 0;
        return n;
    }
    
    public static String getNetString(final byte[] bytes, final int offset) {
        int length;
        for (length = 0; bytes[offset + length] != 0; ++length) {}
        return (length == 0) ? null : new String(bytes, offset, length);
    }
    
    public static int getSrvHdrlen() {
        return 20;
    }
    
    public byte[] getwshdr() {
        return this.wsmsghdr;
    }
    
    public void setwshdr(final byte[] wsmsghdr) {
        this.wsmsghdr = wsmsghdr;
    }
    
    public void setwsHeader(final int n, final int n2, final int n3) {
        this.setwsWho(n);
        this.setwsMsgtype(n2);
        this.setwsTimestamp(0);
        this.setwsMsglen(n3);
        this.setwsVersion(9010);
    }
    
    public int getwsWho() {
        return ubMsg.getNetInt(this.wsmsghdr, 0);
    }
    
    public void setwsWho(final int n) {
        ubMsg.setNetInt(this.wsmsghdr, 0, n);
    }
    
    public int getwsMsgtype() {
        return ubMsg.getNetInt(this.wsmsghdr, 4);
    }
    
    public void setwsMsgtype(final int n) {
        ubMsg.setNetInt(this.wsmsghdr, 4, n);
    }
    
    public int getwsTimestamp() {
        return ubMsg.getNetInt(this.wsmsghdr, 8);
    }
    
    public void setwsTimestamp(final int n) {
        ubMsg.setNetInt(this.wsmsghdr, 8, n);
    }
    
    public int getwsMsglen() {
        return ubMsg.getNetInt(this.wsmsghdr, 12);
    }
    
    public void setwsMsglen(final int n) {
        ubMsg.setNetInt(this.wsmsghdr, 12, (short)n);
    }
    
    public int getwsVersion() {
        return ubMsg.getNetInt(this.wsmsghdr, 16);
    }
    
    public void setwsVersion(final int n) {
        ubMsg.setNetInt(this.wsmsghdr, 16, n);
    }
    
    public boolean cmpMsg(final ubWebSpeedMsg ubWebSpeedMsg) {
        boolean cmpMsg = super.cmpMsg(ubWebSpeedMsg);
        if (cmpMsg) {
            cmpMsg = (this.getwsWho() == ubWebSpeedMsg.getwsWho() && this.getwsMsgtype() == ubWebSpeedMsg.getwsMsgtype() && this.getwsTimestamp() == ubWebSpeedMsg.getwsTimestamp() && this.getwsMsglen() == ubWebSpeedMsg.getwsMsglen() && this.getwsVersion() == ubWebSpeedMsg.getwsVersion());
        }
        return cmpMsg;
    }
    
    public void printSrvHeader() {
        System.err.println(" who= " + this.getwsWho());
        System.err.println(" msgtype= " + this.getwsMsgtype());
        System.err.println(" timestamp= " + this.getwsTimestamp());
        System.err.println(" msglen= " + this.getwsMsglen());
        System.err.println(" version= " + this.getwsVersion());
    }
    
    public void printSrvHeader(final int n, final int n2, final IAppLogger appLogger) {
        appLogger.logWithThisLevel(n, n2, " who= " + this.getwsWho());
        appLogger.logWithThisLevel(n, n2, " msgtype= " + this.getwsMsgtype());
        appLogger.logWithThisLevel(n, n2, " timestamp= " + this.getwsTimestamp());
        appLogger.logWithThisLevel(n, n2, " msglen= " + this.getwsMsglen());
        appLogger.logWithThisLevel(n, n2, " version= " + this.getwsVersion());
    }
    
    public byte[] getSrvHeader() {
        return this.getwshdr();
    }
    
    public int getSrvHeaderlen() {
        return 20;
    }
    
    private void initWSMsg(final int n, final int n2, final int n3, final int n4, final int n5) {
        ubMsg.setNetInt(this.wsmsghdr = new byte[20], 0, n);
        ubMsg.setNetInt(this.wsmsghdr, 4, n2);
        ubMsg.setNetInt(this.wsmsghdr, 8, n3);
        ubMsg.setNetInt(this.wsmsghdr, 12, n4);
        ubMsg.setNetInt(this.wsmsghdr, 16, n5);
    }
}
