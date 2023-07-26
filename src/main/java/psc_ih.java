import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ih extends psc_h6
{
    boolean a;
    
    public psc_ih(final InputStream inputStream, final Socket socket, final boolean a, final psc_dw psc_dw, final long n) throws IOException {
        super(socket, inputStream, n);
        this.a = a;
        super.a(psc_dw);
    }
}
