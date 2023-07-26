// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.io.BufferedReader;

abstract class ServerGroupReader extends ProcessReader
{
    JAService serverGroup;
    
    public ServerGroupReader(final JAConfiguration jaConfiguration, final JAService serverGroup, final BufferedReader bufferedReader, final Process process) {
        super(jaConfiguration, bufferedReader, process);
        this.serverGroup = null;
        this.serverGroup = serverGroup;
    }
    
    String descr() {
        return this.serverGroup.name();
    }
}
