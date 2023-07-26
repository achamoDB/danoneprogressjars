import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_m1 implements Cloneable, Serializable
{
    private int a;
    private psc_m6 b;
    private static final int c = 8;
    
    psc_m1() {
        this.a = 3;
        this.b = null;
    }
    
    psc_m1(final psc_ah psc_ah, final psc_ed psc_ed, final char[] array, char[] array2, final byte[] array3, final int n, final int n2) throws psc_m0 {
        this.a = 3;
        this.b = null;
        try {
            final psc_h psc_h = new psc_h(n2);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(12288);
            final psc_k psc_k2 = new psc_k(77824);
            psc_l.a(array3, n, new psc_i[] { psc_h, psc_p, psc_k, psc_k2, psc_j });
            this.a = psc_p.e();
            if (array2 == null) {
                array2 = new char[array.length];
                System.arraycopy(array, 0, array2, 0, array.length);
            }
            final byte[] a = this.a(array2, psc_ed, psc_ah, psc_k.b, psc_k.c, psc_k.d);
            if (psc_k2.a) {
                final psc_m5 psc_m5 = new psc_m5(psc_ah, array, a, psc_k2.b, psc_k2.c, 0);
            }
            final psc_w psc_w = new psc_w(0, 12288, new psc_k(65280));
            psc_l.a(a, 0, new psc_i[] { psc_w });
            this.b = new psc_m6();
            int i = 0;
            while (i < psc_w.j()) {
                final psc_i a2 = psc_w.a(i);
                final byte[] a3 = this.a(array2, psc_ed, psc_ah, a2.b, a2.c, a2.d);
                switch (psc_m2.h(a2.b, a2.c, a2.d)) {
                    case 1:
                    case 6: {
                        final psc_m7 psc_m6 = new psc_m7(psc_ah, psc_ed, this.b, array2, a3, 0, 0);
                        ++i;
                        continue;
                    }
                    case 3: {
                        throw new psc_m0("enveloped data is not implemented yet");
                    }
                    default: {
                        throw new psc_m0("Illegal contentType found");
                    }
                }
            }
            if (psc_ed != null) {
                final Vector c = this.b.c();
                final Vector b = this.b.b;
                for (int j = 0; j < c.size(); ++j) {
                    final psc_dt psc_dt = c.elementAt(j);
                    final psc_cr psc_cr = b.elementAt(j);
                    if (psc_cr != null) {
                        final psc_e a4 = this.b.a(psc_cr);
                        if (a4 != null) {
                            psc_ed.a(a4, psc_dt);
                        }
                    }
                }
            }
        }
        catch (psc_mv psc_mv) {
            throw new psc_m0(psc_mv.getMessage());
        }
        catch (psc_nk psc_nk) {
            throw new psc_m0(psc_nk.getMessage());
        }
        catch (psc_m psc_m8) {
            throw new psc_m0("Cannot decode the BER of the PFX.");
        }
        catch (psc_m3 psc_m7) {
            throw new psc_m0("PFX.PFX: Illegal contentType found(" + psc_m7.getMessage() + ").");
        }
    }
    
    psc_m1(final psc_f[] array, final psc_na[] array2, final psc_dt[] array3, final psc_cr[] array4, final psc_cr[] array5, final psc_cr[] array6, final String[] array7) throws psc_d7 {
        this.a = 3;
        this.b = null;
        this.b = new psc_m6(array, array2, array3, array4, array5, array6, array7);
    }
    
    private byte[] a(final char[] array, final psc_ed psc_ed, final psc_ah psc_ah, final byte[] array2, final int n, final int n2) throws psc_m0 {
        try {
            switch (psc_m2.h(array2, n, n2)) {
                case 1: {
                    final psc_m4 psc_m4 = new psc_m4();
                    psc_m4.j(array2, n, n2);
                    return psc_m4.b();
                }
                case 3: {
                    throw new psc_m0("Enveloped Data is not implemented yet");
                }
                case 6: {
                    return this.a(psc_ah, array, array2, n, n2);
                }
                default: {
                    throw new psc_m0("Illegal contentType found");
                }
            }
        }
        catch (psc_m3 psc_m5) {
            throw new psc_m0(psc_m5.getMessage());
        }
    }
    
    private byte[] a(final psc_ah psc_ah, final char[] array, final byte[] array2, final int n, final int n2) throws psc_m0 {
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(10616576);
            psc_l.a(array2, n, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
            final psc_h psc_h2 = new psc_h(10551296);
            final psc_j psc_j2 = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k2 = new psc_k(12288);
            psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_h2, psc_p, psc_k2, psc_j2 });
            final psc_h psc_h3 = new psc_h(0);
            final psc_j psc_j3 = new psc_j();
            final psc_r psc_r2 = new psc_r(16777216);
            final psc_k psc_k3 = new psc_k(12288);
            final psc_t psc_t = new psc_t(8454144);
            psc_l.a(psc_k2.b, psc_k2.c, new psc_i[] { psc_h3, psc_r2, psc_k3, psc_t, psc_j3 });
            final psc_h psc_h4 = new psc_h(0);
            final psc_j psc_j4 = new psc_j();
            final psc_r psc_r3 = new psc_r(0);
            final psc_k psc_k4 = new psc_k(130816);
            psc_l.a(psc_k3.b, psc_k3.c, new psc_i[] { psc_h4, psc_r3, psc_k4, psc_j4 });
            final psc_h psc_h5 = new psc_h(0);
            final psc_j psc_j5 = new psc_j();
            final psc_t psc_t2 = new psc_t(0);
            psc_l.a(psc_k4.b, psc_k4.c, new psc_i[] { psc_h5, psc_t2, new psc_p(0), psc_j5 });
            final psc_df a = psc_df.a(psc_k3.b, psc_k3.c, psc_ah.f());
            a.c(psc_t2.b, psc_t2.c, psc_t2.d);
            final psc_dc o = a.o();
            o.a(array, 0, array.length);
            a.b(o);
            final byte[] array3 = new byte[psc_t.d];
            a.c(array3, a.b(psc_t.b, psc_t.c, psc_t.d, array3, 0));
            return array3;
        }
        catch (psc_e1 psc_e1) {
            throw new psc_m0(psc_e1.getMessage());
        }
        catch (psc_gx psc_gx) {
            throw new psc_m0(psc_gx.getMessage());
        }
        catch (psc_be psc_be) {
            throw new psc_m0(psc_be.getMessage());
        }
        catch (psc_ao psc_ao) {
            throw new psc_m0(psc_ao.getMessage());
        }
        catch (psc_en psc_en) {
            throw new psc_m0(psc_en.getMessage());
        }
        catch (psc_bf psc_bf) {
            throw new psc_m0(psc_bf.getMessage());
        }
        catch (psc_gw psc_gw) {
            throw new psc_m0(psc_gw.getMessage());
        }
        catch (psc_m psc_m) {
            throw new psc_m0(psc_m.getMessage());
        }
    }
    
    void a(final int a) {
        this.a = a;
    }
    
    int a() {
        return this.a;
    }
    
    psc_m6 b() {
        return this.b;
    }
    
    byte[] a(final psc_ah psc_ah, final char[] array, char[] array2, final String s, final String s2, final int n, final int n2) throws psc_m0 {
        if (array2 == null) {
            array2 = new char[array.length];
            System.arraycopy(array, 0, array2, 0, array.length);
        }
        final byte[] a = new psc_m7(this.b).a(psc_ah, s, array2, n2);
        final psc_m4 psc_m4 = new psc_m4();
        byte[] array4;
        byte[] array5;
        try {
            psc_m4.a(a, 0, a.length);
            Cloneable cloneable = psc_m4;
            if (n2 == 1) {
                try {
                    final psc_av e = psc_ah.e();
                    final byte[] array3 = new byte[8];
                    e.b(array3, 0, 8);
                    final psc_nr psc_nr = new psc_nr(psc_ah, null);
                    psc_nr.a(0);
                    psc_nr.c(array3, 0, array3.length);
                    psc_nr.a(s);
                    psc_nr.a(array2, 0, array2.length);
                    psc_nr.a(psc_m4);
                    cloneable = psc_nr;
                    psc_df.a(s, psc_ah.f());
                }
                catch (psc_ap psc_ap) {
                    throw new psc_m0("PFX.derEncode: " + psc_ap.getMessage());
                }
                catch (psc_d6 psc_d6) {
                    throw new psc_m0("PFX.derEncode: No random provider error(" + psc_d6.getMessage() + ").");
                }
            }
            final int l = ((psc_m2)cloneable).l();
            try {
                final psc_n psc_n = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_k(12288, true, 0, null, 0, l), new psc_j() });
                array4 = new byte[psc_n.a()];
                ((psc_m2)cloneable).a(array4, psc_n.a(array4, 0));
            }
            catch (psc_m psc_m5) {
                throw new psc_m0("PFX.derEncode: Encoding of authenticated failed(" + psc_m5.getMessage() + ").");
            }
            final psc_m4 psc_m6 = new psc_m4();
            psc_m6.a(array4, 0, array4.length);
            array5 = new byte[psc_m6.l()];
            psc_m6.a(array5, 0);
        }
        catch (psc_m3 psc_m7) {
            throw new psc_m0("PFX.derEncode: ContentInfo encoding failed(" + psc_m7.getMessage() + ").");
        }
        final byte[] a2 = psc_m5.a(psc_ah, array4, 0, array4.length, s2, n, array);
        try {
            final psc_n psc_n2 = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_p(0, true, 0, this.a), new psc_k(12288, true, 0, array5, 0, array5.length), new psc_k(77824, true, 0, a2, 0, a2.length), new psc_j() });
            final byte[] array6 = new byte[psc_n2.a()];
            psc_n2.a(array6, 0);
            return array6;
        }
        catch (psc_m psc_m8) {
            throw new psc_m0("PFX.derEncode: Encoding PFX failed(" + psc_m8.getMessage() + ").");
        }
    }
}
