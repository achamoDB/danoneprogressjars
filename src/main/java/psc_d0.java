import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public interface psc_d0
{
    psc_d1 b(final psc_c2 p0, final int p1, final InputStream p2, final OutputStream p3, final psc_d2 p4, final String p5) throws psc_d, psc_b;
    
    psc_d1 a(final InputStream p0, final OutputStream p1, final Socket p2) throws SocketException, InterruptedIOException, psc_d, psc_b;
    
    InputStream c();
    
    OutputStream d();
}
