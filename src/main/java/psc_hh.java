import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hh extends psc_g3
{
    boolean a;
    
    public psc_hh(final OutputStream outputStream, final psc_da psc_da, final psc_da psc_da2, final Socket socket, final psc_dw psc_dw, final psc_c5 psc_c5) throws IOException {
        super(outputStream, psc_da, psc_da2);
        this.a = false;
        super.a(psc_dw);
        super.a(psc_c5);
        super.b(769);
    }
    
    public void flush() throws IOException {
        if (!this.a) {
            super.a(23);
        }
        super.flush();
    }
    
    public void close() throws IOException {
        this.a = true;
        super.close();
    }
}
