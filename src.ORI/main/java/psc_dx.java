import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.ServerSocket;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_dx extends ServerSocket
{
    psc_c2 a;
    
    public psc_dx(final int port, final psc_c2 a) throws IOException {
        super(port);
        this.a = a;
    }
    
    public psc_dx(final int port, final int backlog, final psc_c2 a) throws IOException {
        super(port, backlog);
        this.a = a;
    }
    
    public psc_dx(final int port, final int backlog, final InetAddress bindAddr, final psc_c2 a) throws IOException {
        super(port, backlog, bindAddr);
        this.a = a;
    }
    
    public Socket accept() throws IOException {
        final psc_dy s = new psc_dy(this.a);
        super.implAccept(s);
        return s;
    }
    
    public InetAddress getInetAddress() {
        return super.getInetAddress();
    }
    
    public int getLocalPort() {
        return super.getLocalPort();
    }
    
    public void a(final psc_c2 a) {
        this.a = a;
    }
    
    public void close() throws IOException {
        super.close();
    }
}
