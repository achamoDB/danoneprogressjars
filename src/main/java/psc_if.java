import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_if extends psc_h4
{
    private int a;
    private byte[] b;
    private byte[] c;
    
    public psc_if() {
        super(8);
    }
    
    public psc_if(final int a, final byte[] array, final byte[] array2) {
        super(8);
        this.a = a;
        System.arraycopy(array, 0, this.b = new byte[array.length], 0, array.length);
        System.arraycopy(array2, 0, this.c = new byte[array2.length], 0, array2.length);
    }
    
    public void b(final psc_h6 psc_h6) throws IOException, psc_g8 {
        this.a = psc_h6.read();
        final int b = psc_h6.b();
        final int b2 = psc_h6.b();
        psc_h6.read(this.b = new byte[b], 0, b);
        psc_h6.read(this.c = new byte[b2], 0, b2);
    }
    
    public void a(final psc_h7 psc_h7) throws IOException, psc_g8 {
        psc_h7.write(8);
        psc_h7.write(this.a);
        psc_h7.a(this.b.length);
        psc_h7.a(this.c.length);
        psc_h7.write(this.b, 0, this.b.length);
        psc_h7.write(this.c, 0, this.c.length);
        psc_h7.flush();
    }
    
    public int a() {
        return this.a;
    }
    
    public byte[] b() {
        return this.b;
    }
    
    public byte[] c() {
        return this.c;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CLIENT CERTIFICATE]:\n");
        sb.append("[CLIENT CERTIFICATE / Certificate type]: " + this.a);
        sb.append("\n");
        sb.append("[CLIENT CERTIFICATE / Certificate]: \n" + psc_c9.a(this.b));
        sb.append("\n");
        sb.append("[CLIENT CERTIFICATE / Response]:\n" + psc_c9.a(this.c));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
