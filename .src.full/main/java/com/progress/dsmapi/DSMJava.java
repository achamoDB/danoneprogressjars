// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.dsmapi;

public class DSMJava implements DSMConstants
{
    private static DSMJava c;
    public int retval;
    private long dsmContext;
    private long dsmContextCopy;
    private int area;
    private int objectAttr;
    private int associate;
    private int associateType;
    private int block;
    private int root;
    private int cursor;
    public int cursorStruct;
    public int objectNumber;
    public int recid;
    public int seqValue;
    public DSMKey key;
    public DSMSeqParm seqParm;
    
    private native int dsmContextCreateN();
    
    private native int dsmContextDeleteN(final long p0);
    
    private native int dsmContextSetStringN(final long p0, final int p1, final int p2, final String p3);
    
    private native int dsmContextSetLongN(final long p0, final int p1, final int p2);
    
    private native int dsmContextCopyN(final long p0, final int p1);
    
    private native int dsmUserConnectN(final long p0, final String p1, final int p2);
    
    private native int dsmUserDisconnectN(final long p0, final int p1);
    
    private native int dsmUserResetN(final long p0);
    
    private native int dsmUserSetNameN(final long p0, final String p1, final String p2);
    
    private native int dsmTransactionN(final long p0, final int p1, final int p2, final int p3, final String p4);
    
    private native int dsmObjectCreateN(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    private native int dsmObjectDeleteN(final long p0, final int p1, final int p2, final int p3, final String p4);
    
    private native int dsmObjectInfoN(final long p0, final int p1, final int p2);
    
    private native int dsmObjectLockN(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final String p6);
    
    private native int dsmObjectUnlockN(final long p0, final int p1, final int p2, final int p3, final int p4, final String p5);
    
    private native int dsmCursorCreateN(final long p0, final int p1, final int p2, final int p3, final int p4);
    
    private native int dsmCursorFindN(final long p0, final int p1, final DSMKey p2, final DSMKey p3, final int p4, final int p5, final int p6, final int p7, final int p8, final DSMKey p9);
    
    private native int dsmCursorGetN(final long p0, final int p1);
    
    private native int dsmCursorDeleteN(final long p0, final int p1, final int p2);
    
    private native int dsmAreaCreateN(final long p0, final int p1, final int p2, final int p3);
    
    private native int dsmAreaDeleteN(final long p0, final int p1);
    
    private native int dsmExtentCreateN(final long p0, final int p1, final int p2, final int p3, final int p4, final String p5);
    
    private native int dsmExtentDeleteN(final long p0, final int p1, final int p2);
    
    private native int dsmSeqCreateN(final long p0, final DSMSeqParm p1);
    
    private native int dsmSeqDeleteN(final long p0, final int p1);
    
    private native int dsmSeqInfoN(final long p0, final DSMSeqParm p1);
    
    private native int dsmSeqGetValueN(final long p0, final int p1, final int p2);
    
    private native int dsmSeqSetValueN(final long p0, final int p1, final int p2);
    
    private native int dsmKeyCreateN(final long p0, final DSMKey p1, final int p2, final int p3, final String p4);
    
    private native int dsmKeyDeleteN(final long p0, final DSMKey p1, final int p2, final int p3, final int p4, final String p5);
    
    private native int dsmRecordCreateN(final long p0, final DSMRecord p1, final Byte[] p2, final int p3);
    
    private native int dsmRecordDeleteN(final long p0, final DSMRecord p1, final Byte[] p2, final int p3, final String p4);
    
    private native int dsmRecordGetN(final long p0, final DSMRecord p1, final Byte[] p2, final int p3);
    
    private native int dsmRecordUpdateN(final long p0, final DSMRecord p1, final Byte[] p2, final int p3, final String p4);
    
    private native int dsmBlobGetN(final long p0, final DSMBlob p1, final Byte[] p2, final int p3, final String p4);
    
    private native int dsmBlobPutN(final long p0, final DSMBlob p1, final Byte[] p2, final int p3, final String p4);
    
    private native int dsmBlobUpdateN(final long p0, final DSMBlob p1, final Byte[] p2, final int p3, final String p4);
    
    private native int dsmBlobDeleteN(final long p0, final DSMBlob p1, final Byte[] p2, final int p3, final String p4);
    
    private native int dsmBlobStartN(final long p0, final DSMBlob p1);
    
    private native int dsmBlobEndN(final long p0, final DSMBlob p1);
    
    private native int dsmBlobUnlockN(final long p0, final DSMBlob p1);
    
    public int retval() {
        return DSMJava.c.retval;
    }
    
    public long dsmContext() {
        return DSMJava.c.dsmContext;
    }
    
    public long dsmContextCreate() {
        DSMJava.c.retval = DSMJava.c.dsmContextCreateN();
        return DSMJava.c.dsmContext;
    }
    
    public int dsmContextDelete(final long n) {
        return DSMJava.c.retval = DSMJava.c.dsmContextDeleteN(n);
    }
    
    public int dsmContextSetString(final long n, final int n2, final int n3, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmContextSetStringN(n, n2, n3, s);
    }
    
    public int dsmContextSetLong(final long n, final int n2, final int n3) {
        return DSMJava.c.retval = DSMJava.c.dsmContextSetLongN(n, n2, n3);
    }
    
    public long dsmContextCopy(final long n, final int n2) {
        DSMJava.c.retval = DSMJava.c.dsmContextCopyN(n, n2);
        return DSMJava.c.dsmContextCopy;
    }
    
    public int dsmUserConnect(final long n, final String s, final int n2) {
        return DSMJava.c.retval = DSMJava.c.dsmUserConnectN(n, s, n2);
    }
    
    public int dsmUserDisconnect(final long n, final int n2) {
        return DSMJava.c.retval = DSMJava.c.dsmUserDisconnectN(n, n2);
    }
    
    public int dsmUserReset(final long n) {
        return DSMJava.c.retval = DSMJava.c.dsmUserResetN(n);
    }
    
    public int dsmUserSetName(final long n, final String s, final String s2) {
        return DSMJava.c.retval = DSMJava.c.dsmUserSetNameN(n, s, s2);
    }
    
    public int dsmTransaction(final long n, final int n2, final int n3, final int n4, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmTransactionN(n, n2, n3, n4, s);
    }
    
    public DSMObjectInfo dsmObjectCreate(final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final DSMObjectInfo dsmObjectInfo = new DSMObjectInfo();
        DSMJava.c.retval = DSMJava.c.dsmObjectCreateN(n, n2, n3, n4, n5, n6, n7, n8, n9);
        dsmObjectInfo.retval = DSMJava.c.retval;
        if (dsmObjectInfo.retval == 0) {
            dsmObjectInfo.associate = DSMJava.c.associate;
            dsmObjectInfo.block = DSMJava.c.block;
            dsmObjectInfo.root = DSMJava.c.root;
        }
        return dsmObjectInfo;
    }
    
    public int dsmObjectDelete(final long n, final int n2, final int n3, final int n4, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmObjectDeleteN(n, n2, n3, n4, s);
    }
    
    public DSMObjectInfo dsmObjectInfo(final long n, final int n2, final int n3) {
        final DSMObjectInfo dsmObjectInfo = new DSMObjectInfo();
        DSMJava.c.retval = DSMJava.c.dsmObjectInfoN(n, n2, n3);
        dsmObjectInfo.retval = DSMJava.c.retval;
        if (dsmObjectInfo.retval == 0) {
            dsmObjectInfo.area = DSMJava.c.area;
            dsmObjectInfo.objectAttr = DSMJava.c.objectAttr;
            dsmObjectInfo.associate = DSMJava.c.associate;
            dsmObjectInfo.associateType = DSMJava.c.associateType;
            dsmObjectInfo.block = DSMJava.c.block;
            dsmObjectInfo.root = DSMJava.c.root;
        }
        return dsmObjectInfo;
    }
    
    public int dsmObjectLock(final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmObjectLockN(n, n2, n3, n4, n5, n6, s);
    }
    
    public int dsmObjectUnlock(final long n, final int n2, final int n3, final int n4, final int n5, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmObjectUnlockN(n, n2, n3, n4, n5, s);
    }
    
    public int dsmCursorCreate(final long n, final int n2, final int n3, final int n4, final int n5) {
        return DSMJava.c.retval = DSMJava.c.dsmCursorCreateN(n, n2, n3, n4, n5);
    }
    
    public int dsmCursorFind(final long n, final int n2, final DSMKey dsmKey, final DSMKey dsmKey2, final int n3, final int n4, final int n5, final int n6, final int n7, final DSMKey dsmKey3) {
        DSMJava.c.key.index = dsmKey3.index;
        DSMJava.c.key.keycomps = dsmKey3.keycomps;
        DSMJava.c.key.area = dsmKey3.area;
        DSMJava.c.key.root = dsmKey3.root;
        DSMJava.c.key.keyLen = dsmKey3.keyLen;
        DSMJava.c.key.unknown_comp = dsmKey3.unknown_comp;
        DSMJava.c.key.unique_index = dsmKey3.unique_index;
        DSMJava.c.key.word_index = dsmKey3.word_index;
        DSMJava.c.key.descending_key = dsmKey3.descending_key;
        DSMJava.c.key.ksubstr = dsmKey3.ksubstr;
        DSMJava.c.key.keystr = dsmKey3.keystr;
        return DSMJava.c.retval = DSMJava.c.dsmCursorFindN(n, n2, dsmKey, dsmKey2, n3, n4, n5, n6, n7, DSMJava.c.key);
    }
    
    public int dsmCursorGet(final long n, final int n2) {
        return DSMJava.c.retval = DSMJava.c.dsmCursorGet(n, n2);
    }
    
    public int dsmCursorDelete(final long n, final int n2, final int n3) {
        return DSMJava.c.retval = DSMJava.c.dsmCursorDelete(n, n2, n3);
    }
    
    public int dsmAreaCreate(final long n, final int n2, final int n3, final int n4) {
        return DSMJava.c.retval = DSMJava.c.dsmAreaCreateN(n, n2, n3, n4);
    }
    
    public int dsmAreaDelete(final long n, final int n2) {
        return DSMJava.c.retval = DSMJava.c.dsmAreaDeleteN(n, n2);
    }
    
    public int dsmExtentCreate(final long n, final int n2, final int n3, final int n4, final int n5, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmExtentCreateN(n, n2, n3, n4, n5, s);
    }
    
    public int dsmExtentDelete(final long n, final int n2, final int n3) {
        return DSMJava.c.retval = DSMJava.c.dsmExtentDeleteN(n, n2, n3);
    }
    
    public int dsmSeqCreate(final long n, final DSMSeqParm dsmSeqParm) {
        return DSMJava.c.retval = DSMJava.c.dsmSeqCreateN(n, dsmSeqParm);
    }
    
    public int dsmSeqDelete(final long n, final int n2) {
        return DSMJava.c.retval = DSMJava.c.dsmSeqDeleteN(n, n2);
    }
    
    public int dsmSeqInfo(final long n, final DSMSeqParm dsmSeqParm) {
        return DSMJava.c.retval = DSMJava.c.dsmSeqInfoN(n, dsmSeqParm);
    }
    
    public int dsmSeqGetValue(final long n, final int n2, final int n3) {
        DSMJava.c.retval = DSMJava.c.dsmSeqGetValueN(n, n2, n3);
        return DSMJava.c.seqValue;
    }
    
    public int dsmSeqSetValue(final long n, final int n2, final int n3) {
        return DSMJava.c.retval = DSMJava.c.dsmSeqSetValueN(n, n2, n3);
    }
    
    public int dsmKeyCreate(final long n, final DSMKey dsmKey, final int n2, final int n3, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmKeyCreate(n, dsmKey, n2, n3, s);
    }
    
    public int dsmKeyDelete(final long n, final DSMKey dsmKey, final int n2, final int n3, final int n4, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmKeyDeleteN(n, dsmKey, n2, n3, n4, s);
    }
    
    public int dsmRecordCreate(final long n, final DSMRecord dsmRecord, final int n2) {
        return DSMJava.c.retval = DSMJava.c.dsmRecordCreateN(n, dsmRecord, dsmRecord.pbuffer, n2);
    }
    
    public int dsmRecordDelete(final long n, final DSMRecord dsmRecord, final int n2, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmRecordDeleteN(n, dsmRecord, dsmRecord.pbuffer, n2, s);
    }
    
    public int dsmRecordGet(final long n, final DSMRecord dsmRecord, final int n2) {
        return DSMJava.c.retval = DSMJava.c.dsmRecordGetN(n, dsmRecord, dsmRecord.pbuffer, n2);
    }
    
    public int dsmRecordUpdateN(final long n, final DSMRecord dsmRecord, final int n2, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmRecordUpdateN(n, dsmRecord, dsmRecord.pbuffer, n2, s);
    }
    
    public int dsmBlobGet(final long n, final DSMBlob dsmBlob, final int n2, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmBlobGetN(n, dsmBlob, dsmBlob.pBuffer, n2, s);
    }
    
    public int dsmBlobPut(final long n, final DSMBlob dsmBlob, final int n2, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmBlobPutN(n, dsmBlob, dsmBlob.pBuffer, n2, s);
    }
    
    public int dsmBlobUpdate(final long n, final DSMBlob dsmBlob, final int n2, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmBlobUpdateN(n, dsmBlob, dsmBlob.pBuffer, n2, s);
    }
    
    public int dsmBlobDelete(final long n, final DSMBlob dsmBlob, final int n2, final String s) {
        return DSMJava.c.retval = DSMJava.c.dsmBlobDeleteN(n, dsmBlob, dsmBlob.pBuffer, n2, s);
    }
    
    public int dsmBlobStart(final long n, final DSMBlob dsmBlob) {
        return DSMJava.c.retval = DSMJava.c.dsmBlobStartN(n, dsmBlob);
    }
    
    public int dsmBlobEnd(final long n, final DSMBlob dsmBlob) {
        return DSMJava.c.retval = DSMJava.c.dsmBlobEnd(n, dsmBlob);
    }
    
    public int dsmBlobUnlock(final long n, final DSMBlob dsmBlob) {
        return DSMJava.c.retval = DSMJava.c.dsmBlobUnlockN(n, dsmBlob);
    }
    
    static {
        System.loadLibrary("DSMJavaLib");
        DSMJava.c = new DSMJava();
    }
}
