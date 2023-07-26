// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.util.ubThread;
import com.progress.ubroker.util.RequestQueue;

public interface IServerThreadControl
{
    int start_Servers(final int p0, final RequestQueue p1);
    
    ubThread start_ServerWithArgs(final Object[] p0, final RequestQueue p1);
    
    int stop_Servers(final int p0, final RequestQueue p1);
    
    int stop_Server(final int p0, final RequestQueue p1);
    
    String queryBroker(final int p0);
}
