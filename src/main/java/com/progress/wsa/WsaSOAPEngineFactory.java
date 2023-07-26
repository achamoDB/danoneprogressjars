// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.util.HashMap;

public class WsaSOAPEngineFactory
{
    public static final String ENGINE_TYPE_APPSERVER = "AppServer";
    public static final String ENGINE_TYPE_ADAPTER = "Adapter";
    public static final String ENGINE_TYPE_CONTAINER = "Container";
    private static HashMap engines;
    
    public static WsaSOAPEngine getInstance(String s) throws WsaSOAPEngineException {
        if (null == s) {
            s = "Adapter";
        }
        if (0 == s.length()) {
            s = "Adapter";
        }
        if (!WsaSOAPEngineFactory.engines.containsKey(s)) {
            throw new WsaSOAPEngineException("Invalid WsaSOAPEngine type specified: %s", new Object[] { s });
        }
        final WsaSOAPEngine wsaSOAPEngine = WsaSOAPEngineFactory.engines.get(s);
        wsaSOAPEngine.incRefCount();
        return wsaSOAPEngine;
    }
    
    static {
        WsaSOAPEngineFactory.engines = new HashMap();
        try {
            WsaSOAPEngineFactory.engines.put("AppServer", new ApacheSOAPEngine("AppServer"));
            WsaSOAPEngineFactory.engines.put("Adapter", new ApacheSOAPEngine("Adapter"));
            WsaSOAPEngineFactory.engines.put("Container", new ApacheSOAPEngine("Container"));
        }
        catch (Exception ex) {
            System.err.println("Error creating SOAP engines.");
        }
    }
}
