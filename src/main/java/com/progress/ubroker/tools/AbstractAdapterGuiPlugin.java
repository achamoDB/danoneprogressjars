// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.chimera.adminserver.IAdministrationServer;

public abstract class AbstractAdapterGuiPlugin extends AbstractGuiPlugin implements IBTMsgCodes
{
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        return super.init(n, administrationServer, array);
    }
    
    public abstract void shutdown();
}
