// 
// Decompiled by Procyon v0.5.36
// 

final class psc_gu extends psc_an
{
    static byte[] a(final StringBuffer sb, final byte[] array, final int n) throws psc_bf {
        sb.setLength(0);
        final psc_h psc_h = new psc_h(0);
        final psc_h psc_h2 = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_r psc_r = new psc_r(16777216);
        final psc_k psc_k = new psc_k(77824);
        final psc_t psc_t = new psc_t(0);
        final psc_i[] array2 = { psc_h, psc_h2, psc_r, psc_k, psc_j, psc_t, psc_j };
        try {
            psc_l.a(array, n, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not decode Secret key from BER.(" + psc_m.getMessage() + ").");
        }
        final char[] str = new char[psc_r.d / 2];
        int c = psc_r.c;
        for (int i = 0; i < str.length; ++i) {
            str[i] = (char)(psc_r.b[c] << 8);
            final char[] array3 = str;
            final int n2 = i;
            array3[n2] |= (char)(psc_r.b[c + 1] & 0xFF);
            c += 2;
        }
        sb.insert(0, str);
        final byte[] array4 = new byte[psc_t.d];
        if (psc_t.b != null) {
            System.arraycopy(psc_t.b, psc_t.c, array4, 0, psc_t.d);
        }
        return array4;
    }
    
    static byte[] a(final String s, final byte[] array) throws psc_bf {
        try {
            final char[] charArray = s.toCharArray();
            final byte[] array2 = new byte[charArray.length * 2];
            for (int i = 0, n = 0; i < charArray.length; ++i, n += 2) {
                array2[n] = (byte)(charArray[i] >>> 8 & 0xFF);
                array2[n + 1] = (byte)(charArray[i] & '\u00ff');
            }
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_h psc_h2 = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            return psc_l.a(new psc_i[] { psc_h, psc_h2, new psc_r(16777216, true, 0, array2, 0, array2.length), new psc_k(77824, false, 5, null, 0, 0), psc_j, new psc_t(0, true, 0, array, 0, array.length), psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_bf("Could not BER encode the Secret key.(" + psc_m.getMessage() + ").");
        }
    }
}
