// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

import java.util.Enumeration;
import java.util.Vector;

public class WSRequest
{
    private String wsObject;
    private Vector wsFields;
    private Vector wsHeaderInfo;
    private WSTransactionID wsTransactionID;
    
    public WSRequest() {
        this.Initialize();
    }
    
    public void Initialize() {
        this.wsObject = null;
        this.wsFields = new Vector();
        this.wsHeaderInfo = new Vector();
        this.wsTransactionID = null;
    }
    
    public String getWSObject() throws WSNoValueException {
        if (this.wsObject == null) {
            throw new WSNoValueException("WSRequest: Object name not set");
        }
        return this.wsObject;
    }
    
    public void setWSObject(final String original) {
        this.wsObject = new String(original);
    }
    
    public String getHeader(final String s) throws WSNoValueException {
        if (this.wsHeaderInfo == null) {
            throw new WSNoValueException("WSRequest: Header information is not set");
        }
        String s2 = null;
        final Enumeration<WSNameValue> elements = this.wsHeaderInfo.elements();
        while (elements.hasMoreElements()) {
            final WSNameValue wsNameValue = elements.nextElement();
            if (wsNameValue.name.equalsIgnoreCase(s)) {
                s2 = new String(wsNameValue.value);
                break;
            }
        }
        if (s2 == null) {
            throw new WSNoValueException("WSRequest: Header information for " + s + " is not set");
        }
        return s2;
    }
    
    public void setHeader(final String s, final String s2) {
        this.wsHeaderInfo.addElement(new WSNameValue(s, s2));
    }
    
    public Vector getVFields() throws WSNoValueException {
        if (this.wsFields.isEmpty()) {
            throw new WSNoValueException(new String("WSRequest: no Fields available"));
        }
        return this.wsFields;
    }
    
    public Vector getVHeaderInfo() throws WSNoValueException {
        if (this.wsHeaderInfo.isEmpty()) {
            throw new WSNoValueException(new String("WSRequest: no Header Information available"));
        }
        return this.wsHeaderInfo;
    }
    
    public String getField(final String s) throws WSNoValueException {
        if (this.wsFields == null) {
            throw new WSNoValueException("WSRequest: no Fields have been defined");
        }
        String s2 = null;
        final Enumeration<WSNameValue> elements = this.wsFields.elements();
        while (elements.hasMoreElements()) {
            final WSNameValue wsNameValue = elements.nextElement();
            if (wsNameValue.name.equalsIgnoreCase(s)) {
                s2 = new String(wsNameValue.value);
                break;
            }
        }
        if (s2 == null) {
            throw new WSNoValueException("WSRequest: Field information for " + s + " is not set");
        }
        return s2;
    }
    
    public void setField(final String s, final String s2) {
        this.wsFields.addElement(new WSNameValue(s, s2));
    }
    
    public WSTransactionID getTransactionID() {
        return this.wsTransactionID;
    }
    
    public void setTransactionID(final WSTransactionID wsTransactionID) {
        this.wsTransactionID = wsTransactionID;
    }
    
    public boolean Check() {
        try {
            this.getWSObject();
        }
        catch (WSNoValueException ex) {
            return false;
        }
        return true;
    }
}
