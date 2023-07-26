// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssllite;

import com.rsa.certj.cert.X509Certificate;
import com.rsa.ssl.CipherSuite;
import com.rsa.ssl.SSLSocket;
import com.rsa.ssl.SSLSession;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.Socket;
import com.progress.ubroker.util.ISSLParams;
import java.net.InetAddress;
import com.progress.ubroker.util.ISSLSocketUtils;

public class SSLSocketUtilsLite implements ISSLSocketUtils
{
    public static final char[] m_toHex;
    
    public Socket createSSLSocket(final InetAddress inetAddress, final int n, final ISSLParams isslParams) throws IOException, UnknownHostException {
        return new pscl_n(inetAddress, n, (pscl_b)isslParams.getVendorParams());
    }
    
    public Socket createSSLSocket(final Socket socket, final ISSLParams isslParams) throws IOException {
        return new pscl_n(socket, (pscl_b)isslParams.getVendorParams());
    }
    
    public Socket createSSLSocket(final Socket socket, final ISSLParams isslParams, final boolean b) {
        throw new UnsupportedOperationException("SSL server operations are not supported in this component version");
    }
    
    public Socket createSSLSocket(final String s, final int n, final ISSLParams isslParams) throws IOException {
        return new pscl_n(s, n, (pscl_b)isslParams.getVendorParams());
    }
    
    public ISSLInfo getSocketSSLInfo(final Socket socket) throws IOException {
        return new bp((pscl_n)socket);
    }
    
    static {
        m_toHex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
    
    public class bq implements IPeerCertificateInfo
    {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private byte[] f;
        
        public bq(final String a, final String b, final String c, final String d, final String e, final byte[] f) {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = null;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
        }
        
        public String getSerialNumber() {
            return this.a;
        }
        
        public String getSubjectName() {
            return this.b;
        }
        
        public String getIssuerName() {
            return this.c;
        }
        
        public String getFromDate() {
            return this.d;
        }
        
        public String getToDate() {
            return this.e;
        }
        
        public byte[] getSignature() {
            return this.f;
        }
    }
    
    public class PeerCertificateInfo implements IPeerCertificateInfo
    {
        private String m_serialNumber;
        private String m_subjectName;
        private String m_issuerName;
        private String m_fromDate;
        private String m_toDate;
        private byte[] m_signature;
        
        public PeerCertificateInfo(final String serialNumber, final String subjectName, final String issuerName, final String fromDate, final String toDate, final byte[] signature) {
            this.m_serialNumber = null;
            this.m_subjectName = null;
            this.m_issuerName = null;
            this.m_fromDate = null;
            this.m_toDate = null;
            this.m_signature = null;
            this.m_serialNumber = serialNumber;
            this.m_subjectName = subjectName;
            this.m_issuerName = issuerName;
            this.m_fromDate = fromDate;
            this.m_toDate = toDate;
            this.m_signature = signature;
        }
        
        public String getSerialNumber() {
            return this.m_serialNumber;
        }
        
        public String getSubjectName() {
            return this.m_subjectName;
        }
        
        public String getIssuerName() {
            return this.m_issuerName;
        }
        
        public String getFromDate() {
            return this.m_fromDate;
        }
        
        public String getToDate() {
            return this.m_toDate;
        }
        
        public byte[] getSignature() {
            return this.m_signature;
        }
    }
    
    public class bp implements ISSLInfo
    {
        private String a;
        private pscl_l b;
        private String c;
        private int d;
        private String e;
        private bq[] f;
        
        public bp(final pscl_n pscl_n) throws IOException {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = -1;
            this.e = null;
            this.f = null;
            if (null != pscl_n) {
                try {
                    final pscl_i c = pscl_n.c();
                    if (null != c) {
                        this.a = c.l();
                    }
                    final pscl_l d = pscl_n.d();
                    if (null != d) {
                        this.c = d.f();
                        this.d = d.c();
                        this.e = d.g();
                        final pscl_j[] j = d.j();
                        if (null != j && 0 < j.length) {
                            this.f = new bq[j.length];
                            for (int i = 0; i < j.length; ++i) {
                                final pscl_j pscl_j = j[i];
                                this.f[i] = new bq("N/A", pscl_j.d(), pscl_j.e(), "N/A", "N/A", null);
                            }
                        }
                    }
                }
                catch (Exception ex) {}
            }
        }
        
        public String getCipherSuite() {
            return this.a;
        }
        
        public String getSessionId() {
            return this.c;
        }
        
        public int getVersion() {
            return this.d;
        }
        
        public String getPeerName() {
            return this.e;
        }
        
        public IPeerCertificateInfo[] getPeerCertificateInfo() {
            return this.f;
        }
    }
    
    public class SSLInfo implements ISSLInfo
    {
        private String m_cipherSuiteName;
        private SSLSession m_sslSession;
        private String m_sessionId;
        private int m_version;
        private String m_peerName;
        private PeerCertificateInfo[] m_peerCertificates;
        
        public SSLInfo(final SSLSocket sslSocket) throws IOException {
            this.m_cipherSuiteName = null;
            this.m_sslSession = null;
            this.m_sessionId = null;
            this.m_version = -1;
            this.m_peerName = null;
            this.m_peerCertificates = null;
            if (null != sslSocket) {
                try {
                    final CipherSuite cipherSuite = sslSocket.getCipherSuite();
                    if (null != cipherSuite) {
                        this.m_cipherSuiteName = cipherSuite.getCipherSuiteName();
                    }
                    final SSLSession session = sslSocket.getSession();
                    if (null != session) {
                        this.m_sessionId = session.getIDString();
                        this.m_version = session.getVersion();
                        this.m_peerName = session.getHost();
                        final X509Certificate[] serverCertChain = session.getServerCertChain();
                        if (null != serverCertChain && 0 < serverCertChain.length) {
                            this.m_peerCertificates = new PeerCertificateInfo[serverCertChain.length];
                            for (int i = 0; i < serverCertChain.length; ++i) {
                                final X509Certificate x509Certificate = serverCertChain[i];
                                this.m_peerCertificates[i] = new PeerCertificateInfo("N/A", x509Certificate.getSubjectNameString(), x509Certificate.getIssuerNameString(), "N/A", "N/A", null);
                            }
                        }
                    }
                }
                catch (Exception ex) {}
            }
        }
        
        public String getCipherSuite() {
            return this.m_cipherSuiteName;
        }
        
        public String getSessionId() {
            return this.m_sessionId;
        }
        
        public int getVersion() {
            return this.m_version;
        }
        
        public String getPeerName() {
            return this.m_peerName;
        }
        
        public IPeerCertificateInfo[] getPeerCertificateInfo() {
            return this.m_peerCertificates;
        }
    }
}
