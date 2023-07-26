// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.util.HashMap;
import java.io.IOException;
import java.util.Properties;
import java.io.OutputStream;
import com.progress.ubroker.util.ubProperties;
import java.io.PrintStream;

public class ClientParams extends SSLParamsFull
{
    private static ClientParamsMap a;
    private PrintStream b;
    private int c;
    
    public ClientParams() {
        this.b = null;
        this.c = 0;
    }
    
    public void init(final ubProperties ubProperties, final OutputStream outputStream, final int n) throws IOException {
        final Properties properties = new Properties();
        properties.setProperty("PROGRESS.Session.certificateStore", ubProperties.getValueAsString("certStorePath"));
        this.init(properties, outputStream, n);
    }
    
    public void init(final Properties properties, final OutputStream outputStream, final int c) throws IOException {
        super.init(properties, outputStream, c);
        if (outputStream instanceof PrintStream) {
            this.b = (PrintStream)outputStream;
        }
        this.c = c;
        this.setSSLVersions("sslv3,tlsv1");
        this.a(properties);
        try {
            this.b();
        }
        catch (IOException cause) {
            final InvalidCertificateException ex = new InvalidCertificateException(cause.getMessage());
            ex.initCause(cause);
            throw ex;
        }
    }
    
    protected void a(final Properties properties) throws IOException {
        if (properties == null) {
            return;
        }
        this.setCertStorePath(properties.getProperty("PROGRESS.Session.certificateStore"));
        this.setReusingSessions(!this.b(properties));
    }
    
    protected void b() throws IOException {
        if (this.loadAuthenticationCertificates(this.c()) == 0) {
            throw new IOException("No certificate found");
        }
    }
    
    protected String c() {
        final StringBuffer sb = new StringBuffer();
        sb.append("-i ");
        if (this.c > 0) {
            sb.append("-d ");
        }
        sb.append(this.getCertStorePath());
        if (this.b != null) {
            this.b.println("Using load command: " + sb.toString());
        }
        return sb.toString();
    }
    
    private boolean b(final Properties properties) {
        return "1".equals(properties.getProperty("PROGRESS.Session.noSslSessionReuse", "0"));
    }
    
    public static ClientParams getInstance(final String s, final Properties properties, final PrintStream printStream) throws IOException {
        return ClientParams.a.contains(s) ? ClientParams.a.get(s) : a(s, properties, printStream);
    }
    
    private static ClientParams a(final String s, final Properties properties, final PrintStream debugStream) throws IOException {
        final ClientParams clientParams = new ClientParams();
        clientParams.init(properties, debugStream, 6);
        SessionCache.getInstance().setDebugStream(debugStream);
        ClientParams.a.put(s, clientParams);
        return clientParams;
    }
    
    public static synchronized void release(final String s) {
        ClientParams.a.remove(s);
    }
    
    static {
        ClientParams.a = new ClientParamsMap();
    }
    
    private static class ClientParamsMap
    {
        private HashMap a;
        
        private ClientParamsMap() {
            this.a = new HashMap();
        }
        
        public boolean contains(final String key) {
            return this.a.containsKey(key);
        }
        
        public void put(final String key, final ClientParams clientParams) {
            final MapEntry value = new MapEntry(key, clientParams);
            this.a(value, "PARAMS Adding entry for: ");
            this.a.put(key, value);
        }
        
        public ClientParams get(final String key) {
            if (!this.contains(key)) {
                return null;
            }
            final MapEntry mapEntry = this.a.get(key);
            mapEntry.addReference();
            this.a(mapEntry, "PARAMS Getting entry for: ");
            return mapEntry.getParams();
        }
        
        public void remove(final String s) {
            if (!this.contains(s)) {
                return;
            }
            final MapEntry mapEntry = this.a.get(s);
            if (mapEntry.removeReference() <= 0L) {
                this.a(mapEntry, "PARAMS Removing entry for: ");
                this.a.remove(s);
            }
        }
        
        private void a(final MapEntry mapEntry, final String str) {
            final ClientParams params = mapEntry.getParams();
            final StringBuffer sb = new StringBuffer(str);
            sb.append(mapEntry.getKey().replace('{', '('));
            sb.append(" count=");
            sb.append(mapEntry.getReferenceCount());
            if (params.b != null) {
                params.b.println(sb.toString());
            }
        }
    }
    
    private static class MapEntry
    {
        private long a;
        private ClientParams b;
        private String c;
        
        public MapEntry(final String c, final ClientParams b) {
            this.c = c;
            this.b = b;
            this.a = 1L;
        }
        
        public long addReference() {
            return ++this.a;
        }
        
        public long removeReference() {
            return --this.a;
        }
        
        public long getReferenceCount() {
            return this.a;
        }
        
        public String getKey() {
            return this.c;
        }
        
        public ClientParams getParams() {
            return this.b;
        }
    }
}
