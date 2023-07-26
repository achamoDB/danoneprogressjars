import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bw extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private Vector[] a;
    private Vector[] b;
    psc_n c;
    
    public psc_bw() {
        this.a = this.a(3);
        this.b = this.a(3);
        super.bu = 33;
        super.bt = false;
        this.c(33);
        super.a = "PolicyMappings";
    }
    
    public psc_bw(final byte[] obj, final int value, final int value2, final byte[] obj2, final int value3, final int value4, final boolean bt) {
        this.a = this.a(3);
        this.b = this.a(3);
        super.bu = 33;
        super.bt = bt;
        this.c(33);
        if (obj != null && value2 != 0) {
            this.a[0].addElement(obj);
            this.a[1].addElement(new Integer(value));
            this.a[2].addElement(new Integer(value2));
        }
        if (obj2 != null && value4 != 0) {
            this.b[0].addElement(obj2);
            this.b[1].addElement(new Integer(value3));
            this.b[2].addElement(new Integer(value4));
        }
        super.a = "PolicyMappings";
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
            final psc_w psc_w = new psc_w(super.bx, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_h psc_h = new psc_h(0);
                final psc_j psc_j = new psc_j();
                final psc_r psc_r = new psc_r(0);
                final psc_r psc_r2 = new psc_r(0);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_r, psc_r2, psc_j });
                this.a[0].addElement(psc_r.b);
                this.a[1].addElement(new Integer(psc_r.c));
                this.a[2].addElement(new Integer(psc_r.d));
                this.b[0].addElement(psc_r2.b);
                this.b[1].addElement(new Integer(psc_r2.c));
                this.b[2].addElement(new Integer(psc_r2.d));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode PolicyMappings extension.");
        }
    }
    
    public void a(final byte[] obj, final int value, final int value2, final byte[] obj2, final int value3, final int value4) {
        if (obj != null && value2 != 0) {
            this.a[0].addElement(obj);
            this.a[1].addElement(new Integer(value));
            this.a[2].addElement(new Integer(value2));
        }
        if (obj2 != null && value4 != 0) {
            this.b[0].addElement(obj2);
            this.b[1].addElement(new Integer(value3));
            this.b[2].addElement(new Integer(value4));
        }
    }
    
    public byte[] g(final int n) throws psc_g {
        if (this.a[0].size() <= n) {
            throw new psc_g("Specified index is invalid.");
        }
        if (this.a[0].elementAt(n) != null) {
            final Integer n2 = this.a[1].elementAt(n);
            final Integer n3 = this.a[2].elementAt(n);
            final byte[] array = new byte[(int)n3];
            System.arraycopy(this.a[0].elementAt(n), n2, array, 0, n3);
            return array;
        }
        return null;
    }
    
    public byte[] h(final int n) throws psc_g {
        if (this.b[0].size() <= n) {
            throw new psc_g("Specified index is invalid.");
        }
        if (this.b[0].elementAt(n) != null) {
            final Integer n2 = this.b[1].elementAt(n);
            final Integer n3 = this.b[2].elementAt(n);
            final byte[] array = new byte[(int)n3];
            System.arraycopy(this.b[0].elementAt(n), n2, array, 0, n3);
            return array;
        }
        return null;
    }
    
    public int a() {
        return this.b[0].size();
    }
    
    public int e() {
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(super.bx, true, 0, 12288, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.a[0].size(); ++i) {
                try {
                    obj.b(this.b(i));
                }
                catch (Exception ex) {
                    return 0;
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.c = new psc_n(anArray);
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    private psc_k b(final int n) throws psc_g {
        try {
            final psc_n psc_n = new psc_n(new psc_i[] { new psc_h(0, true, 0), new psc_r(16777216, true, 0, this.a[0].elementAt(n), this.a[1].elementAt(n), this.a[2].elementAt(n)), new psc_r(16777216, true, 0, this.b[0].elementAt(n), this.b[1].elementAt(n), this.b[2].elementAt(n)), new psc_j() });
            final byte[] array = new byte[psc_n.a()];
            return new psc_k(12288, true, 0, array, 0, psc_n.a(array, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_g(" Can't encode PolicyMappings");
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.c == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.c.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bw psc_bw = new psc_bw();
        for (int i = 0; i < this.a.length; ++i) {
            for (int j = 0; j < this.a[i].size(); ++j) {
                psc_bw.a[i].addElement(this.a[i].elementAt(j));
            }
        }
        for (int k = 0; k < this.b.length; ++k) {
            for (int l = 0; l < this.b[k].size(); ++l) {
                psc_bw.b[k].addElement(this.b[k].elementAt(l));
            }
        }
        if (this.c != null) {
            psc_bw.e();
        }
        super.a(psc_bw);
        return psc_bw;
    }
    
    protected void f() {
        super.f();
        this.a = null;
        this.b = null;
        this.c = null;
    }
}
