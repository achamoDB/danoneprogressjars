import java.io.IOException;
import java.io.OutputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_ao extends pscl_ap
{
    public static int a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[][] f;
    private byte[] g;
    
    public pscl_ao(final int c, final byte[] array, final byte[] array2, final byte[][] array3, final byte[] array4) {
        super(1);
        this.c = c;
        System.arraycopy(array, 0, this.d = new byte[pscl_ao.a], 0, pscl_ao.a);
        System.arraycopy(array2, 0, this.e = new byte[array2.length], 0, array2.length);
        System.arraycopy(array3, 0, this.f = new byte[array3.length][2], 0, array3.length);
        System.arraycopy(array4, 0, this.g = new byte[array4.length], 0, array4.length);
        this.b = 2 + pscl_ao.a + 1 + this.e.length + 2 + this.f.length * 2 + 1 + this.g.length;
    }
    
    public void a(final OutputStream outputStream) throws IOException, pscl_av {
        outputStream.write(1);
        outputStream.write((byte)(this.a() >> 16));
        outputStream.write((byte)(this.a() >> 8));
        outputStream.write((byte)(this.a() & 0xFF));
        outputStream.write((byte)(this.c >> 8));
        outputStream.write((byte)(this.c & 0xFF));
        outputStream.write(this.d, 0, pscl_ao.a);
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
    
    public String toString() throws NullPointerException {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CLIENT HELLO]: \n");
        sb.append("SSLV3 \n");
        sb.append("[CLIENT HELLO / random / gmtUnixTime]: \n" + pscl_k.b(this.d, 0, 4));
        sb.append("\n");
        sb.append("[CLIENT HELLO / random / Random bytes]: \n" + pscl_k.b(this.d, 4, this.d.length - 4));
        sb.append("\n");
        sb.append("[CLIENT HELLO / Session ID] \n" + pscl_k.b(this.e, 0, this.e.length));
        sb.append("\n");
        sb.append("[CLIENT HELLO / Client Cipher Suite]:\n");
        for (int i = 0; i < this.f.length; ++i) {
            sb.append(pscl_k.b(this.f[i], 0, 2));
        }
        sb.append("\n");
        sb.append("[CLIENT HELLO/ Compression Method]:\n" + pscl_k.b(this.g, 0, this.g.length));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
    
    public int a() {
        if (this.b == 0) {
            this.b = 2 + pscl_ao.a + 1 + this.e.length + 2 + this.f.length * 2 + 1 + this.g.length;
        }
        return this.b;
    }
    
    static {
        pscl_ao.a = 32;
    }
}
