import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_h3 extends psc_h4
{
    public static int a;
    public static int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[][] f;
    
    public psc_h3() {
        super(1);
    }
    
    public psc_h3(final int c, final byte[] array, final byte[] array2, final byte[][] array3) {
        super(1);
        this.c = c;
        System.arraycopy(array, 0, this.d = new byte[array.length], 0, array.length);
        System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        this.f = new byte[array3.length][3];
        for (int i = 0; i < array3.length; ++i) {
            System.arraycopy(array3[i], 0, this.f[i], 0, 3);
        }
    }
    
    public void a(final int c) {
        this.c = c;
    }
    
    public void a(final byte[] array) {
        System.arraycopy(array, 0, this.d = new byte[array.length], 0, array.length);
    }
    
    public void b(final byte[] array) {
        System.arraycopy(array, 0, this.e = new byte[array.length], 0, array.length);
    }
    
    public void a(final byte[][] array) {
        this.f = new byte[array.length][3];
        for (int i = 0; i < array.length; ++i) {
            System.arraycopy(array[i], 0, this.f[i], 0, 3);
        }
    }
    
    public void a(final ByteArrayInputStream byteArrayInputStream) throws IOException, psc_g8 {
        final byte[] array = new byte[2];
        if (byteArrayInputStream.read() != 1) {
            throw new psc_g8("not expecting type");
        }
        byteArrayInputStream.read(array, 0, 2);
        this.c = ((array[0] & 0xFF) << 8 | (array[1] & 0xFF));
        byteArrayInputStream.read(array, 0, 2);
        final int n = (array[0] & 0xFF) << 8 | (array[1] & 0xFF);
        byteArrayInputStream.read(array, 0, 2);
        final int len = (array[0] & 0xFF) << 8 | (array[1] & 0xFF);
        byteArrayInputStream.read(array, 0, 2);
        final int len2 = (array[0] & 0xFF) << 8 | (array[1] & 0xFF);
        this.f = new byte[n / 3][3];
        for (int i = 0; i < n / 3; ++i) {
            byteArrayInputStream.read(this.f[i], 0, 3);
        }
        byteArrayInputStream.read(this.e = new byte[len], 0, len);
        byteArrayInputStream.read(this.d = new byte[len2], 0, len2);
    }
    
    public void a(final psc_h6 psc_h6) throws IOException, psc_g8 {
        if (psc_h6.read() != 1) {
            throw new psc_g8("not expecting handshake type");
        }
        this.c = psc_h6.b();
        final int b = psc_h6.b();
        final int b2 = psc_h6.b();
        final int b3 = psc_h6.b();
        this.f = new byte[b / 3][3];
        for (int i = 0; i < b / 3; ++i) {
            psc_h6.read(this.f[i], 0, 3);
        }
        psc_h6.read(this.e = new byte[b2], 0, b2);
        psc_h6.read(this.d = new byte[b3], 0, b3);
    }
    
    public void a(final OutputStream outputStream) throws IOException, psc_g8 {
        outputStream.write(1);
        outputStream.write((byte)(this.c >> 8));
        outputStream.write((byte)(this.c & 0xFF));
        outputStream.write((byte)(this.f.length * 3 >> 8));
        outputStream.write((byte)(this.f.length * 3 & 0xFF));
        outputStream.write((byte)(this.e.length >> 8));
        outputStream.write((byte)(this.e.length & 0xFF));
        outputStream.write((byte)(this.d.length >> 8));
        outputStream.write((byte)(this.d.length & 0xFF));
        for (int i = 0; i < this.f.length; ++i) {
            outputStream.write(this.f[i], 0, 3);
        }
        outputStream.write(this.e, 0, this.e.length);
        outputStream.write(this.d, 0, this.d.length);
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
    
    public int e() {
        return 1;
    }
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CLIENT HELLO]: \n SSLV2 - " + psc_c9.a(this.c, 2) + "\n");
        sb.append("[CLIENT HELLO / Client Cipher Suite]: \n");
        for (int i = 0; i < this.f.length; ++i) {
            sb.append(psc_c9.b(this.f[i], 0, 3));
        }
        sb.append("\n");
        sb.append("[CLIENT HELLO / Session ID]:  \n" + psc_c9.b(this.e, 0, this.e.length));
        sb.append("\n");
        sb.append("[CLIENT HELLO / Challenge Data]: \n" + psc_c9.b(this.d, 0, this.d.length));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
    
    static {
        psc_h3.a = 32;
        psc_h3.b = 16;
    }
}
