// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Properties;
import java.io.OutputStream;

public class ServerParams extends SSLParamsFull
{
    private static final String a = ".pem";
    private OutputStream b;
    
    public ServerParams() {
        this.b = null;
    }
    
    public void init(final Properties properties, final OutputStream b, final int n) throws IOException {
        super.init(properties, b, n);
        this.b = b;
        this.setSSLVersions("sslv3,tlsv1");
        this.b();
    }
    
    protected void b() throws IOException {
        final RSAKeyEntry rsaKeyEntry = new RSAKeyEntry();
        rsaKeyEntry.setDebugWriter(new PrintWriter(this.b, true));
        rsaKeyEntry.loadKeyEntry(this.c());
        rsaKeyEntry.addCertificate(super.m_vendorParams, this.getKeyStoreEntryPassword());
    }
    
    protected String c() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getKeyStorePath());
        sb.append(System.getProperty("file.separator"));
        sb.append(this.getKeyStoreEntryAlias());
        sb.append(".pem");
        return sb.toString();
    }
    
    protected void a() throws IOException {
        this.setAlgorithms("RC4-MD5:RC4-SHA");
    }
}
