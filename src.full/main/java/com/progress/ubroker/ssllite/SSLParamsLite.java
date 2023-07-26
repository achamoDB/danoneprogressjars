// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssllite;

import java.util.Vector;
import com.progress.ubroker.util.NetworkProtocolOptions;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Properties;
import java.io.OutputStream;
import java.util.Hashtable;
import com.progress.ubroker.util.ISSLParams;

public class SSLParamsLite implements ISSLParams
{
    public pscl_b m_vendorParams;
    public pscl_j[] m_x509Certificates;
    private String a;
    private String b;
    private String c;
    private static Hashtable d;
    private static final String[] e;
    private OutputStream f;
    
    public SSLParamsLite() {
        this.m_vendorParams = null;
        this.m_x509Certificates = null;
        this.a = "SSL";
        this.b = "default";
        this.c = null;
        this.f = null;
    }
    
    public SSLParamsLite(final String a) {
        this.m_vendorParams = null;
        this.m_x509Certificates = null;
        this.a = "SSL";
        this.b = "default";
        this.c = null;
        this.f = null;
        if (null != a && 0 < a.length()) {
            this.a = a;
        }
    }
    
    public void setProtocolName(final String a) {
        this.a = a;
    }
    
    public String getProtocolName() {
        return this.a;
    }
    
    public void setServerDomainAuthentication(final String original) {
        if (null != original && 0 < original.length()) {
            this.c = new String(original);
        }
        else {
            this.c = null;
        }
    }
    
    public String getServerDomainAuthentication() {
        return this.c;
    }
    
    public Object getVendorParams() {
        return this.m_vendorParams;
    }
    
    public void init(final Properties properties, final OutputStream f, final int n) throws IOException {
        this.f = f;
        try {
            this.m_vendorParams = new pscl_b();
            this.a();
            if (null != properties) {
                this.a(properties);
            }
        }
        catch (IOException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new IOException("Error setting SSL parameters: " + ex2.toString());
        }
    }
    
    public int loadAuthenticationCertificates(final String str) throws IOException {
        int length = 0;
        final CertLoaderLite certLoaderLite = new CertLoaderLite();
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(str);
            final String[] array = new String[stringTokenizer.countTokens()];
            int n = 0;
            while (stringTokenizer.hasMoreTokens()) {
                array[n++] = stringTokenizer.nextToken();
            }
            if (null != this.f) {
                certLoaderLite.setLogStream(new PrintWriter(this.f, true));
            }
            certLoaderLite.load(array);
            this.m_x509Certificates = certLoaderLite.certificates();
        }
        catch (Exception ex) {
            throw new IOException("Error loading SSL certificates: " + ex.toString());
        }
        if (null != this.m_x509Certificates && 0 < this.m_x509Certificates.length) {
            length = this.m_x509Certificates.length;
            this.m_vendorParams.a(this.m_x509Certificates);
        }
        return length;
    }
    
    public void loadPrivateIdentify(final String s, final String s2, final String s3, final String s4, final String s5, final String s6) throws IOException {
        throw new IOException("SSL private certificates is not supported.");
    }
    
    public void unloadPrivateIdentity() {
    }
    
    public void setSSLVersions(final String str) throws IOException {
        if (null != str && 0 < str.length()) {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
            final int[] array = new int[stringTokenizer.countTokens()];
            int n = 0;
            while (stringTokenizer.hasMoreTokens()) {
                final String nextToken = stringTokenizer.nextToken();
                if ("sslv2" == nextToken) {
                    array[n++] = 2;
                }
                else if ("sslv3" == nextToken) {
                    array[n++] = 768;
                }
                else {
                    if ("tlsv1" != nextToken) {
                        throw new IOException("Invalid SSL protocol version:" + nextToken);
                    }
                    array[n++] = 769;
                }
                this.m_vendorParams.a(array);
            }
        }
    }
    
    public String getSSLVersions() {
        String string = null;
        if (null != this.m_vendorParams) {
            final StringBuffer sb = new StringBuffer();
            final int[] e = this.m_vendorParams.e();
            for (int i = 0; i < e.length; ++i) {
                switch (e[i]) {
                    case 2: {
                        sb.append("sslv2");
                        break;
                    }
                    case 768: {
                        sb.append("sslv3");
                        break;
                    }
                    case 769: {
                        sb.append("tlsv1");
                        break;
                    }
                }
                if (i < e.length - 1) {
                    sb.append(",");
                }
            }
            string = sb.toString();
        }
        return string;
    }
    
    public void setSessionIDCacheTime(final long n) throws IOException {
        if (null != this.m_vendorParams) {
            if (-1L > n) {
                throw new IOException("Bad SSL id cache value:" + n);
            }
            try {
                this.m_vendorParams.a(n);
            }
            catch (Exception ex) {
                throw new IOException("Bad SSL id cache value:" + n);
            }
        }
    }
    
    public long getSessionIDCacheTime() {
        return (null != this.m_vendorParams) ? this.m_vendorParams.d() : 0L;
    }
    
    public void setSessionIDCacheSize(final int n) throws IOException {
    }
    
    public int getSessionIDCacheSize() {
        return 256;
    }
    
    public void purgeSessionIdCache() throws IOException {
        if (null != this.m_vendorParams) {
            try {
                this.m_vendorParams.b(this.getSessionIDCacheTime());
            }
            catch (Exception ex) {
                throw new IOException("Error purging session id cache: " + ex.getMessage());
            }
        }
    }
    
    public void setBufferedOutput(final boolean b) {
        if (null != this.m_vendorParams) {
            this.m_vendorParams.b(b);
        }
    }
    
    public boolean getBufferedOutput() {
        return null != this.m_vendorParams && this.m_vendorParams.m();
    }
    
    public void setCompressionTypes(final String s) throws IOException {
        throw new IOException("SSL compression is not supported.");
    }
    
    public String getCompressionTypes() {
        return "none";
    }
    
    public String[] getCiphers() throws IOException {
        if (null == this.m_vendorParams) {
            return SSLParamsLite.e;
        }
        String[] array;
        try {
            final pscl_i[] a = this.m_vendorParams.a();
            array = new String[a.length];
            for (int i = 0; i < a.length; ++i) {
                array[i] = a[i].l();
            }
        }
        catch (Exception ex) {
            throw new IOException("Error obtains cipher suites: " + ex.toString());
        }
        return array;
    }
    
    public void setCiphers(final String[] array) throws IOException {
        if (null != this.m_vendorParams && null != array && 0 < array.length) {
            final pscl_i[] array2 = new pscl_i[array.length];
            for (int i = 0; i < array.length; ++i) {
                array2[i] = this.a(array[i]);
            }
            try {
                this.m_vendorParams.a(array2);
            }
            catch (Exception ex) {
                throw new IOException("Error SSL setting cipher suites: " + ex.getMessage());
            }
        }
    }
    
    public String[] getSupportedCipherSuites() {
        return SSLParamsLite.e;
    }
    
    public void setClientAuth(final String s) throws IOException {
        throw new IOException("SSL client authentication is not supported.");
    }
    
    public String getClientAuth() {
        return "none";
    }
    
    public void setServerAuth(final String s) throws IOException {
        if (s == "default" || s == "domain") {
            this.b = s;
            return;
        }
        throw new IOException("Invalid SSL server authentication type: " + s);
    }
    
    public String getServerAuth() {
        return this.b;
    }
    
    public void setMaxInputBufferSize(final int n) throws IOException {
        if (null != this.m_vendorParams) {
            int n2;
            if (16000 > (n2 = n)) {
                n2 = 16000;
            }
            if (65536 > n) {
                n2 = 65536;
            }
            try {
                this.m_vendorParams.a(n2);
            }
            catch (Exception ex) {
                throw new IOException("Error setting max input buffer size:" + ex.getMessage());
            }
        }
    }
    
    public int getMaxInputBufferSize() {
        return (null != this.m_vendorParams) ? this.m_vendorParams.f() : 32768;
    }
    
    private void a() throws IOException {
        if (null != this.m_vendorParams) {
            this.setSessionIDCacheTime(120000L);
            this.setMaxInputBufferSize(32768);
            final byte[] array = new byte[4];
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < 4; ++i) {
                array[i] = (byte)(currentTimeMillis & 0xFFL);
                currentTimeMillis >>= 8;
            }
            this.m_vendorParams.e(array);
            this.setCiphers(SSLParamsLite.e);
        }
    }
    
    private void a(final Properties properties) throws Exception {
        if (null != this.m_vendorParams && null != properties) {
            final String property = properties.getProperty("psc.ssl.auth.client");
            if (null != property) {
                this.setServerAuth(property);
            }
            final String property2 = properties.getProperty("psc.ssl.versions");
            if (null != property2) {
                this.setSSLVersions(property2);
            }
            final String property3 = properties.getProperty("psc.ssl.cache.size");
            if (null != property3) {
                this.setSessionIDCacheSize(NetworkProtocolOptions.intFromString(property3, this.getSessionIDCacheSize()));
            }
            final String property4 = properties.getProperty("psc.ssl.cache.timeout");
            if (null != property4) {
                this.setSessionIDCacheTime(NetworkProtocolOptions.longFromString(property4, this.getSessionIDCacheTime()));
            }
            final String property5 = properties.getProperty("psc.ssl.bufferedoutput");
            if (null != property5) {
                this.setBufferedOutput(NetworkProtocolOptions.booleanFromString(property5, this.getBufferedOutput()));
            }
            final String property6 = properties.getProperty("psc.ssl.inputbuffer");
            if (null != property6) {
                this.setMaxInputBufferSize(NetworkProtocolOptions.intFromString(property6, this.getMaxInputBufferSize()));
            }
            final Vector vector = new Vector<String>();
            int n = 1;
            while (true) {
                final String property7 = properties.getProperty("psc.ssl.cipher." + Integer.toString(n++));
                if (null == property7) {
                    break;
                }
                vector.add(property7);
            }
            if (!vector.isEmpty()) {
                this.setCiphers(vector.toArray(new String[1]));
            }
        }
    }
    
    private pscl_i a(final String s) throws IOException {
        final pscl_i pscl_i = SSLParamsLite.d.get(s);
        if (null == pscl_i) {
            throw new IOException("Unsupported SSL cipher suite name:" + s);
        }
        return pscl_i;
    }
    
    static {
        SSLParamsLite.d = new Hashtable();
        e = new String[] { "RSA_Export_With_RC4_40_MD5", "RSA_With_RC4_MD5", "RSA_With_RC4_SHA" };
        SSLParamsLite.d.put("RSA_With_RC4_MD5", new pscl_ba());
        SSLParamsLite.d.put("RSA_With_RC4_SHA", new pscl_be());
        SSLParamsLite.d.put("RSA_Export_With_RC4_40_MD5", new pscl_bf());
    }
}
