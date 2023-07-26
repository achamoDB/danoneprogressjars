import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_aw extends pscl_ap
{
    private byte[] a;
    private int b;
    
    public pscl_aw() {
        super(11);
    }
    
    public pscl_aw(final byte[] array) {
        super(11);
        this.b = 3 + array.length;
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
    }
    
    public void a(final pscl_ar pscl_ar) throws IOException, pscl_av {
        if (pscl_au.a(pscl_ar) != 11) {
            throw new pscl_av("Not expecting handshake type");
        }
        this.b = pscl_au.c(pscl_ar);
        final int c = pscl_au.c(pscl_ar);
        pscl_au.a(pscl_ar, this.a = new byte[c], 0, c);
    }
    
    public void a(final pscl_as pscl_as) throws IOException {
        pscl_as.a(22);
        pscl_as.b(768);
        pscl_as.write(11);
        pscl_as.d(this.b);
        pscl_as.d(this.a.length);
        pscl_as.write(this.a, 0, this.a.length);
        pscl_as.flush();
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public int b() {
        return this.b;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CERTIFICATE]: \n");
        sb.append("[CERTIFICATE / certificateList]: \n" + pscl_k.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
