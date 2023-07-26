// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.common.IChimeraHierarchy;

public interface IAgentDatabaseHandle extends IChimeraHierarchy, IJAHierarchy, IJAExecutableObject
{
    public static final String AGENT_DATABASE_CLASSNAME = "com.progress.agent.database.AgentDatabase";
    public static final String EDBA_CRASH_CLASSNAME = "com.progress.agent.database.EDBACrash";
    public static final String EDBA_STATE_CHANGE_CLASSNAME = "com.progress.agent.database.EDBAStateChanged";
    public static final String AGENT_SOCKETINT_CLASSNAME = "com.progress.agent.database.AgentSocketInt";
}
