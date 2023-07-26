import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hs extends psc_hm
{
    private byte[] a;
    private int b;
    
    public psc_hs() {
        super(11);
    }
    
    public psc_hs(final byte[] array) {
        super(11);
        this.b = 3 + array.length;
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
    }
    
    public void b(final psc_hn psc_hn) throws IOException, psc_g8 {
        if (psc_hn.read() != 11) {
            throw new psc_g8("Not expecting handshake type");
        }
        this.b = psc_hn.c();
        final int c = psc_hn.c();
        psc_hn.read(this.a = new byte[c], 0, c);
    }
    
    public void a(final psc_ho psc_ho) throws IOException {
        psc_ho.a(22);
        psc_ho.b(768);
        psc_ho.write(11);
        psc_ho.d(this.b);
        psc_ho.d(this.a.length);
        psc_ho.write(this.a, 0, this.a.length);
        psc_ho.flush();
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
        sb.append("[CERTIFICATE / certificateList]: \n" + psc_c9.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
