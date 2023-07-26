// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.aia;

import com.progress.ubroker.tools.SvcStartArgsPkt;
import com.progress.ubroker.tools.IBTMsgCodes;
import com.progress.ubroker.tools.SvcControlCmd;

public class AiaSvcControlCmd extends SvcControlCmd implements IBTMsgCodes
{
    public AiaSvcControlCmd(final SvcStartArgsPkt svcStartArgsPkt, final String s, final String s2, final String s3) {
        super(svcStartArgsPkt, s, s2, s3);
    }
}
