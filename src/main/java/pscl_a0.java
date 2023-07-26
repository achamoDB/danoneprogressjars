import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a0 extends pscl_ap
{
    private byte[] a;
    private byte[] b;
    private int c;
    
    public pscl_a0() {
        super(13);
    }
    
    public pscl_a0(final byte[] array, final byte[] array2) {
        super(13);
        this.c = 1 + array.length + 2 + array2.length;
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
        System.arraycopy(array2, 0, this.b = new byte[array2.length], 0, array2.length);
    }
    
    public void a(final pscl_ar pscl_ar) throws IOException, pscl_av {
        this.c = pscl_au.c(pscl_ar);
        final int a = pscl_au.a(pscl_ar);
        pscl_au.a(pscl_ar, this.a = new byte[a], 0, a);
        final int b = pscl_au.b(pscl_ar);
        pscl_au.a(pscl_ar, this.b = new byte[b], 0, b);
    }
    
    public void a(final pscl_as pscl_as) throws IOException, pscl_av {
        final int n = 1 + this.a.length + 2 + this.b.length;
        pscl_as.a(22);
        pscl_as.b(768);
        pscl_as.write(13);
        pscl_as.d(n);
        pscl_as.write(this.a.length);
        pscl_as.write(this.a, 0, this.a.length);
        pscl_as.c(this.b.length);
        pscl_as.write(this.b, 0, this.b.length);
        pscl_as.flush();
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public byte[] b() {
        return this.b;
    }
    
    public int c() {
        return this.c;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CERTIFICATE REQUEST]: \n");
        sb.append("[CERTIFICATE REQUEST / Certificate Type List]: \n" + pscl_k.a(this.a));
        sb.append("\n");
        sb.append("[CERTIFICATE REQUEST / Certificate Authorities]: \n" + pscl_k.a(this.b));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
