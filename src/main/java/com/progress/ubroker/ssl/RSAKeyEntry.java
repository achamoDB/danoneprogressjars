// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.ssl;

import java.io.ByteArrayInputStream;
import com.progress.ubroker.util.Base64;
import java.util.StringTokenizer;
import java.io.EOFException;
import java.io.BufferedInputStream;
import java.util.Date;
import com.progress.common.util.crypto;
import java.util.Enumeration;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;

public class RSAKeyEntry
{
    protected boolean a;
    protected boolean b;
    protected Vector c;
    protected byte[] d;
    protected byte[] e;
    protected String f;
    protected boolean g;
    protected String h;
    protected String i;
    private static final int j = 0;
    private static final int k = 1;
    private static final int l = 2;
    private static final int m = 3;
    private static final int n = 4;
    private static final int o = 5;
    private static final String[] p;
    private static final String[] q;
    public PrintWriter m_printStream;
    public static final int NO_PRIVATE_KEY = 0;
    public static final int PKCS_1_KEY = 1;
    public static final int PKCS_8_KEY = 2;
    
    public RSAKeyEntry() {
        this.a = true;
        this.b = false;
        this.c = new Vector();
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = false;
        this.h = null;
        this.i = null;
        this.m_printStream = new PrintWriter(System.out);
    }
    
    public int loadedKeyType() {
        int n = 0;
        if (null != this.d) {
            n = 1;
        }
        else if (null != this.e) {
            n = 2;
        }
        return n;
    }
    
    public void loadKeyEntry(final String pathname) throws IOException {
        try {
            this.loadKeyEntry(new File(pathname));
        }
        catch (InvalidCertificateException ex) {
            throw ex;
        }
        catch (IOException cause) {
            final KeyException ex2 = new KeyException(cause.getMessage());
            ex2.initCause(cause);
            throw ex2;
        }
    }
    
    public void loadKeyEntry(final File file) throws IOException {
        final String canonicalPath = file.getCanonicalPath();
        final FileInputStream in = new FileInputStream(file);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        if (this.a) {
            this.a("Reading from file: " + canonicalPath);
        }
        try {
            final boolean a = this.a(bufferedReader);
            in.close();
            if (!a) {
                if (!this.b) {
                    throw new IOException("No key/certificate found in the PEM file");
                }
                this.a("No certificate found in PEM file");
            }
        }
        catch (IOException ex) {
            in.close();
            if (!this.b) {
                throw ex;
            }
        }
    }
    
    public psc_e[] certificates() {
        if (this.c.isEmpty()) {
            return null;
        }
        final psc_e[] array = new psc_e[this.c.size()];
        int n = 0;
        final Enumeration<psc_e> elements = this.c.elements();
        while (elements.hasMoreElements()) {
            array[n++] = elements.nextElement();
        }
        return array;
    }
    
    public byte[] pkcs1Key(final String f) throws IOException {
        byte[] a = null;
        if (null == f) {
            this.a();
            this.f = new crypto().encrypt(this.f);
        }
        else {
            this.f = f;
        }
        if (!this.g) {
            a = this.a(this.d, this.f);
            this.b(a);
        }
        return a;
    }
    
    public byte[] pkcs8Key() {
        return (byte[])(this.g ? this.e : null);
    }
    
    private static byte[] a(final int n) {
        byte[] array = null;
        if (n <= 127) {
            array = new byte[] { (byte)n };
        }
        else if (n <= 255) {
            array = new byte[] { -127, (byte)n };
        }
        else if (n <= 65535) {
            array = new byte[] { -126, (byte)(n >>> 8), (byte)n };
        }
        return array;
    }
    
    private static void c(final byte[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                array[i] = 0;
            }
        }
    }
    
    private void a(final String x) {
        this.m_printStream.println(x);
    }
    
    protected void a(final byte[] array) throws IOException {
        try {
            if (this.a) {
                this.a("Creating X509Certificate...");
            }
            final psc_e obj = new psc_e(array, 0, 0);
            if (this.a) {
                this.a("Storing X509Certificate...");
            }
            if (!obj.a(new Date())) {
                throw new InvalidCertificateException(obj.j(), obj.k());
            }
            this.c.addElement(obj);
            if (this.a) {
                this.a("Certificate subject: " + obj.f().toString());
                this.a("Certificate expires: " + obj.k().toString());
            }
        }
        catch (InvalidCertificateException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            if (!this.b) {
                throw new IOException("Can't create X509Certiciate : " + ex2.toString());
            }
            this.a("Can't create X509Certiciate : " + ex2.toString());
        }
    }
    
    protected boolean a(final BufferedInputStream bufferedInputStream) throws Exception, IOException, InstantiationException {
        final boolean b = false;
        if (null != this.e) {
            throw new IOException("Attempting to load more than one private key.");
        }
        if (null == bufferedInputStream) {
            throw new IOException("Attempting to load private key from a null stream.");
        }
        if (this.a) {
            this.a("Loading " + bufferedInputStream.available() + " bytes of encrypted private key data...");
        }
        this.e = new byte[bufferedInputStream.available()];
        if (0 == bufferedInputStream.read(this.e, 0, bufferedInputStream.available())) {
            throw new IOException("Error reading 0 bytes from file before EOL");
        }
        return b;
    }
    
    protected boolean b(final BufferedInputStream bufferedInputStream) throws Exception, IOException, InstantiationException {
        int n = 0;
        final byte[] b = new byte[4096];
        while (0 != bufferedInputStream.available()) {
            final int read = bufferedInputStream.read(b, n, b.length - n);
            if (read <= 0) {
                throw new IOException("Error reading 0 bytes from file before EOL");
            }
            n += read;
        }
        if (this.a) {
            this.a("  Loaded " + n + " bytes of certificate data.");
        }
        final byte[] array = new byte[n];
        System.arraycopy(b, 0, array, 0, n);
        this.a(array);
        return true;
    }
    
    private byte[] b(final String s) {
        final byte[] array = new byte[s.length() / 2];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (byte)Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
        }
        return array;
    }
    
    private psc_dc a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4, final int n5, final String s, final String s2) throws IOException {
        psc_dc a = null;
        psc_da a2 = null;
        int n6 = 0;
        int i = 0;
        int n7 = 0;
        final int n8 = 8;
        final int n9 = 24;
        if (s.equals("3DES_EDE")) {
            i = n9;
        }
        else if (s.equals("DES")) {
            i = n8;
        }
        final byte[] array3 = new byte[16];
        final byte[] array4 = new byte[i];
        try {
            a2 = psc_da.a("MD5", "Java");
            do {
                a2.i();
                if (n6++ > 0) {
                    a2.a(array3, 0, array3.length);
                }
                a2.a(array, n, n2);
                if (array2 != null) {
                    a2.a(array2, n3, n4);
                }
                a2.c(array3, 0);
                for (int j = 1; j < n5; ++j) {
                    a2.i();
                    a2.a(array3, 0, 16);
                    a2.c(array3, 0);
                }
                int n10 = 0;
                if (i > 0) {
                    while (i != 0) {
                        if (n10 == 16) {
                            break;
                        }
                        array4[n7++] = array3[n10];
                        --i;
                        ++n10;
                    }
                }
            } while (i != 0);
            a = psc_dc.a(s, s2);
            a.a(array4, 0, array4.length);
        }
        catch (psc_ap psc_ap) {
            throw new IOException("Error generating key input data.");
        }
        finally {
            if (a2 != null) {
                a2.y();
            }
            c(array4);
            c(array3);
        }
        return a;
    }
    
    protected byte[] a(final byte[] array, final String s) throws IOException {
        if (null == array) {
            throw new IOException("Cannot decrypt an empty RSA Private Key.");
        }
        if (null == this.h) {
            throw new IOException("Cannot decrypt an empty RSA Private Key without an encryption algorithm.");
        }
        if (null == this.i) {
            throw new IOException("Cannot decrypt an empty RSA Private Key without an initialization vector.");
        }
        final int length = array.length;
        if (null == s) {
            throw new IOException("Attempting to decrypt a private key without a password");
        }
        final byte[] bytes = new crypto().decrypt(s).getBytes();
        final int n = 0;
        final int length2 = bytes.length;
        final byte[] b = this.b(this.i);
        byte[] array2 = null;
        try {
            psc_dc psc_dc;
            psc_df psc_df;
            if (this.h.equals("DES-EDE3-CBC")) {
                psc_dc = this.a(bytes, n, length2, b, 0, b.length, 1, "3DES_EDE", "Java");
                psc_df = psc_df.a("3DES_EDE/CBC/PKCS5Padding", "Java");
            }
            else {
                psc_dc = this.a(bytes, n, length2, b, 0, b.length, 1, "DES", "Java");
                psc_df = psc_df.a("DES/CBC/PKCS5Padding", "Java");
            }
            psc_df.b(b, 0, b.length);
            psc_df.b(psc_dc);
            array2 = new byte[psc_df.a(length)];
            psc_df.c(array2, psc_df.b(array, 0, length, array2, 0));
        }
        catch (psc_ap psc_ap) {
            throw new InvalidKeyException("Invalid private key input data.");
        }
        finally {
            c(b);
            c(bytes);
        }
        return array2;
    }
    
    protected void b(final byte[] array) throws IOException {
        if (null == array) {
            throw new IOException("Cannot convert an empty key to PKCS #8");
        }
        final int length = array.length;
        final byte[] a = a(length);
        final byte[] array2 = { 2, 1, 0, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 4 };
        final int n = a.length + array2.length + length;
        final byte[] a2 = a(n);
        (this.e = new byte[1 + n + a2.length])[0] = 48;
        final int n2 = 1;
        System.arraycopy(a2, 0, this.e, n2, a2.length);
        final int n3 = n2 + a2.length;
        System.arraycopy(array2, 0, this.e, n3, array2.length);
        final int n4 = n3 + array2.length;
        System.arraycopy(a, 0, this.e, n4, a.length);
        System.arraycopy(array, 0, this.e, n4 + a.length, length);
    }
    
    protected boolean a(final BufferedReader bufferedReader) throws IOException {
        int n = 0;
        int i = 1;
        while (i != 0) {
            int j = 0;
            boolean b = false;
            final boolean b2 = true;
            i = 1;
            while (b2) {
                final String line = bufferedReader.readLine();
                if (null == line) {
                    i = 0;
                    break;
                }
                for (int k = 1; k < RSAKeyEntry.p.length; ++k) {
                    if (-1 != line.indexOf(RSAKeyEntry.p[k])) {
                        if (this.a) {
                            this.a(line);
                        }
                        j = k;
                        break;
                    }
                }
                if (0 < j) {
                    break;
                }
            }
            if (0 < j) {
                int n2 = 0;
                final StringBuffer sb = new StringBuffer(4096);
                while (b2) {
                    final String line2 = bufferedReader.readLine();
                    if (null == line2) {
                        throw new EOFException("Unexpected end of file");
                    }
                    if (-1 != line2.indexOf(RSAKeyEntry.q[j])) {
                        if (this.a) {
                            this.a(line2);
                        }
                        b = true;
                        break;
                    }
                    if (4 == j && n2 == 0) {
                        if (line2.startsWith("Proc-Type")) {
                            if (-1 == line2.indexOf("ENCRYPTED")) {
                                throw new IOException("Unencrypted RSA PRIVATE KEY is not supported.");
                            }
                            continue;
                        }
                        else if (line2.startsWith("DEK-Info")) {
                            final StringTokenizer stringTokenizer = new StringTokenizer(line2, " ,");
                            stringTokenizer.nextToken();
                            this.h = stringTokenizer.nextToken();
                            this.i = stringTokenizer.nextToken();
                            if (!this.a) {
                                continue;
                            }
                            this.a("RSA Private Key algorithm: " + this.h);
                            this.a("RSA Private Key IV: " + this.i);
                        }
                        else {
                            if (0 != line2.length()) {
                                continue;
                            }
                            n2 = 1;
                        }
                    }
                    else {
                        if (this.a) {
                            this.a(line2);
                        }
                        sb.append(line2);
                    }
                }
                if (!b) {
                    throw new IOException("Could not find matching " + RSAKeyEntry.q[j]);
                }
                try {
                    if (this.a) {
                        this.a("Converting PEM type " + j + " to binary...");
                    }
                    switch (j) {
                        case 1:
                        case 2: {
                            this.b(new BufferedInputStream(new ByteArrayInputStream(Base64.decode(sb.toString()))));
                            break;
                        }
                        case 3: {
                            if (null == this.e && null == this.d) {
                                this.g = true;
                                this.a(new BufferedInputStream(new ByteArrayInputStream(Base64.decode(sb.toString()))));
                                break;
                            }
                            if (this.b) {
                                this.a("A private key has already been defined, skipping encrypted private key");
                                break;
                            }
                            throw new IOException("A private key has already been defined, skipping encrypted private key");
                        }
                        case 4: {
                            if (null == this.d && null == this.d) {
                                this.g = false;
                                this.d = Base64.decode(sb.toString());
                                break;
                            }
                            if (this.b) {
                                this.a("A private key has already been defined, skipping RSA private key");
                                break;
                            }
                            throw new IOException("A private key has already been defined, skipping RSA private key");
                        }
                        case 5: {
                            throw new IOException("Unsupported PEM type:  DSA");
                        }
                        default: {
                            if (this.b) {
                                this.a("Cannot convert unknown PEM type to binary : " + j);
                                break;
                            }
                            throw new IOException("Unknown PEM type : " + j);
                        }
                    }
                    ++n;
                }
                catch (InvalidCertificateException ex) {
                    throw ex;
                }
                catch (Exception ex2) {
                    if (!this.b) {
                        throw new IOException("Cannot convert type " + j + " key/PEM certificate to binary : " + ex2.toString());
                    }
                    this.a("Cannot convert PEM type " + j + " key/certificate to binary : " + ex2.toString());
                    if (!this.a) {
                        continue;
                    }
                    ex2.printStackTrace(this.m_printStream);
                }
            }
        }
        return 0 < n;
    }
    
    protected void a() throws IOException {
        final byte[] array = new byte[16];
        System.out.print("Private key password? ");
        System.out.flush();
        System.in.read(array, 0, 16);
        this.f = new String(array);
        for (int i = 0; i < 16; ++i) {
            array[i] = 0;
        }
        if (this.a) {
            System.out.println("Using password: " + this.f);
        }
    }
    
    public void addCertificate(final psc_c2 psc_c2, final String s) throws IOException {
        if (this.g) {
            psc_c2.a(this.certificates(), this.pkcs8Key(), s.toCharArray());
        }
        else {
            try {
                this.pkcs1Key(s);
                psc_c2.a(this.certificates(), psc_dt.a(this.e, 0, "Java"));
            }
            catch (psc_ap psc_ap) {
                throw new IOException(psc_ap.getMessage());
            }
        }
    }
    
    public void run(final String[] array) throws Exception {
        if (0 == array.length) {
            this.a("usage: KeyCertLoader <keycertfile>.pem [password]");
        }
        else {
            if (null != System.getProperty("debug")) {
                this.a = true;
            }
            if (2 > array.length) {
                throw new IOException("Insufficient key-path and password arguments");
            }
            this.loadKeyEntry(array[0]);
            final psc_c2 psc_c2 = new psc_c2();
            if (null != System.getProperty("debugssl")) {
                psc_c2.c(5);
            }
            psc_c2.a(new psc_dw[] { new psc_du() });
            psc_c2.a(new int[] { 768 });
            if (this.c.size() > 0) {
                if (null != this.e || null != this.d) {
                    if (this.a) {
                        System.out.println("Adding X509Certificate array and private key to SSLParams...");
                    }
                    if (this.g) {
                        psc_c2.a(this.certificates(), this.pkcs8Key(), array[1].toCharArray());
                    }
                    else {
                        this.pkcs1Key(array[1]);
                        psc_c2.a(this.certificates(), psc_dt.a(this.e, 0, "Java"));
                    }
                    if (this.a) {
                        System.out.println("Creating server socket on port 4443...");
                    }
                    final psc_dx psc_dx = new psc_dx(4443, 5, psc_c2);
                    if (this.a) {
                        System.out.println("Accepting connection on port 4443...");
                    }
                    if (this.a) {
                        System.out.println("    SSLServerSocket: " + psc_dx.getClass().getName());
                    }
                    final psc_dy psc_dy = (psc_dy)psc_dx.accept();
                    if (this.a) {
                        System.out.println("    Socket: " + psc_dy.getClass().getName());
                    }
                    final byte[] array2 = new byte[1024];
                    final InputStream inputStream = psc_dy.getInputStream();
                    if (this.a) {
                        System.out.println("    InputStream: " + inputStream.getClass().getName());
                    }
                    if (this.a) {
                        final psc_d2 b = psc_dy.b();
                        if (null != b) {
                            System.out.println("    SSLSession:");
                            System.out.println("        Peer:      " + b.h());
                            final psc_dw l = b.l();
                            if (null != l) {
                                System.out.println("        Cipher:    " + l.i());
                            }
                            else {
                                System.out.println("        No Cipher Suite??");
                            }
                            final psc_e[] k = b.k();
                            if (null != k) {
                                for (int i = 0; i < k.length; ++i) {
                                    System.out.println("        Client " + i + " Subject: " + k[i].f().toString());
                                    System.out.println("        Client " + i + " Issuer: " + k[i].g().toString());
                                }
                            }
                            else {
                                System.out.println("        No Client Certificate??");
                            }
                        }
                        else {
                            System.out.println("    No SSLSession!");
                        }
                    }
                    while (true) {
                        final int read = inputStream.read(array2);
                        if (-1 == read) {
                            break;
                        }
                        System.out.println("Received " + read + " bytes...");
                        if (0 >= read) {
                            continue;
                        }
                        System.out.println("Received: " + new String(array2));
                    }
                    System.out.println("InputStream closed by peer.");
                    psc_dy.close();
                    psc_dx.close();
                }
                else {
                    System.out.println("No private keys loaded, cannot set SSLParams");
                }
            }
            else {
                System.out.println("No Certificates loaded, cannot set SSLParams");
            }
        }
    }
    
    public void setDebugWriter(final PrintWriter printStream) {
        if (printStream == null) {
            return;
        }
        this.m_printStream = printStream;
    }
    
    public static void main(final String[] array) {
        final RSAKeyEntry rsaKeyEntry = new RSAKeyEntry();
        try {
            rsaKeyEntry.run(array);
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            ex.printStackTrace(System.out);
        }
    }
    
    static {
        p = new String[] { "", "-BEGIN CERTIFICATE-", "-BEGIN X509 CERTIFICATE-", "-BEGIN ENCRYPTED PRIVATE KEY-", "-BEGIN RSA PRIVATE KEY-", "-BEGIN DSA PRIVATE KEY-" };
        q = new String[] { "", "-END CERTIFICATE-", "-END X509 CERTIFICATE-", "-END ENCRYPTED PRIVATE KEY-", "-END RSA PRIVATE KEY-", "-END DSA PRIVATE KEY-" };
    }
}
