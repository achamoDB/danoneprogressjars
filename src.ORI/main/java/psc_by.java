import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_by extends psc_aj implements Cloneable, Serializable, psc_bj
{
    public static byte[] a;
    public static byte[] b;
    public static byte[] c;
    public static byte[] d;
    public static byte[] e;
    public static byte[] f;
    private Vector[] g;
    psc_n h;
    
    public psc_by() {
        this.g = this.a(3);
        super.bu = 37;
        super.bt = false;
        this.c(37);
        super.a = "ExtendedKeyUsage";
    }
    
    public psc_by(final byte[] obj, final int value, final int value2, final boolean bt) {
        this.g = this.a(3);
        super.bu = 37;
        super.bt = bt;
        this.c(37);
        if (obj != null && value2 != 0) {
            this.g[0].addElement(obj);
            this.g[1].addElement(new Integer(value));
            this.g[2].addElement(new Integer(value2));
        }
        super.a = "ExtendedKeyUsage";
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
            final psc_w psc_w = new psc_w(super.bx, 12288, new psc_k(1536));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_r psc_r = new psc_r(16777216);
                psc_l.a(a.b, a.c, new psc_i[] { psc_r });
                this.g[0].addElement(psc_r.b);
                this.g[1].addElement(new Integer(psc_r.c));
                this.g[2].addElement(new Integer(psc_r.d));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode Extended Key Usage extension.");
        }
    }
    
    public void a(final byte[] obj, final int value, final int value2) {
        if (obj != null && value2 != 0) {
            this.g[0].addElement(obj);
            this.g[1].addElement(new Integer(value));
            this.g[2].addElement(new Integer(value2));
        }
    }
    
    public byte[] g(final int n) throws psc_g {
        if (n >= this.g[0].size()) {
            throw new psc_g(" Invalid Index ");
        }
        if (this.g[0].elementAt(n) != null) {
            final Integer n2 = this.g[1].elementAt(n);
            final Integer n3 = this.g[2].elementAt(n);
            final byte[] array = new byte[(int)n3];
            System.arraycopy(this.g[0].elementAt(n), n2, array, 0, n3);
            return array;
        }
        return null;
    }
    
    public int a() {
        return this.g[0].size();
    }
    
    public int e() {
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(super.bx, true, 0, 12288, new psc_k(1536));
            vector.addElement(obj);
            for (int i = 0; i < this.g[0].size(); ++i) {
                try {
                    obj.b(this.b(i));
                }
                catch (Exception ex) {
                    return 0;
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.h = new psc_n(anArray);
            return this.h.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    private psc_k b(final int index) throws psc_v {
        try {
            final psc_n psc_n = new psc_n(new psc_i[] { new psc_r(16777216, true, 0, this.g[0].elementAt(index), this.g[1].elementAt(index), this.g[2].elementAt(index)) });
            final byte[] array = new byte[psc_n.a()];
            return new psc_k(1536, true, 0, array, 0, psc_n.a(array, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_v(" Can't encode Extended Key Usage");
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.h == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.h.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_by psc_by = new psc_by();
        for (int i = 0; i < this.g.length; ++i) {
            for (int j = 0; j < this.g[i].size(); ++j) {
                psc_by.g[i].addElement(this.g[i].elementAt(j));
            }
        }
        if (this.h != null) {
            psc_by.e();
        }
        super.a(psc_by);
        return psc_by;
    }
    
    protected void f() {
        super.f();
        this.g = null;
        this.h = null;
    }
    
    static {
        psc_by.a = new byte[] { 43, 6, 1, 5, 5, 7, 3, 1 };
        psc_by.b = new byte[] { 43, 6, 1, 5, 5, 7, 3, 2 };
        psc_by.c = new byte[] { 43, 6, 1, 5, 5, 7, 3, 3 };
        psc_by.d = new byte[] { 43, 6, 1, 5, 5, 7, 3, 4 };
        psc_by.e = new byte[] { 43, 6, 1, 5, 5, 7, 3, 8 };
        psc_by.f = new byte[] { 43, 6, 1, 5, 5, 7, 3, 9 };
    }
}
