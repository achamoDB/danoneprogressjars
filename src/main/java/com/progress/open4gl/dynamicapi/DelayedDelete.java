// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

class DelayedDelete implements DelayedRequest
{
    private PersistentProc procedure;
    private ProcListResultSet pSet;
    private ResultSetMetaData pMeta;
    
    DelayedDelete(final PersistentProc procedure, final ProcListResultSet pSet, final ResultSetMetaData pMeta) {
        this.procedure = procedure;
        this.pSet = pSet;
        this.pMeta = pMeta;
    }
    
    public void sendRequest(final Session session) {
        try {
            session.deleteProceduresNoLock(this.procedure, this.pSet, this.pMeta);
        }
        catch (Exception ex) {}
    }
}
