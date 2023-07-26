// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.util.Enumeration;
import java.util.Vector;
import com.progress.ubroker.util.CertLoader;

public class CertReader extends CertLoader
{
    public CertFile[] certificateStores() {
        if (super.m_x509Certificates.isEmpty()) {
            return null;
        }
        final Vector<CertFile> vector = new Vector<CertFile>();
        final Enumeration<CertFile> elements = super.m_x509Certificates.elements();
        while (elements.hasMoreElements()) {
            vector.add(elements.nextElement());
        }
        return vector.toArray(new CertFile[0]);
    }
    
    public static CertData[] certificateFiles(final CertFile certFile) {
        final Vector<CertData> vector = new Vector<CertData>();
        final Enumeration enumCertData = certFile.enumCertData();
        while (enumCertData.hasMoreElements()) {
            vector.add(enumCertData.nextElement());
        }
        return vector.toArray(new CertData[0]);
    }
    
    public CertData[] certificateFiles() {
        final Vector<CertData> vector = new Vector<CertData>();
        final CertFile[] certificateStores = this.certificateStores();
        for (int i = 0; i < certificateStores.length; ++i) {
            final Enumeration enumCertData = certificateStores[i].enumCertData();
            while (enumCertData.hasMoreElements()) {
                vector.add(enumCertData.nextElement());
            }
        }
        return vector.toArray(new CertData[0]);
    }
    
    public byte[][] certificateBytes() {
        if (super.m_x509Certificates.isEmpty()) {
            return null;
        }
        final byte[][] array = new byte[this.numberOfCertificates()][];
        int n = 0;
        final Enumeration<CertFile> elements = super.m_x509Certificates.elements();
        while (elements.hasMoreElements()) {
            final Enumeration enumCertData = elements.nextElement().enumCertData();
            while (enumCertData.hasMoreElements()) {
                final Enumeration enumCerts = enumCertData.nextElement().enumCerts();
                while (enumCerts.hasMoreElements()) {
                    array[n++] = enumCerts.nextElement();
                }
            }
        }
        return array;
    }
    
    public psc_e[] certificates() throws Exception {
        final byte[][] certificateBytes = this.certificateBytes();
        if (certificateBytes == null) {
            return null;
        }
        final psc_e[] array = new psc_e[certificateBytes.length];
        for (int i = 0; i < certificateBytes.length; ++i) {
            try {
                array[i] = new psc_e(certificateBytes[i], 0, 0);
            }
            catch (Exception ex) {
                if (!super.m_flagIgnoreLoadErrors) {
                    throw new InstantiationException("Can't create X509Certiciate : " + ex.toString());
                }
                this.println("Can't create X509Certiciate : " + ex.toString());
            }
        }
        return array;
    }
    
    protected void createAndStoreBinaryCertificate(final byte[] array) throws Exception {
        super.m_certData.addCert(array);
    }
}
