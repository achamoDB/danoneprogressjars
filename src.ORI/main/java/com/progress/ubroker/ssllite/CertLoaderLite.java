// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssllite;

import java.util.Enumeration;
import com.progress.ubroker.util.CertLoader;

public class CertLoaderLite extends CertLoader
{
    public pscl_j[] certificates() {
        if (super.m_x509Certificates.isEmpty()) {
            return null;
        }
        final pscl_j[] array = new pscl_j[this.numberOfCertificates()];
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
            final pscl_j pscl_j = new pscl_j(array, 0, 0);
            if (super.m_flagDebug) {
                this.println("Storing X509Certificate...");
            }
            super.m_certData.addCert(pscl_j);
        }
        catch (Exception ex) {
            if (!super.m_flagIgnoreLoadErrors) {
                throw new InstantiationException("Can't create X509Certiciate : " + ex.toString());
            }
            this.println("Can't create X509Certiciate : " + ex.toString());
        }
    }
    
    public static void main(final String[] array) {
        final CertLoaderLite certLoaderLite = new CertLoaderLite();
        try {
            certLoaderLite.load(array);
            final pscl_j[] certificates = certLoaderLite.certificates();
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
