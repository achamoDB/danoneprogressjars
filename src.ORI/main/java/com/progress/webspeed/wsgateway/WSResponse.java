// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

import java.util.Enumeration;
import java.io.StringBufferInputStream;
import java.util.Vector;

public class WSResponse
{
    int ch;
    BufMgr header;
    BufMgr body;
    Vector vHeader;
    Vector vBody;
    StringBufferInputStream input;
    HTTPStream headerStream;
    HTTPStream bodyStream;
    WSTransactionID cookie;
    
    public String getContentType() throws WSNoValueException {
        return this.getHeaderField("Content-Type");
    }
    
    public WSTransactionID getTransactionID() {
        WSTransactionID wsTransactionID;
        try {
            wsTransactionID = new WSTransactionID(this.cookie.getValue());
        }
        catch (WSBadValueException ex) {
            return null;
        }
        return wsTransactionID;
    }
    
    public String getHeaderField(final String s) throws WSNoValueException {
        return this.getValue(this.vHeader, s);
    }
    
    public String getHeaderField(final String s, final int n) throws WSNoValueException {
        return this.getValue(this.vHeader, s, n);
    }
    
    public String getField(final String s) throws WSNoValueException {
        return this.getValue(this.vBody, s);
    }
    
    public String getField(final String s, final int n) throws WSNoValueException {
        return this.getValue(this.vBody, s, n);
    }
    
    public String getHeader() {
        return this.header.toString();
    }
    
    public String getBody() {
        return this.body.toString();
    }
    
    public Vector getVHeader() {
        return this.vHeader;
    }
    
    public Vector getVBody() {
        return this.vBody;
    }
    
    public WSResponse(final String s) throws WSBadSyntaxException, WSBadValueException {
        this.ch = -1;
        this.header = new BufMgr();
        this.body = new BufMgr();
        this.vHeader = new Vector();
        this.vBody = new Vector();
        this.cookie = null;
        this.input = new StringBufferInputStream(s);
        this.headerStream = new HTTPStream(this.input, this.header);
        this.bodyStream = new HTTPStream(this.input, this.body);
        this.parseHeader();
        String headerField = "";
        try {
            headerField = this.getHeaderField("Set-Cookie", 59);
        }
        catch (WSNoValueException ex) {}
        this.cookie = new WSTransactionID(headerField);
        try {
            if (this.getHeaderField("Content-Type").equalsIgnoreCase("application/x-www-form-urlencoded")) {
                this.parseBody();
            }
            else {
                this.putBody();
            }
        }
        catch (WSNoValueException ex2) {
            this.putBody();
        }
    }
    
    WSResponse(final String s, final WSTransactionID wsTransactionID) throws WSBadSyntaxException, WSBadValueException {
        this.ch = -1;
        this.header = new BufMgr();
        this.body = new BufMgr();
        this.vHeader = new Vector();
        this.vBody = new Vector();
        this.cookie = null;
        this.input = new StringBufferInputStream(s);
        this.headerStream = new HTTPStream(this.input, this.header);
        this.bodyStream = new HTTPStream(this.input, this.body);
        this.parseHeader();
        String headerField = "";
        try {
            headerField = this.getHeaderField("Set-Cookie", 59);
        }
        catch (WSNoValueException ex) {}
        (this.cookie = new WSTransactionID(wsTransactionID)).setCookie(headerField);
        try {
            if (this.getHeaderField("Content-Type").equalsIgnoreCase("application/x-www-form-urlencoded")) {
                this.parseBody();
            }
            else {
                this.putBody();
            }
        }
        catch (WSNoValueException ex2) {
            this.putBody();
        }
    }
    
    void putBody() {
        while (this.bodyStream.nextChar() != -1) {}
    }
    
    void addPair(final Vector vector, final String s, final String s2) {
        vector.addElement(new WSNameValue(s, s2));
    }
    
    String getValue(final Vector vector, final String str, final int n) throws WSNoValueException {
        String str2 = null;
        final String string = new Character((char)n).toString();
        final Enumeration<WSNameValue> elements = vector.elements();
        while (elements.hasMoreElements()) {
            final WSNameValue wsNameValue = elements.nextElement();
            if (str.equalsIgnoreCase(wsNameValue.name)) {
                str2 = ((str2 != null) ? (str2 + string + wsNameValue.value) : new String(wsNameValue.value));
            }
        }
        if (str2 == null) {
            throw new WSNoValueException("No field named " + str);
        }
        return str2;
    }
    
    String getValue(final Vector vector, final String s) throws WSNoValueException {
        return this.getValue(vector, s, 44);
    }
    
    void parseHeader() throws WSBadSyntaxException {
        this.ch = this.headerStream.nextChar();
        while (true) {
            if (this.ch == 13) {
                if ((this.ch = this.headerStream.nextChar()) == 10) {
                    return;
                }
            }
            else if (this.ch == 10) {
                return;
            }
            String headerName;
            try {
                headerName = this.parseHeaderName();
            }
            catch (WSStatusLineException ex) {
                if (this.ch == 13) {
                    if ((this.ch = this.headerStream.nextChar()) != 10) {
                        continue;
                    }
                    this.ch = this.headerStream.nextChar();
                }
                else {
                    if (this.ch != 10) {
                        continue;
                    }
                    this.ch = this.headerStream.nextChar();
                }
                continue;
            }
            while (this.ch == 32 || this.ch == 9) {
                this.ch = this.headerStream.nextChar();
            }
            this.addPair(this.vHeader, headerName, this.parseHeaderValue());
        }
    }
    
    String parseHeaderName() throws WSBadSyntaxException, WSStatusLineException {
        final BufMgr bufMgr = new BufMgr();
        while (this.ch >= 32 && this.ch != 58) {
            bufMgr.append(this.ch);
            this.ch = this.headerStream.nextChar();
        }
        if (this.ch == 58 && bufMgr.getSize() > 0) {
            this.ch = this.headerStream.nextChar();
            return bufMgr.toString();
        }
        if (bufMgr.getSize() > 0 && bufMgr.toString().startsWith("HTTP/", 0)) {
            throw new WSStatusLineException();
        }
        throw new WSBadSyntaxException("Expected a header name.");
    }
    
    String parseHeaderValue() throws WSBadSyntaxException {
        final BufMgr bufMgr = new BufMgr();
        while (this.ch == 32 || this.ch == 9) {
            this.ch = this.headerStream.nextChar();
        }
    Label_0214:
        while (true) {
            Label_0200: {
                switch (this.ch) {
                    case -1: {
                        break Label_0214;
                    }
                    case 13: {
                        final int nextChar = this.headerStream.nextChar();
                        this.ch = nextChar;
                        if (nextChar != 10) {
                            bufMgr.append(13);
                            continue;
                        }
                    }
                    case 10: {
                        switch (this.ch = this.headerStream.nextChar()) {
                            case 9:
                            case 32: {
                                do {
                                    this.ch = this.headerStream.nextChar();
                                } while (this.ch == 32 || this.ch == 9);
                                bufMgr.append(this.ch);
                                break Label_0200;
                            }
                            default: {
                                break Label_0214;
                            }
                        }
                        break;
                    }
                    default: {
                        bufMgr.append((char)this.ch);
                        break;
                    }
                }
            }
            this.ch = this.headerStream.nextChar();
        }
        return bufMgr.toString();
    }
    
    void parseBody() throws WSBadSyntaxException {
        String string = null;
        BufMgr bufMgr = new BufMgr();
        this.ch = this.bodyStream.nextChar();
        while (true) {
            switch (this.ch) {
                case 43: {
                    bufMgr.append(32);
                    break;
                }
                case 37: {
                    final int nextChar = this.bodyStream.nextChar();
                    this.ch = nextChar;
                    final int n = nextChar;
                    if (nextChar == -1) {
                        throw new WSBadSyntaxException("Invalid escape seq.");
                    }
                    final int nextChar2 = this.bodyStream.nextChar();
                    this.ch = nextChar2;
                    final int n2;
                    if ((n2 = nextChar2) == -1) {
                        throw new WSBadSyntaxException("Invalid escape seq.");
                    }
                    bufMgr.append((char)((byte)(Character.isDigit((char)n2) ? (n2 - 48) : (10 + (Character.toUpperCase((char)n2) - 'A'))) | (byte)(Character.isDigit((char)n) ? (n - 48) : (10 + (Character.toUpperCase((char)n) - 'A'))) << 4));
                    break;
                }
                case 38: {
                    if (string == null) {
                        if (bufMgr.getSize() != 0) {
                            this.addPair(this.vBody, bufMgr.toString(), "");
                        }
                    }
                    else {
                        this.addPair(this.vBody, string, bufMgr.toString());
                        string = null;
                    }
                    bufMgr = new BufMgr();
                    break;
                }
                case 61: {
                    if (string != null) {
                        throw new WSBadSyntaxException("Already has a key.");
                    }
                    string = bufMgr.toString();
                    bufMgr = new BufMgr();
                    break;
                }
                case -1: {
                    if (string == null) {
                        if (bufMgr.getSize() != 0) {
                            this.addPair(this.vBody, bufMgr.toString(), "");
                        }
                    }
                    else {
                        this.addPair(this.vBody, string, bufMgr.toString());
                    }
                    return;
                }
                case 10:
                case 13: {
                    break;
                }
                default: {
                    bufMgr.append(this.ch);
                    break;
                }
            }
            this.ch = this.bodyStream.nextChar();
        }
    }
}
