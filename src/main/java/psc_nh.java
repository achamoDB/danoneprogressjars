import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nh extends psc_hm
{
    private int a;
    
    public psc_nh() {
        super(0);
    }
    
    public void a(final psc_ho psc_ho) throws IOException, psc_g8 {
        psc_ho.a(22);
        psc_ho.b(768);
        psc_ho.write(0);
        psc_ho.d(0);
        psc_ho.flush();
    }
    
    public void b(final psc_hn psc_hn) throws IOException, psc_g8 {
        if (psc_hn.read() != 0) {
            throw new psc_g8("Not expecting handshake type");
        }
        this.a = psc_hn.c();
    }
    
    public String toString() {
        return new String("[HELLO REQUEST]: \n\n");
    }
}
