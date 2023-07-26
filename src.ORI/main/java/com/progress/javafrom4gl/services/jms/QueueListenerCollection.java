// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.QueueReceiver;
import javax.jms.TemporaryQueue;
import javax.jms.Queue;
import javax.jms.MessageConsumer;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.QueueSession;

class QueueListenerCollection extends ListenerCollection
{
    QueueListenerCollection(final QueueSession queueSession, final MessageQueue messageQueue, final DestCache destCache) {
        super((Session)queueSession, messageQueue, destCache);
    }
    
    protected MessageConsumer createConsumer(final Session session, final Destination destination, final String s, final String s2, final boolean b) throws Exception {
        return (MessageConsumer)((QueueSession)session).createReceiver((Queue)destination, s2);
    }
    
    protected Destination createTemporaryDestination(final Session session) throws Exception {
        return (Destination)((QueueSession)session).createTemporaryQueue();
    }
    
    protected void deleteTemporary(final Destination destination) throws Exception {
        ((TemporaryQueue)destination).delete();
    }
    
    protected Destination getDestination(final DestCache destCache, final Session session, final String s) throws Exception {
        return (Destination)destCache.getQueue(session, s);
    }
    
    protected Destination getDestination(final MessageConsumer messageConsumer) throws Exception {
        return (Destination)((QueueReceiver)messageConsumer).getQueue();
    }
}
