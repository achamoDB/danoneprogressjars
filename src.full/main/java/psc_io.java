// 
// Decompiled by Procyon v0.5.36
// 

class psc_io extends psc_an
{
    String a;
    private static final String[] b;
    
    void a(final byte[] array, final int n) throws psc_ao {
        final psc_k psc_k = new psc_k(130816);
        try {
            this.a = psc_q.b(array, n, 1, psc_k);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data. (" + psc_m.getMessage() + ")");
        }
        if (this.a == null) {
            return;
        }
        int n2;
        for (n2 = 0; n2 < psc_io.b.length && this.a.compareTo(psc_io.b[n2]) != 0; ++n2) {}
        if (n2 >= psc_io.b.length) {
            return;
        }
        this.a(n2, psc_k.b, psc_k.c);
    }
    
    private void a(final int n, final byte[] array, final int n2) throws psc_ao {
        try {
            final psc_i[] b = b(null, null, null, 0);
            psc_l.a(array, n2, b);
            if (b[1].a) {
                this.a = ((psc_r)b[2]).a.concat("/" + this.a);
            }
            else {
                this.a = "SHA1/".concat(this.a);
            }
            if (b[9].a) {
                if (((psc_p)b[6]).e() == 1) {
                    this.a = this.a.concat("-1");
                }
                else {
                    this.a = this.a.concat("-2");
                }
            }
            else {
                this.a = this.a.concat("-1");
            }
            if (b[5].a) {
                this.a = this.a.concat("/" + ((psc_r)b[6]).a);
                this.a = this.a.concat("/" + ((psc_r)b[8]).a);
            }
            else {
                this.a = this.a.concat("/MGF1/SHA1");
            }
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid RSA PSS parameter BER encoding. (" + psc_m.getMessage() + ")");
        }
    }
    
    static byte[] a(final String s, final String s2, final String s3, final int n) throws psc_ao {
        try {
            return psc_l.a(b(s, s2, s3, n));
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not DER encode the PSS AlgID parameters.(" + psc_m.getMessage() + ")");
        }
    }
    
    private static psc_i[] b(final String s, final String s2, final String s3, final int n) throws psc_m {
        final psc_j psc_j = new psc_j();
        final psc_h psc_h = new psc_h(0, true, 0);
        boolean b = false;
        if (s != null && s.compareTo("SHA1") != 0) {
            b = true;
        }
        final psc_h psc_h2 = new psc_h(10616832, b, 0);
        final psc_r psc_r = new psc_r(0, true, 0, s, 10);
        final psc_k psc_k = new psc_k(130816, true, 5, null, 0, 0);
        boolean b2 = false;
        if (s2 != null && s2.compareTo("MGF1") != 0 && s3 != null && s3.compareTo("SHA1") != 0) {
            b2 = true;
        }
        final psc_h psc_h3 = new psc_h(10616833, b2, 0);
        final psc_r psc_r2 = new psc_r(0, true, 0, s2, 12);
        final psc_h psc_h4 = new psc_h(0, true, 0);
        final psc_r psc_r3 = new psc_r(0, true, 0, s3, 11);
        final psc_k psc_k2 = new psc_k(130816, true, 5, null, 0, 0);
        boolean b3 = false;
        if (n != 1) {
            b3 = true;
        }
        return new psc_i[] { psc_h, psc_h2, psc_r, psc_k, psc_j, psc_h3, psc_r2, psc_h4, psc_r3, psc_k2, psc_j, psc_j, new psc_p(10616834, b3, 5, n), psc_j };
    }
    
    static {
        b = new String[] { "RSA/PKCS1V2PSS" };
    }
}
