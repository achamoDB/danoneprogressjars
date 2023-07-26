import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hx extends psc_hm
{
    private byte[] a;
    private int b;
    
    public psc_hx() {
        super(15);
    }
    
    public psc_hx(final byte[] array) {
        super(15);
        this.b = array.length;
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
    }
    
    public void b(final psc_hn psc_hn) throws IOException, psc_g8 {
        if (psc_hn.read() != 15) {
            throw new psc_g8("Not expecting handshake type");
        }
        this.b = psc_hn.c();
        psc_hn.read(this.a = new byte[this.b], 0, this.b);
    }
    
    public void a(final psc_ho psc_ho) throws IOException, psc_g8 {
        psc_ho.a(22);
        psc_ho.b(768);
        psc_ho.write(15);
        psc_ho.d(this.a.length + 2);
        psc_ho.c(this.a.length);
        psc_ho.write(this.a, 0, this.a.length);
        psc_ho.flush();
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
        sb.append("[CERTIFICATE VERIFY / Signature]: \n" + psc_c9.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
