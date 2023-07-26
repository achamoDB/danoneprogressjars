// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import java.util.StringTokenizer;
import java.util.Enumeration;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.TemporaryTopic;
import javax.jms.TemporaryQueue;
import javax.jms.Queue;
import javax.jms.Topic;
import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.javafrom4gl.Log;
import java.util.Hashtable;

class DestCache
{
    private Hashtable topicCache;
    private Hashtable queueCache;
    private Hashtable producerCache;
    private Hashtable publisherCache;
    private Hashtable tempQueueCache;
    private Hashtable tempTopicCache;
    private Log log;
    
    public DestCache(final int n) {
        this.log = ServiceRuntime.getLog();
        this.topicCache = new Hashtable(n);
        this.queueCache = new Hashtable(n);
        this.producerCache = new Hashtable(n);
        this.publisherCache = new Hashtable(n);
        this.tempQueueCache = new Hashtable(n);
        this.tempTopicCache = new Hashtable(n);
    }
    
    public Topic putTopic(final String key, final Topic value) {
        if (this.topicCache.size() >= 1000) {
            this.log.LogMsgln(1, true, "DestCache.putTopic(): ", "WARNING: TopicCache size is " + this.topicCache.size());
        }
        return this.topicCache.put(key, value);
    }
    
    public Queue putQueue(final String key, final Queue value) {
        if (this.queueCache.size() >= 1000) {
            this.log.LogMsgln(1, true, "DestCache.putQueue(): ", "WARNING: QueueCache size is " + this.queueCache.size());
        }
        return this.queueCache.put(key, value);
    }
    
    public Queue putTempQueue(final String key, final TemporaryQueue value) {
        if (this.tempQueueCache.size() >= 1000) {
            this.log.LogMsgln(1, true, "DestCache.putTempQueue(): ", "WARNING: tempQueueCache size is " + this.tempQueueCache.size());
        }
        return this.tempQueueCache.put(key, value);
    }
    
    public Topic putTempTopic(final String key, final TemporaryTopic value) {
        if (this.tempTopicCache.size() >= 1000) {
            this.log.LogMsgln(1, true, "DestCache.putTempTopic(): ", "WARNING: tempTopicCache size is " + this.tempTopicCache.size());
        }
        return this.tempTopicCache.put(key, value);
    }
    
    public MessageProducer putPublisher(final String s, final MessageProducer value) {
        if (s.indexOf("TemporaryQueues") >= 0 || s.indexOf("DIRECTHttp") >= 0) {
            return null;
        }
        if (this.publisherCache.size() >= 1000) {
            this.log.LogMsgln(1, true, "DestCache.putPublisher(): ", "WARNING: PublisherCache size is " + this.publisherCache.size());
            try {
                this.closePublishers();
            }
            catch (Exception ex) {}
        }
        this.log.LogMsgln(3, true, "DestCache.putPublisher(): ", "Adding publisher to cache - " + s);
        return this.publisherCache.put(s, value);
    }
    
    public MessageProducer putProducer(final String s, final MessageProducer value) {
        if (s.indexOf("TemporaryQueues") >= 0 || s.indexOf("DIRECTHttp") >= 0) {
            return null;
        }
        if (this.producerCache.size() >= 1000) {
            this.log.LogMsgln(1, true, "DestCache.putProducer(): ", "WARNING: ProducerCache size is " + this.producerCache.size());
            try {
                this.closeProducers();
            }
            catch (Exception ex) {}
        }
        this.log.LogMsgln(3, true, "DestCache.putProducer(): ", "Adding producer to cache - " + s);
        return this.producerCache.put(s, value);
    }
    
    Topic getTopic(final Session session, final String key) throws Exception {
        final Topic topic = this.topicCache.get(key);
        if (topic != null) {
            return topic;
        }
        final Topic topic2 = jms.getTopic(key);
        if (topic2 != null) {
            return topic2;
        }
        return session.createTopic(key);
    }
    
    Queue getQueue(final Session session, final String key) throws Exception {
        final Queue queue = this.queueCache.get(key);
        if (queue != null) {
            return queue;
        }
        final Queue queue2 = jms.getQueue(key);
        if (queue2 != null) {
            return queue2;
        }
        return session.createQueue(key);
    }
    
    MessageProducer getPublisher(final Session session, final String str) throws Exception {
        this.log.LogMsgln(3, true, "DestCache.getPublisher(): ", "Getting publisher for " + str);
        final MessageProducer messageProducer = this.publisherCache.get(str);
        if (messageProducer != null) {
            return messageProducer;
        }
        this.log.LogMsgln(3, true, "DestCache.getPublisher(): ", "Creating publisher for " + str);
        final MessageProducer producer = session.createProducer((Destination)this.getTopic(session, str));
        if (producer != null) {
            this.putPublisher(str, producer);
        }
        return producer;
    }
    
    MessageProducer getProducer(final Session session, final String str) throws Exception {
        this.log.LogMsgln(3, true, "DestCache.getProducer(): ", "Getting producer for " + str);
        final MessageProducer messageProducer = this.producerCache.get(str);
        if (messageProducer != null) {
            return messageProducer;
        }
        this.log.LogMsgln(3, true, "DestCache.getProducer(): ", "Creating producer for " + str);
        final MessageProducer producer = session.createProducer((Destination)this.getQueue(session, str));
        if (producer != null) {
            this.putProducer(str, producer);
        }
        return producer;
    }
    
    TemporaryQueue getTempQueue(final Session session, final String str) throws Exception {
        if (str != null) {
            this.log.LogMsgln(3, true, "DestCache.getTempQueue(): ", "Getting temp queue for " + str);
            final TemporaryQueue temporaryQueue = this.tempQueueCache.get(str);
            if (temporaryQueue != null) {
                return temporaryQueue;
            }
        }
        this.log.LogMsgln(3, true, "DestCache.getTempQueue(): ", "Creating temp queue for " + str);
        final TemporaryQueue temporaryQueue2 = session.createTemporaryQueue();
        if (temporaryQueue2 != null) {
            this.putTempQueue(((Queue)temporaryQueue2).getQueueName(), temporaryQueue2);
        }
        return temporaryQueue2;
    }
    
    boolean deleteTempQueue(final String s) throws Exception {
        if (s != null) {
            this.log.LogMsgln(3, true, "DestCache.deleteTempQueue(): ", "Getting temporary queue for " + s);
            final TemporaryQueue temporaryQueue = this.tempQueueCache.get(s);
            if (temporaryQueue != null) {
                temporaryQueue.delete();
                this.tempQueueCache.remove(s);
                return true;
            }
        }
        this.log.LogMsgln(3, true, "DestCache.getTempQueue(): ", "Temporary queue not found - " + s);
        return false;
    }
    
    void closeTempQueues() throws Exception {
        final Enumeration<TemporaryQueue> elements = this.tempQueueCache.elements();
        while (elements.hasMoreElements()) {
            final TemporaryQueue temporaryQueue = elements.nextElement();
            this.log.LogMsgln(3, true, "DestCache.closeTempQueues(): ", "Closing " + temporaryQueue.toString());
            temporaryQueue.delete();
        }
        this.tempQueueCache.clear();
    }
    
    TemporaryTopic getTempTopic(final Session session, final String str) throws Exception {
        if (str != null) {
            this.log.LogMsgln(3, true, "DestCache.getTempTopic(): ", "Getting temporary topic for " + str);
            final TemporaryTopic temporaryTopic = this.tempTopicCache.get(str);
            if (temporaryTopic != null) {
                return temporaryTopic;
            }
        }
        this.log.LogMsgln(3, true, "DestCache.getTempTopic(): ", "Creating temporary topic for " + str);
        final TemporaryTopic temporaryTopic2 = session.createTemporaryTopic();
        if (temporaryTopic2 != null) {
            this.putTempTopic(((Topic)temporaryTopic2).getTopicName(), temporaryTopic2);
        }
        return temporaryTopic2;
    }
    
    boolean deleteTempTopic(final String s) throws Exception {
        if (s != null) {
            this.log.LogMsgln(3, true, "DestCache.deleteTempTopic(): ", "Getting temporary topic for " + s);
            final TemporaryTopic temporaryTopic = this.tempTopicCache.get(s);
            if (temporaryTopic != null) {
                temporaryTopic.delete();
                this.tempTopicCache.remove(s);
                return true;
            }
        }
        this.log.LogMsgln(3, true, "DestCache.getTempTopic(): ", "Temporary topic not found - " + s);
        return false;
    }
    
    void closeTempTopics() throws Exception {
        final Enumeration<TemporaryTopic> elements = this.tempTopicCache.elements();
        while (elements.hasMoreElements()) {
            final TemporaryTopic temporaryTopic = elements.nextElement();
            this.log.LogMsgln(3, true, "DestCache.closeTempTopics(): ", "Closing " + temporaryTopic.toString());
            temporaryTopic.delete();
        }
        this.tempTopicCache.clear();
    }
    
    void closePublishers() throws Exception {
        final Enumeration<MessageProducer> elements = this.publisherCache.elements();
        while (elements.hasMoreElements()) {
            final MessageProducer messageProducer = elements.nextElement();
            this.log.LogMsgln(3, true, "DestCache.closePublishers(): ", "Closing " + messageProducer.toString());
            messageProducer.close();
        }
        this.publisherCache.clear();
    }
    
    void closeProducers() throws Exception {
        final Enumeration<MessageProducer> elements = this.producerCache.elements();
        while (elements.hasMoreElements()) {
            final MessageProducer messageProducer = elements.nextElement();
            this.log.LogMsgln(3, true, "DestCache.closeProducers(): ", "Closing " + messageProducer.toString());
            messageProducer.close();
        }
        this.producerCache.clear();
    }
    
    Destination getAnyDestination(final String s) {
        final Queue queue = this.queueCache.get(s);
        if (queue != null) {
            return (Destination)queue;
        }
        final Topic topic = this.topicCache.get(s);
        if (topic != null) {
            return (Destination)topic;
        }
        final Queue queue2 = jms.getQueue(s);
        if (queue2 != null) {
            return (Destination)queue2;
        }
        return (Destination)jms.getTopic(s);
    }
    
    void removeDestinations(final String s) {
        final DestinationList list = new DestinationList(s);
        while (list.hasMoreDestinations()) {
            final String nextDest = list.getNextDest();
            if (nextDest != null) {
                this.topicCache.remove(nextDest);
                this.queueCache.remove(nextDest);
            }
        }
    }
    
    static class DestinationList
    {
        private static final char DEST_DELIMITER = '\u0002';
        private static final char[] DEST_DELIMITER_ARRAY;
        private static final String DEST_DELIMITER_STR;
        private StringTokenizer destinations;
        
        DestinationList(final String str) {
            if (str == null || str.length() == 0) {
                this.destinations = null;
            }
            else {
                this.destinations = new StringTokenizer(str, DestinationList.DEST_DELIMITER_STR, false);
            }
        }
        
        boolean hasMoreDestinations() {
            return this.destinations != null && this.destinations.hasMoreTokens();
        }
        
        String getNextDest() {
            return this.destinations.nextToken();
        }
        
        static {
            DEST_DELIMITER_ARRAY = new char[] { '\u0002' };
            DEST_DELIMITER_STR = new String(DestinationList.DEST_DELIMITER_ARRAY);
        }
    }
}
