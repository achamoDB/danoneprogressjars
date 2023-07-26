import java.io.IOException;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a7 extends pscl_as
{
    public OutputStream a;
    public pscl_n b;
    public boolean c;
    
    public pscl_a7(final OutputStream a, final pscl_d pscl_d, final pscl_d pscl_d2, final pscl_n b, final pscl_i pscl_i, final pscl_bn pscl_bn) throws Exception {
        super(a, pscl_d, pscl_d2);
        this.c = false;
        this.a = a;
        this.b = b;
        super.a(pscl_i);
        super.a(pscl_bn);
        super.b(768);
    }
    
    public void write(final int n) throws IOException {
        super.write(n);
        this.flush();
    }
    
    public void write(final byte[] b) throws IOException {
        super.write(b);
        this.flush();
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        super.write(array, n, n2);
        this.flush();
    }
    
    public void flush() throws IOException {
        if (!this.c) {
            super.a(23);
        }
        super.flush();
    }
    
    public void close() throws IOException {
        this.c = true;
        super.f = 21;
        super.write(1);
        super.write(0);
        super.flush();
        this.a.close();
    }
}
