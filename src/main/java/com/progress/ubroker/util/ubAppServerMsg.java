// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.ehnlog.IAppLogger;

public class ubAppServerMsg extends ubMsg implements ubConstants
{
    private static final int CSHDRLEN = 12;
    public static final int CSMSSGHDRLEN = 4;
    private static final int OFST_CSNETVER = 0;
    private static final int OFST_SEQNUM = 2;
    private static final int OFST_MSGLEN = 6;
    private static final int OFST_CSMSSGVER = 8;
    private static final int OFST_HANDLE = 10;
    private static final int OFST_MSGCODE = 11;
    private static final short CUR_CSNET_VER = 102;
    private static final int DEF_SEQNUM = 0;
    private static final short DEF_MSGLEN = 0;
    private static final short CUR_CSMSSG_VER = 102;
    private static final byte DEF_HANDLE = 0;
    public static final int CSMSSG_CONNECT = 10;
    public static final int CSMSSG_CONNECT_ACK = 11;
    public static final int CSMSSG_DISCONN = 20;
    public static final int CSMSSG_DISCONN_ACK = 21;
    public static final int CSMSSG_SHUTDOWN = 30;
    public static final int CSMSSG_SHUTDOWN_ACK = 31;
    public static final int CSMSSG_STOP = 40;
    public static final int CSMSSG_OPEN4GL = 70;
    public static final int CSMSSG_OPEN4GL_ACK = 71;
    public static final int CSMSSG_CLIENT_CONNECT = 80;
    public static final int CSMSSG_CLIENT_DISCONNECT = 81;
    public static final int CSMSSG_PROCSTATS = 82;
    public static final int CSMSSG_ADMIN = 90;
    public static final int CSMSSG_ADMIN_ACK = 91;
    public static final byte DEF_MSGCODE = 70;
    public static final int[] CSMSSG_MSGCODES;
    public static final String[] DESC_MSGCODE;
    private static final int OFST_4GL_COND_CODE = 0;
    private static final int OFST_4GL_ERROR_CODE = 1;
    private static final int OFST_4GL_ERR_MSG = 3;
    public static final byte CS_COND_NONE = 0;
    public static final byte CS_COND_ERROR = 1;
    public static final byte CS_COND_STOP = 2;
    public static final byte CS_COND_QUIT = 3;
    public static final byte CS_COND_DEBUG = 4;
    public static final byte CS_CONN_OKTODEBUG = 2;
    public static final byte CSO_STREAM_VERSION = 82;
    public static final byte CSO_STREAM_TAG_TABLE = 1;
    public static final byte CSO_STREAM_TAG_RECORD = 2;
    public static final byte CSO_STREAM_TAG_UNKNOWN = 3;
    public static final byte CSO_STREAM_TAG_TODAY = 4;
    public static final byte CSO_STREAM_TAG_2BYTELEN = 5;
    public static final byte CSO_STREAM_TAG_0FLD = 6;
    public static final byte CSO_STREAM_TAG_ERROR = 7;
    public static final byte CSO_STREAM_TAG_NORMAL = 8;
    private byte[] csmsghdr;
    
    public ubAppServerMsg(final short n) {
        super(n, (byte)2);
        this.initCSMsg((short)102, 0, (short)0, (short)102, (byte)0, (byte)70);
    }
    
    public ubAppServerMsg(final short n, final int n2) {
        super(n, (byte)2, n2);
        this.initCSMsg((short)102, 0, (short)0, (short)102, (byte)0, (byte)70);
    }
    
    public ubAppServerMsg(final short n, final int n2, final int n3, final int n4, final int n5) {
        super(n, (byte)2, n5);
        this.initCSMsg((short)102, n3, (short)0, (short)102, (byte)0, (byte)70);
        this.setCsHeaders(n3, n4, n2);
    }
    
    public ubAppServerMsg(final byte[] array, final byte[] array2, final byte[] csmsghdr) throws InvalidMsgVersionException {
        super(array, array2);
        this.csmsghdr = csmsghdr;
    }
    
    public static byte[] newNetByteArray(final String s) {
        final byte[] netByteArray = ubMsg.newNetByteArray(s);
        final byte[] array = new byte[(netByteArray == null) ? 2 : (netByteArray.length + 3)];
        setNetString(array, 0, netByteArray);
        return array;
    }
    
    public static int setNetString(final byte[] array, final int n, final String s) {
        return setNetString(array, n, ubMsg.newNetByteArray(s));
    }
    
    public static int setNetString(final byte[] array, int n, final byte[] array2) {
        final int n2 = (array2 == null) ? 0 : (array2.length + 1);
        ubMsg.setNetShort(array, n, (short)n2);
        n += 2;
        if (array2 != null) {
            final int length = array2.length;
            System.arraycopy(array2, 0, array, n, length);
            array[n + length] = 0;
            n += n2;
        }
        return n;
    }
    
    public static String getNetString(final byte[] array, int n) {
        final short netShort = ubMsg.getNetShort(array, n);
        n += 2;
        return ubMsg.newNetString(array, n, netShort - 1);
    }
    
    public static int skipNetString(final byte[] array, int n) {
        n += ubMsg.getNetShort(array, n) + 2;
        return n;
    }
    
    public static byte[] csmssgRspbuf(final int n, final int n2, final String s) {
        final int n3 = (s == null) ? 0 : (s.getBytes().length + 1);
        final byte[] array = new byte[n3 + 5];
        array[0] = (byte)n;
        ubMsg.setNetShort(array, 1, (short)n2);
        ubMsg.setNetShort(array, 3, (short)n3);
        if (s != null) {
            System.arraycopy(s.getBytes(), 0, array, 5, s.getBytes().length);
            array[array.length - 1] = 0;
        }
        return array;
    }
    
    public static byte[] csmssgErrorRspbuf(final byte b, final String s, final String s2) {
        final int n = (s == null) ? 0 : s.getBytes().length;
        final int n2 = (s2 == null) ? 0 : s2.getBytes().length;
        final byte[] array = new byte[27 + n + n2];
        array[0] = 82;
        ubMsg.setNetShort(array, 1, (short)(24 + n + n2));
        array[3] = 7;
        array[4] = 1;
        ubMsg.setNetShort(array, 5, (short)0);
        array[7] = 2;
        array[8] = 5;
        ubMsg.setNetShort(array, 9, (short)1);
        array[11] = b;
        array[13] = (array[12] = 6);
        array[14] = 5;
        ubMsg.setNetShort(array, 15, (short)1);
        array[17] = 1;
        array[18] = 5;
        ubMsg.setNetShort(array, 19, (short)n);
        if (n > 0) {
            System.arraycopy(s.getBytes(), 0, array, 21, n);
        }
        array[21 + n] = 5;
        ubMsg.setNetShort(array, 22 + n, (short)n2);
        if (n2 > 0) {
            System.arraycopy(s.getBytes(), 0, array, 24 + n, n2);
        }
        array[24 + n2 + n] = 1;
        ubMsg.setNetShort(array, 25 + n + n2, (short)0);
        return array;
    }
    
    public static int getSrvHdrlen() {
        return 12;
    }
    
    public byte[] getcshdr() {
        return this.csmsghdr;
    }
    
    public void setcshdr(final byte[] csmsghdr) {
        this.csmsghdr = csmsghdr;
    }
    
    public void setCsHeaders(final int seqnum, int msglen, final int n) {
        this.setCsnetVer((short)102);
        this.setSeqnum(seqnum);
        msglen += 4;
        this.setMsglen(msglen);
        this.setCsmssgVer((short)102);
        this.setHandle((byte)0);
        this.setMsgcode((byte)n);
    }
    
    public short getCsnetVer() {
        return ubMsg.getNetShort(this.csmsghdr, 0);
    }
    
    public void setCsnetVer(final short n) {
        ubMsg.setNetShort(this.csmsghdr, 0, n);
    }
    
    public int getSeqnum() {
        return ubMsg.getNetInt(this.csmsghdr, 2);
    }
    
    public void setSeqnum(final int n) {
        ubMsg.setNetInt(this.csmsghdr, 2, n);
    }
    
    public int getMsglen() {
        return ubMsg.getNetShort(this.csmsghdr, 6);
    }
    
    public void setMsglen(final int n) {
        ubMsg.setNetShort(this.csmsghdr, 6, (short)n);
    }
    
    public short getCsmssgVer() {
        return ubMsg.getNetShort(this.csmsghdr, 8);
    }
    
    public void setCsmssgVer(final short n) {
        ubMsg.setNetShort(this.csmsghdr, 8, n);
    }
    
    public byte getHandle() {
        return this.csmsghdr[10];
    }
    
    public void setHandle(final byte b) {
        this.csmsghdr[10] = b;
    }
    
    public byte getMsgcode() {
        return this.csmsghdr[11];
    }
    
    public void setMsgcode(final byte b) {
        this.csmsghdr[11] = b;
    }
    
    public byte get4GLCondCode() {
        return this.getMsgbuf()[0];
    }
    
    public void set4GLCondCode(final byte b) {
        this.getMsgbuf()[0] = b;
    }
    
    public byte get4GLErrCode() {
        return this.getMsgbuf()[1];
    }
    
    public void set4GLErrCode(final short n) {
        ubMsg.setNetShort(this.getMsgbuf(), 1, n);
    }
    
    public String get4GLErrMsg() {
        return getNetString(this.getMsgbuf(), 3);
    }
    
    public byte get4GLConnAckFlags() {
        final byte[] msgbuf = this.getMsgbuf();
        return msgbuf[5 + ubMsg.getNetShort(msgbuf, 3)];
    }
    
    public void set4GLConnAckFlags(final byte b) {
        final byte[] msgbuf = this.getMsgbuf();
        msgbuf[5 + ubMsg.getNetShort(msgbuf, 3)] = b;
    }
    
    public boolean cmpMsg(final ubAppServerMsg ubAppServerMsg) {
        boolean cmpMsg = super.cmpMsg(ubAppServerMsg);
        if (cmpMsg) {
            cmpMsg = (this.getCsnetVer() == ubAppServerMsg.getCsnetVer() && this.getMsglen() == ubAppServerMsg.getMsglen() && this.getCsmssgVer() == ubAppServerMsg.getCsmssgVer() && this.getHandle() == ubAppServerMsg.getHandle() && this.getMsgcode() == ubAppServerMsg.getMsgcode());
        }
        return cmpMsg;
    }
    
    public void printSrvHeader() {
        System.err.println(" CSNET ver= " + this.getCsnetVer());
        System.err.println(" seqnum= " + this.getSeqnum());
        System.err.println(" msglen= " + this.getMsglen());
        System.err.println(" CSMSSG ver= " + this.getCsmssgVer());
        System.err.println(" handle= " + this.getHandle());
        System.err.println(" msgcode= " + this.getMsgcode() + " " + this.msgcodeDesc(this.getMsgcode()));
    }
    
    public void printSrvHeader(final int n, final int n2, final IAppLogger appLogger) {
        appLogger.logWithThisLevel(n, n2, " CSNET ver= " + this.getCsnetVer());
        appLogger.logWithThisLevel(n, n2, " seqnum= " + this.getSeqnum());
        appLogger.logWithThisLevel(n, n2, " msglen= " + this.getMsglen());
        appLogger.logWithThisLevel(n, n2, " CSMSSG ver= " + this.getCsmssgVer());
        appLogger.logWithThisLevel(n, n2, " handle= " + this.getHandle());
        appLogger.logWithThisLevel(n, n2, " msgcode= " + this.getMsgcode() + " " + this.msgcodeDesc(this.getMsgcode()));
    }
    
    public byte[] getSrvHeader() {
        return this.getcshdr();
    }
    
    public int getSrvHeaderlen() {
        return 12;
    }
    
    private void initCSMsg(final short n, final int n2, final short n3, final short n4, final byte b, final byte b2) {
        ubMsg.setNetShort(this.csmsghdr = new byte[12], 0, n);
        ubMsg.setNetInt(this.csmsghdr, 2, n2);
        ubMsg.setNetShort(this.csmsghdr, 6, n3);
        ubMsg.setNetShort(this.csmsghdr, 8, n4);
        this.csmsghdr[10] = b;
        this.csmsghdr[11] = b2;
    }
    
    private String msgcodeDesc(final int n) {
        int n2;
        String s;
        for (n2 = 0, s = ubAppServerMsg.DESC_MSGCODE[n2]; s != null && n != ubAppServerMsg.CSMSSG_MSGCODES[n2]; s = ubAppServerMsg.DESC_MSGCODE[++n2]) {}
        return s;
    }
    
    static {
        CSMSSG_MSGCODES = new int[] { 10, 11, 70, 71, 20, 21, 30, 31, 40, 80, 81, 82, 90, 91 };
        DESC_MSGCODE = new String[] { "CSMSSG_CONNECT", "CSMSSG_CONNECT_ACK", "CSMSSG_OPEN4GL", "CSMSSG_OPEN4GL_ACK", "CSMSSG_DISCONN", "CSMSSG_DISCONN_ACK", "CSMSSG_SHUTDOWN", "CSMSSG_SHUTDOWN_ACK", "CSMSSG_STOP", "CSMSSG_CLIENT_CONNECT", "CSMSSG_CLIENT_DISCONNECT", "CSMSSG_PROCSTATS", "CSMSSG_ADMIN", "CSMSSG_ADMIN_ACK", null };
    }
}
