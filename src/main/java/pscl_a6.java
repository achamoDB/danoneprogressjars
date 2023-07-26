import java.io.IOException;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a6 extends pscl_as
{
    public OutputStream a;
    public pscl_n b;
    public boolean c;
    
    public pscl_a6(final OutputStream a, final pscl_d pscl_d, final pscl_d pscl_d2, final pscl_n b, final pscl_i pscl_i, final pscl_bn pscl_bn) throws Exception {
        super(a, pscl_d, pscl_d2);
        this.a = a;
        this.b = b;
        super.a(pscl_i);
        super.a(pscl_bn);
        this.c = false;
    }
    
    public void flush() throws IOException {
        if (!this.c) {
            super.a(23);
        }
        super.flush();
    }
    
    public void close() throws IOException {
        this.c = true;
        super.close();
    }
}
