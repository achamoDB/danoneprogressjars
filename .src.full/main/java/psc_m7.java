import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

final class psc_m7 implements Cloneable, Serializable
{
    private psc_m6 a;
    private psc_ed b;
    private static final int c = 1;
    private static final int d = 2;
    private static final int e = 3;
    private static final int f = 4;
    private static final int g = 5;
    private static final int h = 6;
    private static final byte[] i;
    private static final byte[] j;
    private static final byte[] k;
    private static final byte[] l;
    private static final byte[] m;
    private static final byte[] n;
    private static final byte[] o;
    private static final byte[] p;
    private static final byte[] q;
    private static final int r = 8;
    private static int s;
    
    psc_m7() {
        this.a = null;
        this.b = null;
    }
    
    psc_m7(final psc_ah psc_ah, final psc_ed b, final psc_m6 a, final char[] array, final byte[] array2, final int n, final int n2) throws psc_m0 {
        this.a = null;
        this.b = null;
        this.a = a;
        this.b = b;
        this.a(psc_ah, array, array2, n, n2);
    }
    
    psc_m7(final psc_m6 a) throws psc_m0 {
        this.a = null;
        this.b = null;
        this.a = a;
    }
    
    byte[] a(final psc_ah psc_ah, final String s, final char[] array, final int n) throws psc_m0 {
        final psc_n[] b = this.b(psc_ah, s, array, n);
        if (b == null || b.length == 0) {
            throw new psc_m0("SafeContents.derEncode: No bag to be exported.");
        }
        int n2 = 0;
        try {
            for (int i = 0; i < b.length; ++i) {
                n2 += b[i].a();
            }
            final int n3 = 48;
            final int a = psc_o.a(n3);
            final int n4 = psc_o.b(n2) + a;
            final byte[] array2 = new byte[n4 + n2];
            psc_o.a(array2, 0, n3);
            psc_o.b(array2, a, n2);
            int n5 = n4;
            for (int j = 0; j < b.length; ++j) {
                n5 += b[j].a(array2, n5);
            }
            return array2;
        }
        catch (psc_m psc_m) {
            throw new psc_m0("SafeContents.derEncode: Encoding a bag failed(" + psc_m.getMessage() + ").");
        }
    }
    
    private void a(final psc_ah psc_ah, final char[] array, final byte[] array2, final int n, final int n2) throws psc_m0 {
        try {
            final psc_w psc_w = new psc_w(n2, 12288, new psc_k(12288));
            psc_l.a(array2, 0, new psc_i[] { psc_w });
            for (int i = 0; i < psc_w.j(); ++i) {
                this.a(psc_ah, array, psc_w.a(i).b, psc_w.a(i).c);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m0("Cannot decode the BER of the SafeContents.");
        }
    }
    
    private void a(final psc_ah psc_ah, final char[] array, final byte[] array2, final int n) throws psc_m0 {
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(10616576);
            final psc_k psc_k2 = new psc_k(78080, true, 0, null, 0, 0);
            psc_l.a(array2, n, new psc_i[] { psc_h, psc_r, psc_k, psc_k2, psc_j });
            psc_cr psc_cr = null;
            if (psc_k2.a && psc_k2.d > 2) {
                psc_cr = new psc_cr(psc_k2.b, psc_k2.c, 0);
                for (int i = 0; i < psc_cr.a(); ++i) {
                    psc_cr.c(i);
                }
            }
            final int n2 = psc_r.b[psc_r.c + psc_r.d - 1] & -1;
            final int n3 = psc_k.c + (1 + psc_o.a(psc_k.b, psc_k.c + 1));
            switch (n2) {
                case 1: {
                    this.a.b.addElement(psc_cr);
                    this.c(psc_ah, psc_k.b, n3);
                    break;
                }
                case 2: {
                    this.a.b.addElement(psc_cr);
                    this.a(array, psc_ah, psc_k.b, n3);
                    break;
                }
                case 3: {
                    this.a.a.addElement(psc_cr);
                    this.a(psc_ah, psc_k.b, n3);
                    break;
                }
                case 4: {
                    this.a.c.addElement(psc_cr);
                    this.b(psc_ah, psc_k.b, n3);
                    break;
                }
                case 5: {
                    throw new psc_m0("Secret Bag is not implemented yet");
                }
                case 6: {
                    this.a(psc_ah, array, psc_k.b, 0, 10551040);
                    break;
                }
                default: {
                    throw new psc_m0("Illegal BagType found");
                }
            }
        }
        catch (psc_f0 psc_f0) {
            throw new psc_m0(psc_f0.getMessage());
        }
        catch (psc_m psc_m) {
            throw new psc_m0("Cannot decode the BER of the SafeContents.");
        }
    }
    
    private void a(final psc_ah psc_ah, final byte[] array, final int n) throws psc_m0 {
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(10616576);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
            final psc_t psc_t = new psc_t(0);
            psc_l.a(psc_k.b, psc_k.c + (1 + psc_o.a(psc_k.b, psc_k.c + 1)), new psc_i[] { psc_t });
            final psc_e obj = new psc_e(psc_t.b, psc_t.c, 0);
            this.a.a().addElement(obj);
            if (this.b != null) {
                this.b.a(obj);
            }
        }
        catch (psc_mv psc_mv) {
            throw new psc_m0(psc_mv.getMessage());
        }
        catch (psc_nk psc_nk) {
            throw new psc_m0(psc_nk.getMessage());
        }
        catch (psc_g psc_g) {
            throw new psc_m0(psc_g.getMessage());
        }
        catch (psc_m psc_m) {
            throw new psc_m0("Cannot decode the BER of the CertBag.");
        }
    }
    
    private void b(final psc_ah psc_ah, final byte[] array, final int n) throws psc_m0 {
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(10616576);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
            final psc_t psc_t = new psc_t(0, true, 0, null, 0, 0);
            psc_l.a(psc_k.b, psc_k.c + (1 + psc_o.a(psc_k.b, psc_k.c + 1)), new psc_i[] { psc_t });
            final psc_m9 obj = new psc_m9(psc_t.b, psc_t.c, 0);
            this.a.b().addElement(obj);
            final psc_ed psc_ed = (psc_ed)psc_ah.a(1);
            if (psc_ed != null) {
                psc_ed.a(obj);
            }
        }
        catch (psc_d7 psc_d7) {
            throw new psc_m0(psc_d7.getMessage());
        }
        catch (psc_d9 psc_d8) {
            throw new psc_m0(psc_d8.getMessage());
        }
        catch (psc_mv psc_mv) {
            throw new psc_m0(psc_mv.getMessage());
        }
        catch (psc_nk psc_nk) {
            throw new psc_m0(psc_nk.getMessage());
        }
        catch (psc_g psc_g) {
            throw new psc_m0(psc_g.getMessage());
        }
        catch (psc_m psc_m) {
            throw new psc_m0("Cannot decode the BER of the CertBag.");
        }
    }
    
    private void a(final char[] array, final psc_ah psc_ah, final byte[] array2, final int n) throws psc_m0 {
        try {
            final psc_df a = psc_df.a(array2, n, psc_ah.f());
            final psc_dc o = a.o();
            o.a(array, 0, array.length);
            a.b(o);
            this.a.c().addElement(a.a(array2, n, array2.length, true));
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
    }
    
    private void c(final psc_ah psc_ah, final byte[] array, final int n) throws psc_m0 {
        try {
            this.a.c().addElement(psc_dt.a(array, n, psc_ah.f()));
        }
        catch (psc_ao psc_ao) {
            throw new psc_m0(psc_ao.getMessage());
        }
    }
    
    private psc_n[] b(final psc_ah psc_ah, final String s, final char[] array, final int n) throws psc_m0 {
        final Vector a = this.a.a();
        final Vector b = this.a.b();
        final Vector c = this.a.c();
        final Vector g = this.a.g();
        final Vector a2 = this.a.a;
        final Vector c2 = this.a.c;
        final Vector b2 = this.a.b;
        final int size = a.size();
        final int size2 = b.size();
        final int size3 = c.size();
        final int size4 = a2.size();
        final int size5 = c2.size();
        final int size6 = b2.size();
        final int size7 = g.size();
        final psc_n[] array2 = new psc_n[size + size2 + size3];
        for (int i = 0; i < size; ++i) {
            psc_cr psc_cr = null;
            if (size4 > i) {
                psc_cr = a2.elementAt(i);
            }
            array2[i] = this.a(psc_m7.k, this.a(a.elementAt(i)), psc_cr);
        }
        for (int j = 0; j < size2; ++j) {
            psc_cr psc_cr2 = null;
            if (size5 > j) {
                psc_cr2 = c2.elementAt(j);
            }
            array2[size + j] = this.a(psc_m7.l, this.a(b.elementAt(j)), psc_cr2);
        }
        final byte[] array3 = (n == 2) ? psc_m7.j : psc_m7.i;
        for (int k = 0; k < size3; ++k) {
            psc_cr psc_cr3 = null;
            if (size6 > k) {
                psc_cr3 = b2.elementAt(k);
            }
            final psc_dt psc_dt = c.elementAt(k);
            String s2 = null;
            if (size7 > k) {
                s2 = g.elementAt(k);
            }
            array2[size + size2 + k] = this.a(array3, (n == 2) ? this.a(psc_ah, psc_dt, s, array, s2) : this.a(psc_dt, s2), psc_cr3);
        }
        return array2;
    }
    
    private psc_n a(final byte[] array, final byte[] array2, final psc_cr psc_cr) throws psc_m0 {
        final byte[] a = this.a(psc_cr);
        try {
            return new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_r(16777216, true, 0, array, 0, array.length), new psc_k(0, true, 0, array2, 0, array2.length), new psc_k(0, true, 0, a, 0, a.length), new psc_j() });
        }
        catch (psc_m psc_m) {
            throw new psc_m0("SafeContents.createSafeBagTemplate: " + psc_m.getMessage());
        }
    }
    
    private byte[] a(final psc_f psc_f) throws psc_m0 {
        if (!(psc_f instanceof psc_e)) {
            throw new psc_m0("SafeContents.encodeCertBag: Unknown certificate type.");
        }
        final psc_e psc_e = (psc_e)psc_f;
        final int c = psc_e.c(0);
        try {
            final psc_n psc_n = new psc_n(new psc_i[] { new psc_h(10485760, true, 0), new psc_r(16777216, true, 0, psc_m7.o, 0, psc_m7.o.length), new psc_t(10551296, true, 0, null, 0, c), new psc_j() });
            final byte[] array = new byte[psc_n.a()];
            psc_e.d(array, psc_n.a(array, 0), 0);
            return array;
        }
        catch (psc_m psc_m) {
            throw new psc_m0("SafeContents.encodeCertBag: DER encoding of CertBag failed(" + psc_m.getMessage() + ").");
        }
        catch (psc_g psc_g) {
            throw new psc_m0("SafeContents.encodeCertBag: DER encoding of X509Certificate failed(" + psc_g.getMessage() + ").");
        }
    }
    
    private byte[] a(final psc_na psc_na) throws psc_m0 {
        if (!(psc_na instanceof psc_m9)) {
            throw new psc_m0("SafeContents.encodeCRLBag: Unknown CRL type.");
        }
        final psc_m9 psc_m9 = (psc_m9)psc_na;
        final int c = psc_m9.c(0);
        try {
            final psc_n psc_n = new psc_n(new psc_i[] { new psc_h(10485760, true, 0), new psc_r(16777216, true, 0, psc_m7.p, 0, psc_m7.p.length), new psc_t(10551296, true, 0, null, 0, c), new psc_j() });
            final byte[] array = new byte[psc_n.a()];
            psc_m9.d(array, psc_n.a(array, 0), 0);
            return array;
        }
        catch (psc_m psc_m10) {
            throw new psc_m0("SafeContents.encodeCRLBag: DER encoding of CRLBag failed(" + psc_m10.getMessage() + ").");
        }
        catch (psc_g psc_g) {
            throw new psc_m0("SafeContents.encodeCRLBag: DER encoding of X509CRL failed(" + psc_g.getMessage() + ").");
        }
    }
    
    private byte[] a(final psc_dt psc_dt, final String s) throws psc_m0 {
        byte[] array = null;
        Label_0177: {
            if (s != null) {
                if (s.endsWith("BER")) {
                    try {
                        array = psc_dt.b(s)[0];
                        break Label_0177;
                    }
                    catch (psc_ap psc_ap) {
                        throw new psc_m0("SafeContents.encodeKeyBag: getKeyData failed(" + psc_ap.getMessage() + ").");
                    }
                }
                throw new psc_m0("SafeContents.encodeKeyBag: getKeyData failed(Wrong key format. )");
            }
            final String[] o = psc_dt.o();
            for (int i = 0; i < o.length; ++i) {
                try {
                    if (o[i].endsWith("BER")) {
                        array = psc_dt.b(o[i])[0];
                        break;
                    }
                }
                catch (psc_ap psc_ap2) {
                    throw new psc_m0("SafeContents.encodeKeyBag: getKeyData failed(" + psc_ap2.getMessage() + ").");
                }
            }
        }
        if (array == null) {
            throw new psc_m0("SafeContents.encodeKeyBag: No BER format found for private key.");
        }
        return this.a(array);
    }
    
    private byte[] a(final psc_ah psc_ah, final psc_dt psc_dt, final String s, final char[] array, final String s2) throws psc_m0 {
        psc_df a = null;
        try {
            a = psc_df.a(s, psc_ah.f());
            final psc_av e = psc_ah.e();
            final byte[] array2 = new byte[8];
            e.b(array2, 0, 8);
            a.c(array2, 0, 8);
            final psc_dc o = a.o();
            o.a(array, 0, array.length);
            a.a(o);
            return this.a(a.a(psc_dt, true, s2));
        }
        catch (psc_ap psc_ap) {
            throw new psc_m0("SafeContents.encodeShroudedKeyBag: Key wrapping failed(" + psc_ap.getMessage() + ").");
        }
        catch (psc_mv psc_mv) {
            throw new psc_m0("SafeContents.encodeShroudedKeyBag: Random provider not found(" + psc_mv.getMessage() + ").");
        }
        catch (psc_n5 psc_n5) {
            throw new psc_m0("SafeContents.encodeShroudedKeyBag: Random provider failed(" + psc_n5.getMessage() + ").");
        }
        finally {
            if (a != null) {
                a.y();
            }
        }
    }
    
    private byte[] a(final byte[] array) throws psc_m0 {
        final int n = 10485760;
        final int n2 = 1;
        final int length = array.length;
        try {
            final int n3 = psc_o.b(length) + n2;
            final byte[] array2 = new byte[n3 + length];
            psc_o.a(array2, 0, n);
            psc_o.b(array2, n2, length);
            System.arraycopy(array, 0, array2, n3, length);
            return array2;
        }
        catch (psc_m psc_m) {
            throw new psc_m0("SafeContents.addContextExplicitHeader: " + psc_m.getMessage());
        }
    }
    
    private byte[] a(final psc_cr psc_cr) throws psc_m0 {
        if (psc_cr == null || psc_cr.a() == 0) {
            return psc_m7.q;
        }
        try {
            final byte[] array = new byte[psc_cr.b(0)];
            psc_cr.b(array, 0, 0);
            return array;
        }
        catch (psc_f0 psc_f0) {
            throw new psc_m0("SafeContents.encodeAttributes: DER encoding of X509Attributes failed(" + psc_f0.getMessage() + ").");
        }
    }
    
    static {
        i = new byte[] { 42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 1 };
        j = new byte[] { 42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 2 };
        k = new byte[] { 42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 3 };
        l = new byte[] { 42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 4 };
        m = new byte[] { 42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 5 };
        n = new byte[] { 42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 6 };
        o = new byte[] { 42, -122, 72, -122, -9, 13, 1, 9, 22, 1 };
        p = new byte[] { 42, -122, 72, -122, -9, 13, 1, 9, 23, 1 };
        q = new byte[] { 49, 0 };
    }
}
