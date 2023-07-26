// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.SystemErrorException;
import com.progress.open4gl.RunTime4GLException;
import com.progress.open4gl.Open4GLException;
import java.util.zip.CRC32;

public class PersistentProc
{
    private Object procId;
    private Session session;
    private String fileName;
    private boolean fDeletedByServer;
    private int stateMode;
    private Object procSignature;
    private CRC32 crc;
    
    PersistentProc(final String fileName, final Object procId, final Session session) {
        this.crc = new CRC32();
        this.fileName = fileName;
        this.procId = procId;
        this.session = session;
        this.fDeletedByServer = false;
        this.stateMode = 1;
        this.genProcSignature();
    }
    
    PersistentProc(final String fileName, final Session session, final int stateMode) {
        this.crc = new CRC32();
        this.fileName = fileName;
        this.procId = null;
        this.session = session;
        this.fDeletedByServer = false;
        this.stateMode = stateMode;
        this.genProcSignature();
    }
    
    public String toString() {
        return "<" + super.toString() + "|" + this.session + "|" + this.fileName + "|" + this.procId + ">";
    }
    
    public void runProcedure(final String s, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.validate();
        this.runTheProcedure(null, s, set, null);
    }
    
    public void runProcedure(final String s, final String s2, final ParameterSet set) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.validate();
        this.runTheProcedure(s, s2, set, null);
    }
    
    public void runProcedure(final String s, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.validate();
        this.runTheProcedure(null, s, set, resultSetSchema);
    }
    
    public void runProcedure(final String s, final String s2, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        this.validate();
        this.runTheProcedure(s, s2, set, resultSetSchema);
    }
    
    protected void runTheProcedure(final String s, final String str, final ParameterSet set, final ResultSetSchema resultSetSchema) throws Open4GLException, RunTime4GLException, SystemErrorException {
        if (1 == this.stateMode) {
            this.session.runProcedure(s, str, set, this.procId, resultSetSchema);
        }
        else {
            this.session.runStatelessProcedure(s, this.fileName + ":" + str, set, this.procSignature, resultSetSchema, this.stateMode);
        }
    }
    
    public Session getSession() throws Open4GLException {
        this.validate();
        return this.session;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public Object getProcId() {
        if (1 == this.stateMode) {
            return this.procId;
        }
        return this.procSignature;
    }
    
    public long updateProcId(final long value) {
        final long longValue = (long)this.procId;
        this.procId = new Long(value);
        return longValue;
    }
    
    public void deletedByServer(final boolean fDeletedByServer) {
        this.fDeletedByServer = fDeletedByServer;
    }
    
    public boolean deletedByServer() {
        return this.fDeletedByServer;
    }
    
    public void delete() throws Open4GLException, SystemErrorException {
        if (!this.isValid()) {
            return;
        }
        if (!this.fDeletedByServer && 1 == this.stateMode) {
            this.session.deleteProcedures(this, new ProcListResultSet(this.procId), ProcListMetaData.metaData);
        }
        this.session = null;
    }
    
    private void validate() throws Open4GLException {
        if (!this.isValid()) {
            throw new Open4GLException(51L, null);
        }
    }
    
    public boolean isValid() {
        return this.session != null && this.session.isConnected();
    }
    
    protected final void finalize() {
    }
    
    public void genProcSignature() {
        this.crc.reset();
        this.crc.update(this.fileName.toUpperCase().getBytes());
        this.procSignature = new Long(this.crc.getValue());
    }
}
