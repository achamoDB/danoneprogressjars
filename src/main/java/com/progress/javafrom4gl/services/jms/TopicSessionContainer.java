// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.TopicConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.Connection;
import javax.jms.TopicPublisher;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import javax.jms.Destination;
import javax.jms.TopicSession;
import javax.jms.Session;

public class TopicSessionContainer extends SessionContainer
{
    TopicSessionContainer() throws Exception {
        super.log.LogMsgln(2, true, super.connectionId, "In TopicSessionContainer()");
    }
    
    protected String getDfltDestType() {
        return "topic";
    }
    
    protected ListenerCollection createListenersCollection(final Session session, final MessageQueue messageQueue, final DestCache destCache) throws Exception {
        return new TopicListenerCollection((TopicSession)session, messageQueue, destCache);
    }
    
    protected MessageEventServer createEventServer(final MessageQueue messageQueue, final DestCache destCache) throws Exception {
        return new TopicMessageEventServer(messageQueue, destCache);
    }
    
    protected String getDestName(final Destination destination) throws Exception {
        return ((Topic)destination).getTopicName();
    }
    
    protected Destination getDestination(final DestCache destCache, final Session session, final String s) throws Exception {
        return (Destination)destCache.getTopic(session, s);
    }
    
    protected void unsubscribe(final Session session, final String s) throws Exception {
        ((TopicSession)session).unsubscribe(s);
    }
    
    protected void send(final MessageProducer messageProducer, final Message message, final int n, final int n2, final long n3) throws Exception {
        ((TopicPublisher)messageProducer).publish(message, n, n2, n3);
    }
    
    protected MessageProducer createProducer(final Session session, final DestCache destCache, final String s) throws Exception {
        if (session.getTransacted()) {
            return destCache.getPublisher(session, s);
        }
        return (MessageProducer)((TopicSession)session).createPublisher(destCache.getTopic(session, s));
    }
    
    protected Session createSession(final Connection connection, final boolean b, final int n) throws Exception {
        return (Session)((TopicConnection)connection).createTopicSession(b, n);
    }
    
    protected Connection createConnection(final ConnectionFactory connectionFactory, final String s, final String s2) throws Exception {
        if (s.length() == 0) {
            return (Connection)((TopicConnectionFactory)connectionFactory).createTopicConnection();
        }
        return (Connection)((TopicConnectionFactory)connectionFactory).createTopicConnection(s, s2);
    }
}
