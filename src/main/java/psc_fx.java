import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fx implements Cloneable, Serializable
{
    private byte[] a;
    private int b;
    private int c;
    private byte[] d;
    private int e;
    private int f;
    private byte[] g;
    private int h;
    private int i;
    private Vector j;
    protected int k;
    protected psc_n l;
    private static final int m = 10551296;
    private static final int n = 10551297;
    private static final int o = 10551298;
    private static final int p = 10485763;
    
    public psc_fx(final byte[] array, final int n, final int k) throws psc_v {
        this.j = new Vector();
        this.l = null;
        this.k = k;
        if (array == null) {
            throw new psc_v("NameBER is null.");
        }
        try {
            final psc_h psc_h = new psc_h(k);
            final psc_j psc_j = new psc_j();
            final psc_t psc_t = new psc_t(10551296);
            final psc_t psc_t2 = new psc_t(10551297);
            final psc_t psc_t3 = new psc_t(10551298);
            final psc_k psc_k = new psc_k(10498307);
            psc_l.a(array, n, new psc_i[] { psc_h, psc_t, psc_t2, psc_t3, psc_k, psc_j });
            final psc_w psc_w = new psc_w(10485763, 12544, new psc_t(0));
            psc_l.a(psc_k.b, psc_k.c, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final byte[] obj = new byte[a.d];
                System.arraycopy(a.b, a.c, obj, 0, a.d);
                this.j.addElement(obj);
            }
            if (psc_t.a) {
                this.a = new byte[psc_t.d];
                System.arraycopy(psc_t.b, psc_t.c, this.a, 0, psc_t.d);
                this.b = 0;
                this.c = psc_t.d;
            }
            if (psc_t2.a) {
                this.d = new byte[psc_t2.d];
                System.arraycopy(psc_t2.b, psc_t2.c, this.d, 0, psc_t2.d);
                this.e = 0;
                this.f = psc_t2.d;
            }
            if (psc_t3.a) {
                this.g = new byte[psc_t3.d];
                System.arraycopy(psc_t3.b, psc_t3.c, this.g, 0, psc_t3.d);
                this.h = 0;
                this.i = psc_t3.d;
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the PresentationAddress.");
        }
    }
    
    public psc_fx() {
        this.j = new Vector();
        this.l = null;
    }
    
    public void a(final byte[] array, final int n, final int c) throws psc_v {
        if (array == null || c == 0) {
            throw new psc_v("Data is null.");
        }
        System.arraycopy(array, n, this.a = new byte[c], 0, c);
        this.b = 0;
        this.c = c;
    }
    
    public void b(final byte[] array, final int n, final int f) throws psc_v {
        if (array == null || f == 0) {
            throw new psc_v("Data is null.");
        }
        System.arraycopy(array, n, this.d = new byte[f], 0, f);
        this.e = 0;
        this.f = f;
    }
    
    public void c(final byte[] array, final int n, final int i) throws psc_v {
        if (array == null || i == 0) {
            throw new psc_v("Data is null.");
        }
        System.arraycopy(array, n, this.g = new byte[i], 0, i);
        this.h = 0;
        this.i = i;
    }
    
    public void d(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null || n2 == 0) {
            throw new psc_v("Data is null.");
        }
        final byte[] obj = new byte[n2];
        System.arraycopy(array, n, obj, 0, n2);
        this.j.addElement(obj);
    }
    
    public byte[] b() {
        if (this.a == null) {
            return null;
        }
        final byte[] array = new byte[this.c];
        System.arraycopy(this.a, this.b, array, 0, this.c);
        return array;
    }
    
    public byte[] c() {
        if (this.d == null) {
            return null;
        }
        final byte[] array = new byte[this.f];
        System.arraycopy(this.d, this.e, array, 0, this.f);
        return array;
    }
    
    public byte[] d() {
        if (this.g == null) {
            return null;
        }
        final byte[] array = new byte[this.i];
        System.arraycopy(this.g, this.h, array, 0, this.i);
        return array;
    }
    
    public byte[][] e() {
        final int size = this.j.size();
        final byte[][] array = new byte[size][];
        for (int i = 0; i < size; ++i) {
            final byte[] array2 = this.j.elementAt(i);
            System.arraycopy(array2, 0, array[i] = new byte[array2.length], 0, array2.length);
        }
        return array;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.a != null) {
            sb.append(new String(this.a, this.b, this.c));
        }
        if (this.d != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(new String(this.d, this.e, this.f));
        }
        if (this.g != null) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(new String(this.g, this.h, this.i));
        }
        for (int i = 0; i < this.j.size(); ++i) {
            final byte[] bytes = this.j.elementAt(i);
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(new String(bytes, 0, bytes.length));
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
    
    public int a(final int k) {
        this.k = k;
        return this.a();
    }
    
    public int e(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.l == null || n2 != this.k) && this.a(n2) == 0) {
                throw new psc_v("Unable to encode PresentationAddress");
            }
            final int a = this.l.a(array, n);
            this.l = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.l = null;
            throw new psc_v("Unable to encode PresentationAddress");
        }
    }
    
    private int a() {
        if (this.l != null && this.k == this.k) {
            return 0;
        }
        try {
            final Vector vector = new Vector<psc_w>();
            final psc_w obj = new psc_w(10485763, true, 0, 12544, new psc_t(0));
            vector.addElement(obj);
            for (int i = 0; i < this.j.size(); ++i) {
                final byte[] array = this.j.elementAt(i);
                obj.b(new psc_t(0, true, 0, array, 0, array.length));
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            final psc_n psc_n = new psc_n(anArray);
            final byte[] array2 = new byte[psc_n.a()];
            final psc_k psc_k = new psc_k(10498307, true, 0, array2, 0, psc_n.a(array2, 0));
            final psc_h psc_h = new psc_h(this.k, true, 0);
            final psc_j psc_j = new psc_j();
            psc_t psc_t;
            if (this.a != null) {
                psc_t = new psc_t(10551296, true, 0, this.a, this.b, this.c);
            }
            else {
                psc_t = new psc_t(10551296, false, 0, this.a, this.b, this.c);
            }
            psc_t psc_t2;
            if (this.d != null) {
                psc_t2 = new psc_t(10551297, true, 0, this.d, this.e, this.f);
            }
            else {
                psc_t2 = new psc_t(10551297, false, 0, this.d, this.e, this.f);
            }
            psc_t psc_t3;
            if (this.g != null) {
                psc_t3 = new psc_t(10551298, true, 0, this.g, this.h, this.i);
            }
            else {
                psc_t3 = new psc_t(10551298, false, 0, this.g, this.h, this.i);
            }
            this.l = new psc_n(new psc_i[] { psc_h, psc_t, psc_t2, psc_t3, psc_k, psc_j });
            return this.l.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fx)) {
            return false;
        }
        final psc_fx psc_fx = (psc_fx)o;
        if (this.a != null) {
            if (this.c != psc_fx.c || this.b != psc_fx.b) {
                return false;
            }
            for (int i = 0; i < this.c; ++i) {
                if (this.a[i] != psc_fx.a[i]) {
                    return false;
                }
            }
        }
        else if (psc_fx.a != null) {
            return false;
        }
        if (this.d != null) {
            if (this.f != psc_fx.f || this.e != psc_fx.e) {
                return false;
            }
            for (int j = 0; j < this.f; ++j) {
                if (this.d[j] != psc_fx.d[j]) {
                    return false;
                }
            }
        }
        else if (psc_fx.d != null) {
            return false;
        }
        if (this.g != null) {
            if (this.i != psc_fx.i || this.h != psc_fx.h) {
                return false;
            }
            for (int k = 0; k < this.i; ++k) {
                if (this.g[k] != psc_fx.g[k]) {
                    return false;
                }
            }
        }
        else if (psc_fx.g != null) {
            return false;
        }
        if (this.j.size() != psc_fx.j.size()) {
            return false;
        }
        for (int l = 0; l < this.j.size(); ++l) {
            if (!this.j.elementAt(l).equals(psc_fx.j.elementAt(l))) {
                return false;
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fx psc_fx = new psc_fx();
        if (this.a != null) {
            psc_fx.b = this.b;
            psc_fx.c = this.c;
            psc_fx.a = new byte[this.c];
            System.arraycopy(this.a, this.b, psc_fx.a, psc_fx.b, this.c);
        }
        if (this.d != null) {
            psc_fx.e = this.e;
            psc_fx.f = this.f;
            psc_fx.d = new byte[this.f];
            System.arraycopy(this.d, this.e, psc_fx.d, psc_fx.e, this.f);
        }
        if (this.g != null) {
            psc_fx.h = this.h;
            psc_fx.i = this.i;
            psc_fx.g = new byte[this.i];
            System.arraycopy(this.g, this.h, psc_fx.g, psc_fx.h, this.i);
        }
        psc_fx.j = (Vector)this.j.clone();
        psc_fx.k = this.k;
        if (this.l != null) {
            psc_fx.a();
        }
        return psc_fx;
    }
}
