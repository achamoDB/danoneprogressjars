import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nt extends psc_m2 implements Cloneable, Serializable
{
    protected int a;
    protected Vector b;
    private Vector c;
    private Vector d;
    protected Vector e;
    protected Vector f;
    protected Vector g;
    private boolean h;
    private boolean i;
    private Vector j;
    private Vector k;
    private Vector l;
    private final int m = 20;
    
    public psc_nt(final psc_ah o, final psc_nq n) {
        this.a = -1;
        this.b = new Vector();
        this.c = new Vector();
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.g = new Vector();
        this.h = false;
        this.i = false;
        this.j = new Vector();
        this.k = new Vector();
        this.l = new Vector();
        super.k = 2;
        super.o = o;
        super.n = n;
    }
    
    public void a(final psc_m2 psc_m2) throws psc_m3 {
        if (psc_m2 == null) {
            throw new psc_m3("Unable to set: content is null.");
        }
        try {
            super.m = (psc_m2)psc_m2.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Unable to clone content " + ex.getMessage());
        }
    }
    
    public void c(final int a) {
        this.a = a;
    }
    
    public void a(final byte[] array, final String s) throws psc_m3 {
        final int index = this.d.indexOf(s);
        if (index == -1) {
            this.b.addElement(psc_nv.a(s));
            this.d.addElement(s);
            this.c.addElement(array);
        }
        else {
            this.c.setElementAt(array, index);
        }
        this.i = true;
    }
    
    public int t() {
        return this.a;
    }
    
    public void a(final psc_e obj) throws psc_m3 {
        if (obj == null) {
            throw new psc_m3("Certificate is null.");
        }
        this.e.addElement(obj);
    }
    
    public Vector u() throws psc_m3 {
        return (Vector)this.e.clone();
    }
    
    public Vector v() throws psc_m3 {
        return (Vector)this.f.clone();
    }
    
    public void a(final psc_m9 obj) throws psc_m3 {
        if (obj == null) {
            throw new psc_m3("CRL is null.");
        }
        this.f.addElement(obj);
    }
    
    public void a(final psc_nw psc_nw) throws psc_m3 {
        if (psc_nw == null) {
            throw new psc_m3("Cannot add this Signer Information: it is null.");
        }
        try {
            this.g.addElement(psc_nw.clone());
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Cannot add this Signer Information: " + ex.getMessage());
        }
    }
    
    public void w() {
        this.h = true;
    }
    
    public Vector x() {
        return (Vector)this.g.clone();
    }
    
    protected int m() throws psc_m3 {
        return this.a();
    }
    
    protected int b(final byte[] array, final int n) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Specified array is null.");
        }
        try {
            if (super.q == null) {
                this.m();
            }
            final int a = super.q.a(array, n);
            super.q = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.q = null;
            throw new psc_m3("Unable to DER encode SignedData message: " + psc_m.getMessage());
        }
    }
    
    private int a() throws psc_m3 {
        try {
            if (super.af == 0) {
                this.b();
            }
            psc_k psc_k;
            if (this.b.size() > 0) {
                final psc_w obj = new psc_w(0, true, 0, 12544, new psc_k(12288, true, 0, null, 0, 0));
                final Vector vector = new Vector<psc_w>();
                vector.addElement(obj);
                for (int i = 0; i < this.b.size(); ++i) {
                    try {
                        final byte[] array = this.b.elementAt(i);
                        obj.b(new psc_k(12288, true, 0, array, 0, array.length));
                    }
                    catch (psc_m psc_m) {
                        throw new psc_m3("Unable to encode digest Alg IDs: " + psc_m.getMessage());
                    }
                }
                final psc_i[] anArray = new psc_i[vector.size()];
                vector.copyInto(anArray);
                final psc_n psc_n = new psc_n(anArray);
                final byte[] array2 = new byte[psc_n.a()];
                psc_k = new psc_k(0, true, 0, array2, 0, psc_n.a(array2, 0));
            }
            else {
                psc_k = new psc_k(0, true, 0, new byte[] { 49, 0 }, 0, 2);
            }
            psc_k psc_k2;
            if (this.g.size() > 0) {
                final psc_w obj2 = new psc_w(0, true, 0, 12544, new psc_k(12288));
                final Vector vector2 = new Vector<psc_w>();
                vector2.addElement(obj2);
                for (int j = 0; j < this.g.size(); ++j) {
                    try {
                        final psc_nw psc_nw = this.g.elementAt(j);
                        final byte[] array3 = new byte[psc_nw.b(0)];
                        obj2.b(new psc_k(0, true, 0, array3, 0, psc_nw.d(array3, 0, 0)));
                    }
                    catch (psc_m psc_m2) {
                        throw new psc_m3("Unable to encode SignerInfos: " + psc_m2.getMessage());
                    }
                }
                final psc_i[] anArray2 = new psc_i[vector2.size()];
                vector2.copyInto(anArray2);
                final psc_n psc_n2 = new psc_n(anArray2);
                final byte[] array4 = new byte[psc_n2.a()];
                psc_k2 = new psc_k(0, true, 0, array4, 0, psc_n2.a(array4, 0));
            }
            else {
                psc_k2 = new psc_k(0, true, 0, new byte[] { 49, 0 }, 0, 2);
            }
            psc_i psc_i = null;
            if (this.e.size() > 0) {
                final psc_w obj3 = new psc_w(8454144, true, 0, 12544, new psc_k(12288));
                final Vector vector3 = new Vector<psc_w>();
                vector3.addElement(obj3);
                for (int k = 0; k < this.e.size(); ++k) {
                    try {
                        final psc_e psc_e = this.e.elementAt(k);
                        final byte[] array5 = new byte[psc_e.c(0)];
                        obj3.b(new psc_k(0, true, 0, array5, 0, psc_e.d(array5, 0, 0)));
                    }
                    catch (psc_m psc_m3) {
                        throw new psc_m3("Unable to encode Certificates: " + psc_m3.getMessage());
                    }
                    catch (psc_g psc_g) {
                        throw new psc_m3("Unable to encode Certificates: " + psc_g.getMessage());
                    }
                }
                final psc_i[] anArray3 = new psc_i[vector3.size()];
                vector3.copyInto(anArray3);
                final psc_n psc_n3 = new psc_n(anArray3);
                final byte[] array6 = new byte[psc_n3.a()];
                psc_i = new psc_k(0, true, 0, array6, 0, psc_n3.a(array6, 0));
            }
            psc_i psc_i2 = null;
            if (this.f.size() > 0) {
                final psc_w obj4 = new psc_w(8454145, true, 0, 12544, new psc_k(12288));
                final Vector vector4 = new Vector<psc_w>();
                vector4.addElement(obj4);
                for (int l = 0; l < this.f.size(); ++l) {
                    try {
                        final psc_m9 psc_m4 = this.f.elementAt(l);
                        final byte[] array7 = new byte[psc_m4.c(0)];
                        obj4.b(new psc_k(0, true, 0, array7, 0, psc_m4.d(array7, 0, 0)));
                    }
                    catch (psc_m psc_m5) {
                        throw new psc_m3("Unable to encode CRLs: " + psc_m5.getMessage());
                    }
                    catch (psc_g psc_g2) {
                        throw new psc_m3("Unable to encode CRLs: " + psc_g2.getMessage());
                    }
                }
                final psc_i[] anArray4 = new psc_i[vector4.size()];
                vector4.copyInto(anArray4);
                final psc_n psc_n4 = new psc_n(anArray4);
                final byte[] array8 = new byte[psc_n4.a()];
                psc_i2 = new psc_k(0, true, 0, array8, 0, psc_n4.a(array8, 0));
            }
            if (super.m == null && !this.i) {
                throw new psc_m3("Content is NULL.");
            }
            if (this.h) {
                (super.ac = new byte[13])[0] = 48;
                super.ac[1] = 11;
                super.ac[2] = 6;
                super.ac[3] = 9;
                System.arraycopy(psc_m2.i, 0, super.ac, 4, 9);
                super.ac[12] = (byte)(this.i ? 1 : ((byte)super.m.j()));
            }
            else if (super.ac == null) {
                super.ac = new byte[super.m.l()];
                super.m.a(super.ac, 0);
            }
            final psc_k psc_k3 = new psc_k(0, true, 0, super.ac, 0, super.ac.length);
            if (this.a == -1) {
                this.a = 1;
            }
            final psc_h psc_h = new psc_h(10551296, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, this.a);
            if (this.f.size() > 0) {
                if (this.e.size() > 0) {
                    super.q = new psc_n(new psc_i[] { psc_h, psc_p, psc_k, psc_k3, psc_i, psc_i2, psc_k2, psc_j });
                }
                else {
                    super.q = new psc_n(new psc_i[] { psc_h, psc_p, psc_k, psc_k3, psc_i2, psc_k2, psc_j });
                }
            }
            else if (this.e.size() > 0) {
                super.q = new psc_n(new psc_i[] { psc_h, psc_p, psc_k, psc_k3, psc_i, psc_k2, psc_j });
            }
            else {
                super.q = new psc_n(new psc_i[] { psc_h, psc_p, psc_k, psc_k3, psc_k2, psc_j });
            }
            return super.q.a();
        }
        catch (psc_m psc_m6) {
            throw new psc_m3("Could not DER encode SignedData: " + psc_m6.getMessage());
        }
    }
    
    private void a(final int n) throws psc_m3 {
        byte[] array = null;
        try {
            if (!this.i) {
                if (super.ac == null) {
                    if (super.m == null) {
                        throw new psc_m3("There is no content to sign.");
                    }
                    super.ac = new byte[super.m.l()];
                    super.m.a(super.ac, 0);
                }
                final psc_h psc_h = new psc_h(0);
                final psc_j psc_j = new psc_j();
                final psc_r psc_r = new psc_r(16777216);
                final psc_k psc_k = new psc_k(10616576);
                psc_l.a(super.ac, 0, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
                if (psc_k.a) {
                    if (psc_k4.a(psc_r.b, psc_r.c, psc_r.d - 1, psc_m2.i, 0, psc_m2.i.length - 1)) {
                        switch (psc_r.b[psc_r.c + psc_r.d - 1]) {
                            case 1: {
                                final psc_t psc_t = new psc_t(10551296, true, 0, psc_k.b, psc_k.c, psc_k.d);
                                psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_t });
                                if (psc_t.b == null || psc_t.d == 0) {
                                    array = new byte[0];
                                    break;
                                }
                                array = new byte[psc_t.d];
                                System.arraycopy(psc_t.b, psc_t.c, array, 0, psc_t.d);
                                break;
                            }
                            default: {
                                int n2 = 1 + psc_o.a(psc_k.b, psc_k.c + 1);
                                final int n3 = ++n2 + psc_o.a(psc_k.b, psc_k.c + n2);
                                array = new byte[psc_k.d - n3];
                                System.arraycopy(psc_k.b, psc_k.c + n3, array, 0, psc_k.d - n3);
                                break;
                            }
                        }
                    }
                }
                else {
                    array = new byte[0];
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not DER encode ContentInfo: " + psc_m.getMessage());
        }
        try {
            if (n == 0) {
                final Enumeration<psc_nw> elements = (Enumeration<psc_nw>)this.g.elements();
                while (elements.hasMoreElements()) {
                    final psc_nw psc_nw = elements.nextElement();
                    final String g = psc_nw.g();
                    if (g == null) {
                        throw new psc_m3("Could not DER encode ContentInfo: Signer's digest algorithm is not set.");
                    }
                    if (this.d.contains(g)) {
                        continue;
                    }
                    this.d.addElement(g);
                    this.b.addElement(psc_nw.f());
                    this.c.addElement(null);
                }
            }
            if (array == null) {
                return;
            }
            if (!this.i) {
                for (int i = 0; i < this.b.size(); ++i) {
                    final psc_da a = psc_da.a(this.b.elementAt(i), 0, this.h());
                    if (this.c.elementAt(i) == null) {
                        final byte[] array2 = new byte[a.g()];
                        a.i();
                        a.a(array, 0, array.length);
                        final byte[] j = a.j();
                        a.y();
                        this.c.setElementAt(j, i);
                    }
                }
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not digest ContentInfo: " + psc_ap.getMessage());
        }
    }
    
    private void b() throws psc_m3 {
        this.a(0);
        if (this.g.size() == 0) {
            return;
        }
        if (super.o == null) {
            throw new psc_m3("CertJ object cannot be null.");
        }
        if (super.n == null) {
            throw new psc_m3("CertPathCtx object cannot be null.");
        }
        final psc_ed e = super.n.e();
        if (e == null) {
            throw new psc_m3("database field of theCertPathCtx object cannot be null.");
        }
        psc_ed a = null;
        psc_d8 a2 = null;
        String b = null;
        Label_0793: {
            try {
                if (this.e.size() > 0) {
                    a2 = this.a("Temp Memory DB", this.e, null);
                    b = a2.b();
                    a = this.a(e, b);
                }
                final psc_ed psc_ed = (a == null) ? e : a;
                for (int i = 0; i < this.g.size(); ++i) {
                    try {
                        final psc_nw psc_nw = this.g.elementAt(i);
                        final byte[] a3 = this.a(psc_nw, 0);
                        final byte[] f = psc_nw.f();
                        if (f == null) {
                            throw new psc_m3("Signer's digest algorithm is missing.");
                        }
                        byte[] a4 = this.a(f, a3);
                        final byte[] j = psc_nw.j();
                        if (j == null) {
                            throw new psc_m3("Signer's encryption algorithm is missing.");
                        }
                        String s;
                        try {
                            s = "NoDigest/" + psc_iq.a(j, 0, "Java").p() + "/PKCS1Block01Pad";
                        }
                        catch (psc_ap psc_ap2) {
                            s = "NoDigest/" + psc_eo.a(j, 0, "Java").m() + "/NoPad";
                            a4 = new byte[a3.length];
                            System.arraycopy(a3, 0, a4, 0, a3.length);
                        }
                        final Vector<psc_e> vector = new Vector<psc_e>();
                        final int a5 = psc_ed.a(psc_nw.c(), psc_nw.d(), vector);
                        if (a5 == 0) {
                            throw new psc_m3("Certificate is missing.");
                        }
                        psc_dt b2 = null;
                        for (int k = 0; k < a5; ++k) {
                            b2 = psc_ed.b(vector.elementAt(k));
                            if (b2 != null) {
                                break;
                            }
                        }
                        final psc_eo a6 = psc_eo.a(s, this.h());
                        a6.a(b2, null, super.o.e(), super.o.a());
                        final int p = a6.p();
                        if (a4.length > p) {
                            final StringBuffer sb = new StringBuffer();
                            sb.append("Signer key too small for digest info being signed. Digest info length: ");
                            sb.append(a4.length);
                            sb.append(". Max length given key size: ");
                            sb.append(p);
                            throw new psc_m3(sb.toString());
                        }
                        byte[] g = new byte[a6.q()];
                        a6.d(a4, 0, a4.length);
                        final int e2 = a6.e(g, 0);
                        if (a6.m().equals("DSA")) {
                            g = this.g(g, 0, e2);
                        }
                        psc_nw.c(g, 0, g.length);
                        vector.removeAllElements();
                        a6.y();
                        b2.y();
                    }
                    catch (psc_ap psc_ap) {
                        throw new psc_m3("Could not sign ContentInfo: " + psc_ap.getMessage());
                    }
                    catch (psc_n5 psc_n5) {
                        throw new psc_m3("Could not sign ContentInfo: " + psc_n5.getMessage());
                    }
                    catch (psc_mv psc_mv) {
                        throw new psc_m3("Could not sign ContentInfo: " + psc_mv.getMessage());
                    }
                    catch (psc_nk psc_nk) {
                        throw new psc_m3("Cannot get Cert from DB: " + psc_nk.getMessage());
                    }
                }
                break Label_0793;
            }
            finally {
                if (a != null) {
                    super.o.a(a);
                }
                Label_0862: {
                    if (a2 != null) {
                        try {
                            super.o.a(1, b);
                            break Label_0862;
                        }
                        catch (psc_d7 psc_d7) {}
                        break Label_0862;
                    }
                    break Label_0862;
                }
                while (true) {}
                Label_0826: {
                    return;
                }
                // iftrue(Label_0807:, a == null)
                while (true) {
                    Block_26: {
                        break Block_26;
                        try {
                            super.o.a(1, b);
                        }
                        catch (psc_d7 psc_d8) {}
                        return;
                    }
                    super.o.a(a);
                    Label_0807:
                    continue;
                }
            }
            // iftrue(Label_0826:, a2 == null)
        }
    }
    
    private byte[] a(final psc_nw psc_nw, final int n) throws psc_m3 {
        if (psc_nw == null) {
            throw new psc_m3("Cannot set SignerDigest: SignerInfo is null");
        }
        byte[] j;
        try {
            final String g = psc_nw.g();
            if (g == null) {
                throw new psc_m3("Signer's digest algorithm is not set.");
            }
            final int index = this.d.indexOf(g);
            j = this.c.elementAt(index);
            final psc_da a = psc_da.a(this.b.elementAt(index), 0, "Java");
            psc_cr i;
            if ((i = psc_nw.i()) != null || (super.m != null && super.m.j() != 1)) {
                if (i == null) {
                    i = new psc_cr();
                }
                psc_fz d;
                if ((d = i.d(14)) == null) {
                    if (n == 1) {
                        throw new psc_m3("While decoding, ContentType attribute is missing");
                    }
                    d = new psc_gg();
                }
                if (n == 0) {
                    int k;
                    if (this.i) {
                        k = 1;
                    }
                    else {
                        k = super.m.j();
                    }
                    final byte[] array = new byte[9];
                    System.arraycopy(psc_m2.i, 0, array, 0, 9);
                    array[8] = (byte)k;
                    ((psc_gg)d).f(array, 0, 9);
                    i.a(d);
                }
                psc_fz d2;
                if ((d2 = i.d(15)) == null) {
                    if (n == 1) {
                        throw new psc_m3("While decoding, MessageDigest attribute is missing");
                    }
                    d2 = new psc_gh();
                }
                if (n == 0) {
                    if (j != null) {
                        ((psc_gh)d2).f(j, 0, j.length);
                    }
                    i.a(d2);
                    psc_nw.a(i);
                }
                else if (!psc_k4.a(((psc_gh)d2).g(), j)) {
                    throw new psc_m3("Value in MessageDigest attribute does not contain the right digest.");
                }
                byte[] h = psc_nw.h();
                int n2;
                if (h == null) {
                    h = new byte[i.b(0)];
                    n2 = i.b(h, 0, 0);
                }
                else {
                    n2 = h.length;
                }
                final byte[] array2 = new byte[a.g()];
                a.i();
                a.a(h, 0, n2);
                j = a.j();
                a.y();
            }
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not digest ContentInfo: " + psc_ap.getMessage());
        }
        catch (psc_f0 psc_f0) {
            throw new psc_m3("Could not DER encode Attributes: " + psc_f0.getMessage());
        }
        return j;
    }
    
    private byte[] a(final byte[] array) throws psc_m3 {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        final psc_k psc_k = new psc_k(65536);
        final psc_i[] array2 = { psc_h, new psc_r(0), psc_k, psc_j };
        psc_s.b(array, 0, array.length, -1);
        final psc_n psc_n = new psc_n(array2);
        try {
            psc_l.a(array, 0, array2);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot decode algorithm OID: " + psc_m.getMessage());
        }
        if (psc_k.b == null) {
            final byte[] array3 = { 5, 0 };
            try {
                psc_k.a(array3, 0, array3.length, true, true);
                return psc_l.a(array2);
            }
            catch (psc_m psc_m2) {
                throw new psc_m3("Cannot encode algorithm OID: " + psc_m2.getMessage());
            }
        }
        return array;
    }
    
    private byte[] a(final byte[] array, final byte[] array2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("DigestAlgorithm OID is null.");
        }
        try {
            final byte[] a = this.a(array);
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_k psc_k = new psc_k(12288, true, 0, a, 0, a.length);
            int length = 0;
            if (array2 != null) {
                length = array2.length;
            }
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, psc_k, new psc_t(0, true, 0, array2, 0, length), psc_j });
            final byte[] array3 = new byte[psc_n.a()];
            psc_n.a(array3, 0);
            return array3;
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot encode digestInfo: " + psc_m.getMessage());
        }
    }
    
    protected boolean k(final byte[] array, final int n, final int n2) throws psc_m3 {
        try {
            final psc_h psc_h = new psc_h(10551296);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(12544);
            final psc_k psc_k2 = new psc_k(12288, true, 0, super.l, null, 0, 0);
            final psc_k psc_k3 = new psc_k(8466688);
            final psc_k psc_k4 = new psc_k(8466689);
            final psc_k psc_k5 = new psc_k(12544);
            (super.p = new psc_i[8])[0] = psc_h;
            super.p[1] = psc_p;
            super.p[2] = psc_k;
            super.p[3] = psc_k2;
            super.p[4] = psc_k3;
            super.p[5] = psc_k4;
            super.p[6] = psc_k5;
            super.p[7] = psc_j;
            (super.q = new psc_n(super.p)).c();
            super.q.a(array, n, n2);
            if (super.p[0].g()) {
                return false;
            }
            this.c();
            super.af = 1;
            return true;
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not decode message: " + psc_m.getMessage());
        }
    }
    
    private void c() throws psc_m3 {
        try {
            if (this.a == -1) {
                if (super.p[1].f()) {
                    if (super.ab == 1) {
                        final byte[] a = this.a(super.p[1]);
                        this.a = new psc_p(0, true, 0, a, 0, a.length, true).e();
                    }
                    else {
                        this.a = ((psc_p)super.p[1]).e();
                    }
                }
                else {
                    if (super.p[1].b == null) {
                        return;
                    }
                    super.ab = 1;
                    this.c(super.p[1].b, super.p[1].c, super.p[1].d, 0);
                    return;
                }
            }
            if (this.b.size() == 0) {
                if (!super.p[2].f()) {
                    if (super.p[2].b == null) {
                        return;
                    }
                    super.ab = 2;
                    this.c(super.p[2].b, super.p[2].c, super.p[2].d, 0);
                    return;
                }
                else if (super.ab == 2) {
                    final byte[] a2 = this.a(super.p[2]);
                    this.a(a2, 0, a2.length);
                }
                else {
                    this.a(super.p[2].b, super.p[2].c, super.p[2].d);
                }
            }
            if (super.m == null) {
                if (super.p[3].f()) {
                    if (super.ab == 3) {
                        final byte[] a3 = this.a(super.p[3]);
                        this.b(a3, 0, a3.length);
                    }
                    else {
                        this.b(super.p[3].b, super.p[3].c, super.p[3].d);
                    }
                }
                else {
                    if (super.p[3].b == null) {
                        return;
                    }
                    super.ab = 3;
                    this.c(super.p[3].b, super.p[3].c, super.p[3].d, super.l);
                    return;
                }
            }
            if (this.f()) {
                this.a(1);
            }
            if (this.e.size() == 0) {
                if (super.p[4].f()) {
                    if (super.p[4].a) {
                        if (super.ab == 4) {
                            final byte[] a4 = this.a(super.p[4]);
                            this.c(a4, 0, a4.length);
                        }
                        else {
                            this.c(super.p[4].b, super.p[4].c, super.p[4].d);
                        }
                    }
                }
                else {
                    if (super.p[4].b == null) {
                        return;
                    }
                    super.ab = 4;
                    this.c(super.p[4].b, super.p[4].c, super.p[4].d, 0);
                    return;
                }
            }
            if (this.f.size() == 0) {
                if (super.p[5].f()) {
                    if (super.p[5].a) {
                        if (super.ab == 5) {
                            final byte[] a5 = this.a(super.p[5]);
                            this.d(a5, 0, a5.length);
                        }
                        else {
                            this.d(super.p[5].b, super.p[5].c, super.p[5].d);
                        }
                    }
                }
                else {
                    if (super.p[5].b == null) {
                        return;
                    }
                    super.ab = 5;
                    this.c(super.p[5].b, super.p[5].c, super.p[5].d, 0);
                    return;
                }
            }
            if (this.g.size() == 0) {
                if (super.p[6].f()) {
                    if (super.ab == 6) {
                        final byte[] a6 = this.a(super.p[6]);
                        this.e(a6, 0, a6.length);
                    }
                    else {
                        this.e(super.p[6].b, super.p[6].c, super.p[6].d);
                    }
                }
                else {
                    if (super.p[6].b == null) {
                        return;
                    }
                    super.ab = 6;
                    this.c(super.p[6].b, super.p[6].c, super.p[6].d, 0);
                    return;
                }
            }
            if (this.g.size() > 0) {
                this.d();
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot set decoded values: " + ex.getMessage());
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Digest OIDs data is null.");
        }
        try {
            final psc_w psc_w = new psc_w(0, 12544, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_k psc_k = new psc_k(12288, true, 0, null, 0, 0);
                psc_l.a(a.b, a.c, new psc_i[] { psc_k });
                final byte[] obj = new byte[psc_k.d];
                System.arraycopy(psc_k.b, psc_k.c, obj, 0, psc_k.d);
                this.b.addElement(obj);
                this.d.addElement(psc_nv.a(obj, null));
                this.c.addElement(null);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot decode digest OIDs: " + psc_m.getMessage());
        }
    }
    
    private void b(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Content data is null.");
        }
        try {
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_r, new psc_k(10616576, true, 0, super.l, null, 0, 0), psc_j });
            super.m = psc_m2.a(psc_r.b, psc_r.c, psc_r.d, super.o, super.n);
            if (super.m.b(array, n, n2, super.l) && super.m.n()) {
                return;
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot decode content: " + ex.getMessage());
        }
        throw new psc_m3("Cannot decode content.");
    }
    
    private void c(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Certs data is null.");
        }
        try {
            final psc_w psc_w = new psc_w(8454144, 12544, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a(new psc_e(a.b, a.c, 0));
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot decode certs: " + ex.getMessage());
        }
    }
    
    private void d(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("CRLs data is null.");
        }
        try {
            final psc_w psc_w = new psc_w(8454145, 12544, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a(new psc_m9(a.b, a.c, 0));
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot decode crls: " + ex.getMessage());
        }
    }
    
    private void e(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("SignerInfo data is null.");
        }
        try {
            final psc_w psc_w = new psc_w(0, 12544, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a(new psc_nw(a.b, a.c, 0));
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot decode signerInfo: " + ex.getMessage());
        }
    }
    
    private void d() throws psc_m3 {
        if (this.g.size() == 0) {
            return;
        }
        if (super.o == null) {
            throw new psc_m3("CertJ context object is null.");
        }
        psc_ed e = null;
        if (super.n != null) {
            e = super.n.e();
        }
        psc_ed a = null;
        psc_d8 a2 = null;
        String b = null;
        psc_ed psc_ed;
        psc_nw obj;
        psc_nq psc_nq;
        Vector<psc_e> vector;
        psc_e psc_e;
        psc_al b2;
        byte[] a3;
        byte[] f;
        byte[] a4;
        byte[] k;
        byte[] array;
        String s;
        psc_eo a5;
        Block_45_Outer:Label_1192_Outer:
        while (true) {
            Label_1128: {
                try {
                    if (super.n != null && (this.e.size() > 0 || this.f.size() > 0)) {
                        a2 = this.a("Temp Memory DB", this.e, this.f);
                        b = a2.b();
                        a = this.a(e, b);
                    }
                    psc_ed = ((a == null) ? e : a);
                    if (this.e.size() == 0 && psc_ed == null) {
                        throw new psc_m3("Cannot get a certificate for all signers.");
                    }
                    for (int i = 0; i < this.g.size(); ++i) {
                        obj = this.g.elementAt(i);
                        psc_nq = null;
                        try {
                            vector = new Vector<psc_e>();
                            psc_e = null;
                            if (psc_ed != null) {
                                if (psc_ed.a(obj.c(), obj.d(), vector) == 0) {
                                    this.l.addElement(obj);
                                    continue;
                                }
                                psc_e = vector.elementAt(0);
                                if (a != null) {
                                    psc_nq = new psc_nq(super.n.a(), super.n.b(), super.n.c(), super.n.d(), a);
                                }
                                if (!super.o.a((psc_nq == null) ? super.n : psc_nq, psc_e, null, null, null, null)) {
                                    this.k.addElement(obj);
                                }
                            }
                            else {
                                for (int j = 0; j < this.e.size(); ++j) {
                                    if (obj.c() == null || obj.d() == null) {
                                        throw new psc_m3("Signer's name or serial number is not set.");
                                    }
                                    if (obj.c().equals(((psc_e)this.e.elementAt(j)).g()) && psc_k4.a(obj.d(), ((psc_e)this.e.elementAt(j)).h())) {
                                        psc_e = (psc_e)this.e.elementAt(j);
                                        break;
                                    }
                                }
                                if (psc_e == null) {
                                    this.l.addElement(obj);
                                    continue;
                                }
                                this.k.addElement(obj);
                            }
                            b2 = psc_e.b(this.h());
                        }
                        catch (psc_mv psc_mv) {
                            throw new psc_m3("Cannot get Cert from DB: " + psc_mv.getMessage());
                        }
                        catch (psc_nk psc_nk) {
                            throw new psc_m3("Cannot get Cert from DB: " + psc_nk.getMessage());
                        }
                        catch (psc_g psc_g) {
                            throw new psc_m3("Cannot get IssuerName and SerialNumber from cert: " + psc_g.getMessage());
                        }
                        catch (psc_n6 psc_n6) {
                            throw new psc_m3("Cannot validate cert path: " + psc_n6.getMessage());
                        }
                        catch (psc_d7 psc_d7) {
                            throw new psc_m3("Cannot validate cert path: " + psc_d7.getMessage());
                        }
                        try {
                            a3 = this.a(obj, 1);
                            f = obj.f();
                            if (f == null) {
                                this.j.addElement(obj);
                                b2.y();
                            }
                            else {
                                a4 = this.a(f, a3);
                                k = obj.j();
                                if (k == null) {
                                    throw new psc_m3("Signer's signing algorithm is not set.");
                                }
                                array = obj.l();
                                if (array == null) {
                                    this.j.addElement(obj);
                                    b2.y();
                                }
                                else {
                                    try {
                                        s = "NoDigest/" + psc_iq.a(k, 0, "Java").p() + "/PKCS1Block01Pad";
                                    }
                                    catch (psc_ap psc_ap2) {
                                        s = "NoDigest/" + psc_eo.a(k, 0, "Java").m() + "/NoPad";
                                        a4 = new byte[a3.length];
                                        System.arraycopy(a3, 0, a4, 0, a3.length);
                                    }
                                    a5 = psc_eo.a(s, this.h());
                                    a5.a(b2, null, super.o.e(), super.o.a());
                                    a5.e(a4, 0, a4.length);
                                    if (a5.m().equals("DSA")) {
                                        array = this.c(array);
                                    }
                                    if (!a5.f(array, 0, array.length)) {
                                        this.j.addElement(obj);
                                    }
                                    a5.y();
                                    b2.y();
                                }
                            }
                        }
                        catch (psc_ap psc_ap) {
                            throw new psc_m3("Could not sign ContentInfo: " + psc_ap.getMessage());
                        }
                        catch (psc_n5 psc_n7) {
                            throw new psc_m3("Could not sign ContentInfo: " + psc_n7.getMessage());
                        }
                        catch (psc_mv psc_mv2) {
                            throw new psc_m3("Could not sign ContentInfo: " + psc_mv2.getMessage());
                        }
                    }
                    break Block_45_Outer;
                    break Label_1128;
                }
                finally {
                    if (a != null) {
                        super.o.a(a);
                    }
                    Label_1247: {
                        if (a2 != null) {
                            try {
                                super.o.a(1, b);
                                break Label_1247;
                            }
                            catch (psc_d7 psc_d8) {}
                            break Label_1247;
                        }
                        break Label_1247;
                    }
                    while (true) {}
                    // iftrue(Label_1192:, a == null)
                    // iftrue(Label_1211:, a2 == null)
                    while (true) {
                        Block_44: {
                        Label_1211:
                            while (true) {
                                try {
                                    super.o.a(1, b);
                                }
                                catch (psc_d7 psc_d9) {}
                                break Label_1211;
                                break Block_44;
                                continue Label_1192_Outer;
                            }
                            continue Block_45_Outer;
                        }
                        super.o.a(a);
                        continue;
                    }
                    Label_1177: {
                        return;
                    }
                    // iftrue(Label_1177:, this.j.size() <= 0 && this.k.size() <= 0 || super.n == null && this.l.size() <= 0)
                    throw new psc_m3(this.e());
                }
            }
            break;
        }
    }
    
    private String e() {
        final StringBuffer sb = new StringBuffer();
        if (this.l.size() > 0) {
            sb.append("Cannot find certificates for signers: ");
            for (int i = 0; i < this.l.size(); ++i) {
                final psc_nw psc_nw = this.l.elementAt(i);
                try {
                    if (psc_nw.c() != null && psc_nw.d() != null) {
                        sb.append("\nIssuer Name ");
                        sb.append(psc_nw.c().toString());
                        sb.append(" , Serial Number ");
                        sb.append(this.b(psc_nw.d()));
                    }
                }
                catch (psc_m3 psc_m3) {}
            }
        }
        if (this.k.size() > 0) {
            sb.append("\nCannot build and verify certPath for signers: ");
            for (int j = 0; j < this.k.size(); ++j) {
                final psc_nw psc_nw2 = this.k.elementAt(j);
                try {
                    if (psc_nw2.c() != null && psc_nw2.d() != null) {
                        sb.append("\n Issuer Name ");
                        sb.append(psc_nw2.c().toString());
                        sb.append(" , Serial Number ");
                        sb.append(this.b(psc_nw2.d()));
                    }
                }
                catch (psc_m3 psc_m4) {}
            }
        }
        if (this.j.size() > 0) {
            sb.append("\nCannot verify signature for signers: ");
            for (int k = 0; k < this.j.size(); ++k) {
                final psc_nw psc_nw3 = this.j.elementAt(k);
                try {
                    if (psc_nw3.c() != null && psc_nw3.d() != null) {
                        sb.append("\n Issuer Name ");
                        sb.append(psc_nw3.c().toString());
                        sb.append(" , Serial Number ");
                        sb.append(this.b(psc_nw3.d()));
                    }
                }
                catch (psc_m3 psc_m5) {}
            }
        }
        return sb.toString();
    }
    
    private String b(final byte[] array) {
        return this.f(array, 0, array.length);
    }
    
    private String f(final byte[] array, int n, int i) {
        final StringBuffer sb = new StringBuffer();
        while (i > 0) {
            for (int n2 = 0; n2 < 8 && i != 0; --i, ++n2, ++n) {
                final String hexString = Integer.toHexString(array[n] & 0xFF);
                if (hexString.length() == 1) {
                    sb.append(" 0");
                }
                else {
                    sb.append(" ");
                }
                sb.append(hexString);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public Vector y() {
        if (this.j.size() == 0) {
            return null;
        }
        return (Vector)this.j.clone();
    }
    
    public Vector z() {
        if (this.k.size() == 0) {
            return null;
        }
        return (Vector)this.k.clone();
    }
    
    protected int m(final byte[] array, final int n, final int n2) throws psc_m3 {
        final int n3 = 0;
        if (array == null) {
            return n3;
        }
        if (super.q == null) {
            throw new psc_m3("Call readInit before readUpdate.");
        }
        try {
            if (super.q.e()) {
                return n3;
            }
            final int a = super.q.a(array, n, n2);
            this.c();
            return a;
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not decode message: " + psc_m.getMessage());
        }
    }
    
    protected boolean aa() throws psc_m3 {
        try {
            super.q.d();
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Cannot call readFinal: " + psc_m.getMessage());
        }
        if (!super.q.e()) {
            throw new psc_m3("Cannot call readFinal, more message expected.");
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_nt psc_nt = (psc_nt)super.clone();
        psc_nt.a = this.a;
        psc_nt.h = this.h;
        psc_nt.i = this.i;
        if (this.b != null) {
            psc_nt.b = (Vector)this.b.clone();
        }
        if (this.d != null) {
            psc_nt.d = (Vector)this.d.clone();
        }
        if (this.b != null) {
            psc_nt.c = (Vector)this.c.clone();
        }
        if (this.e != null) {
            psc_nt.e = (Vector)this.e.clone();
        }
        if (this.f != null) {
            psc_nt.f = (Vector)this.f.clone();
        }
        if (this.g != null) {
            psc_nt.g = (Vector)this.g.clone();
        }
        return psc_nt;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_nt)) {
            return false;
        }
        final psc_nt psc_nt = (psc_nt)o;
        if (psc_nt.a != this.a) {
            return false;
        }
        if (super.m != null && !super.m.equals(psc_nt.m)) {
            return false;
        }
        if (psc_nt.i != this.i) {
            return false;
        }
        final int size = this.g.size();
        final int size2 = psc_nt.g.size();
        if (size != size2) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (this.g.elementAt(i) != null) {
                int index;
                for (index = 0; index < size2 && !this.g.elementAt(i).equals(psc_nt.g.elementAt(index)); ++index) {}
                if (index == size2) {
                    return false;
                }
            }
        }
        final int size3 = this.b.size();
        final int size4 = psc_nt.b.size();
        if (size3 != size4) {
            return false;
        }
        for (int j = 0; j < size3; ++j) {
            int index2 = 0;
            if (this.b.elementAt(j) != null) {
                for (index2 = 0; index2 < size4 && !psc_k4.a(this.b.elementAt(j), (byte[])psc_nt.b.elementAt(index2)); ++index2) {}
                if (index2 == size4) {
                    return false;
                }
            }
            final String s = this.d.elementAt(j);
            final byte[] array = this.c.elementAt(j);
            final String anObject = psc_nt.d.elementAt(index2);
            final byte[] array2 = psc_nt.c.elementAt(index2);
            if (s == null) {
                if (anObject != null) {
                    return false;
                }
            }
            else {
                if (anObject == null) {
                    return false;
                }
                if (!s.equals(anObject)) {
                    return false;
                }
            }
            if (array == null) {
                if (array2 != null) {
                    return false;
                }
            }
            else {
                if (array2 == null) {
                    return false;
                }
                if (!psc_k4.a(array, array2)) {
                    return false;
                }
            }
        }
        final int size5 = this.j.size();
        if (size5 != psc_nt.j.size()) {
            return false;
        }
        for (int k = 0; k < size5; ++k) {
            if (this.j.elementAt(k) != null) {
                if (!((psc_nw)this.j.elementAt(k)).equals(psc_nt.j.elementAt(k))) {
                    return false;
                }
            }
            else if (psc_nt.j.elementAt(k) != null) {
                return false;
            }
        }
        final int size6 = this.e.size();
        final int size7 = psc_nt.e.size();
        if (size6 != size7) {
            return false;
        }
        for (int l = 0; l < size6; ++l) {
            if (this.e.elementAt(l) != null) {
                int index3;
                for (index3 = 0; index3 < size7 && !this.e.elementAt(l).equals(psc_nt.e.elementAt(index3)); ++index3) {}
                if (index3 == size7) {
                    return false;
                }
            }
        }
        final int size8 = this.f.size();
        final int size9 = psc_nt.f.size();
        if (size8 != size9) {
            return false;
        }
        for (int n = 0; n < size8; ++n) {
            if (this.f.elementAt(n) != null) {
                int index4;
                for (index4 = 0; index4 < size9 && !this.f.elementAt(n).equals(psc_nt.f.elementAt(index4)); ++index4) {}
                if (index4 == size9) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void s() {
        super.s();
        this.a = -1;
        this.b = new Vector();
        this.d = new Vector();
        this.c = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.g = new Vector();
    }
    
    protected void finalize() {
        this.s();
    }
    
    private psc_nx a(final String s, final Vector vector, final Vector vector2) throws psc_m3 {
        if ((vector == null || vector.size() == 0) && (vector2 == null || vector2.size() == 0)) {
            return null;
        }
        if (super.o == null) {
            throw new psc_m3("SignedData.storeIntoTempDB: theCertJ should not be null.");
        }
        psc_ed psc_ed = null;
        Label_0231: {
            try {
                final String a = this.a("Temp Memory DB");
                final psc_nx psc_nx = new psc_nx(a);
                super.o.a(psc_nx);
                psc_ed = (psc_ed)super.o.c(1, a);
                if (vector != null && vector.size() != 0) {
                    for (int i = 0; i < vector.size(); ++i) {
                        psc_ed.a(vector.elementAt(i));
                    }
                }
                if (vector2 != null && vector2.size() != 0) {
                    for (int j = 0; j < vector2.size(); ++j) {
                        psc_ed.a(vector2.elementAt(j));
                    }
                }
                final psc_nx psc_nx2 = psc_nx;
                break Label_0231;
            }
            catch (psc_d6 psc_d6) {
                throw new psc_m3("SignedData.storeIntoTempDB: " + psc_d6.getMessage());
            }
            finally {
                Label_0266: {
                    if (psc_ed != null) {
                        super.o.a(psc_ed);
                        break Label_0266;
                    }
                    break Label_0266;
                }
                while (true) {}
                final psc_nx psc_nx2;
                Label_0247: {
                    return psc_nx2;
                }
                while (true) {
                    super.o.a(psc_ed);
                    return psc_nx2;
                    continue;
                }
            }
            // iftrue(Label_0247:, psc_ed == null)
        }
    }
    
    private psc_ed a(final psc_ed psc_ed, final String s) throws psc_m3 {
        try {
            final String[] k = psc_ed.k();
            final String[] array = new String[k.length + 1];
            System.arraycopy(k, 0, array, 0, k.length);
            array[k.length] = s;
            return (psc_ed)super.o.a(1, array);
        }
        catch (psc_d6 psc_d6) {
            throw new psc_m3("SignedData.addTempDB: " + psc_d6.getMessage());
        }
    }
    
    private String a(final String str) {
        final String[] b = super.o.b(1);
        if (!this.a(str, b)) {
            return str;
        }
        int i = 0;
        String string;
        while (true) {
            string = str + i;
            if (!this.a(string, b)) {
                break;
            }
            ++i;
        }
        return string;
    }
    
    private boolean a(final String s, final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (s.equals(array[i])) {
                return true;
            }
        }
        return false;
    }
    
    private byte[] g(final byte[] array, final int n, final int i) throws psc_m3 {
        if (i != 40) {
            throw new psc_m3("Wrong R S length: " + i);
        }
        try {
            return psc_l.a(new psc_i[] { new psc_h(0, true, 0), new psc_p(0, true, 0, array, n, 20, true), new psc_p(0, true, 0, array, n + 20, 20, true), new psc_j() });
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Unable to encode R,S: " + psc_m.getMessage());
        }
    }
    
    private byte[] c(final byte[] array) throws psc_m3 {
        final byte[] array2 = new byte[40];
        final psc_h psc_h = new psc_h(0);
        final psc_j psc_j = new psc_j();
        final psc_p psc_p = new psc_p(0);
        final psc_p psc_p2 = new psc_p(0);
        try {
            psc_l.a(array, 0, new psc_i[] { psc_h, psc_p, psc_p2, psc_j });
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Unable to encode R,S: " + psc_m.getMessage());
        }
        for (int n = psc_p.c + psc_p.d - 1, i = 19; i >= 0; --i, --n) {
            if (n >= psc_p.c) {
                array2[i] = psc_p.b[n];
            }
            else {
                array2[i] = 0;
            }
        }
        for (int n2 = psc_p2.c + psc_p2.d - 1, j = 39; j >= 20; --j, --n2) {
            if (n2 >= psc_p2.c) {
                array2[j] = psc_p2.b[n2];
            }
            else {
                array2[j] = 0;
            }
        }
        return array2;
    }
    
    private boolean f() {
        final Enumeration elements = this.c.elements();
        while (elements.hasMoreElements()) {
            if (elements.nextElement() != null) {
                return false;
            }
        }
        return true;
    }
}
