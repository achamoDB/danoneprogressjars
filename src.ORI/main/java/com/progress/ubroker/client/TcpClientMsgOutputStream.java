// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import com.progress.ubroker.util.MsgReader;
import com.progress.ubroker.util.NetworkProtocolException;
import java.io.IOException;
import com.progress.ubroker.util.ubMsg;
import java.io.OutputStream;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.IubMsgOutputStream;
import com.progress.ubroker.util.ubConstants;
import java.io.BufferedOutputStream;

public class TcpClientMsgOutputStream extends BufferedOutputStream implements ubConstants, IubMsgOutputStream
{
    private IAppLogger m_log;
    private int m_logDest;
    private int m_streamTraceLevel;
    private int m_serverType;
    private TcpClientProtocol m_parentProtocol;
    private int m_msgBufferSize;
    private long m_debugLogEntries;
    private int m_debugLogIndex;
    
    public TcpClientMsgOutputStream(final TcpClientProtocol parentProtocol, final OutputStream out, final int serverType) {
        super(out, 10240);
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
    
    public synchronized void writeMsg(final ubMsg obj) throws IOException {
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logBasic(this.m_debugLogIndex, "writeMsg(" + obj + ")");
        }
        if (obj.getubhdr() != null) {
            this.writeubhdr(obj);
        }
        switch (obj.getubVer()) {
            case 108: {
                break;
            }
            default: {
                this.writetlvbuf(obj);
                break;
            }
        }
        if (obj.getSrvHeader() != null) {
            this.writeSrvHeader(obj);
        }
        if (obj.getBuflen() > 0) {
            this.writeMsgbuf(obj);
        }
    }
    
    public void flushMsg() throws IOException, NetworkProtocolException {
        this.flush();
    }
    
    private void writeSrvHeader(final ubMsg ubMsg) throws IOException {
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "writeSrvrHeader", ubMsg.getSrvHeader(), ubMsg.getSrvHeaderlen());
        }
        this.writestream(ubMsg.getSrvHeader(), 0, ubMsg.getSrvHeaderlen());
    }
    
    private void writeubhdr(final ubMsg ubMsg) throws IOException {
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "writeubhdr", ubMsg.getubhdr(), 20);
        }
        this.writestream(ubMsg.getubhdr(), 0, 20);
    }
    
    private void writetlvbuf(final ubMsg ubMsg) throws IOException {
        try {
            final byte[] getubTlvBuf = ubMsg.getubTlvBuf();
            if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.m_log.logDump(2, this.m_debugLogIndex, "writetlvbuf", getubTlvBuf, getubTlvBuf.length);
            }
            this.writestream(getubTlvBuf, 0, getubTlvBuf.length);
        }
        catch (ubMsg.MsgFormatException ex) {
            this.m_log.logStackTrace("getubTlvBuf() Exception in writetlvbuf : " + ex.getMessage(), ex);
        }
    }
    
    private void writeMsgbuf(final ubMsg ubMsg) throws IOException {
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "writeMsgbuf", ubMsg.getMsgbuf(), ubMsg.getBuflen());
        }
        this.writestream(ubMsg.getMsgbuf(), 0, ubMsg.getBuflen());
    }
    
    private void writestream(final byte[] b, final int off, final int len) throws IOException {
        final MsgReader reader = this.m_parentProtocol.getReader();
        if (reader != null && reader.isClosed()) {
            throw new IOException("Failed to send data to the server");
        }
        this.write(b, off, len);
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
