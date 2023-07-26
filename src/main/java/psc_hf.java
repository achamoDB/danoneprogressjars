import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hf extends psc_g1
{
    private byte[] a;
    private int b;
    
    public psc_hf() {
        super(20);
    }
    
    public psc_hf(final byte[] array) {
        super(20);
        this.b = 12;
        System.arraycopy(array, 0, this.a = new byte[12], 0, 12);
    }
    
    public void b(final psc_g2 psc_g2) throws IOException, psc_g8 {
        if (psc_g2.read() != 20) {
            throw new psc_g8("Not expecting handshake type");
        }
        this.b = psc_g2.c();
        psc_g2.read(this.a = new byte[12], 0, 12);
    }
    
    public void a(final psc_g3 psc_g3) throws IOException, psc_g8 {
        psc_g3.a(22);
        psc_g3.b(769);
        psc_g3.write(20);
        psc_g3.d(this.b);
        psc_g3.write(this.a, 0, 12);
        psc_g3.flush();
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public int b() {
        return this.b;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[HANDSHAKE FINISHED]:\n");
        sb.append("[HANDSHAKE FINISHED / Verify Data]: \n" + psc_c9.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
