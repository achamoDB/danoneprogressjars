// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.EOFException;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.InputStream;
import com.progress.common.ehnlog.IAppLogger;
import java.io.BufferedInputStream;

public class MsgInputStream extends BufferedInputStream implements ubConstants
{
    IAppLogger applog;
    int stream_trace_level;
    long entrytype;
    int serverType;
    int index_entry_type;
    String entryTypeName;
    String logEnvID;
    
    public MsgInputStream(final InputStream in, final int size, final int serverType, final IAppLogger applog, final int stream_trace_level, final long entrytype, final int index_entry_type) {
        super(in, size);
        this.serverType = serverType;
        this.applog = applog;
        this.stream_trace_level = stream_trace_level;
        this.entrytype = entrytype;
        this.index_entry_type = index_entry_type;
        this.entryTypeName = this.applog.getLogContext().getEntrytypeName(this.index_entry_type);
        this.logEnvID = this.applog.getExecEnvId();
        if (!this.markSupported()) {
            this.applog.logError("mark() is not supported on input stream.");
        }
    }
    
    public MsgInputStream(final InputStream inputStream, final int n, final int n2, final IAppLogger appLogger) {
        this(inputStream, n, n2, appLogger, 2, 2L, 1);
    }
    
    public synchronized ubMsg readMsg() throws IOException, ubMsg.MsgFormatException {
        if (this.applog != null && this.applog.ifLogIt(4, this.entrytype, this.index_entry_type)) {
            this.applog.logWriteMessage(2, 4, this.logEnvID, this.entryTypeName, "reading message ...");
        }
        this.mark(10240);
        if (this.applog != null && this.applog.ifLogIt(4, this.entrytype, this.index_entry_type)) {
            this.applog.logWriteMessage(2, 4, this.logEnvID, this.entryTypeName, "mark() : markpos=" + super.markpos);
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
        if (getwsMsglen >= 0) {
            this.readMsgbuf((ubMsg)ubConstants, getwsMsglen);
            return (ubMsg)ubConstants;
        }
        if (this.applog != null) {
            this.applog.logError("Negative buffer length received");
        }
        throw new ubMsg.MsgFormatException("Negative value received for buffer length.");
    }
    
    private byte[] readubhdr() throws IOException, ubMsg.InvalidMsgVersionException, ubMsg.InvalidHeaderLenException {
        final byte[] array = new byte[20];
        this.readstream(array, 0, 2);
        ubMsg.checkubVer(array);
        this.readstream(array, 2, array.length - 2);
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_level, this.entrytype, this.index_entry_type)) {
            this.applog.logWriteMessage(2, this.stream_trace_level, this.logEnvID, this.entryTypeName, "readMsg()");
            this.applog.logDump(this.stream_trace_level, this.index_entry_type, "readubhdr", array, array.length);
        }
        return array;
    }
    
    private byte[] readtlvbuf(final byte[] array) throws IOException {
        byte[] array2 = new byte[0];
        try {
            final short getubTlvBuflen = ubMsg.getubTlvBuflen(array);
            if (getubTlvBuflen > 0) {
                array2 = new byte[getubTlvBuflen];
                this.readstream(array2, 0, getubTlvBuflen);
            }
            if (getubTlvBuflen > 0 && this.applog != null && this.applog.ifLogIt(this.stream_trace_level, this.entrytype, this.index_entry_type)) {
                this.applog.logDump(this.stream_trace_level, this.index_entry_type, "readtlvbuf", array2, array2.length);
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            array2 = null;
        }
        return array2;
    }
    
    private void readsrvhdr(final ubMsg ubMsg) throws IOException {
        this.readstream(ubMsg.getSrvHeader(), 0, ubMsg.getSrvHeaderlen());
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_level, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.stream_trace_level, this.index_entry_type, "readsrvhdr", ubMsg.getSrvHeader(), ubMsg.getSrvHeaderlen());
        }
    }
    
    private byte[] readsrvhdr(final int n) throws IOException {
        final byte[] array = new byte[n];
        this.readstream(array, 0, n);
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_level, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.stream_trace_level, this.index_entry_type, "readsrvhdr", array, n);
        }
        return array;
    }
    
    private int readMsgbuf(final ubMsg ubMsg, final int i) throws IOException {
        final byte[] array = new byte[i];
        this.readstream(array, 0, i);
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_level, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.stream_trace_level, this.index_entry_type, "readMsgbuf[" + i + "]", array, i);
        }
        ubMsg.setMsgbuf(array, i);
        return i;
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
                    if (this.applog != null) {
                        this.applog.logError("IOException on reset() : " + obj + obj.getMessage());
                    }
                }
                if (this.applog != null && this.applog.ifLogIt(4, this.entrytype, this.index_entry_type)) {
                    this.applog.logWriteMessage(2, 4, this.logEnvID, this.entryTypeName, "readMsg() : " + ex.toString());
                }
                throw ex;
            }
            catch (IOException ex2) {
                if (this.applog != null && this.applog.ifLogIt(this.stream_trace_level, this.entrytype, this.index_entry_type)) {
                    this.applog.logWriteMessage(2, this.stream_trace_level, this.logEnvID, this.entryTypeName, "read() IOException in readstream : " + ex2.getMessage() + " : got= " + read + " need= " + i);
                }
                throw ex2;
            }
            if (read == -1) {
                throw new EOFException();
            }
        }
    }
}
