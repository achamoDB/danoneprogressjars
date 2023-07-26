// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.util.Vector;
import com.progress.ubroker.util.NetworkProtocolOptions;
import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Hashtable;
import java.io.PrintStream;
import java.io.OutputStream;
import com.progress.common.util.InstallPath;
import com.progress.ubroker.util.ISSLParams;

public class SSLParamsFull implements ISSLParams
{
    public static final String SSL_SESSION_CACHE_ENABLE = "psc.ssl.cache.enable";
    public static final boolean SSL_SESSION_CACHE_DEFAULT = true;
    public static final String SSL_CERTSTORE_PATH = "psc.ssl.certstorepath";
    public static final String SSL_CERTSTORE_PATH_DEFAULT = "certs";
    public static final String SSL_KEYSTORE_PATH = "psc.ssl.keystorepath";
    public static final String SSL_KEYSTORE_PATH_DEFAULT = "keys";
    public static final String SSL_KEYSTORE_PASSWORD = "psc.ssl.keystorepasswd";
    public static final String SSL_NO_SESSION_REUSE = "psc.ssl.nosessionreuse";
    public static final String SSL_KEYALIAS = "psc.ssl.keyalias";
    public static final String SSL_KEYALIAS_DEFAULT = "default_server";
    public static final String SSL_KEYALIAS_PASSWORD = "psc.ssl.keyaliaspasswd";
    public static final String SSL_KEYALIAS_PASSWORD_DEFAULT = "20333c34252a2137";
    public static final String SSL_ALGORITHMS = "psc.ssl.algorithms";
    public psc_c2 m_vendorParams;
    public psc_e[] m_x509Certificates;
    private String a;
    private String b;
    private String c;
    private boolean d;
    private long e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private AlgorithmList k;
    private InstallPath l;
    protected OutputStream m;
    protected PrintStream n;
    private static Hashtable o;
    private static final String[] p;
    
    public SSLParamsFull() {
        this.m_vendorParams = null;
        this.m_x509Certificates = null;
        this.a = "SSL";
        this.b = "default";
        this.c = null;
        this.d = true;
        this.e = 120000L;
        this.i = "default_server";
        this.j = "20333c34252a2137";
        this.k = null;
        this.l = new InstallPath();
        this.m = null;
        this.n = null;
    }
    
    public SSLParamsFull(final String a) {
        this.m_vendorParams = null;
        this.m_x509Certificates = null;
        this.a = "SSL";
        this.b = "default";
        this.c = null;
        this.d = true;
        this.e = 120000L;
        this.i = "default_server";
        this.j = "20333c34252a2137";
        this.k = null;
        this.l = new InstallPath();
        this.m = null;
        this.n = null;
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
    
    public void init(final Properties properties, final OutputStream outputStream, final int n) throws IOException {
        this.m = outputStream;
        if (outputStream != null) {
            if (outputStream instanceof PrintStream) {
                this.n = (PrintStream)outputStream;
            }
            else {
                this.n = new PrintStream(outputStream, true);
            }
        }
        try {
            this.m_vendorParams = new psc_c2();
            if (outputStream != null) {
                this.m_vendorParams.a(outputStream);
                if (n > 0) {
                    this.m_vendorParams.c(5);
                }
            }
            this.b();
            if (null != properties) {
                this.a(properties);
            }
        }
        catch (Exception ex) {
            throw new IOException("Error setting SSL parameters: " + ex.toString());
        }
    }
    
    public void init(final Properties properties, final PrintWriter printWriter, final int n) throws IOException {
        this.init(properties, (OutputStream)null, n);
    }
    
    public int loadAuthenticationCertificates(final String str) throws IOException {
        int length = 0;
        final CertLoaderFull certLoaderFull = new CertLoaderFull();
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(str);
            final String[] array = new String[stringTokenizer.countTokens()];
            int n = 0;
            while (stringTokenizer.hasMoreTokens()) {
                array[n++] = stringTokenizer.nextToken();
            }
            if (null != this.m) {
                certLoaderFull.setLogStream(new PrintWriter(this.m, true));
            }
            certLoaderFull.load(array);
            this.m_x509Certificates = certLoaderFull.certificates();
        }
        catch (Exception ex) {
            throw new IOException("Error loading SSL certificates: " + ex.toString());
        }
        if (null != this.m_x509Certificates && 0 < this.m_x509Certificates.length) {
            length = this.m_x509Certificates.length;
            this.m_vendorParams.b(this.m_x509Certificates);
        }
        return length;
    }
    
    public int loadAuthenticationCertificates(final String s, final PrintWriter printWriter) throws IOException {
        return this.loadAuthenticationCertificates(s);
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
                if ("sslv3".equals(nextToken)) {
                    array[n++] = 768;
                }
                else if ("tlsv1".equals(nextToken)) {
                    array[n++] = 769;
                }
                else {
                    if (!"sslv2".equals(nextToken)) {
                        throw new IOException("Invalid SSL protocol version:" + nextToken);
                    }
                    array[n++] = 2;
                }
            }
            this.m_vendorParams.a(array);
        }
    }
    
    public String getSSLVersions() {
        String string = null;
        if (null != this.m_vendorParams) {
            final StringBuffer sb = new StringBuffer();
            final int[] m = this.m_vendorParams.m();
            for (int i = 0; i < m.length; ++i) {
                switch (m[i]) {
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
                if (i < m.length - 1) {
                    sb.append(",");
                }
            }
            string = sb.toString();
        }
        return string;
    }
    
    public void setCachingSessions(final boolean d) throws IOException {
        this.d = d;
        this.setSessionIDCacheTime(this.e);
    }
    
    public boolean isCachingSessions() {
        return this.d;
    }
    
    public void setReusingSessions(final boolean cachingSessions) throws IOException {
        this.setCachingSessions(cachingSessions);
    }
    
    public boolean isReusingSessions() {
        return this.isCachingSessions();
    }
    
    public void setSessionIDCacheTime(final long lng) throws IOException {
        if (-1L > lng) {
            throw new IOException("Bad SSL id cache value:" + lng);
        }
        this.e = lng;
        if (null != this.m_vendorParams) {
            try {
                this.m_vendorParams.a(this.d ? lng : 0L);
            }
            catch (Exception ex) {
                throw new IOException("Bad SSL id cache value:" + lng);
            }
        }
    }
    
    public long getSessionIDCacheTime() {
        return this.e;
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
        return null != this.m_vendorParams && this.m_vendorParams.u();
    }
    
    public void setCompressionTypes(final String s) throws IOException {
        throw new IOException("SSL compression is not supported.");
    }
    
    public String getCompressionTypes() {
        return "none";
    }
    
    public String[] getCiphers() throws IOException {
        if (null == this.m_vendorParams) {
            return SSLParamsFull.p;
        }
        String[] array;
        try {
            final psc_dw[] c = this.m_vendorParams.c();
            array = new String[c.length];
            for (int i = 0; i < c.length; ++i) {
                array[i] = c[i].i();
            }
        }
        catch (Exception ex) {
            throw new IOException("Error obtains cipher suites: " + ex.toString());
        }
        return array;
    }
    
    public void setCiphers(final String[] array) throws IOException {
        if (null != this.m_vendorParams && null != array && 0 < array.length) {
            final psc_dw[] array2 = new psc_dw[array.length];
            for (int i = 0; i < array.length; ++i) {
                array2[i] = this.b(array[i]);
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
        return SSLParamsFull.p;
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
        return (null != this.m_vendorParams) ? this.m_vendorParams.n() : 32768;
    }
    
    public void setCertStorePath(final String f) {
        if (f != null && f.length() > 0) {
            this.f = f;
        }
    }
    
    public String getCertStorePath() {
        return this.f;
    }
    
    public void setKeyStorePath(final String g) {
        if (g.length() > 0) {
            this.g = g;
        }
    }
    
    public String getKeyStorePath() {
        return this.g;
    }
    
    public void setKeyStorePassword(final String h) {
        if (h.length() > 0) {
            this.h = h;
        }
    }
    
    public String getKeyStorePassword() {
        return this.h;
    }
    
    public void setKeyStoreEntryAlias(final String i) {
        if (i.length() > 0) {
            this.i = i;
        }
    }
    
    public String getKeyStoreEntryAlias() {
        return this.i;
    }
    
    public void setKeyStoreEntryPassword(final String j) {
        if (j.length() > 0) {
            this.j = j;
        }
    }
    
    public String getKeyStoreEntryPassword() {
        return this.j;
    }
    
    public void setAlgorithms(final String s) throws IOException {
        if (s.length() > 0) {
            this.k = new AlgorithmList(s);
            this.m_vendorParams.a(this.k.getCipherSuites());
        }
    }
    
    public String getAlgorithms() {
        if (this.k == null) {
            return "";
        }
        return this.k.toString();
    }
    
    public void cacheSession(final Object o) throws IOException {
        if (o == null) {
            return;
        }
        final psc_d2 psc_d2 = (psc_d2)o;
        this.n.println("Adding session to vendor cache: " + psc_d2.f());
        this.m_vendorParams.b(psc_d2);
    }
    
    public void removeSession(final String s) throws IOException {
        final psc_d2 b = this.m_vendorParams.b(s);
        if (b != null) {
            this.n.println("Removing session from vendor cache: " + b.f());
            this.m_vendorParams.a(b);
        }
    }
    
    private void b() throws IOException {
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
            this.a();
            this.setCertStorePath(this.a("certs"));
            this.setKeyStorePath(this.a("keys"));
        }
    }
    
    protected void a() throws IOException {
        this.setCiphers(SSLParamsFull.p);
    }
    
    private String a(final String str) {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.l.getInstallPath());
        sb.append(System.getProperty("file.separator"));
        sb.append(str);
        return sb.toString();
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
            final String property4 = properties.getProperty("psc.ssl.cache.enable");
            if (null != property4) {
                this.setCachingSessions(NetworkProtocolOptions.booleanFromString(property4, this.isCachingSessions()));
            }
            final String property5 = properties.getProperty("psc.ssl.cache.timeout");
            if (null != property5) {
                this.setSessionIDCacheTime(NetworkProtocolOptions.longFromString(property5, this.getSessionIDCacheTime()));
            }
            final String property6 = properties.getProperty("psc.ssl.bufferedoutput");
            if (null != property6) {
                this.setBufferedOutput(NetworkProtocolOptions.booleanFromString(property6, this.getBufferedOutput()));
            }
            final String property7 = properties.getProperty("psc.ssl.inputbuffer");
            if (null != property7) {
                this.setMaxInputBufferSize(NetworkProtocolOptions.intFromString(property7, this.getMaxInputBufferSize()));
            }
            final Vector vector = new Vector<String>();
            int n = 1;
            while (true) {
                final String property8 = properties.getProperty("psc.ssl.cipher." + Integer.toString(n++));
                if (null == property8) {
                    break;
                }
                vector.add(property8);
            }
            if (!vector.isEmpty()) {
                this.setCiphers(vector.toArray(new String[1]));
            }
            final String property9 = properties.getProperty("psc.ssl.keystorepath");
            if (null != property9) {
                this.setKeyStorePath(property9);
            }
            final String property10 = properties.getProperty("psc.ssl.certstorepath");
            if (null != property10) {
                this.setCertStorePath(property10);
            }
            final String property11 = properties.getProperty("psc.ssl.keystorepasswd");
            if (null != property11) {
                this.setKeyStorePassword(property11);
            }
            final String property12 = properties.getProperty("psc.ssl.keyalias");
            if (null != property12) {
                this.setKeyStoreEntryAlias(property12);
            }
            final String property13 = properties.getProperty("psc.ssl.keyaliaspasswd");
            if (null != property13) {
                this.setKeyStoreEntryPassword(property13);
            }
            final String property14 = properties.getProperty("psc.ssl.nosessionreuse");
            if (null != property14) {
                this.setReusingSessions(!NetworkProtocolOptions.booleanFromString(property14, !this.isReusingSessions()));
            }
            final String property15 = properties.getProperty("psc.ssl.algorithms");
            if (null != property15) {
                this.setAlgorithms(property15);
            }
        }
    }
    
    private psc_dw b(final String s) throws IOException {
        final psc_dw psc_dw = SSLParamsFull.o.get(s);
        if (null == psc_dw) {
            throw new IOException("Unsupported SSL cipher suite name:" + s);
        }
        return psc_dw;
    }
    
    static {
        SSLParamsFull.o = new Hashtable();
        p = new String[] { "RSA_With_Null_MD5", "RSA_With_Null_SHA", "RSA_With_RC4_MD5", "RSA_With_RC4_SHA", "RSA_With_RC2_CBC_MD5", "RSA_With_3DES_EDE_CBC_MD5", "RSA_With_3DES_EDE_CBC_SHA", "RSA_With_DES_CBC_MD5", "RSA_Export_With_DES_40_CBC_SHA", "RSA_Export_With_RC4_40_MD5", "RSA_Export_With_RC2_40_CBC_MD5", "DHE_RSA_With_3DES_EDE_CBC_SHA", "DHE_RSA_With_DES_CBC_SHA", "DHE_RSA_Export_With_DES_40_CBC_SHA", "DHE_DSS_With_3DES_EDE_CBC_SHA", "DHE_DSS_With_DES_CBC_SHA", "DHE_DSS_Export_With_DES_40_CBC_SHA", "DH_RSA_With_3DES_EDE_CBC_SHA", "DH_RSA_With_DES_CBC_SHA", "DH_RSA_Export_With_DES_40_CBC_SHA", "DH_DSS_With_3DES_EDE_CBC_SHA", "DH_DSS_With_DES_CBC_SHA", "DH_DSS_Export_With_DES_40_CBC_SHA", "DH_Anon_With_RC4_MD5", "DH_Anon_With_3DES_EDE_CBC_SHA", "DH_Anon_With_DES_CBC_SHA", "DH_Anon_Export_With_RC4_40_MD5", "DH_Anon_Export_With_DES_40_CBC_SHA" };
        SSLParamsFull.o.put("Null_With_Null_Null", new psc_g6());
        SSLParamsFull.o.put("RSA_With_Null_MD5", new psc_lz());
        SSLParamsFull.o.put("RSA_With_Null_SHA", new psc_l0());
        SSLParamsFull.o.put("RSA_With_RC4_MD5", new psc_du());
        SSLParamsFull.o.put("RSA_With_RC4_SHA", new psc_l1());
        SSLParamsFull.o.put("RSA_With_RC2_CBC_MD5", new psc_l2());
        SSLParamsFull.o.put("RSA_With_3DES_EDE_CBC_MD5", new psc_l3());
        SSLParamsFull.o.put("RSA_With_3DES_EDE_CBC_SHA", new psc_l4());
        SSLParamsFull.o.put("RSA_With_DES_CBC_MD5", new psc_l5());
        SSLParamsFull.o.put("RSA_With_DES_CBC_SHA", new psc_l6());
        SSLParamsFull.o.put("RSA_Export_With_DES_40_CBC_SHA", new psc_l7());
        SSLParamsFull.o.put("RSA_Export_With_RC4_40_MD5", new psc_l8());
        SSLParamsFull.o.put("RSA_Export_With_RC2_40_CBC_MD5", new psc_l9());
        SSLParamsFull.o.put("DHE_RSA_With_3DES_EDE_CBC_SHA", new psc_ma());
        SSLParamsFull.o.put("DHE_RSA_With_DES_CBC_SHA", new psc_mb());
        SSLParamsFull.o.put("DHE_RSA_Export_With_DES_40_CBC_SHA", new psc_mc());
        SSLParamsFull.o.put("DHE_DSS_With_3DES_EDE_CBC_SHA", new psc_md());
        SSLParamsFull.o.put("DHE_DSS_With_DES_CBC_SHA", new psc_me());
        SSLParamsFull.o.put("DHE_DSS_Export_With_DES_40_CBC_SHA", new psc_mf());
        SSLParamsFull.o.put("DH_RSA_With_3DES_EDE_CBC_SHA", new psc_mg());
        SSLParamsFull.o.put("DH_RSA_With_DES_CBC_SHA", new psc_mh());
        SSLParamsFull.o.put("DH_RSA_Export_With_DES_40_CBC_SHA", new psc_mi());
        SSLParamsFull.o.put("DH_DSS_With_3DES_EDE_CBC_SHA", new psc_mj());
        SSLParamsFull.o.put("DH_DSS_With_DES_CBC_SHA", new psc_mk());
        SSLParamsFull.o.put("DH_DSS_Export_With_DES_40_CBC_SHA", new psc_ml());
        SSLParamsFull.o.put("DH_Anon_With_RC4_MD5", new psc_mm());
        SSLParamsFull.o.put("DH_Anon_With_3DES_EDE_CBC_SHA", new psc_mn());
        SSLParamsFull.o.put("DH_Anon_With_DES_CBC_SHA", new psc_mo());
        SSLParamsFull.o.put("DH_Anon_Export_With_RC4_40_MD5", new psc_mp());
        SSLParamsFull.o.put("DH_Anon_Export_With_DES_40_CBC_SHA", new psc_mq());
        SSLParamsFull.o.put("RSA_With_DES_CBC_MD5", new psc_l5());
    }
}
