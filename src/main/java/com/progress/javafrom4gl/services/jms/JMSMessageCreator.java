// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.sql.SQLException;
import progress.message.jclient.Header;
import progress.message.jclient.Part;
import progress.message.jclient.MultipartMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.StreamMessage;
import progress.message.jclient.XMLMessage;
import javax.jms.TextMessage;
import javax.jms.BytesMessage;
import com.progress.open4gl.dynamicapi.Util;
import javax.jms.Message;
import java.sql.ResultSet;
import javax.jms.Destination;
import javax.jms.Session;
import com.progress.javafrom4gl.ServiceRuntime;
import com.progress.javafrom4gl.Log;

class JMSMessageCreator
{
    protected Log log;
    private StringBuffer stringBuffer;
    
    JMSMessageCreator() {
        this.log = ServiceRuntime.getLog();
        this.stringBuffer = new StringBuffer(2048);
    }
    
    Message createMessage(final Session session, final boolean b, final Destination destination, final byte[] array, final String s, final String s2, final int n, final ResultSet set, final ResultSet set2) throws Exception {
        Message message = null;
        try {
            switch (n) {
                case 7:
                case 9:
                case 10: {
                    if (!b) {
                        throw new Exception(Util.getMessageText(7017734119350084681L));
                    }
                    message = this.createTextMessage(session, set2, true);
                    break;
                }
                case 2: {
                    message = this.createTextMessage(session, set2, false);
                    break;
                }
                case 3: {
                    message = this.createBytesMessage(session, set2);
                    break;
                }
                case 1: {
                    message = this.createMessage(session);
                    break;
                }
                case 6: {
                    message = this.createStreamMessage(session, set2);
                    break;
                }
                case 4: {
                    message = this.createMapMessage(session, set2);
                    break;
                }
                case 8: {
                    if (!b) {
                        throw new Exception(Util.getMessageText(7017734119350084681L));
                    }
                    message = this.createMultipartMessage(session, set2, set);
                    break;
                }
                default: {
                    throw new Exception("Unknown message data type.");
                }
            }
        }
        catch (Exception ex) {
            try {
                set2.close();
                set.close();
            }
            catch (Throwable t) {}
            throw ex;
        }
        if (n != 8) {
            set2.close();
            try {
                this.setHeaderValues(message, destination, array, s, s2, set);
            }
            catch (Exception ex2) {
                try {
                    set.close();
                }
                catch (Throwable t2) {}
                throw ex2;
            }
            set.close();
        }
        else {
            this.setJMSProperties(message, destination, array, s, s2);
        }
        return message;
    }
    
    private Message createMessage(final Session session) throws Exception {
        return session.createMessage();
    }
    
    private Message createBytesMessage(final Session session, final ResultSet set) throws Exception {
        final BytesMessage bytesMessage = session.createBytesMessage();
        while (set.next()) {
            bytesMessage.writeObject(set.getObject(6));
        }
        return (Message)bytesMessage;
    }
    
    private Message createTextMessage(final Session session, final ResultSet set, final boolean b) throws Exception {
        this.stringBuffer.setLength(0);
        boolean b2 = true;
        while (set.next()) {
            final String str = (String)set.getObject(5);
            if (str != null) {
                this.stringBuffer.append(str);
                b2 = false;
            }
        }
        Object o;
        if (!b) {
            if (!b2) {
                o = session.createTextMessage();
                ((TextMessage)o).setText(this.stringBuffer.toString());
            }
            else {
                o = session.createTextMessage();
            }
        }
        else {
            o = ((progress.message.jclient.Session)session).createXMLMessage();
            if (!b2) {
                ((TextMessage)o).setText(this.stringBuffer.toString());
            }
        }
        return (Message)o;
    }
    
    private Message createStreamMessage(final Session session, final ResultSet set) throws Exception {
        final StreamMessage streamMessage = session.createStreamMessage();
        while (set.next()) {
            int n = set.getInt(4);
            int next = 1;
            Object o;
            if (n != 10) {
                o = set.getObject(5);
            }
            else {
                o = set.getObject(6);
            }
            if (n == 11) {
                String s = o.toString();
                int int1 = set.getInt(8);
                while (next = (set.next() ? 1 : 0)) {
                    n = set.getInt(4);
                    if (n != 10) {
                        o = set.getObject(5);
                    }
                    else {
                        o = set.getObject(6);
                    }
                    if (n != 11) {
                        break;
                    }
                    final int int2 = set.getInt(8);
                    if (int1 != int2) {
                        streamMessage.writeString(s);
                        s = o.toString();
                        int1 = int2;
                    }
                    else {
                        s = s.concat(o.toString());
                    }
                }
                streamMessage.writeString(s);
            }
            if (next == 0) {
                break;
            }
            streamMessage.writeObject(JMSDataTypes.convertFrom4gl(o, n));
        }
        return (Message)streamMessage;
    }
    
    private Message createMapMessage(final Session session, final ResultSet set) throws Exception {
        final MapMessage mapMessage = session.createMapMessage();
        while (set.next()) {
            String anObject = set.getString(3);
            int n = set.getInt(4);
            int next = 1;
            Object o;
            if (n != 10) {
                o = set.getObject(5);
            }
            else {
                o = set.getObject(6);
            }
            if (n == 11) {
                String s = o.toString();
                String s2 = anObject;
                while (next = (set.next() ? 1 : 0)) {
                    anObject = set.getString(3);
                    n = set.getInt(4);
                    if (n != 10) {
                        o = set.getObject(5);
                    }
                    else {
                        o = set.getObject(6);
                    }
                    if (n != 11) {
                        break;
                    }
                    if (!s2.equals(anObject)) {
                        mapMessage.setString(s2, s);
                        s = o.toString();
                        s2 = anObject;
                    }
                    else {
                        s = s.concat(o.toString());
                    }
                }
                mapMessage.setString(s2, s);
            }
            if (next == 0) {
                break;
            }
            mapMessage.setObject(anObject, JMSDataTypes.convertFrom4gl(o, n));
        }
        return (Message)mapMessage;
    }
    
    private Message createMultipartMessage(final Session session, final ResultSet set, final ResultSet set2) throws Exception {
        int i = 1;
        final MultipartMessage multipartMessage = ((progress.message.jclient.Session)session).createMultipartMessage();
        this.log.LogMsgln(36, true, "", "Created MultipartMessage");
        final MPBodyContainer mpBodyContainer = new MPBodyContainer();
        final MPPartContainer mpPartContainer = new MPPartContainer();
        while (set.next()) {
            mpBodyContainer.addMessageBodyRow(set);
        }
        set.close();
        while (set2.next()) {
            mpPartContainer.addMessageHeaderRow(set2);
        }
        set2.close();
        this.log.LogMsgln(36, true, "", "Setting multipart message properties");
        final MMessagePart partByPartID = mpPartContainer.getPartByPartID(0);
        if (null != partByPartID) {
            this.log.LogMsgln(36, true, "", "Properties not null");
            final OutMessageHeaderSet messageHeaders = partByPartID.getMessageHeaders();
            while (messageHeaders.next()) {
                final String string = messageHeaders.getString(3);
                ((Message)multipartMessage).setObjectProperty(string, JMSDataTypes.convertFrom4gl(messageHeaders.getString(5), messageHeaders.getInt(4)));
                this.log.LogMsgln(36, true, "", "Setting multipart message property " + string);
            }
        }
        MMessagePart partByPartID2;
        while (null != (partByPartID2 = mpPartContainer.getPartByPartID(i))) {
            if (partByPartID2.isMessagePart()) {
                final int jmsMessageType = partByPartID2.getJMSMessageType();
                this.log.LogMsgln(36, true, "", "Creating message part of type " + Integer.toString(jmsMessageType));
                final OutMessageBodySet bodyByMsgID = mpBodyContainer.getBodyByMsgID(partByPartID2.getMessageID());
                Message message = null;
                try {
                    switch (jmsMessageType) {
                        case 7:
                        case 9:
                        case 10: {
                            message = this.createTextMessage(session, bodyByMsgID, true);
                            break;
                        }
                        case 2: {
                            message = this.createTextMessage(session, bodyByMsgID, false);
                            break;
                        }
                        case 3: {
                            message = this.createBytesMessage(session, bodyByMsgID);
                            break;
                        }
                        case 1: {
                            message = this.createMessage(session);
                            break;
                        }
                        case 6: {
                            message = this.createStreamMessage(session, bodyByMsgID);
                            break;
                        }
                        case 4: {
                            message = this.createMapMessage(session, bodyByMsgID);
                            break;
                        }
                        default: {
                            throw new Exception("Unknown message data type.");
                        }
                    }
                }
                catch (Exception ex) {
                    this.log.LogMsgln(1, true, "", "Exception creating message part: " + ex.toString());
                    throw ex;
                }
                final String jmsCorrelationID = partByPartID2.getJMSCorrelationID();
                if (null != jmsCorrelationID) {
                    message.setJMSCorrelationID(jmsCorrelationID);
                }
                final String jmsType = partByPartID2.getJMSType();
                if (null != jmsType) {
                    message.setJMSType(jmsType);
                }
                final byte[] jmsCorrelationIDAsBytes = partByPartID2.getJMSCorrelationIDAsBytes();
                if (null != jmsType) {
                    message.setJMSCorrelationIDAsBytes(jmsCorrelationIDAsBytes);
                }
                final String contentID = partByPartID2.getContentID();
                MMessagePart mMessagePart = mpPartContainer.getPartByMsgID(i);
                if (mMessagePart == null) {
                    mMessagePart = mpPartContainer.getPartByPartID(i);
                }
                final OutMessageHeaderSet messageHeaders2 = mMessagePart.getMessageHeaders();
                while (messageHeaders2.next()) {
                    message.setObjectProperty(messageHeaders2.getString(3), JMSDataTypes.convertFrom4gl(messageHeaders2.getString(5), messageHeaders2.getInt(4)));
                }
                final Part messagePart = multipartMessage.createMessagePart(message);
                final Header header = messagePart.getHeader();
                try {
                    if (mMessagePart.getContentID() == null) {
                        header.setContentId(contentID);
                    }
                    else {
                        header.setContentId(mMessagePart.getContentID());
                    }
                }
                catch (JMSException ex2) {
                    this.log.LogMsgln(1, true, "", "Error setting content-id on message part " + Integer.toString(i - 1) + ": " + ((Throwable)ex2).toString());
                    throw ex2;
                }
                multipartMessage.addPart(messagePart);
            }
            else {
                final Part part = multipartMessage.createPart();
                this.log.LogMsgln(36, true, "", "Created non-message part");
                final byte[] bodyContent = mpBodyContainer.getBodyContent(i);
                part.setContent(bodyContent);
                this.log.LogMsgln(36, true, "", "Set content of part with size of " + Integer.toString(bodyContent.length));
                final Header header2 = part.getHeader();
                header2.setContentId(partByPartID2.getContentID());
                this.log.LogMsgln(36, true, "", "Set content-id to " + partByPartID2.getContentID());
                header2.setContentType(partByPartID2.getContentType());
                this.log.LogMsgln(36, true, "", "Set content-type to " + partByPartID2.getContentType());
                multipartMessage.addPart(part);
                this.log.LogMsgln(36, true, "", "Added part to message " + Integer.toString(i));
            }
            ++i;
        }
        return (Message)multipartMessage;
    }
    
    private void setHeaderValues(final Message message, final Destination jmsReplyTo, final byte[] jmsCorrelationIDAsBytes, final String jmsCorrelationID, final String jmsType, final ResultSet set) throws Exception {
        while (set.next()) {
            message.setObjectProperty(set.getString(3), JMSDataTypes.convertFrom4gl(set.getString(5), set.getInt(4)));
        }
        if (jmsReplyTo != null) {
            message.setJMSReplyTo(jmsReplyTo);
        }
        if (jmsCorrelationIDAsBytes != null) {
            message.setJMSCorrelationIDAsBytes(jmsCorrelationIDAsBytes);
        }
        if (jmsCorrelationID != null) {
            message.setJMSCorrelationID(jmsCorrelationID);
        }
        if (jmsType != null) {
            message.setJMSType(jmsType);
        }
    }
    
    private void setJMSProperties(final Message message, final Destination jmsReplyTo, final byte[] jmsCorrelationIDAsBytes, final String jmsCorrelationID, final String jmsType) throws Exception {
        if (jmsReplyTo != null) {
            message.setJMSReplyTo(jmsReplyTo);
        }
        if (jmsCorrelationIDAsBytes != null) {
            message.setJMSCorrelationIDAsBytes(jmsCorrelationIDAsBytes);
        }
        if (jmsCorrelationID != null) {
            message.setJMSCorrelationID(jmsCorrelationID);
        }
        if (jmsType != null) {
            message.setJMSType(jmsType);
        }
    }
    
    private class MMessagePart
    {
        private String contentID;
        private String contentType;
        private String jmsReplyTo;
        private String jmsCorrelationID;
        private byte[] jmsCorrIDasBytes;
        private String jmsType;
        private int jmsMessageType;
        private boolean msgPart;
        private int msgID;
        private OutMessageHeaderSet msgHeader;
        
        MMessagePart() {
            this.contentID = null;
            this.contentType = null;
            this.jmsReplyTo = null;
            this.jmsCorrelationID = null;
            this.jmsCorrIDasBytes = null;
            this.jmsType = null;
            this.jmsMessageType = 0;
            this.msgPart = false;
            this.msgID = 0;
            this.msgHeader = null;
            this.msgHeader = new OutMessageHeaderSet();
        }
        
        public void setMessagePart(final boolean msgPart) {
            this.msgPart = msgPart;
        }
        
        public boolean isMessagePart() {
            return this.msgPart;
        }
        
        public void setContentID(final String contentID) {
            this.contentID = contentID;
        }
        
        public String getContentID() {
            return this.contentID;
        }
        
        public void setContentType(final String contentType) {
            this.contentType = contentType;
        }
        
        public String getContentType() {
            return this.contentType;
        }
        
        public void setJMSReplyTo(final String jmsReplyTo) {
            this.jmsReplyTo = jmsReplyTo;
        }
        
        public String getJMSReplyTo() {
            return this.jmsReplyTo;
        }
        
        public void setJMSCorrelationID(final String jmsCorrelationID) {
            this.jmsCorrelationID = jmsCorrelationID;
        }
        
        public String getJMSCorrelationID() {
            return this.jmsCorrelationID;
        }
        
        public void setJMSCorrelationIDAsBytes(final byte[] jmsCorrIDasBytes) {
            this.jmsCorrIDasBytes = jmsCorrIDasBytes;
        }
        
        public byte[] getJMSCorrelationIDAsBytes() {
            return this.jmsCorrIDasBytes;
        }
        
        public void setJMSType(final String jmsType) {
            this.jmsType = jmsType;
        }
        
        public String getJMSType() {
            return this.jmsType;
        }
        
        public void setJMSMessageType(final int jmsMessageType) {
            this.jmsMessageType = jmsMessageType;
        }
        
        public int getJMSMessageType() {
            return this.jmsMessageType;
        }
        
        public void setMessageID(final int msgID) {
            this.msgID = msgID;
        }
        
        public int getMessageID() {
            return this.msgID;
        }
        
        public OutMessageHeaderSet getMessageHeaders() {
            return this.msgHeader;
        }
        
        public boolean addMessageHeaderRow(final ResultSet set) {
            String string;
            int int1;
            String string2;
            try {
                string = set.getString(3);
                int1 = set.getInt(4);
                string2 = set.getString(5);
                JMSMessageCreator.this.log.LogMsgln(36, true, "", "Adding message header: Name = " + string + " Value = " + string2);
            }
            catch (SQLException ex) {
                JMSMessageCreator.this.log.LogMsgln(36, true, "", ex.toString());
                return false;
            }
            if (string.equals("_messagePart")) {
                this.msgPart = string2.equals("true");
            }
            else if (string.equals("content-id")) {
                this.contentID = string2;
            }
            else if (string.equals("content-type")) {
                this.contentType = string2;
            }
            else if (string.equals("_JMSReplyTo")) {
                this.jmsReplyTo = string2;
            }
            else if (string.equals("_JMSCorrelationID")) {
                this.jmsCorrelationID = string2;
            }
            else if (string.equals("_JMSType")) {
                this.jmsType = string2;
            }
            else if (string.equals("_JMSCorrelationIDAsBytes")) {
                try {
                    this.jmsCorrIDasBytes = string2.getBytes("UTF-8");
                }
                catch (Exception ex2) {
                    this.jmsCorrIDasBytes = string2.getBytes();
                }
            }
            else if (string.equals("_JMSMessageType")) {
                this.jmsMessageType = (int)JMSDataTypes.convertFrom4gl(string2, 5);
            }
            else if (string.equals("_MessageId")) {
                this.msgID = (int)JMSDataTypes.convertFrom4gl(string2, 5);
            }
            else {
                JMSMessageCreator.this.log.LogMsgln(36, true, "", "Adding message header to resultset");
                this.msgHeader.addRow(new Integer(-1), new Integer(-1), new String(string), new Integer(int1), new String(string2));
            }
            return true;
        }
    }
    
    private class MPBodyContainer
    {
        private Hashtable messageBodyList;
        
        MPBodyContainer() {
            this.messageBodyList = null;
            this.messageBodyList = new Hashtable();
        }
        
        public boolean addMessageBodyRow(final ResultSet set) {
            int int1;
            int int2;
            try {
                int1 = set.getInt(1);
                int2 = set.getInt(2);
            }
            catch (SQLException ex) {
                return false;
            }
            OutMessageBodySet set2;
            if (int1 > 0) {
                set2 = this.getBodyByMsgID(int1);
                if (set2 == null) {
                    set2 = this.createBodyByMsgID(int1);
                }
            }
            else {
                set2 = this.getBodyByPartID(int2);
                if (set2 == null) {
                    set2 = this.createBodyByPartID(int2);
                }
            }
            try {
                set2.addRow(new Integer(-1), new Integer(-1), (String)set.getObject(3), (Integer)set.getObject(4), (String)set.getObject(5), (byte[])set.getObject(6), (Integer)set.getObject(7));
            }
            catch (SQLException ex2) {
                return false;
            }
            return true;
        }
        
        public OutMessageBodySet getBodyByMsgID(final int i) {
            return this.messageBodyList.get("msg" + Integer.toString(i));
        }
        
        public OutMessageBodySet createBodyByPartID(final int n) {
            final String string = "part" + Integer.toString(n);
            final OutMessageBodySet value = new OutMessageBodySet();
            this.messageBodyList.put(string, value);
            JMSMessageCreator.this.log.LogMsgln(36, true, "", "Creating body by partID: " + Integer.toString(n));
            return value;
        }
        
        public OutMessageBodySet createBodyByMsgID(final int n) {
            final String string = "msg" + Integer.toString(n);
            final OutMessageBodySet value = new OutMessageBodySet();
            this.messageBodyList.put(string, value);
            JMSMessageCreator.this.log.LogMsgln(36, true, "", "Creating body by msgID: " + Integer.toString(n));
            return value;
        }
        
        public OutMessageBodySet getBodyByPartID(final int i) {
            return this.messageBodyList.get("part" + Integer.toString(i));
        }
        
        public byte[] getBodyContent(final int i) {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final OutMessageBodySet set = this.messageBodyList.get("part" + Integer.toString(i));
            if (null != set) {
                try {
                    while (set.next()) {
                        final int int1 = set.getInt(4);
                        final Object object = set.getObject(6);
                        byte[] bytes;
                        if (int1 == 9) {
                            bytes = ((String)object).getBytes();
                        }
                        else {
                            bytes = (byte[])object;
                        }
                        byteArrayOutputStream.write(bytes, 0, bytes.length);
                    }
                }
                catch (SQLException ex) {
                    return null;
                }
            }
            return byteArrayOutputStream.toByteArray();
        }
    }
    
    private class MPPartContainer
    {
        private Hashtable messagePartList;
        
        MPPartContainer() {
            this.messagePartList = null;
            this.messagePartList = new Hashtable();
        }
        
        public boolean addMessageHeaderRow(final ResultSet set) {
            int int1;
            int int2;
            try {
                int1 = set.getInt(1);
                int2 = set.getInt(2);
                JMSMessageCreator.this.log.LogMsgln(36, true, "", "Adding message header row: MsgID = " + Integer.toString(int1) + " PartID = " + Integer.toString(int2));
            }
            catch (SQLException ex) {
                return false;
            }
            MMessagePart mMessagePart;
            if (int1 > 0) {
                mMessagePart = this.getPartByMsgID(int1);
                if (mMessagePart == null) {
                    mMessagePart = this.createPartByMsgID(int1);
                }
            }
            else {
                mMessagePart = this.getPartByPartID(int2);
                if (mMessagePart == null) {
                    mMessagePart = this.createPartByPartID(int2);
                }
            }
            return mMessagePart.addMessageHeaderRow(set);
        }
        
        public MMessagePart getPartByPartID(final int i) {
            return this.messagePartList.get("part" + Integer.toString(i));
        }
        
        public MMessagePart getPartByMsgID(final int i) {
            return this.messagePartList.get("msg" + Integer.toString(i));
        }
        
        public MMessagePart createPartByPartID(final int n) {
            final String string = "part" + Integer.toString(n);
            final MMessagePart value = new MMessagePart();
            this.messagePartList.put(string, value);
            JMSMessageCreator.this.log.LogMsgln(36, true, "", "Creating part by partID: " + Integer.toString(n));
            return value;
        }
        
        public MMessagePart createPartByMsgID(final int i) {
            final String string = "msg" + Integer.toString(i);
            final MMessagePart value = new MMessagePart();
            this.messagePartList.put(string, value);
            value.setMessageID(i);
            JMSMessageCreator.this.log.LogMsgln(36, true, "", "Creating part by msgID: " + Integer.toString(i));
            return value;
        }
    }
}
