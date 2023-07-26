// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.ubroker.util.ubProperties;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Hashtable;
import com.progress.javafrom4gl.JavaService;

public class ServiceImpl
{
    static final String NEW_LINE;
    private JavaService javaService;
    private String jvmSessionId;
    private Hashtable servletList;
    private JServicesLogger logger;
    
    public String toString() {
        String s = ServiceImpl.NEW_LINE + "*** Adapter status: " + ": ***" + ServiceImpl.NEW_LINE + "Client list:" + ServiceImpl.NEW_LINE;
        final Enumeration<JavaServlet> elements = this.servletList.elements();
        while (elements.hasMoreElements()) {
            final JavaServlet javaServlet = elements.nextElement();
            if (javaServlet != null) {
                s += javaServlet.toString();
            }
        }
        return s + "End client list";
    }
    
    public ServiceImpl(final String s, final String s2, final String s3, final Properties properties, final JServicesLogger servicesLogger, final ubProperties brokerProperties) throws Exception {
        Class<?> forName;
        try {
            forName = Class.forName(s3);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            servicesLogger.LogMsgln(2, true, "", s3 + " or SonicMQ client not found.");
            throw noClassDefFoundError;
        }
        this.logger = servicesLogger;
        ConnectionContextImpl.javaServiceLog = servicesLogger;
        ConnectionContextImpl.brokerProperties = brokerProperties;
        (this.javaService = (JavaService)forName.newInstance())._startup(properties);
        if (ObjectTable.objectTable == null) {
            new ObjectTable(s);
        }
        this.servletList = new Hashtable();
    }
    
    public JavaServlet createConnectionServlet(final String key, final String s, final String s2, final String s3, final StopInterface stopInterface, final String s4, final String s5) throws Exception {
        final JavaServlet value = new JavaServlet(this, key, s, s2, s3, stopInterface, s4, s5);
        this.servletList.put(key, value);
        return value;
    }
    
    public JavaServlet getServletByID(final String key) {
        return this.servletList.get(key);
    }
    
    public void removeServletByID(final String key) {
        this.servletList.remove(key);
    }
    
    public void shutDown() {
        this.javaService._shutdown();
        if (this.logger != null) {
            this.logger.CloseLogfile();
            this.logger = null;
        }
    }
    
    JavaService getJavaService() {
        return this.javaService;
    }
    
    String getJvmId() {
        return this.jvmSessionId;
    }
    
    public void setLoggingLevel(final int loggingLevel) {
        if (this.logger != null) {
            this.logger.setLoggingLevel(loggingLevel);
        }
    }
    
    static {
        NEW_LINE = new String(System.getProperty("line.separator"));
    }
}
