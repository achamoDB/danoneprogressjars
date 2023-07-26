import java.security.SecureRandom;
import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nu extends psc_m2 implements Cloneable, Serializable
{
    protected int a;
    protected Vector b;
    protected psc_ns c;
    private psc_dc d;
    private int e;
    private String f;
    
    public psc_nu(final psc_ah o, final psc_nq n) {
        this.a = -1;
        this.b = new Vector();
        this.c = new psc_ns();
        super.k = 3;
        super.o = o;
        super.n = n;
    }
    
    public void a(final int a) {
        this.a = a;
    }
    
    public int f() {
        return this.a;
    }
    
    public void a(final String original, final int e) throws psc_m3 {
        if (original == null) {
            throw new psc_m3("Algorithm name is null.");
        }
        this.f = new String(original);
        try {
            if (super.o == null) {
                throw new psc_m3("CertJ object is NULL; cannot get Random object.");
            }
            final psc_df a = psc_df.a(original, this.h());
            if (a.j() != null && !a.j().equals("ECB")) {
                a.a(super.o.e());
            }
            final byte[] f = a.f();
            if (f != null) {
                this.c.b(f, 0, f.length);
            }
            this.e = e;
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not get algorithm OID: " + psc_ap.getMessage());
        }
        catch (psc_n5 psc_n5) {
            throw new psc_m3("Could not get algorithm OID" + psc_n5.getMessage());
        }
        catch (psc_mv psc_mv) {
            throw new psc_m3("Could not get algorithm OID" + psc_mv.getMessage());
        }
    }
    
    public void d(final byte[] array, final int n, final int n2, final int e) throws psc_m3 {
        if (array == null || n2 <= 0) {
            throw new psc_m3("Could not set algorithm OID: OID is null");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new psc_m3("invalid data");
        }
        this.c.b(array, n, n2);
        this.e = e;
    }
    
    public String t() throws psc_m3 {
        if (this.f != null) {
            return new String(this.f);
        }
        if (this.c == null) {
            return null;
        }
        final byte[] c = this.c.c();
        if (c == null) {
            throw new psc_m3("Encryption algorithm is not set.");
        }
        try {
            final psc_h psc_h = new psc_h(0, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(0);
            psc_l.a(c, 0, new psc_i[] { psc_h, psc_r, new psc_k(130816), psc_j });
            return psc_s.b(psc_r.b, psc_r.c, psc_r.d, 6);
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Encryption algorithm is not valid." + psc_m.getMessage());
        }
    }
    
    public byte[] u() {
        if (this.c == null) {
            return null;
        }
        return this.c.c();
    }
    
    public void a(final psc_m2 psc_m2) throws psc_m3 {
        if (psc_m2 == null) {
            throw new psc_m3("Null content");
        }
        try {
            super.m = (psc_m2)psc_m2.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Unable to clone ContentInfo type");
        }
    }
    
    public void a(final psc_ny psc_ny) throws psc_m3 {
        if (psc_ny == null) {
            throw new psc_m3("Null RecipientInfo");
        }
        try {
            this.b.addElement(psc_ny.clone());
        }
        catch (CloneNotSupportedException ex) {
            throw new psc_m3("Cannot add this Recipient Information.");
        }
    }
    
    public Vector v() {
        return (Vector)this.b.clone();
    }
    
    protected int m() throws psc_m3 {
        return this.a();
    }
    
    protected int b(final byte[] array, final int n) throws psc_m3 {
        if (array == null) {
            throw new psc_m3("Cannot write EnvelopedData: output array is null.");
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
            throw new psc_m3("Unable to DER encode EnvelopedData message: " + psc_m.getMessage());
        }
    }
    
    private int a() throws psc_m3 {
        try {
            if (super.af == 0) {
                this.b();
            }
            if (this.b.size() == 0) {
                throw new psc_m3("RecipientInfos are not set.");
            }
            final psc_w obj = new psc_w(0, 12544, new psc_k(12288));
            final Vector vector = new Vector<psc_w>();
            vector.addElement(obj);
            for (int i = 0; i < this.b.size(); ++i) {
                try {
                    final psc_ny psc_ny = this.b.elementAt(i);
                    final byte[] array = new byte[psc_ny.b(0)];
                    obj.b(new psc_k(0, true, 0, array, 0, psc_ny.c(array, 0, 0)));
                }
                catch (psc_m psc_m) {
                    throw new psc_m3("Unable to encode RecipientInfos: " + psc_m.getMessage());
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            final psc_n psc_n = new psc_n(anArray);
            final byte[] array2 = new byte[psc_n.a()];
            final psc_k psc_k = new psc_k(0, true, 0, array2, 0, psc_n.a(array2, 0));
            final byte[] array3 = new byte[this.c.a(0)];
            final psc_k psc_k2 = new psc_k(0, true, 0, array3, 0, this.c.d(array3, 0, 0));
            final psc_h psc_h = new psc_h(10551296, true, 0);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0, true, 0, this.a);
            (super.p = new psc_i[5])[0] = psc_h;
            super.p[1] = psc_p;
            super.p[2] = psc_k;
            super.p[3] = psc_k2;
            super.p[4] = psc_j;
            super.q = new psc_n(super.p);
            return super.q.a();
        }
        catch (psc_m psc_m2) {
            throw new psc_m3("Could not DER encode EnvelopedData: " + psc_m2.getMessage());
        }
    }
    
    private void b() throws psc_m3 {
        if (this.a == -1) {
            this.a = 0;
        }
        if (super.m == null) {
            throw new psc_m3("There is no content to encrypt.");
        }
        final int j = super.m.j();
        final byte[] array = new byte[9];
        System.arraycopy(psc_m2.i, 0, array, 0, 8);
        array[8] = (byte)j;
        this.c.a(array, 0, 9);
        byte[] array2 = null;
        try {
            if (super.ac == null) {
                super.ac = new byte[super.m.l()];
                super.m.a(super.ac, 0);
            }
            final psc_h psc_h = new psc_h(0);
            final psc_j psc_j = new psc_j();
            final psc_r psc_r = new psc_r(16777216);
            final psc_k psc_k = new psc_k(10616576);
            psc_l.a(super.ac, 0, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
            if (psc_k.a) {
                int n = 1 + psc_o.a(psc_k.b, psc_k.c + 1);
                final int n2 = ++n + psc_o.a(psc_k.b, psc_k.c + n);
                array2 = new byte[psc_k.d - n2];
                System.arraycopy(psc_k.b, psc_k.c + n2, array2, 0, psc_k.d - n2);
            }
        }
        catch (psc_m psc_m) {
            throw new psc_m3("Could not DER encode ContentInfo: " + psc_m.getMessage());
        }
        try {
            final byte[] c = this.c.c();
            if (c == null) {
                throw new psc_m3("Encryption Algorithm is not set.");
            }
            if (super.o == null) {
                throw new psc_m3("CertJ object is NULL; cannot get Random object.");
            }
            final psc_av e = super.o.e();
            final psc_df a = psc_df.a(c, 0, this.h());
            this.d = psc_dc.a(a.i(), this.h());
            if (this.e != 0) {
                this.d.a(new int[] { this.e }, e);
            }
            else {
                this.d.a(null, e);
            }
            this.d.t();
            if (array2 != null) {
                final byte[] array3 = new byte[a.a(array2.length)];
                a.a(this.d);
                final int a2 = a.a(array2, 0, array2.length, array3, 0);
                this.c.c(array3, 0, a2 + a.b(array3, a2));
            }
            a.y();
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not encrypt content: " + psc_ap.getMessage());
        }
        catch (psc_n5 psc_n5) {
            throw new psc_m3("Could not encrypt content" + psc_n5.getMessage());
        }
        catch (psc_mv psc_mv) {
            throw new psc_m3("Could not encrypt content" + psc_mv.getMessage());
        }
        try {
            if (super.n == null) {
                throw new psc_m3("CertPathCtx object is null, cannot get Database.");
            }
            final psc_ed e2 = super.n.e();
            final Vector<psc_e> vector = new Vector<psc_e>();
            if (super.o == null) {
                throw new psc_m3("CertJ object is NULL; cannot get Random object.");
            }
            for (int i = 0; i < this.b.size(); ++i) {
                final psc_ny psc_ny = this.b.elementAt(i);
                if (e2.a(psc_ny.c(), psc_ny.d(), vector) == 0) {
                    throw new psc_m3("Cannot find this Recipient cert.");
                }
                final psc_al b = vector.elementAt(0).b(this.h());
                final psc_av e3 = super.o.e();
                final psc_iq a3 = psc_iq.a(psc_ny.e(), 0, this.h());
                a3.a(b, e3, super.o.a());
                final byte[] b2 = a3.b(this.d, false);
                psc_ny.b(b2, 0, b2.length);
                b.y();
                a3.y();
                vector.removeAllElements();
            }
        }
        catch (psc_ap psc_ap2) {
            throw new psc_m3("Could not encrypt ContentInfo" + psc_ap2.getMessage());
        }
        catch (psc_n5 psc_n6) {
            throw new psc_m3("Could not encrypt ContentInfo" + psc_n6.getMessage());
        }
        catch (psc_mv psc_mv2) {
            throw new psc_m3("Could not encrypt ContentInfo" + psc_mv2.getMessage());
        }
        catch (psc_nk psc_nk) {
            throw new psc_m3("Cannot get Cert from DB." + psc_nk.getMessage());
        }
        catch (psc_g psc_g) {
            throw new psc_m3("Cannot get IssuerName and SerialNumber from cert." + psc_g.getMessage());
        }
    }
    
    protected boolean k(final byte[] array, final int n, final int n2) throws psc_m3 {
        try {
            final psc_h psc_h = new psc_h(10551296);
            final psc_j psc_j = new psc_j();
            final psc_p psc_p = new psc_p(0);
            final psc_k psc_k = new psc_k(12544);
            final psc_k psc_k2 = new psc_k(12288, true, 0, super.l, null, 0, 0);
            (super.p = new psc_i[5])[0] = psc_h;
            super.p[1] = psc_p;
            super.p[2] = psc_k;
            super.p[3] = psc_k2;
            super.p[4] = psc_j;
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
                if (super.p[2].f()) {
                    if (super.ab == 2) {
                        final byte[] a2 = this.a(super.p[2]);
                        this.a(a2, 0, a2.length);
                    }
                    else {
                        this.a(super.p[2].b, super.p[2].c, super.p[2].d);
                    }
                }
                else {
                    if (super.p[2].b == null) {
                        return;
                    }
                    super.ab = 2;
                    this.c(super.p[2].b, super.p[2].c, super.p[2].d, 0);
                    return;
                }
            }
            if (super.p[3].f()) {
                if (super.ab == 3) {
                    this.c = new psc_ns(this.a(super.p[3]), 0, 0, super.l);
                }
                else {
                    this.c = new psc_ns(super.p[3].b, super.p[3].c, 0, super.l);
                }
                this.e();
                this.d();
            }
            else {
                if (super.p[3].b == null) {
                    return;
                }
                super.ab = 3;
                this.c(super.p[3].b, super.p[3].c, super.p[3].d, super.l);
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot set decoded values." + ex.getMessage());
        }
    }
    
    private void a(final byte[] array, final int n, final int n2) throws psc_m3 {
        if (array == null || n2 == 0) {
            throw new psc_m3("Cannot decode RecipientInfo: data is null.");
        }
        try {
            final psc_w psc_w = new psc_w(0, 12544, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a(new psc_ny(a.b, a.c, 0));
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot decode recipientInfo." + ex.getMessage());
        }
    }
    
    private void d() throws psc_m3 {
        try {
            final byte[] b = this.c.b();
            super.m = psc_m2.a(b, 0, 9, super.o, super.n);
            final byte[] c = this.c.c();
            if (c == null) {
                throw new psc_m3("Encryption Algorithm is not set.");
            }
            final byte[] d = this.c.d();
            if (d == null) {
                return;
            }
            final psc_df a = psc_df.a(c, 0, this.h());
            final byte[] array = new byte[a.a(d.length)];
            a.b(this.d);
            final int b2 = a.b(d, 0, d.length, array, 0);
            final int n = b2 + a.c(array, b2);
            a.y();
            this.d.y();
            final byte[] array2 = new byte[n + 1 + psc_o.b(n)];
            if (b[8] == 1) {
                array2[0] = 4;
            }
            else {
                array2[0] = 48;
            }
            System.arraycopy(array, 0, array2, 1 + psc_o.b(array2, 1, n), n);
            final byte[] array3 = new byte[array2.length + 1 + psc_o.b(array2.length)];
            array3[0] = -96;
            System.arraycopy(array2, 0, array3, 1 + psc_o.b(array3, 1, array2.length), array2.length);
            final psc_n psc_n = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_r(16777216, true, 0, b, 0, b.length), new psc_k(10616576, true, 0, array3, 0, array3.length), new psc_j() });
            final int a2 = psc_n.a();
            final byte[] array4 = new byte[a2];
            psc_n.a(array4, 0);
            if (super.m.a(array4, 0, a2, 0, super.l) && super.m.n()) {
                return;
            }
        }
        catch (Exception ex) {
            throw new psc_m3("Cannot decode content." + ex.getMessage());
        }
        throw new psc_m3("Cannot decode content.");
    }
    
    private void e() throws psc_m3 {
        if (super.n == null) {
            throw new psc_m3("CertPathCtx object is null, cannot get Database.");
        }
        if (super.o == null) {
            throw new psc_m3("CertJ object is NULL.");
        }
        final psc_ed e = super.n.e();
        final Vector<psc_e> vector = new Vector<psc_e>();
        psc_dt b = null;
        try {
            int i;
            for (i = 0; i < this.b.size(); ++i) {
                final psc_ny psc_ny = this.b.elementAt(i);
                final int a = e.a(psc_ny.c(), psc_ny.d(), vector);
                if (a != 0) {
                    for (int j = 0; j < a; ++j) {
                        b = e.b(vector.elementAt(j));
                        if (b != null) {
                            break;
                        }
                    }
                    if (b == null) {
                        vector.removeAllElements();
                    }
                    else {
                        final byte[] g = psc_ny.g();
                        if (g == null) {
                            throw new psc_m3("Recipient's encrypted Secret key is not set.");
                        }
                        final byte[] c = this.c.c();
                        if (c == null) {
                            throw new psc_m3("Encryption Algorithm is not set.");
                        }
                        final String k = psc_df.a(c, 0, "Java").i();
                        final byte[] e2 = psc_ny.e();
                        if (e2 == null) {
                            throw new psc_m3("Recipient's Encryption Algorithm is not set.");
                        }
                        final psc_iq a2 = psc_iq.a(e2, 0, this.h());
                        a2.a(b, super.o.a());
                        this.d = a2.a(g, 0, g.length, false, k);
                        a2.y();
                        b.y();
                        vector.removeAllElements();
                        break;
                    }
                }
            }
            if (i == this.b.size()) {
                throw new psc_m3("Private Key is not set");
            }
        }
        catch (psc_mv psc_mv) {
            throw new psc_m3("Cannot get Cert from DB." + psc_mv.getMessage());
        }
        catch (psc_nk psc_nk) {
            throw new psc_m3("Cannot get Cert from DB." + psc_nk.getMessage());
        }
        catch (psc_ap psc_ap) {
            throw new psc_m3("Could not decode encrypted key" + psc_ap.getMessage());
        }
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
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_nu)) {
            return false;
        }
        final psc_nu psc_nu = (psc_nu)o;
        if (psc_nu.a != this.a) {
            return false;
        }
        if (super.m != null && !super.m.equals(psc_nu.m)) {
            return false;
        }
        final int size = this.b.size();
        final int size2 = psc_nu.b.size();
        if (size != size2) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (this.b.elementAt(i) != null) {
                int index;
                for (index = 0; index < size2 && !this.b.elementAt(i).equals(psc_nu.b.elementAt(index)); ++index) {}
                if (index == size2) {
                    return false;
                }
            }
        }
        if (this.c != null) {
            if (!this.c.equals(psc_nu.c)) {
                return false;
            }
        }
        else if (psc_nu.c != null) {
            return false;
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_nu psc_nu = (psc_nu)super.clone();
        psc_nu.a = this.a;
        psc_nu.b = (Vector)this.b.clone();
        psc_nu.c = (psc_ns)this.c.clone();
        return psc_nu;
    }
    
    public void s() {
        super.s();
        this.b = new Vector();
        this.a = -1;
        this.c = new psc_ns();
    }
    
    protected void finalize() {
        this.s();
    }
}
