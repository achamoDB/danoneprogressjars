import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ij extends psc_h7
{
    public psc_ij(final OutputStream outputStream, final Socket socket, final psc_dw psc_dw, final long n) throws IOException {
        super(outputStream, n);
        super.a(psc_dw);
    }
    
    public void write(final int n) throws IOException {
        super.write(n);
        this.flush();
    }
    
    public void write(final byte[] array) throws IOException {
        super.write(array);
        this.flush();
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        super.write(array, n, n2);
        this.flush();
    }
    
    public void flush() throws IOException {
        super.flush();
    }
}
