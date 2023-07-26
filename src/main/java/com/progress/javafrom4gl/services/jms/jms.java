// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.Queue;
import javax.jms.Topic;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import javax.jms.JMSException;
import com.progress.javafrom4gl.ServiceConnection;
import com.progress.open4gl.dynamicapi.Util;
import progress.message.zclient.SessionConfig;
import com.progress.javafrom4gl.ServiceRuntime;
import java.lang.reflect.Method;
import com.progress.javafrom4gl.Log;
import java.util.Hashtable;
import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import java.util.Properties;
import com.progress.javafrom4gl.JavaService;

public class jms implements JavaService
{
    private static final String PROGRESS_TOPIC_FACTORY = "progress.message.jclient.TopicConnectionFactory";
    private static final String PROGRESS_QUEUE_FACTORY = "progress.message.jclient.QueueConnectionFactory";
    private static final String PROGRESS_CONNECTION_FACTORY = "progress.message.jclient.ConnectionFactory";
    private static final String SECOND_CONN = "__secondconn:";
    private Properties properties;
    private TopicConnectionFactory dfltTopicFactory;
    private QueueConnectionFactory dfltQueueFactory;
    private ConnectionFactory dfltConnectionFactory;
    private Hashtable partialConnections;
    private boolean dfltSonicMQServer;
    private Log log;
    private static Object objectFinder;
    private static Method topicConnFactoryFinder;
    private static Method topicFinder;
    private static Method queueConnFactoryFinder;
    private static Method queueFinder;
    
    public jms() {
        this.properties = null;
        this.dfltTopicFactory = null;
        this.dfltQueueFactory = null;
        this.dfltConnectionFactory = null;
        this.dfltSonicMQServer = true;
    }
    
    public void _startup(final Properties properties) throws Exception {
        (this.log = ServiceRuntime.getLog()).LogMsgln(3, true, "", "SonicMQ Client Version: " + SessionConfig.RELEASE_NAME);
        this.properties = properties;
        final StartupParameters startupParameters = new StartupParameters(properties);
        this.getObjectFinders();
        if (startupParameters.jmsServerName != null) {
            this.dfltSonicMQServer = this.getSonicMQ(startupParameters.jmsServerName);
        }
        if (startupParameters.brokerURL != null) {
            this.log.LogMsgln(3, true, "", "Sonic broker url: " + startupParameters.brokerURL);
            this.dfltTopicFactory = getTopicConnFactory(startupParameters.brokerURL);
            this.dfltQueueFactory = getQueueConnFactory(startupParameters.brokerURL);
            if (this.dfltTopicFactory == null) {
                this.log.LogMsgln(3, true, "", "Creating Topic Connection Factory");
                this.dfltTopicFactory = this.createTopicFactory(startupParameters.brokerURL, this.dfltSonicMQServer);
            }
            if (this.dfltQueueFactory == null) {
                this.log.LogMsgln(3, true, "", "Creating Queue Connection Factory");
                this.dfltQueueFactory = this.createQueueFactory(startupParameters.brokerURL, this.dfltSonicMQServer);
            }
            if (this.dfltConnectionFactory == null) {
                this.log.LogMsgln(3, true, "", "Creating JMS Connection Factory");
                this.dfltConnectionFactory = (ConnectionFactory)this.createConnectionFactory(startupParameters.brokerURL, this.dfltSonicMQServer);
            }
        }
        this.partialConnections = new Hashtable();
    }
    
    private boolean getSonicMQ(final String s) throws Exception {
        if (s.toUpperCase().equals("SONICMQ")) {
            return true;
        }
        if (s.toUpperCase().equals("TEST")) {
            return false;
        }
        throw new Exception(Util.getMessageText(7017734119350084689L, s));
    }
    
    private progress.message.jclient.ConnectionFactory createConnectionFactory(final String str, final boolean b) throws Exception {
        this.log.LogMsgln(3, true, "", "Creating JMS connection factory: " + str);
        if (b) {
            try {
                return new progress.message.jclient.ConnectionFactory(str, (String)null);
            }
            catch (Throwable t) {
                throw new Exception("SonicMQ's *.jar files are not installed correctly: " + t.toString());
            }
        }
        throw new Exception("SonicMQ is the only supported JMS server.");
    }
    
    private TopicConnectionFactory createTopicFactory(final String str, final boolean b) throws Exception {
        this.log.LogMsgln(3, true, "", "Creating Topic connection factory: " + str);
        if (b) {
            try {
                return (TopicConnectionFactory)new progress.message.jclient.TopicConnectionFactory(str, (String)null);
            }
            catch (Throwable t) {
                throw new Exception("SonicMQ's *.jar files are not installed correctly: " + t.toString());
            }
        }
        throw new Exception("SonicMQ is the only supported JMS server.");
    }
    
    private QueueConnectionFactory createQueueFactory(final String str, final boolean b) throws Exception {
        this.log.LogMsgln(3, true, "", "Creating Queue connection factory: " + str);
        if (b) {
            return (QueueConnectionFactory)new progress.message.jclient.QueueConnectionFactory(str, (String)null);
        }
        throw new Exception("Point-to-Point is not supported with test server.");
    }
    
    public ServiceConnection _connect(final String s, final String s2, final String s3, final String s4, final String s5) throws Exception {
        try {
            final String firstConnection = this.getFirstConnection(s4);
            if (firstConnection != null) {
                final SessionContainer sessionContainer = this.partialConnections.get(firstConnection);
                this.partialConnections.remove(firstConnection);
                return new JmsConnection(sessionContainer);
            }
            final StartupParameters startupParameters = new StartupParameters(this.properties);
            startupParameters.setConnectionParameters(s, s2, s4, s5);
            this.log.LogMsgln(3, true, s3, startupParameters.toString());
            SessionContainer value;
            if (startupParameters.pubSub) {
                value = new TopicSessionContainer();
            }
            else if (startupParameters.jmsDomain) {
                value = new SessionContainer();
            }
            else {
                value = new QueueSessionContainer();
            }
            this.partialConnections.put(s3, value);
            Object o;
            if (startupParameters.pubSub) {
                o = this.dfltTopicFactory;
            }
            else if (startupParameters.jmsDomain) {
                o = this.dfltConnectionFactory;
            }
            else {
                o = this.dfltQueueFactory;
            }
            boolean b;
            if (startupParameters.jmsServerName != null) {
                b = this.getSonicMQ(startupParameters.jmsServerName);
            }
            else {
                b = this.dfltSonicMQServer;
            }
            if (startupParameters.clientSpecifiedJms || startupParameters.clientFactory || o == null) {
                if (startupParameters.pubSub) {
                    o = getTopicConnFactory(startupParameters.brokerURL);
                }
                else {
                    o = getQueueConnFactory(startupParameters.brokerURL);
                }
                if (startupParameters.scConnectionFile != null) {
                    o = this.readFile(startupParameters.scConnectionFile);
                }
                if (o == null) {
                    if (startupParameters.pubSub) {
                        o = this.createTopicFactory(startupParameters.brokerURL, b);
                    }
                    else if (startupParameters.jmsDomain) {
                        o = this.createConnectionFactory(startupParameters.brokerURL, b);
                    }
                    else {
                        o = this.createQueueFactory(startupParameters.brokerURL, b);
                    }
                }
            }
            this.verifyServerSupport((ConnectionFactory)o);
            try {
                return new JmsConnection(value, (ConnectionFactory)o, s3, startupParameters, b);
            }
            catch (JMSException ex) {
                this.partialConnections.remove(s3);
                throw ex;
            }
        }
        catch (Throwable t) {
            this.log.LogStackTrace(3, true, s3, t);
            if (Exception.class.isAssignableFrom(t.getClass())) {
                throw (Exception)t;
            }
            throw new Exception(t.getMessage());
        }
    }
    
    private Object readFile(final String name) throws Exception {
        final FileInputStream in = new FileInputStream(name);
        final Object object = new ObjectInputStream(in).readObject();
        in.close();
        return object;
    }
    
    private void verifyServerSupport(final ConnectionFactory connectionFactory) throws Exception {
        final String name = connectionFactory.getClass().getName();
        if (name.equals("progress.message.jclient.TopicConnectionFactory")) {
            return;
        }
        if (name.equals("progress.message.jclient.QueueConnectionFactory")) {
            return;
        }
        if (name.equals("progress.message.jclient.ConnectionFactory")) {
            return;
        }
        throw new Exception(Util.getMessageText(7017734119350084690L, name));
    }
    
    private String getFirstConnection(final String s) {
        if (s == null || !s.startsWith("__secondconn:")) {
            return null;
        }
        return s.substring("__secondconn:".length());
    }
    
    private void getObjectFinders() {
        Class<?> forName = null;
        try {
            forName = Class.forName("jmsfrom4gl.AdminObjectFinder");
        }
        catch (Throwable t2) {}
        if (forName == null) {
            final String messageText = Util.getMessageText(7017734119350084691L);
            final Log log = this.log;
            final Log log2 = this.log;
            log.LogMsgln(1, false, "", messageText);
            return;
        }
        try {
            jms.objectFinder = forName.newInstance();
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, false, "", t);
        }
        if (jms.objectFinder == null) {
            final Log log3 = this.log;
            final Log log4 = this.log;
            log3.LogMsgln(1, false, "", Util.getMessageText(7017734119350084692L));
            return;
        }
        try {
            final Class[] array = { String.class };
            jms.topicConnFactoryFinder = forName.getMethod("getTopicConnectionFactory", (Class[])array);
            jms.topicFinder = forName.getMethod("getTopic", (Class[])array);
            jms.queueConnFactoryFinder = forName.getMethod("getQueueConnectionFactory", (Class[])array);
            jms.queueFinder = forName.getMethod("getQueue", (Class[])array);
        }
        catch (Throwable t3) {}
        if (jms.topicConnFactoryFinder != null) {
            final Log log5 = this.log;
            final Log log6 = this.log;
            log5.LogMsgln(1, false, "", Util.getMessageText(7017734119350084693L));
        }
        else {
            final Log log7 = this.log;
            final Log log8 = this.log;
            log7.LogMsgln(1, false, "", Util.getMessageText(7017734119350084694L));
        }
        if (jms.queueConnFactoryFinder != null) {
            final Log log9 = this.log;
            final Log log10 = this.log;
            log9.LogMsgln(1, false, "", Util.getMessageText(7017734119350084695L));
        }
        else {
            final Log log11 = this.log;
            final Log log12 = this.log;
            log11.LogMsgln(1, false, "", Util.getMessageText(7017734119350084696L));
        }
        if (jms.topicFinder != null) {
            final Log log13 = this.log;
            final Log log14 = this.log;
            log13.LogMsgln(1, false, "", Util.getMessageText(7017734119350084697L));
        }
        else {
            final Log log15 = this.log;
            final Log log16 = this.log;
            log15.LogMsgln(1, false, "", Util.getMessageText(7017734119350084698L));
        }
        if (jms.queueFinder != null) {
            final Log log17 = this.log;
            final Log log18 = this.log;
            log17.LogMsgln(1, false, "", Util.getMessageText(7017734119350084699L));
        }
        else {
            final Log log19 = this.log;
            final Log log20 = this.log;
            log19.LogMsgln(1, false, "", Util.getMessageText(7017734119350084700L));
        }
    }
    
    static TopicConnectionFactory getTopicConnFactory(final String s) {
        if (jms.topicConnFactoryFinder == null) {
            return null;
        }
        TopicConnectionFactory topicConnectionFactory = null;
        final Object[] args = { s };
        try {
            topicConnectionFactory = (TopicConnectionFactory)jms.topicConnFactoryFinder.invoke(jms.objectFinder, args);
        }
        catch (Throwable t) {
            ServiceRuntime.getLog().LogStackTrace(1, false, "", t);
        }
        return topicConnectionFactory;
    }
    
    static QueueConnectionFactory getQueueConnFactory(final String s) {
        if (jms.queueConnFactoryFinder == null) {
            return null;
        }
        QueueConnectionFactory queueConnectionFactory = null;
        final Object[] args = { s };
        try {
            queueConnectionFactory = (QueueConnectionFactory)jms.queueConnFactoryFinder.invoke(jms.objectFinder, args);
        }
        catch (Throwable t) {
            ServiceRuntime.getLog().LogStackTrace(1, false, "", t);
        }
        return queueConnectionFactory;
    }
    
    static Topic getTopic(final String s) {
        if (jms.topicFinder == null) {
            return null;
        }
        Topic topic = null;
        final Object[] args = { s };
        try {
            topic = (Topic)jms.topicFinder.invoke(jms.objectFinder, args);
        }
        catch (Throwable t) {
            ServiceRuntime.getLog().LogStackTrace(1, false, "", t);
        }
        return topic;
    }
    
    static Queue getQueue(final String s) {
        if (jms.queueFinder == null) {
            return null;
        }
        Queue queue = null;
        final Object[] args = { s };
        try {
            queue = (Queue)jms.queueFinder.invoke(jms.objectFinder, args);
        }
        catch (Throwable t) {
            ServiceRuntime.getLog().LogStackTrace(1, false, "", t);
        }
        return queue;
    }
    
    public void _shutdown() {
    }
    
    static {
        jms.objectFinder = null;
        jms.topicConnFactoryFinder = null;
        jms.topicFinder = null;
        jms.queueConnFactoryFinder = null;
        jms.queueFinder = null;
    }
}
