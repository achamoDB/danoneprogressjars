// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import java.io.Serializable;

public class ListInfo implements Serializable
{
    public static final int STATUS_UNKNOWN = -1;
    public static final int STATUS_OK = 0;
    public static final int STATUS_DISABLED = 1;
    public static final int STATUS_DAMAGED = 2;
    public static final String STATUS_UNKNOWN_STR = "UNKNOWN";
    public static final String STATUS_OK_STR = "ENABLED";
    public static final String STATUS_DISABLED_STR = "DISABLED";
    public static final String STATUS_DAMAGED_STR = "DAMAGED";
    protected String m_friendlyName;
    protected String m_targetURI;
    protected int m_status;
    
    public ListInfo() {
        this.m_friendlyName = null;
        this.m_targetURI = null;
        this.m_status = -1;
    }
    
    public String getFriendlyName() {
        return this.m_friendlyName;
    }
    
    public void setFriendlyName(final String friendlyName) {
        this.m_friendlyName = friendlyName;
    }
    
    public String getTargetURI() {
        return this.m_targetURI;
    }
    
    public void setTargetURI(final String targetURI) {
        this.m_targetURI = targetURI;
    }
    
    public int getStatus() {
        return this.m_status;
    }
    
    public void setStatus(final int status) {
        if (status < -1 || status > 2) {
            throw new IllegalArgumentException("Invalid value for ListInfo Status");
        }
        this.m_status = status;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.m_friendlyName);
        sb.append("\n   Target NameSpace: " + this.m_targetURI);
        sb.append("\n   Status          : ");
        switch (this.m_status) {
            case -1: {
                sb.append("UNKNOWN");
                break;
            }
            case 0: {
                sb.append("ENABLED");
                break;
            }
            case 1: {
                sb.append("DISABLED");
                break;
            }
            case 2: {
                sb.append("DAMAGED");
                break;
            }
        }
        return sb.toString();
    }
}
