import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ib extends psc_h4
{
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private byte[] d;
    
    public psc_ib() {
        super(2);
    }
    
    public psc_ib(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4) {
        super(2);
        System.arraycopy(array, 0, this.a = new byte[3], 0, 3);
        System.arraycopy(array2, 0, this.b = new byte[array2.length], 0, array2.length);
        System.arraycopy(array3, 0, this.c = new byte[array3.length], 0, array3.length);
        System.arraycopy(array4, 0, this.d = new byte[array4.length], 0, array4.length);
    }
    
    public void b(final psc_h6 psc_h6) throws IOException, psc_g8 {
        if (psc_h6.read() != 2) {
            throw new psc_g8("Not expecting message type");
        }
        psc_h6.read(this.a = new byte[3], 0, 3);
        final int b = psc_h6.b();
        final int b2 = psc_h6.b();
        final int b3 = psc_h6.b();
        psc_h6.read(this.b = new byte[b], 0, b);
        psc_h6.read(this.c = new byte[b2], 0, b2);
        psc_h6.read(this.d = new byte[b3], 0, b3);
    }
    
    public void a(final psc_h7 psc_h7) throws IOException, psc_g8 {
        psc_h7.write(2);
        psc_h7.write(this.a, 0, 3);
        psc_h7.a(this.b.length);
        psc_h7.a(this.c.length);
        psc_h7.a(this.d.length);
        psc_h7.write(this.b, 0, this.b.length);
        psc_h7.write(this.c, 0, this.c.length);
        psc_h7.write(this.d, 0, this.d.length);
        psc_h7.flush();
    }
    
    public byte[] a() {
        return this.b;
    }
    
    public byte[] b() {
        return this.a;
    }
    
    public byte[] c() {
        return this.c;
    }
    
    public byte[] d() {
        return this.d;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[CLIENT MASTER KEY]\n");
        sb.append("[CLIENT MASTER KEY / CipherSuite]: \n" + psc_c9.a(this.a));
        sb.append("\n");
        sb.append("[CLIENT MASTER KEY / Clear Key]: \n" + psc_c9.a(this.b));
        sb.append("\n");
        sb.append("[CLIENT MASTER KEY / Encrypted Key]: \n" + psc_c9.a(this.c));
        sb.append("\n");
        sb.append("[CLIENT MASTER KEY / Key Arg Data]: \n" + psc_c9.a(this.d));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
