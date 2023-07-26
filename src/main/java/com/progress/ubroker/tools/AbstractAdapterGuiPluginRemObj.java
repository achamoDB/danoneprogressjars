// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.rmi.RemoteException;
import com.progress.ubroker.util.IToolCmdConst;
import com.progress.ubroker.util.IPropConst;
import com.progress.chimera.common.IChimeraHierarchy;

public abstract class AbstractAdapterGuiPluginRemObj extends AbstractGuiPluginRemObj implements IChimeraHierarchy, IPMUAccessCallback, IYodaRMI, IYodaSharedResources, IBTMsgCodes, IPropConst, IToolCmdConst
{
    public AbstractAdapterGuiPluginRemObj(final AbstractGuiPlugin abstractGuiPlugin, final String s, final PropMgrPlugin propMgrPlugin) throws RemoteException {
        super(abstractGuiPlugin, s, propMgrPlugin);
    }
}
