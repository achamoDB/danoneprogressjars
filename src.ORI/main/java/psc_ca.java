import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_ca extends psc_aj implements Cloneable, Serializable, psc_bj
{
    public static byte[] a;
    private Vector b;
    private Vector c;
    psc_n d;
    
    public psc_ca() {
        this.b = new Vector();
        this.c = new Vector();
        super.bu = 100;
        super.bt = false;
        this.a(psc_aj.ab);
        super.a = "AuthorityInfoAccess";
    }
    
    public psc_ca(final byte[] array, final int n, final int n2, final psc_fh obj, final boolean bt) throws psc_g {
        this.b = new Vector();
        this.c = new Vector();
        super.bu = 100;
        super.bt = bt;
        this.a(psc_aj.ab);
        if (array == null || n2 == 0 || obj == null) {
            throw new psc_g("Missing values");
        }
        final byte[] obj2 = new byte[n2];
        System.arraycopy(array, n, obj2, 0, n2);
        this.c.addElement(obj2);
        this.b.addElement(obj);
        super.a = "AuthorityInfoAccess";
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_fh obj) throws psc_g {
        if (array == null || n2 == 0 || obj == null) {
            throw new psc_g("Missing values");
        }
        final byte[] obj2 = new byte[n2];
        System.arraycopy(array, n, obj2, 0, n2);
        this.c.addElement(obj2);
        this.b.addElement(obj);
    }
    
    public psc_fh b(final int index) throws psc_g {
        if (index < this.b.size()) {
            return this.b.elementAt(index);
        }
        throw new psc_g("Index is invalid.");
    }
    
    public byte[] g(final int index) throws psc_g {
        if (index < this.c.size()) {
            return this.c.elementAt(index);
        }
        throw new psc_g("Index is invalid.");
    }
    
    public int a() {
        return this.c.size();
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null) {
            throw new psc_g("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(0, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_h psc_h = new psc_h(0);
                final psc_j psc_j = new psc_j();
                final psc_r psc_r = new psc_r(16777216);
                final psc_k psc_k = new psc_k(65280);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
                if (psc_r.b != null && psc_r.d != 0) {
                    final byte[] obj = new byte[psc_r.d];
                    System.arraycopy(psc_r.b, psc_r.c, obj, 0, psc_r.d);
                    this.c.addElement(obj);
                }
                this.b.addElement(new psc_fh(psc_k.b, psc_k.c, 0));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode AuthorityInfoAccess extension.");
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not decode AuthorityInfoAccess extension.");
        }
    }
    
    public int e() {
        if (this.b.size() == 0) {
            return 0;
        }
        try {
            final Vector vector = new Vector<psc_w>();
            final psc_w obj = new psc_w(0, true, 0, 12288, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.c.size(); ++i) {
                obj.b(this.a(i));
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.d = new psc_n(anArray);
            return this.d.a();
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    private psc_k a(final int n) throws psc_g {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        final byte[] array = this.c.elementAt(n);
        psc_k psc_k;
        try {
            final psc_r psc_r = new psc_r(16777216, true, 0, array, 0, array.length);
            final psc_fh psc_fh = this.b.elementAt(n);
            final byte[] array2 = new byte[psc_fh.a(0)];
            final psc_n psc_n = new psc_n(new psc_i[] { psc_h, psc_r, new psc_k(0, true, 0, array2, 0, psc_fh.a(array2, 0, 0)), psc_j });
            final byte[] array3 = new byte[psc_n.a()];
            psc_k = new psc_k(12288, true, 0, array3, 0, psc_n.a(array3, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_g(" Can't encode Access Description");
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not encode Access Description ");
        }
        return psc_k;
    }
    
    public int e(final byte[] array, final int n) {
        if (this.d == null && this.e() == 0) {
            return 0;
        }
        try {
            return this.d.a(array, n);
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_ca psc_ca = new psc_ca();
        for (int i = 0; i < this.c.size(); ++i) {
            psc_ca.c.addElement(this.c.elementAt(i));
            psc_ca.b.addElement(this.b.elementAt(i));
        }
        if (this.d != null) {
            psc_ca.e();
        }
        super.a(psc_ca);
        return psc_ca;
    }
    
    protected void f() {
        super.f();
        this.c = new Vector();
        this.b = new Vector();
    }
    
    static {
        psc_ca.a = new byte[] { 43, 6, 1, 5, 5, 7, 48, 1 };
    }
}
