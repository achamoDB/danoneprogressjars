import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_g0 extends psc_g1
{
    public static int a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[][] f;
    private byte[] g;
    
    public psc_g0() {
        super(1);
    }
    
    public psc_g0(final int c, final byte[] array, final byte[] array2, final byte[][] array3, final byte[] array4) {
        super(1);
        this.c = c;
        System.arraycopy(array, 0, this.d = new byte[psc_g0.a], 0, psc_g0.a);
        System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        System.arraycopy(array3, 0, this.f = new byte[array3.length][2], 0, array3.length);
        System.arraycopy(array4, 0, this.g = new byte[array4.length], 0, array4.length);
        this.b = 2 + psc_g0.a + 1 + this.e.length + 2 + this.f.length * 2 + 1 + this.g.length;
    }
    
    public void a(final int c) {
        this.c = c;
    }
    
    public void a(final byte[] array) {
        System.arraycopy(array, 0, this.d = new byte[psc_g0.a], 0, psc_g0.a);
    }
    
    public void b(final byte[] array) {
        System.arraycopy(array, 0, this.e = new byte[array.length], 0, array.length);
    }
    
    public void a(final byte[][] array) {
        this.f = new byte[array.length][2];
        for (int i = 0; i < array.length; ++i) {
            System.arraycopy(array[i], 0, this.f[i], 0, array[i].length);
        }
    }
    
    public void c(final byte[] array) {
        System.arraycopy(array, 0, this.g = new byte[array.length], 0, array.length);
    }
    
    public void a(final InputStream inputStream) throws IOException {
        final byte[] array = new byte[2];
        inputStream.read(array, 0, 2);
        this.c = ((array[0] & 0xFF) << 8 | (array[1] & 0xFF));
        inputStream.read(this.d = new byte[psc_g0.a], 0, psc_g0.a);
        final int read = inputStream.read();
        inputStream.read(this.e = new byte[read], 0, read);
        inputStream.read(array, 0, 2);
        final int n = (array[0] & 0xFF) << 8 | (array[1] & 0xFF);
        this.f = new byte[n / 2][2];
        for (int i = 0; i < n / 2; ++i) {
            inputStream.read(this.f[i], 0, 2);
        }
        final int read2 = inputStream.read();
        inputStream.read(this.g = new byte[read2], 0, read2);
    }
    
    public void a(final psc_g2 psc_g2) throws IOException, psc_g8 {
        if (psc_g2.read() != 1) {
            throw new psc_g8("not expecting handshake type");
        }
        this.b = psc_g2.c();
        this.c = psc_g2.b();
        psc_g2.read(this.d = new byte[psc_g0.a], 0, psc_g0.a);
        final int read = psc_g2.read();
        psc_g2.read(this.e = new byte[read], 0, read);
        final int b = psc_g2.b();
        this.f = new byte[b / 2][2];
        for (int i = 0; i < b / 2; ++i) {
            psc_g2.read(this.f[i], 0, 2);
        }
        final int read2 = psc_g2.read();
        psc_g2.read(this.g = new byte[read2], 0, read2);
    }
    
    public void a(final OutputStream outputStream) throws IOException, psc_g8 {
        outputStream.write(1);
        outputStream.write((byte)(this.g() >> 16));
        outputStream.write((byte)(this.g() >> 8));
        outputStream.write((byte)(this.g() & 0xFF));
        outputStream.write((byte)(this.c >> 8));
        outputStream.write((byte)(this.c & 0xFF));
        outputStream.write(this.d, 0, psc_g0.a);
        outputStream.write(this.e.length);
        outputStream.write(this.e, 0, this.e.length);
        outputStream.write((byte)(this.f.length * 2 >> 8));
        outputStream.write((byte)(this.f.length * 2 & 0xFF));
        for (int i = 0; i < this.f.length; ++i) {
            outputStream.write(this.f[i], 0, this.f[i].length);
        }
        outputStream.write(this.g.length);
        outputStream.write(this.g, 0, this.g.length);
        outputStream.flush();
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
    
    public byte[][] d() {
        return this.f;
    }
    
    public byte[] e() {
        return this.g;
    }
    
    public int f() {
        return 1;
    }
    
    public int g() {
        if (this.b == 0) {
            this.b = 2 + psc_g0.a + 1 + this.e.length + 2 + this.f.length * 2 + 1 + this.g.length;
        }
        return this.b;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CLIENT HELLO]: \nTLSV1\n");
        sb.append("[CLIENT HELLO / Random / gmtUnixTime]: \n" + psc_c9.b(this.d, 0, 4));
        sb.append("\n");
        sb.append("[CLIENT HELLO / Random / Random bytes]: \n" + psc_c9.b(this.d, 4, this.d.length - 4));
        sb.append("\n");
        sb.append("[CLIENT HELLO / Session ID]: \n" + psc_c9.b(this.e, 0, this.e.length));
        sb.append("\n");
        sb.append("[CLIENT HELLO / Client Cipher Suites]: \n");
        for (int i = 0; i < this.f.length; ++i) {
            sb.append(psc_c9.b(this.f[i], 0, 2));
        }
        sb.append("\n");
        sb.append("[CLIENT HELLO / Compression Methods]: \n" + psc_c9.b(this.g, 0, this.g.length));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
    
    static {
        psc_g0.a = 32;
    }
}
