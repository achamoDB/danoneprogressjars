import java.io.InputStream;
import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hr extends psc_hm
{
    public static int a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g;
    
    public psc_hr() {
        super(2);
    }
    
    public psc_hr(final int c, final byte[] array, final byte[] array2, final byte[] array3, final int g) {
        super(2);
        this.c = c;
        System.arraycopy(array, 0, this.d = new byte[psc_hr.a], 0, psc_hr.a);
        System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        System.arraycopy(array3, 0, this.f = new byte[2], 0, 2);
        this.g = g;
        this.b = 35 + this.e.length + 2 + 1;
    }
    
    public void a(final psc_hn psc_hn) throws IOException, psc_g8 {
        if (psc_hn.read() != 2) {
            throw new psc_g8("not expecting message type");
        }
        this.b = psc_hn.c();
        this.c = psc_hn.b();
        psc_hn.read(this.d = new byte[psc_hr.a], 0, psc_hr.a);
        final int read = psc_hn.read();
        psc_hn.read(this.e = new byte[read], 0, read);
        psc_hn.read(this.f = new byte[2], 0, 2);
        this.g = psc_hn.read();
    }
    
    public void a(final InputStream inputStream) throws IOException {
        this.c = (inputStream.read() << 8 | inputStream.read());
        inputStream.read(this.d = new byte[psc_hr.a], 0, psc_hr.a);
        final int read = inputStream.read();
        inputStream.read(this.e = new byte[read], 0, read);
        inputStream.read(this.f = new byte[2], 0, 2);
        this.g = inputStream.read();
    }
    
    public void a(final psc_ho psc_ho) throws IOException, psc_g8 {
        psc_ho.a(22);
        psc_ho.b(this.c);
        psc_ho.write(2);
        psc_ho.d(this.g());
        psc_ho.c(this.c);
        psc_ho.write(this.d, 0, this.d.length);
        psc_ho.write(this.e.length);
        psc_ho.write(this.e, 0, this.e.length);
        psc_ho.write(this.f, 0, this.f.length);
        psc_ho.write(this.g);
        psc_ho.flush();
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
        sb.append("[SERVER HELLO]: \n");
        sb.append("SSLV3");
        sb.append("\n");
        sb.append("\n");
        sb.append("[SERVER HELLO / random / gmtUnixTime]:\n" + psc_c9.b(this.d, 0, 4));
        sb.append("\n");
        sb.append("[SERVER HELLO / random / Random bytes]:\n" + psc_c9.b(this.d, 4, this.d.length - 4));
        sb.append("[SERVER HELLO / session ID]: \n" + psc_c9.b(this.e, 0, this.e.length));
        sb.append("[SERVER HELLO / cipherSuite]: \n" + psc_c9.b(this.f, 0, this.f.length));
        sb.append("[SERVER HELLO / compression method]: \n" + this.g);
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
    
    static {
        psc_hr.a = 32;
    }
}
