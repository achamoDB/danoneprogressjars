// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.util.StringTokenizer;

public class SDOModificationException extends ProSQLException
{
    private ParseErrorMessage parsedMessages;
    
    public SDOModificationException(final OutputSetException ex, final String s, final String s2) {
        super(ex, s);
        this.parsedMessages = new ParseErrorMessage(s2);
    }
    
    public SDOModificationException(final String s) {
        super(s);
        this.parsedMessages = new ParseErrorMessage(s);
    }
    
    public boolean nextMessage() {
        return this.parsedMessages != null && this.parsedMessages.next();
    }
    
    public String getText() {
        if (this.parsedMessages != null) {
            return this.parsedMessages.getText();
        }
        return null;
    }
    
    public String getFields() {
        if (this.parsedMessages != null) {
            return this.parsedMessages.getFields();
        }
        return null;
    }
    
    public String getTable() {
        if (this.parsedMessages != null) {
            return this.parsedMessages.getTable();
        }
        return null;
    }
    
    static class ParseErrorMessage
    {
        private static final char MSG_DELIMITER = '\u0003';
        private static final char[] MSG_DELIMITER_ARRAY;
        private static final String MSG_DELIMITER_STR;
        private static final char MSGFLDS_DELIMITER = '\u0004';
        private static final char NUM_MSG_FLDS = '\u0003';
        private StringTokenizer messages;
        private StringTokenizer currentMessage;
        private String currentMessageString;
        private String currentText;
        private String currentTable;
        private String currentFields;
        private int[] delMap;
        
        ParseErrorMessage(final String str) {
            this.messages = new StringTokenizer(str, ParseErrorMessage.MSG_DELIMITER_STR, false);
            this.delMap = new int[4];
        }
        
        boolean next() {
            if (!this.messages.hasMoreTokens()) {
                return false;
            }
            this.currentMessageString = this.messages.nextToken();
            this.delMap[0] = -1;
            this.delMap[3] = this.currentMessageString.length();
            int n = 1;
            for (int index = 0; index < this.currentMessageString.length() && n < 3; ++index) {
                if (this.currentMessageString.charAt(index) == '\u0004') {
                    this.delMap[n++] = index;
                }
            }
            final int n2 = n;
            final String currentText = null;
            this.currentTable = currentText;
            this.currentFields = currentText;
            this.currentText = currentText;
            int n3 = 0;
            for (int i = 1; i <= n2; ++i) {
                switch (i) {
                    case 1: {
                        this.currentText = this.currentMessageString.substring(this.delMap[n3] + 1, this.delMap[i]);
                        break;
                    }
                    case 2: {
                        this.currentFields = this.currentMessageString.substring(this.delMap[n3] + 1, this.delMap[i]);
                        break;
                    }
                    case 3: {
                        this.currentTable = this.currentMessageString.substring(this.delMap[n3] + 1, this.delMap[i]);
                        break;
                    }
                }
                ++n3;
            }
            return true;
        }
        
        String getText() {
            return this.currentText;
        }
        
        String getFields() {
            return this.currentFields;
        }
        
        String getTable() {
            return this.currentTable;
        }
        
        static {
            MSG_DELIMITER_ARRAY = new char[] { '\u0003' };
            MSG_DELIMITER_STR = new String(ParseErrorMessage.MSG_DELIMITER_ARRAY);
        }
    }
}
