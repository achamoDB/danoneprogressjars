// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.common.Tools;
import com.progress.international.resources.ProgressResources;
import com.progress.chimera.log.ConnectionManagerLog;

public class StateObject
{
    State state;
    static ConnectionManagerLog theLog;
    
    public StateObject(final State state) {
        this.state = null;
        this.state = state;
    }
    
    public void changeState(final Object o, final State obj) throws StateException {
        final State state = this.state;
        try {
            StateObject.theLog.log(4, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Changing state of object", new Object[] { o.toString(), state.name(), obj.name() }));
            if (!obj.transitionOK(o, state)) {
                throw new StateException("Bad configuration state transition: " + state + "->" + obj);
            }
            state.exit(o);
            if (state.nextNormalState != null && !Tools.isaInstance(obj, state.nextNormalState)) {
                state.cleanup(o, obj);
            }
            obj.transition(o, state);
            obj.enter(o);
            (this.state = obj).during(o);
        }
        catch (StateException ex) {
            Tools.px(ex, "Error changing states from " + this.state + " to " + obj + ".");
            throw ex;
        }
    }
    
    public State get() {
        return this.state;
    }
    
    public void set(final State state) {
        this.state = state;
    }
    
    static {
        StateObject.theLog = ConnectionManagerLog.get();
    }
}
