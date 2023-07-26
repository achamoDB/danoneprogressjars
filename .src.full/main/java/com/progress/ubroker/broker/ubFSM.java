// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubConstants;

public abstract class ubFSM implements ubConstants
{
    static final int OFST_ACTION = 0;
    static final int OFST_NEXTSTATE = 1;
    private String[] desc_state;
    private String[] desc_event;
    private String[] desc_action;
    private IAppLogger log;
    private byte[][][] FSM;
    private String tableName;
    
    public ubFSM(final String s, final byte[][][] array, final String[] array2, final String[] array3, final String[] array4, final IAppLogger appLogger) {
        this.init(s, array, appLogger, array2, array3, array4);
    }
    
    public byte getNextState(final byte b, final byte b2) {
        return this.FSM[b][b2][1];
    }
    
    public byte getAction(final byte b, final byte b2) {
        return this.FSM[b][b2][0];
    }
    
    public void print(final IAppLogger appLogger, final int n, final int n2, final byte b, final byte b2, final byte b3, final byte b4) {
        if (n == 2) {
            appLogger.logBasic(n2, "FSM : state=" + this.desc_state[b] + "event=" + this.desc_event[b2] + "action=" + this.desc_action[b3]);
        }
        if (n == 3) {
            appLogger.logVerbose(n2, "FSM : state=" + this.desc_state[b] + "event=" + this.desc_event[b2] + "action=" + this.desc_action[b3]);
        }
        if (n == 4) {
            appLogger.logExtended(n2, "FSM : state=" + this.desc_state[b] + "event=" + this.desc_event[b2] + "action=" + this.desc_action[b3]);
        }
    }
    
    private void init(final String tableName, final byte[][][] fsm, final IAppLogger log, final String[] desc_state, final String[] desc_event, final String[] desc_action) {
        this.tableName = tableName;
        this.FSM = fsm;
        this.log = log;
        this.desc_state = desc_state;
        this.desc_event = desc_event;
        this.desc_action = desc_action;
    }
}
