// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubConstants;

class ubServerIPC implements ubConstants
{
    public static final int RET_OK = 0;
    public static final int EOF = -1;
    public static final int STATE_CLOSED = 0;
    public static final int STATE_OPEN = 1;
    long id;
    long key;
    byte state;
    
    public ubServerIPC(final long key) throws ServerIPCException {
        this.key = key;
        this.id = 0L;
        this.state = 0;
        this.createChannel(key);
    }
    
    public void create(final long n) throws ServerIPCException {
        final long channel = this.createChannel(n);
        if (channel != 0L) {
            throw new ServerIPCException("createChannel error " + channel);
        }
        this.id = channel;
        this.state = 1;
    }
    
    public void delete() throws ServerIPCException {
        final long deleteChannel = this.deleteChannel(this.id);
        this.id = 0L;
        this.state = 0;
        if (deleteChannel != 0L) {
            throw new ServerIPCException("deleteChannel error " + deleteChannel);
        }
    }
    
    public long write(final byte[] array, final int n, final int n2) throws ServerIPCException {
        final long writeChannel = this.writeChannel(this.id, array, n, n2, false);
        if (writeChannel < 0L) {
            throw new ServerIPCException("writeChannel error " + writeChannel);
        }
        return writeChannel;
    }
    
    public long writeLast(final byte[] array, final int n, final int n2) throws ServerIPCException {
        final long writeChannel = this.writeChannel(this.id, array, n, n2, true);
        if (writeChannel < 0L) {
            throw new ServerIPCException("writeChannel error " + writeChannel);
        }
        return writeChannel;
    }
    
    public long read(final byte[] array, final int n, final int n2) throws ServerIPCException {
        final long channel = this.readChannel(this.id, array, n, n2);
        if (channel < 0L) {
            throw new ServerIPCException("readChannel error " + channel);
        }
        return (channel == 0L) ? -1L : channel;
    }
    
    public void setStop() throws ServerIPCException {
        final long postStopChannel = this.postStopChannel(this.id);
        if (postStopChannel != 0L) {
            throw new ServerIPCException("postStopChannel error " + postStopChannel);
        }
    }
    
    private native long createChannel(final long p0);
    
    private native long deleteChannel(final long p0);
    
    private native long readChannel(final long p0, final byte[] p1, final int p2, final int p3);
    
    private native long writeChannel(final long p0, final byte[] p1, final int p2, final int p3, final boolean p4);
    
    private native long postStopChannel(final long p0);
}
