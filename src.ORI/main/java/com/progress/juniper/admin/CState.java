// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.message.jpMsg;

abstract class CState extends State implements jpMsg, MProservJuniperAPI
{
    CState(final Class clazz) {
        super(clazz);
    }
    
    public void during(final Object o) {
    }
    
    static int getTiming(final String s) {
        JAPlugIn.get();
        return JAPlugIn.getTiming(s);
    }
    
    static int getProratedTiming(final String s) {
        JAPlugIn.get();
        return JAPlugIn.getProratedTiming(s);
    }
    
    static int getProratedTiming(final String s, final String s2) {
        JAPlugIn.get();
        return JAPlugIn.getProratedTiming(s, s2);
    }
}
