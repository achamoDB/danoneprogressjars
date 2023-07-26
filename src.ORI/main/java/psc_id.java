import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_id extends psc_h4
{
    private int a;
    private byte[] b;
    
    public psc_id() {
        super(7);
    }
    
    public psc_id(final int a, final byte[] array) {
        super(7);
        this.a = a;
        System.arraycopy(array, 0, this.b = new byte[array.length], 0, array.length);
    }
    
    public void b(final psc_h6 psc_h6) throws IOException, psc_g8 {
        this.a = psc_h6.read();
        final int f = psc_h6.f();
        psc_h6.read(this.b = new byte[f - 2], 0, f - 2);
    }
    
    public void a(final psc_h7 psc_h7) throws IOException, psc_g8 {
        psc_h7.write(7);
        psc_h7.write(this.a);
        psc_h7.write(this.b, 0, this.b.length);
        psc_h7.flush();
    }
    
    public byte[] a() {
        return this.b;
    }
    
    public int b() {
        return this.a;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[REQUEST CERTIFICATE]:\n");
        sb.append("[REQUEST CERTIFICATE / Authentication type]:\n" + this.a);
        sb.append("\n");
        sb.append("[REQUEST CERTIFICATE / Challenge data]: \n" + psc_c9.a(this.b));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
