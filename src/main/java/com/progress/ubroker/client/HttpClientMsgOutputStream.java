// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.ubMsg;
import java.io.IOException;
import com.progress.ubroker.util.NetworkProtocolException;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.IubMsgOutputStream;
import java.io.ByteArrayOutputStream;

public class HttpClientMsgOutputStream extends ByteArrayOutputStream implements IubMsgOutputStream
{
    private static final int INITIAL_SIZE = 65536;
    private HttpClientProtocol m_parentHandler;
    private int m_msgBufferSize;
    private IAppLogger m_log;
    private int m_logDest;
    private int m_streamTraceLevel;
    private int m_lastUbMsgRequestCode;
    private long m_debugLogEntries;
    private int m_debugLogIndex;
    
    public HttpClientMsgOutputStream(final HttpClientProtocol parentHandler) {
        super(65536);
        this.m_parentHandler = null;
        this.m_msgBufferSize = 65536;
        this.m_logDest = 0;
        this.m_streamTraceLevel = 4;
        this.m_lastUbMsgRequestCode = 0;
        if (null == parentHandler) {
            throw new NullPointerException("Null parent protocol handler");
        }
        this.m_parentHandler = parentHandler;
        this.m_log = this.m_parentHandler.loggingObject();
        this.m_logDest = this.m_parentHandler.loggingDestination();
        this.initializeLogging(this.m_log);
    }
    
    public int lastUbMsgRequestCode() {
        return this.m_lastUbMsgRequestCode;
    }
    
    public void flushMsg() throws IOException, NetworkProtocolException {
        super.flush();
        if (0 < super.count) {
            try {
                if (7 == this.m_lastUbMsgRequestCode) {
                    this.m_parentHandler.sendStopMessage(this);
                    this.m_lastUbMsgRequestCode = 0;
                }
                else {
                    this.m_parentHandler.sendUbMessage(this);
                    this.m_lastUbMsgRequestCode = 0;
                }
            }
            catch (Exception ex2) {
                final NetworkProtocolException ex = new NetworkProtocolException(4L, this.m_parentHandler.protocolName(), ex2.toString());
                this.m_log.logStackTrace("", ex);
                throw ex;
            }
        }
        this.reset();
    }
    
    public void close() throws IOException {
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logBasic(this.m_debugLogIndex, "Closing HttpClientMsgOutputStream.");
        }
        this.m_parentHandler.releaseMsgOutputStream();
        super.close();
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
    
    public void writeMsg(final ubMsg ubMsg) throws IOException, NetworkProtocolException {
        this.m_lastUbMsgRequestCode = ubMsg.getubRq();
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            String str = "unknown";
            final byte msgcode = ((ubAppServerMsg)ubMsg).getMsgcode();
            for (int i = 0; i < ubAppServerMsg.CSMSSG_MSGCODES.length; ++i) {
                if (msgcode == ubAppServerMsg.CSMSSG_MSGCODES[i]) {
                    str = ubAppServerMsg.DESC_MSGCODE[i];
                    break;
                }
            }
            this.m_log.logBasic(this.m_debugLogIndex, "Write ubAppServerMsg, apMsg: " + str + " ; ubMsg: " + ubMsg.getubRqDesc());
        }
        try {
            final byte[] serializeMsg = ubMsg.serializeMsg();
            this.write(serializeMsg);
            if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.m_log.logDump(2, this.m_debugLogIndex, "writeMsg", serializeMsg, serializeMsg.length);
            }
            this.flushMsg();
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
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
