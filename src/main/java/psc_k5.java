// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_k5 extends psc_an
{
    static byte[] a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws psc_en {
        try {
            final byte[] a = psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_k(12288, true, 0, array, n, n2), new psc_t(0, true, 0, array2, n3, n4), new psc_j() });
            if (a == null) {
                throw new psc_en("Cannot build the PKCS #8 encrypted key.");
            }
            return a;
        }
        catch (psc_m psc_m) {
            throw new psc_en("Cannot build the PKCS #8 encrypted key. (" + psc_m.getMessage() + ")");
        }
    }
    
    static int[] a(final byte[] array, final int n) throws psc_en {
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_k psc_k = new psc_k(12288);
        final psc_t psc_t = new psc_t(0);
        final psc_i[] array2 = { psc_h, psc_k, psc_t, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_en("Cannot build the PKCS #8 encrypted key. (" + psc_m.getMessage() + ")");
        }
        return new int[] { psc_t.c, psc_t.d };
    }
}
