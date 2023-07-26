// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.TopicSubscriber;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.MessageConsumer;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TopicSession;

class TopicListenerCollection extends ListenerCollection
{
    TopicListenerCollection(final TopicSession topicSession, final MessageQueue messageQueue, final DestCache destCache) {
        super((Session)topicSession, messageQueue, destCache);
    }
    
    protected MessageConsumer createConsumer(final Session session, final Destination destination, final String s, final String s2, final boolean b) throws Exception {
        if (s == null) {
            return (MessageConsumer)((TopicSession)session).createSubscriber((Topic)destination, s2, b);
        }
        return (MessageConsumer)((TopicSession)session).createDurableSubscriber((Topic)destination, s, s2, b);
    }
    
    protected Destination createTemporaryDestination(final Session session) throws Exception {
        return (Destination)((TopicSession)session).createTemporaryTopic();
    }
    
    protected void deleteTemporary(final Destination destination) throws Exception {
        ((TemporaryTopic)destination).delete();
    }
    
    protected Destination getDestination(final DestCache destCache, final Session session, final String s) throws Exception {
        return (Destination)destCache.getTopic(session, s);
    }
    
    protected Destination getDestination(final MessageConsumer messageConsumer) throws Exception {
        return (Destination)((TopicSubscriber)messageConsumer).getTopic();
    }
}
