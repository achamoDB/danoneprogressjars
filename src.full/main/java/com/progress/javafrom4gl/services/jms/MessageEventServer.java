// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import com.actional.lg.interceptor.sdk.FlowStub;
import com.actional.lg.interceptor.sdk.SiteStub;
import com.actional.lg.interceptor.sdk.InteractionStub;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.Queue;
import progress.message.jclient.Part;
import javax.jms.Destination;
import java.sql.ResultSet;
import progress.message.jclient.MultipartMessage;
import java.math.BigDecimal;
import progress.message.jclient.DeliveryMode;
import com.actional.lg.interceptor.sdk.Interaction;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import com.progress.open4gl.ResultSetHolder;
import com.progress.open4gl.BooleanHolder;
import com.progress.open4gl.ByteArrayHolder;
import com.progress.open4gl.BigDecimalHolder;
import com.progress.open4gl.StringHolder;
import com.progress.open4gl.IntHolder;
import javax.jms.Message;
import java.net.InetAddress;
import com.progress.javafrom4gl.ServiceRuntime;
import com.actional.lg.interceptor.sdk.ClientInteraction;
import com.actional.lg.interceptor.sdk.ServerInteraction;
import com.progress.ubroker.util.ubProperties;
import javax.jms.Session;
import com.progress.javafrom4gl.Log;

public abstract class MessageEventServer
{
    static final String NEW_LINE;
    private static final short ACT_DT_OEGROUP = 681;
    private static final short ACT_DT_MQADAPTER = 683;
    private MessageQueue messageQueue;
    private DestCache replyCache;
    private MessageContainer lastMessageSent;
    private volatile int callID;
    private int recoverID;
    private boolean transacted;
    private boolean sonicMQServer;
    private String connectionID;
    private MessageQueue browseQueue;
    private Log log;
    private Session queueSession;
    private ubProperties brokerProperties;
    private boolean symbiontMode;
    private String localHostName;
    private String brokerHostName;
    private String clientHostName;
    private String clientID;
    private ServerInteraction actionalSI;
    private ClientInteraction actionalCI;
    
    MessageEventServer(final MessageQueue messageQueue, final DestCache replyCache) {
        this.brokerProperties = ServiceRuntime.getBrokerProperties();
        this.log = ServiceRuntime.getLog();
        this.browseQueue = null;
        this.messageQueue = messageQueue;
        this.replyCache = replyCache;
        this.lastMessageSent = null;
        this.callID = 0;
        this.recoverID = 0;
        this.sonicMQServer = false;
        this.symbiontMode = false;
        this.queueSession = null;
        this.actionalSI = null;
        this.actionalCI = null;
        try {
            this.localHostName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception ex) {
            this.localHostName = null;
        }
    }
    
    void setQueueSession(final Session queueSession) {
        this.queueSession = queueSession;
    }
    
    void setBrowseQueue(final MessageQueue browseQueue) {
        this.browseQueue = browseQueue;
    }
    
    void setConnID(final String connectionID) {
        this.connectionID = connectionID;
    }
    
    void setBrokerHostName(final String brokerHostName) {
        this.brokerHostName = brokerHostName;
    }
    
    void setClientHostName(final String clientHostName) {
        this.clientHostName = clientHostName;
    }
    
    void setSymbiontMode() {
        this.symbiontMode = true;
    }
    
    void setSonicMQ() {
        this.sonicMQServer = true;
    }
    
    void setTransacted(final boolean transacted) {
        this.transacted = transacted;
    }
    
    void setRecoverID(final int recoverID) {
        this.recoverID = recoverID;
    }
    
    public String toString() {
        final MessageContainer lastMessageSent = this.lastMessageSent;
        String string = "";
        if (lastMessageSent != null) {
            string = string + MessageEventServer.NEW_LINE + " Last message sent: " + lastMessageSent.toString() + MessageEventServer.NEW_LINE;
        }
        return string + this.messageQueue.toString();
    }
    
    void waitForNextMsgCall(final int i) {
        int callID = -1;
        if (i == -1) {
            this.lastMessageSent = null;
            return;
        }
        while (true) {
            if (this.callID >= i) {
                if (i >= 1000 || this.callID <= 1999999000) {
                    break;
                }
            }
            try {
                if (callID != this.callID) {
                    callID = this.callID;
                    this.log.LogMsgln(3, true, "", "waitForNextMsgCall(): id = " + i + "callID = " + this.callID);
                }
                Thread.sleep(50L);
            }
            catch (Exception ex) {}
        }
    }
    
    void acknowledgeLastMsg() throws Exception {
        String jmsMessageID = null;
        try {
            final MessageContainer lastMessageSent = this.lastMessageSent;
            if (lastMessageSent != null) {
                final Message message = lastMessageSent.getMessage();
                try {
                    jmsMessageID = message.getJMSMessageID();
                }
                catch (Exception ex) {}
                message.acknowledge();
                this.log.LogMsgln(3, true, this.connectionID, "acknowledgeLastMsg(): Acknowledged message: " + jmsMessageID);
            }
            this.lastMessageSent = null;
        }
        catch (Throwable t) {
            this.log.LogMsgln(3, true, this.connectionID, "acknowledgeLastMsg(): CANNOT acknowledge message: " + jmsMessageID);
            this.log.LogStackTrace(1, true, this.connectionID, t);
            if (JMSException.class.isAssignableFrom(t.getClass())) {
                final MessageContainer peek = this.messageQueue.peek();
                if (peek != null && peek.getMessageType() == 3) {
                    return;
                }
            }
            if (Exception.class.isAssignableFrom(t.getClass())) {
                throw (Exception)t;
            }
            throw new Exception(t.getMessage());
        }
    }
    
    public void getNextMessage(final String s, final int callID, final IntHolder intHolder, final boolean b, final StringHolder stringHolder, final BigDecimalHolder bigDecimalHolder, final ByteArrayHolder byteArrayHolder, final StringHolder stringHolder2, final StringHolder stringHolder3, final StringHolder stringHolder4, final StringHolder stringHolder5, final StringHolder stringHolder6, final BooleanHolder booleanHolder, final StringHolder stringHolder7, final BigDecimalHolder bigDecimalHolder2, final IntHolder intHolder2, final IntHolder intHolder3, final IntHolder intHolder4, final IntHolder intHolder5, final ResultSetHolder resultSetHolder, final ResultSetHolder resultSetHolder2, final IntHolder intHolder6, final IntHolder intHolder7) throws Exception {
        boolean valueAsBoolean = this.brokerProperties.getValueAsBoolean("actionalEnabled");
        final String valueAsString = this.brokerProperties.getValueAsString("actionalGroup");
        final String brokerName = this.brokerProperties.brokerName;
        this.log.LogMsgln(3, true, this.connectionID, "getNextMessage(): Client requests next message.");
        try {
            if (this.replyCache != null) {
                this.replyCache.removeDestinations(s);
            }
            if (!this.transacted && b) {
                this.acknowledgeLastMsg();
            }
            if (null != this.actionalCI) {
                this.log.LogMsgln(3, true, this.connectionID, "getNextMessage(): Ending ClientInteraction.");
                ((Interaction)this.actionalCI).end();
                this.actionalCI = null;
            }
            if (null != this.actionalSI) {
                this.log.LogMsgln(3, true, this.connectionID, "getNextMessage(): Ending ServerInteraction.");
                ((Interaction)this.actionalSI).end();
                this.actionalSI = null;
            }
            this.callID = callID;
            this.log.LogMsgln(36, true, this.connectionID, "getNextMessage(): Queue status before resume:   empty: " + this.messageQueue.isEmpty() + ", should block: " + this.messageQueue.isBlock() + ", locked: " + this.messageQueue.isLocked());
            this.messageQueue.resume();
            this.log.LogMsgln(36, true, this.connectionID, "getNextMessage(): Queue status after resume:   empty: " + this.messageQueue.isEmpty() + ", should block: " + this.messageQueue.isBlock() + ", locked: " + this.messageQueue.isLocked());
            MessageContainer lastMessageSent = new WakeupMessage();
            while (lastMessageSent instanceof WakeupMessage) {
                if (this.browseQueue != null && !this.browseQueue.isEmpty()) {
                    lastMessageSent = (MessageContainer)this.browseQueue.dequeue();
                }
                else {
                    lastMessageSent = (MessageContainer)this.messageQueue.dequeue();
                }
            }
            intHolder.setIntValue(this.recoverID);
            final Message message = lastMessageSent.getMessage();
            final boolean dataMessage = lastMessageSent.isDataMessage();
            if (this.symbiontMode || !dataMessage) {
                valueAsBoolean = false;
            }
            if (valueAsBoolean) {
                this.log.LogMsgln(36, true, this.connectionID, "getNextMessage(): Setting up Actional Interaction");
                final String stringProperty = message.getStringProperty("LG_Header");
                this.log.LogMsgln(36, true, this.connectionID, "getNextMessage(): Got incoming header - " + stringProperty);
                this.actionalSI = ServerInteraction.begin();
                if (null != stringProperty) {
                    InterHelpBase.readHeader(stringProperty, this.actionalSI);
                }
                ((InteractionStub)this.actionalSI).setGroupName(valueAsString);
                ((InteractionStub)this.actionalSI).setServiceName(brokerName);
                ((InteractionStub)this.actionalSI).setUrl("OpenEdge://" + this.localHostName + "/MQAdapter/" + brokerName);
                ((InteractionStub)this.actionalSI).setPeerAddr(this.brokerHostName);
                ((SiteStub)this.actionalSI).setAppType((short)681);
                ((SiteStub)this.actionalSI).setSvcType((short)683);
                ((InteractionStub)this.actionalSI).setOneWay(true);
                ((Interaction)this.actionalSI).requestAnalyzed();
                this.logInteraction((Interaction)this.actionalSI);
                ((InteractionStub)(this.actionalCI = ClientInteraction.begin())).setGroupName(valueAsString);
                ((InteractionStub)this.actionalCI).setServiceName(brokerName);
                ((InteractionStub)this.actionalCI).setUrl("OpenEdge://" + this.localHostName + "/JMSClient/" + this.clientID);
                ((InteractionStub)this.actionalCI).setPeerAddr(this.clientHostName);
                ((SiteStub)this.actionalCI).setAppType((short)681);
                ((InteractionStub)this.actionalCI).setOneWay(true);
                this.actionalCI.requestAnalyzed();
                this.logInteraction((Interaction)this.actionalCI);
            }
            final Destination jmsReplyTo = message.getJMSReplyTo();
            final String destinationToName = this.destinationToName(this.replyCache, jmsReplyTo);
            Destination jmsDestination = null;
            try {
                jmsDestination = message.getJMSDestination();
            }
            catch (Exception ex) {}
            final String destinationToName2 = this.destinationToName(null, jmsDestination);
            final int jmsDeliveryMode = message.getJMSDeliveryMode();
            String stringValue;
            if (jmsDeliveryMode == 2) {
                stringValue = "PERSISTENT";
            }
            else if (jmsDeliveryMode == 1) {
                stringValue = "NON_PERSISTENT";
            }
            else if (this.sonicMQServer && jmsDeliveryMode == DeliveryMode.NON_PERSISTENT_ASYNC) {
                stringValue = "NON_PERSISTENT_ASYNC";
            }
            else if (this.sonicMQServer && jmsDeliveryMode == DeliveryMode.DISCARDABLE) {
                stringValue = "DISCARDABLE";
            }
            else {
                stringValue = "?";
            }
            final String jmsMessageID = message.getJMSMessageID();
            stringHolder.setStringValue(jmsMessageID);
            bigDecimalHolder.setBigDecimalValue(new BigDecimal(message.getJMSTimestamp()));
            if (message.getJMSCorrelationID() != null) {
                byteArrayHolder.setByteArrayValue(message.getJMSCorrelationIDAsBytes());
            }
            stringHolder2.setStringValue(message.getJMSCorrelationID());
            stringHolder3.setStringValue(destinationToName);
            if (jmsReplyTo != null) {
                stringHolder4.setStringValue(Queue.class.isAssignableFrom(jmsReplyTo.getClass()) ? "queue" : "topic");
            }
            else {
                stringHolder4.setStringValue(null);
            }
            stringHolder5.setStringValue(destinationToName2);
            stringHolder6.setStringValue(stringValue);
            booleanHolder.setBooleanValue(message.getJMSRedelivered());
            stringHolder7.setStringValue(message.getJMSType());
            bigDecimalHolder2.setBigDecimalValue(new BigDecimal(message.getJMSExpiration()));
            intHolder2.setIntValue(message.getJMSPriority());
            final int messageDataType;
            if ((messageDataType = lastMessageSent.getMessageDataType()) == 8) {
                int i = 0;
                int n = 0;
                this.log.LogMsgln(36, true, this.connectionID, "Multipart message received");
                intHolder6.setIntValue(lastMessageSent.getMessageType());
                intHolder7.setIntValue(lastMessageSent.getListenerID());
                final OutMessageHeaderSet resultSetValue = (OutMessageHeaderSet)MessageToResultSet.createMPPropertySet(message);
                final OutMessageBodySet resultSetValue2 = new OutMessageBodySet();
                final MultipartMessage multipartMessage = (MultipartMessage)message;
                final int partCount = multipartMessage.getPartCount();
                this.log.LogMsgln(36, true, this.connectionID, "Message contains " + Integer.toString(partCount) + " parts");
                for (int j = 0; j < partCount; ++j) {
                    final Part part = multipartMessage.getPart(j);
                    ++n;
                    if (multipartMessage.isMessagePart(j)) {
                        this.log.LogMsgln(36, true, this.connectionID, "JMS Message part received: Part " + n);
                        resultSetValue.addRow(new Integer(0), new Integer(n), "_messagePart", new Integer(1), "true");
                        ++i;
                        resultSetValue.addRow(new Integer(0), new Integer(n), "_messageId", new Integer(5), Integer.toString(i));
                        final Message messageFromPart = multipartMessage.getMessageFromPart(j);
                        resultSetValue.addRow(new Integer(0), new Integer(n), "_JMSMessageType", new Integer(5), Integer.toString(MessageContainer.interfaceTypeToInt(messageFromPart)));
                        MessageToResultSet.addPartHeaders(0, n, part, resultSetValue);
                        MessageToResultSet.addMessageHeaders(i, n, messageFromPart, resultSetValue);
                        MessageToResultSet.addMessageProperties(i, messageFromPart, resultSetValue);
                        MessageToResultSet.appendBodySet(i, messageFromPart, MessageContainer.interfaceTypeToInt(messageFromPart), resultSetValue2, intHolder4, intHolder5);
                    }
                    else {
                        this.log.LogMsgln(36, true, this.connectionID, "Non-JMS Message part received: Part " + n);
                        resultSetValue.addRow(new Integer(0), new Integer(n), "_messagePart", new Integer(1), "false");
                        MessageToResultSet.addPartHeaders(0, n, part, resultSetValue);
                        MessageToResultSet.addPartBody(0, n, part, resultSetValue2, intHolder4, intHolder5);
                    }
                }
                intHolder3.setIntValue(messageDataType);
                resultSetHolder.setResultSetValue(resultSetValue);
                resultSetHolder2.setResultSetValue(resultSetValue2);
            }
            else {
                intHolder6.setIntValue(lastMessageSent.getMessageType());
                if (lastMessageSent.getMessageDataType() == 5) {
                    intHolder7.setIntValue(2000000000);
                }
                else {
                    intHolder7.setIntValue(lastMessageSent.getListenerID());
                }
                intHolder3.setIntValue(lastMessageSent.getMessageDataType());
                resultSetHolder.setResultSetValue(MessageToResultSet.createPropertySet(message));
                resultSetHolder2.setResultSetValue(MessageToResultSet.createBodySet(message, lastMessageSent.getMessageDataType(), intHolder4, intHolder5));
            }
            if (lastMessageSent.getMessageType() == 1) {
                this.lastMessageSent = lastMessageSent;
            }
            String string = "";
            if (lastMessageSent.isDataMessage()) {
                string = " Message ID is: " + jmsMessageID;
            }
            this.log.LogMsgln(3, true, this.connectionID, "getNextMessage(): Sending " + lastMessageSent.getMessageTypeName() + " message " + " to the client." + string);
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, true, this.connectionID, t);
            if (Exception.class.isAssignableFrom(t.getClass())) {
                throw (Exception)t;
            }
            throw new Exception(t.getMessage());
        }
    }
    
    public void setClientID(final String clientID) {
        this.clientID = clientID;
    }
    
    private String destinationToName(final DestCache destCache, final Destination destination) throws Exception {
        if (destination == null) {
            return null;
        }
        String s;
        if (Queue.class.isAssignableFrom(destination.getClass())) {
            s = ((Queue)destination).getQueueName();
            if (destCache != null) {
                destCache.putQueue(s, (Queue)destination);
            }
        }
        else {
            s = ((Topic)destination).getTopicName();
            if (destCache != null) {
                destCache.putTopic(s, (Topic)destination);
            }
        }
        return s;
    }
    
    public String acknowledgeAndForward(final String str, final IntHolder intHolder, final BigDecimalHolder bigDecimalHolder, final BooleanHolder booleanHolder, final BooleanHolder booleanHolder2) throws Exception {
        boolean b = true;
        Integer obj = null;
        Long obj2 = null;
        boolean booleanValue = false;
        String string = null;
        this.log.LogMsgln(3, true, this.connectionID, "acknowledgeAndForward(): Sending a message to: " + str);
        try {
            final Queue queue = this.replyCache.getQueue(this.queueSession, str);
            final Message message = this.lastMessageSent.getMessage();
            if (!intHolder.isNull()) {
                obj = (Integer)intHolder.getValue();
                b = false;
                this.log.LogMsgln(3, true, this.connectionID, "acknowledgeAndForward(): Changing priority to: " + obj);
            }
            else if (obj == null) {
                obj = new Integer(message.getJMSPriority());
            }
            if (!bigDecimalHolder.isNull()) {
                obj2 = new Long(((BigDecimal)bigDecimalHolder.getValue()).longValue());
                b = false;
                this.log.LogMsgln(3, true, this.connectionID, "acknowledgeAndForward(): Changing timeToLive to: " + obj2);
            }
            else if (obj2 == null) {
                obj2 = new Long(0L);
            }
            if (!booleanHolder.isNull()) {
                booleanValue = (boolean)booleanHolder.getValue();
                b = false;
            }
            int n;
            if (!booleanHolder2.isNull()) {
                final boolean booleanValue2 = (boolean)booleanHolder2.getValue();
                b = false;
                if (booleanValue) {
                    n = 2;
                }
                else if (booleanValue2) {
                    n = 1;
                }
                else {
                    n = message.getJMSDeliveryMode();
                }
            }
            else if (!booleanValue) {
                n = DeliveryMode.DISCARDABLE;
                b = false;
            }
            else {
                n = message.getJMSDeliveryMode();
            }
            if (b) {
                ((progress.message.jclient.Message)message).acknowledgeAndForward((Destination)queue);
            }
            else {
                ((progress.message.jclient.Message)message).acknowledgeAndForward((Destination)queue, n, (int)obj, (long)obj2);
            }
        }
        catch (Throwable t) {
            this.log.LogStackTrace(1, true, this.connectionID, t);
            if (!Exception.class.isAssignableFrom(t.getClass())) {
                throw new Exception(t.getMessage());
            }
            string = t.toString();
        }
        return string;
    }
    
    public void logInteraction(final Interaction interaction) {
        final String valueAsString = this.brokerProperties.getValueAsString("srvrLogEntryTypes");
        int n = 2;
        if (interaction == null || valueAsString.indexOf("Ubroker.Actional") < 0) {
            return;
        }
        if (!this.log.ignore(3)) {
            n = 3;
        }
        String str;
        if (interaction instanceof ClientInteraction) {
            str = "CI";
        }
        else {
            str = "SI";
        }
        final StringBuffer sb = new StringBuffer();
        sb.append("OpenEdge SonicMQ Adapter Interceptor ").append(str);
        if (n == 3) {
            sb.append("(").append(((InteractionStub)interaction).getInteractionID()).append(")");
        }
        sb.append(" on thread: ").append(Thread.currentThread().getName());
        sb.append(" ==>");
        sb.append(" PeerAddr: [ " + ((SiteStub)interaction).getPeerAddr());
        sb.append(" ] URL: [ " + ((SiteStub)interaction).getUrl());
        sb.append(" ] Type: [ " + ((SiteStub)interaction).getSvcType());
        if (n == 3) {
            sb.append(" ] Flow ID: [ " + ((FlowStub)interaction).getFlowID());
            sb.append(" ] Op ID: [ " + ((InteractionStub)interaction).getOpID());
            sb.append(" ] Locus ID: [ " + ((InteractionStub)interaction).getParentID());
            sb.append(" ] Chain ID: [ " + ((FlowStub)interaction).getChainID());
        }
        sb.append(" ] OneWay: [ " + ((InteractionStub)interaction).getOneWay());
        sb.append(" ] isFault: [ " + (((InteractionStub)interaction).getFailure() != null));
        sb.append(" ] G: " + ((SiteStub)interaction).getGroupName());
        sb.append(" S: " + ((SiteStub)interaction).getServiceName());
        sb.append(" O: " + ((SiteStub)interaction).getOpName());
        this.log.LogMsgln(2, true, this.connectionID, sb.toString());
    }
    
    static {
        NEW_LINE = new String(System.getProperty("line.separator"));
    }
}
