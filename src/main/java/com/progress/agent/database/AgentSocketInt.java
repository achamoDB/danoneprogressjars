// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.io.IOException;

public class AgentSocketInt
{
    public AgentSocketInt() throws IOException {
        if (AgentPlugIn.get() != null) {
            AgentPlugIn.get();
            AgentPlugIn.getServerComSockAgent();
        }
    }
}
