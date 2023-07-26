// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.net.SocketException;
import java.io.IOException;
import java.io.OutputStream;
import com.progress.common.ehnlog.IAppLogger;
import java.io.BufferedOutputStream;

public class MsgOutputStream extends BufferedOutputStream
{
    IAppLogger applog;
    int stream_trace_opt;
    long entrytype;
    int index_entry_type;
    String entryTypeName;
    String logEnvID;
    
    public MsgOutputStream(final OutputStream out, final int size, final IAppLogger applog, final int stream_trace_opt, final long entrytype, final int index_entry_type) {
        super(out, size);
        this.applog = applog;
        this.stream_trace_opt = stream_trace_opt;
        this.entrytype = entrytype;
        this.index_entry_type = index_entry_type;
        this.entryTypeName = this.applog.getLogContext().getEntrytypeName(this.index_entry_type);
        this.logEnvID = this.applog.getExecEnvId();
    }
    
    public synchronized void writeMsg(final ubMsg ubMsg) throws IOException {
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_opt, this.entrytype, this.index_entry_type)) {
            this.applog.logWriteMessage(2, this.stream_trace_opt, this.logEnvID, this.entryTypeName, "writeMsg()");
        }
        if (ubMsg.getubhdr() != null) {
            this.writeubhdr(ubMsg);
        }
        switch (ubMsg.getubVer()) {
            case 108: {
                break;
            }
            default: {
                this.writetlvbuf(ubMsg);
                break;
            }
        }
        if (ubMsg.getSrvHeader() != null) {
            this.writeSrvHeader(ubMsg);
        }
        if (ubMsg.getBuflen() > 0) {
            this.writeMsgbuf(ubMsg);
        }
    }
    
    private void writeSrvHeader(final ubMsg ubMsg) throws IOException {
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_opt, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.stream_trace_opt, this.index_entry_type, "writeSrvHeader", ubMsg.getSrvHeader(), ubMsg.getSrvHeaderlen());
        }
        this.write(ubMsg.getSrvHeader(), 0, ubMsg.getSrvHeaderlen());
    }
    
    private void writeubhdr(final ubMsg ubMsg) throws IOException {
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_opt, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.stream_trace_opt, this.index_entry_type, "writeubhdr", ubMsg.getubhdr(), 20);
        }
        this.write(ubMsg.getubhdr(), 0, 20);
    }
    
    private void writetlvbuf(final ubMsg ubMsg) throws IOException {
        try {
            final byte[] getubTlvBuf = ubMsg.getubTlvBuf();
            final short getubTlvBuflen = ubMsg.getubTlvBuflen();
            if (this.applog != null && this.applog.ifLogIt(this.stream_trace_opt, this.entrytype, this.index_entry_type)) {
                this.applog.logDump(this.stream_trace_opt, this.index_entry_type, "writetlvbuf", getubTlvBuf, getubTlvBuflen);
            }
            if (getubTlvBuflen > 0) {
                this.write(getubTlvBuf, 0, getubTlvBuflen);
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            if (this.applog != null) {
                this.applog.logStackTrace(this.index_entry_type, "getubTlvBuf() Exception in writetlvbuf : " + ex.getMessage(), ex);
            }
        }
    }
    
    private void writeMsgbuf(final ubMsg ubMsg) throws IOException {
        final int buflen = ubMsg.getBuflen();
        if (this.applog != null && this.applog.ifLogIt(this.stream_trace_opt, this.entrytype, this.index_entry_type)) {
            this.applog.logDump(this.stream_trace_opt, this.index_entry_type, "writeMsgbuf[" + buflen + "]", ubMsg.getMsgbuf(), buflen);
        }
        this.write(ubMsg.getMsgbuf(), 0, buflen);
    }
    
    public void close() throws IOException {
        try {
            super.close();
        }
        catch (SocketException ex) {
            if (this.applog != null && this.applog.ifLogIt(2, this.entrytype, this.index_entry_type)) {
                this.applog.logWriteMessage(2, 2, this.logEnvID, this.entryTypeName, "Closing output stream: " + ex.getMessage());
            }
        }
    }
}
