import java.util.Vector;
import java.io.Serializable;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_fq implements Cloneable, Serializable
{
    private Vector a;
    protected int b;
    protected psc_n c;
    
    public psc_fq(final byte[] array, final int n, final int b) throws psc_v {
        this.a = new Vector();
        this.c = null;
        this.b = b;
        if (array == null) {
            throw new psc_v("Encoding is null.");
        }
        try {
            final psc_w psc_w = new psc_w(b, 12544, new psc_k(12288));
            psc_l.a(array, n, new psc_i[] { psc_w });
            for (int j = psc_w.j(), i = 0; i < j; ++i) {
                final psc_i a = psc_w.a(i);
                this.a.addElement(new psc_fr(a.b, a.c, 0));
            }
        }
        catch (psc_m psc_m) {
            throw new psc_v("Cannot decode the BER of ExtensionAttributes.");
        }
    }
    
    public psc_fq() {
        this.a = new Vector();
        this.c = null;
    }
    
    public void a(final psc_fr obj) {
        if (obj != null) {
            this.a.addElement(obj);
        }
    }
    
    public psc_fr a(final int index) throws psc_v {
        if (index >= this.a.size()) {
            throw new psc_v(" Invalid index ");
        }
        return this.a.elementAt(index);
    }
    
    public int b() {
        return this.a.size();
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        final int index = this.a.size() - 1;
        for (int i = 0; i < index; ++i) {
            sb.append(((psc_fr)this.a.elementAt(i)).toString());
            sb.append(",");
        }
        sb.append(((psc_fr)this.a.elementAt(index)).toString());
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
    
    public int b(final int b) throws psc_v {
        this.b = b;
        return this.a();
    }
    
    public int a(final byte[] array, final int n, final int n2) throws psc_v {
        if (array == null) {
            throw new psc_v("Specified array is null.");
        }
        try {
            if (this.c == null || n2 != this.b) {
                this.b(n2);
            }
            final int a = this.c.a(array, n);
            this.c = null;
            return a;
        }
        catch (psc_m psc_m) {
            this.c = null;
            throw new psc_v("Unable to encode ExtensionAttributes");
        }
    }
    
    private int a() {
        if (this.c != null && this.b == this.b) {
            return 0;
        }
        try {
            final Vector vector = new Vector<psc_w>();
            final psc_w obj = new psc_w(this.b, true, 0, 12544, new psc_k(12288));
            vector.addElement(obj);
            for (int i = 0; i < this.a.size(); ++i) {
                final byte[] array = new byte[this.a.elementAt(i).a(0)];
                obj.b(new psc_k(0, true, 0, array, 0, this.a.elementAt(i).a(array, 0, 0)));
            }
            final psc_i[] anArray = new psc_i[vector.size()];
            vector.copyInto(anArray);
            this.c = new psc_n(anArray);
            return this.c.a();
        }
        catch (psc_m psc_m) {
            return 0;
        }
        catch (psc_v psc_v) {
            return 0;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof psc_fq)) {
            return false;
        }
        final psc_fq psc_fq = (psc_fq)o;
        if (this.a.size() != psc_fq.a.size()) {
            return false;
        }
        for (int i = 0; i < this.a.size(); ++i) {
            if (!((psc_fr)this.a.elementAt(i)).equals(psc_fq.a.elementAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final psc_fq psc_fq = new psc_fq();
        for (int i = 0; i < this.a.size(); ++i) {
            psc_fq.a.addElement(this.a.elementAt(i));
        }
        psc_fq.b = this.b;
        if (this.c != null) {
            psc_fq.a();
        }
        return psc_fq;
    }
}
