// 
// Decompiled by Procyon v0.5.36
// 

final class psc_lc extends psc_an
{
    static void a(final psc_jo psc_jo, final byte[] array, final int n) throws psc_ao {
        psc_r psc_r;
        psc_w psc_w;
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            psc_r = new psc_r(0, 4);
            psc_w = new psc_w(65536, 12288, new psc_p(0));
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, psc_w, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid DSA parameter BER encoding.(" + psc_m.getMessage() + ")");
        }
        if (psc_r.a.compareTo("DSA") != 0) {
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
            psc_jo.a(e, array2[j].b, array2[j].c, array2[j].d, array2[j + 1].b, array2[j + 1].c, array2[j + 1].d, array2[j + 2].b, array2[j + 2].c, array2[j + 2].d);
        }
        catch (psc_m psc_m2) {
            throw new psc_ao("Could not read the DSA parameters BER. (" + psc_m2.getMessage() + ")");
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao(psc_ap.getMessage());
        }
    }
    
    static byte[] a(final String s, final byte[] array, final byte[] array2, final byte[] array3) throws psc_ao {
        psc_h psc_h;
        psc_r psc_r;
        psc_w psc_w;
        try {
            psc_h = new psc_h(0, true, 0);
            String s2 = "DSA";
            if (s != null) {
                s2 = s;
            }
            psc_r = new psc_r(0, true, 0, s2, 4);
            psc_w = new psc_w(65536, true, 0, 12288, new psc_p(0));
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
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not compute the DSA parameters BER. (" + psc_m.getMessage() + ")");
        }
        final psc_i[] array4 = { psc_h, psc_r, psc_w, new psc_j() };
        try {
            return psc_l.a(array4);
        }
        catch (psc_m psc_m2) {
            throw new psc_ao("Invalid DSA parameter BER encoding. (" + psc_m2.getMessage() + ")");
        }
    }
}
