import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ii extends psc_h7
{
    public psc_ii(final OutputStream outputStream, final Socket socket, final psc_dw psc_dw, final long n) throws IOException {
        super(outputStream, n);
        super.a(psc_dw);
    }
    
    public void flush() throws IOException {
        super.flush();
    }
}
