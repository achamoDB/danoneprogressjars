import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_kw extends psc_kx implements Cloneable, Serializable
{
    private static final String a = "CFB";
    
    public psc_kw() {
        super("CFB");
    }
    
    public psc_kw(final int[] array) throws psc_be {
        super("CFB", array);
    }
    
    psc_kw(final byte[] array, final int n, final int n2, final int n3) throws psc_be, psc_gw {
        super("CFB", array, n, n2, n3);
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_t psc_t = new psc_t(0);
            final psc_p psc_p = new psc_p(0);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_t, psc_p, psc_j });
            this.b(psc_p.e());
            this.b(psc_t.b, psc_t.c, psc_t.d);
        }
        catch (psc_m obj) {
            throw new psc_ao("Invalid BER encoding. (" + obj + ")");
        }
    }
    
    byte[] g() throws psc_ao {
        if (super.a == null || super.e == 0) {
            throw new psc_ao("Cannot DER encode CBC, IV or bit length not set.");
        }
        try {
            return psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_t(0, true, 0, super.a, 0, super.a.length), new psc_p(0, true, 0, super.e), new psc_j() });
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
    
    int a(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        return this.a(psc_dl, array, n, array2, n2, array2, n2, super.e >> 3);
    }
    
    int a(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        return this.a(psc_dl, array, n, array2, n2, array2, n2, n3);
    }
    
    int a(final psc_dl psc_dl, final byte[] array, int n, final byte[] array2, int n2, final byte[] array3, int n3, final int n4) {
        final int n5 = super.e >> 3;
        final int n6 = super.b.length - n5;
        final int f = (super.f + n4) % n5;
        final int n7 = (n4 - ((super.f == 0) ? 0 : (n5 - super.f))) / n5;
        boolean b = true;
        if (super.f != 0) {
            int n8 = n5;
            if (n4 <= n5 - super.f) {
                if (f != 0) {
                    n8 = f;
                }
                b = false;
            }
            for (int i = super.f; i < n8; ++i) {
                array2[n2++] = (byte)(array[n++] ^ super.c[i]);
                super.b[n6 + i] = array3[n3++];
            }
        }
        if (b) {
            for (int j = 0; j < n7; ++j) {
                psc_dl.a(super.b, 0, super.c, 0);
                System.arraycopy(super.b, n5, super.b, 0, n6);
                for (int k = 0; k < n5; ++k) {
                    array2[n2++] = (byte)(array[n++] ^ super.c[k]);
                    super.b[n6 + k] = array3[n3++];
                }
            }
            if (f != 0) {
                psc_dl.a(super.b, 0, super.c, 0);
                System.arraycopy(super.b, n5, super.b, 0, n6);
                for (int l = 0; l < f; ++l) {
                    array2[n2++] = (byte)(array[n++] ^ super.c[l]);
                    super.b[n6 + l] = array3[n3++];
                }
            }
        }
        super.f = f;
        return n4;
    }
    
    int b(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        return this.a(psc_dl, array, n, array2, n2, array, n, super.e >> 3);
    }
    
    int b(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        return this.a(psc_dl, array, n, array2, n2, array, n, n3);
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
