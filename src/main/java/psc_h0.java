import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h0 extends psc_ho
{
    boolean a;
    
    public psc_h0(final OutputStream outputStream, final psc_da psc_da, final psc_da psc_da2, final Socket socket, final psc_dw psc_dw, final psc_c5 psc_c5) throws IOException {
        super(outputStream, psc_da, psc_da2);
        super.a(psc_dw);
        super.a(psc_c5);
        this.a = false;
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
