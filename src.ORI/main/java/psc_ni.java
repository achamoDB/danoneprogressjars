import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ni extends psc_g1
{
    private int a;
    
    public psc_ni() {
        super(0);
    }
    
    public void a(final psc_g3 psc_g3) throws IOException, psc_g8 {
        psc_g3.a(22);
        psc_g3.b(769);
        psc_g3.write(0);
        psc_g3.d(0);
        psc_g3.flush();
    }
    
    public void b(final psc_g2 psc_g2) throws IOException, psc_g8 {
        if (psc_g2.read() != 0) {
            throw new psc_g8("Not expecting handshake type");
        }
        this.a = psc_g2.c();
    }
    
    public String toString() {
        return new String("[HELLO REQUEST]: \n\n");
    }
}
