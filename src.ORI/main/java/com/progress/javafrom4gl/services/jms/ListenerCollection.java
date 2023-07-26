// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.Message;
import javax.jms.TopicSubscriber;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import progress.message.jclient.QueueReceiver;
import javax.jms.Destination;
import java.util.Enumeration;
import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.javafrom4gl.Log;
import javax.jms.Session;
import java.util.Hashtable;

class ListenerCollection extends Hashtable
{
    static final String NEW_LINE;
    private Session session;
    private MessageQueue queue;
    private DestCache replyCache;
    private String connectionID;
    private Log log;
    
    ListenerCollection(final Session session, final MessageQueue queue, final DestCache replyCache) {
        this.log = ServiceRuntime.getLog();
        this.session = session;
        this.queue = queue;
        this.replyCache = replyCache;
    }
    
    void setConnID(final String connectionID) {
        this.connectionID = connectionID;
    }
    
    public synchronized String toString() {
        String s = ListenerCollection.NEW_LINE + "Listeners: " + ListenerCollection.NEW_LINE;
        final Enumeration<Object> elements = this.elements();
        while (elements.hasMoreElements()) {
            final Object nextElement = elements.nextElement();
            if (nextElement != null) {
                s = s + "    " + nextElement.toString() + ListenerCollection.NEW_LINE;
            }
        }
        return s + "End Listeners: " + ListenerCollection.NEW_LINE;
    }
    
    Destination createReplyListener(final int n, final String s) throws Exception {
        final Destination temporaryDestination = this.createTemporaryDestination(this.session);
        try {
            this.createListener(temporaryDestination, null, n, s, false, -1, -1, true);
        }
        catch (Exception ex) {
            this.deleteTemporary(temporaryDestination);
            throw ex;
        }
        return temporaryDestination;
    }
    
    Destination createReplyTopicListener(final int n, final String s) throws Exception {
        final Destination temporaryTopicDestination = this.createTemporaryTopicDestination(this.session);
        try {
            this.createListener(temporaryTopicDestination, null, n, s, false, -1, -1, true);
        }
        catch (Exception ex) {
            this.deleteTemporary(temporaryTopicDestination);
            throw ex;
        }
        return temporaryTopicDestination;
    }
    
    void createDestinationListener(final int n, final String s, final String s2, final String s3, final int n2, final int n3, final boolean b) throws Exception {
        this.createListener(this.getDestination(this.replyCache, this.session, s), s2, n, s3, b, n2, n3, false);
    }
    
    void createDestinationListener(final int n, final String s, final String s2, final String s3, final String s4, final int n2, final int n3, final boolean b) throws Exception {
        Object o;
        if (s2.equals("ps")) {
            o = this.replyCache.getTopic(this.session, s);
        }
        else {
            o = this.replyCache.getQueue(this.session, s);
        }
        this.createListener((Destination)o, s3, n, s4, b, n2, n3, false);
    }
    
    private void createListener(final Destination destination, final String s, final int value, final String s2, final boolean b, final int n, final int n2, final boolean b2) throws Exception {
        final MessageConsumer consumer = this.createConsumer(this.session, destination, s, s2, b);
        if (consumer instanceof QueueReceiver) {
            if (n > 0) {
                ((progress.message.jclient.MessageConsumer)consumer).setPrefetchCount(n);
                this.log.LogMsgln(3, true, this.connectionID, "prefetchCount set to " + n);
            }
            if (n2 > 0) {
                ((progress.message.jclient.MessageConsumer)consumer).setPrefetchThreshold(n2);
                this.log.LogMsgln(3, true, this.connectionID, "Threshold set to " + n2);
            }
        }
        final Listener listener = new Listener(value, this.queue, consumer, destination, b2, this.connectionID);
        this.put(new Integer(value), (MessageListener)listener);
        consumer.setMessageListener((MessageListener)listener);
        this.log.LogMsgln(3, true, this.connectionID, "createListener(): Message listener is set.");
    }
    
    void deleteAll() throws Exception {
        final Enumeration<Integer> keys = this.keys();
        while (keys.hasMoreElements()) {
            this.deleteListener(keys.nextElement());
        }
    }
    
    void deleteListener(final int n) throws Exception {
        try {
            final Listener listener = this.getListener(n);
            if (listener == null) {
                return;
            }
            final MessageConsumer consumer = listener.getConsumer();
            final Destination destination = this.getDestination(consumer);
            consumer.close();
            if (listener.getNewTemporary()) {
                this.deleteTemporary(destination);
            }
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            this.removeListener(n);
        }
    }
    
    boolean listenerExists(final int n) {
        return this.getListener(n) != null;
    }
    
    private Listener getListener(final int value) {
        return this.get(new Integer(value));
    }
    
    private void removeListener(final int value) {
        this.remove(new Integer(value));
    }
    
    Destination getDestination(final int n) throws Exception {
        return this.getDestination(this.getListener(n).getConsumer());
    }
    
    protected MessageConsumer createConsumer(final Session session, final Destination destination, final String s, final String s2, final boolean b) throws Exception {
        if (destination instanceof Topic && s != null) {
            return (MessageConsumer)this.session.createDurableSubscriber((Topic)destination, s, s2, b);
        }
        return this.session.createConsumer(destination, s2);
    }
    
    protected Destination createTemporaryDestination(final Session session) throws Exception {
        return (Destination)session.createTemporaryQueue();
    }
    
    protected Destination createTemporaryTopicDestination(final Session session) throws Exception {
        return (Destination)session.createTemporaryTopic();
    }
    
    protected void deleteTemporary(final Destination destination) throws Exception {
        ((TemporaryQueue)destination).delete();
    }
    
    protected Destination getDestination(final DestCache destCache, final Session session, final String s) throws Exception {
        return (Destination)destCache.getQueue(session, s);
    }
    
    protected Destination getDestination(final MessageConsumer messageConsumer) throws Exception {
        if (messageConsumer instanceof javax.jms.QueueReceiver) {
            return (Destination)((javax.jms.QueueReceiver)messageConsumer).getQueue();
        }
        return (Destination)((TopicSubscriber)messageConsumer).getTopic();
    }
    
    static {
        NEW_LINE = new String(System.getProperty("line.separator"));
    }
    
    static class Listener implements MessageListener
    {
        private int listenerID;
        private MessageQueue queue;
        private MessageConsumer consumer;
        private boolean newTemporary;
        private Destination destination;
        private String connectionID;
        private Log log;
        
        public String toString() {
            final String string = new Integer(this.listenerID).toString();
            String string2 = null;
            if (this.destination != null) {
                string2 = this.destination.toString();
            }
            return "Listener: " + string + " destination: " + string2;
        }
        
        Listener(final int listenerID, final MessageQueue queue, final MessageConsumer consumer, final Destination destination, final boolean newTemporary, final String connectionID) {
            this.log = ServiceRuntime.getLog();
            this.listenerID = listenerID;
            this.queue = queue;
            this.consumer = consumer;
            this.newTemporary = newTemporary;
            this.destination = destination;
            this.connectionID = connectionID;
        }
        
        boolean getNewTemporary() {
            return this.newTemporary;
        }
        
        MessageConsumer getConsumer() {
            return this.consumer;
        }
        
        public void onMessage(final Message message) {
            String jmsMessageID = null;
            try {
                jmsMessageID = message.getJMSMessageID();
            }
            catch (Exception ex2) {}
            if (this.queue.isLocked()) {
                this.log.LogMsgln(3, true, this.connectionID, "onMessage(): Queue locked - discarding message: " + jmsMessageID);
                return;
            }
            this.log.LogMsgln(3, true, this.connectionID, "onMessage(): Queuing message: " + jmsMessageID);
            try {
                this.queue.enqueue(new MessageContainer(message, 1, this.listenerID, this.connectionID));
            }
            catch (Exception ex) {
                throw new Error("Queue error: " + ex.getMessage());
            }
            this.log.LogMsgln(3, true, this.connectionID, "onMessage(): Waiting for delivery: " + jmsMessageID);
            this.log.LogMsgln(36, true, this.connectionID, "onMessage(): Queue status:  empty: " + this.queue.isEmpty() + ", should block: " + this.queue.isBlock() + ", locked: " + this.queue.isLocked());
            this.queue.block();
            this.log.LogMsgln(3, true, this.connectionID, "onMessage(): Delivery complete: " + jmsMessageID);
        }
    }
}
