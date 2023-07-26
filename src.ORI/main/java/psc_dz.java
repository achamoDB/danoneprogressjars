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

public class psc_dz implements psc_d0
{
    private psc_c2 a;
    InputStream b;
    OutputStream c;
    
    public psc_dz(final psc_c2 a) {
        this.a = a;
    }
    
    public psc_d1 a(final InputStream inputStream, final OutputStream outputStream, final String s, final Socket socket) throws InterruptedIOException, psc_d, psc_b {
        boolean b = false;
        boolean b2 = false;
        boolean b3 = false;
        final psc_d1 psc_d1 = new psc_d1();
        final int[] m = this.a.m();
        for (int i = 0; i < m.length; ++i) {
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
        if ((this.a.v() & 0x1) == 0x1) {
            this.a.z().println("STATE: Receiving Client Hello");
        }
        int a;
        try {
            a = psc_gy.a(socket, inputStream);
        }
        catch (IOException ex5) {
            throw new psc_d("Connection closed before handshake could be established");
        }
        if ((this.a.v() & 0x2) == 0x2) {
            this.a.z().println("DATA: " + psc_c9.a(a, 1));
        }
        if (a == 22) {
            if ((this.a.v() & 0x4) == 0x4) {
                this.a.z().println("First byte of header indicates either SSLV3 or TLSV1");
            }
            try {
                psc_gy.a(socket, inputStream);
                psc_gy.a(socket, inputStream);
                final int n = 0 + (psc_gy.a(socket, inputStream) << 8) + psc_gy.a(socket, inputStream);
                final byte[] array = new byte[n];
                psc_gy.a(socket, inputStream, array, 0, n);
                final int n2 = array[4] << 8 | (array[5] & 0xFF);
                if (n2 == 769 && b3) {
                    final psc_hj a2 = psc_gz.a(array, inputStream, outputStream, s, socket, this.a, psc_d1);
                    this.b = a2.a();
                    this.c = a2.b();
                }
                else {
                    if (n2 < 768 || !b2) {
                        inputStream.close();
                        outputStream.close();
                        throw new psc_d("Unknown protocol: " + psc_c9.a(n2, 2));
                    }
                    final psc_hj a3 = psc_hk.a(array, inputStream, outputStream, s, socket, this.a, psc_d1);
                    this.b = a3.a();
                    this.c = a3.b();
                }
                return psc_d1;
            }
            catch (psc_b psc_b) {
                throw psc_b;
            }
            catch (psc_c psc_c) {
                throw psc_c;
            }
            catch (IOException ex) {
                throw new psc_d(ex.getMessage());
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
                throw new psc_d(ex2.getMessage());
            }
        }
        if (a >= 128) {
            if ((this.a.v() & 0x4) == 0x4) {
                this.a.z().println("First byte of header indicates SSLV2");
            }
            try {
                final int n3 = 0 + psc_gy.a(socket, inputStream);
                final byte[] array2 = new byte[n3];
                psc_gy.a(socket, inputStream, array2, 0, n3);
                final psc_h5 a4 = psc_h2.a(array2, this.a);
                if (a4.f() == 769 && b3) {
                    final psc_hj a5 = psc_gz.a(a4, inputStream, outputStream, s, socket, this.a, psc_d1);
                    this.b = a5.a();
                    this.c = a5.b();
                }
                else if (a4.f() >= 768 && b2) {
                    final psc_hj a6 = psc_hk.a(a4, inputStream, outputStream, s, socket, this.a, psc_d1);
                    this.b = a6.a();
                    this.c = a6.b();
                }
                else {
                    if (!b) {
                        inputStream.close();
                        outputStream.close();
                        throw new psc_d("Unknown protocol: " + psc_c9.a(a4.f(), 2));
                    }
                    final psc_hj a7 = psc_h2.a(a4, inputStream, outputStream, s, socket, this.a, psc_d1);
                    this.b = a7.a();
                    this.c = a7.b();
                }
                return psc_d1;
            }
            catch (psc_b psc_b2) {
                throw psc_b2;
            }
            catch (IOException ex3) {
                throw new psc_d(ex3.getMessage());
            }
            catch (Exception ex4) {
                ex4.printStackTrace();
                throw new psc_d(ex4.getMessage());
            }
        }
        throw new psc_d("Bad hello.  Probably not an SSL connection");
    }
    
    public static psc_d1 a(final psc_c2 psc_c2, final int n, final InputStream inputStream, final OutputStream outputStream, final psc_d2 psc_d2, final String s) throws psc_d, psc_b {
        switch (n) {
            case 2: {
                throw new psc_d("Renegotiation not supported with SSL Version 2");
            }
            case 768: {
                return psc_hk.a(psc_c2, inputStream, outputStream, psc_d2, s);
            }
            case 769: {
                return psc_gz.a(psc_c2, inputStream, outputStream, psc_d2, s);
            }
            default: {
                throw new psc_d("Could not renegotiate because we can not determine version");
            }
        }
    }
    
    public InputStream a() {
        return this.b;
    }
    
    public OutputStream b() {
        return this.c;
    }
    
    public psc_d1 a(InputStream in, OutputStream out, final Socket socket) throws SocketException, InterruptedIOException, psc_d, psc_b {
        final int n = 1024;
        if (!(in instanceof BufferedInputStream)) {
            in = new BufferedInputStream(in, n);
        }
        if (!(out instanceof BufferedOutputStream)) {
            out = new BufferedOutputStream(out, n);
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
