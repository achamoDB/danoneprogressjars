// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.EOFException;
import java.nio.channels.SelectionKey;
import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import com.progress.common.ehnlog.IAppLogger;

public class UbMsgChannel implements IUbMsgChannel, IubMsgInputStream, IubMsgOutputStream
{
    IAppLogger applog;
    int streamLoggingLevel;
    long entrytype;
    int serverType;
    int index_entry_type;
    String entryTypeName;
    String logEnvID;
    SocketChannel channel;
    ByteBuffer channelBuffer;
    private int m_msgBufferSize;
    private byte[] m_peekBuf;
    private boolean m_bufferedByte;
    
    public UbMsgChannel(final SocketChannel channel, final int capacity, final IAppLogger applog, final int streamLoggingLevel, final long entrytype, final int index_entry_type) {
        this.m_msgBufferSize = 10240;
        this.m_peekBuf = new byte[1];
        this.m_bufferedByte = false;
        this.channel = channel;
        this.channelBuffer = ByteBuffer.allocateDirect(capacity);
        this.applog = applog;
        this.streamLoggingLevel = streamLoggingLevel;
        this.entrytype = entrytype;
        this.index_entry_type = index_entry_type;
        this.entryTypeName = this.applog.getLogContext().getEntrytypeName(this.index_entry_type);
        this.logEnvID = this.applog.getExecEnvId();
    }
    
    public synchronized int available() {
        final Object blockingLock = this.channel.blockingLock();
        String str = "";
        int n = 0;
        synchronized (blockingLock) {
            try {
                str = "configureBlocking(false)";
                this.channel.configureBlocking(false);
                str = "Selector.open()";
                final Selector open = Selector.open();
                str = "channel.register(OP_READ)";
                final SelectionKey register = this.channel.register(open, 1);
                str = "selector.selectNow()";
                if (open.selectNow() > 0 && register.isReadable()) {
                    n = 1;
                }
                str = "selector.close()";
                open.close();
            }
            catch (IOException obj) {
                if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
                    this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, str + " error : " + obj);
                }
                n = 0;
                try {
                    this.channel.configureBlocking(true);
                }
                catch (IOException obj2) {
                    if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
                        this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, "error resetting channel to blocking mode : " + obj2);
                    }
                }
            }
            finally {
                try {
                    this.channel.configureBlocking(true);
                }
                catch (IOException obj3) {
                    if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
                        this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, "error resetting channel to blocking mode : " + obj3);
                    }
                }
            }
        }
        return n;
    }
    
    public synchronized boolean socketIsConnected() {
        if (this.m_bufferedByte) {
            return true;
        }
        boolean b;
        if (this.available() != 0) {
            try {
                this.readChannel(this.m_peekBuf, 0, 1);
                this.m_bufferedByte = true;
                b = true;
            }
            catch (IOException ex) {
                b = false;
            }
        }
        else {
            b = true;
        }
        return b;
    }
    
    public void setMsgBufferSize(final int msgBufferSize) throws Exception {
        this.m_msgBufferSize = msgBufferSize;
    }
    
    public int getMsgBufferSize() {
        return this.m_msgBufferSize;
    }
    
    public void setLoggingTraceLevel(final int streamLoggingLevel) throws Exception {
        this.streamLoggingLevel = streamLoggingLevel;
    }
    
    public int getLoggingTraceLevel() {
        return this.streamLoggingLevel;
    }
    
    public void flushMsg() {
    }
    
    public synchronized ubMsg readMsg() throws IOException, ubMsg.MsgFormatException {
        this.channelBuffer.clear();
        if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
            this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, "readMsg()");
        }
        final byte[] readubhdr = this.readubhdr();
        final byte[] readtlvbuf = this.readtlvbuf(readubhdr);
        final int getubType = ubMsg.getubType(readubhdr);
        ubConstants ubConstants = null;
        int getwsMsglen = 0;
        switch (getubType) {
            case 2: {
                if (this.serverType != 0 && this.serverType != 4 && this.serverType != 6 && this.serverType != 7 && this.serverType != 2 && this.serverType != 3 && this.serverType != 5) {
                    if (this.applog != null) {
                        this.applog.logError(7665970990714724715L, new Object[] { ubMsg.DESC_UBTYPE[getubType], com.progress.ubroker.util.ubConstants.STRING_SERVER_TYPES[this.serverType] });
                    }
                    throw new ubMsg.WrongServerTypeException("ServerType=(" + ubMsg.getubType(readubhdr) + ") not supported for this broker");
                }
                ubConstants = new ubAppServerMsg(readubhdr, readtlvbuf, this.readsrvhdr(ubAppServerMsg.getSrvHdrlen()));
                getwsMsglen = ((ubAppServerMsg)ubConstants).getMsglen() - 4;
                break;
            }
            case 3: {
                if (this.serverType != 1) {
                    if (this.applog != null) {
                        this.applog.logError(7665970990714724715L, new Object[] { ubMsg.DESC_UBTYPE[getubType], com.progress.ubroker.util.ubConstants.STRING_SERVER_TYPES[this.serverType] });
                    }
                    throw new ubMsg.WrongServerTypeException("ServerType=(" + ubMsg.getubType(readubhdr) + ") not supported for this broker");
                }
                ubConstants = new ubWebSpeedMsg(readubhdr, readtlvbuf, this.readsrvhdr(ubWebSpeedMsg.getSrvHdrlen()));
                getwsMsglen = ((ubWebSpeedMsg)ubConstants).getwsMsglen();
                break;
            }
            default: {
                throw new ubMsg.InvalidServerTypeException("ServerType=(" + ubMsg.getubType(readubhdr) + ") not supported");
            }
        }
        if (getwsMsglen > 0) {
            this.readMsgbuf((ubMsg)ubConstants, getwsMsglen);
        }
        return (ubMsg)ubConstants;
    }
    
    public synchronized void writeMsg(final ubMsg ubMsg) throws IOException {
        this.logMsg("writeMsg()", ubMsg);
        this.channelBuffer.clear();
        (this.channelBuffer = ubMsg.wrapMsg(this.channelBuffer)).flip();
        while (this.channelBuffer.hasRemaining()) {
            this.channel.write(this.channelBuffer);
        }
    }
    
    public void close() throws IOException {
        try {
            this.channel.close();
        }
        catch (IOException ex) {
            if (this.applog != null && this.applog.ifLogIt(2, this.entrytype, this.index_entry_type)) {
                this.applog.logWriteMessage(2, 2, this.logEnvID, this.entryTypeName, "Closing channel: " + ex.getMessage());
            }
        }
    }
    
    private byte[] readubhdr() throws IOException, ubMsg.InvalidMsgVersionException, ubMsg.InvalidHeaderLenException {
        final byte[] array = new byte[20];
        this.readChannel(array, 0, 2);
        ubMsg.checkubVer(array);
        this.readChannel(array, 2, array.length - 2);
        if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "readubhdr", array, array.length);
        }
        return array;
    }
    
    private byte[] readtlvbuf(final byte[] array) throws IOException {
        byte[] array2 = null;
        try {
            final short getubTlvBuflen = ubMsg.getubTlvBuflen(array);
            if (getubTlvBuflen > 0) {
                array2 = new byte[getubTlvBuflen];
                this.readChannel(array2, 0, getubTlvBuflen);
            }
            if (getubTlvBuflen > 0 && this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
                this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "readtlvbuf", array2, array2.length);
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            array2 = null;
        }
        return array2;
    }
    
    private byte[] readsrvhdr(final int n) throws IOException {
        final byte[] array = new byte[n];
        this.readChannel(array, 0, n);
        if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "readsrvhdr", array, n);
        }
        return array;
    }
    
    private int readMsgbuf(final ubMsg ubMsg, final int i) throws IOException {
        final byte[] array = new byte[i];
        this.readChannel(array, 0, i);
        if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "readMsgbuf[" + i + "]", array, i);
        }
        ubMsg.setMsgbuf(array, i);
        return i;
    }
    
    private void readChannel(final byte[] dst, int offset, int n) throws IOException {
        this.channelBuffer.clear();
        if (this.m_bufferedByte) {
            dst[offset++] = this.m_peekBuf[0];
            this.m_bufferedByte = false;
            if (--n == 0) {
                return;
            }
        }
        this.channelBuffer.limit(n);
        while (this.channelBuffer.hasRemaining()) {
            int read;
            try {
                read = this.channel.read(this.channelBuffer);
            }
            catch (IOException ex) {
                if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
                    this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, "read() IOException in readChannel : " + ex.getMessage());
                }
                throw ex;
            }
            if (read == -1) {
                throw new EOFException();
            }
        }
        this.channelBuffer.flip();
        this.channelBuffer.get(dst, offset, n);
    }
    
    private void logMsg(final String s, final ubMsg ubMsg) {
        final int buflen = ubMsg.getBuflen();
        if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
            this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, s);
            this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "writeubhdr", ubMsg.getubhdr(), 20);
            this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "writeSrvHeader", ubMsg.getSrvHeader(), ubMsg.getSrvHeaderlen());
            try {
                final byte[] getubTlvBuf = ubMsg.getubTlvBuf();
                if (getubTlvBuf != null) {
                    this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "writetlvbuf", getubTlvBuf, ubMsg.getubTlvBuflen());
                }
            }
            catch (ubMsg.MsgFormatException ex) {}
            this.applog.logDump(this.streamLoggingLevel, this.index_entry_type, "writeMsgbuf[" + buflen + "]", ubMsg.getMsgbuf(), buflen);
        }
    }
    
    private void setBlockingMode(final SocketChannel obj, final boolean block) throws IOException {
        try {
            obj.configureBlocking(block);
        }
        catch (IOException obj2) {
            if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
                this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, "error setting channel (" + obj + ") to " + (block ? "" : "non-") + "blocking mode : " + obj2);
            }
            throw obj2;
        }
    }
    
    private Selector openSelector() throws IOException {
        Selector open;
        try {
            open = Selector.open();
        }
        catch (IOException obj) {
            if (this.applog != null && this.applog.ifLogIt(this.streamLoggingLevel, this.entrytype, this.index_entry_type)) {
                this.applog.logWriteMessage(2, this.streamLoggingLevel, this.logEnvID, this.entryTypeName, "error opening selector " + obj);
            }
            throw obj;
        }
        return open;
    }
}
