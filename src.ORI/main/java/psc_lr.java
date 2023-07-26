// 
// Decompiled by Procyon v0.5.36
// 

final class psc_lr extends psc_an
{
    static void a(final psc_ke psc_ke, final byte[] array, final int n) throws psc_ao, psc_be {
        psc_r psc_r;
        psc_w psc_w;
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            psc_r = new psc_r(0, 1);
            psc_w = new psc_w(65536, 12288, new psc_p(0));
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, psc_w, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid DSA parameter BER encoding.(" + psc_m.getMessage() + ")");
        }
        if (psc_r.a.compareTo("SHA1/DSA/NoPad") != 0) {
            throw new psc_ao("Invalid DSA parameter BER encoding.");
        }
        if (!psc_w.a) {
            return;
        }
        int j = psc_w.j();
        if (j != 4 && j != 3) {
            throw new psc_ao("Could not read the DSA parameters BER.");
        }
        final psc_p[] array2 = new psc_p[j];
        int e = -1;
        try {
            for (int i = 0; i < j; ++i) {
                array2[i] = (psc_p)psc_w.a(i);
            }
            if (j == 4) {
                e = array2[0].e();
            }
            j -= 3;
            psc_ke.a(e, array2[j].b, array2[j].c, array2[j].d, array2[j + 1].b, array2[j + 1].c, array2[j + 1].d, array2[j + 2].b, array2[j + 2].c, array2[j + 2].d);
        }
        catch (psc_m psc_m2) {
            throw new psc_ao("Could not read the DSA parameters BER.(" + psc_m2.getMessage() + ")");
        }
    }
    
    static byte[] a(final psc_az psc_az, final psc_et psc_et, final String str, final boolean b, final psc_e0 psc_e0, final psc_e0 psc_e2, final psc_e0 psc_e3) throws psc_ao {
        int n = 1;
        String string;
        if (str == null) {
            string = psc_az.e() + "/DSA/" + psc_et.d();
        }
        else {
            if (psc_az.e().compareTo("SHA1") != 0) {
                throw new psc_ao("DER for " + str + " with " + psc_az.e() + " unknown");
            }
            if (str.compareTo("DSAWithSHA1X930") != 0) {
                n = 0;
            }
            string = str;
        }
        byte[] a = null;
        final int n2 = 0;
        int length = 0;
        if (b && psc_e0 != null && psc_e2 != null && psc_e3 != null) {
            try {
                final byte[] n3 = psc_e0.n();
                final byte[] n4 = psc_e2.n();
                final byte[] n5 = psc_e3.n();
                final psc_w psc_w = new psc_w(0, true, 0, 12288, new psc_p(0));
                if (n == 1) {
                    psc_w.b(new psc_p(0, true, 0, psc_e0.o()));
                }
                final psc_p psc_p = new psc_p(0, true, 0, n3, 0, n3.length, true);
                final psc_p psc_p2 = new psc_p(0, true, 0, n4, 0, n4.length, true);
                final psc_p psc_p3 = new psc_p(0, true, 0, n5, 0, n5.length, true);
                psc_w.b(psc_p);
                psc_w.b(psc_p2);
                psc_w.b(psc_p3);
                a = psc_l.a(new psc_i[] { psc_w });
                length = a.length;
            }
            catch (psc_ap psc_ap) {
                throw new psc_ao("DER for " + string + " unknown(" + psc_ap.getMessage());
            }
            catch (psc_m psc_m) {
                throw new psc_ao("DER for " + string + " unknown(" + psc_m.getMessage());
            }
        }
        try {
            return psc_q.a(string, 1, a, n2, length);
        }
        catch (psc_m psc_m2) {
            throw new psc_ao("DER for " + string + " unknown(" + psc_m2.getMessage());
        }
    }
}
