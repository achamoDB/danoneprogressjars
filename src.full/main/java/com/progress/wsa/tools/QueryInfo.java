// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import java.util.Enumeration;
import java.util.List;
import java.util.Collections;
import java.util.Vector;
import java.util.Hashtable;
import java.io.Serializable;

public class QueryInfo implements Serializable
{
    public static final int STYLE_UNKNOWN = 0;
    public static final int STYLE_RPC = 1;
    public static final int STYLE_DOC = 2;
    public static final String STYLE_UNKNOWN_STR = "Unknown";
    public static final String STYLE_RPC_STR = "RPC";
    public static final String STYLE_DOC_STR = "Document";
    public static final int ENCODING_UNKNOWN = 0;
    public static final int ENCODING_SOAP = 1;
    public static final int ENCODING_LITERAL = 2;
    public static final String ENCODING_UNKNOWN_STR = "Unknown";
    public static final String ENCODING_SOAP_STR = "Encoded";
    public static final String ENCODING_LITERAL_STR = "Literal";
    public static final String SESSION_MANAGED_STR = "Managed";
    public static final String SESSION_FREE_STR = "Free";
    protected ListInfo m_info;
    protected String m_appServerInfo;
    protected boolean m_sessionFree;
    protected int m_style;
    protected int m_encoding;
    protected Hashtable m_appProps;
    
    public QueryInfo() {
        this.m_info = null;
        this.m_appServerInfo = null;
        this.m_sessionFree = false;
        this.m_style = 0;
        this.m_encoding = 0;
        this.m_appProps = null;
    }
    
    public ListInfo getListInfo() {
        return this.m_info;
    }
    
    public void setListInfo(final ListInfo info) {
        this.m_info = info;
    }
    
    public String getAppServerInfo() {
        return this.m_appServerInfo;
    }
    
    public void setAppServerInfo(final String appServerInfo) {
        this.m_appServerInfo = appServerInfo;
    }
    
    public boolean isSessionFree() {
        return this.m_sessionFree;
    }
    
    public void setSessionFree(final boolean sessionFree) {
        this.m_sessionFree = sessionFree;
    }
    
    public int getStyle() {
        return this.m_style;
    }
    
    public void setStyle(final int style) {
        this.m_style = style;
    }
    
    public int getEncoding() {
        return this.m_encoding;
    }
    
    public void setEncoding(final int encoding) {
        this.m_encoding = encoding;
    }
    
    public Hashtable getAppProps() {
        return this.m_appProps;
    }
    
    public void setAppProps(final Hashtable appProps) {
        this.m_appProps = appProps;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.m_info);
        sb.append("\n   AppServerInfo   : " + this.m_appServerInfo);
        sb.append("\n   Session Model   : ");
        sb.append(this.m_sessionFree ? "Free" : "Managed");
        sb.append("\n   Style/Use       : ");
        switch (this.m_style) {
            case 0: {
                sb.append("Unknown");
                break;
            }
            case 1: {
                sb.append("RPC");
                break;
            }
            case 2: {
                sb.append("Document");
                break;
            }
        }
        sb.append("/");
        switch (this.m_encoding) {
            case 0: {
                sb.append("Unknown");
                break;
            }
            case 1: {
                sb.append("Encoded");
                break;
            }
            case 2: {
                sb.append("Literal");
                break;
            }
        }
        sb.append("\n" + this.getAppPropsString());
        return sb.toString();
    }
    
    private String getAppPropsString() {
        final StringBuffer sb = new StringBuffer();
        int length = 0;
        sb.append("\nApplication properties:");
        final Enumeration<String> keys = (Enumeration<String>)this.m_appProps.keys();
        final Vector<Comparable> list = new Vector<Comparable>(this.m_appProps.size());
        while (keys.hasMoreElements()) {
            final String obj = keys.nextElement();
            if (obj.length() > length) {
                length = obj.length();
            }
            list.addElement(obj);
        }
        Collections.sort(list);
        for (int i = 0; i < list.size(); ++i) {
            final String s = list.elementAt(i);
            final Object value = this.m_appProps.get(s);
            sb.append("\n   " + s);
            for (int j = 0; j < length - s.length(); ++j) {
                sb.append(" ");
            }
            sb.append(": " + value);
        }
        return sb.toString();
    }
}
