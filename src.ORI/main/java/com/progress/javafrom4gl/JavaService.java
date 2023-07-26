// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl;

import java.util.Properties;

public interface JavaService
{
    void _startup(final Properties p0) throws Exception;
    
    ServiceConnection _connect(final String p0, final String p1, final String p2, final String p3, final String p4) throws Exception;
    
    void _shutdown();
}
