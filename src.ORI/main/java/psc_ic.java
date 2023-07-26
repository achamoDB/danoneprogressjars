import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ic extends psc_h4
{
    private byte[] a;
    
    public psc_ic() {
        super(5);
    }
    
    public psc_ic(final byte[] array) {
        super(5);
        System.arraycopy(array, 0, this.a = new byte[array.length], 0, array.length);
    }
    
    public void b(final psc_h6 psc_h6) throws IOException, psc_g8 {
        if (psc_h6.read() != 5) {
            throw new psc_g8("Not expecting handshake type");
        }
        final int f = psc_h6.f();
        psc_h6.read(this.a = new byte[f - 1], 0, f - 1);
    }
    
    public void a(final psc_h7 psc_h7) throws IOException, psc_g8 {
        psc_h7.write(5);
        psc_h7.write(this.a, 0, this.a.length);
        psc_h7.flush();
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[SERVER VERIFY]:\n");
        sb.append("[SERVER VERIFY / ChallengeData]: \n");
        sb.append(psc_c9.a(this.a));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
