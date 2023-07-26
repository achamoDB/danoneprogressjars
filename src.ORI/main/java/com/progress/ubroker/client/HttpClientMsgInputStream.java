// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import com.progress.ubroker.util.ubWebSpeedMsg;
import java.io.EOFException;
import java.io.IOException;
import com.progress.ubroker.util.NetworkProtocolException;
import HTTPClient.HTTPConnection;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.ubMsg;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.IubMsgInputStream;
import com.progress.ubroker.util.ubConstants;

public class HttpClientMsgInputStream implements ubConstants, IubMsgInputStream
{
    private HttpClientProtocol m_parentHandler;
    private IAppLogger m_log;
    private int m_logDest;
    private int m_streamTraceLevel;
    private boolean m_inMessageStream;
    private HttpClientProtocol.UBMessageResponse m_httpResponse;
    private int m_serverType;
    private boolean m_emulateAbnormalEOF;
    private long m_debugLogEntries;
    private int m_debugLogIndex;
    
    public HttpClientMsgInputStream(final HttpClientProtocol parentHandler) {
        this.m_parentHandler = null;
        this.m_log = null;
        this.m_logDest = 0;
        this.m_streamTraceLevel = 4;
        this.m_inMessageStream = false;
        this.m_httpResponse = null;
        this.m_serverType = 0;
        this.m_emulateAbnormalEOF = false;
        if (null == parentHandler) {
            throw new NullPointerException("Null protocol handler");
        }
        this.m_parentHandler = parentHandler;
        this.m_log = this.m_parentHandler.loggingObject();
        this.m_logDest = this.m_parentHandler.loggingDestination();
        this.initializeLogging(this.m_log);
    }
    
    protected void finalize() throws Throwable {
        this.close();
    }
    
    public void setMsgBufferSize(final int n) throws Exception {
    }
    
    public int getMsgBufferSize() {
        return this.available();
    }
    
    public void setLoggingTraceLevel(final int streamTraceLevel) throws Exception {
        this.m_streamTraceLevel = streamTraceLevel;
    }
    
    public int getLoggingTraceLevel() {
        return this.m_streamTraceLevel;
    }
    
    public ubMsg readMsg() throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        ubAppServerMsg ubAppServerMsg = null;
        int i = 1;
        int getubRq = 0;
        while (i != 0) {
            if (null == this.m_httpResponse || 0 == this.m_httpResponse.m_httpInputStream.available()) {
                this.readNextHttpMessage();
            }
            try {
                ubAppServerMsg = (ubAppServerMsg)this.readUBMsg();
                getubRq = ubAppServerMsg.getubRq();
                if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    String str = "unknown";
                    final byte msgcode = ubAppServerMsg.getMsgcode();
                    for (int j = 0; j < com.progress.ubroker.util.ubAppServerMsg.CSMSSG_MSGCODES.length; ++j) {
                        if (msgcode == com.progress.ubroker.util.ubAppServerMsg.CSMSSG_MSGCODES[j]) {
                            str = com.progress.ubroker.util.ubAppServerMsg.DESC_MSGCODE[j];
                            break;
                        }
                    }
                    this.m_log.logBasic(this.m_debugLogIndex, "Read ubAppServerMsg, apMsg: " + str + " ; ubMsg: " + ubAppServerMsg.getubRqDesc());
                }
                if (20 == getubRq) {
                    if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                        this.m_log.logBasic(this.m_debugLogIndex, "Skipping [UBRQ_SEND_EMPTY_MSG] message");
                    }
                    this.m_httpResponse.m_httpInputStream.close();
                    this.m_httpResponse.m_httpInputStream = null;
                    this.m_httpResponse.m_ubMsgResponse = null;
                    this.m_httpResponse = null;
                    if (this.m_parentHandler.available() > 0) {
                        continue;
                    }
                    if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                        this.m_log.logBasic(this.m_debugLogIndex, "Nothing else on the pipe after [UBRQ_SEND_EMPTY_MSG] message");
                    }
                }
                i = 0;
                if (12 == getubRq) {
                    this.m_inMessageStream = true;
                }
                if (1 == ubAppServerMsg.getubRsp()) {
                    if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                        this.m_log.logBasic(this.m_debugLogIndex, "Error detected in ubMsg respsonse, closing " + this.m_parentHandler.protocolName() + " connection.");
                    }
                    this.m_parentHandler.closeHttpConnection(null, this.m_parentHandler);
                    String get4GLErrMsg = "N/A";
                    try {
                        get4GLErrMsg = ubAppServerMsg.get4GLErrMsg();
                    }
                    catch (Exception ex6) {}
                    final NetworkProtocolException ex = new NetworkProtocolException(12L, this.m_parentHandler.protocolName(), get4GLErrMsg);
                    this.m_log.logStackTrace("", ex);
                    throw ex;
                }
                continue;
            }
            catch (IOException ex2) {
                if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.m_log.logBasic(this.m_debugLogIndex, "IOException reading AppServer message: " + ex2.getMessage());
                }
                throw ex2;
            }
            catch (ubMsg.MsgFormatException ex3) {
                if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.m_log.logBasic(this.m_debugLogIndex, "Invalid AppServer message format: " + ex3.getMessage());
                }
                throw ex3;
            }
            catch (Exception ex4) {
                ex4.printStackTrace();
                final NetworkProtocolException ex5 = new NetworkProtocolException(0L, this.m_parentHandler.protocolName(), "Reading response: " + ex4.toString());
                this.m_log.logStackTrace("", ex5);
                throw ex5;
            }
            break;
        }
        if (20 == getubRq) {
            ubAppServerMsg = null;
        }
        return ubAppServerMsg;
    }
    
    public int available() {
        int n = 0;
        try {
            if (null == this.m_httpResponse) {
                n = this.m_parentHandler.available();
            }
            else {
                n = this.m_httpResponse.m_httpInputStream.available();
                if (n == 0) {
                    n = this.m_parentHandler.available();
                }
            }
        }
        catch (Exception ex) {}
        return n;
    }
    
    public void close() throws IOException {
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logBasic(this.m_debugLogIndex, "Closing HttpClientMsgInputStream.");
        }
        if (null != this.m_httpResponse) {
            this.m_httpResponse.m_httpInputStream.close();
            this.m_httpResponse.m_httpInputStream = null;
            this.m_httpResponse.m_ubMsgResponse = null;
            this.m_httpResponse = null;
        }
        this.m_parentHandler.releaseMsgInputStream();
    }
    
    public void setStop() {
        try {
            if (0 < this.m_httpResponse.m_httpInputStream.available()) {
                this.m_emulateAbnormalEOF = true;
            }
        }
        catch (Exception ex) {
            if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.m_log.logBasic(this.m_debugLogIndex, "setStop() encountered an exception: " + ex.toString());
            }
        }
    }
    
    protected void readNextHttpMessage() throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        if (null != this.m_httpResponse) {
            this.m_httpResponse.m_httpInputStream.close();
            this.m_httpResponse.m_httpInputStream = null;
            this.m_httpResponse.m_ubMsgResponse = null;
            this.m_httpResponse = null;
        }
        try {
            if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.m_log.logBasic(this.m_debugLogIndex, "Getting the next HTTP response message");
            }
            this.m_httpResponse = this.m_parentHandler.getUbResponseMessage();
        }
        catch (EOFException ex) {
            throw ex;
        }
        catch (NetworkProtocolException ex2) {
            this.close();
            throw ex2;
        }
        catch (Exception ex4) {
            final NetworkProtocolException ex3 = new NetworkProtocolException(0L, this.m_parentHandler.protocolName(), "Getting response message: " + ex4.toString());
            this.m_log.logStackTrace("", ex3);
            this.close();
            throw ex3;
        }
        String header = null;
        try {
            header = this.m_httpResponse.m_ubMsgResponse.getHeader("Content-Length");
        }
        catch (Exception ex5) {}
        if (null == header) {
            header = "unknown";
        }
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logBasic(this.m_debugLogIndex, "HTTP Input stream now has : " + header + " bytes available for reading.");
        }
    }
    
    private synchronized ubMsg readUBMsg() throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logBasic(this.m_debugLogIndex, "readUBMsg()");
        }
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
        if (this.m_emulateAbnormalEOF) {
            this.m_emulateAbnormalEOF = false;
            if (12 == ((ubMsg)ubConstants).getubRq()) {
                ((ubMsg)ubConstants).setubRsp(9);
                this.m_httpResponse.m_httpInputStream.skip(this.m_httpResponse.m_httpInputStream.available());
            }
        }
        return (ubMsg)ubConstants;
    }
    
    private byte[] readubhdr() throws IOException, ubMsg.InvalidMsgVersionException, ubMsg.InvalidHeaderLenException, ubMsg.MsgFormatException, NetworkProtocolException {
        final byte[] array = new byte[20];
        this.readstream(array, 0, 2);
        ubMsg.checkubVer(array);
        this.readstream(array, 2, array.length - 2);
        return array;
    }
    
    private byte[] readtlvbuf(final byte[] array) throws IOException, NetworkProtocolException {
        byte[] array2;
        try {
            final short getubTlvBuflen = ubMsg.getubTlvBuflen(array);
            array2 = new byte[getubTlvBuflen];
            if (getubTlvBuflen > 0) {
                this.readstream(array2, 0, getubTlvBuflen);
                if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.m_log.logDump(2, this.m_debugLogIndex, "readtlvbuf", array2, getubTlvBuflen);
                }
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            array2 = null;
        }
        return array2;
    }
    
    private void readsrvhdr(final ubMsg ubMsg) throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        this.readstream(ubMsg.getSrvHeader(), 0, ubMsg.getSrvHeaderlen());
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readsrvhdr", ubMsg.getSrvHeader(), ubMsg.getSrvHeaderlen());
        }
    }
    
    private byte[] readsrvhdr(final int n) throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        final byte[] array = new byte[n];
        this.readstream(array, 0, n);
        if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readsrvhdr", array, n);
        }
        return array;
    }
    
    private int readMsgbuf(final ubMsg ubMsg, final int n) throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        final byte[] array = new byte[n];
        this.readstream(array, 0, n);
        if (this.m_log.ifLogVerbose(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readMsgbuf[" + n + "]", array, n);
        }
        else if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.m_log.logDump(2, this.m_debugLogIndex, "readMsgbuf[" + n + "]", array, (n > 64) ? 64 : n);
        }
        ubMsg.setMsgbuf(array, n);
        return n;
    }
    
    private void readstream(final byte[] b, final int n, final int n2) throws IOException, ubMsg.MsgFormatException, NetworkProtocolException {
        for (int i = n2, read = 0; i > 0; i -= read) {
            try {
                read = this.m_httpResponse.m_httpInputStream.read(b, n + (n2 - i), i);
            }
            catch (IOException ex) {
                if (this.m_log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.m_log.logBasic(this.m_debugLogIndex, "read() IOException in readstream : " + ex.getMessage() + " : got= " + read + " need= " + i);
                }
                throw ex;
            }
            if (read == -1) {}
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
