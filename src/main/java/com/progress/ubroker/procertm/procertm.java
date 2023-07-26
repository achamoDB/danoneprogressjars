// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.procertm;

import java.util.Properties;
import java.io.File;
import java.util.Vector;

public class procertm
{
    private boolean a;
    
    public procertm() {
        this.a = false;
    }
    
    public void run(final String[] array) throws Exception {
        Vector<String> vector = null;
        Vector<String> vector2 = null;
        Vector<String> vector3 = null;
        this.a = false;
        boolean b = false;
        boolean b2 = false;
        boolean b3 = false;
        String str = null;
        String pathname = null;
        final String[] a = new String[0];
        String s = null;
        String s2 = null;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equals("-v")) {
                this.a = true;
            }
            else if (array[i].equals("-h")) {
                b2 = true;
            }
            else if (array[i].equals("-l")) {
                b3 = true;
            }
            else if (!array[i].equals("-l")) {
                if (array[i].equals("-i")) {
                    ++i;
                    if (vector == null) {
                        vector = new Vector<String>();
                    }
                    if (i >= array.length) {
                        System.out.println("-i should be followed by file name");
                        return;
                    }
                    vector.add(array[i]);
                }
                else if (array[i].equals("-r")) {
                    ++i;
                    if (vector2 == null) {
                        vector2 = new Vector<String>();
                    }
                    if (i >= array.length) {
                        System.out.println("-r should be followed by file name");
                        return;
                    }
                    vector2.add(array[i]);
                }
                else if (array[i].equals("-e")) {
                    ++i;
                    if (vector3 == null) {
                        vector3 = new Vector<String>();
                    }
                    if (i >= array.length) {
                        System.out.println("-e should be followed by file name");
                        return;
                    }
                    vector3.add(array[i]);
                }
                else if (array[i].equals("-c")) {
                    i += 2;
                    if (i >= array.length) {
                        System.out.println("-c should be followed by two file names");
                        return;
                    }
                    s = array[i - 1];
                    s2 = array[i];
                }
                else if (array[i].equals("-d")) {
                    if (++i >= array.length) {
                        System.out.println("-d should be followed by a valid directory path");
                        return;
                    }
                    try {
                        pathname = array[i];
                        final File file = new File(pathname);
                        if (!file.exists() || !file.isDirectory()) {
                            System.out.println("-d did not refer to a valid directory");
                            return;
                        }
                    }
                    catch (Exception ex) {
                        System.out.println("-d did not refer to a valid directory");
                        return;
                    }
                }
                else if (array[i].equals("-p")) {
                    b = true;
                }
                else {
                    if (str != null) {
                        System.out.println("Only one Certificate Store file may be specified");
                        return;
                    }
                    str = array[i];
                }
            }
        }
        if (b2) {
            this.a();
            return;
        }
        if (vector == null && vector3 == null && vector2 == null && s2 == null && !b && !b3) {
            System.out.println("Please specify a valid action");
            this.a();
            return;
        }
        if (s2 != null) {
            if (!this.b(s)) {
                return;
            }
            if (!this.b(s2)) {
                return;
            }
        }
        else if (str == null || (!str.endsWith(".jar") && !str.endsWith(".zip"))) {
            System.out.println("Invalid cert_store_file: " + str);
            return;
        }
        final Properties properties = new Properties();
        properties.setProperty("psc.certstore.debug", "FALSE");
        properties.setProperty("psc.certstore.verbose", this.a ? "TRUE" : "FALSE");
        properties.setProperty("psc.certstore.logfile", "stdout");
        final CertStore certStore = new CertStore(properties, pathname);
        if (str != null) {
            certStore.openCertStore(str, vector != null);
        }
        if (s2 != null) {
            certStore.convertCertificate(s, s2);
        }
        if (vector != null) {
            certStore.importCertificate(vector.toArray(a));
        }
        if (vector3 != null) {
            certStore.exportCertificate(vector3.toArray(a));
        }
        if (vector2 != null) {
            certStore.removeCertificate(vector2.toArray(a));
        }
        if (b) {
            certStore.printDigitalCertificateList(str.substring(0, str.indexOf(".")).concat(".dcl"));
        }
        if (b3) {
            certStore.listDigitalCertificates();
        }
        certStore.closeCertStore(true);
    }
    
    public boolean getVerbose() {
        return this.a;
    }
    
    public void setVerbose(final boolean a) {
        this.a = a;
    }
    
    private boolean a(final String s) {
        return s.endsWith(".pem") || s.endsWith(".txt") || s.endsWith(".0") || s.endsWith(".crt") || s.endsWith(".cer");
    }
    
    private boolean a(final String[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        for (int i = 0; i < array.length; ++i) {
            if (!this.b(array[i])) {
                return false;
            }
        }
        return true;
    }
    
    private boolean b(final String str) {
        if (str == null) {
            return true;
        }
        if (!this.a(str)) {
            System.out.println("Invalid certificate file: " + str);
            return false;
        }
        return true;
    }
    
    public static void main(final String[] array) {
        final procertm procertm = new procertm();
        try {
            procertm.run(array);
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            if (procertm.getVerbose()) {
                ex.printStackTrace();
            }
        }
        catch (ProcertmException ex2) {
            System.out.println(ex2.getMessage());
            if (procertm.getVerbose()) {
                ex2.printStackTrace();
            }
        }
        catch (Exception ex3) {
            System.out.println(ex3.getMessage());
            if (procertm.getVerbose()) {
                ex3.printStackTrace();
            }
        }
    }
    
    protected void a() {
        System.out.println("usage: procertm [options] cert_store");
        System.out.println("    cert_store is a file of type .jar, or .zip");
        System.out.println("    options:");
        System.out.println("        -v          print verbose progress information");
        System.out.println("        -l          list the contents of the cert_store");
        System.out.println("        -i cert     import a certificate file 'cert'");
        System.out.println("                    -i may appear multiple times");
        System.out.println("        -r cert     remove the certificate with the file name 'cert'");
        System.out.println("                    -r may appear multiple times");
        System.out.println("        -e cert     export the certificate with the file name 'cert'");
        System.out.println("                    -e may appear multiple times");
        System.out.println("        -c cert1 cert2     store the certificates from certificate file cert1");
        System.out.println("                    to file cert2, convert the encoding if necassary");
        System.out.println("        -p          print the digital certificate list for");
        System.out.println("                    the 'cert_store' to 'cert_store'.dcl ");
        System.out.println("        ");
        System.out.println("    cert            The argument's file extension should be one of:");
        System.out.println("                        .pem PEM Encoded certificate(s)");
        System.out.println("                        .txt PEM Encoded certificate(s)");
        System.out.println("                        .cer DER Encoded certificate");
        System.out.println("                        .crt DER Encoded certificate");
        System.out.println("                        .0 RSA hashcode filename with ");
        System.out.println("                           PEM Encoded certificate(s)");
        System.out.println("        ");
    }
}
