// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

import java.util.Date;
import java.net.UnknownHostException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.net.URLEncoder;
import java.io.IOException;

public class WSConnectHTTP
{
    String wsProtocol;
    String wsHost;
    Integer wsPort;
    String wsService;
    String wsMessenger;
    String wsMethod;
    
    public WSConnectHTTP() {
        this.wsProtocol = new String("http");
        this.Initialize();
    }
    
    private void Initialize() {
        this.wsHost = null;
        this.wsPort = new Integer(80);
        this.wsService = null;
        this.wsMessenger = null;
        this.wsMethod = null;
    }
    
    public String getHost() throws WSNoValueException {
        if (this.wsHost == null) {
            throw new WSNoValueException(new String("WSConnectHTTP: Host name not specified"));
        }
        return this.wsHost;
    }
    
    public void setHost(final String original) {
        this.wsHost = new String(original);
    }
    
    public Integer getPort() throws WSNoValueException {
        if (this.wsPort == null) {
            throw new WSNoValueException("WSConnectHTTP: Port number not specified");
        }
        return this.wsPort;
    }
    
    public void setPort(final Integer wsPort) {
        this.wsPort = wsPort;
    }
    
    public String getService() throws WSNoValueException {
        if (this.wsService == null) {
            throw new WSNoValueException("WSConnectHTTP: WebSpeed Service name not specified");
        }
        return this.wsService;
    }
    
    public void setService(final String original) {
        this.wsService = new String(original);
    }
    
    public String getMessengerName() throws WSNoValueException {
        if (this.wsMessenger == null) {
            throw new WSNoValueException("WSConnectHTTP: Messenger name not specified");
        }
        return this.wsMessenger;
    }
    
    public void setMessengerName(final String original) {
        this.wsMessenger = new String(original);
    }
    
    public String getMethod() throws WSNoValueException {
        if (this.wsMethod == null) {
            throw new WSNoValueException("WSConnectHTTP: send Method is not specified");
        }
        return this.wsMethod;
    }
    
    public void setMethod(final String original) throws WSBadValueException {
        if (original.equalsIgnoreCase("GET") || original.equalsIgnoreCase("POST")) {
            this.wsMethod = new String(original);
            return;
        }
        throw new WSBadValueException("WSConnectHTTP: send Method must be POST or GET", (Object)this.wsMethod);
    }
    
    public WSResponse send(final WSRequest wsRequest) throws WSConnectionException, WSBadValueException, WSBadSyntaxException, WSNoValueException, IOException {
        if (!this.Check()) {
            throw new WSNoValueException("WSConnectHTTP: WSConnectHTTP information was not completed");
        }
        if (!wsRequest.Check()) {
            throw new WSNoValueException("WSConnectHTTP: WSRequest information was not completed");
        }
        String sendHTTP;
        try {
            sendHTTP = this.SendHTTP(wsRequest);
        }
        catch (WSNoValueException ex) {
            throw ex;
        }
        catch (WSConnectionException ex2) {
            throw ex2;
        }
        catch (IOException ex3) {
            throw ex3;
        }
        WSResponse wsResponse;
        try {
            wsResponse = new WSResponse(sendHTTP, wsRequest.getTransactionID());
        }
        catch (WSBadSyntaxException ex4) {
            throw ex4;
        }
        return wsResponse;
    }
    
    public boolean Check() {
        boolean b = true;
        try {
            this.getHost();
            this.getPort();
            this.getMessengerName();
            this.getMethod();
        }
        catch (WSNoValueException ex) {
            b = false;
        }
        return b;
    }
    
    private String BuildHTTPParams(final WSRequest wsRequest) {
        String string = null;
        Vector<WSNameValue> vFields;
        try {
            vFields = (Vector<WSNameValue>)wsRequest.getVFields();
        }
        catch (WSNoValueException ex) {
            vFields = null;
            string = new String("");
        }
        if (vFields != null) {
            final Enumeration<WSNameValue> elements = vFields.elements();
            while (elements.hasMoreElements()) {
                String string2;
                if (string != null) {
                    string2 = string + "&";
                }
                else {
                    string2 = new String("");
                }
                final WSNameValue wsNameValue = elements.nextElement();
                string = string2 + URLEncoder.encode(wsNameValue.name) + "=" + URLEncoder.encode(wsNameValue.value);
            }
        }
        return string;
    }
    
    private String SendHTTP(final WSRequest wsRequest) throws WSConnectionException, WSNoValueException, WSBadValueException, IOException {
        final String str = new String("");
        final StringBuffer sb = new StringBuffer();
        final String buildHTTPParams = this.BuildHTTPParams(wsRequest);
        String string = new String("/" + this.wsMessenger + "/");
        if (this.wsService != null) {
            string = string + "WService=" + this.wsService + "/";
        }
        String s;
        try {
            s = string + wsRequest.getWSObject();
        }
        catch (WSNoValueException ex) {
            throw ex;
        }
        if (this.wsMethod.equalsIgnoreCase("GET")) {
            s = s + "?" + buildHTTPParams;
        }
        final URL url = new URL(this.wsProtocol, this.wsHost, this.wsPort, s);
        try {
            Socket socket;
            try {
                socket = new Socket(this.wsHost, this.wsPort);
            }
            catch (IOException ex3) {
                throw new WSCouldNotConnectException("WSConnectHTTP: Could not open socket to host: " + this.wsHost + " on port: " + this.wsPort, "SOCKET_FAILED");
            }
            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String s2 = str + this.wsMethod + " " + url.getFile() + " HTTP/1.0\r\n";
            final WSTransactionID transactionID = wsRequest.getTransactionID();
            if (transactionID != null) {
                final Vector vCookies = transactionID.getVCookies();
                if (vCookies != null) {
                    final Enumeration<WSCookie> elements = vCookies.elements();
                    WSCookie wsCookie = null;
                    while (elements.hasMoreElements()) {
                        if (wsCookie == null) {
                            s2 += "Cookie: ";
                        }
                        else {
                            s2 += ";";
                        }
                        wsCookie = elements.nextElement();
                        final Date expires = wsCookie.getExpires();
                        if (expires != null && expires.getTime() < System.currentTimeMillis()) {
                            transactionID.removeCookie(wsCookie);
                            wsCookie = null;
                        }
                        if (wsCookie != null) {
                            s2 += wsCookie.valueOf();
                        }
                    }
                    if (wsCookie != null) {
                        s2 += "\r\n";
                    }
                }
            }
            if (this.wsMethod.equalsIgnoreCase("POST")) {
                final String string2 = Integer.toString(buildHTTPParams.length());
                Vector<WSNameValue> vHeaderInfo;
                try {
                    vHeaderInfo = (Vector<WSNameValue>)wsRequest.getVHeaderInfo();
                }
                catch (WSNoValueException ex4) {
                    vHeaderInfo = null;
                }
                if (vHeaderInfo != null) {
                    final Enumeration<WSNameValue> elements2 = vHeaderInfo.elements();
                    while (elements2.hasMoreElements()) {
                        final WSNameValue wsNameValue = elements2.nextElement();
                        s2 = s2 + wsNameValue.name + ": " + wsNameValue.value + "\r\n";
                    }
                }
                else {
                    s2 += "Content-type: application/x-www-form-urlencoded\r\n";
                }
                s2 = s2 + "Content-length: " + string2 + "\r\n\r\n" + buildHTTPParams + "\r\n";
            }
            dataOutputStream.writeBytes(s2 + "\r\n");
            dataOutputStream.flush();
            final DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String line;
            while ((line = dataInputStream.readLine()) != null) {
                sb.append(line + "\n");
            }
            dataInputStream.close();
            dataOutputStream.close();
        }
        catch (UnknownHostException ex5) {
            throw new WSCouldNotConnectException("WSConnectHTTP: Unknown Host exception detected", "UNKNOWN_HOST");
        }
        catch (IOException ex2) {
            throw ex2;
        }
        return sb.toString();
    }
}
