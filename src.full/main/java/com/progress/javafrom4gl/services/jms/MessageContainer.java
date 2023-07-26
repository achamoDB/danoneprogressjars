// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import progress.message.jclient.MultipartMessage;
import javax.jms.TextMessage;
import javax.jms.Session;
import java.util.Vector;
import javax.jms.JMSException;
import javax.jms.Destination;
import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.javafrom4gl.Log;
import javax.jms.Message;

class MessageContainer
{
    static final String NEW_LINE;
    static final int ERROR_LISTENER_ID = 2000000000;
    static final int STATE_LISTENER_ID = 2010000000;
    static final int REJECT_LISTENER_ID = 2020000000;
    static final int PERSIST_LISTENER_ID = 2030000000;
    static final int DATA_MESSAGE = 1;
    static final int SHUTDOWN_MESSAGE = 2;
    static final int ERROR_MESSAGE = 3;
    static final int WAKEUP_MESSAGE = 4;
    static final int STATE_MESSAGE = 5;
    static final int REJECT_MESSAGE = 6;
    static final int PERSIST_MESSAGE = 7;
    static final int HEADER_MESSAGE_TYPE = 1;
    static final int TEXT_MESSAGE_TYPE = 2;
    static final int BYTES_MESSAGE_TYPE = 3;
    static final int MAP_MESSAGE_TYPE = 4;
    static final int OBJECT_MESSAGE_TYPE = 5;
    static final int STREAM_MESSAGE_TYPE = 6;
    static final int XML_MESSAGE_TYPE = 7;
    static final int MULTI_MESSAGE_TYPE = 8;
    static final int TEMPTABLE_MESSAGE_TYPE = 9;
    static final int DATASET_MESSAGE_TYPE = 10;
    private Message message;
    private int messageType;
    private String messageTypeName;
    private int listenerID;
    private int messageDataType;
    private String connectionId;
    private Log log;
    private static Class textClass;
    private static Class bytesClass;
    private static Class mapClass;
    private static Class objectClass;
    private static Class streamClass;
    private static Class xmlClass;
    private static Class multipartClass;
    
    MessageContainer(final Message message, final int messageType, final int listenerID, final String connectionId) {
        this.messageTypeName = "";
        this.log = ServiceRuntime.getLog();
        this.message = message;
        this.messageType = messageType;
        this.listenerID = listenerID;
        this.connectionId = connectionId;
        if (message != null) {
            this.messageDataType = interfaceTypeToInt(message);
        }
        switch (this.messageType) {
            case 1: {
                this.messageTypeName = "DATA";
                break;
            }
            case 2: {
                this.messageTypeName = "SHUTDOWN";
                break;
            }
            case 3: {
                this.messageTypeName = "ERROR";
                break;
            }
            case 4: {
                this.messageTypeName = "WAKEUP";
                break;
            }
            case 5: {
                this.messageTypeName = "STATE";
                break;
            }
            case 6: {
                this.messageTypeName = "REJECT";
                break;
            }
            default: {
                this.messageTypeName = "UNKNOWN";
                break;
            }
        }
    }
    
    MessageContainer() {
        this(null, 4, 0, "");
    }
    
    public String toString() {
        if (this.message == null) {
            return MessageContainer.NEW_LINE + "Wakeup System Message" + MessageContainer.NEW_LINE;
        }
        interfaceTypeToString(this.message);
        final Integer n = new Integer(this.listenerID);
        String jmsMessageID = null;
        try {
            final Destination jmsReplyTo = this.message.getJMSReplyTo();
            if (jmsReplyTo != null) {
                jmsReplyTo.toString();
            }
            this.message.getJMSCorrelationID();
            jmsMessageID = this.message.getJMSMessageID();
        }
        catch (Throwable t) {}
        return MessageContainer.NEW_LINE + "    MessageID: " + jmsMessageID + " " + MessageContainer.NEW_LINE;
    }
    
    Message getMessage() {
        return this.message;
    }
    
    boolean isDataMessage() {
        return this.messageType == 1;
    }
    
    String getMessageTypeName() {
        return this.messageTypeName;
    }
    
    int getMessageType() {
        return this.messageType;
    }
    
    int getListenerID() {
        return this.listenerID;
    }
    
    int getMessageDataType() {
        return this.messageDataType;
    }
    
    static int interfaceTypeToInt(final Message message) {
        if (message == null) {
            return 0;
        }
        if (MessageContainer.xmlClass != null && MessageContainer.xmlClass.isAssignableFrom(message.getClass())) {
            return 7;
        }
        if (MessageContainer.textClass.isAssignableFrom(message.getClass())) {
            return 2;
        }
        if (MessageContainer.bytesClass.isAssignableFrom(message.getClass())) {
            return 3;
        }
        if (MessageContainer.mapClass.isAssignableFrom(message.getClass())) {
            return 4;
        }
        if (MessageContainer.objectClass.isAssignableFrom(message.getClass())) {
            return 5;
        }
        if (MessageContainer.streamClass.isAssignableFrom(message.getClass())) {
            return 6;
        }
        if (MessageContainer.multipartClass != null && MessageContainer.multipartClass.isAssignableFrom(message.getClass())) {
            return 8;
        }
        return 1;
    }
    
    private static String interfaceTypeToString(final Message message) {
        if (message == null) {
            return "";
        }
        if (MessageContainer.xmlClass != null && MessageContainer.xmlClass.isAssignableFrom(message.getClass())) {
            return "xml";
        }
        if (MessageContainer.textClass.isAssignableFrom(message.getClass())) {
            return "text";
        }
        if (MessageContainer.bytesClass.isAssignableFrom(message.getClass())) {
            return "bytes";
        }
        if (MessageContainer.mapClass.isAssignableFrom(message.getClass())) {
            return "map";
        }
        if (MessageContainer.objectClass.isAssignableFrom(message.getClass())) {
            return "object";
        }
        if (MessageContainer.streamClass.isAssignableFrom(message.getClass())) {
            return "stream";
        }
        if (MessageContainer.multipartClass != null && MessageContainer.multipartClass.isAssignableFrom(message.getClass())) {
            return "multipart";
        }
        return "header";
    }
    
    static MessageContainer createErrorMessage(final Log log, final String s, final JMSException ex, final Vector vector, final Session session, final TextMessage textMessage) {
        TextMessage textMessage2;
        try {
            textMessage2 = session.createTextMessage();
        }
        catch (Throwable t) {
            log.LogStackTrace(1, true, s, t);
            textMessage2 = textMessage;
        }
        try {
            textMessage2.setText(((Throwable)ex).toString());
            ((Message)textMessage2).setStringProperty("exception", ex.getClass().getName());
            ((Message)textMessage2).setStringProperty("errorCode", ex.getErrorCode());
            for (int i = 0; i < vector.size(); ++i) {
                ((Message)textMessage2).setObjectProperty("linkedException-" + new Integer(i + 1).toString(), vector.elementAt(i));
            }
        }
        catch (Throwable t2) {
            log.LogStackTrace(1, true, s, t2);
        }
        return new MessageContainer((Message)textMessage2, 3, 2000000000, null);
    }
    
    static TextMessage createEmeregencyMessage(final Session session) throws Exception {
        final TextMessage textMessage = session.createTextMessage();
        textMessage.setText("JMS Server Failure.");
        ((Message)textMessage).setStringProperty("errorCode", "");
        return textMessage;
    }
    
    static MessageContainer createRejectMessage(final Log log, final String s, final Message message, final JMSException ex, final Vector vector, final Session session, final MultipartMessage multipartMessage) {
        MultipartMessage multipartMessage2;
        try {
            multipartMessage2 = ((progress.message.jclient.Session)session).createMultipartMessage();
        }
        catch (Throwable t) {
            log.LogStackTrace(1, true, s, t);
            multipartMessage2 = multipartMessage;
        }
        try {
            ((Message)multipartMessage2).setStringProperty("errorText", ((Throwable)ex).toString());
            ((Message)multipartMessage2).setStringProperty("exception", ex.getClass().getName());
            ((Message)multipartMessage2).setStringProperty("errorCode", ex.getErrorCode());
            for (int i = 0; i < vector.size(); ++i) {
                ((Message)multipartMessage2).setObjectProperty("linkedException-" + new Integer(i + 1).toString(), vector.elementAt(i));
            }
            multipartMessage2.addPart(multipartMessage2.createMessagePart(message));
        }
        catch (Throwable t2) {
            log.LogStackTrace(1, true, s, t2);
        }
        return new MessageContainer((Message)multipartMessage2, 6, 2020000000, null);
    }
    
    static MultipartMessage createRejectEmeregencyMessage(final Session session) throws Exception {
        final MultipartMessage multipartMessage = ((progress.message.jclient.Session)session).createMultipartMessage();
        ((Message)multipartMessage).setStringProperty("errorText", "JMS Server Failure.");
        ((Message)multipartMessage).setStringProperty("exception", "JMS Server Failure.");
        ((Message)multipartMessage).setStringProperty("errorCode", "");
        return multipartMessage;
    }
    
    static MessageContainer createPersistStateMessage(final Log log, final String s, final String text, final Session session, final TextMessage textMessage) {
        TextMessage textMessage2;
        try {
            textMessage2 = session.createTextMessage();
        }
        catch (Throwable t2) {
            textMessage2 = textMessage;
        }
        try {
            textMessage2.setText(text);
            ((Message)textMessage2).setStringProperty("state", text);
        }
        catch (Throwable t) {
            log.LogStackTrace(1, true, s, t);
        }
        return new MessageContainer((Message)textMessage2, 7, 2030000000, null);
    }
    
    static MessageContainer createChangeStateMessage(final Log log, final String s, final String text, final Session session, final TextMessage textMessage) {
        TextMessage textMessage2;
        try {
            textMessage2 = session.createTextMessage();
        }
        catch (Throwable t2) {
            textMessage2 = textMessage;
        }
        try {
            textMessage2.setText(text);
            ((Message)textMessage2).setStringProperty("state", text);
        }
        catch (Throwable t) {
            log.LogStackTrace(1, true, s, t);
        }
        return new MessageContainer((Message)textMessage2, 5, 2010000000, null);
    }
    
    static TextMessage createStateEmeregencyMessage(final Session session) throws Exception {
        final TextMessage textMessage = session.createTextMessage();
        textMessage.setText("UNKNOWN");
        ((Message)textMessage).setStringProperty("state", "unknown");
        return textMessage;
    }
    
    static {
        NEW_LINE = new String(System.getProperty("line.separator"));
        try {
            MessageContainer.textClass = Class.forName("javax.jms.TextMessage");
            MessageContainer.bytesClass = Class.forName("javax.jms.BytesMessage");
            MessageContainer.mapClass = Class.forName("javax.jms.MapMessage");
            MessageContainer.objectClass = Class.forName("javax.jms.ObjectMessage");
            MessageContainer.streamClass = Class.forName("javax.jms.StreamMessage");
            try {
                MessageContainer.xmlClass = Class.forName("progress.message.jclient.XMLMessage");
            }
            catch (Throwable t2) {}
            try {
                MessageContainer.multipartClass = Class.forName("progress.message.jclient.MultipartMessage");
            }
            catch (Throwable t3) {}
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
