import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a4 extends pscl_ap
{
    private byte[] a;
    private int b;
    
    public pscl_a4() {
        super(15);
    }
    
    public pscl_a4(final byte[] array) {
        super(15);
        this.b = array.length;
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
    }
    
    public void a(final pscl_ar pscl_ar) throws IOException, pscl_av {
        if (pscl_au.a(pscl_ar) != 15) {
            throw new pscl_av("Not expecting handshake type");
        }
        this.b = pscl_au.c(pscl_ar);
        pscl_au.a(pscl_ar, this.a = new byte[this.b], 0, this.b);
    }
    
    public void a(final pscl_as pscl_as) throws IOException, pscl_av {
        pscl_as.a(22);
        pscl_as.b(768);
        pscl_as.write(15);
        pscl_as.d(this.a.length + 2);
        pscl_as.c(this.a.length);
        pscl_as.write(this.a, 0, this.a.length);
        pscl_as.flush();
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public int b() {
        return this.b;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CERTIFICATE VERIFY]: \n");
        sb.append("[CERTIFICATE VERIFY / Signature]: \n" + pscl_k.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
