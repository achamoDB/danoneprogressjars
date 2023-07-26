import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bv extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private Vector[] a;
    private psc_n b;
    
    public psc_bv() {
        this.a = this.a(2);
        this.b = null;
        super.bu = 32;
        super.bt = false;
        this.c(32);
        super.a = "CertPolicies";
    }
    
    public psc_bv(final byte[] array, final int n, final int n2, final psc_gq obj, final boolean bt) {
        this.a = this.a(2);
        this.b = null;
        super.bu = 32;
        super.bt = bt;
        this.c(32);
        if (array != null && n2 != 0) {
            final byte[] obj2 = new byte[n2];
            System.arraycopy(array, n, obj2, 0, n2);
            this.a[0].addElement(obj2);
            this.a[1].addElement(obj);
        }
        super.a = "CertPolicies";
    }
    
    public void a(final byte[] array, final int n, final int n2, final psc_gq obj) {
        if (array != null && n2 != 0) {
            final byte[] obj2 = new byte[n2];
            System.arraycopy(array, n, obj2, 0, n2);
            this.a[0].addElement(obj2);
            this.a[1].addElement(obj);
        }
    }
    
    public byte[] g(final int index) throws psc_g {
        if (this.a[0].size() <= index) {
            throw new psc_g("Specified index is invalid.");
        }
        return this.a[0].elementAt(index);
    }
    
    public psc_gq h(final int index) throws psc_g {
        if (this.a[1].size() <= index) {
            throw new psc_g("Specified index is invalid.");
        }
        return this.a[1].elementAt(index);
    }
    
    public int a() {
        return this.a[0].size();
    }
    
    private Vector[] a(final int n) {
        final Vector[] array = new Vector[n];
        for (int i = 0; i < n; ++i) {
            array[i] = new Vector();
        }
        return array;
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
                final psc_k psc_k = new psc_k(77824);
                final psc_r psc_r = new psc_r(0);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
                final byte[] obj = new byte[psc_r.d];
                System.arraycopy(psc_r.b, psc_r.c, obj, 0, psc_r.d);
                this.a[0].addElement(obj);
                if (psc_k.a) {
                    this.a[1].addElement(new psc_gq(psc_k.b, psc_k.c, 65536));
                }
                else {
                    this.a[1].addElement(null);
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode Certificate Policies extension.");
        }
        catch (psc_g psc_g) {
            throw new psc_g("Could not decode Certificate Policies extension.");
        }
    }
    
    public int e() {
        if (this.a[0].size() == 0) {
            return 0;
        }
        try {
            final Vector vector = new Vector<psc_w>();
            final psc_w obj = new psc_w(super.bx, true, 0, 12288, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.a[0].size(); ++i) {
                obj.b(this.b(i));
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.b = new psc_n(anArray);
            return this.b.a();
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    private psc_k b(final int n) throws psc_g {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        final byte[] array = this.a[0].elementAt(n);
        psc_k psc_k;
        try {
            final psc_r psc_r = new psc_r(16777216, true, 0, array, 0, array.length);
            final psc_gq psc_gq = this.a[1].elementAt(n);
            psc_n psc_n;
            if (psc_gq != null) {
                final byte[] array2 = new byte[psc_gq.e(65536)];
                psc_n = new psc_n(new psc_i[] { psc_h, psc_r, new psc_k(77824, true, 0, array2, 0, psc_gq.a(array2, 0, 65536)), psc_j });
            }
            else {
                psc_n = new psc_n(new psc_i[] { psc_h, psc_r, psc_j });
            }
            final byte[] array3 = new byte[psc_n.a()];
            psc_k = new psc_k(12288, true, 0, array3, 0, psc_n.a(array3, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_g(" Can't encode Certificate Policy");
        }
        return psc_k;
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.b == null && this.e() == 0) {
            return 0;
        }
        try {
            return this.b.a(array, n);
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bv psc_bv = new psc_bv();
        for (int i = 0; i < this.a.length; ++i) {
            for (int j = 0; j < this.a[i].size(); ++j) {
                psc_bv.a[i].addElement(this.a[i].elementAt(j));
            }
        }
        if (this.b != null) {
            psc_bv.e();
        }
        super.a(psc_bv);
        return psc_bv;
    }
    
    protected void f() {
        super.f();
        this.a = this.a(2);
    }
}
