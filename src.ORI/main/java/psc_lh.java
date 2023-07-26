// 
// Decompiled by Procyon v0.5.36
// 

final class psc_lh extends psc_an
{
    static int a(final byte[] array, final int n, psc_r psc_r, psc_k psc_k, psc_t psc_t) throws psc_bf {
        final psc_h psc_h = new psc_h(0);
        final psc_h psc_h2 = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(0);
        if (psc_r == null) {
            psc_r = new psc_r(0, 3);
        }
        if (psc_k == null) {
            psc_k = new psc_k(130816);
        }
        if (psc_t == null) {
            psc_t = new psc_t(0);
        }
        final psc_i[] array2 = { psc_h, psc_p, psc_h2, psc_r, psc_k, psc_j, psc_t, new psc_k(8462336), psc_j };
        try {
            return psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not decode key from BER. (" + psc_m.getMessage() + ")");
        }
    }
    
    static byte[] a(final String s, final byte[] array, final byte[] array2) throws psc_bf {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_h psc_h2 = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, 0);
            final psc_r psc_r = new psc_r(0, true, 0, s, 3);
            int length = 0;
            boolean b = true;
            if (array != null) {
                length = array.length;
            }
            else {
                b = false;
            }
            return psc_l.a(new psc_i[] { psc_h, psc_p, psc_h2, psc_r, new psc_k(77824, b, 5, array, 0, length), psc_j, new psc_t(0, true, 0, array2, 0, array2.length), new psc_k(8462336, false, 0, null, 0, 0), psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not compute private key DER. (" + psc_m.getMessage() + ")");
        }
    }
}
