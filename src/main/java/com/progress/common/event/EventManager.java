// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

import java.util.Enumeration;
import java.util.Vector;

public class EventManager
{
    Vector m_interestList;
    IEventListener m_unhandledEventsListener;
    
    public EventManager() {
        this.m_interestList = new Vector();
        this.m_unhandledEventsListener = null;
    }
    
    public void expressInterest(final Class clazz, final IEventListener eventListener) {
        this.m_interestList.addElement(new Interest(clazz, eventListener));
    }
    
    public void post(final ProEvent proEvent) {
        final Class<? extends ProEvent> class1 = proEvent.getClass();
        boolean b = true;
        final Enumeration<Interest> elements = (Enumeration<Interest>)this.m_interestList.elements();
        while (elements.hasMoreElements()) {
            final Interest interest = elements.nextElement();
            if (this.subclassOf(class1, interest.getEventClass())) {
                try {
                    interest.getListener().processEvent(proEvent);
                    b = false;
                }
                catch (Throwable t) {}
            }
        }
        if (b && this.m_unhandledEventsListener != null) {
            this.m_unhandledEventsListener.processEvent(proEvent);
        }
    }
    
    private boolean subclassOf(final Class clazz, final Class clazz2) {
        return clazz != null && (clazz == clazz2 || this.subclassOf(clazz.getSuperclass(), clazz2));
    }
    
    public void unhandledEvents(final IEventListener unhandledEventsListener) {
        this.m_unhandledEventsListener = unhandledEventsListener;
    }
}
