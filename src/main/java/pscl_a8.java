import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a8 extends pscl_ar
{
    public InputStream a;
    public pscl_as b;
    public pscl_n c;
    public pscl_b d;
    public byte[] e;
    
    public pscl_a8(final InputStream a, final pscl_as b, final pscl_d pscl_d, final pscl_d pscl_d2, final pscl_n c, final boolean b2, final pscl_b d, final pscl_i pscl_i, final pscl_bn pscl_bn) throws Exception {
        super(a, pscl_d, pscl_d2);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        super.a(pscl_i);
        super.a(pscl_bn);
    }
    
    public int b() throws IOException {
        int b;
        try {
            b = super.b();
        }
        catch (pscl_ah pscl_ah) {
            if (pscl_ah.b() == 0) {
                this.a.close();
                this.b.e().close();
                this.c.close();
                return -1;
            }
            throw pscl_ah;
        }
        if (b == -1) {
            return -1;
        }
        switch (super.g) {
            case 22: {
                try {
                    if (!this.d.g()) {
                        this.b.a(21);
                        this.b.write(1);
                        this.b.write(100);
                        this.b.flush();
                        this.b.a(23);
                    }
                    if (this.read() == 0) {
                        this.c.a(this.d);
                    }
                }
                catch (pscl_h pscl_h) {
                    throw new IOException(pscl_h.getMessage());
                }
            }
            case 23: {
                break;
            }
            case 21: {
                return -1;
            }
            default: {
                throw new IOException("Unexpected message");
            }
        }
        return b;
    }
}
