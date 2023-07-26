// 
// Decompiled by Procyon v0.5.36
// 

class psc_nl extends psc_an
{
    String a;
    byte[] b;
    int c;
    int d;
    private static final String[] e;
    
    void a(final byte[] array, final int n) throws psc_ao {
        this.b = null;
        final psc_k psc_k = new psc_k(130816);
        try {
            this.a = psc_q.b(array, n, 2, psc_k);
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not read BER data. (" + psc_m.getMessage() + ")");
        }
        if (this.a == null) {
            return;
        }
        int n2;
        for (n2 = 0; n2 < psc_nl.e.length && this.a.compareTo(psc_nl.e[n2]) != 0; ++n2) {}
        if (n2 >= psc_nl.e.length) {
            return;
        }
        this.a(n2, psc_k.b, psc_k.c);
    }
    
    private void a(final int n, final byte[] array, final int n2) throws psc_ao {
        try {
            final psc_i[] b = b(null, null, null, null, null);
            psc_l.a(array, n2, b);
            if (b[1].a) {
                this.a = this.a.concat("/" + ((psc_r)b[2]).a);
            }
            else {
                this.a = this.a.concat("/SHA1");
            }
            if (b[5].a) {
                this.a = this.a.concat("/" + ((psc_r)b[6]).a);
                this.a = this.a.concat("/" + ((psc_r)b[8]).a);
            }
            else {
                this.a = this.a.concat("/MGF1/SHA1");
            }
            if (b[12].a) {
                this.a = this.a.concat("/" + ((psc_r)b[13]).a);
                this.b = b[14].b;
                this.c = b[14].c;
                this.d = b[14].d;
            }
            else {
                this.a = this.a.concat("/SpecifiedParams");
            }
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Invalid RSA OAEP parameter BER encoding. (" + psc_m.getMessage() + ")");
        }
    }
    
    static byte[] a(final String s, final String s2, final String s3, final String s4, final byte[] array) throws psc_ao {
        try {
            return psc_l.a(b(s, s2, s3, s4, array));
        }
        catch (psc_m psc_m) {
            throw new psc_ao("Could not DER encode the OAEP AlgID parameters. " + psc_m.getMessage());
        }
    }
    
    private static psc_i[] b(final String s, final String s2, final String s3, final String s4, final byte[] array) throws psc_m {
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
        if (s4 != null && s4.compareTo("SpecifiedParams") != 0) {
            b3 = true;
        }
        int length = 0;
        if (array != null && array.length != 0) {
            b3 = true;
            length = array.length;
        }
        return new psc_i[] { psc_h, psc_h2, psc_r, psc_k, psc_j, psc_h3, psc_r2, psc_h4, psc_r3, psc_k2, psc_j, psc_j, new psc_h(10616834, b3, 0), new psc_r(0, true, 0, s4, 13), new psc_t(0, true, 0, array, 0, length), psc_j, psc_j };
    }
    
    static {
        e = new String[] { "RSA/PKCS1V2OAEPPad" };
    }
}
