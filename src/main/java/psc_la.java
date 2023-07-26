// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_la extends psc_an
{
    public static void a(final psc_dn psc_dn, final psc_dg psc_dg, final int n, final byte[] array, final int n2, final int n3) throws psc_ao {
        if (array == null || array.length == 0) {
            throw new psc_ao("Invalid PKCS12 BER encoding.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_t psc_t = new psc_t(0);
        final psc_p psc_p = new psc_p(0);
        final psc_i[] array2 = { psc_h, psc_t, psc_p, psc_j };
        int e;
        try {
            psc_l.a(array, n2, array2);
            e = psc_p.e();
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid PKCS12 BER encoding. (" + psc_m.getMessage() + ")");
        }
        final int[] array3 = { e };
        try {
            psc_dn.a(array3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Invalid PKCS12 BER encoding.");
        }
        psc_dn.a(psc_t.b, psc_t.c, psc_t.d);
        psc_dg.a(n);
    }
    
    public static byte[] a(final int n, final byte[] array) throws psc_ao {
        if (n < 1) {
            throw new psc_ao("Cannot DER encode PKCS #12 PBE, no iteration count.");
        }
        if (array == null || array.length < 1) {
            throw new psc_ao("Cannot DER encode PKCS #12 PBE, no salt.");
        }
        try {
            return psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_t(0, true, 0, array, 0, array.length), new psc_p(0, true, 0, n), new psc_j() });
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
