// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import java.util.Hashtable;

public class NSStatisticSchema implements INSStatisticConstants
{
    public static Hashtable getActNSStat() {
        final Hashtable<String, String[][]> hashtable = new Hashtable<String, String[][]>();
        hashtable.put("ActNSStat", new String[][] { { "ClientCxRequests", "0", "Client connection requests", "String", "Number", "", "", "" }, { "ClientCxFailures", "1", "Client connection failures", "String", "Number", "", "", "" }, { "AppServiceCount", "2", "AppService count", "String", "Number", "", "", "" }, { "BrokerCount", "3", "Broker count", "String", "Number", "", "", "" } });
        return hashtable;
    }
    
    public static Hashtable getActNSAppService() {
        final Hashtable<String, String[][]> hashtable = new Hashtable<String, String[][]>();
        hashtable.put("ActNSAppService", new String[][] { { "AppServiceName", "4", "AppService name", "String", "String", "", "", "" }, { "AppServiceCount", "5", "AppService count", "String", "Number", "", "", "" }, { "BrokerCount", "6", "Broker count", "String", "Number", "", "", "" } });
        return hashtable;
    }
    
    public static Hashtable getActNSBroker() {
        final Hashtable<String, String[][]> hashtable = new Hashtable<String, String[][]>();
        hashtable.put("ActNSBroker", new String[][] { { "ClientCxCount", "7", "Client connection count", "String", "Number", "", "", "" }, { "ClientASCxCount", "8", "Client AppService connection count", "String", "Number", "", "", "" }, { "UUID", "9", "UUID", "String", "String", "", "", "" }, { "Name", "10", "Broker name", "String", "String", "", "", "" }, { "Host", "11", "Broker host", "String", "String", "", "", "" }, { "Port", "12", "Broker port", "String", "Number", "", "", "" }, { "Weight", "13", "Broker weight", "String", "Number", "", "", "" }, { "Time", "14", "Broker timeout", "String", "Number", "", "", "" } });
        return hashtable;
    }
    
    public static Hashtable getSchema(final String s) {
        Hashtable hashtable = null;
        if (s != null) {
            if (s.equalsIgnoreCase("ActNSStat")) {
                hashtable = getActNSStat();
            }
            else if (s.equalsIgnoreCase("ActNSAppService")) {
                hashtable = getActNSAppService();
            }
            else if (s.equalsIgnoreCase("ActNSBroker")) {
                hashtable = getActNSBroker();
            }
        }
        return hashtable;
    }
}
