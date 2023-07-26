import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ku extends psc_di implements Cloneable, Serializable
{
    private static final String a = "CBC";
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private boolean g;
    private int h;
    
    public psc_ku() {
        super("CBC");
        this.g = true;
    }
    
    public psc_ku(final int[] array) throws psc_be {
        super("CBC");
        this.g = true;
        this.a(array);
    }
    
    psc_ku(final byte[] array, int n, final int h) {
        super("CBC");
        this.g = true;
        final byte[] b = new byte[h];
        final byte[] c = new byte[h];
        final byte[] d = new byte[h];
        for (int i = 0; i < h; ++i, ++n) {
            b[i] = (c[i] = array[n]);
        }
        this.b = b;
        this.c = c;
        this.d = d;
        this.h = h;
    }
    
    public int k() {
        return -1;
    }
    
    public String f() {
        return "CBC";
    }
    
    public int a(final int n) {
        return n;
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        try {
            final psc_t psc_t = new psc_t(0);
            psc_l.a(array, n, new psc_i[] { psc_t });
            this.b(psc_t.b, psc_t.c, psc_t.d);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid BER encoding. (" + psc_m.getMessage() + ")");
        }
    }
    
    byte[] g() throws psc_ao {
        if (this.b == null) {
            throw new psc_ao("Cannot DER encode CBC, IV not set.");
        }
        final byte[] array = new byte[this.b.length + 2];
        array[0] = 4;
        array[1] = (byte)this.b.length;
        System.arraycopy(this.b, 0, array, 2, this.b.length);
        return array;
    }
    
    public void b(final byte[] array, int n, final int h) throws psc_gw {
        if (this.h != 0 && h != this.h) {
            throw new psc_gw("Incorrect IV Length. Should be " + this.h + ".");
        }
        this.g = true;
        final byte[] b = new byte[h];
        final byte[] c = new byte[h];
        final byte[] d = new byte[h];
        for (int i = 0; i < h; ++i, ++n) {
            b[i] = (c[i] = array[n]);
        }
        this.b = b;
        this.c = c;
        this.d = d;
        this.h = h;
    }
    
    public int i() {
        return this.h;
    }
    
    public byte[] j() {
        if (this.h == 0) {
            return null;
        }
        final byte[] array = new byte[this.h];
        for (int i = 0; i < this.h; ++i) {
            array[i] = this.b[i];
        }
        return array;
    }
    
    void a(final psc_dl psc_dl, final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        psc_dl.a(psc_dc, secureRandom);
    }
    
    void b(final psc_dl psc_dl, final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        psc_dl.b(psc_dc, secureRandom);
    }
    
    public Object clone() throws CloneNotSupportedException {
        final Object clone = super.clone();
        if (this.b != null) {
            ((psc_ku)clone).b = this.b.clone();
        }
        if (this.c != null) {
            ((psc_ku)clone).c = this.c.clone();
        }
        if (this.d != null) {
            ((psc_ku)clone).d = this.d.clone();
        }
        if (this.e != null) {
            ((psc_ku)clone).e = this.e.clone();
        }
        if (this.f != null) {
            ((psc_ku)clone).f = this.f.clone();
        }
        return clone;
    }
    
    int a(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        for (int i = 0; i < this.h; ++i) {
            final byte[] c = this.c;
            final int n3 = i;
            c[n3] ^= array[i + n];
        }
        final int a = psc_dl.a(this.c, 0, array2, n2);
        for (int j = 0; j < this.h; ++j) {
            this.c[j] = array2[j + n2];
        }
        return a;
    }
    
    int b(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        if (this.g) {
            this.e = this.d;
            this.f = this.c;
        }
        else {
            this.e = this.c;
            this.f = this.d;
        }
        this.g = !this.g;
        for (int i = 0; i < this.h; ++i) {
            this.e[i] = array[i + n];
        }
        final int b = psc_dl.b(array, n, array2, n2);
        for (int j = 0; j < this.h; ++j) {
            final int n3 = j + n2;
            array2[n3] ^= this.f[j];
        }
        return b;
    }
    
    public void y() {
        super.y();
        psc_au.c(this.b);
        psc_au.c(this.c);
        psc_au.c(this.d);
        psc_au.c(this.e);
        psc_au.c(this.f);
        this.h = 0;
        this.g = true;
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
