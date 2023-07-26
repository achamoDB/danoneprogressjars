// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

import java.util.Date;

class WSCookie
{
    String Name;
    String Value;
    String Domain;
    Date Expires;
    String Path;
    boolean Secure;
    
    public WSCookie() {
        this.Name = null;
        this.Value = null;
        this.Domain = null;
        this.Expires = null;
        this.Path = null;
        this.Secure = false;
    }
    
    public void setSecure() {
        this.Secure = true;
    }
    
    public void setSecure(final boolean secure) {
        this.Secure = secure;
    }
    
    public boolean isSecure() {
        return this.Secure;
    }
    
    public void setExpires(final String s) {
        if (s == null) {
            this.Expires = null;
        }
        else {
            this.Expires = new Date(s);
        }
    }
    
    public void setExpires(final Date expires) {
        this.Expires = expires;
    }
    
    public Date getExpires() {
        return this.Expires;
    }
    
    public void setPath(final String original) {
        if (original == null) {
            this.Path = null;
        }
        else {
            this.Path = new String(original);
        }
    }
    
    public String getPath() {
        return new String(this.Path);
    }
    
    public void setDomain(final String original) {
        if (original == null) {
            this.Domain = null;
        }
        else {
            this.Domain = new String(original);
        }
    }
    
    public String getDomain(final String s) {
        return new String(this.Domain);
    }
    
    public void setName(final String original, final String original2) throws WSBadValueException {
        if (original != null) {
            this.Name = new String(original);
            if (original2 == null) {
                this.Value = new String("");
            }
            else {
                this.Value = new String(original2);
            }
            return;
        }
        if (original2 != null) {
            throw new WSBadValueException("WSCookie: setName called with value but no name", (Object)original2);
        }
        this.Name = null;
        this.Value = null;
    }
    
    public String getName() {
        if (this.Name == null) {
            return null;
        }
        return new String(this.Name);
    }
    
    public String getValue() {
        if (this.Value == null) {
            return null;
        }
        return new String(this.Value);
    }
    
    public String valueOf() {
        final StringBuffer sb = new StringBuffer("");
        if (this.Name == null) {
            return sb.toString();
        }
        sb.append(this.Name + "=");
        if (this.Value != null) {
            sb.append(this.Value);
        }
        if (this.Expires != null) {
            sb.append("; expires=" + this.Expires.toString());
        }
        if (this.Path != null) {
            sb.append("; path=" + this.Path);
        }
        if (this.Domain != null) {
            sb.append("; domain=" + this.Domain);
        }
        if (this.Secure) {
            sb.append("; secure");
        }
        return sb.toString();
    }
}
