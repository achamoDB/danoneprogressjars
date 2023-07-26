// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.common.exception.ProException;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubConstants;

public class ubServerPipeMsg implements ubConstants
{
    public static final int MSGCODE_INVALID_FORMAT = 0;
    public static final int MSGCODE_LOG_MSG = 1;
    public static final int MSGCODE_PORTNUM = 2;
    public static final int MSGCODE_CLIENT_CONNECT = 3;
    public static final int MSGCODE_CLIENT_DISCONNECT = 4;
    public static final int MSGCODE_SERVER_DISCONNECT = 5;
    public static final int MSGCODE_ERROR = 6;
    public static final int MSGCODE_INVALID_ARGS = 7;
    public static final int MSGCODE_SERVER_LOG_MSG = 8;
    public static final int MSGCODE_PROCS = 9;
    public static final String[] DESC_MSGCODE;
    private static final char SENTINEL = '\u0002';
    private static final char ERR = '-';
    private static final char OK = '+';
    private static final char NUMFIELD = '#';
    private static final char TEXTFIELD = '%';
    private int msgcode;
    private char res;
    private String msgDesc;
    private int nargs;
    private Object[] args;
    
    public ubServerPipeMsg(final String s) throws InvalidMsgFormatException {
        try {
            if (s.charAt(0) != '\u0002') {
                throw new InvalidMsgFormatException("no start sentinel");
            }
            this.res = s.charAt(1);
            if (this.res != '-' && this.res != '+') {
                throw new InvalidMsgFormatException("bad result indicator");
            }
            this.msgcode = Integer.parseInt(s.substring(2, 6));
            if (s.charAt(6) != '%') {
                throw new InvalidMsgFormatException("bad description field");
            }
            int index = s.indexOf(35, 7);
            this.msgDesc = s.substring(7, index);
            ++index;
            this.nargs = Integer.parseInt(s.substring(index, index + 2));
            this.args = new Object[this.nargs];
            index += 2;
            for (int i = 0; i < this.nargs; ++i) {
                final char char1 = s.charAt(index++);
                int n;
                if ((n = s.indexOf(35, index)) < 0) {
                    n = s.length();
                }
                int n2;
                if ((n2 = s.indexOf(37, index)) < 0) {
                    n2 = s.length();
                }
                final int n3 = (n < n2) ? n : n2;
                if (char1 == '#') {
                    this.args[i] = new Integer(Integer.parseInt(s.substring(index, n3)));
                }
                else {
                    this.args[i] = s.substring(index, n3);
                }
                index = n3;
            }
        }
        catch (StringIndexOutOfBoundsException ex) {
            throw new InvalidMsgFormatException("String out of bounds " + ex.getMessage());
        }
        catch (NumberFormatException ex2) {
            throw new InvalidMsgFormatException("bad number" + ex2.getMessage());
        }
    }
    
    public int getMsgcode() {
        return this.msgcode;
    }
    
    public boolean getResult() {
        return this.res == '+';
    }
    
    public String getDescription() {
        return this.msgDesc;
    }
    
    public int getNumArgs() {
        return this.nargs;
    }
    
    public Object getArg(final int n) {
        return (n >= 0 && n < this.nargs) ? this.args[n] : null;
    }
    
    public void print(final String str, final int n, final int n2, final IAppLogger appLogger) {
        appLogger.logWithThisLevel(n, n2, str + " msgcode = " + this.msgcode + " (" + ubServerPipeMsg.DESC_MSGCODE[this.msgcode] + ") " + this.res + " " + this.msgDesc);
        for (int i = 0; i < this.nargs; ++i) {
            appLogger.logWithThisLevel(n, n2, " arg[" + i + "] = (" + this.args[i] + ") type= (" + ((this.args[i] instanceof Integer) ? "integer" : "text") + ")");
        }
    }
    
    static {
        DESC_MSGCODE = new String[] { "MSGCODE_INVALID_FORMAT", "MSGCODE_LOG_MSG", "MSGCODE_PORTNUM", "MSGCODE_CLIENT_CONNECT", "MSGCODE_CLIENT_DISCONNECT", "MSGCODE_SERVER_DISCONNECT", "MSGCODE_ERROR", "MSGCODE_INVALID_ARGS", "MSGCODE_SERVER_LOG_MSG", "MSGCODE_PROCS" };
    }
    
    public static class InvalidMsgFormatException extends ProException
    {
        public InvalidMsgFormatException(final String s) {
            super("InvalidMsgFormat", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
}
