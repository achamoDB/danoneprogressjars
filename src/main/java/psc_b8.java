import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_b8 extends psc_aj implements Cloneable, Serializable, psc_bj
{
    public static final int a = 7;
    public static final int b = -33554432;
    public static final int c = Integer.MIN_VALUE;
    public static final int d = 1073741824;
    public static final int e = 536870912;
    public static final int f = 268435456;
    public static final int g = 134217728;
    public static final int h = 67108864;
    public static final int i = 33554432;
    private Vector[] j;
    private static final int k = 10551296;
    private static final int l = 8454145;
    private static final int m = 8454146;
    private static final int n = 8388608;
    private static final int o = 8388609;
    psc_n p;
    
    public psc_b8() {
        this.j = this.a();
        super.bu = 31;
        super.bt = false;
        this.c(31);
        super.a = "CRLDistributionPoints";
    }
    
    public psc_b8(final psc_x obj, final int value, final psc_bm obj2, final boolean bt) {
        this.j = this.a();
        super.bu = 31;
        super.bt = bt;
        this.c(31);
        this.j[0].addElement(obj);
        this.j[1].addElement(new Integer(value));
        this.j[2].addElement(obj2);
        super.a = "CRLDistributionPoints";
    }
    
    public psc_b8(final psc_bm obj, final int value, final psc_bm obj2, final boolean bt) {
        this.j = this.a();
        super.bu = 31;
        super.bt = bt;
        this.c(31);
        this.j[0].addElement(obj);
        this.j[1].addElement(new Integer(value));
        this.j[2].addElement(obj2);
        super.a = "CRLDistributionPoints";
    }
    
    private Vector[] a() {
        final Vector[] array = new Vector[3];
        for (int i = 0; i < 3; ++i) {
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
                final psc_z psc_z = new psc_z(10551296);
                final psc_k psc_k = new psc_k(8400896);
                final psc_k psc_k2 = new psc_k(8401153);
                final psc_d4 psc_d4 = new psc_d4(8454145);
                final psc_k psc_k3 = new psc_k(8466434);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_z, psc_k, psc_k2, psc_j, psc_d4, psc_k3, psc_j });
                if (psc_k.a) {
                    this.j[0].addElement(new psc_bm(psc_k.b, psc_k.c, 8388608));
                }
                else if (psc_k2.a) {
                    this.j[0].addElement(new psc_x(psc_k2.b, psc_k2.c, 8388609));
                }
                else {
                    this.j[0].addElement(null);
                }
                if (psc_d4.a) {
                    if (psc_d4.d > 4) {
                        throw new psc_g("Could not decode CRLDistributionPoints extension.");
                    }
                    if (psc_d4.d == 0) {
                        this.j[1].addElement(new Integer(0));
                    }
                    else {
                        int n2 = 0;
                        for (int k = psc_d4.c, n3 = 24; k < psc_d4.c + psc_d4.d; ++k, n3 -= 8) {
                            n2 |= (psc_d4.b[k] & 0xFF) << n3;
                        }
                        this.j[1].addElement(new Integer(n2 & 0xFE000000));
                    }
                }
                else {
                    this.j[1].addElement(new Integer(-1));
                }
                if (psc_k3.a) {
                    this.j[2].addElement(new psc_bm(psc_k3.b, psc_k3.c, 8454146));
                }
                else {
                    this.j[2].addElement(null);
                }
            }
        }
        catch (psc_m psc_m) {
            throw new psc_g("Could not decode CRLDistributionPoints extension.");
        }
        catch (psc_v psc_v) {
            throw new psc_g("Could not create new GeneralNames object.");
        }
    }
    
    public void a(final psc_x obj, int value, final psc_bm obj2) {
        this.j[0].addElement(obj);
        if (value == -1) {
            this.j[1].addElement(new Integer(-1));
        }
        else {
            value &= 0xFE000000;
            this.j[1].addElement(new Integer(value));
        }
        this.j[2].addElement(obj2);
    }
    
    public void a(final psc_bm obj, int value, final psc_bm obj2) {
        this.j[0].addElement(obj);
        if (value == -1) {
            this.j[1].addElement(new Integer(-1));
        }
        else {
            value &= 0xFE000000;
            this.j[1].addElement(new Integer(value));
        }
        this.j[2].addElement(obj2);
    }
    
    public Object g(final int index) throws psc_v {
        if (this.j[0].size() <= index) {
            throw new psc_v("Specified index is invalid.");
        }
        if (this.j[0] == null) {
            return null;
        }
        return this.j[0].elementAt(index);
    }
    
    public int h(final int index) throws psc_v {
        if (this.j[1].size() <= index) {
            throw new psc_v("Specified index is invalid.");
        }
        return this.j[1].elementAt(index);
    }
    
    public psc_bm i(final int index) throws psc_v {
        if (this.j[2].size() <= index) {
            throw new psc_v("Specified index is invalid.");
        }
        return this.j[2].elementAt(index);
    }
    
    public int g() {
        return this.j[0].size();
    }
    
    public int e() {
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(0, true, 0, 12288, new psc_k(12288));
            final psc_j psc_j = new psc_j();
            vector.addElement(obj);
            for (int i = 0; i < this.j[0].size(); ++i) {
                try {
                    int n = 0;
                    final psc_h psc_h = new psc_h(0, true, 0);
                    psc_i psc_i = null;
                    psc_i psc_i2 = null;
                    psc_i b = null;
                    psc_i a = null;
                    psc_n psc_n = null;
                    if (this.j[0].elementAt(i) != null) {
                        psc_i = new psc_z(10551296, 0);
                        a = this.a(i);
                        n = 1;
                    }
                    final int intValue = this.j[1].elementAt(i);
                    if (intValue != -1) {
                        psc_i2 = new psc_d4(8454145, true, 0, intValue, 7, true);
                        if (n == 0) {
                            n = 2;
                        }
                        else {
                            n = 3;
                        }
                    }
                    if (this.j[2].elementAt(i) != null) {
                        b = this.b(i);
                        if (n == 0) {
                            n = 4;
                        }
                        else if (n == 1) {
                            n = 5;
                        }
                        else if (n == 2) {
                            n = 6;
                        }
                        else if (n == 3) {
                            n = 7;
                        }
                    }
                    switch (n) {
                        case 0: {
                            psc_n = new psc_n(new psc_i[] { psc_h, psc_j });
                            break;
                        }
                        case 1: {
                            psc_n = new psc_n(new psc_i[] { psc_h, psc_i, a, psc_j, psc_j });
                            break;
                        }
                        case 2: {
                            psc_n = new psc_n(new psc_i[] { psc_h, psc_i2, psc_j });
                            break;
                        }
                        case 3: {
                            psc_n = new psc_n(new psc_i[] { psc_h, psc_i, a, psc_j, psc_i2, psc_j });
                            break;
                        }
                        case 4: {
                            psc_n = new psc_n(new psc_i[] { psc_h, b, psc_j });
                            break;
                        }
                        case 5: {
                            psc_n = new psc_n(new psc_i[] { psc_h, psc_i, a, psc_j, b, psc_j });
                            break;
                        }
                        case 6: {
                            psc_n = new psc_n(new psc_i[] { psc_h, psc_i2, b, psc_j });
                            break;
                        }
                        case 7: {
                            psc_n = new psc_n(new psc_i[] { psc_h, psc_i, a, psc_j, psc_i2, b, psc_j });
                            break;
                        }
                    }
                    final byte[] array = new byte[psc_n.a()];
                    obj.b(new psc_k(12288, true, 0, array, 0, psc_n.a(array, 0)));
                }
                catch (psc_m psc_m) {
                    return 0;
                }
                catch (psc_g psc_g) {
                    return 0;
                }
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.p = new psc_n(anArray);
            return this.p.a();
        }
        catch (psc_m psc_m2) {
            return 0;
        }
    }
    
    private psc_k a(final int n) throws psc_g {
        psc_k psc_k = null;
        try {
            if (this.j[0].elementAt(n) instanceof psc_bm) {
                final byte[] array = new byte[this.j[0].elementAt(n).c(8388608)];
                psc_k = new psc_k(12288, true, 0, array, 0, this.j[0].elementAt(n).a(array, 0, 8388608));
            }
            else if (this.j[0].elementAt(n) instanceof psc_x) {
                final byte[] array2 = new byte[this.j[0].elementAt(n).d(8388609)];
                psc_k = new psc_k(12544, true, 0, array2, 0, this.j[0].elementAt(n).b(array2, 0, 8388609));
            }
            return psc_k;
        }
        catch (psc_m psc_m) {
            throw new psc_g("Can't encode DistributionPointNames" + psc_m.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_g("Can't encode DistributionPointNames" + psc_v.getMessage());
        }
    }
    
    private psc_k b(final int n) throws psc_g {
        try {
            final byte[] array = new byte[this.j[2].elementAt(n).c(8454146)];
            return new psc_k(12288, true, 0, array, 0, this.j[2].elementAt(n).a(array, 0, 8454146));
        }
        catch (psc_m psc_m) {
            throw new psc_g("Can't encode cRLIssuer" + psc_m.getMessage());
        }
        catch (psc_v psc_v) {
            throw new psc_g("Can't encode cRLIssuer" + psc_v.getMessage());
        }
    }
    
    public int e(final byte[] array, final int n) {
        if (array == null) {
            return 0;
        }
        if (this.p == null && this.e() == 0) {
            return 0;
        }
        try {
            final int a = this.p.a(array, n);
            super.by = null;
            return a;
        }
        catch (psc_m psc_m) {
            super.by = null;
            return 0;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_b8 psc_b8 = new psc_b8();
        for (int i = 0; i < this.j[0].size(); ++i) {
            if (this.j[0].elementAt(i) == null) {
                psc_b8.j[0].addElement(null);
            }
            if (this.j[0].elementAt(i) instanceof psc_bm) {
                psc_b8.j[0].addElement(((psc_bm)this.j[0].elementAt(i)).clone());
            }
            else if (this.j[0].elementAt(i) instanceof psc_x) {
                psc_b8.j[0].addElement(((psc_x)this.j[0].elementAt(i)).clone());
            }
            psc_b8.j[1].addElement(new Integer((int)this.j[1].elementAt(i)));
            if (this.j[2].elementAt(i) == null) {
                psc_b8.j[2].addElement(null);
            }
            else {
                psc_b8.j[2].addElement(((psc_bm)this.j[2].elementAt(i)).clone());
            }
        }
        if (this.p != null) {
            psc_b8.e();
        }
        super.a(psc_b8);
        return psc_b8;
    }
    
    protected void f() {
        super.f();
        this.j = null;
        this.p = null;
    }
}
