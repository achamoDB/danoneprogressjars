// 
// Decompiled by Procyon v0.5.36
// 

public class psc_q
{
    public static final String b(final byte[] array, final int n, final int n2, final psc_k psc_k) throws psc_m {
        return a(array, a(array, n), n2, psc_k);
    }
    
    public static final int a(final byte[] array, final int n, final int n2) throws psc_m {
        final int a = a(array, n);
        return a + a(array, a, n2, null, null);
    }
    
    public static final byte[] a(final String s, final int n, final byte[] array, final int n2, final int n3) throws psc_m {
        return a(s, n, array, n2, n3, true);
    }
    
    public static final byte[] a(final String s, final int n, final byte[] array, final int n2, final int n3, final boolean b) throws psc_m {
        return psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_r(0, true, 0, s, n), new psc_k(130816, array != null, b ? 5 : 0, array, n2, n3), new psc_j() });
    }
    
    private static int a(final byte[] array, int i) throws psc_m {
        if (array == null) {
            throw new psc_m("AlgorithmID.findAlgID: encoding should not be null.");
        }
        if (i < 0) {
            throw new psc_m("AlgorithmID.findAlgID: offset should not be less than 0.");
        }
        while (i < array.length) {
            if (array[i] != 48) {
                ++i;
            }
            else {
                final int a = psc_o.a(array, i + 1);
                if (array[i + 1 + a] == 6) {
                    return i;
                }
                i += 1 + a;
            }
        }
        throw new psc_m("AlgorithmID.findAlgID: Could not find the OID");
    }
    
    private static String a(final byte[] array, final int n, final int n2, final psc_k psc_k) throws psc_m {
        final psc_r psc_r = new psc_r(0, n2);
        a(array, n, n2, psc_k, psc_r);
        return psc_r.a;
    }
    
    private static int a(final byte[] array, final int n, final int n2, final psc_k psc_k, final psc_r psc_r) throws psc_m {
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_r psc_r2 = (psc_r == null) ? new psc_r(0, n2) : psc_r;
        final psc_k psc_k2 = (psc_k == null) ? new psc_k(130816) : psc_k;
        final int a = psc_l.a(array, n, new psc_i[] { psc_h, psc_r2, psc_k2, psc_j });
        if (psc_r != null && psc_r2.a == null) {
            throw new psc_m("No Crypto-J transformation found for the specified OID");
        }
        if (psc_r2.a.equalsIgnoreCase("PBES2")) {
            final psc_h psc_h2 = new psc_h(0);
            final psc_h psc_h3 = new psc_h(0);
            final psc_r psc_r3 = new psc_r(0, n2);
            final psc_h psc_h4 = new psc_h(0);
            final psc_t psc_t = new psc_t(0);
            final psc_p psc_p = new psc_p(0);
            final psc_p psc_p2 = new psc_p(65536);
            final psc_h psc_h5 = new psc_h(131072);
            final psc_r psc_r4 = new psc_r(0, n2);
            final psc_k psc_k3 = new psc_k(130816);
            final psc_j psc_j2 = new psc_j();
            final psc_j psc_j3 = new psc_j();
            final psc_j psc_j4 = new psc_j();
            final psc_h psc_h6 = new psc_h(0);
            final psc_r psc_r5 = new psc_r(0, n2);
            psc_l.a(array, psc_k2.c, new psc_i[] { psc_h2, psc_h3, psc_r3, psc_h4, psc_t, psc_p, psc_p2, psc_h5, psc_r4, psc_k3, psc_j2, psc_j3, psc_j4, psc_h6, psc_r5, new psc_k(130816), new psc_j(), new psc_j() });
            final StringBuffer sb = new StringBuffer("PBE/");
            String str = "SHA1";
            if (psc_r4.a) {
                final String a2 = psc_r4.a;
                if (a2 == null) {
                    throw new psc_m("Unknown HMAC/Digest algorithm specified in PKCS5v2 BER");
                }
                if (!a2.equalsIgnoreCase("HMAC/SHA1")) {
                    if (!a2.equalsIgnoreCase("HMAC/SHA1a")) {
                        if (!a2.equalsIgnoreCase("HMAC/SHA256")) {
                            throw new psc_m("Unknown HMAC/Digest algorithm specified in PKCS5v2 BER");
                        }
                        str = "SHA256";
                    }
                }
            }
            sb.append(str);
            sb.append("/");
            sb.append(psc_r5.a.substring(0, psc_r5.a.lastIndexOf(47) + 1));
            sb.append("PKCS5V2PBE");
            psc_r2.a = sb.toString();
        }
        return a;
    }
}
