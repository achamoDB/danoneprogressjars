import java.io.IOException;
import java.io.ByteArrayInputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ia extends psc_h4
{
    public static int a;
    private int b;
    private int c;
    private int d;
    private byte[] e;
    private byte[] f;
    private byte[][] g;
    
    public psc_ia() {
        super(4);
    }
    
    public psc_ia(final int b, final int c, final byte[] array, final byte[] array2, final byte[][] array3) {
        super(4);
        this.b = b;
        this.c = c;
        this.d = 2;
        System.arraycopy(array, 0, this.e = new byte[array.length], 0, array.length);
        System.arraycopy(array2, 0, this.f = new byte[array2.length], 0, array2.length);
        this.g = new byte[array3.length][3];
        for (int i = 0; i < array3.length; ++i) {
            System.arraycopy(array3[i], 0, this.g[i], 0, 3);
        }
    }
    
    public void a(final ByteArrayInputStream byteArrayInputStream) throws IOException, psc_g8 {
        final byte[] array = new byte[2];
        if (byteArrayInputStream.read() != 4) {
            throw new psc_g8("not expecting message type");
        }
        this.b = byteArrayInputStream.read();
        this.c = byteArrayInputStream.read();
        byteArrayInputStream.read(array, 0, 2);
        this.d = ((array[0] & 0xFF) << 8 | (array[1] & 0xFF));
        if (this.d != 2) {
            throw new psc_g8("not expecting protocol version");
        }
        byteArrayInputStream.read(array, 0, 2);
        final int len = (array[0] & 0xFF) << 8 | (array[1] & 0xFF);
        byteArrayInputStream.read(array, 0, 2);
        final int n = (array[0] & 0xFF) << 8 | (array[1] & 0xFF);
        byteArrayInputStream.read(array, 0, 2);
        final int len2 = (array[0] & 0xFF) << 8 | (array[1] & 0xFF);
        byteArrayInputStream.read(this.f = new byte[len], 0, len);
        this.g = new byte[n / 3][3];
        for (int i = 0; i < n / 3; ++i) {
            byteArrayInputStream.read(this.g[i], 0, 3);
        }
        byteArrayInputStream.read(this.e = new byte[len2], 0, len2);
    }
    
    public void a(final psc_h6 psc_h6) throws IOException, psc_g8 {
        if (psc_h6.read() != 4) {
            throw new psc_g8("not expecting message type");
        }
        this.b = psc_h6.read();
        this.c = psc_h6.read();
        this.d = psc_h6.b();
        if (this.d != 2) {
            throw new psc_g8("not expecting version " + this.d);
        }
        final int b = psc_h6.b();
        final int b2 = psc_h6.b();
        final int b3 = psc_h6.b();
        psc_h6.read(this.f = new byte[b], 0, b);
        this.g = new byte[b2 / 3][3];
        for (int i = 0; i < b2 / 3; ++i) {
            psc_h6.read(this.g[i], 0, 3);
        }
        psc_h6.read(this.e = new byte[b3], 0, b3);
    }
    
    public void a(final psc_h7 psc_h7) throws IOException, psc_g8 {
        psc_h7.write(4);
        psc_h7.write(this.b);
        psc_h7.write(this.c);
        psc_h7.a(2);
        psc_h7.a(this.f.length);
        psc_h7.a(this.g.length * 3);
        psc_h7.a(this.e.length);
        psc_h7.write(this.f, 0, this.f.length);
        for (int i = 0; i < this.g.length; ++i) {
            psc_h7.write(this.g[i], 0, 3);
        }
        psc_h7.write(this.e, 0, this.e.length);
        psc_h7.flush();
    }
    
    public int a() {
        return this.b;
    }
    
    public int b() {
        return this.c;
    }
    
    public int c() {
        return 2;
    }
    
    public byte[] d() {
        return this.f;
    }
    
    public byte[] e() {
        return this.e;
    }
    
    public byte[][] f() {
        return this.g;
    }
    
    public int g() {
        return 4;
    }
    
    public void a(final byte[] array) {
        System.arraycopy(array, 0, this.e = new byte[array.length], 0, array.length);
    }
    
    public void a(final byte[][] array) {
        this.g = new byte[array.length][3];
        for (int i = 0; i < array.length; ++i) {
            System.arraycopy(array[i], 0, this.g[i], 0, 3);
        }
    }
    
    public void b(final byte[] array) {
        System.arraycopy(array, 0, this.f = new byte[array.length], 0, array.length);
    }
    
    public void a(final int c) {
        this.c = c;
    }
    
    public void c(final int b) {
        this.b = b;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[SERVER HELLO]: \nSSLV2\n");
        sb.append("[SERVER HELLO / Certificate]: \n" + psc_c9.b(this.f, 0, this.f.length));
        sb.append("\n");
        sb.append("[SERVER HELLO / CipherSuite]: \n");
        for (int i = 0; i < this.g.length; ++i) {
            sb.append(psc_c9.b(this.g[i], 0, 3));
        }
        sb.append("\n");
        sb.append("[SERVER HELLO / Connection ID]: \n" + psc_c9.b(this.e, 0, this.e.length));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
    
    static {
        psc_ia.a = 32;
    }
}
