// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import com.progress.common.util.IMessageCallback;

class MessageHandler implements IMessageCallback
{
    public void handleMessage(final String x) {
        System.out.println(x);
    }
    
    public void handleMessage(final int n, final String x) {
        System.out.println(x);
    }
    
    public void handleException(final Throwable t, final String str) {
        System.out.println("EXCP - " + str + " " + t.getClass() + " " + t.getMessage());
    }
}
