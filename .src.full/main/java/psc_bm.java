import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_bm implements Cloneable, Serializable
{
    private Vector a;
    protected static int b;
    private psc_n c;
    
    public psc_bm(final byte[] array, final int n, final int n2) throws psc_v {
        this.a = new Vector();
        this.c = null;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(n2, 12288, new psc_k(65280));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a.addElement(new psc_fh(a.b, a.c, 0));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of the name.");
        }
        catch (psc_v psc_v) {
            throw new psc_v("Cannot decode the BER of the name.");
        }
    }
    
    public psc_bm() {
        this.a = new Vector();
        this.c = null;
    }
    
    public void a(final psc_fh obj) {
        if (obj != null) {
            this.a.addElement(obj);
        }
    }
    
    public Vector b() {
        return this.a;
    }
    
    public psc_fh a(final int index) throws psc_v {
        if (index < this.a.size()) {
            return this.a.elementAt(index);
        }
        throw new psc_v("Invalid index.");
    }
    
    public void b(final int index) throws psc_v {
        if (index < this.a.size()) {
            this.a.removeElementAt(index);
            return;
        }
        throw new psc_v("Invalid Index.");
    }
    
    public void a(final psc_fh obj, final int index) throws psc_v {
        if (obj == null) {
            throw new psc_v("Specified name is null.");
        }
        if (index < this.a.size()) {
            this.a.setElementAt(obj, index);
            return;
        }
        throw new psc_v("Invalid index.");
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.a.size(); ++i) {
            sb.append(((psc_fh)this.a.elementAt(i)).toString());
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public int c() {
        return this.a.size();
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
    
    public int c(final int b) throws psc_v {
        psc_bm.b = b;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if ((this.c == null || n2 != psc_bm.b) && this.c(n2) == 0) {
                throw new psc_v("Cannot encode GeneralNames.");
            }
            final int a = this.c.a(array, n);
            this.c = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.c = null;
            throw new psc_v("Unable to encode GeneralNames.");
        }
    }
    
    private int a() {
        final Vector vector = new Vector<psc_w>();
        try {
            final psc_w obj = new psc_w(psc_bm.b, true, 0, 12288, new psc_k(65280));
            vector.addElement(obj);
            for (int i = 0; i < this.a.size(); ++i) {
                try {
                    final psc_fh psc_fh = this.a.elementAt(i);
                    final byte[] array = new byte[psc_fh.a(0)];
                    obj.b(new psc_k(0, true, 0, array, 0, psc_fh.a(array, 0, 0)));
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
            this.c = new psc_n(anArray);
            return this.c.a();
        }
        catch (psc_m psc_m2) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_bm)) {
            return false;
        }
        final psc_bm psc_bm = (psc_bm)o;
        final int size = this.a.size();
        if (size != psc_bm.a.size()) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (!this.a.elementAt(i).equals(psc_bm.a.elementAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean b(final psc_fh psc_fh) {
        if (psc_fh == null) {
            return false;
        }
        try {
            for (int i = 0; i < this.c(); ++i) {
                if (psc_fh.equals(this.a(i))) {
                    return true;
                }
            }
            return false;
        }
        catch (psc_v psc_v) {
            return false;
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_bm psc_bm = new psc_bm();
        psc_bm.a = (Vector)this.a.clone();
        psc_bm.b = psc_bm.b;
        if (this.c != null) {
            psc_bm.a();
        }
        return psc_bm;
    }
}
