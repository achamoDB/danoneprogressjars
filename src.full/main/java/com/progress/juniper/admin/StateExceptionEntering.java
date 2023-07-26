// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class StateExceptionEntering extends StateException
{
    State state;
    
    StateExceptionEntering(final State state) {
        this.state = state;
    }
    
    StateExceptionEntering(final State state, final String s) {
        this.state = state;
    }
}
