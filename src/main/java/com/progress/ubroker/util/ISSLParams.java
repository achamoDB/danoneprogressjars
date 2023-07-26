// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public interface ISSLParams
{
    public static final String CLIENT_AUTH = "psc.ssl.auth.client";
    public static final String CLIENT_AUTH_OFF = "none";
    public static final String CLIENT_AUTH_REQUESTED = "requested";
    public static final String CLIENT_AUTH_REQUIRED = "required";
    public static final String SERVER_AUTH = "psc.ssl.auth.client";
    public static final String SERVER_AUTH_DEFAULT = "default";
    public static final String SERVER_AUTH_DOMAIN = "domain";
    public static final String SSL_VERSIONS = "psc.ssl.versions";
    public static final String SSL_VERSION_SSLV2 = "sslv2";
    public static final String SSL_VERSION_SSLV3 = "sslv3";
    public static final String SSL_VERSION_TLSV1 = "tlsv1";
    public static final String SSL_VERSION_ALL = "sslv2,sslv3,tlsv1";
    public static final String SSL_COMPRESSION = "psc.ssl.compression";
    public static final String SSL_COMPRESSION_NONE = "none";
    public static final String SSL_SESSION_CACHE_SIZE = "psc.ssl.cache.size";
    public static final int SSL_SESSION_CACHE_SIZE_DEFAULT = 256;
    public static final String SSL_SESSION_CACHE_TIMEOUT = "psc.ssl.cache.timeout";
    public static final int SSL_SESSION_CACHE_TIMEOUT_DEFAULT = 120000;
    public static final String SSL_BUFFERED_OUTPUT = "psc.ssl.bufferedoutput";
    public static final String SSL_BUFFERED_OUTPUT_ON = "true";
    public static final String SSL_BUFFERED_OUTPUT_OFF = "false";
    public static final String SSL_MAX_INPUT_BUFFER = "psc.ssl.inputbuffer";
    public static final int SSL_DEF_INPUT_BUFFER = 32768;
    public static final String SSL_CIPHER = "psc.ssl.cipher.";
    public static final String SSL_CIPHER_NULL_WITH_NULL_NULL = "Null_With_Null_Null";
    public static final String SSL_CIPHER_RSA_WITH_RC4 = "RSA_With_RC4";
    public static final String SSL_CIPHER_RSA_WITH_NULL_MD5 = "RSA_With_Null_MD5";
    public static final String SSL_CIPHER_RSA_WITH_NULL_SHA = "RSA_With_Null_SHA";
    public static final String SSL_CIPHER_RSA_WITH_RC4_MD5 = "RSA_With_RC4_MD5";
    public static final String SSL_CIPHER_RSA_WITH_RC4_SHA = "RSA_With_RC4_SHA";
    public static final String SSL_CIPHER_RSA_WITH_RC2_CBC_MD5 = "RSA_With_RC2_CBC_MD5";
    public static final String SSL_CIPHER_RSA_WITH_3DES_EDE_CBC_MD5 = "RSA_With_3DES_EDE_CBC_MD5";
    public static final String SSL_CIPHER_RSA_WITH_3DES_EDE_CBC_SHA = "RSA_With_3DES_EDE_CBC_SHA";
    public static final String SSL_CIPHER_RSA_WITH_DES_CBC_MD5 = "RSA_With_DES_CBC_MD5";
    public static final String SSL_CIPHER_RSA_WITH_DES_CBC_SHA = "RSA_With_DES_CBC_SHA";
    public static final String SSL_CIPHER_RSA_EXPORT_WITH_DES_40_CBC_SHA = "RSA_Export_With_DES_40_CBC_SHA";
    public static final String SSL_CIPHER_RSA_EXPORT_WITH_RC4_40_MD5 = "RSA_Export_With_RC4_40_MD5";
    public static final String SSL_CIPHER_RSA_EXPORT_WITH_RC2_40_CBC_MD5 = "RSA_Export_With_RC2_40_CBC_MD5";
    public static final String SSL_CIPHER_DHE_RSA_WITH_3DES_EDE_CBC_SHA = "DHE_RSA_With_3DES_EDE_CBC_SHA";
    public static final String SSL_CIPHER_DHE_RSA_WITH_DES_CBC_SHA = "DHE_RSA_With_DES_CBC_SHA";
    public static final String SSL_CIPHER_DHE_RSA_EXPORT_WITH_DES_40_CBC_SHA = "DHE_RSA_Export_With_DES_40_CBC_SHA";
    public static final String SSL_CIPHER_DHE_DSS_WITH_3DES_EDE_CBC_SHA = "DHE_DSS_With_3DES_EDE_CBC_SHA";
    public static final String SSL_CIPHER_DHE_DSS_WITH_DES_CBC_SHA = "DHE_DSS_With_DES_CBC_SHA";
    public static final String SSL_CIPHER_DHE_DSS_EXPORT_WITH_DES_40_CBC_SHA = "DHE_DSS_Export_With_DES_40_CBC_SHA";
    public static final String SSL_CIPHER_DH_RSA_WITH_3DES_EDE_CBC_SHA = "DH_RSA_With_3DES_EDE_CBC_SHA";
    public static final String SSL_CIPHER_DH_RSA_WITH_DES_CBC_SHA = "DH_RSA_With_DES_CBC_SHA";
    public static final String SSL_CIPHER_DH_RSA_EXPORT_WITH_DES_40_CBC_SHA = "DH_RSA_Export_With_DES_40_CBC_SHA";
    public static final String SSL_CIPHER_DH_DSS_WITH_3DES_EDE_CBC_SHA = "DH_DSS_With_3DES_EDE_CBC_SHA";
    public static final String SSL_CIPHER_DH_DSS_WITH_DES_CBC_SHA = "DH_DSS_With_DES_CBC_SHA";
    public static final String SSL_CIPHER_DH_DSS_EXPORT_WITH_DES_40_CBC_SHA = "DH_DSS_Export_With_DES_40_CBC_SHA";
    public static final String SSL_CIPHER_DH_ANON_WITH_RC4_MD5 = "DH_Anon_With_RC4_MD5";
    public static final String SSL_CIPHER_DH_ANON_WITH_3DES_EDE_CBC_SHA = "DH_Anon_With_3DES_EDE_CBC_SHA";
    public static final String SSL_CIPHER_DH_ANON_WITH_DES_CBC_SHA = "DH_Anon_With_DES_CBC_SHA";
    public static final String SSL_CIPHER_DH_ANON_EXPORT_WITH_RC4_40_MD5 = "DH_Anon_Export_With_RC4_40_MD5";
    public static final String SSL_CIPHER_DH_ANON_EXPORT_WITH_DES_40_CBC_SHA = "DH_Anon_Export_With_DES_40_CBC_SHA";
    public static final int SSL_CIPHER_MAX_SUITES = 30;
    public static final int DEFAULT_SSL_SESSION_CACHE_TIMEOUT = 12000;
    
    void setProtocolName(final String p0);
    
    String getProtocolName();
    
    void setServerDomainAuthentication(final String p0);
    
    String getServerDomainAuthentication();
    
    void init(final Properties p0, final OutputStream p1, final int p2) throws Exception;
    
    Object getVendorParams();
    
    int loadAuthenticationCertificates(final String p0) throws IOException;
    
    void loadPrivateIdentify(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5) throws IOException;
    
    void unloadPrivateIdentity();
    
    void setSSLVersions(final String p0) throws IOException;
    
    String getSSLVersions();
    
    void setSessionIDCacheTime(final long p0) throws IOException;
    
    long getSessionIDCacheTime();
    
    void setSessionIDCacheSize(final int p0) throws IOException;
    
    int getSessionIDCacheSize();
    
    void purgeSessionIdCache() throws IOException;
    
    void setBufferedOutput(final boolean p0);
    
    boolean getBufferedOutput();
    
    void setCompressionTypes(final String p0) throws IOException;
    
    String getCompressionTypes();
    
    String[] getCiphers() throws IOException;
    
    void setCiphers(final String[] p0) throws IOException;
    
    String[] getSupportedCipherSuites();
    
    void setClientAuth(final String p0) throws IOException;
    
    String getClientAuth();
    
    void setServerAuth(final String p0) throws IOException;
    
    String getServerAuth();
    
    void setMaxInputBufferSize(final int p0) throws IOException;
    
    int getMaxInputBufferSize();
}
