// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.JMSException;
import java.util.Enumeration;
import javax.jms.QueueBrowser;
import com.progress.open4gl.BooleanHolder;
import javax.jms.QueueConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.QueueConnection;
import javax.jms.Connection;
import javax.jms.QueueSender;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Destination;
import javax.jms.QueueSession;
import javax.jms.Session;

public class QueueSessionContainer extends SessionContainer
{
    QueueSessionContainer() throws Exception {
        super.log.LogMsgln(2, true, super.connectionId, "In QueueSessionContainer()");
    }
    
    protected String getDfltDestType() {
        return "queue";
    }
    
    protected ListenerCollection createListenersCollection(final Session session, final MessageQueue messageQueue, final DestCache destCache) throws Exception {
        return new QueueListenerCollection((QueueSession)session, messageQueue, destCache);
    }
    
    protected MessageEventServer createEventServer(final MessageQueue messageQueue, final DestCache destCache) throws Exception {
        return new QueueMessageEventServer(messageQueue, destCache);
    }
    
    protected String getDestName(final Destination destination) throws Exception {
        return ((Queue)destination).getQueueName();
    }
    
    protected Destination getDestination(final DestCache destCache, final Session session, final String s) throws Exception {
        return (Destination)destCache.getQueue(session, s);
    }
    
    protected void unsubscribe(final Session session, final String s) throws Exception {
    }
    
    protected void send(final MessageProducer messageProducer, final Message message, final int n, final int n2, final long n3) throws Exception {
        ((QueueSender)messageProducer).send(message, n, n2, n3);
    }
    
    protected MessageProducer createProducer(final Session session, final DestCache destCache, final String s) throws Exception {
        return destCache.getProducer(session, s);
    }
    
    protected Session createSession(final Connection connection, final boolean b, final int n) throws Exception {
        final QueueSession queueSession = ((QueueConnection)connection).createQueueSession(b, n);
        super.eventServer.setQueueSession((Session)queueSession);
        return (Session)queueSession;
    }
    
    protected Connection createConnection(final ConnectionFactory connectionFactory, final String s, final String s2) throws Exception {
        if (s.length() == 0) {
            return (Connection)((QueueConnectionFactory)connectionFactory).createQueueConnection();
        }
        return (Connection)((QueueConnectionFactory)connectionFactory).createQueueConnection(s, s2);
    }
    
    public String browse(final String str, final String s, final int n, final BooleanHolder booleanHolder) throws Throwable {
        super.log.LogMsgln(3, true, super.connectionId, "browse(): Request to browse: " + str);
        String string = null;
        boolean b = true;
        booleanHolder.setBooleanValue(true);
        try {
            final QueueBrowser browser = ((QueueSession)super.sendSession).createBrowser((Queue)this.getDestination(super.replyCache, super.sendSession, str), s);
            final Enumeration enumeration = browser.getEnumeration();
            while (enumeration.hasMoreElements()) {
                b = false;
                final Message message = enumeration.nextElement();
                if (super.browseQueue.isFull()) {
                    super.browseQueue.setQueueLimit(100 + super.browseQueue.getQueueLimit());
                }
                super.browseQueue.enqueue(new MessageContainer(message, 1, n, super.connectionId));
            }
            browser.close();
            if (!b) {
                super.queue.enqueue(new WakeupMessage());
                super.queue.wakeup();
            }
            booleanHolder.setBooleanValue(false);
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                super.log.LogStackTrace(1, true, super.connectionId, t);
            }
            else {
                super.log.LogStackTrace(3, true, super.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
}
