// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import com.actional.lg.interceptor.sdk.SiteStub;
import com.actional.lg.interceptor.sdk.InteractionStub;
import progress.message.jclient.MultipartMessage;
import java.util.Vector;
import javax.jms.TemporaryTopic;
import javax.jms.TemporaryQueue;
import java.util.Enumeration;
import javax.jms.QueueBrowser;
import javax.jms.Topic;
import javax.jms.MessageProducer;
import com.actional.lg.interceptor.sdk.ClientInteraction;
import com.actional.lg.interceptor.sdk.Interaction;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import com.actional.lg.interceptor.sdk.ServerInteraction;
import javax.jms.Queue;
import javax.jms.Destination;
import progress.message.jclient.DeliveryMode;
import java.math.BigDecimal;
import java.sql.ResultSet;
import com.progress.open4gl.StringHolder;
import com.progress.open4gl.BooleanHolder;
import com.progress.open4gl.BigDecimalHolder;
import com.progress.open4gl.IntHolder;
import javax.jms.ConnectionMetaData;
import progress.message.jclient.RejectionListener;
import progress.message.jclient.ConnectionStateChangeListener;
import javax.jms.ExceptionListener;
import javax.jms.TextMessage;
import java.io.File;
import javax.jms.JMSException;
import com.progress.open4gl.dynamicapi.Util;
import javax.jms.ConnectionFactory;
import java.net.InetAddress;
import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.ubroker.util.ubProperties;
import javax.jms.Message;
import com.progress.javafrom4gl.Log;
import javax.jms.Session;
import javax.jms.Connection;

public class SessionContainer
{
    static final String NEW_LINE;
    private static final short ACT_DT_OEGROUP = 681;
    private static final short ACT_DT_MQADAPTER = 683;
    public static final String ADAPTER_VER = "10.2B";
    protected MessageEventServer eventServer;
    protected Connection connection;
    protected Session sendSession;
    protected Session receiveSession;
    protected MessageQueue queue;
    protected ListenerCollection listeners;
    protected StartupParameters startParams;
    protected JMSMessageCreator jmsMessageCreator;
    protected String connectionId;
    protected DestCache replyCache;
    protected boolean sonicMQServer;
    protected String metaDataString;
    protected String factoryMetaData;
    protected Log log;
    protected Message ShutdownMessage;
    protected int prefetchCount;
    protected int prefetchThreshold;
    protected String domain;
    protected MessageQueue browseQueue;
    protected ubProperties brokerProperties;
    protected String localHostName;
    protected String brokerHostName;
    protected String clientHostName;
    protected int brokerPort;
    
    SessionContainer() throws Exception {
        this.prefetchCount = -1;
        this.prefetchThreshold = -1;
        this.domain = null;
        this.brokerProperties = ServiceRuntime.getBrokerProperties();
        this.log = ServiceRuntime.getLog();
        this.replyCache = new DestCache(30);
        this.queue = new MessageQueue();
        this.jmsMessageCreator = new JMSMessageCreator();
        this.eventServer = this.createEventServer(this.queue, this.replyCache);
        this.browseQueue = new MessageQueue(500);
        this.eventServer.setBrowseQueue(this.browseQueue);
        try {
            this.localHostName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception ex) {
            this.localHostName = null;
        }
        this.log.LogMsgln(2, true, "", "In SessionContainer()");
    }
    
    String getConnectionID() {
        return this.connectionId;
    }
    
    public String toString() {
        return SessionContainer.NEW_LINE + SessionContainer.NEW_LINE + "JMS client " + this.connectionId + SessionContainer.NEW_LINE + this.startParams.toString() + this.eventServer.toString() + this.listeners.toString();
    }
    
    void init(final ConnectionFactory connectionFactory, final String s, final StartupParameters startParams, final boolean sonicMQServer) throws Exception {
        this.sonicMQServer = sonicMQServer;
        this.startParams = startParams;
        this.connectionId = s;
        this.queue.setConnID(s);
        this.eventServer.setConnID(s);
        this.eventServer.setTransacted(startParams.transactedReceive);
        if (sonicMQServer) {
            this.eventServer.setSonicMQ();
        }
        this.clientHostName = startParams.clientHostName;
        this.eventServer.setClientHostName(this.clientHostName);
        this.brokerHostName = ((progress.message.jclient.ConnectionFactory)connectionFactory).getBrokerHostName();
        this.eventServer.setBrokerHostName(this.brokerHostName);
        this.brokerPort = ((progress.message.jclient.ConnectionFactory)connectionFactory).getBrokerPort();
        this.eventServer.setClientID(startParams.connectID);
        this.prefetchCount = startParams.prefetchCount;
        this.prefetchThreshold = startParams.prefetchThreshold;
        try {
            ((progress.message.jclient.ConnectionFactory)connectionFactory).setLoadBalancing(startParams.loadBalancing);
            if (startParams.loadBalancing) {
                this.log.LogMsgln(2, true, s, Util.getMessageText(7017734119350085267L));
            }
        }
        catch (NoSuchMethodError noSuchMethodError) {
            this.log.LogMsgln(2, true, s, Util.getMessageText(7017734119350085271L));
        }
        try {
            if (startParams.connectionURLs != null) {
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setConnectionURLs(startParams.connectionURLs);
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setSequential(startParams.foSequential);
                this.log.LogMsgln(2, true, s, Util.getMessageText(7017734119350085268L, startParams.connectionURLs));
            }
        }
        catch (NoSuchMethodError noSuchMethodError2) {
            this.log.LogMsgln(2, true, s, Util.getMessageText(7017734119350085272L));
            throw new JMSException(Util.getMessageText(7017734119350085272L));
        }
        if (startParams.connectID != null) {
            ((progress.message.jclient.ConnectionFactory)connectionFactory).setConnectID(startParams.connectID);
        }
        if (startParams.pubSub || startParams.jmsDomain) {
            switch (startParams.flowToDisk) {
                case 0: {
                    ((progress.message.jclient.ConnectionFactory)connectionFactory).setFlowToDisk(new Integer(0));
                    break;
                }
                case 1: {
                    ((progress.message.jclient.ConnectionFactory)connectionFactory).setFlowToDisk(new Integer(1));
                    break;
                }
                case 2: {
                    ((progress.message.jclient.ConnectionFactory)connectionFactory).setFlowToDisk(new Integer(2));
                    break;
                }
                default: {
                    this.log.LogMsgln(2, true, s, "Invalid value for FlowToDisk - ignored.");
                    break;
                }
            }
            ((progress.message.jclient.ConnectionFactory)connectionFactory).setDurableMessageOrder(new Boolean(startParams.durableMessageOrder));
            ((progress.message.jclient.ConnectionFactory)connectionFactory).setSelectorAtBroker(new Boolean(startParams.serverMessageSelector));
        }
        if (startParams.symbiontAdapt) {
            this.eventServer.setSymbiontMode();
            if (startParams.faultTolerant) {
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setFaultTolerant(new Boolean(true));
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setInitialConnectTimeout(new Integer(startParams.ftInitialTimeout));
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setFaultTolerantReconnectTimeout(new Integer(startParams.ftReconnTimeout));
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setClientTransactionBufferSize(new Long(startParams.ftBufferSize));
            }
            if (startParams.clientPersistence) {
                if (startParams.clientID == null) {
                    startParams.clientID = System.getProperty("user.name") + File.separator + System.currentTimeMillis();
                }
                if (startParams.lsDirectory == null) {
                    startParams.lsDirectory = System.getProperty("user.dir");
                }
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setClientID(startParams.clientID);
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setLocalStoreDirectory(startParams.lsDirectory);
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setEnableLocalStore(true);
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setLocalStoreSize(startParams.lsStoreSize);
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setLocalStoreWaitTime(new Integer(startParams.lsWaitTime));
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setReconnectTimeout(startParams.lsReconnTimeout);
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setReconnectInterval(startParams.lsReconnInterval);
            }
            if (startParams.clientPersistence && startParams.faultTolerant) {
                if (startParams.connectID == null) {
                    startParams.connectID = System.getProperty("user.name") + startParams.clientID;
                }
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setConnectID(startParams.connectID);
            }
        }
        else {
            if (startParams.clientPersistence) {
                this.log.LogMsgln(2, true, s, "Client Persistence is not supported for BrokerConnect.");
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setEnableLocalStore(false);
                startParams.clientPersistence = false;
            }
            if (startParams.faultTolerant) {
                this.log.LogMsgln(2, true, s, "Fault Tolerance is not supported for BrokerConnect.");
                ((progress.message.jclient.ConnectionFactory)connectionFactory).setFaultTolerant(new Boolean(false));
                startParams.faultTolerant = false;
            }
        }
        String str = startParams.user;
        String str2 = startParams.password;
        if (str.length() == 0) {
            str = ((progress.message.jclient.ConnectionFactory)connectionFactory).getDefaultUser();
        }
        if (str.length() == 0) {
            str = null;
        }
        if (str2.length() == 0) {
            str2 = ((progress.message.jclient.ConnectionFactory)connectionFactory).getDefaultPassword();
        }
        if (str2.length() == 0) {
            str2 = null;
        }
        this.factoryMetaData = "26," + startParams.clientID + "," + startParams.persistency + "," + startParams.pingInterval + "," + startParams.foSequential + "," + startParams.loadBalancing + "," + startParams.prefetchCount + "," + startParams.prefetchThreshold + "," + startParams.durableMessageOrder + "," + startParams.flowToDisk + "," + startParams.serverMessageSelector + "," + startParams.clientPersistence + "," + startParams.lsDirectory + "," + startParams.lsStoreSize + "," + startParams.lsReconnTimeout + "," + startParams.lsReconnInterval + "," + startParams.faultTolerant + "," + startParams.ftReconnTimeout + "," + startParams.ftBufferSize + "," + startParams.ftInitialTimeout + "," + startParams.lsWaitTime + "," + startParams.connectID + "," + str + "," + str2 + "," + this.localHostName + "," + this.brokerHostName;
        this.log.LogMsgln(3, true, s, "Using connection values: " + this.factoryMetaData);
        this.connection = this.createConnection(connectionFactory, startParams.user, startParams.password);
        if (sonicMQServer && startParams.pingInterval != null) {
            ((progress.message.jclient.Connection)this.connection).setPingInterval(Long.parseLong(startParams.pingInterval));
        }
        final ConnectionMetaData metaData = this.connection.getMetaData();
        if (metaData != null) {
            this.metaDataString = metaData.getJMSVersion() + "," + metaData.getJMSMajorVersion() + "," + metaData.getJMSMinorVersion() + "," + metaData.getJMSProviderName() + "," + metaData.getProviderVersion() + "," + metaData.getProviderMajorVersion() + "," + metaData.getProviderMinorVersion() + "," + "10.2B";
        }
        try {
            if (startParams.clientID != null && !startParams.clientPersistence && this.connection.getClientID() == null) {
                this.connection.setClientID(startParams.clientID);
            }
            this.sendSession = this.createSession(this.connection, startParams.transactedPublish, 2);
            this.ShutdownMessage = (Message)this.sendSession.createTextMessage();
            ((TextMessage)this.ShutdownMessage).setText("");
            ((Message)this.ShutdownMessage).setJMSCorrelationID("");
            final boolean valueAsBoolean = this.brokerProperties.getValueAsBoolean("actionalEnabled");
            try {
                if (valueAsBoolean || startParams.symbiontAdapt) {
                    ((progress.message.jimpl.Connection)this.connection).setEnableActionalInstrumentation(true, false);
                }
            }
            catch (Throwable t) {
                this.log.LogMsgln(2, true, s, "Cannot enable Sonic Actional Instrumentation when persisting messages to disk");
            }
            if (!startParams.clientPersistence) {
                try {
                    ((progress.message.jclient.Session)this.sendSession).setFlowControlDisabled(true);
                }
                catch (NoSuchMethodError noSuchMethodError3) {
                    this.log.LogMsgln(3, true, s, Util.getMessageText(7017734119350085273L));
                }
            }
            this.receiveSession = this.createSession(this.connection, false, startParams.singleMessageAck ? 1004 : 2);
            (this.listeners = this.createListenersCollection(this.receiveSession, this.queue, this.replyCache)).setConnID(s);
            this.connection.setExceptionListener((ExceptionListener)new ExceptionListenerImpl(this.queue, this.sendSession, this.log, s));
            if (startParams.symbiontAdapt) {
                this.log.LogMsgln(2, true, s, "SonicMQ Adapter running in Symbiont mode.");
                if (startParams.faultTolerant) {
                    ((progress.message.jclient.Connection)this.connection).setConnectionStateChangeListener((ConnectionStateChangeListener)new ChangeStateListenerImpl(this.queue, this.sendSession, this.log, s));
                    if (((progress.message.jclient.Connection)this.connection).isFaultTolerant()) {
                        this.log.LogMsgln(2, true, s, "Fault-Tolerance enabled, connected to Fault-Tolerant broker.");
                    }
                }
                if (startParams.clientPersistence) {
                    ((progress.message.jclient.Connection)this.connection).setRejectionListener((RejectionListener)new RejectionListenerImpl(this.queue, this.sendSession, this.log, s));
                    this.log.LogMsgln(2, true, s, "Client-Persistence enabled, client id: " + this.connection.getClientID());
                    this.log.LogMsgln(3, true, s, "Using Local Store: " + ((progress.message.jclient.ConnectionFactory)connectionFactory).getLocalStoreDirectory());
                }
            }
        }
        catch (Exception ex) {
            this.log.LogStackTrace(3, true, s, ex);
            this.log.LogMsgln(2, true, s, Util.getMessageText(7017734119350084682L));
            this.log.LogMsgln(2, true, s, ex.getMessage());
            this.connection.close();
            throw ex;
        }
        this.log.LogMsgln(2, true, s, Util.getMessageText(7017734119350084683L));
        try {
            this.log.LogMsgln(2, true, s, Util.getMessageText(7017734119350085269L, ((progress.message.jclient.Connection)this.connection).getBrokerURL()));
        }
        catch (Exception ex2) {}
    }
    
    MessageEventServer getMessageEventServer() {
        return this.eventServer;
    }
    
    public String sendToAdptr(final String s, final IntHolder intHolder, final BigDecimalHolder bigDecimalHolder, final BooleanHolder booleanHolder, final BooleanHolder booleanHolder2, final StringHolder stringHolder, final BigDecimalHolder bigDecimalHolder2, final byte[] array, final String s2, final String s3, final String s4, final StringHolder stringHolder2, final StringHolder stringHolder3, final BooleanHolder booleanHolder3, final String s5, final BigDecimalHolder bigDecimalHolder3, final IntHolder intHolder2, final int n, final ResultSet set, final ResultSet set2, final String s6, final int n2, final StringHolder stringHolder4, final BooleanHolder booleanHolder4) throws Throwable {
        Object o = null;
        Integer priority = this.startParams.priority;
        Long timeToLive = this.startParams.timeToLive;
        boolean b = this.startParams.persistency;
        boolean b2 = this.startParams.syncPublish;
        int n3 = 2;
        String string = null;
        boolean b3 = false;
        boolean b4 = false;
        this.log.LogMsgln(3, true, this.connectionId, "send(): Sending a message to: " + s);
        try {
            booleanHolder4.setBooleanValue(false);
            if (n2 != -1 && !this.listeners.listenerExists(n2)) {
                b3 = true;
            }
            if (!intHolder.isNull()) {
                priority = (Integer)intHolder.getValue();
            }
            else if (priority == null) {
                priority = new Integer(4);
            }
            if (!bigDecimalHolder.isNull()) {
                timeToLive = new Long(((BigDecimal)bigDecimalHolder.getValue()).longValue());
            }
            else if (timeToLive == null) {
                timeToLive = new Long(0L);
            }
            if (!booleanHolder.isNull() && booleanHolder2.isNull()) {
                if (!(boolean)booleanHolder.getValue()) {
                    n3 = DeliveryMode.DISCARDABLE;
                }
            }
            else {
                if (!booleanHolder.isNull()) {
                    b = (boolean)booleanHolder.getValue();
                }
                if (!booleanHolder2.isNull()) {
                    b2 = (boolean)booleanHolder2.getValue();
                }
                if (b) {
                    n3 = 2;
                }
                else if (b2) {
                    n3 = 1;
                }
                else {
                    if (!this.sonicMQServer) {
                        throw new Exception(Util.getMessageText(7017734119350084684L));
                    }
                    n3 = DeliveryMode.NON_PERSISTENT_ASYNC;
                }
            }
            if (b3) {
                this.log.LogMsgln(3, true, this.connectionId, "send(): Creating reply listener.");
                Destination destination;
                if (this.domain != null && this.domain.equals("ps")) {
                    destination = this.listeners.createReplyTopicListener(n2, s6);
                }
                else {
                    destination = this.listeners.createReplyListener(n2, s6);
                }
                final String destName = this.getDestName(destination);
                stringHolder4.setStringValue(destName);
                this.log.LogMsgln(3, true, this.connectionId, "send(): Created temporary destination " + destName);
                o = destination;
                b4 = true;
            }
            else if (n2 != -1) {
                o = this.listeners.getDestination(n2);
            }
            else if (s3 != null) {
                if (s4 == null) {
                    o = this.getDestination(this.replyCache, this.receiveSession, s3);
                }
                else {
                    final String dfltDestType = this.getDfltDestType();
                    if (s4.equals(dfltDestType)) {
                        o = this.getDestination(this.replyCache, this.receiveSession, s3);
                    }
                    else if (dfltDestType.equals("jms1.1")) {
                        if (s4.equals("queue")) {
                            o = this.replyCache.getQueue(this.receiveSession, s3);
                        }
                        else {
                            o = this.replyCache.getTopic(this.receiveSession, s3);
                        }
                    }
                    else {
                        o = this.replyCache.getAnyDestination(s3);
                    }
                }
                if (o == null) {
                    throw new JMSException(Util.getMessageText(7017734119350084685L, s3));
                }
            }
            final Message message = this.jmsMessageCreator.createMessage(this.sendSession, this.sonicMQServer, (Destination)o, array, s2, s5, n, set2, set);
            final MessageProducer producer = this.createProducer(this.sendSession, this.replyCache, s);
            boolean valueAsBoolean = this.brokerProperties.getValueAsBoolean("actionalEnabled");
            final String valueAsString = this.brokerProperties.getValueAsString("actionalGroup");
            final String brokerName = this.brokerProperties.brokerName;
            Object begin = null;
            Object begin2 = null;
            final String stringProperty = message.getStringProperty("LG_Header");
            this.log.LogMsgln(36, true, this.connectionId, "send(): Got incoming header - " + stringProperty);
            if (this.startParams.symbiontAdapt) {
                valueAsBoolean = false;
            }
            if (valueAsBoolean) {
                this.log.LogMsgln(36, true, this.connectionId, "send(): Setting up Actional Interaction");
                final String routingNodeName = ((progress.message.jclient.Connection)this.connection).getRoutingNodeName();
                final String s7 = (producer.getDestination() instanceof Queue) ? "$Queue$" : "$Topic$";
                begin = ServerInteraction.begin();
                if (null != stringProperty) {
                    InterHelpBase.readHeader(stringProperty, (ServerInteraction)begin);
                }
                ((InteractionStub)begin).setGroupName(valueAsString);
                ((InteractionStub)begin).setServiceName(brokerName);
                ((InteractionStub)begin).setUrl("OpenEdge://" + this.localHostName + "/MQAdapter/" + brokerName);
                ((InteractionStub)begin).setPeerAddr(this.clientHostName);
                ((SiteStub)begin).setAppType((short)681);
                ((SiteStub)begin).setSvcType((short)683);
                ((InteractionStub)begin).setOneWay(true);
                ((Interaction)begin).requestAnalyzed();
                this.eventServer.logInteraction((Interaction)begin);
                begin2 = ClientInteraction.begin();
                ((InteractionStub)begin2).setGroupName(routingNodeName + "::" + Integer.toString(this.brokerPort));
                if (s.indexOf("TemporaryQueues") >= 0) {
                    ((InteractionStub)begin2).setServiceName(s7 + "TemporaryQueues");
                    ((InteractionStub)begin2).setUrl("sonic://" + this.brokerHostName + "/" + routingNodeName + "/" + Integer.toString(this.brokerPort) + "/" + s7 + "/" + "TemporaryQueues");
                }
                else {
                    ((InteractionStub)begin2).setServiceName(s7 + s);
                    ((InteractionStub)begin2).setUrl("sonic://" + this.brokerHostName + "/" + routingNodeName + "/" + Integer.toString(this.brokerPort) + "/" + s7 + "/" + s.replace('.', '/'));
                }
                ((InteractionStub)begin2).setPeerAddr(this.brokerHostName);
                ((InteractionStub)begin2).setOneWay(true);
                ((ClientInteraction)begin2).requestAnalyzed();
                try {
                    message.setStringProperty("LG_Header", InterHelpBase.writeHeader((ClientInteraction)begin2));
                }
                catch (Exception ex) {
                    this.log.LogMsgln(1, true, this.connectionId, "send(): Unable to reset LG_Header - " + ex.getMessage());
                }
            }
            this.send(producer, message, n3, priority, timeToLive);
            if (valueAsBoolean) {
                this.eventServer.logInteraction((Interaction)begin2);
                ((Interaction)begin2).end();
            }
            final String jmsMessageID = message.getJMSMessageID();
            stringHolder.setStringValue(jmsMessageID);
            this.log.LogMsgln(3, true, this.connectionId, "send(): Message " + jmsMessageID + " sent.");
            bigDecimalHolder2.setBigDecimalValue(new BigDecimal(message.getJMSTimestamp()));
            final Destination jmsDestination = message.getJMSDestination();
            String destName2 = null;
            if (jmsDestination != null) {
                destName2 = this.getDestName(jmsDestination);
            }
            stringHolder2.setStringValue(destName2);
            final int jmsDeliveryMode = message.getJMSDeliveryMode();
            String stringValue;
            if (jmsDeliveryMode == 2) {
                stringValue = "PERSISTENT";
            }
            else if (jmsDeliveryMode == 1) {
                stringValue = "NON_PERSISTENT";
            }
            else if (jmsDeliveryMode == DeliveryMode.NON_PERSISTENT_ASYNC) {
                stringValue = "NON_PERSISTENT_ASYNC";
            }
            else if (jmsDeliveryMode == DeliveryMode.DISCARDABLE) {
                stringValue = "DISCARDABLE";
            }
            else {
                stringValue = "?";
            }
            stringHolder3.setStringValue(stringValue);
            bigDecimalHolder3.setBigDecimalValue(new BigDecimal(message.getJMSExpiration()));
            intHolder2.setIntValue(message.getJMSPriority());
            if (s.indexOf("TemporaryQueues") >= 0 || s.indexOf("DIRECTHttp") >= 0 || (this.domain != null && this.domain.equals("ps") && !this.sendSession.getTransacted())) {
                this.log.LogMsgln(3, true, this.connectionId, "send(): Closing producer " + producer.toString());
                producer.close();
            }
            if (valueAsBoolean) {
                ((Interaction)begin).end();
            }
        }
        catch (Throwable t) {
            if (b3 && !b4) {
                booleanHolder4.setBooleanValue(true);
            }
            if (!JMSException.class.isAssignableFrom(t.getClass()) && !NumberFormatException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
    
    public String sendToAdptr2(final String str, final String str2, final IntHolder intHolder, final BigDecimalHolder bigDecimalHolder, final BooleanHolder booleanHolder, final BooleanHolder booleanHolder2, final StringHolder stringHolder, final BigDecimalHolder bigDecimalHolder2, final byte[] array, final String s, final String s2, final String s3, final StringHolder stringHolder2, final StringHolder stringHolder3, final BooleanHolder booleanHolder3, final String s4, final BigDecimalHolder bigDecimalHolder3, final IntHolder intHolder2, final int n, final ResultSet set, final ResultSet set2, final String s5, final int n2, final StringHolder stringHolder4, final BooleanHolder booleanHolder4) throws Throwable {
        if (str2 == null) {
            this.log.LogMsgln(3, true, this.connectionId, "send(): Attempting to determine domain of destination " + str);
            final Destination anyDestination = this.replyCache.getAnyDestination(str);
            if (anyDestination != null && anyDestination instanceof Topic) {
                this.domain = "ps";
            }
            else {
                this.domain = "ptp";
            }
            if (anyDestination == null) {
                this.log.LogMsgln(3, true, this.connectionId, "send(): Defaulting to ptp domain");
            }
        }
        else {
            this.log.LogMsgln(3, true, this.connectionId, "send(): Sending a message to a " + str2);
            if (str2.equals("topic")) {
                this.domain = "ps";
            }
            else {
                this.domain = "ptp";
            }
        }
        final String sendToAdptr = this.sendToAdptr(str, intHolder, bigDecimalHolder, booleanHolder, booleanHolder2, stringHolder, bigDecimalHolder2, array, s, s2, s3, stringHolder2, stringHolder3, booleanHolder3, s4, bigDecimalHolder3, intHolder2, n, set, set2, s5, n2, stringHolder4, booleanHolder4);
        this.domain = null;
        return sendToAdptr;
    }
    
    public String receive(final String str, final String str2, final String s, final boolean b, final int n, final BooleanHolder booleanHolder) throws Throwable {
        String string = "";
        if (str2 != null) {
            string = "Durable subscription name: " + str2;
        }
        this.log.LogMsgln(3, true, this.connectionId, "receive(): Request to receive from: " + str + ". " + string);
        String string2 = null;
        booleanHolder.setBooleanValue(true);
        try {
            if (this.domain == null) {
                this.listeners.createDestinationListener(n, str, str2, s, this.prefetchCount, this.prefetchThreshold, b);
            }
            else {
                this.listeners.createDestinationListener(n, str, this.domain, str2, s, this.prefetchCount, this.prefetchThreshold, b);
            }
            booleanHolder.setBooleanValue(false);
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string2 = t.toString();
        }
        return string2;
    }
    
    public String receive2(final String s, final String domain, final String s2, final String s3, final boolean b, final int n, final BooleanHolder booleanHolder) throws Throwable {
        this.domain = domain;
        final String receive = this.receive(s, s2, s3, b, n, booleanHolder);
        this.domain = null;
        return receive;
    }
    
    public String browse(final String str, final String s, final int n, final BooleanHolder booleanHolder) throws Throwable {
        this.log.LogMsgln(3, true, this.connectionId, "browse(): Request to browse: " + str);
        String string = null;
        boolean b = true;
        booleanHolder.setBooleanValue(true);
        try {
            this.domain = "ptp";
            final QueueBrowser browser = this.sendSession.createBrowser((Queue)this.getDestination(this.replyCache, this.sendSession, str), s);
            final Enumeration enumeration = browser.getEnumeration();
            this.domain = null;
            while (enumeration.hasMoreElements()) {
                b = false;
                final Message message = enumeration.nextElement();
                if (this.browseQueue.isFull()) {
                    this.browseQueue.setQueueLimit(100 + this.browseQueue.getQueueLimit());
                }
                this.browseQueue.enqueue(new MessageContainer(message, 1, n, this.connectionId));
            }
            browser.close();
            if (!b) {
                this.queue.enqueue(new WakeupMessage());
                this.queue.wakeup();
            }
            booleanHolder.setBooleanValue(false);
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
    
    public void getConnectionMetaData(final StringHolder stringHolder) {
        stringHolder.setStringValue(this.metaDataString);
    }
    
    public void getFactoryMetaData(final StringHolder stringHolder) {
        stringHolder.setStringValue(this.factoryMetaData);
    }
    
    public String cancelDurableSubscription(final String str) throws Throwable {
        String string = null;
        this.log.LogMsgln(3, true, this.connectionId, "cancelDurableSubscription(): Canceling " + str);
        try {
            this.unsubscribe(this.sendSession, str);
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
    
    public String rollbackSend(final int n) throws Throwable {
        String string = null;
        this.log.LogMsgln(3, true, this.connectionId, "rollbackSend(): Rolling back...");
        try {
            if (this.startParams.transactedPublish) {
                this.sendSession.rollback();
            }
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
    
    public String rollbackReceive(final int n, final int n2) throws Throwable {
        String string = null;
        this.log.LogMsgln(3, true, this.connectionId, "rollbackReceive(): Rolling back...");
        try {
            if (this.startParams.transactedReceive) {
                this.recover(n, n2);
            }
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
    
    public String commitSend(final int n) throws Throwable {
        String string = null;
        this.log.LogMsgln(3, true, this.connectionId, "commitSend(): Committing...");
        try {
            if (this.startParams.transactedPublish) {
                this.sendSession.commit();
            }
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
    
    public String commitReceive(final int n) throws Throwable {
        String string = null;
        this.log.LogMsgln(3, true, this.connectionId, "commitReceive(): Committing...");
        try {
            if (this.startParams.transactedReceive) {
                this.eventServer.waitForNextMsgCall(n);
                this.eventServer.acknowledgeLastMsg();
            }
        }
        catch (Throwable t) {
            if (!JMSException.class.isAssignableFrom(t.getClass())) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
            else {
                this.log.LogStackTrace(3, true, this.connectionId, t);
            }
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw t;
            }
            string = t.toString();
        }
        return string;
    }
    
    public void recover(final int recoverID, final int n) throws Exception {
        this.eventServer.waitForNextMsgCall(n);
        this.log.LogMsgln(3, true, this.connectionId, "recover(): Recovering...");
        this.log.LogMsgln(36, true, this.connectionId, "recover(): Queue status:  empty: " + this.queue.isEmpty() + ", should block: " + this.queue.isBlock() + ", locked: " + this.queue.isLocked());
        try {
            this.queue.lock();
            this.connection.stop();
            this.log.LogMsgln(36, true, this.connectionId, "recover(): Messages stopped...");
            this.queue.removeMessages();
            this.eventServer.setRecoverID(recoverID);
            this.queue.unLock();
            this.receiveSession.recover();
            this.connection.start();
            this.log.LogMsgln(36, true, this.connectionId, "recover(): Messages started...");
            this.log.LogMsgln(36, true, this.connectionId, "recover(): Queue status:  empty: " + this.queue.isEmpty() + ", should block: " + this.queue.isBlock() + ", locked: " + this.queue.isLocked());
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, true, this.connectionId, t);
        }
        this.log.LogMsgln(3, true, this.connectionId, "recover(): Recovery completed...");
    }
    
    public void deleteSubscriber(final int i, final int j) throws Exception {
        this.log.LogMsgln(3, true, this.connectionId, "deleteSubscriber(): listenerID = " + i + "callID = " + j);
        this.eventServer.waitForNextMsgCall(j);
        try {
            this.listeners.deleteListener(i);
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, true, this.connectionId, t);
        }
    }
    
    public void stop() {
        this.log.LogMsgln(3, true, this.connectionId, "stop(): Stopping message reception.");
        this.log.LogMsgln(36, true, this.connectionId, "stop(): Queue status:  empty: " + this.queue.isEmpty() + ", should block: " + this.queue.isBlock() + ", locked: " + this.queue.isLocked());
        this.queue.lock();
        try {
            this.connection.stop();
        }
        catch (Exception ex) {
            this.log.LogStackTrace(1, true, this.connectionId, ex);
        }
        this.log.LogMsgln(3, true, this.connectionId, "stop(): Message reception stopped.");
    }
    
    public void start() throws Exception {
        this.log.LogMsgln(3, true, this.connectionId, "start(): Starting message reception.");
        this.queue.unLock();
        try {
            this.connection.start();
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, true, this.connectionId, t);
            if (Exception.class.isAssignableFrom(t.getClass())) {
                throw (Exception)t;
            }
            throw new Exception(t.getMessage());
        }
        this.log.LogMsgln(36, true, this.connectionId, "start(): Queue status:  empty: " + this.queue.isEmpty() + ", should block: " + this.queue.isBlock() + ", locked: " + this.queue.isLocked());
        this.log.LogMsgln(3, true, this.connectionId, "start(): Message reception started.");
    }
    
    private void shutdownMessageDelivery0() throws Exception {
        if (this.ShutdownMessage == null) {
            this.ShutdownMessage = (Message)this.sendSession.createTextMessage();
            ((TextMessage)this.ShutdownMessage).setText("");
            ((Message)this.ShutdownMessage).setJMSCorrelationID("");
        }
        this.queue.enqueuePriority(new MessageContainer(this.ShutdownMessage, 2, 0, this.connectionId));
    }
    
    synchronized void shutdownMessageDelivery() throws Exception {
        if (this.connection == null) {
            return;
        }
        this.shutdownMessageDelivery0();
    }
    
    public synchronized void closeSession(final int n) throws Exception {
        if (this.connection != null && !this.queue.isLocked()) {
            this.stop();
        }
        this.queue.unLock();
        this.eventServer.waitForNextMsgCall(n);
        if (this.connection != null && n == -1) {
            this.log.LogMsgln(1, true, this.connectionId, Util.getMessageText(7017734119350084686L));
        }
        if (this.connection == null) {
            return;
        }
        try {
            this.shutdownMessageDelivery0();
            this.listeners.deleteAll();
            this.replyCache.closeProducers();
            this.replyCache.closePublishers();
            this.replyCache.closeTempQueues();
            this.replyCache.closeTempTopics();
            this.sendSession.close();
            this.receiveSession.close();
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, true, this.connectionId, t);
        }
        finally {
            this.log.LogMsgln(2, true, this.connectionId, Util.getMessageText(7017734119350084687L));
            try {
                this.connection.close();
            }
            catch (Throwable t2) {
                this.log.LogStackTrace(1, true, this.connectionId, t2);
            }
            this.connection = null;
        }
    }
    
    public String getBrokerURL() {
        String brokerURL = "";
        try {
            brokerURL = ((progress.message.jclient.Connection)this.connection).getBrokerURL();
        }
        catch (Exception ex) {}
        return brokerURL;
    }
    
    public void isFaultTolerant(final BooleanHolder booleanHolder) {
        booleanHolder.setBooleanValue(((progress.message.jclient.Connection)this.connection).isFaultTolerant());
    }
    
    public void isSecureBroker(final BooleanHolder booleanHolder) {
        booleanHolder.setBooleanValue(((progress.message.jclient.Connection)this.connection).isSecure());
    }
    
    public String createTempQueue() {
        String queueName = "";
        try {
            final TemporaryQueue tempQueue = this.replyCache.getTempQueue(this.sendSession, null);
            if (tempQueue != null) {
                queueName = ((Queue)tempQueue).getQueueName();
            }
        }
        catch (Exception ex) {
            this.log.LogStackTrace(1, true, this.connectionId, ex);
        }
        return queueName;
    }
    
    public void deleteTempQueue(final BooleanHolder booleanHolder, final String s) {
        try {
            booleanHolder.setBooleanValue(this.replyCache.deleteTempQueue(s));
        }
        catch (Exception ex) {
            this.log.LogStackTrace(1, true, this.connectionId, ex);
            booleanHolder.setBooleanValue(false);
        }
    }
    
    public String createTempTopic() {
        String topicName = "";
        try {
            final TemporaryTopic tempTopic = this.replyCache.getTempTopic(this.sendSession, null);
            if (tempTopic != null) {
                topicName = ((Topic)tempTopic).getTopicName();
            }
        }
        catch (Exception ex) {
            this.log.LogStackTrace(1, true, this.connectionId, ex);
        }
        return topicName;
    }
    
    public void deleteTempTopic(final BooleanHolder booleanHolder, final String s) {
        try {
            booleanHolder.setBooleanValue(this.replyCache.deleteTempTopic(s));
        }
        catch (Exception ex) {
            this.log.LogStackTrace(1, true, this.connectionId, ex);
            booleanHolder.setBooleanValue(false);
        }
    }
    
    protected ListenerCollection createListenersCollection(final Session session, final MessageQueue messageQueue, final DestCache destCache) throws Exception {
        return new ListenerCollection(session, messageQueue, destCache);
    }
    
    protected MessageEventServer createEventServer(final MessageQueue messageQueue, final DestCache destCache) throws Exception {
        return new QueueMessageEventServer(messageQueue, destCache);
    }
    
    protected String getDestName(final Destination destination) throws Exception {
        if (this.domain != null && this.domain.equals("ps")) {
            return ((Topic)destination).getTopicName();
        }
        return ((Queue)destination).getQueueName();
    }
    
    protected Destination getDestination(final DestCache destCache, final Session session, final String s) throws Exception {
        if (this.domain != null && this.domain.equals("ps")) {
            return (Destination)destCache.getTopic(session, s);
        }
        return (Destination)destCache.getQueue(session, s);
    }
    
    protected void unsubscribe(final Session session, final String s) throws Exception {
        session.unsubscribe(s);
    }
    
    protected void send(final MessageProducer messageProducer, final Message message, final int n, final int n2, final long n3) throws Exception {
        messageProducer.send(message, n, n2, n3);
    }
    
    protected MessageProducer createProducer(final Session session, final DestCache destCache, final String s) throws Exception {
        if (!this.domain.equals("ps")) {
            return destCache.getProducer(session, s);
        }
        if (!session.getTransacted()) {
            return session.createProducer((Destination)destCache.getTopic(session, s));
        }
        return destCache.getPublisher(session, s);
    }
    
    protected Session createSession(final Connection connection, final boolean b, final int n) throws Exception {
        final Session session = connection.createSession(b, n);
        this.eventServer.setQueueSession(session);
        return session;
    }
    
    protected Connection createConnection(final ConnectionFactory connectionFactory, final String s, final String s2) throws Exception {
        if (s.length() == 0) {
            return connectionFactory.createConnection();
        }
        return connectionFactory.createConnection(s, s2);
    }
    
    protected String getDfltDestType() {
        return "jms1.1";
    }
    
    static {
        NEW_LINE = new String(System.getProperty("line.separator"));
    }
    
    static class ExceptionListenerImpl implements ExceptionListener
    {
        private MessageQueue queue;
        private Session session;
        private TextMessage emergencyMessage;
        private String connectionId;
        private Log log;
        
        ExceptionListenerImpl(final MessageQueue queue, final Session session, final Log log, final String connectionId) throws Exception {
            this.log = log;
            this.connectionId = connectionId;
            this.queue = queue;
            this.session = session;
            this.emergencyMessage = MessageContainer.createEmeregencyMessage(session);
        }
        
        public void onException(final JMSException ex) {
            try {
                this.log.LogStackTrace(1, true, this.connectionId, (Throwable)ex);
                final Vector<String> vector = new Vector<String>();
                Object linkedException = ex;
                while (linkedException != null && JMSException.class.isAssignableFrom(((JMSException)linkedException).getClass())) {
                    final Log log = this.log;
                    final Log log2 = this.log;
                    log.LogMsgln(1, false, this.connectionId, "Error Code " + ((JMSException)linkedException).getErrorCode());
                    linkedException = ((JMSException)linkedException).getLinkedException();
                    if (linkedException != null) {
                        this.log.LogStackTrace(1, false, this.connectionId, (Throwable)linkedException);
                        vector.addElement(((Throwable)linkedException).toString());
                    }
                }
                this.queue.enqueuePriority(MessageContainer.createErrorMessage(this.log, this.connectionId, ex, vector, this.session, this.emergencyMessage));
                this.queue.unLock();
            }
            catch (Throwable t) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
        }
    }
    
    static class RejectionListenerImpl implements RejectionListener
    {
        private MessageQueue queue;
        private Session session;
        private MultipartMessage emergencyMessage;
        private String connectionId;
        private Log log;
        
        RejectionListenerImpl(final MessageQueue queue, final Session session, final Log log, final String connectionId) throws Exception {
            this.log = log;
            this.connectionId = connectionId;
            this.queue = queue;
            this.session = session;
            this.emergencyMessage = MessageContainer.createRejectEmeregencyMessage(session);
        }
        
        public void onRejectedMessage(final Message message, final JMSException ex) {
            try {
                this.log.LogStackTrace(1, true, this.connectionId, (Throwable)ex);
                final Vector<String> vector = new Vector<String>();
                Object linkedException = ex;
                while (linkedException != null && JMSException.class.isAssignableFrom(((JMSException)linkedException).getClass())) {
                    final Log log = this.log;
                    final Log log2 = this.log;
                    log.LogMsgln(1, false, this.connectionId, "Error Code " + ((JMSException)linkedException).getErrorCode());
                    linkedException = ((JMSException)linkedException).getLinkedException();
                    if (linkedException != null) {
                        this.log.LogStackTrace(1, false, this.connectionId, (Throwable)linkedException);
                        vector.addElement(((Throwable)linkedException).toString());
                    }
                }
                this.queue.enqueuePriority(MessageContainer.createRejectMessage(this.log, this.connectionId, message, ex, vector, this.session, this.emergencyMessage));
                this.queue.unLock();
            }
            catch (Throwable t) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
        }
    }
    
    static class ChangeStateListenerImpl implements ConnectionStateChangeListener
    {
        private MessageQueue queue;
        private Session session;
        private TextMessage emergencyMessage;
        private String connectionId;
        private Log log;
        
        ChangeStateListenerImpl(final MessageQueue queue, final Session session, final Log log, final String connectionId) throws Exception {
            this.log = log;
            this.connectionId = connectionId;
            this.queue = queue;
            this.session = session;
            this.emergencyMessage = MessageContainer.createStateEmeregencyMessage(session);
        }
        
        public void connectionStateChanged(final int n) {
            String str = null;
            switch (n) {
                case 0: {
                    str = "active";
                    break;
                }
                case 1: {
                    str = "reconnecting";
                    break;
                }
                case 2: {
                    str = "failed";
                    break;
                }
                case 3: {
                    str = "closed";
                    break;
                }
                default: {
                    str = "unknown";
                    break;
                }
            }
            this.log.LogMsgln(3, true, this.connectionId, "connectionStateChanged(): Broker state changed to " + str);
            try {
                this.queue.enqueuePriority(MessageContainer.createChangeStateMessage(this.log, this.connectionId, str, this.session, this.emergencyMessage));
                this.queue.unLock();
            }
            catch (Throwable t) {
                this.log.LogStackTrace(1, true, this.connectionId, t);
            }
        }
    }
}
