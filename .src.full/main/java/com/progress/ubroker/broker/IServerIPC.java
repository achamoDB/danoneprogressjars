// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubMsg;

public interface IServerIPC
{
    public static final int WAIT_FOREVER = 0;
    public static final int RET_OK = 0;
    public static final int EOF = -1;
    
    void create(final int p0) throws ServerIPCException;
    
    void create(final int p0, final int p1) throws ServerIPCException;
    
    void delete() throws ServerIPCException;
    
    void write(final ubMsg p0, final boolean p1) throws ServerIPCException;
    
    void writeLast(final ubMsg p0, final boolean p1) throws ServerIPCException;
    
    ubMsg read() throws ServerIPCException;
    
    int available() throws ServerIPCException;
}
