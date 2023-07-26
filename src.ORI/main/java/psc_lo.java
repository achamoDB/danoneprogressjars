// 
// Decompiled by Procyon v0.5.36
// 

final class psc_lo extends psc_an
{
    static int a(final byte[] array, final int n, psc_r psc_r, psc_k psc_k, psc_d4 psc_d4) throws psc_bf {
        final psc_h psc_h = new psc_h(0);
        final psc_h psc_h2 = new psc_h(0);
        final psc_j psc_j = new psc_j();
        if (psc_r == null) {
            psc_r = new psc_r(0, 3);
        }
        if (psc_k == null) {
            psc_k = new psc_k(130816);
        }
        if (psc_d4 == null) {
            psc_d4 = new psc_d4(0);
        }
        final psc_i[] array2 = { psc_h, psc_h2, psc_r, psc_k, psc_j, psc_d4, psc_j };
        try {
            return psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not decode public key from BER. (" + psc_m.getMessage() + ")");
        }
    }
    
    static byte[] a(final String s, final byte[] array, final byte[] array2) throws psc_bf {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_h psc_h2 = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(0, true, 0, s, 3);
            int length = 0;
            boolean b = true;
            if (array == null) {
                b = false;
            }
            else {
                length = array.length;
            }
            return psc_l.a(new psc_i[] { psc_h, psc_h2, psc_r, new psc_k(77824, b, 5, array, 0, length), psc_j, new psc_d4(0, true, 0, array2, 0, array2.length, array2.length * 8, false), psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not DER encode the public key. (" + psc_m.getMessage() + ")");
        }
    }
}
