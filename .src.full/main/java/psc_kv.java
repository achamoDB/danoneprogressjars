import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_kv extends psc_di implements Cloneable, Serializable
{
    private static final String a = "ECB";
    private int b;
    
    public psc_kv() {
        super("ECB");
        this.b = 8;
    }
    
    public psc_kv(final int[] array) throws psc_be {
        super("ECB");
        this.a(array);
        this.b = 8;
    }
    
    public int k() {
        return -1;
    }
    
    public int a(final int n) {
        return 0;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
    }
    
    byte[] g() {
        return null;
    }
    
    void b(final int b) {
        this.b = b;
    }
    
    public void b(final byte[] array, final int n, final int n2) {
    }
    
    public int i() {
        return 0;
    }
    
    public byte[] j() {
        return null;
    }
    
    public boolean h() {
        return false;
    }
    
    void a(final psc_dl psc_dl, final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        psc_dl.a(psc_dc, secureRandom);
    }
    
    void b(final psc_dl psc_dl, final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        psc_dl.b(psc_dc, secureRandom);
    }
    
    int a(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        return psc_dl.a(array, n, array2, n2);
    }
    
    int b(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        return psc_dl.b(array, n, array2, n2);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public void y() {
        super.y();
    }
    
    protected void finalize() {
        try {
            this.y();
        }
        finally {
            super.finalize();
        }
    }
}
