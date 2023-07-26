import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_x implements Cloneable, Serializable
{
    private Vector a;
    protected int b;
    protected psc_n c;
    
    public psc_x(final byte[] array, final int n, final int b) throws psc_v {
        this.a = new Vector();
        this.c = null;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        this.b = b;
        try {
            final psc_w psc_w = new psc_w(b, 12544, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                final psc_h psc_h = new psc_h(0);
                final psc_r psc_r = new psc_r(16777216);
                final psc_k psc_k = new psc_k(65280);
                psc_l.a(a.b, a.c, new psc_i[] { psc_h, psc_r, psc_k, new psc_j() });
                final int a2 = a(psc_r.b, psc_r.c, psc_r.d);
                byte[] array2 = null;
                if (a2 == -1 && psc_r.b != null && psc_r.d > 0) {
                    array2 = new byte[psc_r.d];
                    System.arraycopy(psc_r.b, psc_r.c, array2, 0, psc_r.d);
                }
                this.a(new psc_y(a2, array2, psc_k.b, psc_k.c, psc_k.d));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the RDN.");
        }
    }
    
    public psc_x() {
        this.a = new Vector();
        this.c = null;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_x psc_x = new psc_x();
        for (int i = 0; i < this.a.size(); ++i) {
            psc_x.a.addElement(((psc_y)this.a.elementAt(i)).clone());
        }
        psc_x.b = this.b;
        if (this.c != null) {
            psc_x.a();
        }
        return psc_x;
    }
    
    public String toString() {
        return this.a(true);
    }
    
    public String a(final boolean b) {
        final StringBuffer sb = new StringBuffer();
        if (this.a.size() == 0) {
            return "";
        }
        for (int i = 0; i < this.a.size(); ++i) {
            sb.append(((psc_y)this.a.elementAt(i)).b(b));
            if (i < this.a.size() - 1) {
                sb.append("+");
            }
        }
        return sb.toString();
    }
    
    public int b() {
        return this.a.size();
    }
    
    public psc_y a(final int index) throws psc_v {
        final int size = this.a.size();
        if (index >= 0 && size > index) {
            return this.a.elementAt(index);
        }
        throw new psc_v("Invalid Index.");
    }
    
    public void b(final int index) throws psc_v {
        if (index < this.a.size()) {
            this.a.removeElementAt(index);
            return;
        }
        throw new psc_v("Invalid Iidex.");
    }
    
    public void a(final psc_y obj, final int index) throws psc_v {
        if (obj == null) {
            throw new psc_v("Specified AVA is null.");
        }
        if (index < this.a.size()) {
            this.a.setElementAt(obj, index);
            return;
        }
        throw new psc_v("Invalid index.");
    }
    
    public psc_y c(final int n) {
        for (int size = this.a.size(), i = 0; i < size; ++i) {
            final psc_y psc_y = this.a.elementAt(i);
            if (psc_y.d() == n) {
                return psc_y;
            }
        }
        return null;
    }
    
    private static int a(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return -1;
        }
        for (int i = 0; i < psc_y.bx.length; ++i) {
            if (n2 == psc_y.bx[i].a.length) {
                int n3;
                for (n3 = 0; n3 < n2 && (array[n3 + n] & 0xFF) == (psc_y.bx[i].a[n3] & 0xFF); ++n3) {}
                if (n3 >= n2) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int a(final byte[] array, final int n) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
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
    
    public int d(final int b) {
        this.b = b;
        return this.a();
    }
    
    public int b(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.c == null || n2 != this.b) {
                this.d(n2);
                if (this.c == null) {
                    throw new psc_v("Unable to encode RDN.");
                }
            }
            return this.c.a(array, n);
        }
        catch (psc_m psc_m) {
            this.c = null;
            throw new psc_v("Unable to encode RDN.");
        }
    }
    
    private int a() {
        try {
            final int size = this.a.size();
            final psc_w psc_w = new psc_w(this.b, true, 0, 12544, new psc_k(12288));
            for (int i = 0; i < size; ++i) {
                final psc_y psc_y = this.a.elementAt(i);
                final byte[] array = new byte[psc_y.h()];
                psc_w.b(new psc_k(12288, true, 0, array, 0, psc_y.b(array, 0)));
            }
            this.c = new psc_n(new psc_i[] { psc_w });
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public void a(final psc_y obj) {
        if (obj != null) {
            this.a.addElement(obj);
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_x)) {
            return false;
        }
        final psc_x psc_x = (psc_x)o;
        if (this.a.size() != psc_x.a.size()) {
            return false;
        }
        for (int i = 0; i < this.a.size(); ++i) {
            if (!this.a((psc_y)this.a.elementAt(i), psc_x.a)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean a(final psc_y psc_y, final Vector vector) {
        if (psc_y == null || vector == null) {
            return false;
        }
        for (int i = 0; i < vector.size(); ++i) {
            if (psc_y.equals(vector.elementAt(i))) {
                return true;
            }
        }
        return false;
    }
}
