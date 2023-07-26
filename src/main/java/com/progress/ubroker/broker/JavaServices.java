// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.Properties;
import com.progress.javafrom4gl.Log;
import com.progress.javafrom4gl.implementation.JServicesLogger;
import com.progress.ubroker.util.ubProperties;
import com.progress.javafrom4gl.implementation.ServiceImpl;

public class JavaServices
{
    static ServiceImpl service;
    
    static void createServices(final ubProperties ubProperties, final String s) throws Exception {
        if (ubProperties.serverType != 4 && ubProperties.serverType != 6 && ubProperties.serverType != 7) {
            JavaServices.service = null;
            return;
        }
        ubProperties.putValueAsInt("maxClientInstance", ubProperties.getValueAsInt("maxClientInstance") * 2 + 1);
        ubProperties.putValueAsInt("defaultService", ubProperties.serverMode = 0);
        System.err.println("Starts the Adapter broker.");
        final JServicesLogger servicesLogger = new JServicesLogger(ubProperties.getValueAsString("srvrLogFile"), ubProperties.getValueAsInt("srvrLoggingLevel"), ubProperties.getValueAsInt("srvrLogAppend") == 1);
        servicesLogger.LogMsgln(1, true, "", "Starts Adapter server logging.");
        servicesLogger.LogMsgln(1, true, "", "LoggingLevel set to = " + ubProperties.getValueAsInt("srvrLoggingLevel"));
        JavaServices.service = new ServiceImpl(s, "adapter", "com.progress.javafrom4gl.services.jms.jms", stringToProperties(ubProperties.getValueAsString("srvrStartupParam"), ';', servicesLogger), servicesLogger, ubProperties);
    }
    
    static void shutDown() {
        if (JavaServices.service != null) {
            JavaServices.service.shutDown();
        }
    }
    
    public static void setLoggingLevel(final int loggingLevel) {
        if (JavaServices.service != null) {
            JavaServices.service.setLoggingLevel(loggingLevel);
        }
    }
    
    private static Properties stringToProperties(final String s, final char oldChar, final Log log) {
        try {
            final Properties properties = new Properties();
            if (s == null || s.length() == 0) {
                return properties;
            }
            final ByteArrayInputStream in = new ByteArrayInputStream(s.replace(oldChar, '\n').getBytes());
            properties.load(new BufferedInputStream(in));
            in.close();
            return properties;
        }
        catch (Throwable t) {
            log.LogStackTrace(1, true, "", t);
            return null;
        }
    }
}
