// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.io.IOException;
import java.io.FileNotFoundException;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.common.networkevents.EventBroker;
import com.progress.ubroker.util.IPropConst;

public class MergePropMgr extends PropertyManager implements IPropConst
{
    public MergePropMgr() {
        super(null, false);
        super.m_saveSorted = true;
    }
    
    public MergePropMgr(final String s) throws LoadIOException, PropertySyntaxException, PropertyVersionException, LoadFileNotFoundException, GroupNameException, PropertyNameException, PropertyValueException, NoSuchGroupException, PropertyException, PropMgrUtils.EnumGroupError, PropMgrUtils.LoadPropFileError, PropMgrUtils.CantGetParentGroup, PropMgrUtils.CantGetPropCollection, FileNotFoundException, IOException {
        this();
        this.load(s, true);
    }
    
    protected boolean chkPropertyVersion(String s) {
        if (s != null) {
            s = s.trim().toLowerCase();
            final int index = s.indexOf("%% version b001");
            if (index >= 0) {
                s = s.substring(index + "%% version".length()).trim();
                if (s.equals("b001")) {
                    return true;
                }
            }
        }
        return true;
    }
}
