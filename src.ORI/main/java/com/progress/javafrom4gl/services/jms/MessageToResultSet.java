// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import javax.jms.MessageEOFException;
import com.progress.javafrom4gl.Log;
import progress.message.jclient.Header;
import progress.message.jclient.Part;
import javax.jms.MapMessage;
import javax.jms.StreamMessage;
import javax.jms.BytesMessage;
import javax.jms.TextMessage;
import com.progress.open4gl.IntHolder;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import com.actional.lg.interceptor.sdk.ClientInteraction;
import java.util.Enumeration;
import java.sql.ResultSet;
import javax.jms.Message;

class MessageToResultSet
{
    static final int SEGMENT_SIZE = 8192;
    static final int MAX_STRING_SIZE = 32767;
    static int seqNum;
    
    static ResultSet createPropertySet(final Message message) throws Exception {
        final OutMessageHeaderSet set = new OutMessageHeaderSet();
        final Enumeration propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = propertyNames.nextElement();
            final Object objectProperty = message.getObjectProperty(s);
            String string = null;
            final int itemType = JMSDataTypes.getItemType(objectProperty);
            if (objectProperty != null) {
                string = objectProperty.toString();
            }
            if (s.equals("LG_Header")) {
                final String actionalManifest = getActionalManifest();
                if (null != actionalManifest) {
                    string = actionalManifest;
                }
            }
            set.addRow(new Integer(-1), new Integer(-1), s, new Integer(itemType), string);
        }
        MessageToResultSet.seqNum = 0;
        return set;
    }
    
    private static String getActionalManifest() {
        String writeHeader = null;
        final ClientInteraction value = ClientInteraction.get();
        if (null != value) {
            try {
                writeHeader = InterHelpBase.writeHeader(value);
            }
            catch (Exception ex) {}
        }
        return writeHeader;
    }
    
    static ResultSet createMPPropertySet(final Message message) throws Exception {
        final OutMessageHeaderSet set = new OutMessageHeaderSet();
        final Enumeration propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = propertyNames.nextElement();
            final Object objectProperty = message.getObjectProperty(s);
            String string = null;
            final int itemType = JMSDataTypes.getItemType(objectProperty);
            if (objectProperty != null) {
                string = objectProperty.toString();
            }
            set.addRow(new Integer(0), new Integer(0), s, new Integer(itemType), string);
        }
        MessageToResultSet.seqNum = 0;
        return set;
    }
    
    static ResultSet createBodySet(final Message message, final int i, final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        intHolder.setIntValue(0);
        intHolder2.setIntValue(0);
        switch (i) {
            case 2:
            case 7: {
                return createTextSet((TextMessage)message, intHolder, intHolder2);
            }
            case 3: {
                return createBytesSet((BytesMessage)message, intHolder, intHolder2);
            }
            case 6: {
                return createStreamSet((StreamMessage)message, intHolder);
            }
            case 4: {
                return createMapSet((MapMessage)message);
            }
            case 1: {
                return createEmptySet();
            }
            case 5: {
                return createObjectSet(intHolder, intHolder2);
            }
            default: {
                throw new Error("Unsupported message type: " + i);
            }
        }
    }
    
    static void addMessageHeaders(final int n, final int n2, final Message message, final OutMessageHeaderSet set) throws Exception {
        final String s = "_JMSMessageID";
        final String jmsMessageID = message.getJMSMessageID();
        if (jmsMessageID != null && jmsMessageID.length() > 0) {
            set.addRow(new Integer(n), new Integer(0), s, new Integer(9), jmsMessageID);
        }
        final String s2 = "_JMSCorrelationID";
        final String jmsCorrelationID = message.getJMSCorrelationID();
        if (jmsCorrelationID != null && jmsCorrelationID.length() > 0) {
            set.addRow(new Integer(n), new Integer(0), s2, new Integer(9), jmsCorrelationID);
        }
        final String s3 = "_JMSCorrelationIDAsBytes";
        byte[] jmsCorrelationIDAsBytes;
        try {
            jmsCorrelationIDAsBytes = message.getJMSCorrelationIDAsBytes();
        }
        catch (Exception ex) {
            jmsCorrelationIDAsBytes = null;
        }
        if (jmsCorrelationIDAsBytes != null && jmsCorrelationIDAsBytes.length > 0) {
            set.addRow(new Integer(n), new Integer(0), s3, new Integer(10), jmsCorrelationIDAsBytes);
        }
        final String s4 = "_JMSDeliveryMode";
        final String string = Integer.toString(message.getJMSDeliveryMode());
        if (string != null && string.length() > 0) {
            set.addRow(new Integer(n), new Integer(0), s4, new Integer(5), string);
        }
        final String s5 = "_JMSType";
        final String jmsType = message.getJMSType();
        if (jmsType != null && jmsType.length() > 0) {
            set.addRow(new Integer(n), new Integer(0), s5, new Integer(9), jmsType);
        }
        final String s6 = "_JMSRedelivered";
        final String s7 = message.getJMSRedelivered() ? "true" : "false";
        if (s7 != null && s7.length() > 0) {
            set.addRow(new Integer(n), new Integer(0), s6, new Integer(1), s7);
        }
        final String s8 = "_JMSExpiration";
        final String string2 = Long.toString(message.getJMSExpiration());
        if (string2 != null && string2.length() > 0) {
            set.addRow(new Integer(n), new Integer(0), s8, new Integer(6), string2);
        }
        final String s9 = "_JMSPriority";
        final String string3 = Long.toString(message.getJMSPriority());
        if (string3 != null && string3.length() > 0) {
            set.addRow(new Integer(n), new Integer(0), s9, new Integer(5), string3);
        }
    }
    
    static void addMessageProperties(final int value, final Message message, final OutMessageHeaderSet set) throws Exception {
        final Enumeration propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = propertyNames.nextElement();
            final Object objectProperty = message.getObjectProperty(s);
            String string = null;
            final int itemType = JMSDataTypes.getItemType(objectProperty);
            if (objectProperty != null) {
                string = objectProperty.toString();
            }
            set.addRow(new Integer(value), new Integer(0), s, new Integer(itemType), string);
        }
    }
    
    static void appendPropertySet(final int value, final OutMessageHeaderSet set, final Message message) throws Exception {
        final Enumeration propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = propertyNames.nextElement();
            final Object objectProperty = message.getObjectProperty(s);
            String string = null;
            final int itemType = JMSDataTypes.getItemType(objectProperty);
            if (objectProperty != null) {
                string = objectProperty.toString();
            }
            set.addRow(new Integer(value), new Integer(0), s, new Integer(itemType), string);
        }
    }
    
    static void addPartHeaders(final int value, final int value2, final Part part, final OutMessageHeaderSet set) {
        final Header header = part.getHeader();
        set.addRow(new Integer(value), new Integer(value2), "content-type", new Integer(9), header.getContentType());
        set.addRow(new Integer(value), new Integer(value2), "content-id", new Integer(9), header.getContentId());
        final Enumeration headerFieldNames = header.getHeaderFieldNames();
        while (headerFieldNames.hasMoreElements()) {
            final String s = headerFieldNames.nextElement();
            set.addRow(new Integer(value), new Integer(value2), s, new Integer(9), header.getHeaderField(s));
        }
    }
    
    static void addPartBody(final int value, final int value2, final Part part, final OutMessageBodySet set, final IntHolder intHolder, final IntHolder intHolder2) {
        int intValue = 0;
        final byte[] contentBytes = part.getContentBytes();
        final int length = contentBytes.length;
        int n = 0;
        int i = 0;
        while (i == 0) {
            byte[] array;
            if (n + 8192 > length) {
                final int n2 = length - n;
                array = new byte[n2];
                System.arraycopy(contentBytes, n, array, 0, n2);
                i = 1;
            }
            else {
                array = new byte[8192];
                System.arraycopy(contentBytes, n, array, 0, 8192);
                n += 8192;
            }
            set.addRow(new Integer(value), new Integer(value2), null, new Integer(10), null, array, new Integer(intValue++));
        }
        intHolder.setIntValue(intValue);
        intHolder2.setIntValue(length);
    }
    
    static void addPartBodyLog(final Log log, final int n, final int n2, final Part part, final OutMessageBodySet set, final IntHolder intHolder, final IntHolder intHolder2) {
        int intValue = 0;
        final byte[] contentBytes = part.getContentBytes();
        final int length = contentBytes.length;
        int n3 = 0;
        int i = 0;
        while (i == 0) {
            byte[] array;
            if (n3 + 8192 > length) {
                final int n4 = length - n3;
                array = new byte[n4];
                System.arraycopy(contentBytes, n3, array, 0, n4);
                i = 1;
            }
            else {
                array = new byte[8192];
                System.arraycopy(contentBytes, n3, array, 0, 8192);
                n3 += 8192;
            }
            log.LogMsgln(36, true, "", "Msg Id: " + n + " Part Id: " + n2 + " Bytes: " + array.length);
            set.addRow(new Integer(n), new Integer(n2), null, new Integer(10), null, array, new Integer(intValue++));
        }
        intHolder.setIntValue(intValue);
        intHolder2.setIntValue(length);
    }
    
    static void appendBodySet(final int n, final Message message, final int i, final OutMessageBodySet set, final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        intHolder.setIntValue(0);
        intHolder2.setIntValue(0);
        switch (i) {
            case 2:
            case 7: {
                appendTextSet(n, (TextMessage)message, set, intHolder, intHolder2);
                break;
            }
            case 3: {
                appendBytesSet(n, (BytesMessage)message, set, intHolder, intHolder2);
                break;
            }
            case 6: {
                appendStreamSet(n, (StreamMessage)message, set, intHolder);
                break;
            }
            case 4: {
                appendMapSet(n, (MapMessage)message, set);
                break;
            }
            case 1: {
                break;
            }
            case 5: {
                appendObjectSet(n, set, intHolder, intHolder2);
                break;
            }
            default: {
                throw new Error("Unsupported message type: " + i);
            }
        }
    }
    
    static ResultSet createEmptySet() throws Exception {
        return new OutMessageBodySet();
    }
    
    static ResultSet createObjectSet(final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        final OutMessageBodySet set = new OutMessageBodySet();
        final String s = new String("MessageObject: Not Supported");
        set.addRow(new Integer(-1), new Integer(-1), null, new Integer(9), s, null, new Integer(0));
        intHolder.setIntValue(1);
        intHolder2.setIntValue(s.length());
        return set;
    }
    
    static void appendObjectSet(final int value, final OutMessageBodySet set, final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        final String s = new String("MessageObject: Not Supported");
        set.addRow(new Integer(value), new Integer(0), null, new Integer(9), s, null, new Integer(0));
        intHolder.setIntValue(1);
        intHolder2.setIntValue(s.length());
    }
    
    static ResultSet createTextSet(final TextMessage textMessage, final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        final OutMessageBodySet set = new OutMessageBodySet();
        final TextSegmentation textSegmentation = new TextSegmentation(textMessage.getText());
        int intValue = 0;
        int intValue2 = 0;
        while (textSegmentation.hasMoreElements()) {
            final String s = textSegmentation.nextElement();
            set.addRow(new Integer(-1), new Integer(-1), null, new Integer(9), s, null, new Integer(intValue++));
            if (s != null) {
                intValue2 += s.length();
            }
        }
        intHolder.setIntValue(intValue);
        intHolder2.setIntValue(intValue2);
        return set;
    }
    
    static void appendTextSet(final int value, final TextMessage textMessage, final OutMessageBodySet set, final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        final TextSegmentation textSegmentation = new TextSegmentation(textMessage.getText());
        int intValue = 0;
        int intValue2 = 0;
        while (textSegmentation.hasMoreElements()) {
            final String s = textSegmentation.nextElement();
            set.addRow(new Integer(value), new Integer(0), null, new Integer(9), s, null, new Integer(intValue++));
            if (s != null) {
                intValue2 += s.length();
            }
        }
        intHolder.setIntValue(intValue);
        intHolder2.setIntValue(intValue2);
    }
    
    static ResultSet createBytesSet(final BytesMessage bytesMessage, final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        final OutMessageBodySet set = new OutMessageBodySet();
        bytesMessage.reset();
        int intValue = 0;
        int intValue2 = 0;
        final byte[] array = new byte[8192];
        int i = 0;
        while (i == 0) {
            try {
                final int bytes = bytesMessage.readBytes(array, array.length);
                if (bytes <= 0) {
                    i = 1;
                }
                else {
                    intValue2 += bytes;
                    final byte[] array2 = new byte[bytes];
                    System.arraycopy(array, 0, array2, 0, bytes);
                    set.addRow(new Integer(-1), new Integer(-1), null, new Integer(10), null, array2, new Integer(intValue++));
                }
            }
            catch (MessageEOFException ex) {
                i = 1;
            }
        }
        intHolder.setIntValue(intValue);
        intHolder2.setIntValue(intValue2);
        return set;
    }
    
    static void appendBytesSet(final int value, final BytesMessage bytesMessage, final OutMessageBodySet set, final IntHolder intHolder, final IntHolder intHolder2) throws Exception {
        bytesMessage.reset();
        int intValue = 0;
        int intValue2 = 0;
        final byte[] array = new byte[8192];
        int i = 0;
        while (i == 0) {
            try {
                final int bytes = bytesMessage.readBytes(array, array.length);
                if (bytes <= 0) {
                    i = 1;
                }
                else {
                    intValue2 += bytes;
                    final byte[] array2 = new byte[bytes];
                    System.arraycopy(array, 0, array2, 0, bytes);
                    set.addRow(new Integer(value), new Integer(0), null, new Integer(10), null, array2, new Integer(intValue++));
                }
            }
            catch (MessageEOFException ex) {
                i = 1;
            }
        }
        intHolder.setIntValue(intValue);
        intHolder2.setIntValue(intValue2);
    }
    
    static ResultSet createStreamSet(final StreamMessage streamMessage, final IntHolder intHolder) throws Exception {
        final OutMessageBodySet set = new OutMessageBodySet();
        streamMessage.reset();
        int intValue = 0;
        int i = 0;
        while (i == 0) {
            try {
                final Object object = streamMessage.readObject();
                String string = null;
                byte[] array = null;
                final int itemType = JMSDataTypes.getItemType(object);
                if (object != null) {
                    if (itemType == 10) {
                        array = (byte[])object;
                    }
                    else {
                        string = object.toString();
                    }
                }
                if (itemType == 9 && string.length() > 32767) {
                    final TextSegmentation textSegmentation = new TextSegmentation(string);
                    ++MessageToResultSet.seqNum;
                    while (textSegmentation.hasMoreElements()) {
                        set.addRow(new Integer(-1), new Integer(-1), null, new Integer(11), textSegmentation.nextElement(), null, new Integer(intValue++), new Integer(MessageToResultSet.seqNum));
                    }
                }
                else {
                    set.addRow(new Integer(-1), new Integer(-1), null, new Integer(itemType), string, array, new Integer(intValue++));
                }
            }
            catch (MessageEOFException ex) {
                i = 1;
            }
        }
        intHolder.setIntValue(intValue);
        return set;
    }
    
    static void appendStreamSet(final int n, final StreamMessage streamMessage, final OutMessageBodySet set, final IntHolder intHolder) throws Exception {
        streamMessage.reset();
        int intValue = 0;
        int i = 0;
        while (i == 0) {
            try {
                final Object object = streamMessage.readObject();
                String string = null;
                byte[] array = null;
                final int itemType = JMSDataTypes.getItemType(object);
                if (object != null) {
                    if (itemType == 10) {
                        array = (byte[])object;
                    }
                    else {
                        string = object.toString();
                    }
                }
                if (itemType == 9 && string.length() > 32767) {
                    final TextSegmentation textSegmentation = new TextSegmentation(string);
                    ++MessageToResultSet.seqNum;
                    while (textSegmentation.hasMoreElements()) {
                        set.addRow(new Integer(n), new Integer(0), null, new Integer(11), textSegmentation.nextElement(), null, new Integer(intValue++), new Integer(MessageToResultSet.seqNum));
                    }
                }
                else {
                    set.addRow(new Integer(n), new Integer(0), null, new Integer(itemType), string, array, new Integer(intValue++));
                }
            }
            catch (MessageEOFException ex) {
                i = 1;
            }
        }
        intHolder.setIntValue(intValue);
    }
    
    static ResultSet createMapSet(final MapMessage mapMessage) throws Exception {
        final OutMessageBodySet set = new OutMessageBodySet();
        final Enumeration mapNames = mapMessage.getMapNames();
        int n = 0;
        while (mapNames.hasMoreElements()) {
            final String s = mapNames.nextElement();
            final Object object = mapMessage.getObject(s);
            String string = null;
            byte[] array = null;
            final int itemType = JMSDataTypes.getItemType(object);
            if (object != null) {
                if (itemType == 10) {
                    array = (byte[])object;
                }
                else {
                    string = object.toString();
                }
            }
            if (itemType == 9 && string.length() > 32767) {
                final TextSegmentation textSegmentation = new TextSegmentation(string);
                ++MessageToResultSet.seqNum;
                while (textSegmentation.hasMoreElements()) {
                    set.addRow(new Integer(-1), new Integer(-1), s, new Integer(11), textSegmentation.nextElement(), null, new Integer(n++), new Integer(MessageToResultSet.seqNum));
                }
            }
            else {
                set.addRow(new Integer(-1), new Integer(-1), s, new Integer(itemType), string, array, new Integer(n++));
            }
        }
        return set;
    }
    
    static void appendMapSet(final int n, final MapMessage mapMessage, final OutMessageBodySet set) throws Exception {
        final Enumeration mapNames = mapMessage.getMapNames();
        int n2 = 0;
        while (mapNames.hasMoreElements()) {
            final String s = mapNames.nextElement();
            final Object object = mapMessage.getObject(s);
            String string = null;
            byte[] array = null;
            final int itemType = JMSDataTypes.getItemType(object);
            if (object != null) {
                if (itemType == 10) {
                    array = (byte[])object;
                }
                else {
                    string = object.toString();
                }
            }
            if (itemType == 9 && string.length() > 32767) {
                final TextSegmentation textSegmentation = new TextSegmentation(string);
                ++MessageToResultSet.seqNum;
                while (textSegmentation.hasMoreElements()) {
                    set.addRow(new Integer(n), new Integer(0), s, new Integer(11), textSegmentation.nextElement(), null, new Integer(n2++), new Integer(MessageToResultSet.seqNum));
                }
            }
            else {
                set.addRow(new Integer(n), new Integer(0), s, new Integer(itemType), string, array, new Integer(n2++));
            }
        }
    }
    
    static {
        MessageToResultSet.seqNum = 0;
    }
    
    static class TextSegmentation implements Enumeration
    {
        private String text;
        private boolean oneSegment;
        private boolean moreElements;
        private int current;
        private int textLength;
        
        TextSegmentation(final String text) {
            this.text = text;
            if (text != null) {
                this.textLength = text.length();
            }
            if (text == null || this.textLength <= 8192) {
                this.oneSegment = true;
            }
            else {
                this.oneSegment = false;
            }
            this.moreElements = true;
            this.current = 0;
        }
        
        public boolean hasMoreElements() {
            if (this.moreElements) {
                if (this.oneSegment) {
                    this.moreElements = false;
                }
                return true;
            }
            return false;
        }
        
        public Object nextElement() {
            if (this.oneSegment) {
                return this.text;
            }
            int textLength = this.current + 8192;
            if (textLength >= this.textLength) {
                this.moreElements = false;
                textLength = this.textLength;
            }
            final String substring = this.text.substring(this.current, textLength);
            this.current = textLength;
            return substring;
        }
    }
}
