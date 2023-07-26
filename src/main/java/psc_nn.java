// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_nn extends psc_an
{
    static boolean a(final psc_i9 psc_i9, final byte[] array, final int n) throws psc_ao, psc_be {
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_r psc_r = new psc_r(0, 5);
        final psc_h psc_h2 = new psc_h(65536);
        final psc_p psc_p = new psc_p(0);
        final psc_p psc_p2 = new psc_p(0);
        final psc_p psc_p3 = new psc_p(65536);
        final psc_i[] array2 = { psc_h, psc_r, psc_h2, psc_p, psc_p2, psc_p3, psc_j, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid DH parameter BER encoding.(" + psc_m.getMessage() + ")");
        }
        if (!psc_h2.a) {
            return false;
        }
        try {
            psc_i9.a(psc_p.b, psc_p.c, psc_p.d, psc_p2.b, psc_p2.c, psc_p2.d, -1, psc_p3.a, psc_p3.b, psc_p3.c, psc_p3.d);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao(psc_ap.getMessage());
        }
        return true;
    }
    
    static byte[] a(final psc_e0 psc_e0, final psc_e0 psc_e2, final int n) throws psc_ao {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(0, true, 0, "DH", 5);
            boolean b = true;
            if (psc_e0 == null || psc_e2 == null) {
                b = false;
            }
            byte[] n2 = null;
            if (psc_e0 != null) {
                try {
                    n2 = psc_e0.n();
                }
                catch (psc_ap psc_ap) {
                    n2 = null;
                }
            }
            psc_p psc_p;
            if (n2 == null) {
                b = false;
                psc_p = new psc_p(0, false, 0, 0);
            }
            else {
                psc_p = new psc_p(0, true, 0, n2, 0, n2.length, true);
            }
            byte[] n3 = null;
            if (psc_e2 != null) {
                try {
                    n3 = psc_e2.n();
                }
                catch (psc_ap psc_ap2) {
                    n3 = null;
                }
            }
            psc_p psc_p2;
            if (n3 == null) {
                b = false;
                psc_p2 = new psc_p(0, false, 0, 0);
            }
            else {
                psc_p2 = new psc_p(0, true, 0, n3, 0, n3.length, true);
            }
            return psc_l.a(new psc_i[] { psc_h, psc_r, new psc_h(65536, b, 0), psc_p, psc_p2, new psc_p(65536, true, 0, n), psc_j, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_ao("DER for DH unknown: (" + psc_m.getMessage() + ")");
        }
    }
}
