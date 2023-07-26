import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a1 extends pscl_ap
{
    private int a;
    
    public pscl_a1() {
        super(14);
    }
    
    public void a(final pscl_ar pscl_ar) throws IOException {
        this.a = pscl_au.c(pscl_ar);
    }
    
    public int a() {
        return this.a;
    }
    
    public String toString() {
        return new String("[SERVER HELLO DONE]: \n\n");
    }
}
