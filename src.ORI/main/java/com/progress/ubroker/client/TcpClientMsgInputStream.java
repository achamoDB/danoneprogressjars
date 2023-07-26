// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import java.io.EOFException;
import com.progress.ubroker.util.MsgReader;
import com.progress.ubroker.util.NetworkProtocolException;
import java.io.InterruptedIOException;
import com.progress.ubroker.util.ubWebSpeedMsg;
import com.progress.ubroker.util.ubAppServerMsg;
import java.io.IOException;
import com.progress.ubroker.util.RequestQueue;
import com.progress.ubroker.util.ubAdminMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.ubroker.util.Request;
import java.io.InputStream;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.IubMsgInputStream;
import com.progress.ubroker.util.ubConstants;
import java.io.BufferedInputStream;

public class TcpClientMsgInputStream extends BufferedInputStream implements ubConstants, IubMsgInputStream
{
    private IAppLogger m_log;
    private int m_logDest;
    private int m_streamTraceLevel;
    private int m_serverType;
    private TcpClientProtocol m_parentProtocol;
    private int m_msgBufferSize;
    private long m_debugLogEntries;
    private int m_debugLogIndex;
    
    public TcpClientMsgInputStream(final TcpClientProtocol parentProtocol, final InputStream in, final int serverType) {
        super(in, 10240);
        this.m_logDest = 0;
        this.m_streamTraceLevel = 4;
        this.m_serverType = 0;
        this.m_parentProtocol = null;
        this.m_msgBufferSize = 10240;
        if (null == parentProtocol) {
            throw new NullPointerException("Cannot initialize with a null protocol object");
        }
        this.m_parentProtocol = parentProtocol;
        this.m_serverType = serverType;
        this.m_log = this.m_parentProtocol.loggingObject();
        this.m_logDest = this.m_parentProtocol.loggingDestination();
        this.initializeLogging(this.m_log);
        if (!this.markSupported()) {
            this.m_log.logError("mark() is not supported on input stream.");
        }
    }
    
    public void setMsgBufferSize(final int msgBufferSize) throws Exception {
        if (msgBufferSize > this.m_msgBufferSize) {
            this.m_msgBufferSize = msgBufferSize;
            final byte[] buf = new byte[this.m_msgBufferSize];
            if (null != buf) {
                throw new Exception("Cannot extend the stream buffer");
            }
            if (0 < super.count) {
                System.arraycopy(super.buf, 0, buf, 0, super.count - 1);
            }
            super.buf = buf;
        }
    }
    
    public int getMsgBufferSize() {
        return this.m_msgBufferSize;
    }
    
    public void setLoggingTraceLevel(final int streamTraceLevel) throws Exception {
        this.m_streamTraceLevel = streamTraceLevel;
    }
    
    public int getLoggingTraceLevel() {
        return this.m_streamTraceLevel;
    }
    
    public int available() throws IOException {
        final RequestQueue queue = this.m_parentProtocol.getQueue();
        if (queue == null) {
            return super.available();
        }
        if (queue.isEmpty()) {
            return 0;
        }
        while (true) {
            final Request request = (Request)queue.findFirst();
            if (request == null) {
                return 0;
            }
            final ubMsg ubMsg = (ubMsg)request.getMsg();
            if (ubMsg == null) {
                return 0;
            }
            if (ubMsg.getubType() != 1) {
                return 1;
            }
            if (((ubAdminMsg)ubMsg).getadRq() != 14) {
                return 1;
            }
            queue.dequeueRequest();
        }
    }
    
    public synchronized ubMsg readMsg() throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        final RequestQueue queue = this.m_parentProtocol.getQueue();
        final MsgReader reader = this.m_parentProtocol.getReader();
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logBasic(this.m_debugLogIndex, "readMsg()");
        }
        if (queue == null) {
            this.mark(10240);
            final byte[] readubhdr = this.readubhdr();
            final byte[] readtlvbuf = this.readtlvbuf(readubhdr);
            final int getubType = ubMsg.getubType(readubhdr);
            ubConstants ubConstants = null;
            int getwsMsglen = 0;
            switch (getubType) {
                case 2: {
                    if (this.m_serverType != 0 && this.m_serverType != 4 && this.m_serverType != 6 && this.m_serverType != 7 && this.m_serverType != 2 && this.m_serverType != 3) {
                        this.m_log.logError(7665970990714724715L, new Object[] { ubMsg.DESC_UBTYPE[getubType], com.progress.ubroker.util.ubConstants.STRING_SERVER_TYPES[this.m_serverType] });
                        throw new ubMsg.WrongServerTypeException("ServerType=(" + ubMsg.getubType(readubhdr) + ") not supported for this broker");
                    }
                    ubConstants = new ubAppServerMsg(readubhdr, readtlvbuf, this.readsrvhdr(ubAppServerMsg.getSrvHdrlen()));
                    getwsMsglen = ((ubAppServerMsg)ubConstants).getMsglen() - 4;
                    break;
                }
                case 3: {
                    if (this.m_serverType != 1) {
                        this.m_log.logError(7665970990714724715L, new Object[] { ubMsg.DESC_UBTYPE[getubType], com.progress.ubroker.util.ubConstants.STRING_SERVER_TYPES[this.m_serverType] });
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
        if ((reader == null || (reader != null && reader.isClosed())) && queue.isEmpty()) {
            throw new IOException("Error reading the message");
        }
        final ubMsg ubMsg = (ubMsg)queue.dequeueRequest().getMsg();
        if (ubMsg.getubType() != 1) {
            return ubMsg;
        }
        final ubAdminMsg ubAdminMsg = (ubAdminMsg)ubMsg;
        ubAdminMsg.getadParm();
        switch (ubAdminMsg.getadRq()) {
            case 14: {
                throw new InterruptedIOException("Timeout reading message");
            }
            case 13: {
                throw new ubMsg.MsgFormatException("Invalid message format");
            }
            case 15: {
                throw new NetworkProtocolException();
            }
            default: {
                throw new IOException("Error reading message");
            }
        }
    }
    
    private byte[] readubhdr() throws IOException, ubMsg.InvalidMsgVersionException, ubMsg.InvalidHeaderLenException {
        final byte[] array = new byte[20];
        this.readstream(array, 0, 2);
        ubMsg.checkubVer(array);
        this.readstream(array, 2, array.length - 2);
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readubhdr", array, array.length);
        }
        return array;
    }
    
    private byte[] readtlvbuf(final byte[] array) throws IOException {
        byte[] array2;
        try {
            final short getubTlvBuflen = ubMsg.getubTlvBuflen(array);
            array2 = new byte[getubTlvBuflen];
            this.readstream(array2, 0, getubTlvBuflen);
            if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.m_log.logDump(2, this.m_debugLogIndex, "readtlvbuf", array2, array2.length);
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            array2 = null;
        }
        return array2;
    }
    
    private void readsrvhdr(final ubMsg ubMsg) throws IOException {
        this.readstream(ubMsg.getSrvHeader(), 0, ubMsg.getSrvHeaderlen());
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readsrvhdr", ubMsg.getSrvHeader(), ubMsg.getSrvHeaderlen());
        }
    }
    
    private byte[] readsrvhdr(final int n) throws IOException {
        final byte[] array = new byte[n];
        this.readstream(array, 0, n);
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readsrvhdr", array, n);
        }
        return array;
    }
    
    private int readMsgbuf(final ubMsg ubMsg, final int n) throws IOException {
        final byte[] array = new byte[n];
        this.readstream(array, 0, n);
        if (this.m_log.ifLogVerbose(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(3, this.m_debugLogIndex, "readMsgbuf[" + n + "]", array, n);
        }
        else if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readMsgbuf[" + n + "]", array, (n > 64) ? 64 : n);
        }
        ubMsg.setMsgbuf(array, n);
        return n;
    }
    
    private void readstream(final byte[] b, final int n, final int n2) throws IOException {
        for (int i = n2, read = 0; i > 0; i -= read) {
            try {
                read = this.read(b, n + (n2 - i), i);
            }
            catch (InterruptedIOException ex) {
                try {
                    this.reset();
                }
                catch (IOException obj) {
                    if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                        this.m_log.logBasic(this.m_debugLogIndex, "IOException on reset() : " + obj + obj.getMessage());
                    }
                }
                throw ex;
            }
            catch (IOException ex2) {
                if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.m_log.logBasic(this.m_debugLogIndex, "read() IOException in readstream : " + ex2.getMessage() + " : got= " + read + " need= " + i);
                }
                throw ex2;
            }
            if (read == -1) {
                throw new EOFException();
            }
        }
    }
    
    private void initializeLogging(final IAppLogger appLogger) {
        final String logContextName = appLogger.getLogContext().getLogContextName();
        if (logContextName.equals("Wsa")) {
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 33;
        }
        else if (logContextName.equals("O4gl")) {
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 10;
        }
        else if (logContextName.equals("UBroker")) {
            this.m_debugLogEntries = 2L;
            this.m_debugLogIndex = 1;
        }
        else {
            this.m_debugLogEntries = 0L;
            this.m_debugLogIndex = 0;
        }
    }
}
