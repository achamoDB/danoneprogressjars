import java.net.SocketException;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_n extends Socket
{
    public static final int a = 443;
    public static final int b = 465;
    public static final int c = 563;
    private pscl_b d;
    public Socket e;
    private InputStream f;
    private OutputStream g;
    private InputStream h;
    private OutputStream i;
    private pscl_o j;
    
    public pscl_n(final String host, final int port, final pscl_b pscl_b) throws IOException, UnknownHostException {
        this(new Socket(host, port), pscl_b);
    }
    
    public pscl_n(final InetAddress address, final int port, final pscl_b pscl_b) throws IOException {
        this(new Socket(address, port), pscl_b);
    }
    
    public pscl_n(final Socket socket, final pscl_b pscl_b, final boolean b) throws IOException, UnknownHostException {
        throw new UnsupportedOperationException("SSL-J Lite is a client implementation only.");
    }
    
    public pscl_n(final Socket e, final pscl_b d) {
        this.d = d;
        this.e = e;
    }
    
    public synchronized InputStream getInputStream() throws IOException {
        if (this.f == null) {
            this.a();
        }
        return this.h;
    }
    
    public synchronized OutputStream getOutputStream() throws IOException {
        if (this.g == null) {
            this.a();
        }
        return this.i;
    }
    
    private void a() throws IOException {
        this.f = this.e.getInputStream();
        this.g = this.e.getOutputStream();
        final int n = 1024;
        if (!(this.f instanceof BufferedInputStream)) {
            this.f = new BufferedInputStream(this.f, n);
        }
        if (!(this.g instanceof BufferedOutputStream)) {
            this.g = new BufferedOutputStream(this.g, n);
        }
        try {
            this.b();
        }
        catch (pscl_h pscl_h) {
            throw new IOException("the SSL handshake failed: " + pscl_h.getMessage());
        }
    }
    
    public final pscl_i c() {
        if (this.j == null) {
            return null;
        }
        return this.j.a();
    }
    
    public final pscl_l d() {
        if (this.j == null) {
            return null;
        }
        return this.j.b();
    }
    
    public InetAddress getInetAddress() {
        return this.e.getInetAddress();
    }
    
    public InetAddress getLocalAddress() {
        return this.e.getLocalAddress();
    }
    
    public int getPort() {
        return this.e.getPort();
    }
    
    public int getLocalPort() {
        return this.e.getLocalPort();
    }
    
    public int getSoLinger() throws SocketException {
        return this.e.getSoLinger();
    }
    
    public int getSoTimeout() throws SocketException {
        return this.e.getSoTimeout();
    }
    
    public boolean getTcpNoDelay() throws SocketException {
        return this.e.getTcpNoDelay();
    }
    
    public void setSoLinger(final boolean on, final int linger) throws SocketException {
        this.e.setSoLinger(on, linger);
    }
    
    public void setSoTimeout(final int soTimeout) throws SocketException {
        this.e.setSoTimeout(soTimeout);
    }
    
    public void setTcpNoDelay(final boolean tcpNoDelay) throws SocketException {
        this.e.setTcpNoDelay(tcpNoDelay);
    }
    
    public final pscl_bn e() {
        if (this.j == null) {
            return null;
        }
        return this.j.c();
    }
    
    public pscl_j[] f() {
        if (this.j == null) {
            return null;
        }
        return this.j.d();
    }
    
    private void b() throws pscl_h, pscl_ah {
        if ((this.d.n() & 0x1) == 0x1) {
            this.d.o().println("Connecting to " + this.e.getInetAddress());
        }
        final pscl_am pscl_am = new pscl_am(this.d, this.f, this.g, this.e.getInetAddress().getHostAddress(), false);
        this.j = pscl_am.c();
        try {
            final pscl_as e = pscl_am.e();
            if (this.d.m()) {
                this.i = new pscl_a6(e.e(), e.f(), e.g(), this, pscl_am.f(), null);
                ((pscl_a6)this.i).a(this.d.n(), this.d.o());
            }
            else {
                this.i = new pscl_a7(e.e(), e.f(), e.g(), this, pscl_am.f(), null);
                ((pscl_a7)this.i).a(this.d.n(), this.d.o());
            }
            this.h = new pscl_a8(pscl_am.d().m(), e, e.f(), e.g(), this, false, this.d, pscl_am.f(), null);
            ((pscl_a8)this.h).a(this.d.n(), this.d.o());
        }
        catch (Exception ex) {
            throw new pscl_h("Could not retrieve IO streams: " + ex.getMessage());
        }
    }
    
    public void close() throws IOException {
        try {
            this.i.flush();
            this.i.close();
            super.close();
        }
        catch (Exception ex) {}
    }
    
    public void a(final pscl_b pscl_b) throws IOException {
        this.d.a(this.d());
        final pscl_aq pscl_aq = new pscl_aq();
        final pscl_c pscl_c = new pscl_c();
        pscl_aq.f();
        pscl_c.f();
        final pscl_ar pscl_ar = new pscl_ar(this.f, pscl_aq, pscl_c);
        pscl_ar.a(pscl_b.n(), pscl_b.o());
        final pscl_as pscl_as = new pscl_as(this.g, pscl_aq, pscl_c);
        pscl_as.a(pscl_b.n(), pscl_b.o());
        pscl_ar.a(((pscl_ar)this.h).i());
        pscl_ar.a(((pscl_ar)this.h).j());
        pscl_as.a(((pscl_as)this.i).c());
        pscl_as.a(((pscl_as)this.i).d());
        pscl_as.a(22);
        while (pscl_ar.available() > 0 && pscl_ar.g() == 23) {
            final byte[] array = new byte[pscl_ar.available()];
            pscl_au.a(pscl_ar, array, 0, array.length);
        }
        this.j = new pscl_am(pscl_b, pscl_ar, pscl_as, this.e.getInetAddress().getHostAddress(), true).c();
        ((pscl_ar)this.h).a(pscl_ar.i());
        ((pscl_as)this.i).a(pscl_as.c());
    }
    
    public String toString() {
        return this.e.toString();
    }
}
