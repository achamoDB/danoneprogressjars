// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_lv extends psc_an
{
    public static void a(final psc_dn psc_dn, final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm, final byte[] array, final int n, final int n2) throws psc_ao, psc_gw, psc_be {
        if (array == null || array.length == 0 || array.length < n + n2) {
            throw new psc_ao("Invalid PKCS5V2 BER encoding.");
        }
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_h psc_h2 = new psc_h(0);
        final psc_r psc_r = new psc_r(0);
        final psc_h psc_h3 = new psc_h(0);
        final psc_t psc_t = new psc_t(0);
        final psc_p psc_p = new psc_p(0);
        final psc_p psc_p2 = new psc_p(65536);
        final psc_k psc_k = new psc_k(130816);
        final psc_j psc_j2 = new psc_j();
        final psc_j psc_j3 = new psc_j();
        final psc_h psc_h4 = new psc_h(0);
        final psc_r psc_r2 = new psc_r(0);
        final psc_k psc_k2 = new psc_k(130816);
        final psc_i[] array2 = { psc_h, psc_h2, psc_r, psc_h3, psc_t, psc_p, psc_p2, psc_k, psc_j2, psc_j3, psc_h4, psc_r2, psc_k2, new psc_j(), psc_j };
        int e;
        try {
            psc_l.a(array, n, array2);
            e = psc_p.e();
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid PKCS5 BER encoding. (" + psc_m.getMessage() + ")");
        }
        int n3 = -1;
        try {
            if (psc_p2.a) {
                n3 = psc_p2.e() * 8;
            }
        }
        catch (psc_m psc_m2) {
            throw new psc_ao("Invalid PKCS5 BER encoding. (" + psc_m2.getMessage() + ")");
        }
        int[] array3;
        if (n3 == -1) {
            array3 = new int[] { e };
        }
        else {
            array3 = new int[] { e, n3 };
        }
        try {
            psc_dn.a(array3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Invalid PKCS5 BER encoding.");
        }
        psc_dn.a(psc_t.b, psc_t.c, psc_t.d);
        ((psc_dl)psc_dg).a(array, psc_k2.c, psc_k2.d, psc_di, psc_dm);
    }
    
    public static byte[] a(final psc_dn psc_dn, final psc_az psc_az, final psc_dg psc_dg, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao {
        try {
            final byte[] a = ((psc_dl)psc_dg).a(psc_di.g());
            final byte[] a2 = psc_q.a(psc_dg.d() + "/CBC/PKCS5Padding", 8, a, 0, a.length);
            final psc_k psc_k = new psc_k(65280, true, 5, a2, 0, a2.length);
            byte[] a3 = null;
            if (psc_az.e().compareTo("SHA1") != 0) {
                a3 = psc_q.a("HMAC/" + psc_az.e(), 14, null, 0, 0, false);
            }
            final psc_k psc_k2 = new psc_k(196352, a3 != null, 5, a3, 0, (a3 == null) ? 0 : a3.length);
            final byte[] g = psc_dn.g();
            if (g == null) {
                throw new psc_ao("Salt not set.");
            }
            final psc_t psc_t = new psc_t(0, true, 0, g, 0, g.length);
            final int[] c = psc_dn.c();
            final boolean b = c.length != 1;
            final byte[] a4 = psc_l.a(new psc_i[] { new psc_h(0, true, 0), psc_t, new psc_p(0, true, 0, c[0]), new psc_p(65536, b, 0, b ? (c[1] / 8) : 0), psc_k2, new psc_j() });
            final byte[] a5 = psc_q.a("PBKDF2", -1, a4, 0, a4.length);
            final byte[] a6 = psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_k(65280, true, 5, a5, 0, a5.length), psc_k, new psc_j() });
            return psc_q.a("PBES2", -1, a6, 0, a6.length);
        }
        catch (psc_m psc_m) {
            throw new psc_ao(psc_m.getMessage());
        }
    }
}
