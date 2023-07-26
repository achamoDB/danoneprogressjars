// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.util.Enumeration;
import com.progress.ubroker.util.CertLoader;

public class CertLoaderFull extends CertLoader
{
    public psc_e[] certificates() {
        if (super.m_x509Certificates.isEmpty()) {
            return null;
        }
        final psc_e[] array = new psc_e[this.numberOfCertificates()];
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
    
    protected void createAndStoreBinaryCertificate(final byte[] array) throws Exception {
        try {
            if (super.m_flagDebug) {
                this.println("Creating X509Certificate...");
            }
            final psc_e psc_e = new psc_e(array, 0, 0);
            if (super.m_flagDebug) {
                this.println("Storing X509Certificate...");
            }
            super.m_certData.addCert(psc_e);
            if (super.m_flagDebug) {
                this.println("Certificate subject: " + psc_e.f().toString());
                this.println("Certificate expires: " + psc_e.k().toString());
            }
        }
        catch (Exception ex) {
            if (!super.m_flagIgnoreLoadErrors) {
                throw new InstantiationException("Can't create X509Certiciate : " + ex.toString());
            }
            this.println("Can't create X509Certiciate : " + ex.toString());
        }
    }
    
    public static void main(final String[] array) {
        final CertLoaderFull certLoaderFull = new CertLoaderFull();
        try {
            certLoaderFull.load(array);
            final psc_e[] certificates = certLoaderFull.certificates();
            if (null == certificates) {
                System.out.println("0 certificates were loaded.");
            }
            else {
                System.out.println(Integer.toString(certificates.length) + " certificates were loaded.");
            }
        }
        catch (IllegalArgumentException ex) {
            if (ex.getMessage().startsWith("Insufficient")) {
                CertLoader.usage();
            }
            else {
                System.out.println(ex.getMessage());
            }
        }
        catch (Exception ex2) {
            System.out.println(ex2.getMessage());
        }
    }
}
