// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import com.progress.ubroker.util.ubProperties;
import java.util.Properties;

public class ServerProperties extends Properties
{
    public ServerProperties(final ubProperties ubProperties) {
        this.a(ubProperties);
    }
    
    private void a(final ubProperties ubProperties) {
        this.a("psc.ssl.certstorepath", ubProperties.getValueAsString("certStorePath"));
        this.a("psc.ssl.keyalias", ubProperties.getValueAsString("keyAlias"));
        this.a("psc.ssl.keyaliaspasswd", ubProperties.getValueAsString("keyAliasPasswd"));
        this.a("psc.ssl.keystorepath", ubProperties.getValueAsString("keyStorePath"));
        this.a("psc.ssl.keystorepasswd", ubProperties.getValueAsString("keyStorePasswd"));
        this.a("psc.ssl.cache.enable", !ubProperties.getValueAsBoolean("noSessionCache"));
        this.a("psc.ssl.cache.timeout", ubProperties.getValueAsInt("sessionTimeout") * 1000);
        this.a("psc.ssl.algorithms", ubProperties.getValueAsString("sslAlgorithms"));
    }
    
    private void a(final String key, final String value) {
        if (value == null) {
            return;
        }
        if (value.length() == 0) {
            return;
        }
        this.setProperty(key, value);
    }
    
    private void a(final String key, final boolean b) {
        this.setProperty(key, b ? "true" : "false");
    }
    
    private void a(final String key, final int i) {
        if (i < 0) {
            return;
        }
        this.setProperty(key, Integer.toString(i));
    }
}
