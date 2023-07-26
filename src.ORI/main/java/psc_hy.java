import java.io.IOException;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_hy extends psc_hm
{
    private byte[] a;
    private byte[] b;
    private int c;
    
    public psc_hy() {
        super(20);
    }
    
    public psc_hy(final byte[] array, final byte[] array2) {
        super(20);
        this.c = 36;
        this.a = new byte[16];
        this.b = new byte[20];
        System.arraycopy(array, 0, this.a, 0, 16);
        System.arraycopy(array2, 0, this.b, 0, 20);
    }
    
    public void b(final psc_hn psc_hn) throws IOException, psc_g8 {
        if (psc_hn.read() != 20) {
            throw new psc_g8("Not expecting handshake type");
        }
        this.c = psc_hn.c();
        psc_hn.read(this.a = new byte[16], 0, 16);
        psc_hn.read(this.b = new byte[20], 0, 20);
    }
    
    public void a(final psc_ho psc_ho) throws IOException, psc_g8 {
        psc_ho.a(22);
        psc_ho.b(768);
        psc_ho.write(20);
        psc_ho.d(this.c);
        psc_ho.write(this.a, 0, 16);
        psc_ho.write(this.b, 0, 20);
        psc_ho.flush();
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public byte[] b() {
        return this.b;
    }
    
    public int c() {
        return this.c;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[HANDSHAKE FINISHED]: \n");
        sb.append("[HANDSHAKE FINISHED / md5_hash]:\n" + psc_c9.a(this.a));
        sb.append("\n");
        sb.append("[HANDSHAKE FINISHED / sha_hash]: \n" + psc_c9.a(this.b));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
