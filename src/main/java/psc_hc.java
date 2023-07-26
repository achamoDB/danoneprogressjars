import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hc extends psc_g1
{
    private int a;
    
    public psc_hc() {
        super(14);
    }
    
    public void b(final psc_g2 psc_g2) throws IOException {
        this.a = psc_g2.c();
    }
    
    public void a(final psc_g3 psc_g3) throws IOException, psc_g8 {
        psc_g3.a(22);
        psc_g3.b(769);
        psc_g3.write(14);
        psc_g3.d(0);
        psc_g3.flush();
    }
    
    public int a() {
        return this.a;
    }
    
    public String toString() {
        return new String("[SERVER HELLO DONE]: \n\n");
    }
}
