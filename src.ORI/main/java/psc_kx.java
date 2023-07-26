import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public abstract class psc_kx extends psc_di implements Cloneable, Serializable
{
    protected byte[] a;
    protected byte[] b;
    protected byte[] c;
    protected int d;
    protected int e;
    protected int f;
    
    public psc_kx(final String s) {
        super(s);
    }
    
    public psc_kx(final String s, final int[] array) throws psc_be {
        super(s);
        this.a(array);
    }
    
    psc_kx(final String s, final byte[] array, final int n, final int n2, final int n3) throws psc_be, psc_gw {
        super(s);
        this.b(n3);
        this.b(array, n, n2);
    }
    
    void a(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length != 1) {
            throw new psc_be("Incorrect Number of Algorithm Parameters: " + array.length + " : expected one, bitLength.");
        }
        this.b(array[0]);
    }
    
    boolean c() {
        return true;
    }
    
    public int a(final int n) {
        return n;
    }
    
    void b(final int e) throws psc_be {
        this.e = e;
        if (this.e / 8 * 8 != this.e) {
            throw new psc_be("OFB can only be used in bit lengths that are multiples of eight.");
        }
    }
    
    public void b(final byte[] array, int n, final int d) throws psc_gw {
        if (this.d != 0) {
            if (d != this.d) {
                throw new psc_gw("Incorrect IV Length. Should be " + this.d + ".");
            }
            if (d < this.e >> 3) {
                throw new psc_gw("The bit length must be smaller than the block size.");
            }
            for (int i = 0; i < d; ++i, ++n) {
                this.a[i] = (this.b[i] = array[n]);
            }
        }
        else {
            if (this.e == 0) {
                this.e = d * 8;
            }
            if (d < this.e >> 3) {
                throw new psc_gw("The bit length must be smaller than the block size.");
            }
            this.a = new byte[d];
            this.b = new byte[d];
            this.c = new byte[d];
            for (int j = 0; j < d; ++j, ++n) {
                this.a[j] = (this.b[j] = array[n]);
            }
            this.d = d;
        }
    }
    
    public int i() {
        return this.d;
    }
    
    public byte[] j() {
        if (this.d == 0) {
            return null;
        }
        final byte[] array = new byte[this.d];
        System.arraycopy(this.a, 0, array, 0, this.d);
        return array;
    }
    
    public int k() {
        if (this.e == 0) {
            return -1;
        }
        return this.e / 8;
    }
    
    void a(final psc_dl psc_dl, final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf, psc_gw {
        if (this.d == 0) {
            throw new psc_gw(super.a + " initialization vector not set.");
        }
        psc_dl.a(psc_dc, secureRandom);
        this.f = 0;
    }
    
    void b(final psc_dl psc_dl, final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf, psc_gw {
        this.a(psc_dl, psc_dc, secureRandom);
    }
    
    public Object clone() throws CloneNotSupportedException {
        final Object clone = super.clone();
        if (this.a != null) {
            ((psc_kx)clone).a = this.a.clone();
        }
        if (this.b != null) {
            ((psc_kx)clone).b = this.b.clone();
        }
        if (this.c != null) {
            ((psc_kx)clone).c = this.c.clone();
        }
        return clone;
    }
    
    public void y() {
        super.y();
        this.d(this.a);
        this.d(this.b);
        this.d(this.c);
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = 0;
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
