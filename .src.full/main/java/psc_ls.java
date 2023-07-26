// 
// Decompiled by Procyon v0.5.36
// 

public final class psc_ls extends psc_an
{
    public static void a(final psc_dg psc_dg, final byte[] array, final int n, final int n2, final psc_di psc_di, final psc_dm psc_dm) throws psc_ao, psc_be, psc_gw {
        int e = 32;
        if ((array[n] & 0xFF) != 0x30) {
            psc_di.a(array, n, n2);
        }
        else {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(130816);
            final psc_i[] array2 = { psc_h, psc_p, psc_k, psc_j };
            try {
                psc_l.a(array, n, array2);
                e = psc_p.e();
            }
            catch (psc_m psc_m) {
                throw new psc_ao("Invalid RC2 BER encoding. (" + psc_m.getMessage() + ")");
            }
            if (e < 256) {
                for (int i = 0; i < 256; ++i) {
                    if (psc_n3.b[i] == (byte)e) {
                        e = i;
                        break;
                    }
                }
            }
            psc_di.a(psc_k.b, psc_k.c, psc_k.d);
        }
        final int[] array3 = { e };
        try {
            psc_dg.a(array3);
        }
        catch (psc_ap psc_ap) {
            throw new psc_ao("Invalid RC2 BER encoding.");
        }
    }
    
    public static byte[] a(final int n, final byte[] array) {
        if (n == 32) {
            return array;
        }
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, (n >= 256) ? n : (psc_n3.b[n] & 0xFF));
            boolean b = false;
            int length = 0;
            if (array != null) {
                b = true;
                length = array.length;
            }
            return psc_l.a(new psc_i[] { psc_h, psc_p, new psc_k(130816, b, 5, array, 0, length), psc_j });
        }
        catch (psc_m psc_m) {
            return null;
        }
    }
}
