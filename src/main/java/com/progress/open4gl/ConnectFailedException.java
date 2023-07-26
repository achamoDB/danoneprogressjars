// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.ubroker.util.ubMsg;
import com.progress.message.jcMsg;

public class ConnectFailedException extends ConnectException implements jcMsg
{
    public ConnectFailedException(final int n) {
        super(7665970990714723411L, new Object[] { ubMsg.DESC_UBRSP_EXT[n] });
    }
    
    public ConnectFailedException(final String procReturnString) {
        super(7665970990714723411L, new Object[] { procReturnString });
        super.setProcReturnString(procReturnString);
    }
    
    public ConnectFailedException(final int n, final String procReturnString) {
        super(7665970990714723411L, new Object[] { ubMsg.DESC_UBRSP_EXT[n] });
        super.setProcReturnString(procReturnString);
    }
}
