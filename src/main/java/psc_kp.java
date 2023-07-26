import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_kp extends psc_an implements psc_dl, Cloneable, Serializable
{
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected psc_kr e;
    
    public psc_kp() {
        try {
            this.a(k(), j(), o());
        }
        catch (psc_be psc_be) {}
    }
    
    public psc_kp(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            this.a(k(), j(), o());
        }
        else {
            this.a(array);
        }
    }
    
    protected psc_kp h() {
        return new psc_kp();
    }
    
    public void a(final int[] array) throws psc_be {
        if (array == null || array.length == 0) {
            return;
        }
        if (array.length != 3) {
            throw new psc_be("Incorrect number of RC5 parameters: expected 3: version, wordSizeInBits, rounds)");
        }
        this.a(array[0], array[1], array[2]);
    }
    
    void a(final int a, final int c, final int b) throws psc_be {
        if (a != k()) {
            throw new psc_be("Only Version " + k() + " of " + this.d() + " is currently implemented");
        }
        if (b < l() || b > m()) {
            throw new psc_be("Number of Rounds for " + this.d() + " should be between " + l() + " and " + m());
        }
        final psc_kr a2 = this.a(c, b);
        this.a = a;
        this.c = c;
        this.b = b;
        this.d = this.n();
        this.e = a2;
    }
    
    protected psc_kr a(final int n, final int n2) throws psc_be {
        switch (n) {
            case 32: {
                return new psc_kq(n2);
            }
            case 64: {
                return new psc_ks(n2);
            }
            default: {
                throw new psc_be("Invalid word size for " + this.d() + " only 32 and 64 are supported");
            }
        }
    }
    
    public int[] c() {
        return new int[] { this.a, this.c, this.b };
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        psc_lt.a(this, array, n, n2, psc_di, psc_dm);
    }
    
    public byte[] a(final byte[] array) {
        return psc_lt.a(this.a, this.c, this.b, array);
    }
    
    public String d() {
        return "RC5";
    }
    
    protected psc_kr i() {
        return this.e;
    }
    
    public int g() {
        return this.d;
    }
    
    protected static int j() {
        return 32;
    }
    
    protected static int k() {
        return 16;
    }
    
    protected static int l() {
        return 0;
    }
    
    protected static int m() {
        return 255;
    }
    
    protected int n() {
        return 2 * (this.c / 8);
    }
    
    protected static int o() {
        return 12;
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
    
    public Object clone() throws CloneNotSupportedException {
        final psc_kp h = this.h();
        h.a = this.a;
        h.b = this.b;
        h.c = this.c;
        h.d = this.d;
        final psc_kr i = this.i();
        if (i != null) {
            h.e = (psc_kr)i.clone();
        }
        return h;
    }
    
    public void a(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.c(psc_dc, secureRandom);
    }
    
    public void b(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        this.c(psc_dc, secureRandom);
    }
    
    public void c(final psc_dc psc_dc, final SecureRandom secureRandom) throws psc_bf {
        byte[] a = null;
    Label_0077_Outer:
        while (true) {
            try {
                a = ((psc_j7)psc_dc).a("Clear");
                if (a != null) {
                    this.i().a(a, 0, a.length);
                }
                break Label_0077_Outer;
            }
            catch (ClassCastException ex) {
                throw new psc_bf("Invalid key type");
            }
            catch (psc_ap psc_ap) {
                throw new psc_bf("Invalid Key Type");
            }
            finally {
                Label_0125: {
                    if (a != null) {
                        for (int i = 0; i < a.length; ++i) {
                            a[i] = 0;
                        }
                        break Label_0125;
                    }
                    break Label_0125;
                }
                while (true) {}
                // iftrue(Label_0095:, n >= a.length)
                // iftrue(Label_0095:, a == null)
                while (true) {
                    int n = 0;
                    a[n] = 0;
                    ++n;
                    continue;
                    n = 0;
                    continue;
                }
                Label_0095: {
                    continue Label_0077_Outer;
                }
            }
            break;
        }
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.i().a(array, n, array2, n2);
        return this.d;
    }
    
    public int b(final byte[] array, final int n, final byte[] array2, final int n2) {
        this.i().b(array, n, array2, n2);
        return this.d;
    }
    
    public void e() {
        this.i().c();
    }
    
    public void f() {
        this.i().d();
    }
    
    public void y() {
        super.y();
        final psc_kr i = this.i();
        if (i != null) {
            i.y();
        }
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
