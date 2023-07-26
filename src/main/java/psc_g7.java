import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_g7 extends psc_g1
{
    public static int a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g;
    
    public psc_g7() {
        super(2);
    }
    
    public psc_g7(final int c, final byte[] array, final byte[] array2, final byte[] array3, final int g) {
        super(2);
        this.c = c;
        System.arraycopy(array, 0, this.d = new byte[psc_g7.a], 0, psc_g7.a);
        System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        System.arraycopy(array3, 0, this.f = new byte[2], 0, 2);
        this.g = g;
        this.b = 35 + this.e.length + 2 + 1;
    }
    
    public void b(final psc_g2 psc_g2) throws IOException, psc_g8 {
        this.c = (psc_g2.read() << 8 | psc_g2.read());
        psc_g2.read(this.d = new byte[psc_g7.a], 0, psc_g7.a);
        final int read = psc_g2.read();
        psc_g2.read(this.e = new byte[read], 0, read);
        psc_g2.read(this.f = new byte[2], 0, 2);
        this.g = psc_g2.read();
    }
    
    public void a(final psc_g2 psc_g2) throws IOException, psc_g8 {
        if (psc_g2.read() != 2) {
            throw new psc_g8("not expecting message type");
        }
        this.b = psc_g2.c();
        this.c = psc_g2.b();
        psc_g2.read(this.d = new byte[psc_g7.a], 0, psc_g7.a);
        final int read = psc_g2.read();
        psc_g2.read(this.e = new byte[read], 0, read);
        psc_g2.read(this.f = new byte[2], 0, 2);
        this.g = psc_g2.read();
    }
    
    public void a(final psc_g3 psc_g3) throws IOException {
        psc_g3.a(22);
        psc_g3.b(this.c);
        psc_g3.write(2);
        psc_g3.d(this.g());
        psc_g3.c(this.c);
        psc_g3.write(this.d, 0, this.d.length);
        psc_g3.write(this.e.length);
        psc_g3.write(this.e, 0, this.e.length);
        psc_g3.write(this.f, 0, this.f.length);
        psc_g3.write(this.g);
        psc_g3.flush();
    }
    
    public int a() {
        return this.c;
    }
    
    public byte[] b() {
        return this.d;
    }
    
    public byte[] c() {
        return this.e;
    }
    
    public byte[] d() {
        return this.f;
    }
    
    public int e() {
        return this.g;
    }
    
    public int f() {
        return 2;
    }
    
    public int g() {
        if (this.b == 0) {
            this.b = 35 + this.e.length + 2 + 1;
        }
        return this.b;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[SERVER HELLO]: \nTLSV1\n");
        sb.append("[SERVER HELLO / Random / gmtUnixTime]:\n" + psc_c9.b(this.d, 0, 4));
        sb.append("\n");
        sb.append("[SERVER HELLO / Random / Random bytes]: \n" + psc_c9.b(this.d, 4, this.d.length - 4));
        sb.append("\n");
        sb.append("[SERVER HELLO/ Session ID]: \n" + psc_c9.b(this.e, 0, this.e.length));
        sb.append("\n");
        sb.append("[SERVER HELLO / CipherSuite]: \n" + psc_c9.b(this.f, 0, this.f.length));
        sb.append("\n");
        sb.append("[SERVER HELLO / Compression method]: \n" + this.g);
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
    
    static {
        psc_g7.a = 32;
    }
}
