// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.message.jpMsg;

abstract class SState extends State implements jpMsg, MProservJuniperAPI
{
    SState(final Class clazz) {
        super(clazz);
    }
    
    public void during(final Object o) {
        final JAService jaService = (JAService)o;
    }
}
