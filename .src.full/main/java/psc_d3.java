import java.net.SocketException;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_d3 implements psc_d0
{
    private psc_c2 a;
    InputStream b;
    OutputStream c;
    psc_da d;
    psc_da e;
    String f;
    psc_d2 g;
    
    public psc_d3(final psc_c2 a) throws psc_d {
        this.a = a;
        try {
            (this.d = psc_da.a("MD5", "Java")).i();
            (this.e = psc_da.a("SHA1", "Java")).i();
        }
        catch (psc_ap psc_ap) {
            throw new psc_d("An error in the crypto-module occured during initialization");
        }
    }
    
    public psc_d1 a(final InputStream inputStream, final OutputStream outputStream, final String f, final Socket socket) throws InterruptedIOException, psc_d, psc_b {
        this.f = f;
        final byte[] array = new byte[0];
        final int[] m = this.a.m();
        boolean b = false;
        boolean b2 = false;
        boolean b3 = false;
        int n = 0;
        final psc_d1 psc_d1 = new psc_d1();
        for (int i = 0; i < m.length; ++i) {
            if (m[i] > n) {
                n = m[i];
            }
            if (m[i] == 2) {
                b = true;
            }
            else if (m[i] == 768) {
                b2 = true;
            }
            else if (m[i] == 769) {
                b3 = true;
            }
        }
        psc_h5 psc_h5 = null;
        byte[] b5;
        try {
            boolean b4 = false;
            this.g = this.a.c(f);
            if (this.g != null) {
                b4 = true;
                final int c = this.g.c();
                if (c == 2) {
                    psc_h5 = psc_h2.a(2, outputStream, this.a, f, this.d, this.e, this.g);
                }
                else if (c == 768) {
                    psc_h5 = psc_hk.a(768, outputStream, this.a, f, this.d, this.e, this.g);
                }
                else if (c == 769) {
                    psc_h5 = psc_gz.a(769, outputStream, this.a, f, this.d, this.e, this.g);
                }
                else {
                    this.a.c(this.g.e());
                    b4 = false;
                }
            }
            if (!b4) {
                if (b) {
                    psc_h5 = psc_h2.a(n, outputStream, this.a, f, this.d, this.e, this.g);
                }
                else if (b2) {
                    psc_h5 = psc_hk.a(n, outputStream, this.a, f, this.d, this.e, this.g);
                }
                else if (b3) {
                    psc_h5 = psc_gz.a(n, outputStream, this.a, f, this.d, this.e, this.g);
                }
            }
            b5 = psc_h5.b();
        }
        catch (IOException ex) {
            if (psc_h5 != null) {
                this.a.c(psc_h5.c());
            }
            throw new psc_d("An IOException occured while saying hello: " + ex.getMessage());
        }
        int a;
        try {
            a = psc_gy.a(socket, inputStream);
        }
        catch (IOException ex2) {
            this.a.c(psc_h5.c());
            throw new psc_d("Connection closed before handshake could be established: " + ex2.getMessage());
        }
        if (a == -1) {
            this.a.c(psc_h5.c());
            throw new psc_d("Could not establish connection");
        }
        if ((this.a.v() & 0x2) == 0x2) {
            this.a.z().println("DATA: " + psc_c9.a(a, 1));
        }
        if (a == 22) {
            if ((this.a.v() & 0x4) == 0x4) {
                this.a.z().println("First byte of header indicates either SSLV3 or TLSV1");
            }
            try {
                final byte[] array2 = new byte[5];
                final int n2 = 1;
                array2[0] = 22;
                psc_gy.a(socket, inputStream, array2, 1, array2.length - n2);
                final int n3 = array2[1] << 8 | array2[2];
                if (n3 == 768 && b2) {
                    final psc_hj a2 = psc_hk.a(inputStream, outputStream, f, socket, array2, b5, n, this.a, this.g, this.d, this.e, psc_d1);
                    this.b = a2.a();
                    this.c = a2.b();
                    psc_d1.a(768);
                }
                else {
                    if (n3 != 769 || !b3) {
                        inputStream.close();
                        outputStream.close();
                        throw new psc_d("Unknown protocol version " + psc_c9.a(n3, 3));
                    }
                    final psc_hj a3 = psc_gz.a(inputStream, outputStream, f, socket, array2, b5, n, this.a, this.g, this.d, this.e, psc_d1);
                    this.b = a3.a();
                    this.c = a3.b();
                    psc_d1.a(769);
                }
                return psc_d1;
            }
            catch (psc_b psc_b) {
                throw psc_b;
            }
            catch (psc_c psc_c) {
                throw new psc_b(psc_c.getMessage(), psc_c.a(), psc_c.b());
            }
            catch (IOException ex3) {
                ex3.printStackTrace();
                this.a.c(psc_h5.c());
                throw new psc_d(ex3.getMessage());
            }
            catch (Exception ex4) {
                ex4.printStackTrace();
                this.a.c(psc_h5.c());
                throw new psc_d("Bad hello.  Probably not an SSL connection");
            }
        }
        if (a == 21) {
            try {
                psc_gy.a(socket, inputStream, new byte[4]);
                final int a4 = psc_gy.a(socket, inputStream);
                final int a5 = psc_gy.a(socket, inputStream);
                throw new psc_b(psc_b.a(a5), a4, a5);
            }
            catch (psc_b psc_b2) {
                throw psc_b2;
            }
            catch (IOException ex6) {
                throw new psc_d("Could not establish the SSL connection");
            }
        }
        if (a >= 128 && b) {
            try {
                final psc_hj a6 = psc_h2.a(inputStream, outputStream, f, socket, psc_h5, n, this.a, new byte[] { (byte)a, (byte)psc_gy.a(socket, inputStream) }, psc_d1);
                this.b = a6.a();
                this.c = a6.b();
                psc_d1.a(2);
                return psc_d1;
            }
            catch (psc_c psc_c2) {
                throw new psc_b(psc_c2.getMessage(), psc_c2.a(), psc_c2.b());
            }
            catch (psc_d psc_d2) {
                this.a.c(psc_h5.c());
                psc_d2.fillInStackTrace();
                throw psc_d2;
            }
            catch (Exception ex5) {
                this.a.c(psc_h5.c());
                throw new psc_d("Bad hello.  Probably not an SSL connection.\n" + ex5.getMessage());
            }
        }
        try {
            inputStream.close();
            outputStream.close();
        }
        catch (IOException ex7) {}
        throw new psc_d("Unknown protocol");
    }
    
    public InputStream a() {
        return this.b;
    }
    
    public OutputStream b() {
        return this.c;
    }
    
    public static psc_d1 a(final psc_c2 psc_c2, final int n, final InputStream inputStream, final OutputStream outputStream, final psc_d2 psc_d2, final String s) throws psc_d, psc_b {
        switch (n) {
            case 2: {
                throw new psc_d("Renegotiation not supported with SSL Version 2");
            }
            case 768: {
                return psc_hk.b(psc_c2, inputStream, outputStream, psc_d2, s);
            }
            case 769: {
                return psc_gz.b(psc_c2, inputStream, outputStream, psc_d2, s);
            }
            default: {
                throw new psc_d("Could not renegotiate because we can not determine version");
            }
        }
    }
    
    public psc_d1 a(InputStream in, OutputStream out, final Socket socket) throws SocketException, InterruptedIOException, psc_d, psc_b {
        final int n = 1024;
        if (!(in instanceof BufferedInputStream)) {
            in = new BufferedInputStream(in, n);
        }
        if (!(out instanceof BufferedOutputStream)) {
            out = new BufferedOutputStream(out, n);
        }
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("connecting to " + socket.getInetAddress());
        }
        return this.a(in, out, socket.getInetAddress().getHostAddress(), socket);
    }
    
    public InputStream c() {
        return this.b;
    }
    
    public OutputStream d() {
        return this.c;
    }
    
    public psc_d1 b(final psc_c2 psc_c2, final int n, final InputStream inputStream, final OutputStream outputStream, final psc_d2 psc_d2, final String s) throws psc_d, psc_b {
        return a(psc_c2, n, inputStream, outputStream, psc_d2, s);
    }
}
