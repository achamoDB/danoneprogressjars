// 
// Decompiled by Procyon v0.5.36
// 

final class psc_ly extends psc_an
{
    static void a(final psc_jp psc_jp, final byte[] array, final int n) throws psc_ao {
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
            throw new psc_ao("Invalid DH parameter BER encoding. (" + psc_m.getMessage() + ")");
        }
        if (psc_r.a.compareTo("DH") != 0) {
            throw new psc_ao("Invalid DH parameter BER encoding.");
        }
        if (!psc_h2.a) {
            return;
        }
        try {
            psc_jp.a(psc_p.b, psc_p.c, psc_p.d, psc_p2.b, psc_p2.c, psc_p2.d, -1, psc_p3.a, psc_p3.b, psc_p3.c, psc_p3.d);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao(psc_ap.getMessage());
        }
    }
    
    static byte[] a(final byte[] array, final byte[] array2, final int n) throws psc_ao {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_h psc_h2 = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            return psc_l.a(new psc_i[] { psc_h, new psc_r(0, true, 0, "DH", 5), psc_h2, new psc_p(0, true, 0, array, 0, array.length, true), new psc_p(0, true, 0, array2, 0, array2.length, true), new psc_p(65536, true, 0, n), psc_j, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid DH parameter BER encoding. (" + psc_m.getMessage() + ")");
        }
    }
}
