import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_cz extends psc_aj implements Cloneable, Serializable, psc_bj
{
    private Vector[] a;
    psc_n b;
    
    public psc_cz() {
        this.a = this.a();
        super.bu = 123;
        super.bt = false;
        this.a(psc_aj.bl);
        super.a = "QC_Statements";
    }
    
    public psc_cz(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4, final boolean bt) throws psc_g {
        this.a = this.a();
        this.a(array, n, n2, array2, n3, n4);
        super.bu = 123;
        super.bt = bt;
        this.a(psc_aj.bl);
        super.a = "QC_Statements";
    }
    
    private Vector[] a() {
        final Vector[] array = new Vector[3];
        for (int i = 0; i < 3; ++i) {
            array[i] = new Vector();
        }
        return array;
    }
    
    public void d(final byte[] array, final int n) throws psc_g {
        if (array == null || n < 0) {
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
                final psc_k psc_k = new psc_k(130816);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_r, psc_k, psc_j });
                if (psc_k.a) {
                    this.a(psc_r.b, psc_r.c, psc_r.d, psc_k.b, psc_k.c, psc_k.d);
                }
                else {
                    this.a(psc_r.b, psc_r.c, psc_r.d, null, 0, 0);
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode QCStatements extension. " + psc_m.getMessage());
        }
    }
    
    public void a(final byte[] array, final int n, final int n2, final byte[] array2, final int n3, final int n4) throws psc_g {
        if (array != null && n2 > 0 && n >= 0 && array.length >= n2 + n) {
            final byte[] obj = new byte[n2];
            System.arraycopy(array, n, obj, 0, n2);
            this.a[0].addElement(obj);
            if (array2 != null && n4 > 0 && n3 >= 0 && array2.length >= n4 + n3) {
                final byte[] obj2 = new byte[n4];
                System.arraycopy(array2, n3, obj2, 0, n4);
                this.a[1].addElement(obj2);
            }
            else {
                this.a[1].addElement(null);
            }
            return;
        }
        if (array == null) {
            throw new psc_g("Statement ID cannot be null.");
        }
        throw new psc_g("Invalid parameter");
    }
    
    public byte[] a(final int index) throws psc_g {
        if (this.a[0].size() <= index) {
            throw new psc_g("Specified index is invalid.");
        }
        return this.a[0].elementAt(index);
    }
    
    public byte[] b(final int index) throws psc_g {
        if (this.a[1].size() <= index) {
            throw new psc_g("Specified index is invalid.");
        }
        return this.a[1].elementAt(index);
    }
    
    public int g() {
        return this.a[0].size();
    }
    
    public int e() {
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(0, true, 0, 12288, new psc_k(12288));
            final psc_j psc_j = new psc_j();
            vector.addElement(obj);
            for (int i = 0; i < this.a[0].size(); ++i) {
                final byte[] array = this.a[0].elementAt(i);
                byte[] array2;
                if (i < this.a[1].size()) {
                    array2 = this.a[1].elementAt(i);
                }
                else {
                    array2 = null;
                }
                try {
                    final psc_h psc_h = new psc_h(0);
                    final psc_j psc_j2 = new psc_j();
                    final psc_r psc_r = new psc_r(16777216, true, 0, array, 0, array.length);
                    psc_k psc_k;
                    if (array2 != null) {
                        psc_k = new psc_k(130816, true, 0, array2, 0, array2.length);
                    }
                    else {
                        psc_k = new psc_k(130816, false, 0, null, 0, 0);
                    }
                    super.by = new psc_n(new psc_i[] { psc_h, psc_r, psc_k, psc_j2 });
                    final byte[] array3 = new byte[super.by.a()];
                    obj.b(new psc_k(12288, true, 0, array3, 0, super.by.a(array3, 0)));
                }
                catch (psc_m psc_m) {
                    return 0;
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.b = new psc_n(anArray);
            return this.b.a();
        }
        catch (psc_m psc_m2) {
            return 0;
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.b == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.b.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_cz psc_cz = new psc_cz();
        for (int i = 0; i < this.a[0].size(); ++i) {
            psc_cz.a[0].addElement(((byte[])this.a[0].elementAt(i)).clone());
        }
        for (int j = 0; j < this.a[1].size(); ++j) {
            if (this.a[1].elementAt(j) == null) {
                psc_cz.a[1].addElement(null);
            }
            else {
                psc_cz.a[1].addElement(((byte[])this.a[1].elementAt(j)).clone());
            }
        }
        if (this.b != null) {
            psc_cz.e();
        }
        super.a(psc_cz);
        return psc_cz;
    }
    
    protected void f() {
        super.f();
        this.a = this.a();
        this.b = null;
    }
}
