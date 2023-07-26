// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.net.UnknownHostException;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;

public interface ISSLSocketUtils
{
    Socket createSSLSocket(final InetAddress p0, final int p1, final ISSLParams p2) throws IOException, UnknownHostException;
    
    Socket createSSLSocket(final Socket p0, final ISSLParams p1) throws IOException;
    
    Socket createSSLSocket(final Socket p0, final ISSLParams p1, final boolean p2) throws IOException;
    
    Socket createSSLSocket(final String p0, final int p1, final ISSLParams p2) throws IOException;
    
    ISSLInfo getSocketSSLInfo(final Socket p0) throws IOException;
    
    public interface ISSLInfo
    {
        String getCipherSuite();
        
        String getSessionId();
        
        int getVersion();
        
        String getPeerName();
        
        IPeerCertificateInfo[] getPeerCertificateInfo();
    }
    
    public interface IPeerCertificateInfo
    {
        String getSerialNumber();
        
        String getSubjectName();
        
        String getIssuerName();
        
        String getFromDate();
        
        String getToDate();
        
        byte[] getSignature();
    }
}
