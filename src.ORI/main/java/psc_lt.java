// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_lt extends psc_an
{
    public static void a(final psc_dg psc_dg, final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(0);
        final psc_p psc_p2 = new psc_p(0);
        final psc_p psc_p3 = new psc_p(0);
        final psc_k psc_k = new psc_k(130816);
        final psc_i[] array2 = { psc_h, psc_p, psc_p2, psc_p3, psc_k, psc_j };
        int e;
        int e2;
        int e3;
        try {
            psc_l.a(array, n, array2);
            e = psc_p.e();
            e2 = psc_p2.e();
            e3 = psc_p3.e();
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid RC5 BER encoding. (" + psc_m.getMessage() + ")");
        }
        final int n3 = e3 / 2;
        try {
            psc_dg.a(new int[] { e, n3, e2 });
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Invalid RC2 BER encoding.");
        }
        psc_di.a(psc_k.b, psc_k.c, psc_k.d);
    }
    
    public static byte[] a(final int n, final int n2, final int n3, final byte[] array) {
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, n);
            final psc_p psc_p2 = new psc_p(0, true, 0, n3);
            final psc_p psc_p3 = new psc_p(0, true, 0, n2 * 2);
            boolean b = false;
            int length = 0;
            if (array != null) {
                b = true;
                length = array.length;
            }
            return psc_l.a(new psc_i[] { psc_h, psc_p, psc_p2, psc_p3, new psc_k(130816, b, 5, array, 0, length), psc_j });
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
