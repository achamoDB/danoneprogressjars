import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hb extends psc_g1
{
    private byte[] a;
    private byte[] b;
    private int c;
    
    public psc_hb() {
        super(13);
    }
    
    public psc_hb(final byte[] array, final byte[] array2) {
        super(13);
        this.c = 1 + array.length + 2 + array2.length;
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
        System.arraycopy(array2, 0, this.b = new byte[array2.length], 0, array2.length);
    }
    
    public void b(final psc_g2 psc_g2) throws IOException, psc_g8 {
        this.c = psc_g2.c();
        final int read = psc_g2.read();
        psc_g2.read(this.a = new byte[read], 0, read);
        final int b = psc_g2.b();
        psc_g2.read(this.b = new byte[b], 0, b);
    }
    
    public void a(final psc_g3 psc_g3) throws IOException, psc_g8 {
        final int n = 1 + this.a.length + 2 + this.b.length;
        psc_g3.a(22);
        psc_g3.b(769);
        psc_g3.write(13);
        psc_g3.d(n);
        psc_g3.write(this.a.length);
        psc_g3.write(this.a, 0, this.a.length);
        psc_g3.c(this.b.length);
        psc_g3.write(this.b, 0, this.b.length);
        psc_g3.flush();
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
        sb.append("[CERTIFICATE REQUEST]:\n");
        sb.append("[CERTIFICATE REQUEST / Certificate Type List]: \n" + psc_c9.a(this.a));
        sb.append("\n");
        sb.append("[CERTIFICATE REQUEST / Certificate Authorities]: \n" + psc_c9.a(this.b));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
