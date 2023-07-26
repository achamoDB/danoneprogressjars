// 
// Decompiled by Procyon v0.5.36
// 

final class psc_lp extends psc_an
{
    static void a(final psc_ju psc_ju, final byte[] array, final int n) throws psc_bf {
        final psc_r psc_r = new psc_r(0, 3);
        final psc_k psc_k = new psc_k(77824);
        final psc_d4 psc_d4 = new psc_d4(0);
        psc_lo.a(array, n, psc_r, psc_k, psc_d4);
        if (psc_r.a.compareTo("DSA") != 0) {
            throw new psc_bf("Invalid DSA public key BER encoding.");
        }
        final psc_p psc_p = new psc_p(0);
        final psc_i[] array2 = { psc_p };
        try {
            psc_l.a(psc_d4.b, psc_d4.c, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not decode DSA key from BER: (" + psc_m.getMessage() + ")");
        }
        if (psc_k.a) {
            psc_w psc_w;
            try {
                psc_w = new psc_w(0, 12288, new psc_p(0));
                psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_w });
            }
            catch (psc_m psc_m2) {
                throw new psc_bf("Could not decode DSA key from BER. (" + psc_m2.getMessage() + ")");
            }
            int j = psc_w.j();
            if (j != 4 && j != 3) {
                throw new psc_bf("Could not read the DSA parameters BER.");
            }
            final psc_p[] array3 = new psc_p[j];
            int e = -1;
            try {
                for (int i = 0; i < j; ++i) {
                    array3[i] = (psc_p)psc_w.a(i);
                }
                if (j == 4) {
                    e = array3[0].e();
                }
            }
            catch (psc_m psc_m3) {
                throw new psc_bf("Could not read the DSA parameters BER. (" + psc_m3.getMessage() + ")");
            }
            j -= 3;
            psc_ju.a(e, array3[j].b, array3[j].c, array3[j].d, array3[j + 1].b, array3[j + 1].c, array3[j + 1].d, array3[j + 2].b, array3[j + 2].c, array3[j + 2].d);
        }
        psc_ju.a(psc_p.b, psc_p.c, psc_p.d);
    }
    
    static byte[] a(final String s, final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4) throws psc_bf {
        byte[] a = null;
        if (array != null && array3 != null && array2 != null) {
            try {
                final psc_w psc_w = new psc_w(65536, true, 0, 12288, new psc_p(0));
                if (s == null) {
                    int n = array.length * 8;
                    for (int n2 = array[0] & 0xFF; (n2 & 0x80) == 0x0; n2 <<= 1) {
                        --n;
                    }
                    psc_w.b(new psc_p(0, true, 0, n));
                }
                final psc_p psc_p = new psc_p(0, true, 0, array, 0, array.length, true);
                final psc_p psc_p2 = new psc_p(0, true, 0, array2, 0, array2.length, true);
                final psc_p psc_p3 = new psc_p(0, true, 0, array3, 0, array3.length, true);
                psc_w.b(psc_p);
                psc_w.b(psc_p2);
                psc_w.b(psc_p3);
                a = psc_l.a(new psc_i[] { psc_w });
            }
            catch (psc_m psc_m) {
                throw new psc_bf("Could not compute the DSA parameters BER. (" + psc_m.getMessage() + ")");
            }
        }
        byte[] a2;
        try {
            a2 = psc_l.a(new psc_i[] { new psc_p(0, true, 0, array4, 0, array4.length, true) });
        }
        catch (psc_m psc_m2) {
            throw new psc_bf("Could not compute DSA public key BER: (" + psc_m2.getMessage() + ")");
        }
        String s2 = "DSA";
        if (s != null) {
            s2 = s;
        }
        try {
            return psc_lo.a(s2, a, a2);
        }
        finally {
            if (a2 != null) {
                psc_au.c(a2);
            }
        }
    }
}
