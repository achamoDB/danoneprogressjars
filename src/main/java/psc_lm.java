// 
// Decompiled by Procyon v0.5.36
// 

final class psc_lm extends psc_an
{
    static void a(final psc_js psc_js, final byte[] array, final int n) throws psc_bf {
        final psc_r psc_r = new psc_r(0, 3);
        final psc_k psc_k = new psc_k(77824);
        final psc_t psc_t = new psc_t(0);
        psc_lh.a(array, n, psc_r, psc_k, psc_t);
        if (psc_r.a.compareTo("DH") != 0) {
            throw new psc_bf("Invalid DH private key BER encoding.");
        }
        final psc_p psc_p = new psc_p(0);
        final psc_i[] array2 = { psc_p };
        try {
            psc_l.a(psc_t.b, psc_t.c, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not decode DH key from BER. (" + psc_m.getMessage() + ")");
        }
        if (psc_k.a) {
            psc_w psc_w;
            try {
                psc_w = new psc_w(0, 12288, new psc_p(0));
                psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_w });
            }
            catch (psc_m psc_m2) {
                throw new psc_bf("Could not decode DH key from BER. (" + psc_m2.getMessage() + ")");
            }
            final int j = psc_w.j();
            if (j != 3 && j != 2) {
                throw new psc_bf("Could not read the DH parameters BER.");
            }
            final psc_p[] array3 = new psc_p[j];
            int e = -1;
            try {
                for (int i = 0; i < j; ++i) {
                    array3[i] = (psc_p)psc_w.a(i);
                }
                if (j == 3) {
                    e = array3[2].e();
                }
            }
            catch (psc_m psc_m3) {
                throw new psc_bf("Could not read the DSA parameters BER. (" + psc_m3.getMessage() + ")");
            }
            psc_js.a(array3[0].b, array3[0].c, array3[0].d, array3[1].b, array3[1].c, array3[1].d, e, false, null, 0, 0);
        }
        psc_js.a(psc_p.b, psc_p.c, psc_p.d);
    }
    
    static byte[] a(final byte[] array, final byte[] array2, final byte[] array3, final int n) throws psc_bf {
        byte[] a = null;
        if (array != null && array2 != null) {
            try {
                final psc_w psc_w = new psc_w(65536, true, 0, 12288, new psc_p(0));
                final psc_p psc_p = new psc_p(0, true, 0, array, 0, array.length, true);
                final psc_p psc_p2 = new psc_p(0, true, 0, array2, 0, array2.length, true);
                psc_w.b(psc_p);
                psc_w.b(psc_p2);
                if (n > 0) {
                    psc_w.b(new psc_p(0, true, 0, n));
                }
                a = psc_l.a(new psc_i[] { psc_w });
            }
            catch (psc_m psc_m) {
                throw new psc_bf("Could not compute the DH parameters BER. (" + psc_m.getMessage() + ")");
            }
        }
        byte[] a2 = null;
        try {
            a2 = psc_l.a(new psc_i[] { new psc_p(0, true, 0, array3, 0, array3.length, true) });
        }
        catch (psc_m psc_m2) {
            if (a2 != null) {
                psc_au.c(a2);
            }
            throw new psc_bf("Could not compute DH private key BER. (" + psc_m2.getMessage() + ")");
        }
        try {
            return psc_lh.a("DH", a, a2);
        }
        finally {
            if (a2 != null) {
                psc_au.c(a2);
            }
        }
    }
}
