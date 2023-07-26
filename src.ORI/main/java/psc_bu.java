import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bu implements Cloneable, Serializable
{
    private static final int a = 8519680;
    private static final int b = 8454145;
    private Vector[] c;
    protected static int d;
    private psc_n e;
    
    public psc_bu() {
        this.c = this.a(3);
        this.e = null;
    }
    
    public psc_bu(final byte[] array, final int n, final int n2) throws psc_v {
        this.c = this.a(3);
        this.e = null;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(n2, 12288, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_h psc_h = new psc_h(0);
                final psc_j psc_j = new psc_j();
                final psc_k psc_k = new psc_k(65280);
                final psc_p psc_p = new psc_p(8519680);
                final psc_p psc_p2 = new psc_p(8454145);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_k, psc_p, psc_p2, psc_j });
                this.c[0].addElement(new psc_fh(psc_k.b, psc_k.c, 0));
                if (psc_p.a) {
                    this.c[1].addElement(new Integer(this.a(psc_p.b, psc_p.c, psc_p.d)));
                }
                else {
                    this.c[1].addElement(new Integer(0));
                }
                if (psc_p2.a) {
                    this.c[2].addElement(new Integer(this.a(psc_p2.b, psc_p2.c, psc_p2.d)));
                }
                else {
                    this.c[2].addElement(new Integer(-1));
                }
            }
        }
        catch (Exception ex) {
            throw new psc_v("Cannot decode the BER of GeneralSubtrees.");
        }
    }
    
    private int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (n2 == 0 || array == null) {
            return 0;
        }
        if (n2 > 4) {
            throw new psc_v("Could not decode General Subtrees.");
        }
        int n3 = 0;
        for (int i = n; i < n2 + n; ++i) {
            n3 = (n3 << 8 | (array[i] & 0xFF));
        }
        return n3;
    }
    
    public void a(final psc_fh obj, int value, int value2) {
        if (obj != null) {
            this.c[0].addElement(obj);
            if (value < 0) {
                value = 0;
            }
            this.c[1].addElement(new Integer(value));
            if (value2 < 0) {
                value2 = -1;
            }
            this.c[2].addElement(new Integer(value2));
        }
    }
    
    public psc_fh c(final int index) throws psc_v {
        if (this.c[0].size() <= index) {
            throw new psc_v("Specified index is invalid.");
        }
        return this.c[0].elementAt(index);
    }
    
    public int d(final int index) throws psc_v {
        if (this.c[1].size() <= index) {
            throw new psc_v("Specified index is invalid.");
        }
        return this.c[1].elementAt(index);
    }
    
    public int e(final int index) throws psc_v {
        if (this.c[2].size() <= index) {
            throw new psc_v("Specified index is invalid.");
        }
        return this.c[2].elementAt(index);
    }
    
    private Vector[] a(final int n) {
        final Vector[] array = new Vector[n];
        for (int i = 0; i < n; ++i) {
            array[i] = new Vector();
        }
        return array;
    }
    
    public int b() {
        return this.c[0].size();
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.c[0].size() == 0) {
            return "";
        }
        for (int i = 0; i < this.c[0].size(); ++i) {
            sb.append(((psc_fh)this.c[0].elementAt(i)).toString());
            sb.append("   min = ");
            sb.append(this.c[1].elementAt(i));
            sb.append(", max = ");
            sb.append(this.c[2].elementAt(i));
            sb.append(" \n");
        }
        return sb.toString();
    }
    
    public static int a(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        if (array[n] == 0 && array[n + 1] == 0) {
            return n + 2;
        }
        try {
            return n + 1 + psc_o.a(array, n + 1) + psc_o.b(array, n + 1);
        }
        catch (psc_m psc_m) {
            throw new psc_v("Unable to determine length of the BER");
        }
    }
    
    public int f(final int d) throws psc_v {
        psc_bu.d = d;
        return this.a();
    }
    
    public int b(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.e == null || n2 != psc_bu.d) {
                this.f(n2);
            }
            final int a = this.e.a(array, n);
            this.e = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.e = null;
            throw new psc_v("Unable to encode GeneralTrees.");
        }
    }
    
    private int a() {
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(psc_bu.d, true, 0, 12288, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.c[0].size(); ++i) {
                try {
                    obj.b(this.b(i));
                }
                catch (psc_m psc_m) {
                    return 0;
                }
                catch (psc_v psc_v) {
                    return 0;
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.e = new psc_n(anArray);
            return this.e.a();
        }
        catch (psc_m psc_m2) {
            return 0;
        }
    }
    
    private psc_k b(final int n) throws psc_v {
        final psc_h psc_h = new psc_h(0, true, 0);
        final psc_j psc_j = new psc_j();
        final byte[] array = new byte[this.c[0].elementAt(n).a(0)];
        final int n2 = 0;
        final int a = this.c[0].elementAt(n).a(array, n2, 0);
        psc_k psc_k2;
        try {
            final psc_k psc_k = new psc_k(65280, true, 0, array, n2, a);
            final int intValue = this.c[1].elementAt(n);
            psc_p psc_p;
            if (intValue == 0) {
                psc_p = new psc_p(8519680, false, 0, 0);
            }
            else {
                psc_p = new psc_p(8519680, true, 0, intValue);
            }
            final int intValue2 = this.c[2].elementAt(n);
            psc_n psc_n;
            if (intValue2 == -1) {
                psc_n = new psc_n(new psc_i[] { psc_h, psc_k, psc_p, psc_j });
            }
            else {
                psc_n = new psc_n(new psc_i[] { psc_h, psc_k, psc_p, new psc_p(8454145, true, 0, intValue2), psc_j });
            }
            final byte[] array2 = new byte[psc_n.a()];
            psc_k2 = new psc_k(12288, true, 0, array2, 0, psc_n.a(array2, 0));
        }
        catch (psc_m psc_m) {
            throw new psc_v(" Can't encode GeneralSubtrees");
        }
        return psc_k2;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_bu)) {
            return false;
        }
        final psc_bu psc_bu = (psc_bu)o;
        final int length = this.c.length;
        if (length != psc_bu.c.length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final int size = this.c[i].size();
            if (size != psc_bu.c[i].size()) {
                return false;
            }
            for (int j = 0; j < size; ++j) {
                if (!this.c[i].elementAt(j).equals(psc_bu.c[i].elementAt(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bu psc_bu = new psc_bu();
        for (int i = 0; i < this.c.length; ++i) {
            for (int j = 0; j < this.c[i].size(); ++j) {
                psc_bu.c[i].addElement(this.c[i].elementAt(j));
            }
        }
        psc_bu.d = psc_bu.d;
        if (this.e != null) {
            psc_bu.a();
        }
        return psc_bu;
    }
}
