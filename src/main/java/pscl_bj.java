// 
// Decompiled by Procyon v0.5.36
// 

public final class pscl_bj extends pscl_bd
{
    public static int[] a(final byte[] array, final int n, final pscl_bi pscl_bi) throws pscl_bk {
        final pscl_z pscl_z = new pscl_z(0, true, 0);
        final pscl_ac pscl_ac = new pscl_ac();
        final pscl_t pscl_t = new pscl_t(130816, true, 0, null, 0, 0);
        final pscl_s pscl_s = new pscl_s(0, true, 0, null, 0, 0);
        final pscl_q[] array2 = { pscl_z, pscl_t, pscl_s, pscl_ac };
        try {
            pscl_u.a(array, n, array2);
        }
        catch (pscl_x pscl_x) {
            throw new pscl_bk("Cannot build the PKCS #8 encrypted key.");
        }
        final pscl_z pscl_z2 = new pscl_z(0, true, 0);
        final pscl_z pscl_z3 = new pscl_z(0, true, 0);
        final pscl_aa pscl_aa = new pscl_aa(0, true, 0, null, 0, 0, 10, 11);
        final pscl_s pscl_s2 = new pscl_s(0, true, 0, null, 0, 0);
        final pscl_r pscl_r = new pscl_r(0, true, 0, 0);
        final pscl_q[] array3 = { pscl_z2, pscl_aa, pscl_z3, pscl_s2, pscl_r, pscl_ac, pscl_ac };
        try {
            pscl_u.a(pscl_t.l, pscl_t.m, array3);
        }
        catch (pscl_x pscl_x2) {
            throw new pscl_bk("Invalid PKCS12 BER encoding.");
        }
        if (pscl_r.n > 4) {
            throw new pscl_bk("Invalid PKCS12 BER encoding.");
        }
        int n2 = pscl_r.l[pscl_r.m] & 0xFF;
        for (int i = pscl_r.n - 1, n3 = pscl_r.m + 1; i > 0; --i, ++n3) {
            n2 = (n2 << 8 | (pscl_r.l[n3] & 0xFF));
        }
        final int[] array4 = { n2 };
        try {
            pscl_bi.a(array4);
        }
        catch (pscl_bk pscl_bk) {
            throw new pscl_bk("Invalid PKCS12 BER encoding.");
        }
        pscl_bi.a(pscl_s2.l, pscl_s2.m, pscl_s2.n);
        return new int[] { pscl_s.m, pscl_s.n };
    }
}
