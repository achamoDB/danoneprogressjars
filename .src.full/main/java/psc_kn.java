import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kn extends psc_an implements psc_dl, Cloneable, Serializable
{
    public static final int a = 8;
    private psc_km b;
    private psc_km c;
    private psc_km d;
    private psc_j3 e;
    private psc_j3 f;
    private psc_j3 g;
    private byte[] h;
    private byte[] i;
    
    public psc_kn() {
        this.h = new byte[8];
        this.i = new byte[8];
    }
    
    public psc_kn(final int[] array) throws psc_be {
        this();
        this.a(array);
    }
    
    public void a(final int[] array) throws psc_be {
        if (array != null && array.length != 0) {
            throw new psc_be("Expected no Algorithm Parameters");
        }
    }
    
    public int[] c() {
        return new int[0];
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        psc_di.a(array, n, n2);
    }
    
    public byte[] a(final byte[] array) {
        return array;
    }
    
    public String d() {
        return "3DES_EDE";
    }
    
    public int g() {
        return 8;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kn psc_kn = new psc_kn();
        if (this.b != null) {
            psc_kn.b = (psc_km)this.b.clone();
        }
        if (this.c != null) {
            psc_kn.c = (psc_km)this.c.clone();
        }
        if (this.d != null) {
            psc_kn.d = (psc_km)this.d.clone();
        }
        if (this.e != null) {
            psc_kn.e = (psc_j3)this.e.clone();
        }
        if (this.f != null) {
            psc_kn.f = (psc_j3)this.f.clone();
        }
        if (this.g != null) {
            psc_kn.g = (psc_j3)this.g.clone();
        }
        return psc_kn;
    }
    
    public boolean a(final boolean b) {
        return false;
    }
    
    public void a(final int n) {
    }
    
    public byte[] a(final psc_am psc_am, final boolean b, final psc_di psc_di, final psc_dm psc_dm) throws psc_en {
        throw new psc_en("Cannot wrap key.");
    }
    
    public psc_dt a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_al b(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    public psc_dc a(final byte[] array, final int n, final int n2, final boolean b, final psc_di psc_di, final psc_dm psc_dm, final String s) throws psc_en {
        throw new psc_en("Cannot unwrap key.");
    }
    
    private void a(final psc_j4 psc_j4, final SecureRandom secureRandom, final boolean b) throws psc_bf {
        if (this.b == null) {
            this.b = new psc_km();
        }
        if (this.c == null) {
            this.c = new psc_km();
        }
        if (this.d == null) {
            this.d = new psc_km();
        }
        this.e = psc_j4.e();
        this.f = psc_j4.f();
        this.g = psc_j4.g();
        if (b) {
            this.b.a(this.e, secureRandom);
            this.c.b(this.f, secureRandom);
            this.d.a(this.g, secureRandom);
        }
        else {
            this.b.b(this.e, secureRandom);
            this.c.a(this.f, secureRandom);
            this.d.b(this.g, secureRandom);
        }
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        psc_j4 psc_j4;
        try {
            psc_j4 = (psc_j4)psc_dc;
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Incorrect key type");
        }
        this.a(psc_j4, secureRandom, true);
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        psc_j4 psc_j4;
        try {
            psc_j4 = (psc_j4)psc_dc;
        }
        catch (ClassCastException ex) {
            throw new psc_bf("Incorrect key type");
        }
        this.a(psc_j4, secureRandom, false);
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.b.a(array, n, this.h, 0);
        this.c.b(this.h, 0, this.i, 0);
        this.d.a(this.i, 0, array2, n2);
        return 8;
    }
    
    public int b(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.d.b(array, n, this.h, 0);
        this.c.a(this.h, 0, this.i, 0);
        this.b.b(this.i, 0, array2, n2);
        return 8;
    }
    
    public void e() {
        this.b.e();
        this.c.e();
        this.d.e();
    }
    
    public void f() {
        this.b.f();
        this.c.f();
        this.d.f();
    }
    
    public void y() {
        super.y();
        if (this.b != null) {
            this.b.y();
        }
        if (this.c != null) {
            this.c.y();
        }
        if (this.d != null) {
            this.d.y();
        }
        if (this.e != null) {
            this.e.y();
        }
        if (this.f != null) {
            this.f.y();
        }
        if (this.g != null) {
            this.g.y();
        }
        final psc_km b = null;
        this.d = b;
        this.c = b;
        this.b = b;
        final psc_j3 e = null;
        this.g = e;
        this.f = e;
        this.e = e;
        this.d(this.h);
        this.d(this.i);
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
