// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.Socket;
import com.progress.ubroker.util.ISSLParams;
import java.net.InetAddress;
import com.progress.ubroker.util.ISSLSocketUtils;

public class SSLSocketUtilsFull implements ISSLSocketUtils
{
    public static final char[] m_toHex;
    private static final byte[] a;
    
    public Socket createSSLSocket(final InetAddress inetAddress, final int n, final ISSLParams isslParams) throws IOException, UnknownHostException {
        return new psc_dy(inetAddress, n, (psc_c2)isslParams.getVendorParams());
    }
    
    public Socket createSSLSocket(final Socket socket, final ISSLParams isslParams) throws IOException {
        return new psc_dy(socket, (psc_c2)isslParams.getVendorParams());
    }
    
    public Socket createSSLSocket(final Socket socket, final ISSLParams isslParams, final boolean b) throws IOException {
        return new psc_dy(socket, (psc_c2)isslParams.getVendorParams(), b);
    }
    
    public Socket createSSLSocket(final String s, final int n, final ISSLParams isslParams) throws IOException {
        return new psc_dy(s, n, (psc_c2)isslParams.getVendorParams());
    }
    
    public ServerSocket createSSLServerSocket(final int n, final int n2, final ISSLParams isslParams) throws IOException {
        return new psc_dx(n, n2, (psc_c2)isslParams.getVendorParams());
    }
    
    public ISSLInfo getSocketSSLInfo(final Socket socket) throws IOException {
        return new SSLInfo((psc_dy)socket);
    }
    
    public byte[] getSignature(final ISSLInfo isslInfo) {
        return isslInfo.getPeerCertificateInfo()[0].getSignature();
    }
    
    public String getSubjectName(final ISSLInfo isslInfo) {
        return isslInfo.getPeerCertificateInfo()[0].getSubjectName();
    }
    
    public boolean isDefaultCertificate(final ISSLInfo isslInfo) {
        return this.a(this.getSignature(isslInfo));
    }
    
    private boolean a(final byte[] array) {
        if (array.length != SSLSocketUtilsFull.a.length) {
            return false;
        }
        for (int i = 0; i < SSLSocketUtilsFull.a.length; ++i) {
            if (array[i] != SSLSocketUtilsFull.a[i]) {
                return false;
            }
        }
        return true;
    }
    
    public String getCommonNameField(final String s) {
        String substring = null;
        final String[] split = s.split(",");
        for (int i = 0; i < split.length; ++i) {
            if (split[i].startsWith("CN=") || split[i].startsWith("cn=")) {
                substring = split[i].substring(3);
                break;
            }
        }
        return substring;
    }
    
    static {
        m_toHex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        a = new byte[] { -90, 90, 113, 16, -125, -92, 41, -37, -69, 104, 24, 34, -69, 86, 42, 50, -53, -97, -115, -59, 33, 61, 101, -55, 65, -54, 95, 78, 109, 124, -22, -112, -34, -8, -28, -5, 77, -95, -86, -7, 17, -93, 120, -91, 115, 47, 42, -121, -93, 110, -44, -105, -96, -9, -48, 109, 125, 122, -119, -30, -38, 16, 127, 53, -47, 96, -65, 48, -1, 117, 121, -12, -121, -93, -109, -120, -66, -33, -88, -96, 56, -38, 111, -88, -5, -90, -46, -104, 109, 94, -22, 43, 36, -124, 54, -84, -81, 15, 113, -100, -88, 43, 2, -114, 11, -87, -1, 52, -84, 108, 21, -7, -102, -88, 55, -116, 37, 18, -87, 18, -126, -119, 29, 116, -122, 77, 122, -17 };
    }
    
    public class PeerCertificateInfo implements IPeerCertificateInfo
    {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private byte[] f;
        
        public PeerCertificateInfo(final String a, final String b, final String c, final String d, final String e, final byte[] f) {
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
        
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append("    serial:  ");
            sb.append(this.getSerialNumber());
            sb.append("\n");
            sb.append("    subject: ");
            sb.append(this.getSubjectName());
            sb.append("\n");
            sb.append("    issuer:  ");
            sb.append(this.getIssuerName());
            sb.append("\n");
            sb.append("    from:    ");
            sb.append(this.getFromDate());
            sb.append("\n");
            sb.append("    to:      ");
            sb.append(this.getToDate());
            sb.append("\n");
            return sb.toString();
        }
    }
    
    public class SSLInfo implements ISSLInfo
    {
        private String a;
        private psc_d2 b;
        private String c;
        private int d;
        private String e;
        private PeerCertificateInfo[] f;
        
        public SSLInfo(final psc_dy psc_dy) throws IOException {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = -1;
            this.e = null;
            this.f = null;
            if (null != psc_dy) {
                try {
                    final psc_dw a = psc_dy.a();
                    if (null != a) {
                        this.a = a.i();
                    }
                    this.b = psc_dy.b();
                    if (null != this.b) {
                        this.c = this.b.f();
                        this.d = this.b.c();
                        this.e = this.b.g();
                        final psc_e[] j = this.b.j();
                        if (null != j && 0 < j.length) {
                            this.f = new PeerCertificateInfo[j.length];
                            for (int i = 0; i < j.length; ++i) {
                                final psc_e psc_e = j[i];
                                final byte[] h = psc_e.h();
                                final StringBuffer sb = new StringBuffer("");
                                if (null != h && 0 < h.length) {
                                    for (int k = 0; k < h.length; ++k) {
                                        sb.append(SSLSocketUtilsFull.m_toHex[(byte)((h[k] & 0xFFFFFFF0) >> 8 & 0xF)]);
                                        sb.append(SSLSocketUtilsFull.m_toHex[(byte)(h[k] & 0xF)]);
                                    }
                                }
                                this.f[i] = new PeerCertificateInfo(sb.toString(), psc_e.f().toString(), psc_e.g().toString(), psc_e.j().toString(), psc_e.k().toString(), psc_e.d());
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
        
        public Object getVendorSession() {
            return this.b;
        }
        
        public String toString() {
            final StringBuffer sb = new StringBuffer("SSL connection params:\n");
            sb.append("    Protocol version: ");
            sb.append(Integer.toString(this.getVersion()));
            sb.append("\n");
            sb.append("    Protocol cipher suite: ");
            sb.append(this.getCipherSuite());
            sb.append("\n");
            sb.append("    Protocol session ID: ");
            sb.append(this.getSessionId());
            sb.append("\n");
            sb.append("    Protocol peer: ");
            sb.append(this.getPeerName());
            sb.append("\n");
            final IPeerCertificateInfo[] peerCertificateInfo = this.getPeerCertificateInfo();
            if (null != peerCertificateInfo) {
                for (int i = 0; i < peerCertificateInfo.length; ++i) {
                    sb.append("Certificate #");
                    sb.append(Integer.toString(i));
                    sb.append("\n");
                    sb.append(peerCertificateInfo[i].toString());
                }
            }
            else {
                sb.append("WARNING: No peer certificates found\n");
            }
            return sb.toString();
        }
    }
}
