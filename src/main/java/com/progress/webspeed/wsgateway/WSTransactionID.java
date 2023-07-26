// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

import java.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Vector;

public class WSTransactionID
{
    Vector cookies;
    
    public WSTransactionID(final String cookie) throws WSBadValueException {
        this.cookies = new Vector();
        if (cookie != null && cookie.length() > 0) {
            this.setCookie(cookie);
        }
    }
    
    public WSTransactionID(final WSTransactionID wsTransactionID) throws WSBadValueException {
        this.cookies = new Vector();
        if (wsTransactionID == null) {
            return;
        }
        final Vector vCookies = wsTransactionID.getVCookies();
        if (vCookies == null) {
            return;
        }
        final Enumeration<WSCookie> elements = vCookies.elements();
        while (elements.hasMoreElements()) {
            this.setCookie(elements.nextElement().valueOf());
        }
    }
    
    public String getValue() {
        final StringBuffer sb = new StringBuffer("");
        int n = 1;
        if (this.cookies != null) {
            final Enumeration<WSCookie> elements = (Enumeration<WSCookie>)this.cookies.elements();
            while (elements.hasMoreElements()) {
                final WSCookie wsCookie = elements.nextElement();
                if (n == 0) {
                    sb.append(";");
                }
                sb.append(wsCookie.valueOf());
                n = 0;
            }
        }
        return sb.toString();
    }
    
    public void setCookie(final String str) throws WSBadValueException {
        WSCookie scanCookies = new WSCookie();
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
        while (stringTokenizer.hasMoreTokens()) {
            final StringTokenizer stringTokenizer2 = new StringTokenizer(new String(stringTokenizer.nextToken()), "=");
            String trim = null;
            String trim2 = null;
            if (stringTokenizer2.hasMoreTokens()) {
                trim = stringTokenizer2.nextToken().trim();
            }
            if (stringTokenizer2.hasMoreTokens()) {
                trim2 = stringTokenizer2.nextToken().trim();
            }
            if (trim != null) {
                if (trim.equalsIgnoreCase("secure")) {
                    if (scanCookies == null) {
                        continue;
                    }
                    scanCookies.setSecure();
                }
                else if (trim.equalsIgnoreCase("expires")) {
                    if (scanCookies == null) {
                        continue;
                    }
                    scanCookies.setExpires(trim2);
                }
                else if (trim.equalsIgnoreCase("path")) {
                    if (scanCookies == null) {
                        continue;
                    }
                    scanCookies.setPath(trim2);
                }
                else if (trim.equalsIgnoreCase("domain")) {
                    if (scanCookies == null) {
                        continue;
                    }
                    scanCookies.setDomain(trim2);
                }
                else {
                    if (scanCookies != null && scanCookies.getName() != null) {
                        this.cookies.addElement(scanCookies);
                    }
                    try {
                        scanCookies = this.scanCookies(trim);
                    }
                    catch (WSBadValueException ex) {
                        scanCookies = null;
                    }
                    if (scanCookies == null) {
                        scanCookies = new WSCookie();
                    }
                    scanCookies.setName(trim, trim2);
                }
            }
        }
        if (scanCookies != null) {
            this.cookies.addElement(scanCookies);
        }
    }
    
    public Vector getVCookies() {
        return this.cookies;
    }
    
    public void removeCookie(final String str) throws WSBadValueException {
        WSCookie scanCookies;
        try {
            scanCookies = this.scanCookies(str);
        }
        catch (WSBadValueException ex) {
            throw new WSBadValueException("WSTransactionID: removeCookie could not locate cookie named: " + str, (Object)str);
        }
        if (scanCookies != null) {
            this.cookies.removeElement(scanCookies);
        }
    }
    
    public void removeCookie(final WSCookie obj) throws WSBadValueException {
        if (this.cookies == null) {
            throw new WSBadValueException("WSTransactionID: WSCookie Vector not initialized", this.cookies);
        }
        if (obj == null) {
            throw new WSBadValueException("WSTransactionID: removeCookie recieved null WSCookie value", obj);
        }
        this.cookies.removeElement(obj);
    }
    
    private WSCookie scanCookies(final String s) throws WSBadValueException {
        if (this.cookies != null && s != null) {
            final Enumeration<WSCookie> elements = this.cookies.elements();
            while (elements.hasMoreElements()) {
                final WSCookie obj = elements.nextElement();
                if (obj.getName().equalsIgnoreCase(s)) {
                    this.cookies.removeElement(obj);
                    return obj;
                }
            }
        }
        throw new WSBadValueException("WSTransactionID: could not find cookie named: " + s, (Object)s);
    }
}
