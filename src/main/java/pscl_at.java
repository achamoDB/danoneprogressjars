import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_at extends pscl_ap
{
    public static int a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g;
    
    public pscl_at() {
        super(2);
    }
    
    public void b(final pscl_ar pscl_ar) throws IOException, pscl_av {
        if (pscl_au.a(pscl_ar) != 2) {
            throw new pscl_av("not expecting message type");
        }
        this.b = pscl_au.c(pscl_ar);
        this.c = pscl_au.b(pscl_ar);
        pscl_au.a(pscl_ar, this.d = new byte[pscl_at.a], 0, pscl_at.a);
        final int a = pscl_au.a(pscl_ar);
        pscl_au.a(pscl_ar, this.e = new byte[a], 0, a);
        pscl_au.a(pscl_ar, this.f = new byte[2], 0, 2);
        this.g = pscl_au.a(pscl_ar);
    }
    
    public void a(final InputStream inputStream) throws IOException {
        this.c = (pscl_au.a(inputStream) << 8 | pscl_au.a(inputStream));
        pscl_au.a(inputStream, this.d = new byte[pscl_at.a], 0, pscl_at.a);
        final int a = pscl_au.a(inputStream);
        pscl_au.a(inputStream, this.e = new byte[a], 0, a);
        pscl_au.a(inputStream, this.f = new byte[2], 0, 2);
        this.g = pscl_au.a(inputStream);
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
        sb.append("[SERVER HELLO / random / gmtUnixTime]:\n" + pscl_k.b(this.d, 0, 4));
        sb.append("\n");
        sb.append("[SERVER HELLO / random / Random bytes]:\n" + pscl_k.b(this.d, 4, this.d.length - 4));
        sb.append("[SERVER HELLO / session ID]: \n" + pscl_k.b(this.e, 0, this.e.length));
        sb.append("[SERVER HELLO / cipherSuite]: \n" + pscl_k.b(this.f, 0, this.f.length));
        sb.append("[SERVER HELLO / compression method]: \n" + this.g);
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
    
    static {
        pscl_at.a = 32;
    }
}
