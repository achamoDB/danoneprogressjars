// 
// Decompiled by Procyon v0.5.36
// 

final class psc_ln extends psc_an
{
    static void a(final psc_jt psc_jt, final byte[] array, final int n) throws psc_bf {
        final psc_r psc_r = new psc_r(0, 3);
        final psc_d4 psc_d4 = new psc_d4(0);
        psc_lo.a(array, n, psc_r, null, psc_d4);
        if (psc_r.a.compareTo("RSA") != 0) {
            throw new psc_bf("Invalid RSA key BER encoding.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(0);
        final psc_p psc_p2 = new psc_p(0);
        final psc_i[] array2 = { psc_h, psc_p, psc_p2, psc_j };
        try {
            psc_l.a(psc_d4.b, psc_d4.c, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not decode RSA key from BER. (" + psc_m.getMessage() + ")");
        }
        psc_jt.a(psc_p.b, psc_p.c, psc_p.d, psc_p2.b, psc_p2.c, psc_p2.d);
    }
    
    static byte[] a(final byte[] array, final byte[] array2) throws psc_bf {
        byte[] a;
        try {
            a = psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_p(0, true, 0, array, 0, array.length, true), new psc_p(0, true, 0, array2, 0, array2.length, true), new psc_j() });
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not BER encode the RSA key. (" + psc_m.getMessage() + ")");
        }
        try {
            return psc_lo.a("RSA", null, a);
        }
        finally {
            if (a != null) {
                psc_au.c(a);
            }
        }
    }
}
