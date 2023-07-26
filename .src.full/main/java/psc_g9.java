import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_g9 extends psc_g1
{
    private byte[] a;
    private int b;
    
    public psc_g9() {
        super(11);
    }
    
    public psc_g9(final byte[] array) {
        super(11);
        this.b = 3 + array.length;
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
    }
    
    public void b(final psc_g2 psc_g2) throws IOException, psc_g8 {
        if (psc_g2.read() != 11) {
            throw new psc_g8("Not expecting handshake type");
        }
        this.b = psc_g2.c();
        final int c = psc_g2.c();
        psc_g2.read(this.a = new byte[c], 0, c);
    }
    
    public void a(final psc_g3 psc_g3) throws IOException {
        psc_g3.a(22);
        psc_g3.b(769);
        psc_g3.write(11);
        psc_g3.d(this.b);
        psc_g3.d(this.a.length);
        psc_g3.write(this.a, 0, this.a.length);
        psc_g3.flush();
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public int b() {
        return this.b;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CERTIFICATE]:\n");
        sb.append("[CERTIFICATE / CertficateList:] \n" + psc_c9.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
