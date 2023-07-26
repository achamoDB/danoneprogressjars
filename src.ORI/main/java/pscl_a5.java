import java.io.IOException;
import java.io.InputStream;

// 
// Decompiled by Procyon v0.5.36
// 

public class pscl_a5 extends pscl_ap
{
    private byte[] a;
    private byte[] b;
    private int c;
    
    public pscl_a5() {
        super(20);
    }
    
    public pscl_a5(final byte[] array, final byte[] array2) {
        super(20);
        this.c = 36;
        this.a = new byte[16];
        this.b = new byte[20];
        System.arraycopy(array, 0, this.a, 0, 16);
        System.arraycopy(array2, 0, this.b, 0, 20);
    }
    
    public void a(final pscl_ar pscl_ar) throws IOException, pscl_av {
        if (pscl_au.a(pscl_ar) != 20) {
            throw new pscl_av("Not expecting handshake type");
        }
        this.c = pscl_au.c(pscl_ar);
        pscl_au.a(pscl_ar, this.a = new byte[16], 0, 16);
        pscl_au.a(pscl_ar, this.b = new byte[20], 0, 20);
    }
    
    public void a(final pscl_as pscl_as) throws IOException, pscl_av {
        pscl_as.a(22);
        pscl_as.b(768);
        pscl_as.write(20);
        pscl_as.d(this.c);
        pscl_as.write(this.a, 0, 16);
        pscl_as.write(this.b, 0, 20);
        pscl_as.flush();
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
        sb.append("[HANDSHAKE FINISHED / md5_hash]:\n" + pscl_k.a(this.a));
        sb.append("\n");
        sb.append("[HANDSHAKE FINISHED / sha_hash]: \n" + pscl_k.a(this.b));
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
