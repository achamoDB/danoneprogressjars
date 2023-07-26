import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hv extends psc_hm
{
    private int a;
    
    public psc_hv() {
        super(14);
    }
    
    public void b(final psc_hn psc_hn) throws IOException {
        this.a = psc_hn.c();
    }
    
    public void a(final psc_ho psc_ho) throws IOException, psc_g8 {
        psc_ho.a(22);
        psc_ho.b(768);
        psc_ho.write(14);
        psc_ho.d(0);
        psc_ho.flush();
    }
    
    public int a() {
        return this.a;
    }
    
    public String toString() {
        return new String("[SERVER HELLO DONE]: \n\n");
    }
}
