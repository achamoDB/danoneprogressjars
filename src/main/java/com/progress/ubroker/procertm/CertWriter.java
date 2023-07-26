// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.procertm;

import com.progress.ubroker.util.Base64;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipEntry;
import java.util.Vector;
import java.util.Enumeration;
import java.util.jar.JarOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import com.progress.common.exception.ProException;
import java.io.PrintWriter;
import com.progress.ubroker.util.CertLoader;

public class CertWriter
{
    private String a;
    private CertLoader.CertFile b;
    protected PrintWriter c;
    protected int d;
    
    public CertWriter(final String a, final PrintWriter c, final int d) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = 2;
        this.a = a;
        this.c = c;
        this.d = d;
    }
    
    public void writeCert(final CertLoader.CertData certData) throws ProException {
        if (this.a.endsWith(".pem") || this.a.endsWith(".txt") || this.a.endsWith(".0")) {
            this.a(certData, true);
        }
        else {
            if (!this.a.endsWith(".cer") && !this.a.endsWith(".crt")) {
                throw new IllegalArgumentException("Unknown file type: " + this.a);
            }
            this.b(certData, true);
        }
    }
    
    public void writeCertStore(final CertLoader.CertFile b) throws ProException {
        this.b = b;
        a(this.a, true);
        ZipOutputStream zipOutputStream = null;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(this.a);
        }
        catch (Exception ex) {
            throw new ProcertmException("Cannot new FileOutputStream " + this.a + " : " + ex.getMessage());
        }
        Label_0193: {
            if (!this.a.endsWith(".jar")) {
                if (this.a.endsWith(".zip")) {
                    try {
                        zipOutputStream = new ZipOutputStream(fileOutputStream);
                        break Label_0193;
                    }
                    catch (Exception ex2) {
                        throw new ProcertmException("Cannot new ZipOutputStream " + ex2.getMessage());
                    }
                }
                throw new IllegalArgumentException("Unknown file type: " + this.a);
            }
            try {
                zipOutputStream = new JarOutputStream(fileOutputStream);
            }
            catch (Exception ex3) {}
        }
        this.a(zipOutputStream);
    }
    
    public void writeDCL(final CertLoader.CertFile b, final boolean b2) throws ProException {
        this.b = b;
        this.a(this.a(), this.a, true);
    }
    
    public StringBuffer getDCL(final CertLoader.CertData[] array) throws ProException {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            n += array[i].getCertCount();
        }
        final StringBuffer sb = new StringBuffer();
        for (int j = 0; j < array.length; ++j) {
            final Enumeration enumCerts = array[j].enumCerts();
            while (enumCerts.hasMoreElements()) {
                final byte[] array2 = enumCerts.nextElement();
                psc_e psc_e;
                try {
                    psc_e = new psc_e(array2, 0, 0);
                }
                catch (Exception ex) {
                    this.a(1, "Could not create new X509Certificate for " + array[j].getCertName() + ": " + ex.getMessage());
                    continue;
                }
                sb.append("Name: " + array[j].getCertName() + "\n");
                sb.append("subject: " + psc_e.f().toString() + "\n");
                sb.append("issuer: " + psc_e.g().toString() + "\n");
                sb.append("notafter: " + psc_e.k().toString() + "\n");
            }
            sb.append("\n");
        }
        return sb;
    }
    
    private StringBuffer a() throws ProException {
        return this.getDCL(this.b());
    }
    
    private CertLoader.CertData[] b() {
        final Vector<Object> vector = new Vector<Object>();
        final Enumeration enumCertData = this.b.enumCertData();
        while (enumCertData.hasMoreElements()) {
            vector.add(enumCertData.nextElement());
        }
        return vector.toArray(new CertLoader.CertData[0]);
    }
    
    private psc_e[] c() throws Exception {
        final byte[][] d = this.d();
        if (d == null) {
            return null;
        }
        final psc_e[] array = new psc_e[d.length];
        for (int i = 0; i < d.length; ++i) {
            array[i] = new psc_e(d[i], 0, 0);
        }
        return array;
    }
    
    private byte[][] d() throws ProcertmException {
        final CertLoader.CertData[] b = this.b();
        int n = 0;
        for (int i = 0; i < b.length; ++i) {
            n += b[i].getCertCount();
        }
        final byte[][] array = new byte[n][];
        int n2 = 0;
        for (int j = 0; j < b.length; ++j) {
            final Enumeration enumCerts = b[j].enumCerts();
            while (enumCerts.hasMoreElements()) {
                array[n2] = enumCerts.nextElement();
                ++n2;
            }
        }
        return array;
    }
    
    protected void a(final CertLoader.CertData certData, final boolean b) throws ProException {
        final String s = (this.a != null) ? this.a : certData.getCertName();
        certData.enumCerts();
        final Enumeration enumCerts = certData.enumCerts();
        final Vector vector = new Vector<Byte>();
        while (enumCerts.hasMoreElements()) {
            this.a(enumCerts.nextElement(), vector);
        }
        final byte[] array = new byte[vector.size()];
        for (int i = 0; i < vector.size(); ++i) {
            array[i] = vector.get(i);
        }
        this.a(array, s, b);
    }
    
    protected void b(final CertLoader.CertData certData, final boolean b) throws ProException {
        final String s = (this.a != null) ? this.a : certData.getCertName();
        int n = 0;
        final Enumeration enumCerts = certData.enumCerts();
        while (enumCerts.hasMoreElements()) {
            n += ((byte[])enumCerts.nextElement()).length;
        }
        final byte[] array = new byte[n];
        final Enumeration enumCerts2 = certData.enumCerts();
        while (enumCerts2.hasMoreElements()) {
            final byte[] array2 = enumCerts2.nextElement();
            for (int i = 0; i < array2.length; ++i) {
                array[i] = array2[i];
            }
        }
        this.a(array, s, b);
    }
    
    private void a(final ZipOutputStream zipOutputStream, final String name) throws ProException {
        ZipEntry e;
        try {
            e = new ZipEntry(name);
        }
        catch (Exception ex) {
            throw new ProcertmException("Cannot new ZipEntry: " + ex.getMessage(), ProcertmException.FATAL_ERROR);
        }
        try {
            zipOutputStream.putNextEntry(e);
        }
        catch (ZipException ex2) {
            throw new ProcertmException("ZipException: " + ex2.getMessage(), ProcertmException.FATAL_ERROR);
        }
        catch (IOException ex3) {
            throw new ProcertmException("IOException: " + ex3.getMessage(), ProcertmException.FATAL_ERROR);
        }
    }
    
    private void a(final ZipOutputStream zipOutputStream, final byte[] b) throws ProException {
        try {
            zipOutputStream.write(b, 0, b.length);
        }
        catch (IOException ex) {
            throw new ProcertmException("IOException: " + ex.getMessage());
        }
    }
    
    protected void a(final ZipOutputStream zipOutputStream) throws ProException {
        int i = 0;
        final CertLoader.CertData[] b = this.b();
        this.a(3, "Updating the store file " + this.a);
        for (int j = 0; j < b.length; ++j) {
            final String certName = b[j].getCertName();
            int n = 0;
            this.a(3, "  " + certName);
            final Enumeration enumCerts = b[j].enumCerts();
            while (enumCerts.hasMoreElements()) {
                byte[] b2 = enumCerts.nextElement();
                try {
                    final psc_e psc_e = new psc_e(b2, 0, 0);
                }
                catch (Exception ex2) {
                    this.a(1, "Unable to write " + certName + " to " + this.a);
                    continue;
                }
                if (certName.endsWith(".pem") || certName.endsWith(".txt") || certName.endsWith(".0")) {
                    b2 = this.b(b2);
                }
                if (n == 0) {
                    this.a(zipOutputStream, certName);
                    n = 1;
                }
                this.a(zipOutputStream, b2);
                ++i;
            }
        }
        this.a(3, "wrote " + i + " certificates to " + this.a);
        final String name = new File(this.a).getName();
        final String concat = name.substring(0, name.indexOf(".")).concat(".dcl");
        this.a(zipOutputStream, concat);
        this.a(zipOutputStream, this.a().toString().getBytes());
        this.a(3, "wrote " + concat + " to " + this.a);
        try {
            zipOutputStream.finish();
            zipOutputStream.close();
            zipOutputStream.flush();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("flush " + ex.getMessage());
        }
    }
    
    protected static void a(final String s, final boolean b) throws ProException {
        final File file = new File(s);
        final String parent = file.getParent();
        if (null != parent) {
            try {
                new File(parent).mkdirs();
            }
            catch (Exception ex) {
                throw new ProcertmException("Can not create file's directory path " + parent + " " + ex.getMessage(), ProcertmException.DEBUG_INFO);
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                return;
            }
            catch (Exception ex2) {
                throw new IllegalArgumentException("Can not create file " + s + " " + ex2.getMessage());
            }
        }
        if (b) {
            file.delete();
            try {
                file.createNewFile();
                return;
            }
            catch (IOException ex3) {
                throw new ProcertmException("Can not create file " + s + " " + ex3.getMessage(), ProcertmException.DEBUG_INFO);
            }
        }
        throw new ProcertmException("Error: file " + s + " already exist", ProcertmException.DEBUG_INFO);
    }
    
    protected void a(final byte[] b, final String str, final boolean b2) throws ProException {
        a(str, b2);
        BufferedOutputStream bufferedOutputStream;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Error accessing " + str + " : " + ex.getMessage());
        }
        this.a(3, "Writing " + b.length + " bytes to " + str);
        try {
            bufferedOutputStream.write(b, 0, b.length);
            bufferedOutputStream.flush();
        }
        catch (IOException ex2) {
            throw new ProcertmException("outStream.write: " + ex2.getMessage(), ProcertmException.DEBUG_INFO);
        }
    }
    
    protected void a(final StringBuffer sb, final String s, final boolean b) throws ProException {
        this.a(sb.toString().getBytes(), s, b);
    }
    
    private byte[] a(final byte[] array) {
        final Base64 base64 = new Base64();
        return Base64.encode(array).getBytes();
    }
    
    private byte[] b(final byte[] array) {
        final Vector a = this.a(array, null);
        final byte[] array2 = new byte[a.size()];
        for (int i = 0; i < a.size(); ++i) {
            array2[i] = a.get(i);
        }
        return array2;
    }
    
    private Vector a(final byte[] array, Vector vector) {
        if (vector == null) {
            vector = new Vector<Byte>();
        }
        final byte[] bytes = (this.c(array) + new String("-----BEGIN CERTIFICATE-----\n")).getBytes();
        final byte[] bytes2 = new String("\n-----END CERTIFICATE-----\n\n").getBytes();
        final byte[] a = this.a(array);
        for (int i = 0; i < bytes.length; ++i) {
            vector.add(new Byte(bytes[i]));
        }
        for (int j = 0; j < a.length; ++j) {
            vector.add(new Byte(a[j]));
        }
        for (int k = vector.size() - a.length + 64; k < vector.size(); k += 65) {
            vector.add(k, new Byte(new String("\n").getBytes()[0]));
        }
        for (int l = 0; l < bytes2.length; ++l) {
            vector.add(new Byte(bytes2[l]));
        }
        return vector;
    }
    
    private String c(final byte[] array) {
        final String s = "";
        psc_e psc_e = null;
        try {
            psc_e = new psc_e(array, 0, 0);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
        return s.concat("Subject: " + psc_e.f().toString() + "\n").concat("Issuer: " + psc_e.g().toString() + "\n").concat("From:    " + psc_e.j().toString() + "\n").concat("To:      " + psc_e.k().toString() + "\n");
    }
    
    private void a(final int n, final String x) {
        if (this.c != null && n <= this.d && null != x) {
            this.c.println(x);
        }
    }
}
