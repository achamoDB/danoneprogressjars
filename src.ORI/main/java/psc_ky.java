import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ky extends psc_kx implements Cloneable, Serializable
{
    private static final String a = "OFB";
    
    public psc_ky() {
        super("OFB");
    }
    
    public psc_ky(final int[] array) throws psc_be {
        super("OFB", array);
    }
    
    psc_ky(final byte[] array, final int n, final int n2, final int n3) throws psc_be, psc_gw {
        super("OFB", array, n, n2, n3);
    }
    
    public void a(final byte[] array, final int n, final int n2) throws psc_ao, psc_be, psc_gw {
        try {
            final psc_t psc_t = new psc_t(0);
            psc_l.a(array, n, new psc_i[] { psc_t });
            this.b(psc_t.d * 8);
            this.b(psc_t.b, psc_t.c, psc_t.d);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid BER encoding. (" + psc_m.getMessage() + ")");
        }
    }
    
    byte[] g() throws psc_ao {
        if (super.a == null) {
            throw new psc_ao("Cannot DER encode CBC, IV not set.");
        }
        final byte[] array = new byte[super.a.length + 2];
        array[0] = 4;
        array[1] = (byte)super.a.length;
        System.arraycopy(super.a, 0, array, 2, super.a.length);
        return array;
    }
    
    int a(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        return this.c(psc_dl, array, n, array2, n2, super.e >> 3);
    }
    
    int a(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        return this.c(psc_dl, array, n, array2, n2, n3);
    }
    
    int c(final psc_dl psc_dl, final byte[] array, int n, final byte[] array2, int n2, final int n3) {
        final int n4 = super.e >> 3;
        final int n5 = super.b.length - n4;
        final int f = (super.f + n3) % n4;
        final int n6 = (n3 - ((super.f == 0) ? 0 : (n4 - super.f))) / n4;
        boolean b = true;
        if (super.f != 0) {
            int n7 = n4;
            if (n3 <= n4 - super.f) {
                if (f != 0) {
                    n7 = f;
                }
                b = false;
            }
            for (int i = super.f; i < n7; ++i) {
                array2[n2++] = (byte)(array[n++] ^ super.c[i]);
            }
        }
        if (b) {
            for (int j = 0; j < n6; ++j) {
                psc_dl.a(super.b, 0, super.c, 0);
                System.arraycopy(super.b, n4, super.b, 0, n5);
                System.arraycopy(super.c, 0, super.b, n5, n4);
                for (int k = 0; k < n4; ++k) {
                    array2[n2++] = (byte)(array[n++] ^ super.c[k]);
                }
            }
            if (f != 0) {
                psc_dl.a(super.b, 0, super.c, 0);
                System.arraycopy(super.b, n4, super.b, 0, n5);
                System.arraycopy(super.c, 0, super.b, n5, n4);
                for (int l = 0; l < f; ++l) {
                    array2[n2++] = (byte)(array[n++] ^ super.c[l]);
                }
            }
        }
        super.f = f;
        return n3;
    }
    
    int b(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2) {
        return this.c(psc_dl, array, n, array2, n2, super.e >> 3);
    }
    
    int b(final psc_dl psc_dl, final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        return this.c(psc_dl, array, n, array2, n2, n3);
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
