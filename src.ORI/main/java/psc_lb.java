// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_lb extends psc_an
{
    public static void a(final psc_dn psc_dn, final psc_dg psc_dg, final int n, final byte[] array, final int n2, final int n3) throws psc_ao {
        if (array == null || array.length == 0) {
            throw new psc_ao("Invalid PKIX BER encoding.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_t psc_t = new psc_t(0);
        final psc_k psc_k = new psc_k(0);
        final psc_p psc_p = new psc_p(0);
        final psc_i[] array2 = { psc_h, psc_t, psc_k, psc_p, new psc_k(0), psc_j };
        int e;
        try {
            psc_l.a(array, n2, array2);
            e = psc_p.e();
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid PKIX BER encoding. (" + psc_m.getMessage() + ")");
        }
        final int[] array3 = { e };
        try {
            psc_dn.a(array3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Invalid PKIX BER encoding.");
        }
        psc_dn.a(psc_t.b, psc_t.c, psc_t.d);
        if (psc_dg != null) {
            psc_dg.a(n);
        }
    }
    
    public static byte[] a(final String s, final String s2, final int n, final byte[] array) throws psc_ao {
        if (n < 1) {
            throw new psc_ao("Cannot DER encode PKIX PBE, no iteration count.");
        }
        if (array == null || array.length < 1) {
            throw new psc_ao("Cannot DER encode PKIX PBE, no salt.");
        }
        final byte[] array2 = { 5, 0 };
        byte[] a;
        byte[] a2;
        try {
            a = psc_q.a(s2, 10, array2, 0, 2);
            a2 = psc_q.a(s, 6, array2, 0, 2);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Cannot DER encode PKIX PBE, unfamiliar information.(" + psc_m.getMessage() + ")");
        }
        try {
            return psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_t(0, true, 0, array, 0, array.length), new psc_k(0, true, 0, a, 0, a.length), new psc_p(0, true, 0, n), new psc_k(0, true, 0, a2, 0, a2.length), new psc_j() });
        }
        catch (psc_m psc_m2) {
            return null;
        }
    }
    
    public static byte[] a(final int n, final byte[] array) throws psc_ao {
        throw new psc_ao("Missing info for PKIX algID.");
    }
}
