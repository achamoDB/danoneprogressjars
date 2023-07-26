// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

final class Interest
{
    Class m_eventClass;
    IEventListener m_listener;
    
    public Interest(final Class eventClass, final IEventListener listener) {
        this.m_eventClass = eventClass;
        this.m_listener = listener;
    }
    
    public final Class getEventClass() {
        return this.m_eventClass;
    }
    
    public final IEventListener getListener() {
        return this.m_listener;
    }
}
