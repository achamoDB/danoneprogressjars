import java.security.SecureRandom;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_m5 implements Cloneable, Serializable
{
    private static final int a = 8;
    private static int b;
    
    psc_m5() {
    }
    
    static byte[] a(final psc_ah psc_ah, final byte[] array, final int n, final int n2, final String s, final int n3, final char[] array2) throws psc_m0 {
        psc_eh a = null;
        byte[] array3 = null;
        final byte[] array4 = new byte[8];
        final String f = psc_ah.f();
        try {
            a = psc_eh.a(a(s, n3), f);
            psc_ah.e().b(array4, 0, 8);
            a.b(array4, 0, array4.length);
            final psc_dc n4 = a.n();
            n4.a(array2, 0, array2.length);
            a.b(n4, null);
            array3 = new byte[a.m()];
            a.a(array, n, n2);
            a.c(array3, 0);
        }
        catch (psc_ap psc_ap) {
            throw new psc_m0("MacData.computeMac: JSAFE operation failed(" + psc_ap.getMessage() + ").");
        }
        catch (psc_d6 psc_d6) {
            throw new psc_m0("MacData.computeMac: " + psc_d6.getMessage());
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
        byte[] array5;
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_h psc_h2 = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, psc_h2, new psc_r(0, true, 0, s, 10), new psc_k(77824, false, 0, null, 0, 0), psc_j, new psc_t(0, true, 0, array3, 0, array3.length), psc_j });
            array5 = new byte[psc_n.a()];
            psc_n.a(array5, 0);
        }
        catch (psc_m psc_m) {
            throw new psc_m0("MacData.encodeMac: Encoding DigestInfo failed(" + psc_m.getMessage() + ").");
        }
        try {
            final psc_n psc_n2 = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_k(12288, true, 0, array5, 0, array5.length), new psc_t(0, true, 0, array4, 0, 8), new psc_p(131072, n3 != psc_m5.b, 0, n3), new psc_j() });
            final byte[] array6 = new byte[psc_n2.a()];
            psc_n2.a(array6, 0);
            return array6;
        }
        catch (psc_m psc_m2) {
            throw new psc_m0("MacData.encodeMac: Encoding MacData failed(" + psc_m2.getMessage() + ").");
        }
    }
    
    psc_m5(final psc_ah psc_ah, final char[] array, final byte[] array2, final byte[] array3, final int n, final int n2) throws psc_m0 {
        if (psc_ah == null) {
            throw new psc_m0("MacData.MacData: certJ can not be null.");
        }
        try {
            final psc_h psc_h = new psc_h(n2);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(12288);
            final psc_t psc_t = new psc_t(0);
            final psc_p psc_p = new psc_p(131072);
            psc_l.a(array3, n, new psc_i[] { psc_h, psc_k, psc_t, psc_p, psc_j });
            int n3 = psc_m5.b;
            if (psc_p.a) {
                n3 = psc_p.e();
            }
            final psc_h psc_h2 = new psc_h(0);
            final psc_h psc_h3 = new psc_h(0);
            final psc_r psc_r = new psc_r(0, 10);
            final psc_k psc_k2 = new psc_k(77824);
            final psc_t psc_t2 = new psc_t(0);
            psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_h2, psc_h3, psc_r, psc_k2, psc_j, psc_t2, psc_j });
            final psc_eh a = psc_eh.a(a(psc_r.a, n3), psc_ah.f());
            a.b(psc_t.b, psc_t.c, psc_t.d);
            final psc_dc n4 = a.n();
            n4.a(array, 0, array.length);
            a.c(n4, null);
            a.c(array2, 0, array2.length);
            if (!a.d(psc_t2.b, psc_t2.c, psc_t2.d)) {
                throw new psc_m0("MacData.MacData: MAC Verification failed");
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m0("Cannot decode the BER of the MacData.");
        }
        catch (psc_en psc_en) {
            throw new psc_m0(psc_en.getMessage());
        }
        catch (psc_ao psc_ao) {
            throw new psc_m0(psc_ao.getMessage());
        }
        catch (psc_bf psc_bf) {
            throw new psc_m0(psc_bf.getMessage());
        }
        catch (psc_be psc_be) {
            throw new psc_m0(psc_be.getMessage());
        }
    }
    
    private static String a(final String str, final int i) {
        return "PBE/HMAC/" + str + "/PKCS12V1PBE-" + i + "-160";
    }
    
    static {
        psc_m5.b = 1;
    }
}
