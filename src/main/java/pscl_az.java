import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_az extends pscl_ap
{
    private byte[] a;
    private int b;
    
    public pscl_az() {
        super(12);
    }
    
    public void a(final pscl_ar pscl_ar) throws IOException, pscl_av {
        if (pscl_au.a(pscl_ar) != 12) {
            throw new pscl_av("Not expecting handshake type");
        }
        this.b = pscl_au.c(pscl_ar);
        pscl_au.a(pscl_ar, this.a = new byte[this.b], 0, this.b);
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public int b() {
        return this.b;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[SERVER KEY EXCHANGE]: \n");
        sb.append("[SERVER KEY EXCHANGE / Server ExchangeKeys]: \n" + pscl_k.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
