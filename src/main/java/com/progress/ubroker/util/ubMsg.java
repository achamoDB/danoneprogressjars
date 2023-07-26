// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.exception.ProException;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import com.progress.common.ehnlog.IAppLogger;
import java.io.UnsupportedEncodingException;

public abstract class ubMsg implements ubConstants
{
    public static final int UBHDRLEN = 20;
    public static final int OFST_UBVER = 0;
    public static final int OFST_UBTYPE = 2;
    public static final int OFST_UBSRC = 3;
    public static final int OFST_UBRQ = 4;
    public static final int OFST_UBRQEXT = 8;
    public static final int OFST_UBTLVLEN = 8;
    public static final int OFST_UBRSP = 12;
    public static final int OFST_UBRSPEXT = 16;
    public static final short UBMSG_PROTOCOL_V0 = 108;
    public static final short UBMSG_PROTOCOL_V1 = 109;
    public static final short MIN_UBVER = 108;
    public static final short MAX_UBVER = 109;
    public static final short DEF_TLVBUFLEN = 0;
    public static final byte UBTYPE_INVALID = 0;
    public static final byte UBTYPE_ADMIN = 1;
    public static final byte UBTYPE_APPSERVER = 2;
    public static final byte UBTYPE_WEBSPEED = 3;
    public static final byte UBTYPE_NAMESERVER = 4;
    public static final byte UBTYPE_ADAPTER = 5;
    public static final byte UBTYPE_AIA = 6;
    public static final byte MAX_UBTYPE = 7;
    public static final String[] DESC_UBTYPE;
    public static final byte DEF_UBSRC = 0;
    public static final byte UBSRC_BROKER = 1;
    public static final byte UBSRC_NAMESERVER = 2;
    public static final byte UBSRC_CLIENT = 3;
    public static final byte UBSRC_SERVER = 4;
    public static final byte UBSRC_ADAPTER = 5;
    public static final byte UBSRC_AIA = 6;
    public static final String[] DESC_UBSRC;
    public static final int DEF_UBRQ = 0;
    public static final int UBRQ_ADMIN = 1;
    public static final int UBRQ_XID = 2;
    public static final int UBRQ_CONNECT = 3;
    public static final int UBRQ_WRITEDATA = 4;
    public static final int UBRQ_WRITEDATALAST = 5;
    public static final int UBRQ_DISCONNECT = 6;
    public static final int UBRQ_SETSTOP = 7;
    public static final int UBRQ_SHUTDOWN = 8;
    public static final int UBRQ_SRVR_STATE_CHG = 9;
    public static final int UBRQ_SRVR_LOG_MSG = 10;
    public static final int UBRQ_CONNECT_DIRECT = 11;
    public static final int UBRQ_RSPDATA = 12;
    public static final int UBRQ_RSPDATALAST = 13;
    public static final int UBRQ_RSPCONN = 14;
    public static final int UBRQ_RSPDISC = 15;
    public static final int UBRQ_INIT_RQ = 16;
    public static final int UBRQ_FINISH_RQ = 17;
    public static final int UBRQ_BROKER_STATUS = 18;
    public static final int UBRQ_BROKER_STATUS_RSP = 19;
    public static final int UBRQ_SEND_EMPTY_MSG = 20;
    public static final int UBRQ_RSPXID = 21;
    public static final int UBRQ_ASKPING_RQ = 22;
    public static final int UBRQ_ASKPING_RSP = 23;
    public static final String[] DESC_UBRQ;
    public static final int DEF_UBRQEXT = 0;
    public static final int UBRQEXT_BOUND = 1;
    public static final int UBRQEXT_SUMMARY_STATUS = 0;
    public static final int UBRQEXT_DETAIL_STATUS = 1;
    public static final int UBRQEXT_NEED_NEW_CONNID = 1;
    public static final int DEF_UBRSP = 0;
    public static final int UBRSP_OK = 0;
    public static final int UBRSP_ERROR = 1;
    public static final int UBRSP_PROTOCOL_ERROR = 2;
    public static final int UBRSP_UNSUPPORTED_RQ = 3;
    public static final int UBRSP_IOEXCEPTION = 4;
    public static final int UBRSP_BROKEREXCEPTION = 5;
    public static final int UBRSP_SERVERIPCEXCEPTION = 6;
    public static final int UBRSP_NO_AVAILABLE_SERVERS = 7;
    public static final int UBRSP_MSGFORMATEXCEPTION = 8;
    public static final int UBRSP_ABNORMAL_EOF = 9;
    public static final int UBRSP_FATAL = 10;
    public static final int UBRSP_CONN_REFUSED = 11;
    public static final int UBRSP_TOO_MANY_CLIENTS = 12;
    public static final int UBRSP_INVALID_SERVERMODE = 13;
    public static final String[] DESC_UBRSP;
    public static final String[] DESC_UBRSP_EXT;
    public static final int DEF_UBRSPEXT = 0;
    public static final int UBRSPEXT_BOUND = 1;
    public static final int UBRSPEXT_CONNID_SENT = 2;
    public static final int UBRSPEXT_BINMAXSIZE_AWARE = 4;
    public static final String[] DESC_UBRSPEXT;
    public static final int DEF_BUFSIZE = 0;
    public static final int DEF_BUFLEN = 0;
    public static final short TLVTYPE_RQID = 1;
    public static final short TLVTYPE_UNCMPRLEN = 2;
    public static final short TLVTYPE_CMPRLEVEL = 3;
    public static final short TLVTYPE_CMPRMINSZ = 4;
    public static final short TLVTYPE_CMPRCAPABLE = 5;
    public static final short TLVTYPE_WHATEVER = 6;
    public static final short TLVTYPE_CLIENT_CODEPAGE = 7;
    public static final short TLVTYPE_CONNECTION_ID = 8;
    public static final short TLVTYPE_CLIENT_CONTEXT = 9;
    public static final short TLVTYPE_ASKPING_VER = 10;
    public static final short TLVTYPE_ASKPING_CAPS = 11;
    public static final short TLVTYPE_ASKPING_RQST_ACK = 12;
    public static final short TLVTYPE_LG_HEADER = 13;
    public static final short TLVTYPE_CLIENT_HOSTNAME = 14;
    public static final short TLVTYPE_UB_MSG_VERS = 2001;
    public static final short TLVTYPE_CSNET_VERS = 2002;
    public static final short TLVTYPE_CSNET_MSG_VERS = 2003;
    public static final short TLVTYPE_OE_MAJOR_VERS = 2004;
    public static final short TLVTYPE_OE_MINOR_VERS = 2005;
    public static final short TLVTYPE_OE_MAINT_VERS = 2006;
    public static final short TLVTYPE_STREAM_VERS = 2007;
    public static final short TLVTYPE_APSVCL_VERS = 3001;
    public static final String[] DESC_TLVTYPES;
    public static final int INVALID_ASK_VERSION = 0;
    public static final int ASK_MAJOR_VER = 1;
    public static final int ASK_MINOR_VER = 0;
    public static final int ASK_VERSION = 65536;
    public static final int ASK_DISABLED = 0;
    public static final int ASK_SERVERASK_ENABLED = 1;
    public static final int ASK_CLIENTASK_ENABLED = 2;
    public static final int ASK_DEFAULT = 0;
    private byte[] ubmsghdr;
    private byte[] tlvbuf;
    private int buflen;
    private byte[] msgbuf;
    
    public ubMsg(final short n, final byte b) {
        this.initUBMsg(n, b, 0);
    }
    
    public ubMsg(final short n, final byte b, final int n2) {
        this.initUBMsg(n, b, n2);
    }
    
    public ubMsg(final byte[] ubmsghdr, final byte[] array) throws InvalidMsgVersionException {
        this.ubmsghdr = ubmsghdr;
        switch (this.getubVer()) {
            case 108: {
                if (array != null) {
                    throw new InvalidMsgVersionException("tlvbuf not valid for ver= (0x" + Integer.toString(108, 16) + ")");
                }
                this.tlvbuf = array;
                break;
            }
            default: {
                this.tlvbuf = array;
                short n;
                if (this.tlvbuf == null) {
                    n = 0;
                }
                else {
                    n = (short)this.tlvbuf.length;
                }
                this.setubTlvBuflen(n);
                break;
            }
        }
        this.buflen = 0;
        this.msgbuf = new byte[0];
    }
    
    public static byte[] newNetByteArray(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return new byte[0];
        }
        byte[] array;
        try {
            array = s.getBytes("UTF8");
        }
        catch (UnsupportedEncodingException ex) {
            array = s.getBytes();
        }
        return array;
    }
    
    public static byte[] newTlvByteArray(final String s) {
        final byte[] netByteArray = newNetByteArray(s);
        final byte[] array = new byte[(netByteArray == null) ? 2 : (netByteArray.length + 3)];
        setTlvString(array, 0, netByteArray);
        return array;
    }
    
    public static int setTlvString(final byte[] array, final int n, final String s) {
        return setTlvString(array, n, newNetByteArray(s));
    }
    
    public static int setTlvString(final byte[] array, int n, final byte[] array2) {
        final int n2 = (array2 == null) ? 0 : (array2.length + 1);
        setNetShort(array, n, (short)n2);
        n += 2;
        if (array2 != null) {
            final int length = array2.length;
            System.arraycopy(array2, 0, array, n, length);
            array[n + length] = 0;
            n += n2;
        }
        return n;
    }
    
    public static String getTlvString(final byte[] array, int n) {
        final short netShort = getNetShort(array, n);
        n += 2;
        return newNetString(array, n, netShort - 1);
    }
    
    public static int skipTlvString(final byte[] array, int n) {
        n += getNetShort(array, n) + 2;
        return n;
    }
    
    public static String getTlvField(final byte[] array, final short n, final short i) throws TlvFieldNotFoundException, InvalidMsgVersionException, InvalidTlvBufferException {
        for (int j = 0; j < n; j = skipTlvString(array, j)) {
            final int n2 = getNetShort(array, j) & 0xFFFFFF;
            j += 2;
            if (n2 == i) {
                return getTlvString(array, j);
            }
        }
        throw new TlvFieldNotFoundException((i < ubMsg.DESC_TLVTYPES.length) ? ubMsg.DESC_TLVTYPES[i] : ("UNKNOWN TLVTYPE (" + i + ")"));
    }
    
    public static String newNetString(final byte[] array, final int n, final int n2) {
        String s = null;
        if (n2 > 0) {
            try {
                s = new String(array, n, n2, "UTF8");
            }
            catch (UnsupportedEncodingException ex) {
                s = new String(array, n, n2);
            }
        }
        return s;
    }
    
    public static int checkubVer(final byte[] array) throws InvalidMsgVersionException, InvalidHeaderLenException {
        if (array.length < 2) {
            throw new InvalidHeaderLenException("len=(" + array.length + ")");
        }
        final short netShort = getNetShort(array, 0);
        if (netShort < 108 || netShort > 109) {
            throw new InvalidMsgVersionException("got ver= (0x" + Integer.toString(netShort, 16) + ")" + ": expecting ver=" + " (0x" + Integer.toString(108, 16) + " - " + "0x" + Integer.toString(109, 16) + ")");
        }
        return netShort;
    }
    
    public static int getubType(final byte[] array) throws InvalidMsgVersionException, InvalidHeaderLenException, InvalidServerTypeException {
        if (array.length != 20) {
            throw new InvalidHeaderLenException("len=(" + array.length + ")");
        }
        checkubVer(array);
        final byte i = array[2];
        if (i > 7) {
            throw new InvalidServerTypeException("serverType=(" + i + ")");
        }
        return i;
    }
    
    public static short getubTlvBuflen(final byte[] array) throws InvalidMsgVersionException, InvalidHeaderLenException {
        final int checkubVer = checkubVer(array);
        switch (checkubVer) {
            case 108: {
                throw new InvalidMsgVersionException("tlvbuf not valid for ver= (0x" + Integer.toString(checkubVer, 16) + ")");
            }
            default: {
                return getNetShort(array, 8);
            }
        }
    }
    
    public static void setNetShort(final byte[] array, final int n, final short n2) {
        int i = 1;
        int n3 = n2;
        while (i >= 0) {
            array[n + i] = (byte)(n3 & 0xFF);
            n3 >>= 8;
            --i;
        }
    }
    
    public static void setNetInt(final byte[] array, final int n, final int n2) {
        int i = 3;
        int n3 = n2;
        while (i >= 0) {
            array[n + i] = (byte)(n3 & 0xFF);
            n3 >>= 8;
            --i;
        }
    }
    
    public static short getNetShort(final byte[] array, final int n) {
        int i = 0;
        int n2 = 0;
        while (i < 2) {
            n2 = (n2 << 8 | (array[n + i] & 0xFF));
            ++i;
        }
        return (short)n2;
    }
    
    public static int getNetInt(final byte[] array, final int n) {
        int i = 0;
        int n2 = 0;
        while (i < 4) {
            n2 = (n2 << 8 | (array[n + i] & 0xFF));
            ++i;
        }
        return n2;
    }
    
    public static String getubRspDesc(final int i) {
        return (i < 0 || i > ubMsg.DESC_UBRSP_EXT.length) ? new String("error=" + i) : new String(ubMsg.DESC_UBRSP_EXT[i]);
    }
    
    public boolean getNeedNewConnID() {
        return (this.getubRqExt() & 0x1) > 0;
    }
    
    public static ubMsg newMsg(final byte[] buf, final int offset, final int length, final IAppLogger appLogger) throws IOException, MsgFormatException {
        return new MsgInputStream(new ByteArrayInputStream(buf, offset, length), 10240, 0, appLogger).readMsg();
    }
    
    public void set_ubhdr(final short n, final byte b, final byte b2, final int n2, final int n3, final int n4, final int n5) {
        setNetShort(this.ubmsghdr, 0, n);
        this.ubmsghdr[2] = b;
        this.ubmsghdr[3] = b2;
        setNetInt(this.ubmsghdr, 4, n2);
        setNetInt(this.ubmsghdr, 8, n3);
        setNetInt(this.ubmsghdr, 12, n4);
        setNetInt(this.ubmsghdr, 16, n5);
    }
    
    public int getubVer() {
        return getNetShort(this.ubmsghdr, 0);
    }
    
    public byte[] getubhdr() {
        return this.ubmsghdr;
    }
    
    public void setubhdr(final byte[] ubmsghdr) {
        this.ubmsghdr = ubmsghdr;
    }
    
    public void setubVer(final int n) {
        setNetShort(this.ubmsghdr, 0, (short)n);
    }
    
    public int getubType() {
        return this.ubmsghdr[2];
    }
    
    public void setubType(final int n) {
        this.ubmsghdr[2] = (byte)n;
    }
    
    public int getubSrc() {
        return this.ubmsghdr[3];
    }
    
    public void setubSrc(final int n) {
        this.ubmsghdr[3] = (byte)n;
    }
    
    public int getubRq() {
        return getNetInt(this.ubmsghdr, 4);
    }
    
    public void setubRq(final int n) {
        setNetInt(this.ubmsghdr, 4, n);
    }
    
    public int getubRqExt() {
        int n = 0;
        switch (this.getubVer()) {
            case 108: {
                n = getNetInt(this.ubmsghdr, 8);
                break;
            }
            default: {
                n = getNetShort(this.ubmsghdr, 10);
                break;
            }
        }
        return n;
    }
    
    public void setubRqExt(final int n) {
        switch (this.getubVer()) {
            case 108: {
                setNetInt(this.ubmsghdr, 8, n);
                break;
            }
            default: {
                setNetShort(this.ubmsghdr, 10, (short)n);
                break;
            }
        }
    }
    
    public int getubRsp() {
        return getNetInt(this.ubmsghdr, 12);
    }
    
    public void setubRsp(final int n) {
        setNetInt(this.ubmsghdr, 12, n);
    }
    
    public int getubRspExt() {
        return getNetInt(this.ubmsghdr, 16);
    }
    
    public void setubRspExt(final int n) {
        setNetInt(this.ubmsghdr, 16, n);
    }
    
    public byte[] getubTlvBuf() throws InvalidMsgVersionException, InvalidTlvBufferException {
        final int validateTlvBuffer = this.validateTlvBuffer();
        switch (validateTlvBuffer) {
            case 108: {
                throw new InvalidMsgVersionException("tlvbuf not valid for ver= (0x" + Integer.toString(validateTlvBuffer, 16) + ")");
            }
            default: {
                return this.tlvbuf;
            }
        }
    }
    
    public void setubTlvBuf(final byte[] tlvbuf) throws InvalidMsgVersionException, InvalidTlvBufferException {
        final int validateTlvBuffer = this.validateTlvBuffer();
        switch (validateTlvBuffer) {
            case 108: {
                throw new InvalidMsgVersionException("tlvbuf not valid for ver= (0x" + Integer.toString(validateTlvBuffer, 16) + ")");
            }
            default: {
                this.tlvbuf = tlvbuf;
                this.setubTlvBuflen((short)tlvbuf.length);
            }
        }
    }
    
    public void resetubTlvBuf() throws InvalidMsgVersionException, InvalidTlvBufferException {
        this.setubTlvBuf(new byte[0]);
    }
    
    public short getubTlvBuflen() throws InvalidMsgVersionException, InvalidTlvBufferException {
        final int validateTlvBuffer = this.validateTlvBuffer();
        switch (validateTlvBuffer) {
            case 108: {
                throw new InvalidMsgVersionException("tlvbuf not valid for ver= (0x" + Integer.toString(validateTlvBuffer, 16) + ")");
            }
            default: {
                return getNetShort(this.ubmsghdr, 8);
            }
        }
    }
    
    public int validateTlvBuffer() throws InvalidTlvBufferException {
        final int getubVer = this.getubVer();
        switch (getubVer) {
            case 108: {
                if (this.tlvbuf != null) {
                    throw new InvalidTlvBufferException("tlvbuf is not null ... length=" + this.tlvbuf.length);
                }
                break;
            }
            default: {
                final short netShort = getNetShort(this.ubmsghdr, 8);
                if ((this.tlvbuf == null && netShort != 0) || (this.tlvbuf != null && this.tlvbuf.length != netShort)) {
                    throw new InvalidTlvBufferException("tlvbuf.length(" + this.tlvbuf.length + ")" + " != tlvbuflen(" + netShort + ")");
                }
                break;
            }
        }
        return getubVer;
    }
    
    public String getTlvField(final short n) throws TlvFieldNotFoundException, InvalidMsgVersionException, InvalidTlvBufferException {
        return getTlvField(this.getubTlvBuf(), this.getubTlvBuflen(), n);
    }
    
    public String getTlvField_NoThrow(final short n) {
        byte[] getubTlvBuf;
        short getubTlvBuflen;
        try {
            getubTlvBuf = this.getubTlvBuf();
            getubTlvBuflen = this.getubTlvBuflen();
        }
        catch (Exception ex) {
            return null;
        }
        return getTlvField_NoThrow(getubTlvBuf, getubTlvBuflen, n);
    }
    
    public String remTlvField_NoThrow(final short n) {
        byte[] getubTlvBuf;
        short getubTlvBuflen;
        try {
            getubTlvBuf = this.getubTlvBuf();
            getubTlvBuflen = this.getubTlvBuflen();
        }
        catch (Exception ex) {
            return null;
        }
        return this.remTlvField_NoThrow(getubTlvBuf, getubTlvBuflen, n);
    }
    
    public short appendTlvField(final short n, final String s) throws TlvFieldAlreadyExistsException, InvalidMsgVersionException, InvalidTlvBufferException {
        final byte[] getubTlvBuf = this.getubTlvBuf();
        final short getubTlvBuflen = this.getubTlvBuflen();
        try {
            getTlvField(getubTlvBuf, getubTlvBuflen, n);
            throw new TlvFieldAlreadyExistsException(ubMsg.DESC_TLVTYPES[n]);
        }
        catch (TlvFieldNotFoundException ex) {
            final byte[] tlvByteArray = newTlvByteArray(s);
            this.appendTlvBuf(n, (short)tlvByteArray.length, tlvByteArray);
            return this.getubTlvBuflen();
        }
    }
    
    public byte[] appendTlvBuf(final short n, final short n2, final byte[] array) throws InvalidMsgVersionException, InvalidTlvBufferException {
        final short getubTlvBuflen = this.getubTlvBuflen();
        if (getubTlvBuflen + n2 + 2 > this.tlvbuf.length) {
            final byte[] array2 = new byte[getubTlvBuflen + n2 + 2];
            System.arraycopy(this.tlvbuf, 0, array2, 0, getubTlvBuflen);
            setNetShort(array2, getubTlvBuflen, n);
            System.arraycopy(array, 0, array2, getubTlvBuflen + 2, n2);
            this.setubTlvBuf(array2);
        }
        else {
            setNetShort(this.tlvbuf, getubTlvBuflen, n);
            System.arraycopy(array, 0, this.tlvbuf, getubTlvBuflen + 2, n2);
            this.setubTlvBuflen((short)(getubTlvBuflen + n2 + 2));
        }
        return this.tlvbuf;
    }
    
    public boolean testubRspExtBits(final int n) {
        return (this.getubRspExt() & n) > 0;
    }
    
    public void setubRspExtBits(final int n) {
        this.setubRspExt(this.getubRspExt() | n);
    }
    
    public boolean cmpMsg(final ubMsg ubMsg) {
        boolean b = this.getubVer() == ubMsg.getubVer() && this.getubSrc() == ubMsg.getubSrc() && this.getubRq() == ubMsg.getubRq() && this.getubRqExt() == ubMsg.getubRqExt() && this.getubRsp() == ubMsg.getubRsp() && this.getubRspExt() == ubMsg.getubRspExt() && this.getBuflen() == ubMsg.getBuflen();
        if (b) {
            for (int buflen = this.getBuflen(), n = 0; b && n < buflen; b = (this.msgbuf[n] == ubMsg.msgbuf[n]), ++n) {}
        }
        return b;
    }
    
    public void print____this_isnt_called(final String x) {
        System.err.println(x);
        System.err.println(" version= " + this.getubVer());
        System.err.println(" src= " + this.getubSrc() + " " + ubMsg.DESC_UBSRC[this.getubSrc()]);
        System.err.println(" rq= 0x" + Integer.toString(this.getubRq(), 16) + " " + ubMsg.DESC_UBRQ[this.getubRq()]);
        System.err.println(" rqExt= 0x" + Integer.toString(this.getubRqExt(), 16));
        System.err.println(" rsp= 0x" + Integer.toString(this.getubRsp(), 16) + " " + ubMsg.DESC_UBRSP[this.getubRsp()]);
        System.err.println(" rspExt= 0x" + Integer.toString(this.getubRspExt(), 16));
        this.printSrvHeader();
        final int buflen = this.getBuflen();
        System.err.println(" buflen= " + buflen);
        if (buflen > 0) {
            System.err.print(" msgbuf= ");
            for (int i = 0; i < buflen; ++i) {
                System.err.print((char)this.msgbuf[i]);
            }
            System.err.println("");
        }
    }
    
    public void print(final String s, final int n, final int n2, final IAppLogger appLogger) {
        int getubTlvBuflen = 0;
        final int getubVer = this.getubVer();
        appLogger.logWithThisLevel(n, n2, s);
        appLogger.logWithThisLevel(n, n2, " version= " + getubVer);
        appLogger.logWithThisLevel(n, n2, " type= " + this.getubType() + " " + ubMsg.DESC_UBTYPE[this.getubType()]);
        appLogger.logWithThisLevel(n, n2, " src= " + this.getubSrc() + " " + ubMsg.DESC_UBSRC[this.getubSrc()]);
        appLogger.logWithThisLevel(n, n2, " rq= 0x" + Integer.toString(this.getubRq(), 16) + " " + ubMsg.DESC_UBRQ[this.getubRq()]);
        try {
            getubTlvBuflen = this.getubTlvBuflen();
            appLogger.logWithThisLevel(n, n2, " tlvBuflen= 0x" + Integer.toString(getubTlvBuflen, 16));
        }
        catch (MsgFormatException ex) {}
        appLogger.logWithThisLevel(n, n2, " rqExt= 0x" + Integer.toString(this.getubRqExt(), 16));
        appLogger.logWithThisLevel(n, n2, " rsp= 0x" + Integer.toString(this.getubRsp(), 16) + " " + ubMsg.DESC_UBRSP[this.getubRsp()]);
        appLogger.logWithThisLevel(n, n2, " rspExt= 0x" + Integer.toString(this.getubRspExt(), 16));
        if (getubTlvBuflen > 0) {
            appLogger.logDump(n, n2, " tlvbuf[" + getubTlvBuflen + "]= ", this.tlvbuf, getubTlvBuflen);
        }
        this.printSrvHeader(n, n2, appLogger);
        final int buflen = this.getBuflen();
        appLogger.logWithThisLevel(n, n2, " buflen= " + buflen);
        if (buflen > 0) {
            appLogger.logDump(n, n2, " msgbuf[" + buflen + "]= ", this.msgbuf, buflen);
        }
    }
    
    public int getBuflen() {
        return this.buflen;
    }
    
    public void setBuflen(final int buflen) {
        this.buflen = buflen;
    }
    
    public byte[] getMsgbuf() {
        return this.msgbuf;
    }
    
    public byte[] setMsgbuf(final byte[] msgbuf) {
        return this.msgbuf = msgbuf;
    }
    
    public byte[] setMsgbuf(final String s) {
        this.msgbuf = s.getBytes();
        this.setBuflen(this.msgbuf.length);
        return this.msgbuf;
    }
    
    public byte[] setMsgbuf(final byte[] msgbuf, final int buflen) {
        this.msgbuf = msgbuf;
        this.setBuflen(buflen);
        return this.msgbuf;
    }
    
    public byte[] appendMsgbuf(final byte[] array, final int n) {
        final int buflen = this.getBuflen();
        if (buflen + n > this.getMsgbuf().length) {
            final byte[] array2 = new byte[buflen + n];
            System.arraycopy(this.msgbuf, 0, array2, 0, buflen);
            System.arraycopy(array, 0, array2, buflen, n);
            this.setMsgbuf(array2, buflen + n);
        }
        else {
            System.arraycopy(array, 0, this.getMsgbuf(), buflen, n);
            this.setBuflen(buflen + n);
        }
        return this.msgbuf;
    }
    
    public byte[] appendMsgbuf(final int n) {
        final int buflen = this.getBuflen();
        final int n2 = 4;
        if (buflen + n2 > this.getMsgbuf().length) {
            final byte[] array = new byte[buflen + n2];
            System.arraycopy(this.msgbuf, 0, array, 0, buflen);
            setNetInt(array, buflen, n);
            this.setMsgbuf(array, buflen + n2);
        }
        else {
            setNetInt(this.msgbuf, buflen, n);
            this.setBuflen(buflen + n2);
        }
        return this.msgbuf;
    }
    
    public String getubRqDesc() {
        final int netInt = getNetInt(this.ubmsghdr, 4);
        String string;
        try {
            string = ubMsg.DESC_UBRQ[netInt];
        }
        catch (Exception ex) {
            string = "[RQ= 0x" + Integer.toString(netInt, 16) + "]";
        }
        return string;
    }
    
    public byte[] serializeMsg() {
        short getubTlvBuflen;
        try {
            getubTlvBuflen = this.getubTlvBuflen();
        }
        catch (MsgFormatException ex) {
            getubTlvBuflen = 0;
        }
        final int srvHeaderlen = this.getSrvHeaderlen();
        final byte[] array = new byte[20 + getubTlvBuflen + srvHeaderlen + this.buflen];
        System.arraycopy(this.ubmsghdr, 0, array, 0, 20);
        if (this.tlvbuf != null) {
            System.arraycopy(this.tlvbuf, 0, array, 20, getubTlvBuflen);
        }
        System.arraycopy(this.getSrvHeader(), 0, array, 20 + getubTlvBuflen, srvHeaderlen);
        System.arraycopy(this.msgbuf, 0, array, 20 + getubTlvBuflen + srvHeaderlen, this.buflen);
        return array;
    }
    
    public ByteBuffer wrapMsg(final ByteBuffer byteBuffer) {
        int getubTlvBuflen;
        try {
            getubTlvBuflen = this.getubTlvBuflen();
        }
        catch (MsgFormatException ex) {
            getubTlvBuflen = 0;
        }
        byteBuffer.put(this.ubmsghdr, 0, 20);
        if (this.tlvbuf != null) {
            byteBuffer.put(this.tlvbuf, 0, getubTlvBuflen);
        }
        byteBuffer.put(this.getSrvHeader(), 0, this.getSrvHeaderlen());
        byteBuffer.put(this.msgbuf, 0, this.buflen);
        return byteBuffer;
    }
    
    public abstract byte[] getSrvHeader();
    
    public abstract int getSrvHeaderlen();
    
    public abstract void printSrvHeader();
    
    public abstract void printSrvHeader(final int p0, final int p1, final IAppLogger p2);
    
    private void initUBMsg(final short n, final byte b, final int n2) {
        setNetShort(this.ubmsghdr = new byte[20], 0, n);
        this.ubmsghdr[2] = b;
        switch (n) {
            case 108: {
                this.tlvbuf = null;
                break;
            }
            default: {
                this.tlvbuf = new byte[0];
                this.setubTlvBuflen((short)this.tlvbuf.length);
                break;
            }
        }
        this.buflen = 0;
        this.msgbuf = new byte[n2];
    }
    
    private void setubTlvBuflen(final short n) {
        setNetShort(this.ubmsghdr, 8, n);
    }
    
    public static String getTlvField_NoThrow(final byte[] array, final short n, final short n2) {
        for (int i = 0; i < n; i = skipTlvString(array, i)) {
            final int n3 = getNetShort(array, i) & 0xFFFF;
            i += 2;
            if (n3 == n2) {
                return getTlvString(array, i);
            }
        }
        return null;
    }
    
    public String remTlvField_NoThrow(final byte[] array, final short n, final short n2) {
        for (int i = 0; i < n; i = skipTlvString(array, i)) {
            final int n3 = getNetShort(array, i) & 0xFFFF;
            i += 2;
            if (n3 == n2) {
                final short n4 = (short)(getNetShort(this.ubmsghdr, 8) - (getNetShort(array, i) + 4));
                final String tlvString = getTlvString(array, i);
                final int skipTlvString = skipTlvString(array, i);
                System.arraycopy(array, skipTlvString, array, i - 2, n - skipTlvString);
                final byte[] tlvbuf = new byte[n4];
                System.arraycopy(array, 0, tlvbuf, 0, n4);
                this.tlvbuf = tlvbuf;
                this.setubTlvBuflen(n4);
                return tlvString;
            }
        }
        return null;
    }
    
    static {
        DESC_UBTYPE = new String[] { "UBTYPE_INVALID", "UBTYPE_ADMIN", "UBTYPE_APPSERVER", "UBTYPE_WEBSPEED", "UBTYPE_NAMESERVER", "UBTYPE_ADAPTER", "UBTYPE_AIA" };
        DESC_UBSRC = new String[] { "DEFAULT_UBSRC", "UBSRC_BROKER", "UBSRC_NAMESERVER", "UBSRC_CLIENT", "UBSRC_SERVER", "UBSRC_ADAPTER", "UBSRC_AIA" };
        DESC_UBRQ = new String[] { "DEF_UBRQ", "UBRQ_ADMIN", "UBRQ_XID", "UBRQ_CONNECT", "UBRQ_WRITEDATA", "UBRQ_WRITEDATALAST", "UBRQ_DISCONNECT", "UBRQ_SETSTOP", "UBRQ_SHUTDOWN", "UBRQ_SRVR_STATE_CHG", "UBRQ_SRVR_LOG_MSG", "UBRQ_CONNECT_DIRECT", "UBRQ_RSPDATA", "UBRQ_RSPDATALAST", "UBRQ_RSPCONN", "UBRQ_RSPDISC", "UBRQ_INIT_RQ", "UBRQ_FINISH_RQ", "UBRQ_BROKER_STATUS", "UBRQ_BROKER_STATUS_RSP", "UBRQ_SEND_EMPTY_MSG", "UBRQ_RSPXID", "UBRQ_ASKPING_RQ", "UBRQ_ASKPING_RSP" };
        DESC_UBRSP = new String[] { "UBRSP_OK", "UBRSP_ERROR", "UBRSP_PROTOCOL_ERROR", "UBRSP_UNSUPPORTED_UBRQ", "UBRSP_IOEXCEPTION", "UBRSP_BROKEREXCEPTION", "UBRSP_SERVERIPCEXCEPTION", "UBRSP_NO_AVAILABLE_SERVERS", "UBRSP_MSGFORMATEXCEPTION", "UBRSP_ABNORMAL_EOF", "UBRSP_FATAL", "UBRSP_CONN_REFUSED", "UBRSP_TOO_MANY_CLIENTS", "UBRSP_INVALID_SERVERMODE" };
        DESC_UBRSP_EXT = new String[] { "Ok", "Unspecified Error", "Protocol Error", "Unsupported Request", "IO Exception", "Broker Exception", "ServerIPC Exception", "No Available Servers", "Invalid Message Format", "Abnormal End Of File from Server", "Fatal Error from Server", "Server refused connection", "Max client connections has been reached", "client operatingMode does not match broker operatingMode" };
        DESC_UBRSPEXT = new String[] { "DEF_UBRSPEXT" };
        DESC_TLVTYPES = new String[] { "TLVT_RQID", "TLVT_UNCMPRLEN", "TLVT_CMPRLEVEL", "TLVT_CMPRMINSZ", "TLVT_CMPRCAPABLE", "TLVT_WHATEVER", "TLVT_CLIENT_CODEPAGE", "TLVT_CONNECTION_ID", "TLVT_CLIENT_CONTEXT", "TLVT_ASKPING_VER", "TLVT_ASKPING_CAPS", "TLVT_ASKPING_RQST_ACK", "TLVT_LG_HEADER", "TLVT_CLIENT_HOSTNAME", "TLVT_UB_MSG_VERS", "TLVT_CSNET_VERS", "TLVT_CSNET_MSG_VERS", "TLVT_OE_MAJOR_VERS", "TLVT_OE_MINOR_VERS", "TLVT_OE_MAINT_VERS", "TLVT_STREAM_VERS", "TLVT_APSVCL_VERS" };
    }
    
    public static class MsgFormatException extends ProException
    {
        public MsgFormatException(final String s) {
            super("MsgFormat", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class InvalidMsgVersionException extends MsgFormatException
    {
        public InvalidMsgVersionException(final String str) {
            super("InvalidMsgVersion[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class InvalidHeaderLenException extends MsgFormatException
    {
        public InvalidHeaderLenException(final String str) {
            super("InvalidHeaderLen[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class InvalidServerTypeException extends MsgFormatException
    {
        public InvalidServerTypeException(final String str) {
            super("InvalidServerType[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class InvalidTlvBufferException extends MsgFormatException
    {
        public InvalidTlvBufferException(final String str) {
            super("InvalidTlvBuffer[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class WrongServerTypeException extends MsgFormatException
    {
        public WrongServerTypeException(final String str) {
            super("WrongServerType[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class TlvAccessException extends ProException
    {
        public TlvAccessException(final String s) {
            super("TlvAccessException", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class TlvFieldNotFoundException extends TlvAccessException
    {
        public TlvFieldNotFoundException(final String str) {
            super("TlvFieldNotFound[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class TlvFieldAlreadyExistsException extends TlvAccessException
    {
        public TlvFieldAlreadyExistsException(final String str) {
            super("TlvFieldAlreadyExists[" + str + "]");
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
}
