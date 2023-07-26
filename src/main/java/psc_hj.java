import java.io.OutputStream;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hj
{
    private InputStream a;
    private OutputStream b;
    
    public psc_hj(final InputStream a, final OutputStream b) {
        this.a = a;
        this.b = b;
    }
    
    public InputStream a() {
        return this.a;
    }
    
    public OutputStream b() {
        return this.b;
    }
}
